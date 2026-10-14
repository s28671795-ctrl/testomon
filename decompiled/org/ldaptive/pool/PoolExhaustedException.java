/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import org.ldaptive.pool.PoolException;

public class PoolExhaustedException
extends PoolException {
    private static final long serialVersionUID = -2092251274513447389L;

    public PoolExhaustedException(String msg) {
        super(msg);
    }

    public PoolExhaustedException(Exception e) {
        super(e);
    }

    public PoolExhaustedException(String msg, Exception e) {
        super(msg, e);
    }
}

