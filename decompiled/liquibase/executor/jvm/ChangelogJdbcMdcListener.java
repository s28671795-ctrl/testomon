/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor.jvm;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.sql.Sql;
import liquibase.util.SqlUtil;

public class ChangelogJdbcMdcListener {
    public static void execute(Database database, ExecuteJdbc jdbcQuery) throws DatabaseException {
        try {
            AtomicReference<Object> sqls = new AtomicReference<Object>(null);
            Scope.child(Collections.singletonMap("generatedSqlArray", sqls), () -> jdbcQuery.execute(Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database)));
            ChangelogJdbcMdcListener.addSqlMdc(sqls);
            ChangelogJdbcMdcListener.addSqlToScope(sqls, database);
            ChangelogJdbcMdcListener.logSuccess();
        }
        catch (Exception e) {
            Scope.getCurrentScope().addMdcValue("databaseChangelogTableOutcome", "failed");
            throw new DatabaseException(e);
        }
    }

    public static <T> T query(Database database, QueryJdbc<T> jdbcQuery) throws DatabaseException {
        try {
            AtomicReference<Object> sqls = new AtomicReference<Object>(null);
            Object value = Scope.child(Collections.singletonMap("generatedSqlArray", sqls), () -> jdbcQuery.execute(Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database)));
            ChangelogJdbcMdcListener.addSqlMdc(sqls);
            ChangelogJdbcMdcListener.addSqlToScope(sqls, database);
            ChangelogJdbcMdcListener.logSuccess();
            return (T)value;
        }
        catch (Exception e) {
            Scope.getCurrentScope().addMdcValue("databaseChangelogTableOutcome", "failed");
            throw new DatabaseException(e);
        }
    }

    private static void addSqlMdc(AtomicReference<Sql[]> sqlsRef) {
        Sql[] sqls;
        if (sqlsRef != null && (sqls = sqlsRef.get()) != null) {
            Scope.getCurrentScope().addMdcValue("databaseChangelogSQL", SqlUtil.convertSqlArrayToString(sqls));
        }
    }

    private static void addSqlToScope(AtomicReference<Sql[]> sqlsRef, Database database) {
        Sql[] sqls;
        if (sqlsRef != null && (sqls = sqlsRef.get()) != null) {
            database.addCompleteSqlToScope(SqlUtil.convertSqlArrayToString(sqls));
        }
    }

    private static void logSuccess() {
        Scope.getCurrentScope().addMdcValue("databaseChangelogTableOutcome", "executed");
        Scope.getCurrentScope().getLog(ChangelogJdbcMdcListener.class).fine("Changelog query completed.");
        Scope.getCurrentScope().getMdcManager().remove("databaseChangelogTableOutcome");
        Scope.getCurrentScope().getMdcManager().remove("databaseChangelogSQL");
    }

    public static interface ExecuteJdbc {
        public void execute(Executor var1) throws DatabaseException;
    }

    public static interface QueryJdbc<T> {
        public T execute(Executor var1) throws DatabaseException;
    }
}

