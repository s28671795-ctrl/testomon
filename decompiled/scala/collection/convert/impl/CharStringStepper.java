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

@ScalaSignature(bytes="\u0006\u0005a2Qa\u0002\u0005\u0003\u0019AA\u0001B\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\tO\u0001\u0011\t\u0011)A\u0005Q!AA\u0006\u0001B\u0001B\u0003%\u0001\u0006C\u0003.\u0001\u0011\u0005a\u0006C\u00033\u0001\u0011\u00051\u0007C\u00035\u0001\u0011\u0005QGA\tDQ\u0006\u00148\u000b\u001e:j]\u001e\u001cF/\u001a9qKJT!!\u0003\u0006\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u00171\tqaY8om\u0016\u0014HO\u0003\u0002\u000e\u001d\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003=\tQa]2bY\u0006\u001c2\u0001A\t\u0016!\u0011\u00112#F\r\u000e\u0003!I!\u0001\u0006\u0005\u0003%%sG-\u001a=fIN#X\r\u001d9fe\n\u000b7/\u001a\t\u0003-]i\u0011\u0001D\u0005\u000311\u0011!\"\u00138u'R,\u0007\u000f]3s!\t\u0011\u0002!\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\u001c\u0001\u0001\u0005\u0002\u001eI9\u0011aD\t\t\u0003?9i\u0011\u0001\t\u0006\u0003Cm\ta\u0001\u0010:p_Rt\u0014BA\u0012\u000f\u0003\u0019\u0001&/\u001a3fM&\u0011QE\n\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\rr\u0011aA0jaA\u0011\u0011FK\u0007\u0002\u001d%\u00111F\u0004\u0002\u0004\u0013:$\u0018aA0j\u001d\u00061A(\u001b8jiz\"B!G\u00181c!)!\u0004\u0002a\u00019!)q\u0005\u0002a\u0001Q!)A\u0006\u0002a\u0001Q\u0005Aa.\u001a=u'R,\u0007\u000fF\u0001)\u0003%\u0019X-\\5dY>tW\r\u0006\u0002\u001am!)qG\u0002a\u0001Q\u0005!\u0001.\u00197g\u0001")
public final class CharStringStepper
extends IndexedStepperBase<IntStepper, CharStringStepper>
implements IntStepper {
    public final String scala$collection$convert$impl$CharStringStepper$$underlying;

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
    public CharStringStepper semiclone(int half) {
        return new CharStringStepper(this.scala$collection$convert$impl$CharStringStepper$$underlying, this.i0(), half);
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return this.scala$collection$convert$impl$CharStringStepper$$underlying.charAt(j);
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public CharStringStepper(String underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$CharStringStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

