/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Converter;
import org.jooq.Converters;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.impl.Cast;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.SQLDataType;

public class DateAsTimestampBinding
implements Binding<Timestamp, Timestamp> {
    private final Converter<Timestamp, Timestamp> converter = Converters.identity(Timestamp.class);
    private final Binding<Timestamp, Timestamp> delegate = DefaultBinding.binding(this.converter);

    @Override
    public final Converter<Timestamp, Timestamp> converter() {
        return this.converter;
    }

    @Override
    public final void sql(BindingSQLContext<Timestamp> ctx) throws SQLException {
        SQLDialect family = ctx.family();
        RenderContext render = ctx.render();
        if (family == SQLDialect.ORACLE) {
            render.visit(new Cast.CastNative<Date>(DSL.val(ctx.value()), SQLDataType.DATE));
        } else {
            this.delegate.sql(ctx);
        }
    }

    @Override
    public final void register(BindingRegisterContext<Timestamp> ctx) throws SQLException {
        this.delegate.register(ctx);
    }

    @Override
    public final void set(BindingSetStatementContext<Timestamp> ctx) throws SQLException {
        this.delegate.set(ctx);
    }

    @Override
    public final void set(BindingSetSQLOutputContext<Timestamp> ctx) throws SQLException {
        this.delegate.set(ctx);
    }

    @Override
    public final void get(BindingGetResultSetContext<Timestamp> ctx) throws SQLException {
        this.delegate.get(ctx);
    }

    @Override
    public final void get(BindingGetStatementContext<Timestamp> ctx) throws SQLException {
        this.delegate.get(ctx);
    }

    @Override
    public final void get(BindingGetSQLInputContext<Timestamp> ctx) throws SQLException {
        this.delegate.get(ctx);
    }
}

