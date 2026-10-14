/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import java.util.SortedMap;
import liquibase.logging.mdc.CustomMdcObject;

public class UpdateSummary
implements CustomMdcObject {
    private String value;
    private int run;
    private int runPreviously;
    private SortedMap<String, Integer> skipped;
    private int totalChangesets;

    public UpdateSummary() {
    }

    public UpdateSummary(String value, int run, int runPreviously, SortedMap<String, Integer> skipped, int totalChangesets) {
        this.value = value;
        this.run = run;
        this.runPreviously = runPreviously;
        this.skipped = skipped;
        this.totalChangesets = totalChangesets;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRun() {
        return this.run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public int getRunPreviously() {
        return this.runPreviously;
    }

    public void setRunPreviously(int runPreviously) {
        this.runPreviously = runPreviously;
    }

    public SortedMap<String, Integer> getSkipped() {
        return this.skipped;
    }

    public void setSkipped(SortedMap<String, Integer> skipped) {
        this.skipped = skipped;
    }

    public int getTotalChangesets() {
        return this.totalChangesets;
    }

    public void setTotalChangesets(int totalChangesets) {
        this.totalChangesets = totalChangesets;
    }
}

