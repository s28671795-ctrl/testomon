/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.function.IOConsumer;
import org.apache.commons.io.output.AppendableWriter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.StringBuilderWriter;

public class IOUtils {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final char DIR_SEPARATOR = File.separatorChar;
    public static final char DIR_SEPARATOR_UNIX = '/';
    public static final char DIR_SEPARATOR_WINDOWS = '\\';
    public static final int EOF = -1;
    public static final String LINE_SEPARATOR;
    public static final String LINE_SEPARATOR_UNIX = "\n";
    public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
    private static final int SKIP_BUFFER_SIZE = 2048;
    private static byte[] SKIP_BYTE_BUFFER;
    private static char[] SKIP_CHAR_BUFFER;

    public static BufferedInputStream buffer(InputStream inputStream2) {
        Objects.requireNonNull(inputStream2, "inputStream");
        return inputStream2 instanceof BufferedInputStream ? (BufferedInputStream)inputStream2 : new BufferedInputStream(inputStream2);
    }

    public static BufferedInputStream buffer(InputStream inputStream2, int size) {
        Objects.requireNonNull(inputStream2, "inputStream");
        return inputStream2 instanceof BufferedInputStream ? (BufferedInputStream)inputStream2 : new BufferedInputStream(inputStream2, size);
    }

    public static BufferedOutputStream buffer(OutputStream outputStream2) {
        Objects.requireNonNull(outputStream2, "outputStream");
        return outputStream2 instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream2 : new BufferedOutputStream(outputStream2);
    }

    public static BufferedOutputStream buffer(OutputStream outputStream2, int size) {
        Objects.requireNonNull(outputStream2, "outputStream");
        return outputStream2 instanceof BufferedOutputStream ? (BufferedOutputStream)outputStream2 : new BufferedOutputStream(outputStream2, size);
    }

    public static BufferedReader buffer(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
    }

