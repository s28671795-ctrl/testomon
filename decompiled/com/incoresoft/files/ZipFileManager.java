/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.files;

import com.incoresoft.files.exceptions.ZipFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.util.Optional;
import net.lingala.zip4j.ZipFile;

public class ZipFileManager {
    public void zipRegularFile(File source2, File target) {
        if (!source2.exists() || source2.isDirectory()) {
            throw new ZipFileException("Source does not exist or it is a directory");
        }
        try (ZipFile zipFile = new ZipFile(target);){
            Files.createDirectories(target.getParentFile().toPath(), new FileAttribute[0]);
            zipFile.addFile(source2);
        }
        catch (IOException e) {
            throw new ZipFileException("Error while zipping file", e);
        }
    }

    public void zipAllFilesOfDirectory(File source2, File target) {
        if (!source2.exists() || !source2.isDirectory()) {
            throw new ZipFileException("Source is not a directory");
        }
        try (ZipFile zipFile = new ZipFile(target);){
            Files.createDirectories(target.getParentFile().toPath(), new FileAttribute[0]);
            for (File file : Optional.ofNullable(source2.listFiles()).orElse(new File[0])) {
                if (file.isDirectory()) {
                    zipFile.addFolder(file);
                    continue;
                }
                zipFile.addFile(file);
            }
        }
        catch (Exception e) {
            throw new ZipFileException("Error while zipping files", e);
        }
    }

    public void unzip(File source2, File target) {
        try (ZipFile zipFile = new ZipFile(source2);){
            zipFile.extractAll(target.getAbsolutePath());
        }
        catch (Exception e) {
            throw new ZipFileException("Error while zipping files", e);
        }
    }
}

