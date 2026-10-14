/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.convert.ConverterFactory;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class RegistryBinder {
    private final ConverterFactory factory;
    private final Cache<Class> cache = new ConcurrentCache<Class>();

    public RegistryBinder() {
        this.factory = new ConverterFactory();
    }

    public Converter lookup(Class type) throws Exception {
        Class result = this.cache.fetch(type);
        if (result != null) {
            return this.create(result);
        }
        return null;
    }

    private Converter create(Class type) throws Exception {
        return this.factory.getInstance(type);
    }

    public void bind(Class type, Class converter) throws Exception {
        this.cache.cache(type, converter);
    }
}

