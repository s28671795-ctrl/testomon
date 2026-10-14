/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.rolling.helper;

import ch.qos.logback.core.rolling.helper.CompressionMode;
import ch.qos.logback.core.rolling.helper.CompressionStrategyBase;
import ch.qos.logback.core.rolling.helper.Compressor;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.WarnStatus;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompressionStrategy
extends CompressionStrategyBase {
    static final int BUFFER_SIZE = 8192;

    @Override
    public void compress(String originalFileName, String compressedFileName, String innerEntryName) {
        File zippedFile;
        File file2zip = new File(originalFileName);
        if (!file2zip.exists()) {
            this.addStatus(new WarnStatus("The file to compress named [" + originalFileName + "] does not exist.", this));
            return;
        }
        if (innerEntryName == null) {
            this.addStatus(new WarnStatus("The innerEntryName parameter cannot be null", this));
            return;
        }
        if (!((String)compressedFileName).endsWith(".zip")) {
            compressedFileName = (String)compressedFileName + ".zip";
        }
        if ((zippedFile = new File((String)compressedFileName)).exists()) {
            this.addStatus(new WarnStatus("The target compressed file named [" + (String)compressedFileName + "] exist already.", this));
            return;
        }
        this.addInfo("ZIP compressing [" + String.valueOf(file2zip) + "] as [" + String.valueOf(zippedFile) + "]");
        this.createMissingTargetDirsIfNecessary(zippedFile);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(originalFileName));
             ZipOutputStream zos = new ZipOutputStream(new FileOutputStream((String)compressedFileName));){
            int n;
            ZipEntry zipEntry = this.computeZipEntry(innerEntryName);
            zos.putNextEntry(zipEntry);
            byte[] inbuf = new byte[8192];
            while ((n = bis.read(inbuf)) != -1) {
                zos.write(inbuf, 0, n);
            }
            this.addInfo("Done ZIP compressing [" + String.valueOf(file2zip) + "] as [" + String.valueOf(zippedFile) + "]");
        }
        catch (Exception e) {
            this.addStatus(new ErrorStatus("Error occurred while compressing [" + originalFileName + "] into [" + (String)compressedFileName + "].", this, e));
        }
        if (!file2zip.delete()) {
            this.addStatus(new WarnStatus("Could not delete [" + originalFileName + "].", this));
        }
    }

    ZipEntry computeZipEntry(String filename) {
        String nameOfFileNestedWithinArchive = Compressor.computeFileNameStrWithoutCompSuffix(filename, CompressionMode.ZIP);
        return new ZipEntry(nameOfFileNestedWithinArchive);
    }
}

