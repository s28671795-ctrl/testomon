/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.database.jvm.SybaseASAConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.core.Index;

public class SybaseASADatabase
extends AbstractJdbcDatabase {
    private static final Set<String> systemTablesAndViews = new HashSet<String>();

    public SybaseASADatabase() {
        super.setCurrentDateTimeFunction("now()");
        this.unmodifiableDataTypes.addAll(Arrays.asList("integer", "bigint"));
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:sqlanywhere")) {
            return "sap.jdbc4.sqlanywhere.IDriver";
        }
        if (url2.startsWith("jdbc:sybase")) {
            return "com.sybase.jdbc4.jdbc.SybDriver";
        }
        if (url2.startsWith("jdbc:ianywhere")) {
            return "ianywhere.ml.jdbcodbc.jdbc3.IDriver";
        }
        return null;
    }

    @Override
    public Integer getDefaultPort() {
        return 2638;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Sybase Anywhere";
    }

    @Override
    public String getShortName() {
        return "asany";
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return "Adaptive Server Anywhere".equalsIgnoreCase(conn.getDatabaseProductName()) || "SQL Anywhere".equalsIgnoreCase(conn.getDatabaseProductName()) || "Adaptive Server IQ".equalsIgnoreCase(conn.getDatabaseProductName()) || "Sybase IQ".equalsIgnoreCase(conn.getDatabaseProductName());
    }

    @Override
    public String getDefaultCatalogName() {
        try {
            DatabaseConnection connection = this.getConnection();
            if (connection == null) {
                return null;
            }
            return connection.getCatalog();
        }
        catch (DatabaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    protected String getConnectionSchemaName() {
        try {
            if (this.getConnection() instanceof OfflineConnection) {
                return "dba";
            }
            Connection connection = ((JdbcConnection)this.getConnection()).getWrappedConnection();
            return connection == null ? null : connection.getMetaData().getUserName();
        }
        catch (SQLException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        return super.getViewDefinition(schema, viewName);
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return true;
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }

    @Override
    public Set<String> getSystemViews() {
        return systemTablesAndViews;
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    protected String getAutoIncrementClause() {
        return "DEFAULT AUTOINCREMENT";
    }

    @Override
    protected boolean generateAutoIncrementStartWith(BigInteger startWith) {
        return false;
    }

    @Override
    protected boolean generateAutoIncrementBy(BigInteger incrementBy) {
        return false;
    }

    @Override
    public void setAutoCommit(boolean b) throws DatabaseException {
        if (b || super.isAutoCommit()) {
            super.setAutoCommit(b);
        }
    }

    @Override
    public String getJdbcCatalogName(CatalogAndSchema schema) {
        return "";
    }

    @Override
    public String escapeIndexName(String catalogName, String schemaName, String indexName) {
        return this.escapeObjectName(indexName, Index.class);
    }

    @Override
    public void setConnection(DatabaseConnection conn) {
        DatabaseConnection dbConn = conn instanceof JdbcConnection ? new SybaseASAConnection(((JdbcConnection)conn).getWrappedConnection()) : conn;
        super.setConnection(dbConn);
    }

    @Override
    public boolean requiresExplicitNullForColumns() {
        return true;
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        return 6;
    }

    @Override
    public void afterUpdate() throws LiquibaseException {
        this.execute(new SqlStatement[]{new RawSqlStatement("sa_recompile_views(1)")}, null);
    }

    static {
        systemTablesAndViews.add("dummy");
        systemTablesAndViews.add("sysarticle");
        systemTablesAndViews.add("sysarticlecol");
        systemTablesAndViews.add("sysarticlecols");
        systemTablesAndViews.add("sysarticles");
        systemTablesAndViews.add("sysattribute");
        systemTablesAndViews.add("sysattributename");
        systemTablesAndViews.add("syscapabilities");
        systemTablesAndViews.add("syscapability");
        systemTablesAndViews.add("syscapabilityname");
        systemTablesAndViews.add("syscatalog");
        systemTablesAndViews.add("syscolauth");
        systemTablesAndViews.add("syscollation");
        systemTablesAndViews.add("syscollationmappings");
        systemTablesAndViews.add("syscolperm");
        systemTablesAndViews.add("syscolstat");
        systemTablesAndViews.add("syscolstats");
        systemTablesAndViews.add("syscolumn");
        systemTablesAndViews.add("syscolumns");
        systemTablesAndViews.add("sysdomain");
        systemTablesAndViews.add("sysevent");
        systemTablesAndViews.add("syseventtype");
        systemTablesAndViews.add("sysextent");
        systemTablesAndViews.add("sysexternlogins");
        systemTablesAndViews.add("sysfile");
        systemTablesAndViews.add("sysfkcol");
        systemTablesAndViews.add("sysforeignkey");
        systemTablesAndViews.add("sysforeignkeys");
        systemTablesAndViews.add("sysgroup");
        systemTablesAndViews.add("sysgroups");
        systemTablesAndViews.add("sysindex");
        systemTablesAndViews.add("sysindexes");
        systemTablesAndViews.add("sysinfo");
        systemTablesAndViews.add("sysixcol");
        systemTablesAndViews.add("sysjar");
        systemTablesAndViews.add("sysjarcomponent");
        systemTablesAndViews.add("sysjavaclass");
        systemTablesAndViews.add("syslogin");
        systemTablesAndViews.add("sysoptblock");
        systemTablesAndViews.add("sysoption");
        systemTablesAndViews.add("sysoptions");
        systemTablesAndViews.add("sysoptjoinstrategy");
        systemTablesAndViews.add("sysoptorder");
        systemTablesAndViews.add("sysoptorders");
        systemTablesAndViews.add("sysoptplans");
        systemTablesAndViews.add("sysoptquantifier");
        systemTablesAndViews.add("sysoptrequest");
        systemTablesAndViews.add("sysoptrewrite");
        systemTablesAndViews.add("sysoptstat");
        systemTablesAndViews.add("sysoptstrategies");
        systemTablesAndViews.add("sysprocauth");
        systemTablesAndViews.add("sysprocedure");
        systemTablesAndViews.add("sysprocparm");
        systemTablesAndViews.add("sysprocparms");
        systemTablesAndViews.add("sysprocperm");
        systemTablesAndViews.add("syspublication");
        systemTablesAndViews.add("syspublications");
        systemTablesAndViews.add("sysremoteoption");
        systemTablesAndViews.add("sysremoteoptions");
        systemTablesAndViews.add("sysremoteoptiontype");
        systemTablesAndViews.add("sysremotetype");
        systemTablesAndViews.add("sysremotetypes");
        systemTablesAndViews.add("sysremoteuser");
        systemTablesAndViews.add("sysremoteusers");
        systemTablesAndViews.add("sysschedule");
        systemTablesAndViews.add("sysservers");
        systemTablesAndViews.add("syssqlservertype");
        systemTablesAndViews.add("syssubscription");
        systemTablesAndViews.add("syssubscriptions");
        systemTablesAndViews.add("syssync");
        systemTablesAndViews.add("syssyncdefinitions");
        systemTablesAndViews.add("syssyncpublicationdefaults");
        systemTablesAndViews.add("syssyncs");
        systemTablesAndViews.add("syssyncsites");
        systemTablesAndViews.add("syssyncsubscriptions");
        systemTablesAndViews.add("syssynctemplates");
        systemTablesAndViews.add("syssyncusers");
        systemTablesAndViews.add("systabauth");
        systemTablesAndViews.add("systable");
        systemTablesAndViews.add("systableperm");
        systemTablesAndViews.add("systrigger");
        systemTablesAndViews.add("systriggers");
        systemTablesAndViews.add("systypemap");
        systemTablesAndViews.add("sysuserauth");
        systemTablesAndViews.add("sysuserlist");
        systemTablesAndViews.add("sysusermessages");
        systemTablesAndViews.add("sysuseroptions");
        systemTablesAndViews.add("sysuserperm");
        systemTablesAndViews.add("sysuserperms");
        systemTablesAndViews.add("sysusertype");
        systemTablesAndViews.add("sysviews");
    }
}

