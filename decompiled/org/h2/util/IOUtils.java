/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import org.h2.engine.SysProperties;
import org.h2.mvstore.DataUtils;
import org.h2.store.fs.FileUtils;

public class IOUtils {
    private IOUtils() {
    }

    public static void closeSilently(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                IOUtils.trace("closeSilently", null, autoCloseable);
                autoCloseable.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static void skipFully(InputStream inputStream2, long l) throws IOException {
        try {
            while (l > 0L) {
                long l2 = inputStream2.skip(l);
                if (l2 <= 0L) {
                    throw new EOFException();
                }
                l -= l2;
            }
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
    }

    public static void skipFully(Reader reader, long l) throws IOException {
        try {
            while (l > 0L) {
                long l2 = reader.skip(l);
                if (l2 <= 0L) {
                    throw new EOFException();
                }
                l -= l2;
            }
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
    }

    public static long copyAndClose(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        try {
            long l = IOUtils.copyAndCloseInput(inputStream2, outputStream2);
            outputStream2.close();
            long l2 = l;
            return l2;
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
        finally {
            IOUtils.closeSilently(outputStream2);
        }
    }

    public static long copyAndCloseInput(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        try {
            long l = IOUtils.copy(inputStream2, outputStream2);
            return l;
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
        finally {
            IOUtils.closeSilently(inputStream2);
        }
    }

    public static long copy(InputStream inputStream2, OutputStream outputStream2) throws IOException {
        return IOUtils.copy(inputStream2, outputStream2, Long.MAX_VALUE);
    }

    public static long copy(InputStream inputStream2, OutputStream outputStream2, long l) throws IOException {
        try {
            long l2 = 0L;
            int n = (int)Math.min(l, 4096L);
            byte[] byArray = new byte[n];
            while (l > 0L && (n = inputStream2.read(byArray, 0, n)) >= 0) {
                if (outputStream2 != null) {
                    outputStream2.write(byArray, 0, n);
                }
                l2 += (long)n;
                l -= (long)n;
                n = (int)Math.min(l, 4096L);
            }
            return l2;
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
    }

    public static long copy(FileChannel fileChannel, OutputStream outputStream2) throws IOException {
        return IOUtils.copy(fileChannel, outputStream2, Long.MAX_VALUE);
    }

    public static long copy(FileChannel fileChannel, OutputStream outputStream2, long l) throws IOException {
        try {
            int n;
            long l2 = 0L;
            byte[] byArray = new byte[(int)Math.min(l, 4096L)];
            ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
            while (l > 0L && (n = fileChannel.read(byteBuffer, l2)) >= 0) {
                if (outputStream2 != null) {
                    outputStream2.write(byArray, 0, n);
                }
                l2 += (long)n;
                byteBuffer.rewind();
                if ((l -= (long)n) >= (long)byteBuffer.limit()) continue;
                byteBuffer.limit((int)l);
            }
            return l2;
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
    }

    public static long copyAndCloseInput(Reader reader, Writer writer, long l) throws IOException {
        try {
            long l2 = 0L;
            int n = (int)Math.min(l, 4096L);
            char[] cArray = new char[n];
            while (l > 0L && (n = reader.read(cArray, 0, n)) >= 0) {
                if (writer != null) {
                    writer.write(cArray, 0, n);
                }
                l2 += (long)n;
                l -= (long)n;
                n = (int)Math.min(l, 4096L);
            }
            long l3 = l2;
            return l3;
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
        finally {
            reader.close();
        }
    }

    public static byte[] readBytesAndClose(InputStream inputStream2, int n) throws IOException {
        try {
            if (n <= 0) {
                n = Integer.MAX_VALUE;
            }
            int n2 = Math.min(4096, n);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(n2);
            IOUtils.copy(inputStream2, (OutputStream)byteArrayOutputStream, (long)n);
            byte[] byArray = byteArrayOutputStream.toByteArray();
            return byArray;
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
        finally {
            inputStream2.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String readStringAndClose(Reader reader, int n) throws IOException {
        try {
            if (n <= 0) {
                n = Integer.MAX_VALUE;
            }
            int n2 = Math.min(4096, n);
            StringWriter stringWriter = new StringWriter(n2);
            IOUtils.copyAndCloseInput(reader, stringWriter, n);
            String string = stringWriter.toString();
            return string;
        }
        finally {
            reader.close();
        }
    }

    public static int readFully(InputStream inputStream2, byte[] byArray, int n) throws IOException {
        try {
            int n2;
            int n3 = 0;
            for (int i = Math.min(n, byArray.length); i > 0 && (n2 = inputStream2.read(byArray, n3, i)) >= 0; i -= n2) {
                n3 += n2;
            }
            return n3;
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
    }

    public static int readFully(Reader reader, char[] cArray, int n) throws IOException {
        try {
            int n2;
            int n3 = 0;
            for (int i = Math.min(n, cArray.length); i > 0 && (n2 = reader.read(cArray, n3, i)) >= 0; i -= n2) {
                n3 += n2;
            }
            return n3;
        }
        catch (Exception exception) {
            throw DataUtils.convertToIOException(exception);
        }
    }

    public static Reader getReader(InputStream inputStream2) {
        return inputStream2 == null ? null : new BufferedReader(new InputStreamReader(inputStream2, StandardCharsets.UTF_8));
    }

    public static Writer getBufferedWriter(OutputStream outputStream2) {
        return outputStream2 == null ? null : new BufferedWriter(new OutputStreamWriter(outputStream2, StandardCharsets.UTF_8));
    }

    public static Reader getAsciiReader(InputStream inputStream2) {
        return inputStream2 == null ? null : new InputStreamReader(inputStream2, StandardCharsets.US_ASCII);
    }

    public static void trace(String string, String string2, Object object) {
        if (SysProperties.TRACE_IO) {
            System.out.println("IOUtils." + string + ' ' + string2 + ' ' + object);
        }
    }

    public static InputStream getInputStreamFromString(String string) {
        if (string == null) {
            return null;
        }
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    public static void copyFiles(String string, String string2) throws IOException {
        InputStream inputStream2 = FileUtils.newInputStream(string);
        OutputStream outputStream2 = FileUtils.newOutputStream(string2, false);
        IOUtils.copyAndClose(inputStream2, outputStream2);
    }

    public static String nameSeparatorsToNative(String string) {
        return File.separatorChar == '/' ? string.replace('\\', '/') : string.replace('/', '\\');
    }
}

