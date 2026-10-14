/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.AsyncContext
 *  javax.servlet.AsyncEvent
 *  javax.servlet.AsyncListener
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.WriteListener
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.continuations.Continuation;
import org.apache.cxf.continuations.ContinuationCallback;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;

public class Servlet3ContinuationProvider
implements ContinuationProvider {
    static final boolean IS_31;
    HttpServletRequest req;
    HttpServletResponse resp;
    Message inMessage;
    Servlet3Continuation continuation;

    public Servlet3ContinuationProvider(HttpServletRequest req, HttpServletResponse resp, Message inMessage) {
        this.inMessage = inMessage;
        this.req = req;
        this.resp = resp;
    }

    @Override
    public void complete() {
        if (this.continuation != null) {
            this.continuation.reset();
            this.continuation = null;
        }
    }

    @Override
    public Continuation getContinuation() {
        if (this.inMessage.getExchange().isOneWay()) {
            return null;
        }
        if (this.continuation == null) {
            this.continuation = IS_31 ? new Servlet31Continuation() : new Servlet3Continuation();
        } else {
            this.continuation.startAsyncAgain();
        }
        return this.continuation;
    }

    static {
        boolean is31 = false;
        try {
            ClassLoaderUtils.loadClass("javax.servlet.WriteListener", HttpServletRequest.class);
            is31 = true;
        }
        catch (Throwable t2) {
            is31 = false;
        }
        IS_31 = is31;
    }

    public class Servlet31Continuation
    extends Servlet3Continuation {
        @Override
        protected void updateMessageForSuspend() {
            Message currentMessage = PhaseInterceptorChain.getCurrentMessage();
            if (currentMessage.get(WriteListener.class) != null) {
                this.getOutputStream().setWriteListener(currentMessage.get(WriteListener.class));
                currentMessage.getInterceptorChain().suspend();
            } else {
                Servlet3ContinuationProvider.this.inMessage.getExchange().getInMessage().getInterceptorChain().suspend();
            }
        }

        @Override
        public boolean isReadyForWrite() {
            return this.getOutputStream().isReady();
        }
    }

    public class Servlet3Continuation
    implements Continuation,
    AsyncListener {
        private static final String BLOCK_RESTART = "org.apache.cxf.continuation.block.restart";
        AsyncContext context;
        volatile boolean isNew = true;
        volatile boolean isResumed;
        volatile boolean isPending;
        volatile boolean isComplete;
        volatile boolean isTimeout;
        volatile Object obj;
        private ContinuationCallback callback;
        private boolean blockRestart;

        public Servlet3Continuation() {
            Servlet3ContinuationProvider.this.req.setAttribute("cxf.continuation.message", (Object)Servlet3ContinuationProvider.this.inMessage.getExchange().getInMessage());
            this.callback = Servlet3ContinuationProvider.this.inMessage.getExchange().get(ContinuationCallback.class);
            this.blockRestart = PropertyUtils.isTrue(Servlet3ContinuationProvider.this.inMessage.getContextualProperty(BLOCK_RESTART));
            this.context = Servlet3ContinuationProvider.this.req.startAsync();
            this.context.addListener((AsyncListener)this);
        }

        void startAsyncAgain() {
            if (this.blockRestart) {
                return;
            }
            AsyncContext old = this.context;
            try {
                this.context = Servlet3ContinuationProvider.this.req.startAsync();
                this.context.addListener((AsyncListener)this);
                this.isComplete = false;
            }
            catch (IllegalStateException ex) {
                this.context = old;
            }
        }

        @Override
        public boolean suspend(long timeout2) {
            if (this.isPending && timeout2 != 0L) {
                long currentTimeout = this.context.getTimeout();
                timeout2 = currentTimeout + timeout2;
            } else {
                this.isPending = true;
            }
            this.isNew = false;
            this.isResumed = false;
            this.context.setTimeout(timeout2);
            this.updateMessageForSuspend();
            return true;
        }

        protected void updateMessageForSuspend() {
            Servlet3ContinuationProvider.this.inMessage.getExchange().getInMessage().getInterceptorChain().suspend();
        }

        public void redispatch() {
            if (!this.isComplete) {
                this.context.dispatch();
            }
        }

        @Override
        public void resume() {
            this.isResumed = true;
            this.isPending = false;
            this.redispatch();
        }

        @Override
        public void reset() {
            Exception ex;
            Throwable cause;
            this.isComplete = true;
            try {
                this.context.complete();
            }
            catch (IllegalStateException illegalStateException) {
                // empty catch block
            }
            this.isPending = false;
            this.isResumed = false;
            this.isNew = false;
            this.isTimeout = false;
            this.obj = null;
            if (this.callback != null && (cause = this.isCausedByIO(ex = Servlet3ContinuationProvider.this.inMessage.getExchange().get(Exception.class))) != null && this.isClientDisconnected(cause)) {
                this.callback.onDisconnect();
            }
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

        @Override
        public Object getObject() {
            return this.obj;
        }

        @Override
        public void setObject(Object o) {
            this.obj = o;
        }

        public void onComplete(AsyncEvent event) throws IOException {
            Servlet3ContinuationProvider.this.inMessage.getExchange().getInMessage().remove("cxf.continuation.message");
            if (this.callback != null) {
                Exception ex = Servlet3ContinuationProvider.this.inMessage.getExchange().get(Exception.class);
                if (ex == null) {
                    this.callback.onComplete();
                } else {
                    this.callback.onError(ex);
                }
            }
            this.isResumed = false;
            this.isPending = false;
        }

        public void onError(AsyncEvent event) throws IOException {
            if (this.callback != null) {
                this.callback.onError(event.getThrowable());
            }
        }

        public void onStartAsync(AsyncEvent event) throws IOException {
        }

        public void onTimeout(AsyncEvent event) throws IOException {
            this.resume();
            this.isTimeout = true;
        }

        private Throwable isCausedByIO(Exception ex) {
            Throwable cause;
            for (cause = ex; cause != null && !(cause instanceof IOException); cause = cause.getCause()) {
            }
            return cause;
        }

        private boolean isClientDisconnected(Throwable ex) {
            String exName = (String)Servlet3ContinuationProvider.this.inMessage.getContextualProperty("disconnected.client.exception.class");
            if (exName != null) {
                return exName.equals(IOException.class.getName()) || exName.equals(ex.getClass().getName());
            }
            return false;
        }

        @Override
        public boolean isReadyForWrite() {
            return true;
        }

        protected ServletOutputStream getOutputStream() {
            try {
                return Servlet3ContinuationProvider.this.resp.getOutputStream();
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public boolean isTimeout() {
            return this.isTimeout;
        }
    }
}

