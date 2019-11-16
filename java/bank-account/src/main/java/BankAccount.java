class BankAccount {

    private int balance;
    private boolean opened ;

    BankAccount() {
        balance = 0;
        opened = false;
    }

    public void open() {
        opened = true;
    }

    public synchronized void deposit(int credit) throws BankAccountActionInvalidException {
        if(!opened) throw new BankAccountActionInvalidException("Account closed");
        if(credit < 0) throw new BankAccountActionInvalidException("Cannot deposit or withdraw negative amount");

        balance += credit;
    }

    public synchronized void withdraw(int debit) throws BankAccountActionInvalidException {
        if(!opened) throw new BankAccountActionInvalidException("Account closed");
        if(balance == 0) throw new BankAccountActionInvalidException("Cannot withdraw money from an empty account");
        if(debit > balance) throw new BankAccountActionInvalidException("Cannot withdraw more money than is currently in the account");
        if(debit < 0) throw new BankAccountActionInvalidException("Cannot deposit or withdraw negative amount");

        balance -= debit;
    }

    public void close() {
        opened = false;
    }

    public int getBalance() throws BankAccountActionInvalidException {
        if(!opened) throw new BankAccountActionInvalidException("Account closed");

        return balance;
    }
}