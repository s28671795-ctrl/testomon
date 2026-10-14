/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.store;

import java.util.Optional;

public interface Store<K, O> {
    public Optional<O> get(K var1);

    public void set(K var1, O var2);

    public void remove(K var1);
}

