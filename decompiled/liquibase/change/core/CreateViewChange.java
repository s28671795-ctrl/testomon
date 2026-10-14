/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.ChecksumVersion;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.AbstractSQLChange;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.ChangeStatus;
import liquibase.change.CheckSum;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.NormalizingStreamV8;
import liquibase.change.ReplaceIfExists;
import liquibase.change.core.DropViewChange;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.PropertyExpandingStream;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.CreateViewStatement;
import liquibase.statement.core.DropViewStatement;
import liquibase.statement.core.SetViewRemarksStatement;
import liquibase.structure.core.View;
import liquibase.util.FileUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

@DatabaseChange(name="createView", description="Create a new database view", priority=1)
public class CreateViewChange
extends AbstractChange
implements ReplaceIfExists {
    private String catalogName;
    private String schemaName;
    private String viewName;
    private String selectQuery;
    private Boolean replaceIfExists;
    private Boolean fullDefinition;
    private String path;
    private Boolean relativeToChangelogFile;
    private String encoding;
    private String remarks;

    @DatabaseChangeProperty(since="3.0", description="Name of the database catalog")
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

    @DatabaseChangeProperty(description="Name of the view to create")
    public String getViewName() {
        return this.viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @DatabaseChangeProperty(serializationType=LiquibaseSerializable.SerializationType.DIRECT_VALUE, description="SQL for generating the view", exampleValue="SELECT id, name FROM person WHERE id > 10")
    public String getSelectQuery() {
        return this.selectQuery;
    }

    public void setSelectQuery(String selectQuery) {
        this.selectQuery = selectQuery;
    }

    @DatabaseChangeProperty(description="Use 'CREATE OR REPLACE' syntax", since="1.5")
    public Boolean getReplaceIfExists() {
        return this.replaceIfExists;
    }

    @Override
    public void setReplaceIfExists(Boolean replaceIfExists) {
        this.replaceIfExists = replaceIfExists;
    }

    @DatabaseChangeProperty(since="3.3", description="Set to true if selectQuery is the entire view definition. Set to false if the CREATE VIEW header should be added")
    public Boolean getFullDefinition() {
        return this.fullDefinition;
    }

    public void setFullDefinition(Boolean fullDefinition) {
        this.fullDefinition = fullDefinition;
    }

    @DatabaseChangeProperty(description="Path to the file containing the view definition. Specifying 'path' is an alternative to selectQuery.", since="3.6")
    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @DatabaseChangeProperty(description="Specifies whether the file path is relative to the changelog file rather than looked up in the search path. Default: false.")
    public Boolean getRelativeToChangelogFile() {
        return this.relativeToChangelogFile;
    }

    public void setRelativeToChangelogFile(Boolean relativeToChangelogFile) {
        this.relativeToChangelogFile = relativeToChangelogFile;
    }

    @DatabaseChangeProperty(description="Encoding used in the file you specify in 'path'")
    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @DatabaseChangeProperty(description="A brief descriptive comment stored in the view metadata")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validate = super.validate(database);
        if (!validate.hasErrors()) {
            if (StringUtil.trimToNull(this.getSelectQuery()) != null && StringUtil.trimToNull(this.getPath()) != null) {
                validate.addError("Cannot specify both 'path' and a nested view definition in " + Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName());
            }
            if (StringUtil.trimToNull(this.getSelectQuery()) == null && StringUtil.trimToNull(this.getPath()) == null) {
                validate.addError("For a createView change, you must specify either 'path' or a nested view definition in " + Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName());
            }
        }
        return validate;
    }

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return false;
    }

    protected InputStream openSqlStream() throws IOException {
        if (this.path == null) {
            return null;
        }
        try {
            ResourceAccessor resourceAccessor = Scope.getCurrentScope().getResourceAccessor();
            if (ObjectUtil.defaultIfNull(this.getRelativeToChangelogFile(), false).booleanValue()) {
                return resourceAccessor.get(this.getChangeSet().getChangeLog().getPhysicalFilePath()).resolveSibling(this.getPath()).openInputStream();
            }
            return resourceAccessor.getExisting(this.getPath()).openInputStream();
        }
        catch (IOException e) {
            throw new IOException("<" + Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName() + " path=" + this.path + "> -Unable to read file", e);
        }
    }

    @Override
    public String[] getExcludedFieldFilters(ChecksumVersion version) {
        if (version.lowerOrEqualThan(ChecksumVersion.V8)) {
            return new String[0];
        }
        return new String[]{"path", "relativeToChangelogFile", "selectQuery", "encoding"};
    }

    @Override
    public CheckSum generateCheckSum() {
        ChecksumVersion version = Scope.getCurrentScope().getChecksumVersion();
        if (version.lowerOrEqualThan(ChecksumVersion.V8)) {
            return this.generateCheckSumV8();
        }
        return this.generateCheckSumLatest(version);
    }

    private CheckSum generateCheckSumLatest(ChecksumVersion version) {
        InputStream stream = null;
        try {
            Object selectQuery;
            if (this.getPath() == null) {
                selectQuery = this.selectQuery;
                Charset encoding = GlobalConfiguration.FILE_ENCODING.getCurrentValue();
                if (selectQuery != null) {
                    stream = new ByteArrayInputStream(((String)selectQuery).getBytes(encoding));
                }
            } else {
                stream = this.openSqlStream();
                stream = new PropertyExpandingStream(this.getChangeSet(), stream);
            }
            CheckSum checkSum = CheckSum.compute(new AbstractSQLChange.NormalizingStream(stream), false);
            selectQuery = CheckSum.compute(super.generateCheckSum().toString() + ":" + checkSum);
            return selectQuery;
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
            String selectQuery = this.selectQuery;
            if (stream == null && selectQuery == null) {
                selectQuery = "";
            }
            String encoding = GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue();
            if (selectQuery != null) {
                try {
                    stream = new ByteArrayInputStream(selectQuery.getBytes(encoding));
                }
                catch (UnsupportedEncodingException e) {
                    throw new AssertionError((Object)(encoding + " is not supported by the JVM, this should not happen according to the JavaDoc of the Charset class"));
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

    @Override
    public SqlStatement[] generateStatements(Database database) {
        String selectQuery;
        String path;
        ArrayList<AbstractSqlStatement> statements = new ArrayList<AbstractSqlStatement>();
        boolean replaceIfExists = this.getReplaceIfExists() != null && this.getReplaceIfExists() != false;
        boolean fullDefinition = false;
        if (this.fullDefinition != null) {
            fullDefinition = this.fullDefinition;
        }
        if ((path = this.getPath()) == null) {
            selectQuery = StringUtil.trimToNull(this.getSelectQuery());
        } else {
            try {
                ChangeLogParameters parameters;
                InputStream stream = this.openSqlStream();
                if (stream == null) {
                    throw new IOException(FileUtil.getFileNotFoundMessage(path));
                }
                selectQuery = StreamUtil.readStreamAsString(stream, this.encoding);
                if (this.getChangeSet() != null && (parameters = this.getChangeSet().getChangeLogParameters()) != null) {
                    selectQuery = parameters.expandExpressions(selectQuery, this.getChangeSet().getChangeLog());
                }
            }
            catch (IOException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        if (!this.supportsReplaceIfExistsOption(database) && replaceIfExists) {
            statements.add(new DropViewStatement(this.getCatalogName(), this.getSchemaName(), this.getViewName()));
            statements.add(this.createViewStatement(this.getCatalogName(), this.getSchemaName(), this.getViewName(), selectQuery, false).setFullDefinition(fullDefinition));
        } else {
            statements.add(this.createViewStatement(this.getCatalogName(), this.getSchemaName(), this.getViewName(), selectQuery, replaceIfExists).setFullDefinition(fullDefinition));
        }
        List<Class> databaseSupportsViewComments = Arrays.asList(OracleDatabase.class, PostgresDatabase.class, MSSQLDatabase.class, DB2Database.class, SybaseASADatabase.class);
        boolean supportsViewComments = databaseSupportsViewComments.stream().anyMatch(clazz -> clazz.isInstance(database));
        if (supportsViewComments && StringUtil.trimToNull(this.remarks) != null) {
            SetViewRemarksStatement remarksStatement = new SetViewRemarksStatement(this.catalogName, this.schemaName, this.viewName, this.remarks);
            if (SqlGeneratorFactory.getInstance().supports(remarksStatement, database)) {
                statements.add(remarksStatement);
            }
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    protected CreateViewStatement createViewStatement(String catalogName, String schemaName, String viewName, String selectQuery, boolean replaceIfExists) {
        return new CreateViewStatement(catalogName, schemaName, viewName, selectQuery, replaceIfExists);
    }

    @Override
    public String getConfirmationMessage() {
        return "View " + this.getViewName() + " created";
    }

    @Override
    protected Change[] createInverses() {
        DropViewChange inverse = new DropViewChange();
        inverse.setViewName(this.getViewName());
        inverse.setSchemaName(this.getSchemaName());
        return new Change[]{inverse};
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        ChangeStatus result = new ChangeStatus();
        try {
            View example = new View(this.getCatalogName(), this.getSchemaName(), this.getViewName());
            View snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(example, database);
            result.assertComplete(snapshot != null, "View does not exist");
            return result;
        }
        catch (Exception e) {
            return result.unknown(e);
        }
    }

    private boolean supportsReplaceIfExistsOption(Database database) {
        return !(database instanceof SQLiteDatabase);
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    protected void customLoadLogic(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        Object value = parsedNode.getValue();
        if (value instanceof String) {
            this.setSelectQuery((String)value);
        }
    }
}

