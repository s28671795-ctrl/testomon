/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import org.apache.maven.model.FileSet;

public class Resource
extends FileSet
implements Serializable,
Cloneable {
    private String targetPath;
    private String filtering;
    private String mergeId;
    private static int mergeIdCounter = 0;

    @Override
    public Resource clone() {
        try {
            Resource copy = (Resource)super.clone();
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getFiltering() {
        return this.filtering;
    }

    public String getMergeId() {
        return this.mergeId;
    }

    public String getTargetPath() {
        return this.targetPath;
    }

    public void setFiltering(String filtering) {
        this.filtering = filtering;
    }

    public void setMergeId(String mergeId) {
        this.mergeId = mergeId;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public void initMergeId() {
        if (this.getMergeId() == null) {
            this.setMergeId("resource-" + mergeIdCounter++);
        }
    }

    public boolean isFiltering() {
        return this.filtering != null ? Boolean.parseBoolean(this.filtering) : false;
    }

    public void setFiltering(boolean filtering) {
        this.filtering = String.valueOf(filtering);
    }

    @Override
    public String toString() {
        return "Resource {targetPath: " + this.getTargetPath() + ", filtering: " + this.isFiltering() + ", " + super.toString() + "}";
    }
}

