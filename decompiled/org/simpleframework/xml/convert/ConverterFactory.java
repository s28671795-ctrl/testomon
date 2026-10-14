/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.convert;

import java.lang.reflect.Constructor;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.ConvertException;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.ConcurrentCache;

class ConverterFactory {
    private final Cache<Converter> cache = new ConcurrentCache<Converter>();

    public Converter getInstance(Class type) throws Exception {
        Converter converter = this.cache.fetch(type);
        if (converter == null) {
            return this.getConverter(type);
        }
        return converter;
    }

    public Converter getInstance(Convert convert) throws Exception {
        Class<? extends Converter> type = convert.value();
        if (type.isInterface()) {
            throw new ConvertException("Can not instantiate %s", type);
        }
        return this.getInstance(type);
    }

    private Converter getConverter(Class type) throws Exception {
        Constructor factory2 = this.getConstructor(type);
        if (factory2 == null) {
            throw new ConvertException("No default constructor for %s", type);
        }
        return this.getConverter(type, factory2);
    }

    private Converter getConverter(Class type, Constructor factory2) throws Exception {
        Converter converter = (Converter)factory2.newInstance(new Object[0]);
        if (converter != null) {
            this.cache.cache(type, converter);
        }
        return converter;
    }

    private Constructor getConstructor(Class type) throws Exception {
        Constructor factory2 = type.getDeclaredConstructor(new Class[0]);
        if (!factory2.isAccessible()) {
            factory2.setAccessible(true);
        }
        return factory2;
    }
}

