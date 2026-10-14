/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

public final class ArrayOps$ArrayIterator$mcZ$sp
extends ArrayOps.ArrayIterator<Object> {
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
            boolean r = this.xs$mcZ$sp[this.scala$collection$ArrayOps$ArrayIterator$$pos];
            ++this.scala$collection$ArrayOps$ArrayIterator$$pos;
            return (boolean)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToBoolean(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ArrayIterator$mcZ$sp(boolean[] xs$mcZ$sp) {
        this.xs$mcZ$sp = xs$mcZ$sp;
        super(xs$mcZ$sp);
        Statics.releaseFence();
    }
}

