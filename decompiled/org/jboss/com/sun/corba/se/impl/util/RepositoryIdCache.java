/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.util;

import java.util.Hashtable;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.impl.util.RepositoryIdPool;

public class RepositoryIdCache
extends Hashtable<String, RepositoryId> {
    private static final long serialVersionUID = -8725173778421510608L;
    private RepositoryIdPool pool = new RepositoryIdPool();

    public RepositoryIdCache() {
        this.pool.setCaches(this);
    }

    public final synchronized RepositoryId getId(String key) {
        RepositoryId repId = (RepositoryId)super.get(key);
        if (repId != null) {
            return repId;
        }
        repId = new RepositoryId(key);
        this.put(key, repId);
        return repId;
    }
}

