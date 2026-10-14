/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddPrimaryKeyGenerator;
import liquibase.statement.core.AddPrimaryKeyStatement;

public class AddPrimaryKeyGeneratorInformix
extends AddPrimaryKeyGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(AddPrimaryKeyStatement statement, Database database) {
        return database instanceof InformixDatabase;
    }

    @Override
    public Sql[] generateSql(AddPrimaryKeyStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder sql = new StringBuilder();
        sql.append("ALTER TABLE ");
        sql.append(database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()));
        sql.append(" ADD CONSTRAINT PRIMARY KEY (");
        sql.append(database.escapeColumnNameList(statement.getColumnNames()));
        sql.append(")");
        String constraintName = statement.getConstraintName();
        if (constraintName != null && !constraintName.matches("[urcn][0-9]+_[0-9]+")) {
            sql.append(" CONSTRAINT ");
            sql.append(database.escapeConstraintName(constraintName));
        }
        return new Sql[]{new UnparsedSql(sql.toString(), this.getAffectedPrimaryKey(statement))};
    }
}

