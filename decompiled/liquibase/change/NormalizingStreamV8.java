/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import liquibase.GlobalConfiguration;
import liquibase.exception.UnexpectedLiquibaseException;

@Deprecated
public class NormalizingStreamV8
extends InputStream {
    private ByteArrayInputStream headerStream;
    private PushbackInputStream stream;
    private byte[] quickBuffer = new byte[100];
    private List<Byte> resizingBuffer = new ArrayList<Byte>();
    private int lastChar = 88;
    private boolean seenNonSpace;

    @Deprecated
    public NormalizingStreamV8(String endDelimiter, Boolean splitStatements, Boolean stripComments, InputStream stream) {
        this.stream = new PushbackInputStream(stream, 2048);
        try {
            this.headerStream = new ByteArrayInputStream((endDelimiter + ":" + splitStatements + ":" + stripComments + ":").getBytes(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
        }
        catch (UnsupportedEncodingException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public int read() throws IOException {
        int returnChar;
        if (this.headerStream != null) {
            returnChar = this.headerStream.read();
            if (returnChar != -1) {
                return returnChar;
            }
            this.headerStream = null;
        }
        if (this.isWhiteSpace(returnChar = this.stream.read())) {
            returnChar = 32;
        }
        while (!(returnChar != 32 || this.seenNonSpace && this.lastChar != 32)) {
            returnChar = this.stream.read();
            if (!this.isWhiteSpace(returnChar)) continue;
            returnChar = 32;
        }
        this.seenNonSpace = true;
        this.lastChar = returnChar;
        if (this.lastChar == 32 && this.isOnlyWhitespaceRemaining()) {
            return -1;
        }
        return returnChar;
    }

    @Override
    public int available() throws IOException {
        return this.stream.available();
    }

    @Override
    public boolean markSupported() {
        return this.stream.markSupported();
    }

    @Override
    public synchronized void mark(int readLimit) {
        this.stream.mark(readLimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.stream.reset();
    }

    private boolean isOnlyWhitespaceRemaining() throws IOException {
        try {
            int quickBufferUsed = 0;
            while (true) {
                byte read = (byte)this.stream.read();
                if (quickBufferUsed >= this.quickBuffer.length) {
                    this.resizingBuffer.add(read);
                } else {
                    this.quickBuffer[quickBufferUsed++] = read;
                }
                if (read == -1) {
                    boolean bl = true;
                    return bl;
                }
                if (this.isWhiteSpace(read)) continue;
                if (!this.resizingBuffer.isEmpty()) {
                    byte[] buf = new byte[this.resizingBuffer.size()];
                    for (int i = 0; i < this.resizingBuffer.size(); ++i) {
                        buf[i] = this.resizingBuffer.get(i);
                    }
                    this.stream.unread(buf);
                }
                this.stream.unread(this.quickBuffer, 0, quickBufferUsed);
                boolean bl = false;
                return bl;
            }
        }
        finally {
            this.resizingBuffer.clear();
        }
    }

    private boolean isWhiteSpace(int read) {
        return read == 32 || read == 10 || read == 13 || read == 9;
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}

