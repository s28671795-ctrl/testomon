/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.collection;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.collection.ClassToInstanceMultiMap;

public class LockableClassToInstanceMultiMap<B>
extends ClassToInstanceMultiMap<B> {
    @Nonnull
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);

    public LockableClassToInstanceMultiMap() {
        this(false);
    }

    public LockableClassToInstanceMultiMap(boolean isIndexingSupertypes) {
        super(isIndexingSupertypes);
    }

    @Nonnull
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
}

