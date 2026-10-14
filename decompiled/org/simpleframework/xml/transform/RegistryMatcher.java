/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

public class RegistryMatcher
implements Matcher {
    private final Cache<Transform> transforms = new ConcurrentCache<Transform>();
    private final Cache<Class> types = new ConcurrentCache<Class>();

    public void bind(Class type, Class transform) {
        this.types.cache(type, transform);
    }

    public void bind(Class type, Transform transform) {
        this.transforms.cache(type, transform);
    }

    @Override
    public Transform match(Class type) throws Exception {
        Transform transform = this.transforms.fetch(type);
        if (transform == null) {
            return this.create(type);
        }
        return transform;
    }

    private Transform create(Class type) throws Exception {
        Class factory2 = this.types.fetch(type);
        if (factory2 != null) {
            return this.create(type, factory2);
        }
        return null;
    }

    private Transform create(Class type, Class factory2) throws Exception {
        Object value = factory2.newInstance();
        Transform transform = (Transform)value;
        if (transform != null) {
            this.transforms.cache(type, transform);
        }
        return transform;
    }
}

