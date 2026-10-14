/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil.concurrent;

import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.impl.orbutil.concurrent.Sync;
import org.omg.CORBA.INTERNAL;

public class ReentrantMutex
implements Sync {
    protected Thread holder_ = null;
    protected int counter_ = 0;
    protected boolean debug = false;

    public ReentrantMutex() {
        this(false);
    }

    public ReentrantMutex(boolean debug) {
        this.debug = debug;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void acquire() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        ReentrantMutex reentrantMutex = this;
        synchronized (reentrantMutex) {
            try {
                Thread thr;
                if (this.debug) {
                    ORBUtility.dprintTrace(this, "acquire enter: holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
                }
                if (this.holder_ != (thr = Thread.currentThread())) {
                    try {
                        while (this.counter_ > 0) {
                            this.wait();
                        }
                        if (this.counter_ != 0) {
                            throw new INTERNAL("counter not 0 when first acquiring mutex");
                        }
                        this.holder_ = thr;
                    }
                    catch (InterruptedException ex) {
                        this.notify();
                        throw ex;
                    }
                }
                ++this.counter_;
            }
            finally {
                if (this.debug) {
                    ORBUtility.dprintTrace(this, "acquire exit: holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void acquireAll(int count) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        ReentrantMutex reentrantMutex = this;
        synchronized (reentrantMutex) {
            try {
                Thread thr;
                if (this.debug) {
                    ORBUtility.dprintTrace(this, "acquireAll enter: count=" + count + " holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
                }
                if (this.holder_ == (thr = Thread.currentThread())) {
                    throw new INTERNAL("Cannot acquireAll while holding the mutex");
                }
                try {
                    while (this.counter_ > 0) {
                        this.wait();
                    }
                    if (this.counter_ != 0) {
                        throw new INTERNAL("counter not 0 when first acquiring mutex");
                    }
                    this.holder_ = thr;
                }
                catch (InterruptedException ex) {
                    this.notify();
                    throw ex;
                }
                this.counter_ = count;
            }
            finally {
                if (this.debug) {
                    ORBUtility.dprintTrace(this, "acquireAll exit: count=" + count + " holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
                }
            }
        }
    }

    @Override
    public synchronized void release() {
        try {
            Thread thr;
            if (this.debug) {
                ORBUtility.dprintTrace(this, "release enter:  holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
            }
            if ((thr = Thread.currentThread()) != this.holder_) {
                throw new INTERNAL("Attempt to release Mutex by thread not holding the Mutex");
            }
            --this.counter_;
            if (this.counter_ == 0) {
                this.holder_ = null;
                this.notify();
            }
        }
        finally {
            if (this.debug) {
                ORBUtility.dprintTrace(this, "release exit:  holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    synchronized int releaseAll() {
        try {
            Thread thr;
            if (this.debug) {
                ORBUtility.dprintTrace(this, "releaseAll enter:  holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
            }
            if ((thr = Thread.currentThread()) != this.holder_) {
                throw new INTERNAL("Attempt to releaseAll Mutex by thread not holding the Mutex");
            }
            int result = this.counter_;
            this.counter_ = 0;
            this.holder_ = null;
            this.notify();
            int n = result;
            return n;
        }
        finally {
            if (this.debug) {
                ORBUtility.dprintTrace(this, "releaseAll exit:  holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean attempt(long msecs) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        ReentrantMutex reentrantMutex = this;
        synchronized (reentrantMutex) {
            try {
                if (this.debug) {
                    ORBUtility.dprintTrace(this, "attempt enter: msecs=" + msecs + " holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
                }
                Thread thr = Thread.currentThread();
                if (this.counter_ == 0) {
                    this.holder_ = thr;
                    this.counter_ = 1;
                    boolean bl = true;
                    return bl;
                }
                if (msecs <= 0L) {
                    boolean bl = false;
                    return bl;
                }
                long waitTime = msecs;
                long start2 = System.currentTimeMillis();
                do {
                    boolean bl;
                    try {
                        this.wait(waitTime);
                        if (this.counter_ != 0) continue;
                        this.holder_ = thr;
                        this.counter_ = 1;
                        bl = true;
                    }
                    catch (InterruptedException ex) {
                        this.notify();
                        throw ex;
                    }
                    return bl;
                } while ((waitTime = msecs - (System.currentTimeMillis() - start2)) > 0L);
                boolean bl = false;
                return bl;
            }
            finally {
                if (this.debug) {
                    ORBUtility.dprintTrace(this, "attempt exit:  holder_=" + ORBUtility.getThreadName(this.holder_) + " counter_=" + this.counter_);
                }
            }
        }
    }
}

