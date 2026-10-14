/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database.core;

import liquibase.Scope;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.database.core.AbstractDb2Database;
import liquibase.exception.DatabaseException;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class DB2Database
extends AbstractDb2Database {
    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        return conn.getDatabaseProductName().startsWith("DB2") && !StringUtil.startsWith(conn.getDatabaseProductVersion(), "DSN");
    }

    @Override
    public String getShortName() {
        return "db2";
    }

    public boolean supportsBooleanDataType() {
        if (this.getConnection() == null) {
            return false;
        }
        try {
            Integer fixPack = this.getDb2FixPack();
            if (fixPack == null) {
                throw new DatabaseException("Error getting fix pack number");
            }
            return this.getDatabaseMajorVersion() > 11 || this.getDatabaseMajorVersion() == 11 && (this.getDatabaseMinorVersion() == 1 && fixPack >= 1 || this.getDatabaseMinorVersion() > 1);
        }
        catch (DatabaseException e) {
            return false;
        }
    }

    private Integer getDb2FixPack() {
        if (this.getConnection() == null || this.getConnection() instanceof OfflineConnection) {
            return null;
        }
        try {
            return Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this).queryForObject(new RawSqlStatement("SELECT fixpack_num FROM TABLE (sysproc.env_get_inst_info()) as INSTANCEINFO"), Integer.class);
        }
        catch (Exception e) {
            Scope.getCurrentScope().getLog(this.getClass()).info("Error getting fix pack number", e);
            return null;
        }
    }

    @Override
    protected String getDefaultDatabaseProductName() {
        return "DB2/LUW";
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

