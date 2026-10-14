/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import scala.runtime.BoxedUnit;
import scala.util.hashing.MurmurHash3;
import scala.util.hashing.MurmurHash3$;

public class MurmurHash3$ArrayHashing$mcV$sp
extends MurmurHash3.ArrayHashing<BoxedUnit> {
    @Override
    public int hash(BoxedUnit[] a) {
        return this.hash$mcV$sp(a);
    }

    @Override
    public int hash$mcV$sp(BoxedUnit[] a) {
        return MurmurHash3$.MODULE$.arrayHash$mVc$sp(a, 1007110753);
    }
}

