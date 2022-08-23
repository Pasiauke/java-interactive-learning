package core.threads.model;

import static core.threads.util.ThreadUtils.sleepThread;

public class SynchronizedResource {
    public static synchronized void process() {
        sleepThread();
    }
}
