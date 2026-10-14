/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util;

import org.eclipse.jetty.util.Decorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeprecationWarning
implements Decorator {
    private static final Logger LOG = LoggerFactory.getLogger(DeprecationWarning.class);

    @Override
    public <T> T decorate(T o) {
        if (o == null) {
            return null;
        }
        Class<?> clazz = o.getClass();
        try {
            Deprecated depr = clazz.getAnnotation(Deprecated.class);
            if (depr != null) {
                LOG.warn("Using @Deprecated Class {}", (Object)clazz.getName());
            }
        }
        catch (Throwable t2) {
            LOG.trace("IGNORED", t2);
        }
        this.verifyIndirectTypes(clazz.getSuperclass(), clazz, "Class");
        for (Class<?> ifaceClazz : clazz.getInterfaces()) {
            this.verifyIndirectTypes(ifaceClazz, clazz, "Interface");
        }
        return o;
    }

    private void verifyIndirectTypes(Class<?> superClazz, Class<?> clazz, String typeName) {
        try {
            while (superClazz != null && superClazz != Object.class) {
                Deprecated supDepr = superClazz.getAnnotation(Deprecated.class);
                if (supDepr != null) {
                    LOG.warn("Using indirect @Deprecated {} {} - (seen from {})", typeName, superClazz.getName(), clazz);
                }
                superClazz = superClazz.getSuperclass();
            }
        }
        catch (Throwable t2) {
            LOG.trace("IGNORED", t2);
        }
    }

    @Override
    public void destroy(Object o) {
    }
}

