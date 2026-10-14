/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcResultSet;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.result.ResultInterface;
import org.h2.result.SimpleResult;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueToObjectConverter;

public final class JdbcArray
extends TraceObject
implements Array {
    private ValueArray value;
    private final JdbcConnection conn;

    public JdbcArray(JdbcConnection jdbcConnection, Value value, int n) {
        this.setTrace(jdbcConnection.getSession().getTrace(), 16, n);
        this.conn = jdbcConnection;
        this.value = value.convertToAnyArray(jdbcConnection);
    }

    @Override
    public Object getArray() throws SQLException {
        try {
            this.debugCodeCall("getArray");
            this.checkClosed();
            return this.get();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Object getArray(Map<String, Class<?>> map) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getArray(" + JdbcArray.quoteMap(map) + ')');
            }
            JdbcConnection.checkMap(map);
            this.checkClosed();
            return this.get();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Object getArray(long l, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getArray(" + l + ", " + n + ')');
            }
            this.checkClosed();
            return this.get(l, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Object getArray(long l, int n, Map<String, Class<?>> map) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getArray(" + l + ", " + n + ", " + JdbcArray.quoteMap(map) + ')');
            }
            this.checkClosed();
            JdbcConnection.checkMap(map);
            return this.get(l, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getBaseType() throws SQLException {
        try {
            this.debugCodeCall("getBaseType");
            this.checkClosed();
            return DataType.convertTypeToSQLType(this.value.getComponentType());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getBaseTypeName() throws SQLException {
        try {
            this.debugCodeCall("getBaseTypeName");
            this.checkClosed();
            return this.value.getComponentType().getDeclaredTypeName();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        try {
            this.debugCodeCall("getResultSet");
            this.checkClosed();
            return this.getResultSetImpl(1L, Integer.MAX_VALUE);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getResultSet(" + JdbcArray.quoteMap(map) + ')');
            }
            this.checkClosed();
            JdbcConnection.checkMap(map);
            return this.getResultSetImpl(1L, Integer.MAX_VALUE);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getResultSet(long l, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getResultSet(" + l + ", " + n + ')');
            }
            this.checkClosed();
            return this.getResultSetImpl(l, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet getResultSet(long l, int n, Map<String, Class<?>> map) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getResultSet(" + l + ", " + n + ", " + JdbcArray.quoteMap(map) + ')');
            }
            this.checkClosed();
            JdbcConnection.checkMap(map);
            return this.getResultSetImpl(l, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void free() {
        this.debugCodeCall("free");
        this.value = null;
    }

    private ResultSet getResultSetImpl(long l, int n) {
        int n2 = JdbcArray.getNextId(4);
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("INDEX", TypeInfo.TYPE_BIGINT);
        simpleResult.addColumn("VALUE", this.value.getComponentType());
        Value[] valueArray = this.value.getList();
        n = JdbcArray.checkRange(l, n, valueArray.length);
        int n3 = (int)l;
        while ((long)n3 < l + (long)n) {
            simpleResult.addRow(ValueBigint.get(n3), valueArray[n3 - 1]);
            ++n3;
        }
        return new JdbcResultSet(this.conn, null, null, (ResultInterface)simpleResult, n2, true, false, false);
    }

    private void checkClosed() {
        this.conn.checkClosed();
        if (this.value == null) {
            throw DbException.get(90007);
        }
    }

    private Object get() {
        return ValueToObjectConverter.valueToDefaultArray(this.value, this.conn, true);
    }

    private Object get(long l, int n) {
        Value[] valueArray = this.value.getList();
        n = JdbcArray.checkRange(l, n, valueArray.length);
        Object[] objectArray = new Object[n];
        int n2 = 0;
        int n3 = (int)l - 1;
        while (n2 < n) {
            objectArray[n2] = ValueToObjectConverter.valueToDefaultObject(valueArray[n3], this.conn, true);
            ++n2;
            ++n3;
        }
        return objectArray;
    }

    private static int checkRange(long l, int n, int n2) {
        if (l < 1L || l != 1L && l > (long)n2) {
            throw DbException.getInvalidValueException("index (1.." + n2 + ')', l);
        }
        int n3 = n2 - (int)l + 1;
        if (n < 0) {
            throw DbException.getInvalidValueException("count (0.." + n3 + ')', n);
        }
        return Math.min(n3, n);
    }

    public String toString() {
        return this.value == null ? "null" : this.getTraceObjectName() + ": " + this.value.getTraceSQL();
    }
}

