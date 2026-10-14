/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxedUnit;

public final class ArrayOps$ReverseIterator$mcV$sp
extends ArrayOps.ReverseIterator<BoxedUnit> {
    private static final long serialVersionUID = 3L;
    public final BoxedUnit[] xs$mcV$sp;

    @Override
    public void next() {
        this.next$mcV$sp();
    }

    @Override
    public void next$mcV$sp() {
        try {
            BoxedUnit cfr_ignored_0 = this.xs$mcV$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            Iterator$.scala$collection$Iterator$$_empty.next();
            return;
        }
    }

    public ArrayOps$ReverseIterator$mcV$sp(BoxedUnit[] xs$mcV$sp) {
        this.xs$mcV$sp = xs$mcV$sp;
        super(xs$mcV$sp);
    }
}

