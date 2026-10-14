/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.orbutil;

import java.util.Hashtable;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryIdPool_1_3_1;
import org.jboss.com.sun.corba.se.impl.orbutil.RepositoryId_1_3_1;

public class RepositoryIdCache_1_3_1
extends Hashtable<String, RepositoryId_1_3_1> {
    private static final long serialVersionUID = -3274465091318937121L;
    private RepositoryIdPool_1_3_1 pool = new RepositoryIdPool_1_3_1();

    public RepositoryIdCache_1_3_1() {
        this.pool.setCaches(this);
    }

    public final synchronized RepositoryId_1_3_1 getId(String key) {
        RepositoryId_1_3_1 repId = (RepositoryId_1_3_1)super.get(key);
        if (repId != null) {
            return repId;
        }
        repId = new RepositoryId_1_3_1(key);
        this.put(key, repId);
        return repId;
    }
}

