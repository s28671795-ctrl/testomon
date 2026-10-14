/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.IndexedSeqOps;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005=3Qa\u0002\u0005\u0001\u0019AA\u0001\"\u0011\u0001\u0003\u0002\u0003\u0006Ia\u0007\u0005\t\u0005\u0002\u0011\t\u0011)A\u0005S!A1\t\u0001B\u0001B\u0003%\u0011\u0006C\u0003E\u0001\u0011\u0005Q\tC\u0003J\u0001\u0011\u0005!\nC\u0003L\u0001\u0011EAJ\u0001\u000bJ]RLe\u000eZ3yK\u0012\u001cV-]*uKB\u0004XM\u001d\u0006\u0003\u0013)\tA![7qY*\u00111\u0002D\u0001\bG>tg/\u001a:u\u0015\tia\"\u0001\u0006d_2dWm\u0019;j_:T\u0011aD\u0001\u0006g\u000e\fG.Y\u000b\u0003#u\u00192\u0001\u0001\n\u0017!\u0011\u0019BC\u0006\u000e\u000e\u0003!I!!\u0006\u0005\u0003%%sG-\u001a=fIN#X\r\u001d9fe\n\u000b7/\u001a\t\u0003/ai\u0011\u0001D\u0005\u000331\u0011!\"\u00138u'R,\u0007\u000f]3s!\r\u0019\u0002a\u0007\t\u00039ua\u0001\u0001B\u0003\u001f\u0001\t\u0007\u0001E\u0001\u0002D\u0007\u000e\u0001\u0011CA\u0011&!\t\u00113%D\u0001\u000f\u0013\t!cBA\u0004O_RD\u0017N\\41\u0005\u0019Z\u0004#B\f(S1R\u0014B\u0001\u0015\r\u00055Ie\u000eZ3yK\u0012\u001cV-](qgB\u0011!EK\u0005\u0003W9\u00111!\u00138u!\tisG\u0004\u0002/k9\u0011q\u0006\u000e\b\u0003aMj\u0011!\r\u0006\u0003e}\ta\u0001\u0010:p_Rt\u0014\"A\b\n\u00055q\u0011B\u0001\u001c\r\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001O\u001d\u0003\u0013\u0005s\u0017pQ8ogR\u0014(B\u0001\u001c\r!\ta2\bB\u0005=;\u0005\u0005\t\u0011!B\u0001{\t\u0019q\fJ\u001a\u0012\u0005\u0005r\u0004C\u0001\u0012@\u0013\t\u0001eBA\u0002B]f\f!\"\u001e8eKJd\u00170\u001b8h\u0003\ry\u0016\u000eM\u0001\u0004?&t\u0015A\u0002\u001fj]&$h\b\u0006\u0003\u001b\r\u001eC\u0005\"B!\u0005\u0001\u0004Y\u0002\"\u0002\"\u0005\u0001\u0004I\u0003\"B\"\u0005\u0001\u0004I\u0013\u0001\u00038fqR\u001cF/\u001a9\u0015\u0003%\n\u0011b]3nS\u000edwN\\3\u0015\u0005ii\u0005\"\u0002(\u0007\u0001\u0004I\u0013\u0001\u00025bY\u001a\u0004")
public class IntIndexedSeqStepper<CC extends IndexedSeqOps<Object, Object, ?>>
extends IndexedStepperBase<IntStepper, IntIndexedSeqStepper<CC>>
implements IntStepper {
    public final CC scala$collection$convert$impl$IntIndexedSeqStepper$$underlying;

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
    public IntIndexedSeqStepper<CC> semiclone(int half) {
        return new IntIndexedSeqStepper<CC>(this.scala$collection$convert$impl$IntIndexedSeqStepper$$underlying, this.i0(), half);
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return BoxesRunTime.unboxToInt(this.scala$collection$convert$impl$IntIndexedSeqStepper$$underlying.apply(j));
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public IntIndexedSeqStepper(CC underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$IntIndexedSeqStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

