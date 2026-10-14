/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  oracle.sql.BLOB
 */
package org.jooq.impl;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import oracle.sql.BLOB;
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
import org.jooq.conf.ParamType;
import org.jooq.impl.ClobBinding;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.R2DBC;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.jdbc.JDBCUtils;

public class BlobBinding
implements Binding<byte[], byte[]> {
    @Override
    public final Converter<byte[], byte[]> converter() {
        return Converters.identity(byte[].class);
    }

    @Override
    public final void sql(BindingSQLContext<byte[]> ctx) throws SQLException {
        if (ctx.render().paramType() == ParamType.INLINED) {
            ctx.render().visit(DSL.inline((Object)ctx.convert(this.converter()).value(), SQLDataType.BLOB));
        } else {
            ctx.render().sql(ctx.variable());
        }
    }

    @Override
    public final void register(BindingRegisterContext<byte[]> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), 2004);
    }

    @Override
    public final void set(BindingSetStatementContext<byte[]> ctx) throws SQLException {
        if (ctx.statement() instanceof R2DBC.R2DBCPreparedStatement) {
            ctx.statement().setBytes(ctx.index(), ctx.value());
        } else {
            Blob blob = BlobBinding.newBlob(ctx, ctx.value(), ctx.statement().getConnection());
            if (blob == null && ClobBinding.NO_SUPPORT_NULL_LOBS.contains((Object)ctx.dialect())) {
                ctx.statement().setNull(ctx.index(), 2004);
            } else {
                ctx.statement().setBlob(ctx.index(), blob);
            }
        }
    }

    @Override
    public final void set(BindingSetSQLOutputContext<byte[]> ctx) throws SQLException {
        ctx.output().writeBlob(BlobBinding.newBlob(ctx, ctx.value(), null));
    }

    @Override
    public final void get(BindingGetResultSetContext<byte[]> ctx) throws SQLException {
        if (ctx.resultSet() instanceof R2DBC.R2DBCResultSet) {
            ctx.value(ctx.resultSet().getBytes(ctx.index()));
        } else {
            Blob blob = ctx.resultSet().getBlob(ctx.index());
            try {
                ctx.value(blob == null ? null : blob.getBytes(1L, Tools.asInt(blob.length())));
            }
            finally {
                JDBCUtils.safeFree(blob);
            }
        }
    }

    @Override
    public final void get(BindingGetStatementContext<byte[]> ctx) throws SQLException {
        Blob blob = ctx.statement().getBlob(ctx.index());
        try {
            ctx.value(blob == null ? null : blob.getBytes(1L, Tools.asInt(blob.length())));
        }
        finally {
            JDBCUtils.safeFree(blob);
        }
    }

    @Override
    public final void get(BindingGetSQLInputContext<byte[]> ctx) throws SQLException {
        Blob blob = ctx.input().readBlob();
        try {
            ctx.value(blob == null ? null : blob.getBytes(1L, Tools.asInt(blob.length())));
        }
        finally {
            JDBCUtils.safeFree(blob);
        }
    }

    static final Blob newBlob(ResourceManagingScope scope, byte[] bytes, Connection connection) throws SQLException {
        if (bytes == null) {
            return null;
        }
        Blob blob = switch (scope.dialect()) {
            case SQLDialect.ORACLE10G, SQLDialect.ORACLE11G, SQLDialect.ORACLE12C -> BLOB.createTemporary((Connection)DefaultExecuteContext.localTargetConnection(scope), (boolean)false, (int)10);
            default -> (connection != null ? connection : DefaultExecuteContext.localConnection()).createBlob();
        };
        scope.autoFree(blob);
        blob.setBytes(1L, bytes);
        return blob;
    }
}

