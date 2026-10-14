/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.commons.compiler.io;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import org.codehaus.commons.compiler.util.LineAndColumnTracker;
import org.codehaus.commons.nullanalysis.NotNullByDefault;
import org.codehaus.commons.nullanalysis.Nullable;

public final class Readers {
    public static final Reader EMPTY_READER = new StringReader("");

    private Readers() {
    }

    public static Reader onFirstChar(Reader in, final Runnable runnable2) {
        return new FilterReader(in){
            private boolean hadChars;

            @Override
            public int read() throws IOException {
                this.aboutToRead();
                return super.read();
            }

            @Override
            @NotNullByDefault(value=false)
            public int read(char[] cbuf, int off, int len) throws IOException {
                this.aboutToRead();
                return super.read(cbuf, off, len);
            }

            @Override
            public long skip(long n) throws IOException {
                this.aboutToRead();
                return super.skip(n);
            }

            private void aboutToRead() {
                if (!this.hadChars) {
                    runnable2.run();
                    this.hadChars = true;
                }
            }
        };
    }

    public static Reader trackLineAndColumn(Reader in, final LineAndColumnTracker tracker) {
        return new FilterReader(in){

            @Override
            public int read() throws IOException {
                int c = super.read();
                if (c >= 0) {
                    tracker.consume((char)c);
                }
                return c;
            }

            @Override
            @NotNullByDefault(value=false)
            public int read(char[] cbuf, int off, int len) throws IOException {
                if (len <= 0) {
                    return 0;
                }
                int c = this.read();
                if (c < 0) {
                    return -1;
                }
                cbuf[off] = (char)c;
                return 1;
            }

            @Override
            public long skip(long n) throws IOException {
                if (n <= 0L) {
                    return 0L;
                }
                int c = this.read();
                if (c < 0) {
                    return 0L;
                }
                return 1L;
            }

            @Override
            public boolean markSupported() {
                return false;
            }

            @Override
            public void mark(int readAheadLimit) throws IOException {
                throw new IOException();
            }

            @Override
            public void reset() throws IOException {
                throw new IOException();
            }
        };
    }

    public static Reader concat(Reader ... delegates) {
        return Readers.concat(Arrays.asList(delegates));
    }

    public static Reader concat(final Iterable<Reader> delegates) {
        return new Reader(){
            private final Iterator<Reader> delegateIterator;
            private Reader currentDelegate;
            {
                this.delegateIterator = delegates.iterator();
                this.currentDelegate = EMPTY_READER;
            }

            @Override
            public void close() throws IOException {
                for (Reader delegate : delegates) {
                    delegate.close();
                }
            }

            @Override
            public int read() throws IOException {
                int result;
                while ((result = this.currentDelegate.read()) == -1) {
                    if (!this.delegateIterator.hasNext()) {
                        return -1;
                    }
                    this.currentDelegate = this.delegateIterator.next();
                }
                return result;
            }

            @Override
            public long skip(long n) throws IOException {
                long result;
                while ((result = this.currentDelegate.skip(n)) == -1L) {
                    if (!this.delegateIterator.hasNext()) {
                        return 0L;
                    }
                    this.currentDelegate = this.delegateIterator.next();
                }
                return result;
            }

            @Override
            public int read(@Nullable char[] cbuf, int off, int len) throws IOException {
                int result;
                while ((long)(result = this.currentDelegate.read(cbuf, off, len)) == -1L) {
                    if (!this.delegateIterator.hasNext()) {
                        return -1;
                    }
                    this.currentDelegate = this.delegateIterator.next();
                }
                return result;
            }
        };
    }

    public static Reader teeReader(Reader in, final Writer out, final boolean closeWriterOnEoi) {
        return new FilterReader(in){

            @Override
            public void close() throws IOException {
                this.in.close();
                out.close();
            }

            @Override
            public int read() throws IOException {
                int c = this.in.read();
                if (c == -1) {
                    if (closeWriterOnEoi) {
                        out.close();
                    } else {
                        out.flush();
                    }
                } else {
                    out.write(c);
                }
                return c;
            }

            @Override
            public int read(@Nullable char[] cbuf, int off, int len) throws IOException {
                int bytesRead = this.in.read(cbuf, off, len);
                if (bytesRead == -1) {
                    if (closeWriterOnEoi) {
                        out.close();
                    } else {
                        out.flush();
                    }
                } else {
                    out.write(cbuf, off, bytesRead);
                }
                return bytesRead;
            }
        };
    }

    public static String readAll(Reader in) throws IOException {
        StringWriter sw = new StringWriter();
        Readers.copy(in, sw);
        return sw.toString();
    }

    public static void copy(Reader in, Writer out) throws IOException {
        int n;
        char[] buffer = new char[8192];
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
    }
}

