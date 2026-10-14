/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import java.util.Arrays;
import java.util.Collections;
import liquibase.Scope;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.MySQLDatabase;
import liquibase.exception.DatabaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Sequence;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class MariaDBDatabase
extends MySQLDatabase {
    private static final String PRODUCT_NAME = "MariaDB";

    public MariaDBDatabase() {
        this.addReservedWords(Collections.singletonList("PERIOD"));
        this.sequenceNextValueFunction = "NEXT VALUE FOR %s";
        this.unmodifiableDataTypes.addAll(Arrays.asList("boolean", "tinyint", "smallint", "mediumint", "int", "integer", "bigint", "dec", "numeric", "fixed", "float", "bit"));
    }

    @Override
    public String getShortName() {
        return "mariadb";
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return PRODUCT_NAME;
    }

    @Override
    public String getDefaultDriver(String url2) {
        if (url2.startsWith("jdbc:mariadb")) {
            return "org.mariadb.jdbc.Driver";
        }
        return null;
    }

    @Override
    public int getMaxFractionalDigitsForTimestamp() {
        int major = 0;
        int minor = 0;
        int patch = 0;
        String productVersion = null;
        try {
            productVersion = this.getDatabaseProductVersion();
            if (productVersion != null && productVersion.toLowerCase().contains("clustrix")) {
                return 6;
            }
        }
        catch (DatabaseException databaseException) {
            // empty catch block
        }
        try {
            major = this.getDatabaseMajorVersion();
            minor = this.getDatabaseMinorVersion();
            patch = this.getDatabasePatchVersion();
        }
        catch (DatabaseException x) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Unable to determine exact database server version - specified TIMESTAMP precision will not be set: ", x);
            return 0;
        }
        String minimumVersion = "5.3.0";
        if (StringUtil.isMinimumVersion(minimumVersion, major, minor, patch)) {
            return 6;
        }
        return 0;
    }

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        if (PRODUCT_NAME.equalsIgnoreCase(conn.getDatabaseProductName())) {
            return true;
        }
        return "MYSQL".equalsIgnoreCase(conn.getDatabaseProductName()) && (conn.getDatabaseProductVersion().toLowerCase().contains("mariadb") || conn.getDatabaseProductVersion().toLowerCase().contains("clustrix"));
    }

    @Override
    protected String getMinimumVersionForFractionalDigitsForTimestamp() {
        return "5.3.0";
    }

    @Override
    public boolean supports(Class<? extends DatabaseObject> object) {
        if (Sequence.class.isAssignableFrom(object)) {
            try {
                int majorVersion = this.getDatabaseMajorVersion();
                return majorVersion > 10 || majorVersion == 10 && this.getDatabaseMinorVersion() >= 3;
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot retrieve database version", e);
                return false;
            }
        }
        return super.supports(object);
    }

    @Override
    public boolean supportsSequences() {
        try {
            int majorVersion = this.getDatabaseMajorVersion();
            return majorVersion > 10 || majorVersion == 10 && this.getDatabaseMinorVersion() >= 3;
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot retrieve database version", e);
            return false;
        }
    }

    @Override
    public boolean supportsCreateIfNotExists(Class<? extends DatabaseObject> type) {
        return type.isAssignableFrom(Table.class);
    }

    @Override
    public boolean supportsDatabaseChangeLogHistory() {
        return true;
    }
}

