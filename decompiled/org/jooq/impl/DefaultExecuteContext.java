/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  oracle.jdbc.OracleConnection
 */
package org.jooq.impl;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import oracle.jdbc.OracleConnection;
import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.ConverterContext;
import org.jooq.DDLQuery;
import org.jooq.DSLContext;
import org.jooq.Delete;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteType;
import org.jooq.Insert;
import org.jooq.Merge;
import org.jooq.Pro;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Routine;
import org.jooq.SQLDialect;
import org.jooq.Scope;
import org.jooq.Update;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.DataMap;
import org.jooq.impl.DefaultConverterContext;
import org.jooq.impl.DiagnosticsConnection;
import org.jooq.impl.Patterns;
import org.jooq.impl.ProviderEnabledConnection;
import org.jooq.impl.SettingsEnabledConnection;
import org.jooq.impl.ThrowingRunnable;
import org.jooq.impl.ThrowingSupplier;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.tools.reflect.Reflect;

class DefaultExecuteContext
implements ExecuteContext {
    private static final JooqLogger log = JooqLogger.getLogger(DefaultExecuteContext.class);
    private static final JooqLogger logVersionSupport = JooqLogger.getLogger(DefaultExecuteContext.class, "logVersionSupport", 1);
    private static final JooqLogger logDefaultDialect = JooqLogger.getLogger(DefaultExecuteContext.class, "logDefaultDialect", 1);
    private final ConverterContext converterContext;
    private final Instant creationTime;
    private final Configuration originalConfiguration;
    private final Configuration derivedConfiguration;
    private final Map<Object, Object> data;
    @Pro
    private final Query originalQuery;
    @Pro
    private final Query[] originalBatchQueries;
    private Query query;
    private final Routine<?> routine;
    private String sql;
    private final ExecuteContext.BatchMode batchMode;
    private Query[] batchQueries;
    private String[] batchSQL;
    private int[] batchRows;
    ConnectionProvider connectionProvider;
    private Connection connection;
    private Connection wrappedConnection;
    private PreparedStatement statement;
    private int statementExecutionCount;
    private ResultSet resultSet;
    private Record record;
    private Result<?> result;
    int recordLevel;
    int resultLevel;
    private int rows = -1;
    private RuntimeException exception;
    private SQLException sqlException;
    private SQLWarning sqlWarning;
    private String[] serverOutput;
    private static final ThreadLocal<List<AutoCloseable>> RESOURCES = new ThreadLocal();
    private static final ThreadLocal<ExecuteContext> LOCAL_EXECUTE_CONTEXT = new ThreadLocal();
    private static final ThreadLocal<Connection> LOCAL_CONNECTION = new ThreadLocal();

    static final void clean() {
        List<AutoCloseable> resources = RESOURCES.get();
        if (resources != null) {
            for (AutoCloseable resource : resources) {
                JDBCUtils.safeClose(resource);
            }
            RESOURCES.remove();
        }
        LOCAL_CONNECTION.remove();
    }

    static final void register(Blob blob) {
        DefaultExecuteContext.register(blob::free);
    }

    static final void register(Clob clob) {
        DefaultExecuteContext.register(clob::free);
    }

    static final void register(SQLXML xml) {
        DefaultExecuteContext.register(xml::free);
    }

    static final void register(Array array) {
        DefaultExecuteContext.register(array::free);
    }

    static final void register(AutoCloseable closeable) {
        List<AutoCloseable> list = RESOURCES.get();
        if (list == null) {
            list = new ArrayList<AutoCloseable>();
            RESOURCES.set(list);
        }
        list.add(closeable);
    }

    static final ExecuteContext localExecuteContext() {
        return LOCAL_EXECUTE_CONTEXT.get();
    }

    static final <E extends Exception> void localExecuteContext(ExecuteContext ctx, ThrowingRunnable<E> runnable2) throws E {
        DefaultExecuteContext.localExecuteContext(ctx, () -> {
            runnable2.run();
            return null;
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static final <T, E extends Exception> T localExecuteContext(ExecuteContext ctx, ThrowingSupplier<T, E> supplier) throws E {
        ExecuteContext old = DefaultExecuteContext.localExecuteContext();
        try {
            LOCAL_EXECUTE_CONTEXT.set(ctx);
            T t2 = supplier.get();
            return t2;
        }
        finally {
            LOCAL_EXECUTE_CONTEXT.set(old);
        }
    }

    static final Connection localConnection() {
        return LOCAL_CONNECTION.get();
    }

    static final Connection localTargetConnection(Scope scope) {
        Connection result = DefaultExecuteContext.localConnection();
        if (result instanceof OracleConnection) {
            return result;
        }
        try {
            return (Connection)scope.configuration().unwrapperProvider().provide().unwrap(result, OracleConnection.class);
        }
        catch (NoClassDefFoundError e2) {
            log.warn((Object)"ojdbc class not found", e2);
            log.info("Could not unwrap native Connection type. Consider implementing an org.jooq.UnwrapperProvider");
            return result;
        }
    }

    DefaultExecuteContext(Configuration configuration) {
        this(configuration, ExecuteContext.BatchMode.NONE, null, null, null);
    }

    DefaultExecuteContext(Configuration configuration, ExecuteContext.BatchMode batchMode, Query[] batchQueries) {
        this(configuration, batchMode, null, batchQueries, null);
    }

    DefaultExecuteContext(Configuration configuration, Query query) {
        this(configuration, ExecuteContext.BatchMode.NONE, query, null, null);
    }

    DefaultExecuteContext(Configuration configuration, Routine<?> routine) {
        this(configuration, ExecuteContext.BatchMode.NONE, null, null, routine);
    }

    private DefaultExecuteContext(Configuration configuration, ExecuteContext.BatchMode batchMode, Query query, Query[] batchQueries, Routine<?> routine) {
        this.creationTime = configuration.clock().instant();
        this.connectionProvider = configuration.connectionProvider();
        this.originalConfiguration = configuration;
        this.derivedConfiguration = configuration.derive(new ExecuteContextConnectionProvider());
        this.data = new DataMap();
        this.batchMode = batchMode;
        this.query = query;
        this.originalQuery = query;
        this.originalBatchQueries = batchQueries != null ? (Query[])batchQueries.clone() : batchQueries;
        this.routine = routine;
        this.converterContext = new DefaultConverterContext(this.derivedConfiguration, this.data);
        this.batchQueries0(batchQueries);
        DefaultExecuteContext.clean();
    }

    @Override
    public final ConverterContext converterContext() {
        return this.converterContext;
    }

    @Override
    public final Instant creationTime() {
        return this.creationTime;
    }

    @Override
    public final Map<Object, Object> data() {
        return this.data;
    }

    @Override
    public final Object data(Object key) {
        return this.data.get(key);
    }

    @Override
    public final Object data(Object key, Object value) {
        return this.data.put(key, value);
    }

    @Override
    public final ExecuteType type() {
        if (this.routine != null) {
            return ExecuteType.ROUTINE;
        }
        if (this.batchMode != ExecuteContext.BatchMode.NONE) {
            return ExecuteType.BATCH;
        }
        if (this.query != null) {
            if (this.query instanceof ResultQuery) {
                return ExecuteType.READ;
            }
            if (this.query instanceof Insert || this.query instanceof Update || this.query instanceof Delete || this.query instanceof Merge) {
                return ExecuteType.WRITE;
            }
            if (this.query instanceof DDLQuery) {
                return ExecuteType.DDL;
            }
            String s2 = this.query.getSQL().toLowerCase(SettingsTools.renderLocale(this.configuration().settings()));
            if (s2.matches("^(with\\b.*?\\bselect|select|explain)\\b.*?")) {
                return ExecuteType.READ;
            }
            if (s2.matches("^(insert|update|delete|merge|replace|upsert|lock)\\b.*?")) {
                return ExecuteType.WRITE;
            }
            if (s2.matches("^(create|alter|drop|truncate|grant|revoke|analyze|comment|flashback|enable|disable)\\b.*?")) {
                return ExecuteType.DDL;
            }
            if (s2.matches("^\\s*\\{\\s*(\\?\\s*=\\s*)call.*?")) {
                return ExecuteType.ROUTINE;
            }
            if (s2.matches("^(call|begin|declare)\\b.*?")) {
                return ExecuteType.ROUTINE;
            }
        } else if (this.resultSet != null) {
            return ExecuteType.READ;
        }
        return ExecuteType.OTHER;
    }

    @Override
    public final Query query() {
        return this.query;
    }

    @Override
    public final Query originalQuery() {
        return this.originalQuery;
    }

    @Override
    public final Query transformedQuery() {
        return this.query();
    }

    @Override
    public final void query(Query newQuery) {
        this.query = newQuery;
    }

    @Override
    public final ExecuteContext.BatchMode batchMode() {
        return this.batchMode;
    }

    @Override
    public final Query[] batchQueries() {
        Query[] queryArray;
        if (this.batchMode != ExecuteContext.BatchMode.NONE) {
            queryArray = this.batchQueries;
        } else if (this.query() != null) {
            Query[] queryArray2 = new Query[1];
            queryArray = queryArray2;
            queryArray2[0] = this.query();
        } else {
            queryArray = Tools.EMPTY_QUERY;
        }
        return queryArray;
    }

    @Override
    public final Query[] originalBatchQueries() {
        return this.originalBatchQueries != null ? (Query[])this.originalBatchQueries.clone() : this.originalBatchQueries;
    }

    @Override
    public final Query[] transformedBatchQueries() {
        return this.batchQueries();
    }

    @Override
    public final void batchQueries(Query ... newQueries) {
        this.batchQueries0(newQueries);
    }

    private final void batchQueries0(Query ... newQueries) {
        if (newQueries != null) {
            this.batchQueries = (Query[])newQueries.clone();
            this.batchSQL = new String[newQueries.length];
            this.batchRows = new int[newQueries.length];
            Arrays.fill(this.batchRows, -1);
        } else {
            this.batchQueries = null;
            this.batchSQL = null;
            this.batchRows = null;
        }
    }

    @Override
    public final Routine<?> routine() {
        return this.routine;
    }

    @Override
    public final void sql(String s2) {
        this.sql = s2;
        if (this.batchSQL != null && this.batchSQL.length == 1) {
            this.batchSQL[0] = s2;
        }
    }

    @Override
    public final String sql() {
        return this.sql;
    }

    @Override
    public final String[] batchSQL() {
        String[] stringArray;
        if (this.batchMode != ExecuteContext.BatchMode.NONE) {
            stringArray = this.batchSQL;
        } else if (this.routine != null || this.query() != null) {
            String[] stringArray2 = new String[1];
            stringArray = stringArray2;
            stringArray2[0] = this.sql;
        } else {
            stringArray = Tools.EMPTY_STRING;
        }
        return stringArray;
    }

    @Override
    public final void statement(PreparedStatement s2) {
        this.statement = s2;
    }

    @Override
    public final PreparedStatement statement() {
        return this.statement;
    }

    @Override
    public final int statementExecutionCount() {
        return this.statementExecutionCount;
    }

    @Override
    public final void resultSet(ResultSet rs) {
        this.resultSet = rs;
    }

    @Override
    public final ResultSet resultSet() {
        return this.resultSet;
    }

    @Override
    public final Configuration configuration() {
        return this.derivedConfiguration;
    }

    final Configuration originalConfiguration() {
        return this.originalConfiguration;
    }

    @Override
    public final DSLContext dsl() {
        return this.configuration().dsl();
    }

    @Override
    public final Settings settings() {
        return Tools.settings(this.configuration());
    }

    @Override
    public final SQLDialect dialect() {
        return Tools.configuration(this.configuration()).dialect();
    }

    @Override
    public final SQLDialect family() {
        return this.dialect().family();
    }

    @Override
    public final void connectionProvider(ConnectionProvider provider) {
        this.connectionProvider = provider;
    }

    @Override
    public final Connection connection() {
        if (this.wrappedConnection == null && this.connectionProvider != null) {
            this.connection(this.connectionProvider, this.connectionProvider.acquire());
        }
        return this.wrappedConnection;
    }

    final void connection(ConnectionProvider provider, Connection c) {
        if (c != null) {
            if (this.dialect().isVersioned() && logVersionSupport.isWarnEnabled()) {
                String productVersion = null;
                try {
                    int majorVersion = c.getMetaData().getDatabaseMajorVersion();
                    int minorVersion = c.getMetaData().getDatabaseMinorVersion();
                    productVersion = c.getMetaData().getDatabaseProductVersion();
                    if (this.family() == SQLDialect.COCKROACHDB) {
                        productVersion = (String)Reflect.on(c).call("getParameterStatus", "crdb_version").get();
                        Pattern p = Pattern.compile("^.*v(\\d+)\\.(\\d+)(\\s|\\.\\d+)?.*$");
                        Matcher m4 = p.matcher(productVersion);
                        if (m4.find()) {
                            this.logVersionSupport(Integer.parseInt(m4.group(1)), Integer.parseInt(m4.group(2)), productVersion);
                        } else {
                            logVersionSupport.info((Object)"Version", "Cannot extract database version for " + String.valueOf((Object)this.dialect()) + ": " + productVersion + ". Please consider reporting this here: https://jooq.org/bug");
                        }
                    } else {
                        this.logVersionSupport(majorVersion, minorVersion, productVersion);
                    }
                }
                catch (SQLException e) {
                    logVersionSupport.info((Object)"Version", "Database version cannot be read: " + e.getMessage());
                }
                catch (Exception e) {
                    logVersionSupport.info((Object)"Version", "Cannot obtain database version for " + String.valueOf((Object)this.dialect()) + ": " + productVersion + ". (" + String.valueOf(e.getClass()) + ": " + e.getMessage() + "). Please consider reporting this here: https://jooq.org/bug");
                }
            }
            LOCAL_CONNECTION.set(c);
            this.connection = c;
            this.wrappedConnection = this.wrap(provider, c);
        }
    }

    private final void logVersionSupport(int majorVersion, int minorVersion, String productVersion) {
        if (!this.dialect().supportsDatabaseVersion(majorVersion, minorVersion, productVersion)) {
            logVersionSupport.warn((Object)"Version mismatch", "Database version is older than what dialect " + String.valueOf((Object)this.dialect()) + " supports: " + productVersion + ". Consider https://www.jooq.org/download/support-matrix to see what jOOQ version and edition supports which RDBMS versions.");
        } else {
            logVersionSupport.info((Object)"Version", "Database version is supported by dialect " + String.valueOf((Object)this.dialect()) + ": " + productVersion);
        }
    }

    private final Connection wrap(ConnectionProvider provider, Connection c) {
        return this.wrap0(new SettingsEnabledConnection(new ProviderEnabledConnection(provider, c), this.derivedConfiguration.settings(), this));
    }

    private final Connection wrap0(Connection c) {
        if (this.derivedConfiguration.settings().getDiagnosticsConnection() == org.jooq.conf.DiagnosticsConnection.ON) {
            return new DiagnosticsConnection(this.derivedConfiguration, c);
        }
        return c;
    }

    final void incrementStatementExecutionCount() {
        ++this.statementExecutionCount;
    }

    final DefaultExecuteContext withStatementExecutionCount(int count) {
        this.statementExecutionCount = count;
        return this;
    }

    @Override
    public final void record(Record r) {
        this.record = r;
    }

    @Override
    public final Record record() {
        return this.record;
    }

    @Override
    public final int recordLevel() {
        return this.recordLevel;
    }

    @Override
    public final int rows() {
        return this.rows;
    }

    @Override
    public final void rows(int r) {
        this.rows = r;
        if (this.batchRows != null && this.batchRows.length == 1) {
            this.batchRows[0] = r;
        }
    }

    @Override
    public final int[] batchRows() {
        int[] nArray;
        if (this.batchMode != ExecuteContext.BatchMode.NONE) {
            nArray = this.batchRows;
        } else if (this.routine != null || this.query() != null) {
            int[] nArray2 = new int[1];
            nArray = nArray2;
            nArray2[0] = this.rows;
        } else {
            nArray = Tools.EMPTY_INT;
        }
        return nArray;
    }

    @Override
    public final void result(Result<?> r) {
        this.result = r;
    }

    @Override
    public final Result<?> result() {
        return this.result;
    }

    @Override
    public final int resultLevel() {
        return this.resultLevel;
    }

    @Override
    public final RuntimeException exception() {
        return this.exception;
    }

    @Override
    public final void exception(RuntimeException e) {
        StackTraceElement[] oldStack;
        this.exception = Tools.translate(this.sql(), e);
        if (Boolean.TRUE.equals(this.settings().isDebugInfoOnStackTrace()) && (oldStack = this.exception.getStackTrace()) != null) {
            StackTraceElement[] newStack = new StackTraceElement[oldStack.length + 1];
            System.arraycopy(oldStack, 0, newStack, 1, oldStack.length);
            newStack[0] = new StackTraceElement("org.jooq_3.18.7." + String.valueOf((Object)this.dialect()), "debug", null, -1);
            this.exception.setStackTrace(newStack);
        }
    }

    @Override
    public final SQLException sqlException() {
        return this.sqlException;
    }

    @Override
    public final void sqlException(SQLException e) {
        this.sqlException = e;
        this.exception(Tools.translate(this.sql(), e));
        if (this.family() == SQLDialect.DEFAULT && logDefaultDialect.isWarnEnabled()) {
            logDefaultDialect.warn((Object)"Unsupported dialect", "An exception was thrown when executing a query with unsupported dialect: SQLDialect.DEFAULT.\n\nThis is usually due to one of 2 reasons:\n- The dialect was configured by accident (e.g. through a wrong Spring Boot configuration).\n  In this case, the solution is to configure the correct dialect, e.g. SQLDialect.POSTGRES\n- SQLDialect.DEFAULT is used as a \"close enough\" approximation of an unsupported dialect.\n  Please beware that SQLDialect.DEFAULT is used mainly for DEBUG logging SQL strings, e.g.\n  when calling Query.toString(). It does not guarantee stability of generated SQL, i.e.\n  future versions of jOOQ may produce different SQL strings, which may break assumptions\n  about your unsupported dialect.\n  Please visit https://github.com/jOOQ/jOOQ/discussions/14059 for new dialect support.\n");
        }
    }

    @Override
    public final SQLWarning sqlWarning() {
        return this.sqlWarning;
    }

    @Override
    public final void sqlWarning(SQLWarning e) {
        this.sqlWarning = e;
    }

    @Override
    public final String[] serverOutput() {
        return this.serverOutput == null ? Tools.EMPTY_STRING : this.serverOutput;
    }

    @Override
    public final void serverOutput(String[] output) {
        this.serverOutput = output;
    }

    final void transformQueries(ExecuteListener listener) {
        listener.transformStart(this);
        if (Boolean.TRUE.equals(this.settings().isTransformPatterns()) && this.configuration().requireCommercial(() -> "SQL transformations are a commercial only feature. Please consider upgrading to the jOOQ Professional Edition or jOOQ Enterprise Edition.") && this.query != null) {
            this.query((Query)this.query.$replace(new Patterns(this.configuration(), false).transformPatterns()));
        }
        listener.transformEnd(this);
    }

    final class ExecuteContextConnectionProvider
    implements ConnectionProvider {
        ExecuteContextConnectionProvider() {
        }

        @Override
        @NotNull
        public final Connection acquire() {
            if (DefaultExecuteContext.this.connection == null) {
                DefaultExecuteContext.this.connection();
            }
            return DefaultExecuteContext.this.wrap(this, DefaultExecuteContext.this.connection);
        }

        @Override
        public final void release(Connection c) {
        }
    }
}

