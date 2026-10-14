/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.falcon;

class FalconConversions {
    FalconConversions() {
    }

    byte[] int_to_bytes(int n) {
        byte[] byArray = new byte[]{(byte)(n >>> 0), (byte)(n >>> 8), (byte)(n >>> 16), (byte)(n >>> 24)};
        return byArray;
    }

    int bytes_to_int(byte[] byArray, int n) {
        int n2 = 0;
        n2 = this.toUnsignedInt(byArray[n + 0]) << 0 | this.toUnsignedInt(byArray[n + 1]) << 8 | this.toUnsignedInt(byArray[n + 2]) << 16 | this.toUnsignedInt(byArray[n + 3]) << 24;
        return n2;
    }

    int[] bytes_to_int_array(byte[] byArray, int n, int n2) {
        int[] nArray = new int[n2];
        for (int i = 0; i < n2; ++i) {
            nArray[i] = this.bytes_to_int(byArray, n + 4 * i);
        }
        return nArray;
    }

    byte[] long_to_bytes(long l) {
        byte[] byArray = new byte[]{(byte)(l >>> 0), (byte)(l >>> 8), (byte)(l >>> 16), (byte)(l >>> 24), (byte)(l >>> 32), (byte)(l >>> 40), (byte)(l >>> 48), (byte)(l >>> 56)};
        return byArray;
    }

    long bytes_to_long(byte[] byArray, int n) {
        long l = 0L;
        l = this.toUnsignedLong(byArray[n + 0]) << 0 | this.toUnsignedLong(byArray[n + 1]) << 8 | this.toUnsignedLong(byArray[n + 2]) << 16 | this.toUnsignedLong(byArray[n + 3]) << 24 | this.toUnsignedLong(byArray[n + 4]) << 32 | this.toUnsignedLong(byArray[n + 5]) << 40 | this.toUnsignedLong(byArray[n + 6]) << 48 | this.toUnsignedLong(byArray[n + 7]) << 56;
        return l;
    }

    private int toUnsignedInt(byte by) {
        return by & 0xFF;
    }

    private long toUnsignedLong(byte by) {
        return (long)by & 0xFFL;
    }
}

