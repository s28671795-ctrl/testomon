/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DiscoveredAnnotation {
    private static final Logger LOG = LoggerFactory.getLogger(DiscoveredAnnotation.class);
    protected WebAppContext _context;
    protected String _className;
    protected Class<?> _clazz;
    protected Resource _resource;

    public abstract void apply();

    public DiscoveredAnnotation(WebAppContext context, String className) {
        this(context, className, null);
    }

    public DiscoveredAnnotation(WebAppContext context, String className, Resource resource) {
        this._context = context;
        this._className = className;
        this._resource = resource;
    }

    public String getClassName() {
        return this._className;
    }

    public Resource getResource() {
        return this._resource;
    }

    public Class<?> getTargetClass() {
        if (this._clazz != null) {
            return this._clazz;
        }
        this.loadClass();
        return this._clazz;
    }

    private void loadClass() {
        if (this._clazz != null) {
            return;
        }
        if (this._className == null) {
            return;
        }
        try {
            this._clazz = Loader.loadClass(this._className);
        }
        catch (Exception e) {
            LOG.warn("Unable to load {}", (Object)this._className, (Object)e);
        }
    }

    public String toString() {
        return this.getClass().getName() + "[" + this.getClassName() + "," + this.getResource() + "]";
    }
}

