/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Query;
import org.jooq.exception.DataAccessException;

public interface LoaderError {
    public DataAccessException exception();

    public int rowIndex();

    public String[] row();

    public Query query();
}

