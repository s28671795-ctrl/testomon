/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;

public final class ArrayOps$ReverseIterator$mcJ$sp
extends ArrayOps.ReverseIterator<Object> {
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
            long r = this.xs$mcJ$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return (long)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToLong(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ReverseIterator$mcJ$sp(long[] xs$mcJ$sp) {
        this.xs$mcJ$sp = xs$mcJ$sp;
        super(xs$mcJ$sp);
    }
}

