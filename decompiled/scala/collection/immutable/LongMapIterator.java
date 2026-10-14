/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.MatchError;
import scala.collection.AbstractIterator;
import scala.collection.immutable.LongMap;
import scala.collection.immutable.LongMap$Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000514aAD\b\u0002\u0002=)\u0002\u0002\u0003\u0015\u0001\u0005\u0003\u0005\u000b\u0011B\u0015\t\u000bA\u0002A\u0011A\u0019\t\u000fQ\u0002\u0001\u0019!C\u0001k!9\u0011\b\u0001a\u0001\n\u0003Q\u0004B\u0002!\u0001A\u0003&a\u0007C\u0004B\u0001\u0001\u0007I\u0011\u0001\"\t\u000f%\u0003\u0001\u0019!C\u0001\u0015\"1A\n\u0001Q!\n\rCQ!\u0014\u0001\u0005\u00029CQa\u0014\u0001\u0005\u0002ACQa\u0015\u0001\u0007\u0002QCQA\u0018\u0001\u0005\u0002}CQa\u0019\u0001\u0005\u0006\u0011\u0014q\u0002T8oO6\u000b\u0007/\u0013;fe\u0006$xN\u001d\u0006\u0003!E\t\u0011\"[7nkR\f'\r\\3\u000b\u0005I\u0019\u0012AC2pY2,7\r^5p]*\tA#A\u0003tG\u0006d\u0017-F\u0002\u0017]u\u0019\"\u0001A\f\u0011\u0007aI2$D\u0001\u0012\u0013\tQ\u0012C\u0001\tBEN$(/Y2u\u0013R,'/\u0019;peB\u0011A$\b\u0007\u0001\t\u0015q\u0002A1\u0001!\u0005\u0005!6\u0001A\t\u0003C\u0015\u0002\"AI\u0012\u000e\u0003MI!\u0001J\n\u0003\u000f9{G\u000f[5oOB\u0011!EJ\u0005\u0003OM\u00111!\u00118z\u0003\tIG\u000fE\u0002+W5j\u0011aD\u0005\u0003Y=\u0011q\u0001T8oO6\u000b\u0007\u000f\u0005\u0002\u001d]\u0011)q\u0006\u0001b\u0001A\t\ta+\u0001\u0004=S:LGO\u0010\u000b\u0003eM\u0002BA\u000b\u0001.7!)\u0001F\u0001a\u0001S\u0005)\u0011N\u001c3fqV\ta\u0007\u0005\u0002#o%\u0011\u0001h\u0005\u0002\u0004\u0013:$\u0018!C5oI\u0016Dx\fJ3r)\tYd\b\u0005\u0002#y%\u0011Qh\u0005\u0002\u0005+:LG\u000fC\u0004@\t\u0005\u0005\t\u0019\u0001\u001c\u0002\u0007a$\u0013'\u0001\u0004j]\u0012,\u0007\u0010I\u0001\u0007EV4g-\u001a:\u0016\u0003\r\u00032A\t#G\u0013\t)5CA\u0003BeJ\f\u0017\u0010\u0005\u0002#\u000f&\u0011\u0001j\u0005\u0002\u0007\u0003:L(+\u001a4\u0002\u0015\t,hMZ3s?\u0012*\u0017\u000f\u0006\u0002<\u0017\"9qhBA\u0001\u0002\u0004\u0019\u0015a\u00022vM\u001a,'\u000fI\u0001\u0004a>\u0004H#A\u0015\u0002\tA,8\u000f\u001b\u000b\u0003wECQA\u0015\u0006A\u0002%\n\u0011\u0001_\u0001\bm\u0006dW/Z(g)\tYR\u000bC\u0003W\u0017\u0001\u0007q+A\u0002uSB\u00042\u0001W..\u001d\tQ\u0013,\u0003\u0002[\u001f\u00059Aj\u001c8h\u001b\u0006\u0004\u0018B\u0001/^\u0005\r!\u0016\u000e\u001d\u0006\u00035>\tq\u0001[1t\u001d\u0016DH/F\u0001a!\t\u0011\u0013-\u0003\u0002c'\t9!i\\8mK\u0006t\u0017\u0001\u00028fqR$\u0012a\u0007\u0015\u0003\u001b\u0019\u0004\"a\u001a6\u000e\u0003!T!![\n\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002lQ\n9A/Y5me\u0016\u001c\u0007")
public abstract class LongMapIterator<V, T>
extends AbstractIterator<T> {
    private int index = 0;
    private Object[] buffer = new Object[65];

    public int index() {
        return this.index;
    }

    public void index_$eq(int x$1) {
        this.index = x$1;
    }

    public Object[] buffer() {
        return this.buffer;
    }

    public void buffer_$eq(Object[] x$1) {
        this.buffer = x$1;
    }

    public LongMap<V> pop() {
        this.index_$eq(this.index() - 1);
        return (LongMap)this.buffer()[this.index()];
    }

    public void push(LongMap<V> x) {
        this.buffer()[this.index()] = x;
        this.index_$eq(this.index() + 1);
    }

    public abstract T valueOf(LongMap.Tip<V> var1);

    @Override
    public boolean hasNext() {
        return this.index() != 0;
    }

    @Override
    public final T next() {
        LongMap<V> longMap;
        while (true) {
            boolean bl = false;
            LongMap.Bin bin = null;
            longMap = this.pop();
            if (longMap instanceof LongMap.Bin) {
                bl = true;
                bin = (LongMap.Bin)longMap;
                LongMap t2 = bin.left();
                LongMap right = bin.right();
                if (t2 instanceof LongMap.Tip) {
                    LongMap.Tip tip = (LongMap.Tip)t2;
                    this.push(right);
                    return this.valueOf(tip);
                }
            }
            if (!bl) break;
            LongMap left = bin.left();
            LongMap right = bin.right();
            this.push(right);
            this.push(left);
        }
        if (longMap instanceof LongMap.Tip) {
            LongMap.Tip tip = (LongMap.Tip)longMap;
            return this.valueOf(tip);
        }
        if (((Object)LongMap$Nil$.MODULE$).equals(longMap)) {
            throw new IllegalStateException("Empty maps not allowed as subtrees");
        }
        throw new MatchError(longMap);
    }

    public LongMapIterator(LongMap<V> it) {
        this.push(it);
    }
}

