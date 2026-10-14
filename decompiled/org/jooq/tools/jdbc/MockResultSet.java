/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
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
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.Converters;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.Internal;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.JDBC41ResultSet;
import org.jooq.tools.jdbc.MockResultSetMetaData;

public class MockResultSet
extends JDBC41ResultSet
implements ResultSet,
Serializable {
    private final int maxRows;
    Result<?> result;
    private final int size;
    private transient int index;
    private transient Record record;
    private transient boolean wasNull;
    private final Converter<?, ?>[] converters;

    public MockResultSet(Result<?> result) {
        this(result, 0);
    }

    public MockResultSet(Result<?> result, int maxRows) {
        this.result = result;
        this.maxRows = maxRows;
        if (result != null) {
            this.size = result.size();
            int l = result.fieldsRow().size();
            this.converters = new Converter[l];
            for (int i = 0; i < l; ++i) {
                this.converters[i] = Converters.inverse(result.field(i).getConverter());
            }
        } else {
            this.size = 0;
            this.converters = new Converter[0];
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (this.isWrapperFor(iface)) {
            return (T)this;
        }
        throw new SQLException("MockResultSet does not implement " + String.valueOf(iface));
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

    private int size() {
        if (this.maxRows == 0) {
            return this.size;
        }
        return Math.min(this.maxRows, this.size);
    }

    void checkNotClosed() throws SQLException {
        if (this.result == null) {
            throw new SQLException("ResultSet is already closed");
        }
    }

    private void checkInRange() throws SQLException {
        this.checkNotClosed();
        if (this.index <= 0 || this.index > this.size) {
            throw new SQLException("ResultSet index is at an illegal position : " + this.index);
        }
    }

    private Field<?> field(String columnLabel) throws SQLException {
        Field<?> field2 = this.result.field(columnLabel);
        if (field2 == null) {
            throw new SQLException("Unknown column label : " + columnLabel);
        }
        return field2;
    }

    private Converter<?, ?> converter(int columnIndex) throws SQLException {
        if (columnIndex > 0 && columnIndex <= this.converters.length) {
            return this.converters[columnIndex - 1];
        }
        throw new SQLException("Unknown column index : " + columnIndex);
    }

    private long getMillis(Calendar cal, int year, int month, int day, int hour, int minute, int second, int millis) {
        cal = (Calendar)cal.clone();
        cal.clear();
        cal.setLenient(true);
        if (year <= 0) {
            cal.set(0, 0);
            cal.set(1, 1 - year);
        } else {
            cal.set(0, 1);
            cal.set(1, year);
        }
        cal.set(2, month);
        cal.set(5, day);
        cal.set(11, hour);
        cal.set(12, minute);
        cal.set(13, second);
        cal.set(14, millis);
        return cal.getTimeInMillis();
    }

    private Timestamp withTZ(Timestamp timestamp, Calendar cal) {
        if (timestamp == null) {
            return null;
        }
        int year = timestamp.getYear() + 1900;
        int month = timestamp.getMonth();
        int day = timestamp.getDate();
        int hour = timestamp.getHours();
        int minute = timestamp.getMinutes();
        int second = timestamp.getSeconds();
        int nanos = timestamp.getNanos();
        int millis = nanos / 1000000;
        Timestamp r = new Timestamp(this.getMillis(cal, year, month, day, hour, minute, second, millis));
        r.setNanos((nanos -= millis * 1000000) + millis * 1000000);
        return r;
    }

    private Time withTZ(Time time, Calendar cal) {
        if (time == null) {
            return null;
        }
        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();
        int millis = (int)(time.getTime() % 1000L);
        return new Time(this.getMillis(cal, 1970, 0, 1, hour, minute, second, millis));
    }

    private Date withTZ(Date date, Calendar cal) {
        if (date == null) {
            return null;
        }
        int year = date.getYear() + 1900;
        int month = date.getMonth();
        int day = date.getDate();
        return new Date(this.getMillis(cal, year, month, day, 0, 0, 0, 0));
    }

    @Override
    public boolean next() throws SQLException {
        return this.relative(1);
    }

    @Override
    public boolean previous() throws SQLException {
        return this.relative(-1);
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        if (this.size() > 0) {
            if (row > 0) {
                if (row <= this.size()) {
                    this.index(row);
                    return true;
                }
                this.afterLast();
                return false;
            }
            if (row == 0) {
                this.beforeFirst();
                return false;
            }
            if (-row <= this.size()) {
                this.index(this.size() + 1 + row);
                return true;
            }
            this.beforeFirst();
            return false;
        }
        return false;
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        this.checkNotClosed();
        return this.index(this.index + rows);
    }

    @Override
    public int getRow() throws SQLException {
        return this.index > this.size() ? 0 : this.index;
    }

    @Override
    public void beforeFirst() throws SQLException {
        this.checkNotClosed();
        this.index(0);
    }

    @Override
    public void afterLast() throws SQLException {
        this.checkNotClosed();
        this.index(this.size() + 1);
    }

    @Override
    public boolean first() throws SQLException {
        return this.absolute(1);
    }

    @Override
    public boolean last() throws SQLException {
        this.checkNotClosed();
        return this.absolute(this.size());
    }

    @Override
    public boolean isFirst() throws SQLException {
        this.checkNotClosed();
        return this.size() > 0 && this.index == 1;
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        this.checkNotClosed();
        return this.size() > 0 && this.index == 0;
    }

    @Override
    public boolean isLast() throws SQLException {
        this.checkNotClosed();
        return this.size() > 0 && this.index == this.size();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        this.checkNotClosed();
        return this.size() > 0 && this.index > this.size();
    }

    @Override
    public void close() throws SQLException {
        this.result = null;
        this.index = 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.result == null;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public String getCursorName() throws SQLException {
        throw new SQLFeatureNotSupportedException("jOOQ ResultSets don't have a cursor name");
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        this.checkNotClosed();
        Field<?> field2 = this.result.field(columnLabel);
        if (field2 == null) {
            throw new SQLException("No such column : " + columnLabel);
        }
        return this.result.fieldsRow().indexOf(field2) + 1;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        if (direction != 1000) {
            throw new SQLException("Fetch direction can only be FETCH_FORWARD");
        }
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 1000;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getType() throws SQLException {
        return 1004;
    }

    @Override
    public int getConcurrency() throws SQLException {
        return 1007;
    }

    @Override
    public int getHoldability() throws SQLException {
        return 2;
    }

    @Override
    public boolean wasNull() throws SQLException {
        this.checkNotClosed();
        return this.wasNull;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return new MockResultSetMetaData(this);
    }

    @Override
    public Statement getStatement() throws SQLException {
        return null;
    }

    private <T> T get(String columnLabel, Class<T> type) throws SQLException {
        this.checkInRange();
        ContextConverter converter = Converters.inverse(this.field(columnLabel).getConverter());
        return this.get0(this.record.get(columnLabel, converter), type);
    }

    private <T> T get(int columnIndex, Class<T> type) throws SQLException {
        this.checkInRange();
        return this.get0(this.record.get(columnIndex - 1, this.converter(columnIndex)), type);
    }

    private <T> T get0(Object value, Class<T> type) {
        Converter<Object, T> converter = (this.record.configuration() == null ? new DefaultConfiguration() : this.record.configuration()).converterProvider().provide(value == null ? Object.class : value.getClass(), type);
        T converted = converter == null ? null : (T)ContextConverter.scoped(converter).from(value, Internal.converterContext());
        this.wasNull = converted == null;
        return converted;
    }

    private boolean index(int newIndex) {
        int s2 = this.size();
        this.index = Math.min(Math.max(newIndex, 0), s2 + 1);
        boolean inRange = this.index > 0 && this.index <= s2;
        this.record = inRange ? (Record)this.result.get(this.index - 1) : null;
        return inRange;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return this.get(columnIndex, String.class);
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        return this.get(columnLabel, String.class);
    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        return this.getString(columnIndex);
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        return this.getString(columnLabel);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        Boolean value = this.get(columnIndex, Boolean.class);
        return this.wasNull ? false : value;
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        Boolean value = this.get(columnLabel, Boolean.class);
        return this.wasNull ? false : value;
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        Byte value = this.get(columnIndex, Byte.class);
        return this.wasNull ? (byte)0 : value;
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        Byte value = this.get(columnLabel, Byte.class);
        return this.wasNull ? (byte)0 : value;
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        Short value = this.get(columnIndex, Short.class);
        return this.wasNull ? (short)0 : value;
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        Short value = this.get(columnLabel, Short.class);
        return this.wasNull ? (short)0 : value;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        Integer value = this.get(columnIndex, Integer.class);
        return this.wasNull ? 0 : value;
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        Integer value = this.get(columnLabel, Integer.class);
        return this.wasNull ? 0 : value;
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        Long value = this.get(columnIndex, Long.class);
        return this.wasNull ? 0L : value;
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        Long value = this.get(columnLabel, Long.class);
        return this.wasNull ? 0L : value;
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        Float value = this.get(columnIndex, Float.class);
        return this.wasNull ? 0.0f : value.floatValue();
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        Float value = this.get(columnLabel, Float.class);
        return this.wasNull ? 0.0f : value.floatValue();
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        Double value = this.get(columnIndex, Double.class);
        return this.wasNull ? 0.0 : value;
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        Double value = this.get(columnLabel, Double.class);
        return this.wasNull ? 0.0 : value;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return this.get(columnIndex, BigDecimal.class);
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return this.get(columnIndex, BigDecimal.class);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return this.get(columnLabel, BigDecimal.class);
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return this.get(columnLabel, BigDecimal.class);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return this.get(columnIndex, byte[].class);
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        return this.get(columnLabel, byte[].class);
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return this.get(columnIndex, Date.class);
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        return this.withTZ(this.get(columnIndex, Date.class), cal);
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        return this.get(columnLabel, Date.class);
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        return this.withTZ(this.get(columnLabel, Date.class), cal);
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return this.get(columnIndex, Time.class);
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        return this.withTZ(this.get(columnIndex, Time.class), cal);
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        return this.get(columnLabel, Time.class);
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        return this.withTZ(this.get(columnLabel, Time.class), cal);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return this.get(columnIndex, Timestamp.class);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        return this.withTZ(this.get(columnIndex, Timestamp.class), cal);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return this.get(columnLabel, Timestamp.class);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        return this.withTZ(this.get(columnLabel, Timestamp.class), cal);
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        byte[] bytes = this.getBytes(columnIndex);
        return this.wasNull ? null : new ByteArrayInputStream(bytes);
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        byte[] bytes = this.getBytes(columnLabel);
        return this.wasNull ? null : new ByteArrayInputStream(bytes);
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        String string = this.getString(columnIndex);
        return this.wasNull ? null : new ByteArrayInputStream(string.getBytes());
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        String string = this.getString(columnLabel);
        return this.wasNull ? null : new ByteArrayInputStream(string.getBytes());
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        String string = this.getString(columnIndex);
        return this.wasNull ? null : new StringReader(string);
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        String string = this.getString(columnLabel);
        return this.wasNull ? null : new StringReader(string);
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        return this.getCharacterStream(columnIndex);
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        return this.getCharacterStream(columnLabel);
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        byte[] bytes = this.getBytes(columnIndex);
        return this.wasNull ? null : new ByteArrayInputStream(bytes);
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        byte[] bytes = this.getBytes(columnLabel);
        return this.wasNull ? null : new ByteArrayInputStream(bytes);
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type");
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type");
    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type");
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type");
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type");
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type");
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type. Use getBytes() instead");
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type. Use getBytes() instead");
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type. Use getString() instead");
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type. Use getString() instead");
    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type. Use getString() instead");
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        throw new SQLFeatureNotSupportedException("Unsupported data type. Use getString() instead");
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        return this.get(columnIndex, Array.class);
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        return this.get(columnLabel, Array.class);
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        return this.get(columnIndex, URL.class);
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        return this.get(columnLabel, URL.class);
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return this.get(columnIndex, Object.class);
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        return this.get(columnIndex, Object.class);
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        return this.get(columnLabel, Object.class);
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        return this.get(columnLabel, Object.class);
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return this.get(columnIndex, type);
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return this.get(columnLabel, type);
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return false;
    }

    @Override
    public boolean rowInserted() throws SQLException {
        return false;
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        return false;
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream2, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream2, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream2) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream2) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void insertRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void updateRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void deleteRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void refreshRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("Cannot update ResultSet");
    }

    public String toString() {
        if (this.result == null) {
            return "null";
        }
        if (this.result.size() == 0 || this.index == 0 || this.index > this.size()) {
            return this.result.toString();
        }
        String prefix = "row " + this.index + " -> ";
        String prefixEmpty = StringUtils.leftPad("", prefix.length());
        Result<Record> r = DSL.using(SQLDialect.DEFAULT).newResult(this.result.fields());
        r.addAll(this.result.subList(Math.max(0, this.index - 3), Math.min(this.size, this.index + 2)));
        StringBuilder sb = new StringBuilder();
        String[] split = r.toString().split("\n");
        for (int i = 0; i < split.length; ++i) {
            sb.append((String)(i - 2 == Math.min(3, this.index) ? prefix : prefixEmpty)).append(split[i]).append('\n');
        }
        return sb.toString();
    }
}

