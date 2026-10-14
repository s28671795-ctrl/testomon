/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

public class CrcUtil {
    private static final int BUF_SIZE = 16384;

    public static long computeFileCrc(File inputFile, ProgressMonitor progressMonitor) throws IOException {
        if (inputFile == null || !inputFile.exists() || !inputFile.canRead()) {
            throw new ZipException("input file is null or does not exist or cannot read. Cannot calculate CRC for the file");
        }
        byte[] buff = new byte[16384];
        CRC32 crc32 = new CRC32();
        try (FileInputStream inputStream2 = new FileInputStream(inputFile);){
            int readLen;
            while ((readLen = ((InputStream)inputStream2).read(buff)) != -1) {
                crc32.update(buff, 0, readLen);
                if (progressMonitor == null) continue;
                progressMonitor.updateWorkCompleted(readLen);
                if (!progressMonitor.isCancelAllTasks()) continue;
                progressMonitor.setResult(ProgressMonitor.Result.CANCELLED);
                progressMonitor.setState(ProgressMonitor.State.READY);
                long l = 0L;
                return l;
            }
            long l = crc32.getValue();
            return l;
        }
    }
}

