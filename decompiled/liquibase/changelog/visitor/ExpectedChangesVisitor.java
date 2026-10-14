/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;

public class ExpectedChangesVisitor
implements ChangeSetVisitor {
    private final LinkedHashSet<RanChangeSet> unexpectedChangeSets;

    public ExpectedChangesVisitor(List<RanChangeSet> ranChangeSetList) {
        this.unexpectedChangeSets = new LinkedHashSet<RanChangeSet>(ranChangeSetList);
    }

    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.FORWARD;
    }

    @Override
    public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        this.unexpectedChangeSets.removeIf(ranChangeSet -> ranChangeSet.isSameAs(changeSet) || ranChangeSet.getChangeLog().equalsIgnoreCase("liquibase-internal"));
    }

    public Collection<RanChangeSet> getUnexpectedChangeSets() {
        return this.unexpectedChangeSets;
    }
}

