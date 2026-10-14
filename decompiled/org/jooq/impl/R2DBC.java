/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.Batch;
import io.r2dbc.spi.ColumnMetadata;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import io.r2dbc.spi.R2dbcException;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.r2dbc.spi.Statement;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLType;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jooq.Configuration;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.JSONB;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.TransactionalPublishable;
import org.jooq.XML;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractBatch;
import org.jooq.impl.AbstractDMLQuery;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractResultQuery;
import org.jooq.impl.AbstractRowCountQuery;
import org.jooq.impl.BatchMultiple;
import org.jooq.impl.BatchSingle;
import org.jooq.impl.Convert;
import org.jooq.impl.CursorImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBindContext;
import org.jooq.impl.DefaultBindingGetResultSetContext;
import org.jooq.impl.DefaultConnectionFactory;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.DefaultRenderContext;
import org.jooq.impl.Internal;
import org.jooq.impl.RecordDelegate;
import org.jooq.impl.ResultQueryTrait;
import org.jooq.impl.SimpleExecuteContext;
import org.jooq.impl.ThreadGuard;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;
import org.jooq.tools.jdbc.DefaultPreparedStatement;
import org.jooq.tools.jdbc.DefaultResultSet;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.jdbc.MockArray;
import org.jooq.types.Interval;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

final class R2DBC {
    private static final JooqLogger log = JooqLogger.getLogger(R2DBC.class);
    static volatile boolean is_0_9 = true;

    R2DBC() {
    }

    static final DefaultRenderContext.Rendered rendered(Configuration configuration, Query query) {
        DefaultRenderContext render = new DefaultRenderContext(configuration.deriveSettings(s2 -> R2DBC.setParamType(configuration.dialect(), s2)), null);
        return new DefaultRenderContext.Rendered(((RenderContext)((RenderContext)render.paramType(render.settings().getParamType())).visit(query)).render(), render.bindValues(), render.skipUpdateCounts());
    }

    static final long addNoOverflow(long x, long y) {
        long r = x + y;
        if (((x ^ r) & (y ^ r)) < 0L) {
            return Long.MAX_VALUE;
        }
        return r;
    }

    static final <T> T block(Publisher<? extends T> publisher) {
        Object complete = new Object();
        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        publisher.subscribe(Internal.subscriber(s2 -> s2.request(1L), queue::add, queue::add, () -> queue.add(complete)));
        try {
            Object result = queue.take();
            if (result instanceof Throwable) {
                Throwable t2 = (Throwable)result;
                throw new DataAccessException("Exception when blocking on publisher", t2);
            }
            if (result == complete) {
                return null;
            }
            return (T)result;
        }
        catch (InterruptedException e) {
            throw new DataAccessException("Exception when blocking on publisher", e);
        }
    }

    static final Connection getConnection(String url2) {
        return R2DBC.getConnection(url2, new Properties());
    }

    static final Connection getConnection(String url2, String username, String password) {
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        return R2DBC.getConnection(url2, properties);
    }

    static final Connection getConnection(String url2, Properties properties) {
        if (properties.isEmpty()) {
            return R2DBC.block(ConnectionFactories.get(url2).create());
        }
        ConnectionFactoryOptions.Builder builder = ConnectionFactoryOptions.parse(url2).mutate();
        properties.forEach((BiConsumer<? super Object, ? super Object>)((BiConsumer<Object, Object>)(k, v) -> {
            if ("user".equals(k)) {
                R2DBC.setOption(builder, ConnectionFactoryOptions.USER, v);
            } else if ("password".equals(k)) {
                R2DBC.setOption(builder, ConnectionFactoryOptions.PASSWORD, v);
            } else if ("host".equals(k)) {
                R2DBC.setOption(builder, ConnectionFactoryOptions.HOST, v);
            } else if ("port".equals(k)) {
                R2DBC.setOption(builder, ConnectionFactoryOptions.PORT, Integer.parseInt(String.valueOf(v)));
            } else if ("database".equals(k)) {
                R2DBC.setOption(builder, ConnectionFactoryOptions.DATABASE, v);
            } else if ("ssl".equals(k)) {
                R2DBC.setOption(builder, ConnectionFactoryOptions.SSL, v);
            } else {
                R2DBC.setOption(builder, Option.valueOf(String.valueOf(k)), v);
            }
        }));
        return R2DBC.block(ConnectionFactories.get(builder.build()).create());
    }

    private static <T> ConnectionFactoryOptions.Builder setOption(ConnectionFactoryOptions.Builder builder, Option<T> option, Object v) {
        return builder.option(option, option.cast(v));
    }

    static final void wrapExceptions(Runnable runnable2) {
        try {
            runnable2.run();
        }
        catch (R2dbcException e) {
            throw e;
        }
        catch (Exception e) {
            throw new R2DBCGenericException(e);
        }
    }

    static final <T> T wrapExceptions(Callable<T> callable) {
        try {
            return callable.call();
        }
        catch (R2dbcException e) {
            throw e;
        }
        catch (Exception e) {
            throw new R2DBCGenericException(e);
        }
    }

