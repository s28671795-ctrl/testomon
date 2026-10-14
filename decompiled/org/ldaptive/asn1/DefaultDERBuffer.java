/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.nio.ByteBuffer;
import org.ldaptive.asn1.DERBuffer;

public class DefaultDERBuffer
implements DERBuffer {
    private final ByteBuffer buffer;

    public DefaultDERBuffer(int capacity) {
        this.buffer = ByteBuffer.allocate(capacity);
    }

    public DefaultDERBuffer(byte[] array) {
        this.buffer = ByteBuffer.wrap(array);
    }

    public DefaultDERBuffer(ByteBuffer buf) {
        this.buffer = buf;
    }

    public DefaultDERBuffer(ByteBuffer buf, int pos, int lim) {
        this.buffer = buf;
        this.buffer.position(pos);
        this.buffer.limit(lim);
    }

    @Override
    public int position() {
        return this.buffer.position();
    }

    @Override
    public DERBuffer position(int newPosition) {
        this.buffer.position(newPosition);
        return this;
    }

    @Override
    public int limit() {
        return this.buffer.limit();
    }

    @Override
    public int capacity() {
        return this.buffer.capacity();
    }

    @Override
    public DERBuffer limit(int newLimit) {
        this.buffer.limit(newLimit);
        return this;
    }

    @Override
    public DERBuffer clear() {
        this.buffer.clear();
        return this;
    }

    @Override
    public byte get() {
        return this.buffer.get();
    }

    @Override
    public DERBuffer get(byte[] dst) {
        this.buffer.get(dst);
        return this;
    }

    @Override
    public DERBuffer slice() {
        return new DefaultDERBuffer(this.buffer.slice());
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "pos=" + this.position() + ", " + "lim=" + this.limit() + ", " + "cap=" + this.capacity();
    }
}

