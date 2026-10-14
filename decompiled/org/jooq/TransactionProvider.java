/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.TransactionContext;
import org.jooq.exception.DataAccessException;

public interface TransactionProvider {
    public void begin(TransactionContext var1) throws DataAccessException;

    public void commit(TransactionContext var1) throws DataAccessException;

    public void rollback(TransactionContext var1) throws DataAccessException;
}

