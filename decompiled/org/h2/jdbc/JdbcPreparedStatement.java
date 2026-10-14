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
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import org.h2.command.CommandInterface;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Session;
import org.h2.expression.ParameterInterface;
import org.h2.jdbc.JdbcBatchUpdateException;
import org.h2.jdbc.JdbcConnection;
import org.h2.jdbc.JdbcParameterMetaData;
import org.h2.jdbc.JdbcResultSet;
import org.h2.jdbc.JdbcResultSetMetaData;
import org.h2.jdbc.JdbcStatement;
import org.h2.message.DbException;
import org.h2.result.MergedResult;
import org.h2.result.ResultInterface;
import org.h2.result.ResultWithGeneratedKeys;
import org.h2.util.IOUtils;
import org.h2.util.LegacyDateTimeUtils;
import org.h2.util.Utils;
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

public class JdbcPreparedStatement
extends JdbcStatement
implements PreparedStatement {
    protected CommandInterface command;
    private ArrayList<Value[]> batchParameters;
    private MergedResult batchIdentities;
    private HashMap<String, Integer> cachedColumnLabelMap;
    private final Object generatedKeysRequest;

    JdbcPreparedStatement(JdbcConnection jdbcConnection, String string, int n, int n2, int n3, Object object) {
        super(jdbcConnection, n, n2, n3);
        this.generatedKeysRequest = object;
        this.setTrace(this.session.getTrace(), 3, n);
        this.command = jdbcConnection.prepareCommand(string, this.fetchSize);
    }

    void setCachedColumnLabelMap(HashMap<String, Integer> hashMap) {
        this.cachedColumnLabelMap = hashMap;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResultSet executeQuery() throws SQLException {
        try {
            int n = JdbcPreparedStatement.getNextId(4);
            this.debugCodeAssign("ResultSet", 4, n, "executeQuery()");
            this.batchIdentities = null;
            Session session = this.session;
            session.lock();
            try {
                ResultInterface resultInterface;
                this.checkClosed();
                this.closeOldResultSet();
                boolean bl = false;
                boolean bl2 = this.resultSetType != 1003;
                boolean bl3 = this.resultSetConcurrency == 1008;
                try {
                    this.setExecutingStatement(this.command);
                    resultInterface = this.command.executeQuery(this.maxRows, bl2);
                    bl = resultInterface.isLazy();
                }
                finally {
                    if (!bl) {
                        this.setExecutingStatement(null);
                    }
                }
                this.resultSet = new JdbcResultSet(this.conn, this, this.command, resultInterface, n, bl2, bl3, this.cachedColumnLabelMap);
            }
            finally {
                session.unlock();
            }
            return this.resultSet;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int executeUpdate() throws SQLException {
        try {
            this.debugCodeCall("executeUpdate");
            this.checkClosed();
            this.batchIdentities = null;
            long l = this.executeUpdateInternal();
            return l <= Integer.MAX_VALUE ? (int)l : -2;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public long executeLargeUpdate() throws SQLException {
        try {
            this.debugCodeCall("executeLargeUpdate");
            this.checkClosed();
            this.batchIdentities = null;
            return this.executeUpdateInternal();
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long executeUpdateInternal() {
        this.closeOldResultSet();
        Session session = this.session;
        session.lock();
        try {
            try {
                this.setExecutingStatement(this.command);
                ResultWithGeneratedKeys resultWithGeneratedKeys = this.command.executeUpdate(this.generatedKeysRequest);
                this.updateCount = resultWithGeneratedKeys.getUpdateCount();
                ResultInterface resultInterface = resultWithGeneratedKeys.getGeneratedKeys();
                if (resultInterface != null) {
                    int n = JdbcPreparedStatement.getNextId(4);
                    this.generatedKeys = new JdbcResultSet(this.conn, (JdbcStatement)this, this.command, resultInterface, n, true, false, false);
                }
            }
            finally {
                this.setExecutingStatement(null);
            }
        }
        finally {
            session.unlock();
        }
        return this.updateCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean execute() throws SQLException {
        try {
            boolean bl;
            int n = JdbcPreparedStatement.getNextId(4);
            this.debugCodeCall("execute");
            this.checkClosed();
            Session session = this.session;
            session.lock();
            try {
                this.closeOldResultSet();
                boolean bl2 = false;
                try {
                    this.setExecutingStatement(this.command);
                    if (this.command.isQuery()) {
                        bl = true;
                        boolean bl3 = this.resultSetType != 1003;
                        boolean bl4 = this.resultSetConcurrency == 1008;
                        ResultInterface resultInterface = this.command.executeQuery(this.maxRows, bl3);
                        bl2 = resultInterface.isLazy();
                        this.resultSet = new JdbcResultSet(this.conn, this, this.command, resultInterface, n, bl3, bl4, this.cachedColumnLabelMap);
                    } else {
                        bl = false;
                        ResultWithGeneratedKeys resultWithGeneratedKeys = this.command.executeUpdate(this.generatedKeysRequest);
                        this.updateCount = resultWithGeneratedKeys.getUpdateCount();
                        ResultInterface resultInterface = resultWithGeneratedKeys.getGeneratedKeys();
                        if (resultInterface != null) {
                            this.generatedKeys = new JdbcResultSet(this.conn, (JdbcStatement)this, this.command, resultInterface, n, true, false, false);
                        }
                    }
                }
                finally {
                    if (!bl2) {
                        this.setExecutingStatement(null);
                    }
                }
            }
            finally {
                session.unlock();
            }
            return bl;
        }
        catch (Throwable throwable) {
            throw this.logAndConvert(throwable);
        }
    }

    @Override
    public void clearParameters() throws SQLException {
        try {
            this.debugCodeCall("clearParameters");
            this.checkClosed();
            ArrayList<? extends ParameterInterface> arrayList = this.command.getParameters();
            for (ParameterInterface parameterInterface : arrayList) {
                parameterInterface.setValue(null, this.batchParameters == null);
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ResultSet executeQuery(String string) throws SQLException {
        try {
            this.debugCodeCall("executeQuery", string);
            throw DbException.get(90130);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void addBatch(String string) throws SQLException {
        try {
            this.debugCodeCall("addBatch", string);
            throw DbException.get(90130);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setNull(int n, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setNull(" + n + ", " + n2 + ')');
            }
            this.setParameter(n, ValueNull.INSTANCE);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setInt(int n, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setInt(" + n + ", " + n2 + ')');
            }
            this.setParameter(n, ValueInteger.get(n2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setString(int n, String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setString(" + n + ", " + JdbcPreparedStatement.quote(string) + ')');
            }
            this.setParameter(n, string == null ? ValueNull.INSTANCE : ValueVarchar.get(string, this.conn));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setBigDecimal(int n, BigDecimal bigDecimal) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBigDecimal(" + n + ", " + JdbcPreparedStatement.quoteBigDecimal(bigDecimal) + ')');
            }
            this.setParameter(n, bigDecimal == null ? ValueNull.INSTANCE : ValueNumeric.getAnyScale(bigDecimal));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setDate(int n, Date date) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setDate(" + n + ", " + JdbcPreparedStatement.quoteDate(date) + ')');
            }
            this.setParameter(n, date == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromDate(this.conn, null, date));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setTime(int n, Time time) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setTime(" + n + ", " + JdbcPreparedStatement.quoteTime(time) + ')');
            }
            this.setParameter(n, time == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromTime(this.conn, null, time));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setTimestamp(int n, Timestamp timestamp) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setTimestamp(" + n + ", " + JdbcPreparedStatement.quoteTimestamp(timestamp) + ')');
            }
            this.setParameter(n, timestamp == null ? ValueNull.INSTANCE : LegacyDateTimeUtils.fromTimestamp((CastDataProvider)this.conn, null, timestamp));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setObject(int n, Object object) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setObject(" + n + ", x)");
            }
            if (object == null) {
                this.setParameter(n, ValueNull.INSTANCE);
            } else {
                this.setParameter(n, ValueToObjectConverter.objectToValue(this.session, object, -1));
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setObject(int n, Object object, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setObject(" + n + ", x, " + n2 + ')');
            }
            this.setObjectWithType(n, object, DataType.convertSQLTypeToValueType(n2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setObject(int n, Object object, int n2, int n3) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setObject(" + n + ", x, " + n2 + ", " + n3 + ')');
            }
            this.setObjectWithType(n, object, DataType.convertSQLTypeToValueType(n2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setObject(int n, Object object, SQLType sQLType) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setObject(" + n + ", x, " + DataType.sqlTypeToString(sQLType) + ')');
            }
            this.setObjectWithType(n, object, DataType.convertSQLTypeToValueType(sQLType));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setObject(int n, Object object, SQLType sQLType, int n2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setObject(" + n + ", x, " + DataType.sqlTypeToString(sQLType) + ", " + n2 + ')');
            }
            this.setObjectWithType(n, object, DataType.convertSQLTypeToValueType(sQLType));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void setObjectWithType(int n, Object object, int n2) {
        if (object == null) {
            this.setParameter(n, ValueNull.INSTANCE);
        } else {
            Value value = ValueToObjectConverter.objectToValue(this.conn.getSession(), object, n2);
            if (n2 != -1) {
                value = value.convertTo(n2, (CastDataProvider)this.conn);
            }
            this.setParameter(n, value);
        }
    }

    @Override
    public void setBoolean(int n, boolean bl) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBoolean(" + n + ", " + bl + ')');
            }
            this.setParameter(n, ValueBoolean.get(bl));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setByte(int n, byte by) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setByte(" + n + ", " + by + ')');
            }
            this.setParameter(n, ValueTinyint.get(by));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setShort(int n, short s2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setShort(" + n + ", (short) " + s2 + ')');
            }
            this.setParameter(n, ValueSmallint.get(s2));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setLong(int n, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setLong(" + n + ", " + l + "L)");
            }
            this.setParameter(n, ValueBigint.get(l));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setFloat(int n, float f) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setFloat(" + n + ", " + f + "f)");
            }
            this.setParameter(n, ValueReal.get(f));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setDouble(int n, double d) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setDouble(" + n + ", " + d + "d)");
            }
            this.setParameter(n, ValueDouble.get(d));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setRef(int n, Ref ref) throws SQLException {
        throw this.unsupported("ref");
    }

    @Override
    public void setDate(int n, Date date, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setDate(" + n + ", " + JdbcPreparedStatement.quoteDate(date) + ", calendar)");
            }
            if (date == null) {
                this.setParameter(n, ValueNull.INSTANCE);
            } else {
                this.setParameter(n, LegacyDateTimeUtils.fromDate(this.conn, calendar != null ? calendar.getTimeZone() : null, date));
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setTime(int n, Time time, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setTime(" + n + ", " + JdbcPreparedStatement.quoteTime(time) + ", calendar)");
            }
            if (time == null) {
                this.setParameter(n, ValueNull.INSTANCE);
            } else {
                this.setParameter(n, LegacyDateTimeUtils.fromTime(this.conn, calendar != null ? calendar.getTimeZone() : null, time));
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setTimestamp(int n, Timestamp timestamp, Calendar calendar) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setTimestamp(" + n + ", " + JdbcPreparedStatement.quoteTimestamp(timestamp) + ", calendar)");
            }
            if (timestamp == null) {
                this.setParameter(n, ValueNull.INSTANCE);
            } else {
                this.setParameter(n, LegacyDateTimeUtils.fromTimestamp((CastDataProvider)this.conn, calendar != null ? calendar.getTimeZone() : null, timestamp));
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    @Deprecated
    public void setUnicodeStream(int n, InputStream inputStream2, int n2) throws SQLException {
        throw this.unsupported("unicodeStream");
    }

    @Override
    public void setNull(int n, int n2, String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setNull(" + n + ", " + n2 + ", " + JdbcPreparedStatement.quote(string) + ')');
            }
            this.setNull(n, n2);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setBlob(int n, Blob blob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBlob(" + n + ", x)");
            }
            this.checkClosed();
            Value value = blob == null ? ValueNull.INSTANCE : this.conn.createBlob(blob.getBinaryStream(), -1L);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setBlob(int n, InputStream inputStream2) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBlob(" + n + ", x)");
            }
            this.checkClosed();
            Value value = this.conn.createBlob(inputStream2, -1L);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setClob(int n, Clob clob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setClob(" + n + ", x)");
            }
            this.checkClosed();
            Value value = clob == null ? ValueNull.INSTANCE : this.conn.createClob(clob.getCharacterStream(), -1L);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setClob(int n, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setClob(" + n + ", x)");
            }
            this.checkClosed();
            Value value = reader == null ? ValueNull.INSTANCE : this.conn.createClob(reader, -1L);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setArray(int n, Array array) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setArray(" + n + ", x)");
            }
            this.checkClosed();
            Value value = array == null ? ValueNull.INSTANCE : ValueToObjectConverter.objectToValue(this.session, array.getArray(), 40);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setBytes(int n, byte[] byArray) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBytes(" + n + ", " + JdbcPreparedStatement.quoteBytes(byArray) + ')');
            }
            this.setParameter(n, byArray == null ? ValueNull.INSTANCE : ValueVarbinary.get(byArray));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setBinaryStream(int n, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBinaryStream(" + n + ", x, " + l + "L)");
            }
            this.checkClosed();
            Value value = this.conn.createBlob(inputStream2, l);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setBinaryStream(int n, InputStream inputStream2, int n2) throws SQLException {
        this.setBinaryStream(n, inputStream2, (long)n2);
    }

    @Override
    public void setBinaryStream(int n, InputStream inputStream2) throws SQLException {
        this.setBinaryStream(n, inputStream2, -1);
    }

    @Override
    public void setAsciiStream(int n, InputStream inputStream2, int n2) throws SQLException {
        this.setAsciiStream(n, inputStream2, (long)n2);
    }

    @Override
    public void setAsciiStream(int n, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setAsciiStream(" + n + ", x, " + l + "L)");
            }
            this.checkClosed();
            Value value = this.conn.createClob(IOUtils.getAsciiReader(inputStream2), l);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setAsciiStream(int n, InputStream inputStream2) throws SQLException {
        this.setAsciiStream(n, inputStream2, -1);
    }

    @Override
    public void setCharacterStream(int n, Reader reader, int n2) throws SQLException {
        this.setCharacterStream(n, reader, (long)n2);
    }

    @Override
    public void setCharacterStream(int n, Reader reader) throws SQLException {
        this.setCharacterStream(n, reader, -1);
    }

    @Override
    public void setCharacterStream(int n, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setCharacterStream(" + n + ", x, " + l + "L)");
            }
            this.checkClosed();
            Value value = this.conn.createClob(reader, l);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setURL(int n, URL uRL) throws SQLException {
        throw this.unsupported("url");
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            this.debugCodeCall("getMetaData");
            this.checkClosed();
            ResultInterface resultInterface = this.command.getMetaData();
            if (resultInterface == null) {
                return null;
            }
            int n = JdbcPreparedStatement.getNextId(5);
            this.debugCodeAssign("ResultSetMetaData", 5, n, "getMetaData()");
            String string = this.conn.getCatalog();
            return new JdbcResultSetMetaData(null, this, resultInterface, string, this.session.getTrace(), n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void clearBatch() throws SQLException {
        try {
            this.debugCodeCall("clearBatch");
            this.checkClosed();
            this.batchParameters = null;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void close() throws SQLException {
        try {
            super.close();
            this.batchParameters = null;
            this.batchIdentities = null;
            if (this.command != null) {
                this.command.close();
                this.command = null;
            }
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public int[] executeBatch() throws SQLException {
        try {
            this.debugCodeCall("executeBatch");
            if (this.batchParameters == null) {
                this.batchParameters = new ArrayList();
            }
            this.batchIdentities = new MergedResult();
            int n = this.batchParameters.size();
            int[] nArray = new int[n];
            SQLException sQLException = new SQLException();
            this.checkClosed();
            for (int i = 0; i < n; ++i) {
                long l = this.executeBatchElement(this.batchParameters.get(i), sQLException);
                nArray[i] = l <= Integer.MAX_VALUE ? (int)l : -2;
            }
            this.batchParameters = null;
            if ((sQLException = sQLException.getNextException()) != null) {
                throw new JdbcBatchUpdateException(sQLException, nArray);
            }
            return nArray;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public long[] executeLargeBatch() throws SQLException {
        try {
            this.debugCodeCall("executeLargeBatch");
            if (this.batchParameters == null) {
                this.batchParameters = new ArrayList();
            }
            this.batchIdentities = new MergedResult();
            int n = this.batchParameters.size();
            long[] lArray = new long[n];
            SQLException sQLException = new SQLException();
            this.checkClosed();
            for (int i = 0; i < n; ++i) {
                lArray[i] = this.executeBatchElement(this.batchParameters.get(i), sQLException);
            }
            this.batchParameters = null;
            if ((sQLException = sQLException.getNextException()) != null) {
                throw new JdbcBatchUpdateException(sQLException, lArray);
            }
            return lArray;
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private long executeBatchElement(Value[] valueArray, SQLException sQLException) {
        long l;
        ArrayList<? extends ParameterInterface> arrayList = this.command.getParameters();
        int n = valueArray.length;
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).setValue(valueArray[i], false);
        }
        try {
            l = this.executeUpdateInternal();
            ResultSet resultSet = super.getGeneratedKeys();
            this.batchIdentities.add(((JdbcResultSet)resultSet).result);
        }
        catch (Exception exception) {
            sQLException.setNextException(this.logAndConvert(exception));
            l = -3L;
        }
        return l;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        if (this.batchIdentities != null) {
            try {
                int n = JdbcPreparedStatement.getNextId(4);
                this.debugCodeAssign("ResultSet", 4, n, "getGeneratedKeys()");
                this.checkClosed();
                this.generatedKeys = new JdbcResultSet(this.conn, (JdbcStatement)this, null, (ResultInterface)this.batchIdentities.getResult(), n, true, false, false);
            }
            catch (Exception exception) {
                throw this.logAndConvert(exception);
            }
        }
        return super.getGeneratedKeys();
    }

    @Override
    public void addBatch() throws SQLException {
        try {
            this.debugCodeCall("addBatch");
            this.checkClosed();
            ArrayList<? extends ParameterInterface> arrayList = this.command.getParameters();
            int n = arrayList.size();
            Value[] valueArray = new Value[n];
            for (int i = 0; i < n; ++i) {
                Value value;
                ParameterInterface parameterInterface = arrayList.get(i);
                parameterInterface.checkSet();
                valueArray[i] = value = parameterInterface.getParamValue();
            }
            if (this.batchParameters == null) {
                this.batchParameters = Utils.newSmallArrayList();
            }
            this.batchParameters.add(valueArray);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        try {
            int n = JdbcPreparedStatement.getNextId(11);
            this.debugCodeAssign("ParameterMetaData", 11, n, "getParameterMetaData()");
            this.checkClosed();
            return new JdbcParameterMetaData(this.session.getTrace(), this, this.command, n);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    private void setParameter(int n, Value value) {
        this.checkClosed();
        ArrayList<? extends ParameterInterface> arrayList = this.command.getParameters();
        if (--n < 0 || n >= arrayList.size()) {
            throw DbException.getInvalidValueException("parameterIndex", n + 1);
        }
        ParameterInterface parameterInterface = arrayList.get(n);
        parameterInterface.setValue(value, this.batchParameters == null);
    }

    @Override
    public void setRowId(int n, RowId rowId) throws SQLException {
        throw this.unsupported("rowId");
    }

    @Override
    public void setNString(int n, String string) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setNString(" + n + ", " + JdbcPreparedStatement.quote(string) + ')');
            }
            this.setParameter(n, string == null ? ValueNull.INSTANCE : ValueVarchar.get(string, this.conn));
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setNCharacterStream(int n, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setNCharacterStream(" + n + ", x, " + l + "L)");
            }
            this.checkClosed();
            Value value = this.conn.createClob(reader, l);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setNCharacterStream(int n, Reader reader) throws SQLException {
        this.setNCharacterStream(n, reader, -1L);
    }

    @Override
    public void setNClob(int n, NClob nClob) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setNClob(" + n + ", x)");
            }
            this.checkClosed();
            Value value = nClob == null ? ValueNull.INSTANCE : this.conn.createClob(nClob.getCharacterStream(), -1L);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setNClob(int n, Reader reader) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setNClob(" + n + ", x)");
            }
            this.checkClosed();
            Value value = this.conn.createClob(reader, -1L);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setClob(int n, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setClob(" + n + ", x, " + l + "L)");
            }
            this.checkClosed();
            Value value = this.conn.createClob(reader, l);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setBlob(int n, InputStream inputStream2, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setBlob(" + n + ", x, " + l + "L)");
            }
            this.checkClosed();
            Value value = this.conn.createBlob(inputStream2, l);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setNClob(int n, Reader reader, long l) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setNClob(" + n + ", x, " + l + "L)");
            }
            this.checkClosed();
            Value value = this.conn.createClob(reader, l);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public void setSQLXML(int n, SQLXML sQLXML) throws SQLException {
        try {
            if (this.isDebugEnabled()) {
                this.debugCode("setSQLXML(" + n + ", x)");
            }
            this.checkClosed();
            Value value = sQLXML == null ? ValueNull.INSTANCE : this.conn.createClob(sQLXML.getCharacterStream(), -1L);
            this.setParameter(n, value);
        }
        catch (Exception exception) {
            throw this.logAndConvert(exception);
        }
    }

    @Override
    public String toString() {
        return this.getTraceObjectName() + ": " + this.command;
    }
}

