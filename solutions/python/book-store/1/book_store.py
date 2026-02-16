from collections import Counter
from itertools import combinations
from typing import List, Tuple

PRICE = 800
DISCOUNTS = {0: 0, 1: 100, 2: 95, 3: 90, 4: 80, 5: 75}
TITLE_COUNT = 5

def total(basket: List[int]):
    return _get_best_price(basket)

# Could use @lru_cache(maxsize=None) from functools instead of handling cache manually
def _get_best_price(basket: List[int]) -> int:
    def _best_price_state(state: Tuple[int, ...]) -> int:
        if state in cache:
            return cache[state]

        if all(i == 0 for i in state):
            cache[state] = 0
            return 0

        books_available = [i for i in range(1, TITLE_COUNT + 1) if state[i - 1] > 0]
        minimal_cost = float('inf')

        for i in range(len(books_available), 0, -1):
            for comb in combinations(books_available, i):
                next_state = tuple(state[i] - (1 if i + 1 in comb else 0) for i in range(TITLE_COUNT))
                minimal_cost = min(_get_group_price(i) + _best_price_state(next_state), minimal_cost)

        minimal_cost = int(minimal_cost)
        cache[state] = minimal_cost
        return minimal_cost

    cache = {}
    counts = Counter(basket)
    st = tuple(counts.get(i, 0) for i in range(1, TITLE_COUNT + 1))
    return _best_price_state(st)

def _get_group_price(books: int) -> int:
    return books * PRICE * DISCOUNTS[books] // 100
