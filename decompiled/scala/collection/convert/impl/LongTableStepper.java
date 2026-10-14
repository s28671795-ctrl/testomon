/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.Function1;
import scala.collection.Iterator;
import scala.collection.LongStepper;
import scala.collection.Stepper;
import scala.collection.convert.impl.TableStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005A3QAC\u0006\u0003\u001fMA\u0001B\f\u0001\u0003\u0002\u0003\u0006Ia\f\u0005\ne\u0001\u0011\t\u0011)A\u0005gYB\u0001\u0002\u000f\u0001\u0003\u0002\u0003\u0006I!\u000f\u0005\ty\u0001\u0011\t\u0011)A\u0005{!Aa\b\u0001B\u0001B\u0003%q\u0006C\u0005@\u0001\t\u0005\t\u0015!\u00030\u0001\")!\t\u0001C\u0001\u0007\")!\n\u0001C\u0001\u0017\")A\n\u0001C\u0001\u001b\n\u0001Bj\u001c8h)\u0006\u0014G.Z*uKB\u0004XM\u001d\u0006\u0003\u00195\tA![7qY*\u0011abD\u0001\bG>tg/\u001a:u\u0015\t\u0001\u0012#\u0001\u0006d_2dWm\u0019;j_:T\u0011AE\u0001\u0006g\u000e\fG.Y\u000b\u0003)}\u00192\u0001A\u000b*!\u00191r#G\u000f*[5\t1\"\u0003\u0002\u0019\u0017\t\u0001B+\u00192mKN#X\r\u001d9fe\n\u000b7/\u001a\t\u00035mi\u0011!E\u0005\u00039E\u0011A\u0001T8oOB\u0011ad\b\u0007\u0001\t\u0015\u0001\u0003A1\u0001#\u0005\u0005I5\u0001A\t\u0003G\u0019\u0002\"A\u0007\u0013\n\u0005\u0015\n\"\u0001\u0002(vY2\u0004\"AG\u0014\n\u0005!\n\"AB!osJ+g\r\u0005\u0002+W5\tq\"\u0003\u0002-\u001f\tYAj\u001c8h'R,\u0007\u000f]3s!\r1\u0002!H\u0001\u000b?6\f\u0007\u0010T3oORD\u0007C\u0001\u000e1\u0013\t\t\u0014CA\u0002J]R\faa\u0018;bE2,\u0007c\u0001\u000e5;%\u0011Q'\u0005\u0002\u0006\u0003J\u0014\u0018-_\u0005\u0003o]\tQ\u0001^1cY\u0016\fq!\u001b;fe\u0006$X\r\u0005\u0003\u001buui\u0012BA\u001e\u0012\u0005%1UO\\2uS>t\u0017'A\u0004fqR\u0014\u0018m\u0019;\u0011\tiQT$G\u0001\u0004?&\u0004\u0014aA0j\u001d&\u0011\u0011iF\u0001\u0003S:\u000ba\u0001P5oSRtDcB\u0017E\u000b\u001a;\u0005*\u0013\u0005\u0006]\u001d\u0001\ra\f\u0005\u0006e\u001d\u0001\ra\r\u0005\u0006q\u001d\u0001\r!\u000f\u0005\u0006y\u001d\u0001\r!\u0010\u0005\u0006}\u001d\u0001\ra\f\u0005\u0006\u007f\u001d\u0001\raL\u0001\t]\u0016DHo\u0015;faR\t\u0011$A\u0005tK6L7\r\\8oKR\u0011QF\u0014\u0005\u0006\u001f&\u0001\raL\u0001\u0005Q\u0006dg\r")
public final class LongTableStepper<I>
extends TableStepperBase<Object, I, LongStepper, LongTableStepper<I>>
implements LongStepper {
    public final Function1<I, I> scala$collection$convert$impl$LongTableStepper$$iterate;
    public final Function1<I, Object> scala$collection$convert$impl$LongTableStepper$$extract;

    @Override
    public <B> Spliterator.OfLong spliterator() {
        return LongStepper.spliterator$(this);
    }

    @Override
    public <B> PrimitiveIterator.OfLong javaIterator() {
        return LongStepper.javaIterator$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcJ$sp() {
        return LongStepper.spliterator$mcJ$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
        return LongStepper.javaIterator$mcJ$sp$(this);
    }

    @Override
    public double nextStep$mcD$sp() {
        return Stepper.nextStep$mcD$sp$(this);
    }

    @Override
    public int nextStep$mcI$sp() {
        return Stepper.nextStep$mcI$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcD$sp() {
        return Stepper.trySplit$mcD$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcI$sp() {
        return Stepper.trySplit$mcI$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcJ$sp() {
        return Stepper.trySplit$mcJ$sp$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcD$sp() {
        return Stepper.spliterator$mcD$sp$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return Stepper.spliterator$mcI$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
        return Stepper.javaIterator$mcD$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return Stepper.javaIterator$mcI$sp$(this);
    }

    @Override
    public Iterator<Object> iterator() {
        return Stepper.iterator$(this);
    }

    @Override
    public long nextStep() {
        return this.nextStep$mcJ$sp();
    }

    @Override
    public LongTableStepper<I> semiclone(int half) {
        return new LongTableStepper(this.maxLength(), this.table(), this.scala$collection$convert$impl$LongTableStepper$$iterate, this.scala$collection$convert$impl$LongTableStepper$$extract, this.i0(), half);
    }

    @Override
    public long nextStep$mcJ$sp() {
        if (this.hasStep()) {
            long ans = BoxesRunTime.unboxToLong(this.scala$collection$convert$impl$LongTableStepper$$extract.apply(this.myCurrent()));
            this.myCurrent_$eq(this.scala$collection$convert$impl$LongTableStepper$$iterate.apply(this.myCurrent()));
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public LongTableStepper(int _maxLength, I[] _table, Function1<I, I> iterate, Function1<I, Object> extract, int _i0, int _iN) {
        this.scala$collection$convert$impl$LongTableStepper$$iterate = iterate;
        this.scala$collection$convert$impl$LongTableStepper$$extract = extract;
        super(_maxLength, _table, _i0, _iN);
    }
}

