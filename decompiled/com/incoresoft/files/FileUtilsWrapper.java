/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.files;

import com.incoresoft.files.exceptions.CopyFileException;
import com.incoresoft.files.exceptions.DeleteFileException;
import java.io.File;
import org.apache.commons.io.FileUtils;

public class FileUtilsWrapper {
    public static void deleteDirectory(File file) {
        try {
            FileUtils.deleteDirectory(file);
        }
        catch (Exception e) {
            throw new DeleteFileException("Failed to delete file", e);
        }
    }

    public static void copyToDirectory(File source2, File destination) {
        try {
            FileUtils.copyToDirectory(source2, destination);
        }
        catch (Exception e) {
            throw new CopyFileException("Failed to copy file", e);
        }
    }
}

