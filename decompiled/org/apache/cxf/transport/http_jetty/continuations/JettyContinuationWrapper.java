/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http_jetty.continuations;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.continuations.ContinuationCallback;
import org.apache.cxf.message.Message;
import org.eclipse.jetty.continuation.Continuation;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationSupport;

public class JettyContinuationWrapper
implements org.apache.cxf.continuations.Continuation,
ContinuationListener {
    volatile boolean isNew;
    volatile boolean isResumed;
    volatile boolean isPending;
    volatile boolean isTimeout;
    volatile long pendingTimeout;
    volatile Object obj;
    private Message message;
    private Continuation continuation;
    private ContinuationCallback callback;

    public JettyContinuationWrapper(HttpServletRequest request, HttpServletResponse resp, Message m4) {
        this.continuation = ContinuationSupport.getContinuation((ServletRequest)request);
        this.message = m4;
        boolean bl = this.isNew = request.getAttribute("cxf.continuation.message") == null;
        if (this.isNew) {
            request.setAttribute("cxf.continuation.message", (Object)this.message.getExchange().getInMessage());
            this.continuation.addContinuationListener(this);
            this.callback = this.message.getExchange().get(ContinuationCallback.class);
        }
    }

    @Override
    public Object getObject() {
        return this.obj;
    }

    @Override
    public void setObject(Object userObject) {
        this.obj = userObject;
    }

    @Override
    public void resume() {
        this.isResumed = true;
        this.isPending = false;
        this.continuation.resume();
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Override
    public boolean isPending() {
        return this.isPending;
    }

    @Override
    public boolean isResumed() {
        return this.isResumed;
    }

    public boolean isExpired() {
        return this.continuation.isExpired();
    }

    @Override
    public void reset() {
        try {
            this.continuation.complete();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        this.obj = null;
        this.pendingTimeout = 0L;
        this.isTimeout = false;
    }

    @Override
    public boolean suspend(long timeout2) {
        this.pendingTimeout = this.isPending && timeout2 != 0L ? (this.pendingTimeout += this.pendingTimeout + timeout2) : timeout2;
        this.isNew = false;
        this.message.getExchange().getInMessage().getInterceptorChain().suspend();
        this.continuation.setTimeout(this.pendingTimeout);
        if (!this.isPending) {
            this.continuation.suspend();
            this.isPending = true;
        }
        return true;
    }

    protected Message getMessage() {
        Message m4 = this.message;
        if (m4 != null && m4.getExchange().getInMessage() != null) {
            m4 = m4.getExchange().getInMessage();
        }
        return m4;
    }

    @Override
    public void onComplete(Continuation cont) {
        this.getMessage().remove("cxf.continuation.message");
        this.isPending = false;
        this.pendingTimeout = 0L;
        this.isResumed = false;
        if (this.callback != null) {
            this.callback.onComplete();
        }
    }

    @Override
    public void onTimeout(Continuation cont) {
        this.isPending = false;
        this.pendingTimeout = 0L;
        this.isResumed = true;
        this.isTimeout = true;
    }

    @Override
    public boolean isReadyForWrite() {
        return true;
    }

    @Override
    public boolean isTimeout() {
        return this.isTimeout;
    }
}

