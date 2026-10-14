/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Key;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.InjectorBindingData;
import com.google.inject.internal.WeakKeySet;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

final class InjectorJitBindingData {
    private final Map<Key<?>, BindingImpl<?>> jitBindings = Maps.newHashMap();
    private final Set<Key<?>> failedJitBindings = Sets.newHashSet();
    private final WeakKeySet bannedKeys;
    private final Optional<InjectorJitBindingData> parent;
    private final Object lock;

    InjectorJitBindingData(Optional<InjectorJitBindingData> parent) {
        this.parent = parent;
        this.lock = parent.isPresent() ? parent.get().lock() : this;
        this.bannedKeys = new WeakKeySet(this.lock);
    }

    Map<Key<?>, BindingImpl<?>> getJitBindings() {
        return Collections.unmodifiableMap(this.jitBindings);
    }

    BindingImpl<?> getJitBinding(Key<?> key) {
        return this.jitBindings.get(key);
    }

    void putJitBinding(Key<?> key, BindingImpl<?> binding) {
        this.jitBindings.put(key, binding);
    }

    void removeJitBinding(Key<?> key) {
        this.jitBindings.remove(key);
    }

    boolean isFailedJitBinding(Key<?> key) {
        return this.failedJitBindings.contains(key);
    }

    void addFailedJitBinding(Key<?> key) {
        this.failedJitBindings.add(key);
    }

    void banKey(Key<?> key, InjectorBindingData injectorBindingData, Object source2) {
        this.banKeyInParent(key, injectorBindingData, source2);
        this.bannedKeys.add(key, injectorBindingData, source2);
    }

    void banKeyInParent(Key<?> key, InjectorBindingData injectorBindingData, Object source2) {
        if (this.parent.isPresent()) {
            this.parent.get().banKey(key, injectorBindingData, source2);
        }
    }

    boolean isBannedKey(Key<?> key) {
        return this.bannedKeys.contains(key);
    }

    Set<Object> getSourcesForBannedKey(Key<?> key) {
        return this.bannedKeys.getSources(key);
    }

    Object lock() {
        return this.lock;
    }
}

