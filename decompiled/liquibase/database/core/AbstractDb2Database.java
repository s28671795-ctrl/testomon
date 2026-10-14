/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.OfflineConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.DateParseException;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Index;
import liquibase.structure.core.Schema;
import liquibase.util.JdbcUtil;
import liquibase.util.StringUtil;

public abstract class AbstractDb2Database
extends AbstractJdbcDatabase {
    private static final int MAX_DB2_TIMESTAMP_FRACTIONAL_DIGITS = 12;

    public AbstractDb2Database() {
        super.setCurrentDateTimeFunction("CURRENT TIMESTAMP");
        this.sequenceNextValueFunction = "NEXT VALUE FOR %s";
        this.sequenceCurrentValueFunction = "PREVIOUS VALUE FOR %s";
        this.unquotedObjectsAreUppercased = true;
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:db2")) {
            return "com.ibm.db2.jcc.DB2Driver";
        }
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public Integer getDefaultPort() {
        return 446;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        return !Schema.class.isAssignableFrom(object);
    }

    @Override
    public boolean supportsSchemas() {
        return false;
    }

    @Override
    public boolean supportsCatalogs() {
        return true;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "DB2";
    }

    @Override
    public String getDefaultCatalogName() {
        if (this.defaultCatalogName != null) {
            return this.defaultCatalogName;
        }
        if (this.defaultSchemaName != null) {
            return this.defaultSchemaName;
        }
        if (this.getConnection() == null) {
            return null;
        }
        if (this.getConnection() instanceof OfflineConnection) {
            return ((OfflineConnection)this.getConnection()).getSchema();
        }
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = ((JdbcConnection)this.getConnection()).createStatement();
            rs = stmt.executeQuery("select current schema from sysibm.sysdummy1");
            if (rs.next()) {
                String result = rs.getString(1);
                this.defaultSchemaName = result != null ? StringUtil.trimToNull(result) : StringUtil.trimToNull(super.getDefaultSchemaName());
            }
        }
        catch (Exception e) {
            try {
                throw new RuntimeException("Could not determine current schema", e);
            }
            catch (Throwable throwable) {
                JdbcUtil.close(rs, stmt);
                throw throwable;
            }
        }
        JdbcUtil.close(rs, stmt);
        return this.defaultSchemaName;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public String getDateLiteral(String isoDate) {
        String normalLiteral = super.getDateLiteral(isoDate);
        if (this.isDateOnly(isoDate)) {
            return "DATE(" + normalLiteral + ')';
        }
        if (this.isTimeOnly(isoDate)) {
            return "TIME(" + normalLiteral + ')';
        }
        if (this.isDateTime(isoDate)) {
            return "TIMESTAMP(" + normalLiteral + ')';
        }
        return "UNSUPPORTED:" + isoDate;
    }

    @Override
    public boolean supportsTablespaces() {
        return true;
    }

    @Override
    public String getViewDefinition(CatalogAndSchema schema, String viewName) throws DatabaseException {
        schema = schema.customize(this);
        String definition = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new GetViewDefinitionStatement(schema.getCatalogName(), schema.getSchemaName(), viewName), String.class);
        return "FULL_DEFINITION: " + definition;
    }

    @Override
    public Date parseDate(String dateAsString) throws DateParseException {
        try {
            if (dateAsString.indexOf(32) > 0) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateAsString);
            }
            if (dateAsString.indexOf(46) > 0 && dateAsString.indexOf(45) > 0) {
                return new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS").parse(dateAsString);
            }
            if (dateAsString.indexOf(58) > 0) {
                return new SimpleDateFormat("HH:mm:ss").parse(dateAsString);
            }
            if (dateAsString.indexOf(46) > 0) {
                return new SimpleDateFormat("HH.mm.ss").parse(dateAsString);
            }
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateAsString);
        }
        catch (ParseException e) {
            throw new DateParseException(dateAsString);
        }
    }

    @Override
    public String generatePrimaryKeyName(String tableName) {
        if (tableName.equals(this.getDatabaseChangeLogTableName())) {
            tableName = "DbChgLog".toUpperCase(Locale.US);
        } else if (tableName.equals(this.getDatabaseChangeLogLockTableName())) {
            tableName = "DbChgLogLock".toUpperCase(Locale.US);
        }
        String pkName = super.generatePrimaryKeyName(tableName);
        if (pkName.length() > 18) {
            pkName = pkName.substring(0, 17);
        }
        return pkName;
    }

    @Override
    public CatalogAndSchema getSchemaFromJdbcInfo(String rawCatalogName, String rawSchemaName) {
        if (rawCatalogName != null && rawSchemaName == null) {
            rawSchemaName = rawCatalogName;
        }
        return new CatalogAndSchema(rawSchemaName, null).customize(this);
    }

    @Override
    public String getJdbcCatalogName(CatalogAndSchema schema) {
        return null;
    }

    @Override
    public String getJdbcSchemaName(CatalogAndSchema schema) {
        return this.correctObjectName(schema.getCatalogName(), Catalog.class);
    }

    @Override
    public boolean jdbcCallsCatalogsSchemas() {
        return true;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example instanceof Index && example.getName() != null && example.getName().matches("SQL\\d+")) {
            return true;
        }
        return super.isSystemObject(example);
    }

    @Override
    protected boolean mustQuoteObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectType.isAssignableFrom(Schema.class) || objectType.isAssignableFrom(Catalog.class)) {
            return true;
        }
        return super.mustQuoteObjectName(objectName, objectType);
    }

    @Override
    public CatalogAndSchema.CatalogAndSchemaCase getSchemaAndCatalogCase() {
        return CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE;
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        try {
            if (this.getDatabaseMajorVersion() > 9 || this.getDatabaseMajorVersion() == 9 && this.getDatabaseMinorVersion() >= 7) {
                return 12;
            }
            return super.getMaxFractionalDigitsForTimestamp();
        }
        catch (Exception e) {
            return super.getMaxFractionalDigitsForTimestamp();
        }
    }
}

