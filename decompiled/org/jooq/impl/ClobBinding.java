/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  oracle.sql.CLOB
 */
package org.jooq.impl;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;
import oracle.sql.CLOB;
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
import org.jooq.Scope;
import org.jooq.Source;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.jdbc.JDBCUtils;

public class ClobBinding
implements Binding<String, String> {
    static final Set<SQLDialect> NO_SUPPORT_NULL_LOBS = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.HSQLDB);

    @Override
    public final Converter<String, String> converter() {
        return Converters.identity(String.class);
    }

    @Override
    public final void sql(BindingSQLContext<String> ctx) throws SQLException {
        if (ctx.render().paramType() == ParamType.INLINED) {
            ctx.render().visit(DSL.inline((Object)ctx.convert(this.converter()).value(), SQLDataType.CLOB));
        } else {
            ctx.render().sql(ctx.variable());
        }
    }

    @Override
    public final void register(BindingRegisterContext<String> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), 2005);
    }

    @Override
    public final void set(BindingSetStatementContext<String> ctx) throws SQLException {
        Clob clob = ClobBinding.newClob(ctx, ctx.value());
        if (clob == null && NO_SUPPORT_NULL_LOBS.contains((Object)ctx.dialect())) {
            ctx.statement().setNull(ctx.index(), 2005);
        } else {
            ctx.statement().setClob(ctx.index(), clob);
        }
    }

    @Override
    public final void set(BindingSetSQLOutputContext<String> ctx) throws SQLException {
        ctx.output().writeClob(ClobBinding.newClob(ctx, ctx.value()));
    }

    @Override
    public final void get(BindingGetResultSetContext<String> ctx) throws SQLException {
        Clob clob = ctx.resultSet().getClob(ctx.index());
        try {
            ctx.value(clob == null ? null : ClobBinding.read(ctx, clob));
        }
        finally {
            JDBCUtils.safeFree(clob);
        }
    }

    @Override
    public final void get(BindingGetStatementContext<String> ctx) throws SQLException {
        Clob clob = ctx.statement().getClob(ctx.index());
        try {
            ctx.value(clob == null ? null : ClobBinding.read(ctx, clob));
        }
        finally {
            JDBCUtils.safeFree(clob);
        }
    }

    @Override
    public final void get(BindingGetSQLInputContext<String> ctx) throws SQLException {
        Clob clob = ctx.input().readClob();
        try {
            ctx.value(clob == null ? null : ClobBinding.read(ctx, clob));
        }
        finally {
            JDBCUtils.safeFree(clob);
        }
    }

    static final String read(Scope ctx, Clob clob) throws SQLException {
        switch (ctx.family()) {
            case FIREBIRD: 
            case SQLITE: {
                Reader r = clob.getCharacterStream();
                return r == null ? null : Source.of(r).readString();
            }
        }
        return clob.getSubString(1L, Tools.asInt(clob.length()));
    }

    static final Clob newClob(ResourceManagingScope scope, String string) throws SQLException {
        if (string == null) {
            return null;
        }
        Clob clob = switch (scope.dialect()) {
            case SQLDialect.ORACLE10G, SQLDialect.ORACLE11G, SQLDialect.ORACLE12C -> CLOB.createTemporary((Connection)DefaultExecuteContext.localTargetConnection(scope), (boolean)false, (int)10);
            default -> DefaultExecuteContext.localConnection().createClob();
        };
        scope.autoFree(clob);
        clob.setString(1L, string);
        return clob;
    }
}

