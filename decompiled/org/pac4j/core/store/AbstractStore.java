/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.store;

import java.util.Optional;
import org.pac4j.core.store.Store;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.InitializableObject;

public abstract class AbstractStore<K, O>
extends InitializableObject
implements Store<K, O> {
    @Override
    protected void internalInit(boolean forceReinit) {
    }

    @Override
    public Optional<O> get(K key) {
        CommonHelper.assertNotNull("key", key);
        this.init();
        return this.internalGet(key);
    }

    @Override
    public void set(K key, O value) {
        CommonHelper.assertNotNull("key", key);
        this.init();
        if (value == null) {
            this.internalRemove(key);
        } else {
            this.internalSet(key, value);
        }
    }

    @Override
    public void remove(K key) {
        CommonHelper.assertNotNull("key", key);
        this.init();
        this.internalRemove(key);
    }

    protected abstract Optional<O> internalGet(K var1);

    protected abstract void internalSet(K var1, O var2);

    protected abstract void internalRemove(K var1);
}

