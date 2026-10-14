/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class CompressionUtils {
    private CompressionUtils() {
    }

    public static InputStream inflate(byte[] deflatedToken) throws DataFormatException {
        return CompressionUtils.inflate(deflatedToken, true);
    }

    public static InputStream inflate(byte[] deflatedToken, boolean nowrap) throws DataFormatException {
        Inflater inflater = new Inflater(true);
        inflater.setInput(deflatedToken);
        byte[] buffer = new byte[deflatedToken.length];
        ByteArrayOutputStream inflatedToken = new ByteArrayOutputStream();
        while (!inflater.finished()) {
            int inflateLen = inflater.inflate(buffer, 0, deflatedToken.length);
            if (inflateLen == 0 && !inflater.finished()) {
                if (!inflater.needsInput()) break;
                throw new DataFormatException("Inflater can not inflate all the token bytes");
            }
            inflatedToken.write(buffer, 0, inflateLen);
        }
        return new ByteArrayInputStream(inflatedToken.toByteArray());
    }

    public static byte[] deflate(byte[] tokenBytes) {
        return CompressionUtils.deflate(tokenBytes, true);
    }

    public static byte[] deflate(byte[] tokenBytes, boolean nowrap) {
        return CompressionUtils.deflate(tokenBytes, 8, nowrap);
    }

    public static byte[] deflate(byte[] tokenBytes, int level, boolean nowrap) {
        Deflater compresser = new Deflater(level, nowrap);
        compresser.setInput(tokenBytes);
        compresser.finish();
        byte[] output = new byte[tokenBytes.length * 2];
        int compressedDataLength = compresser.deflate(output);
        byte[] result = new byte[compressedDataLength];
        System.arraycopy(output, 0, result, 0, compressedDataLength);
        return result;
    }
}

