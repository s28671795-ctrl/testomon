/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jvnet.mimepull.InternetHeaders;
import org.jvnet.mimepull.MIMEConfig;
import org.jvnet.mimepull.MIMEEvent;
import org.jvnet.mimepull.MIMEParsingException;

class MIMEParser
implements Iterable<MIMEEvent> {
    private static final Logger LOGGER = Logger.getLogger(MIMEParser.class.getName());
    private static final String HEADER_ENCODING = "ISO8859-1";
    private static final int NO_LWSP = 1000;
    private STATE state = STATE.START_MESSAGE;
    private final InputStream in;
    private final byte[] bndbytes;
    private final int bl;
    private final MIMEConfig config;
    private final int[] bcs = new int[128];
    private final int[] gss;
    private boolean parsed;
    private boolean done = false;
    private boolean eof;
    private final int capacity;
    private byte[] buf;
    private int len;
    private boolean bol;

    MIMEParser(InputStream in, String boundary, MIMEConfig config) {
        this.in = in;
        this.bndbytes = MIMEParser.getBytes("--" + boundary);
        this.bl = this.bndbytes.length;
        this.config = config;
        this.gss = new int[this.bl];
        this.compileBoundaryPattern();
        this.capacity = config.chunkSize + 2 + this.bl + 4 + 1000;
        this.createBuf(this.capacity);
    }

    @Override
    public Iterator<MIMEEvent> iterator() {
        return new MIMEEventIterator();
    }

    private InternetHeaders readHeaders() {
        if (!this.eof) {
            this.fillBuf();
        }
        return new InternetHeaders(new LineInputStream());
    }

    private ByteBuffer readBody() {
        int start2;
        if (!this.eof) {
            this.fillBuf();
        }
        if ((start2 = this.match(this.buf, 0, this.len)) == -1) {
            int chunkSize;
            assert (this.eof || this.len >= this.config.chunkSize);
            int n = chunkSize = this.eof ? this.len : this.config.chunkSize;
            if (this.eof) {
                this.done = true;
                throw new MIMEParsingException("Reached EOF, but there is no closing MIME boundary.");
            }
            return this.adjustBuf(chunkSize, this.len - chunkSize);
        }
        int chunkLen = start2;
        if (!this.bol || start2 != 0) {
            if (start2 > 0 && (this.buf[start2 - 1] == 10 || this.buf[start2 - 1] == 13)) {
                --chunkLen;
                if (this.buf[start2 - 1] == 10 && start2 > 1 && this.buf[start2 - 2] == 13) {
                    --chunkLen;
                }
            } else {
                return this.adjustBuf(start2 + 1, this.len - start2 - 1);
            }
        }
        if (start2 + this.bl + 1 < this.len && this.buf[start2 + this.bl] == 45 && this.buf[start2 + this.bl + 1] == 45) {
            this.state = STATE.END_PART;
            this.done = true;
            return this.adjustBuf(chunkLen, 0);
        }
        int lwsp = 0;
        for (int i = start2 + this.bl; i < this.len && (this.buf[i] == 32 || this.buf[i] == 9); ++i) {
            ++lwsp;
        }
        if (start2 + this.bl + lwsp < this.len && this.buf[start2 + this.bl + lwsp] == 10) {
            this.state = STATE.END_PART;
            return this.adjustBuf(chunkLen, this.len - start2 - this.bl - lwsp - 1);
        }
        if (start2 + this.bl + lwsp + 1 < this.len && this.buf[start2 + this.bl + lwsp] == 13 && this.buf[start2 + this.bl + lwsp + 1] == 10) {
            this.state = STATE.END_PART;
            return this.adjustBuf(chunkLen, this.len - start2 - this.bl - lwsp - 2);
        }
        if (start2 + this.bl + lwsp + 1 < this.len) {
            return this.adjustBuf(chunkLen + 1, this.len - chunkLen - 1);
        }
        if (this.eof) {
            this.done = true;
            throw new MIMEParsingException("Reached EOF, but there is no closing MIME boundary.");
        }
        return this.adjustBuf(chunkLen, this.len - chunkLen);
    }

    private ByteBuffer adjustBuf(int chunkSize, int remaining) {
        assert (this.buf != null);
        assert (chunkSize >= 0);
        assert (remaining >= 0);
        byte[] temp = this.buf;
        this.createBuf(remaining);
        System.arraycopy(temp, this.len - remaining, this.buf, 0, remaining);
        this.len = remaining;
        return ByteBuffer.wrap(temp, 0, chunkSize);
    }

    private void createBuf(int min2) {
        this.buf = new byte[min2 < this.capacity ? this.capacity : min2];
    }

    private void skipPreamble() {
        while (true) {
            int start2;
            if (!this.eof) {
                this.fillBuf();
            }
            if ((start2 = this.match(this.buf, 0, this.len)) == -1) {
                if (this.eof) {
                    throw new MIMEParsingException("Missing start boundary");
                }
                this.adjustBuf(this.len - this.bl + 1, this.bl - 1);
                continue;
            }
            if (start2 > this.config.chunkSize) {
                this.adjustBuf(start2, this.len - start2);
                continue;
            }
            int lwsp = 0;
            for (int i = start2 + this.bl; i < this.len && (this.buf[i] == 32 || this.buf[i] == 9); ++i) {
                ++lwsp;
            }
            if (start2 + this.bl + lwsp < this.len && (this.buf[start2 + this.bl + lwsp] == 10 || this.buf[start2 + this.bl + lwsp] == 13)) {
                if (this.buf[start2 + this.bl + lwsp] == 10) {
                    this.adjustBuf(start2 + this.bl + lwsp + 1, this.len - start2 - this.bl - lwsp - 1);
                    break;
                }
                if (start2 + this.bl + lwsp + 1 < this.len && this.buf[start2 + this.bl + lwsp + 1] == 10) {
                    this.adjustBuf(start2 + this.bl + lwsp + 2, this.len - start2 - this.bl - lwsp - 2);
                    break;
                }
            }
            this.adjustBuf(start2 + 1, this.len - start2 - 1);
        }
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Skipped the preamble. buffer len={0}", this.len);
        }
    }

    private static byte[] getBytes(String s2) {
        char[] chars = s2.toCharArray();
        int size = chars.length;
        byte[] bytes = new byte[size];
        int i = 0;
        while (i < size) {
            bytes[i] = (byte)chars[i++];
        }
        return bytes;
    }

    private void compileBoundaryPattern() {
        int i;
        for (i = 0; i < this.bndbytes.length; ++i) {
            this.bcs[this.bndbytes[i] & 0x7F] = i + 1;
        }
        block1: for (i = this.bndbytes.length; i > 0; --i) {
            int j;
            for (j = this.bndbytes.length - 1; j >= i; --j) {
                if (this.bndbytes[j] != this.bndbytes[j - i]) continue block1;
                this.gss[j - 1] = i;
            }
            while (j > 0) {
                this.gss[--j] = i;
            }
        }
        this.gss[this.bndbytes.length - 1] = 1;
    }

    private int match(byte[] mybuf, int off, int len) {
        int last = len - this.bndbytes.length;
        block0: while (off <= last) {
            for (int j = this.bndbytes.length - 1; j >= 0; --j) {
                byte ch = mybuf[off + j];
                if (ch == this.bndbytes[j]) continue;
                off += Math.max(j + 1 - this.bcs[ch & 0x7F], this.gss[j]);
                continue block0;
            }
            return off;
        }
        return -1;
    }

    private void fillBuf() {
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "Before fillBuf() buffer len={0}", this.len);
        }
        assert (!this.eof);
        while (this.len < this.buf.length) {
            int read;
            try {
                read = this.in.read(this.buf, this.len, this.buf.length - this.len);
            }
            catch (IOException ioe) {
                throw new MIMEParsingException(ioe);
            }
            if (read == -1) {
                this.eof = true;
                try {
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.fine("Closing the input stream.");
                    }
                    this.in.close();
                    break;
                }
                catch (IOException ioe) {
                    throw new MIMEParsingException(ioe);
                }
            }
            this.len += read;
        }
        if (LOGGER.isLoggable(Level.FINER)) {
            LOGGER.log(Level.FINER, "After fillBuf() buffer len={0}", this.len);
        }
    }

    private void doubleBuf() {
        byte[] temp = new byte[2 * this.len];
        System.arraycopy(this.buf, 0, temp, 0, this.len);
        this.buf = temp;
        if (!this.eof) {
            this.fillBuf();
        }
    }

    class LineInputStream {
        private int offset;

        LineInputStream() {
        }

        public String readLine() throws IOException {
            int hdrLen = 0;
            int lwsp = 0;
            while (this.offset + hdrLen < MIMEParser.this.len) {
                if (MIMEParser.this.buf[this.offset + hdrLen] == 10) {
                    lwsp = 1;
                    break;
                }
                if (this.offset + hdrLen + 1 == MIMEParser.this.len) {
                    MIMEParser.this.doubleBuf();
                }
                if (this.offset + hdrLen + 1 >= MIMEParser.this.len) {
                    assert (MIMEParser.this.eof);
                    return null;
                }
                if (MIMEParser.this.buf[this.offset + hdrLen] == 13 && MIMEParser.this.buf[this.offset + hdrLen + 1] == 10) {
                    lwsp = 2;
                    break;
                }
                ++hdrLen;
            }
            if (hdrLen == 0) {
                MIMEParser.this.adjustBuf(this.offset + lwsp, MIMEParser.this.len - this.offset - lwsp);
                return null;
            }
            String hdr2 = new String(MIMEParser.this.buf, this.offset, hdrLen, MIMEParser.HEADER_ENCODING);
            this.offset += hdrLen + lwsp;
            return hdr2;
        }
    }

    class MIMEEventIterator
    implements Iterator<MIMEEvent> {
        MIMEEventIterator() {
        }

        @Override
        public boolean hasNext() {
            return !MIMEParser.this.parsed;
        }

        @Override
        public MIMEEvent next() {
            if (MIMEParser.this.parsed) {
                throw new NoSuchElementException();
            }
            switch (MIMEParser.this.state) {
                case START_MESSAGE: {
                    if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.log(Level.FINER, "MIMEParser state={0}", (Object)STATE.START_MESSAGE);
                    }
                    MIMEParser.this.state = STATE.SKIP_PREAMBLE;
                    return MIMEEvent.START_MESSAGE;
                }
                case SKIP_PREAMBLE: {
                    if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.log(Level.FINER, "MIMEParser state={0}", (Object)STATE.SKIP_PREAMBLE);
                    }
                    MIMEParser.this.skipPreamble();
                }
                case START_PART: {
                    if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.log(Level.FINER, "MIMEParser state={0}", (Object)STATE.START_PART);
                    }
                    MIMEParser.this.state = STATE.HEADERS;
                    return MIMEEvent.START_PART;
                }
                case HEADERS: {
                    if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.log(Level.FINER, "MIMEParser state={0}", (Object)STATE.HEADERS);
                    }
                    InternetHeaders ih = MIMEParser.this.readHeaders();
                    MIMEParser.this.state = STATE.BODY;
                    MIMEParser.this.bol = true;
                    return new MIMEEvent.Headers(ih);
                }
                case BODY: {
                    if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.log(Level.FINER, "MIMEParser state={0}", (Object)STATE.BODY);
                    }
                    ByteBuffer buf = MIMEParser.this.readBody();
                    MIMEParser.this.bol = false;
                    return new MIMEEvent.Content(buf);
                }
                case END_PART: {
                    if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.log(Level.FINER, "MIMEParser state={0}", (Object)STATE.END_PART);
                    }
                    if (MIMEParser.this.done) {
                        MIMEParser.this.state = STATE.END_MESSAGE;
                    } else {
                        MIMEParser.this.state = STATE.START_PART;
                    }
                    return MIMEEvent.END_PART;
                }
                case END_MESSAGE: {
                    if (LOGGER.isLoggable(Level.FINER)) {
                        LOGGER.log(Level.FINER, "MIMEParser state={0}", (Object)STATE.END_MESSAGE);
                    }
                    MIMEParser.this.parsed = true;
                    return MIMEEvent.END_MESSAGE;
                }
            }
            throw new MIMEParsingException("Unknown Parser state = " + (Object)((Object)MIMEParser.this.state));
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static enum STATE {
        START_MESSAGE,
        SKIP_PREAMBLE,
        START_PART,
        HEADERS,
        BODY,
        END_PART,
        END_MESSAGE;

    }
}

