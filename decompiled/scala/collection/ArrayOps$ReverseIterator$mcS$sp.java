/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;

public final class ArrayOps$ReverseIterator$mcS$sp
extends ArrayOps.ReverseIterator<Object> {
    private static final long serialVersionUID = 3L;
    public final short[] xs$mcS$sp;

    @Override
    public short next() {
        return this.next$mcS$sp();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public short next$mcS$sp() {
        try {
            void var1_1;
            short r = this.xs$mcS$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return (short)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToShort(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ReverseIterator$mcS$sp(short[] xs$mcS$sp) {
        this.xs$mcS$sp = xs$mcS$sp;
        super(xs$mcS$sp);
    }
}

