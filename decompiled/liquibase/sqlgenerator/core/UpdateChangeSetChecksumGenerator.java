/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.ChecksumVersion;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.column.LiquibaseColumn;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.UpdateChangeSetChecksumStatement;
import liquibase.statement.core.UpdateStatement;
import liquibase.util.StringUtil;

public class UpdateChangeSetChecksumGenerator
extends AbstractSqlGenerator<UpdateChangeSetChecksumStatement> {
    @Override
    public ValidationErrors validate(UpdateChangeSetChecksumStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("changeSet", statement.getChangeSet());
        return validationErrors;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(UpdateChangeSetChecksumStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ChangeSet changeSet = statement.getChangeSet();
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            UpdateStatement runStatement = null;
            runStatement = new UpdateStatement(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogTableName()).addNewColumnValue("MD5SUM", changeSet.generateCheckSum(ChecksumVersion.latest()).toString()).setWhereClause(database.escapeObjectName("ID", LiquibaseColumn.class) + " = ? AND " + database.escapeObjectName("AUTHOR", LiquibaseColumn.class) + " = ? AND " + database.escapeObjectName("FILENAME", LiquibaseColumn.class) + " = ?").addWhereParameters(changeSet.getId(), changeSet.getAuthor(), this.getFilePath(changeSet));
            Sql[] sqlArray = SqlGeneratorFactory.getInstance().generateSql(runStatement, database);
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    private String getFilePath(ChangeSet changeSet) {
        if (StringUtil.isNotEmpty(changeSet.getStoredFilePath())) {
            return changeSet.getStoredFilePath();
        }
        return changeSet.getFilePath();
    }
}

