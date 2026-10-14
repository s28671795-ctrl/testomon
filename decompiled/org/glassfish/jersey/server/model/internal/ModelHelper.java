/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model.internal;

import javax.ws.rs.Path;

public final class ModelHelper {
    public static Class<?> getAnnotatedResourceClass(Class<?> resourceClass) {
        Class<?> foundInterface = null;
        Class<?> cls = resourceClass;
        block0: do {
            if (cls.isAnnotationPresent(Path.class)) {
                return cls;
            }
            if (foundInterface != null) continue;
            for (Class<?> i : cls.getInterfaces()) {
                if (!i.isAnnotationPresent(Path.class)) continue;
                foundInterface = i;
                continue block0;
            }
        } while ((cls = cls.getSuperclass()) != null);
        if (foundInterface != null) {
            return foundInterface;
        }
        return resourceClass;
    }

    private ModelHelper() {
        throw new AssertionError((Object)"Instantiation not allowed.");
    }
}

