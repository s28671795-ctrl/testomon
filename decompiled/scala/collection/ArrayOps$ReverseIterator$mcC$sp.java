/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.ArrayOps;
import scala.collection.Iterator$;
import scala.runtime.BoxesRunTime;

public final class ArrayOps$ReverseIterator$mcC$sp
extends ArrayOps.ReverseIterator<Object> {
    private static final long serialVersionUID = 3L;
    public final char[] xs$mcC$sp;

    @Override
    public char next() {
        return this.next$mcC$sp();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public char next$mcC$sp() {
        try {
            void var1_1;
            char r = this.xs$mcC$sp[this.scala$collection$ArrayOps$ReverseIterator$$pos];
            --this.scala$collection$ArrayOps$ReverseIterator$$pos;
            return (char)var1_1;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return BoxesRunTime.unboxToChar(Iterator$.scala$collection$Iterator$$_empty.next());
        }
    }

    public ArrayOps$ReverseIterator$mcC$sp(char[] xs$mcC$sp) {
        this.xs$mcC$sp = xs$mcC$sp;
        super(xs$mcC$sp);
    }
}

