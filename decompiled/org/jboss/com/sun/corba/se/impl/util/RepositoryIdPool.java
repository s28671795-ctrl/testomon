/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.util;

import java.util.EmptyStackException;
import java.util.Stack;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.impl.util.RepositoryIdCache;

class RepositoryIdPool
extends Stack<RepositoryId> {
    private static final long serialVersionUID = -3730506696831365115L;
    private static int MAX_CACHE_SIZE = 4;
    private RepositoryIdCache cache;

    RepositoryIdPool() {
    }

    public final synchronized RepositoryId popId() {
        try {
            return (RepositoryId)super.pop();
        }
        catch (EmptyStackException e) {
            this.increasePool(5);
            return (RepositoryId)super.pop();
        }
    }

    final void increasePool(int size) {
        for (int i = size; i > 0; --i) {
            this.push(new RepositoryId());
        }
    }

    final void setCaches(RepositoryIdCache cache) {
        this.cache = cache;
    }
}

