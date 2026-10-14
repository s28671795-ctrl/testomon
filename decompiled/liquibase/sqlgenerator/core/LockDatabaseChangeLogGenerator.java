/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.core.LockDatabaseChangeLogStatement;
import liquibase.statement.core.UpdateStatement;
import liquibase.util.NetUtil;

public class LockDatabaseChangeLogGenerator
extends AbstractSqlGenerator<LockDatabaseChangeLogStatement> {
    protected static final String hostname;
    protected static final String hostaddress;
    protected static final String hostDescription;

    @Override
    public ValidationErrors validate(LockDatabaseChangeLogStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(LockDatabaseChangeLogStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String liquibaseSchema = database.getLiquibaseSchemaName();
        String liquibaseCatalog = database.getLiquibaseCatalogName();
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            String dateValue = database.getCurrentDateTimeFunction();
            UpdateStatement updateStatement = new UpdateStatement(liquibaseCatalog, liquibaseSchema, database.getDatabaseChangeLogLockTableName());
            updateStatement.addNewColumnValue("LOCKED", true);
            updateStatement.addNewColumnValue("LOCKGRANTED", new DatabaseFunction(dateValue));
            updateStatement.addNewColumnValue("LOCKEDBY", hostname + hostDescription + " (" + hostaddress + ")");
            updateStatement.setWhereClause(database.escapeColumnName(liquibaseCatalog, liquibaseSchema, database.getDatabaseChangeLogTableName(), "ID") + " = 1 AND " + database.escapeColumnName(liquibaseCatalog, liquibaseSchema, database.getDatabaseChangeLogTableName(), "LOCKED") + " = " + DataTypeFactory.getInstance().fromDescription("boolean", database).objectToSql(false, database));
            Sql[] sqlArray = SqlGeneratorFactory.getInstance().generateSql(updateStatement, database);
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    static {
        hostDescription = System.getProperty("liquibase.hostDescription") == null ? "" : "#" + System.getProperty("liquibase.hostDescription");
        try {
            hostname = NetUtil.getLocalHostName();
            hostaddress = NetUtil.getLocalHostAddress();
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }
}

