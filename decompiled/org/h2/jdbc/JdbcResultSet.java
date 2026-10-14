/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
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
import java.util.HashMap;
import java.util.Map;
import org.h2.command.CommandInterface;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Session;
import org.h2.engine.SysProperties;
import org.h2.jdbc.JdbcArray;
import org.h2.jdbc.JdbcBlob;
import org.h2.jdbc.JdbcClob;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcLob;
import org.h2.jdbc.JdbcPreparedStatement;
import org.h2.jdbc.JdbcResultSetMetaData;
import org.h2.jdbc.JdbcSQLXML;
import org.h2.jdbc.JdbcStatement;
import org.h2.message.DbException;
import org.h2.message.TraceObject;
import org.h2.result.ResultInterface;
import org.h2.result.UpdatableRow;
import org.h2.util.IOUtils;
import org.h2.util.LegacyDateTimeUtils;
import org.h2.util.StringUtils;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueNumeric;
import org.h2.value.ValueReal;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueTinyint;
import org.h2.value.ValueToObjectConverter;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;

public final class JdbcResultSet
extends TraceObject
implements ResultSet {
    private final boolean scrollable;
    private final boolean updatable;
    private final boolean triggerUpdatable;
    ResultInterface result;
    private JdbcConnection conn;
    private JdbcStatement stat;
    private int columnCount;
    private boolean wasNull;
    private Value[] insertRow;
    private Value[] updateRow;
    private HashMap<String, Integer> columnLabelMap;
    private HashMap<Long, Value[]> patchedRows;
    private JdbcPreparedStatement preparedStatement;
    private final CommandInterface command;

    public JdbcResultSet(JdbcConnection jdbcConnection, JdbcStatement jdbcStatement, CommandInterface commandInterface, ResultInterface resultInterface, int n, boolean bl, boolean bl2, boolean bl3) {
        this.setTrace(jdbcConnection.getSession().getTrace(), 4, n);
        this.conn = jdbcConnection;
        this.stat = jdbcStatement;
        this.command = commandInterface;
        this.result = resultInterface;
        this.columnCount = resultInterface.getVisibleColumnCount();
        this.scrollable = bl;
        this.updatable = bl2;
        this.triggerUpdatable = bl3;
    }

    JdbcResultSet(JdbcConnection jdbcConnection, JdbcPreparedStatement jdbcPreparedStatement, CommandInterface commandInterface, ResultInterface resultInterface, int n, boolean bl, boolean bl2, HashMap<String, Integer> hashMap) {
        this(jdbcConnection, (JdbcStatement)jdbcPreparedStatement, commandInterface, resultInterface, n, bl, bl2, false);
        this.columnLabelMap = hashMap;
        this.preparedStatement = jdbcPreparedStatement;
    }

    @Override
    public boolean next() throws SQLException {
        try {
            this.debugCodeCall("next");
            this.checkClosed();
            return this.nextRow();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            int n = JdbcResultSet.getNextId(5);
            this.debugCodeAssign("ResultSetMetaData", 5, n, "getMetaData()");
            this.checkClosed();
            String string = this.conn.getCatalog();
            return new JdbcResultSetMetaData(this, null, this.result, string, this.conn.getSession().getTrace(), n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean wasNull() throws SQLException {
        try {
            this.debugCodeCall("wasNull");
            this.checkClosed();
            return this.wasNull;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int findColumn(String string) throws SQLException {
        try {
            this.debugCodeCall("findColumn", string);
            return this.getColumnIndex(string);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            this.debugCodeCall("close");
            this.closeInternal(false);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    void closeInternal(boolean bl) {
        if (this.result != null) {
            try {
                if (this.result.isLazy()) {
                    this.stat.onLazyResultSetClose(this.command, this.preparedStatement == null);
                }
                this.result.close();
            }
            finally {
                JdbcStatement jdbcStatement = this.stat;
                this.columnCount = 0;
                this.result = null;
                this.stat = null;
                this.conn = null;
                this.insertRow = null;
                this.updateRow = null;
                if (!bl && jdbcStatement != null) {
                    jdbcStatement.closeIfCloseOnCompletion();
                }
            }
        }
    }

    @Override
    public Statement getStatement() throws SQLException {
        try {
            this.debugCodeCall("getStatement");
            this.checkClosed();
            return this.stat;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        try {
            this.debugCodeCall("getWarnings");
            this.checkClosed();
            return null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void clearWarnings() throws SQLException {
        try {
            this.debugCodeCall("clearWarnings");
            this.checkClosed();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getString(int n) throws SQLException {
        try {
            this.debugCodeCall("getString", n);
            return this.get(this.checkColumnIndex(n)).getString();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getString(String string) throws SQLException {
        try {
            this.debugCodeCall("getString", string);
            return this.get(this.getColumnIndex(string)).getString();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getInt(int n) throws SQLException {
        try {
            this.debugCodeCall("getInt", n);
            return this.getIntInternal(this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getInt(String string) throws SQLException {
        try {
            this.debugCodeCall("getInt", string);
            return this.getIntInternal(this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private int getIntInternal(int n) {
        int n2;
        Value value = this.getInternal(n);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            n2 = value.getInt();
        } else {
            this.wasNull = true;
            n2 = 0;
        }
        return n2;
    }

    @Override
    public BigDecimal getBigDecimal(int n) throws SQLException {
        try {
            this.debugCodeCall("getBigDecimal", n);
            return this.get(this.checkColumnIndex(n)).getBigDecimal();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Date getDate(int n) throws SQLException {
        try {
            this.debugCodeCall("getDate", n);
            return LegacyDateTimeUtils.toDate(this.conn, null, this.get(this.checkColumnIndex(n)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Time getTime(int n) throws SQLException {
        try {
            this.debugCodeCall("getTime", n);
            return LegacyDateTimeUtils.toTime(this.conn, null, this.get(this.checkColumnIndex(n)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Timestamp getTimestamp(int n) throws SQLException {
        try {
            this.debugCodeCall("getTimestamp", n);
            return LegacyDateTimeUtils.toTimestamp(this.conn, null, this.get(this.checkColumnIndex(n)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public BigDecimal getBigDecimal(String string) throws SQLException {
        try {
            this.debugCodeCall("getBigDecimal", string);
            return this.get(this.getColumnIndex(string)).getBigDecimal();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Date getDate(String string) throws SQLException {
        try {
            this.debugCodeCall("getDate", string);
            return LegacyDateTimeUtils.toDate(this.conn, null, this.get(this.getColumnIndex(string)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Time getTime(String string) throws SQLException {
        try {
            this.debugCodeCall("getTime", string);
            return LegacyDateTimeUtils.toTime(this.conn, null, this.get(this.getColumnIndex(string)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Timestamp getTimestamp(String string) throws SQLException {
        try {
            this.debugCodeCall("getTimestamp", string);
            return LegacyDateTimeUtils.toTimestamp(this.conn, null, this.get(this.getColumnIndex(string)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Object getObject(int n) throws SQLException {
        try {
            this.debugCodeCall("getObject", n);
            return ValueToObjectConverter.valueToDefaultObject(this.get(this.checkColumnIndex(n)), this.conn, true);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Object getObject(String string) throws SQLException {
        try {
            this.debugCodeCall("getObject", string);
            return ValueToObjectConverter.valueToDefaultObject(this.get(this.getColumnIndex(string)), this.conn, true);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean getBoolean(int n) throws SQLException {
        try {
            this.debugCodeCall("getBoolean", n);
            return this.getBooleanInternal(this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean getBoolean(String string) throws SQLException {
        try {
            this.debugCodeCall("getBoolean", string);
            return this.getBooleanInternal(this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private boolean getBooleanInternal(int n) {
        boolean bl;
        Value value = this.getInternal(n);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            bl = value.getBoolean();
        } else {
            this.wasNull = true;
            bl = false;
        }
        return bl;
    }

    @Override
    public byte getByte(int n) throws SQLException {
        try {
            this.debugCodeCall("getByte", n);
            return this.getByteInternal(this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public byte getByte(String string) throws SQLException {
        try {
            this.debugCodeCall("getByte", string);
            return this.getByteInternal(this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private byte getByteInternal(int n) {
        byte by;
        Value value = this.getInternal(n);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            by = value.getByte();
        } else {
            this.wasNull = true;
            by = 0;
        }
        return by;
    }

    @Override
    public short getShort(int n) throws SQLException {
        try {
            this.debugCodeCall("getShort", n);
            return this.getShortInternal(this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public short getShort(String string) throws SQLException {
        try {
            this.debugCodeCall("getShort", string);
            return this.getShortInternal(this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private short getShortInternal(int n) {
        short s2;
        Value value = this.getInternal(n);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            s2 = value.getShort();
        } else {
            this.wasNull = true;
            s2 = 0;
        }
        return s2;
    }

    @Override
    public long getLong(int n) throws SQLException {
        try {
            this.debugCodeCall("getLong", n);
            return this.getLongInternal(this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public long getLong(String string) throws SQLException {
        try {
            this.debugCodeCall("getLong", string);
            return this.getLongInternal(this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private long getLongInternal(int n) {
        long l;
        Value value = this.getInternal(n);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            l = value.getLong();
        } else {
            this.wasNull = true;
            l = 0L;
        }
        return l;
    }

    @Override
    public float getFloat(int n) throws SQLException {
        try {
            this.debugCodeCall("getFloat", n);
            return this.getFloatInternal(this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public float getFloat(String string) throws SQLException {
        try {
            this.debugCodeCall("getFloat", string);
            return this.getFloatInternal(this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private float getFloatInternal(int n) {
        float f;
        Value value = this.getInternal(n);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            f = value.getFloat();
        } else {
            this.wasNull = true;
            f = 0.0f;
        }
        return f;
    }

    @Override
    public double getDouble(int n) throws SQLException {
        try {
            this.debugCodeCall("getDouble", n);
            return this.getDoubleInternal(this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public double getDouble(String string) throws SQLException {
        try {
            this.debugCodeCall("getDouble", string);
            return this.getDoubleInternal(this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private double getDoubleInternal(int n) {
        double d;
        Value value = this.getInternal(n);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            d = value.getDouble();
        } else {
            this.wasNull = true;
            d = 0.0;
        }
        return d;
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(String string, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getBigDecimal(" + JdbcResultSet.quote(string) + ", " + n + ')');
            }
            if (n < 0) {
                throw DbException.getInvalidValueException("scale", n);
            }
            BigDecimal bigDecimal = this.get(this.getColumnIndex(string)).getBigDecimal();
            return bigDecimal == null ? null : ValueNumeric.setScale(bigDecimal, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int n, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getBigDecimal(" + n + ", " + n2 + ')');
            }
            if (n2 < 0) {
                throw DbException.getInvalidValueException("scale", n2);
            }
            BigDecimal bigDecimal = this.get(this.checkColumnIndex(n)).getBigDecimal();
            return bigDecimal == null ? null : ValueNumeric.setScale(bigDecimal, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(int n) throws SQLException {
        throw this.unsupported("unicodeStream");
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(String string) throws SQLException {
        throw this.unsupported("unicodeStream");
    }

    @Override
    public Object getObject(int n, Map<String, Class<?>> map) throws SQLException {
        throw this.unsupported("map");
    }

    @Override
    public Object getObject(String string, Map<String, Class<?>> map) throws SQLException {
        throw this.unsupported("map");
    }

    @Override
    public Ref getRef(int n) throws SQLException {
        throw this.unsupported("ref");
    }

    @Override
    public Ref getRef(String string) throws SQLException {
        throw this.unsupported("ref");
    }

    @Override
    public Date getDate(int n, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getDate(" + n + ", calendar)");
            }
            return LegacyDateTimeUtils.toDate(this.conn, calendar != null ? calendar.getTimeZone() : null, this.get(this.checkColumnIndex(n)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Date getDate(String string, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getDate(" + JdbcResultSet.quote(string) + ", calendar)");
            }
            return LegacyDateTimeUtils.toDate(this.conn, calendar != null ? calendar.getTimeZone() : null, this.get(this.getColumnIndex(string)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Time getTime(int n, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getTime(" + n + ", calendar)");
            }
            return LegacyDateTimeUtils.toTime(this.conn, calendar != null ? calendar.getTimeZone() : null, this.get(this.checkColumnIndex(n)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Time getTime(String string, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getTime(" + JdbcResultSet.quote(string) + ", calendar)");
            }
            return LegacyDateTimeUtils.toTime(this.conn, calendar != null ? calendar.getTimeZone() : null, this.get(this.getColumnIndex(string)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Timestamp getTimestamp(int n, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getTimestamp(" + n + ", calendar)");
            }
            return LegacyDateTimeUtils.toTimestamp(this.conn, calendar != null ? calendar.getTimeZone() : null, this.get(this.checkColumnIndex(n)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Timestamp getTimestamp(String string, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("getTimestamp(" + JdbcResultSet.quote(string) + ", calendar)");
            }
            return LegacyDateTimeUtils.toTimestamp(this.conn, calendar != null ? calendar.getTimeZone() : null, this.get(this.getColumnIndex(string)));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Blob getBlob(int n) throws SQLException {
        try {
            int n2 = JdbcResultSet.getNextId(9);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Blob", 9, n2, "getBlob(" + n + ')');
            }
            return this.getBlob(n2, this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Blob getBlob(String string) throws SQLException {
        try {
            int n = JdbcResultSet.getNextId(9);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Blob", 9, n, "getBlob(" + JdbcResultSet.quote(string) + ')');
            }
            return this.getBlob(n, this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private JdbcBlob getBlob(int n, int n2) {
        JdbcBlob jdbcBlob;
        Value value = this.getInternal(n2);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            jdbcBlob = new JdbcBlob(this.conn, value, JdbcLob.State.WITH_VALUE, n);
        } else {
            this.wasNull = true;
            jdbcBlob = null;
        }
        return jdbcBlob;
    }

    @Override
    public byte[] getBytes(int n) throws SQLException {
        try {
            this.debugCodeCall("getBytes", n);
            return this.get(this.checkColumnIndex(n)).getBytes();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public byte[] getBytes(String string) throws SQLException {
        try {
            this.debugCodeCall("getBytes", string);
            return this.get(this.getColumnIndex(string)).getBytes();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public InputStream getBinaryStream(int n) throws SQLException {
        try {
            this.debugCodeCall("getBinaryStream", n);
            return this.get(this.checkColumnIndex(n)).getInputStream();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public InputStream getBinaryStream(String string) throws SQLException {
        try {
            this.debugCodeCall("getBinaryStream", string);
            return this.get(this.getColumnIndex(string)).getInputStream();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Clob getClob(int n) throws SQLException {
        try {
            int n2 = JdbcResultSet.getNextId(10);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Clob", 10, n2, "getClob(" + n + ')');
            }
            return this.getClob(n2, this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Clob getClob(String string) throws SQLException {
        try {
            int n = JdbcResultSet.getNextId(10);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Clob", 10, n, "getClob(" + JdbcResultSet.quote(string) + ')');
            }
            return this.getClob(n, this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Array getArray(int n) throws SQLException {
        try {
            int n2 = JdbcResultSet.getNextId(16);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Array", 16, n2, "getArray(" + n + ')');
            }
            return this.getArray(n2, this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Array getArray(String string) throws SQLException {
        try {
            int n = JdbcResultSet.getNextId(16);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("Array", 16, n, "getArray(" + JdbcResultSet.quote(string) + ')');
            }
            return this.getArray(n, this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private Array getArray(int n, int n2) {
        JdbcArray jdbcArray;
        Value value = this.getInternal(n2);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            jdbcArray = new JdbcArray(this.conn, value, n);
        } else {
            this.wasNull = true;
            jdbcArray = null;
        }
        return jdbcArray;
    }

    @Override
    public InputStream getAsciiStream(int n) throws SQLException {
        try {
            this.debugCodeCall("getAsciiStream", n);
            String string = this.get(this.checkColumnIndex(n)).getString();
            return string == null ? null : IOUtils.getInputStreamFromString(string);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public InputStream getAsciiStream(String string) throws SQLException {
        try {
            this.debugCodeCall("getAsciiStream", string);
            String string2 = this.get(this.getColumnIndex(string)).getString();
            return IOUtils.getInputStreamFromString(string2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Reader getCharacterStream(int n) throws SQLException {
        try {
            this.debugCodeCall("getCharacterStream", n);
            return this.get(this.checkColumnIndex(n)).getReader();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Reader getCharacterStream(String string) throws SQLException {
        try {
            this.debugCodeCall("getCharacterStream", string);
            return this.get(this.getColumnIndex(string)).getReader();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public URL getURL(int n) throws SQLException {
        throw this.unsupported("url");
    }

    @Override
    public URL getURL(String string) throws SQLException {
        throw this.unsupported("url");
    }

    @Override
    public void updateNull(int n) throws SQLException {
        try {
            this.debugCodeCall("updateNull", n);
            this.update(this.checkColumnIndex(n), ValueNull.INSTANCE);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNull(String string) throws SQLException {
        try {
            this.debugCodeCall("updateNull", string);
            this.update(this.getColumnIndex(string), ValueNull.INSTANCE);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBoolean(int n, boolean bl) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBoolean(" + n + ", " + bl + ')');
            }
            this.update(this.checkColumnIndex(n), ValueBoolean.get(bl));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBoolean(String string, boolean bl) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBoolean(" + JdbcResultSet.quote(string) + ", " + bl + ')');
            }
            this.update(this.getColumnIndex(string), ValueBoolean.get(bl));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateByte(int n, byte by) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateByte(" + n + ", " + by + ')');
            }
            this.update(this.checkColumnIndex(n), ValueTinyint.get(by));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateByte(String string, byte by) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateByte(" + JdbcResultSet.quote(string) + ", " + by + ')');
            }
            this.update(this.getColumnIndex(string), ValueTinyint.get(by));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBytes(int n, byte[] byArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBytes(" + n + ", x)");
            }
            this.update(this.checkColumnIndex(n), byArray == null ? ValueNull.INSTANCE : ValueVarbinary.get(byArray));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBytes(String string, byte[] byArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBytes(" + JdbcResultSet.quote(string) + ", x)");
            }
            this.update(this.getColumnIndex(string), byArray == null ? ValueNull.INSTANCE : ValueVarbinary.get(byArray));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateShort(int n, short s2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateShort(" + n + ", (short) " + s2 + ')');
            }
            this.update(this.checkColumnIndex(n), ValueSmallint.get(s2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateShort(String string, short s2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateShort(" + JdbcResultSet.quote(string) + ", (short) " + s2 + ')');
            }
            this.update(this.getColumnIndex(string), ValueSmallint.get(s2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateInt(int n, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateInt(" + n + ", " + n2 + ')');
            }
            this.update(this.checkColumnIndex(n), ValueInteger.get(n2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateInt(String string, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateInt(" + JdbcResultSet.quote(string) + ", " + n + ')');
            }
            this.update(this.getColumnIndex(string), ValueInteger.get(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateLong(int n, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateLong(" + n + ", " + l + "L)");
            }
            this.update(this.checkColumnIndex(n), ValueBigint.get(l));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateLong(String string, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateLong(" + JdbcResultSet.quote(string) + ", " + l + "L)");
            }
            this.update(this.getColumnIndex(string), ValueBigint.get(l));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateFloat(int n, float f) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateFloat(" + n + ", " + f + "f)");
            }
            this.update(this.checkColumnIndex(n), ValueReal.get(f));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateFloat(String string, float f) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateFloat(" + JdbcResultSet.quote(string) + ", " + f + "f)");
            }
            this.update(this.getColumnIndex(string), ValueReal.get(f));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateDouble(int n, double d) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateDouble(" + n + ", " + d + "d)");
            }
            this.update(this.checkColumnIndex(n), ValueDouble.get(d));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateDouble(String string, double d) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateDouble(" + JdbcResultSet.quote(string) + ", " + d + "d)");
            }
            this.update(this.getColumnIndex(string), ValueDouble.get(d));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBigDecimal(int n, BigDecimal bigDecimal) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBigDecimal(" + n + ", " + JdbcResultSet.quoteBigDecimal(bigDecimal) + ')');
            }
            this.update(this.checkColumnIndex(n), bigDecimal == null ? ValueNull.INSTANCE : ValueNumeric.getAnyScale(bigDecimal));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBigDecimal(String string, BigDecimal bigDecimal) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBigDecimal(" + JdbcResultSet.quote(string) + ", " + JdbcResultSet.quoteBigDecimal(bigDecimal) + ')');
            }
            this.update(this.getColumnIndex(string), bigDecimal == null ? ValueNull.INSTANCE : ValueNumeric.getAnyScale(bigDecimal));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateString(int n, String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateString(" + n + ", " + JdbcResultSet.quote(string) + ')');
            }
            this.update(this.checkColumnIndex(n), string == null ? ValueNull.INSTANCE : ValueVarchar.get(string, this.conn));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateString(String string, String string2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateString(" + JdbcResultSet.quote(string) + ", " + JdbcResultSet.quote(string2) + ')');
            }
            this.update(this.getColumnIndex(string), string2 == null ? ValueNull.INSTANCE : ValueVarchar.get(string2, this.conn));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateDate(int n, Date date) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateDate(" + n + ", " + JdbcResultSet.quoteDate(date) + ')');
            }
            this.update(this.checkColumnIndex(n), date == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromDate(this.conn, null, date));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateDate(String string, Date date) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateDate(" + JdbcResultSet.quote(string) + ", " + JdbcResultSet.quoteDate(date) + ')');
            }
            this.update(this.getColumnIndex(string), date == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromDate(this.conn, null, date));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateTime(int n, Time time) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateTime(" + n + ", " + JdbcResultSet.quoteTime(time) + ')');
            }
            this.update(this.checkColumnIndex(n), time == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromTime(this.conn, null, time));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateTime(String string, Time time) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateTime(" + JdbcResultSet.quote(string) + ", " + JdbcResultSet.quoteTime(time) + ')');
            }
            this.update(this.getColumnIndex(string), time == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromTime(this.conn, null, time));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateTimestamp(int n, Timestamp timestamp) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateTimestamp(" + n + ", " + JdbcResultSet.quoteTimestamp(timestamp) + ')');
            }
            this.update(this.checkColumnIndex(n), timestamp == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromTimestamp((CastDataProvider)this.conn, null, timestamp));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateTimestamp(String string, Timestamp timestamp) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateTimestamp(" + JdbcResultSet.quote(string) + ", " + JdbcResultSet.quoteTimestamp(timestamp) + ')');
            }
            this.update(this.getColumnIndex(string), timestamp == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromTimestamp((CastDataProvider)this.conn, null, timestamp));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateAsciiStream(int n, InputStream inputStream2, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateAsciiStream(" + n + ", x, " + n2 + ')');
            }
            this.updateAscii(this.checkColumnIndex(n), inputStream2, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateAsciiStream(int n, InputStream inputStream2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateAsciiStream(" + n + ", x)");
            }
            this.updateAscii(this.checkColumnIndex(n), inputStream2, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateAsciiStream(int n, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateAsciiStream(" + n + ", x, " + l + "L)");
            }
            this.updateAscii(this.checkColumnIndex(n), inputStream2, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateAsciiStream(String string, InputStream inputStream2, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateAsciiStream(" + JdbcResultSet.quote(string) + ", x, " + n + ')');
            }
            this.updateAscii(this.getColumnIndex(string), inputStream2, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateAsciiStream(String string, InputStream inputStream2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateAsciiStream(" + JdbcResultSet.quote(string) + ", x)");
            }
            this.updateAscii(this.getColumnIndex(string), inputStream2, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateAsciiStream(String string, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateAsciiStream(" + JdbcResultSet.quote(string) + ", x, " + l + "L)");
            }
            this.updateAscii(this.getColumnIndex(string), inputStream2, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void updateAscii(int n, InputStream inputStream2, long l) {
        this.update(n, this.conn.createClob(IOUtils.getAsciiReader(inputStream2), l));
    }

    @Override
    public void updateBinaryStream(int n, InputStream inputStream2, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBinaryStream(" + n + ", x, " + n2 + ')');
            }
            this.updateBlobImpl(this.checkColumnIndex(n), inputStream2, (long)n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBinaryStream(int n, InputStream inputStream2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBinaryStream(" + n + ", x)");
            }
            this.updateBlobImpl(this.checkColumnIndex(n), inputStream2, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBinaryStream(int n, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBinaryStream(" + n + ", x, " + l + "L)");
            }
            this.updateBlobImpl(this.checkColumnIndex(n), inputStream2, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBinaryStream(String string, InputStream inputStream2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBinaryStream(" + JdbcResultSet.quote(string) + ", x)");
            }
            this.updateBlobImpl(this.getColumnIndex(string), inputStream2, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBinaryStream(String string, InputStream inputStream2, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBinaryStream(" + JdbcResultSet.quote(string) + ", x, " + n + ')');
            }
            this.updateBlobImpl(this.getColumnIndex(string), inputStream2, (long)n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBinaryStream(String string, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBinaryStream(" + JdbcResultSet.quote(string) + ", x, " + l + "L)");
            }
            this.updateBlobImpl(this.getColumnIndex(string), inputStream2, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateCharacterStream(int n, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateCharacterStream(" + n + ", x, " + l + "L)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateCharacterStream(int n, Reader reader, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateCharacterStream(" + n + ", x, " + n2 + ')');
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateCharacterStream(int n, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateCharacterStream(" + n + ", x)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateCharacterStream(String string, Reader reader, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateCharacterStream(" + JdbcResultSet.quote(string) + ", x, " + n + ')');
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateCharacterStream(String string, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateCharacterStream(" + JdbcResultSet.quote(string) + ", x)");
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateCharacterStream(String string, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateCharacterStream(" + JdbcResultSet.quote(string) + ", x, " + l + "L)");
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateObject(int n, Object object, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateObject(" + n + ", x, " + n2 + ')');
            }
            this.update(this.checkColumnIndex(n), this.convertToUnknownValue(object));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateObject(String string, Object object, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateObject(" + JdbcResultSet.quote(string) + ", x, " + n + ')');
            }
            this.update(this.getColumnIndex(string), this.convertToUnknownValue(object));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateObject(int n, Object object) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateObject(" + n + ", x)");
            }
            this.update(this.checkColumnIndex(n), this.convertToUnknownValue(object));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateObject(String string, Object object) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateObject(" + JdbcResultSet.quote(string) + ", x)");
            }
            this.update(this.getColumnIndex(string), this.convertToUnknownValue(object));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateObject(int n, Object object, SQLType sQLType) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateObject(" + n + ", x, " + DataType.sqlTypeToString(sQLType) + ')');
            }
            this.update(this.checkColumnIndex(n), this.convertToValue(object, sQLType));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateObject(int n, Object object, SQLType sQLType, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateObject(" + n + ", x, " + DataType.sqlTypeToString(sQLType) + ", " + n2 + ')');
            }
            this.update(this.checkColumnIndex(n), this.convertToValue(object, sQLType));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateObject(String string, Object object, SQLType sQLType) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateObject(" + JdbcResultSet.quote(string) + ", x, " + DataType.sqlTypeToString(sQLType) + ')');
            }
            this.update(this.getColumnIndex(string), this.convertToValue(object, sQLType));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateObject(String string, Object object, SQLType sQLType, int n) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateObject(" + JdbcResultSet.quote(string) + ", x, " + DataType.sqlTypeToString(sQLType) + ", " + n + ')');
            }
            this.update(this.getColumnIndex(string), this.convertToValue(object, sQLType));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateRef(int n, Ref ref) throws SQLException {
        throw this.unsupported("ref");
    }

    @Override
    public void updateRef(String string, Ref ref) throws SQLException {
        throw this.unsupported("ref");
    }

    @Override
    public void updateBlob(int n, InputStream inputStream2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBlob(" + n + ", (InputStream) x)");
            }
            this.updateBlobImpl(this.checkColumnIndex(n), inputStream2, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBlob(int n, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBlob(" + n + ", (InputStream) x, " + l + "L)");
            }
            this.updateBlobImpl(this.checkColumnIndex(n), inputStream2, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBlob(int n, Blob blob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBlob(" + n + ", (Blob) x)");
            }
            this.updateBlobImpl(this.checkColumnIndex(n), blob, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBlob(String string, Blob blob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBlob(" + JdbcResultSet.quote(string) + ", (Blob) x)");
            }
            this.updateBlobImpl(this.getColumnIndex(string), blob, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void updateBlobImpl(int n, Blob blob, long l) throws SQLException {
        this.update(n, blob == null ? ValueNull.INSTANCE : this.conn.createBlob(blob.getBinaryStream(), l));
    }

    @Override
    public void updateBlob(String string, InputStream inputStream2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBlob(" + JdbcResultSet.quote(string) + ", (InputStream) x)");
            }
            this.updateBlobImpl(this.getColumnIndex(string), inputStream2, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateBlob(String string, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateBlob(" + JdbcResultSet.quote(string) + ", (InputStream) x, " + l + "L)");
            }
            this.updateBlobImpl(this.getColumnIndex(string), inputStream2, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void updateBlobImpl(int n, InputStream inputStream2, long l) {
        this.update(n, this.conn.createBlob(inputStream2, l));
    }

    @Override
    public void updateClob(int n, Clob clob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateClob(" + n + ", (Clob) x)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), clob);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateClob(int n, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateClob(" + n + ", (Reader) x)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateClob(int n, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateClob(" + n + ", (Reader) x, " + l + "L)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateClob(String string, Clob clob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateClob(" + JdbcResultSet.quote(string) + ", (Clob) x)");
            }
            this.updateClobImpl(this.getColumnIndex(string), clob);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateClob(String string, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateClob(" + JdbcResultSet.quote(string) + ", (Reader) x)");
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateClob(String string, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateClob(" + JdbcResultSet.quote(string) + ", (Reader) x, " + l + "L)");
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateArray(int n, Array array) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateArray(" + n + ", x)");
            }
            this.updateArrayImpl(this.checkColumnIndex(n), array);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateArray(String string, Array array) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateArray(" + JdbcResultSet.quote(string) + ", x)");
            }
            this.updateArrayImpl(this.getColumnIndex(string), array);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void updateArrayImpl(int n, Array array) throws SQLException {
        this.update(n, array == null ? ValueNull.INSTANCE : ValueToObjectConverter.objectToValue(this.stat.session, array.getArray(), 40));
    }

    @Override
    public String getCursorName() throws SQLException {
        throw this.unsupported("cursorName");
    }

    @Override
    public int getRow() throws SQLException {
        try {
            this.debugCodeCall("getRow");
            this.checkClosed();
            if (this.result.isAfterLast()) {
                return 0;
            }
            long l = this.result.getRowId() + 1L;
            return l <= Integer.MAX_VALUE ? (int)l : -2;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getConcurrency() throws SQLException {
        try {
            this.debugCodeCall("getConcurrency");
            this.checkClosed();
            if (!this.updatable) {
                return 1007;
            }
            UpdatableRow updatableRow = new UpdatableRow(this.conn, this.result);
            return updatableRow.isUpdatable() ? 1008 : 1007;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getFetchDirection() throws SQLException {
        try {
            this.debugCodeCall("getFetchDirection");
            this.checkClosed();
            return 1000;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int getFetchSize() throws SQLException {
        try {
            this.debugCodeCall("getFetchSize");
            this.checkClosed();
            return this.result.getFetchSize();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setFetchSize(int n) throws SQLException {
        try {
            this.debugCodeCall("setFetchSize", n);
            this.checkClosed();
            if (n < 0) {
                throw DbException.getInvalidValueException("rows", n);
            }
            if (n > 0) {
                int n2;
                if (this.stat != null && (n2 = this.stat.getMaxRows()) > 0 && n > n2) {
                    throw DbException.getInvalidValueException("rows", n);
                }
            } else {
                n = SysProperties.SERVER_RESULT_SET_FETCH_SIZE;
            }
            this.result.setFetchSize(n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setFetchDirection(int n) throws SQLException {
        this.debugCodeCall("setFetchDirection", n);
        if (n != 1000) {
            throw this.unsupported("setFetchDirection");
        }
    }

    @Override
    public int getType() throws SQLException {
        try {
            this.debugCodeCall("getType");
            this.checkClosed();
            return this.stat == null ? 1003 : this.stat.resultSetType;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        try {
            this.debugCodeCall("isBeforeFirst");
            this.checkClosed();
            return this.result.getRowId() < 0L && this.result.hasNext();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        try {
            this.debugCodeCall("isAfterLast");
            this.checkClosed();
            return this.result.getRowId() > 0L && this.result.isAfterLast();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isFirst() throws SQLException {
        try {
            this.debugCodeCall("isFirst");
            this.checkClosed();
            return this.result.getRowId() == 0L && !this.result.isAfterLast();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isLast() throws SQLException {
        try {
            this.debugCodeCall("isLast");
            this.checkClosed();
            long l = this.result.getRowId();
            return l >= 0L && !this.result.isAfterLast() && !this.result.hasNext();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void beforeFirst() throws SQLException {
        try {
            this.debugCodeCall("beforeFirst");
            this.checkClosed();
            if (this.result.getRowId() >= 0L) {
                this.resetResult();
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void afterLast() throws SQLException {
        try {
            this.debugCodeCall("afterLast");
            this.checkClosed();
            while (this.nextRow()) {
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean first() throws SQLException {
        try {
            this.debugCodeCall("first");
            this.checkClosed();
            if (this.result.getRowId() >= 0L) {
                this.resetResult();
            }
            return this.nextRow();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean last() throws SQLException {
        try {
            this.debugCodeCall("last");
            this.checkClosed();
            if (this.result.isAfterLast()) {
                this.resetResult();
            }
            while (this.result.hasNext()) {
                this.nextRow();
            }
            return this.isOnValidRow();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean absolute(int n) throws SQLException {
        try {
            long l;
            this.debugCodeCall("absolute", n);
            this.checkClosed();
            long l2 = l = n >= 0 ? (long)n : this.result.getRowCount() + (long)n + 1L;
            if (--l < this.result.getRowId()) {
                this.resetResult();
            }
            while (this.result.getRowId() < l) {
                if (this.nextRow()) continue;
                return false;
            }
            return this.isOnValidRow();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean relative(int n) throws SQLException {
        try {
            long l;
            this.debugCodeCall("relative", n);
            this.checkClosed();
            if (n < 0) {
                l = this.result.getRowId() + (long)n + 1L;
                this.resetResult();
            } else {
                l = n;
            }
            while (l-- > 0L) {
                if (this.nextRow()) continue;
                return false;
            }
            return this.isOnValidRow();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean previous() throws SQLException {
        try {
            this.debugCodeCall("previous");
            this.checkClosed();
            return this.relative(-1);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        try {
            this.debugCodeCall("moveToInsertRow");
            this.checkUpdatable();
            this.insertRow = new Value[this.columnCount];
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        try {
            this.debugCodeCall("moveToCurrentRow");
            this.checkUpdatable();
            this.insertRow = null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        try {
            this.debugCodeCall("rowUpdated");
            return false;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean rowInserted() throws SQLException {
        try {
            this.debugCodeCall("rowInserted");
            return false;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        try {
            this.debugCodeCall("rowDeleted");
            return false;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void insertRow() throws SQLException {
        try {
            this.debugCodeCall("insertRow");
            this.checkUpdatable();
            if (this.insertRow == null) {
                throw DbException.get(90029);
            }
            this.getUpdatableRow().insertRow(this.insertRow);
            this.insertRow = null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateRow() throws SQLException {
        try {
            this.debugCodeCall("updateRow");
            this.checkUpdatable();
            if (this.insertRow != null) {
                throw DbException.get(90029);
            }
            this.checkOnValidRow();
            if (this.updateRow != null) {
                int n;
                UpdatableRow updatableRow = this.getUpdatableRow();
                Value[] valueArray = new Value[this.columnCount];
                for (n = 0; n < this.updateRow.length; ++n) {
                    valueArray[n] = this.getInternal(this.checkColumnIndex(n + 1));
                }
                updatableRow.updateRow(valueArray, this.updateRow);
                for (n = 0; n < this.updateRow.length; ++n) {
                    if (this.updateRow[n] != null) continue;
                    this.updateRow[n] = valueArray[n];
                }
                Value[] valueArray2 = updatableRow.readRow(this.updateRow);
                this.patchCurrentRow(valueArray2);
                this.updateRow = null;
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void deleteRow() throws SQLException {
        try {
            this.debugCodeCall("deleteRow");
            this.checkUpdatable();
            if (this.insertRow != null) {
                throw DbException.get(90029);
            }
            this.checkOnValidRow();
            this.getUpdatableRow().deleteRow(this.result.currentRow());
            this.updateRow = null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void refreshRow() throws SQLException {
        try {
            this.debugCodeCall("refreshRow");
            this.checkClosed();
            if (this.insertRow != null) {
                throw DbException.get(2000);
            }
            this.checkOnValidRow();
            this.patchCurrentRow(this.getUpdatableRow().readRow(this.result.currentRow()));
            this.updateRow = null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        try {
            this.debugCodeCall("cancelRowUpdates");
            this.checkClosed();
            if (this.insertRow != null) {
                throw DbException.get(2000);
            }
            this.updateRow = null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private UpdatableRow getUpdatableRow() throws SQLException {
        UpdatableRow updatableRow = new UpdatableRow(this.conn, this.result);
        if (!updatableRow.isUpdatable()) {
            throw DbException.get(90127);
        }
        return updatableRow;
    }

    private int getColumnIndex(String string) {
        int n;
        this.checkClosed();
        if (string == null) {
            throw DbException.getInvalidValueException("columnLabel", null);
        }
        if (this.columnCount >= 3) {
            Serializable serializable;
            if (this.columnLabelMap == null) {
                String string2;
                int n2;
                serializable = new HashMap();
                for (n2 = 0; n2 < this.columnCount; ++n2) {
                    string2 = StringUtils.toUpperEnglish(this.result.getAlias(n2));
                    ((HashMap)serializable).putIfAbsent(string2, n2);
                }
                for (n2 = 0; n2 < this.columnCount; ++n2) {
                    string2 = this.result.getColumnName(n2);
                    if (string2 == null) continue;
                    string2 = StringUtils.toUpperEnglish(string2);
                    ((HashMap)serializable).putIfAbsent(string2, n2);
                    String string3 = this.result.getTableName(n2);
                    if (string3 == null) continue;
                    string2 = StringUtils.toUpperEnglish(string3) + '.' + string2;
                    ((HashMap)serializable).putIfAbsent(string2, n2);
                }
                this.columnLabelMap = serializable;
                if (this.preparedStatement != null) {
                    this.preparedStatement.setCachedColumnLabelMap(this.columnLabelMap);
                }
            }
            if ((serializable = this.columnLabelMap.get(StringUtils.toUpperEnglish(string))) == null) {
                throw DbException.get(42122, string);
            }
            return (Integer)serializable + 1;
        }
        for (n = 0; n < this.columnCount; ++n) {
            if (!string.equalsIgnoreCase(this.result.getAlias(n))) continue;
            return n + 1;
        }
        n = string.indexOf(46);
        if (n > 0) {
            String string4 = string.substring(0, n);
            String string5 = string.substring(n + 1);
            for (int i = 0; i < this.columnCount; ++i) {
                if (!string4.equalsIgnoreCase(this.result.getTableName(i)) || !string5.equalsIgnoreCase(this.result.getColumnName(i))) continue;
                return i + 1;
            }
        } else {
            for (int i = 0; i < this.columnCount; ++i) {
                if (!string.equalsIgnoreCase(this.result.getColumnName(i))) continue;
                return i + 1;
            }
        }
        throw DbException.get(42122, string);
    }

    private int checkColumnIndex(int n) {
        this.checkClosed();
        if (n < 1 || n > this.columnCount) {
            throw DbException.getInvalidValueException("columnIndex", n);
        }
        return n;
    }

    void checkClosed() {
        if (this.result == null) {
            throw DbException.get(90007);
        }
        if (this.stat != null) {
            this.stat.checkClosed();
        }
        if (this.conn != null) {
            this.conn.checkClosed();
        }
    }

    private boolean isOnValidRow() {
        return this.result.getRowId() >= 0L && !this.result.isAfterLast();
    }

    private void checkOnValidRow() {
        if (!this.isOnValidRow()) {
            throw DbException.get(2000);
        }
    }

    private Value get(int n) {
        Value value = this.getInternal(n);
        this.wasNull = value == ValueNull.INSTANCE;
        return value;
    }

    public Value getInternal(int n) {
        Value[] valueArray;
        this.checkOnValidRow();
        if (this.patchedRows == null || (valueArray = this.patchedRows.get(this.result.getRowId())) == null) {
            valueArray = this.result.currentRow();
        }
        return valueArray[n - 1];
    }

    private void update(int n, Value value) {
        if (!this.triggerUpdatable) {
            this.checkUpdatable();
        }
        if (this.insertRow != null) {
            this.insertRow[n - 1] = value;
        } else {
            if (this.updateRow == null) {
                this.updateRow = new Value[this.columnCount];
            }
            this.updateRow[n - 1] = value;
        }
    }

    private boolean nextRow() {
        boolean bl;
        boolean bl2 = bl = this.result.isLazy() ? this.nextLazyRow() : this.result.next();
        if (!bl && !this.scrollable) {
            this.result.close();
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean nextLazyRow() {
        boolean bl;
        Session session;
        if (this.stat.isCancelled() || this.conn == null || (session = this.conn.getSession()) == null) {
            throw DbException.get(57014);
        }
        Session session2 = session.setThreadLocalSession();
        try {
            bl = this.result.next();
        }
        finally {
            session.resetThreadLocalSession(session2);
        }
        return bl;
    }

    private void resetResult() {
        if (!this.scrollable) {
            throw DbException.get(90128);
        }
        this.result.reset();
    }

    @Override
    public RowId getRowId(int n) throws SQLException {
        throw this.unsupported("rowId");
    }

    @Override
    public RowId getRowId(String string) throws SQLException {
        throw this.unsupported("rowId");
    }

    @Override
    public void updateRowId(int n, RowId rowId) throws SQLException {
        throw this.unsupported("rowId");
    }

    @Override
    public void updateRowId(String string, RowId rowId) throws SQLException {
        throw this.unsupported("rowId");
    }

    @Override
    public int getHoldability() throws SQLException {
        try {
            this.debugCodeCall("getHoldability");
            this.checkClosed();
            return this.conn.getHoldability();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public boolean isClosed() throws SQLException {
        try {
            this.debugCodeCall("isClosed");
            return this.result == null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNString(int n, String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNString(" + n + ", " + JdbcResultSet.quote(string) + ')');
            }
            this.update(this.checkColumnIndex(n), string == null ? ValueNull.INSTANCE : ValueVarchar.get(string, this.conn));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNString(String string, String string2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNString(" + JdbcResultSet.quote(string) + ", " + JdbcResultSet.quote(string2) + ')');
            }
            this.update(this.getColumnIndex(string), string2 == null ? ValueNull.INSTANCE : ValueVarchar.get(string2, this.conn));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNClob(int n, NClob nClob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNClob(" + n + ", (NClob) x)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), nClob);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNClob(int n, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNClob(" + n + ", (Reader) x)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNClob(int n, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNClob(" + n + ", (Reader) x, " + l + "L)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNClob(String string, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNClob(" + JdbcResultSet.quote(string) + ", (Reader) x)");
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNClob(String string, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNClob(" + JdbcResultSet.quote(string) + ", (Reader) x, " + l + "L)");
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNClob(String string, NClob nClob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNClob(" + JdbcResultSet.quote(string) + ", (NClob) x)");
            }
            this.updateClobImpl(this.getColumnIndex(string), nClob);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void updateClobImpl(int n, Clob clob) throws SQLException {
        this.update(n, clob == null ? ValueNull.INSTANCE : this.conn.createClob(clob.getCharacterStream(), -1L));
    }

    @Override
    public NClob getNClob(int n) throws SQLException {
        try {
            int n2 = JdbcResultSet.getNextId(10);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("NClob", 10, n2, "getNClob(" + n + ')');
            }
            return this.getClob(n2, this.checkColumnIndex(n));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public NClob getNClob(String string) throws SQLException {
        try {
            int n = JdbcResultSet.getNextId(10);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("NClob", 10, n, "getNClob(" + JdbcResultSet.quote(string) + ')');
            }
            return this.getClob(n, this.getColumnIndex(string));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private JdbcClob getClob(int n, int n2) {
        JdbcClob jdbcClob;
        Value value = this.getInternal(n2);
        if (value != ValueNull.INSTANCE) {
            this.wasNull = false;
            jdbcClob = new JdbcClob(this.conn, value, JdbcLob.State.WITH_VALUE, n);
        } else {
            this.wasNull = true;
            jdbcClob = null;
        }
        return jdbcClob;
    }

    @Override
    public SQLXML getSQLXML(int n) throws SQLException {
        try {
            Value value;
            int n2 = JdbcResultSet.getNextId(17);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("SQLXML", 17, n2, "getSQLXML(" + n + ')');
            }
            return (value = this.get(this.checkColumnIndex(n))) == ValueNull.INSTANCE ? null : new JdbcSQLXML(this.conn, value, JdbcLob.State.WITH_VALUE, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public SQLXML getSQLXML(String string) throws SQLException {
        try {
            Value value;
            int n = JdbcResultSet.getNextId(17);
            if (this.isDebugEnabled()) {
                this.debugCodeAssign("SQLXML", 17, n, "getSQLXML(" + JdbcResultSet.quote(string) + ')');
            }
            return (value = this.get(this.getColumnIndex(string))) == ValueNull.INSTANCE ? null : new JdbcSQLXML(this.conn, value, JdbcLob.State.WITH_VALUE, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateSQLXML(int n, SQLXML sQLXML) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateSQLXML(" + n + ", x)");
            }
            this.updateSQLXMLImpl(this.checkColumnIndex(n), sQLXML);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateSQLXML(String string, SQLXML sQLXML) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateSQLXML(" + JdbcResultSet.quote(string) + ", x)");
            }
            this.updateSQLXMLImpl(this.getColumnIndex(string), sQLXML);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void updateSQLXMLImpl(int n, SQLXML sQLXML) throws SQLException {
        this.update(n, sQLXML == null ? ValueNull.INSTANCE : this.conn.createClob(sQLXML.getCharacterStream(), -1L));
    }

    @Override
    public String getNString(int n) throws SQLException {
        try {
            this.debugCodeCall("getNString", n);
            return this.get(this.checkColumnIndex(n)).getString();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String getNString(String string) throws SQLException {
        try {
            this.debugCodeCall("getNString", string);
            return this.get(this.getColumnIndex(string)).getString();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Reader getNCharacterStream(int n) throws SQLException {
        try {
            this.debugCodeCall("getNCharacterStream", n);
            return this.get(this.checkColumnIndex(n)).getReader();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public Reader getNCharacterStream(String string) throws SQLException {
        try {
            this.debugCodeCall("getNCharacterStream", string);
            return this.get(this.getColumnIndex(string)).getReader();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNCharacterStream(int n, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNCharacterStream(" + n + ", x)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNCharacterStream(int n, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNCharacterStream(" + n + ", x, " + l + "L)");
            }
            this.updateClobImpl(this.checkColumnIndex(n), reader, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNCharacterStream(String string, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNCharacterStream(" + JdbcResultSet.quote(string) + ", x)");
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, -1L);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void updateNCharacterStream(String string, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("updateNCharacterStream(" + JdbcResultSet.quote(string) + ", x, " + l + "L)");
            }
            this.updateClobImpl(this.getColumnIndex(string), reader, l);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void updateClobImpl(int n, Reader reader, long l) {
        this.update(n, this.conn.createClob(reader, l));
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

    @Override
    public <T> T getObject(int n, Class<T> clazz) throws SQLException {
        try {
            if (clazz == null) {
                throw DbException.getInvalidValueException("type", clazz);
            }
            this.debugCodeCall("getObject", n);
            return ValueToObjectConverter.valueToObject(clazz, this.get(this.checkColumnIndex(n)), this.conn);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public <T> T getObject(String string, Class<T> clazz) throws SQLException {
        try {
            if (clazz == null) {
                throw DbException.getInvalidValueException("type", clazz);
            }
            this.debugCodeCall("getObject", string);
            return ValueToObjectConverter.valueToObject(clazz, this.get(this.getColumnIndex(string)), this.conn);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    public String toString() {
        return this.getTraceObjectName() + ": " + this.result;
    }

    private void patchCurrentRow(Value[] valueArray) {
        boolean bl = false;
        Value[] valueArray2 = this.result.currentRow();
        CompareMode compareMode = this.conn.getCompareMode();
        for (int i = 0; i < valueArray.length; ++i) {
            if (valueArray[i].compareTo(valueArray2[i], this.conn, compareMode) == 0) continue;
            bl = true;
            break;
        }
        if (this.patchedRows == null) {
            this.patchedRows = new HashMap();
        }
        Long l = this.result.getRowId();
        if (!bl) {
            this.patchedRows.remove(l);
        } else {
            this.patchedRows.put(l, valueArray);
        }
    }

    private Value convertToValue(Object object, SQLType sQLType) {
        if (object == null) {
            return ValueNull.INSTANCE;
        }
        int n = DataType.convertSQLTypeToValueType(sQLType);
        Value value = ValueToObjectConverter.objectToValue(this.conn.getSession(), object, n);
        return value.convertTo(n, (CastDataProvider)this.conn);
    }

    private Value convertToUnknownValue(Object object) {
        return ValueToObjectConverter.objectToValue(this.conn.getSession(), object, -1);
    }

    private void checkUpdatable() {
        this.checkClosed();
        if (!this.updatable) {
            throw DbException.get(90140);
        }
    }

    public Value[] getUpdateRow() {
        return this.updateRow;
    }

    public ResultInterface getResult() {
        return this.result;
    }
}

