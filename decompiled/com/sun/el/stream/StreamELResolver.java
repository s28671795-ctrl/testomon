/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.stream;

import com.sun.el.stream.Stream;
import java.beans.FeatureDescriptor;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELResolver;

public class StreamELResolver
extends ELResolver {
    @Override
    public Object invoke(ELContext context, Object base, Object method2, Class<?>[] paramTypes, Object[] params) {
        if (context == null) {
            throw new NullPointerException();
        }
        if (base instanceof Collection) {
            Collection c = (Collection)base;
            if ("stream".equals(method2) && params.length == 0) {
                context.setPropertyResolved(true);
                return new Stream(c.iterator());
            }
        }
        if (base.getClass().isArray() && "stream".equals(method2) && params.length == 0) {
            context.setPropertyResolved(true);
            return new Stream(StreamELResolver.arrayIterator(base));
        }
        return null;
    }

    private static Iterator<Object> arrayIterator(final Object base) {
        final int size = Array.getLength(base);
        return new Iterator<Object>(){
            int index = 0;
            boolean yielded;
            Object current;

            @Override
            public boolean hasNext() {
                if (!this.yielded && this.index < size) {
                    this.current = Array.get(base, this.index++);
                    this.yielded = true;
                }
                return this.yielded;
            }

            @Override
            public Object next() {
                this.yielded = false;
                return this.current;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public Object getValue(ELContext context, Object base, Object property) {
        return null;
    }

    @Override
    public Class<?> getType(ELContext context, Object base, Object property) {
        return null;
    }

    @Override
    public void setValue(ELContext context, Object base, Object property, Object value) {
    }

    @Override
    public boolean isReadOnly(ELContext context, Object base, Object property) {
        return false;
    }

    @Override
    public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
        return null;
    }

    @Override
    public Class<?> getCommonPropertyType(ELContext context, Object base) {
        return String.class;
    }
}

