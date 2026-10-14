/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.jcajce.provider.drbg;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

class EntropyDaemon
implements Runnable {
    private static final Logger LOG = Logger.getLogger(EntropyDaemon.class.getName());
    private final LinkedList<Runnable> tasks = new LinkedList();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addTask(Runnable runnable2) {
        LinkedList<Runnable> linkedList = this.tasks;
        synchronized (linkedList) {
            this.tasks.add(runnable2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Runnable runnable2;
            LinkedList<Runnable> linkedList = this.tasks;
            synchronized (linkedList) {
                runnable2 = this.tasks.poll();
            }
            if (runnable2 != null) {
                try {
                    runnable2.run();
                }
                catch (Throwable throwable) {}
                continue;
            }
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
            }
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("entropy thread interrupted - exiting");
        }
    }
}

