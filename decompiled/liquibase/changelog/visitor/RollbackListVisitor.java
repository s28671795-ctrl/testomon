/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.changelog.visitor.ListVisitor;

public class RollbackListVisitor
extends ListVisitor {
    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.REVERSE;
    }
}

