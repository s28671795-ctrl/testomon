/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

public final class ArrayOps$ArrayIterator$mcS$sp
extends ArrayOps.ArrayIterator<Object> {
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
            short r = this.xs$mcS$sp[this.scala$collection$ArrayOps$ArrayIterator$$pos];
            ++this.scala$collection$ArrayOps$ArrayIterator$$pos;
            return (short)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToShort(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ArrayIterator$mcS$sp(short[] xs$mcS$sp) {
        this.xs$mcS$sp = xs$mcS$sp;
        super(xs$mcS$sp);
        Statics.releaseFence();
    }
}

