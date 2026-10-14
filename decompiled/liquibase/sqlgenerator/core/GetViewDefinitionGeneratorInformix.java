/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.GetViewDefinitionGenerator;
import liquibase.statement.core.GetViewDefinitionStatement;
import liquibase.structure.DatabaseObject;

public class GetViewDefinitionGeneratorInformix
extends GetViewDefinitionGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(GetViewDefinitionStatement statement, Database database) {
        return database instanceof InformixDatabase;
    }

    @Override
    public Sql[] generateSql(GetViewDefinitionStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        return new Sql[]{new UnparsedSql("select v.viewtext from sysviews v, systables t where t.tabname = '" + statement.getViewName() + "' and v.tabid = t.tabid and t.tabtype = 'V' order by v.seqno", new DatabaseObject[0])};
    }
}

