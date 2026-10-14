/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class NIOBufferedInputStream
extends InputStream {
    private static final int BUFFER_SIZE = 8192;
    private SocketChannel sc = null;
    private ByteBuffer bb = null;
    private Selector rs = null;

    public NIOBufferedInputStream(ReadableByteChannel channel, int size) throws ClosedChannelException, IOException {
        if (size <= 0) {
            throw new IllegalArgumentException("Buffer size <= 0");
        }
        this.bb = ByteBuffer.allocateDirect(size);
        this.sc = (SocketChannel)channel;
        this.sc.configureBlocking(false);
        this.rs = Selector.open();
        this.sc.register(this.rs, 1);
        this.bb.position(0);
        this.bb.limit(0);
    }

    public NIOBufferedInputStream(ReadableByteChannel channel) throws ClosedChannelException, IOException {
        this(channel, 8192);
    }

    @Override
    public int available() throws IOException {
        if (!this.rs.isOpen()) {
            throw new IOException("Input Stream Closed");
        }
        return this.bb.remaining();
    }

    @Override
    public void close() throws IOException {
        if (this.rs.isOpen()) {
            this.rs.close();
            if (this.sc.isOpen()) {
                this.sc.socket().shutdownInput();
                this.sc.socket().close();
            }
            this.bb = null;
            this.sc = null;
        }
    }

    @Override
    public int read() throws IOException {
        if (!this.rs.isOpen()) {
            throw new IOException("Input Stream Closed");
        }
        if (!this.bb.hasRemaining()) {
            try {
                this.fill(1);
            }
            catch (ClosedChannelException e) {
                this.close();
                return -1;
            }
        }
        return this.bb.get() & 0xFF;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int bytesCopied = -1;
        if (!this.rs.isOpen()) {
            throw new IOException("Input Stream Closed");
        }
        while (bytesCopied == -1) {
            if (this.bb.hasRemaining()) {
                bytesCopied = len < this.bb.remaining() ? len : this.bb.remaining();
                this.bb.get(b, off, bytesCopied);
                continue;
            }
            try {
                this.fill(1);
            }
            catch (ClosedChannelException e) {
                this.close();
                return -1;
            }
        }
        return bytesCopied;
    }

    @Override
    public long skip(long n) throws IOException {
        long skiped = 0L;
        if (!this.rs.isOpen()) {
            throw new IOException("Input Stream Closed");
        }
        while (n > 0L) {
            if (n <= (long)this.bb.remaining()) {
                skiped += n;
                this.bb.position(this.bb.position() + (int)n);
                n = 0L;
                continue;
            }
            skiped += (long)this.bb.remaining();
            n -= (long)this.bb.remaining();
            this.bb.position(this.bb.limit());
            try {
                this.fill((int)n);
            }
            catch (ClosedChannelException e) {
                this.close();
                return skiped;
            }
        }
        return skiped;
    }

    private void fill(int n) throws IOException, ClosedChannelException {
        int bytesRead = -1;
        if (n <= 0 || n <= this.bb.remaining()) {
            return;
        }
        this.bb.compact();
        int n2 = n = this.bb.remaining() < n ? this.bb.remaining() : n;
        while (true) {
            if ((bytesRead = this.sc.read(this.bb)) == -1) {
                throw new ClosedChannelException();
            }
            if ((n -= bytesRead) <= 0) break;
            this.rs.select(0L);
            this.rs.selectedKeys().clear();
        }
        this.bb.flip();
    }
}

