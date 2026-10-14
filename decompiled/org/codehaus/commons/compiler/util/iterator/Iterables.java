/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.util.iterator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.codehaus.commons.compiler.util.Predicate;
import org.codehaus.commons.nullanalysis.Nullable;

public final class Iterables {
    private Iterables() {
    }

    public static <T> Iterable<T> filterByClass(Object[] delegate, Class<T> qualifyingClass) {
        return Iterables.filterByClass(Arrays.asList(delegate), qualifyingClass);
    }

    public static <T> Iterable<T> filterByClass(final Iterable<?> delegate, final Class<T> qualifyingClass) {
        return new Iterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterables.filterByClass(delegate.iterator(), qualifyingClass);
            }
        };
    }

    public static <T> Iterator<T> filterByClass(Iterator<?> delegate, final Class<T> qualifyingClass) {
        Iterator<Object> result = Iterables.filter(delegate, new Predicate<Object>(){

            @Override
            public boolean evaluate(@Nullable Object o) {
                return o != null && qualifyingClass.isAssignableFrom(o.getClass());
            }
        });
        return result;
    }

    public static <T> Iterable<T> filter(T[] delegate, Predicate<? super T> predicate) {
        return Iterables.filter(Arrays.asList(delegate), predicate);
    }

    public static <T> Iterable<T> filter(final Iterable<? extends T> delegate, final Predicate<? super T> predicate) {
        return new Iterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterables.filter(delegate.iterator(), predicate);
            }
        };
    }

    public static <T> Iterator<T> filter(final Iterator<? extends T> delegate, final Predicate<? super T> predicate) {
        return new Iterator<T>(){
            State state = State.DEFAULT;
            @Nullable
            T nextElement;

            @Override
            public boolean hasNext() {
                switch (this.state) {
                    case DEFAULT: {
                        while (delegate.hasNext()) {
                            Object ne = delegate.next();
                            if (!predicate.evaluate(ne)) continue;
                            this.nextElement = ne;
                            this.state = State.READ_AHEAD;
                            return true;
                        }
                        this.state = State.AT_END;
                        return false;
                    }
                    case READ_AHEAD: {
                        return true;
                    }
                    case AT_END: {
                        return false;
                    }
                }
                throw new AssertionError((Object)this.state);
            }

            @Override
            @Nullable
            public T next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.state = State.DEFAULT;
                Object result = this.nextElement;
                this.nextElement = null;
                return result;
            }

            @Override
            public void remove() {
                delegate.remove();
            }
        };
    }

    public static <T> T[] toArray(Iterable<T> delegate, Class<T> elementType) {
        return Iterables.toArray(delegate.iterator(), elementType);
    }

    public static <T> T[] toArray(Iterator<T> delegate, Class<T> componentType) {
        ArrayList<T> l = new ArrayList<T>();
        while (delegate.hasNext()) {
            l.add(delegate.next());
        }
        Object[] array = (Object[])Array.newInstance(componentType, l.size());
        return l.toArray(array);
    }

    private static enum State {
        DEFAULT,
        READ_AHEAD,
        AT_END;

    }
}

