/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import liquibase.ChecksumVersion;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.ChangeStatus;
import liquibase.change.CheckSum;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.DbmsTargetedChange;
import liquibase.change.NormalizingStreamV8;
import liquibase.change.core.RawSQLChange;
import liquibase.changeset.ChangeSetService;
import liquibase.changeset.ChangeSetServiceFactory;
import liquibase.database.Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawCompoundStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.util.BooleanUtil;
import liquibase.util.StringUtil;

public abstract class AbstractSQLChange
extends AbstractChange
implements DbmsTargetedChange {
    private boolean stripComments;
    private boolean splitStatements;
    @Deprecated
    private Boolean originalSplitStatements;
    @Deprecated
    private Boolean ignoreOriginalSplitStatements;
    @Deprecated
    private boolean splitStatementsSet;
    private String endDelimiter;
    private String sql;
    private String dbms;
    protected String encoding;

    protected AbstractSQLChange() {
        this.setStripComments(null);
        this.setSplitStatements(null);
    }

    @Deprecated
    public void setOriginalSplitStatements(Boolean originalSplitStatements) {
        this.originalSplitStatements = originalSplitStatements;
    }

    @Deprecated
    public void setIgnoreOriginalSplitStatements(Boolean ignoreOriginalSplitStatements) {
        this.ignoreOriginalSplitStatements = ignoreOriginalSplitStatements;
    }

    @Deprecated
    public Boolean isIgnoreOriginalSplitStatements() {
        return this.ignoreOriginalSplitStatements;
    }

    public InputStream openSqlStream() throws IOException {
        return null;
    }

    @Override
    @DatabaseChangeProperty(since="3.0", exampleValue="h2, oracle", description="Specifies which database type(s) a changeset is to be used for. See valid database type names on Supported Databases docs page. Separate multiple databases with commas. Specify that a changeset is not applicable to a particular database type by prefixing with !. The keywords 'all' and 'none' are also available.")
    public String getDbms() {
        return this.dbms;
    }

    @Override
    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    @Override
    public boolean supports(Database database) {
        return true;
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors();
        if (StringUtil.trimToNull(this.sql) == null) {
            validationErrors.addError("'sql' is required");
        }
        return validationErrors;
    }

    @DatabaseChangeProperty(description="Set to true to remove any comments in the SQL before executing, otherwise false. Defaults to false if not set")
    public Boolean isStripComments() {
        return this.stripComments;
    }

    public void setStripComments(Boolean stripComments) {
        this.stripComments = stripComments == null ? false : stripComments;
    }

    @DatabaseChangeProperty(description="Set to false to not have liquibase split statements on ;'s and GO's. Defaults to true if not set")
    public Boolean isSplitStatements() {
        return this.splitStatements;
    }

    public void setSplitStatements(Boolean splitStatements) {
        if (splitStatements == null) {
            this.splitStatements = true;
        } else {
            this.splitStatements = splitStatements;
            this.splitStatementsSet = true;
        }
    }

    @Deprecated
    public boolean isSplitStatementsSet() {
        return this.splitStatementsSet;
    }

    @DatabaseChangeProperty(serializationType=LiquibaseSerializable.SerializationType.DIRECT_VALUE)
    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = StringUtil.trimToNull(sql);
    }

    @DatabaseChangeProperty(description="Delimiter to apply to the end of the statement. Defaults to ';', may be set to ''.", exampleValue="\\nGO")
    public String getEndDelimiter() {
        ChangeSetService service2 = ChangeSetServiceFactory.getInstance().createChangeSetService();
        if (this.endDelimiter != null) {
            return service2.getOverrideDelimiter(this.endDelimiter);
        }
        return service2.getEndDelimiter(this.getChangeSet());
    }

    public void setEndDelimiter(String endDelimiter) {
        this.endDelimiter = endDelimiter;
    }

    @Override
    public CheckSum generateCheckSum() {
        InputStream stream = null;
        try {
            ChecksumVersion version;
            stream = this.openSqlStream();
            String sql = this.sql;
            if (stream == null && sql == null) {
                sql = "";
            }
            if (sql != null) {
                version = Scope.getCurrentScope().getChecksumVersion();
                stream = version.lowerOrEqualThan(ChecksumVersion.V8) ? new ByteArrayInputStream(sql.getBytes(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue())) : new ByteArrayInputStream(sql.getBytes(GlobalConfiguration.FILE_ENCODING.getCurrentValue()));
            }
            if ((version = Scope.getCurrentScope().getChecksumVersion()).lowerOrEqualThan(ChecksumVersion.V8)) {
                boolean isSplitStatements = this.isSplitStatements();
                if (this.getChangeSet() != null && this.getChangeSet().getRunWith() != null && !BooleanUtil.isTrue(this.isIgnoreOriginalSplitStatements()) && !isSplitStatements) {
                    isSplitStatements = BooleanUtil.isTrue(this.originalSplitStatements);
                }
                CheckSum checkSum = CheckSum.compute(new NormalizingStreamV8(this.getEndDelimiter(), isSplitStatements, this.isStripComments(), stream), false);
                return checkSum;
            }
            CheckSum checkSum = CheckSum.compute(new NormalizingStream(stream), false);
            return checkSum;
        }
        catch (IOException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).fine("Error closing stream", e);
                }
            }
        }
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        ArrayList<RawSqlStatement> returnStatements = new ArrayList<RawSqlStatement>();
        String sql = StringUtil.trimToNull(this.getSql());
        if (sql == null) {
            return SqlStatement.EMPTY_SQL_STATEMENT;
        }
        String processedSQL = this.normalizeLineEndings(sql);
        if (this instanceof RawSQLChange && ((RawSQLChange)this).isRerunnable()) {
            returnStatements.add(new RawSqlStatement(processedSQL, this.getEndDelimiter()));
            return returnStatements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
        }
        for (String statement : StringUtil.processMultiLineSQL(processedSQL, this.isStripComments(), this.isSplitStatements(), this.getEndDelimiter(), this.getChangeSet())) {
            if (database instanceof MSSQLDatabase) {
                statement = statement.replaceAll("\\n", "\r\n");
            }
            String escapedStatement = statement;
            try {
                if (database.getConnection() != null) {
                    escapedStatement = database.getConnection().nativeSQL(statement);
                }
            }
            catch (DatabaseException e) {
                escapedStatement = statement;
            }
            if (database instanceof Db2zDatabase && escapedStatement.toUpperCase().startsWith("CALL")) {
                returnStatements.add(new RawCompoundStatement(escapedStatement, this.getEndDelimiter()));
                continue;
            }
            returnStatements.add(new RawSqlStatement(escapedStatement, this.getEndDelimiter()));
        }
        return returnStatements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return false;
    }

    @Override
    public boolean generateRollbackStatementsVolatile(Database database) {
        return false;
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        return new ChangeStatus().unknown("Cannot check raw sql status");
    }

    protected String normalizeLineEndings(String string) {
        return string.replace("\r", "");
    }

    public static class NormalizingStream
    extends InputStream {
        private final InputStream stream;

        @Deprecated
        public NormalizingStream(String endDelimiter, Boolean splitStatements, Boolean stripComments, InputStream stream) {
            this(stream);
        }

        public NormalizingStream(InputStream stream) {
            if (stream == null) {
                stream = new ByteArrayInputStream(new byte[0]);
            }
            this.stream = new PushbackInputStream(stream, 2048);
        }

        @Override
        public int read() throws IOException {
            int returnChar = this.stream.read();
            while (this.isWhiteSpace(returnChar)) {
                returnChar = this.stream.read();
            }
            return returnChar;
        }

        @Override
        public int available() throws IOException {
            return this.stream.available();
        }

        @Override
        public boolean markSupported() {
            return this.stream.markSupported();
        }

        @Override
        public synchronized void mark(int readLimit) {
            this.stream.mark(readLimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            this.stream.reset();
        }

        private boolean isWhiteSpace(int read) {
            return read == 32 || read == 10 || read == 13 || read == 9;
        }

        @Override
        public void close() throws IOException {
            this.stream.close();
        }
    }
}

