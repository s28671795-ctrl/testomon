/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator.core;

import java.util.ArrayList;
import java.util.Arrays;
import liquibase.database.Database;
import liquibase.database.core.PostgresDatabase;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AddDefaultValueGenerator;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.core.AddDefaultValueStatement;
import liquibase.structure.core.Column;
import liquibase.structure.core.Sequence;

public class AddDefaultValueGeneratorPostgres
extends AddDefaultValueGenerator {
    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public boolean supports(AddDefaultValueStatement statement, Database database) {
        return database instanceof PostgresDatabase;
    }

    @Override
    public Sql[] generateSql(AddDefaultValueStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        if (!(statement.getDefaultValue() instanceof SequenceNextValueFunction)) {
            return super.generateSql(statement, database, sqlGeneratorChain);
        }
        ArrayList<Sql> commands = new ArrayList<Sql>(Arrays.asList(super.generateSql(statement, database, sqlGeneratorChain)));
        SequenceNextValueFunction sequenceFunction = (SequenceNextValueFunction)statement.getDefaultValue();
        String sequenceName = sequenceFunction.getValue();
        String sequenceSchemaName = sequenceFunction.getSchemaName();
        String sequence2 = database.escapeObjectName(null, sequenceSchemaName, sequenceName, Sequence.class);
        UnparsedSql alterSequenceOwner = new UnparsedSql("ALTER SEQUENCE " + sequence2 + " OWNED BY " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(), statement.getTableName()) + "." + database.escapeObjectName(statement.getColumnName(), Column.class), this.getAffectedColumn(statement), this.getAffectedSequence(sequenceFunction));
        commands.add(alterSequenceOwner);
        return commands.toArray(EMPTY_SQL);
    }

    protected Sequence getAffectedSequence(SequenceNextValueFunction sequenceFunction) {
        return new Sequence().setName(sequenceFunction.getValue());
    }
}

