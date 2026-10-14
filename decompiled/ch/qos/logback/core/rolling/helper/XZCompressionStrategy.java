/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.tukaani.xz.FilterOptions
 *  org.tukaani.xz.LZMA2Options
 *  org.tukaani.xz.XZOutputStream
 */
package ch.qos.logback.core.rolling.helper;

import ch.qos.logback.core.rolling.helper.CompressionStrategyBase;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.tukaani.xz.FilterOptions;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;

public class XZCompressionStrategy
extends CompressionStrategyBase {
    @Override
    public void compress(String nameOfFile2xz, String nameOfxzedFile, String innerEntryName) {
        File xzedFile;
        File file2xz = new File(nameOfFile2xz);
        if (!file2xz.exists()) {
            this.addWarn("The file to compress named [" + nameOfFile2xz + "] does not exist.");
            return;
        }
        if (!((String)nameOfxzedFile).endsWith(".xz")) {
            nameOfxzedFile = (String)nameOfxzedFile + ".xz";
        }
        if ((xzedFile = new File((String)nameOfxzedFile)).exists()) {
            this.addWarn("The target compressed file named [" + (String)nameOfxzedFile + "] exist already. Aborting file compression.");
            return;
        }
        this.addInfo("XZ compressing [" + String.valueOf(file2xz) + "] as [" + String.valueOf(xzedFile) + "]");
        this.createMissingTargetDirsIfNecessary(xzedFile);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(nameOfFile2xz));
             XZOutputStream xzos = new XZOutputStream((OutputStream)new FileOutputStream((String)nameOfxzedFile), (FilterOptions)new LZMA2Options());){
            int n;
            byte[] inbuf = new byte[8192];
            while ((n = bis.read(inbuf)) != -1) {
                xzos.write(inbuf, 0, n);
            }
        }
        catch (Exception e) {
            this.addError("Error occurred while compressing [" + nameOfFile2xz + "] into [" + (String)nameOfxzedFile + "].", e);
        }
        if (!file2xz.delete()) {
            this.addWarn("Could not delete [" + nameOfFile2xz + "].");
        }
    }
}

