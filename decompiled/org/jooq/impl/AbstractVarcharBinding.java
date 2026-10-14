/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLException;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.impl.DSL;
import org.jooq.tools.Convert;

abstract class AbstractVarcharBinding<T>
implements Binding<Object, T> {
    AbstractVarcharBinding() {
    }

    @Override
    public final void sql(BindingSQLContext<T> ctx) throws SQLException {
        ctx.render().visit(DSL.val(ctx.convert(this.converter()).value()));
    }

    @Override
    public final void register(BindingRegisterContext<T> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), 12);
    }

    @Override
    public final void set(BindingSetStatementContext<T> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Convert.convert(ctx.convert(this.converter()).value(), String.class));
    }

    @Override
    public final void get(BindingGetResultSetContext<T> ctx) throws SQLException {
        ctx.convert(this.converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    @Override
    public final void get(BindingGetStatementContext<T> ctx) throws SQLException {
        ctx.convert(this.converter()).value(ctx.statement().getString(ctx.index()));
    }

    @Override
    public final void set(BindingSetSQLOutputContext<T> ctx) throws SQLException {
        ctx.output().writeString(Convert.convert(ctx.convert(this.converter()).value(), String.class));
    }

    @Override
    public final void get(BindingGetSQLInputContext<T> ctx) throws SQLException {
        ctx.convert(this.converter()).value(ctx.input().readString());
    }
}

