/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;

public final class ArrayOps$ReverseIterator$mcB$sp
extends ArrayOps.ReverseIterator<Object> {
    private static final long serialVersionUID = 3L;
    public final byte[] xs$mcB$sp;

    @Override
    public byte next() {
        return this.next$mcB$sp();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public byte next$mcB$sp() {
        try {
            void var1_1;
            byte r = this.xs$mcB$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return (byte)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToByte(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ReverseIterator$mcB$sp(byte[] xs$mcB$sp) {
        this.xs$mcB$sp = xs$mcB$sp;
        super(xs$mcB$sp);
    }
}

