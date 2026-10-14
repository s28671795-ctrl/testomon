/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.change.ColumnConfig;
import liquibase.database.Database;
import liquibase.database.core.SQLiteDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.CopyRowsStatement;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Table;

public class CopyRowsGenerator
extends AbstractSqlGenerator<CopyRowsStatement> {
    @Override
    public boolean supports(CopyRowsStatement statement, Database database) {
        return database instanceof SQLiteDatabase;
    }

    @Override
    public ValidationErrors validate(CopyRowsStatement copyRowsStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("targetTable", copyRowsStatement.getTargetTable());
        validationErrors.checkRequiredField("sourceTable", copyRowsStatement.getSourceTable());
        validationErrors.checkRequiredField("copyColumns", copyRowsStatement.getCopyColumns());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(CopyRowsStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        if (statement.getCopyColumns().isEmpty()) {
            return new Sql[0];
        }
        if (database instanceof SQLiteDatabase) {
            ColumnConfig column;
            int i;
            sql.append("INSERT INTO ").append(database.escapeTableName(null, null, statement.getTargetTable())).append(" (");
            for (i = 0; i < statement.getCopyColumns().size(); ++i) {
                column = statement.getCopyColumns().get(i);
                if (i > 0) {
                    sql.append(",");
                }
                sql.append(database.escapeColumnName(null, null, statement.getTargetTable(), column.getName()));
            }
            sql.append(") SELECT ");
            for (i = 0; i < statement.getCopyColumns().size(); ++i) {
                column = statement.getCopyColumns().get(i);
                if (i > 0) {
                    sql.append(",");
                }
                sql.append(database.escapeColumnName(null, null, statement.getSourceTable(), column.getName()));
            }
            sql.append(" FROM ").append(database.escapeTableName(null, null, statement.getSourceTable()));
        }
        return new Sql[]{new UnparsedSql(sql.toString(), this.getAffectedTable(statement))};
    }

    protected Relation getAffectedTable(CopyRowsStatement statement) {
        return new Table().setName(statement.getTargetTable()).setSchema(null, null);
    }
}

