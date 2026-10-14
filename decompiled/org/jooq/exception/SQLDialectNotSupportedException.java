/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.exception;

import org.jooq.tools.JooqLogger;

public class SQLDialectNotSupportedException
extends RuntimeException {
    private static final JooqLogger log = JooqLogger.getLogger(SQLDialectNotSupportedException.class);

    public SQLDialectNotSupportedException(String message) {
        this(message, true);
    }

    public SQLDialectNotSupportedException(String message, boolean warn) {
        super(message);
        if (warn) {
            log.warn((Object)"Not supported by dialect", message);
        }
    }
}

