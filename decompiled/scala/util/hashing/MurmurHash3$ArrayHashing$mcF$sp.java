/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcF$sp
extends MurmurHash3.ArrayHashing<Object> {
    @Override
    public int hash(float[] a) {
        return this.hash$mcF$sp(a);
    }

    @Override
    public int hash$mcF$sp(float[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mFc$sp(a, 1007110753);
    }
}

