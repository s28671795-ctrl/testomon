/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.changelog.ChangeSet;
import liquibase.statement.AbstractSqlStatement;

public class UpdateChangeSetChecksumStatement
extends AbstractSqlStatement {
    private final ChangeSet changeSet;

    public UpdateChangeSetChecksumStatement(ChangeSet changeSet) {
        this.changeSet = changeSet;
    }

    public ChangeSet getChangeSet() {
        return this.changeSet;
    }
}

