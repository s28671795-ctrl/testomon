/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import org.ldaptive.pool.PoolException;

public class BlockingTimeoutException
extends PoolException {
    private static final long serialVersionUID = 6013765020562222482L;

    public BlockingTimeoutException(String msg) {
        super(msg);
    }

    public BlockingTimeoutException(Exception e) {
        super(e);
    }

    public BlockingTimeoutException(String msg, Exception e) {
        super(msg, e);
    }
}

