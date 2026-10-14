/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.util.ISODateFormat;

public class ExecutedAfterChangeSetFilter
implements ChangeSetFilter {
    private final Date date;
    private final Set<String> changeLogsAfterDate = new HashSet<String>();

    public ExecutedAfterChangeSetFilter(Date date, List<RanChangeSet> ranChangeSets) {
        this.date = date;
        for (RanChangeSet ranChangeSet : ranChangeSets) {
            if (ranChangeSet.getDateExecuted() == null || ranChangeSet.getDateExecuted().getTime() <= date.getTime()) continue;
            this.changeLogsAfterDate.add(ranChangeSet.toString());
        }
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        if (this.changeLogsAfterDate.contains(changeSet.toString())) {
            return new ChangeSetFilterResult(true, "Changeset ran after " + new ISODateFormat().format(new Timestamp(this.date.getTime())), this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(false, "Changeset ran before " + new ISODateFormat().format(new Timestamp(this.date.getTime())), this.getClass(), this.getMdcName(), this.getDisplayName());
    }
}

