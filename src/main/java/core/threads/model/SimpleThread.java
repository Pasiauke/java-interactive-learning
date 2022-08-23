package core.threads.model;

import static core.threads.util.ThreadUtils.sleepThread;

public class SimpleThread extends Thread {
    private final Long sleepDuration;

    public SimpleThread() {
        this.setName("Simple Thread");
        this.sleepDuration = 1000L;
    }

    public SimpleThread(String name) {
        this.setName(name);
        this.sleepDuration = 1000L;
    }

    public SimpleThread(String name, Long sleepDuration) {
        this.setName(name);
        this.sleepDuration = sleepDuration;
    }

    @Override
    public void run() {
        System.out.printf(">>> Start thread [%s]%n", this.getName());
        sleepThread(this.sleepDuration);
        System.out.printf("<<< End thread [%s]%n", this.getName());

    }

}
