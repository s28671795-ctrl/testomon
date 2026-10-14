/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.exception.DatabaseException;
import liquibase.executor.AbstractExecutor;
import liquibase.executor.Executor;
import liquibase.servicelocator.LiquibaseService;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.ExecutablePreparedStatement;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateProcedureStatement;
import liquibase.statement.core.GetNextChangeSetSequenceValueStatement;
import liquibase.statement.core.LockDatabaseChangeLogStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.statement.core.SelectFromDatabaseChangeLogLockStatement;
import liquibase.statement.core.UnlockDatabaseChangeLogStatement;
import liquibase.util.StreamUtil;

@LiquibaseService(skip=true)
public class LoggingExecutor
extends AbstractExecutor {
    private final Writer output;
    private final Executor delegatedReadExecutor;

    public LoggingExecutor(Executor delegatedExecutor, Writer output, Database database) {
        this.output = output != null ? output : new NoopWriter();
        this.delegatedReadExecutor = delegatedExecutor;
        this.setDatabase(database);
    }

    @Override
    public String getName() {
        return "logging";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    protected Writer getOutput() {
        return this.output;
    }

    @Override
    public void execute(SqlStatement sql) throws DatabaseException {
        this.outputStatement(sql);
    }

    @Override
    public int update(SqlStatement sql) throws DatabaseException {
        this.outputStatement(sql);
        if (sql instanceof LockDatabaseChangeLogStatement || sql instanceof UnlockDatabaseChangeLogStatement) {
            return 1;
        }
        return 0;
    }

    @Override
    public void execute(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        this.outputStatement(sql, sqlVisitors);
    }

    @Override
    public int update(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        this.outputStatement(sql, sqlVisitors);
        return 0;
    }

    @Override
    public void comment(String message) throws DatabaseException {
        try {
            this.output.write(this.database.getLineComment());
            this.output.write(" ");
            this.output.write(message);
            this.output.write(StreamUtil.getLineSeparator());
        }
        catch (IOException e) {
            throw new DatabaseException(e);
        }
    }

    private void outputStatement(SqlStatement sql) throws DatabaseException {
        this.outputStatement(sql, new ArrayList<SqlVisitor>());
    }

    private void outputStatement(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        try {
            if (SqlGeneratorFactory.getInstance().generateStatementsVolatile(sql, this.database)) {
                throw new DatabaseException(sql.getClass().getSimpleName() + " requires access to up to date database metadata which is not available in SQL output mode");
            }
            if (sql instanceof ExecutablePreparedStatement) {
                this.output.write("WARNING: This statement uses a prepared statement which cannot be execute directly by this script. Only works in 'update' mode\n\n");
            }
            for (String statement : this.applyVisitors(sql, sqlVisitors)) {
                if (statement == null) continue;
                if (this.database instanceof OracleDatabase) {
                    while (statement.matches("(?s).*[\\s\\r\\n]*/[\\s\\r\\n]*$")) {
                        statement = statement.replaceFirst("[\\s\\r\\n]*/[\\s\\r\\n]*$", "");
                    }
                }
                this.output.write(statement);
                if (this.database instanceof MSSQLDatabase || this.database instanceof SybaseDatabase || this.database instanceof SybaseASADatabase) {
                    this.output.write(StreamUtil.getLineSeparator());
                    this.output.write("GO");
                } else {
                    String endDelimiter = ";";
                    String potentialDelimiter = null;
                    if (sql instanceof RawSqlStatement) {
                        potentialDelimiter = ((RawSqlStatement)sql).getEndDelimiter();
                    } else if (sql instanceof CreateProcedureStatement) {
                        potentialDelimiter = ((CreateProcedureStatement)sql).getEndDelimiter();
                    }
                    if (potentialDelimiter != null && (potentialDelimiter = potentialDelimiter.replaceFirst("\\$$", "")).replaceAll("\\n", "\n").replace("\\r", "\r").matches("[;/\r\n\\w@\\-]+")) {
                        endDelimiter = potentialDelimiter;
                    }
                    endDelimiter = endDelimiter.replace("\\n", "\n");
                    if (!statement.endsWith(endDelimiter = endDelimiter.replace("\\r", "\r"))) {
                        this.output.write(endDelimiter);
                    }
                }
                this.output.write(StreamUtil.getLineSeparator());
                this.output.write(StreamUtil.getLineSeparator());
            }
        }
        catch (IOException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public <T> T queryForObject(SqlStatement sql, Class<T> requiredType) throws DatabaseException {
        if (sql instanceof SelectFromDatabaseChangeLogLockStatement) {
            return (T)Boolean.FALSE;
        }
        return this.delegatedReadExecutor.queryForObject(sql, requiredType);
    }

    @Override
    public <T> T queryForObject(SqlStatement sql, Class<T> requiredType, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return this.delegatedReadExecutor.queryForObject(sql, requiredType, sqlVisitors);
    }

    @Override
    public long queryForLong(SqlStatement sql) throws DatabaseException {
        return this.delegatedReadExecutor.queryForLong(sql);
    }

    @Override
    public long queryForLong(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return this.delegatedReadExecutor.queryForLong(sql, sqlVisitors);
    }

    @Override
    public int queryForInt(SqlStatement sql) throws DatabaseException {
        try {
            return this.delegatedReadExecutor.queryForInt(sql);
        }
        catch (DatabaseException e) {
            if (sql instanceof GetNextChangeSetSequenceValueStatement) {
                return 0;
            }
            throw e;
        }
    }

    @Override
    public int queryForInt(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return this.delegatedReadExecutor.queryForInt(sql, sqlVisitors);
    }

    @Override
    public List queryForList(SqlStatement sql, Class elementType) throws DatabaseException {
        return this.delegatedReadExecutor.queryForList(sql, elementType);
    }

    @Override
    public List queryForList(SqlStatement sql, Class elementType, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return this.delegatedReadExecutor.queryForList(sql, elementType, sqlVisitors);
    }

    @Override
    public List<Map<String, ?>> queryForList(SqlStatement sql) throws DatabaseException {
        return this.delegatedReadExecutor.queryForList(sql);
    }

    @Override
    public List<Map<String, ?>> queryForList(SqlStatement sql, List<SqlVisitor> sqlVisitors) throws DatabaseException {
        return this.delegatedReadExecutor.queryForList(sql, sqlVisitors);
    }

    @Override
    public boolean updatesDatabase() {
        return false;
    }

    private class NoopWriter
    extends Writer {
        private NoopWriter() {
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void close() throws IOException {
        }
    }
}

