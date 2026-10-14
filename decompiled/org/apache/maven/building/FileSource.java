/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.maven.building.Source;

public class FileSource
implements Source {
    private final File file;

    public FileSource(File file) {
        this.file = Objects.requireNonNull(file, "file cannot be null").getAbsoluteFile();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    @Override
    public String getLocation() {
        return this.file.getPath();
    }

    public File getFile() {
        return this.file;
    }

    public String toString() {
        return this.getLocation();
    }
}

