/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.collection.immutable.NumericRange;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005M2Qa\u0002\u0005\u0001\u0019AA\u0001B\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\tM\u0001\u0011\t\u0011)A\u0005E!Aq\u0005\u0001B\u0001B\u0003%!\u0005C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0003.\u0001\u0011\u0005a\u0006C\u00030\u0001\u0011\u0005\u0001G\u0001\fJ]RtU/\\3sS\u000e\u0014\u0016M\\4f'R,\u0007\u000f]3s\u0015\tI!\"\u0001\u0003j[Bd'BA\u0006\r\u0003\u001d\u0019wN\u001c<feRT!!\u0004\b\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u0010\u0003\u0015\u00198-\u00197b'\r\u0001\u0011#\u0006\t\u0005%M)\u0012$D\u0001\t\u0013\t!\u0002B\u0001\nJ]\u0012,\u00070\u001a3Ti\u0016\u0004\b/\u001a:CCN,\u0007C\u0001\f\u0018\u001b\u0005a\u0011B\u0001\r\r\u0005)Ie\u000e^*uKB\u0004XM\u001d\t\u0003%\u0001\t!\"\u001e8eKJd\u00170\u001b8h\u0007\u0001\u00012!\b\u0011#\u001b\u0005q\"BA\u0010\r\u0003%IW.\\;uC\ndW-\u0003\u0002\"=\taa*^7fe&\u001c'+\u00198hKB\u00111\u0005J\u0007\u0002\u001d%\u0011QE\u0004\u0002\u0004\u0013:$\u0018aA0ja\u0005\u0019q,\u001b(\u0002\rqJg.\u001b;?)\u0011I\"f\u000b\u0017\t\u000bi!\u0001\u0019\u0001\u000f\t\u000b\u0019\"\u0001\u0019\u0001\u0012\t\u000b\u001d\"\u0001\u0019\u0001\u0012\u0002\u00119,\u0007\u0010^*uKB$\u0012AI\u0001\ng\u0016l\u0017n\u00197p]\u0016$\"!G\u0019\t\u000bI2\u0001\u0019\u0001\u0012\u0002\t!\fGN\u001a")
public class IntNumericRangeStepper
extends IndexedStepperBase<IntStepper, IntNumericRangeStepper>
implements IntStepper {
    public final NumericRange<Object> scala$collection$convert$impl$IntNumericRangeStepper$$underlying;

    @Override
    public <B> Spliterator.OfInt spliterator() {
        return IntStepper.spliterator$(this);
    }

    @Override
    public <B> PrimitiveIterator.OfInt javaIterator() {
        return IntStepper.javaIterator$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return IntStepper.spliterator$mcI$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return IntStepper.javaIterator$mcI$sp$(this);
    }

    @Override
    public double nextStep$mcD$sp() {
        return Stepper.nextStep$mcD$sp$(this);
    }

    @Override
    public long nextStep$mcJ$sp() {
        return Stepper.nextStep$mcJ$sp$(this);
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
    public <B> Spliterator<?> spliterator$mcJ$sp() {
        return Stepper.spliterator$mcJ$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
        return Stepper.javaIterator$mcD$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
        return Stepper.javaIterator$mcJ$sp$(this);
    }

    @Override
    public Iterator<Object> iterator() {
        return Stepper.iterator$(this);
    }

    @Override
    public int nextStep() {
        return this.nextStep$mcI$sp();
    }

    @Override
    public IntNumericRangeStepper semiclone(int half) {
        return new IntNumericRangeStepper(this.scala$collection$convert$impl$IntNumericRangeStepper$$underlying, this.i0(), half);
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return BoxesRunTime.unboxToInt(this.scala$collection$convert$impl$IntNumericRangeStepper$$underlying.apply(j));
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public IntNumericRangeStepper(NumericRange<Object> underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$IntNumericRangeStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

