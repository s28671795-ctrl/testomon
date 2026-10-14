/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.exception.DataAccessException;

public class MockFileDatabaseException
extends DataAccessException {
    public MockFileDatabaseException(String message) {
        super(message);
    }

    public MockFileDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