    static final Settings setParamType(SQLDialect dialect, Settings settings) {
        switch (dialect.family()) {
            case MYSQL: 
            case MARIADB: {
                return settings;
            }
            case ORACLE: {
                return settings.withParamType(ParamType.NAMED);
            }
            case SQLSERVER: {
                return settings.withParamType(ParamType.NAMED).withRenderNamedParamPrefix("@p").withParseNamedParamPrefix("@p");
            }
        }
        return settings.withParamType(ParamType.NAMED).withRenderNamedParamPrefix("$").withParseNamedParamPrefix("$");
    }

    static final boolean isR2dbc(java.sql.Statement statement) {
        return statement instanceof R2DBCPreparedStatement;
    }

    static final String sql0(Supplier<String> supplier) {
        try {
            return supplier.get();
        }
        catch (Throwable t2) {
            return "Error while rendering SQL: " + t2.getMessage();
        }
    }

    static final class R2DBCGenericException
    extends R2dbcException {
        R2DBCGenericException(Throwable cause) {
            super(cause);
        }
    }

    static final class R2DBCPreparedStatement
    extends DefaultPreparedStatement {
        final Configuration c;
        final Statement s;
        private static final Set<SQLDialect> NO_SUPPORT_UUID = SQLDialect.supportedBy(SQLDialect.MARIADB, SQLDialect.MYSQL);

        R2DBCPreparedStatement(Configuration c, Statement s2) {
            super(null, null, () -> new SQLFeatureNotSupportedException("Unsupported operation of the JDBC to R2DBC bridge."));
            this.c = c;
            this.s = s2;
        }

        private final void bindNonNull(int parameterIndex, Object x) {
            R2DBC.wrapExceptions(() -> {
                switch (this.c.family()) {
                    case SQLSERVER: {
                        this.s.bind("p" + parameterIndex, x);
                        break;
                    }
                    default: {
                        this.s.bind(parameterIndex - 1, x);
                    }
                }
            });
        }

        private final <T> void bindNull(int parameterIndex, Class<T> type) {
            R2DBC.wrapExceptions(() -> {
                switch (this.c.family()) {
                    case SQLSERVER: {
                        this.s.bindNull("p" + parameterIndex, type);
                        break;
                    }
                    default: {
                        this.s.bindNull(parameterIndex - 1, type);
                    }
                }
            });
        }

        private final <T> void bindNullable(int parameterIndex, T x, Class<T> type) {
            this.bindNullable(parameterIndex, x, type, t2 -> t2);
        }

        private final <T, U> void bindNullable(int parameterIndex, T x, Class<U> type, Function<? super T, ? extends U> conversion) {
            if (x == null) {
                this.bindNull(parameterIndex, type);
            } else {
                this.bindNonNull(parameterIndex, conversion.apply(x));
            }
        }

        private final Class<?> type(int sqlType) {
            switch (sqlType) {
                case 91: {
                    return LocalDate.class;
                }
                case 92: {
                    return LocalTime.class;
                }
                case 93: {
                    return LocalDateTime.class;
                }
            }
            return DefaultDataType.getDataType(this.c.family(), sqlType).getType();
        }

        private final Class<?> nullType(Class<?> type) {
            if (type == Date.class) {
                return LocalDate.class;
            }
            if (type == Time.class) {
                return LocalTime.class;
            }
            if (type == Timestamp.class) {
                return LocalDateTime.class;
            }
            if (type == Year.class) {
                return Integer.class;
            }
            if (type == XML.class) {
                return String.class;
            }
            if (type == JSON.class) {
                return String.class;
            }
            if (type == JSONB.class) {
                return String.class;
            }
            if (type == UUID.class && NO_SUPPORT_UUID.contains((Object)this.c.dialect())) {
                return String.class;
            }
            if (Enum.class.isAssignableFrom(type)) {
                return String.class;
            }
            if (Interval.class.isAssignableFrom(type)) {
                return String.class;
            }
            if (type == Boolean.class && this.c.family() == SQLDialect.ORACLE) {
                return Integer.class;
            }
            return type;
        }

        @Override
        public final void setNull(int parameterIndex, int sqlType) throws SQLException {
            this.bindNull(parameterIndex, this.type(sqlType));
        }

        @Override
        public final void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
            this.bindNull(parameterIndex, this.type(sqlType));
        }

        public final void setNull(int parameterIndex, DataType<?> dataType) {
            this.bindNull(parameterIndex, this.nullType(dataType.getType()));
        }

        @Override
        public final void setBoolean(int parameterIndex, boolean x) throws SQLException {
            switch (this.c.family()) {
                case MYSQL: {
                    this.bindNonNull(parameterIndex, x ? 1 : 0);
                    break;
                }
                default: {
                    this.bindNonNull(parameterIndex, x);
                }
            }
        }

        @Override
        public final void setByte(int parameterIndex, byte x) throws SQLException {
            this.bindNonNull(parameterIndex, x);
        }

        @Override
        public final void setShort(int parameterIndex, short x) throws SQLException {
            this.bindNonNull(parameterIndex, x);
        }

        @Override
        public final void setInt(int parameterIndex, int x) throws SQLException {
            this.bindNonNull(parameterIndex, x);
        }

        @Override
        public final void setLong(int parameterIndex, long x) throws SQLException {
            this.bindNonNull(parameterIndex, x);
        }

        @Override
        public final void setFloat(int parameterIndex, float x) throws SQLException {
            this.bindNonNull(parameterIndex, Float.valueOf(x));
        }

        @Override
        public final void setDouble(int parameterIndex, double x) throws SQLException {
            this.bindNonNull(parameterIndex, x);
        }

