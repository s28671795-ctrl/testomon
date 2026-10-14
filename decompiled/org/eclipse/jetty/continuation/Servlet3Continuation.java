/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.AsyncContext
 *  javax.servlet.AsyncEvent
 *  javax.servlet.AsyncListener
 *  javax.servlet.DispatcherType
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletResponse
 *  javax.servlet.ServletResponseWrapper
 */
package org.eclipse.jetty.continuation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.DispatcherType;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationFilter;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationThrowable;

@Deprecated
public class Servlet3Continuation
implements Continuation,
AsyncListener {
    private static final ContinuationThrowable __exception = new ContinuationThrowable();
    private final ServletRequest _request;
    private ServletResponse _response;
    private AsyncContext _context;
    private final List<ContinuationListener> _listeners = new ArrayList<ContinuationListener>();
    private volatile boolean _initial = true;
    private volatile boolean _resumed = false;
    private volatile boolean _expired = false;
    private volatile boolean _responseWrapped = false;
    private long _timeoutMs = -1L;

    public Servlet3Continuation(ServletRequest request) {
        this._request = request;
    }

    @Override
    public void addContinuationListener(ContinuationListener listener) {
        this._listeners.add(listener);
    }

    @Override
    public void complete() {
        AsyncContext context = this._context;
        if (context == null) {
            throw new IllegalStateException();
        }
        this._context.complete();
    }

    @Override
    public ServletResponse getServletResponse() {
        return this._response;
    }

    @Override
    public boolean isExpired() {
        return this._expired;
    }

    @Override
    public boolean isInitial() {
        return this._initial && this._request.getDispatcherType() != DispatcherType.ASYNC;
    }

    @Override
    public boolean isResumed() {
        return this._resumed;
    }

    @Override
    public boolean isSuspended() {
        if (this._request.isAsyncStarted()) {
            return true;
        }
        try {
            return this._request.getAsyncContext() != null;
        }
        catch (IllegalStateException illegalStateException) {
            return false;
        }
    }

    public void keepWrappers() {
        this._responseWrapped = true;
    }

    @Override
    public void resume() {
        AsyncContext context = this._context;
        if (context == null) {
            throw new IllegalStateException();
        }
        this._resumed = true;
        this._context.dispatch();
    }

    @Override
    public void setTimeout(long timeoutMs) {
        this._timeoutMs = timeoutMs;
        if (this._context != null) {
            this._context.setTimeout(timeoutMs);
        }
    }

    @Override
    public void suspend(ServletResponse response) {
        this._response = response;
        this._responseWrapped = response instanceof ServletResponseWrapper;
        this._resumed = false;
        this._expired = false;
        this._context = this._request.startAsync();
        this._context.setTimeout(this._timeoutMs);
        this._context.addListener((AsyncListener)this);
    }

    @Override
    public void suspend() {
        this._resumed = false;
        this._expired = false;
        this._context = this._request.startAsync();
        this._context.setTimeout(this._timeoutMs);
        this._context.addListener((AsyncListener)this);
    }

    @Override
    public boolean isResponseWrapped() {
        return this._responseWrapped;
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
            this._initial = false;
            if (ContinuationFilter.__debug) {
                throw new ContinuationThrowable();
            }
            throw __exception;
        }
        throw new IllegalStateException("!suspended");
    }

    public void onComplete(AsyncEvent event) throws IOException {
        for (ContinuationListener listener : this._listeners) {
            listener.onComplete(this);
        }
    }

    public void onError(AsyncEvent event) throws IOException {
    }

    public void onStartAsync(AsyncEvent event) throws IOException {
    }

    public void onTimeout(AsyncEvent event) throws IOException {
        this._expired = true;
        for (ContinuationListener listener : this._listeners) {
            listener.onTimeout(this);
        }
        if (event.getSuppliedRequest().isAsyncStarted()) {
            event.getAsyncContext().dispatch();
        }
    }
}

