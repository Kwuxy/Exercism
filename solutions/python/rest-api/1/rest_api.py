import json
from dataclasses import dataclass, field, asdict


@dataclass
class User:
    name: str
    owes: dict[str, float] = field(default_factory=dict)
    owed_by: dict[str, float] = field(default_factory=dict)
    balance: float = 0.0


@dataclass
class Transaction:
    lender: User
    borrower: User
    amount: float


class RestAPI:
    def __init__(self, database=None):
        self.trello = TrelloService(database['users'])

    def get(self, url, payload=None):
        payload = json.loads(payload) if payload else None
        if url == '/users':
            return self.get_users(payload)

        raise Exception(f'Invalid URL: {url}')

    def get_users(self, json_dict):
        if json_dict is None:
            return json.dumps({'users': self.trello.get_all_users()})

        users = self.trello.get_users(json_dict['users'])
        users = list(map(asdict, users))
        return json.dumps({'users': users})

    def post(self, url, payload=None):
        payload = json.loads(payload)
        if url == '/add':
            return self.post_add_user(payload)
        if url == '/iou':
            return self.post_iou(payload)

        raise Exception(f'Invalid URL: {url}')

    def post_add_user(self, json_dict: dict):
        user_name = json_dict['user']
        self.trello.add_user({'name': user_name})
        return json.dumps(asdict(self.trello.get_user(user_name)))

    def post_iou(self, json_dict: dict):
        lender = self.trello.get_user(json_dict['lender'])
        borrower = self.trello.get_user(json_dict['borrower'])
        transaction = Transaction(lender, borrower, json_dict['amount'])
        TrelloService.borrow(transaction)
        return self.get_users({'users': [lender.name, borrower.name]})


class TrelloService:
    def __init__(self, users):
        self.users = {}
        for user in users:
            self.add_user(user)

    def add_user(self, json_dict):
        user = User(**json_dict)
        self.users[user.name] = user

    @staticmethod
    def borrow(transaction: Transaction):
        lender, borrower, amount = transaction.lender, transaction.borrower, transaction.amount

        if borrower.name in lender.owes:
            old_debt = lender.owes[borrower.name]
            amount = old_debt - amount
            lender.balance += old_debt
            borrower.balance -= old_debt
            if amount > 0:
                lender, borrower = borrower, lender
            else:
                del lender.owes[borrower.name]
                del borrower.owed_by[lender.name]
                amount *= -1

        if amount == 0:
            return

        lender.owed_by[borrower.name] = amount
        lender.balance += amount
        borrower.owes[lender.name] = amount
        borrower.balance -= amount

    def get_all_users(self):
        return list(self.users.values())

    def get_users(self, user_names: list[str]):
        user_names = sorted(user_names)
        users = [self.get_user(user_name) for user_name in user_names]
        return users

    def get_user(self, user_name: str):
        return self.users[user_name]
