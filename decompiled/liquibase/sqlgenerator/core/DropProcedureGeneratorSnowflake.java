/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.SnowflakeDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.DropProcedureGenerator;
import liquibase.statement.core.DropProcedureStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;

public class DropProcedureGeneratorSnowflake
extends DropProcedureGenerator {
    @Override
    public boolean supports(DropProcedureStatement statement, Database database) {
        return database instanceof SnowflakeDatabase;
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public Sql[] generateSql(DropProcedureStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        StringBuilder unparsedSql = new StringBuilder("DROP PROCEDURE ");
        if (statement.getSchemaName() != null) {
            unparsedSql.append(database.escapeObjectName(statement.getSchemaName(), Schema.class));
            unparsedSql.append(".");
        }
        unparsedSql.append(statement.getProcedureName());
        unparsedSql.append("()");
        return new Sql[]{new UnparsedSql(unparsedSql.toString(), new DatabaseObject[0])};
    }
}

