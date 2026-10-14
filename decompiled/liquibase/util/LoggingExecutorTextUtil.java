/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.text.DateFormat;
import java.util.Date;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.MSSQLDatabase;
import liquibase.exception.DatabaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.core.Catalog;
import liquibase.util.LiquibaseUtil;
import liquibase.util.StreamUtil;

public class LoggingExecutorTextUtil {
    private LoggingExecutorTextUtil() {
    }

    public static void outputHeader(String message, Database database, String changeLogFile) throws DatabaseException {
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("logging", database);
        executor2.comment("*********************************************************************");
        executor2.comment(message);
        executor2.comment("*********************************************************************");
        executor2.comment("Change Log: " + changeLogFile);
        executor2.comment("Ran at: " + DateFormat.getDateTimeInstance(3, 3).format(new Date()));
        DatabaseConnection connection = database.getConnection();
        if (connection != null) {
            executor2.comment("Against: " + connection.getConnectionUserName() + "@" + connection.getURL());
        }
        executor2.comment("Liquibase version: " + LiquibaseUtil.getBuildVersionInfo());
        executor2.comment("*********************************************************************" + StreamUtil.getLineSeparator());
        if (database instanceof MSSQLDatabase && database.getDefaultCatalogName() != null) {
            executor2.execute(new RawSqlStatement("USE " + database.escapeObjectName(database.getDefaultCatalogName(), Catalog.class) + ";"));
        }
    }
}

