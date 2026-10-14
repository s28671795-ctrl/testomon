/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.concurrent;

import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.ReentrantMutex;
import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.Sync;

public class CondVar {
    protected boolean debug_;
    protected final Sync mutex_;
    protected final ReentrantMutex remutex_;

    private int releaseMutex() {
        int count = 1;
        if (this.remutex_ != null) {
            count = this.remutex_.releaseAll();
        } else {
            this.mutex_.release();
        }
        return count;
    }

    private void acquireMutex(int count) throws InterruptedException {
        if (this.remutex_ != null) {
            this.remutex_.acquireAll(count);
        } else {
            this.mutex_.acquire();
        }
    }

    public CondVar(Sync mutex, boolean debug) {
        this.debug_ = debug;
        this.mutex_ = mutex;
        this.remutex_ = mutex instanceof ReentrantMutex ? (ReentrantMutex)mutex : null;
    }

    public CondVar(Sync mutex) {
        this(mutex, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void await() throws InterruptedException {
        int count = 0;
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        try {
            if (this.debug_) {
                ORBUtility.dprintTrace(this, "await enter");
            }
            CondVar condVar = this;
            synchronized (condVar) {
                count = this.releaseMutex();
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {
                    this.notify();
                    throw ex;
                }
            }
        }
        finally {
            boolean interrupted = false;
            while (true) {
                try {
                    this.acquireMutex(count);
                }
                catch (InterruptedException ex) {
                    interrupted = true;
                    continue;
                }
                break;
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            if (this.debug_) {
                ORBUtility.dprintTrace(this, "await exit");
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean timedwait(long msecs) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        boolean success = false;
        int count = 0;
        try {
            if (this.debug_) {
                ORBUtility.dprintTrace(this, "timedwait enter");
            }
            CondVar condVar = this;
            synchronized (condVar) {
                count = this.releaseMutex();
                try {
                    if (msecs > 0L) {
                        long start2 = System.currentTimeMillis();
                        this.wait(msecs);
                        success = System.currentTimeMillis() - start2 <= msecs;
                    }
                }
                catch (InterruptedException ex) {
                    this.notify();
                    throw ex;
                }
            }
        }
        finally {
            boolean interrupted = false;
            while (true) {
                try {
                    this.acquireMutex(count);
                }
                catch (InterruptedException ex) {
                    interrupted = true;
                    continue;
                }
                break;
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            if (this.debug_) {
                ORBUtility.dprintTrace(this, "timedwait exit");
            }
        }
        return success;
    }

    public synchronized void signal() {
        this.notify();
    }

    public synchronized void broadcast() {
        this.notifyAll();
    }
}

