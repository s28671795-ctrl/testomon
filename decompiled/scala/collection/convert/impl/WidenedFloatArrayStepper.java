/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.DoubleStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y2Qa\u0002\u0005\u0001\u0019AA\u0001B\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\tG\u0001\u0011\t\u0011)A\u0005I!Aq\u0005\u0001B\u0001B\u0003%A\u0005C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0003.\u0001\u0011\u0005a\u0006C\u00033\u0001\u0011E1G\u0001\rXS\u0012,g.\u001a3GY>\fG/\u0011:sCf\u001cF/\u001a9qKJT!!\u0003\u0006\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u00171\tqaY8om\u0016\u0014HO\u0003\u0002\u000e\u001d\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003=\tQa]2bY\u0006\u001c2\u0001A\t\u0016!\u0011\u00112#F\r\u000e\u0003!I!\u0001\u0006\u0005\u0003%%sG-\u001a=fIN#X\r\u001d9fe\n\u000b7/\u001a\t\u0003-]i\u0011\u0001D\u0005\u000311\u0011Q\u0002R8vE2,7\u000b^3qa\u0016\u0014\bC\u0001\n\u0001\u0003))h\u000eZ3sYfLgnZ\u0002\u0001!\rib\u0004I\u0007\u0002\u001d%\u0011qD\u0004\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0003;\u0005J!A\t\b\u0003\u000b\u0019cw.\u0019;\u0002\u0007}K\u0007\u0007\u0005\u0002\u001eK%\u0011aE\u0004\u0002\u0004\u0013:$\u0018aA0j\u001d\u00061A(\u001b8jiz\"B!\u0007\u0016,Y!)!\u0004\u0002a\u00019!)1\u0005\u0002a\u0001I!)q\u0005\u0002a\u0001I\u0005Aa.\u001a=u'R,\u0007\u000fF\u00010!\ti\u0002'\u0003\u00022\u001d\t1Ai\\;cY\u0016\f\u0011b]3nS\u000edwN\\3\u0015\u0005e!\u0004\"B\u001b\u0007\u0001\u0004!\u0013\u0001\u00025bY\u001a\u0004")
public class WidenedFloatArrayStepper
extends IndexedStepperBase<DoubleStepper, WidenedFloatArrayStepper>
implements DoubleStepper {
    public final float[] scala$collection$convert$impl$WidenedFloatArrayStepper$$underlying;

    @Override
    public <B> Spliterator.OfDouble spliterator() {
        return DoubleStepper.spliterator$(this);
    }

    @Override
    public <B> PrimitiveIterator.OfDouble javaIterator() {
        return DoubleStepper.javaIterator$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcD$sp() {
        return DoubleStepper.spliterator$mcD$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
        return DoubleStepper.javaIterator$mcD$sp$(this);
    }

    @Override
    public int nextStep$mcI$sp() {
        return Stepper.nextStep$mcI$sp$(this);
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
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return Stepper.spliterator$mcI$sp$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcJ$sp() {
        return Stepper.spliterator$mcJ$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return Stepper.javaIterator$mcI$sp$(this);
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
    public double nextStep() {
        return this.nextStep$mcD$sp();
    }

    @Override
    public WidenedFloatArrayStepper semiclone(int half) {
        return new WidenedFloatArrayStepper(this.scala$collection$convert$impl$WidenedFloatArrayStepper$$underlying, this.i0(), half);
    }

    @Override
    public double nextStep$mcD$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return this.scala$collection$convert$impl$WidenedFloatArrayStepper$$underlying[j];
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public WidenedFloatArrayStepper(float[] underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$WidenedFloatArrayStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

