/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4A\u0001D\u0007\u0005)!AA\u0005\u0001B\u0001B\u0003%\u0011\u0004\u0003\u0005&\u0001\t\u0005\t\u0015!\u0003\u001a\u0011!1\u0003A!A!\u0002\u0013I\u0002\u0002C\u0014\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0015\t\u000b-\u0002A\u0011\u0001\u0017\t\rM\u0002\u0001\u0015)\u0003)\u0011\u0019!\u0004\u0001)Q\u00053!)Q\u0007\u0001C!m!)q\u0007\u0001C\u0001q!)\u0011\b\u0001C\u0001u!)A\r\u0001C!K\ni!+\u00198hK&#XM]1u_JT!AD\b\u0002\u0013%lW.\u001e;bE2,'B\u0001\t\u0012\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002%\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u0016;A\u0019acF\r\u000e\u0003=I!\u0001G\b\u0003!\u0005\u00137\u000f\u001e:bGRLE/\u001a:bi>\u0014\bC\u0001\u000e\u001c\u001b\u0005\t\u0012B\u0001\u000f\u0012\u0005\rIe\u000e\u001e\t\u0003=\u0005r!AG\u0010\n\u0005\u0001\n\u0012a\u00029bG.\fw-Z\u0005\u0003E\r\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001I\t\u0002\u000bM$\u0018M\u001d;\u0002\tM$X\r]\u0001\fY\u0006\u001cH/\u00127f[\u0016tG/\u0001\bj]&$\u0018.\u00197ms\u0016k\u0007\u000f^=\u0011\u0005iI\u0013B\u0001\u0016\u0012\u0005\u001d\u0011un\u001c7fC:\fa\u0001P5oSRtD#B\u00170aE\u0012\u0004C\u0001\u0018\u0001\u001b\u0005i\u0001\"\u0002\u0013\u0006\u0001\u0004I\u0002\"B\u0013\u0006\u0001\u0004I\u0002\"\u0002\u0014\u0006\u0001\u0004I\u0002\"B\u0014\u0006\u0001\u0004A\u0013\u0001C0iCNtU\r\u001f;\u0002\u000b}sW\r\u001f;\u0002\u0013-twn\u001e8TSj,W#A\r\u0002\u000f!\f7OT3yiV\t\u0001&\u0001\u0003oKb$H#A\r)\u0007)a$\tE\u0002\u001b{}J!AP\t\u0003\rQD'o\\<t!\tq\u0002)\u0003\u0002BG\t1bj\\*vG\",E.Z7f]R,\u0005pY3qi&|g.\r\u0003\u001f\u0007:\u001b\u0007C\u0001#L\u001d\t)\u0015\n\u0005\u0002G#5\tqI\u0003\u0002I'\u00051AH]8pizJ!AS\t\u0002\rA\u0013X\rZ3g\u0013\taUJ\u0001\u0004TiJLgn\u001a\u0006\u0003\u0015F\tTaI(T=R+\"\u0001U)\u0016\u0003\r#QAU\nC\u0002]\u0013\u0011\u0001V\u0005\u0003)V\u000b1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n$B\u0001,\u0012\u0003\u0019!\bN]8xgF\u0011\u0001l\u0017\t\u00035eK!AW\t\u0003\u000f9{G\u000f[5oOB\u0011a\u0004X\u0005\u0003;\u000e\u0012\u0011\u0002\u00165s_^\f'\r\\32\u000b\rz\u0006-\u0019,\u000f\u0005i\u0001\u0017B\u0001,\u0012c\u0011\u0011#$\u00052\u0003\u000bM\u001c\u0017\r\\12\u0005\u0019z\u0014\u0001\u00023s_B$\"AZ5\u0011\u0007Y9\u0017$\u0003\u0002i\u001f\tA\u0011\n^3sCR|'\u000fC\u0003k\u0017\u0001\u0007\u0011$A\u0001oQ\u0011\u0001An\u001c9\u0011\u0005ii\u0017B\u00018\u0012\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004\u0001")
public class RangeIterator
extends AbstractIterator<Object>
implements Serializable {
    private static final long serialVersionUID = 3L;
    private final int step;
    private final int lastElement;
    private boolean _hasNext;
    private int _next;

    @Override
    public int knownSize() {
        if (this._hasNext) {
            return (this.lastElement - this._next) / this.step + 1;
        }
        return 0;
    }

    @Override
    public boolean hasNext() {
        return this._hasNext;
    }

    @Override
    public int next() throws NoSuchElementException {
        int value;
        if (!this._hasNext) {
            Iterator$.scala$collection$Iterator$$_empty.next();
        }
        this._hasNext = (value = this._next) != this.lastElement;
        this._next = value + this.step;
        return value;
    }

    @Override
    public Iterator<Object> drop(int n) {
        if (n > 0) {
            long longPos = (long)this._next + (long)(this.step * n);
            if (this.step > 0) {
                this._next = (int)Math.min((long)this.lastElement, longPos);
                this._hasNext = longPos <= (long)this.lastElement;
            } else if (this.step < 0) {
                this._next = (int)Math.max((long)this.lastElement, longPos);
                this._hasNext = longPos >= (long)this.lastElement;
            }
        }
        return this;
    }

    public RangeIterator(int start2, int step, int lastElement, boolean initiallyEmpty) {
        this.step = step;
        this.lastElement = lastElement;
        this._hasNext = !initiallyEmpty;
        this._next = start2;
    }
}

