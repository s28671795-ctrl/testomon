/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.ArrayList;
import java.util.Collection;
import liquibase.LabelExpression;
import liquibase.Labels;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.sql.visitor.SqlVisitor;

public class LabelChangeSetFilter
implements ChangeSetFilter {
    private LabelExpression labelExpression;

    public LabelChangeSetFilter() {
        this(new LabelExpression());
    }

    public LabelChangeSetFilter(LabelExpression labels) {
        this.labelExpression = labels;
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        ArrayList<SqlVisitor> visitorsToRemove = new ArrayList<SqlVisitor>();
        for (SqlVisitor visitor : changeSet.getSqlVisitors()) {
            if (visitor.getLabels() == null || this.labelExpression.matches(visitor.getLabels())) continue;
            visitorsToRemove.add(visitor);
        }
        changeSet.getSqlVisitors().removeAll(visitorsToRemove);
        if (this.labelExpression == null) {
            this.labelExpression = new LabelExpression();
        }
        Collection<Labels> inheritableLabels = changeSet.getInheritableLabels();
        if (this.labelExpression.matches(changeSet.getLabels()) && LabelExpression.matchesAll(inheritableLabels, this.labelExpression)) {
            return new ChangeSetFilterResult(true, "Labels matches '" + this.labelExpression.toString() + "'", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(false, "Labels does not match '" + this.labelExpression.toString() + "'", this.getClass(), this.getMdcName(), this.getDisplayName());
    }

    @Override
    public String getMdcName() {
        return "labelsMismatch";
    }

    @Override
    public String getDisplayName() {
        return "Label mismatch";
    }
}

