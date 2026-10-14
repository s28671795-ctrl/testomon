/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import org.jooq.Binding;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;

public abstract class AbstractBinding<T, U>
implements Binding<T, U> {
    @Override
    public void sql(BindingSQLContext<U> ctx) throws SQLException {
        if (ctx.render().paramType() == ParamType.INLINED) {
            if (ctx.value() == null) {
                ctx.render().visit(Keywords.K_NULL);
            } else {
                this.sqlInline(ctx);
            }
        } else {
            this.sqlBind(ctx);
        }
    }

    protected void sqlInline(BindingSQLContext<U> ctx) throws SQLException {
        ctx.render().visit(DSL.inline(String.valueOf(ctx.value())));
    }

    protected void sqlBind(BindingSQLContext<U> ctx) throws SQLException {
        ctx.render().sql(ctx.variable());
    }

    @Override
    public void register(BindingRegisterContext<U> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException("AbstractBinding::register");
    }

    @Override
    public void get(BindingGetStatementContext<U> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException("AbstractBinding::get");
    }

    @Override
    public void set(BindingSetSQLOutputContext<U> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException("AbstractBinding::set");
    }

    @Override
    public void get(BindingGetSQLInputContext<U> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException("AbstractBinding::get");
    }
}

