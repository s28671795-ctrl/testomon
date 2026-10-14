/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.ServletResponseWrapper
 */
package org.eclipse.jetty.continuation;

import java.util.ArrayList;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import org.eclipse.jetty.continuation.ContinuationFilter;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationThrowable;

@Deprecated
class FauxContinuation
implements ContinuationFilter.FilteredContinuation {
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private static final int __HANDLING = 1;
    private static final int __SUSPENDING = 2;
    private static final int __RESUMING = 3;
    private static final int __COMPLETING = 4;
    private static final int __SUSPENDED = 5;
    private static final int __UNSUSPENDING = 6;
    private static final int __COMPLETE = 7;
    private final ServletRequest _request;
    private ServletResponse _response;
    private int _state = 1;
    private boolean _initial = true;
    private boolean _resumed = false;
    private boolean _timeout = false;
    private boolean _responseWrapped = false;
    private long _timeoutMs = 30000L;
    private ArrayList<ContinuationListener> _listeners;

    FauxContinuation(ServletRequest request) {
        this._request = request;
    }

    public void onComplete() {
        if (this._listeners != null) {
            for (ContinuationListener l : this._listeners) {
                l.onComplete(this);
            }
        }
    }

    public void onTimeout() {
        if (this._listeners != null) {
            for (ContinuationListener l : this._listeners) {
                l.onTimeout(this);
            }
        }
    }

    @Override
    public boolean isResponseWrapped() {
        return this._responseWrapped;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isInitial() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            return this._initial;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isResumed() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            return this._resumed;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isSuspended() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            switch (this._state) {
                case 1: {
                    return false;
                }
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    return true;
                }
            }
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isExpired() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            return this._timeout;
        }
    }

    @Override
    public void setTimeout(long timeoutMs) {
        this._timeoutMs = timeoutMs;
    }

    @Override
    public void suspend(ServletResponse response) {
        this._response = response;
        this._responseWrapped = response instanceof ServletResponseWrapper;
        this.suspend();
    }

    @Override
    public void suspend() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            switch (this._state) {
                case 1: {
                    this._timeout = false;
                    this._resumed = false;
                    this._state = 2;
                    return;
                }
                case 2: 
                case 3: {
                    return;
                }
                case 4: 
                case 5: 
                case 6: {
                    throw new IllegalStateException(this.getStatusString());
                }
            }
            throw new IllegalStateException("" + this._state);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resume() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            switch (this._state) {
                case 1: {
                    this._resumed = true;
                    return;
                }
                case 2: {
                    this._resumed = true;
                    this._state = 3;
                    return;
                }
                case 3: 
                case 4: {
                    return;
                }
                case 5: {
                    this.fauxResume();
                    this._resumed = true;
                    this._state = 6;
                    break;
                }
                case 6: {
                    this._resumed = true;
                    return;
                }
                default: {
                    throw new IllegalStateException(this.getStatusString());
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void complete() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            switch (this._state) {
                case 1: {
                    throw new IllegalStateException(this.getStatusString());
                }
                case 2: {
                    this._state = 4;
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    return;
                }
                case 5: {
                    this._state = 4;
                    this.fauxResume();
                    break;
                }
                case 6: {
                    return;
                }
                default: {
                    throw new IllegalStateException(this.getStatusString());
                }
            }
        }
    }

    @Override
    public boolean enter(ServletResponse response) {
        this._response = response;
        return true;
    }

    @Override
    public ServletResponse getServletResponse() {
        return this._response;
    }

    void handling() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            this._responseWrapped = false;
            switch (this._state) {
                case 1: {
                    throw new IllegalStateException(this.getStatusString());
                }
                case 2: 
                case 3: {
                    throw new IllegalStateException(this.getStatusString());
                }
                case 4: {
                    return;
                }
                case 5: {
                    this.fauxResume();
                    this._state = 1;
                    return;
                }
                case 6: {
                    this._state = 1;
                    return;
                }
            }
            throw new IllegalStateException("" + this._state);
        }
    }

    @Override
    public boolean exit() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            switch (this._state) {
                case 1: {
                    this._state = 7;
                    this.onComplete();
                    return true;
                }
                case 2: {
                    this._initial = false;
                    this._state = 5;
                    this.fauxSuspend();
                    if (this._state == 5 || this._state == 4) {
                        this.onComplete();
                        return true;
                    }
                    this._initial = false;
                    this._state = 1;
                    return false;
                }
                case 3: {
                    this._initial = false;
                    this._state = 1;
                    return false;
                }
                case 4: {
                    this._initial = false;
                    this._state = 7;
                    this.onComplete();
                    return true;
                }
            }
            throw new IllegalStateException(this.getStatusString());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void expire() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            this._timeout = true;
        }
        this.onTimeout();
        fauxContinuation = this;
        synchronized (fauxContinuation) {
            switch (this._state) {
                case 1: {
                    return;
                }
                case 2: {
                    this._timeout = true;
                    this._state = 3;
                    this.fauxResume();
                    return;
                }
                case 3: {
                    return;
                }
                case 4: {
                    return;
                }
                case 5: {
                    this._timeout = true;
                    this._state = 6;
                    break;
                }
                case 6: {
                    this._timeout = true;
                    return;
                }
                default: {
                    throw new IllegalStateException(this.getStatusString());
                }
            }
        }
    }

    private void fauxSuspend() {
        long expireAt = System.currentTimeMillis() + this._timeoutMs;
        long wait = this._timeoutMs;
        while (this._timeoutMs > 0L && wait > 0L) {
            try {
                this.wait(wait);
            }
            catch (InterruptedException e) {
                break;
            }
            wait = expireAt - System.currentTimeMillis();
        }
        if (this._timeoutMs > 0L && wait <= 0L) {
            this.expire();
        }
    }

    private void fauxResume() {
        this._timeoutMs = 0L;
        this.notifyAll();
    }

    public String toString() {
        return this.getStatusString();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    String getStatusString() {
        FauxContinuation fauxContinuation = this;
        synchronized (fauxContinuation) {
            return (this._state == 1 ? "HANDLING" : (this._state == 2 ? "SUSPENDING" : (this._state == 5 ? "SUSPENDED" : (this._state == 3 ? "RESUMING" : (this._state == 6 ? "UNSUSPENDING" : (this._state == 4 ? "COMPLETING" : "???" + this._state)))))) + (this._initial ? ",initial" : "") + (this._resumed ? ",resumed" : "") + (this._timeout ? ",timeout" : "");
        }
    }

    @Override
    public void addContinuationListener(ContinuationListener listener) {
        if (this._listeners == null) {
            this._listeners = new ArrayList();
        }
        this._listeners.add(listener);
    }

    @Override
    public Object getAttribute(String name) {
        return this._request.getAttribute(name);
    }

    @Override
    public void removeAttribute(String name) {
        this._request.removeAttribute(name);
    }

    @Override
    public void setAttribute(String name, Object attribute) {
        this._request.setAttribute(name, attribute);
    }

    @Override
    public void undispatch() {
        if (this.isSuspended()) {
            if (ContinuationFilter.__debug) {
                throw new ContinuationThrowable();
            }
            throw __exception;
        }
        throw new IllegalStateException("!suspended");
    }
}

