/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.core.MSSQLDatabase;
import liquibase.datatype.DataTypeFactory;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.NotNullConstraint;
import liquibase.statement.core.CreateDatabaseChangeLogLockTableStatement;
import liquibase.statement.core.CreateTableStatement;

public class CreateDatabaseChangeLogLockTableGenerator
extends AbstractSqlGenerator<CreateDatabaseChangeLogLockTableStatement> {
    @Override
    public ValidationErrors validate(CreateDatabaseChangeLogLockTableStatement createDatabaseChangeLogLockTableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(CreateDatabaseChangeLogLockTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String charTypeName = this.getCharTypeName(database);
        String dateTimeTypeString = this.getDateTimeTypeString(database);
        CreateTableStatement createTableStatement = new CreateTableStatement(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogLockTableName()).setTablespace(database.getLiquibaseTablespaceName()).addPrimaryKeyColumn("ID", DataTypeFactory.getInstance().fromDescription("int", database), null, null, null, new NotNullConstraint()).addColumn("LOCKED", DataTypeFactory.getInstance().fromDescription("boolean", database), null, null, new NotNullConstraint()).addColumn("LOCKGRANTED", DataTypeFactory.getInstance().fromDescription(dateTimeTypeString, database)).addColumn("LOCKEDBY", DataTypeFactory.getInstance().fromDescription(charTypeName + "(255)", database));
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            Sql[] sqlArray = SqlGeneratorFactory.getInstance().generateSql(createTableStatement, database);
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    protected String getCharTypeName(Database database) {
        if (database instanceof MSSQLDatabase && ((MSSQLDatabase)database).sendsStringParametersAsUnicode()) {
            return "nvarchar";
        }
        return "varchar";
    }

    protected String getDateTimeTypeString(Database database) {
        if (database instanceof MSSQLDatabase) {
            return "datetime2(3)";
        }
        return "datetime";
    }
}

