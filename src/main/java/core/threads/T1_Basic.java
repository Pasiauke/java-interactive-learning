package core.threads;

import core.threads.model.SimpleThread;

import static core.threads.util.ThreadUtils.sleepThread;

public class T1_Basic {

    public void start() {

        // Wątek w Javie można stworzyc na dwa sposoby:

        // 1. Stworznie klasy dziedziczącej po klasie Thread oraz overridującej metodę run(). Następnie wywołanie metody start();
        Thread threadFromClass = new SimpleThread("Simple Thread", 1000L);
        threadFromClass.start();

        // 2. Implementacja interface'u Runnable i wywołąnie metody run(); - Albo użycie wywołania Lambda
        Runnable threadFromInterface = () -> {

            System.out.printf(">>> Start thread [%s]%n", "Simple Runnable Thread");
            sleepThread(2000L);
            System.out.printf("<<< End thread [%s]%n", "Simple Runnable Thread");
        };
        threadFromInterface.run();

    }
}
