/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.sql.ParameterMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import org.h2.command.CommandInterface;
import org.h2.expression.ParameterInterface;
import org.h2.jdbc.JdbcPreparedStatement;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.message.TraceObject;
import org.h2.util.MathUtils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.ValueToObjectConverter;

public final class JdbcParameterMetaData
extends TraceObject
implements ParameterMetaData {
    private final JdbcPreparedStatement prep;
    private final int paramCount;
    private final ArrayList<? extends ParameterInterface> parameters;

    JdbcParameterMetaData(Trace trace, JdbcPreparedStatement jdbcPreparedStatement, CommandInterface commandInterface, int n) {
        this.setTrace(trace, 11, n);
        this.prep = jdbcPreparedStatement;
        this.parameters = commandInterface.getParameters();
        this.paramCount = this.parameters.size();
    }

    @Override
    public int getParameterCount() throws SQLException {
        try {
            this.debugCodeCall("getParameterCount");
            this.checkClosed();
            return this.paramCount;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getParameterMode(int n) throws SQLException {
        try {
            this.debugCodeCall("getParameterMode", n);
            this.getParameter(n);
            return 1;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getParameterType(int n) throws SQLException {
        try {
            this.debugCodeCall("getParameterType", n);
            TypeInfo typeInfo = this.getParameter(n).getType();
            if (typeInfo.getValueType() == -1) {
                typeInfo = TypeInfo.TYPE_VARCHAR;
            }
            return DataType.convertTypeToSQLType(typeInfo);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getPrecision(int n) throws SQLException {
        try {
            this.debugCodeCall("getPrecision", n);
            TypeInfo typeInfo = this.getParameter(n).getType();
            return typeInfo.getValueType() == -1 ? 0 : MathUtils.convertLongToInt(typeInfo.getPrecision());
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getScale(int n) throws SQLException {
        try {
            this.debugCodeCall("getScale", n);
            TypeInfo typeInfo = this.getParameter(n).getType();
            return typeInfo.getValueType() == -1 ? 0 : typeInfo.getScale();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int isNullable(int n) throws SQLException {
        try {
            this.debugCodeCall("isNullable", n);
            return this.getParameter(n).getNullable();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isSigned(int n) throws SQLException {
        try {
            this.debugCodeCall("isSigned", n);
            this.getParameter(n);
            return true;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getParameterClassName(int n) throws SQLException {
        try {
            this.debugCodeCall("getParameterClassName", n);
            int n2 = this.getParameter(n).getType().getValueType();
            if (n2 == -1) {
                n2 = 2;
            }
            return ValueToObjectConverter.getDefaultClass(n2, true).getName();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getParameterTypeName(int n) throws SQLException {
        try {
            this.debugCodeCall("getParameterTypeName", n);
            TypeInfo typeInfo = this.getParameter(n).getType();
            if (typeInfo.getValueType() == -1) {
                typeInfo = TypeInfo.TYPE_VARCHAR;
            }
            return typeInfo.getDeclaredTypeName();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private ParameterInterface getParameter(int n) {
        this.checkClosed();
        if (n < 1 || n > this.paramCount) {
            throw DbException.getInvalidValueException("param", n);
        }
        return this.parameters.get(n - 1);
    }

    private void checkClosed() {
        this.prep.checkClosed();
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
        return this.getTraceObjectName() + ": parameterCount=" + this.paramCount;
    }
}

