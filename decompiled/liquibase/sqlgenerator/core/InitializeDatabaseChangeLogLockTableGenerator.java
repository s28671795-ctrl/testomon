/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.ArrayList;
import java.util.Arrays;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DeleteStatement;
import liquibase.statement.core.InitializeDatabaseChangeLogLockTableStatement;
import liquibase.statement.core.InsertStatement;

public class InitializeDatabaseChangeLogLockTableGenerator
extends AbstractSqlGenerator<InitializeDatabaseChangeLogLockTableStatement> {
    @Override
    public ValidationErrors validate(InitializeDatabaseChangeLogLockTableStatement initializeDatabaseChangeLogLockTableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(InitializeDatabaseChangeLogLockTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            DeleteStatement deleteStatement = new DeleteStatement(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogLockTableName());
            InsertStatement insertStatement = new InsertStatement(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogLockTableName()).addColumnValue("ID", 1).addColumnValue("LOCKED", Boolean.FALSE);
            ArrayList<Sql> sql = new ArrayList<Sql>();
            sql.addAll(Arrays.asList(SqlGeneratorFactory.getInstance().generateSql(deleteStatement, database)));
            sql.addAll(Arrays.asList(SqlGeneratorFactory.getInstance().generateSql(insertStatement, database)));
            Sql[] sqlArray = sql.toArray(EMPTY_SQL);
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }
}

