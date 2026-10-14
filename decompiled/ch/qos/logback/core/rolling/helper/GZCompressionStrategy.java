/*
 * Decompiled with CFR 0.152.
 */
package ch.qos.logback.core.rolling.helper;

import ch.qos.logback.core.rolling.helper.CompressionStrategyBase;
import ch.qos.logback.core.status.ErrorStatus;
import ch.qos.logback.core.status.WarnStatus;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

public class GZCompressionStrategy
extends CompressionStrategyBase {
    @Override
    public void compress(String originalFileName, String compressedFileName, String innerEntryName) {
        File gzedFile;
        File file2gz = new File(originalFileName);
        if (!file2gz.exists()) {
            this.addStatus(new WarnStatus("The file to compress named [" + originalFileName + "] does not exist.", this));
            return;
        }
        if (!((String)compressedFileName).endsWith(".gz")) {
            compressedFileName = (String)compressedFileName + ".gz";
        }
        if ((gzedFile = new File((String)compressedFileName)).exists()) {
            this.addWarn("The target compressed file named [" + (String)compressedFileName + "] exist already. Aborting file compression.");
            return;
        }
        this.addInfo("GZ compressing [" + String.valueOf(file2gz) + "] as [" + String.valueOf(gzedFile) + "]");
        this.createMissingTargetDirsIfNecessary(gzedFile);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(originalFileName));
             GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream((String)compressedFileName));){
            int n;
            byte[] inbuf = new byte[8192];
            while ((n = bis.read(inbuf)) != -1) {
                gzos.write(inbuf, 0, n);
            }
            this.addInfo("Done GZ compressing [" + String.valueOf(file2gz) + "] as [" + String.valueOf(gzedFile) + "]");
        }
        catch (Exception e) {
            this.addStatus(new ErrorStatus("Error occurred while compressing [" + originalFileName + "] into [" + (String)compressedFileName + "].", this, e));
        }
        if (!file2gz.delete()) {
            this.addStatus(new WarnStatus("Could not delete [" + originalFileName + "].", this));
        }
    }
}

