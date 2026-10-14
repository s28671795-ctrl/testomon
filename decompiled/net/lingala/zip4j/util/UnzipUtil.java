/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.inputstream.NumberedSplitFileInputStream;
import net.lingala.zip4j.io.inputstream.SplitFileInputStream;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.io.inputstream.ZipStandardSplitFileInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.util.FileUtils;

public class UnzipUtil {
    public static ZipInputStream createZipInputStream(ZipModel zipModel, FileHeader fileHeader, char[] password) throws IOException {
        SplitFileInputStream splitInputStream = null;
        try {
            splitInputStream = UnzipUtil.createSplitInputStream(zipModel);
            splitInputStream.prepareExtractionForFileHeader(fileHeader);
            ZipInputStream zipInputStream = new ZipInputStream((InputStream)splitInputStream, password);
            if (zipInputStream.getNextEntry(fileHeader, false) == null) {
                throw new ZipException("Could not locate local file header for corresponding file header");
            }
            return zipInputStream;
        }
        catch (IOException e) {
            if (splitInputStream != null) {
                splitInputStream.close();
            }
            throw e;
        }
    }

    public static void applyFileAttributes(FileHeader fileHeader, File file) {
        try {
            Path path = file.toPath();
            FileUtils.setFileAttributes(path, fileHeader.getExternalFileAttributes());
            FileUtils.setFileLastModifiedTime(path, fileHeader.getLastModifiedTime());
        }
        catch (NoSuchMethodError e) {
            FileUtils.setFileLastModifiedTimeWithoutNio(file, fileHeader.getLastModifiedTime());
        }
    }

    public static SplitFileInputStream createSplitInputStream(ZipModel zipModel) throws IOException {
        File zipFile = zipModel.getZipFile();
        if (zipFile.getName().endsWith(".zip.001")) {
            return new NumberedSplitFileInputStream(zipModel.getZipFile());
        }
        return new ZipStandardSplitFileInputStream(zipModel.getZipFile(), zipModel.isSplitArchive(), zipModel.getEndOfCentralDirectoryRecord().getNumberOfThisDisk());
    }
}

