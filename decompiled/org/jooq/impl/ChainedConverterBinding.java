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
import org.jooq.Converter;
import org.jooq.Converters;

class ChainedConverterBinding<T, U1, U2>
implements Binding<T, U2> {
    private final Binding<T, U1> delegate;
    private final Converter<U1, U2> suffix;
    private final Converter<T, U2> chained;

    ChainedConverterBinding(Binding<T, U1> delegate, Converter<U1, U2> converter) {
        this.delegate = delegate;
        this.suffix = converter;
        this.chained = Converters.of(delegate.converter(), converter);
    }

    @Override
    public Converter<T, U2> converter() {
        return this.chained;
    }

    @Override
    public void sql(BindingSQLContext<U2> ctx) throws SQLException {
        this.delegate.sql(ctx.convert(this.suffix));
    }

    @Override
    public void register(BindingRegisterContext<U2> ctx) throws SQLException {
        this.delegate.register(ctx.convert(this.suffix));
    }

    @Override
    public void set(BindingSetStatementContext<U2> ctx) throws SQLException {
        this.delegate.set(ctx.convert(this.suffix));
    }

    @Override
    public void set(BindingSetSQLOutputContext<U2> ctx) throws SQLException {
        this.delegate.set(ctx.convert(this.suffix));
    }

    @Override
    public void get(BindingGetResultSetContext<U2> ctx) throws SQLException {
        this.delegate.get(ctx.convert(this.suffix));
    }

    @Override
    public void get(BindingGetStatementContext<U2> ctx) throws SQLException {
        this.delegate.get(ctx.convert(this.suffix));
    }

    @Override
    public void get(BindingGetSQLInputContext<U2> ctx) throws SQLException {
        this.delegate.get(ctx.convert(this.suffix));
    }
}

