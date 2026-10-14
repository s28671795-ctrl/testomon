/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.io.Resource
 */
package org.apache.cxf.bus.spring;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.resource.ResourceResolver;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

@NoJSR250Annotations
public class BusApplicationContextResourceResolver
implements ResourceResolver,
ApplicationContextAware {
    ApplicationContext context;

    public BusApplicationContextResourceResolver() {
    }

    public BusApplicationContextResourceResolver(ApplicationContext c) {
        this.context = c;
    }

    @Override
    public InputStream getAsStream(String name) {
        Resource r = this.context.getResource(name);
        if (r != null && r.exists()) {
            try {
                return r.getInputStream();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if ((r = this.context.getResource("/" + name)) != null && r.exists()) {
            try {
                return r.getInputStream();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    @Override
    public <T> T resolve(String resourceName, Class<T> resourceType) {
        try {
            T resource = null;
            if (resourceName == null) {
                String[] names = this.context.getBeanNamesForType(resourceType);
                if (names != null && names.length > 0) {
                    resource = resourceType.cast(this.context.getBean(names[0], resourceType));
                }
            } else {
                resource = resourceType.cast(this.context.getBean(resourceName, resourceType));
            }
            return resource;
        }
        catch (NoSuchBeanDefinitionException resource) {
            try {
                Resource r;
                if (ClassLoader.class.isAssignableFrom(resourceType)) {
                    return resourceType.cast(this.context.getClassLoader());
                }
                if (URL.class.isAssignableFrom(resourceType) && (r = this.context.getResource(resourceName)) != null && r.exists()) {
                    r.getInputStream().close();
                    return resourceType.cast(r.getURL());
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return null;
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }
}

