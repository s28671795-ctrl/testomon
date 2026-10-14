/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.Set;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;

public class DetermineNumberChangesFollowingVisitor
implements ChangeSetVisitor {
    private final String id;
    private final String author;
    private final String filePath;
    private int changeSetPosition;
    private int totalChangeSets;

    public DetermineNumberChangesFollowingVisitor(String id, String author, String filePath) {
        this.id = id;
        this.author = author;
        this.filePath = filePath;
    }

    public int getNumChangeSetsFollowing() {
        return this.totalChangeSets - this.changeSetPosition;
    }

    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.FORWARD;
    }

    @Override
    public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        ++this.totalChangeSets;
        if (changeSet.getId().equalsIgnoreCase(this.id) && changeSet.getAuthor().equalsIgnoreCase(this.author) && changeSet.getFilePath().equals(this.filePath)) {
            this.changeSetPosition = this.totalChangeSets;
        }
    }
}

