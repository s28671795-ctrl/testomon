/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import liquibase.changelog.ChangeSet;
import liquibase.logging.mdc.CustomMdcObject;

public class MdcChangeset
implements CustomMdcObject {
    private String changesetId;
    private String changesetAuthor;
    private String changesetFilepath;

    public static MdcChangeset fromChangeset(ChangeSet changeSet) {
        return new MdcChangeset(changeSet.getId(), changeSet.getAuthor(), changeSet.getFilePath());
    }

    public String getChangesetId() {
        return this.changesetId;
    }

    public String getChangesetAuthor() {
        return this.changesetAuthor;
    }

    public String getChangesetFilepath() {
        return this.changesetFilepath;
    }

    public void setChangesetId(String changesetId) {
        this.changesetId = changesetId;
    }

    public void setChangesetAuthor(String changesetAuthor) {
        this.changesetAuthor = changesetAuthor;
    }

    public void setChangesetFilepath(String changesetFilepath) {
        this.changesetFilepath = changesetFilepath;
    }

    public MdcChangeset() {
    }

    public MdcChangeset(String changesetId, String changesetAuthor, String changesetFilepath) {
        this.changesetId = changesetId;
        this.changesetAuthor = changesetAuthor;
        this.changesetFilepath = changesetFilepath;
    }
}

