package core.threads;

import core.threads.model.Account;
import core.threads.model.AccountSynchronized;
import core.threads.util.ThreadUtils;

import java.util.HashMap;
import java.util.Map;

public class T3_Synchronized {
    /*
    W programach wielowątkowych może wystąpic tak zwany `race condition` czyli sytuacja, kiedy dwa watki chca
    uzyc wspoldzielony zasob, ktory jest modifykowany przez oba te watki. W takim przypadku nie mamy zadnej kontroli nad tym,
    w jaki sposob oba watki sie zachowaja i jaki bedzie tego wynik.

    Dzieki slowku `synchronized` mozemy oznaczyc blok kodu, ktory jest wrazliwy na taki wlasnie `race condition`
     */

    public void start() {

        Map<String, Integer> nonSynchronizedResults = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            AccountBalances accountBalances = withoutSynchronized();
            String keyA = "A-" + accountBalances.getA();
            Integer counterA = nonSynchronizedResults.getOrDefault(keyA, 0);
            counterA++;
            nonSynchronizedResults.put(keyA, counterA);

            String keyB = "B-" + accountBalances.getB();
            Integer counterB = nonSynchronizedResults.getOrDefault(keyB, 0);
            counterB++;
            nonSynchronizedResults.put(keyB, counterB);
        }

        System.out.println(nonSynchronizedResults);

        Map<String, Integer> synchronizedResults = new HashMap<>();

        for (int i = 0; i < 100; i++) {
            AccountBalances accountBalances = withSynchronized();
            String keyA = "A-" + accountBalances.getA();
            Integer counterA = synchronizedResults.getOrDefault(keyA, 0);
            counterA++;
            synchronizedResults.put(keyA, counterA);

            String keyB = "B-" + accountBalances.getB();
            Integer counterB = synchronizedResults.getOrDefault(keyB, 0);
            counterB++;
            synchronizedResults.put(keyB, counterB);
        }

        System.out.println(synchronizedResults);

    }

    private AccountBalances withoutSynchronized() {
        Account a1 = new Account("Konto A", 200);
        Account a2 = new Account("Konto B", 500);

        Thread t1 = new Thread(new CashTransfer("Transfer 1", a1, a2, 150));
        Thread t2 = new Thread(new CashTransfer("Transfer 2", a1, a2, 200));
        t1.start();
        t2.start();
        ThreadUtils.sleepThread(100L);
        return new AccountBalances(a1.getBalance(), a2.getBalance());

    }

    private AccountBalances withSynchronized() {
        AccountSynchronized a1 = new AccountSynchronized("Konto A", 200);
        AccountSynchronized a2 = new AccountSynchronized("Konto B", 500);

        Thread t1 = new SynchronizedCashTransfer("Transfer 1", a1, a2, 150);
        Thread t2 = new SynchronizedCashTransfer("Transfer 2", a1, a2, 200);
        t1.start();
        t2.start();
        ThreadUtils.sleepThread(100L);

        return new AccountBalances(a1.getBalance(), a2.getBalance());
    }

    static class AccountBalances {
        private final int a;
        private final int b;

        AccountBalances(int a, int b) {
            this.a = a;
            this.b = b;
        }

        int getA() {
            return this.a;
        }

        int getB() {
            return this.b;
        }
    }

    static class CashTransfer implements Runnable {
        private final Account source;
        private final Account target;

        private final int amount;

        private final String name;

        CashTransfer(String name, Account source, Account target, int amount) {
            this.source = source;
            this.target = target;
            this.amount = amount;
            this.name = name;
        }

        @Override
        public void run() {
            if (source.getBalance() > amount) {
                target.addBalance(amount);
                source.removeBalance(amount);
            }
        }
    }

    static class SynchronizedCashTransfer extends Thread {
        private AccountSynchronized source;
        private AccountSynchronized target;

        private final int amount;

        private final String name;

        SynchronizedCashTransfer(String name, AccountSynchronized source, AccountSynchronized target, int amount) {
            this.source = source;
            this.target = target;
            this.amount = amount;
            this.name = name;
        }

        @Override
        public void run() {
            if (source.getBalance() > amount) {
                target.addBalance(amount);
                source.removeBalance(amount);
            }
        }

    }

}
