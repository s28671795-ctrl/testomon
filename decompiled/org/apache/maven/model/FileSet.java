/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import org.apache.maven.model.PatternSet;

public class FileSet
extends PatternSet
implements Serializable,
Cloneable {
    private String directory;

    @Override
    public FileSet clone() {
        try {
            FileSet copy = (FileSet)super.clone();
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return "FileSet {directory: " + this.getDirectory() + ", " + super.toString() + "}";
    }
}

