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

@ScalaSignature(bytes="\u0006\u0005y2Qa\u0003\u0007\u0003!QA\u0001B\b\u0001\u0003\u0002\u0004%\t\u0002\t\u0005\tK\u0001\u0011\t\u0019!C\tM!AA\u0006\u0001B\u0001B\u0003&\u0011\u0005\u0003\u0005.\u0001\t\u0005\t\u0015!\u0003\"\u0011!q\u0003A!A!\u0002\u0013\t\u0003\u0002C\u0018\u0001\u0005\u0003\u0005\u000b\u0011B\u0011\t\u000bA\u0002A\u0011A\u0019\t\u000bY\u0002A\u0011A\u001c\t\u000ba\u0002A\u0011C\u001d\t\u000bq\u0002A\u0011I\u001f\u0003\u0019I\u000bgnZ3Ti\u0016\u0004\b/\u001a:\u000b\u00055q\u0011\u0001B5na2T!a\u0004\t\u0002\u000f\r|gN^3si*\u0011\u0011CE\u0001\u000bG>dG.Z2uS>t'\"A\n\u0002\u000bM\u001c\u0017\r\\1\u0014\u0007\u0001)\u0012\u0004\u0005\u0003\u0017/eiR\"\u0001\u0007\n\u0005aa!AE%oI\u0016DX\rZ*uKB\u0004XM\u001d\"bg\u0016\u0004\"AG\u000e\u000e\u0003AI!\u0001\b\t\u0003\u0015%sGo\u0015;faB,'\u000f\u0005\u0002\u0017\u0001\u00051Q.\u001f(fqR\u001c\u0001!F\u0001\"!\t\u00113%D\u0001\u0013\u0013\t!#CA\u0002J]R\f!\"\\=OKb$x\fJ3r)\t9#\u0006\u0005\u0002#Q%\u0011\u0011F\u0005\u0002\u0005+:LG\u000fC\u0004,\u0005\u0005\u0005\t\u0019A\u0011\u0002\u0007a$\u0013'A\u0004ns:+\u0007\u0010\u001e\u0011\u0002\r5L8\u000b^3q\u0003\ry\u0016\u000eM\u0001\u0004?&t\u0015A\u0002\u001fj]&$h\bF\u0003\u001eeM\"T\u0007C\u0003\u001f\u000f\u0001\u0007\u0011\u0005C\u0003.\u000f\u0001\u0007\u0011\u0005C\u0003/\u000f\u0001\u0007\u0011\u0005C\u00030\u000f\u0001\u0007\u0011%\u0001\u0005oKb$8\u000b^3q)\u0005\t\u0013!C:f[&\u001cGn\u001c8f)\ti\"\bC\u0003<\u0013\u0001\u0007\u0011%\u0001\u0003iC24\u0017\u0001\u0003;ssN\u0003H.\u001b;\u0015\u0003e\u0001")
public final class RangeStepper
extends IndexedStepperBase<IntStepper, RangeStepper>
implements IntStepper {
    private int myNext;
    public final int scala$collection$convert$impl$RangeStepper$$myStep;

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

    public int myNext() {
        return this.myNext;
    }

    public void myNext_$eq(int x$1) {
        this.myNext = x$1;
    }

    @Override
    public int nextStep() {
        return this.nextStep$mcI$sp();
    }

    @Override
    public RangeStepper semiclone(int half) {
        return new RangeStepper(this.myNext(), this.scala$collection$convert$impl$RangeStepper$$myStep, this.i0(), half);
    }

    @Override
    public IntStepper trySplit() {
        int old_i0 = this.i0();
        IntStepper ans = (IntStepper)super.trySplit();
        this.myNext_$eq(this.myNext() + (this.i0() - old_i0) * this.scala$collection$convert$impl$RangeStepper$$myStep);
        return ans;
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.hasStep()) {
            int ans = this.myNext();
            this.myNext_$eq(this.myNext() + this.scala$collection$convert$impl$RangeStepper$$myStep);
            this.i0_$eq(this.i0() + 1);
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public RangeStepper(int myNext, int myStep, int _i0, int _iN) {
        this.myNext = myNext;
        this.scala$collection$convert$impl$RangeStepper$$myStep = myStep;
        super(_i0, _iN);
    }
}

