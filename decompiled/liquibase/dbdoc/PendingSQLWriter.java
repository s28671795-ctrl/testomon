/*
 * Decompiled with CFR 0.152.
 */
package liquibase.dbdoc;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.dbdoc.HTMLWriter;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.exception.MigrationFailedException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;
import liquibase.resource.Resource;

public class PendingSQLWriter
extends HTMLWriter {
    private final DatabaseChangeLog databaseChangeLog;

    public PendingSQLWriter(Resource rootOutputDir, Database database, DatabaseChangeLog databaseChangeLog) {
        super(rootOutputDir.resolve("pending"), database);
        this.databaseChangeLog = databaseChangeLog;
    }

    @Override
    protected String createTitle(Object object) {
        return "Pending SQL";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void writeBody(Writer fileWriter, Object object, List<Change> ranChanges, List<Change> changesToRun) throws IOException, DatabaseHistoryException, DatabaseException {
        Executor oldTemplate = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", this.database);
        LoggingExecutor loggingExecutor = new LoggingExecutor(oldTemplate, fileWriter, this.database);
        Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("logging", this.database, loggingExecutor);
        Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", this.database, loggingExecutor);
        try {
            if (changesToRun.isEmpty()) {
                fileWriter.append("<b>NONE</b>");
            }
            fileWriter.append("<code><pre>");
            ChangeSet lastRunChangeSet = null;
            for (Change change : changesToRun) {
                ChangeSet thisChangeSet = change.getChangeSet();
                if (thisChangeSet.equals(lastRunChangeSet)) continue;
                lastRunChangeSet = thisChangeSet;
                String anchor = thisChangeSet.toString(false).replaceAll("\\W", "_");
                fileWriter.append("<a name='").append(anchor).append("'/>");
                try {
                    thisChangeSet.execute(this.databaseChangeLog, null, this.database);
                }
                catch (MigrationFailedException e) {
                    fileWriter.append("EXECUTION ERROR: ").append(Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(change).getDescription()).append(": ").append(e.getMessage()).append("\n\n");
                }
            }
            fileWriter.append("</pre></code>");
        }
        finally {
            Scope.getCurrentScope().getSingleton(ExecutorService.class).setExecutor("jdbc", this.database, oldTemplate);
        }
    }

    @Override
    protected void writeCustomHTML(Writer fileWriter, Object object, List<Change> changes, Database database) throws IOException {
    }
}

