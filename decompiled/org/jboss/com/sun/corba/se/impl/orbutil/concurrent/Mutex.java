/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.concurrent;

import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.Sync;

public class Mutex
implements Sync {
    protected boolean inuse_ = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void acquire() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Mutex mutex = this;
        synchronized (mutex) {
            try {
                while (this.inuse_) {
                    this.wait();
                }
                this.inuse_ = true;
            }
            catch (InterruptedException ex) {
                this.notify();
                throw ex;
            }
        }
    }

    @Override
    public synchronized void release() {
        this.inuse_ = false;
        this.notify();
    }

    @Override
    public boolean attempt(long msecs) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Mutex mutex = this;
        synchronized (mutex) {
            if (!this.inuse_) {
                this.inuse_ = true;
                return true;
            }
            if (msecs <= 0L) {
                return false;
            }
            long waitTime = msecs;
            long start2 = System.currentTimeMillis();
            try {
                do {
                    this.wait(waitTime);
                    if (this.inuse_) continue;
                    this.inuse_ = true;
                    return true;
                } while ((waitTime = msecs - (System.currentTimeMillis() - start2)) > 0L);
                return false;
            }
            catch (InterruptedException ex) {
                this.notify();
                throw ex;
            }
        }
    }
}

