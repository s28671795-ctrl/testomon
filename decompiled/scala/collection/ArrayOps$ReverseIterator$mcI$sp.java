/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;

public final class ArrayOps$ReverseIterator$mcI$sp
extends ArrayOps.ReverseIterator<Object> {
    private static final long serialVersionUID = 3L;
    public final int[] xs$mcI$sp;

    @Override
    public int next() {
        return this.next$mcI$sp();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public int next$mcI$sp() {
        try {
            void var1_1;
            int r = this.xs$mcI$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return (int)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToInt(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ReverseIterator$mcI$sp(int[] xs$mcI$sp) {
        this.xs$mcI$sp = xs$mcI$sp;
        super(xs$mcI$sp);
    }
}

