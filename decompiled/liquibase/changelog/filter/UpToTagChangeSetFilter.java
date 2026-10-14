/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.List;
import liquibase.change.Change;
import liquibase.change.core.TagDatabaseChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;

public class UpToTagChangeSetFilter
implements ChangeSetFilter {
    private final String tag;
    private boolean seenTag;
    private String ranChangesetTagId = null;

    public UpToTagChangeSetFilter(String tag, List<RanChangeSet> ranChangeSets) {
        this.tag = tag;
        for (RanChangeSet ranChangeSet : ranChangeSets) {
            if (!tag.equalsIgnoreCase(ranChangeSet.getTag())) continue;
            this.ranChangesetTagId = ranChangeSet.toString();
            break;
        }
    }

    public boolean isSeenTag() {
        return this.seenTag;
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        if (this.seenTag) {
            return new ChangeSetFilterResult(false, "Changeset is after tag '" + this.tag + "'", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        if (changeSet.toString().equals(this.ranChangesetTagId)) {
            this.seenTag = true;
        } else {
            String changesetTag = null;
            for (Change change : changeSet.getChanges()) {
                if (!(change instanceof TagDatabaseChange)) continue;
                changesetTag = ((TagDatabaseChange)change).getTag();
            }
            if (this.tag.equals(changesetTag)) {
                this.seenTag = true;
            }
        }
        return new ChangeSetFilterResult(true, "Changeset is at or before tag '" + this.tag + "'", this.getClass(), this.getMdcName(), this.getDisplayName());
    }

    @Override
    public String getMdcName() {
        return "afterTag";
    }

    @Override
    public String getDisplayName() {
        return "After tag";
    }
}

