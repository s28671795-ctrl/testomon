/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import java.lang.reflect.Array;
import org.simpleframework.xml.transform.Transform;

class CharacterArrayTransform
implements Transform {
    private final Class entry;

    public CharacterArrayTransform(Class entry) {
        this.entry = entry;
    }

    public Object read(String value) throws Exception {
        char[] list = value.toCharArray();
        int length = list.length;
        if (this.entry == Character.TYPE) {
            return list;
        }
        return this.read(list, length);
    }

    private Object read(char[] list, int length) throws Exception {
        Object array = Array.newInstance(this.entry, length);
        for (int i = 0; i < length; ++i) {
            Array.set(array, i, Character.valueOf(list[i]));
        }
        return array;
    }

    public String write(Object value) throws Exception {
        int length = Array.getLength(value);
        if (this.entry == Character.TYPE) {
            char[] array = (char[])value;
            return new String(array);
        }
        return this.write(value, length);
    }

    private String write(Object value, int length) throws Exception {
        StringBuilder text = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            Object entry = Array.get(value, i);
            if (entry == null) continue;
            text.append(entry);
        }
        return text.toString();
    }
}

