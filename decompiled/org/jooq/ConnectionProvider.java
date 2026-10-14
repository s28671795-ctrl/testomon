/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.sql.Connection;
import org.jetbrains.annotations.Nullable;
import org.jooq.exception.DataAccessException;

public interface ConnectionProvider {
    @Nullable
    public Connection acquire() throws DataAccessException;

    public void release(Connection var1) throws DataAccessException;
}

