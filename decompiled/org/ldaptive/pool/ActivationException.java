/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import org.ldaptive.pool.PoolException;

public class ActivationException
extends PoolException {
    private static final long serialVersionUID = 5547712224386623996L;

    public ActivationException(String msg) {
        super(msg);
    }

    public ActivationException(Exception e) {
        super(e);
    }

    public ActivationException(String msg, Exception e) {
        super(msg, e);
    }
}