        @Override
        public final void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
            this.bindNullable(parameterIndex, x, BigDecimal.class);
        }

        @Override
        public final void setString(int parameterIndex, String x) throws SQLException {
            this.bindNullable(parameterIndex, x, String.class);
        }

        @Override
        public final void setNString(int parameterIndex, String value) throws SQLException {
            this.bindNullable(parameterIndex, value, String.class);
        }

        @Override
        public final void setBytes(int parameterIndex, byte[] x) throws SQLException {
            this.bindNullable(parameterIndex, x, byte[].class);
        }

        @Override
        public final void setDate(int parameterIndex, Date x) throws SQLException {
            this.bindNullable(parameterIndex, x, LocalDate.class, Date::toLocalDate);
        }

        @Override
        public final void setTime(int parameterIndex, Time x) throws SQLException {
            this.bindNullable(parameterIndex, x, LocalTime.class, Time::toLocalTime);
        }

        @Override
        public final void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
            this.bindNullable(parameterIndex, x, LocalDateTime.class, Timestamp::toLocalDateTime);
        }

        @Override
        public final void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
            this.bindNullable(parameterIndex, x, this.type(targetSqlType));
        }

        @Override
        public final void setObject(int parameterIndex, Object x) throws SQLException {
            this.bindNullable(parameterIndex, x, Object.class);
        }

        @Override
        public final void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
            this.setObject(parameterIndex, x, targetSqlType);
        }

        @Override
        public final void setObject(int parameterIndex, Object x, SQLType targetSqlType, int scaleOrLength) throws SQLException {
            this.setObject(parameterIndex, x, StringUtils.defaultIfNull(targetSqlType.getVendorTypeNumber(), 1111));
        }

        @Override
        public final void setObject(int parameterIndex, Object x, SQLType targetSqlType) throws SQLException {
            this.setObject(parameterIndex, x, StringUtils.defaultIfNull(targetSqlType.getVendorTypeNumber(), 1111));
        }

        @Pro
        public final void setBinaryFloat(int parameterIndex, float x) throws SQLException {
            this.setFloat(parameterIndex, x);
        }

        @Pro
        public final void setBinaryDouble(int parameterIndex, double x) throws SQLException {
            this.setDouble(parameterIndex, x);
        }
    }

    static final class BlockingTransactionSubscription<T>
    extends AbstractSubscription<T> {
        final DSLContext ctx;
        final TransactionalPublishable<T> transactional;

        BlockingTransactionSubscription(DSLContext ctx, Subscriber<? super T> subscriber, TransactionalPublishable<T> transactional) {
            super(subscriber);
            this.ctx = ctx;
            this.transactional = transactional;
        }

        @Override
        final void request0() {
            try {
                this.subscriber.onNext(this.ctx.transactionResult(c -> R2DBC.block(this.transactional.run(c))));
                this.subscriber.onComplete();
            }
            catch (Throwable t2) {
                this.subscriber.onError(t2);
            }
        }
    }

    static final class BlockingRowCountSubscription
    extends AbstractSubscription<Integer> {
        final AbstractRowCountQuery query;

        BlockingRowCountSubscription(AbstractRowCountQuery query, Subscriber<? super Integer> subscriber) {
            super(subscriber);
            this.query = query;
        }

        @Override
        final void request0() {
            try {
                if (this.query.isExecutable()) {
                    this.subscriber.onNext(this.query.execute());
                } else if (log.isDebugEnabled()) {
                    log.debug((Object)"Query is not executable", this.query);
                }
                this.subscriber.onComplete();
            }
            catch (Throwable t2) {
                this.subscriber.onError(t2);
            }
        }
    }

    static final class BlockingRecordSubscription<R extends Record>
    extends AbstractSubscription<R> {
        private final ResultQueryTrait<R> query;
        private volatile Cursor<R> c;

        BlockingRecordSubscription(ResultQueryTrait<R> query, Subscriber<? super R> subscriber) {
            super(subscriber);
            this.query = query;
        }

        @Override
        final synchronized void request0() {
            try {
                if (this.c == null) {
                    this.c = this.query.fetchLazyNonAutoClosing();
                }
                while (this.moreRequested()) {
                    R r = this.c.fetchNext();
                    if (r == null) {
                        this.subscriber.onComplete();
                        JDBCUtils.safeClose(this.c);
                        break;
                    }
                    this.subscriber.onNext(r);
                }
            }
            catch (Throwable t2) {
                this.subscriber.onError(t2);
                JDBCUtils.safeClose(this.c);
            }
        }

        @Override
        final void cancel0(boolean closeAfterTransaction, Runnable onComplete) {
            JDBCUtils.safeClose(this.c);
            onComplete.run();
        }
    }

    record R2DBCResultSetMetaData(Configuration c, RowMetadata m) implements ResultSetMetaData
    {
        private final ColumnMetadata meta(int column) {
            return this.m.getColumnMetadata(column - 1);
        }

        @Override
        public final <T> T unwrap(Class<T> iface) throws SQLException {
            throw new SQLFeatureNotSupportedException("R2DBC can't unwrap JDBC types");
        }

        @Override
        public final boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }

        @Override
        public final int getColumnCount() throws SQLException {
            return this.m.getColumnMetadatas().size();
        }

        @Override
        public final int isNullable(int column) throws SQLException {
            switch (this.meta(column).getNullability()) {
                case NON_NULL: {
                    return 0;
                }
                case NULLABLE: {
                    return 1;
                }
                case UNKNOWN: {
                    return 2;
                }
            }
            throw new SQLFeatureNotSupportedException("Nullability: " + this.meta(column).getNullability().toString());
        }

        @Override
        public final String getCatalogName(int column) throws SQLException {
            return "";
        }

        @Override
        public final String getSchemaName(int column) throws SQLException {
            return "";
        }

        @Override
        public final String getTableName(int column) throws SQLException {
            return "";
        }

        @Override
        public final String getColumnLabel(int column) throws SQLException {
            return this.getColumnName(column);
        }

        @Override
        public final String getColumnName(int column) throws SQLException {
            return this.meta(column).getName();
        }

        @Override
        public final int getPrecision(int column) throws SQLException {
            return StringUtils.defaultIfNull(this.meta(column).getPrecision(), 0);
        }

        @Override
        public final int getScale(int column) throws SQLException {
            return StringUtils.defaultIfNull(this.meta(column).getScale(), 0);
        }

        private final Class<?> getType(int column) {
            return StringUtils.defaultIfNull(this.meta(column).getJavaType(), Object.class);
        }

        private final DataType<?> getDataType(int column) {
            return DefaultDataType.getDataType(this.c.family(), this.getType(column));
        }

        @Override
        public final int getColumnType(int column) throws SQLException {
            return this.getDataType(column).getSQLType();
        }

        @Override
        public final String getColumnClassName(int column) throws SQLException {
            return this.getType(column).getName();
        }

        @Override
        public final String getColumnTypeName(int column) throws SQLException {
            if (is_0_9) {
                try {
                    return this.meta(column).getType().getName();
                }
                catch (AbstractMethodError e) {
                    is_0_9 = false;
                }
            }
            return this.getDataType(column).getName();
        }

        @Override
        public final boolean isReadOnly(int column) throws SQLException {
            return false;
        }

        @Override
        public final boolean isWritable(int column) throws SQLException {
            return true;
        }

        @Override
        public final boolean isDefinitelyWritable(int column) throws SQLException {
            return true;
        }

        @Override
        public final boolean isSigned(int column) throws SQLException {
            return false;
        }

        @Override
        public final int getColumnDisplaySize(int column) throws SQLException {
            return 0;
        }

        @Override
        public final boolean isAutoIncrement(int column) throws SQLException {
            return false;
        }

        @Override
        public final boolean isCaseSensitive(int column) throws SQLException {
            return false;
        }

        @Override
        public final boolean isSearchable(int column) throws SQLException {
            return false;
        }

        @Override
        public final boolean isCurrency(int column) throws SQLException {
            return false;
        }
    }

    static final class R2DBCResultSet
    extends DefaultResultSet {
        final Configuration c;
        final Row r;
        final RowMetadata m;
        boolean wasNull;

        R2DBCResultSet(Configuration c, Row r, RowMetadata m4) {
            super(null, null, () -> new SQLFeatureNotSupportedException("Unsupported operation of the JDBC to R2DBC bridge."));
            this.c = c;
            this.r = new DefaultRow(c, r);
            this.m = m4;
        }

        private final <T> T wasNull(T nullable) {
            this.wasNull = nullable == null;
            return nullable;
        }

        private final <T> T nullable(int columnIndex, Class<T> type) {
            return (T)this.nullable(columnIndex, type, t2 -> t2);
        }

        private final <T, U> U nullable(int columnIndex, Class<T> type, Function<? super T, ? extends U> conversion) {
            return (U)R2DBC.wrapExceptions(() -> {
                Object t2 = this.wasNull(this.r.get(columnIndex - 1, type));
                return this.wasNull ? null : conversion.apply((Object)t2);
            });
        }

        private final <U> U nullable(int columnIndex, Function<? super Object, ? extends U> conversion) {
            return (U)R2DBC.wrapExceptions(() -> {
                Object t2 = this.wasNull(this.r.get(columnIndex - 1));
                return this.wasNull ? null : conversion.apply(t2);
            });
        }

        private final <T> T nonNull(int columnIndex, Class<T> type, T nullValue) {
            return (T)R2DBC.wrapExceptions(() -> {
                Object t2 = this.wasNull(this.r.get(columnIndex - 1, type));
                return this.wasNull ? nullValue : t2;
            });
        }

        @Override
        public final boolean wasNull() throws SQLException {
            return this.wasNull;
        }

        @Override
        public final boolean getBoolean(int columnIndex) throws SQLException {
            return this.nonNull(columnIndex, Boolean.class, false);
        }

        @Override
        public final byte getByte(int columnIndex) throws SQLException {
            return this.nonNull(columnIndex, Byte.class, (byte)0);
        }

        @Override
        public final short getShort(int columnIndex) throws SQLException {
            return this.nonNull(columnIndex, Short.class, (short)0);
        }

        @Override
        public final int getInt(int columnIndex) throws SQLException {
            return this.nonNull(columnIndex, Integer.class, 0);
        }

        @Override
        public final long getLong(int columnIndex) throws SQLException {
            return this.nonNull(columnIndex, Long.class, 0L);
        }

        @Override
        public final float getFloat(int columnIndex) throws SQLException {
            return this.nonNull(columnIndex, Float.class, Float.valueOf(0.0f)).floatValue();
        }

        @Override
        public final double getDouble(int columnIndex) throws SQLException {
            return this.nonNull(columnIndex, Double.class, 0.0);
        }

        @Override
        public final BigDecimal getBigDecimal(int columnIndex) throws SQLException {
            return this.nullable(columnIndex, BigDecimal.class);
        }

        @Override
        public final String getString(int columnIndex) throws SQLException {
            return this.nullable(columnIndex, String.class);
        }

        @Override
        public final byte[] getBytes(int columnIndex) throws SQLException {
            if (this.c.family() == SQLDialect.ORACLE) {
                return this.nullable(columnIndex, (? super Object o) -> {
                    byte[] byArray;
                    if (o instanceof ByteBuffer) {
                        ByteBuffer b = (ByteBuffer)o;
                        byArray = b.array();
                    } else {
                        byArray = Convert.convert(o, byte[].class);
                    }
                    return byArray;
                });
            }
            return this.nullable(columnIndex, byte[].class);
        }

        @Override
        public final Date getDate(int columnIndex) throws SQLException {
            return this.nullable(columnIndex, LocalDate.class, Date::valueOf);
        }

        @Override
        public final Time getTime(int columnIndex) throws SQLException {
            return this.nullable(columnIndex, LocalTime.class, Time::valueOf);
        }

        @Override
        public final Timestamp getTimestamp(int columnIndex) throws SQLException {
            return this.nullable(columnIndex, LocalDateTime.class, Timestamp::valueOf);
        }

        @Override
        public final Object getObject(int columnIndex) throws SQLException {
            return this.getObject(columnIndex, Object.class);
        }

        @Override
        public final <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
            return this.nullable(columnIndex, type);
        }

        @Override
        public final Array getArray(int columnIndex) throws SQLException {
            return new MockArray<Object>(this.c.dialect(), (Object[])this.nullable(columnIndex, Object.class), Object[].class);
        }

        private record DefaultRow(Configuration c, Row r) implements Row
        {
            @Override
            public final <T> T get(int index, Class<T> uType) {
                return (T)R2DBC.wrapExceptions(() -> {
                    switch (this.c.family()) {
                        case MYSQL: 
                        case H2: {
                            return this.get0(this.r.get(index), uType);
                        }
                    }
                    return this.r.get(index, uType);
                });
            }

            @Override
            public final <T> T get(String name, Class<T> uType) {
                return (T)R2DBC.wrapExceptions(() -> {
                    switch (this.c.family()) {
                        case MYSQL: 
                        case H2: {
                            return this.get0(this.r.get(name), uType);
                        }
                    }
                    return this.r.get(name, uType);
                });
            }

            private final <T> T get0(Object o, Class<T> uType) {
                if (o == null) {
                    return null;
                }
                Converter<?, T> converter = this.c.converterProvider().provide(o.getClass(), uType);
                if (converter == null) {
                    throw new DataTypeException("Cannot convert from " + String.valueOf(o.getClass()) + " to " + String.valueOf(uType) + ". Please report an issue here: https://jooq.org/bug. As a workaround, you can implement a ConverterProvider.");
                }
                return ContextConverter.scoped(converter).from(o, Tools.converterContext(this.c));
            }

            @Override
            public final RowMetadata getMetadata() {
                return this.r.getMetadata();
            }
        }
    }

    static final class TransactionSubscription<T>
    extends AbstractNonBlockingSubscription<T> {
        final TransactionalPublishable<T> transactional;
        final ConnectionSubscriber<T> delegate;

        TransactionSubscription(DSLContext ctx, final Subscriber<? super T> subscriber, final TransactionalPublishable<T> transactional) {
            super(ctx.configuration(), subscriber);
            this.transactional = transactional;
            this.delegate = new ConnectionSubscriber<T>(this){

                @Override
                void onNext0(Connection c) {
                    c.beginTransaction().subscribe(Internal.subscriber(s2 -> s2.request(1L), v -> {}, subscriber::onError, () -> transactional.run(c instanceof DefaultConnectionFactory.NonClosingConnection ? configuration : configuration.derive(new DefaultConnectionFactory(c))).subscribe(Internal.subscriber(s1 -> s1.request(Long.MAX_VALUE), subscriber::onNext, e -> c.rollbackTransaction().subscribe(Internal.subscriber(s2 -> s2.request(1L), v -> {}, t2 -> this.cancel0(true, () -> subscriber.onError((Throwable)t2)), () -> this.cancel0(true, () -> subscriber.onError((Throwable)e)))), () -> c.commitTransaction().subscribe(Internal.subscriber(s2 -> s2.request(1L), v -> {}, t2 -> this.cancel0(true, () -> subscriber.onError((Throwable)t2)), () -> this.cancel0(true, () -> subscriber.onComplete())))))));
                }
            };
        }

        @Override
        final String sql() {
            return "TransactionSubscription";
        }

        @Override
        final ConnectionSubscriber<T> delegate() {
            return this.delegate;
        }
    }

    static final class BatchSubscription<B extends AbstractBatch>
    extends AbstractNonBlockingSubscription<Integer> {
        final ConnectionSubscriber<Integer> batchSubscriber;
        final B batch;

        BatchSubscription(B batch, Subscriber<? super Integer> subscriber, Function<BatchSubscription<B>, ConnectionSubscriber<Integer>> batchSubscriber) {
            super(((AbstractBatch)batch).configuration, subscriber);
            this.batchSubscriber = batchSubscriber.apply(this);
            this.batch = batch;
        }

        @Override
        final ConnectionSubscriber<Integer> delegate() {
            return this.batchSubscriber;
        }

        @Override
        final String sql() {
            return R2DBC.sql0(() -> this.batch.toString());
        }
    }

    static final class QuerySubscription<T, Q extends Query>
    extends AbstractNonBlockingSubscription<T> {
        final QueryExecutionSubscriber<T, Q> queryExecutionSubscriber;

        QuerySubscription(Q query, Subscriber<? super T> subscriber, BiFunction<Q, AbstractNonBlockingSubscription<T>, Subscriber<Result>> resultSubscriber) {
            super(query.configuration(), subscriber);
            this.queryExecutionSubscriber = new QueryExecutionSubscriber<T, Q>(query, this, resultSubscriber);
        }

        final QueryExecutionSubscriber<T, Q> delegate() {
            return this.queryExecutionSubscriber;
        }

        @Override
        final String sql() {
            String result = this.queryExecutionSubscriber.sql;
            return result != null ? result : R2DBC.sql0(() -> String.valueOf(this.queryExecutionSubscriber.query));
        }
    }

    static abstract class AbstractNonBlockingSubscription<T>
    extends AbstractSubscription<T> {
        final Configuration configuration;
        final AtomicBoolean subscribed;
        final Publisher<? extends Connection> connection;
        final AtomicInteger nextForwarderIndex;
        final ConcurrentMap<Integer, Forwarding<T>> forwarders;

        AbstractNonBlockingSubscription(Configuration configuration, Subscriber<? super T> subscriber) {
            super(subscriber);
            this.configuration = configuration;
            this.subscribed = new AtomicBoolean();
            this.connection = configuration.connectionFactory().create();
            this.nextForwarderIndex = new AtomicInteger();
            this.forwarders = new ConcurrentHashMap<Integer, Forwarding<T>>();
        }

        abstract String sql();

        @Override
        final void request0() {
            if (!this.subscribed.getAndSet(true)) {
                ConnectionSubscriber<T> delegate = this.delegate();
                this.connection.subscribe(Internal.subscriber(delegate::onSubscribe, c -> {
                    delegate.onNext((Connection)c);
                    this.request1();
                }, delegate::onError, delegate::onComplete));
            } else {
                this.request1();
            }
        }

        private final void forAllForwardingSubscriptions(Consumer<? super Subscription> consumer) {
            for (Forwarding f : this.forwarders.values()) {
                Subscription s2 = f.subscription.get();
                if (s2 == null) continue;
                consumer.accept(s2);
            }
        }

        private final void request1() {
            this.forAllForwardingSubscriptions(this::request2);
        }

        final void request2(Subscription s2) {
            if (this.moreRequested()) {
                s2.request(1L);
            }
        }

        @Override
        final void cancel0(boolean closeAfterTransaction, Runnable onComplete) {
            this.forAllForwardingSubscriptions(Subscription::cancel);
            this.delegate().connection.updateAndGet(c -> {
                if (c == null || c instanceof DefaultConnectionFactory.NonClosingConnection || this instanceof TransactionSubscription && !closeAfterTransaction) {
                    onComplete.run();
                    return c;
                }
                c.close().subscribe(Internal.subscriber(s2 -> s2.request(Long.MAX_VALUE), t2 -> {}, t2 -> {}, onComplete));
                return null;
            });
        }

        abstract ConnectionSubscriber<T> delegate();

        final Forwarding<T> forwardingSubscriber(AbstractResultSubscriber<T> resultSubscriber) {
            int i = this.nextForwarderIndex.getAndIncrement();
            Forwarding<T> f = new Forwarding<T>(i, resultSubscriber);
            this.forwarders.put(i, f);
            return f;
        }
    }

    static final class BatchSingleSubscriber
    extends ConnectionSubscriber<Integer> {
        final BatchSingle batch;

        BatchSingleSubscriber(BatchSingle batch, BatchSubscription<BatchSingle> downstream) {
            super(downstream);
            this.batch = batch;
        }

        @Override
        final void onNext0(Connection c) {
            try {
                this.batch.checkBindValues();
                DefaultRenderContext.Rendered rendered = R2DBC.rendered(this.batch.configuration, this.batch.query);
                Statement stmt = c.createStatement(rendered.sql);
                Param<?>[] params = rendered.bindValues.toArray((E[])Tools.EMPTY_PARAM);
                boolean first = true;
                for (Object[] bindValues : this.batch.allBindValues) {
                    if (first) {
                        first = false;
                    } else {
                        stmt = stmt.add();
                    }
                    Tools.visitAll(new DefaultBindContext(this.batch.configuration, null, new R2DBCPreparedStatement(this.batch.query.configuration(), stmt)), params.length > 0 ? Tools.fields(bindValues, params) : Tools.fields(bindValues));
                }
                stmt.execute().subscribe(new RowCountSubscriber(this.downstream));
            }
            catch (Throwable t2) {
                this.downstream.cancel();
                this.onError(t2);
            }
        }
    }

    static final class BatchMultipleSubscriber
    extends ConnectionSubscriber<Integer> {
        final BatchMultiple batch;

        BatchMultipleSubscriber(BatchMultiple batch, BatchSubscription<BatchMultiple> downstream) {
            super(downstream);
            this.batch = batch;
        }

        @Override
        final void onNext0(Connection c) {
            try {
                Batch b = c.createBatch();
                for (int i = 0; i < this.batch.queries.length; ++i) {
                    b = b.add(DSL.using(this.batch.configuration).renderInlined(this.batch.queries[i]));
                }
                b.execute().subscribe(new RowCountSubscriber(this.downstream));
            }
            catch (Throwable t2) {
                this.downstream.cancel();
                this.onError(t2);
            }
        }
    }

    record NoOpSubscription(Subscriber<?> subscriber) implements Subscription
    {
        @Override
        public void request(long n) {
            this.subscriber.onComplete();
        }

        @Override
        public void cancel() {
            this.subscriber.onComplete();
        }
    }

    static final class QueryExecutionSubscriber<T, Q extends Query>
    extends ConnectionSubscriber<T> {
        final Q query;
        final Configuration configuration;
        final BiFunction<Q, AbstractNonBlockingSubscription<T>, Subscriber<Result>> resultSubscriber;
        volatile String sql;

        QueryExecutionSubscriber(Q query, QuerySubscription<T, Q> downstream, BiFunction<Q, AbstractNonBlockingSubscription<T>, Subscriber<Result>> resultSubscriber) {
            super(downstream);
            this.query = query;
            this.configuration = query.configuration();
            this.resultSubscriber = resultSubscriber;
        }

        @Override
        final void onNext0(Connection c) {
            try {
                if (this.query.isExecutable()) {
                    AbstractDMLQuery<?> q2;
                    int f;
                    DefaultRenderContext.Rendered rendered = R2DBC.rendered(this.configuration, this.query);
                    this.sql = rendered.sql;
                    Statement stmt = c.createStatement(this.sql);
                    new DefaultBindContext(this.configuration, null, new R2DBCPreparedStatement(this.configuration, stmt)).visit(rendered.bindValues);
                    AbstractResultQuery<?> q1 = Tools.abstractResultQuery(this.query);
                    if (q1 != null && (f = SettingsTools.getFetchSize(q1.fetchSize(), this.configuration.settings())) != 0) {
                        if (log.isDebugEnabled()) {
                            log.debug((Object)"Setting fetch size", f);
                        }
                        stmt.fetchSize(f);
                    }
                    if (!((q2 = Tools.abstractDMLQuery(this.query)) == null || q2.returning.isEmpty() || q2.emulateReturningUsingOutput(this.configuration.dsl()) || q2.nativeSupportReturningOrDataChangeDeltaTable(this.configuration.dsl()))) {
                        stmt.returnGeneratedValues(Tools.map(q2.returningResolvedAsterisks, Field::getName, String[]::new));
                    }
                    stmt.execute().subscribe(this.resultSubscriber.apply(this.query, this.downstream));
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug((Object)"Query is not executable", this.query);
                    }
                    Subscriber<Result> s2 = this.resultSubscriber.apply(this.query, this.downstream);
                    s2.onSubscribe(new NoOpSubscription(s2));
                }
            }
            catch (Throwable t2) {
                this.downstream.cancel();
                this.onError(t2);
            }
        }
    }

    static abstract class ConnectionSubscriber<T>
    implements Subscriber<Connection> {
        final AbstractNonBlockingSubscription<T> downstream;
        final AtomicReference<Connection> connection;

        ConnectionSubscriber(AbstractNonBlockingSubscription<T> downstream) {
            this.downstream = downstream;
            this.connection = new AtomicReference();
        }

        @Override
        public final void onSubscribe(Subscription s2) {
            s2.request(1L);
        }

        @Override
        public final void onNext(Connection c) {
            this.connection.set(c);
            this.onNext0(c);
        }

        abstract void onNext0(Connection var1);

        @Override
        public final void onError(Throwable t2) {
            this.downstream.subscriber.onError(Tools.translate(this.downstream.sql(), t2));
        }

        @Override
        public final void onComplete() {
        }
    }

    static final class ResultSubscriber<R extends Record, Q extends ResultQueryTrait<R>>
    extends AbstractResultSubscriber<R> {
        final Q query;

        ResultSubscriber(Q query, AbstractNonBlockingSubscription<? super R> downstream) {
            super(downstream);
            this.query = query;
        }

        @Override
        public final void onNext(Result r) {
            r.map((row, meta) -> {
                try {
                    Field<?>[] fields = this.query.getFields(() -> new R2DBCResultSetMetaData(this.query.configuration(), (RowMetadata)meta));
                    RecordDelegate<AbstractRecord> delegate = Tools.newRecord(true, Tools.recordFactory(this.query.getRecordType(), Tools.row0(fields)), this.query.configuration());
                    DefaultBindingGetResultSetContext ctx = new DefaultBindingGetResultSetContext(new SimpleExecuteContext(this.query.configuration(), this.query.configuration().data()), new R2DBCResultSet(this.query.configuration(), (Row)row, (RowMetadata)meta), 0);
                    return delegate.operate(new CursorImpl.CursorRecordInitialiser(new DefaultExecuteContext(this.query.configuration(), (Query)this.query), new DefaultExecuteListener(), ctx, Tools.row0(fields), 0, new boolean[0]));
                }
                catch (Throwable t2) {
                    this.onError(t2);
                    return null;
                }
            }).subscribe(this.downstream.forwardingSubscriber(this));
        }
    }

    static final class RowCountSubscriber
    extends AbstractResultSubscriber<Integer> {
        RowCountSubscriber(AbstractNonBlockingSubscription<? super Integer> downstream) {
            super(downstream);
        }

        @Override
        public void onNext(Result r) {
            Forwarding<Integer> s2 = this.downstream.forwardingSubscriber(this);
            r.getRowsUpdated().subscribe(Internal.subscriber(s2::onSubscribe, t2 -> {
                if (t2 instanceof Long) {
                    Long l = (Long)t2;
                    s2.onNext(l.intValue());
                } else {
                    s2.onNext((Integer)t2);
                }
            }, s2::onError, s2::onComplete));
        }
    }

    static abstract class AbstractResultSubscriber<T>
    implements Subscriber<Result> {
        final AbstractNonBlockingSubscription<? super T> downstream;
        final AtomicBoolean completed;
        final AtomicBoolean completionRequested;

        AbstractResultSubscriber(AbstractNonBlockingSubscription<? super T> downstream) {
            this.downstream = downstream;
            this.completed = new AtomicBoolean();
            this.completionRequested = new AtomicBoolean();
        }

        @Override
        public final void onSubscribe(Subscription s2) {
            s2.request(Long.MAX_VALUE);
        }

        @Override
        public final void onError(Throwable t2) {
            this.complete(true, () -> this.downstream.subscriber.onError(Tools.translate(this.downstream.sql(), t2)));
        }

        @Override
        public final void onComplete() {
            this.complete(false, () -> this.downstream.subscriber.onComplete());
        }

        final void complete(boolean cancelled, Runnable onComplete) {
            this.completionRequested.set(true);
            if ((cancelled || this.downstream.forwarders.isEmpty()) && !this.completed.getAndSet(true)) {
                this.downstream.complete(onComplete);
            }
        }
    }

    static final class Forwarding<T>
    implements Subscriber<T> {
        final int forwarderIndex;
        final AbstractResultSubscriber<T> resultSubscriber;
        final AtomicReference<Subscription> subscription;

        Forwarding(int forwarderIndex, AbstractResultSubscriber<T> resultSubscriber) {
            this.forwarderIndex = forwarderIndex;
            this.resultSubscriber = resultSubscriber;
            this.subscription = new AtomicReference();
        }

        @Override
        public final void onSubscribe(Subscription s2) {
            this.subscription.set(s2);
            this.resultSubscriber.downstream.request2(s2);
        }

        @Override
        public final void onNext(T value) {
            if (!this.resultSubscriber.downstream.completed.get()) {
                this.resultSubscriber.downstream.subscriber.onNext(value);
                this.resultSubscriber.downstream.request2(this.subscription.get());
            }
        }

        @Override
        public final void onError(Throwable t2) {
            this.complete(true, () -> this.resultSubscriber.downstream.subscriber.onError(Tools.translate(this.resultSubscriber.downstream.sql(), t2)));
        }

        @Override
        public final void onComplete() {
            this.complete(false, () -> this.resultSubscriber.downstream.subscriber.onComplete());
        }

        private final void complete(boolean cancelled, Runnable onComplete) {
            this.resultSubscriber.downstream.forwarders.remove(this.forwarderIndex);
            if (this.resultSubscriber.downstream.forwarders.isEmpty() && (cancelled || this.resultSubscriber.completionRequested.get())) {
                this.resultSubscriber.complete(cancelled, onComplete);
            }
        }
    }

    static abstract class AbstractSubscription<T>
    implements Subscription {
        final AtomicBoolean completed = new AtomicBoolean();
        final AtomicLong requested = new AtomicLong();
        final Subscriber<? super T> subscriber;
        final ThreadGuard.Guard guard = new ThreadGuard.Guard();

        static <T> Subscription onRequest(Subscriber<? super T> s2, final Consumer<? super Subscriber<? super T>> onRequest) {
            return new AbstractSubscription<T>(s2){

                @Override
                void request0() {
                    onRequest.accept(this.subscriber);
                }
            };
        }

        AbstractSubscription(Subscriber<? super T> subscriber) {
            this.subscriber = Internal.subscriber(subscriber::onSubscribe, subscriber::onNext, subscriber::onError, () -> {
                this.completed.set(true);
                subscriber.onComplete();
            });
        }

        @Override
        public final void request(long n) {
            if (n <= 0L) {
                this.subscriber.onError(new IllegalArgumentException("Rule 3.9 non-positive request signals are illegal"));
            } else if (!this.completed.get()) {
                this.requested.accumulateAndGet(n, R2DBC::addNoOverflow);
                ThreadGuard.run(this.guard, this::request0, () -> {});
            }
        }

        @Override
        public final void cancel() {
            this.complete(() -> {});
        }

        final boolean moreRequested() {
            return !this.completed.get() && this.requested.getAndUpdate(l -> l == Long.MAX_VALUE ? l : Math.max(0L, l - 1L)) > 0L;
        }

        final void complete(Runnable onComplete) {
            if (!this.completed.getAndSet(true)) {
                this.cancel0(false, onComplete);
            }
        }

        abstract void request0();

        void cancel0(boolean closeAfterTransaction, Runnable onComplete) {
        }
    }
}

