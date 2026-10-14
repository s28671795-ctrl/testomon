/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;

@NotThreadSafe
public class ClassToInstanceMultiMap<B> {
    private final boolean indexSupertypes;
    @Nonnull
    private final HashMap<Class<?>, List<B>> backingMap = new HashMap();
    @Nonnull
    private final List<B> values = new ArrayList<B>();

    public ClassToInstanceMultiMap() {
        this(false);
    }

    public ClassToInstanceMultiMap(boolean isIndexingSupertypes) {
        this.indexSupertypes = isIndexingSupertypes;
    }

    public void clear() {
        this.values.clear();
        this.backingMap.clear();
    }

    public boolean containsKey(@Nullable Class<?> key) {
        if (key == null) {
            return false;
        }
        return this.backingMap.containsKey(key);
    }

    public boolean containsValue(@Nonnull B value) {
        if (value == null) {
            return false;
        }
        return this.values.contains(value);
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @Live
    public <T> List<T> get(@Nullable Class<T> type) {
        if (type == null) {
            return Collections.emptyList();
        }
        List<B> indexedValues = this.backingMap.get(type);
        if (indexedValues == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(indexedValues);
    }

    public boolean isEmpty() {
        return this.values.isEmpty();
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @Live
    public Set<Class<?>> keys() {
        return Collections.unmodifiableSet(this.backingMap.keySet());
    }

    public void put(@Nonnull B value) {
        if (value == null) {
            return;
        }
        if (!this.values.contains(value)) {
            this.values.add(value);
        }
        for (Class<?> indexKey : this.getIndexTypes(value)) {
            List<B> indexValues = this.backingMap.get(indexKey);
            if (indexValues == null) {
                indexValues = new ArrayList<B>();
                this.backingMap.put(indexKey, indexValues);
            }
            if (indexValues.contains(value)) continue;
            indexValues.add(value);
        }
    }

    public void putAll(@Nullable @NonnullElements Iterable<? extends B> newValues) {
        if (newValues == null) {
            return;
        }
        for (B value : newValues) {
            this.put(value);
        }
    }

    public void putAll(@Nullable @NonnullElements ClassToInstanceMultiMap<? extends B> map) {
        if (map == null) {
            return;
        }
        this.putAll(map.values());
    }

    public void remove(@Nonnull B value) {
        if (value == null) {
            return;
        }
        this.values.remove(value);
        for (Class<?> indexKey : this.getIndexTypes(value)) {
            List<B> indexValues = this.backingMap.get(indexKey);
            if (indexValues == null) continue;
            indexValues.remove(value);
            if (!indexValues.isEmpty()) continue;
            this.backingMap.remove(indexKey);
        }
    }

    public void removeAll(@Nullable @NonnullElements Iterable<? extends B> removeValues) {
        if (removeValues == null) {
            return;
        }
        for (B value : removeValues) {
            this.remove(value);
        }
    }

    public void removeAll(@Nullable @NonnullElements ClassToInstanceMultiMap<? extends B> map) {
        if (map == null) {
            return;
        }
        this.removeAll(map.values());
    }

    public void remove(@Nullable Class<?> type) {
        if (type == null) {
            return;
        }
        List<B> indexValues = this.backingMap.remove(type);
        if (indexValues != null) {
            for (B value : indexValues) {
                this.remove(value);
            }
        }
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @Live
    public Collection<? extends B> values() {
        return Collections.unmodifiableList(this.values);
    }

    @Nonnull
    @NonnullElements
    private Set<Class<?>> getIndexTypes(@Nonnull B value) {
        HashSet indexTypes = new HashSet();
        indexTypes.add(value.getClass());
        if (this.indexSupertypes) {
            this.getSuperTypes(value.getClass(), indexTypes);
        }
        return indexTypes;
    }

    private void getSuperTypes(@Nonnull Class<?> clazz, @Nonnull @NonnullElements Set<Class<?>> accumulator) {
        Class<?>[] interfaces;
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            accumulator.add(superclass);
            this.getSuperTypes(superclass, accumulator);
        }
        if ((interfaces = clazz.getInterfaces()).length > 0) {
            for (Class<?> iface : interfaces) {
                accumulator.add(iface);
                this.getSuperTypes(iface, accumulator);
            }
        }
    }

    public int hashCode() {
        return this.backingMap.hashCode() + this.values.hashCode();
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (obj instanceof ClassToInstanceMultiMap) {
            ClassToInstanceMultiMap cast = (ClassToInstanceMultiMap)obj;
            return this.backingMap.equals(cast.backingMap) && this.values.equals(cast.values);
        }
        return false;
    }
}

