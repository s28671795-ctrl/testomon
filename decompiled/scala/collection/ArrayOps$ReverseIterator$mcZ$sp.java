/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;

public final class ArrayOps$ReverseIterator$mcZ$sp
extends ArrayOps.ReverseIterator<Object> {
    private static final long serialVersionUID = 3L;
    public final boolean[] xs$mcZ$sp;

    @Override
    public boolean next() {
        return this.next$mcZ$sp();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean next$mcZ$sp() {
        try {
            void var1_1;
            boolean r = this.xs$mcZ$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return (boolean)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToBoolean(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ReverseIterator$mcZ$sp(boolean[] xs$mcZ$sp) {
        this.xs$mcZ$sp = xs$mcZ$sp;
        super(xs$mcZ$sp);
    }
}

