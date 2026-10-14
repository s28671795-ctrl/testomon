/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.resource;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.resource.ClassLoaderResolver;
import org.apache.cxf.resource.ClasspathResolver;
import org.apache.cxf.resource.ResourceManager;
import org.apache.cxf.resource.ResourceResolver;

public class DefaultResourceManager
implements ResourceManager {
    private static final Logger LOG = LogUtils.getL7dLogger(DefaultResourceManager.class);
    protected final List<ResourceResolver> registeredResolvers = new CopyOnWriteArrayList<ResourceResolver>();
    protected boolean firstCalled;

    public DefaultResourceManager() {
        this.initializeDefaultResolvers();
    }

    public DefaultResourceManager(ResourceResolver resolver2) {
        this.addResourceResolver(resolver2);
    }

    public DefaultResourceManager(List<? extends ResourceResolver> resolvers) {
        this.addResourceResolvers(resolvers);
    }

    protected void onFirstResolve() {
        this.firstCalled = true;
    }

    @Override
    public final <T> T resolveResource(String name, Class<T> type) {
        return this.findResource(name, type, false, this.registeredResolvers);
    }

    @Override
    public final <T> T resolveResource(String name, Class<T> type, List<ResourceResolver> resolvers) {
        return this.findResource(name, type, false, resolvers);
    }

    @Override
    public final InputStream getResourceAsStream(String name) {
        return this.findResource(name, InputStream.class, true, this.registeredResolvers);
    }

    @Override
    public final void addResourceResolver(ResourceResolver resolver2) {
        if (!this.registeredResolvers.contains(resolver2)) {
            this.registeredResolvers.add(0, resolver2);
        }
    }

    public final void addResourceResolvers(Collection<? extends ResourceResolver> resolvers) {
        int i = 0;
        for (ResourceResolver resourceResolver : resolvers) {
            while (!this.registeredResolvers.contains(resourceResolver)) {
                try {
                    this.registeredResolvers.add(i++, resourceResolver);
                }
                catch (IndexOutOfBoundsException e) {
                    i = this.registeredResolvers.size();
                }
            }
        }
    }

    @Override
    public final void removeResourceResolver(ResourceResolver resolver2) {
        if (this.registeredResolvers.contains(resolver2)) {
            this.registeredResolvers.remove(resolver2);
        }
    }

    @Override
    public final List<ResourceResolver> getResourceResolvers() {
        return Collections.unmodifiableList(this.registeredResolvers);
    }

    private <T> T findResource(String name, Class<T> type, boolean asStream, List<ResourceResolver> resolvers) {
        if (!this.firstCalled) {
            this.onFirstResolve();
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.fine("resolving resource <" + name + ">" + (asStream ? " as stream " : " type <" + type + ">"));
        }
        T ret = null;
        for (ResourceResolver rr : resolvers != null ? resolvers : this.registeredResolvers) {
            block7: {
                if (asStream) {
                    ret = type.cast(rr.getAsStream(name));
                } else {
                    try {
                        ret = rr.resolve(name, type);
                    }
                    catch (RuntimeException ex) {
                        if (!LOG.isLoggable(Level.FINE)) break block7;
                        LOG.log(Level.FINE, "run into exception when using" + rr.getClass().getName(), ex);
                    }
                }
            }
            if (ret == null) continue;
            break;
        }
        return ret;
    }

    private void initializeDefaultResolvers() {
        this.addResourceResolver(new ClasspathResolver());
        this.addResourceResolver(new ClassLoaderResolver(this.getClass().getClassLoader()));
    }
}

