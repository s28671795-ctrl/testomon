/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.io.File;
import java.net.URI;
import org.apache.maven.building.FileSource;
import org.apache.maven.model.building.ModelSource2;

public class FileModelSource
extends FileSource
implements ModelSource2 {
    public FileModelSource(File pomFile) {
        super(pomFile);
    }

    @Deprecated
    public File getPomFile() {
        return this.getFile();
    }

    @Override
    public ModelSource2 getRelatedSource(String relPath) {
        relPath = relPath.replace('\\', File.separatorChar).replace('/', File.separatorChar);
        File relatedPom = new File(this.getFile().getParentFile(), relPath);
        if (relatedPom.isDirectory()) {
            relatedPom = new File(relatedPom, "pom.xml");
        }
        if (relatedPom.isFile() && relatedPom.canRead()) {
            return new FileModelSource(new File(relatedPom.toURI().normalize()));
        }
        return null;
    }

    @Override
    public URI getLocationURI() {
        return this.getFile().toURI();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FileModelSource)) {
            return false;
        }
        FileModelSource other = (FileModelSource)obj;
        return this.getFile().equals(other.getFile());
    }

    public int hashCode() {
        return this.getFile().hashCode();
    }
}

