/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcZ$sp
extends MurmurHash3.ArrayHashing<Object> {
    @Override
    public int hash(boolean[] a) {
        return this.hash$mcZ$sp(a);
    }

    @Override
    public int hash$mcZ$sp(boolean[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mZc$sp(a, 1007110753);
    }
}