    public static BufferedReader buffer(Reader reader, int size) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, size);
    }

    public static BufferedWriter buffer(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer);
    }

    public static BufferedWriter buffer(Writer writer, int size) {
        return writer instanceof BufferedWriter ? (BufferedWriter)writer : new BufferedWriter(writer, size);
    }

    @Deprecated
    public static void closeQuietly(Closeable closeable) {
        IOUtils.closeQuietly(closeable, (Consumer<IOException>)null);
    }

    public static void closeQuietly(Closeable closeable, Consumer<IOException> consumer) {
        block3: {
            if (closeable != null) {
                try {
                    closeable.close();
                }
                catch (IOException e) {
                    if (consumer == null) break block3;
                    consumer.accept(e);
                }
            }
        }
    }

    public static void close(Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }
    }

    public static void close(Closeable closeable, IOConsumer<IOException> consumer) throws IOException {
        block3: {
            if (closeable != null) {
                try {
                    closeable.close();
                }
                catch (IOException e) {
                    if (consumer == null) break block3;
                    consumer.accept(e);
                }
            }
        }
    }

    public static void close(URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection)conn).disconnect();
        }
    }

    @Deprecated
    public static void closeQuietly(Closeable ... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            IOUtils.closeQuietly(closeable);
        }
    }

    @Deprecated
    public static void closeQuietly(InputStream input) {
        IOUtils.closeQuietly((Closeable)input);
    }

    @Deprecated
    public static void closeQuietly(OutputStream output) {
        IOUtils.closeQuietly((Closeable)output);
    }

    @Deprecated
    public static void closeQuietly(Reader input) {
        IOUtils.closeQuietly((Closeable)input);
    }

    @Deprecated
    public static void closeQuietly(Selector selector) {
        IOUtils.closeQuietly((Closeable)selector);
    }

    @Deprecated
    public static void closeQuietly(ServerSocket serverSocket) {
        IOUtils.closeQuietly((Closeable)serverSocket);
    }

    @Deprecated
    public static void closeQuietly(Socket socket) {
        IOUtils.closeQuietly((Closeable)socket);
    }

    @Deprecated
    public static void closeQuietly(Writer output) {
        IOUtils.closeQuietly((Closeable)output);
    }

    public static boolean contentEquals(InputStream input1, InputStream input2) throws IOException {
        if (input1 == input2) {
            return true;
        }
        if (input1 == null ^ input2 == null) {
            return false;
        }
        BufferedInputStream bufferedInput1 = IOUtils.buffer(input1);
        BufferedInputStream bufferedInput2 = IOUtils.buffer(input2);
        int ch = bufferedInput1.read();
        while (-1 != ch) {
            int ch2 = bufferedInput2.read();
            if (ch != ch2) {
                return false;
            }
            ch = bufferedInput1.read();
        }
        return bufferedInput2.read() == -1;
    }

    public static boolean contentEquals(Reader input1, Reader input2) throws IOException {
        if (input1 == input2) {
            return true;
        }
        if (input1 == null ^ input2 == null) {
            return false;
        }
        BufferedReader bufferedInput1 = IOUtils.toBufferedReader(input1);
        BufferedReader bufferedInput2 = IOUtils.toBufferedReader(input2);
        int ch = bufferedInput1.read();
        while (-1 != ch) {
            int ch2 = bufferedInput2.read();
            if (ch != ch2) {
                return false;
            }
            ch = bufferedInput1.read();
        }
        return bufferedInput2.read() == -1;
    }

    public static boolean contentEqualsIgnoreEOL(Reader input1, Reader input2) throws IOException {
        if (input1 == input2) {
            return true;
        }
        if (input1 == null ^ input2 == null) {
            return false;
        }
        BufferedReader br1 = IOUtils.toBufferedReader(input1);
        BufferedReader br2 = IOUtils.toBufferedReader(input2);
        String line1 = br1.readLine();
        String line2 = br2.readLine();
        while (line1 != null && line1.equals(line2)) {
            line1 = br1.readLine();
            line2 = br2.readLine();
        }
        return Objects.equals(line1, line2);
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = IOUtils.copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)count;
    }

    public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        return IOUtils.copyLarge(input, output, new byte[bufferSize]);
    }

    @Deprecated
    public static void copy(InputStream input, Writer output) throws IOException {
        IOUtils.copy(input, output, Charset.defaultCharset());
    }

    public static void copy(InputStream input, Writer output, Charset inputCharset) throws IOException {
        InputStreamReader in = new InputStreamReader(input, Charsets.toCharset(inputCharset));
        IOUtils.copy((Reader)in, output);
    }

    public static void copy(InputStream input, Writer output, String inputCharsetName) throws IOException {
        IOUtils.copy(input, output, Charsets.toCharset(inputCharsetName));
    }

    public static long copy(Reader input, Appendable output) throws IOException {
        return IOUtils.copy(input, output, CharBuffer.allocate(8192));
    }

    public static long copy(Reader input, Appendable output, CharBuffer buffer) throws IOException {
        int n;
        long count = 0L;
        while (-1 != (n = input.read(buffer))) {
            buffer.flip();
            output.append(buffer, 0, n);
            count += (long)n;
        }
        return count;
    }

    @Deprecated
    public static void copy(Reader input, OutputStream output) throws IOException {
        IOUtils.copy(input, output, Charset.defaultCharset());
    }

    public static void copy(Reader input, OutputStream output, Charset outputCharset) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(output, Charsets.toCharset(outputCharset));
        IOUtils.copy(input, (Writer)out);
        out.flush();
    }

    public static void copy(Reader input, OutputStream output, String outputCharsetName) throws IOException {
        IOUtils.copy(input, output, Charsets.toCharset(outputCharsetName));
    }

    public static int copy(Reader input, Writer output) throws IOException {
        long count = IOUtils.copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return IOUtils.copy(input, output, 8192);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        int n;
        long count = 0L;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += (long)n;
        }
        return count;
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length) throws IOException {
        return IOUtils.copyLarge(input, output, inputOffset, length, new byte[8192]);
    }

    public static long copyLarge(InputStream input, OutputStream output, long inputOffset, long length, byte[] buffer) throws IOException {
        int read;
        int bufferLength;
        if (inputOffset > 0L) {
            IOUtils.skipFully(input, inputOffset);
        }
        if (length == 0L) {
            return 0L;
        }
        int bytesToRead = bufferLength = buffer.length;
        if (length > 0L && length < (long)bufferLength) {
            bytesToRead = (int)length;
        }
        long totalRead = 0L;
        while (bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += (long)read;
            if (length <= 0L) continue;
            bytesToRead = (int)Math.min(length - totalRead, (long)bufferLength);
        }
        return totalRead;
    }

    public static long copyLarge(Reader input, Writer output) throws IOException {
        return IOUtils.copyLarge(input, output, new char[8192]);
    }

    public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        int n;
        long count = 0L;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += (long)n;
        }
        return count;
    }

    public static long copyLarge(Reader input, Writer output, long inputOffset, long length) throws IOException {
        return IOUtils.copyLarge(input, output, inputOffset, length, new char[8192]);
    }

    public static long copyLarge(Reader input, Writer output, long inputOffset, long length, char[] buffer) throws IOException {
        int read;
        if (inputOffset > 0L) {
            IOUtils.skipFully(input, inputOffset);
        }
        if (length == 0L) {
            return 0L;
        }
        int bytesToRead = buffer.length;
        if (length > 0L && length < (long)buffer.length) {
            bytesToRead = (int)length;
        }
        long totalRead = 0L;
        while (bytesToRead > 0 && -1 != (read = input.read(buffer, 0, bytesToRead))) {
            output.write(buffer, 0, read);
            totalRead += (long)read;
            if (length <= 0L) continue;
            bytesToRead = (int)Math.min(length - totalRead, (long)buffer.length);
        }
        return totalRead;
    }

    public static int length(byte[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(char[] array) {
        return array == null ? 0 : array.length;
    }

    public static int length(CharSequence csq) {
        return csq == null ? 0 : csq.length();
    }

    public static int length(Object[] array) {
        return array == null ? 0 : array.length;
    }

    public static LineIterator lineIterator(InputStream input, Charset charset) throws IOException {
        return new LineIterator(new InputStreamReader(input, Charsets.toCharset(charset)));
    }

    public static LineIterator lineIterator(InputStream input, String charsetName) throws IOException {
        return IOUtils.lineIterator(input, Charsets.toCharset(charsetName));
    }

    public static LineIterator lineIterator(Reader reader) {
        return new LineIterator(reader);
    }

    public static int read(InputStream input, byte[] buffer) throws IOException {
        return IOUtils.read(input, buffer, 0, buffer.length);
    }

    public static int read(InputStream input, byte[] buffer, int offset, int length) throws IOException {
        int location;
        int remaining;
        int count;
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + length);
        }
        for (remaining = length; remaining > 0 && -1 != (count = input.read(buffer, offset + (location = length - remaining), remaining)); remaining -= count) {
        }
        return length - remaining;
    }

    public static int read(ReadableByteChannel input, ByteBuffer buffer) throws IOException {
        int count;
        int length = buffer.remaining();
        while (buffer.remaining() > 0 && -1 != (count = input.read(buffer))) {
        }
        return length - buffer.remaining();
    }

    public static int read(Reader input, char[] buffer) throws IOException {
        return IOUtils.read(input, buffer, 0, buffer.length);
    }

    public static int read(Reader input, char[] buffer, int offset, int length) throws IOException {
        int location;
        int remaining;
        int count;
        if (length < 0) {
            throw new IllegalArgumentException("Length must not be negative: " + length);
        }
        for (remaining = length; remaining > 0 && -1 != (count = input.read(buffer, offset + (location = length - remaining), remaining)); remaining -= count) {
        }
        return length - remaining;
    }

    public static void readFully(InputStream input, byte[] buffer) throws IOException {
        IOUtils.readFully(input, buffer, 0, buffer.length);
    }

    public static void readFully(InputStream input, byte[] buffer, int offset, int length) throws IOException {
        int actual = IOUtils.read(input, buffer, offset, length);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    public static byte[] readFully(InputStream input, int length) throws IOException {
        byte[] buffer = new byte[length];
        IOUtils.readFully(input, buffer, 0, buffer.length);
        return buffer;
    }

    public static void readFully(ReadableByteChannel input, ByteBuffer buffer) throws IOException {
        int expected = buffer.remaining();
        int actual = IOUtils.read(input, buffer);
        if (actual != expected) {
            throw new EOFException("Length to read: " + expected + " actual: " + actual);
        }
    }

    public static void readFully(Reader input, char[] buffer) throws IOException {
        IOUtils.readFully(input, buffer, 0, buffer.length);
    }

    public static void readFully(Reader input, char[] buffer, int offset, int length) throws IOException {
        int actual = IOUtils.read(input, buffer, offset, length);
        if (actual != length) {
            throw new EOFException("Length to read: " + length + " actual: " + actual);
        }
    }

    @Deprecated
    public static List<String> readLines(InputStream input) throws IOException {
        return IOUtils.readLines(input, Charset.defaultCharset());
    }

    public static List<String> readLines(InputStream input, Charset charset) throws IOException {
        InputStreamReader reader = new InputStreamReader(input, Charsets.toCharset(charset));
        return IOUtils.readLines(reader);
    }

    public static List<String> readLines(InputStream input, String charsetName) throws IOException {
        return IOUtils.readLines(input, Charsets.toCharset(charsetName));
    }

    public static List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = IOUtils.toBufferedReader(input);
        ArrayList<String> list = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

    public static byte[] resourceToByteArray(String name) throws IOException {
        return IOUtils.resourceToByteArray(name, null);
    }

    public static byte[] resourceToByteArray(String name, ClassLoader classLoader) throws IOException {
        return IOUtils.toByteArray(IOUtils.resourceToURL(name, classLoader));
    }

    public static String resourceToString(String name, Charset charset) throws IOException {
        return IOUtils.resourceToString(name, charset, null);
    }

    public static String resourceToString(String name, Charset charset, ClassLoader classLoader) throws IOException {
        return IOUtils.toString(IOUtils.resourceToURL(name, classLoader), charset);
    }

    public static URL resourceToURL(String name) throws IOException {
        return IOUtils.resourceToURL(name, null);
    }

    public static URL resourceToURL(String name, ClassLoader classLoader) throws IOException {
        URL resource;
        URL uRL = resource = classLoader == null ? IOUtils.class.getResource(name) : classLoader.getResource(name);
        if (resource == null) {
            throw new IOException("Resource not found: " + name);
        }
        return resource;
    }

    public static long skip(InputStream input, long toSkip) throws IOException {
        long remain;
        long n;
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        if (SKIP_BYTE_BUFFER == null) {
            SKIP_BYTE_BUFFER = new byte[2048];
        }
        for (remain = toSkip; remain > 0L && (n = (long)input.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(remain, 2048L))) >= 0L; remain -= n) {
        }
        return toSkip - remain;
    }

    public static long skip(ReadableByteChannel input, long toSkip) throws IOException {
        long remain;
        int n;
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        ByteBuffer skipByteBuffer = ByteBuffer.allocate((int)Math.min(toSkip, 2048L));
        for (remain = toSkip; remain > 0L; remain -= (long)n) {
            skipByteBuffer.position(0);
            skipByteBuffer.limit((int)Math.min(remain, 2048L));
            n = input.read(skipByteBuffer);
            if (n == -1) break;
        }
        return toSkip - remain;
    }

    public static long skip(Reader input, long toSkip) throws IOException {
        long remain;
        long n;
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
        }
        if (SKIP_CHAR_BUFFER == null) {
            SKIP_CHAR_BUFFER = new char[2048];
        }
        for (remain = toSkip; remain > 0L && (n = (long)input.read(SKIP_CHAR_BUFFER, 0, (int)Math.min(remain, 2048L))) >= 0L; remain -= n) {
        }
        return toSkip - remain;
    }

    public static void skipFully(InputStream input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        }
        long skipped = IOUtils.skip(input, toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
        }
    }

    public static void skipFully(ReadableByteChannel input, long toSkip) throws IOException {
        if (toSkip < 0L) {
            throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
        }
        long skipped = IOUtils.skip(input, toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
        }
    }

    public static void skipFully(Reader input, long toSkip) throws IOException {
        long skipped = IOUtils.skip(input, toSkip);
        if (skipped != toSkip) {
            throw new EOFException("Chars to skip: " + toSkip + " actual: " + skipped);
        }
    }

    public static InputStream toBufferedInputStream(InputStream input) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(input);
    }

    public static InputStream toBufferedInputStream(InputStream input, int size) throws IOException {
        return ByteArrayOutputStream.toBufferedInputStream(input, size);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
    }

    public static BufferedReader toBufferedReader(Reader reader, int size) {
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader, size);
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();){
            IOUtils.copy(input, (OutputStream)output);
            byte[] byArray = output.toByteArray();
            return byArray;
        }
    }

    public static byte[] toByteArray(InputStream input, int size) throws IOException {
        int offset;
        int read;
        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        }
        if (size == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] data = new byte[size];
        for (offset = 0; offset < size && (read = input.read(data, offset, size - offset)) != -1; offset += read) {
        }
        if (offset != size) {
            throw new IOException("Unexpected read size. current: " + offset + ", expected: " + size);
        }
        return data;
    }

    public static byte[] toByteArray(InputStream input, long size) throws IOException {
        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
        }
        return IOUtils.toByteArray(input, (int)size);
    }

    @Deprecated
    public static byte[] toByteArray(Reader input) throws IOException {
        return IOUtils.toByteArray(input, Charset.defaultCharset());
    }

    public static byte[] toByteArray(Reader input, Charset charset) throws IOException {
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();){
            IOUtils.copy(input, (OutputStream)output, charset);
            byte[] byArray = output.toByteArray();
            return byArray;
        }
    }

    public static byte[] toByteArray(Reader input, String charsetName) throws IOException {
        return IOUtils.toByteArray(input, Charsets.toCharset(charsetName));
    }

    @Deprecated
    public static byte[] toByteArray(String input) throws IOException {
        return input.getBytes(Charset.defaultCharset());
    }

    public static byte[] toByteArray(URI uri) throws IOException {
        return IOUtils.toByteArray(uri.toURL());
    }

    public static byte[] toByteArray(URL url2) throws IOException {
        URLConnection conn = url2.openConnection();
        try {
            byte[] byArray = IOUtils.toByteArray(conn);
            return byArray;
        }
        finally {
            IOUtils.close(conn);
        }
    }

    public static byte[] toByteArray(URLConnection urlConn) throws IOException {
        try (InputStream inputStream2 = urlConn.getInputStream();){
            byte[] byArray = IOUtils.toByteArray(inputStream2);
            return byArray;
        }
    }

    @Deprecated
    public static char[] toCharArray(InputStream is) throws IOException {
        return IOUtils.toCharArray(is, Charset.defaultCharset());
    }

    public static char[] toCharArray(InputStream is, Charset charset) throws IOException {
        CharArrayWriter output = new CharArrayWriter();
        IOUtils.copy(is, (Writer)output, charset);
        return output.toCharArray();
    }

    public static char[] toCharArray(InputStream is, String charsetName) throws IOException {
        return IOUtils.toCharArray(is, Charsets.toCharset(charsetName));
    }

    public static char[] toCharArray(Reader input) throws IOException {
        CharArrayWriter sw = new CharArrayWriter();
        IOUtils.copy(input, (Writer)sw);
        return sw.toCharArray();
    }

    @Deprecated
    public static InputStream toInputStream(CharSequence input) {
        return IOUtils.toInputStream(input, Charset.defaultCharset());
    }

    public static InputStream toInputStream(CharSequence input, Charset charset) {
        return IOUtils.toInputStream(input.toString(), charset);
    }

    public static InputStream toInputStream(CharSequence input, String charsetName) throws IOException {
        return IOUtils.toInputStream(input, Charsets.toCharset(charsetName));
    }

    @Deprecated
    public static InputStream toInputStream(String input) {
        return IOUtils.toInputStream(input, Charset.defaultCharset());
    }

    public static InputStream toInputStream(String input, Charset charset) {
        return new ByteArrayInputStream(input.getBytes(Charsets.toCharset(charset)));
    }

    public static InputStream toInputStream(String input, String charsetName) throws IOException {
        byte[] bytes = input.getBytes(Charsets.toCharset(charsetName));
        return new ByteArrayInputStream(bytes);
    }

    @Deprecated
    public static String toString(byte[] input) throws IOException {
        return new String(input, Charset.defaultCharset());
    }

    public static String toString(byte[] input, String charsetName) throws IOException {
        return new String(input, Charsets.toCharset(charsetName));
    }

    @Deprecated
    public static String toString(InputStream input) throws IOException {
        return IOUtils.toString(input, Charset.defaultCharset());
    }

    public static String toString(InputStream input, Charset charset) throws IOException {
        try (StringBuilderWriter sw = new StringBuilderWriter();){
            IOUtils.copy(input, (Writer)sw, charset);
            String string = sw.toString();
            return string;
        }
    }

    public static String toString(InputStream input, String charsetName) throws IOException {
        return IOUtils.toString(input, Charsets.toCharset(charsetName));
    }

    public static String toString(Reader input) throws IOException {
        try (StringBuilderWriter sw = new StringBuilderWriter();){
            IOUtils.copy(input, (Writer)sw);
            String string = sw.toString();
            return string;
        }
    }

    @Deprecated
    public static String toString(URI uri) throws IOException {
        return IOUtils.toString(uri, Charset.defaultCharset());
    }

    public static String toString(URI uri, Charset encoding) throws IOException {
        return IOUtils.toString(uri.toURL(), Charsets.toCharset(encoding));
    }

    public static String toString(URI uri, String charsetName) throws IOException {
        return IOUtils.toString(uri, Charsets.toCharset(charsetName));
    }

    @Deprecated
    public static String toString(URL url2) throws IOException {
        return IOUtils.toString(url2, Charset.defaultCharset());
    }

    public static String toString(URL url2, Charset encoding) throws IOException {
        try (InputStream inputStream2 = url2.openStream();){
            String string = IOUtils.toString(inputStream2, encoding);
            return string;
        }
    }

    public static String toString(URL url2, String charsetName) throws IOException {
        return IOUtils.toString(url2, Charsets.toCharset(charsetName));
    }

    public static void write(byte[] data, OutputStream output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    @Deprecated
    public static void write(byte[] data, Writer output) throws IOException {
        IOUtils.write(data, output, Charset.defaultCharset());
    }

    public static void write(byte[] data, Writer output, Charset charset) throws IOException {
        if (data != null) {
            output.write(new String(data, Charsets.toCharset(charset)));
        }
    }

    public static void write(byte[] data, Writer output, String charsetName) throws IOException {
        IOUtils.write(data, output, Charsets.toCharset(charsetName));
    }

    @Deprecated
    public static void write(char[] data, OutputStream output) throws IOException {
        IOUtils.write(data, output, Charset.defaultCharset());
    }

    public static void write(char[] data, OutputStream output, Charset charset) throws IOException {
        if (data != null) {
            output.write(new String(data).getBytes(Charsets.toCharset(charset)));
        }
    }

    public static void write(char[] data, OutputStream output, String charsetName) throws IOException {
        IOUtils.write(data, output, Charsets.toCharset(charsetName));
    }

    public static void write(char[] data, Writer output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    @Deprecated
    public static void write(CharSequence data, OutputStream output) throws IOException {
        IOUtils.write(data, output, Charset.defaultCharset());
    }

    public static void write(CharSequence data, OutputStream output, Charset charset) throws IOException {
        if (data != null) {
            IOUtils.write(data.toString(), output, charset);
        }
    }

    public static void write(CharSequence data, OutputStream output, String charsetName) throws IOException {
        IOUtils.write(data, output, Charsets.toCharset(charsetName));
    }

    public static void write(CharSequence data, Writer output) throws IOException {
        if (data != null) {
            IOUtils.write(data.toString(), output);
        }
    }

    @Deprecated
    public static void write(String data, OutputStream output) throws IOException {
        IOUtils.write(data, output, Charset.defaultCharset());
    }

    public static void write(String data, OutputStream output, Charset charset) throws IOException {
        if (data != null) {
            output.write(data.getBytes(Charsets.toCharset(charset)));
        }
    }

    public static void write(String data, OutputStream output, String charsetName) throws IOException {
        IOUtils.write(data, output, Charsets.toCharset(charsetName));
    }

    public static void write(String data, Writer output) throws IOException {
        if (data != null) {
            output.write(data);
        }
    }

    @Deprecated
    public static void write(StringBuffer data, OutputStream output) throws IOException {
        IOUtils.write(data, output, (String)null);
    }

    @Deprecated
    public static void write(StringBuffer data, OutputStream output, String charsetName) throws IOException {
        if (data != null) {
            output.write(data.toString().getBytes(Charsets.toCharset(charsetName)));
        }
    }

    @Deprecated
    public static void write(StringBuffer data, Writer output) throws IOException {
        if (data != null) {
            output.write(data.toString());
        }
    }

    public static void writeChunked(byte[] data, OutputStream output) throws IOException {
        if (data != null) {
            int bytes = data.length;
            int offset = 0;
            while (bytes > 0) {
                int chunk = Math.min(bytes, 8192);
                output.write(data, offset, chunk);
                bytes -= chunk;
                offset += chunk;
            }
        }
    }

    public static void writeChunked(char[] data, Writer output) throws IOException {
        if (data != null) {
            int bytes = data.length;
            int offset = 0;
            while (bytes > 0) {
                int chunk = Math.min(bytes, 8192);
                output.write(data, offset, chunk);
                bytes -= chunk;
                offset += chunk;
            }
        }
    }

    @Deprecated
    public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output) throws IOException {
        IOUtils.writeLines(lines, lineEnding, output, Charset.defaultCharset());
    }

    public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output, Charset charset) throws IOException {
        if (lines == null) {
            return;
        }
        if (lineEnding == null) {
            lineEnding = LINE_SEPARATOR;
        }
        Charset cs = Charsets.toCharset(charset);
        for (Object line : lines) {
            if (line != null) {
                output.write(line.toString().getBytes(cs));
            }
            output.write(lineEnding.getBytes(cs));
        }
    }

    public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output, String charsetName) throws IOException {
        IOUtils.writeLines(lines, lineEnding, output, Charsets.toCharset(charsetName));
    }

    public static void writeLines(Collection<?> lines, String lineEnding, Writer writer) throws IOException {
        if (lines == null) {
            return;
        }
        if (lineEnding == null) {
            lineEnding = LINE_SEPARATOR;
        }
        for (Object line : lines) {
            if (line != null) {
                writer.write(line.toString());
            }
            writer.write(lineEnding);
        }
    }

    public static Writer writer(Appendable appendable) {
        Objects.requireNonNull(appendable, "appendable");
        if (appendable instanceof Writer) {
            return (Writer)appendable;
        }
        if (appendable instanceof StringBuilder) {
            return new StringBuilderWriter((StringBuilder)appendable);
        }
        return new AppendableWriter<Appendable>(appendable);
    }

    static {
        try (StringBuilderWriter buf = new StringBuilderWriter(4);
             PrintWriter out = new PrintWriter(buf);){
            out.println();
            LINE_SEPARATOR = buf.toString();
        }
    }
}

