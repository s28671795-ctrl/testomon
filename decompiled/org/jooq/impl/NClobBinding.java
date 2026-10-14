/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  oracle.sql.NCLOB
 */
package org.jooq.impl;

import java.sql.Connection;
import java.sql.NClob;
import java.sql.SQLException;
import oracle.sql.NCLOB;
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
import org.jooq.ResourceManagingScope;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.jdbc.JDBCUtils;

public class NClobBinding
implements Binding<String, String> {
    @Override
    public final Converter<String, String> converter() {
        return Converters.identity(String.class);
    }

    @Override
    public final void sql(BindingSQLContext<String> ctx) throws SQLException {
        ctx.render().visit(DSL.val((Object)ctx.value(), SQLDataType.NCLOB));
    }

    @Override
    public final void register(BindingRegisterContext<String> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), 2011);
    }

    @Override
    public final void set(BindingSetStatementContext<String> ctx) throws SQLException {
        ctx.statement().setClob(ctx.index(), NClobBinding.newNClob(ctx, ctx.value()));
    }

    @Override
    public final void set(BindingSetSQLOutputContext<String> ctx) throws SQLException {
        ctx.output().writeClob(NClobBinding.newNClob(ctx, ctx.value()));
    }

    @Override
    public final void get(BindingGetResultSetContext<String> ctx) throws SQLException {
        NClob clob = ctx.resultSet().getNClob(ctx.index());
        try {
            ctx.value(clob == null ? null : clob.getSubString(1L, Tools.asInt(clob.length())));
        }
        finally {
            JDBCUtils.safeFree(clob);
        }
    }

    @Override
    public final void get(BindingGetStatementContext<String> ctx) throws SQLException {
        NClob clob = ctx.statement().getNClob(ctx.index());
        try {
            ctx.value(clob == null ? null : clob.getSubString(1L, Tools.asInt(clob.length())));
        }
        finally {
            JDBCUtils.safeFree(clob);
        }
    }

    @Override
    public final void get(BindingGetSQLInputContext<String> ctx) throws SQLException {
        NClob clob = ctx.input().readNClob();
        try {
            ctx.value(clob == null ? null : clob.getSubString(1L, Tools.asInt(clob.length())));
        }
        finally {
            JDBCUtils.safeFree(clob);
        }
    }

    static final NClob newNClob(ResourceManagingScope scope, String string) throws SQLException {
        NClob clob = switch (scope.dialect()) {
            case SQLDialect.ORACLE10G, SQLDialect.ORACLE11G, SQLDialect.ORACLE12C -> (NClob)NCLOB.createTemporary((Connection)DefaultExecuteContext.localTargetConnection(scope), (boolean)false, (int)10);
            default -> DefaultExecuteContext.localConnection().createNClob();
        };
        scope.autoFree(clob);
        clob.setString(1L, string);
        return clob;
    }
}

