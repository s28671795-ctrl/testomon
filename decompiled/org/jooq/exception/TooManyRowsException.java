/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.InvalidResultException;

public class TooManyRowsException
extends InvalidResultException {
    public TooManyRowsException() {
        super((String)null);
    }

    public TooManyRowsException(String message) {
        super(message);
    }
}

