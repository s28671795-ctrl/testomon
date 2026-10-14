/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.util.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class CachingOutputStream
extends OutputStream {
    private final Path path;
    private FileChannel channel;
    private ByteBuffer readBuffer;
    private ByteBuffer writeBuffer;
    private boolean modified;

    public CachingOutputStream(File path) throws IOException {
        this(Objects.requireNonNull(path).toPath());
    }

    public CachingOutputStream(Path path) throws IOException {
        this(path, 32768);
    }

    public CachingOutputStream(Path path, int bufferSize) throws IOException {
        this.path = Objects.requireNonNull(path);
        this.channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        this.readBuffer = ByteBuffer.allocate(bufferSize);
        this.writeBuffer = ByteBuffer.allocate(bufferSize);
    }

    @Override
    public void write(int b) throws IOException {
        if (this.writeBuffer.remaining() < 1) {
            ((Buffer)this.writeBuffer).flip();
            this.flushBuffer(this.writeBuffer);
            ((Buffer)this.writeBuffer).clear();
        }
        this.writeBuffer.put((byte)b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (this.writeBuffer.remaining() < len) {
            ((Buffer)this.writeBuffer).flip();
            this.flushBuffer(this.writeBuffer);
            ((Buffer)this.writeBuffer).clear();
        }
        int capacity = this.writeBuffer.capacity();
        while (len >= capacity) {
            this.flushBuffer(ByteBuffer.wrap(b, off, capacity));
            off += capacity;
            len -= capacity;
        }
        if (len > 0) {
            this.writeBuffer.put(b, off, len);
        }
    }

    @Override
    public void flush() throws IOException {
        ((Buffer)this.writeBuffer).flip();
        this.flushBuffer(this.writeBuffer);
        ((Buffer)this.writeBuffer).clear();
        super.flush();
    }

    private void flushBuffer(ByteBuffer writeBuffer) throws IOException {
        if (this.modified) {
            this.channel.write(writeBuffer);
        } else {
            ByteBuffer readBuffer;
            int len = writeBuffer.remaining();
            if (this.readBuffer.capacity() >= len) {
                readBuffer = this.readBuffer;
                ((Buffer)readBuffer).clear();
            } else {
                readBuffer = ByteBuffer.allocate(len);
            }
            while (len > 0) {
                int read = this.channel.read(readBuffer);
                if (read <= 0) {
                    this.modified = true;
                    this.channel.position(this.channel.position() - (long)readBuffer.position());
                    this.channel.write(writeBuffer);
                    return;
                }
                len -= read;
            }
            ((Buffer)readBuffer).flip();
            if (readBuffer.compareTo(writeBuffer) != 0) {
                this.modified = true;
                this.channel.position(this.channel.position() - (long)readBuffer.remaining());
                this.channel.write(writeBuffer);
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (this.channel.isOpen()) {
            this.flush();
            long position = this.channel.position();
            if (position != this.channel.size()) {
                this.modified = true;
                this.channel.truncate(position);
            }
            this.channel.close();
        }
    }

    public boolean isModified() {
        return this.modified;
    }
}

