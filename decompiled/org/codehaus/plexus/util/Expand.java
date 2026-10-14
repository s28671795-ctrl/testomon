/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.codehaus.plexus.util.FileUtils;

public class Expand {
    private File dest;
    private File source;
    private boolean overwrite = true;

    public void execute() throws Exception {
        this.expandFile(this.source, this.dest);
    }

    protected void expandFile(File srcF, File dir) throws Exception {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(srcF.toPath(), new OpenOption[0]));){
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                this.extractFile(srcF, dir, zis, ze.getName(), new Date(ze.getTime()), ze.isDirectory());
                ze = zis.getNextEntry();
            }
        }
        catch (IOException ioe) {
            throw new Exception("Error while expanding " + srcF.getPath(), ioe);
        }
    }

    protected void extractFile(File srcF, File dir, InputStream compressedInputStream, String entryName, Date entryDate, boolean isDirectory) throws Exception {
        File f = FileUtils.resolveFile(dir, entryName);
        if (!f.getAbsolutePath().startsWith(dir.getAbsolutePath())) {
            throw new IOException("Entry '" + entryName + "' outside the target directory.");
        }
        try {
            if (!this.overwrite && f.exists() && f.lastModified() >= entryDate.getTime()) {
                return;
            }
            File dirF = f.getParentFile();
            dirF.mkdirs();
            if (isDirectory) {
                f.mkdirs();
            } else {
                byte[] buffer = new byte[65536];
                try (OutputStream fos = Files.newOutputStream(f.toPath(), new OpenOption[0]);){
                    int length = compressedInputStream.read(buffer);
                    while (length >= 0) {
                        fos.write(buffer, 0, length);
                        length = compressedInputStream.read(buffer);
                    }
                }
            }
            f.setLastModified(entryDate.getTime());
        }
        catch (FileNotFoundException ex) {
            throw new Exception("Can't extract file " + srcF.getPath(), ex);
        }
    }

    public void setDest(File d) {
        this.dest = d;
    }

    public void setSrc(File s2) {
        this.source = s2;
    }

    public void setOverwrite(boolean b) {
        this.overwrite = b;
    }
}

