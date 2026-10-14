/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import java.util.List;
import liquibase.changelog.ChangeSet;
import liquibase.logging.mdc.CustomMdcObject;

public class SimpleStatus
implements CustomMdcObject {
    private String message;
    private String liquibaseTargetUrl;
    private int changesetCount;

    public SimpleStatus() {
    }

    public SimpleStatus(String message, String url2, List<ChangeSet> unrunChangeSets) {
        this.message = message;
        this.liquibaseTargetUrl = url2;
        this.changesetCount = unrunChangeSets.size();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLiquibaseTargetUrl() {
        return this.liquibaseTargetUrl;
    }

    public void setLiquibaseTargetUrl(String liquibaseTargetUrl) {
        this.liquibaseTargetUrl = liquibaseTargetUrl;
    }

    public int getChangesetCount() {
        return this.changesetCount;
    }

    public void setChangesetCount(int changesetCount) {
        this.changesetCount = changesetCount;
    }
}

