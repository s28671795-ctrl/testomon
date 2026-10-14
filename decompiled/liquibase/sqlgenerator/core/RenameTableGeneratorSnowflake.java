/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.RenameTableGenerator;
import liquibase.statement.core.RenameTableStatement;

public class RenameTableGeneratorSnowflake
extends RenameTableGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(RenameTableStatement statement, Database database) {
        return database instanceof SnowflakeDatabase;
    }

    @Override
    public Sql[] generateSql(RenameTableStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new Sql[]{new UnparsedSql("ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldTableName()) + " RENAME TO " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getNewTableName()), this.getAffectedOldTable(statement), this.getAffectedNewTable(statement))};
    }
}

