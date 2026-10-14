/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.regex.Matcher;
import liquibase.database.Database;
import liquibase.database.core.H2Database;
import liquibase.exception.LiquibaseException;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.InsertOrUpdateGenerator;
import liquibase.statement.core.InsertOrUpdateStatement;

public class InsertOrUpdateGeneratorH2
extends InsertOrUpdateGenerator {
    @Override
    public boolean supports(InsertOrUpdateStatement statement, Database database) {
        return database instanceof H2Database;
    }

    @Override
    protected String getInsertStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String insertStatement = super.getInsertStatement(insertOrUpdateStatement, database, sqlGeneratorChain);
        return insertStatement.replaceAll("(?i)insert into (.+?) (values .+)", "MERGE INTO $1 KEY(" + Matcher.quoteReplacement(insertOrUpdateStatement.getPrimaryKey()) + ") $2");
    }

    @Override
    protected String getUpdateStatement(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause, SqlGeneratorChain sqlGeneratorChain) throws LiquibaseException {
        if (insertOrUpdateStatement.getOnlyUpdate().booleanValue()) {
            return super.getUpdateStatement(insertOrUpdateStatement, database, whereClause, sqlGeneratorChain);
        }
        return "";
    }

    @Override
    protected String getRecordCheck(InsertOrUpdateStatement insertOrUpdateStatement, Database database, String whereClause) {
        return "";
    }

    @Override
    protected String getElse(Database database) {
        return "";
    }
}

