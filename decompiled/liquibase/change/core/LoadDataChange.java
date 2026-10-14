/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import com.opencsv.exceptions.CsvMalformedLineException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.change.AbstractTableChange;
import liquibase.change.ChangeStatus;
import liquibase.change.ChangeWithColumns;
import liquibase.change.CheckSum;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.core.LoadDataColumnConfig;
import liquibase.changelog.ChangeSet;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.datatype.LiquibaseDataType;
import liquibase.exception.DatabaseException;
import liquibase.exception.DateParseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;
import liquibase.io.EmptyLineAndCommentSkippingInputStream;
import liquibase.logging.Logger;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.statement.BatchDmlExecutablePreparedStatement;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.ExecutablePreparedStatementBase;
import liquibase.statement.InsertExecutablePreparedStatement;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.InsertOrUpdateStatement;
import liquibase.statement.core.InsertSetStatement;
import liquibase.statement.core.InsertStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.DataType;
import liquibase.structure.core.Table;
import liquibase.util.BooleanUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;
import liquibase.util.csv.CSVReader;

@DatabaseChange(name="loadData", description="Loads data from a CSV file into an existing table", priority=1, appliesTo={"table"}, since="1.7")
public class LoadDataChange
extends AbstractTableChange
implements ChangeWithColumns<LoadDataColumnConfig> {
    public static final String DEFAULT_COMMENT_PATTERN = "#";
    public static final Pattern BASE64_PATTERN = Pattern.compile("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");
    private static final Logger LOG = Scope.getCurrentScope().getLog(LoadDataChange.class);
    private static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    private String file;
    private String commentLineStartsWith = "#";
    private Boolean relativeToChangelogFile;
    private String encoding;
    private String separator = ",";
    private String quotchar = "\"";
    private List<LoadDataColumnConfig> columns = new ArrayList<LoadDataColumnConfig>();
    private Boolean usePreparedStatements;

    protected static String getValueToWrite(Object value) {
        if (value == null || "NULL".equalsIgnoreCase(value.toString())) {
            return "";
        }
        return value.toString().trim();
    }

    protected boolean hasPreparedStatementsImplemented() {
        return true;
    }

    @Override
    public boolean supports(Database database) {
        return true;
    }

    @Override
    public boolean generateRollbackStatementsVolatile(Database database) {
        return true;
    }

    @Override
    @DatabaseChangeProperty(description="Name of the table to insert data into", requiredForDatabase={"all"}, mustEqualExisting="table")
    public String getTableName() {
        return super.getTableName();
    }

    @DatabaseChangeProperty(description="CSV file to load", exampleValue="com/example/users.csv", requiredForDatabase={"all"})
    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @DatabaseChangeProperty(description="Whether to use prepared statements instead of INSERT statement strings (if the database supports it)")
    public Boolean getUsePreparedStatements() {
        return this.usePreparedStatements;
    }

    public void setUsePreparedStatements(Boolean usePreparedStatements) {
        this.usePreparedStatements = usePreparedStatements;
    }

    @DatabaseChangeProperty(supportsDatabase={"all"}, description="Lines starting with this are treated as comments and ignored. To disable comments, set 'commentLineStartsWith' to an empty value. Default: #")
    public String getCommentLineStartsWith() {
        return this.commentLineStartsWith;
    }

    public void setCommentLineStartsWith(String commentLineStartsWith) {
        this.commentLineStartsWith = commentLineStartsWith == null ? DEFAULT_COMMENT_PATTERN : ("".equals(commentLineStartsWith) ? null : commentLineStartsWith);
    }

    @DatabaseChangeProperty(supportsDatabase={"all"}, description="Specifies whether the file path is relative to the changelog file rather than looked up in the search path. Default: false.")
    public Boolean isRelativeToChangelogFile() {
        return this.relativeToChangelogFile;
    }

    public void setRelativeToChangelogFile(Boolean relativeToChangelogFile) {
        this.relativeToChangelogFile = relativeToChangelogFile;
    }

    @DatabaseChangeProperty(exampleValue="UTF-8", supportsDatabase={"all"}, description="Encoding of the CSV file (defaults to UTF-8)")
    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @DatabaseChangeProperty(exampleValue=",", supportsDatabase={"all"}, description="Character separating the fields. Default: ,")
    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String separator) {
        if ("\\t".equals(separator)) {
            separator = "\t";
        }
        this.separator = separator;
    }

    @DatabaseChangeProperty(exampleValue="'", supportsDatabase={"all"}, description="The quote character for string fields containing the separator character. Default: \"")
    public String getQuotchar() {
        return this.quotchar;
    }

    public void setQuotchar(String quotchar) {
        this.quotchar = quotchar;
    }

    @Override
    public void addColumn(LoadDataColumnConfig column) {
        this.columns.add(column);
    }

    @Override
    @DatabaseChangeProperty(supportsDatabase={"all"}, serializationType=LiquibaseSerializable.SerializationType.NESTED_OBJECT, description="Column mapping and defaults can be defined.\n\n'header' or 'index' attributes need to be defined. If the header name in the CSV is different than the column, 'name' needs to be inserted.\nIf column 'type' is not defined, the type is taken from the database. Otherwise, for non-string columns, the type definition might be required.\nThe 'defaultValue*' attributes can define values for empty fields.")
    public List<LoadDataColumnConfig> getColumns() {
        return this.columns;
    }

    @Override
    public void setColumns(List<LoadDataColumnConfig> columns) {
        this.columns = columns;
    }

    protected String columnIdString(int index, LoadDataColumnConfig columnConfig) {
        return " / column[" + index + "]" + (StringUtil.trimToNull(columnConfig.getName()) != null ? " (name:'" + columnConfig.getName() + "')" : "");
    }

    protected ValidationErrors validateColumns(ValidationErrors validationErrors) {
        if (this.getColumns() != null) {
            int i = 1;
            for (LoadDataColumnConfig columnConfig : this.getColumns()) {
                this.validateColumn(columnConfig, validationErrors, this.columnIdString(i, columnConfig));
                ++i;
            }
        }
        return validationErrors;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public SqlStatement[] generateStatements(Database database) {
        SqlStatement[] sqlStatementArray;
        this.supportsBatchUpdates(database);
        CSVReader reader = this.getCSVReader();
        try {
            String[] line;
            if (reader == null) {
                throw new UnexpectedLiquibaseException("Unable to read file " + this.getFile());
            }
            String[] headers = reader.readNext();
            if (headers == null) {
                throw new UnexpectedLiquibaseException("Data file " + this.getFile() + " was empty");
            }
            this.addColumnsFromHeaders(headers);
            try {
                this.retrieveMissingColumnLoadTypes(this.columns, database);
            }
            catch (DatabaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
            int lineNumber = 1;
            boolean isCommentingEnabled = StringUtil.isNotEmpty(this.commentLineStartsWith);
            ArrayList<LoadDataRowConfig> rows = new ArrayList<LoadDataRowConfig>();
            while ((line = reader.readNext()) != null) {
                ++lineNumber;
                if (line.length == 0 || line.length == 1 && StringUtil.trimToNull(line[0]) == null || isCommentingEnabled && this.isLineCommented(line)) continue;
                if (line.length != headers.length) {
                    throw new UnexpectedLiquibaseException("CSV file " + this.getFile() + " Line " + lineNumber + " has " + line.length + " values defined, Header has " + headers.length + ". Numbers MUST be equal (check for unquoted string with embedded commas)");
                }
                boolean needsPreparedStatement = false;
                ArrayList<LoadDataColumnConfig> columnsFromCsv = new ArrayList<LoadDataColumnConfig>();
                for (int i = 0; i < headers.length; ++i) {
                    String value = line[i];
                    String columnName = headers[i].trim();
                    LoadDataColumnConfig valueConfig = new LoadDataColumnConfig();
                    LoadDataColumnConfig columnConfig = this.getColumnConfig(i, columnName);
                    if (columnConfig != null) {
                        DatabaseFunction function;
                        if ("skip".equalsIgnoreCase(columnConfig.getType())) continue;
                        if (columnConfig.getName() != null) {
                            columnName = columnConfig.getName();
                        }
                        if ("NULL".equalsIgnoreCase(value)) {
                            valueConfig.setType(columnConfig.getType());
                        }
                        valueConfig.setName(columnName);
                        valueConfig.setAllowUpdate(columnConfig.getAllowUpdate());
                        if (value.isEmpty()) {
                            value = columnConfig.getDefaultValue();
                        }
                        if (StringUtil.equalsWordNull(value)) {
                            valueConfig.setValue(null);
                        } else if (columnConfig.getType() == null) {
                            valueConfig.setValue(value);
                        } else if (columnConfig.getTypeEnum() == LOAD_DATA_TYPE.BOOLEAN) {
                            if (value == null) {
                                valueConfig.setValueBoolean(columnConfig.getDefaultValueBoolean());
                            } else {
                                valueConfig.setValueBoolean(BooleanUtil.parseBoolean(value));
                            }
                        } else if (columnConfig.getTypeEnum() == LOAD_DATA_TYPE.NUMERIC) {
                            if (value != null) {
                                valueConfig.setValueNumeric(value);
                            } else {
                                valueConfig.setValueNumeric(columnConfig.getDefaultValueNumeric());
                            }
                        } else if (columnConfig.getType().equalsIgnoreCase("date") || columnConfig.getType().equalsIgnoreCase("datetime") || columnConfig.getType().equalsIgnoreCase("time")) {
                            if ("NULL".equalsIgnoreCase(value) || "".equals(value)) {
                                valueConfig.setValue(null);
                            } else {
                                try {
                                    valueConfig.setType(columnConfig.getType());
                                    if (value != null) {
                                        valueConfig.setValueDate(value);
                                    }
                                    valueConfig.setValueDate(columnConfig.getDefaultValueDate());
                                }
                                catch (DateParseException e) {
                                    throw new UnexpectedLiquibaseException(e);
                                }
                            }
                        } else if (columnConfig.getTypeEnum() == LOAD_DATA_TYPE.STRING) {
                            valueConfig.setType(columnConfig.getType());
                            valueConfig.setValue(value == null ? "" : value);
                        } else if (columnConfig.getTypeEnum() == LOAD_DATA_TYPE.COMPUTED) {
                            if (null != value) {
                                function = new DatabaseFunction(value);
                                valueConfig.setValueComputed(function);
                            } else {
                                valueConfig.setValueComputed(columnConfig.getDefaultValueComputed());
                            }
                        } else if (columnConfig.getTypeEnum() == LOAD_DATA_TYPE.SEQUENCE) {
                            if (value == null) {
                                throw new UnexpectedLiquibaseException("Must set a sequence name in the loadData column defaultValue attribute");
                            }
                            function = new SequenceNextValueFunction(this.getSchemaName(), value);
                            valueConfig.setValueComputed(function);
                        } else if (columnConfig.getType().equalsIgnoreCase(LOAD_DATA_TYPE.BLOB.toString())) {
                            if ("NULL".equalsIgnoreCase(value)) {
                                valueConfig.setValue(null);
                            } else if (BASE64_PATTERN.matcher(value).matches()) {
                                valueConfig.setType(columnConfig.getType());
                                valueConfig.setValue(value);
                                needsPreparedStatement = true;
                            } else {
                                valueConfig.setValueBlobFile(value);
                                needsPreparedStatement = true;
                            }
                        } else if (columnConfig.getTypeEnum() == LOAD_DATA_TYPE.CLOB) {
                            valueConfig.setValueClobFile(value);
                            needsPreparedStatement = true;
                        } else if (columnConfig.getTypeEnum() == LOAD_DATA_TYPE.UUID) {
                            valueConfig.setType(columnConfig.getType());
                            if ("NULL".equalsIgnoreCase(value)) {
                                valueConfig.setValue(null);
                            } else {
                                valueConfig.setValue(value);
                            }
                        } else if (columnConfig.getType().equalsIgnoreCase(LOAD_DATA_TYPE.OTHER.toString())) {
                            valueConfig.setType(columnConfig.getType());
                            if ("NULL".equalsIgnoreCase(value)) {
                                valueConfig.setValue(null);
                            } else {
                                valueConfig.setValue(value);
                            }
                        } else {
                            if (columnConfig.getTypeEnum() != LOAD_DATA_TYPE.UNKNOWN) throw new UnexpectedLiquibaseException(String.format(coreBundle.getString("loaddata.type.is.not.supported"), columnConfig.getType()));
                            valueConfig.setValue(value);
                        }
                    } else {
                        if (columnName.contains("(") || columnName.contains(")") && database instanceof AbstractJdbcDatabase) {
                            columnName = ((AbstractJdbcDatabase)database).quoteObject(columnName, Column.class);
                        }
                        valueConfig.setName(columnName);
                        valueConfig.setValue(LoadDataChange.getValueToWrite(value));
                    }
                    columnsFromCsv.add(valueConfig);
                }
                boolean actuallyUsePreparedStatements = false;
                if (this.hasPreparedStatementsImplemented()) {
                    if (this.usePreparedStatements != null) {
                        if (!this.usePreparedStatements.booleanValue() && needsPreparedStatement) {
                            throw new UnexpectedLiquibaseException("loadData is requesting usePreparedStatements=false but prepared statements are required");
                        }
                        actuallyUsePreparedStatements = this.usePreparedStatements;
                    } else {
                        actuallyUsePreparedStatements = needsPreparedStatement || !this.isLoggingExecutor(database) && this.preferPreparedStatements(database);
                    }
                }
                rows.add(new LoadDataRowConfig(actuallyUsePreparedStatements, columnsFromCsv));
            }
            sqlStatementArray = this.generateStatementsFromRows(database, rows);
            if (reader == null) return sqlStatementArray;
        }
        catch (Throwable throwable) {
            try {
                if (reader == null) throw throwable;
                try {
                    reader.close();
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (CsvMalformedLineException e) {
                throw new RuntimeException("Error parsing " + this.getRelativeTo() + " on line " + e.getLineNumber() + ": " + e.getMessage());
            }
            catch (IOException | LiquibaseException e) {
                throw new RuntimeException(e);
            }
            catch (UnexpectedLiquibaseException ule) {
                if (this.getChangeSet() == null || this.getChangeSet().getFailOnError() == null || this.getChangeSet().getFailOnError().booleanValue()) throw ule;
                LOG.info("Changeset " + this.getChangeSet().toString(false) + " failed, but failOnError was false.  Error: " + ule.getMessage());
                return SqlStatement.EMPTY_SQL_STATEMENT;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        reader.close();
        return sqlStatementArray;
    }

    private boolean preferPreparedStatements(Database database) {
        if (database instanceof PostgresDatabase) {
            return false;
        }
        return this.supportsBatchUpdates(database);
    }

    protected boolean supportsBatchUpdates(Database database) {
        boolean databaseSupportsBatchUpdates = false;
        try {
            databaseSupportsBatchUpdates = database.supportsBatchUpdates();
        }
        catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        return databaseSupportsBatchUpdates;
    }

    private boolean isLoggingExecutor(Database database) {
        ExecutorService executorService2 = Scope.getCurrentScope().getSingleton(ExecutorService.class);
        return executorService2.executorExists("logging", database) && executorService2.getExecutor("logging", database) instanceof LoggingExecutor;
    }

    private void retrieveMissingColumnLoadTypes(List<LoadDataColumnConfig> columns, Database database) throws DatabaseException {
        Table snapshotOfTable;
        if (columns.stream().noneMatch(c -> c.getType() == null)) {
            return;
        }
        CatalogAndSchema catalogAndSchema = new CatalogAndSchema(this.getCatalogName(), this.getSchemaName());
        catalogAndSchema = catalogAndSchema.standardize(database);
        Table targetTable = new Table(catalogAndSchema.getCatalogName(), catalogAndSchema.getSchemaName(), database.correctObjectName(this.getTableName(), Table.class));
        try {
            snapshotOfTable = SnapshotGeneratorFactory.getInstance().createSnapshot(targetTable, database, new SnapshotControl(database, Table.class, Column.class));
        }
        catch (InvalidExampleException e) {
            throw new DatabaseException(e);
        }
        if (snapshotOfTable == null) {
            LOG.warning(String.format(coreBundle.getString("could.not.snapshot.table.to.get.the.missing.column.type.information"), database.escapeTableName(targetTable.getSchema().getCatalogName(), targetTable.getSchema().getName(), targetTable.getName())));
            return;
        }
        HashMap<String, Column> tableColumns = new HashMap<String, Column>();
        for (Column c2 : snapshotOfTable.getColumns()) {
            tableColumns.put(database.correctObjectName(c2.getName(), Column.class), c2);
        }
        HashMap<String, LoadDataColumnConfig> columnConfigs = new HashMap<String, LoadDataColumnConfig>();
        for (LoadDataColumnConfig loadDataColumnConfig : columns) {
            columnConfigs.put(database.correctObjectName(loadDataColumnConfig.getName(), Column.class), loadDataColumnConfig);
        }
        for (Map.Entry entry : columnConfigs.entrySet()) {
            if (((LoadDataColumnConfig)entry.getValue()).getType() != null) continue;
            LoadDataColumnConfig columnConfig = (LoadDataColumnConfig)entry.getValue();
            Column c4 = (Column)tableColumns.get(entry.getKey());
            if (null == c4) {
                LOG.severe(String.format(coreBundle.getString("unable.to.find.column.in.table"), columnConfig.getName(), snapshotOfTable));
                continue;
            }
            DataType dataType = c4.getType();
            if (dataType == null) {
                LOG.warning(String.format(coreBundle.getString("unable.to.find.load.data.type"), columnConfig.toString(), snapshotOfTable));
                columnConfig.setType(LOAD_DATA_TYPE.STRING);
                continue;
            }
            LiquibaseDataType liquibaseDataType = DataTypeFactory.getInstance().fromDescription(dataType.toString(), database);
            if (liquibaseDataType != null) {
                columnConfig.setType(liquibaseDataType.getLoadTypeName());
                continue;
            }
            LOG.warning(String.format(coreBundle.getString("unable.to.convert.load.data.type"), columnConfig.toString(), snapshotOfTable, dataType));
        }
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors validationErrors = new ValidationErrors(this);
        validationErrors.addAll(super.validate(database));
        return this.validateColumns(validationErrors);
    }

    public void validateColumn(LoadDataColumnConfig columnConfig, ValidationErrors validationErrors, String columnIDString) {
        if (columnConfig.getHeader() != null && columnConfig.getIndex() != null) {
            validationErrors.addWarning("Since attribute 'header' is also defined, 'index' ignored for " + validationErrors.getChangeName() + columnIDString);
        }
    }

    protected LoadDataColumnConfig columnConfigFromName(String name, Integer idx) {
        for (LoadDataColumnConfig c : this.columns) {
            if (!name.equals(c.getName()) && !name.equals(c.getHeader()) && !idx.equals(c.getIndex())) continue;
            return c;
        }
        if (null == StringUtil.trimToNull(name)) {
            throw new UnexpectedLiquibaseException("Unreferenced unnamed column is not supported");
        }
        LoadDataColumnConfig cfg = new LoadDataColumnConfig();
        this.columns.add(cfg);
        cfg.setName(name);
        return cfg;
    }

    private void addColumnsFromHeaders(String[] headers) {
        int i = 0;
        for (String columnNameFromHeader : headers) {
            LoadDataColumnConfig loadDataColumnConfig = this.columnConfigFromName(columnNameFromHeader, i);
            loadDataColumnConfig.setIndex(i);
            loadDataColumnConfig.setHeader(columnNameFromHeader);
            ++i;
        }
    }

    private boolean isLineCommented(String[] line) {
        return StringUtil.startsWith(line[0], this.commentLineStartsWith);
    }

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return true;
    }

    public CSVReader getCSVReader() throws IOException, LiquibaseException {
        ResourceAccessor resourceAccessor = Scope.getCurrentScope().getResourceAccessor();
        if (resourceAccessor == null) {
            throw new UnexpectedLiquibaseException("No file resourceAccessor specified for " + this.getFile());
        }
        Resource resource = this.getRelativeTo() == null ? resourceAccessor.get(this.file) : resourceAccessor.get(this.getRelativeTo()).resolveSibling(this.file);
        if (!resource.exists()) {
            return null;
        }
        InputStream stream = resource.openInputStream();
        if (resource.getPath().toLowerCase().endsWith(".gz") && !(stream instanceof GZIPInputStream)) {
            stream = new GZIPInputStream(stream);
        }
        Reader streamReader = StreamUtil.readStreamWithReader(stream, this.getEncoding());
        char quotchar = StringUtil.trimToEmpty(this.quotchar).isEmpty() ? (char)'\u0001' : this.quotchar.charAt(0);
        if (this.separator == null) {
            this.separator = ",";
        }
        return new CSVReader(streamReader, this.separator.charAt(0), quotchar);
    }

    protected String getRelativeTo() {
        String relativeTo = null;
        if (ObjectUtil.defaultIfNull(this.isRelativeToChangelogFile(), false).booleanValue()) {
            relativeTo = this.getChangeSet().getChangeLog().getPhysicalFilePath();
        }
        return relativeTo;
    }

    protected ExecutablePreparedStatementBase createPreparedStatement(Database database, String catalogName, String schemaName, String tableName, List<LoadDataColumnConfig> columns, ChangeSet changeSet, ResourceAccessor resourceAccessor) {
        return new InsertExecutablePreparedStatement(database, catalogName, schemaName, tableName, columns, changeSet, resourceAccessor);
    }

    protected InsertStatement createStatement(String catalogName, String schemaName, String tableName) {
        return new InsertStatement(catalogName, schemaName, tableName);
    }

    protected InsertSetStatement createStatementSet(String catalogName, String schemaName, String tableName) {
        return new InsertSetStatement(catalogName, schemaName, tableName);
    }

    protected LoadDataColumnConfig getColumnConfig(int index, String header) {
        for (LoadDataColumnConfig config : this.columns) {
            if (config.getIndex() != null && config.getIndex().equals(index)) {
                return config;
            }
            if (config.getHeader() != null && config.getHeader().equalsIgnoreCase(header)) {
                return config;
            }
            if (config.getName() == null || !config.getName().equalsIgnoreCase(header)) continue;
            return config;
        }
        return null;
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        return new ChangeStatus().unknown("Cannot check loadData status");
    }

    @Override
    public String getConfirmationMessage() {
        return String.format(coreBundle.getString("loaddata.successful"), this.getFile(), this.getTableName());
    }

    @Override
    public CheckSum generateCheckSum() {
        InputStream stream = null;
        try {
            ResourceAccessor resourceAccessor = Scope.getCurrentScope().getResourceAccessor();
            Resource resource = this.getRelativeTo() == null ? resourceAccessor.getExisting(this.file) : resourceAccessor.get(this.getRelativeTo()).resolveSibling(this.file);
            stream = new EmptyLineAndCommentSkippingInputStream(resource.openInputStream(), this.commentLineStartsWith);
            CheckSum checkSum = CheckSum.compute(this.getTableName() + ":" + CheckSum.compute(stream, true));
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
                catch (IOException iOException) {}
            }
        }
    }

    @Override
    public Warnings warn(Database database) {
        return null;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    protected SqlStatement[] generateStatementsFromRows(Database database, List<LoadDataRowConfig> rows) {
        ArrayList<InsertStatement> statements = new ArrayList<InsertStatement>();
        ArrayList<ExecutablePreparedStatementBase> preparedStatements = new ArrayList<ExecutablePreparedStatementBase>();
        for (LoadDataRowConfig loadDataRowConfig : rows) {
            List<LoadDataColumnConfig> list = loadDataRowConfig.getColumns();
            if (loadDataRowConfig.needsPreparedStatement()) {
                ExecutablePreparedStatementBase stmt = this.createPreparedStatement(database, this.getCatalogName(), this.getSchemaName(), this.getTableName(), list, this.getChangeSet(), Scope.getCurrentScope().getResourceAccessor());
                preparedStatements.add(stmt);
                continue;
            }
            InsertStatement insertStatement = this.createStatement(this.getCatalogName(), this.getSchemaName(), this.getTableName());
            for (LoadDataColumnConfig column : list) {
                String columnName = column.getName();
                Object value = column.getValueObject();
                if (value == null) {
                    value = "NULL";
                }
                insertStatement.addColumnValue(columnName, value);
                if (!(insertStatement instanceof InsertOrUpdateStatement)) continue;
                ((InsertOrUpdateStatement)insertStatement).setAllowColumnUpdate(columnName, column.getAllowUpdate() == null || column.getAllowUpdate() != false);
            }
            statements.add(insertStatement);
        }
        if (rows.stream().anyMatch(LoadDataRowConfig::needsPreparedStatement)) {
            if (this.supportsBatchUpdates(database) && !preparedStatements.isEmpty()) {
                if (database instanceof PostgresDatabase || database instanceof MySQLDatabase) {
                    return preparedStatements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
                }
                return new SqlStatement[]{new BatchDmlExecutablePreparedStatement(database, this.getCatalogName(), this.getSchemaName(), this.getTableName(), this.columns, this.getChangeSet(), Scope.getCurrentScope().getResourceAccessor(), preparedStatements)};
            }
            return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
        }
        if (statements.isEmpty()) {
            return SqlStatement.EMPTY_SQL_STATEMENT;
        }
        InsertSetStatement statementSet = this.createStatementSet(this.getCatalogName(), this.getSchemaName(), this.getTableName());
        for (SqlStatement sqlStatement : statements) {
            statementSet.addInsertStatement((InsertStatement)sqlStatement);
        }
        if (database instanceof MSSQLDatabase || database instanceof MySQLDatabase || database instanceof PostgresDatabase) {
            List<InsertStatement> list = statementSet.getStatements();
            if (list != null && !list.isEmpty() && list.get(0) instanceof InsertOrUpdateStatement) {
                return statementSet.getStatementsArray();
            }
            return new SqlStatement[]{statementSet};
        }
        return statementSet.getStatementsArray();
    }

    public static enum LOAD_DATA_TYPE {
        BOOLEAN,
        NUMERIC,
        DATE,
        STRING,
        COMPUTED,
        SEQUENCE,
        BLOB,
        CLOB,
        SKIP,
        UUID,
        OTHER,
        UNKNOWN;

    }

    protected static class LoadDataRowConfig {
        private final boolean needsPreparedStatement;
        private final List<LoadDataColumnConfig> columns;

        public LoadDataRowConfig(boolean needsPreparedStatement, List<LoadDataColumnConfig> columns) {
            this.needsPreparedStatement = needsPreparedStatement;
            this.columns = columns;
        }

        public boolean needsPreparedStatement() {
            return this.needsPreparedStatement;
        }

        public List<LoadDataColumnConfig> getColumns() {
            return this.columns;
        }
    }
}

