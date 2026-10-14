/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.util;

import net.lingala.zip4j.headers.VersionMadeBy;
import net.lingala.zip4j.headers.VersionNeededToExtract;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import net.lingala.zip4j.util.FileUtils;
import net.lingala.zip4j.util.RawIO;

public class ZipVersionUtils {
    public static int determineVersionMadeBy(ZipParameters zipParameters, RawIO rawIO) {
        byte[] versionMadeBy = new byte[]{VersionMadeBy.SPECIFICATION_VERSION.getCode(), VersionMadeBy.UNIX.getCode()};
        if (FileUtils.isWindows() && !zipParameters.isUnixMode()) {
            versionMadeBy[1] = VersionMadeBy.WINDOWS.getCode();
        }
        return rawIO.readShortLittleEndian(versionMadeBy, 0);
    }

    public static VersionNeededToExtract determineVersionNeededToExtract(ZipParameters zipParameters) {
        VersionNeededToExtract versionRequired = VersionNeededToExtract.DEFAULT;
        if (zipParameters.getCompressionMethod() == CompressionMethod.DEFLATE) {
            versionRequired = VersionNeededToExtract.DEFLATE_COMPRESSED;
        }
        if (zipParameters.getEntrySize() > 0xFFFFFFFFL) {
            versionRequired = VersionNeededToExtract.ZIP_64_FORMAT;
        }
        if (zipParameters.isEncryptFiles() && zipParameters.getEncryptionMethod().equals((Object)EncryptionMethod.AES)) {
            versionRequired = VersionNeededToExtract.AES_ENCRYPTED;
        }
        return versionRequired;
    }
}

