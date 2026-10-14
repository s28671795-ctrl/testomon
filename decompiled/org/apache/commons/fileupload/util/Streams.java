/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.fileupload.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.fileupload.InvalidFileNameException;
import org.apache.commons.io.IOUtils;

public final class Streams {
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private Streams() {
    }

    public static long copy(InputStream inputStream2, OutputStream outputStream2, boolean closeOutputStream) throws IOException {
        return Streams.copy(inputStream2, outputStream2, closeOutputStream, new byte[8192]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long copy(InputStream inputStream2, OutputStream outputStream2, boolean closeOutputStream, byte[] buffer) throws IOException {
        OutputStream out = outputStream2;
        InputStream in = inputStream2;
        try {
            int res;
            long total = 0L;
            while ((res = in.read(buffer)) != -1) {
                if (res <= 0) continue;
                total += (long)res;
                if (out == null) continue;
                out.write(buffer, 0, res);
            }
            if (out != null) {
                if (closeOutputStream) {
                    out.close();
                } else {
                    out.flush();
                }
                out = null;
            }
            in.close();
            in = null;
            long l = total;
            return l;
        }
        finally {
            IOUtils.closeQuietly(in);
            if (closeOutputStream) {
                IOUtils.closeQuietly(out);
            }
        }
    }

    public static String asString(InputStream inputStream2) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Streams.copy(inputStream2, baos, true);
        return baos.toString();
    }

    public static String asString(InputStream inputStream2, String encoding) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Streams.copy(inputStream2, baos, true);
        return baos.toString(encoding);
    }

    public static String checkFileName(String fileName) {
        if (fileName != null && fileName.indexOf(0) != -1) {
            StringBuilder sb = new StringBuilder();
            block3: for (int i = 0; i < fileName.length(); ++i) {
                char c = fileName.charAt(i);
                switch (c) {
                    case '\u0000': {
                        sb.append("\\0");
                        continue block3;
                    }
                    default: {
                        sb.append(c);
                    }
                }
            }
            throw new InvalidFileNameException(fileName, "Invalid file name: " + sb);
        }
        return fileName;
    }
}

