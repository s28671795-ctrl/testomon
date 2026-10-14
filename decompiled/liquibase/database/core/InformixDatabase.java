/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Table;

public class InformixDatabase
extends AbstractJdbcDatabase {
    private static final String PRODUCT_NAME = "Informix Dynamic Server";
    private static final String PRODUCT_NAME_DB2JCC_PREFIX = "IDS";
    private static final String TIME_FIELD_QUALIFIER = "HOUR TO FRACTION(5)";
    private static final String DATETIME_FIELD_QUALIFIER = "YEAR TO FRACTION(5)";
    private final Set<String> systemTablesAndViews = new HashSet<String>();
    private static final String CREATE_VIEW_AS_REGEX = "^CREATE\\s+.*?VIEW\\s+.*?AS\\s+";
    private static final Pattern CREATE_VIEW_AS_PATTERN = Pattern.compile("^CREATE\\s+.*?VIEW\\s+.*?AS\\s+", 34);

    public InformixDatabase() {
        super.setCurrentDateTimeFunction("CURRENT YEAR TO FRACTION(5)");
        this.sequenceNextValueFunction = "%s.NEXTVAL";
        this.systemTablesAndViews.add("systables");
        this.systemTablesAndViews.add("syscolumns");
        this.systemTablesAndViews.add("sysindices");
        this.systemTablesAndViews.add("systabauth");
        this.systemTablesAndViews.add("syscolauth");
        this.systemTablesAndViews.add("sysviews");
        this.systemTablesAndViews.add("sysusers");
        this.systemTablesAndViews.add("sysdepend");
        this.systemTablesAndViews.add("syssynonyms");
        this.systemTablesAndViews.add("syssyntable");
        this.systemTablesAndViews.add("sysconstraints");
        this.systemTablesAndViews.add("sysreferences");
        this.systemTablesAndViews.add("syschecks");
        this.systemTablesAndViews.add("sysdefaults");
        this.systemTablesAndViews.add("syscoldepend");
        this.systemTablesAndViews.add("sysprocedures");
        this.systemTablesAndViews.add("sysprocbody");
        this.systemTablesAndViews.add("sysprocplan");
        this.systemTablesAndViews.add("sysprocauth");
        this.systemTablesAndViews.add("sysblobs");
        this.systemTablesAndViews.add("sysopclstr");
        this.systemTablesAndViews.add("systriggers");
        this.systemTablesAndViews.add("systrigbody");
        this.systemTablesAndViews.add("sysdistrib");
        this.systemTablesAndViews.add("sysfragments");
        this.systemTablesAndViews.add("sysobjstate");
        this.systemTablesAndViews.add("sysviolations");
        this.systemTablesAndViews.add("sysfragauth");
        this.systemTablesAndViews.add("sysroleauth");
        this.systemTablesAndViews.add("sysxtdtypes");
        this.systemTablesAndViews.add("sysattrtypes");
        this.systemTablesAndViews.add("sysxtddesc");
        this.systemTablesAndViews.add("sysinherits");
        this.systemTablesAndViews.add("syscolattribs");
        this.systemTablesAndViews.add("syslogmap");
        this.systemTablesAndViews.add("syscasts");
        this.systemTablesAndViews.add("sysxtdtypeauth");
        this.systemTablesAndViews.add("sysroutinelangs");
        this.systemTablesAndViews.add("syslangauth");
        this.systemTablesAndViews.add("sysams");
        this.systemTablesAndViews.add("systabamdata");
        this.systemTablesAndViews.add("sysopclasses");
        this.systemTablesAndViews.add("syserrors");
        this.systemTablesAndViews.add("systraceclasses");
        this.systemTablesAndViews.add("systracemsgs");
        this.systemTablesAndViews.add("sysaggregates");
        this.systemTablesAndViews.add("syssequences");
        this.systemTablesAndViews.add("sysdirectives");
        this.systemTablesAndViews.add("sysxasourcetypes");
        this.systemTablesAndViews.add("sysxadatasources");
        this.systemTablesAndViews.add("sysseclabelcomponents");
        this.systemTablesAndViews.add("sysseclabelcomponentelements");
        this.systemTablesAndViews.add("syssecpolicies");
        this.systemTablesAndViews.add("syssecpolicycomponents");
        this.systemTablesAndViews.add("syssecpolicyexemptions");
        this.systemTablesAndViews.add("sysseclabels");
        this.systemTablesAndViews.add("sysseclabelnames");
        this.systemTablesAndViews.add("sysseclabelauth");
        this.systemTablesAndViews.add("syssurrogateauth");
        this.systemTablesAndViews.add("sysproccolumns");
        this.systemTablesAndViews.add("sysdomains");
        this.systemTablesAndViews.add("sysindexes");
        this.sequenceNextValueFunction = "%s.NEXTVAL";
        this.sequenceCurrentValueFunction = "%s.CURRVAL";
    }

    @Override
    protected Set<String> getSystemViews() {
        return this.systemTablesAndViews;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Informix";
    }

    @Override
    public Integer getDefaultPort() {
        return 1526;
    }

    @Override
    public void setConnection(DatabaseConnection connection) {
        super.setConnection(connection);
        if (!(connection instanceof OfflineConnection)) {
            try {
                Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).execute(new RawSqlStatement("EXECUTE PROCEDURE IFX_ALLOW_NEWLINE('T');"));
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException("Could not allow newline characters in quoted strings with IFX_ALLOW_NEWLINE", e);
            }
        }
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:informix-sqli")) {
            return "com.informix.jdbc.IfxDriver";
        }
        return null;
    }

    @Override
    public String getShortName() {
        return "informix";
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        boolean correct = false;
        String name = conn.getDatabaseProductName();
        if (name != null && (name.equals(PRODUCT_NAME) || name.startsWith(PRODUCT_NAME_DB2JCC_PREFIX))) {
            correct = true;
        }
        return correct;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        schema = schema.customize(this);
        List<Map<String, ?>> retList = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForList(new GetViewDefinitionStatement(schema.getCatalogName(), schema.getSchemaName(), viewName));
        StringBuilder sb = new StringBuilder();
        for (Map<String, ?> rowMap : retList) {
            String s2 = (String)rowMap.get("VIEWTEXT");
            sb.append(s2);
        }
        return CREATE_VIEW_AS_PATTERN.matcher(sb.toString()).replaceFirst("");
    }

    @Override
    public String getAutoIncrementClause(BigInteger startWith, BigInteger incrementBy, String generationType, Boolean defaultOnNull) {
        return "";
    }

    @Override
    public String getDateLiteral(String isoDate) {
        if (this.isTimeOnly(isoDate)) {
            return "DATETIME (" + super.getDateLiteral(isoDate).replace("'", "") + ") " + TIME_FIELD_QUALIFIER;
        }
        if (this.isDateOnly(isoDate)) {
            return super.getDateLiteral(isoDate);
        }
        return "DATETIME (" + super.getDateLiteral(isoDate).replace("'", "") + ") " + DATETIME_FIELD_QUALIFIER;
    }

    @Override
    public boolean supportsRestrictForeignKeys() {
        return false;
    }

    @Override
    public String escapeObjectName(String catalogName, String schemaName, String objectName, Class<? extends DatabaseObject> objectType) {
        String name = super.escapeObjectName(catalogName, schemaName, objectName, objectType);
        if (name == null) {
            return null;
        }
        if (name.matches(".*\\..*\\..*")) {
            name = name.replaceFirst("\\.", ":");
        }
        return name;
    }

    @Override
    public String getSystemSchema() {
        return "informix";
    }

    @Override
    public String quoteObject(String objectName, Class<? extends DatabaseObject> objectType) {
        return objectName;
    }

    @Override
    protected String getConnectionSchemaName() {
        if (this.getConnection() == null || this.getConnection() instanceof OfflineConnection) {
            return null;
        }
        try {
            String schemaName = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new RawSqlStatement("select username from sysmaster:informix.syssessions where sid = dbinfo('sessionid')"), String.class);
            if (schemaName != null) {
                return schemaName.trim();
            }
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Error getting connection schema", e);
        }
        return null;
    }

    @Override
    public boolean supportsCatalogInObjectName(Class<? extends DatabaseObject> type) {
        return true;
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return type.isAssignableFrom(Table.class);
    }
}

