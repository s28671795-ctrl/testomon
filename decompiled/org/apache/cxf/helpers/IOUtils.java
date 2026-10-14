/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.io.CopyingOutputStream;
import org.apache.cxf.io.Transferable;

public final class IOUtils {
    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
    public static final int DEFAULT_BUFFER_SIZE = 4096;

    private IOUtils() {
    }

    public static boolean isEmpty(InputStream is) throws IOException {
        if (is == null) {
            return true;
        }
        if (is.available() > 0) {
            return false;
        }
        byte[] bytes = new byte[1];
        if (is.markSupported()) {
            is.mark(1);
            try {
                boolean bl = IOUtils.isEof(is.read(bytes));
                return bl;
            }
            finally {
                is.reset();
            }
        }
        if (!(is instanceof PushbackInputStream)) {
            return false;
        }
        PushbackInputStream pbStream = (PushbackInputStream)is;
        boolean isEmpty = IOUtils.isEof(pbStream.read(bytes));
        if (!isEmpty) {
            pbStream.unread(bytes);
        }
        return isEmpty;
    }

    private static boolean isEof(int result) {
        return result == -1;
    }

    public static String newStringFromBytes(byte[] bytes, String charsetName) {
        try {
            return new String(bytes, charsetName);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Impossible failure: Charset.forName(\"" + charsetName + "\") returns invalid name.");
        }
    }

    public static String newStringFromBytes(byte[] bytes) {
        return IOUtils.newStringFromBytes(bytes, UTF8_CHARSET.name());
    }

    public static String newStringFromBytes(byte[] bytes, String charsetName, int start2, int length) {
        try {
            return new String(bytes, start2, length, charsetName);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Impossible failure: Charset.forName(\"" + charsetName + "\") returns invalid name.");
        }
    }

    public static String newStringFromBytes(byte[] bytes, int start2, int length) {
        return IOUtils.newStringFromBytes(bytes, UTF8_CHARSET.name(), start2, length);
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        if (input == null) {
            return 0;
        }
        if (output instanceof CopyingOutputStream) {
            return ((CopyingOutputStream)((Object)output)).copyFrom(input);
        }
        return IOUtils.copy(input, output, 4096);
    }

    public static int copyAndCloseInput(InputStream input, OutputStream output) throws IOException {
        try (InputStream in = input;){
            int n = IOUtils.copy(in, output);
            return n;
        }
    }

    public static int copyAndCloseInput(InputStream input, OutputStream output, int bufferSize) throws IOException {
        try (InputStream in = input;){
            int n = IOUtils.copy(in, output, bufferSize);
            return n;
        }
    }

    public static void copyAndCloseInput(Reader input, Writer output) throws IOException {
        try (Reader r = input;){
            IOUtils.copy(r, output, 4096);
        }
    }

    public static void copyAndCloseInput(Reader input, Writer output, int bufferSize) throws IOException {
        try (Reader r = input;){
            IOUtils.copy(r, output, bufferSize);
        }
    }

