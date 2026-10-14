/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.util.Locale;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.DatabaseConnection;
import liquibase.exception.DatabaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

public class FirebirdDatabase
extends AbstractJdbcDatabase {
    public FirebirdDatabase() {
        super.setCurrentDateTimeFunction("CURRENT_TIMESTAMP");
        this.sequenceNextValueFunction = "NEXT VALUE FOR %s";
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return conn.getDatabaseProductName().startsWith("Firebird");
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:firebirdsql")) {
            return "org.firebirdsql.jdbc.FBDriver";
        }
        return null;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getShortName() {
        return "firebird";
    }

    @Override
    public Integer getDefaultPort() {
        return 3050;
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "Firebird";
    }

    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public boolean supportsInitiallyDeferrableColumns() {
        return false;
    }

    @Override
    public boolean supportsTablespaces() {
        return false;
    }

    @Override
    public boolean supportsDDLInTransaction() {
        return false;
    }

    @Override
    public boolean isSystemObject(DatabaseObject example) {
        if (example instanceof Table && example.getName().startsWith("RDB$")) {
            return true;
        }
        return super.isSystemObject(example);
    }

    @Override
    public boolean supportsAutoIncrement() {
        return false;
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Schema.class.isAssignableFrom(object)) {
            return false;
        }
        if (Catalog.class.isAssignableFrom(object)) {
            return false;
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsSchemas() {
        return false;
    }

    @Override
    public boolean supportsCatalogs() {
        return false;
    }

    @Override
    public boolean supportsDropTableCascadeConstraints() {
        return false;
    }

    @Override
    public String correctObjectName(String objectName, Class<? extends DatabaseObject> objectType) {
        if (objectName == null) {
            return null;
        }
        return objectName.toUpperCase(Locale.US).trim();
    }

    @Override
    public boolean createsIndexesForForeignKeys() {
        return true;
    }

    @Override
    public String getConnectionCatalogName() {
        return "DEFAULT";
    }
}

