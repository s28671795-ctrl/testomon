/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import liquibase.changelog.ChangeSet;
import liquibase.statement.AbstractSqlStatement;

public class MarkChangeSetRanStatement
extends AbstractSqlStatement {
    private final ChangeSet changeSet;
    private final ChangeSet.ExecType execType;

    public MarkChangeSetRanStatement(ChangeSet changeSet, ChangeSet.ExecType execType) {
        this.changeSet = changeSet;
        this.execType = execType;
    }

    public ChangeSet getChangeSet() {
        return this.changeSet;
    }

    public ChangeSet.ExecType getExecType() {
        return this.execType;
    }
}

