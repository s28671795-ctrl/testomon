/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcB$sp
extends MurmurHash3.ArrayHashing<Object> {
    @Override
    public int hash(byte[] a) {
        return this.hash$mcB$sp(a);
    }

    @Override
    public int hash$mcB$sp(byte[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mBc$sp(a, 1007110753);
    }
}

