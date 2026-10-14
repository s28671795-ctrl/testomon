/*
 * Decompiled with CFR 0.152.
 */
package scala.util.hashing;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;
import scala.util.hashing.ByteswapHashing;
import scala.util.hashing.Hashing;

public final class ByteswapHashing$
implements Serializable {
    public static final ByteswapHashing$ MODULE$ = new ByteswapHashing$();

    public <T> Hashing<T> chain(Hashing<T> h2) {
        return new ByteswapHashing.Chained<T>(h2);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ByteswapHashing$.class);
    }

    private ByteswapHashing$() {
    }
}

