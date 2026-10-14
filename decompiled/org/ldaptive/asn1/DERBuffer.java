/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

public interface DERBuffer {
    public int position();

    public DERBuffer position(int var1);

    public int limit();

    public DERBuffer limit(int var1);

    public DERBuffer clear();

    default public int remaining() {
        return this.limit() - this.position();
    }

    default public boolean hasRemaining() {
        return this.position() < this.limit();
    }

    public int capacity();

    public byte get();

    public DERBuffer get(byte[] var1);

    default public byte[] getRemainingBytes() {
        byte[] b = new byte[this.remaining()];
        this.get(b);
        return b;
    }

    public DERBuffer slice();
}

