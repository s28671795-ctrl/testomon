/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.h2.compress.CompressDeflate;
import org.h2.compress.CompressLZF;
import org.h2.compress.CompressNo;
import org.h2.compress.Compressor;
import org.h2.compress.LZFInputStream;
import org.h2.compress.LZFOutputStream;
import org.h2.message.DbException;
import org.h2.util.Bits;
import org.h2.util.StringUtils;
import org.h2.util.Utils;

public class CompressTool {
    private static final int MAX_BUFFER_SIZE = 393216;
    private byte[] buffer;

    private CompressTool() {
    }

    private byte[] getBuffer(int n) {
        if (n > 393216) {
            return Utils.newBytes(n);
        }
        if (this.buffer == null || this.buffer.length < n) {
            this.buffer = Utils.newBytes(n);
        }
        return this.buffer;
    }

    public static CompressTool getInstance() {
        return new CompressTool();
    }

    public byte[] compress(byte[] byArray, String string) {
        int n = byArray.length;
        if (byArray.length < 5) {
            string = "NO";
        }
        Compressor compressor = CompressTool.getCompressor(string);
        byte[] byArray2 = this.getBuffer((n < 100 ? n + 100 : n) * 2);
        int n2 = CompressTool.compress(byArray, byArray.length, compressor, byArray2);
        return Utils.copyBytes(byArray2, n2);
    }

    private static int compress(byte[] byArray, int n, Compressor compressor, byte[] byArray2) {
        byArray2[0] = (byte)compressor.getAlgorithm();
        int n2 = 1 + CompressTool.writeVariableInt(byArray2, 1, n);
        int n3 = compressor.compress(byArray, 0, n, byArray2, n2);
        if (n3 > n + n2 || n3 <= 0) {
            byArray2[0] = 0;
            System.arraycopy(byArray, 0, byArray2, n2, n);
            n3 = n + n2;
        }
        return n3;
    }

    public byte[] expand(byte[] byArray) {
        if (byArray.length == 0) {
            throw DbException.get(90104);
        }
        byte by = byArray[0];
        Compressor compressor = CompressTool.getCompressor(by);
        try {
            int n = CompressTool.readVariableInt(byArray, 1);
            int n2 = 1 + CompressTool.getVariableIntLength(n);
            byte[] byArray2 = Utils.newBytes(n);
            compressor.expand(byArray, n2, byArray.length - n2, byArray2, 0, n);
            return byArray2;
        }
        catch (Exception exception) {
            throw DbException.get(90104, exception, new String[0]);
        }
    }

    public static void expand(byte[] byArray, byte[] byArray2, int n) {
        byte by = byArray[0];
        Compressor compressor = CompressTool.getCompressor(by);
        try {
            int n2 = CompressTool.readVariableInt(byArray, 1);
            int n3 = 1 + CompressTool.getVariableIntLength(n2);
            compressor.expand(byArray, n3, byArray.length - n3, byArray2, n, n2);
        }
        catch (Exception exception) {
            throw DbException.get(90104, exception, new String[0]);
        }
    }

    public static int readVariableInt(byte[] byArray, int n) {
        int n2;
        if ((n2 = byArray[n++] & 0xFF) < 128) {
            return n2;
        }
        if (n2 < 192) {
            return ((n2 & 0x3F) << 8) + (byArray[n] & 0xFF);
        }
        if (n2 < 224) {
            return ((n2 & 0x1F) << 16) + ((byArray[n++] & 0xFF) << 8) + (byArray[n] & 0xFF);
        }
        if (n2 < 240) {
            return ((n2 & 0xF) << 24) + ((byArray[n++] & 0xFF) << 16) + ((byArray[n++] & 0xFF) << 8) + (byArray[n] & 0xFF);
        }
        return Bits.readInt(byArray, n);
    }

