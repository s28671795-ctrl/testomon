/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.Iterator;
import scala.collection.LongStepper;
import scala.collection.Stepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.collection.immutable.NumericRange;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005Y2Qa\u0002\u0005\u0001\u0019AA\u0001B\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\tM\u0001\u0011\t\u0011)A\u0005O!A!\u0006\u0001B\u0001B\u0003%q\u0005C\u0003,\u0001\u0011\u0005A\u0006C\u00031\u0001\u0011\u0005\u0011\u0007C\u00033\u0001\u0011\u00051GA\fM_:<g*^7fe&\u001c'+\u00198hKN#X\r\u001d9fe*\u0011\u0011BC\u0001\u0005S6\u0004HN\u0003\u0002\f\u0019\u000591m\u001c8wKJ$(BA\u0007\u000f\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u001f\u0005)1oY1mCN\u0019\u0001!E\u000b\u0011\tI\u0019R#G\u0007\u0002\u0011%\u0011A\u0003\u0003\u0002\u0013\u0013:$W\r_3e'R,\u0007\u000f]3s\u0005\u0006\u001cX\r\u0005\u0002\u0017/5\tA\"\u0003\u0002\u0019\u0019\tYAj\u001c8h'R,\u0007\u000f]3s!\t\u0011\u0002!\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\u001c\u0001\u0001E\u0002\u001eA\tj\u0011A\b\u0006\u0003?1\t\u0011\"[7nkR\f'\r\\3\n\u0005\u0005r\"\u0001\u0004(v[\u0016\u0014\u0018n\u0019*b]\u001e,\u0007CA\u0012%\u001b\u0005q\u0011BA\u0013\u000f\u0005\u0011auN\\4\u0002\u0007}K\u0007\u0007\u0005\u0002$Q%\u0011\u0011F\u0004\u0002\u0004\u0013:$\u0018aA0j\u001d\u00061A(\u001b8jiz\"B!G\u0017/_!)!\u0004\u0002a\u00019!)a\u0005\u0002a\u0001O!)!\u0006\u0002a\u0001O\u0005Aa.\u001a=u'R,\u0007\u000fF\u0001#\u0003%\u0019X-\\5dY>tW\r\u0006\u0002\u001ai!)QG\u0002a\u0001O\u0005!\u0001.\u00197g\u0001")
public class LongNumericRangeStepper
extends IndexedStepperBase<LongStepper, LongNumericRangeStepper>
implements LongStepper {
    public final NumericRange<Object> scala$collection$convert$impl$LongNumericRangeStepper$$underlying;

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
    public LongNumericRangeStepper semiclone(int half) {
        return new LongNumericRangeStepper(this.scala$collection$convert$impl$LongNumericRangeStepper$$underlying, this.i0(), half);
    }

    @Override
    public long nextStep$mcJ$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return BoxesRunTime.unboxToLong(this.scala$collection$convert$impl$LongNumericRangeStepper$$underlying.apply(j));
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public LongNumericRangeStepper(NumericRange<Object> underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$LongNumericRangeStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

