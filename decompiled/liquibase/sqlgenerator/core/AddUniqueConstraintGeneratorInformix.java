/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddUniqueConstraintGenerator;
import liquibase.statement.core.AddUniqueConstraintStatement;

public class AddUniqueConstraintGeneratorInformix
extends AddUniqueConstraintGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(AddUniqueConstraintStatement statement, Database database) {
        return database instanceof InformixDatabase;
    }

    @Override
    public Sql[] generateSql(AddUniqueConstraintStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sqlNoConstraintNameTemplate = "ALTER TABLE %s ADD CONSTRAINT UNIQUE (%s)";
        String sqlTemplate = "ALTER TABLE %s ADD CONSTRAINT UNIQUE (%s) CONSTRAINT %s";
        String constraintName = statement.getConstraintName();
        if (constraintName == null || constraintName.startsWith(" ")) {
            return new Sql[]{new UnparsedSql(String.format("ALTER TABLE %s ADD CONSTRAINT UNIQUE (%s)", database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()), database.escapeColumnNameList(statement.getColumnNames())), this.getAffectedUniqueConstraint(statement))};
        }
        return new Sql[]{new UnparsedSql(String.format("ALTER TABLE %s ADD CONSTRAINT UNIQUE (%s) CONSTRAINT %s", database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()), database.escapeColumnNameList(statement.getColumnNames()), database.escapeConstraintName(statement.getConstraintName())), this.getAffectedUniqueConstraint(statement))};
    }
}

