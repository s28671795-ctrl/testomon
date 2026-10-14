/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;

public class MetadataIndexStore<T> {
    @Nonnull
    private Map<MetadataIndexKey, Set<T>> index = new ConcurrentHashMap<MetadataIndexKey, Set<T>>();

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<MetadataIndexKey> getKeys() {
        return ImmutableSet.copyOf(this.index.keySet());
    }

    @Nonnull
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<T> lookup(@Nonnull MetadataIndexKey key) {
        Constraint.isNotNull(key, "IndexKey was null");
        Set<T> items = this.index.get(key);
        if (items == null) {
            return Collections.emptySet();
        }
        return ImmutableSet.copyOf(items);
    }

    public void add(MetadataIndexKey key, T item) {
        Constraint.isNotNull(key, "IndexKey was null");
        Constraint.isNotNull(item, "The indexed data element was null");
        Set<T> items = this.index.get(key);
        if (items == null) {
            items = new HashSet<T>();
            this.index.put(key, items);
        }
        items.add(item);
    }

    public void remove(MetadataIndexKey key, T item) {
        Constraint.isNotNull(key, "IndexKey was null");
        Constraint.isNotNull(item, "The indexed data element was null");
        Set<T> items = this.index.get(key);
        if (items == null) {
            return;
        }
        items.remove(item);
    }

    public void clear(MetadataIndexKey key) {
        Constraint.isNotNull(key, "IndexKey was null");
        this.index.remove(key);
    }

    public void clear() {
        this.index.clear();
    }
}

