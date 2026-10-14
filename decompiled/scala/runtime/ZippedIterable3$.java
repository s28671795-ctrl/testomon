/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Tuple3;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.runtime.ZippedIterable3;

public final class ZippedIterable3$ {
    public static final ZippedIterable3$ MODULE$ = new ZippedIterable3$();

    public <El1, El2, El3> Iterable<Tuple3<El1, El2, El3>> zippedIterable3ToIterable(ZippedIterable3<El1, El2, El3> zz) {
        return new AbstractIterable<Tuple3<El1, El2, El3>>(zz){
            private final ZippedIterable3 zz$1;

            public Iterator<Tuple3<El1, El2, El3>> iterator() {
                return this.zz$1.iterator();
            }

            public boolean isEmpty() {
                return this.zz$1.isEmpty();
            }
            {
                this.zz$1 = zz$1;
            }
        };
    }

    private ZippedIterable3$() {
    }
}

