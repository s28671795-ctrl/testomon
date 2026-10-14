/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcD$sp
extends MurmurHash3.ArrayHashing<Object> {
    @Override
    public int hash(double[] a) {
        return this.hash$mcD$sp(a);
    }

    @Override
    public int hash$mcD$sp(double[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mDc$sp(a, 1007110753);
    }
}

