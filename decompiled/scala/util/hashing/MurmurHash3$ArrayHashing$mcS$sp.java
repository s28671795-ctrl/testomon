/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcS$sp
extends MurmurHash3.ArrayHashing<Object> {
    @Override
    public int hash(short[] a) {
        return this.hash$mcS$sp(a);
    }

    @Override
    public int hash$mcS$sp(short[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mSc$sp(a, 1007110753);
    }
}

