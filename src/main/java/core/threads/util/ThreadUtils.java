package core.threads.util;

public final class ThreadUtils {
    private ThreadUtils() {

    }

    public static void sleepThread(final long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleepThread() {
        while(true){}
    }
}
