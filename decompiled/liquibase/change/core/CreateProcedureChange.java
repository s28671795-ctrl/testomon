/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import liquibase.ChecksumVersion;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.AbstractSQLChange;
import liquibase.change.ChangeFactory;
import liquibase.change.ChangeStatus;
import liquibase.change.CheckSum;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperties;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.DbmsTargetedChange;
import liquibase.change.NormalizingStreamV8;
import liquibase.change.ReplaceIfExists;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.PropertyExpandingStream;
import liquibase.database.Database;
import liquibase.database.DatabaseList;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateProcedureStatement;
import liquibase.util.FileUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

@DatabaseChange(name="createProcedure", description="Defines a stored procedure.", priority=1)
public class CreateProcedureChange
extends AbstractChange
implements DbmsTargetedChange,
ReplaceIfExists {
    private String comments;
    private String catalogName;
    private String schemaName;
    private String procedureName;
    private String procedureText;
    private String dbms;
    private String path;
    private Boolean relativeToChangelogFile;
    private String encoding;
    private Boolean replaceIfExists;
    private final String procedureTextDescription = "The SQL creating the procedure. You need to define either this attribute or 'path'. procedureText is not supported in the XML format; however, you can specify the procedure SQL inline within the createProcedure definition.";

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return false;
    }

    @Override
    public boolean generateRollbackStatementsVolatile(Database database) {
        return false;
    }

    @DatabaseChangeProperty(description="Name of the database catalog")
    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @DatabaseChangeProperty(description="Name of the database schema")
    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @DatabaseChangeProperty(exampleValue="new_customer", description="Name of the stored procedure to create. Required if replaceIfExists=true")
    public String getProcedureName() {
        return this.procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    @DatabaseChangeProperty(exampleValue="utf8", description="Encoding used in the file you specify in 'path'")
    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @DatabaseChangeProperty(description="File containing the procedure text. You must either use this attribute or write inline SQL within the createProcedure definition.", exampleValue="com/example/my-logic.sql")
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @DatabaseChangeProperty(description="Specifies whether the file path is relative to the changelog file rather than looked up in the search path. Default: false.")
    public Boolean isRelativeToChangelogFile() {
        return this.relativeToChangelogFile;
    }

    public void setRelativeToChangelogFile(Boolean relativeToChangelogFile) {
        this.relativeToChangelogFile = relativeToChangelogFile;
    }

    @DatabaseChangeProperties(value={@DatabaseChangeProperty(serializationType=LiquibaseSerializable.SerializationType.DIRECT_VALUE, version={ChecksumVersion.V8}), @DatabaseChangeProperty(isChangeProperty=false)})
    @Deprecated
    public String getProcedureBody() {
        return this.procedureText;
    }

    @Deprecated
    public void setProcedureBody(String procedureText) {
        this.procedureText = procedureText;
    }

    @DatabaseChangeProperties(value={@DatabaseChangeProperty(description="The SQL creating the procedure. You need to define either this attribute or 'path'. procedureText is not supported in the XML format; however, you can specify the procedure SQL inline within the createProcedure definition.", isChangeProperty=false, version={ChecksumVersion.V8}), @DatabaseChangeProperty(description="The SQL creating the procedure. You need to define either this attribute or 'path'. procedureText is not supported in the XML format; however, you can specify the procedure SQL inline within the createProcedure definition.", serializationType=LiquibaseSerializable.SerializationType.DIRECT_VALUE, alternatePropertyNames={"procedureBody"})})
    public String getProcedureText() {
        return this.procedureText;
    }

    public void setProcedureText(String procedureText) {
        this.procedureText = procedureText;
    }

    @Override
    @DatabaseChangeProperty(exampleValue="h2, oracle", since="3.1", description="Specifies which database type(s) a changeset is to be used for. See valid database type names on Supported Databases docs page. Separate multiple databases with commas. Specify that a changeset is not applicable to a particular database type by prefixing with !. The keywords 'all' and 'none' are also available.")
    public String getDbms() {
        return this.dbms;
    }

    @Override
    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    @DatabaseChangeProperty(description="Inline comments generated by update-sql. Not applied to the database")
    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @DatabaseChangeProperty(description="If the stored procedure defined by createProcedure already exists, alter it instead of creating it. Default: false")
    public Boolean getReplaceIfExists() {
        return this.replaceIfExists;
    }

    @Override
    public void setReplaceIfExists(Boolean replaceIfExists) {
        this.replaceIfExists = replaceIfExists;
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validate = new ValidationErrors();
        validate.checkDisallowedField("catalogName", this.getCatalogName(), database, MSSQLDatabase.class);
        if (this.getDbms() != null) {
            DatabaseList.validateDefinitions(this.getDbms(), validate);
        }
        if (StringUtil.trimToNull(this.getProcedureText()) != null && StringUtil.trimToNull(this.getPath()) != null) {
            validate.addError("Cannot specify both 'path' and a nested procedure text in " + Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName());
        }
        if (StringUtil.trimToNull(this.getProcedureText()) == null && StringUtil.trimToNull(this.getPath()) == null) {
            validate.addError("Must specify either 'path' or a nested procedure text in " + Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName());
        }
        if (this.getReplaceIfExists() != null && DatabaseList.definitionMatches(this.getDbms(), database, true)) {
            if (CreateProcedureChange.databaseSupportsReplaceIfExists(database)) {
                if (this.getReplaceIfExists().booleanValue() && this.getProcedureName() == null) {
                    validate.addError("procedureName is required if replaceIfExists = true");
                }
            } else {
                validate.checkDisallowedField("replaceIfExists", this.getReplaceIfExists(), database, new Class[0]);
            }
        }
        return validate;
    }

    public InputStream openSqlStream() throws IOException {
        if (this.path == null) {
            return null;
        }
        try {
            ResourceAccessor resourceAccessor = Scope.getCurrentScope().getResourceAccessor();
            String path = this.getPath();
            Boolean isRelative = this.isRelativeToChangelogFile();
            if (isRelative != null && isRelative.booleanValue()) {
                return resourceAccessor.get(this.getChangeSet().getChangeLog().getPhysicalFilePath()).resolveSibling(path).openInputStream();
            }
            return resourceAccessor.getExisting(path).openInputStream();
        }
        catch (IOException e) {
            throw new IOException("<" + Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName() + " path=" + this.path + "> -Unable to read file", e);
        }
    }

    @Override
    public CheckSum generateCheckSum() {
        ChecksumVersion version = Scope.getCurrentScope().getChecksumVersion();
        if (version.lowerOrEqualThan(ChecksumVersion.V8)) {
            return this.generateCheckSumV8();
        }
        return this.generateCheckSumLatest(this.procedureText);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    private CheckSum generateCheckSumV8() {
        if (this.path == null) {
            return super.generateCheckSum();
        }
        InputStream stream = null;
        try {
            stream = this.openSqlStream();
        }
        catch (IOException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        try {
            String procedureText = this.procedureText;
            if (stream == null && procedureText == null) {
                procedureText = "";
            }
            String localEncoding = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
            if (procedureText != null) {
                try {
                    stream = new ByteArrayInputStream(procedureText.getBytes(localEncoding));
                }
                catch (UnsupportedEncodingException e) {
                    throw new AssertionError((Object)(localEncoding + " is not supported by the JVM, this should not happen according to the JavaDoc of the Charset class"));
                }
            }
            CheckSum checkSum = CheckSum.compute(new NormalizingStreamV8(";", false, false, stream), false);
            CheckSum checkSum2 = CheckSum.compute(super.generateCheckSum().toString() + ":" + checkSum);
            return checkSum2;
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    protected CheckSum generateCheckSumLatest(String sqlText) {
        InputStream stream = null;
        try {
            Object encoding;
            if (this.getPath() == null) {
                encoding = GlobalConfiguration.FILE_ENCODING.getCurrentValue();
                if (sqlText != null) {
                    stream = new ByteArrayInputStream(sqlText.getBytes((Charset)encoding));
                }
            } else {
                stream = this.openSqlStream();
                stream = new PropertyExpandingStream(this.getChangeSet(), stream);
            }
            CheckSum checkSum = CheckSum.compute(new AbstractSQLChange.NormalizingStream(stream), false);
            encoding = CheckSum.compute(super.generateCheckSum().toString() + ":" + checkSum);
            return encoding;
        }
        catch (IOException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        finally {
            if (stream != null) {
                try {
                    stream.close();
                }
                catch (IOException iOException) {}
            }
        }
    }

    @Override
    public String[] getExcludedFieldFilters(ChecksumVersion version) {
        if (version.lowerOrEqualThan(ChecksumVersion.V8)) {
            return new String[0];
        }
        return new String[]{"path", "dbms", "relativeToChangelogFile", "procedureText", "encoding", "comments", "triggerBody", "functionBody", "packageText", "packageBodyText"};
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        String procedureText;
        String endDelimiter = ";";
        if (database instanceof OracleDatabase) {
            endDelimiter = "\n/";
        } else if (database instanceof AbstractDb2Database) {
            endDelimiter = "";
        }
        String path = this.getPath();
        if (path == null) {
            procedureText = StringUtil.trimToNull(this.getProcedureText());
        } else if (this.getChangeSet() == null) {
            procedureText = "NO CHANGESET";
        } else {
            try {
                ChangeLogParameters parameters;
                InputStream stream = this.openSqlStream();
                if (stream == null) {
                    throw new IOException(FileUtil.getFileNotFoundMessage(path));
                }
                procedureText = StreamUtil.readStreamAsString(stream, this.encoding);
                if (this.getChangeSet() != null && (parameters = this.getChangeSet().getChangeLogParameters()) != null) {
                    procedureText = parameters.expandExpressions(procedureText, this.getChangeSet().getChangeLog());
                }
            }
            catch (IOException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return this.generateStatements(procedureText, endDelimiter, database);
    }

    protected SqlStatement[] generateStatements(String logicText, String endDelimiter, Database database) {
        CreateProcedureStatement statement = new CreateProcedureStatement(this.getCatalogName(), this.getSchemaName(), this.getProcedureName(), logicText, endDelimiter);
        statement.setReplaceIfExists(this.getReplaceIfExists());
        return new SqlStatement[]{statement};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        return new ChangeStatus().unknown("Cannot check createProcedure status");
    }

    @Override
    public String getConfirmationMessage() {
        return "Stored procedure created";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    protected Map<String, Object> createExampleValueMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if ("procedureText".equals(parameterName) || "procedureBody".equals(parameterName)) {
            Map<String, Object> returnMap = super.createExampleValueMetaData(parameterName, changePropertyAnnotation);
            returnMap.put(new HsqlDatabase().getShortName(), "CREATE PROCEDURE new_customer(firstname VARCHAR(50), lastname VARCHAR(50))\n   MODIFIES SQL DATA\n   INSERT INTO CUSTOMERS (first_name, last_name) VALUES (firstname, lastname)");
            return returnMap;
        }
        return super.createExampleValueMetaData(parameterName, changePropertyAnnotation);
    }

    private static boolean databaseSupportsReplaceIfExists(Database database) {
        if (database instanceof MSSQLDatabase) {
            return true;
        }
        if (database instanceof MySQLDatabase) {
            return true;
        }
        if (database instanceof DB2Database) {
            return true;
        }
        if (database instanceof Db2zDatabase) {
            try {
                int major = database.getDatabaseMajorVersion();
                if (major > 12) {
                    return true;
                }
                if (major < 12) {
                    return false;
                }
                return database.getDatabaseMinorVersion() >= 1;
            }
            catch (DatabaseException e) {
                return false;
            }
        }
        return false;
    }
}

