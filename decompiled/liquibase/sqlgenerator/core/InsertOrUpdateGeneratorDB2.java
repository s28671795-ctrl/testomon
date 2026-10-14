/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.ArrayList;
import java.util.Arrays;
import liquibase.database.Database;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.InsertOrUpdateGenerator;
import liquibase.statement.core.InsertOrUpdateStatement;
import liquibase.structure.DatabaseObject;

public class InsertOrUpdateGeneratorDB2
extends InsertOrUpdateGenerator {
    private static final String DB2_Z_INSERT_OR_UPDATE_PROCEDURE = "insertOrUpdateDb2Z";

    @Override
    protected String getElse(Database database) {
        return "\tELSEIF v_reccount = 1 THEN\n";
    }

    @Override
    public Sql[] generateSql(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        Sql[] sqls = super.generateSql(insertOrUpdateStatement, database, sqlGeneratorChain);
        if (database instanceof Db2zDatabase) {
            ArrayList<Sql> list = new ArrayList<Sql>(Arrays.asList(sqls));
            list.add(new UnparsedSql("CALL insertOrUpdateDb2Z()", new DatabaseObject[0]));
            list.add(new UnparsedSql("DROP PROCEDURE insertOrUpdateDb2Z", new DatabaseObject[0]));
            sqls = list.toArray(EMPTY_SQL);
        }
        return sqls;
    }

    @Override
    protected String getRecordCheck(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause) {
        StringBuilder recordCheckSql = new StringBuilder();
        if (database instanceof Db2zDatabase) {
            recordCheckSql.append("CREATE PROCEDURE ").append(DB2_Z_INSERT_OR_UPDATE_PROCEDURE).append("()\n");
            recordCheckSql.append("BEGIN\n");
        } else {
            recordCheckSql.append("BEGIN ATOMIC\n");
        }
        recordCheckSql.append("\tDECLARE v_reccount INTEGER;\n");
        recordCheckSql.append("\tSET v_reccount = (SELECT COUNT(*) FROM ");
        recordCheckSql.append(database.escapeTableName(insertOrUpdateStatement.getCatalogName(), insertOrUpdateStatement.getSchemaName(), insertOrUpdateStatement.getTableName()));
        recordCheckSql.append(" WHERE ");
        recordCheckSql.append(whereClause);
        recordCheckSql.append(");\n");
        recordCheckSql.append("\tIF v_reccount = 0 THEN\n");
        return recordCheckSql.toString();
    }

    @Override
    public boolean supports(InsertOrUpdateStatement statement, Database database) {
        return database instanceof AbstractDb2Database;
    }

    @Override
    protected String getPostUpdateStatements(Database database) {
        return "END IF;\nEND\n";
    }
}

