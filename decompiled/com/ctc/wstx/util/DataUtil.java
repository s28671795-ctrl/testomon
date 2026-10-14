/*
 * Decompiled with CFR 0.152.
 */
package com.ctc.wstx.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.codehaus.stax2.ri.SingletonIterator;

public final class DataUtil {
    static final char[] EMPTY_CHAR_ARRAY = new char[0];
    static final Long MAX_LONG = new Long(Long.MAX_VALUE);
    static final String NO_TYPE = "Illegal to pass null; can not determine component type";

    private DataUtil() {
    }

    public static char[] getEmptyCharArray() {
        return EMPTY_CHAR_ARRAY;
    }

    public static Integer Integer(int i) {
        return i;
    }

    @Deprecated
    public static Long Long(long l) {
        if (l == Long.MAX_VALUE) {
            return MAX_LONG;
        }
        return l;
    }

    public static <T> Iterator<T> singletonIterator(T item) {
        return new SingletonIterator(item);
    }

    public static <T> Iterator<T> emptyIterator() {
        return EmptyIterator.INSTANCE;
    }

    public static <T> boolean anyValuesInCommon(Collection<T> c1, Collection<T> c2) {
        if (c1.size() > c2.size()) {
            Collection<T> tmp = c1;
            c1 = c2;
            c2 = tmp;
        }
        Iterator<T> it = c1.iterator();
        while (it.hasNext()) {
            if (!c2.contains(it.next())) continue;
            return true;
        }
        return false;
    }

    public static Object growArrayBy50Pct(Object arr) {
        if (arr == null) {
            throw new IllegalArgumentException(NO_TYPE);
        }
        Object old = arr;
        int len = Array.getLength(arr);
        arr = Array.newInstance(arr.getClass().getComponentType(), len + (len >> 1));
        System.arraycopy(old, 0, arr, 0, len);
        return arr;
    }

    public static Object growArrayToAtLeast(Object arr, int minLen) {
        if (arr == null) {
            throw new IllegalArgumentException(NO_TYPE);
        }
        Object old = arr;
        int oldLen = Array.getLength(arr);
        int newLen = oldLen + (oldLen + 1 >> 1);
        if (newLen < minLen) {
            newLen = minLen;
        }
        arr = Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(old, 0, arr, 0, oldLen);
        return arr;
    }

    public static String[] growArrayBy(String[] arr, int more) {
        if (arr == null) {
            return new String[more];
        }
        return Arrays.copyOf(arr, arr.length + more);
    }

    public static int[] growArrayBy(int[] arr, int more) {
        if (arr == null) {
            return new int[more];
        }
        return Arrays.copyOf(arr, arr.length + more);
    }

    private static final class EmptyIterator
    implements Iterator<Object> {
        public static final Iterator<?> INSTANCE = new EmptyIterator();

        private EmptyIterator() {
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new IllegalStateException();
        }
    }
}

