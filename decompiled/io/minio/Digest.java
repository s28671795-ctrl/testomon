/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.io.BaseEncoding;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

public class Digest {
    public static final String ZERO_MD5_HASH = "1B2M2Y8AsgTpgAmY7PhCfg==";
    public static final String ZERO_SHA256_HASH = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    private Digest() {
    }

    public static String md5Hash(byte[] data, int length) throws NoSuchAlgorithmException {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        md5Digest.update(data, 0, length);
        return Base64.getEncoder().encodeToString(md5Digest.digest());
    }

    public static String sha256Hash(byte[] data, int length) throws NoSuchAlgorithmException {
        MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");
        sha256Digest.update(data, 0, length);
        return BaseEncoding.base16().encode(sha256Digest.digest()).toLowerCase(Locale.US);
    }

    public static String sha256Hash(String string) throws NoSuchAlgorithmException {
        byte[] data = string.getBytes(StandardCharsets.UTF_8);
        return Digest.sha256Hash(data, data.length);
    }

    @Deprecated
    public static String[] sha256Md5Hashes(Object data, int len) throws NoSuchAlgorithmException, IOException, InsufficientDataException, InternalException {
        MessageDigest sha256Digest = MessageDigest.getInstance("SHA-256");
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        if (data instanceof BufferedInputStream || data instanceof RandomAccessFile) {
            Digest.updateDigests(data, len, sha256Digest, md5Digest);
        } else if (data instanceof byte[]) {
            sha256Digest.update((byte[])data, 0, len);
            md5Digest.update((byte[])data, 0, len);
        } else {
            throw new InternalException("Unknown data source to calculate SHA-256 hash. This should not happen, please report this issue at https://github.com/minio/minio-java/issues", null);
        }
        return new String[]{BaseEncoding.base16().encode(sha256Digest.digest()).toLowerCase(Locale.US), BaseEncoding.base64().encode(md5Digest.digest())};
    }

    private static int updateDigests(Object inputStream2, int len, MessageDigest sha256Digest, MessageDigest md5Digest) throws IOException, InsufficientDataException {
        RandomAccessFile file = null;
        BufferedInputStream stream = null;
        if (inputStream2 instanceof RandomAccessFile) {
            file = (RandomAccessFile)inputStream2;
        } else if (inputStream2 instanceof BufferedInputStream) {
            stream = (BufferedInputStream)inputStream2;
        }
        long pos = 0L;
        if (file != null) {
            pos = file.getFilePointer();
        } else {
            stream.mark(len);
        }
        byte[] buf = new byte[16384];
        int bytesToRead = buf.length;
        int bytesRead = 0;
        int totalBytesRead = 0;
        while (totalBytesRead < len) {
            if (len - totalBytesRead < bytesToRead) {
                bytesToRead = len - totalBytesRead;
            }
            if ((bytesRead = file != null ? file.read(buf, 0, bytesToRead) : stream.read(buf, 0, bytesToRead)) < 0) {
                throw new InsufficientDataException("Insufficient data.  bytes read " + totalBytesRead + " expected " + len);
            }
            if (bytesRead <= 0) continue;
            if (sha256Digest != null) {
                sha256Digest.update(buf, 0, bytesRead);
            }
            if (md5Digest != null) {
                md5Digest.update(buf, 0, bytesRead);
            }
            totalBytesRead += bytesRead;
        }
        if (file != null) {
            file.seek(pos);
        } else {
            stream.reset();
        }
        return totalBytesRead;
    }
}

