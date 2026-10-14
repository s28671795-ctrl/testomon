/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Converter;

public interface Binding<T, U>
extends Serializable {
    @NotNull
    public Converter<T, U> converter();

    public void sql(BindingSQLContext<U> var1) throws SQLException;

    public void register(BindingRegisterContext<U> var1) throws SQLException;

    public void set(BindingSetStatementContext<U> var1) throws SQLException;

    public void set(BindingSetSQLOutputContext<U> var1) throws SQLException;

    public void get(BindingGetResultSetContext<U> var1) throws SQLException;

    public void get(BindingGetStatementContext<U> var1) throws SQLException;

    public void get(BindingGetSQLInputContext<U> var1) throws SQLException;

    @NotNull
    public static <T, U> Binding<T, U> of(Converter<T, U> converter, Consumer<? super BindingSQLContext<U>> sqlContext, Consumer<? super BindingGetResultSetContext<U>> getResultSetContext, Consumer<? super BindingSetStatementContext<U>> setStatementContext) {
        return Binding.of(converter, sqlContext, getResultSetContext, setStatementContext, ctx -> {
            throw new UnsupportedOperationException();
        }, ctx -> {
            throw new UnsupportedOperationException();
        }, ctx -> {
            throw new UnsupportedOperationException();
        }, ctx -> {
            throw new UnsupportedOperationException();
        });
    }

    @NotNull
    public static <T, U> Binding<T, U> of(Converter<T, U> converter, Consumer<? super BindingSQLContext<U>> sqlContext, Consumer<? super BindingGetResultSetContext<U>> getResultSetContext, Consumer<? super BindingSetStatementContext<U>> setStatementContext, Consumer<? super BindingRegisterContext<U>> registerContext, Consumer<? super BindingGetStatementContext<U>> getStatementContext) {
        return Binding.of(converter, sqlContext, getResultSetContext, setStatementContext, registerContext, getStatementContext, ctx -> {
            throw new UnsupportedOperationException();
        }, ctx -> {
            throw new UnsupportedOperationException();
        });
    }

    @NotNull
    public static <T, U> Binding<T, U> of(final Converter<T, U> converter, final Consumer<? super BindingSQLContext<U>> sqlContext, final Consumer<? super BindingGetResultSetContext<U>> getResultSetContext, final Consumer<? super BindingSetStatementContext<U>> setStatementContext, final Consumer<? super BindingRegisterContext<U>> registerContext, final Consumer<? super BindingGetStatementContext<U>> getStatementContext, final Consumer<? super BindingGetSQLInputContext<U>> getSqlInputContext, final Consumer<? super BindingSetSQLOutputContext<U>> setSqlOutputContext) {
        return new Binding<T, U>(){

            @Override
            public final Converter<T, U> converter() {
                return converter;
            }

            @Override
            public final void sql(BindingSQLContext<U> ctx) throws SQLException {
                sqlContext.accept(ctx);
            }

            @Override
            public final void register(BindingRegisterContext<U> ctx) throws SQLException {
                registerContext.accept(ctx);
            }

            @Override
            public final void set(BindingSetStatementContext<U> ctx) throws SQLException {
                setStatementContext.accept(ctx);
            }

            @Override
            public final void set(BindingSetSQLOutputContext<U> ctx) throws SQLException {
                setSqlOutputContext.accept(ctx);
            }

            @Override
            public final void get(BindingGetResultSetContext<U> ctx) throws SQLException {
                getResultSetContext.accept(ctx);
            }

            @Override
            public final void get(BindingGetStatementContext<U> ctx) throws SQLException {
                getStatementContext.accept(ctx);
            }

            @Override
            public final void get(BindingGetSQLInputContext<U> ctx) throws SQLException {
                getSqlInputContext.accept(ctx);
            }
        };
    }
}

