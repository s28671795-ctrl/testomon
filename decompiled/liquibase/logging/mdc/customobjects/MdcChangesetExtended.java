/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import java.util.Objects;
import liquibase.changelog.ChangeSet;
import liquibase.logging.mdc.customobjects.MdcChangeset;

public class MdcChangesetExtended
extends MdcChangeset {
    private String deploymentId;
    private String changesetOutcome;

    public MdcChangesetExtended(String changesetId, String changesetAuthor, String changesetFilepath, String deploymentId, String changesetOutcome) {
        super(changesetId, changesetAuthor, changesetFilepath);
        this.deploymentId = deploymentId;
        this.changesetOutcome = changesetOutcome;
    }

    public static MdcChangesetExtended fromChangeset(ChangeSet changeSet) {
        return new MdcChangesetExtended(changeSet.getId(), changeSet.getAuthor(), changeSet.getFilePath(), Objects.toString(changeSet.getAttribute("deploymentId")), Objects.toString(changeSet.getAttribute("updateExecType")));
    }

    public String getDeploymentId() {
        return this.deploymentId;
    }

    public String getChangesetOutcome() {
        return this.changesetOutcome;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public void setChangesetOutcome(String changesetOutcome) {
        this.changesetOutcome = changesetOutcome;
    }

    public MdcChangesetExtended() {
    }
}

