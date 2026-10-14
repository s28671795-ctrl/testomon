/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.function.table.TableFunction;
import org.h2.jdbc.JdbcConnection;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;
import org.h2.result.SimpleResult;
import org.h2.util.JdbcUtils;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.ValueVarchar;

public final class LinkSchemaFunction
extends TableFunction {
    public LinkSchemaFunction() {
        super(new Expression[6]);
    }

    @Override
    public ResultInterface getValue(SessionLocal sessionLocal) {
        sessionLocal.getUser().checkAdmin();
        String string = this.getValue(sessionLocal, 0);
        String string2 = this.getValue(sessionLocal, 1);
        String string3 = this.getValue(sessionLocal, 2);
        String string4 = this.getValue(sessionLocal, 3);
        String string5 = this.getValue(sessionLocal, 4);
        String string6 = this.getValue(sessionLocal, 5);
        if (string == null || string2 == null || string3 == null || string4 == null || string5 == null || string6 == null) {
            return this.getValueTemplate(sessionLocal);
        }
        JdbcConnection jdbcConnection = sessionLocal.createConnection(false);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_NAME", TypeInfo.TYPE_VARCHAR);
        try {
            connection = JdbcUtils.getConnection(string2, string3, string4, string5);
            statement = jdbcConnection.createStatement();
            statement.execute(StringUtils.quoteIdentifier(new StringBuilder("CREATE SCHEMA IF NOT EXISTS "), string).toString());
            resultSet = string3.startsWith("jdbc:postgresql:") ? connection.getMetaData().getTables(null, string6, null, new String[]{"TABLE", "LINKED TABLE", "VIEW", "EXTERNAL"}) : connection.getMetaData().getTables(null, string6, null, null);
            while (resultSet.next()) {
                String string7 = resultSet.getString("TABLE_NAME");
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("DROP TABLE IF EXISTS ");
                StringUtils.quoteIdentifier(stringBuilder, string).append('.');
                StringUtils.quoteIdentifier(stringBuilder, string7);
                statement.execute(stringBuilder.toString());
                stringBuilder.setLength(0);
                stringBuilder.append("CREATE LINKED TABLE ");
                StringUtils.quoteIdentifier(stringBuilder, string).append('.');
                StringUtils.quoteIdentifier(stringBuilder, string7).append('(');
                StringUtils.quoteStringSQL(stringBuilder, string2).append(", ");
                StringUtils.quoteStringSQL(stringBuilder, string3).append(", ");
                StringUtils.quoteStringSQL(stringBuilder, string4).append(", ");
                StringUtils.quoteStringSQL(stringBuilder, string5).append(", ");
                StringUtils.quoteStringSQL(stringBuilder, string6).append(", ");
                StringUtils.quoteStringSQL(stringBuilder, string7).append(')');
                statement.execute(stringBuilder.toString());
                simpleResult.addRow(ValueVarchar.get(string7, sessionLocal));
            }
        }
        catch (SQLException sQLException) {
            try {
                simpleResult.close();
                throw DbException.convert(sQLException);
            }
            catch (Throwable throwable) {
                JdbcUtils.closeSilently(resultSet);
                JdbcUtils.closeSilently(connection);
                JdbcUtils.closeSilently(statement);
                throw throwable;
            }
        }
        JdbcUtils.closeSilently(resultSet);
        JdbcUtils.closeSilently(connection);
        JdbcUtils.closeSilently(statement);
        return simpleResult;
    }

    private String getValue(SessionLocal sessionLocal, int n) {
        return this.args[n].getValue(sessionLocal).getString();
    }

    @Override
    public void optimize(SessionLocal sessionLocal) {
        super.optimize(sessionLocal);
        int n = this.args.length;
        if (n != 6) {
            throw DbException.get(7001, this.getName(), "6");
        }
    }

    @Override
    public ResultInterface getValueTemplate(SessionLocal sessionLocal) {
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_NAME", TypeInfo.TYPE_VARCHAR);
        return simpleResult;
    }

    @Override
    public String getName() {
        return "LINK_SCHEMA";
    }

    @Override
    public boolean isDeterministic() {
        return false;
    }
}

