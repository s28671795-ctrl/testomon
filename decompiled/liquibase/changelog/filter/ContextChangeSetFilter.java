/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.ArrayList;
import java.util.Collection;
import liquibase.ContextExpression;
import liquibase.Contexts;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.sql.visitor.SqlVisitor;

public class ContextChangeSetFilter
implements ChangeSetFilter {
    private Contexts contexts;

    public ContextChangeSetFilter() {
        this(new Contexts());
    }

    public ContextChangeSetFilter(Contexts contexts) {
        this.contexts = contexts;
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        ArrayList<SqlVisitor> visitorsToRemove = new ArrayList<SqlVisitor>();
        for (SqlVisitor visitor : changeSet.getSqlVisitors()) {
            if (visitor.getContextFilter() == null || visitor.getContextFilter().matches(this.contexts)) continue;
            visitorsToRemove.add(visitor);
        }
        changeSet.getSqlVisitors().removeAll(visitorsToRemove);
        if (this.contexts == null) {
            this.contexts = new Contexts();
        }
        Collection<ContextExpression> inheritableContexts = changeSet.getInheritableContextFilter();
        if (changeSet.getContextFilter().isEmpty() && inheritableContexts.isEmpty()) {
            return new ChangeSetFilterResult(true, "Changeset runs under all contexts", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        if (changeSet.getContextFilter().matches(this.contexts) && ContextExpression.matchesAll(inheritableContexts, this.contexts)) {
            return new ChangeSetFilterResult(true, "Context matches '" + this.contexts.toString() + "'", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(false, "Context does not match '" + this.contexts.toString() + "'", this.getClass(), this.getMdcName(), this.getDisplayName());
    }

    @Override
    public String getMdcName() {
        return "contextMismatch";
    }

    @Override
    public String getDisplayName() {
        return "Context mismatch";
    }
}

