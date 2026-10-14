/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.util.EmptyStackException;
import java.util.Stack;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdCache_1_3_1;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryId_1_3_1;

class RepositoryIdPool_1_3_1
extends Stack<RepositoryId_1_3_1> {
    private static final long serialVersionUID = -953493376604302351L;
    private static int MAX_CACHE_SIZE = 4;
    private RepositoryIdCache_1_3_1 cache;

    RepositoryIdPool_1_3_1() {
    }

    public final synchronized RepositoryId_1_3_1 popId() {
        try {
            return (RepositoryId_1_3_1)super.pop();
        }
        catch (EmptyStackException e) {
            this.increasePool(5);
            return (RepositoryId_1_3_1)super.pop();
        }
    }

    final void increasePool(int size) {
        for (int i = size; i > 0; --i) {
            this.push(new RepositoryId_1_3_1());
        }
    }

    final void setCaches(RepositoryIdCache_1_3_1 cache) {
        this.cache = cache;
    }
}

