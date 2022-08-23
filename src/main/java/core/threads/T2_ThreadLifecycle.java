package core.threads;

import core.threads.model.SimpleThread;
import core.threads.model.SynchronizedResource;

import static core.threads.util.ThreadUtils.sleepThread;

public class T2_ThreadLifecycle {

    public void start() {
        /*
        Każdy thread posiada swój lifecycle wyrazany statusem:
        1. NEW              - nowo utworzony thread, ktory czeka na uruchomienie
        2. RUNNABLE         - wątek, który jest uruchomiony albo gotowy na uruchomienie ale czeka na alokacje
        3. BLOCKED          - watek ktory czeka na uzyskanie monitor lock'a aby wejsc do synchronized block/method - blocked zwiazany jest
                              z dostępem do wspóldzielonego zasobu, podczas, gdy WAITING - zwiazany jest z czekaniem na inny wątek
        4. WAITING          - watek czeka az jakis inny watek skonczy konkretną akcje (bez limitu czasu)
        5. TIMED_WAITING    - watek czeka az jakis inny watek skonczy konkretną akcje (z limitem czasu)
        6. TERMINATED       - watek zakonczyl prace

         */

        // newThread();
        // runnableThread();
        // threadBlocked();
        // threadWaiting();
        // threadTimedWaiting();
        // terminatedThread();


    }

    private void newThread() {
        Thread customThread = new SimpleThread();
        System.out.println(customThread.getState());
    }

    private void runnableThread() {
        Thread customThread = new SimpleThread();
        customThread.start();
        System.out.println(customThread.getState());
    }

    private void threadBlocked() {
        Thread t1 = new Thread(new CustomResourceThread("T1"));
        Thread t2 = new Thread(new CustomResourceThread("T2"));
        t1.start();
        t2.start();

        sleepThread(4000L);
        System.out.println(t2.getState());
    }

    private void threadWaiting() {

        Thread customThread = new CustomThread();
        customThread.start();
        sleepThread(500L);
        System.out.println(customThread.getState());

    }

    private void threadTimedWaiting() {

        Thread t1 = new SimpleThread("T1", 2000L);
        Thread t2 = new Thread(t1);
        t2.start();

        sleepThread(1000L);
        System.out.println(t2.getState());
    }

    private void terminatedThread() {
        Thread t1 = new SimpleThread("T1", 100L);
        t1.start();
        sleepThread(1000L);

        System.out.println(t1.getState());
    }

    static class CustomThread extends Thread {

        @Override
        public void run() {
            Thread innerThread = new Thread(new InnerThread());
            innerThread.start();
            try {
                innerThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class InnerThread implements Runnable {
        @Override
        public void run() {
            sleepThread(1000L);
        }
    }


    static class CustomResourceThread implements Runnable {
        private final String name;

        CustomResourceThread(String threadName) {
            this.name = threadName;
        }

        @Override
        public void run() {
            System.out.println(">>> Start computing resource on thread " + this.name);
            SynchronizedResource.process();
            System.out.println("<<< End computing resource on thread " + this.name);
        }
    }
}
