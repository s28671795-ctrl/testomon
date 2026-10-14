/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import org.ldaptive.pool.PoolException;

public class ValidationException
extends PoolException {
    private static final long serialVersionUID = -5043560632396467010L;

    public ValidationException(String msg) {
        super(msg);
    }

    public ValidationException(Exception e) {
        super(e);
    }

    public ValidationException(String msg, Exception e) {
        super(msg, e);
    }
}

