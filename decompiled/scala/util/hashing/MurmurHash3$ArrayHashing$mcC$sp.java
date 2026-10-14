/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcC$sp
extends MurmurHash3.ArrayHashing<Object> {
    @Override
    public int hash(char[] a) {
        return this.hash$mcC$sp(a);
    }

    @Override
    public int hash$mcC$sp(char[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mCc$sp(a, 1007110753);
    }
}

