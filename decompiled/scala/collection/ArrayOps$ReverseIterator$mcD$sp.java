/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;

public final class ArrayOps$ReverseIterator$mcD$sp
extends ArrayOps.ReverseIterator<Object> {
    private static final long serialVersionUID = 3L;
    public final double[] xs$mcD$sp;

    @Override
    public double next() {
        return this.next$mcD$sp();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public double next$mcD$sp() {
        try {
            void var1_1;
            double r = this.xs$mcD$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return (double)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToDouble(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ReverseIterator$mcD$sp(double[] xs$mcD$sp) {
        this.xs$mcD$sp = xs$mcD$sp;
        super(xs$mcD$sp);
    }
}

