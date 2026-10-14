/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcJ$sp
extends MurmurHash3.ArrayHashing<Object> {
    @Override
    public int hash(long[] a) {
        return this.hash$mcJ$sp(a);
    }

    @Override
    public int hash$mcJ$sp(long[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mJc$sp(a, 1007110753);
    }
}

