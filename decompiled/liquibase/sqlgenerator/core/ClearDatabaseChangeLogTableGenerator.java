/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.ClearDatabaseChangeLogTableStatement;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;
import liquibase.util.StringUtil;

public class ClearDatabaseChangeLogTableGenerator
extends AbstractSqlGenerator<ClearDatabaseChangeLogTableStatement> {
    @Override
    public ValidationErrors validate(ClearDatabaseChangeLogTableStatement clearDatabaseChangeLogTableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new ValidationErrors();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(ClearDatabaseChangeLogTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String schemaName = StringUtil.isNotEmpty(statement.getSchemaName()) ? statement.getSchemaName() : database.getLiquibaseSchemaName();
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            Sql[] sqlArray = new Sql[]{new UnparsedSql("DELETE FROM " + database.escapeTableName(database.getLiquibaseCatalogName(), schemaName, database.getDatabaseChangeLogTableName()), this.getAffectedTable(database, schemaName))};
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    protected Relation getAffectedTable(Database database, String schemaName) {
        return new Table().setName(database.getDatabaseChangeLogTableName()).setSchema(database.getLiquibaseCatalogName(), schemaName);
    }
}

