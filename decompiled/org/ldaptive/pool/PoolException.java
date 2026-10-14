/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import org.ldaptive.LdapException;

public class PoolException
extends LdapException {
    private static final long serialVersionUID = 6320399208563015506L;

    public PoolException(String msg) {
        super(msg);
    }

    public PoolException(Exception e) {
        super(e);
    }

    public PoolException(String msg, Exception e) {
        super(msg, (Throwable)e);
    }
}

