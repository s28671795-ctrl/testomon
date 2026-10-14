/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io;

class ThreadMonitor
implements Runnable {
    private final Thread thread;
    private final long timeout;

    public static Thread start(long timeout2) {
        return ThreadMonitor.start(Thread.currentThread(), timeout2);
    }

    public static Thread start(Thread thread2, long timeout2) {
        Thread monitor = null;
        if (timeout2 > 0L) {
            ThreadMonitor timout = new ThreadMonitor(thread2, timeout2);
            monitor = new Thread(timout, ThreadMonitor.class.getSimpleName());
            monitor.setDaemon(true);
            monitor.start();
        }
        return monitor;
    }

    public static void stop(Thread thread2) {
        if (thread2 != null) {
            thread2.interrupt();
        }
    }

    private ThreadMonitor(Thread thread2, long timeout2) {
        this.thread = thread2;
        this.timeout = timeout2;
    }

    @Override
    public void run() {
        try {
            ThreadMonitor.sleep(this.timeout);
            this.thread.interrupt();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private static void sleep(long ms) throws InterruptedException {
        long finishAt = System.currentTimeMillis() + ms;
        long remaining = ms;
        do {
            Thread.sleep(remaining);
        } while ((remaining = finishAt - System.currentTimeMillis()) > 0L);
    }
}

