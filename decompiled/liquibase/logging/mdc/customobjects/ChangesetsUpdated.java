/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import java.util.ArrayList;
import java.util.List;
import liquibase.changelog.ChangeSet;
import liquibase.logging.mdc.CustomMdcObject;
import liquibase.logging.mdc.customobjects.MdcChangesetExtended;

public class ChangesetsUpdated
implements CustomMdcObject {
    private int changesetCount;
    private List<MdcChangesetExtended> changeset;

    public ChangesetsUpdated() {
    }

    public ChangesetsUpdated(List<ChangeSet> deployedChangeSets) {
        this.changesetCount = deployedChangeSets.size();
        this.changeset = new ArrayList<MdcChangesetExtended>(this.changesetCount);
        for (ChangeSet deployedChangeSet : deployedChangeSets) {
            this.changeset.add(MdcChangesetExtended.fromChangeset(deployedChangeSet));
        }
    }

    public int getChangesetCount() {
        return this.changesetCount;
    }

    public void setChangesetCount(int changesetCount) {
        this.changesetCount = changesetCount;
    }

    public List<MdcChangesetExtended> getChangeset() {
        return this.changeset;
    }

    public void setChangeset(List<MdcChangesetExtended> changeset) {
        this.changeset = changeset;
    }
}

