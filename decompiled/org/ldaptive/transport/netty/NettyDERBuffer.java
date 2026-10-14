/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport.netty;

import io.netty.buffer.ByteBuf;
import org.ldaptive.asn1.DERBuffer;

public class NettyDERBuffer
implements DERBuffer {
    private final ByteBuf buffer;

    public NettyDERBuffer(ByteBuf buf) {
        this(buf, 0, buf.capacity());
    }

    public NettyDERBuffer(ByteBuf buf, int pos, int lim) {
        this.buffer = buf;
        this.buffer.setIndex(pos, lim);
    }

    @Override
    public int position() {
        return this.buffer.readerIndex();
    }

    @Override
    public DERBuffer position(int newPosition) {
        this.buffer.readerIndex(newPosition);
        return this;
    }

    @Override
    public int limit() {
        return this.buffer.writerIndex();
    }

    @Override
    public int capacity() {
        return this.buffer.capacity();
    }

    @Override
    public DERBuffer limit(int newLimit) {
        this.buffer.writerIndex(newLimit);
        if (this.buffer.readerIndex() > newLimit) {
            this.buffer.readerIndex(newLimit);
        }
        return this;
    }

    @Override
    public DERBuffer clear() {
        this.buffer.setIndex(0, this.buffer.capacity());
        return this;
    }

    @Override
    public byte get() {
        return this.buffer.readByte();
    }

    @Override
    public DERBuffer get(byte[] dst) {
        this.buffer.readBytes(dst);
        return this;
    }

    @Override
    public DERBuffer slice() {
        return new NettyDERBuffer(this.buffer.slice(this.position(), this.remaining()));
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "pos=" + this.position() + ", " + "lim=" + this.limit() + ", " + "cap=" + this.capacity();
    }
}

