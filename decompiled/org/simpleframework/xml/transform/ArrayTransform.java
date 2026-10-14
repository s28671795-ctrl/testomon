/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.lang.reflect.Array;
import org.simpleframework.xml.transform.StringArrayTransform;
import org.simpleframework.xml.transform.Transform;

class ArrayTransform
implements Transform {
    private final StringArrayTransform split = new StringArrayTransform();
    private final Transform delegate;
    private final Class entry;

    public ArrayTransform(Transform delegate, Class entry) {
        this.delegate = delegate;
        this.entry = entry;
    }

    public Object read(String value) throws Exception {
        String[] list = this.split.read(value);
        int length = list.length;
        return this.read(list, length);
    }

    private Object read(String[] list, int length) throws Exception {
        Object array = Array.newInstance(this.entry, length);
        for (int i = 0; i < length; ++i) {
            Object item = this.delegate.read(list[i]);
            if (item == null) continue;
            Array.set(array, i, item);
        }
        return array;
    }

    public String write(Object value) throws Exception {
        int length = Array.getLength(value);
        return this.write(value, length);
    }

    private String write(Object value, int length) throws Exception {
        String[] list = new String[length];
        for (int i = 0; i < length; ++i) {
            Object entry = Array.get(value, i);
            if (entry == null) continue;
            list[i] = this.delegate.write(entry);
        }
        return this.split.write(list);
    }
}

