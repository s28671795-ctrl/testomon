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
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A2Qa\u0002\u0005\u0001\u0019AA\u0001B\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\tG\u0001\u0011\t\u0011)A\u0005A!AA\u0005\u0001B\u0001B\u0003%\u0001\u0005C\u0003&\u0001\u0011\u0005a\u0005C\u0003+\u0001\u0011\u00051\u0006C\u0003-\u0001\u0011EQFA\bJ]R\f%O]1z'R,\u0007\u000f]3s\u0015\tI!\"\u0001\u0003j[Bd'BA\u0006\r\u0003\u001d\u0019wN\u001c<feRT!!\u0004\b\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u0010\u0003\u0015\u00198-\u00197b'\r\u0001\u0011#\u0006\t\u0005%M)\u0012$D\u0001\t\u0013\t!\u0002B\u0001\nJ]\u0012,\u00070\u001a3Ti\u0016\u0004\b/\u001a:CCN,\u0007C\u0001\f\u0018\u001b\u0005a\u0011B\u0001\r\r\u0005)Ie\u000e^*uKB\u0004XM\u001d\t\u0003%\u0001\t!\"\u001e8eKJd\u00170\u001b8h\u0007\u0001\u00012!\b\u0010!\u001b\u0005q\u0011BA\u0010\u000f\u0005\u0015\t%O]1z!\ti\u0012%\u0003\u0002#\u001d\t\u0019\u0011J\u001c;\u0002\u0007}K\u0007'A\u0002`S:\u000ba\u0001P5oSRtD\u0003B\r(Q%BQA\u0007\u0003A\u0002qAQa\t\u0003A\u0002\u0001BQ\u0001\n\u0003A\u0002\u0001\n\u0001B\\3yiN#X\r\u001d\u000b\u0002A\u0005I1/Z7jG2|g.\u001a\u000b\u000339BQa\f\u0004A\u0002\u0001\nA\u0001[1mM\u0002")
public class IntArrayStepper
extends IndexedStepperBase<IntStepper, IntArrayStepper>
implements IntStepper {
    public final int[] scala$collection$convert$impl$IntArrayStepper$$underlying;

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
    public IntArrayStepper semiclone(int half) {
        return new IntArrayStepper(this.scala$collection$convert$impl$IntArrayStepper$$underlying, this.i0(), half);
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return this.scala$collection$convert$impl$IntArrayStepper$$underlying[j];
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public IntArrayStepper(int[] underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$IntArrayStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

