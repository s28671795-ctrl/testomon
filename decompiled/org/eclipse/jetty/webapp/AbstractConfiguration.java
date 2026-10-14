/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.jetty.webapp.ClassMatcher;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;

public class AbstractConfiguration
implements Configuration {
    private final boolean _enabledByDefault;
    private final List<String> _after = new ArrayList<String>();
    private final List<String> _beforeThis = new ArrayList<String>();
    private final ClassMatcher _system = new ClassMatcher();
    private final ClassMatcher _server = new ClassMatcher();

    protected AbstractConfiguration() {
        this(true);
    }

    protected AbstractConfiguration(boolean enabledByDefault) {
        this._enabledByDefault = enabledByDefault;
    }

    protected void addDependencies(String ... classes) {
        for (String c : classes) {
            this._beforeThis.add(c);
        }
    }

    protected void addDependencies(Class<? extends Configuration> ... classes) {
        this.addDependencies(Arrays.asList(classes).stream().map(Class::getName).collect(Collectors.toList()).toArray(new String[classes.length]));
    }

    protected void addDependents(String ... classes) {
        for (String c : classes) {
            this._after.add(c);
        }
    }

    protected void addDependents(Class<?> ... classes) {
        this.addDependents(Arrays.asList(classes).stream().map(Class::getName).collect(Collectors.toList()).toArray(new String[classes.length]));
    }

    protected void protect(String ... classes) {
        this._system.add(classes);
    }

    protected void hide(String ... classes) {
        this._server.add(classes);
    }

    protected void expose(String ... classes) {
        for (String c : classes) {
            if (c.startsWith("-")) {
                throw new IllegalArgumentException();
            }
            this._server.add("-" + c);
        }
    }

    protected void protectAndExpose(String ... classes) {
        for (String c : classes) {
            if (c.startsWith("-")) {
                throw new IllegalArgumentException();
            }
            this._system.add(c);
            this._server.add("-" + c);
        }
    }

    @Override
    public Collection<String> getDependents() {
        return this._after;
    }

    @Override
    public Collection<String> getDependencies() {
        return this._beforeThis;
    }

    @Override
    public ClassMatcher getSystemClasses() {
        return this._system;
    }

    @Override
    public ClassMatcher getServerClasses() {
        return this._server;
    }

    @Override
    public void preConfigure(WebAppContext context) throws Exception {
    }

    @Override
    public void configure(WebAppContext context) throws Exception {
    }

    @Override
    public void postConfigure(WebAppContext context) throws Exception {
    }

    @Override
    public void deconfigure(WebAppContext context) throws Exception {
    }

    @Override
    public void destroy(WebAppContext context) throws Exception {
    }

    @Override
    public boolean isEnabledByDefault() {
        return this._enabledByDefault;
    }

    @Override
    public boolean abort(WebAppContext context) {
        return false;
    }

    public void cloneConfigure(WebAppContext template, WebAppContext context) throws Exception {
    }
}

