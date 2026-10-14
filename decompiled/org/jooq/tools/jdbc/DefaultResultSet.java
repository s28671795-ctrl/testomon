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
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.function.Supplier;
import org.jooq.tools.jdbc.JDBC41ResultSet;

public class DefaultResultSet
extends JDBC41ResultSet
implements ResultSet {
    private final ResultSet delegate;
    private final Statement creator;
    private final Supplier<? extends SQLException> errorIfUnsupported;

    public DefaultResultSet(ResultSet delegate) {
        this(delegate, null, null);
    }

    public DefaultResultSet(ResultSet delegate, Statement creator) {
        this(delegate, creator, null);
    }

    public DefaultResultSet(ResultSet delegate, Statement creator, Supplier<? extends SQLException> errorIfUnsupported) {
        this.delegate = delegate;
        this.creator = creator;
        this.errorIfUnsupported = errorIfUnsupported;
    }

    public ResultSet getDelegate() throws SQLException {
        if (this.delegate != null || this.errorIfUnsupported == null) {
            return this.delegate;
        }
        throw this.errorIfUnsupported.get();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.getDelegate().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.getDelegate().isWrapperFor(iface);
    }

    @Override
    public boolean next() throws SQLException {
        return this.getDelegate().next();
    }

    @Override
    public void close() throws SQLException {
        this.getDelegate().close();
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.getDelegate().wasNull();
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return this.getDelegate().getString(columnIndex);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return this.getDelegate().getBoolean(columnIndex);
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        return this.getDelegate().getByte(columnIndex);
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return this.getDelegate().getShort(columnIndex);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return this.getDelegate().getInt(columnIndex);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return this.getDelegate().getLong(columnIndex);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return this.getDelegate().getFloat(columnIndex);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return this.getDelegate().getDouble(columnIndex);
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return this.getDelegate().getBigDecimal(columnIndex, scale);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return this.getDelegate().getBytes(columnIndex);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return this.getDelegate().getDate(columnIndex);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return this.getDelegate().getTime(columnIndex);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return this.getDelegate().getTimestamp(columnIndex);
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        return this.getDelegate().getAsciiStream(columnIndex);
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        return this.getDelegate().getUnicodeStream(columnIndex);
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        return this.getDelegate().getBinaryStream(columnIndex);
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        return this.getDelegate().getString(columnLabel);
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        return this.getDelegate().getBoolean(columnLabel);
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        return this.getDelegate().getByte(columnLabel);
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        return this.getDelegate().getShort(columnLabel);
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        return this.getDelegate().getInt(columnLabel);
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        return this.getDelegate().getLong(columnLabel);
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        return this.getDelegate().getFloat(columnLabel);
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        return this.getDelegate().getDouble(columnLabel);
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return this.getDelegate().getBigDecimal(columnLabel, scale);
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        return this.getDelegate().getBytes(columnLabel);
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        return this.getDelegate().getDate(columnLabel);
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        return this.getDelegate().getTime(columnLabel);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return this.getDelegate().getTimestamp(columnLabel);
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        return this.getDelegate().getAsciiStream(columnLabel);
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        return this.getDelegate().getUnicodeStream(columnLabel);
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        return this.getDelegate().getBinaryStream(columnLabel);
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return this.getDelegate().getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        this.getDelegate().clearWarnings();
    }

    @Override
    public String getCursorName() throws SQLException {
        return this.getDelegate().getCursorName();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.getDelegate().getMetaData();
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return this.getDelegate().getObject(columnIndex);
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        return this.getDelegate().getObject(columnLabel);
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        return this.getDelegate().findColumn(columnLabel);
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        return this.getDelegate().getCharacterStream(columnIndex);
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        return this.getDelegate().getCharacterStream(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return this.getDelegate().getBigDecimal(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return this.getDelegate().getBigDecimal(columnLabel);
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        return this.getDelegate().isBeforeFirst();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return this.getDelegate().isAfterLast();
    }

    @Override
    public boolean isFirst() throws SQLException {
        return this.getDelegate().isFirst();
    }

    @Override
    public boolean isLast() throws SQLException {
        return this.getDelegate().isLast();
    }

    @Override
    public void beforeFirst() throws SQLException {
        this.getDelegate().beforeFirst();
    }

    @Override
    public void afterLast() throws SQLException {
        this.getDelegate().afterLast();
    }

    @Override
    public boolean first() throws SQLException {
        return this.getDelegate().first();
    }

    @Override
    public boolean last() throws SQLException {
        return this.getDelegate().last();
    }

    @Override
    public int getRow() throws SQLException {
        return this.getDelegate().getRow();
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        return this.getDelegate().absolute(row);
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        return this.getDelegate().relative(rows);
    }

    @Override
    public boolean previous() throws SQLException {
        return this.getDelegate().previous();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        this.getDelegate().setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return this.getDelegate().getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        this.getDelegate().setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return this.getDelegate().getFetchSize();
    }

    @Override
    public int getType() throws SQLException {
        return this.getDelegate().getType();
    }

    @Override
    public int getConcurrency() throws SQLException {
        return this.getDelegate().getConcurrency();
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return this.getDelegate().rowUpdated();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return this.getDelegate().rowInserted();
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return this.getDelegate().rowDeleted();
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        this.getDelegate().updateNull(columnIndex);
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        this.getDelegate().updateBoolean(columnIndex, x);
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        this.getDelegate().updateByte(columnIndex, x);
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        this.getDelegate().updateShort(columnIndex, x);
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        this.getDelegate().updateInt(columnIndex, x);
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        this.getDelegate().updateLong(columnIndex, x);
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        this.getDelegate().updateFloat(columnIndex, x);
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        this.getDelegate().updateDouble(columnIndex, x);
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        this.getDelegate().updateBigDecimal(columnIndex, x);
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        this.getDelegate().updateString(columnIndex, x);
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        this.getDelegate().updateBytes(columnIndex, x);
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        this.getDelegate().updateDate(columnIndex, x);
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        this.getDelegate().updateTime(columnIndex, x);
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        this.getDelegate().updateTimestamp(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        this.getDelegate().updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        this.getDelegate().updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        this.getDelegate().updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        this.getDelegate().updateObject(columnIndex, x, scaleOrLength);
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        this.getDelegate().updateObject(columnIndex, x);
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        this.getDelegate().updateNull(columnLabel);
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        this.getDelegate().updateBoolean(columnLabel, x);
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        this.getDelegate().updateByte(columnLabel, x);
    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        this.getDelegate().updateShort(columnLabel, x);
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        this.getDelegate().updateInt(columnLabel, x);
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        this.getDelegate().updateLong(columnLabel, x);
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        this.getDelegate().updateFloat(columnLabel, x);
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        this.getDelegate().updateDouble(columnLabel, x);
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        this.getDelegate().updateBigDecimal(columnLabel, x);
    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        this.getDelegate().updateString(columnLabel, x);
    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        this.getDelegate().updateBytes(columnLabel, x);
    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        this.getDelegate().updateDate(columnLabel, x);
    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        this.getDelegate().updateTime(columnLabel, x);
    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        this.getDelegate().updateTimestamp(columnLabel, x);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        this.getDelegate().updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        this.getDelegate().updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        this.getDelegate().updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        this.getDelegate().updateObject(columnLabel, x, scaleOrLength);
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        this.getDelegate().updateObject(columnLabel, x);
    }

    @Override
    public void insertRow() throws SQLException {
        this.getDelegate().insertRow();
    }

    @Override
    public void updateRow() throws SQLException {
        this.getDelegate().updateRow();
    }

    @Override
    public void deleteRow() throws SQLException {
        this.getDelegate().deleteRow();
    }

    @Override
    public void refreshRow() throws SQLException {
        this.getDelegate().refreshRow();
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        this.getDelegate().cancelRowUpdates();
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        this.getDelegate().moveToInsertRow();
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        this.getDelegate().moveToCurrentRow();
    }

    @Override
    public Statement getStatement() throws SQLException {
        return this.creator != null ? this.creator : this.getDelegate().getStatement();
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        return this.getDelegate().getObject(columnIndex, map);
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        return this.getDelegate().getRef(columnIndex);
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        return this.getDelegate().getBlob(columnIndex);
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        return this.getDelegate().getClob(columnIndex);
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        return this.getDelegate().getArray(columnIndex);
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        return this.getDelegate().getObject(columnLabel, map);
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        return this.getDelegate().getRef(columnLabel);
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        return this.getDelegate().getBlob(columnLabel);
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        return this.getDelegate().getClob(columnLabel);
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        return this.getDelegate().getArray(columnLabel);
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        return this.getDelegate().getDate(columnIndex, cal);
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        return this.getDelegate().getDate(columnLabel, cal);
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return this.getDelegate().getTime(columnIndex, cal);
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        return this.getDelegate().getTime(columnLabel, cal);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return this.getDelegate().getTimestamp(columnIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        return this.getDelegate().getTimestamp(columnLabel, cal);
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        return this.getDelegate().getURL(columnIndex);
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        return this.getDelegate().getURL(columnLabel);
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        this.getDelegate().updateRef(columnIndex, x);
    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        this.getDelegate().updateRef(columnLabel, x);
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        this.getDelegate().updateBlob(columnIndex, x);
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        this.getDelegate().updateBlob(columnLabel, x);
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        this.getDelegate().updateClob(columnIndex, x);
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        this.getDelegate().updateClob(columnLabel, x);
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        this.getDelegate().updateArray(columnIndex, x);
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        this.getDelegate().updateArray(columnLabel, x);
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        return this.getDelegate().getRowId(columnIndex);
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        return this.getDelegate().getRowId(columnLabel);
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        this.getDelegate().updateRowId(columnIndex, x);
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        this.getDelegate().updateRowId(columnLabel, x);
    }

    @Override
    public int getHoldability() throws SQLException {
        return this.getDelegate().getHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.getDelegate().isClosed();
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        this.getDelegate().updateNString(columnIndex, nString);
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        this.getDelegate().updateNString(columnLabel, nString);
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        this.getDelegate().updateNClob(columnIndex, nClob);
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        this.getDelegate().updateNClob(columnLabel, nClob);
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        return this.getDelegate().getNClob(columnIndex);
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        return this.getDelegate().getNClob(columnLabel);
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        return this.getDelegate().getSQLXML(columnIndex);
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        return this.getDelegate().getSQLXML(columnLabel);
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        this.getDelegate().updateSQLXML(columnIndex, xmlObject);
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        this.getDelegate().updateSQLXML(columnLabel, xmlObject);
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        return this.getDelegate().getNString(columnIndex);
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        return this.getDelegate().getNString(columnLabel);
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return this.getDelegate().getNCharacterStream(columnIndex);
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return this.getDelegate().getNCharacterStream(columnLabel);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this.getDelegate().updateNCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        this.getDelegate().updateNCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        this.getDelegate().updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        this.getDelegate().updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this.getDelegate().updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        this.getDelegate().updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        this.getDelegate().updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        this.getDelegate().updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream2, long length) throws SQLException {
        this.getDelegate().updateBlob(columnIndex, inputStream2, length);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream2, long length) throws SQLException {
        this.getDelegate().updateBlob(columnLabel, inputStream2, length);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        this.getDelegate().updateClob(columnIndex, reader, length);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        this.getDelegate().updateClob(columnLabel, reader, length);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        this.getDelegate().updateNClob(columnIndex, reader, length);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        this.getDelegate().updateNClob(columnLabel, reader, length);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        this.getDelegate().updateNCharacterStream(columnIndex, x);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        this.getDelegate().updateNCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        this.getDelegate().updateAsciiStream(columnIndex, x);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        this.getDelegate().updateBinaryStream(columnIndex, x);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        this.getDelegate().updateCharacterStream(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        this.getDelegate().updateAsciiStream(columnLabel, x);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        this.getDelegate().updateBinaryStream(columnLabel, x);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        this.getDelegate().updateCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream2) throws SQLException {
        this.getDelegate().updateBlob(columnIndex, inputStream2);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream2) throws SQLException {
        this.getDelegate().updateBlob(columnLabel, inputStream2);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        this.getDelegate().updateClob(columnIndex, reader);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        this.getDelegate().updateClob(columnLabel, reader);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        this.getDelegate().updateNClob(columnIndex, reader);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        this.getDelegate().updateNClob(columnLabel, reader);
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return this.getDelegate().getObject(columnIndex, type);
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return this.getDelegate().getObject(columnLabel, type);
    }

    @Override
    public void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        this.getDelegate().updateObject(columnIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        this.getDelegate().updateObject(columnLabel, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
        this.getDelegate().updateObject(columnIndex, x, targetSqlType);
    }

    @Override
    public void updateObject(String columnLabel, Object x, SQLType targetSqlType) throws SQLException {
        this.getDelegate().updateObject(columnLabel, x, targetSqlType);
    }
}

