/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import liquibase.ChecksumVersion;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;

public class ShouldRunChangeSetFilter
implements ChangeSetFilter {
    private final Map<String, RanChangeSet> ranChangeSets;
    private final boolean ignoreClasspathPrefix;
    public static final String CHANGESET_ALREADY_RAN_MESSAGE = "Changeset already ran";

    public ShouldRunChangeSetFilter(Database database, boolean ignoreClasspathPrefix) throws DatabaseException {
        this.ignoreClasspathPrefix = ignoreClasspathPrefix;
        this.ranChangeSets = new HashMap<String, RanChangeSet>();
        for (RanChangeSet ranChangeSet : database.getRanChangeSetList()) {
            RanChangeSet existingChangeSet = this.ranChangeSets.get(ranChangeSet.toString());
            boolean addToSet = false;
            if (existingChangeSet == null) {
                addToSet = true;
            } else {
                Date existingDate = existingChangeSet.getDateExecuted();
                Date thisDate = ranChangeSet.getDateExecuted();
                if (existingDate != null && thisDate != null) {
                    int comparedDates = thisDate.compareTo(existingDate);
                    if (comparedDates > 0) {
                        addToSet = true;
                    } else if (comparedDates == 0) {
                        Integer existingOrder = existingChangeSet.getOrderExecuted();
                        Integer thisOrder = ranChangeSet.getOrderExecuted();
                        if (existingOrder != null && thisOrder != null && thisOrder.compareTo(existingOrder) < 0) {
                            addToSet = true;
                        }
                    }
                }
            }
            if (!addToSet) continue;
            this.ranChangeSets.put(ranChangeSet.toString(), ranChangeSet);
        }
    }

    public ShouldRunChangeSetFilter(Database database) throws DatabaseException {
        this(database, true);
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        String key = changeSet.toNormalizedString();
        RanChangeSet ranChangeSet = this.ranChangeSets.get(key);
        if (ranChangeSet != null && ranChangeSet.isSameAs(changeSet)) {
            if (changeSet.shouldAlwaysRun()) {
                return new ChangeSetFilterResult(true, "Changeset always runs", this.getClass(), this.getMdcName(), this.getDisplayName());
            }
            if (changeSet.shouldRunOnChange() && this.checksumChanged(changeSet, ranChangeSet)) {
                return new ChangeSetFilterResult(true, "Changeset checksum changed", this.getClass(), this.getMdcName(), this.getDisplayName());
            }
            return new ChangeSetFilterResult(false, CHANGESET_ALREADY_RAN_MESSAGE, this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(true, "Changeset has not ran yet", this.getClass(), this.getMdcName(), this.getDisplayName());
    }

    protected boolean checksumChanged(ChangeSet changeSet, RanChangeSet ranChangeSet) {
        if (ranChangeSet.getLastCheckSum() == null) {
            return false;
        }
        return !changeSet.generateCheckSum(ChecksumVersion.enumFromChecksumVersion(ranChangeSet.getLastCheckSum().getVersion())).equals(ranChangeSet.getLastCheckSum());
    }

    @Override
    public String getMdcName() {
        return "alreadyRan";
    }

    @Override
    public String getDisplayName() {
        return "Already ran";
    }
}

