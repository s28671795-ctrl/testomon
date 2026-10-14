/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.model.ConfigurationContainer;

public class ReportSet
extends ConfigurationContainer
implements Serializable,
Cloneable {
    private String id = "default";
    private List<String> reports;

    public void addReport(String string) {
        this.getReports().add(string);
    }

    @Override
    public ReportSet clone() {
        try {
            ReportSet copy = (ReportSet)super.clone();
            if (this.reports != null) {
                copy.reports = new ArrayList<String>();
                copy.reports.addAll(this.reports);
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getId() {
        return this.id;
    }

    public List<String> getReports() {
        if (this.reports == null) {
            this.reports = new ArrayList<String>();
        }
        return this.reports;
    }

    public void removeReport(String string) {
        this.getReports().remove(string);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    public String toString() {
        return this.getId();
    }
}

