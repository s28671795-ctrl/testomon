/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import liquibase.logging.mdc.CustomMdcObject;

public class ChangesetsRolledback
implements CustomMdcObject {
    private int changesetCount;
    private List<ChangeSet> changesets;

    public ChangesetsRolledback() {
    }

    public ChangesetsRolledback(List<ChangeSet> changeSets) {
        this.changesetCount = changeSets.size();
        this.changesets = changeSets;
    }

    public static ChangesetsRolledback fromChangesetList(List<liquibase.changelog.ChangeSet> changeSets) {
        if (changeSets != null) {
            List<ChangeSet> changesets = changeSets.stream().map(ChangeSet::fromChangeSet).collect(Collectors.toList());
            return new ChangesetsRolledback(changesets);
        }
        return new ChangesetsRolledback(Collections.emptyList());
    }

    public int getChangesetCount() {
        return this.changesetCount;
    }

    public List<ChangeSet> getChangesets() {
        return this.changesets;
    }

    public void setChangesetCount(int changesetCount) {
        this.changesetCount = changesetCount;
    }

    public void setChangesets(List<ChangeSet> changesets) {
        this.changesets = changesets;
    }

    public static class ChangeSet {
        private String changesetId;
        private String author;
        private String filepath;
        private String deploymentId;

        public ChangeSet(String changesetId, String author, String filepath, String deploymentId) {
            this.changesetId = changesetId;
            this.author = author;
            this.filepath = filepath;
            this.deploymentId = deploymentId;
        }

        public static ChangeSet fromChangeSet(liquibase.changelog.ChangeSet changeSet) {
            return new ChangeSet(changeSet.getId(), changeSet.getAuthor(), changeSet.getFilePath(), changeSet.getDeploymentId());
        }

        public String getChangesetId() {
            return this.changesetId;
        }

        public String getAuthor() {
            return this.author;
        }

        public String getFilepath() {
            return this.filepath;
        }

        public String getDeploymentId() {
            return this.deploymentId;
        }

        public void setChangesetId(String changesetId) {
            this.changesetId = changesetId;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setFilepath(String filepath) {
            this.filepath = filepath;
        }

        public void setDeploymentId(String deploymentId) {
            this.deploymentId = deploymentId;
        }
    }
}

