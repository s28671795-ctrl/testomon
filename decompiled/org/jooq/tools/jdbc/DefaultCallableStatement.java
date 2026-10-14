/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import org.jooq.tools.jdbc.DefaultPreparedStatement;

public class DefaultCallableStatement
extends DefaultPreparedStatement
implements CallableStatement {
    public DefaultCallableStatement(CallableStatement delegate) {
        super(delegate);
    }

    protected DefaultCallableStatement(Statement delegate) {
        super(delegate);
    }

    @Override
    public CallableStatement getDelegate() throws SQLException {
        return this.getDelegateCallableStatement();
    }

    public CallableStatement getDelegateCallableStatement() throws SQLException {
        return (CallableStatement)this.getDelegateStatement();
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        this.getDelegateCallableStatement().registerOutParameter(parameterIndex, sqlType);
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        this.getDelegateCallableStatement().registerOutParameter(parameterIndex, sqlType, scale);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.getDelegateCallableStatement().wasNull();
    }

    @Override
    public String getString(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getString(parameterIndex);
    }

    @Override
    public boolean getBoolean(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getBoolean(parameterIndex);
    }

    @Override
    public byte getByte(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getByte(parameterIndex);
    }

    @Override
    public short getShort(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getShort(parameterIndex);
    }

    @Override
    public int getInt(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getInt(parameterIndex);
    }

    @Override
    public long getLong(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getLong(parameterIndex);
    }

    @Override
    public float getFloat(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getFloat(parameterIndex);
    }

    @Override
    public double getDouble(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getDouble(parameterIndex);
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return this.getDelegateCallableStatement().getBigDecimal(parameterIndex, scale);
    }

    @Override
    public byte[] getBytes(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getBytes(parameterIndex);
    }

    @Override
    public Date getDate(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getDate(parameterIndex);
    }

    @Override
    public Time getTime(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getTime(parameterIndex);
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getTimestamp(parameterIndex);
    }

    @Override
    public Object getObject(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getObject(parameterIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getBigDecimal(parameterIndex);
    }

    @Override
    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        return this.getDelegateCallableStatement().getObject(parameterIndex, map);
    }

    @Override
    public Ref getRef(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getRef(parameterIndex);
    }

    @Override
    public Blob getBlob(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getBlob(parameterIndex);
    }

    @Override
    public Clob getClob(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getClob(parameterIndex);
    }

    @Override
    public Array getArray(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getArray(parameterIndex);
    }

    @Override
    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        return this.getDelegateCallableStatement().getDate(parameterIndex, cal);
    }

    @Override
    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        return this.getDelegateCallableStatement().getTime(parameterIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        return this.getDelegateCallableStatement().getTimestamp(parameterIndex, cal);
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        this.getDelegateCallableStatement().registerOutParameter(parameterIndex, sqlType, typeName);
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        this.getDelegateCallableStatement().registerOutParameter(parameterName, sqlType);
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        this.getDelegateCallableStatement().registerOutParameter(parameterName, sqlType, scale);
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        this.getDelegateCallableStatement().registerOutParameter(parameterName, sqlType, typeName);
    }

    @Override
    public URL getURL(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getURL(parameterIndex);
    }

    @Override
    public void setURL(String parameterName, URL val) throws SQLException {
        this.getDelegateCallableStatement().setURL(parameterName, val);
    }

    @Override
    public void setNull(String parameterName, int sqlType) throws SQLException {
        this.getDelegateCallableStatement().setNull(parameterName, sqlType);
    }

    @Override
    public void setBoolean(String parameterName, boolean x) throws SQLException {
        this.getDelegateCallableStatement().setBoolean(parameterName, x);
    }

    @Override
    public void setByte(String parameterName, byte x) throws SQLException {
        this.getDelegateCallableStatement().setByte(parameterName, x);
    }

    @Override
    public void setShort(String parameterName, short x) throws SQLException {
        this.getDelegateCallableStatement().setShort(parameterName, x);
    }

    @Override
    public void setInt(String parameterName, int x) throws SQLException {
        this.getDelegateCallableStatement().setInt(parameterName, x);
    }

    @Override
    public void setLong(String parameterName, long x) throws SQLException {
        this.getDelegateCallableStatement().setLong(parameterName, x);
    }

    @Override
    public void setFloat(String parameterName, float x) throws SQLException {
        this.getDelegateCallableStatement().setFloat(parameterName, x);
    }

    @Override
    public void setDouble(String parameterName, double x) throws SQLException {
        this.getDelegateCallableStatement().setDouble(parameterName, x);
    }

    @Override
    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        this.getDelegateCallableStatement().setBigDecimal(parameterName, x);
    }

    @Override
    public void setString(String parameterName, String x) throws SQLException {
        this.getDelegateCallableStatement().setString(parameterName, x);
    }

    @Override
    public void setBytes(String parameterName, byte[] x) throws SQLException {
        this.getDelegateCallableStatement().setBytes(parameterName, x);
    }

    @Override
    public void setDate(String parameterName, Date x) throws SQLException {
        this.getDelegateCallableStatement().setDate(parameterName, x);
    }

    @Override
    public void setTime(String parameterName, Time x) throws SQLException {
        this.getDelegateCallableStatement().setTime(parameterName, x);
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        this.getDelegateCallableStatement().setTimestamp(parameterName, x);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        this.getDelegateCallableStatement().setAsciiStream(parameterName, x, length);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        this.getDelegateCallableStatement().setBinaryStream(parameterName, x, length);
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        this.getDelegateCallableStatement().setObject(parameterName, x, targetSqlType, scale);
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        this.getDelegateCallableStatement().setObject(parameterName, x, targetSqlType);
    }

    @Override
    public void setObject(String parameterName, Object x) throws SQLException {
        this.getDelegateCallableStatement().setObject(parameterName, x);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        this.getDelegateCallableStatement().setCharacterStream(parameterName, reader, length);
    }

    @Override
    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        this.getDelegateCallableStatement().setDate(parameterName, x, cal);
    }

    @Override
    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        this.getDelegateCallableStatement().setTime(parameterName, x, cal);
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        this.getDelegateCallableStatement().setTimestamp(parameterName, x, cal);
    }

    @Override
    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        this.getDelegateCallableStatement().setNull(parameterName, sqlType, typeName);
    }

    @Override
    public String getString(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getString(parameterName);
    }

    @Override
    public boolean getBoolean(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getBoolean(parameterName);
    }

    @Override
    public byte getByte(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getByte(parameterName);
    }

    @Override
    public short getShort(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getShort(parameterName);
    }

    @Override
    public int getInt(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getInt(parameterName);
    }

    @Override
    public long getLong(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getLong(parameterName);
    }

    @Override
    public float getFloat(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getFloat(parameterName);
    }

    @Override
    public double getDouble(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getDouble(parameterName);
    }

    @Override
    public byte[] getBytes(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getBytes(parameterName);
    }

    @Override
    public Date getDate(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getDate(parameterName);
    }

    @Override
    public Time getTime(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getTime(parameterName);
    }

    @Override
    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getTimestamp(parameterName);
    }

    @Override
    public Object getObject(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getObject(parameterName);
    }

    @Override
    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getBigDecimal(parameterName);
    }

    @Override
    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return this.getDelegateCallableStatement().getObject(parameterName, map);
    }

    @Override
    public Ref getRef(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getRef(parameterName);
    }

    @Override
    public Blob getBlob(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getBlob(parameterName);
    }

    @Override
    public Clob getClob(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getClob(parameterName);
    }

    @Override
    public Array getArray(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getArray(parameterName);
    }

    @Override
    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return this.getDelegateCallableStatement().getDate(parameterName, cal);
    }

    @Override
    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return this.getDelegateCallableStatement().getTime(parameterName, cal);
    }

    @Override
    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return this.getDelegateCallableStatement().getTimestamp(parameterName, cal);
    }

    @Override
    public URL getURL(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getURL(parameterName);
    }

    @Override
    public RowId getRowId(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getRowId(parameterIndex);
    }

    @Override
    public RowId getRowId(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getRowId(parameterName);
    }

    @Override
    public void setRowId(String parameterName, RowId x) throws SQLException {
        this.getDelegateCallableStatement().setRowId(parameterName, x);
    }

    @Override
    public void setNString(String parameterName, String value) throws SQLException {
        this.getDelegateCallableStatement().setNString(parameterName, value);
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        this.getDelegateCallableStatement().setNCharacterStream(parameterName, value, length);
    }

    @Override
    public void setNClob(String parameterName, NClob value) throws SQLException {
        this.getDelegateCallableStatement().setNClob(parameterName, value);
    }

    @Override
    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        this.getDelegateCallableStatement().setClob(parameterName, reader, length);
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream2, long length) throws SQLException {
        this.getDelegateCallableStatement().setBlob(parameterName, inputStream2, length);
    }

    @Override
    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        this.getDelegateCallableStatement().setNClob(parameterName, reader, length);
    }

    @Override
    public NClob getNClob(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getNClob(parameterIndex);
    }

    @Override
    public NClob getNClob(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getNClob(parameterName);
    }

    @Override
    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        this.getDelegateCallableStatement().setSQLXML(parameterName, xmlObject);
    }

    @Override
    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getSQLXML(parameterIndex);
    }

    @Override
    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getSQLXML(parameterName);
    }

    @Override
    public String getNString(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getNString(parameterIndex);
    }

    @Override
    public String getNString(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getNString(parameterName);
    }

    @Override
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getNCharacterStream(parameterIndex);
    }

    @Override
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getNCharacterStream(parameterName);
    }

    @Override
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return this.getDelegateCallableStatement().getCharacterStream(parameterIndex);
    }

    @Override
    public Reader getCharacterStream(String parameterName) throws SQLException {
        return this.getDelegateCallableStatement().getCharacterStream(parameterName);
    }

    @Override
    public void setBlob(String parameterName, Blob x) throws SQLException {
        this.getDelegateCallableStatement().setBlob(parameterName, x);
    }

    @Override
    public void setClob(String parameterName, Clob x) throws SQLException {
        this.getDelegateCallableStatement().setClob(parameterName, x);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        this.getDelegateCallableStatement().setAsciiStream(parameterName, x, length);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        this.getDelegateCallableStatement().setBinaryStream(parameterName, x, length);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        this.getDelegateCallableStatement().setCharacterStream(parameterName, reader, length);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        this.getDelegateCallableStatement().setAsciiStream(parameterName, x);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        this.getDelegateCallableStatement().setBinaryStream(parameterName, x);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        this.getDelegateCallableStatement().setCharacterStream(parameterName, reader);
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        this.getDelegateCallableStatement().setNCharacterStream(parameterName, value);
    }

    @Override
    public void setClob(String parameterName, Reader reader) throws SQLException {
        this.getDelegateCallableStatement().setClob(parameterName, reader);
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream2) throws SQLException {
        this.getDelegateCallableStatement().setBlob(parameterName, inputStream2);
    }

    @Override
    public void setNClob(String parameterName, Reader reader) throws SQLException {
        this.getDelegateCallableStatement().setNClob(parameterName, reader);
    }

    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        return this.getDelegate().getObject(parameterIndex, type);
    }

    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        return this.getDelegate().getObject(parameterName, type);
    }

    @Override
    public void setObject(String parameterName, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        this.getDelegate().setObject(parameterName, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setObject(String parameterName, Object x, SQLType targetSqlType) throws SQLException {
        this.getDelegate().setObject(parameterName, x, targetSqlType);
    }

    @Override
    public void registerOutParameter(int parameterIndex, SQLType sqlType) throws SQLException {
        this.getDelegate().registerOutParameter(parameterIndex, sqlType);
    }

    @Override
    public void registerOutParameter(int parameterIndex, SQLType sqlType, int scale) throws SQLException {
        this.getDelegate().registerOutParameter(parameterIndex, sqlType, scale);
    }

    @Override
    public void registerOutParameter(int parameterIndex, SQLType sqlType, String typeName) throws SQLException {
        this.getDelegate().registerOutParameter(parameterIndex, sqlType, typeName);
    }

    @Override
    public void registerOutParameter(String parameterName, SQLType sqlType) throws SQLException {
        this.getDelegate().registerOutParameter(parameterName, sqlType);
    }

    @Override
    public void registerOutParameter(String parameterName, SQLType sqlType, int scale) throws SQLException {
        this.getDelegate().registerOutParameter(parameterName, sqlType, scale);
    }

    @Override
    public void registerOutParameter(String parameterName, SQLType sqlType, String typeName) throws SQLException {
        this.getDelegate().registerOutParameter(parameterName, sqlType, typeName);
    }
}

