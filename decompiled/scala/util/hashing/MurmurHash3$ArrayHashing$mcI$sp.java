/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcI$sp
extends MurmurHash3.ArrayHashing<Object> {
    @Override
    public int hash(int[] a) {
        return this.hash$mcI$sp(a);
    }

    @Override
    public int hash$mcI$sp(int[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mIc$sp(a, 1007110753);
    }
}

