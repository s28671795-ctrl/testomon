/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Calendar;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AbstractFileHeader;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.util.InternalZipConstants;

public class Zip4jUtil {
    private static final long DOSTIME_BEFORE_1980 = 0x210000L;
    private static final int MAX_RAW_READ_FULLY_RETRY_ATTEMPTS = 15;

    public static boolean isStringNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isStringNotNullAndNotEmpty(String str) {
        return str != null && str.trim().length() > 0;
    }

    public static boolean createDirectoryIfNotExists(File file) throws ZipException {
        if (file == null) {
            throw new ZipException("output path is null");
        }
        if (file.exists()) {
            if (!file.isDirectory()) {
                throw new ZipException("output directory is not valid");
            }
        } else if (!file.mkdirs()) {
            throw new ZipException("Cannot create output directories");
        }
        return true;
    }

    public static long epochToExtendedDosTime(long time) {
        if (time < 0L) {
            return 0x210000L;
        }
        long dostime = Zip4jUtil.epochToDosTime(time);
        return dostime != 0x210000L ? dostime + (time % 2000L << 32) : 0x210000L;
    }

    private static long epochToDosTime(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int year = cal.get(1);
        if (year < 1980) {
            return 0x210000L;
        }
        return year - 1980 << 25 | cal.get(2) + 1 << 21 | cal.get(5) << 16 | cal.get(11) << 11 | cal.get(12) << 5 | cal.get(13) >> 1;
    }

    public static long dosToExtendedEpochTme(long dosTime) {
        long time = Zip4jUtil.dosToEpochTime(dosTime);
        return time + (dosTime >> 32);
    }

    private static long dosToEpochTime(long dosTime) {
        int sec = (int)(dosTime << 1 & 0x3EL);
        int min2 = (int)(dosTime >> 5 & 0x3FL);
        int hrs = (int)(dosTime >> 11 & 0x1FL);
        int day = (int)(dosTime >> 16 & 0x1FL);
        int mon = (int)((dosTime >> 21 & 0xFL) - 1L);
        int year = (int)((dosTime >> 25 & 0x7FL) + 1980L);
        Calendar cal = Calendar.getInstance();
        cal.set(year, mon, day, hrs, min2, sec);
        cal.set(14, 0);
        return cal.getTime().getTime();
    }

    public static byte[] convertCharArrayToByteArray(char[] charArray, boolean useUtf8Charset) {
        return useUtf8Charset ? Zip4jUtil.convertCharArrayToByteArrayUsingUtf8(charArray) : Zip4jUtil.convertCharArrayToByteArrayUsingDefaultCharset(charArray);
    }

    public static CompressionMethod getCompressionMethod(AbstractFileHeader localFileHeader) throws ZipException {
        if (localFileHeader.getCompressionMethod() != CompressionMethod.AES_INTERNAL_ONLY) {
            return localFileHeader.getCompressionMethod();
        }
        if (localFileHeader.getAesExtraDataRecord() == null) {
            throw new ZipException("AesExtraDataRecord not present in local header for aes encrypted data");
        }
        return localFileHeader.getAesExtraDataRecord().getCompressionMethod();
    }

    public static int readFully(InputStream inputStream2, byte[] bufferToReadInto) throws IOException {
        int readLen = inputStream2.read(bufferToReadInto);
        if (readLen == -1) {
            throw new IOException("Unexpected EOF reached when trying to read stream");
        }
        if (readLen != bufferToReadInto.length && (readLen = Zip4jUtil.readUntilBufferIsFull(inputStream2, bufferToReadInto, readLen)) != bufferToReadInto.length) {
            throw new IOException("Cannot read fully into byte buffer");
        }
        return readLen;
    }

    public static int readFully(InputStream inputStream2, byte[] b, int offset, int length) throws IOException {
        int numberOfBytesRead;
        int currentReadLength;
        if (offset < 0) {
            throw new IllegalArgumentException("Negative offset");
        }
        if (length < 0) {
            throw new IllegalArgumentException("Negative length");
        }
        if (length == 0) {
            return 0;
        }
        if (offset + length > b.length) {
            throw new IllegalArgumentException("Length greater than buffer size");
        }
        for (numberOfBytesRead = 0; numberOfBytesRead != length; numberOfBytesRead += currentReadLength) {
            currentReadLength = inputStream2.read(b, offset + numberOfBytesRead, length - numberOfBytesRead);
            if (currentReadLength != -1) continue;
            if (numberOfBytesRead == 0) {
                return -1;
            }
            return numberOfBytesRead;
        }
        return numberOfBytesRead;
    }

    private static int readUntilBufferIsFull(InputStream inputStream2, byte[] bufferToReadInto, int readLength) throws IOException {
        if (readLength < 0) {
            throw new IOException("Invalid readLength");
        }
        if (readLength == 0) {
            return 0;
        }
        int remainingLength = bufferToReadInto.length - readLength;
        int loopReadLength = 0;
        for (int retryAttempt = 1; readLength < bufferToReadInto.length && loopReadLength != -1 && retryAttempt < 15; ++retryAttempt) {
            loopReadLength = inputStream2.read(bufferToReadInto, readLength, remainingLength);
            if (loopReadLength <= 0) continue;
            readLength += loopReadLength;
            remainingLength -= loopReadLength;
        }
        return readLength;
    }

    private static byte[] convertCharArrayToByteArrayUsingUtf8(char[] charArray) {
        try {
            ByteBuffer buf = InternalZipConstants.CHARSET_UTF_8.encode(CharBuffer.wrap(charArray));
            byte[] bytes = new byte[buf.limit()];
            buf.get(bytes);
            return bytes;
        }
        catch (Exception e) {
            return Zip4jUtil.convertCharArrayToByteArrayUsingDefaultCharset(charArray);
        }
    }

    private static byte[] convertCharArrayToByteArrayUsingDefaultCharset(char[] charArray) {
        byte[] bytes = new byte[charArray.length];
        for (int i = 0; i < charArray.length; ++i) {
            bytes[i] = (byte)charArray[i];
        }
        return bytes;
    }
}

