/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractContext;
import org.jooq.impl.Tools;

abstract class AbstractBindContext
extends AbstractContext<BindContext>
implements BindContext {
    AbstractBindContext(Configuration configuration, ExecuteContext ctx, PreparedStatement stmt) {
        super(configuration, ctx, stmt);
    }

    @Override
    protected void visit0(QueryPartInternal internal) {
        this.bindInternal(internal);
    }

    @Override
    public final BindContext bindValue(Object value, Field<?> field2) throws DataAccessException {
        try {
            return this.bindValue0(value, field2);
        }
        catch (SQLException e) {
            throw Tools.translate(null, e);
        }
    }

    @Override
    public final String peekAlias() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final String nextAlias() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final String render() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final String render(QueryPart part) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public final BindContext keyword(String keyword) {
        return this;
    }

    @Override
    public final BindContext sql(String sql) {
        return this;
    }

    @Override
    public final BindContext sql(String sql, boolean literal) {
        return this;
    }

    @Override
    public final BindContext sqlIndentStart(String sql) {
        return this;
    }

    @Override
    public final BindContext sqlIndentEnd(String sql) {
        return this;
    }

    @Override
    public final BindContext sqlIndentStart() {
        return this;
    }

    @Override
    public final BindContext sqlIndentEnd() {
        return this;
    }

    @Override
    public final BindContext sql(char sql) {
        return this;
    }

    @Override
    public final BindContext sqlIndentStart(char sql) {
        return this;
    }

    @Override
    public final BindContext sqlIndentEnd(char sql) {
        return this;
    }

    @Override
    public final BindContext sql(int sql) {
        return this;
    }

    @Override
    public final BindContext sql(long sql) {
        return this;
    }

    @Override
    public final BindContext sql(float sql) {
        return this;
    }

    @Override
    public final BindContext sql(double sql) {
        return this;
    }

    @Override
    public final BindContext format(boolean format) {
        return this;
    }

    @Override
    public final boolean format() {
        return false;
    }

    @Override
    public final BindContext formatNewLine() {
        return this;
    }

    @Override
    public final BindContext formatNewLineAfterPrintMargin() {
        return this;
    }

    @Override
    public final BindContext formatSeparator() {
        return this;
    }

    @Override
    public final BindContext separatorRequired(boolean separatorRequired) {
        return this;
    }

    @Override
    public final boolean separatorRequired() {
        return false;
    }

    @Override
    public final BindContext formatIndentStart() {
        return this;
    }

    @Override
    public final BindContext formatIndentStart(int indent) {
        return this;
    }

    @Override
    public final BindContext formatIndentLockStart() {
        return this;
    }

    @Override
    public final BindContext formatIndentEnd() {
        return this;
    }

    @Override
    public final BindContext formatIndentEnd(int indent) {
        return this;
    }

    @Override
    public final BindContext formatIndentLockEnd() {
        return this;
    }

    @Override
    public final BindContext formatPrintMargin(int margin) {
        return this;
    }

    @Override
    public final BindContext literal(String literal) {
        return this;
    }

    protected void bindInternal(QueryPartInternal internal) {
        internal.accept(this);
    }

    protected BindContext bindValue0(Object value, Field<?> field2) throws SQLException {
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.toString(sb);
        return sb.toString();
    }
}

