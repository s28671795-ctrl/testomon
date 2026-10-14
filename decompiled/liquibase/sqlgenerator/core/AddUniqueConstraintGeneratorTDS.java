/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddUniqueConstraintGenerator;
import liquibase.statement.core.AddUniqueConstraintStatement;

public class AddUniqueConstraintGeneratorTDS
extends AddUniqueConstraintGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(AddUniqueConstraintStatement statement, Database database) {
        return database instanceof SybaseDatabase || database instanceof SybaseASADatabase;
    }

    @Override
    public Sql[] generateSql(AddUniqueConstraintStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sqlTemplate = "ALTER TABLE %s ADD CONSTRAINT %s UNIQUE (%s)";
        String sqlNoConstraintNameTemplate = "ALTER TABLE %s ADD UNIQUE (%s)";
        if (statement.getConstraintName() == null) {
            return new Sql[]{new UnparsedSql(String.format("ALTER TABLE %s ADD UNIQUE (%s)", database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()), database.escapeColumnNameList(statement.getColumnNames())), this.getAffectedUniqueConstraint(statement))};
        }
        return new Sql[]{new UnparsedSql(String.format("ALTER TABLE %s ADD CONSTRAINT %s UNIQUE (%s)", database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()), database.escapeConstraintName(statement.getConstraintName()), database.escapeColumnNameList(statement.getColumnNames())), this.getAffectedUniqueConstraint(statement))};
    }
}

