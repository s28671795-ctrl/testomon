/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.util.Collection;
import java.util.Collections;
import org.eclipse.jetty.webapp.ClassMatcher;
import org.eclipse.jetty.webapp.WebAppContext;

public interface Configuration {
    public static final String ATTR = "org.eclipse.jetty.webapp.configuration";

    default public boolean isAvailable() {
        return true;
    }

    default public Class<? extends Configuration> replaces() {
        return null;
    }

    default public Collection<String> getDependencies() {
        return Collections.emptyList();
    }

    default public Collection<String> getDependents() {
        return Collections.emptyList();
    }

    default public ClassMatcher getSystemClasses() {
        return new ClassMatcher();
    }

    default public ClassMatcher getServerClasses() {
        return new ClassMatcher();
    }

    public void preConfigure(WebAppContext var1) throws Exception;

    public void configure(WebAppContext var1) throws Exception;

    public void postConfigure(WebAppContext var1) throws Exception;

    public void deconfigure(WebAppContext var1) throws Exception;

    public void destroy(WebAppContext var1) throws Exception;

    public boolean isEnabledByDefault();

    public boolean abort(WebAppContext var1);

    public static class Wrapper
    implements Configuration {
        private Configuration delegate;

        public Wrapper(Configuration delegate) {
            this.delegate = delegate;
        }

        public Configuration getWrapped() {
            return this.delegate;
        }

        @Override
        public void preConfigure(WebAppContext context) throws Exception {
            this.delegate.preConfigure(context);
        }

        @Override
        public void configure(WebAppContext context) throws Exception {
            this.delegate.configure(context);
        }

        @Override
        public void postConfigure(WebAppContext context) throws Exception {
            this.delegate.postConfigure(context);
        }

        @Override
        public void deconfigure(WebAppContext context) throws Exception {
            this.delegate.deconfigure(context);
        }

        @Override
        public void destroy(WebAppContext context) throws Exception {
            this.delegate.destroy(context);
        }

        @Override
        public boolean isEnabledByDefault() {
            return this.delegate.isEnabledByDefault();
        }

        @Override
        public boolean abort(WebAppContext context) {
            return this.delegate.abort(context);
        }
    }

    public static interface WrapperFunction {
        public Configuration wrapConfiguration(Configuration var1);
    }
}

