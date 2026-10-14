/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;

public interface CloseableDSLContext
extends DSLContext,
AutoCloseable {
    @Override
    public void close() throws DataAccessException;
}

