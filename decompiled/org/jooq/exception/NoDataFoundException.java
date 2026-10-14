/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.InvalidResultException;

public class NoDataFoundException
extends InvalidResultException {
    public NoDataFoundException() {
        super((String)null);
    }

    public NoDataFoundException(String message) {
        super(message);
    }
}

