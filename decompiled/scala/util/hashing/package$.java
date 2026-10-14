/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

public final class package$ {
    public static final package$ MODULE$ = new package$();

    public int byteswap32(int v) {
        return Integer.reverseBytes(v * -1640532531) * -1640532531;
    }

    public long byteswap64(long v) {
        return Long.reverseBytes(v * -7046033566014671411L) * -7046033566014671411L;
    }

    private package$() {
    }
}

