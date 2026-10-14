/*
 * Decompiled with CFR 0.152.
 */
package liquibase.database;

import java.util.Arrays;
import java.util.List;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.database.Database;
import liquibase.database.LiquibaseTableNames;
import liquibase.exception.DatabaseException;
import liquibase.lockservice.LockServiceFactory;

public class StandardLiquibaseTableNames
implements LiquibaseTableNames {
    @Override
    public List<String> getLiquibaseGeneratedTableNames(Database database) {
        return Arrays.asList(database.getDatabaseChangeLogTableName(), database.getDatabaseChangeLogLockTableName());
    }

    @Override
    public void destroy(Database database) throws DatabaseException {
        Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database).destroy();
        LockServiceFactory.getInstance().getLockService(database).destroy();
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}

