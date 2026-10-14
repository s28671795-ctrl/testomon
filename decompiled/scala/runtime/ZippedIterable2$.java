/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Tuple2;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.runtime.ZippedIterable2;

public final class ZippedIterable2$ {
    public static final ZippedIterable2$ MODULE$ = new ZippedIterable2$();

    public <El1, El2> Iterable<Tuple2<El1, El2>> zippedIterable2ToIterable(ZippedIterable2<El1, El2> zz) {
        return new AbstractIterable<Tuple2<El1, El2>>(zz){
            private final ZippedIterable2 zz$1;

            public Iterator<Tuple2<El1, El2>> iterator() {
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

    private ZippedIterable2$() {
    }
}

