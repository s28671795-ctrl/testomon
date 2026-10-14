/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.Set;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;

public interface ChangeSetVisitor {
    public Direction getDirection();

    public void visit(ChangeSet var1, DatabaseChangeLog var2, Database var3, Set<ChangeSetFilterResult> var4) throws LiquibaseException;

    default public void logMdcData(ChangeSet changeSet) {
        Scope scope = Scope.getCurrentScope();
        scope.addMdcValue("changesetId", changeSet.getId(), false);
        scope.addMdcValue("changesetAuthor", changeSet.getAuthor(), false);
        scope.addMdcValue("changesetFilepath", changeSet.getFilePath());
    }

    public static enum Direction {
        FORWARD,
        REVERSE;

    }
}

