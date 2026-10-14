/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator;

import java.util.Iterator;
import java.util.SortedSet;
import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGenerator;
import liquibase.statement.SqlStatement;

public class SqlGeneratorChain<T extends SqlStatement> {
    private Iterator<SqlGenerator<T>> sqlGenerators;

    public SqlGeneratorChain(SortedSet<SqlGenerator<T>> sqlGenerators) {
        if (sqlGenerators != null) {
            this.sqlGenerators = sqlGenerators.iterator();
        }
    }

    public Sql[] generateSql(T statement, Database database) {
        if (this.sqlGenerators == null) {
            return null;
        }
        if (!this.sqlGenerators.hasNext()) {
            return SqlGenerator.EMPTY_SQL;
        }
        return this.sqlGenerators.next().generateSql(statement, database, this);
    }

    public Warnings warn(T statement, Database database) {
        if (this.sqlGenerators == null || !this.sqlGenerators.hasNext()) {
            return new Warnings();
        }
        return this.sqlGenerators.next().warn(statement, database, this);
    }

    public ValidationErrors validate(T statement, Database database) {
        if (this.sqlGenerators == null || !this.sqlGenerators.hasNext()) {
            return new ValidationErrors();
        }
        return this.sqlGenerators.next().validate(statement, database, this);
    }
}

