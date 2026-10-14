/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.servlet;

import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.BaseHolder;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletContainerInitializerHolder
extends BaseHolder<ServletContainerInitializer> {
    private static final Logger LOG = LoggerFactory.getLogger(ServletContainerInitializerHolder.class);
    protected Set<String> _startupClassNames = new HashSet<String>();
    protected Set<Class<?>> _startupClasses = new HashSet();
    public static final Pattern __pattern = Pattern.compile("ContainerInitializer\\{([^,]*),interested=(\\[[^\\]]*\\])(,applicable=(\\[[^\\]]*\\]))?(,annotated=(\\[[^\\]]*\\]))?\\}");

    protected ServletContainerInitializerHolder(Source source2) {
        super(source2);
    }

    public ServletContainerInitializerHolder() {
        this(Source.EMBEDDED);
    }

    public ServletContainerInitializerHolder(Class<? extends ServletContainerInitializer> sciClass) {
        super(Source.EMBEDDED);
        this.setHeldClass(sciClass);
    }

    public ServletContainerInitializerHolder(Class<? extends ServletContainerInitializer> sciClass, Class<?> ... startupClasses) {
        super(Source.EMBEDDED);
        this.setHeldClass(sciClass);
        this._startupClasses.addAll(Arrays.asList(startupClasses));
    }

    public ServletContainerInitializerHolder(ServletContainerInitializer sci, Class<?> ... startupClasses) {
        this(Source.EMBEDDED, sci, startupClasses);
    }

    public ServletContainerInitializerHolder(Source source2, ServletContainerInitializer sci, Class<?> ... startupClasses) {
        super(source2);
        this.setInstance(sci);
        if (startupClasses != null) {
            this._startupClasses.addAll(Arrays.asList(startupClasses));
        }
    }

    public void addStartupClasses(String ... names) {
        Collections.addAll(this._startupClassNames, names);
    }

    public void addStartupClasses(Class<?> ... clazzes) {
        Collections.addAll(this._startupClasses, clazzes);
    }

    protected Set<Class<?>> resolveStartupClasses() throws Exception {
        HashSet classes = new HashSet();
        for (String name : this._startupClassNames) {
            classes.add(Loader.loadClass(name));
        }
        return classes;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doStart() throws Exception {
        HashSet classes = new HashSet(this._startupClasses);
        super.doStart();
        classes.addAll(this.resolveStartupClasses());
        ContextHandler.Context ctx = null;
        if (this.getServletHandler() != null) {
            ctx = this.getServletHandler().getServletContextHandler().getServletContext();
        }
        if (ctx == null && ContextHandler.getCurrentContext() != null) {
            ctx = ContextHandler.getCurrentContext();
        }
        if (ctx == null) {
            throw new IllegalStateException("No Context");
        }
        ServletContainerInitializer initializer = (ServletContainerInitializer)this.getInstance();
        if (initializer == null) {
            initializer = (ServletContainerInitializer)this.createInstance();
            initializer = this.wrap(initializer, WrapFunction.class, WrapFunction::wrapServletContainerInitializer);
        }
        try {
            ctx.setExtendedListenerTypes(true);
            if (LOG.isDebugEnabled()) {
                long start2 = NanoTime.now();
                initializer.onStartup(classes, ctx);
                LOG.debug("ServletContainerInitializer {} called in {}ms", (Object)this.getClassName(), (Object)NanoTime.millisSince(start2));
            } else {
                initializer.onStartup(classes, ctx);
            }
        }
        finally {
            ctx.setExtendedListenerTypes(false);
        }
    }

    public static ServletContainerInitializerHolder fromString(ClassLoader loader, String string) {
        Matcher m4 = __pattern.matcher(string);
        if (!m4.matches()) {
            throw new IllegalArgumentException(string);
        }
        try {
            String[] classnames;
            String sciClassname = m4.group(1);
            ServletContainerInitializer sci = (ServletContainerInitializer)loader.loadClass(sciClassname).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            ServletContainerInitializerHolder holder = new ServletContainerInitializerHolder(new Source(Source.Origin.ANNOTATION, sciClassname));
            holder.setInstance(sci);
            HashSet classes = new HashSet();
            for (String name : classnames = StringUtil.arrayFromString(m4.group(2))) {
                classes.add(loader.loadClass(name));
            }
            for (String name : classnames = StringUtil.arrayFromString(m4.group(4))) {
                classes.add(loader.loadClass(name));
            }
            for (String name : classnames = StringUtil.arrayFromString(m4.group(6))) {
                classes.add(loader.loadClass(name));
            }
            holder.addStartupClasses(classes.toArray(new Class[0]));
            return holder;
        }
        catch (Exception e) {
            throw new IllegalArgumentException(string, e);
        }
    }

    @Override
    public String toString() {
        HashSet<String> interested = new HashSet<String>(this._startupClassNames);
        this._startupClasses.forEach(c -> interested.add(c.getName()));
        return String.format("ContainerInitializer{%s,interested=%s,applicable=%s,annotated=%s}", this.getClassName(), interested, Collections.emptySet(), Collections.emptySet());
    }

    public static interface WrapFunction {
        public ServletContainerInitializer wrapServletContainerInitializer(ServletContainerInitializer var1);
    }

    public static class Wrapper
    implements ServletContainerInitializer,
    BaseHolder.Wrapped<ServletContainerInitializer> {
        private final ServletContainerInitializer _wrappedSCI;

        public Wrapper(ServletContainerInitializer sci) {
            this._wrappedSCI = Objects.requireNonNull(sci, "ServletContainerInitializer cannot be null");
        }

        @Override
        public ServletContainerInitializer getWrapped() {
            return this._wrappedSCI;
        }

        @Override
        public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
            this._wrappedSCI.onStartup(c, ctx);
        }

        public String toString() {
            return String.format("%s:%s", this.getClass().getSimpleName(), this._wrappedSCI.toString());
        }
    }
}

