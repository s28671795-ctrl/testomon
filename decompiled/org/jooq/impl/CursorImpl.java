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
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import org.jooq.Attachable;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.impl.AbstractCursor;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractRowAsField;
import org.jooq.impl.DefaultBindingGetResultSetContext;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.ExecuteListeners;
import org.jooq.impl.RecordDelegate;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.RowAsField;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBC41ResultSet;
import org.jooq.tools.jdbc.JDBCUtils;

final class CursorImpl<R extends Record>
extends AbstractCursor<R> {
    private static final JooqLogger log = JooqLogger.getLogger(CursorImpl.class);
    final ExecuteContext ctx;
    final ExecuteListener listener;
    private final boolean[] intern;
    private final boolean keepResultSet;
    private final boolean keepStatement;
    private final boolean autoclosing;
    private final int maxRows;
    private final Supplier<? extends R> factory;
    private boolean isClosed;
    private transient CursorResultSet rs;
    private transient Iterator<R> iterator;
    private transient int rows;

    CursorImpl(ExecuteContext ctx, ExecuteListener listener, Field<?>[] fields, int[] internIndexes, boolean keepStatement, boolean keepResultSet) {
        this(ctx, listener, fields, internIndexes, keepStatement, keepResultSet, RecordImplN.class, 0, true);
    }

    CursorImpl(ExecuteContext ctx, ExecuteListener listener, Field<?>[] fields, int[] internIndexes, boolean keepStatement, boolean keepResultSet, Class<? extends R> type, int maxRows, boolean autoclosing) {
        super(ctx.configuration(), Tools.row0(fields));
        this.ctx = ctx;
        this.listener = listener != null ? listener : ExecuteListeners.getAndStart(ctx);
        this.factory = Tools.recordFactory(type, this.fields);
        this.keepStatement = keepStatement;
        this.keepResultSet = keepResultSet;
        this.rs = new CursorResultSet();
        this.maxRows = maxRows;
        this.autoclosing = autoclosing;
        if (internIndexes != null) {
            this.intern = new boolean[fields.length];
            for (int i : internIndexes) {
                this.intern[i] = true;
            }
        } else {
            this.intern = null;
        }
    }

    @Override
    final List<? extends Attachable> getAttachables() {
        return Collections.emptyList();
    }

    @Override
    public final Iterator<R> iterator() {
        if (this.iterator == null) {
            this.iterator = new CursorIterator();
            this.listener.fetchStart(this.ctx);
        }
        return this.iterator;
    }

    @Override
    public final Result<R> fetchNext(int number) {
        this.iterator();
        ResultImpl<Record> result = new ResultImpl<Record>(((DefaultExecuteContext)this.ctx).originalConfiguration(), this.fields);
        this.ctx.result(result);
        this.listener.resultStart(this.ctx);
        for (int i = 0; i < number && this.iterator().hasNext(); ++i) {
            result.addRecord((Record)this.iterator().next());
        }
        this.ctx.result(result);
        this.listener.resultEnd(this.ctx);
        return result;
    }

    @Override
    public final void close() {
        JDBCUtils.safeClose(this.rs);
        this.rs = null;
        this.isClosed = true;
    }

    @Override
    public final boolean isClosed() {
        return this.isClosed;
    }

    @Override
    public final ResultSet resultSet() {
        return this.rs;
    }

    final class CursorResultSet
    extends JDBC41ResultSet
    implements ResultSet {
        CursorResultSet() {
        }

        @Override
        public final <T> T unwrap(Class<T> iface) throws SQLException {
            return CursorImpl.this.ctx.resultSet().unwrap(iface);
        }

        @Override
        public final boolean isWrapperFor(Class<?> iface) throws SQLException {
            return CursorImpl.this.ctx.resultSet().isWrapperFor(iface);
        }

        @Override
        public final Statement getStatement() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getStatement();
        }

        @Override
        public final SQLWarning getWarnings() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getWarnings();
        }

        @Override
        public final void clearWarnings() throws SQLException {
            CursorImpl.this.ctx.resultSet().clearWarnings();
        }

        @Override
        public final String getCursorName() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getCursorName();
        }

        @Override
        public final ResultSetMetaData getMetaData() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getMetaData();
        }

        @Override
        public final int findColumn(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().findColumn(columnLabel);
        }

        @Override
        public final void setFetchDirection(int direction) throws SQLException {
            CursorImpl.this.ctx.resultSet().setFetchDirection(direction);
        }

        @Override
        public final int getFetchDirection() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getFetchDirection();
        }

        @Override
        public final void setFetchSize(int rows) throws SQLException {
            CursorImpl.this.ctx.resultSet().setFetchSize(rows);
        }

        @Override
        public final int getFetchSize() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getFetchSize();
        }

        @Override
        public final int getType() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getType();
        }

        @Override
        public final int getConcurrency() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getConcurrency();
        }

        @Override
        public final int getHoldability() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getHoldability();
        }

        @Override
        public final boolean isBeforeFirst() throws SQLException {
            return CursorImpl.this.ctx.resultSet().isBeforeFirst();
        }

        @Override
        public final boolean isAfterLast() throws SQLException {
            return CursorImpl.this.ctx.resultSet().isAfterLast();
        }

        @Override
        public final boolean isFirst() throws SQLException {
            return CursorImpl.this.ctx.resultSet().isFirst();
        }

        @Override
        public final boolean isLast() throws SQLException {
            return CursorImpl.this.ctx.resultSet().isLast();
        }

        @Override
        public final boolean next() throws SQLException {
            return CursorImpl.this.ctx.resultSet().next();
        }

        @Override
        public final boolean previous() throws SQLException {
            return CursorImpl.this.ctx.resultSet().previous();
        }

        @Override
        public final void beforeFirst() throws SQLException {
            CursorImpl.this.ctx.resultSet().beforeFirst();
        }

        @Override
        public final void afterLast() throws SQLException {
            CursorImpl.this.ctx.resultSet().afterLast();
        }

        @Override
        public final boolean first() throws SQLException {
            return CursorImpl.this.ctx.resultSet().first();
        }

        @Override
        public final boolean last() throws SQLException {
            return CursorImpl.this.ctx.resultSet().last();
        }

        @Override
        public final int getRow() throws SQLException {
            return CursorImpl.this.ctx.resultSet().getRow();
        }

        @Override
        public final boolean absolute(int row) throws SQLException {
            return CursorImpl.this.ctx.resultSet().absolute(row);
        }

        @Override
        public final boolean relative(int r) throws SQLException {
            return CursorImpl.this.ctx.resultSet().relative(r);
        }

        @Override
        public final void moveToInsertRow() throws SQLException {
            CursorImpl.this.ctx.resultSet().moveToInsertRow();
        }

        @Override
        public final void moveToCurrentRow() throws SQLException {
            CursorImpl.this.ctx.resultSet().moveToCurrentRow();
        }

        @Override
        public final void close() throws SQLException {
            CursorImpl.this.ctx.rows(CursorImpl.this.rows);
            try {
                CursorImpl.this.listener.fetchEnd(CursorImpl.this.ctx);
            }
            finally {
                Tools.safeClose(CursorImpl.this.listener, CursorImpl.this.ctx, CursorImpl.this.keepStatement, CursorImpl.this.keepResultSet);
            }
        }

        @Override
        public final boolean isClosed() throws SQLException {
            return CursorImpl.this.ctx.resultSet().isClosed();
        }

        @Override
        public final boolean wasNull() throws SQLException {
            return CursorImpl.this.ctx.resultSet().wasNull();
        }

        @Override
        public final Array getArray(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getArray(columnIndex);
        }

        @Override
        public final Array getArray(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getArray(columnLabel);
        }

        @Override
        public final InputStream getAsciiStream(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getAsciiStream(columnIndex);
        }

        @Override
        public final InputStream getAsciiStream(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getAsciiStream(columnLabel);
        }

        @Override
        public final BigDecimal getBigDecimal(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBigDecimal(columnIndex);
        }

        @Override
        @Deprecated
        public final BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBigDecimal(columnIndex, scale);
        }

        @Override
        public final BigDecimal getBigDecimal(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBigDecimal(columnLabel);
        }

        @Override
        @Deprecated
        public final BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBigDecimal(columnLabel, scale);
        }

        @Override
        public final InputStream getBinaryStream(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBinaryStream(columnIndex);
        }

        @Override
        public final InputStream getBinaryStream(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBinaryStream(columnLabel);
        }

        @Override
        public final Blob getBlob(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBlob(columnIndex);
        }

        @Override
        public final Blob getBlob(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBlob(columnLabel);
        }

        @Override
        public final boolean getBoolean(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBoolean(columnIndex);
        }

        @Override
        public final boolean getBoolean(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBoolean(columnLabel);
        }

        @Override
        public final byte getByte(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getByte(columnIndex);
        }

        @Override
        public final byte getByte(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getByte(columnLabel);
        }

        @Override
        public final byte[] getBytes(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBytes(columnIndex);
        }

        @Override
        public final byte[] getBytes(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getBytes(columnLabel);
        }

        @Override
        public final Reader getCharacterStream(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getCharacterStream(columnIndex);
        }

        @Override
        public final Reader getCharacterStream(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getCharacterStream(columnLabel);
        }

        @Override
        public final Clob getClob(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getClob(columnIndex);
        }

        @Override
        public final Clob getClob(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getClob(columnLabel);
        }

        @Override
        public final Date getDate(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getDate(columnIndex);
        }

        @Override
        public final Date getDate(int columnIndex, Calendar cal) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getDate(columnIndex, cal);
        }

        @Override
        public final Date getDate(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getDate(columnLabel);
        }

        @Override
        public final Date getDate(String columnLabel, Calendar cal) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getDate(columnLabel, cal);
        }

        @Override
        public final double getDouble(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getDouble(columnIndex);
        }

        @Override
        public final double getDouble(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getDouble(columnLabel);
        }

        @Override
        public final float getFloat(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getFloat(columnIndex);
        }

        @Override
        public final float getFloat(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getFloat(columnLabel);
        }

        @Override
        public final int getInt(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getInt(columnIndex);
        }

        @Override
        public final int getInt(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getInt(columnLabel);
        }

        @Override
        public final long getLong(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getLong(columnIndex);
        }

        @Override
        public final long getLong(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getLong(columnLabel);
        }

        @Override
        public final Reader getNCharacterStream(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getNCharacterStream(columnIndex);
        }

        @Override
        public final Reader getNCharacterStream(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getNCharacterStream(columnLabel);
        }

        @Override
        public final NClob getNClob(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getNClob(columnIndex);
        }

        @Override
        public final NClob getNClob(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getNClob(columnLabel);
        }

        @Override
        public final String getNString(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getNString(columnIndex);
        }

        @Override
        public final String getNString(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getNString(columnLabel);
        }

        @Override
        public final Object getObject(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getObject(columnIndex);
        }

        @Override
        public final Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getObject(columnIndex, map);
        }

        @Override
        public final Object getObject(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getObject(columnLabel);
        }

        @Override
        public final Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getObject(columnLabel, map);
        }

        @Override
        public final Ref getRef(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getRef(columnIndex);
        }

        @Override
        public final Ref getRef(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getRef(columnLabel);
        }

        @Override
        public final RowId getRowId(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getRowId(columnIndex);
        }

        @Override
        public final RowId getRowId(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getRowId(columnLabel);
        }

        @Override
        public final short getShort(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getShort(columnIndex);
        }

        @Override
        public final short getShort(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getShort(columnLabel);
        }

        @Override
        public final SQLXML getSQLXML(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getSQLXML(columnIndex);
        }

        @Override
        public final SQLXML getSQLXML(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getSQLXML(columnLabel);
        }

        @Override
        public final String getString(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getString(columnIndex);
        }

        @Override
        public final String getString(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getString(columnLabel);
        }

        @Override
        public final Time getTime(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getTime(columnIndex);
        }

        @Override
        public final Time getTime(int columnIndex, Calendar cal) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getTime(columnIndex, cal);
        }

        @Override
        public final Time getTime(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getTime(columnLabel);
        }

        @Override
        public final Time getTime(String columnLabel, Calendar cal) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getTime(columnLabel, cal);
        }

        @Override
        public final Timestamp getTimestamp(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getTimestamp(columnIndex);
        }

        @Override
        public final Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getTimestamp(columnIndex, cal);
        }

        @Override
        public final Timestamp getTimestamp(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getTimestamp(columnLabel);
        }

        @Override
        public final Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getTimestamp(columnLabel, cal);
        }

        @Override
        @Deprecated
        public final InputStream getUnicodeStream(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getUnicodeStream(columnIndex);
        }

        @Override
        @Deprecated
        public final InputStream getUnicodeStream(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getUnicodeStream(columnLabel);
        }

        @Override
        public final URL getURL(int columnIndex) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getURL(columnIndex);
        }

        @Override
        public final URL getURL(String columnLabel) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getURL(columnLabel);
        }

        @Override
        public final <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getObject(columnIndex, type);
        }

        @Override
        public final <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
            return CursorImpl.this.ctx.resultSet().getObject(columnLabel, type);
        }

        private final void logUpdate(int columnIndex, Object x) throws SQLException {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Updating Result", "Updating Result position " + this.getRow() + ":" + columnIndex + " with value " + String.valueOf(x));
            }
        }

        private final void logUpdate(String columnLabel, Object x) throws SQLException {
            if (log.isDebugEnabled()) {
                log.debug((Object)"Updating Result", "Updating Result position " + this.getRow() + ":" + columnLabel + " with value " + String.valueOf(x));
            }
        }

        @Override
        public final boolean rowUpdated() throws SQLException {
            return CursorImpl.this.ctx.resultSet().rowUpdated();
        }

        @Override
        public final boolean rowInserted() throws SQLException {
            return CursorImpl.this.ctx.resultSet().rowInserted();
        }

        @Override
        public final boolean rowDeleted() throws SQLException {
            return CursorImpl.this.ctx.resultSet().rowDeleted();
        }

        @Override
        public final void insertRow() throws SQLException {
            CursorImpl.this.ctx.resultSet().insertRow();
        }

        @Override
        public final void updateRow() throws SQLException {
            CursorImpl.this.ctx.resultSet().updateRow();
        }

        @Override
        public final void deleteRow() throws SQLException {
            CursorImpl.this.ctx.resultSet().deleteRow();
        }

        @Override
        public final void refreshRow() throws SQLException {
            CursorImpl.this.ctx.resultSet().refreshRow();
        }

        @Override
        public final void cancelRowUpdates() throws SQLException {
            CursorImpl.this.ctx.resultSet().cancelRowUpdates();
        }

        @Override
        public final void updateNull(int columnIndex) throws SQLException {
            this.logUpdate(columnIndex, null);
            CursorImpl.this.ctx.resultSet().updateNull(columnIndex);
        }

        @Override
        public final void updateNull(String columnLabel) throws SQLException {
            this.logUpdate(columnLabel, null);
            CursorImpl.this.ctx.resultSet().updateNull(columnLabel);
        }

        @Override
        public final void updateArray(int columnIndex, Array x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateArray(columnIndex, x);
        }

        @Override
        public final void updateArray(String columnLabel, Array x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateArray(columnLabel, x);
        }

        @Override
        public final void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateAsciiStream(columnIndex, x);
        }

        @Override
        public final void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateAsciiStream(columnIndex, x, length);
        }

        @Override
        public final void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateAsciiStream(columnIndex, x, length);
        }

        @Override
        public final void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateAsciiStream(columnLabel, x);
        }

        @Override
        public final void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateAsciiStream(columnLabel, x, length);
        }

        @Override
        public final void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateAsciiStream(columnLabel, x, length);
        }

        @Override
        public final void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBigDecimal(columnIndex, x);
        }

        @Override
        public final void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBigDecimal(columnLabel, x);
        }

        @Override
        public final void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBinaryStream(columnIndex, x);
        }

        @Override
        public final void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBinaryStream(columnIndex, x, length);
        }

        @Override
        public final void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBinaryStream(columnIndex, x, length);
        }

        @Override
        public final void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBinaryStream(columnLabel, x);
        }

        @Override
        public final void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBinaryStream(columnLabel, x, length);
        }

        @Override
        public final void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBinaryStream(columnLabel, x, length);
        }

        @Override
        public final void updateBlob(int columnIndex, Blob x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBlob(columnIndex, x);
        }

        @Override
        public final void updateBlob(int columnIndex, InputStream x, long length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBlob(columnIndex, x, length);
        }

        @Override
        public final void updateBlob(String columnLabel, Blob x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBlob(columnLabel, x);
        }

        @Override
        public final void updateBlob(int columnIndex, InputStream x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBlob(columnIndex, x);
        }

        @Override
        public final void updateBlob(String columnLabel, InputStream x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBlob(columnLabel, x);
        }

        @Override
        public final void updateBlob(String columnLabel, InputStream x, long length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBlob(columnLabel, x, length);
        }

        @Override
        public final void updateBoolean(int columnIndex, boolean x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBoolean(columnIndex, x);
        }

        @Override
        public final void updateBoolean(String columnLabel, boolean x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBoolean(columnLabel, x);
        }

        @Override
        public final void updateByte(int columnIndex, byte x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateByte(columnIndex, x);
        }

        @Override
        public final void updateByte(String columnLabel, byte x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateByte(columnLabel, x);
        }

        @Override
        public final void updateBytes(int columnIndex, byte[] x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBytes(columnIndex, x);
        }

        @Override
        public final void updateBytes(String columnLabel, byte[] x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateBytes(columnLabel, x);
        }

        @Override
        public final void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateCharacterStream(columnIndex, x);
        }

        @Override
        public final void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateCharacterStream(columnIndex, x, length);
        }

        @Override
        public final void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateCharacterStream(columnIndex, x, length);
        }

        @Override
        public final void updateCharacterStream(String columnLabel, Reader x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateCharacterStream(columnLabel, x);
        }

        @Override
        public final void updateCharacterStream(String columnLabel, Reader x, int length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateCharacterStream(columnLabel, x, length);
        }

        @Override
        public final void updateCharacterStream(String columnLabel, Reader x, long length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateCharacterStream(columnLabel, x, length);
        }

        @Override
        public final void updateClob(int columnIndex, Clob x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateClob(columnIndex, x);
        }

        @Override
        public final void updateClob(int columnIndex, Reader x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateClob(columnIndex, x);
        }

        @Override
        public final void updateClob(int columnIndex, Reader x, long length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateClob(columnIndex, x, length);
        }

        @Override
        public final void updateClob(String columnLabel, Clob x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateClob(columnLabel, x);
        }

        @Override
        public final void updateClob(String columnLabel, Reader x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateClob(columnLabel, x);
        }

        @Override
        public final void updateClob(String columnLabel, Reader x, long length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateClob(columnLabel, x, length);
        }

        @Override
        public final void updateDate(int columnIndex, Date x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateDate(columnIndex, x);
        }

        @Override
        public final void updateDate(String columnLabel, Date x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateDate(columnLabel, x);
        }

        @Override
        public final void updateDouble(int columnIndex, double x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateDouble(columnIndex, x);
        }

        @Override
        public final void updateDouble(String columnLabel, double x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateDouble(columnLabel, x);
        }

        @Override
        public final void updateFloat(int columnIndex, float x) throws SQLException {
            this.logUpdate(columnIndex, (Object)Float.valueOf(x));
            CursorImpl.this.ctx.resultSet().updateFloat(columnIndex, x);
        }

        @Override
        public final void updateFloat(String columnLabel, float x) throws SQLException {
            this.logUpdate(columnLabel, (Object)Float.valueOf(x));
            CursorImpl.this.ctx.resultSet().updateFloat(columnLabel, x);
        }

        @Override
        public final void updateInt(int columnIndex, int x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateInt(columnIndex, x);
        }

        @Override
        public final void updateInt(String columnLabel, int x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateInt(columnLabel, x);
        }

        @Override
        public final void updateLong(int columnIndex, long x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateLong(columnIndex, x);
        }

        @Override
        public final void updateLong(String columnLabel, long x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateLong(columnLabel, x);
        }

        @Override
        public final void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNCharacterStream(columnIndex, x);
        }

        @Override
        public final void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNCharacterStream(columnIndex, x, length);
        }

        @Override
        public final void updateNCharacterStream(String columnLabel, Reader x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNCharacterStream(columnLabel, x);
        }

        @Override
        public final void updateNCharacterStream(String columnLabel, Reader x, long length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNCharacterStream(columnLabel, x, length);
        }

        @Override
        public final void updateNClob(int columnIndex, NClob x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNClob(columnIndex, x);
        }

        @Override
        public final void updateNClob(int columnIndex, Reader x, long length) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNClob(columnIndex, x, length);
        }

        @Override
        public final void updateNClob(int columnIndex, Reader x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNClob(columnIndex, x);
        }

        @Override
        public final void updateNClob(String columnLabel, NClob x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNClob(columnLabel, x);
        }

        @Override
        public final void updateNClob(String columnLabel, Reader x, long length) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNClob(columnLabel, x, length);
        }

        @Override
        public final void updateNClob(String columnLabel, Reader x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNClob(columnLabel, x);
        }

        @Override
        public final void updateNString(int columnIndex, String x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNString(columnIndex, x);
        }

        @Override
        public final void updateNString(String columnLabel, String x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateNString(columnLabel, x);
        }

        @Override
        public final void updateObject(int columnIndex, Object x) throws SQLException {
            this.logUpdate(columnIndex, x);
            CursorImpl.this.ctx.resultSet().updateObject(columnIndex, x);
        }

        @Override
        public final void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
            this.logUpdate(columnIndex, x);
            CursorImpl.this.ctx.resultSet().updateObject(columnIndex, x, scaleOrLength);
        }

        @Override
        public final void updateObject(String columnLabel, Object x) throws SQLException {
            this.logUpdate(columnLabel, x);
            CursorImpl.this.ctx.resultSet().updateObject(columnLabel, x);
        }

        @Override
        public final void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
            this.logUpdate(columnLabel, x);
            CursorImpl.this.ctx.resultSet().updateObject(columnLabel, x, scaleOrLength);
        }

        @Override
        public final void updateRef(int columnIndex, Ref x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateRef(columnIndex, x);
        }

        @Override
        public final void updateRef(String columnLabel, Ref x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateRef(columnLabel, x);
        }

        @Override
        public final void updateRowId(int columnIndex, RowId x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateRowId(columnIndex, x);
        }

        @Override
        public final void updateRowId(String columnLabel, RowId x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateRowId(columnLabel, x);
        }

        @Override
        public final void updateShort(int columnIndex, short x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateShort(columnIndex, x);
        }

        @Override
        public final void updateShort(String columnLabel, short x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateShort(columnLabel, x);
        }

        @Override
        public final void updateSQLXML(int columnIndex, SQLXML x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateSQLXML(columnIndex, x);
        }

        @Override
        public final void updateSQLXML(String columnLabel, SQLXML x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateSQLXML(columnLabel, x);
        }

        @Override
        public final void updateString(int columnIndex, String x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateString(columnIndex, x);
        }

        @Override
        public final void updateString(String columnLabel, String x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateString(columnLabel, x);
        }

        @Override
        public final void updateTime(int columnIndex, Time x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateTime(columnIndex, x);
        }

        @Override
        public final void updateTime(String columnLabel, Time x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateTime(columnLabel, x);
        }

        @Override
        public final void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
            this.logUpdate(columnIndex, (Object)x);
            CursorImpl.this.ctx.resultSet().updateTimestamp(columnIndex, x);
        }

        @Override
        public final void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
            this.logUpdate(columnLabel, (Object)x);
            CursorImpl.this.ctx.resultSet().updateTimestamp(columnLabel, x);
        }

        @Override
        public void updateObject(int columnIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
            this.logUpdate(columnIndex, x);
            CursorImpl.this.ctx.resultSet().updateObject(columnIndex, x, targetSqlType, scaleOrLength);
        }

        @Override
        public void updateObject(String columnLabel, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
            this.logUpdate(columnLabel, x);
            CursorImpl.this.ctx.resultSet().updateObject(columnLabel, x, targetSqlType, scaleOrLength);
        }

        @Override
        public void updateObject(int columnIndex, Object x, SQLType targetSqlType) throws SQLException {
            this.logUpdate(columnIndex, x);
            CursorImpl.this.ctx.resultSet().updateObject(columnIndex, x, targetSqlType);
        }

        @Override
        public void updateObject(String columnLabel, Object x, SQLType targetSqlType) throws SQLException {
            this.logUpdate(columnLabel, x);
            CursorImpl.this.ctx.resultSet().updateObject(columnLabel, x, targetSqlType);
        }
    }

    final class CursorIterator
    implements Iterator<R> {
        private R next;
        private Boolean hasNext;
        private final CursorRecordInitialiser initialiser;
        private final RecordDelegate<AbstractRecord> recordDelegate;

        CursorIterator() {
            this.initialiser = new CursorRecordInitialiser(CursorImpl.this.ctx, CursorImpl.this.listener, new DefaultBindingGetResultSetContext(CursorImpl.this.ctx, CursorImpl.this.rs, 0), CursorImpl.this.fields, 0, CursorImpl.this.intern);
            this.recordDelegate = Tools.newRecord(true, CursorImpl.this.factory, ((DefaultExecuteContext)CursorImpl.this.ctx).originalConfiguration());
        }

        @Override
        public final boolean hasNext() {
            if (this.hasNext == null) {
                if (CursorImpl.this.maxRows > 0 && CursorImpl.this.rows >= CursorImpl.this.maxRows) {
                    return false;
                }
                this.next = this.fetchNext();
                this.hasNext = this.next != null;
            }
            return this.hasNext;
        }

        @Override
        public final R next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("There are no more records to fetch from this Cursor");
            }
            Object result = this.next;
            this.hasNext = null;
            this.next = null;
            return result;
        }

        private final R fetchNext() {
            AbstractRecord record = null;
            try {
                if (!CursorImpl.this.isClosed && CursorImpl.this.rs.next()) {
                    record = this.recordDelegate.operate(this.initialiser.reset());
                    ++CursorImpl.this.rows;
                }
            }
            catch (ControlFlowSignal e) {
                throw e;
            }
            catch (RuntimeException e) {
                CursorImpl.this.ctx.exception(e);
                CursorImpl.this.listener.exception(CursorImpl.this.ctx);
                throw CursorImpl.this.ctx.exception();
            }
            catch (SQLException e) {
                CursorImpl.this.ctx.sqlException(e);
                CursorImpl.this.listener.exception(CursorImpl.this.ctx);
                throw CursorImpl.this.ctx.exception();
            }
            if (record == null && CursorImpl.this.autoclosing) {
                CursorImpl.this.close();
            }
            return record;
        }

        @Override
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    static class CursorRecordInitialiser
    implements ThrowingFunction<AbstractRecord, AbstractRecord, SQLException> {
        private final ExecuteContext ctx;
        private final ExecuteListener listener;
        private final AbstractRow<?> initialiserFields;
        private int offset;
        private final boolean[] intern;
        @Pro
        private int[] longIndexes;
        @Pro
        private int[] otherIndexes;
        private final DefaultBindingGetResultSetContext<?> rsContext;

        CursorRecordInitialiser(ExecuteContext ctx, ExecuteListener listener, DefaultBindingGetResultSetContext<?> rsContext, AbstractRow<?> initialiserFields, int offset, boolean[] intern) {
            this(ctx, listener, rsContext, initialiserFields, offset, null, null, intern);
        }

        CursorRecordInitialiser(ExecuteContext ctx, ExecuteListener listener, DefaultBindingGetResultSetContext<?> rsContext, AbstractRow<?> initialiserFields, int offset, int[] longIndexes, int[] otherIndexes, boolean[] intern) {
            this.longIndexes = longIndexes;
            this.otherIndexes = otherIndexes;
            this.ctx = ctx;
            this.listener = listener;
            this.rsContext = rsContext;
            this.initialiserFields = initialiserFields;
            this.offset = offset;
            this.intern = intern;
            if (ctx.family() == SQLDialect.ORACLE && longIndexes == null) {
                this.initLongIndexes(rsContext.resultSet());
            }
        }

        CursorRecordInitialiser reset() {
            this.offset = 0;
            return this;
        }

        @Override
        public AbstractRecord apply(AbstractRecord record) throws SQLException {
            int i;
            this.ctx.record(record);
            this.listener.recordStart(this.ctx);
            int size = this.initialiserFields.size();
            if (this.longIndexes != null) {
                int index;
                for (i = 0; i < this.longIndexes.length; ++i) {
                    index = this.longIndexes[i];
                    if (index >= size) continue;
                    this.setValue(record, this.initialiserFields.field(index), index);
                }
                for (i = 0; i < this.otherIndexes.length; ++i) {
                    index = this.otherIndexes[i];
                    if (index >= size) continue;
                    this.setValue(record, this.initialiserFields.field(index), index);
                }
            } else {
                for (i = 0; i < size; ++i) {
                    this.setValue(record, this.initialiserFields.field(i), i);
                }
            }
            if (this.intern != null) {
                for (i = 0; i < this.intern.length; ++i) {
                    if (!this.intern[i]) continue;
                    record.intern0(i);
                }
            }
            this.ctx.record(record);
            this.listener.recordEnd(this.ctx);
            return record;
        }

        private final <T> void setValue(AbstractRecord record, Field<T> field2, int index) throws SQLException {
            try {
                Object value;
                AbstractRow<Object> nested = null;
                Class<AbstractRecord> recordType = null;
                Field<?> f = Tools.uncoerce(field2);
                if (f instanceof AbstractRowAsField && RowAsField.NO_NATIVE_SUPPORT.contains((Object)this.ctx.dialect()) && !Boolean.TRUE.equals(this.ctx.data(Tools.BooleanDataKey.DATA_MULTISET_CONTENT))) {
                    nested = ((AbstractRowAsField)f).emulatedFields(this.ctx.configuration());
                    recordType = ((AbstractRowAsField)f).getRecordType();
                } else if (f.getDataType().isEmbeddable()) {
                    nested = Tools.row0(Tools.embeddedFields(f));
                    recordType = Tools.embeddedRecordType(f);
                }
                int nestedOffset = this.offset + index;
                if (nested != null) {
                    CursorRecordInitialiser operation = new CursorRecordInitialiser(this.ctx, this.listener, this.rsContext, nested, nestedOffset, this.longIndexes, this.otherIndexes, this.intern);
                    value = Tools.newRecord(true, recordType, nested, ((DefaultExecuteContext)this.ctx).originalConfiguration()).operate(operation);
                    if (f != field2) {
                        value = field2.getConverter().from(value, this.ctx.converterContext());
                    }
                    this.offset += operation.offset - nestedOffset + nested.size() - 1;
                } else {
                    this.rsContext.index(nestedOffset + 1);
                    this.rsContext.field(field2);
                    field2.getBinding().get(this.rsContext);
                    value = this.rsContext.value();
                }
                record.values[index] = value;
                record.originals[index] = value;
            }
            catch (Exception e) {
                throw new SQLException("Error while reading field: " + String.valueOf(field2) + ", at JDBC index: " + (this.offset + index + 1), e);
            }
        }

        @Pro
        private final void initLongIndexes(ResultSet r) {
            try {
                ResultSetMetaData meta = null;
                try {
                    meta = r.getMetaData();
                }
                catch (NullPointerException | SQLException e) {
                    if (r == null) {
                        throw e;
                    }
                    return;
                }
                int size = meta.getColumnCount();
                int longIndex = 0;
                int otherIndex = 0;
                int[] longIndexesTemp = new int[size];
                int[] otherIndexesTemp = new int[size];
                for (int i = 0; i < size; ++i) {
                    int type = meta.getColumnType(i + 1);
                    if (type == -4 || type == -1) {
                        longIndexesTemp[longIndex++] = i;
                        continue;
                    }
                    otherIndexesTemp[otherIndex++] = i;
                }
                this.longIndexes = new int[longIndex];
                this.otherIndexes = new int[otherIndex];
                System.arraycopy(longIndexesTemp, 0, this.longIndexes, 0, this.longIndexes.length);
                System.arraycopy(otherIndexesTemp, 0, this.otherIndexes, 0, this.otherIndexes.length);
            }
            catch (SQLException e) {
                log.info((Object)"Exception while accessing ResultSetMetaData", e);
            }
        }
    }
}

