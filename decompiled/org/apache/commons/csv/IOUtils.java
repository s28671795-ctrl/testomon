/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.csv;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.CharBuffer;

final class IOUtils {
    static final int DEFAULT_BUFFER_SIZE = 4096;
    private static final int EOF = -1;

    static long copy(Reader input, Appendable output) throws IOException {
        return IOUtils.copy(input, output, CharBuffer.allocate(4096));
    }

    static long copy(Reader input, Appendable output, CharBuffer buffer) throws IOException {
        int n;
        long count = 0L;
        while (-1 != (n = input.read(buffer))) {
            ((Buffer)buffer).flip();
            output.append(buffer, 0, n);
            count += (long)n;
        }
        return count;
    }

    static long copyLarge(Reader input, Writer output) throws IOException {
        return IOUtils.copyLarge(input, output, new char[4096]);
    }

    static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
        int n;
        long count = 0L;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += (long)n;
        }
        return count;
    }

    static <T extends Throwable> RuntimeException rethrow(Throwable throwable) throws T {
        throw throwable;
    }

    private IOUtils() {
    }
}

