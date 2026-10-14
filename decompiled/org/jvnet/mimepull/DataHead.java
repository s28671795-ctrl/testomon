/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.jvnet.mimepull.Chunk;
import org.jvnet.mimepull.DataFile;
import org.jvnet.mimepull.MIMEParsingException;
import org.jvnet.mimepull.MIMEPart;
import org.jvnet.mimepull.MemoryData;

final class DataHead {
    volatile Chunk head;
    volatile Chunk tail;
    DataFile dataFile;
    private final MIMEPart part;
    boolean readOnce;
    volatile long inMemory;
    private Throwable consumedAt;

    DataHead(MIMEPart part) {
        this.part = part;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addBody(ByteBuffer buf) {
        DataHead dataHead = this;
        synchronized (dataHead) {
            this.inMemory += (long)buf.limit();
        }
        if (this.tail != null) {
            this.tail = this.tail.createNext(this, buf);
        } else {
            this.head = this.tail = new Chunk(new MemoryData(buf, this.part.msg.config));
        }
    }

    void doneParsing() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void moveTo(File f) {
        if (this.dataFile != null) {
            this.dataFile.renameTo(f);
        } else {
            try (FileOutputStream os = new FileOutputStream(f);){
                int len;
                InputStream in = this.readOnce();
                byte[] buf = new byte[8192];
                while ((len = in.read(buf)) != -1) {
                    ((OutputStream)os).write(buf, 0, len);
                }
            }
            catch (IOException ioe) {
                throw new MIMEParsingException(ioe);
            }
        }
    }

    void close() {
        this.tail = null;
        this.head = null;
        if (this.dataFile != null) {
            this.dataFile.close();
        }
    }

    public InputStream read() {
        if (this.readOnce) {
            throw new IllegalStateException("readOnce() is called before, read() cannot be called later.");
        }
        while (this.tail == null) {
            if (this.part.msg.makeProgress()) continue;
            throw new IllegalStateException("No such MIME Part: " + this.part);
        }
        if (this.head == null) {
            throw new IllegalStateException("Already read. Probably readOnce() is called before.");
        }
        return new ReadMultiStream();
    }

    private boolean unconsumed() {
        if (this.consumedAt != null) {
            AssertionError error = new AssertionError((Object)"readOnce() is already called before. See the nested exception from where it's called.");
            ((Throwable)((Object)error)).initCause(this.consumedAt);
            throw error;
        }
        this.consumedAt = new Exception().fillInStackTrace();
        return true;
    }

    public InputStream readOnce() {
        assert (this.unconsumed());
        if (this.readOnce) {
            throw new IllegalStateException("readOnce() is called before. It can only be called once.");
        }
        this.readOnce = true;
        while (this.tail == null) {
            if (this.part.msg.makeProgress() || this.tail != null) continue;
            throw new IllegalStateException("No such Part: " + this.part);
        }
        ReadOnceStream in = new ReadOnceStream();
        this.head = null;
        return in;
    }

    final class ReadOnceStream
    extends ReadMultiStream {
        ReadOnceStream() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        void adjustInMemoryUsage() {
            DataHead dataHead = DataHead.this;
            synchronized (dataHead) {
                DataHead.this.inMemory -= (long)this.current.data.size();
            }
        }
    }

    class ReadMultiStream
    extends InputStream {
        Chunk current;
        int offset;
        int len;
        byte[] buf;
        boolean closed;

        public ReadMultiStream() {
            this.current = DataHead.this.head;
            this.len = this.current.data.size();
            this.buf = this.current.data.read();
        }

        @Override
        public int read(byte[] b, int off, int sz) throws IOException {
            if (!this.fetch()) {
                return -1;
            }
            sz = Math.min(sz, this.len - this.offset);
            System.arraycopy(this.buf, this.offset, b, off, sz);
            this.offset += sz;
            return sz;
        }

        @Override
        public int read() throws IOException {
            if (!this.fetch()) {
                return -1;
            }
            return this.buf[this.offset++] & 0xFF;
        }

        void adjustInMemoryUsage() {
        }

        private boolean fetch() throws IOException {
            if (this.closed) {
                throw new IOException("Stream already closed");
            }
            if (this.current == null) {
                return false;
            }
            while (this.offset == this.len) {
                while (!((DataHead)DataHead.this).part.parsed && this.current.next == null) {
                    ((DataHead)DataHead.this).part.msg.makeProgress();
                }
                this.current = this.current.next;
                if (this.current == null) {
                    return false;
                }
                this.adjustInMemoryUsage();
                this.offset = 0;
                this.buf = this.current.data.read();
                this.len = this.current.data.size();
            }
            return true;
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.current = null;
            this.closed = true;
        }
    }
}

