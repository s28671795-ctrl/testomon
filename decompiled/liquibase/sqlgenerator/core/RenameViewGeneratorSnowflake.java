/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.RenameViewGenerator;
import liquibase.statement.core.RenameViewStatement;

public class RenameViewGeneratorSnowflake
extends RenameViewGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(RenameViewStatement statement, Database database) {
        return database instanceof SnowflakeDatabase;
    }

    @Override
    public Sql[] generateSql(RenameViewStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new Sql[]{new UnparsedSql("ALTER VIEW " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getOldViewName()) + " RENAME TO " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getNewViewName()), this.getAffectedOldView(statement), this.getAffectedNewView(statement))};
    }
}

