/*
 * Decompiled with CFR 0.152.
 */
package liquibase.dbdoc;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.dbdoc.HTMLWriter;
import liquibase.exception.DatabaseException;
import liquibase.exception.DatabaseHistoryException;
import liquibase.resource.Resource;

public class PendingChangesWriter
extends HTMLWriter {
    public PendingChangesWriter(Resource rootOutputDir, Database database) {
        super(rootOutputDir.resolve("pending"), database);
    }

    @Override
    protected String createTitle(Object object) {
        return "Pending Changes";
    }

    @Override
    protected void writeBody(Writer fileWriter, Object object, List<Change> ranChanges, List<Change> changesToRun) throws IOException, DatabaseHistoryException, DatabaseException {
        this.writeCustomHTML(fileWriter, object, ranChanges, this.database);
        this.writeChanges("Pending Changes", fileWriter, changesToRun);
    }

    @Override
    protected void writeCustomHTML(Writer fileWriter, Object object, List<Change> changes, Database database) throws IOException {
    }
}

