/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CastUtils {
    private CastUtils() {
    }

    public static <T, U> Map<T, U> cast(Map<?, ?> p) {
        return p;
    }

    public static <T, U> Map<T, U> cast(Map<?, ?> p, Class<T> t2, Class<U> u) {
        return p;
    }

    public static <T, U> Dictionary<T, U> cast(Dictionary<?, ?> p) {
        return p;
    }

    public static <T, U> Dictionary<T, U> cast(Dictionary<?, ?> p, Class<T> t2, Class<U> u) {
        return p;
    }

    public static <T> Collection<T> cast(Collection<?> p) {
        return p;
    }

    public static <T> Collection<T> cast(Collection<?> p, Class<T> cls) {
        return p;
    }

    public static <T> List<T> cast(List<?> p) {
        return p;
    }

    public static <T> List<T> cast(List<?> p, Class<T> cls) {
        return p;
    }

    public static <T> Iterator<T> cast(Iterator<?> p) {
        return p;
    }

    public static <T> Iterator<T> cast(Iterator<?> p, Class<T> cls) {
        return p;
    }

    public static <T> Enumeration<T> cast(Enumeration<?> p) {
        return p;
    }

    public static <T> Enumeration<T> cast(Enumeration<?> p, Class<T> cls) {
        return p;
    }

    public static <T> Set<T> cast(Set<?> p) {
        return p;
    }

    public static <T> Set<T> cast(Set<?> p, Class<T> cls) {
        return p;
    }

    public static <T, U> Map.Entry<T, U> cast(Map.Entry<?, ?> p) {
        return p;
    }

    public static <T, U> Map.Entry<T, U> cast(Map.Entry<?, ?> p, Class<T> pc, Class<U> uc) {
        return p;
    }
}

