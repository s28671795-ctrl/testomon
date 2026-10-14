/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.Spliterator;
import scala.collection.AnyStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2Qa\u0002\u0005\u0001\u0019AA\u0001B\b\u0001\u0003\u0002\u0003\u0006I\u0001\t\u0005\tG\u0001\u0011\t\u0011)A\u0005I!Aq\u0005\u0001B\u0001B\u0003%A\u0005C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0003.\u0001\u0011\u0005a\u0006C\u00030\u0001\u0011E\u0001G\u0001\rC_b,GMQ8pY\u0016\fg.\u0011:sCf\u001cF/\u001a9qKJT!!\u0003\u0006\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u00171\tqaY8om\u0016\u0014HO\u0003\u0002\u000e\u001d\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003=\tQa]2bY\u0006\u001c2\u0001A\t\u0016!\u0011\u00112#F\u000f\u000e\u0003!I!\u0001\u0006\u0005\u0003%%sG-\u001a=fIN#X\r\u001d9fe\n\u000b7/\u001a\t\u0004-]IR\"\u0001\u0007\n\u0005aa!AC!osN#X\r\u001d9feB\u0011!dG\u0007\u0002\u001d%\u0011AD\u0004\u0002\b\u0005>|G.Z1o!\t\u0011\u0002!\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\u001c\u0001\u0001E\u0002\u001bCeI!A\t\b\u0003\u000b\u0005\u0013(/Y=\u0002\u0007}K\u0007\u0007\u0005\u0002\u001bK%\u0011aE\u0004\u0002\u0004\u0013:$\u0018aA0j\u001d\u00061A(\u001b8jiz\"B!\b\u0016,Y!)a\u0004\u0002a\u0001A!)1\u0005\u0002a\u0001I!)q\u0005\u0002a\u0001I\u0005Aa.\u001a=u'R,\u0007\u000fF\u0001\u001a\u0003%\u0019X-\\5dY>tW\r\u0006\u0002\u001ec!)!G\u0002a\u0001I\u0005!\u0001.\u00197g\u0001")
public class BoxedBooleanArrayStepper
extends IndexedStepperBase<AnyStepper<Object>, BoxedBooleanArrayStepper>
implements AnyStepper<Object> {
    private final boolean[] underlying;

    @Override
    public <B> Spliterator<B> spliterator() {
        return AnyStepper.spliterator$(this);
    }

    @Override
    public <B> java.util.Iterator<B> javaIterator() {
        return AnyStepper.javaIterator$(this);
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
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return Stepper.spliterator$mcI$sp$(this);
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
    public boolean nextStep() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return this.underlying[j];
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    @Override
    public BoxedBooleanArrayStepper semiclone(int half) {
        return new BoxedBooleanArrayStepper(this.underlying, this.i0(), half);
    }

    public BoxedBooleanArrayStepper(boolean[] underlying, int _i0, int _iN) {
        this.underlying = underlying;
        super(_i0, _iN);
    }
}

