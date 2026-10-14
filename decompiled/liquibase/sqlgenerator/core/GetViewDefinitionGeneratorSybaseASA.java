/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.SybaseASADatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.GetViewDefinitionGenerator;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.structure.DatabaseObject;

public class GetViewDefinitionGeneratorSybaseASA
extends GetViewDefinitionGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(GetViewDefinitionStatement statement, Database database) {
        return database instanceof SybaseASADatabase;
    }

    @Override
    public Sql[] generateSql(GetViewDefinitionStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new Sql[]{new UnparsedSql("select viewtext from sysviews where upper(viewname)='" + statement.getViewName().toUpperCase() + "' and upper(vcreator) = '" + statement.getSchemaName().toUpperCase() + '\'', new DatabaseObject[0])};
    }
}