    public static int writeVariableInt(byte[] byArray, int n, int n2) {
        if (n2 < 0) {
            byArray[n++] = -16;
            Bits.writeInt(byArray, n, n2);
            return 5;
        }
        if (n2 < 128) {
            byArray[n] = (byte)n2;
            return 1;
        }
        if (n2 < 16384) {
            byArray[n++] = (byte)(0x80 | n2 >> 8);
            byArray[n] = (byte)n2;
            return 2;
        }
        if (n2 < 0x200000) {
            byArray[n++] = (byte)(0xC0 | n2 >> 16);
            byArray[n++] = (byte)(n2 >> 8);
            byArray[n] = (byte)n2;
            return 3;
        }
        if (n2 < 0x10000000) {
            Bits.writeInt(byArray, n, n2 | 0xE0000000);
            return 4;
        }
        byArray[n++] = -16;
        Bits.writeInt(byArray, n, n2);
        return 5;
    }

    public static int getVariableIntLength(int n) {
        if (n < 0) {
            return 5;
        }
        if (n < 128) {
            return 1;
        }
        if (n < 16384) {
            return 2;
        }
        if (n < 0x200000) {
            return 3;
        }
        if (n < 0x10000000) {
            return 4;
        }
        return 5;
    }

    private static Compressor getCompressor(String string) {
        if (string == null) {
            string = "LZF";
        }
        int n = string.indexOf(32);
        String string2 = null;
        if (n > 0) {
            string2 = string.substring(n + 1);
            string = string.substring(0, n);
        }
        int n2 = CompressTool.getCompressAlgorithm(string);
        Compressor compressor = CompressTool.getCompressor(n2);
        compressor.setOptions(string2);
        return compressor;
    }

    private static int getCompressAlgorithm(String string) {
        if ("NO".equals(string = StringUtils.toUpperEnglish(string))) {
            return 0;
        }
        if ("LZF".equals(string)) {
            return 1;
        }
        if ("DEFLATE".equals(string)) {
            return 2;
        }
        throw DbException.get(90103, string);
    }

    private static Compressor getCompressor(int n) {
        switch (n) {
            case 0: {
                return new CompressNo();
            }
            case 1: {
                return new CompressLZF();
            }
            case 2: {
                return new CompressDeflate();
            }
        }
        throw DbException.get(90103, Integer.toString(n));
    }

    public static OutputStream wrapOutputStream(OutputStream outputStream2, String string, String string2) {
        try {
            if ("GZIP".equals(string)) {
                outputStream2 = new GZIPOutputStream(outputStream2);
            } else if ("ZIP".equals(string)) {
                ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream2);
                zipOutputStream.putNextEntry(new ZipEntry(string2));
                outputStream2 = zipOutputStream;
            } else if ("DEFLATE".equals(string)) {
                outputStream2 = new DeflaterOutputStream(outputStream2);
            } else if ("LZF".equals(string)) {
                outputStream2 = new LZFOutputStream(outputStream2);
            } else if (string != null) {
                throw DbException.get(90103, string);
            }
            return outputStream2;
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
    }

    public static InputStream wrapInputStream(InputStream inputStream2, String string, String string2) {
        try {
            if ("GZIP".equals(string)) {
                inputStream2 = new GZIPInputStream(inputStream2);
            } else if ("ZIP".equals(string)) {
                ZipEntry zipEntry;
                ZipInputStream zipInputStream = new ZipInputStream(inputStream2);
                do {
                    if ((zipEntry = zipInputStream.getNextEntry()) != null) continue;
                    return null;
                } while (!string2.equals(zipEntry.getName()));
                inputStream2 = zipInputStream;
            } else if ("DEFLATE".equals(string)) {
                inputStream2 = new InflaterInputStream(inputStream2);
            } else if ("LZF".equals(string)) {
                inputStream2 = new LZFInputStream(inputStream2);
            } else if (string != null) {
                throw DbException.get(90103, string);
            }
            return inputStream2;
        }
        catch (IOException iOException) {
            throw DbException.convertIOException(iOException, null);
        }
    }
}

