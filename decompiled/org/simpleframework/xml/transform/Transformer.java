/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.DefaultMatcher;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;
import org.simpleframework.xml.transform.TransformException;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

public class Transformer {
    private final Cache<Transform> cache = new ConcurrentCache<Transform>();
    private final Cache<Object> error = new ConcurrentCache<Object>();
    private final Matcher matcher;

    public Transformer(Matcher matcher) {
        this.matcher = new DefaultMatcher(matcher);
    }

    public Object read(String value, Class type) throws Exception {
        Transform transform = this.lookup(type);
        if (transform == null) {
            throw new TransformException("Transform of %s not supported", type);
        }
        return transform.read(value);
    }

    public String write(Object value, Class type) throws Exception {
        Transform transform = this.lookup(type);
        if (transform == null) {
            throw new TransformException("Transform of %s not supported", type);
        }
        return transform.write(value);
    }

    public boolean valid(Class type) throws Exception {
        return this.lookup(type) != null;
    }

    private Transform lookup(Class type) throws Exception {
        if (!this.error.contains(type)) {
            Transform transform = this.cache.fetch(type);
            if (transform != null) {
                return transform;
            }
            return this.match(type);
        }
        return null;
    }

    private Transform match(Class type) throws Exception {
        Transform transform = this.matcher.match(type);
        if (transform != null) {
            this.cache.cache(type, transform);
        } else {
            this.error.cache(type, this);
        }
        return transform;
    }
}

