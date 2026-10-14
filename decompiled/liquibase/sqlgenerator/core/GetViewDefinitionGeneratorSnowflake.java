/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.CatalogAndSchema;
import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.GetViewDefinitionGenerator;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.structure.DatabaseObject;

public class GetViewDefinitionGeneratorSnowflake
extends GetViewDefinitionGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(GetViewDefinitionStatement statement, Database database) {
        return database instanceof SnowflakeDatabase;
    }

    @Override
    public Sql[] generateSql(GetViewDefinitionStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        CatalogAndSchema schema = new CatalogAndSchema(statement.getCatalogName(), statement.getSchemaName()).customize(database);
        return new Sql[]{new UnparsedSql("SELECT GET_DDL('VIEW', '" + schema.getCatalogName() + "." + schema.getSchemaName() + "." + statement.getViewName() + "', TRUE)", new DatabaseObject[0])};
    }
}

