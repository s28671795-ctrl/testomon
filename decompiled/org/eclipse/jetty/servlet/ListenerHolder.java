/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.ServletContext;
import java.util.EventListener;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.thread.AutoLock;

public class ListenerHolder
extends BaseHolder<EventListener> {
    private EventListener _listener;

    public ListenerHolder() {
        this(Source.EMBEDDED);
    }

    public ListenerHolder(Source source2) {
        super(source2);
    }

    public ListenerHolder(Class<? extends EventListener> listenerClass) {
        super(Source.EMBEDDED);
        this.setHeldClass(listenerClass);
    }

    public EventListener getListener() {
        return this._listener;
    }

    public void setListener(EventListener listener) {
        this.setInstance(listener);
    }

    @Override
    public void doStart() throws Exception {
        super.doStart();
        if (!EventListener.class.isAssignableFrom(this.getHeldClass())) {
            String msg = this.getHeldClass() + " is not a java.util.EventListener";
            super.stop();
            throw new IllegalStateException(msg);
        }
        ContextHandler contextHandler = null;
        if (this.getServletHandler() != null) {
            contextHandler = this.getServletHandler().getServletContextHandler();
        }
        if (contextHandler == null && ContextHandler.getCurrentContext() != null) {
            contextHandler = ContextHandler.getCurrentContext().getContextHandler();
        }
        if (contextHandler == null) {
            throw new IllegalStateException("No Context");
        }
        this._listener = (EventListener)this.getInstance();
        if (this._listener == null) {
            this._listener = this.createInstance();
            this._listener = this.wrap(this._listener, WrapFunction.class, WrapFunction::wrapEventListener);
        }
        contextHandler.addEventListener(this._listener);
    }

    @Override
    protected EventListener createInstance() throws Exception {
        try (AutoLock l = this.lock();){
            ServletContext ctx;
            EventListener listener = (EventListener)super.createInstance();
            if (listener == null && (ctx = this.getServletContext()) != null) {
                listener = ctx.createListener(this.getHeldClass());
            }
            EventListener eventListener = listener;
            return eventListener;
        }
    }

    @Override
    public void doStop() throws Exception {
        super.doStop();
        if (this._listener != null) {
            try {
                ContextHandler contextHandler = ContextHandler.getCurrentContext().getContextHandler();
                if (contextHandler != null) {
                    contextHandler.removeEventListener(this._listener);
                }
                this.getServletHandler().destroyListener(this.unwrap(this._listener));
            }
            finally {
                this._listener = null;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("%s@%x{src=%s}", this.getClassName(), this.hashCode(), this.getSource());
    }

    public static interface WrapFunction {
        public EventListener wrapEventListener(EventListener var1);
    }

    public static class Wrapper
    implements EventListener,
    BaseHolder.Wrapped<EventListener> {
        final EventListener _listener;

        public Wrapper(EventListener listener) {
            this._listener = listener;
        }

        @Override
        public EventListener getWrapped() {
            return this._listener;
        }

        public String toString() {
            return String.format("%s:%s", this.getClass().getSimpleName(), this._listener.toString());
        }
    }
}

