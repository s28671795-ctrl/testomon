/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.Map;
import liquibase.Beta;
import liquibase.changelog.ChangeSet;
import liquibase.logging.mdc.customobjects.UpdateSummary;

@Beta
public class UpdateSummaryDetails {
    private UpdateSummary summary;
    private String output;
    private Map<ChangeSet, String> skipped;

    public UpdateSummary getSummary() {
        return this.summary;
    }

    public String getOutput() {
        return this.output;
    }

    public Map<ChangeSet, String> getSkipped() {
        return this.skipped;
    }

    public void setSummary(UpdateSummary summary) {
        this.summary = summary;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setSkipped(Map<ChangeSet, String> skipped) {
        this.skipped = skipped;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UpdateSummaryDetails)) {
            return false;
        }
        UpdateSummaryDetails other = (UpdateSummaryDetails)o;
        if (!other.canEqual(this)) {
            return false;
        }
        UpdateSummary this$summary = this.getSummary();
        UpdateSummary other$summary = other.getSummary();
        if (this$summary == null ? other$summary != null : !this$summary.equals(other$summary)) {
            return false;
        }
        String this$output = this.getOutput();
        String other$output = other.getOutput();
        if (this$output == null ? other$output != null : !this$output.equals(other$output)) {
            return false;
        }
        Map<ChangeSet, String> this$skipped = this.getSkipped();
        Map<ChangeSet, String> other$skipped = other.getSkipped();
        return !(this$skipped == null ? other$skipped != null : !((Object)this$skipped).equals(other$skipped));
    }

    protected boolean canEqual(Object other) {
        return other instanceof UpdateSummaryDetails;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        UpdateSummary $summary = this.getSummary();
        result = result * 59 + ($summary == null ? 43 : $summary.hashCode());
        String $output = this.getOutput();
        result = result * 59 + ($output == null ? 43 : $output.hashCode());
        Map<ChangeSet, String> $skipped = this.getSkipped();
        result = result * 59 + ($skipped == null ? 43 : ((Object)$skipped).hashCode());
        return result;
    }

    public String toString() {
        return "UpdateSummaryDetails(summary=" + this.getSummary() + ", output=" + this.getOutput() + ", skipped=" + this.getSkipped() + ")";
    }
}

