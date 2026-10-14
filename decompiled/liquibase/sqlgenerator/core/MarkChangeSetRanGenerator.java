/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.ChecksumVersion;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.core.TagDatabaseChange;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.column.LiquibaseColumn;
import liquibase.database.Database;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.core.InsertStatement;
import liquibase.statement.core.MarkChangeSetRanStatement;
import liquibase.statement.core.UpdateStatement;
import liquibase.util.LiquibaseUtil;
import liquibase.util.StringUtil;

public class MarkChangeSetRanGenerator
extends AbstractSqlGenerator<MarkChangeSetRanStatement> {
    private static final String COMMENTS = "COMMENTS";
    private static final String CONTEXTS = "CONTEXTS";
    private static final String LABELS = "LABELS";

    @Override
    public ValidationErrors validate(MarkChangeSetRanStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("changeSet", statement.getChangeSet());
        return validationErrors;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Sql[] generateSql(MarkChangeSetRanStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String dateValue = database.getCurrentDateTimeFunction();
        ChangeSet changeSet = statement.getChangeSet();
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            AbstractSqlStatement runStatement;
            if (statement.getExecType().equals((Object)ChangeSet.ExecType.FAILED) || statement.getExecType().equals((Object)ChangeSet.ExecType.SKIPPED)) {
                Sql[] sqlArray = EMPTY_SQL;
                return sqlArray;
            }
            try {
                String tag = MarkChangeSetRanGenerator.getTagFromChangeset(changeSet);
                int orderExecuted = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database).getNextSequenceValue();
                DatabaseFunction dateExecuted = new DatabaseFunction(dateValue);
                String liquibaseVersion = MarkChangeSetRanGenerator.getLiquibaseBuildVersion();
                String description = StringUtil.limitSize(changeSet.getDescription(), 250);
                String md5Sum = changeSet.generateCheckSum(ChecksumVersion.latest()).toString();
                String execType = statement.getExecType().value;
                String deploymentId = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database).getDeploymentId();
                if (statement.getExecType().ranBefore) {
                    runStatement = new UpdateStatement(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogTableName()).addNewColumnValue("DATEEXECUTED", dateExecuted).addNewColumnValue("ORDEREXECUTED", orderExecuted).addNewColumnValue("MD5SUM", md5Sum).addNewColumnValue("EXECTYPE", execType).addNewColumnValue("DEPLOYMENT_ID", deploymentId).addNewColumnValue(COMMENTS, this.getCommentsColumn(changeSet)).addNewColumnValue(CONTEXTS, this.getContextsColumn(changeSet)).addNewColumnValue(LABELS, this.getLabelsColumn(changeSet)).addNewColumnValue("LIQUIBASE", liquibaseVersion).addNewColumnValue("DESCRIPTION", description).setWhereClause(database.escapeObjectName("ID", LiquibaseColumn.class) + " = ? AND " + database.escapeObjectName("AUTHOR", LiquibaseColumn.class) + " = ? AND " + database.escapeObjectName("FILENAME", LiquibaseColumn.class) + " = ?").addWhereParameters(changeSet.getId(), changeSet.getAuthor(), changeSet.getFilePath());
                    if (tag != null) {
                        ((UpdateStatement)runStatement).addNewColumnValue("TAG", tag);
                    }
                } else {
                    runStatement = new InsertStatement(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), database.getDatabaseChangeLogTableName()).addColumnValue("ID", changeSet.getId()).addColumnValue("AUTHOR", changeSet.getAuthor()).addColumnValue("FILENAME", changeSet.getFilePath()).addColumnValue("DATEEXECUTED", dateExecuted).addColumnValue("ORDEREXECUTED", orderExecuted).addColumnValue("MD5SUM", md5Sum).addColumnValue("DESCRIPTION", description).addColumnValue(COMMENTS, this.getCommentsColumn(changeSet)).addColumnValue("EXECTYPE", execType).addColumnValue(CONTEXTS, this.getContextsColumn(changeSet)).addColumnValue(LABELS, this.getLabelsColumn(changeSet)).addColumnValue("LIQUIBASE", liquibaseVersion).addColumnValue("DEPLOYMENT_ID", deploymentId);
                    if (tag != null) {
                        runStatement.addColumnValue("TAG", tag);
                    }
                }
            }
            catch (LiquibaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
            Sql[] sqlArray = SqlGeneratorFactory.getInstance().generateSql(runStatement, database);
            return sqlArray;
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    public static String getTagFromChangeset(ChangeSet changeSet) {
        if (changeSet != null) {
            for (Change change : changeSet.getChanges()) {
                if (!(change instanceof TagDatabaseChange)) continue;
                TagDatabaseChange tagChange = (TagDatabaseChange)change;
                return tagChange.getTag();
            }
        }
        return null;
    }

    public static String getLiquibaseBuildVersion() {
        return StringUtil.limitSize(LiquibaseUtil.getBuildVersion().replace("SNAPSHOT", "SNP").replace("beta", "b").replace("alpha", "b"), 20);
    }

    private String getCommentsColumn(ChangeSet changeSet) {
        return StringUtil.limitSize(StringUtil.trimToEmpty(changeSet.getComments()), 250);
    }

    protected String getContextsColumn(ChangeSet changeSet) {
        return changeSet.buildFullContext();
    }

    protected String getLabelsColumn(ChangeSet changeSet) {
        return changeSet.buildFullLabels();
    }
}

