/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import liquibase.Scope;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.PreparedStatementFactory;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.executor.AbstractExecutor;
import liquibase.executor.jvm.CallableStatementCallback;
import liquibase.executor.jvm.ColumnMapRowMapper;
import liquibase.executor.jvm.ResultSetExtractor;
import liquibase.executor.jvm.RowMapper;
import liquibase.executor.jvm.RowMapperResultSetExtractor;
import liquibase.executor.jvm.SingleColumnRowMapper;
import liquibase.executor.jvm.StatementCallback;
import liquibase.listener.SqlListener;
import liquibase.logging.Logger;
import liquibase.sql.CallableSql;
import liquibase.sql.Sql;
import liquibase.sql.SqlConfiguration;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.CallableSqlStatement;
import liquibase.statement.CompoundStatement;
import liquibase.statement.ExecutablePreparedStatement;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawParameterizedSqlStatement;
import liquibase.util.JdbcUtil;
import liquibase.util.StringUtil;

public class JdbcExecutor
extends AbstractExecutor {
    public static final String SHOULD_UPDATE_ROWS_AFFECTED_SCOPE_KEY = "shouldUpdateRowsAffected";
    public static final String ROWS_AFFECTED_SCOPE_KEY = "rowsAffected";

    @Override
    public String getName() {
        return "jdbc";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean updatesDatabase() {
        return true;
    }

    public Object execute(StatementCallback action, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        Object object;
        DatabaseConnection con = this.database.getConnection();
        Statement stmt = null;
        try {
            if (con instanceof OfflineConnection) {
                throw new DatabaseException("Cannot execute commands against an offline database");
            }
            stmt = ((JdbcConnection)con).getUnderlyingConnection().createStatement();
            if (Boolean.TRUE.equals(SqlConfiguration.ALWAYS_SET_FETCH_SIZE.getCurrentValue())) {
                stmt.setFetchSize(this.database.getFetchSize());
            }
            Statement stmtToUse = stmt;
            Object object2 = action.doInStatement(stmtToUse);
            if (stmtToUse.getWarnings() != null) {
                this.showSqlWarnings(stmtToUse);
            }
            object = object2;
        }
        catch (SQLException ex) {
            try {
                try {
                    this.showSqlWarnings(stmt);
                }
                catch (SQLException sqle) {
                    Scope.getCurrentScope().getLog(JdbcExecutor.class).warning(String.format("Unable to access SQL warning: %s", sqle.getMessage()));
                }
                JdbcUtil.closeStatement(stmt);
                stmt = null;
                String url2 = con.isClosed() ? "CLOSED CONNECTION" : con.getURL();
                throw new DatabaseException("Error executing SQL " + StringUtil.join(this.applyVisitors(action.getStatement(), sqlVisitors), "; on " + url2) + ": " + ex.getMessage(), ex);
            }
            catch (Throwable throwable) {
                JdbcUtil.closeStatement(stmt);
                throw throwable;
            }
        }
        JdbcUtil.closeStatement(stmt);
        return object;
    }

    private void showSqlWarnings(Statement stmtToUse) throws SQLException {
        if (Boolean.TRUE.equals(SqlConfiguration.SHOW_SQL_WARNING_MESSAGES.getCurrentValue() == false || stmtToUse == null) || stmtToUse.getWarnings() == null) {
            return;
        }
        SQLWarning sqlWarning = stmtToUse.getWarnings();
        do {
            Scope.getCurrentScope().getLog(JdbcExecutor.class).warning(sqlWarning.getMessage());
        } while ((sqlWarning = sqlWarning.getNextWarning()) != null);
    }

    public Object execute(CallableStatementCallback action, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        Object object;
        DatabaseConnection con = this.database.getConnection();
        if (con instanceof OfflineConnection) {
            throw new DatabaseException("Cannot execute commands against an offline database");
        }
        CallableStatement stmt = null;
        try {
            String sql = this.applyVisitors(action.getStatement(), sqlVisitors)[0];
            stmt = ((JdbcConnection)con).getUnderlyingConnection().prepareCall(sql);
            object = action.doInCallableStatement(stmt);
        }
        catch (SQLException ex) {
            try {
                JdbcUtil.closeStatement(stmt);
                stmt = null;
                throw new DatabaseException("Error executing SQL " + StringUtil.join(this.applyVisitors(action.getStatement(), sqlVisitors), "; on " + con.getURL()) + ": " + ex.getMessage(), ex);
            }
            catch (Throwable throwable) {
                JdbcUtil.closeStatement(stmt);
                throw throwable;
            }
        }
        JdbcUtil.closeStatement(stmt);
        return object;
    }

    @Override
    public void execute(SqlStatement sql) throws DatabaseException {
        this.execute(sql, new ArrayList<SqlVisitor>());
    }

    @Override
    public void execute(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        if (sql instanceof RawParameterizedSqlStatement) {
            PreparedStatementFactory factory2 = new PreparedStatementFactory((JdbcConnection)this.database.getConnection());
            String finalSql = this.applyVisitors((RawParameterizedSqlStatement)sql, sqlVisitors);
            try (PreparedStatement pstmt = factory2.create(finalSql);){
                this.setParameters(pstmt, (RawParameterizedSqlStatement)sql);
                pstmt.execute();
                return;
            }
            catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
        if (sql instanceof ExecutablePreparedStatement) {
            ((ExecutablePreparedStatement)sql).execute(new PreparedStatementFactory((JdbcConnection)this.database.getConnection()));
            return;
        }
        if (sql instanceof CompoundStatement && this.database instanceof Db2zDatabase) {
            this.executeDb2ZosComplexStatement(sql, sqlVisitors);
            return;
        }
        this.execute(new ExecuteStatementCallback(sql, sqlVisitors), sqlVisitors);
    }

    private void setParameters(PreparedStatement pstmt, RawParameterizedSqlStatement sql) throws SQLException {
        List<Object> parameters = sql.getParameters();
        for (int i = 0; i < parameters.size(); ++i) {
            pstmt.setObject(i + 1, parameters.get(i));
        }
    }

    private String applyVisitors(RawParameterizedSqlStatement sql, List<SqlVisitor> sqlVisitors) {
        String finalSql = sql.getSql();
        if (sqlVisitors != null) {
            for (SqlVisitor visitor : sqlVisitors) {
                if (visitor == null) continue;
                finalSql = visitor.modifySql(finalSql, this.database);
            }
        }
        return finalSql;
    }

    public Object query(SqlStatement sql, ResultSetExtractor rse) throws DatabaseException {
        return this.query(sql, rse, new ArrayList<SqlVisitor>());
    }

    public Object query(SqlStatement sql, ResultSetExtractor rse, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        if (sql instanceof RawParameterizedSqlStatement) {
            Object object;
            block10: {
                PreparedStatementFactory factory2 = new PreparedStatementFactory((JdbcConnection)this.database.getConnection());
                String finalSql = this.applyVisitors((RawParameterizedSqlStatement)sql, sqlVisitors);
                PreparedStatement pstmt = factory2.create(finalSql);
                try {
                    this.setParameters(pstmt, (RawParameterizedSqlStatement)sql);
                    object = rse.extractData(pstmt.executeQuery());
                    if (pstmt == null) break block10;
                }
                catch (Throwable throwable) {
                    try {
                        if (pstmt != null) {
                            try {
                                pstmt.close();
                            }
                            catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    }
                    catch (SQLException e) {
                        throw new DatabaseException(e);
                    }
                }
                pstmt.close();
            }
            return object;
        }
        if (sql instanceof CallableSqlStatement) {
            return this.execute(new QueryCallableStatementCallback(sql, rse), sqlVisitors);
        }
        return this.execute(new QueryStatementCallback(sql, rse, sqlVisitors), sqlVisitors);
    }

    public List query(SqlStatement sql, RowMapper rowMapper) throws DatabaseException {
        return this.query(sql, rowMapper, new ArrayList<SqlVisitor>());
    }

    public List query(SqlStatement sql, RowMapper rowMapper, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return (List)this.query(sql, new RowMapperResultSetExtractor(rowMapper), sqlVisitors);
    }

    public Object queryForObject(SqlStatement sql, RowMapper rowMapper) throws DatabaseException {
        return this.queryForObject(sql, rowMapper, new ArrayList<SqlVisitor>());
    }

    public Object queryForObject(SqlStatement sql, RowMapper rowMapper, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        List results = this.query(sql, rowMapper, sqlVisitors);
        try {
            return JdbcUtil.requiredSingleResult(results);
        }
        catch (DatabaseException e) {
            throw new DatabaseException("Expected single row from " + sql + " but got " + results.size(), e);
        }
    }

    @Override
    public <T> T queryForObject(SqlStatement sql, Class<T> requiredType) throws DatabaseException {
        return this.queryForObject(sql, requiredType, new ArrayList<SqlVisitor>());
    }

    @Override
    public <T> T queryForObject(SqlStatement sql, Class<T> requiredType, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return (T)this.queryForObject(sql, this.getSingleColumnRowMapper(requiredType), sqlVisitors);
    }

    @Override
    public long queryForLong(SqlStatement sql) throws DatabaseException {
        return this.queryForLong(sql, new ArrayList<SqlVisitor>());
    }

    @Override
    public long queryForLong(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        Number number = this.queryForObject(sql, Long.class, sqlVisitors);
        return number != null ? number.longValue() : 0L;
    }

    @Override
    public int queryForInt(SqlStatement sql) throws DatabaseException {
        return this.queryForInt(sql, new ArrayList<SqlVisitor>());
    }

    @Override
    public int queryForInt(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        Number number = this.queryForObject(sql, Integer.class, sqlVisitors);
        return number != null ? number.intValue() : 0;
    }

    @Override
    public List queryForList(SqlStatement sql, Class elementType) throws DatabaseException {
        return this.queryForList(sql, elementType, new ArrayList<SqlVisitor>());
    }

    @Override
    public List queryForList(SqlStatement sql, Class elementType, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return this.query(sql, this.getSingleColumnRowMapper(elementType), sqlVisitors);
    }

    @Override
    public List<Map<String, ?>> queryForList(SqlStatement sql) throws DatabaseException {
        return this.queryForList(sql, new ArrayList<SqlVisitor>());
    }

    @Override
    public List<Map<String, ?>> queryForList(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return this.query(sql, this.getColumnMapRowMapper(), sqlVisitors);
    }

    @Override
    public int update(SqlStatement sql) throws DatabaseException {
        return this.update(sql, new ArrayList<SqlVisitor>());
    }

    @Override
    public int update(final SqlStatement sql, final List<SqlVisitor> sqlVisitors) throws DatabaseException {
        if (sql instanceof CallableSqlStatement) {
            throw new DatabaseException("Direct update using CallableSqlStatement not currently implemented");
        }
        class UpdateStatementCallback
        implements StatementCallback {
            UpdateStatementCallback() {
            }

            @Override
            public Object doInStatement(Statement stmt) throws SQLException, DatabaseException {
                String[] sqlToExecute = JdbcExecutor.this.applyVisitors(sql, (List<SqlVisitor>)sqlVisitors);
                if (sqlToExecute.length != 1) {
                    throw new DatabaseException("Cannot call update on Statement that returns back multiple Sql objects");
                }
                for (SqlListener listener : Scope.getCurrentScope().getListeners(SqlListener.class)) {
                    listener.writeSqlWillRun(sqlToExecute[0]);
                }
                return stmt.executeUpdate(sqlToExecute[0]);
            }

            @Override
            public SqlStatement getStatement() {
                return sql;
            }
        }
        return (Integer)this.execute(new UpdateStatementCallback(), sqlVisitors);
    }

    protected RowMapper getColumnMapRowMapper() {
        return new ColumnMapRowMapper(this.database.isCaseSensitive());
    }

    protected RowMapper getSingleColumnRowMapper(Class requiredType) {
        return new SingleColumnRowMapper(requiredType);
    }

    @Override
    public void comment(String message) throws DatabaseException {
        Scope.getCurrentScope().getLog(this.getClass()).fine(message);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeDb2ZosComplexStatement(SqlStatement sqlStatement, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        Sql[] sqls;
        DatabaseConnection con = this.database.getConnection();
        if (con instanceof OfflineConnection) {
            throw new DatabaseException("Cannot execute commands against an offline database");
        }
        for (Sql sql : sqls = SqlGeneratorFactory.getInstance().generateSql(sqlStatement, this.database)) {
            String stmtText = sql.toSql();
            if (sqlVisitors != null) {
                for (SqlVisitor visitor : sqlVisitors) {
                    stmtText = visitor.modifySql(stmtText, this.database);
                }
            }
            try {
                Statement stmt;
                block12: {
                    if (sql instanceof CallableSql) {
                        CallableStatement call = null;
                        ResultSet resultSet = null;
                        try {
                            call = ((JdbcConnection)con).getUnderlyingConnection().prepareCall(stmtText);
                            resultSet = call.executeQuery();
                            this.checkCallStatus(resultSet, ((CallableSql)sql).getExpectedStatus());
                        }
                        catch (Throwable throwable) {
                            JdbcUtil.close(resultSet, call);
                            throw throwable;
                        }
                        JdbcUtil.close(resultSet, call);
                        continue;
                    }
                    stmt = null;
                    try {
                        if (sqlStatement instanceof CompoundStatement) {
                            stmt = ((JdbcConnection)con).getUnderlyingConnection().prepareStatement(stmtText);
                            ((PreparedStatement)stmt).execute();
                            break block12;
                        }
                        stmt = ((JdbcConnection)con).getUnderlyingConnection().createStatement();
                        stmt.execute(stmtText);
                    }
                    catch (Throwable throwable) {
                        JdbcUtil.closeStatement(stmt);
                        throw throwable;
                    }
                }
                JdbcUtil.closeStatement(stmt);
            }
            catch (Exception e) {
                throw new DatabaseException(e.getMessage() + " [Failed SQL: " + this.getErrorCode(e) + sql.toSql() + "]", e);
            }
        }
    }

    private void checkCallStatus(ResultSet resultSet, String status) throws SQLException, DatabaseException {
        if (status != null) {
            StringBuilder message = new StringBuilder();
            while (resultSet.next()) {
                String string = resultSet.getString(2);
                if (string.contains(status)) {
                    return;
                }
                message.append(string).append("\n");
            }
            throw new DatabaseException(message.toString());
        }
    }

    String getErrorCode(Throwable e) {
        if (e instanceof SQLException) {
            return "(" + ((SQLException)e).getErrorCode() + ") ";
        }
        return "";
    }

    private void addUpdateCountToScope(int updateCount) {
        if (updateCount > -1) {
            AtomicInteger scopeRowsAffected = (AtomicInteger)((Object)Scope.getCurrentScope().get(ROWS_AFFECTED_SCOPE_KEY, AtomicInteger.class));
            Boolean shouldUpdateRowsAffected = Scope.getCurrentScope().get(SHOULD_UPDATE_ROWS_AFFECTED_SCOPE_KEY, Boolean.valueOf(true));
            if (scopeRowsAffected != null && Boolean.TRUE.equals(shouldUpdateRowsAffected)) {
                scopeRowsAffected.addAndGet(updateCount);
            }
        }
    }

    private class ExecuteStatementCallback
    implements StatementCallback {
        private final SqlStatement sql;
        private final List<SqlVisitor> sqlVisitors;

        private ExecuteStatementCallback(SqlStatement sql, List<SqlVisitor> sqlVisitors) {
            this.sql = sql;
            this.sqlVisitors = sqlVisitors;
        }

        @Override
        public Object doInStatement(Statement stmt) throws SQLException, DatabaseException {
            Logger log = Scope.getCurrentScope().getLog(this.getClass());
            for (String statement : JdbcExecutor.this.applyVisitors(this.sql, (List<SqlVisitor>)this.sqlVisitors)) {
                if (JdbcExecutor.this.database instanceof OracleDatabase) {
                    while (statement.matches("(?s).*[\\s\\r\\n]*[^*]/[\\s\\r\\n]*$")) {
                        statement = statement.replaceFirst("[\\s\\r\\n]*[^*]/[\\s\\r\\n]*$", "");
                    }
                }
                for (SqlListener listener : Scope.getCurrentScope().getListeners(SqlListener.class)) {
                    listener.writeSqlWillRun(String.format("%s", statement));
                }
                Level sqlLogLevel = SqlConfiguration.SHOW_AT_LOG_LEVEL.getCurrentValue();
                log.log(sqlLogLevel, statement, null);
                if (statement.contains("?")) {
                    stmt.setEscapeProcessing(false);
                }
                try {
                    if (!stmt.execute(statement)) {
                        int updateCount = stmt.getUpdateCount();
                        JdbcExecutor.this.addUpdateCountToScope(updateCount);
                        log.log(sqlLogLevel, updateCount + " row(s) affected", null);
                    }
                }
                catch (Throwable e) {
                    throw new DatabaseException(e.getMessage() + " [Failed SQL: " + JdbcExecutor.this.getErrorCode(e) + statement + "]", e);
                }
                try {
                    int updateCount = 0;
                    do {
                        if (stmt.getMoreResults()) continue;
                        updateCount = stmt.getUpdateCount();
                        JdbcExecutor.this.addUpdateCountToScope(updateCount);
                        if (updateCount == -1) continue;
                        log.log(sqlLogLevel, updateCount + " row(s) affected", null);
                    } while (updateCount != -1);
                }
                catch (Exception e) {
                    throw new DatabaseException(e.getMessage() + " [Failed SQL: " + JdbcExecutor.this.getErrorCode(e) + statement + "]", e);
                }
            }
            return null;
        }

        @Override
        public SqlStatement getStatement() {
            return this.sql;
        }
    }

    private class QueryCallableStatementCallback
    implements CallableStatementCallback {
        private final SqlStatement sql;
        private final ResultSetExtractor rse;

        private QueryCallableStatementCallback(SqlStatement sql, ResultSetExtractor rse) {
            this.sql = sql;
            this.rse = rse;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object doInCallableStatement(CallableStatement cs) throws SQLException, DatabaseException {
            ResultSet rs = null;
            try {
                rs = cs.executeQuery();
                Object object = this.rse.extractData(rs);
                return object;
            }
            finally {
                JdbcUtil.closeResultSet(rs);
            }
        }

        @Override
        public SqlStatement getStatement() {
            return this.sql;
        }
    }

    private class QueryStatementCallback
    implements StatementCallback {
        private final SqlStatement sql;
        private final List<SqlVisitor> sqlVisitors;
        private final ResultSetExtractor rse;

        private QueryStatementCallback(SqlStatement sql, ResultSetExtractor rse, List<SqlVisitor> sqlVisitors) {
            this.sql = sql;
            this.rse = rse;
            this.sqlVisitors = sqlVisitors;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Loose catch block
         */
        @Override
        public Object doInStatement(Statement stmt) throws SQLException, DatabaseException {
            Object object;
            String[] sqlToExecute;
            ResultSet rs;
            block9: {
                rs = null;
                sqlToExecute = JdbcExecutor.this.applyVisitors(this.sql, (List<SqlVisitor>)this.sqlVisitors);
                if (sqlToExecute.length != 1) {
                    throw new DatabaseException("Can only query with statements that return one sql statement");
                }
                ResultSet rsToUse = rs = stmt.executeQuery(sqlToExecute[0]);
                object = this.rse.extractData(rsToUse);
                for (SqlListener listener : Scope.getCurrentScope().getListeners(SqlListener.class)) {
                    listener.readSqlWillRun(sqlToExecute[0]);
                }
                if (rs == null) break block9;
                JdbcUtil.closeResultSet(rs);
            }
            return object;
            {
                catch (Throwable throwable) {
                    try {
                        for (SqlListener listener : Scope.getCurrentScope().getListeners(SqlListener.class)) {
                            listener.readSqlWillRun(sqlToExecute[0]);
                        }
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        if (rs != null) {
                            JdbcUtil.closeResultSet(rs);
                        }
                        throw throwable2;
                    }
                }
            }
        }

        @Override
        public SqlStatement getStatement() {
            return this.sql;
        }
    }
}

