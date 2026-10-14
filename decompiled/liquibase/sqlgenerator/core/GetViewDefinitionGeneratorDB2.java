/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.CatalogAndSchema;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.GetViewDefinitionGenerator;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.structure.DatabaseObject;

public class GetViewDefinitionGeneratorDB2
extends GetViewDefinitionGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(GetViewDefinitionStatement statement, Database database) {
        return database instanceof AbstractDb2Database;
    }

    @Override
    public Sql[] generateSql(GetViewDefinitionStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        CatalogAndSchema schema = new CatalogAndSchema(statement.getCatalogName(), statement.getSchemaName()).customize(database);
        if (database instanceof Db2zDatabase) {
            return new Sql[]{new UnparsedSql("select STATEMENT AS view_definition from SYSIBM.SYSVIEWS where NAME='" + statement.getViewName() + "' and CREATOR = '" + schema.getSchemaName() + "'", new DatabaseObject[0])};
        }
        return new Sql[]{new UnparsedSql("select view_definition from SYSIBM.VIEWS where TABLE_NAME='" + statement.getViewName() + "' and TABLE_SCHEMA='" + schema.getSchemaName() + "'", new DatabaseObject[0])};
    }
}

