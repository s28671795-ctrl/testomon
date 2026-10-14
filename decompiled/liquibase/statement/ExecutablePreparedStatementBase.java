/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import liquibase.Scope;
import liquibase.change.ColumnConfig;
import liquibase.change.core.LoadDataChange;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.database.PreparedStatementFactory;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.LiquibaseDataType;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.listener.SqlListener;
import liquibase.logging.Logger;
import liquibase.resource.ResourceAccessor;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.sql.SqlConfiguration;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.ExecutablePreparedStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Table;
import liquibase.util.FilenameUtil;
import liquibase.util.JdbcUtil;
import liquibase.util.StreamUtil;

public abstract class ExecutablePreparedStatementBase
implements ExecutablePreparedStatement {
    protected static ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    protected Database database;
    private final String catalogName;
    private final String schemaName;
    private final String tableName;
    private final List<? extends ColumnConfig> columns;
    private final ChangeSet changeSet;
    private final Set<Closeable> closeables;
    private final ResourceAccessor resourceAccessor;
    private static final ThreadLocal<PreparedStatement> LAST_PREPARED_STATEMENT = new ThreadLocal();
    private static final ThreadLocal<String> LAST_PREPARED_STATEMENT_SQL = new ThreadLocal();
    private static final ThreadLocal<Method> EXECUTE_WITH_FLAGS_METHOD = new ThreadLocal();
    private final Map<String, Object> snapshotScratchPad = new HashMap<String, Object>();

    protected ExecutablePreparedStatementBase(Database database, String catalogName, String schemaName, String tableName, List<? extends ColumnConfig> columns, ChangeSet changeSet, ResourceAccessor resourceAccessor) {
        this.database = database;
        this.catalogName = catalogName;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columns = columns;
        this.changeSet = changeSet;
        this.closeables = new HashSet<Closeable>();
        this.resourceAccessor = resourceAccessor;
    }

    protected static InputStream createStream(InputStream in) {
        return in instanceof BufferedInputStream ? in : new BufferedInputStream(in);
    }

    @Override
    public void execute(PreparedStatementFactory factory2) throws DatabaseException {
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        ArrayList<ColumnConfig> cols = new ArrayList<ColumnConfig>(this.getColumns().size());
        List sqlVisitors = Optional.ofNullable(this.changeSet).map(ChangeSet::getSqlVisitors).orElseGet(ArrayList::new);
        String sql = this.applyVisitors(this.generateSql(cols), sqlVisitors);
        for (SqlListener listener : Scope.getCurrentScope().getListeners(SqlListener.class)) {
            listener.writeSqlWillRun(sql);
        }
        Level sqlLogLevel = SqlConfiguration.SHOW_AT_LOG_LEVEL.getCurrentValue();
        log.log(sqlLogLevel, sql, null);
        log.fine("Number of columns = " + cols.size());
        PreparedStatement stmt = this.getCachedStatement(sql);
        if (stmt == null) {
            stmt = factory2.create(sql);
            LAST_PREPARED_STATEMENT.set(stmt);
            LAST_PREPARED_STATEMENT_SQL.set(sql);
        } else {
            try {
                stmt.clearParameters();
            }
            catch (SQLException e) {
                log.fine("Error clearing parameters on prepared statement: " + e.getMessage(), e);
            }
        }
        try {
            this.attachParams(cols, stmt);
            this.executePreparedStatement(stmt);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        finally {
            for (Closeable closeable : this.closeables) {
                try {
                    closeable.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    protected String applyVisitors(String sql, List<SqlVisitor> sqlVisitors) {
        String finalSql = sql;
        if (sqlVisitors != null) {
            for (SqlVisitor visitor : sqlVisitors) {
                if (visitor == null) continue;
                finalSql = visitor.modifySql(finalSql, this.database);
            }
        }
        return finalSql;
    }

    protected PreparedStatement getCachedStatement(String sql) {
        if (LAST_PREPARED_STATEMENT.get() == null || LAST_PREPARED_STATEMENT_SQL.get() == null) {
            return null;
        }
        boolean statementIsValid = true;
        if (LAST_PREPARED_STATEMENT_SQL.get().equals(sql)) {
            try {
                Connection connection;
                if (LAST_PREPARED_STATEMENT.get().isClosed()) {
                    statementIsValid = false;
                }
                if (statementIsValid && ((connection = LAST_PREPARED_STATEMENT.get().getConnection()) == null || connection.isClosed())) {
                    statementIsValid = false;
                }
            }
            catch (SQLException e) {
                statementIsValid = false;
            }
        } else {
            statementIsValid = false;
        }
        if (!statementIsValid) {
            JdbcUtil.closeStatement(LAST_PREPARED_STATEMENT.get());
            LAST_PREPARED_STATEMENT.remove();
            LAST_PREPARED_STATEMENT_SQL.remove();
            EXECUTE_WITH_FLAGS_METHOD.remove();
        }
        return LAST_PREPARED_STATEMENT.get();
    }

    protected void executePreparedStatement(PreparedStatement stmt) throws SQLException {
        if (this.database instanceof PostgresDatabase) {
            try {
                if (EXECUTE_WITH_FLAGS_METHOD.get() == null) {
                    Method executeWithFlags = stmt.getClass().getMethod("executeWithFlags", Integer.TYPE);
                    executeWithFlags.setAccessible(true);
                    EXECUTE_WITH_FLAGS_METHOD.set(executeWithFlags);
                }
                EXECUTE_WITH_FLAGS_METHOD.get().invoke((Object)stmt, 1);
            }
            catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                stmt.execute();
            }
        } else {
            stmt.execute();
        }
    }

    protected void attachParams(List<? extends ColumnConfig> cols, PreparedStatement stmt) throws SQLException, DatabaseException {
        Logger LOG = Scope.getCurrentScope().getLog(this.getClass());
        int i = 1;
        for (ColumnConfig columnConfig : cols) {
            LOG.fine("Applying column parameter = " + i + " for column " + columnConfig.getName());
            if (columnConfig.getValueObject() instanceof DatabaseFunction) {
                LOG.fine("Skipping column parameter for " + columnConfig.getName() + ", cannot add functions to prepared statements.");
                continue;
            }
            LOG.fine("Applying column parameter = " + i + " for column " + columnConfig.getName());
            this.applyColumnParameter(stmt, i, columnConfig);
            ++i;
        }
    }

    protected abstract String generateSql(List<ColumnConfig> var1);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected void applyColumnParameter(PreparedStatement stmt, int i, ColumnConfig col) throws SQLException, DatabaseException {
        Logger LOG = Scope.getCurrentScope().getLog(this.getClass());
        if (col.getValue() != null) {
            LOG.fine("value is string/UUID/blob = " + col.getValue());
            if (col.getType() != null && col.getType().equalsIgnoreCase(LoadDataChange.LOAD_DATA_TYPE.UUID.name())) {
                stmt.setObject(i, UUID.fromString(col.getValue()));
                return;
            }
            if (col.getType() != null && col.getType().equalsIgnoreCase(LoadDataChange.LOAD_DATA_TYPE.OTHER.name())) {
                stmt.setObject(i, (Object)col.getValue(), 1111);
                return;
            }
            if (LoadDataChange.LOAD_DATA_TYPE.BLOB.name().equalsIgnoreCase(col.getType())) {
                stmt.setBlob(i, new ByteArrayInputStream(Base64.getDecoder().decode(col.getValue())));
                return;
            }
            if (LoadDataChange.LOAD_DATA_TYPE.CLOB.name().equalsIgnoreCase(col.getType())) {
                try {
                    if (this.database instanceof PostgresDatabase || this.database instanceof SQLiteDatabase) {
                        stmt.setString(i, col.getValue());
                        return;
                    }
                    Clob clobValue = stmt.getConnection().createClob();
                    clobValue.setString(1L, col.getValue());
                    stmt.setClob(i, clobValue);
                    return;
                }
                catch (SQLFeatureNotSupportedException e) {
                    stmt.setString(i, col.getValue());
                }
                return;
            }
            stmt.setString(i, col.getValue());
            return;
        }
        if (col.getValueBoolean() != null) {
            LOG.fine("value is boolean = " + col.getValueBoolean());
            stmt.setBoolean(i, col.getValueBoolean());
            return;
        } else if (col.getValueNumeric() != null) {
            LOG.fine("value is numeric = " + col.getValueNumeric());
            Number number = col.getValueNumeric();
            if (number instanceof ColumnConfig.ValueNumeric) {
                ColumnConfig.ValueNumeric valueNumeric = (ColumnConfig.ValueNumeric)number;
                number = valueNumeric.getDelegate();
            }
            if (number instanceof Long) {
                stmt.setLong(i, number.longValue());
                return;
            } else if (number instanceof Integer) {
                stmt.setInt(i, number.intValue());
                return;
            } else if (number instanceof Double) {
                stmt.setDouble(i, number.doubleValue());
                return;
            } else if (number instanceof Float) {
                stmt.setFloat(i, number.floatValue());
                return;
            } else if (number instanceof BigDecimal) {
                stmt.setBigDecimal(i, (BigDecimal)number);
                return;
            } else {
                if (!(number instanceof BigInteger)) throw new UnexpectedLiquibaseException(String.format(coreBundle.getString("jdbc.bind.parameter.unknown.numeric.value.type"), col.getName(), col.getValueNumeric().toString(), col.getValueNumeric().getClass().getName()));
                stmt.setInt(i, number.intValue());
            }
            return;
        } else if (col.getValueDate() != null) {
            LOG.fine("value is date = " + col.getValueDate());
            if (col.getValueDate() instanceof Timestamp) {
                stmt.setTimestamp(i, (Timestamp)col.getValueDate());
                return;
            } else if (col.getValueDate() instanceof Time) {
                stmt.setTime(i, (Time)col.getValueDate());
                return;
            } else {
                stmt.setDate(i, new Date(col.getValueDate().getTime()));
            }
            return;
        } else if (col.getValueBlobFile() != null) {
            LOG.fine("value is blob = " + col.getValueBlobFile());
            try {
                LOBContent<InputStream> lob = this.toBinaryStream(col.getValueBlobFile());
                if (this.database instanceof PostgresDatabase) {
                    String snapshotKeyName = String.format("%s-%s-%s-%s", this.getCatalogName(), this.getSchemaName(), this.getTableName(), col.getName());
                    Column snapshot = (Column)this.getScratchData(snapshotKeyName);
                    if (snapshot == null) {
                        snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(new Column(Table.class, this.getCatalogName(), this.getSchemaName(), this.getTableName(), col.getName()), this.database);
                        this.setScratchData(snapshotKeyName, snapshot);
                    }
                    if (snapshot.getType().getTypeName().equalsIgnoreCase("bytea")) {
                        if (((LOBContent)lob).length <= Integer.MAX_VALUE) {
                            stmt.setBinaryStream(i, (InputStream)((LOBContent)lob).content, (int)((LOBContent)lob).length);
                            return;
                        } else {
                            stmt.setBinaryStream(i, (InputStream)((LOBContent)lob).content, ((LOBContent)lob).length);
                        }
                        return;
                    }
                }
                if (((LOBContent)lob).length <= Integer.MAX_VALUE) {
                    stmt.setBlob(i, (InputStream)((LOBContent)lob).content, (int)((LOBContent)lob).length);
                    return;
                }
                stmt.setBlob(i, (InputStream)((LOBContent)lob).content, ((LOBContent)lob).length);
                return;
            }
            catch (IOException | LiquibaseException e) {
                throw new DatabaseException(e.getMessage(), e);
            }
        } else if (col.getValueClobFile() != null) {
            try {
                LOG.fine("value is clob = " + col.getValueClobFile());
                LOBContent<Reader> lob = this.toCharacterStream(col.getValueClobFile(), col.getEncoding());
                if (((LOBContent)lob).length <= Integer.MAX_VALUE) {
                    stmt.setCharacterStream(i, (Reader)((LOBContent)lob).content, (int)((LOBContent)lob).length);
                    return;
                }
                stmt.setCharacterStream(i, (Reader)((LOBContent)lob).content, ((LOBContent)lob).length);
                return;
            }
            catch (IOException | LiquibaseException e) {
                throw new DatabaseException(e.getMessage(), e);
            }
        } else {
            LOG.fine("value is explicit null");
            if (col.getType() == null) {
                stmt.setNull(i, 0);
                return;
            }
            if (col.getType().toLowerCase().contains("datetime")) {
                stmt.setNull(i, 93);
                return;
            } else {
                String[] aliases;
                boolean isSet = false;
                LiquibaseDataType dataType = DataTypeFactory.getInstance().fromDescription(col.getType(), this.database);
                for (String alias : aliases = dataType.getAliases()) {
                    if (!alias.contains("java.sql.Types")) continue;
                    String name = alias.replaceAll("java.sql.Types.", "");
                    try {
                        JDBCType jdbcType = Enum.valueOf(JDBCType.class, name);
                        stmt.setNull(i, jdbcType.getVendorTypeNumber());
                        return;
                    }
                    catch (Exception exception) {}
                    break;
                }
                if (isSet) return;
                LOG.info(String.format("Using java.sql.Types.NULL to set null value for type %s", dataType.getName()));
                stmt.setNull(i, 0);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected LOBContent<InputStream> toBinaryStream(String valueLobFile) throws LiquibaseException, IOException {
        InputStream in = this.getResourceAsStream(valueLobFile);
        if (in == null) {
            throw new DatabaseException("BLOB resource not found: " + valueLobFile);
        }
        try {
            if (in instanceof FileInputStream) {
                InputStream bufferedInput = ExecutablePreparedStatementBase.createStream(in);
                LOBContent<InputStream> lOBContent = new LOBContent<InputStream>(bufferedInput, ((FileInputStream)in).getChannel().size());
                return lOBContent;
            }
            in = ExecutablePreparedStatementBase.createStream(in);
            int IN_MEMORY_THRESHOLD = 100000;
            if (in.markSupported()) {
                in.mark(100000);
            }
            long length = this.getContentLength(in);
            if (in.markSupported() && length <= 100000L) {
                in.reset();
            } else {
                try {
                    in.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                in = this.getResourceAsStream(valueLobFile);
                in = ExecutablePreparedStatementBase.createStream(in);
            }
            LOBContent<InputStream> lOBContent = new LOBContent<InputStream>(in, length);
            return lOBContent;
        }
        finally {
            if (in != null) {
                this.closeables.add(in);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected LOBContent<Reader> toCharacterStream(String valueLobFile, String encoding) throws IOException, LiquibaseException {
        InputStream in = this.getResourceAsStream(valueLobFile);
        if (in == null) {
            throw new DatabaseException("CLOB resource not found: " + valueLobFile);
        }
        int IN_MEMORY_THRESHOLD = 100000;
        Reader reader = null;
        try {
            reader = StreamUtil.readStreamWithReader(in, encoding);
            if (reader.markSupported()) {
                reader.mark(100000);
            }
            long length = this.getContentLength(reader);
            if (reader.markSupported() && length <= 100000L) {
                reader.reset();
            } else {
                try {
                    reader.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                in = this.getResourceAsStream(valueLobFile);
                reader = StreamUtil.readStreamWithReader(in, encoding);
            }
            LOBContent<Reader> lOBContent = new LOBContent<Reader>(reader, length);
            return lOBContent;
        }
        finally {
            if (reader != null) {
                this.closeables.add(reader);
            }
            if (in != null) {
                this.closeables.add(in);
            }
        }
    }

    protected InputStream getResourceAsStream(String valueLobFile) throws IOException {
        String fileName = this.getFileName(valueLobFile);
        return this.resourceAccessor.getExisting(fileName).openInputStream();
    }

    private String getFileName(String fileName) {
        String relativeBaseFileName = this.changeSet.getChangeLog().getPhysicalFilePath();
        return FilenameUtil.concat(FilenameUtil.getDirectory(relativeBaseFileName), fileName);
    }

    @Override
    public boolean skipOnUnsupported() {
        return false;
    }

    public String getCatalogName() {
        return this.catalogName;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<? extends ColumnConfig> getColumns() {
        return this.columns;
    }

    public ChangeSet getChangeSet() {
        return this.changeSet;
    }

    public ResourceAccessor getResourceAccessor() {
        return this.resourceAccessor;
    }

    private Object getScratchData(String key) {
        return this.snapshotScratchPad.get(key);
    }

    private Object setScratchData(String key, Object data) {
        return this.snapshotScratchPad.put(key, data);
    }

    protected long getContentLength(InputStream in) throws IOException {
        long length = 0L;
        byte[] buf = new byte[4096];
        int bytesRead = in.read(buf);
        while (bytesRead > 0) {
            length += (long)bytesRead;
            bytesRead = in.read(buf);
        }
        return length;
    }

    protected long getContentLength(Reader reader) throws IOException {
        long length = 0L;
        char[] buf = new char[2048];
        int charsRead = reader.read(buf);
        while (charsRead > 0) {
            length += (long)charsRead;
            charsRead = reader.read(buf);
        }
        return length;
    }

    protected static class LOBContent<T> {
        private final T content;
        private final long length;

        public LOBContent(T content, long length) {
            this.content = content;
            this.length = length;
        }

        public T getContent() {
            return this.content;
        }

        public long getLength() {
            return this.length;
        }
    }
}

