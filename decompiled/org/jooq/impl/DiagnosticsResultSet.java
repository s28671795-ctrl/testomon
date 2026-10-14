/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

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
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Map;
import java.util.function.Predicate;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultDiagnosticsContext;
import org.jooq.impl.DiagnosticsConnection;
import org.jooq.tools.jdbc.DefaultResultSet;

final class DiagnosticsResultSet
extends DefaultResultSet {
    final DiagnosticsConnection connection;
    final String sql;
    final ResultSetMetaData meta;
    final BitSet nullable;
    final BitSet read;
    final int columns;
    int current;
    int rows;
    int wasColumnIndex;
    boolean wasPrimitive;
    boolean wasNullable;

    DiagnosticsResultSet(ResultSet delegate, String sql, Statement creator, DiagnosticsConnection connection) throws SQLException {
        super(delegate, creator);
        this.connection = connection;
        this.sql = sql;
        this.meta = delegate.getMetaData();
        this.columns = this.meta.getColumnCount();
        this.read = new BitSet(this.columns);
        this.nullable = new BitSet(this.columns);
        for (int i = 0; i < this.columns; ++i) {
            this.nullable.set(i, this.meta.isNullable(i + 1) != 0);
        }
    }

    @Override
    public final String getString(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getString(columnIndex);
    }

    @Override
    public final boolean getBoolean(int columnIndex) throws SQLException {
        this.wasPrimitive(columnIndex);
        this.read(columnIndex);
        return super.getBoolean(columnIndex);
    }

    @Override
    public final byte getByte(int columnIndex) throws SQLException {
        this.wasPrimitive(columnIndex);
        this.read(columnIndex);
        return super.getByte(columnIndex);
    }

    @Override
    public final short getShort(int columnIndex) throws SQLException {
        this.wasPrimitive(columnIndex);
        this.read(columnIndex);
        return super.getShort(columnIndex);
    }

    @Override
    public final int getInt(int columnIndex) throws SQLException {
        this.wasPrimitive(columnIndex);
        this.read(columnIndex);
        return super.getInt(columnIndex);
    }

    @Override
    public final long getLong(int columnIndex) throws SQLException {
        this.wasPrimitive(columnIndex);
        this.read(columnIndex);
        return super.getLong(columnIndex);
    }

    @Override
    public final float getFloat(int columnIndex) throws SQLException {
        this.wasPrimitive(columnIndex);
        this.read(columnIndex);
        return super.getFloat(columnIndex);
    }

    @Override
    public final double getDouble(int columnIndex) throws SQLException {
        this.wasPrimitive(columnIndex);
        this.read(columnIndex);
        return super.getDouble(columnIndex);
    }

    @Override
    @Deprecated
    public final BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getBigDecimal(columnIndex, scale);
    }

    @Override
    public final byte[] getBytes(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getBytes(columnIndex);
    }

    @Override
    public final Date getDate(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getDate(columnIndex);
    }

    @Override
    public final Time getTime(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getTime(columnIndex);
    }

    @Override
    public final Timestamp getTimestamp(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getTimestamp(columnIndex);
    }

    @Override
    public final InputStream getAsciiStream(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getAsciiStream(columnIndex);
    }

    @Override
    @Deprecated
    public final InputStream getUnicodeStream(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getUnicodeStream(columnIndex);
    }

    @Override
    public final InputStream getBinaryStream(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getBinaryStream(columnIndex);
    }

    @Override
    public final String getString(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getString(columnLabel);
    }

    @Override
    public final boolean getBoolean(String columnLabel) throws SQLException {
        this.wasPrimitive(columnLabel);
        this.read(columnLabel);
        return super.getBoolean(columnLabel);
    }

    @Override
    public final byte getByte(String columnLabel) throws SQLException {
        this.wasPrimitive(columnLabel);
        this.read(columnLabel);
        return super.getByte(columnLabel);
    }

    @Override
    public final short getShort(String columnLabel) throws SQLException {
        this.wasPrimitive(columnLabel);
        this.read(columnLabel);
        return super.getShort(columnLabel);
    }

    @Override
    public final int getInt(String columnLabel) throws SQLException {
        this.wasPrimitive(columnLabel);
        this.read(columnLabel);
        return super.getInt(columnLabel);
    }

    @Override
    public final long getLong(String columnLabel) throws SQLException {
        this.wasPrimitive(columnLabel);
        this.read(columnLabel);
        return super.getLong(columnLabel);
    }

    @Override
    public final float getFloat(String columnLabel) throws SQLException {
        this.wasPrimitive(columnLabel);
        this.read(columnLabel);
        return super.getFloat(columnLabel);
    }

    @Override
    public final double getDouble(String columnLabel) throws SQLException {
        this.wasPrimitive(columnLabel);
        this.read(columnLabel);
        return super.getDouble(columnLabel);
    }

    @Override
    @Deprecated
    public final BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getBigDecimal(columnLabel, scale);
    }

    @Override
    public final byte[] getBytes(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getBytes(columnLabel);
    }

    @Override
    public final Date getDate(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getDate(columnLabel);
    }

    @Override
    public final Time getTime(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getTime(columnLabel);
    }

    @Override
    public final Timestamp getTimestamp(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getTimestamp(columnLabel);
    }

    @Override
    public final InputStream getAsciiStream(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getAsciiStream(columnLabel);
    }

    @Override
    @Deprecated
    public final InputStream getUnicodeStream(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getUnicodeStream(columnLabel);
    }

    @Override
    public final InputStream getBinaryStream(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getBinaryStream(columnLabel);
    }

    @Override
    public final Object getObject(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getObject(columnIndex);
    }

    @Override
    public final Object getObject(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getObject(columnLabel);
    }

    @Override
    public final Reader getCharacterStream(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getCharacterStream(columnIndex);
    }

    @Override
    public final Reader getCharacterStream(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getCharacterStream(columnLabel);
    }

    @Override
    public final BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getBigDecimal(columnIndex);
    }

    @Override
    public final BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getBigDecimal(columnLabel);
    }

    @Override
    public final Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getObject(columnIndex, map);
    }

    @Override
    public final Ref getRef(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getRef(columnIndex);
    }

    @Override
    public final Blob getBlob(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getBlob(columnIndex);
    }

    @Override
    public final Clob getClob(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getClob(columnIndex);
    }

    @Override
    public final Array getArray(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getArray(columnIndex);
    }

    @Override
    public final Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getObject(columnLabel, map);
    }

    @Override
    public final Ref getRef(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getRef(columnLabel);
    }

    @Override
    public final Blob getBlob(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getBlob(columnLabel);
    }

    @Override
    public final Clob getClob(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getClob(columnLabel);
    }

    @Override
    public final Array getArray(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getArray(columnLabel);
    }

    @Override
    public final Date getDate(int columnIndex, Calendar cal) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getDate(columnIndex, cal);
    }

    @Override
    public final Date getDate(String columnLabel, Calendar cal) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getDate(columnLabel, cal);
    }

    @Override
    public final Time getTime(int columnIndex, Calendar cal) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getTime(columnIndex, cal);
    }

    @Override
    public final Time getTime(String columnLabel, Calendar cal) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getTime(columnLabel, cal);
    }

    @Override
    public final Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getTimestamp(columnIndex, cal);
    }

    @Override
    public final Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getTimestamp(columnLabel, cal);
    }

    @Override
    public final URL getURL(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getURL(columnIndex);
    }

    @Override
    public final URL getURL(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getURL(columnLabel);
    }

    @Override
    public final RowId getRowId(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getRowId(columnIndex);
    }

    @Override
    public final RowId getRowId(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getRowId(columnLabel);
    }

    @Override
    public final NClob getNClob(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getNClob(columnIndex);
    }

    @Override
    public final NClob getNClob(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getNClob(columnLabel);
    }

    @Override
    public final SQLXML getSQLXML(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getSQLXML(columnIndex);
    }

    @Override
    public final SQLXML getSQLXML(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getSQLXML(columnLabel);
    }

    @Override
    public final String getNString(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getNString(columnIndex);
    }

    @Override
    public final String getNString(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getNString(columnLabel);
    }

    @Override
    public final Reader getNCharacterStream(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getNCharacterStream(columnIndex);
    }

    @Override
    public final Reader getNCharacterStream(String columnLabel) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getNCharacterStream(columnLabel);
    }

    @Override
    public final <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        this.checkPrimitive();
        this.read(columnIndex);
        return super.getObject(columnIndex, type);
    }

    @Override
    public final <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        this.checkPrimitive();
        this.read(columnLabel);
        return super.getObject(columnLabel, type);
    }

    @Override
    public final boolean wasNull() throws SQLException {
        if (!this.wasPrimitive && this.check(Settings::isDiagnosticsUnnecessaryWasNullCall)) {
            DefaultDiagnosticsContext ctx = this.ctx("ResultSet::wasNull was called unnecessarily.");
            ctx.resultSetUnnecessaryWasNullCall = true;
            ctx.resultSetColumnIndex = this.wasColumnIndex;
            this.connection.listeners.unnecessaryWasNullCall(ctx);
        }
        this.wasPrimitive = false;
        this.wasNullable = false;
        return super.wasNull();
    }

    private final void wasPrimitive(int columnIndex) throws SQLException {
        this.checkPrimitive();
        this.wasColumnIndex = columnIndex;
        this.wasPrimitive = true;
        this.wasNullable = this.nullable.get(columnIndex - 1);
    }

    private final void wasPrimitive(String columnLabel) throws SQLException {
        this.wasPrimitive(super.findColumn(columnLabel));
    }

    private final void checkPrimitive() throws SQLException {
        if (this.wasPrimitive && this.wasNullable && this.check(Settings::isDiagnosticsMissingWasNullCall)) {
            DefaultDiagnosticsContext ctx = this.ctx("ResultSet::wasNull was not called.");
            ctx.resultSetMissingWasNullCall = true;
            ctx.resultSetColumnIndex = this.wasColumnIndex;
            this.connection.listeners.missingWasNullCall(ctx);
        }
        this.wasPrimitive = false;
        this.wasNullable = false;
    }

    private final void read(int columnIndex) {
        this.read.set(columnIndex - 1);
    }

    private final void read(String columnLabel) throws SQLException {
        this.read(super.findColumn(columnLabel));
    }

    private final boolean check(Predicate<? super Settings> test) {
        return this.connection.check(test);
    }

    private final DefaultDiagnosticsContext ctx(String message) throws SQLException {
        DefaultDiagnosticsContext ctx = new DefaultDiagnosticsContext(this.connection.configuration, message, this.sql);
        ctx.resultSet = super.getDelegate();
        ctx.resultSetWrapper = this;
        ctx.resultSetConsumedColumnCount = this.read.cardinality();
        ctx.resultSetFetchedColumnCount = this.columns;
        ctx.resultSetConsumedRows = this.current;
        ctx.resultSetFetchedRows = this.current + 1;
        return ctx;
    }

    @Override
    public final void beforeFirst() throws SQLException {
        this.checkPrimitive();
        super.beforeFirst();
        this.moveAbsolute(true, super.getRow());
    }

    @Override
    public final void afterLast() throws SQLException {
        this.checkPrimitive();
        super.afterLast();
        this.moveAbsolute(true, super.getRow());
    }

    @Override
    public final boolean first() throws SQLException {
        this.checkPrimitive();
        return this.moveAbsolute(super.first(), super.getRow());
    }

    @Override
    public final boolean last() throws SQLException {
        this.checkPrimitive();
        return this.moveAbsolute(super.last(), super.getRow());
    }

    @Override
    public final boolean absolute(int row) throws SQLException {
        this.checkPrimitive();
        return this.moveAbsolute(super.absolute(row), super.getRow());
    }

    @Override
    public final boolean relative(int relative) throws SQLException {
        this.checkPrimitive();
        return this.moveRelative(super.relative(relative), relative);
    }

    @Override
    public final boolean next() throws SQLException {
        this.checkPrimitive();
        return this.moveRelative(super.next(), 1);
    }

    @Override
    public final boolean previous() throws SQLException {
        this.checkPrimitive();
        return this.moveRelative(super.previous(), -1);
    }

    private final boolean moveRelative(boolean success, int relative) {
        if (success) {
            this.current += relative;
            this.rows = Math.max(this.rows, this.current);
        }
        return success;
    }

    private final boolean moveAbsolute(boolean success, int absolute) {
        if (success) {
            this.current = absolute;
            this.rows = Math.max(this.rows, this.current);
        }
        return success;
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        this.checkPrimitive();
        return super.isBeforeFirst();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        this.checkPrimitive();
        return super.isAfterLast();
    }

    @Override
    public boolean isFirst() throws SQLException {
        this.checkPrimitive();
        return super.isFirst();
    }

    @Override
    public boolean isLast() throws SQLException {
        this.checkPrimitive();
        return super.isLast();
    }

    @Override
    public final void close() throws SQLException {
        this.checkPrimitive();
        try {
            if (this.check(Settings::isDiagnosticsTooManyRowsFetched)) {
                if (this.current < this.rows) {
                    this.current = this.rows;
                    super.absolute(this.current);
                }
                DefaultDiagnosticsContext c1 = this.ctx("Too many rows fetched");
                c1.resultSetClosing = true;
                if (super.next()) {
                    this.connection.listeners.tooManyRowsFetched(c1);
                }
            }
            if (this.check(Settings::isDiagnosticsTooManyColumnsFetched)) {
                DefaultDiagnosticsContext c2 = this.ctx("Too many columns fetched");
                c2.resultSetClosing = true;
                if (this.read.cardinality() != this.columns) {
                    this.connection.listeners.tooManyColumnsFetched(c2);
                }
            }
        }
        catch (SQLException sQLException) {
            // empty catch block
        }
        super.close();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        this.checkPrimitive();
        super.setFetchDirection(direction);
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        this.checkPrimitive();
        super.setFetchSize(rows);
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        this.checkPrimitive();
        return super.rowUpdated();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        this.checkPrimitive();
        return super.rowInserted();
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        this.checkPrimitive();
        return super.rowDeleted();
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        this.checkPrimitive();
        super.updateNull(columnIndex);
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        this.checkPrimitive();
        super.updateBoolean(columnIndex, x);
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        this.checkPrimitive();
        super.updateByte(columnIndex, x);
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        this.checkPrimitive();
        super.updateShort(columnIndex, x);
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        this.checkPrimitive();
        super.updateInt(columnIndex, x);
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        this.checkPrimitive();
        super.updateLong(columnIndex, x);
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        this.checkPrimitive();
        super.updateFloat(columnIndex, x);
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        this.checkPrimitive();
        super.updateDouble(columnIndex, x);
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        this.checkPrimitive();
        super.updateBigDecimal(columnIndex, x);
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        this.checkPrimitive();
        super.updateString(columnIndex, x);
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        this.checkPrimitive();
        super.updateBytes(columnIndex, x);
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        this.checkPrimitive();
        super.updateDate(columnIndex, x);
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        this.checkPrimitive();
        super.updateTime(columnIndex, x);
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        this.checkPrimitive();
        super.updateTimestamp(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        this.checkPrimitive();
        super.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        this.checkPrimitive();
        super.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        this.checkPrimitive();
        super.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        this.checkPrimitive();
        super.updateObject(columnIndex, x, scaleOrLength);
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        this.checkPrimitive();
        super.updateObject(columnIndex, x);
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        this.checkPrimitive();
        super.updateNull(columnLabel);
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        this.checkPrimitive();
        super.updateBoolean(columnLabel, x);
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        this.checkPrimitive();
        super.updateByte(columnLabel, x);
    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        this.checkPrimitive();
        super.updateShort(columnLabel, x);
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        this.checkPrimitive();
        super.updateInt(columnLabel, x);
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        this.checkPrimitive();
        super.updateLong(columnLabel, x);
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        this.checkPrimitive();
        super.updateFloat(columnLabel, x);
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        this.checkPrimitive();
        super.updateDouble(columnLabel, x);
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        this.checkPrimitive();
        super.updateBigDecimal(columnLabel, x);
    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        this.checkPrimitive();
        super.updateString(columnLabel, x);
    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        this.checkPrimitive();
        super.updateBytes(columnLabel, x);
    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        this.checkPrimitive();
        super.updateDate(columnLabel, x);
    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        this.checkPrimitive();
        super.updateTime(columnLabel, x);
    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        this.checkPrimitive();
        super.updateTimestamp(columnLabel, x);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        this.checkPrimitive();
        super.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        this.checkPrimitive();
        super.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        this.checkPrimitive();
        super.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        this.checkPrimitive();
        super.updateObject(columnLabel, x, scaleOrLength);
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        this.checkPrimitive();
        super.updateObject(columnLabel, x);
    }

    @Override
    public void insertRow() throws SQLException {
        this.checkPrimitive();
        super.insertRow();
    }

    @Override
    public void updateRow() throws SQLException {
        this.checkPrimitive();
        super.updateRow();
    }

    @Override
    public void deleteRow() throws SQLException {
        this.checkPrimitive();
        super.deleteRow();
    }

    @Override
    public void refreshRow() throws SQLException {
        this.checkPrimitive();
        super.refreshRow();
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        this.checkPrimitive();
        super.updateRef(columnIndex, x);
    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        this.checkPrimitive();
        super.updateRef(columnLabel, x);
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        this.checkPrimitive();
        super.updateBlob(columnIndex, x);
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        this.checkPrimitive();
        super.updateBlob(columnLabel, x);
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        this.checkPrimitive();
        super.updateClob(columnIndex, x);
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        this.checkPrimitive();
        super.updateClob(columnLabel, x);
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        this.checkPrimitive();
        super.updateArray(columnIndex, x);
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        this.checkPrimitive();
        super.updateArray(columnLabel, x);
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        this.checkPrimitive();
        super.updateRowId(columnIndex, x);
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        this.checkPrimitive();
        super.updateRowId(columnLabel, x);
    }

    @Override
    public boolean isClosed() throws SQLException {
        this.checkPrimitive();
        return super.isClosed();
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        this.checkPrimitive();
        super.updateNString(columnIndex, nString);
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        this.checkPrimitive();
        super.updateNString(columnLabel, nString);
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        this.checkPrimitive();
        super.updateNClob(columnIndex, nClob);
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        this.checkPrimitive();
        super.updateNClob(columnLabel, nClob);
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        this.checkPrimitive();
        super.updateSQLXML(columnIndex, xmlObject);
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        this.checkPrimitive();
        super.updateSQLXML(columnLabel, xmlObject);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this.checkPrimitive();
        super.updateNCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        this.checkPrimitive();
        super.updateNCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        this.checkPrimitive();
        super.updateAsciiStream(columnIndex, x, length);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        this.checkPrimitive();
        super.updateBinaryStream(columnIndex, x, length);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        this.checkPrimitive();
        super.updateCharacterStream(columnIndex, x, length);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        this.checkPrimitive();
        super.updateAsciiStream(columnLabel, x, length);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        this.checkPrimitive();
        super.updateBinaryStream(columnLabel, x, length);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        this.checkPrimitive();
        super.updateCharacterStream(columnLabel, reader, length);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream2, long length) throws SQLException {
        this.checkPrimitive();
        super.updateBlob(columnIndex, inputStream2, length);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream2, long length) throws SQLException {
        this.checkPrimitive();
        super.updateBlob(columnLabel, inputStream2, length);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        this.checkPrimitive();
        super.updateClob(columnIndex, reader, length);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        this.checkPrimitive();
        super.updateClob(columnLabel, reader, length);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        this.checkPrimitive();
        super.updateNClob(columnIndex, reader, length);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        this.checkPrimitive();
        super.updateNClob(columnLabel, reader, length);
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        this.checkPrimitive();
        super.updateNCharacterStream(columnIndex, x);
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        this.checkPrimitive();
        super.updateNCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        this.checkPrimitive();
        super.updateAsciiStream(columnIndex, x);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        this.checkPrimitive();
        super.updateBinaryStream(columnIndex, x);
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        this.checkPrimitive();
        super.updateCharacterStream(columnIndex, x);
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        this.checkPrimitive();
        super.updateAsciiStream(columnLabel, x);
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        this.checkPrimitive();
        super.updateBinaryStream(columnLabel, x);
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        this.checkPrimitive();
        super.updateCharacterStream(columnLabel, reader);
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream2) throws SQLException {
        this.checkPrimitive();
        super.updateBlob(columnIndex, inputStream2);
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream2) throws SQLException {
        this.checkPrimitive();
        super.updateBlob(columnLabel, inputStream2);
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        this.checkPrimitive();
        super.updateClob(columnIndex, reader);
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        this.checkPrimitive();
        super.updateClob(columnLabel, reader);
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        this.checkPrimitive();
        super.updateNClob(columnIndex, reader);
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        this.checkPrimitive();
        super.updateNClob(columnLabel, reader);
    }

    @Override
    public void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        this.checkPrimitive();
        super.updateObject(columnIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
        this.checkPrimitive();
        super.updateObject(columnLabel, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
        this.checkPrimitive();
        super.updateObject(columnIndex, x, targetSqlType);
    }

    @Override
    public void updateObject(String columnLabel, Object x, SQLType targetSqlType) throws SQLException {
        this.checkPrimitive();
        super.updateObject(columnLabel, x, targetSqlType);
    }
}

