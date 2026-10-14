/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

public final class ArrayOps$ArrayIterator$mcJ$sp
extends ArrayOps.ArrayIterator<Object> {
    private static final long serialVersionUID = 3L;
    public final long[] xs$mcJ$sp;

    @Override
    public long next() {
        return this.next$mcJ$sp();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public long next$mcJ$sp() {
        try {
            void var1_1;
            long r = this.xs$mcJ$sp[this.scala$collection$ArrayOps$ArrayIterator$$pos];
            ++this.scala$collection$ArrayOps$ArrayIterator$$pos;
            return (long)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToLong(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ArrayIterator$mcJ$sp(long[] xs$mcJ$sp) {
        this.xs$mcJ$sp = xs$mcJ$sp;
        super(xs$mcJ$sp);
        Statics.releaseFence();
    }
}

