/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.maven.model.BuildBase;
import org.apache.maven.model.Extension;

public class Build
extends BuildBase
implements Serializable,
Cloneable {
    private String sourceDirectory;
    private String scriptSourceDirectory;
    private String testSourceDirectory;
    private String outputDirectory;
    private String testOutputDirectory;
    private List<Extension> extensions;

    public void addExtension(Extension extension) {
        this.getExtensions().add(extension);
    }

    @Override
    public Build clone() {
        try {
            Build copy = (Build)super.clone();
            if (this.extensions != null) {
                copy.extensions = new ArrayList<Extension>();
                for (Extension item : this.extensions) {
                    copy.extensions.add(item.clone());
                }
            }
            return copy;
        }
        catch (Exception ex) {
            throw (RuntimeException)new UnsupportedOperationException(this.getClass().getName() + " does not support clone()").initCause(ex);
        }
    }

    public List<Extension> getExtensions() {
        if (this.extensions == null) {
            this.extensions = new ArrayList<Extension>();
        }
        return this.extensions;
    }

    public String getOutputDirectory() {
        return this.outputDirectory;
    }

    public String getScriptSourceDirectory() {
        return this.scriptSourceDirectory;
    }

    public String getSourceDirectory() {
        return this.sourceDirectory;
    }

    public String getTestOutputDirectory() {
        return this.testOutputDirectory;
    }

    public String getTestSourceDirectory() {
        return this.testSourceDirectory;
    }

    public void removeExtension(Extension extension) {
        this.getExtensions().remove(extension);
    }

    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void setScriptSourceDirectory(String scriptSourceDirectory) {
        this.scriptSourceDirectory = scriptSourceDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public void setTestOutputDirectory(String testOutputDirectory) {
        this.testOutputDirectory = testOutputDirectory;
    }

    public void setTestSourceDirectory(String testSourceDirectory) {
        this.testSourceDirectory = testSourceDirectory;
    }

    @Override
    public String toString() {
        return "Build {" + super.toString() + "}";
    }
}

