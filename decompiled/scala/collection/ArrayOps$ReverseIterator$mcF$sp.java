/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;

public final class ArrayOps$ReverseIterator$mcF$sp
extends ArrayOps.ReverseIterator<Object> {
    private static final long serialVersionUID = 3L;
    public final float[] xs$mcF$sp;

    @Override
    public float next() {
        return this.next$mcF$sp();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public float next$mcF$sp() {
        try {
            void var1_1;
            float r = this.xs$mcF$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return (float)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToFloat(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ReverseIterator$mcF$sp(float[] xs$mcF$sp) {
        this.xs$mcF$sp = xs$mcF$sp;
        super(xs$mcF$sp);
    }
}

