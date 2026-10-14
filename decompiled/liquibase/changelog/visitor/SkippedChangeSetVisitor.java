/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.Set;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;

public interface SkippedChangeSetVisitor {
    public void skipped(ChangeSet var1, DatabaseChangeLog var2, Database var3, Set<ChangeSetFilterResult> var4) throws LiquibaseException;
}

