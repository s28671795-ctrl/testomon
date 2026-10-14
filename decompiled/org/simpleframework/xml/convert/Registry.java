/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.convert.RegistryBinder;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

public class Registry {
    private final Cache<Converter> cache = new ConcurrentCache<Converter>();
    private final RegistryBinder binder = new RegistryBinder();

    public Converter lookup(Class type) throws Exception {
        Converter converter = this.cache.fetch(type);
        if (converter == null) {
            return this.create(type);
        }
        return converter;
    }

    private Converter create(Class type) throws Exception {
        Converter converter = this.binder.lookup(type);
        if (converter != null) {
            this.cache.cache(type, converter);
        }
        return converter;
    }

    public Registry bind(Class type, Class converter) throws Exception {
        if (type != null) {
            this.binder.bind(type, converter);
        }
        return this;
    }

    public Registry bind(Class type, Converter converter) throws Exception {
        if (type != null) {
            this.cache.cache(type, converter);
        }
        return this;
    }
}

