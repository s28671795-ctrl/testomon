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

public class RecentChangesWriter
extends HTMLWriter {
    public RecentChangesWriter(Resource rootOutputDir, Database database) {
        super(rootOutputDir.resolve("recent"), database);
    }

    @Override
    protected String createTitle(Object object) {
        return "Recent Changes";
    }

    @Override
    protected void writeBody(Writer fileWriter, Object object, List<Change> ranChanges, List<Change> changesToRun) throws IOException, DatabaseHistoryException, DatabaseException {
        this.writeCustomHTML(fileWriter, object, ranChanges, this.database);
        this.writeChanges("Most Recent Changes", fileWriter, ranChanges);
    }

    @Override
    protected void writeCustomHTML(Writer fileWriter, Object object, List<Change> changes, Database database) throws IOException {
    }
}

