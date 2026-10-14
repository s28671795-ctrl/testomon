/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.util.Hashtable;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdPool_1_3;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryId_1_3;

public class RepositoryIdCache_1_3
extends Hashtable<String, RepositoryId_1_3> {
    private static final long serialVersionUID = -1165177831273338156L;
    private RepositoryIdPool_1_3 pool = new RepositoryIdPool_1_3();

    public RepositoryIdCache_1_3() {
        this.pool.setCaches(this);
    }

    public final synchronized RepositoryId_1_3 getId(String key) {
        RepositoryId_1_3 repId = (RepositoryId_1_3)super.get(key);
        if (repId != null) {
            return repId;
        }
        repId = new RepositoryId_1_3(key);
        this.put(key, repId);
        return repId;
    }
}

