/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.UnavailableException;
import java.io.IOException;
import java.util.function.BiFunction;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;
import org.eclipse.jetty.util.thread.AutoLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseHolder<T>
extends AbstractLifeCycle
implements Dumpable {
    private static final Logger LOG = LoggerFactory.getLogger(BaseHolder.class);
    private final AutoLock _lock = new AutoLock();
    private final Source _source;
    private Class<? extends T> _class;
    private String _className;
    private T _instance;
    private ServletHandler _servletHandler;

    protected BaseHolder(Source source2) {
        this._source = source2;
    }

    public Source getSource() {
        return this._source;
    }

    AutoLock lock() {
        return this._lock.lock();
    }

    boolean lockIsHeldByCurrentThread() {
        return this._lock.isHeldByCurrentThread();
    }

    public void initialize() throws Exception {
        if (!this.isStarted()) {
            throw new IllegalStateException("Not started: " + this);
        }
    }

    @Override
    public void doStart() throws Exception {
        if (this._class == null && (this._className == null || this._className.equals(""))) {
            throw new UnavailableException("No class in holder " + this.toString());
        }
        if (this._class == null) {
            try {
                this._class = Loader.loadClass(this._className);
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Holding {} from {}", (Object)this._class, (Object)this._class.getClassLoader());
                }
            }
            catch (Exception e) {
                LOG.warn("Unable to load class {}", (Object)this._className, (Object)e);
                throw new UnavailableException("Class loading error for holder " + this.toString());
            }
        }
    }

    @Override
    public void doStop() throws Exception {
        if (this._instance == null) {
            this._class = null;
        }
    }

    @ManagedAttribute(value="Class Name", readonly=true)
    public String getClassName() {
        return this._className;
    }

    public Class<? extends T> getHeldClass() {
        return this._class;
    }

    public ServletHandler getServletHandler() {
        return this._servletHandler;
    }

    public void setServletHandler(ServletHandler servletHandler) {
        this._servletHandler = servletHandler;
    }

    public void setClassName(String className) {
        this._className = className;
        this._class = null;
    }

    public void setHeldClass(Class<? extends T> held) {
        this._class = held;
        if (held != null) {
            this._className = held.getName();
        }
    }

    protected void illegalStateIfContextStarted() {
        ServletContext context;
        if (this._servletHandler != null && (context = this._servletHandler.getServletContext()) instanceof ContextHandler.Context && ((ContextHandler.Context)context).getContextHandler().isStarted()) {
            throw new IllegalStateException("Started");
        }
    }

    protected void setInstance(T instance) {
        try (AutoLock l = this.lock();){
            this._instance = instance;
            if (instance == null) {
                this.setHeldClass(null);
            } else {
                this.setHeldClass(instance.getClass());
            }
        }
    }

    protected T getInstance() {
        try (AutoLock l = this.lock();){
            T t2 = this._instance;
            return t2;
        }
    }

    protected T createInstance() throws Exception {
        try (AutoLock l = this.lock();){
            ServletContext ctx = this.getServletContext();
            if (ctx == null) {
                T t2 = this.getHeldClass().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                return t2;
            }
            if (ServletContextHandler.Context.class.isAssignableFrom(ctx.getClass())) {
                Object t3 = ((ServletContextHandler.Context)ctx).createInstance(this);
                return t3;
            }
            T t4 = null;
            return t4;
        }
    }

    public ServletContext getServletContext() {
        ContextHandler contextHandler;
        ServletContext scontext = null;
        if (this.getServletHandler() != null) {
            scontext = this.getServletHandler().getServletContext();
        }
        if (scontext != null) {
            return scontext;
        }
        ContextHandler.Context ctx = ContextHandler.getCurrentContext();
        if (ctx != null && (contextHandler = ctx.getContextHandler()) != null) {
            return contextHandler.getServletContext();
        }
        return null;
    }

    public boolean isInstance() {
        try (AutoLock l = this.lock();){
            boolean bl = this._instance != null;
            return bl;
        }
    }

    protected <W> T wrap(T component, Class<W> wrapperFunctionType, BiFunction<W, T, T> function) {
        T ret = component;
        ServletContextHandler contextHandler = this.getServletHandler().getServletContextHandler();
        if (contextHandler == null) {
            ContextHandler.Context context = ContextHandler.getCurrentContext();
            contextHandler = (ServletContextHandler)(context == null ? null : context.getContextHandler());
        }
        if (contextHandler != null) {
            for (W wrapperFunction : contextHandler.getBeans(wrapperFunctionType)) {
                ret = function.apply(wrapperFunction, ret);
            }
        }
        return ret;
    }

    protected T unwrap(T component) {
        Object ret = component;
        while (ret instanceof Wrapped) {
            ret = ((Wrapped)ret).getWrapped();
        }
        return ret;
    }

    @Override
    public void dump(Appendable out, String indent) throws IOException {
        Dumpable.dumpObject(out, this);
    }

    @Override
    public String dump() {
        return Dumpable.dump(this);
    }

    static interface Wrapped<C> {
        public C getWrapped();
    }
}

