/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.MatchError;
import scala.collection.AbstractIterator;
import scala.collection.immutable.IntMap;
import scala.collection.immutable.IntMap$Nil$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000514aAD\b\u0002\u0002=)\u0002\u0002\u0003\u0015\u0001\u0005\u0003\u0005\u000b\u0011B\u0015\t\u000bA\u0002A\u0011A\u0019\t\u000fQ\u0002\u0001\u0019!C\u0001k!9\u0011\b\u0001a\u0001\n\u0003Q\u0004B\u0002!\u0001A\u0003&a\u0007C\u0004B\u0001\u0001\u0007I\u0011\u0001\"\t\u000f%\u0003\u0001\u0019!C\u0001\u0015\"1A\n\u0001Q!\n\rCQ!\u0014\u0001\u0005\u00029CQa\u0014\u0001\u0005\u0002ACQa\u0015\u0001\u0007\u0002QCQA\u0018\u0001\u0005\u0002}CQa\u0019\u0001\u0005\u0006\u0011\u0014a\"\u00138u\u001b\u0006\u0004\u0018\n^3sCR|'O\u0003\u0002\u0011#\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003%M\t!bY8mY\u0016\u001cG/[8o\u0015\u0005!\u0012!B:dC2\fWc\u0001\f/;M\u0011\u0001a\u0006\t\u00041eYR\"A\t\n\u0005i\t\"\u0001E!cgR\u0014\u0018m\u0019;Ji\u0016\u0014\u0018\r^8s!\taR\u0004\u0004\u0001\u0005\u000by\u0001!\u0019\u0001\u0011\u0003\u0003Q\u001b\u0001!\u0005\u0002\"KA\u0011!eI\u0007\u0002'%\u0011Ae\u0005\u0002\b\u001d>$\b.\u001b8h!\t\u0011c%\u0003\u0002('\t\u0019\u0011I\\=\u0002\u0005%$\bc\u0001\u0016,[5\tq\"\u0003\u0002-\u001f\t1\u0011J\u001c;NCB\u0004\"\u0001\b\u0018\u0005\u000b=\u0002!\u0019\u0001\u0011\u0003\u0003Y\u000ba\u0001P5oSRtDC\u0001\u001a4!\u0011Q\u0003!L\u000e\t\u000b!\u0012\u0001\u0019A\u0015\u0002\u000b%tG-\u001a=\u0016\u0003Y\u0002\"AI\u001c\n\u0005a\u001a\"aA%oi\u0006I\u0011N\u001c3fq~#S-\u001d\u000b\u0003wy\u0002\"A\t\u001f\n\u0005u\u001a\"\u0001B+oSRDqa\u0010\u0003\u0002\u0002\u0003\u0007a'A\u0002yIE\na!\u001b8eKb\u0004\u0013A\u00022vM\u001a,'/F\u0001D!\r\u0011CIR\u0005\u0003\u000bN\u0011Q!\u0011:sCf\u0004\"AI$\n\u0005!\u001b\"AB!osJ+g-\u0001\u0006ck\u001a4WM]0%KF$\"aO&\t\u000f}:\u0011\u0011!a\u0001\u0007\u00069!-\u001e4gKJ\u0004\u0013a\u00019paV\t\u0011&\u0001\u0003qkNDGCA\u001eR\u0011\u0015\u0011&\u00021\u0001*\u0003\u0005A\u0018a\u0002<bYV,wJ\u001a\u000b\u00037UCQAV\u0006A\u0002]\u000b1\u0001^5q!\rA6,\f\b\u0003UeK!AW\b\u0002\r%sG/T1q\u0013\taVLA\u0002USBT!AW\b\u0002\u000f!\f7OT3yiV\t\u0001\r\u0005\u0002#C&\u0011!m\u0005\u0002\b\u0005>|G.Z1o\u0003\u0011qW\r\u001f;\u0015\u0003mA#!\u00044\u0011\u0005\u001dTW\"\u00015\u000b\u0005%\u001c\u0012AC1o]>$\u0018\r^5p]&\u00111\u000e\u001b\u0002\bi\u0006LGN]3d\u0001")
public abstract class IntMapIterator<V, T>
extends AbstractIterator<T> {
    private int index = 0;
    private Object[] buffer = new Object[33];

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

    public IntMap<V> pop() {
        this.index_$eq(this.index() - 1);
        return (IntMap)this.buffer()[this.index()];
    }

    public void push(IntMap<V> x) {
        this.buffer()[this.index()] = x;
        this.index_$eq(this.index() + 1);
    }

    public abstract T valueOf(IntMap.Tip<V> var1);

    @Override
    public boolean hasNext() {
        return this.index() != 0;
    }

    @Override
    public final T next() {
        IntMap<V> intMap;
        while (true) {
            boolean bl = false;
            IntMap.Bin bin = null;
            intMap = this.pop();
            if (intMap instanceof IntMap.Bin) {
                bl = true;
                bin = (IntMap.Bin)intMap;
                IntMap t2 = bin.left();
                IntMap right = bin.right();
                if (t2 instanceof IntMap.Tip) {
                    IntMap.Tip tip = (IntMap.Tip)t2;
                    this.push(right);
                    return this.valueOf(tip);
                }
            }
            if (!bl) break;
            IntMap left = bin.left();
            IntMap right = bin.right();
            this.push(right);
            this.push(left);
        }
        if (intMap instanceof IntMap.Tip) {
            IntMap.Tip tip = (IntMap.Tip)intMap;
            return this.valueOf(tip);
        }
        if (((Object)IntMap$Nil$.MODULE$).equals(intMap)) {
            throw new IllegalStateException("Empty maps not allowed as subtrees");
        }
        throw new MatchError(intMap);
    }

    public IntMapIterator(IntMap<V> it) {
        this.push(it);
    }
}

