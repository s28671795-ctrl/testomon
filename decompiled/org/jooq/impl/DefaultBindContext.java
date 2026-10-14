/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.R2dbcException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.Field;
import org.jooq.impl.AbstractBindContext;
import org.jooq.impl.DefaultBindingSetStatementContext;
import org.jooq.impl.SimpleExecuteContext;

final class DefaultBindContext
extends AbstractBindContext {
    DefaultBindContext(Configuration configuration, ExecuteContext ctx, PreparedStatement stmt) {
        super(configuration, ctx, stmt);
    }

    @Override
    protected final BindContext bindValue0(Object value, Field<?> field2) throws SQLException {
        int nextIndex = this.nextIndex();
        try {
            field2.getBinding().set(new DefaultBindingSetStatementContext<Object>(new SimpleExecuteContext(this.configuration(), this.data()), this.stmt, nextIndex, value));
        }
        catch (SQLException e) {
            throw new SQLException("Error while writing value at JDBC bind index: " + nextIndex, e.getSQLState(), e.getErrorCode(), e);
        }
        catch (R2dbcException e) {
            throw new SQLException("Error while writing value at R2DBC bind index: " + (nextIndex - 1), e.getSqlState(), e.getErrorCode(), e);
        }
        catch (Exception e) {
            throw new SQLException("Error while writing value at bind index: " + nextIndex, e);
        }
        return this;
    }
}

