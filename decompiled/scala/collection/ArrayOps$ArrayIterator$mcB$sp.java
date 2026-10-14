/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;

public final class ArrayOps$ArrayIterator$mcB$sp
extends ArrayOps.ArrayIterator<Object> {
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
            byte r = this.xs$mcB$sp[this.scala$collection$ArrayOps$ArrayIterator$$pos];
            ++this.scala$collection$ArrayOps$ArrayIterator$$pos;
            return (byte)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToByte(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ArrayIterator$mcB$sp(byte[] xs$mcB$sp) {
        this.xs$mcB$sp = xs$mcB$sp;
        super(xs$mcB$sp);
        Statics.releaseFence();
    }
}

