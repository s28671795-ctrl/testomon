/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DropSequenceStatement;
import liquibase.structure.core.Sequence;

public class DropSequenceGenerator
extends AbstractSqlGenerator<DropSequenceStatement> {
    @Override
    public boolean supports(DropSequenceStatement statement, Database database) {
        return database.supports(Sequence.class);
    }

    @Override
    public ValidationErrors validate(DropSequenceStatement dropSequenceStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("sequenceName", dropSequenceStatement.getSequenceName());
        return validationErrors;
    }

    @Override
    public Sql[] generateSql(DropSequenceStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql = "DROP SEQUENCE ";
        sql = sql + database.escapeSequenceName(statement.getCatalogName(), statement.getSchemaName(), statement.getSequenceName());
        if (database instanceof PostgresDatabase) {
            sql = sql + " CASCADE";
        }
        if (database instanceof DerbyDatabase) {
            sql = sql + " RESTRICT";
        }
        return new Sql[]{new UnparsedSql(sql, this.getAffectedSequence(statement))};
    }

    protected Sequence getAffectedSequence(DropSequenceStatement statement) {
        return new Sequence().setName(statement.getSequenceName()).setSchema(statement.getCatalogName(), statement.getSchemaName());
    }
}

