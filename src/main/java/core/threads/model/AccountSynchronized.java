package core.threads.model;

public class AccountSynchronized {
    private final String name;
    private int balance;

    public AccountSynchronized(String name, int initBalance) {
        this.name = name;
        balance = initBalance;
    }

    public int getBalance() {
        return this.balance;
    }

    public synchronized void addBalance(int value) {
        this.balance += value;

    }

    public synchronized void removeBalance(int value) {
        this.balance -= value;
    }

    public void checkBalance() {
        System.out.println("Saldo konta " + this.name + ": " + this.balance);
    }


}