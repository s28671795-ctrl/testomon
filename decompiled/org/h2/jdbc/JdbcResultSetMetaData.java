/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.h2.jdbc.JdbcPreparedStatement;
import org.h2.jdbc.JdbcResultSet;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceObject;
import org.h2.result.ResultInterface;
import org.h2.util.MathUtils;
import org.h2.value.DataType;
import org.h2.value.ValueToObjectConverter;

public final class JdbcResultSetMetaData
extends TraceObject
implements ResultSetMetaData {
    private final String catalog;
    private final JdbcResultSet rs;
    private final JdbcPreparedStatement prep;
    private final ResultInterface result;
    private final int columnCount;

    JdbcResultSetMetaData(JdbcResultSet jdbcResultSet, JdbcPreparedStatement jdbcPreparedStatement, ResultInterface resultInterface, String string, Trace trace, int n) {
        this.setTrace(trace, 5, n);
        this.catalog = string;
        this.rs = jdbcResultSet;
        this.prep = jdbcPreparedStatement;
        this.result = resultInterface;
        this.columnCount = resultInterface.getVisibleColumnCount();
    }

    @Override
    public int getColumnCount() throws SQLException {
        try {
            this.debugCodeCall("getColumnCount");
            this.checkClosed();
            return this.columnCount;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getColumnLabel(int n) throws SQLException {
        try {
            return this.result.getAlias(this.getColumn("getColumnLabel", n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getColumnName(int n) throws SQLException {
        try {
            return this.result.getColumnName(this.getColumn("getColumnName", n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getColumnType(int n) throws SQLException {
        try {
            return DataType.convertTypeToSQLType(this.result.getColumnType(this.getColumn("getColumnType", n)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getColumnTypeName(int n) throws SQLException {
        try {
            return this.result.getColumnType(this.getColumn("getColumnTypeName", n)).getDeclaredTypeName();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getSchemaName(int n) throws SQLException {
        try {
            String string = this.result.getSchemaName(this.getColumn("getSchemaName", n));
            return string == null ? "" : string;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getTableName(int n) throws SQLException {
        try {
            String string = this.result.getTableName(this.getColumn("getTableName", n));
            return string == null ? "" : string;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getCatalogName(int n) throws SQLException {
        try {
            this.getColumn("getCatalogName", n);
            return this.catalog == null ? "" : this.catalog;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isAutoIncrement(int n) throws SQLException {
        try {
            return this.result.isIdentity(this.getColumn("isAutoIncrement", n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isCaseSensitive(int n) throws SQLException {
        try {
            this.getColumn("isCaseSensitive", n);
            return true;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isSearchable(int n) throws SQLException {
        try {
            this.getColumn("isSearchable", n);
            return true;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isCurrency(int n) throws SQLException {
        try {
            this.getColumn("isCurrency", n);
            return false;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int isNullable(int n) throws SQLException {
        try {
            return this.result.getNullable(this.getColumn("isNullable", n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isSigned(int n) throws SQLException {
        try {
            return DataType.isNumericType(this.result.getColumnType(this.getColumn("isSigned", n)).getValueType());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isReadOnly(int n) throws SQLException {
        try {
            this.getColumn("isReadOnly", n);
            return false;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isWritable(int n) throws SQLException {
        try {
            this.getColumn("isWritable", n);
            return true;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isDefinitelyWritable(int n) throws SQLException {
        try {
            this.getColumn("isDefinitelyWritable", n);
            return false;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getColumnClassName(int n) throws SQLException {
        try {
            int n2 = this.result.getColumnType(this.getColumn("getColumnClassName", n)).getValueType();
            return ValueToObjectConverter.getDefaultClass(n2, true).getName();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getPrecision(int n) throws SQLException {
        try {
            return MathUtils.convertLongToInt(this.result.getColumnType(this.getColumn("getPrecision", n)).getPrecision());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getScale(int n) throws SQLException {
        try {
            return this.result.getColumnType(this.getColumn("getScale", n)).getScale();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getColumnDisplaySize(int n) throws SQLException {
        try {
            return this.result.getColumnType(this.getColumn("getColumnDisplaySize", n)).getDisplaySize();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void checkClosed() {
        if (this.rs != null) {
            this.rs.checkClosed();
        }
        if (this.prep != null) {
            this.prep.checkClosed();
        }
    }

    private int getColumn(String string, int n) {
        this.debugCodeCall(string, n);
        this.checkClosed();
        if (n < 1 || n > this.columnCount) {
            throw DbException.getInvalidValueException("columnIndex", n);
        }
        return n - 1;
    }

    @Override
    public <T> T unwrap(Class<T> clazz) throws SQLException {
        try {
            if (this.isWrapperFor(clazz)) {
                return (T)this;
            }
            throw DbException.getInvalidValueException("iface", clazz);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> clazz) throws SQLException {
        return clazz != null && clazz.isAssignableFrom(this.getClass());
    }

    public String toString() {
        return this.getTraceObjectName() + ": columns=" + this.columnCount;
    }
}

