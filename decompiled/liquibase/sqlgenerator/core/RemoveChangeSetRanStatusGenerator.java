/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.column.LiquibaseColumn;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DeleteStatement;
import liquibase.statement.core.RemoveChangeSetRanStatusStatement;

public class RemoveChangeSetRanStatusGenerator
extends AbstractSqlGenerator<RemoveChangeSetRanStatusStatement> {
    @Override
    public ValidationErrors validate(RemoveChangeSetRanStatusStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors errors2 = new ValidationErrors();
        errors2.checkRequiredField("changeSet", statement.getChangeSet());
        return errors2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(RemoveChangeSetRanStatusStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ChangeSet changeSet = statement.getChangeSet();
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            Sql[] sqlArray = SqlGeneratorFactory.getInstance().generateSql(new DeleteStatement(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogTableName()).setWhere(database.escapeObjectName("ID", LiquibaseColumn.class) + " = ? AND " + database.escapeObjectName("AUTHOR", LiquibaseColumn.class) + " = ? AND " + database.escapeObjectName("FILENAME", LiquibaseColumn.class) + " = ?").addWhereParameters(changeSet.getId(), changeSet.getAuthor(), changeSet.getStoredFilePath()), database);
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }
}

