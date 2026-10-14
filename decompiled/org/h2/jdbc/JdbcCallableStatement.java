/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc;

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
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.h2.expression.ParameterInterface;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcPreparedStatement;
import org.h2.jdbc.JdbcResultSet;
import org.h2.message.DbException;
import org.h2.value.ValueNull;

public final class JdbcCallableStatement
extends JdbcPreparedStatement
implements CallableStatement {
    private BitSet outParameters;
    private int maxOutParameters;
    private HashMap<String, Integer> namedParameters;

    JdbcCallableStatement(JdbcConnection jdbcConnection, String string, int n, int n2, int n3) {
        super(jdbcConnection, string, n, n2, n3, null);
        this.setTrace(this.session.getTrace(), 0, n);
    }

    @Override
    public int executeUpdate() throws SQLException {
        try {
            this.checkClosed();
            if (this.command.isQuery()) {
                super.executeQuery();
                return 0;
            }
            return super.executeUpdate();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public long executeLargeUpdate() throws SQLException {
        try {
            this.checkClosed();
            if (this.command.isQuery()) {
                super.executeQuery();
                return 0L;
            }
            return super.executeLargeUpdate();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void registerOutParameter(int n, int n2) throws SQLException {
        this.registerOutParameter(n);
    }

    @Override
    public void registerOutParameter(int n, int n2, String string) throws SQLException {
        this.registerOutParameter(n);
    }

    @Override
    public void registerOutParameter(int n, int n2, int n3) throws SQLException {
        this.registerOutParameter(n);
    }

    @Override
    public void registerOutParameter(String string, int n, String string2) throws SQLException {
        this.registerOutParameter(this.getIndexForName(string), n, string2);
    }

    @Override
    public void registerOutParameter(String string, int n, int n2) throws SQLException {
        this.registerOutParameter(this.getIndexForName(string), n, n2);
    }

    @Override
    public void registerOutParameter(String string, int n) throws SQLException {
        this.registerOutParameter(this.getIndexForName(string), n);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.getOpenResultSet().wasNull();
    }

    @Override
    public URL getURL(int n) throws SQLException {
        throw this.unsupported("url");
    }

    @Override
    public String getString(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getString(n);
    }

    @Override
    public boolean getBoolean(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getBoolean(n);
    }

    @Override
    public byte getByte(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getByte(n);
    }

    @Override
    public short getShort(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getShort(n);
    }

    @Override
    public int getInt(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getInt(n);
    }

    @Override
    public long getLong(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getLong(n);
    }

    @Override
    public float getFloat(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getFloat(n);
    }

    @Override
    public double getDouble(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getDouble(n);
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int n, int n2) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getBigDecimal(n, n2);
    }

    @Override
    public byte[] getBytes(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getBytes(n);
    }

    @Override
    public Date getDate(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getDate(n);
    }

    @Override
    public Time getTime(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getTime(n);
    }

    @Override
    public Timestamp getTimestamp(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getTimestamp(n);
    }

    @Override
    public Object getObject(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getObject(n);
    }

    @Override
    public BigDecimal getBigDecimal(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getBigDecimal(n);
    }

    @Override
    public Object getObject(int n, Map<String, Class<?>> map) throws SQLException {
        throw this.unsupported("map");
    }

    @Override
    public Ref getRef(int n) throws SQLException {
        throw this.unsupported("ref");
    }

    @Override
    public Blob getBlob(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getBlob(n);
    }

    @Override
    public Clob getClob(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getClob(n);
    }

    @Override
    public Array getArray(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getArray(n);
    }

    @Override
    public Date getDate(int n, Calendar calendar) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getDate(n, calendar);
    }

    @Override
    public Time getTime(int n, Calendar calendar) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getTime(n, calendar);
    }

    @Override
    public Timestamp getTimestamp(int n, Calendar calendar) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getTimestamp(n, calendar);
    }

    @Override
    public URL getURL(String string) throws SQLException {
        throw this.unsupported("url");
    }

    @Override
    public Timestamp getTimestamp(String string, Calendar calendar) throws SQLException {
        return this.getTimestamp(this.getIndexForName(string), calendar);
    }

    @Override
    public Time getTime(String string, Calendar calendar) throws SQLException {
        return this.getTime(this.getIndexForName(string), calendar);
    }

    @Override
    public Date getDate(String string, Calendar calendar) throws SQLException {
        return this.getDate(this.getIndexForName(string), calendar);
    }

    @Override
    public Array getArray(String string) throws SQLException {
        return this.getArray(this.getIndexForName(string));
    }

    @Override
    public Clob getClob(String string) throws SQLException {
        return this.getClob(this.getIndexForName(string));
    }

    @Override
    public Blob getBlob(String string) throws SQLException {
        return this.getBlob(this.getIndexForName(string));
    }

    @Override
    public Ref getRef(String string) throws SQLException {
        throw this.unsupported("ref");
    }

    @Override
    public Object getObject(String string, Map<String, Class<?>> map) throws SQLException {
        throw this.unsupported("map");
    }

    @Override
    public BigDecimal getBigDecimal(String string) throws SQLException {
        return this.getBigDecimal(this.getIndexForName(string));
    }

    @Override
    public Object getObject(String string) throws SQLException {
        return this.getObject(this.getIndexForName(string));
    }

    @Override
    public Timestamp getTimestamp(String string) throws SQLException {
        return this.getTimestamp(this.getIndexForName(string));
    }

    @Override
    public Time getTime(String string) throws SQLException {
        return this.getTime(this.getIndexForName(string));
    }

    @Override
    public Date getDate(String string) throws SQLException {
        return this.getDate(this.getIndexForName(string));
    }

    @Override
    public byte[] getBytes(String string) throws SQLException {
        return this.getBytes(this.getIndexForName(string));
    }

    @Override
    public double getDouble(String string) throws SQLException {
        return this.getDouble(this.getIndexForName(string));
    }

    @Override
    public float getFloat(String string) throws SQLException {
        return this.getFloat(this.getIndexForName(string));
    }

    @Override
    public long getLong(String string) throws SQLException {
        return this.getLong(this.getIndexForName(string));
    }

    @Override
    public int getInt(String string) throws SQLException {
        return this.getInt(this.getIndexForName(string));
    }

    @Override
    public short getShort(String string) throws SQLException {
        return this.getShort(this.getIndexForName(string));
    }

    @Override
    public byte getByte(String string) throws SQLException {
        return this.getByte(this.getIndexForName(string));
    }

    @Override
    public boolean getBoolean(String string) throws SQLException {
        return this.getBoolean(this.getIndexForName(string));
    }

    @Override
    public String getString(String string) throws SQLException {
        return this.getString(this.getIndexForName(string));
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
    public NClob getNClob(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getNClob(n);
    }

    @Override
    public NClob getNClob(String string) throws SQLException {
        return this.getNClob(this.getIndexForName(string));
    }

    @Override
    public SQLXML getSQLXML(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getSQLXML(n);
    }

    @Override
    public SQLXML getSQLXML(String string) throws SQLException {
        return this.getSQLXML(this.getIndexForName(string));
    }

    @Override
    public String getNString(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getNString(n);
    }

    @Override
    public String getNString(String string) throws SQLException {
        return this.getNString(this.getIndexForName(string));
    }

    @Override
    public Reader getNCharacterStream(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getNCharacterStream(n);
    }

    @Override
    public Reader getNCharacterStream(String string) throws SQLException {
        return this.getNCharacterStream(this.getIndexForName(string));
    }

    @Override
    public Reader getCharacterStream(int n) throws SQLException {
        this.checkRegistered(n);
        return this.getOpenResultSet().getCharacterStream(n);
    }

    @Override
    public Reader getCharacterStream(String string) throws SQLException {
        return this.getCharacterStream(this.getIndexForName(string));
    }

    @Override
    public void setNull(String string, int n, String string2) throws SQLException {
        this.setNull(this.getIndexForName(string), n, string2);
    }

    @Override
    public void setNull(String string, int n) throws SQLException {
        this.setNull(this.getIndexForName(string), n);
    }

    @Override
    public void setTimestamp(String string, Timestamp timestamp, Calendar calendar) throws SQLException {
        this.setTimestamp(this.getIndexForName(string), timestamp, calendar);
    }

    @Override
    public void setTime(String string, Time time, Calendar calendar) throws SQLException {
        this.setTime(this.getIndexForName(string), time, calendar);
    }

    @Override
    public void setDate(String string, Date date, Calendar calendar) throws SQLException {
        this.setDate(this.getIndexForName(string), date, calendar);
    }

    @Override
    public void setCharacterStream(String string, Reader reader, int n) throws SQLException {
        this.setCharacterStream(this.getIndexForName(string), reader, n);
    }

    @Override
    public void setObject(String string, Object object) throws SQLException {
        this.setObject(this.getIndexForName(string), object);
    }

    @Override
    public void setObject(String string, Object object, int n) throws SQLException {
        this.setObject(this.getIndexForName(string), object, n);
    }

    @Override
    public void setObject(String string, Object object, int n, int n2) throws SQLException {
        this.setObject(this.getIndexForName(string), object, n, n2);
    }

    @Override
    public void setObject(String string, Object object, SQLType sQLType) throws SQLException {
        this.setObject(this.getIndexForName(string), object, sQLType);
    }

    @Override
    public void setObject(String string, Object object, SQLType sQLType, int n) throws SQLException {
        this.setObject(this.getIndexForName(string), object, sQLType, n);
    }

    @Override
    public void setBinaryStream(String string, InputStream inputStream2, int n) throws SQLException {
        this.setBinaryStream(this.getIndexForName(string), inputStream2, n);
    }

    @Override
    public void setAsciiStream(String string, InputStream inputStream2, long l) throws SQLException {
        this.setAsciiStream(this.getIndexForName(string), inputStream2, l);
    }

    @Override
    public void setTimestamp(String string, Timestamp timestamp) throws SQLException {
        this.setTimestamp(this.getIndexForName(string), timestamp);
    }

    @Override
    public void setTime(String string, Time time) throws SQLException {
        this.setTime(this.getIndexForName(string), time);
    }

    @Override
    public void setDate(String string, Date date) throws SQLException {
        this.setDate(this.getIndexForName(string), date);
    }

    @Override
    public void setBytes(String string, byte[] byArray) throws SQLException {
        this.setBytes(this.getIndexForName(string), byArray);
    }

    @Override
    public void setString(String string, String string2) throws SQLException {
        this.setString(this.getIndexForName(string), string2);
    }

    @Override
    public void setBigDecimal(String string, BigDecimal bigDecimal) throws SQLException {
        this.setBigDecimal(this.getIndexForName(string), bigDecimal);
    }

    @Override
    public void setDouble(String string, double d) throws SQLException {
        this.setDouble(this.getIndexForName(string), d);
    }

    @Override
    public void setFloat(String string, float f) throws SQLException {
        this.setFloat(this.getIndexForName(string), f);
    }

    @Override
    public void setLong(String string, long l) throws SQLException {
        this.setLong(this.getIndexForName(string), l);
    }

    @Override
    public void setInt(String string, int n) throws SQLException {
        this.setInt(this.getIndexForName(string), n);
    }

    @Override
    public void setShort(String string, short s2) throws SQLException {
        this.setShort(this.getIndexForName(string), s2);
    }

    @Override
    public void setByte(String string, byte by) throws SQLException {
        this.setByte(this.getIndexForName(string), by);
    }

    @Override
    public void setBoolean(String string, boolean bl) throws SQLException {
        this.setBoolean(this.getIndexForName(string), bl);
    }

    @Override
    public void setURL(String string, URL uRL) throws SQLException {
        throw this.unsupported("url");
    }

    @Override
    public void setRowId(String string, RowId rowId) throws SQLException {
        throw this.unsupported("rowId");
    }

    @Override
    public void setNString(String string, String string2) throws SQLException {
        this.setNString(this.getIndexForName(string), string2);
    }

    @Override
    public void setNCharacterStream(String string, Reader reader, long l) throws SQLException {
        this.setNCharacterStream(this.getIndexForName(string), reader, l);
    }

    @Override
    public void setNClob(String string, NClob nClob) throws SQLException {
        this.setNClob(this.getIndexForName(string), nClob);
    }

    @Override
    public void setClob(String string, Reader reader, long l) throws SQLException {
        this.setClob(this.getIndexForName(string), reader, l);
    }

    @Override
    public void setBlob(String string, InputStream inputStream2, long l) throws SQLException {
        this.setBlob(this.getIndexForName(string), inputStream2, l);
    }

    @Override
    public void setNClob(String string, Reader reader, long l) throws SQLException {
        this.setNClob(this.getIndexForName(string), reader, l);
    }

    @Override
    public void setBlob(String string, Blob blob) throws SQLException {
        this.setBlob(this.getIndexForName(string), blob);
    }

    @Override
    public void setClob(String string, Clob clob) throws SQLException {
        this.setClob(this.getIndexForName(string), clob);
    }

    @Override
    public void setAsciiStream(String string, InputStream inputStream2) throws SQLException {
        this.setAsciiStream(this.getIndexForName(string), inputStream2);
    }

    @Override
    public void setAsciiStream(String string, InputStream inputStream2, int n) throws SQLException {
        this.setAsciiStream(this.getIndexForName(string), inputStream2, n);
    }

    @Override
    public void setBinaryStream(String string, InputStream inputStream2) throws SQLException {
        this.setBinaryStream(this.getIndexForName(string), inputStream2);
    }

    @Override
    public void setBinaryStream(String string, InputStream inputStream2, long l) throws SQLException {
        this.setBinaryStream(this.getIndexForName(string), inputStream2, l);
    }

    @Override
    public void setBlob(String string, InputStream inputStream2) throws SQLException {
        this.setBlob(this.getIndexForName(string), inputStream2);
    }

    @Override
    public void setCharacterStream(String string, Reader reader) throws SQLException {
        this.setCharacterStream(this.getIndexForName(string), reader);
    }

    @Override
    public void setCharacterStream(String string, Reader reader, long l) throws SQLException {
        this.setCharacterStream(this.getIndexForName(string), reader, l);
    }

    @Override
    public void setClob(String string, Reader reader) throws SQLException {
        this.setClob(this.getIndexForName(string), reader);
    }

    @Override
    public void setNCharacterStream(String string, Reader reader) throws SQLException {
        this.setNCharacterStream(this.getIndexForName(string), reader);
    }

    @Override
    public void setNClob(String string, Reader reader) throws SQLException {
        this.setNClob(this.getIndexForName(string), reader);
    }

    @Override
    public void setSQLXML(String string, SQLXML sQLXML) throws SQLException {
        this.setSQLXML(this.getIndexForName(string), sQLXML);
    }

    @Override
    public <T> T getObject(int n, Class<T> clazz) throws SQLException {
        return this.getOpenResultSet().getObject(n, clazz);
    }

    @Override
    public <T> T getObject(String string, Class<T> clazz) throws SQLException {
        return this.getObject(this.getIndexForName(string), clazz);
    }

    private ResultSetMetaData getCheckedMetaData() throws SQLException {
        ResultSetMetaData resultSetMetaData = this.getMetaData();
        if (resultSetMetaData == null) {
            throw DbException.getUnsupportedException("Supported only for calling stored procedures");
        }
        return resultSetMetaData;
    }

    private void checkIndexBounds(int n) {
        this.checkClosed();
        if (n < 1 || n > this.maxOutParameters) {
            throw DbException.getInvalidValueException("parameterIndex", n);
        }
    }

    private void registerOutParameter(int n) throws SQLException {
        try {
            this.checkClosed();
            if (this.outParameters == null) {
                this.maxOutParameters = Math.min(this.getParameterMetaData().getParameterCount(), this.getCheckedMetaData().getColumnCount());
                this.outParameters = new BitSet();
            }
            this.checkIndexBounds(n);
            ParameterInterface parameterInterface = this.command.getParameters().get(--n);
            if (!parameterInterface.isValueSet()) {
                parameterInterface.setValue(ValueNull.INSTANCE, false);
            }
            this.outParameters.set(n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void checkRegistered(int n) throws SQLException {
        try {
            this.checkIndexBounds(n);
            if (!this.outParameters.get(n - 1)) {
                throw DbException.getInvalidValueException("parameterIndex", n);
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private int getIndexForName(String string) throws SQLException {
        try {
            Object object;
            this.checkClosed();
            if (this.namedParameters == null) {
                object = this.getCheckedMetaData();
                int n = object.getColumnCount();
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                for (int i = 1; i <= n; ++i) {
                    hashMap.put(object.getColumnLabel(i), i);
                }
                this.namedParameters = hashMap;
            }
            if ((object = this.namedParameters.get(string)) == null) {
                throw DbException.getInvalidValueException("parameterName", string);
            }
            return (Integer)object;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private JdbcResultSet getOpenResultSet() throws SQLException {
        try {
            this.checkClosed();
            if (this.resultSet == null) {
                throw DbException.get(2000);
            }
            if (this.resultSet.isBeforeFirst()) {
                this.resultSet.next();
            }
            return this.resultSet;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }
}

