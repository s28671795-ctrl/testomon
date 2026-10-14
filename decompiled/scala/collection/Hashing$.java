/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.runtime.Statics;

public final class Hashing$ {
    public static final Hashing$ MODULE$ = new Hashing$();

    public int elemHashCode(Object key) {
        return Statics.anyHash(key);
    }

    public int improve(int hcode) {
        int h2 = hcode + ~(hcode << 9);
        h2 ^= h2 >>> 14;
        h2 += h2 << 4;
        return h2 ^ h2 >>> 10;
    }

    public int computeHash(Object key) {
        return this.improve(Statics.anyHash(key));
    }

    public int keepBits(int bitmap, int keep) {
        int result = 0;
        int current = bitmap;
        for (int kept = keep; kept != 0; kept >>>= 1) {
            int lsb = current ^ current & current - 1;
            if ((kept & 1) != 0) {
                result |= lsb;
            }
            current &= ~lsb;
        }
        return result;
    }

    private Hashing$() {
    }
}

