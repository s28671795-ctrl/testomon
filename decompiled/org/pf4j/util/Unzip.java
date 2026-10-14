/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.pf4j.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Unzip {
    private static final Logger log = LoggerFactory.getLogger(Unzip.class);
    private File destination;
    private File source;

    public Unzip() {
    }

    public Unzip(File source2, File destination) {
        this.source = source2;
        this.destination = destination;
    }

    public void setSource(File source2) {
        this.source = source2;
    }

    public void setDestination(File destination) {
        this.destination = destination;
    }

    public void extract() throws IOException {
        log.debug("Extract content of '{}' to '{}'", (Object)this.source, (Object)this.destination);
        if (this.destination.exists() && this.destination.isDirectory()) {
            FileUtils.delete(this.destination.toPath());
        }
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(this.source));){
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                File file = new File(this.destination, zipEntry.getName());
                File dir = new File(file.getParent());
                Unzip.mkdirsOrThrow(dir);
                if (zipEntry.isDirectory()) {
                    Unzip.mkdirsOrThrow(file);
                    continue;
                }
                byte[] buffer = new byte[1024];
                try (FileOutputStream fos = new FileOutputStream(file);){
                    int length;
                    while ((length = zipInputStream.read(buffer)) >= 0) {
                        fos.write(buffer, 0, length);
                    }
                }
            }
        }
    }

    private static void mkdirsOrThrow(File dir) throws IOException {
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("Failed to create directory " + dir);
        }
    }
}

