/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.GetViewDefinitionGenerator;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.structure.DatabaseObject;

public class GetViewDefinitionGeneratorFirebird
extends GetViewDefinitionGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(GetViewDefinitionStatement statement, Database database) {
        return database instanceof FirebirdDatabase;
    }

    @Override
    public Sql[] generateSql(GetViewDefinitionStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new Sql[]{new UnparsedSql("select rdb$view_source from rdb$relations where upper(rdb$relation_name)='" + statement.getViewName() + "'", new DatabaseObject[0])};
    }
}

