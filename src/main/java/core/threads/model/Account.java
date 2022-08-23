package core.threads.model;

import core.threads.util.ThreadUtils;

public class Account {
    private final String name;
    private int balance;

    public Account(String name, int initBalance) {
        this.name = name;
        balance = initBalance;
    }

    public int getBalance() {
        return this.balance;
    }

    public void addBalance(int value) {
        //System.out.println("Zwiększ saldo konta " + this.name + " [" + this.balance + "] o " + value);
        this.balance += value;
        //System.out.println("Saldo konta " + this.name + " [" + this.balance + "]");

    }

    public void removeBalance(int value) {
        ThreadUtils.sleepThread(100L);
        //System.out.println("Zmniejsz saldo konta " + this.name + " [" + this.balance + "] o " + value);
        this.balance -= value;
        //System.out.println("Saldo konta " + this.name + " [" + this.balance + "]");

    }

    public void checkBalance() {
        System.out.println("Saldo konta " + this.name + ": " + this.balance);
    }


}