    public static int copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        int avail = input.available();
        if (avail > 262144) {
            avail = 262144;
        }
        if (avail > bufferSize) {
            bufferSize = avail;
        }
        byte[] buffer = new byte[bufferSize];
        int n = input.read(buffer);
        int total = 0;
        while (-1 != n) {
            if (n == 0) {
                throw new IOException("0 bytes read in violation of InputStream.read(byte[])");
            }
            output.write(buffer, 0, n);
            total += n;
            n = input.read(buffer);
        }
        return total;
    }

    public static void copyAtLeast(InputStream input, OutputStream output, int atLeast) throws IOException {
        byte[] buffer = new byte[4096];
        int n = atLeast > buffer.length ? buffer.length : atLeast;
        n = input.read(buffer, 0, n);
        while (-1 != n) {
            if (n == 0) {
                throw new IOException("0 bytes read in violation of InputStream.read(byte[])");
            }
            output.write(buffer, 0, n);
            if ((atLeast -= n) <= 0) {
                return;
            }
            n = atLeast > buffer.length ? buffer.length : atLeast;
            n = input.read(buffer, 0, n);
        }
    }

    public static void copyAtLeast(Reader input, Writer output, int atLeast) throws IOException {
        char[] buffer = new char[4096];
        int n = atLeast > buffer.length ? buffer.length : atLeast;
        n = input.read(buffer, 0, n);
        while (-1 != n) {
            if (n == 0) {
                throw new IOException("0 bytes read in violation of Reader.read(char[])");
            }
            output.write(buffer, 0, n);
            if ((atLeast -= n) <= 0) {
                return;
            }
            n = atLeast > buffer.length ? buffer.length : atLeast;
            n = input.read(buffer, 0, n);
        }
    }

    public static void copy(Reader input, Writer output, int bufferSize) throws IOException {
        char[] buffer = new char[bufferSize];
        int n = input.read(buffer);
        while (-1 != n) {
            output.write(buffer, 0, n);
            n = input.read(buffer);
        }
    }

    public static void transferTo(InputStream inputStream2, File destinationFile) throws IOException {
        if (Transferable.class.isAssignableFrom(inputStream2.getClass())) {
            ((Transferable)((Object)inputStream2)).transferTo(destinationFile);
        } else {
            try (OutputStream out = Files.newOutputStream(destinationFile.toPath(), new OpenOption[0]);){
                IOUtils.copyAndCloseInput(inputStream2, out);
            }
        }
    }

    public static String toString(InputStream input) throws IOException {
        return IOUtils.toString(input, 4096);
    }

    public static String toString(InputStream input, String charset) throws IOException {
        return IOUtils.toString(input, 4096, charset);
    }

    public static String toString(InputStream input, int bufferSize) throws IOException {
        return IOUtils.toString(input, bufferSize, null);
    }

    public static String toString(InputStream input, int bufferSize, String charset) throws IOException {
        int avail = input.available();
        if (avail > bufferSize) {
            bufferSize = avail;
        }
        InputStreamReader reader = charset == null ? new InputStreamReader(input, UTF8_CHARSET) : new InputStreamReader(input, charset);
        return IOUtils.toString(reader, bufferSize);
    }

    public static String toString(Reader input) throws IOException {
        return IOUtils.toString(input, 4096);
    }

    public static String toString(Reader input, int bufSize) throws IOException {
        StringBuilder buf = new StringBuilder();
        char[] buffer = new char[bufSize];
        try (Reader r = input;){
            int n = r.read(buffer);
            while (-1 != n) {
                if (n == 0) {
                    throw new IOException("0 bytes read in violation of InputStream.read(byte[])");
                }
                buf.append(buffer, 0, n);
                n = r.read(buffer);
            }
            String string = buf.toString();
            return string;
        }
    }

    public static String readStringFromStream(InputStream in) throws IOException {
        return IOUtils.toString(in);
    }

    public static ByteArrayInputStream loadIntoBAIS(InputStream in) throws IOException {
        int i = in.available();
        if (i < 4096) {
            i = 4096;
        }
        LoadingByteArrayOutputStream bout = new LoadingByteArrayOutputStream(i);
        IOUtils.copy(in, bout);
        in.close();
        return bout.createInputStream();
    }

    public static void consume(InputStream in) throws IOException {
        int i = in.available();
        if (i == 0) {
            int i2 = in.read();
            if (i2 == -1) {
                return;
            }
            i = in.available();
        }
        if (i < 4096) {
            i = 4096;
        }
        if (i > 65536) {
            i = 65536;
        }
        byte[] bytes = new byte[i];
        while (in.read(bytes) != -1) {
        }
    }

    public static void consume(InputStream input, int atLeast) throws IOException {
        byte[] buffer = new byte[4096];
        int n = atLeast > buffer.length ? buffer.length : atLeast;
        n = input.read(buffer, 0, n);
        while (-1 != n) {
            if (n == 0) {
                throw new IOException("0 bytes read in violation of InputStream.read(byte[])");
            }
            if ((atLeast -= n) <= 0) {
                return;
            }
            n = atLeast > buffer.length ? buffer.length : atLeast;
            n = input.read(buffer, 0, n);
        }
    }

    /*
     * Exception decompiling
     */
    public static byte[] readBytesFromStream(InputStream in) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }
}

