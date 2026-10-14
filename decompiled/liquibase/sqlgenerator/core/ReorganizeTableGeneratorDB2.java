/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.GlobalConfiguration;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.ReorganizeTableStatement;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;

public class ReorganizeTableGeneratorDB2
extends AbstractSqlGenerator<ReorganizeTableStatement> {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(ReorganizeTableStatement statement, Database database) {
        return database instanceof DB2Database;
    }

    @Override
    public ValidationErrors validate(ReorganizeTableStatement reorganizeTableStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", reorganizeTableStatement.getTableName());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(ReorganizeTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        if (!GlobalConfiguration.AUTO_REORG.getCurrentValue().booleanValue()) {
            return null;
        }
        try {
            if (database.getDatabaseMajorVersion() >= 9) {
                return new Sql[]{new UnparsedSql("CALL SYSPROC.ADMIN_CMD ('REORG TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + "')", this.getAffectedTable(statement))};
            }
            return null;
        }
        catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    protected Relation getAffectedTable(ReorganizeTableStatement statement) {
        return new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }
}

