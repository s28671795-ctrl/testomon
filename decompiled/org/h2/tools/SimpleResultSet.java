/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import org.h2.message.DbException;
import org.h2.tools.SimpleRowSource;
import org.h2.util.Bits;
import org.h2.util.JdbcUtils;
import org.h2.util.MathUtils;
import org.h2.util.SimpleColumnInfo;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.Value;
import org.h2.value.ValueToObjectConverter;

public class SimpleResultSet
implements ResultSet,
ResultSetMetaData {
    private ArrayList<Object[]> rows;
    private Object[] currentRow;
    private int rowId = -1;
    private boolean wasNull;
    private SimpleRowSource source;
    private ArrayList<SimpleColumnInfo> columns = Utils.newSmallArrayList();
    private boolean autoClose = true;

    public SimpleResultSet() {
        this.rows = Utils.newSmallArrayList();
    }

    public SimpleResultSet(SimpleRowSource simpleRowSource) {
        this.source = simpleRowSource;
    }

    public void addColumn(String string, int n, int n2, int n3) {
        int n4 = DataType.convertSQLTypeToValueType(n);
        this.addColumn(string, n, Value.getTypeName(n4), n2, n3);
    }

    public void addColumn(String string, int n, String string2, int n2, int n3) {
        if (this.rows != null && !this.rows.isEmpty()) {
            throw new IllegalStateException("Cannot add a column after adding rows");
        }
        if (string == null) {
            string = "C" + (this.columns.size() + 1);
        }
        this.columns.add(new SimpleColumnInfo(string, n, string2, n2, n3));
    }

    public void addRow(Object ... objectArray) {
        if (this.rows == null) {
            throw new IllegalStateException("Cannot add a row when using RowSource");
        }
        this.rows.add(objectArray);
    }

    @Override
    public int getConcurrency() {
        return 1007;
    }

    @Override
    public int getFetchDirection() {
        return 1000;
    }

    @Override
    public int getFetchSize() {
        return 0;
    }

    @Override
    public int getRow() {
        return this.currentRow == null ? 0 : this.rowId + 1;
    }

    @Override
    public int getType() {
        if (this.autoClose) {
            return 1003;
        }
        return 1004;
    }

    @Override
    public void close() {
        this.currentRow = null;
        this.rows = null;
        this.columns = null;
        this.rowId = -1;
        if (this.source != null) {
            this.source.close();
            this.source = null;
        }
    }

    @Override
    public boolean next() throws SQLException {
        if (this.source != null) {
            ++this.rowId;
            this.currentRow = this.source.readRow();
            if (this.currentRow != null) {
                return true;
            }
        } else if (this.rows != null && this.rowId < this.rows.size()) {
            ++this.rowId;
            if (this.rowId < this.rows.size()) {
                this.currentRow = this.rows.get(this.rowId);
                return true;
            }
            this.currentRow = null;
        }
        if (this.autoClose) {
            this.close();
        }
        return false;
    }

    @Override
    public void beforeFirst() throws SQLException {
        if (this.autoClose) {
            throw DbException.getJdbcSQLException(90128);
        }
        this.rowId = -1;
        if (this.source != null) {
            this.source.reset();
        }
    }

    @Override
    public boolean wasNull() {
        return this.wasNull;
    }

    @Override
    public int findColumn(String string) throws SQLException {
        if (string != null && this.columns != null) {
            int n = this.columns.size();
            for (int i = 0; i < n; ++i) {
                if (!string.equalsIgnoreCase(this.getColumn((int)i).name)) continue;
                return i + 1;
            }
        }
        throw DbException.getJdbcSQLException(42122, string);
    }

    @Override
    public ResultSetMetaData getMetaData() {
        return this;
    }

    @Override
    public SQLWarning getWarnings() {
        return null;
    }

    @Override
    public Statement getStatement() {
        return null;
    }

    @Override
    public void clearWarnings() {
    }

    @Override
    public Array getArray(int n) throws SQLException {
        Object[] objectArray = (Object[])this.get(n);
        return objectArray == null ? null : new SimpleArray(objectArray);
    }

    @Override
    public Array getArray(String string) throws SQLException {
        return this.getArray(this.findColumn(string));
    }

    @Override
    public InputStream getAsciiStream(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public InputStream getAsciiStream(String string) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public BigDecimal getBigDecimal(int n) throws SQLException {
        Object object = this.get(n);
        if (object != null && !(object instanceof BigDecimal)) {
            object = new BigDecimal(object.toString());
        }
        return (BigDecimal)object;
    }

    @Override
    public BigDecimal getBigDecimal(String string) throws SQLException {
        return this.getBigDecimal(this.findColumn(string));
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(int n, int n2) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    @Deprecated
    public BigDecimal getBigDecimal(String string, int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public InputStream getBinaryStream(int n) throws SQLException {
        return SimpleResultSet.asInputStream(this.get(n));
    }

    private static InputStream asInputStream(Object object) throws SQLException {
        if (object == null) {
            return null;
        }
        if (object instanceof Blob) {
            return ((Blob)object).getBinaryStream();
        }
        return (InputStream)object;
    }

    @Override
    public InputStream getBinaryStream(String string) throws SQLException {
        return this.getBinaryStream(this.findColumn(string));
    }

    @Override
    public Blob getBlob(int n) throws SQLException {
        return (Blob)this.get(n);
    }

    @Override
    public Blob getBlob(String string) throws SQLException {
        return this.getBlob(this.findColumn(string));
    }

    @Override
    public boolean getBoolean(int n) throws SQLException {
        Object object = this.get(n);
        if (object == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        if (object instanceof Number) {
            Number number = (Number)object;
            if (number instanceof Double || number instanceof Float) {
                return number.doubleValue() != 0.0;
            }
            if (number instanceof BigDecimal) {
                return ((BigDecimal)number).signum() != 0;
            }
            if (number instanceof BigInteger) {
                return ((BigInteger)number).signum() != 0;
            }
            return number.longValue() != 0L;
        }
        return Utils.parseBoolean(object.toString(), false, true);
    }

    @Override
    public boolean getBoolean(String string) throws SQLException {
        return this.getBoolean(this.findColumn(string));
    }

    @Override
    public byte getByte(int n) throws SQLException {
        Object object = this.get(n);
        if (object != null && !(object instanceof Number)) {
            object = Byte.decode(object.toString());
        }
        return object == null ? (byte)0 : ((Number)object).byteValue();
    }

    @Override
    public byte getByte(String string) throws SQLException {
        return this.getByte(this.findColumn(string));
    }

    @Override
    public byte[] getBytes(int n) throws SQLException {
        Object object = this.get(n);
        if (object == null || object instanceof byte[]) {
            return (byte[])object;
        }
        if (object instanceof UUID) {
            return Bits.uuidToBytes((UUID)object);
        }
        return JdbcUtils.serialize(object, null);
    }

    @Override
    public byte[] getBytes(String string) throws SQLException {
        return this.getBytes(this.findColumn(string));
    }

    @Override
    public Reader getCharacterStream(int n) throws SQLException {
        return SimpleResultSet.asReader(this.get(n));
    }

    private static Reader asReader(Object object) throws SQLException {
        if (object == null) {
            return null;
        }
        if (object instanceof Clob) {
            return ((Clob)object).getCharacterStream();
        }
        return (Reader)object;
    }

    @Override
    public Reader getCharacterStream(String string) throws SQLException {
        return this.getCharacterStream(this.findColumn(string));
    }

    @Override
    public Clob getClob(int n) throws SQLException {
        return (Clob)this.get(n);
    }

    @Override
    public Clob getClob(String string) throws SQLException {
        return this.getClob(this.findColumn(string));
    }

    @Override
    public Date getDate(int n) throws SQLException {
        return (Date)this.get(n);
    }

    @Override
    public Date getDate(String string) throws SQLException {
        return this.getDate(this.findColumn(string));
    }

    @Override
    public Date getDate(int n, Calendar calendar) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public Date getDate(String string, Calendar calendar) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public double getDouble(int n) throws SQLException {
        Object object = this.get(n);
        if (object != null && !(object instanceof Number)) {
            return Double.parseDouble(object.toString());
        }
        return object == null ? 0.0 : ((Number)object).doubleValue();
    }

    @Override
    public double getDouble(String string) throws SQLException {
        return this.getDouble(this.findColumn(string));
    }

    @Override
    public float getFloat(int n) throws SQLException {
        Object object = this.get(n);
        if (object != null && !(object instanceof Number)) {
            return Float.parseFloat(object.toString());
        }
        return object == null ? 0.0f : ((Number)object).floatValue();
    }

    @Override
    public float getFloat(String string) throws SQLException {
        return this.getFloat(this.findColumn(string));
    }

    @Override
    public int getInt(int n) throws SQLException {
        Object object = this.get(n);
        if (object != null && !(object instanceof Number)) {
            object = Integer.decode(object.toString());
        }
        return object == null ? 0 : ((Number)object).intValue();
    }

    @Override
    public int getInt(String string) throws SQLException {
        return this.getInt(this.findColumn(string));
    }

    @Override
    public long getLong(int n) throws SQLException {
        Object object = this.get(n);
        if (object != null && !(object instanceof Number)) {
            object = Long.decode(object.toString());
        }
        return object == null ? 0L : ((Number)object).longValue();
    }

    @Override
    public long getLong(String string) throws SQLException {
        return this.getLong(this.findColumn(string));
    }

    @Override
    public Reader getNCharacterStream(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public Reader getNCharacterStream(String string) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public NClob getNClob(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public NClob getNClob(String string) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public String getNString(int n) throws SQLException {
        return this.getString(n);
    }

    @Override
    public String getNString(String string) throws SQLException {
        return this.getString(string);
    }

    @Override
    public Object getObject(int n) throws SQLException {
        return this.get(n);
    }

    @Override
    public Object getObject(String string) throws SQLException {
        return this.getObject(this.findColumn(string));
    }

    @Override
    public <T> T getObject(int n, Class<T> clazz) throws SQLException {
        if (this.get(n) == null) {
            return null;
        }
        if (clazz == BigDecimal.class) {
            return (T)this.getBigDecimal(n);
        }
        if (clazz == BigInteger.class) {
            return (T)this.getBigDecimal(n).toBigInteger();
        }
        if (clazz == String.class) {
            return (T)this.getString(n);
        }
        if (clazz == Boolean.class) {
            return (T)Boolean.valueOf(this.getBoolean(n));
        }
        if (clazz == Byte.class) {
            return (T)Byte.valueOf(this.getByte(n));
        }
        if (clazz == Short.class) {
            return (T)Short.valueOf(this.getShort(n));
        }
        if (clazz == Integer.class) {
            return (T)Integer.valueOf(this.getInt(n));
        }
        if (clazz == Long.class) {
            return (T)Long.valueOf(this.getLong(n));
        }
        if (clazz == Float.class) {
            return (T)Float.valueOf(this.getFloat(n));
        }
        if (clazz == Double.class) {
            return (T)Double.valueOf(this.getDouble(n));
        }
        if (clazz == Date.class) {
            return (T)this.getDate(n);
        }
        if (clazz == Time.class) {
            return (T)this.getTime(n);
        }
        if (clazz == Timestamp.class) {
            return (T)this.getTimestamp(n);
        }
        if (clazz == UUID.class) {
            return (T)this.getObject(n);
        }
        if (clazz == byte[].class) {
            return (T)this.getBytes(n);
        }
        if (clazz == Array.class) {
            return (T)this.getArray(n);
        }
        if (clazz == Blob.class) {
            return (T)this.getBlob(n);
        }
        if (clazz == Clob.class) {
            return (T)this.getClob(n);
        }
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public <T> T getObject(String string, Class<T> clazz) throws SQLException {
        return this.getObject(this.findColumn(string), clazz);
    }

    @Override
    public Object getObject(int n, Map<String, Class<?>> map) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public Object getObject(String string, Map<String, Class<?>> map) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public Ref getRef(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public Ref getRef(String string) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public RowId getRowId(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public RowId getRowId(String string) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public short getShort(int n) throws SQLException {
        Object object = this.get(n);
        if (object != null && !(object instanceof Number)) {
            object = Short.decode(object.toString());
        }
        return object == null ? (short)0 : ((Number)object).shortValue();
    }

    @Override
    public short getShort(String string) throws SQLException {
        return this.getShort(this.findColumn(string));
    }

    @Override
    public SQLXML getSQLXML(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public SQLXML getSQLXML(String string) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public String getString(int n) throws SQLException {
        Object object = this.get(n);
        if (object == null) {
            return null;
        }
        switch (this.columns.get((int)(n - 1)).type) {
            case 2005: {
                Clob clob = (Clob)object;
                return clob.getSubString(1L, MathUtils.convertLongToInt(clob.length()));
            }
        }
        return object.toString();
    }

    @Override
    public String getString(String string) throws SQLException {
        return this.getString(this.findColumn(string));
    }

    @Override
    public Time getTime(int n) throws SQLException {
        return (Time)this.get(n);
    }

    @Override
    public Time getTime(String string) throws SQLException {
        return this.getTime(this.findColumn(string));
    }

    @Override
    public Time getTime(int n, Calendar calendar) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public Time getTime(String string, Calendar calendar) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public Timestamp getTimestamp(int n) throws SQLException {
        return (Timestamp)this.get(n);
    }

    @Override
    public Timestamp getTimestamp(String string) throws SQLException {
        return this.getTimestamp(this.findColumn(string));
    }

    @Override
    public Timestamp getTimestamp(int n, Calendar calendar) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public Timestamp getTimestamp(String string, Calendar calendar) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    @Deprecated
    public InputStream getUnicodeStream(String string) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public URL getURL(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public URL getURL(String string) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void updateArray(int n, Array array) throws SQLException {
        this.update(n, (Object)array);
    }

    @Override
    public void updateArray(String string, Array array) throws SQLException {
        this.update(string, (Object)array);
    }

    @Override
    public void updateAsciiStream(int n, InputStream inputStream2) throws SQLException {
        this.update(n, (Object)inputStream2);
    }

    @Override
    public void updateAsciiStream(String string, InputStream inputStream2) throws SQLException {
        this.update(string, (Object)inputStream2);
    }

    @Override
    public void updateAsciiStream(int n, InputStream inputStream2, int n2) throws SQLException {
        this.update(n, (Object)inputStream2);
    }

    @Override
    public void updateAsciiStream(String string, InputStream inputStream2, int n) throws SQLException {
        this.update(string, (Object)inputStream2);
    }

    @Override
    public void updateAsciiStream(int n, InputStream inputStream2, long l) throws SQLException {
        this.update(n, (Object)inputStream2);
    }

    @Override
    public void updateAsciiStream(String string, InputStream inputStream2, long l) throws SQLException {
        this.update(string, (Object)inputStream2);
    }

    @Override
    public void updateBigDecimal(int n, BigDecimal bigDecimal) throws SQLException {
        this.update(n, (Object)bigDecimal);
    }

    @Override
    public void updateBigDecimal(String string, BigDecimal bigDecimal) throws SQLException {
        this.update(string, (Object)bigDecimal);
    }

    @Override
    public void updateBinaryStream(int n, InputStream inputStream2) throws SQLException {
        this.update(n, (Object)inputStream2);
    }

    @Override
    public void updateBinaryStream(String string, InputStream inputStream2) throws SQLException {
        this.update(string, (Object)inputStream2);
    }

    @Override
    public void updateBinaryStream(int n, InputStream inputStream2, int n2) throws SQLException {
        this.update(n, (Object)inputStream2);
    }

    @Override
    public void updateBinaryStream(String string, InputStream inputStream2, int n) throws SQLException {
        this.update(string, (Object)inputStream2);
    }

    @Override
    public void updateBinaryStream(int n, InputStream inputStream2, long l) throws SQLException {
        this.update(n, (Object)inputStream2);
    }

    @Override
    public void updateBinaryStream(String string, InputStream inputStream2, long l) throws SQLException {
        this.update(string, (Object)inputStream2);
    }

    @Override
    public void updateBlob(int n, Blob blob) throws SQLException {
        this.update(n, (Object)blob);
    }

    @Override
    public void updateBlob(String string, Blob blob) throws SQLException {
        this.update(string, (Object)blob);
    }

    @Override
    public void updateBlob(int n, InputStream inputStream2) throws SQLException {
        this.update(n, (Object)inputStream2);
    }

    @Override
    public void updateBlob(String string, InputStream inputStream2) throws SQLException {
        this.update(string, (Object)inputStream2);
    }

    @Override
    public void updateBlob(int n, InputStream inputStream2, long l) throws SQLException {
        this.update(n, (Object)inputStream2);
    }

    @Override
    public void updateBlob(String string, InputStream inputStream2, long l) throws SQLException {
        this.update(string, (Object)inputStream2);
    }

    @Override
    public void updateBoolean(int n, boolean bl) throws SQLException {
        this.update(n, (Object)bl);
    }

    @Override
    public void updateBoolean(String string, boolean bl) throws SQLException {
        this.update(string, (Object)bl);
    }

    @Override
    public void updateByte(int n, byte by) throws SQLException {
        this.update(n, (Object)by);
    }

    @Override
    public void updateByte(String string, byte by) throws SQLException {
        this.update(string, (Object)by);
    }

    @Override
    public void updateBytes(int n, byte[] byArray) throws SQLException {
        this.update(n, (Object)byArray);
    }

    @Override
    public void updateBytes(String string, byte[] byArray) throws SQLException {
        this.update(string, (Object)byArray);
    }

    @Override
    public void updateCharacterStream(int n, Reader reader) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateCharacterStream(String string, Reader reader) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateCharacterStream(int n, Reader reader, int n2) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateCharacterStream(String string, Reader reader, int n) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateCharacterStream(int n, Reader reader, long l) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateCharacterStream(String string, Reader reader, long l) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateClob(int n, Clob clob) throws SQLException {
        this.update(n, (Object)clob);
    }

    @Override
    public void updateClob(String string, Clob clob) throws SQLException {
        this.update(string, (Object)clob);
    }

    @Override
    public void updateClob(int n, Reader reader) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateClob(String string, Reader reader) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateClob(int n, Reader reader, long l) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateClob(String string, Reader reader, long l) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateDate(int n, Date date) throws SQLException {
        this.update(n, (Object)date);
    }

    @Override
    public void updateDate(String string, Date date) throws SQLException {
        this.update(string, (Object)date);
    }

    @Override
    public void updateDouble(int n, double d) throws SQLException {
        this.update(n, (Object)d);
    }

    @Override
    public void updateDouble(String string, double d) throws SQLException {
        this.update(string, (Object)d);
    }

    @Override
    public void updateFloat(int n, float f) throws SQLException {
        this.update(n, (Object)Float.valueOf(f));
    }

    @Override
    public void updateFloat(String string, float f) throws SQLException {
        this.update(string, (Object)Float.valueOf(f));
    }

    @Override
    public void updateInt(int n, int n2) throws SQLException {
        this.update(n, (Object)n2);
    }

    @Override
    public void updateInt(String string, int n) throws SQLException {
        this.update(string, (Object)n);
    }

    @Override
    public void updateLong(int n, long l) throws SQLException {
        this.update(n, (Object)l);
    }

    @Override
    public void updateLong(String string, long l) throws SQLException {
        this.update(string, (Object)l);
    }

    @Override
    public void updateNCharacterStream(int n, Reader reader) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateNCharacterStream(String string, Reader reader) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateNCharacterStream(int n, Reader reader, long l) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateNCharacterStream(String string, Reader reader, long l) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateNClob(int n, NClob nClob) throws SQLException {
        this.update(n, (Object)nClob);
    }

    @Override
    public void updateNClob(String string, NClob nClob) throws SQLException {
        this.update(string, (Object)nClob);
    }

    @Override
    public void updateNClob(int n, Reader reader) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateNClob(String string, Reader reader) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateNClob(int n, Reader reader, long l) throws SQLException {
        this.update(n, (Object)reader);
    }

    @Override
    public void updateNClob(String string, Reader reader, long l) throws SQLException {
        this.update(string, (Object)reader);
    }

    @Override
    public void updateNString(int n, String string) throws SQLException {
        this.update(n, (Object)string);
    }

    @Override
    public void updateNString(String string, String string2) throws SQLException {
        this.update(string, (Object)string2);
    }

    @Override
    public void updateNull(int n) throws SQLException {
        this.update(n, null);
    }

    @Override
    public void updateNull(String string) throws SQLException {
        this.update(string, null);
    }

    @Override
    public void updateObject(int n, Object object) throws SQLException {
        this.update(n, object);
    }

    @Override
    public void updateObject(String string, Object object) throws SQLException {
        this.update(string, object);
    }

    @Override
    public void updateObject(int n, Object object, int n2) throws SQLException {
        this.update(n, object);
    }

    @Override
    public void updateObject(String string, Object object, int n) throws SQLException {
        this.update(string, object);
    }

    @Override
    public void updateRef(int n, Ref ref) throws SQLException {
        this.update(n, (Object)ref);
    }

    @Override
    public void updateRef(String string, Ref ref) throws SQLException {
        this.update(string, (Object)ref);
    }

    @Override
    public void updateRowId(int n, RowId rowId) throws SQLException {
        this.update(n, (Object)rowId);
    }

    @Override
    public void updateRowId(String string, RowId rowId) throws SQLException {
        this.update(string, (Object)rowId);
    }

    @Override
    public void updateShort(int n, short s2) throws SQLException {
        this.update(n, (Object)s2);
    }

    @Override
    public void updateShort(String string, short s2) throws SQLException {
        this.update(string, (Object)s2);
    }

    @Override
    public void updateSQLXML(int n, SQLXML sQLXML) throws SQLException {
        this.update(n, (Object)sQLXML);
    }

    @Override
    public void updateSQLXML(String string, SQLXML sQLXML) throws SQLException {
        this.update(string, (Object)sQLXML);
    }

    @Override
    public void updateString(int n, String string) throws SQLException {
        this.update(n, (Object)string);
    }

    @Override
    public void updateString(String string, String string2) throws SQLException {
        this.update(string, (Object)string2);
    }

    @Override
    public void updateTime(int n, Time time) throws SQLException {
        this.update(n, (Object)time);
    }

    @Override
    public void updateTime(String string, Time time) throws SQLException {
        this.update(string, (Object)time);
    }

    @Override
    public void updateTimestamp(int n, Timestamp timestamp) throws SQLException {
        this.update(n, (Object)timestamp);
    }

    @Override
    public void updateTimestamp(String string, Timestamp timestamp) throws SQLException {
        this.update(string, (Object)timestamp);
    }

    @Override
    public int getColumnCount() {
        return this.columns.size();
    }

    @Override
    public int getColumnDisplaySize(int n) {
        return 15;
    }

    @Override
    public int getColumnType(int n) throws SQLException {
        return this.getColumn((int)(n - 1)).type;
    }

    @Override
    public int getPrecision(int n) throws SQLException {
        return this.getColumn((int)(n - 1)).precision;
    }

    @Override
    public int getScale(int n) throws SQLException {
        return this.getColumn((int)(n - 1)).scale;
    }

    @Override
    public int isNullable(int n) {
        return 2;
    }

    @Override
    public boolean isAutoIncrement(int n) {
        return false;
    }

    @Override
    public boolean isCaseSensitive(int n) {
        return true;
    }

    @Override
    public boolean isCurrency(int n) {
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int n) {
        return false;
    }

    @Override
    public boolean isReadOnly(int n) {
        return true;
    }

    @Override
    public boolean isSearchable(int n) {
        return true;
    }

    @Override
    public boolean isSigned(int n) {
        return true;
    }

    @Override
    public boolean isWritable(int n) {
        return false;
    }

    @Override
    public String getCatalogName(int n) {
        return "";
    }

    @Override
    public String getColumnClassName(int n) throws SQLException {
        int n2 = DataType.getValueTypeFromResultSet(this, n);
        return ValueToObjectConverter.getDefaultClass(n2, true).getName();
    }

    @Override
    public String getColumnLabel(int n) throws SQLException {
        return this.getColumn((int)(n - 1)).name;
    }

    @Override
    public String getColumnName(int n) throws SQLException {
        return this.getColumnLabel(n);
    }

    @Override
    public String getColumnTypeName(int n) throws SQLException {
        return this.getColumn((int)(n - 1)).typeName;
    }

    @Override
    public String getSchemaName(int n) {
        return "";
    }

    @Override
    public String getTableName(int n) {
        return "";
    }

    @Override
    public void afterLast() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void deleteRow() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void insertRow() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void refreshRow() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void updateRow() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean first() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean isFirst() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean isLast() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean last() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean previous() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean rowInserted() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        return true;
    }

    @Override
    public void setFetchDirection(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public void setFetchSize(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean absolute(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public boolean relative(int n) throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    @Override
    public String getCursorName() throws SQLException {
        throw SimpleResultSet.getUnsupportedException();
    }

    private void update(int n, Object object) throws SQLException {
        this.checkClosed();
        this.checkColumnIndex(n);
        this.currentRow[n - 1] = object;
    }

    private void update(String string, Object object) throws SQLException {
        this.currentRow[this.findColumn((String)string) - 1] = object;
    }

    static SQLException getUnsupportedException() {
        return DbException.getJdbcSQLException(50100);
    }

    private void checkClosed() throws SQLException {
        if (this.columns == null) {
            throw DbException.getJdbcSQLException(90007);
        }
    }

    private void checkColumnIndex(int n) throws SQLException {
        if (n < 1 || n > this.columns.size()) {
            throw DbException.getInvalidValueException("columnIndex", n).getSQLException();
        }
    }

    private Object get(int n) throws SQLException {
        if (this.currentRow == null) {
            throw DbException.getJdbcSQLException(2000);
        }
        this.checkColumnIndex(n);
        Object object = --n < this.currentRow.length ? this.currentRow[n] : null;
        this.wasNull = object == null;
        return object;
    }

    private SimpleColumnInfo getColumn(int n) throws SQLException {
        this.checkColumnIndex(n + 1);
        return this.columns.get(n);
    }

    @Override
    public int getHoldability() {
        return 1;
    }

    @Override
    public boolean isClosed() {
        return this.rows == null && this.source == null;
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
            throw DbException.toSQLException(exception);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> clazz) throws SQLException {
        return clazz != null && clazz.isAssignableFrom(this.getClass());
    }

    public void setAutoClose(boolean bl) {
        this.autoClose = bl;
    }

    public boolean getAutoClose() {
        return this.autoClose;
    }

    public static class SimpleArray
    implements Array {
        private final Object[] value;

        SimpleArray(Object[] objectArray) {
            this.value = objectArray;
        }

        @Override
        public Object getArray() {
            return this.value;
        }

        @Override
        public Object getArray(Map<String, Class<?>> map) throws SQLException {
            throw SimpleResultSet.getUnsupportedException();
        }

        @Override
        public Object getArray(long l, int n) throws SQLException {
            throw SimpleResultSet.getUnsupportedException();
        }

        @Override
        public Object getArray(long l, int n, Map<String, Class<?>> map) throws SQLException {
            throw SimpleResultSet.getUnsupportedException();
        }

        @Override
        public int getBaseType() {
            return 0;
        }

        @Override
        public String getBaseTypeName() {
            return "NULL";
        }

        @Override
        public ResultSet getResultSet() throws SQLException {
            throw SimpleResultSet.getUnsupportedException();
        }

        @Override
        public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
            throw SimpleResultSet.getUnsupportedException();
        }

        @Override
        public ResultSet getResultSet(long l, int n) throws SQLException {
            throw SimpleResultSet.getUnsupportedException();
        }

        @Override
        public ResultSet getResultSet(long l, int n, Map<String, Class<?>> map) throws SQLException {
            throw SimpleResultSet.getUnsupportedException();
        }

        @Override
        public void free() {
        }
    }
}

