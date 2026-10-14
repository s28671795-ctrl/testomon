/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.maven.model.ConfigurationContainer;
import org.apache.maven.model.ReportSet;

public class ReportPlugin
extends ConfigurationContainer
implements Serializable,
Cloneable {
    private String groupId = "org.apache.maven.plugins";
    private String artifactId;
    private String version;
    private List<ReportSet> reportSets;
    private Map<String, ReportSet> reportSetMap = null;

    public void addReportSet(ReportSet reportSet) {
        this.getReportSets().add(reportSet);
    }

    @Override
    public ReportPlugin clone() {
        try {
            ReportPlugin copy = (ReportPlugin)super.clone();
            if (this.reportSets != null) {
                copy.reportSets = new ArrayList<ReportSet>();
                for (ReportSet item : this.reportSets) {
                    copy.reportSets.add(item.clone());
                }
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public List<ReportSet> getReportSets() {
        if (this.reportSets == null) {
            this.reportSets = new ArrayList<ReportSet>();
        }
        return this.reportSets;
    }

    public String getVersion() {
        return this.version;
    }

    public void removeReportSet(ReportSet reportSet) {
        this.getReportSets().remove(reportSet);
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setReportSets(List<ReportSet> reportSets) {
        this.reportSets = reportSets;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void flushReportSetMap() {
        this.reportSetMap = null;
    }

    public Map<String, ReportSet> getReportSetsAsMap() {
        if (this.reportSetMap == null) {
            this.reportSetMap = new LinkedHashMap<String, ReportSet>();
            if (this.getReportSets() != null) {
                for (ReportSet reportSet : this.getReportSets()) {
                    this.reportSetMap.put(reportSet.getId(), reportSet);
                }
            }
        }
        return this.reportSetMap;
    }

    public String getKey() {
        return ReportPlugin.constructKey(this.groupId, this.artifactId);
    }

    public static String constructKey(String groupId, String artifactId) {
        return groupId + ":" + artifactId;
    }
}

