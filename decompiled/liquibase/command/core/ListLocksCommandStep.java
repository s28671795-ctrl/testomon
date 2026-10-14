/*
 * Decompiled with CFR 0.152.
 */
package liquibase.command.core;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Collections;
import java.util.List;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.changelog.ChangeLogHistoryServiceFactory;
import liquibase.command.AbstractCommandStep;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.lockservice.DatabaseChangeLogLock;
import liquibase.lockservice.LockServiceFactory;

public class ListLocksCommandStep
extends AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{"listLocks"};

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("List the hostname, IP address, and timestamp of the Liquibase lock record");
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        CommandScope commandScope = resultsBuilder.getCommandScope();
        ListLocksCommandStep.reportLocks(this.getPrintStream(resultsBuilder), (Database)commandScope.getDependency(Database.class));
        resultsBuilder.addResult("statusCode", (Object)0);
    }

    @Override
    public List<Class<?>> requiredDependencies() {
        return Collections.singletonList(Database.class);
    }

    public static void reportLocks(PrintStream out, Database database) throws LiquibaseException {
        DatabaseChangeLogLock[] locks = ListLocksCommandStep.listLocks(database);
        out.println("Database change log locks for " + database.getConnection().getConnectionUserName() + "@" + database.getConnection().getURL());
        if (locks.length == 0) {
            out.println(" - No locks");
            return;
        }
        for (DatabaseChangeLogLock lock : locks) {
            out.println(" - " + lock.getLockedBy() + " at " + DateFormat.getDateTimeInstance().format(lock.getLockGranted()));
        }
        out.println("NOTE:  The lock time displayed is based on the database's configured time");
    }

    public static DatabaseChangeLogLock[] listLocks(Database database) throws LiquibaseException {
        ListLocksCommandStep.initializeChangelogService(database);
        return LockServiceFactory.getInstance().getLockService(database).listLocks();
    }

    public static void initializeChangelogService(Database database) throws LiquibaseException {
        ChangeLogHistoryService changeLogHistoryService = Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class).getChangeLogService(database);
        changeLogHistoryService.init();
        LockServiceFactory.getInstance().getLockService(database).init();
    }

    private PrintStream getPrintStream(CommandResultsBuilder resultsBuilder) {
        OutputStream os = resultsBuilder.getOutputStream();
        if (os == null) {
            return System.err;
        }
        if (os instanceof PrintStream) {
            return (PrintStream)os;
        }
        return new PrintStream(os);
    }
}

