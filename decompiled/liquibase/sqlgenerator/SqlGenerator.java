/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sqlgenerator;

import liquibase.database.Database;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.servicelocator.PrioritizedService;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.statement.SqlStatement;

public interface SqlGenerator<T extends SqlStatement>
extends PrioritizedService {
    public static final int PRIORITY_DEFAULT = 1;
    public static final int PRIORITY_DATABASE = 5;
    public static final Sql[] EMPTY_SQL = new Sql[0];

    @Override
    public int getPriority();

    public boolean supports(T var1, Database var2);

    public boolean generateStatementsIsVolatile(Database var1);

    public boolean generateRollbackStatementsIsVolatile(Database var1);

    public ValidationErrors validate(T var1, Database var2, SqlGeneratorChain<T> var3);

    public Warnings warn(T var1, Database var2, SqlGeneratorChain<T> var3);

    public Sql[] generateSql(T var1, Database var2, SqlGeneratorChain<T> var3);
}

