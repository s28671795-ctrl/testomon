/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;

public interface ResultOrRows {
    public Result<Record> result();

    public int rows();

    public DataAccessException exception();
}

