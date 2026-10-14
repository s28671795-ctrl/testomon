/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import liquibase.Scope;
import liquibase.TagVersionEnum;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.exception.RollbackFailedException;
import liquibase.integration.commandline.LiquibaseCommandLineConfiguration;
import liquibase.util.StringUtil;

public class AfterTagChangeSetFilter
implements ChangeSetFilter {
    private final String tag;
    private final Set<String> changeLogsAfterTag = new HashSet<String>();

    public AfterTagChangeSetFilter(String tag, List<RanChangeSet> ranChangeSets, TagVersionEnum tagVersion) throws RollbackFailedException {
        this.tag = tag;
        if (tagVersion == TagVersionEnum.OLDEST) {
            this.oldestVersion(ranChangeSets);
            return;
        }
        boolean seenTag = ranChangeSets.stream().anyMatch(ranChangeSet -> tag.equalsIgnoreCase(ranChangeSet.getTag()));
        if (!seenTag) {
            Scope.getCurrentScope().addMdcValue("deploymentOutcome", "fail");
            throw new RollbackFailedException("Could not find tag '" + tag + "' in the database");
        }
        List reversedRanChangeSets = ranChangeSets.stream().collect(Collectors.collectingAndThen(Collectors.toList(), l -> {
            Collections.reverse(l);
            return l;
        }));
        for (RanChangeSet ranChangeSet2 : reversedRanChangeSets) {
            if (tag.equalsIgnoreCase(ranChangeSet2.getTag())) {
                if (!"tagDatabase".equals(StringUtil.trimToEmpty(ranChangeSet2.getDescription()))) break;
                this.changeLogsAfterTag.add(ranChangeSet2.toString());
                break;
            }
            this.changeLogsAfterTag.add(ranChangeSet2.toString());
        }
    }

    private void oldestVersion(List<RanChangeSet> ranChangeSets) throws RollbackFailedException {
        boolean seenTag = false;
        for (RanChangeSet ranChangeSet : ranChangeSets) {
            if (seenTag && (LiquibaseCommandLineConfiguration.INCLUDE_MATCHING_TAG_IN_ROLLBACK_OLDEST.getCurrentValue().booleanValue() || !this.tag.equalsIgnoreCase(ranChangeSet.getTag()))) {
                this.changeLogsAfterTag.add(ranChangeSet.toString());
            }
            if (!seenTag && this.tag.equalsIgnoreCase(ranChangeSet.getTag())) {
                seenTag = true;
            }
            if (!this.tag.equalsIgnoreCase(ranChangeSet.getTag()) || !"tagDatabase".equals(StringUtil.trimToEmpty(ranChangeSet.getDescription()))) continue;
            this.changeLogsAfterTag.add(ranChangeSet.toString());
        }
        if (!seenTag) {
            Scope.getCurrentScope().addMdcValue("deploymentOutcome", "fail");
            throw new RollbackFailedException("Could not find tag '" + this.tag + "' in the database");
        }
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        if (this.changeLogsAfterTag.contains(changeSet.toString())) {
            return new ChangeSetFilterResult(true, "Changeset is before tag '" + this.tag + "'", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(false, "Changeset after tag '" + this.tag + "'", this.getClass(), this.getMdcName(), this.getDisplayName());
    }
}

