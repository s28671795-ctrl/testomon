/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.StandardChangeLogHistoryService;
import liquibase.configuration.ConfiguredValue;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.LiquibaseTableNamesFactory;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.OfflineConnection;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.exception.DateParseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.ExecutorService;
import liquibase.lockservice.LockServiceFactory;
import liquibase.sql.Sql;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceCurrentValueFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.statement.core.RawCallStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.ISODateFormat;
import liquibase.util.NowAndTodayUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

public abstract class AbstractJdbcDatabase
implements Database {
    private static final int FETCH_SIZE = 1000;
    private static final int DEFAULT_MAX_TIMESTAMP_FRACTIONAL_DIGITS = 9;
    private static final String STARTS_WITH_NUMBER_REGEX = "^[0-9].*";
    private static final Pattern STARTS_WITH_NUMBER_PATTERN = Pattern.compile("^[0-9].*");
    private static final String NON_WORD_REGEX = ".*\\W.*";
    private static final Pattern NON_WORD_PATTERN = Pattern.compile(".*\\W.*");
    private static final String CREATE_VIEW_AS_REGEX = "^CREATE\\s+.*?VIEW\\s+.*?\\s+AS(?:\\s+|(?=\\())";
    private static final Pattern CREATE_VIEW_AS_PATTERN = Pattern.compile("^CREATE\\s+.*?VIEW\\s+.*?\\s+AS(?:\\s+|(?=\\())", 34);
    private static final String DATE_ONLY_REGEX = "^\\d{4}\\-\\d{2}\\-\\d{2}$";
    private static final Pattern DATE_ONLY_PATTERN = Pattern.compile("^\\d{4}\\-\\d{2}\\-\\d{2}$");
    private static final String DATE_TIME_REGEX = "^\\d{4}\\-\\d{2}\\-\\d{2}[T ]\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?$";
    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("^\\d{4}\\-\\d{2}\\-\\d{2}[T ]\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?$");
    private static final String TIMESTAMP_REGEX = "^\\d{4}\\-\\d{2}\\-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+$";
    private static final Pattern TIMESTAMP_PATTERN = Pattern.compile("^\\d{4}\\-\\d{2}\\-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+$");
    private static final String TIME_REGEX = "^\\d{2}:\\d{2}:\\d{2}$";
    private static final Pattern TIME_PATTERN = Pattern.compile("^\\d{2}:\\d{2}:\\d{2}$");
    private static final String NAME_WITH_DESC_REGEX = "(?i).*\\s+DESC";
    private static final Pattern NAME_WITH_DESC_PATTERN = Pattern.compile("(?i).*\\s+DESC");
    private static final String NAME_WITH_ASC_REGEX = "(?i).*\\s+ASC";
    private static final Pattern NAME_WITH_ASC_PATTERN = Pattern.compile("(?i).*\\s+ASC");
    private final Set<String> reservedWords = new HashSet<String>();
    protected String defaultCatalogName;
    protected String defaultSchemaName;
    protected String currentDateTimeFunction;
    protected String sequenceNextValueFunction;
    protected String sequenceCurrentValueFunction;
    protected List<DatabaseFunction> dateFunctions = new ArrayList<DatabaseFunction>();
    protected List<String> unmodifiableDataTypes = new ArrayList<String>();
    protected BigInteger defaultAutoIncrementStartWith = BigInteger.ONE;
    protected BigInteger defaultAutoIncrementBy = BigInteger.ONE;
    protected Boolean unquotedObjectsAreUppercased;
    protected ObjectQuotingStrategy quotingStrategy = ObjectQuotingStrategy.LEGACY;
    protected Boolean caseSensitive;
    private String databaseChangeLogTableName;
    private String databaseChangeLogLockTableName;
    private String liquibaseTablespaceName;
    private String liquibaseSchemaName;
    private String liquibaseCatalogName;
    private Boolean previousAutoCommit;
    private boolean canCacheLiquibaseTableInfo = false;
    private DatabaseConnection connection;
    private boolean outputDefaultSchema = true;
    private boolean outputDefaultCatalog = true;
    private boolean defaultCatalogSet;
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    public String getName() {
        return this.toString();
    }

    @Override
    public boolean requiresPassword() {
        return true;
    }

    @Override
    public boolean requiresUsername() {
        return true;
    }

    public DatabaseObject[] getContainingObjects() {
        return null;
    }

    @Override
    public DatabaseConnection getConnection() {
        return this.connection;
    }

    @Override
    public void setConnection(DatabaseConnection conn) {
        Scope.getCurrentScope().getLog(this.getClass()).fine("Connected to " + conn.getConnectionUserName() + "@" + conn.getURL());
        this.connection = conn;
        try {
            boolean autoCommit = conn.getAutoCommit();
            if (autoCommit == this.getAutoCommitMode()) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Not adjusting the auto commit mode; it is already " + autoCommit);
            } else {
                this.previousAutoCommit = autoCommit;
                Scope.getCurrentScope().getLog(this.getClass()).fine("Setting auto commit to " + this.getAutoCommitMode() + " from " + autoCommit);
                this.connection.setAutoCommit(this.getAutoCommitMode());
            }
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Cannot set auto commit to " + this.getAutoCommitMode() + " on connection");
        }
        this.connection.attached(this);
    }

    @Override
    public boolean getAutoCommitMode() {
        return !this.supportsDDLInTransaction();
    }

    @Override
    public final void addReservedWords(Collection<String> words) {
        this.reservedWords.addAll(words);
    }

    @Override
    public boolean supportsDDLInTransaction() {
        return true;
    }

    @Override
    public String getDatabaseProductName() {
        if (this.connection == null) {
            return this.getDefaultDatabaseProductName();
        }
        try {
            return this.connection.getDatabaseProductName();
        }
        catch (DatabaseException e) {
            throw new RuntimeException("Cannot get database name");
        }
    }

    protected abstract String getDefaultDatabaseProductName();

    @Override
    public String getDatabaseProductVersion() throws DatabaseException {
        if (this.connection == null) {
            return null;
        }
        return this.connection.getDatabaseProductVersion();
    }

    @Override
    public int getDatabaseMajorVersion() throws DatabaseException {
        if (this.connection == null) {
            return 999;
        }
        return this.connection.getDatabaseMajorVersion();
    }

    @Override
    public int getDatabaseMinorVersion() throws DatabaseException {
        if (this.connection == null) {
            return -1;
        }
        return this.connection.getDatabaseMinorVersion();
    }

    @Override
    public String getDefaultCatalogName() {
        if (this.defaultCatalogName == null) {
            if (this.defaultSchemaName != null && !this.supports(Schema.class)) {
                return this.defaultSchemaName;
            }
            if (this.connection != null) {
                try {
                    this.defaultCatalogName = this.getConnectionCatalogName();
                }
                catch (DatabaseException e) {
                    Scope.getCurrentScope().getLog(this.getClass()).info("Error getting default catalog", e);
                }
            }
        }
        return this.defaultCatalogName;
    }

    @Override
    public void setDefaultCatalogName(String defaultCatalogName) {
        this.defaultCatalogName = this.correctObjectName(defaultCatalogName, Catalog.class);
        this.defaultCatalogSet = defaultCatalogName != null;
    }

    protected String getConnectionCatalogName() throws DatabaseException {
        return this.connection.getCatalog();
    }

    @Deprecated
    public CatalogAndSchema correctSchema(String catalog, String schema) {
        return new CatalogAndSchema(catalog, schema).standardize(this);
    }

    @Override
    @Deprecated
    public CatalogAndSchema correctSchema(CatalogAndSchema schema) {
        if (schema == null) {
            return new CatalogAndSchema(this.getDefaultCatalogName(), this.getDefaultSchemaName());
        }
        return schema.standardize(this);
    }

    @Override
    public String correctObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (this.isCatalogOrSchemaType(objectType) && this.preserveCaseIfRequested() == CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE) {
            return objectName;
        }
        if (this.getObjectQuotingStrategy() == ObjectQuotingStrategy.QUOTE_ALL_OBJECTS || this.unquotedObjectsAreUppercased == null || objectName == null || objectName.startsWith(this.getQuotingStartCharacter()) && objectName.endsWith(this.getQuotingEndCharacter())) {
            return objectName;
        }
        if (Boolean.TRUE.equals(this.unquotedObjectsAreUppercased)) {
            return objectName.toUpperCase(Locale.US);
        }
        return objectName.toLowerCase(Locale.US);
    }

    protected boolean isCatalogOrSchemaType(Class<? extends DatabaseObject> objectType) {
        return objectType.equals(Catalog.class) || objectType.equals(Schema.class);
    }

    private CatalogAndSchema.CatalogAndSchemaCase preserveCaseIfRequested() {
        if (Boolean.TRUE.equals(GlobalConfiguration.PRESERVE_SCHEMA_CASE.getCurrentValue())) {
            return CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE;
        }
        return this.getSchemaAndCatalogCase();
    }

    @Override
    public CatalogAndSchema getDefaultSchema() {
        return new CatalogAndSchema(this.getDefaultCatalogName(), this.getDefaultSchemaName());
    }

    @Override
    public String getDefaultSchemaName() {
        if (!this.supports(Schema.class)) {
            return this.getDefaultCatalogName();
        }
        if (this.defaultSchemaName == null && this.connection != null) {
            this.defaultSchemaName = this.getConnectionSchemaName();
            if (this.defaultSchemaName != null) {
                Scope.getCurrentScope().getLog(this.getClass()).info("Set default schema name to " + this.defaultSchemaName);
            }
        }
        return this.defaultSchemaName;
    }

    @Override
    public Integer getDefaultScaleForNativeDataType(String nativeDataType) {
        return null;
    }

    @Override
    public void setDefaultSchemaName(String schemaName) {
        this.defaultSchemaName = this.correctObjectName(schemaName, Schema.class);
        if (!this.supports(Schema.class)) {
            this.defaultCatalogSet = schemaName != null;
        }
    }

    protected String getConnectionSchemaName() {
        if (this.connection == null) {
            return null;
        }
        if (this.connection instanceof OfflineConnection) {
            return ((OfflineConnection)this.connection).getSchema();
        }
        if (!(this.connection instanceof JdbcConnection)) {
            return this.defaultSchemaName;
        }
        try {
            SqlStatement currentSchemaStatement = this.getConnectionSchemaNameCallStatement();
            return Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(currentSchemaStatement, String.class);
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Error getting default schema", e);
            return null;
        }
    }

    protected SqlStatement getConnectionSchemaNameCallStatement() {
        return new RawCallStatement("call current_schema");
    }

    @Override
    public Integer getFetchSize() {
        return 1000;
    }

    protected Set<String> getSystemTables() {
        return new HashSet<String>();
    }

    protected Set<String> getSystemViews() {
        return new HashSet<String>();
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public boolean supportsAutoIncrement() {
        return true;
    }

    @Override
    public String getDateLiteral(String isoDate) {
        if (this.isDateOnly(isoDate) || this.isTimeOnly(isoDate)) {
            return "'" + isoDate + "'";
        }
        if (this.isDateTime(isoDate)) {
            return "'" + isoDate.replace('T', ' ') + "'";
        }
        return "BAD_DATE_FORMAT:" + isoDate;
    }

    @Override
    public String getDateTimeLiteral(Timestamp date) {
        return this.getDateLiteral(new ISODateFormat().format(date).replaceFirst("^'", "").replaceFirst("'$", ""));
    }

    @Override
    public String getDateLiteral(Date date) {
        return this.getDateLiteral(new ISODateFormat().format(date).replaceFirst("^'", "").replaceFirst("'$", ""));
    }

    @Override
    public String getTimeLiteral(Time date) {
        return this.getDateLiteral(new ISODateFormat().format(date).replaceFirst("^'", "").replaceFirst("'$", ""));
    }

    @Override
    public String getDateLiteral(java.util.Date date) {
        if (date instanceof Date) {
            return this.getDateLiteral((Date)date);
        }
        if (date instanceof Time) {
            return this.getTimeLiteral((Time)date);
        }
        if (date instanceof Timestamp) {
            return this.getDateTimeLiteral((Timestamp)date);
        }
        if (date instanceof java.util.Date) {
            return this.getDateTimeLiteral(new Timestamp(date.getTime()));
        }
        throw new RuntimeException("Unexpected type: " + date.getClass().getName());
    }

    @Override
    public java.util.Date parseDate(String dateAsString) throws DateParseException {
        try {
            if (dateAsString.indexOf(" ") > 0) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateAsString);
            }
            if (dateAsString.indexOf("T") > 0) {
                return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateAsString);
            }
            if (dateAsString.indexOf(":") > 0) {
                return new SimpleDateFormat("HH:mm:ss").parse(dateAsString);
            }
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateAsString);
        }
        catch (ParseException e) {
            throw new DateParseException(dateAsString);
        }
    }

    protected boolean isDateOnly(String isoDate) {
        return DATE_ONLY_PATTERN.matcher(isoDate).matches() || NowAndTodayUtil.isNowOrTodayFormat(isoDate);
    }

    protected boolean isDateTime(String isoDate) {
        return DATE_TIME_PATTERN.matcher(isoDate).matches() || NowAndTodayUtil.isNowOrTodayFormat(isoDate);
    }

    protected boolean isTimestamp(String isoDate) {
        return TIMESTAMP_PATTERN.matcher(isoDate).matches() || NowAndTodayUtil.isNowOrTodayFormat(isoDate);
    }

    protected boolean isTimeOnly(String isoDate) {
        return TIME_PATTERN.matcher(isoDate).matches() || NowAndTodayUtil.isNowOrTodayFormat(isoDate);
    }

    @Override
    public String getLineComment() {
        return "--";
    }

    @Override
    public String getAutoIncrementClause(BigInteger startWith, BigInteger incrementBy, String generationType, Boolean defaultOnNull) {
        if (!this.supportsAutoIncrement()) {
            return "";
        }
        String autoIncrementClause = this.getAutoIncrementClause(generationType, defaultOnNull);
        boolean generateStartWith = this.generateAutoIncrementStartWith(startWith);
        boolean generateIncrementBy = this.generateAutoIncrementBy(incrementBy);
        if (generateStartWith || generateIncrementBy) {
            autoIncrementClause = autoIncrementClause + this.getAutoIncrementOpening();
            if (generateStartWith) {
                autoIncrementClause = autoIncrementClause + String.format(this.getAutoIncrementStartWithClause(), startWith == null ? this.defaultAutoIncrementStartWith : startWith);
            }
            if (generateIncrementBy) {
                if (generateStartWith) {
                    autoIncrementClause = !(this instanceof PostgresDatabase) ? autoIncrementClause + ", " : autoIncrementClause + " ";
                }
                autoIncrementClause = autoIncrementClause + String.format(this.getAutoIncrementByClause(), incrementBy == null ? this.defaultAutoIncrementBy : incrementBy);
            }
            autoIncrementClause = autoIncrementClause + this.getAutoIncrementClosing();
        }
        return autoIncrementClause;
    }

    protected String getAutoIncrementClause() {
        return "GENERATED BY DEFAULT AS IDENTITY";
    }

    protected String getAutoIncrementClause(String generationType, Boolean defaultOnNull) {
        return this.getAutoIncrementClause();
    }

    protected boolean generateAutoIncrementStartWith(BigInteger startWith) {
        return startWith != null && !startWith.equals(this.defaultAutoIncrementStartWith);
    }

    protected boolean generateAutoIncrementBy(BigInteger incrementBy) {
        return incrementBy != null && !incrementBy.equals(this.defaultAutoIncrementBy);
    }

    protected String getAutoIncrementOpening() {
        return " (";
    }

    protected String getAutoIncrementClosing() {
        return ")";
    }

    protected String getAutoIncrementStartWithClause() {
        return "START WITH %d";
    }

    protected String getAutoIncrementByClause() {
        return "INCREMENT BY %d";
    }

    @Override
    public String getConcatSql(String ... values2) {
        return StringUtil.join(values2, " || ");
    }

    @Override
    public String getDatabaseChangeLogTableName() {
        if (this.databaseChangeLogTableName != null) {
            return this.correctObjectName(this.databaseChangeLogTableName, Table.class);
        }
        return this.correctObjectName(GlobalConfiguration.DATABASECHANGELOG_TABLE_NAME.getCurrentValue(), Table.class);
    }

    @Override
    public void setDatabaseChangeLogTableName(String tableName) {
        this.databaseChangeLogTableName = tableName;
    }

    protected String getRawDatabaseChangeLogTableName() {
        return this.databaseChangeLogTableName;
    }

    @Override
    public String getDatabaseChangeLogLockTableName() {
        if (this.databaseChangeLogLockTableName != null) {
            return this.correctObjectName(this.databaseChangeLogLockTableName, Table.class);
        }
        return this.correctObjectName(GlobalConfiguration.DATABASECHANGELOGLOCK_TABLE_NAME.getCurrentValue(), Table.class);
    }

    @Override
    public void setDatabaseChangeLogLockTableName(String tableName) {
        this.databaseChangeLogLockTableName = tableName;
    }

    protected String getRawDatabaseChangeLogLockTableName() {
        return this.databaseChangeLogLockTableName;
    }

    @Override
    public String getLiquibaseTablespaceName() {
        if (this.liquibaseTablespaceName != null) {
            return this.liquibaseTablespaceName;
        }
        return GlobalConfiguration.LIQUIBASE_TABLESPACE_NAME.getCurrentValue();
    }

    @Override
    public void setLiquibaseTablespaceName(String tablespace) {
        this.liquibaseTablespaceName = tablespace;
    }

    protected boolean canCreateChangeLogTable() throws DatabaseException {
        return ((StandardChangeLogHistoryService)Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this)).canCreateChangeLogTable();
    }

    @Override
    public void setCanCacheLiquibaseTableInfo(boolean canCacheLiquibaseTableInfo) {
        this.canCacheLiquibaseTableInfo = canCacheLiquibaseTableInfo;
    }

    @Override
    public String getLiquibaseCatalogName() {
        if (this.liquibaseCatalogName != null) {
            return this.liquibaseCatalogName;
        }
        String configuredCatalogName = GlobalConfiguration.LIQUIBASE_CATALOG_NAME.getCurrentValue();
        if (configuredCatalogName != null) {
            return configuredCatalogName;
        }
        return this.getDefaultCatalogName();
    }

    @Override
    public void setLiquibaseCatalogName(String catalogName) {
        this.liquibaseCatalogName = catalogName;
    }

    @Override
    public String getLiquibaseSchemaName() {
        if (this.liquibaseSchemaName != null) {
            return this.liquibaseSchemaName;
        }
        ConfiguredValue<String> configuredValue = GlobalConfiguration.LIQUIBASE_SCHEMA_NAME.getCurrentConfiguredValue();
        if (!configuredValue.wasDefaultValueUsed()) {
            return configuredValue.getValue();
        }
        return this.getDefaultSchemaName();
    }

    @Override
    public void setLiquibaseSchemaName(String schemaName) {
        this.liquibaseSchemaName = schemaName;
    }

    @Override
    public boolean isCaseSensitive() {
        if (this.caseSensitive == null && this.connection != null && this.connection instanceof JdbcConnection) {
            try {
                this.caseSensitive = ((JdbcConnection)this.connection).getUnderlyingConnection().getMetaData().supportsMixedCaseIdentifiers();
            }
            catch (SQLException e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Cannot determine case sensitivity from JDBC driver", e);
            }
        }
        if (this.caseSensitive == null) {
            return false;
        }
        return this.caseSensitive;
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean isReservedWord(String string) {
        return this.reservedWords.contains(string.toUpperCase());
    }

    protected boolean startsWithNumeric(String objectName) {
        return STARTS_WITH_NUMBER_PATTERN.matcher(objectName).matches();
    }

    @Override
    public void dropDatabaseObjects(CatalogAndSchema schemaToDrop) throws LiquibaseException {
        this.dropDatabaseObjects(schemaToDrop, null);
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return this instanceof SQLiteDatabase || this instanceof SybaseDatabase || this instanceof SybaseASADatabase || this instanceof PostgresDatabase || this instanceof OracleDatabase;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example == null) {
            return false;
        }
        if (example.getSchema() != null && example.getSchema().getName() != null && "information_schema".equalsIgnoreCase(example.getSchema().getName())) {
            return true;
        }
        if (example instanceof Table && this.getSystemTables().contains(example.getName())) {
            return true;
        }
        return example instanceof View && this.getSystemViews().contains(example.getName());
    }

    public boolean isSystemView(CatalogAndSchema schema, String viewName) {
        if ("information_schema".equalsIgnoreCase((schema = schema.customize(this)).getSchemaName())) {
            return true;
        }
        return this.getSystemViews().contains(viewName);
    }

    @Override
    public boolean isLiquibaseObject(DatabaseObject object) {
        if (object instanceof Table) {
            Schema liquibaseSchema = new Schema(this.getLiquibaseCatalogName(), this.getLiquibaseSchemaName());
            LiquibaseTableNamesFactory liquibaseTableNamesFactory = Scope.getCurrentScope().getSingleton(LiquibaseTableNamesFactory.class);
            List<String> liquibaseTableNames = liquibaseTableNamesFactory.getLiquibaseTableNames(this);
            return liquibaseTableNames.stream().anyMatch(tableName -> DatabaseObjectComparatorFactory.getInstance().isSameObject(object, new Table().setName((String)tableName).setSchema(liquibaseSchema), null, this));
        }
        if (object instanceof Column) {
            return this.isLiquibaseObject(((Column)object).getRelation());
        }
        if (object instanceof Index) {
            return this.isLiquibaseObject(((Index)object).getRelation());
        }
        if (object instanceof PrimaryKey) {
            return this.isLiquibaseObject(((PrimaryKey)object).getTable());
        }
        return false;
    }

    @Override
    public void tag(String tagString) throws DatabaseException {
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).tag(tagString);
    }

    @Override
    public boolean doesTagExist(String tag) throws DatabaseException {
        return Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).tagExists(tag);
    }

    public String toString() {
        if (this.getConnection() == null) {
            return this.getShortName() + " Database";
        }
        return this.getConnection().getConnectionUserName() + " @ " + this.getConnection().getURL() + (this.getDefaultSchemaName() == null ? "" : " (Default Schema: " + this.getDefaultSchemaName() + ")");
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        schema = schema.customize(this);
        String definition = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new GetViewDefinitionStatement(schema.getCatalogName(), schema.getSchemaName(), viewName), String.class);
        if (definition == null) {
            return null;
        }
        return CREATE_VIEW_AS_PATTERN.matcher(definition).replaceFirst("");
    }

    @Override
    public String escapeTableName(String catalogName, String schemaName, String tableName) {
        return this.escapeObjectName(catalogName, schemaName, tableName, Table.class);
    }

    @Override
    public String escapeObjectName(String catalogName, String schemaName, String objectName, Class<? extends DatabaseObject> objectType) {
        if (this.supports(Schema.class)) {
            catalogName = StringUtil.trimToNull(catalogName);
            schemaName = StringUtil.trimToNull(schemaName);
            if (catalogName == null) {
                catalogName = this.getDefaultCatalogName();
            }
            if (schemaName == null) {
                schemaName = this.getDefaultSchemaName();
            }
            if (!this.supportsCatalogInObjectName(objectType)) {
                catalogName = null;
            }
            if (catalogName == null && schemaName == null) {
                return this.escapeObjectName(objectName, objectType);
            }
            if (catalogName == null || !this.supportsCatalogInObjectName(objectType)) {
                if (this.isDefaultSchema(catalogName, schemaName) && !this.getOutputDefaultSchema()) {
                    return this.escapeObjectName(objectName, objectType);
                }
                return this.escapeObjectName(schemaName, Schema.class) + "." + this.escapeObjectName(objectName, objectType);
            }
            if (this.isDefaultSchema(catalogName, schemaName) && !this.getOutputDefaultSchema() && !this.getOutputDefaultCatalog()) {
                return this.escapeObjectName(objectName, objectType);
            }
            if (this.isDefaultSchema(catalogName, schemaName) && !this.getOutputDefaultCatalog()) {
                return this.escapeObjectName(schemaName, Schema.class) + "." + this.escapeObjectName(objectName, objectType);
            }
            return this.escapeObjectName(catalogName, Catalog.class) + "." + this.escapeObjectName(schemaName, Schema.class) + "." + this.escapeObjectName(objectName, objectType);
        }
        if (this.supports(Catalog.class)) {
            catalogName = StringUtil.trimToNull(catalogName);
            schemaName = StringUtil.trimToNull(schemaName);
            if (catalogName != null) {
                if (this.getOutputDefaultCatalog()) {
                    return this.escapeObjectName(catalogName, Catalog.class) + "." + this.escapeObjectName(objectName, objectType);
                }
                if (!this.defaultCatalogSet && this.isDefaultCatalog(catalogName)) {
                    return this.escapeObjectName(objectName, objectType);
                }
                return this.escapeObjectName(catalogName, Catalog.class) + "." + this.escapeObjectName(objectName, objectType);
            }
            if (schemaName != null) {
                if (this.getOutputDefaultCatalog()) {
                    return this.escapeObjectName(schemaName, Catalog.class) + "." + this.escapeObjectName(objectName, objectType);
                }
                if (!this.defaultCatalogSet && this.isDefaultCatalog(schemaName)) {
                    return this.escapeObjectName(objectName, objectType);
                }
                return this.escapeObjectName(schemaName, Catalog.class) + "." + this.escapeObjectName(objectName, objectType);
            }
            catalogName = this.getDefaultCatalogName();
            if (catalogName == null) {
                return this.escapeObjectName(objectName, objectType);
            }
            if (this.defaultCatalogSet || this.isDefaultCatalog(catalogName) && this.getOutputDefaultCatalog()) {
                return this.escapeObjectName(catalogName, Catalog.class) + "." + this.escapeObjectName(objectName, objectType);
            }
            return this.escapeObjectName(objectName, objectType);
        }
        return this.escapeObjectName(objectName, objectType);
    }

    @Override
    public String escapeObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName != null) {
            if (this.mustQuoteObjectName(objectName, objectType)) {
                return this.quoteObject(objectName, objectType).trim();
            }
            if (this.quotingStrategy == ObjectQuotingStrategy.QUOTE_ALL_OBJECTS) {
                return this.quoteObject(objectName, objectType).trim();
            }
            objectName = objectName.trim();
        }
        return objectName;
    }

    protected boolean mustQuoteObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (this.isCatalogOrSchemaType(objectType) && this.preserveCaseIfRequested() == CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE) {
            return true;
        }
        return objectName.contains("-") || this.startsWithNumeric(objectName) || this.isReservedWord(objectName) || NON_WORD_PATTERN.matcher(objectName).matches();
    }

    protected String getQuotingStartCharacter() {
        return "\"";
    }

    protected String getQuotingEndCharacter() {
        return "\"";
    }

    protected String getQuotingEndReplacement() {
        return "\"\"";
    }

    public String quoteObject(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        }
        return this.getQuotingStartCharacter() + objectName.replace(this.getQuotingEndCharacter(), this.getQuotingEndReplacement()) + this.getQuotingEndCharacter();
    }

    @Override
    public String escapeIndexName(String catalogName, String schemaName, String indexName) {
        return this.escapeObjectName(catalogName, schemaName, indexName, Index.class);
    }

    @Override
    public String escapeSequenceName(String catalogName, String schemaName, String sequenceName) {
        return this.escapeObjectName(catalogName, schemaName, sequenceName, Sequence.class);
    }

    @Override
    public String escapeConstraintName(String constraintName) {
        return this.escapeObjectName(constraintName, Index.class);
    }

    @Override
    public String escapeColumnName(String catalogName, String schemaName, String tableName, String columnName) {
        return this.escapeObjectName(columnName, Column.class);
    }

    @Override
    public String escapeColumnName(String catalogName, String schemaName, String tableName, String columnName, boolean quoteNamesThatMayBeFunctions) {
        if (this.quotingStrategy == ObjectQuotingStrategy.QUOTE_ALL_OBJECTS) {
            return this.quoteObject(columnName, Column.class);
        }
        if (columnName.contains("(")) {
            if (quoteNamesThatMayBeFunctions) {
                return this.quoteObject(columnName, Column.class);
            }
            return columnName;
        }
        return this.escapeObjectName(columnName, Column.class);
    }

    @Override
    public String escapeColumnNameList(String columnNames) {
        StringBuilder sb = new StringBuilder();
        for (String columnName : StringUtil.splitAndTrim(columnNames, ",")) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            boolean descending = false;
            if (NAME_WITH_DESC_PATTERN.matcher(columnName).matches()) {
                columnName = columnName.replaceFirst("(?i)\\s+DESC$", "");
                descending = true;
            } else if (NAME_WITH_ASC_PATTERN.matcher(columnName).matches()) {
                columnName = columnName.replaceFirst("(?i)\\s+ASC$", "");
            }
            sb.append(this.escapeObjectName(columnName, Column.class));
            if (!descending) continue;
            sb.append(" DESC");
        }
        return sb.toString();
    }

    @Override
    public boolean supportsSchemas() {
        return true;
    }

    @Override
    public boolean supportsCatalogs() {
        return true;
    }

    public boolean jdbcCallsCatalogsSchemas() {
        return false;
    }

    @Override
    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> type) {
        return false;
    }

    @Override
    public String generatePrimaryKeyName(String tableName) {
        return "PK_" + tableName.toUpperCase(Locale.US);
    }

    @Override
    public String escapeViewName(String catalogName, String schemaName, String viewName) {
        return this.escapeObjectName(catalogName, schemaName, viewName, View.class);
    }

    @Override
    public ChangeSet.RunStatus getRunStatus(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).getRunStatus(changeSet);
    }

    @Override
    public RanChangeSet getRanChangeSet(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).getRanChangeSet(changeSet);
    }

    @Override
    public List<RanChangeSet> getRanChangeSetList() throws DatabaseException {
        return Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).getRanChangeSets();
    }

    @Override
    public java.util.Date getRanDate(ChangeSet changeSet) throws DatabaseException, DatabaseHistoryException {
        return Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).getRanDate(changeSet);
    }

    @Override
    public void markChangeSetExecStatus(ChangeSet changeSet, ChangeSet.ExecType execType) throws DatabaseException {
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).setExecType(changeSet, execType);
    }

    @Override
    public void removeRanStatus(ChangeSet changeSet) throws DatabaseException {
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).removeFromHistory(changeSet);
    }

    @Override
    public String escapeStringForDatabase(String string) {
        if (string == null) {
            return null;
        }
        return string.replaceAll("'", "''");
    }

    @Override
    public void commit() throws DatabaseException {
        this.getConnection().commit();
    }

    @Override
    public void rollback() throws DatabaseException {
        this.getConnection().rollback();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AbstractJdbcDatabase that = (AbstractJdbcDatabase)o;
        if (this.connection == null) {
            if (that.connection == null) {
                return this == that;
            }
            return false;
        }
        return this.connection.equals(that.connection);
    }

    public int hashCode() {
        return this.connection != null ? this.connection.hashCode() : super.hashCode();
    }

    @Override
    public void close() throws DatabaseException {
        Scope.getCurrentScope().getSingleton(ExecutorService.class).clearExecutor("jdbc", this);
        try (DatabaseConnection connection = this.getConnection();){
            if (connection != null && this.previousAutoCommit != null) {
                connection.setAutoCommit(this.previousAutoCommit);
            }
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Failed to restore the auto commit to " + this.previousAutoCommit);
            throw e;
        }
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return true;
    }

    @Override
    public boolean isAutoCommit() throws DatabaseException {
        return this.getConnection().getAutoCommit();
    }

    @Override
    public void setAutoCommit(boolean b) throws DatabaseException {
        this.getConnection().setAutoCommit(b);
    }

    @Override
    public boolean isSafeToRunUpdate() throws DatabaseException {
        DatabaseConnection connection = this.getConnection();
        if (connection == null) {
            return true;
        }
        String url2 = connection.getURL();
        if (url2 == null) {
            return false;
        }
        return url2.contains("localhost") || url2.contains("127.0.0.1");
    }

    @Override
    public void executeStatements(Change change, DatabaseChangeLog changeLog, List<SqlVisitor> sqlVisitors) throws LiquibaseException {
        Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).execute(change, sqlVisitors);
    }

    @Override
    public void execute(SqlStatement[] statements, List<SqlVisitor> sqlVisitors) throws LiquibaseException {
        for (SqlStatement statement : statements) {
            if (statement.skipOnUnsupported() && !SqlGeneratorFactory.getInstance().supports(statement, this)) continue;
            Scope.getCurrentScope().getLog(this.getClass()).fine("Executing Statement: " + statement);
            try {
                Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).execute(statement, sqlVisitors);
            }
            catch (DatabaseException e) {
                if (statement.continueOnError()) {
                    Scope.getCurrentScope().getLog(this.getClass()).severe("Error executing statement '" + statement + "', but continuing", e);
                    continue;
                }
                throw e;
            }
        }
    }

    @Override
    public void saveStatements(Change change, List<SqlVisitor> sqlVisitors, Writer writer) throws IOException {
        SqlStatement[] statements;
        for (SqlStatement statement : statements = change.generateStatements(this)) {
            for (Sql sql : SqlGeneratorFactory.getInstance().generateSql(statement, (Database)this)) {
                writer.append(sql.toSql()).append(sql.getEndDelimiter()).append(StreamUtil.getLineSeparator()).append(StreamUtil.getLineSeparator());
            }
        }
    }

    @Override
    public void executeRollbackStatements(SqlStatement[] statements, List<SqlVisitor> sqlVisitors) throws LiquibaseException {
        this.execute(statements, this.filterRollbackVisitors(sqlVisitors));
    }

    @Override
    public void executeRollbackStatements(Change change, List<SqlVisitor> sqlVisitors) throws LiquibaseException {
        SqlStatement[] statements = change.generateRollbackStatements(this);
        this.executeRollbackStatements(statements, sqlVisitors);
    }

    @Override
    public void saveRollbackStatement(Change change, List<SqlVisitor> sqlVisitors, Writer writer) throws IOException, LiquibaseException {
        SqlStatement[] statements;
        for (SqlStatement statement : statements = change.generateRollbackStatements(this)) {
            for (Sql sql : SqlGeneratorFactory.getInstance().generateSql(statement, (Database)this)) {
                writer.append(sql.toSql()).append(sql.getEndDelimiter()).append("\n\n");
            }
        }
    }

    protected List<SqlVisitor> filterRollbackVisitors(List<SqlVisitor> visitors) {
        ArrayList<SqlVisitor> rollbackVisitors = new ArrayList<SqlVisitor>();
        if (visitors != null) {
            for (SqlVisitor visitor : visitors) {
                if (!visitor.isApplyToRollback()) continue;
                rollbackVisitors.add(visitor);
            }
        }
        return rollbackVisitors;
    }

    @Override
    public List<DatabaseFunction> getDateFunctions() {
        return this.dateFunctions;
    }

    @Override
    public boolean isFunction(String string) {
        if (string.endsWith("()")) {
            return true;
        }
        for (DatabaseFunction function : this.getDateFunctions()) {
            if (!function.toString().equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void resetInternalState() {
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(this).reset();
        LockServiceFactory.getInstance().getLockService(this).reset();
    }

    @Override
    public boolean supportsForeignKeyDisable() {
        return false;
    }

    @Override
    public boolean disableForeignKeyChecks() throws DatabaseException {
        throw new DatabaseException("ForeignKeyChecks Management not supported");
    }

    @Override
    public void enableForeignKeyChecks() throws DatabaseException {
        throw new DatabaseException("ForeignKeyChecks Management not supported");
    }

    @Override
    public boolean createsIndexesForForeignKeys() {
        return false;
    }

    @Override
    public int getDataTypeMaxParameters(String dataTypeName) {
        return 2;
    }

    public CatalogAndSchema getSchemaFromJdbcInfo(String rawCatalogName, String rawSchemaName) {
        return new CatalogAndSchema(rawCatalogName, rawSchemaName).customize(this);
    }

    public String getJdbcCatalogName(CatalogAndSchema schema) {
        return this.correctObjectName(schema.getCatalogName(), Catalog.class);
    }

    public String getJdbcSchemaName(CatalogAndSchema schema) {
        return this.correctObjectName(schema.getSchemaName(), Schema.class);
    }

    public final String getJdbcCatalogName(Schema schema) {
        if (schema == null) {
            return this.getJdbcCatalogName(this.getDefaultSchema());
        }
        return this.getJdbcCatalogName(new CatalogAndSchema(schema.getCatalogName(), schema.getName()));
    }

    public final String getJdbcSchemaName(Schema schema) {
        if (schema == null) {
            return this.getJdbcSchemaName(this.getDefaultSchema());
        }
        return this.getJdbcSchemaName(new CatalogAndSchema(schema.getCatalogName(), schema.getName()));
    }

    @Override
    public boolean dataTypeIsNotModifiable(String typeName) {
        return this.unmodifiableDataTypes.contains(typeName.toLowerCase());
    }

    @Override
    public ObjectQuotingStrategy getObjectQuotingStrategy() {
        return this.quotingStrategy;
    }

    @Override
    public void setObjectQuotingStrategy(ObjectQuotingStrategy quotingStrategy) {
        this.quotingStrategy = quotingStrategy;
    }

    @Override
    public String generateDatabaseFunctionValue(DatabaseFunction databaseFunction) {
        if (databaseFunction.getValue() == null) {
            return null;
        }
        if (this.isCurrentTimeFunction(databaseFunction.getValue().toLowerCase())) {
            return this.getCurrentDateTimeFunction();
        }
        if (databaseFunction instanceof SequenceNextValueFunction) {
            if (this.sequenceNextValueFunction == null) {
                throw new RuntimeException(String.format("next value function for a sequence is not configured for database %s", this.getDefaultDatabaseProductName()));
            }
            String sequenceName = databaseFunction.getValue();
            String sequenceSchemaName = databaseFunction.getSchemaName();
            sequenceName = this.escapeObjectName(null, sequenceSchemaName, sequenceName, Sequence.class);
            if (this.sequenceNextValueFunction.contains("'") && !(this instanceof PostgresDatabase)) {
                sequenceName = sequenceName.replace("\"", "");
            }
            return String.format(this.sequenceNextValueFunction, sequenceName);
        }
        if (databaseFunction instanceof SequenceCurrentValueFunction) {
            if (this.sequenceCurrentValueFunction == null) {
                throw new RuntimeException(String.format("current value function for a sequence is not configured for database %s", this.getDefaultDatabaseProductName()));
            }
            String sequenceSchemaName = databaseFunction.getSchemaName();
            String sequenceName = databaseFunction.getValue();
            sequenceName = this.escapeObjectName(null, sequenceSchemaName, sequenceName, Sequence.class);
            if (this.sequenceCurrentValueFunction.contains("'") && !(this instanceof PostgresDatabase)) {
                sequenceName = sequenceName.replace("\"", "");
            }
            return String.format(this.sequenceCurrentValueFunction, sequenceName);
        }
        return databaseFunction.getValue();
    }

    protected boolean isCurrentTimeFunction(String functionValue) {
        if (functionValue == null) {
            return false;
        }
        return functionValue.startsWith("current_timestamp") || functionValue.startsWith("current_datetime") || functionValue.equalsIgnoreCase(this.getCurrentDateTimeFunction());
    }

    @Override
    public String getCurrentDateTimeFunction() {
        return this.currentDateTimeFunction;
    }

    @Override
    public void setCurrentDateTimeFunction(String function) {
        if (function != null) {
            this.currentDateTimeFunction = function;
            this.dateFunctions.add(new DatabaseFunction(function));
        }
    }

    @Override
    public boolean isDefaultSchema(String catalog, String schema) {
        if (!this.supports(Schema.class)) {
            return true;
        }
        if (!this.isDefaultCatalog(catalog)) {
            return false;
        }
        return schema == null || schema.equalsIgnoreCase(this.getDefaultSchemaName());
    }

    @Override
    public boolean isDefaultCatalog(String catalog) {
        if (!this.supports(Catalog.class)) {
            return true;
        }
        return catalog == null || catalog.equalsIgnoreCase(this.getDefaultCatalogName());
    }

    @Override
    public boolean getOutputDefaultSchema() {
        return this.outputDefaultSchema;
    }

    @Override
    public void setOutputDefaultSchema(boolean outputDefaultSchema) {
        this.outputDefaultSchema = outputDefaultSchema;
    }

    @Override
    public boolean getOutputDefaultCatalog() {
        return this.outputDefaultCatalog;
    }

    @Override
    public void setOutputDefaultCatalog(boolean outputDefaultCatalog) {
        this.outputDefaultCatalog = outputDefaultCatalog;
    }

    @Override
    public boolean supportsPrimaryKeyNames() {
        return true;
    }

    @Override
    public String getSystemSchema() {
        return "information_schema";
    }

    @Override
    public String escapeDataTypeName(String dataTypeName) {
        return dataTypeName;
    }

    @Override
    public String unescapeDataTypeName(String dataTypeName) {
        return dataTypeName;
    }

    @Override
    public String unescapeDataTypeString(String dataTypeString) {
        return dataTypeString;
    }

    public Object get(String key) {
        return this.attributes.get(key);
    }

    public AbstractJdbcDatabase set(String key, Object value) {
        if (value == null) {
            this.attributes.remove(key);
        } else {
            this.attributes.put(key, value);
        }
        return this;
    }

    @Override
    public ValidationErrors validate() {
        return new ValidationErrors();
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        if (this.getConnection() == null) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("No database connection available - specified DATETIME/TIMESTAMP precision will be tried");
        }
        return 9;
    }

    @Override
    public int getDefaultFractionalDigitsForTimestamp() {
        return this.getMaxFractionalDigitsForTimestamp() >= 6 ? 6 : this.getMaxFractionalDigitsForTimestamp();
    }

    @Override
    public boolean supportsBatchUpdates() throws DatabaseException {
        if (this.connection instanceof OfflineConnection) {
            return false;
        }
        if (this.connection instanceof JdbcConnection) {
            return ((JdbcConnection)this.getConnection()).supportsBatchUpdates();
        }
        return false;
    }

    @Override
    public boolean supportsNotNullConstraintNames() {
        return false;
    }

    @Override
    public boolean requiresExplicitNullForColumns() {
        return false;
    }

    @Override
    public CatalogAndSchema.CatalogAndSchemaCase getSchemaAndCatalogCase() {
        return CatalogAndSchema.CatalogAndSchemaCase.UPPER_CASE;
    }
}

