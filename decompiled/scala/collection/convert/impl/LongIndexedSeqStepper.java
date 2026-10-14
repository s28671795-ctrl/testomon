/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.IndexedSeqOps;
import scala.collection.Iterator;
import scala.collection.LongStepper;
import scala.collection.Stepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005I3Qa\u0002\u0005\u0001\u0019AA\u0001\"\u0011\u0001\u0003\u0002\u0003\u0006Ia\u0007\u0005\t\u0005\u0002\u0011\t\u0011)A\u0005\u0007\"Aa\t\u0001B\u0001B\u0003%1\tC\u0003H\u0001\u0011\u0005\u0001\nC\u0003M\u0001\u0011\u0005Q\nC\u0003O\u0001\u0011EqJA\u000bM_:<\u0017J\u001c3fq\u0016$7+Z9Ti\u0016\u0004\b/\u001a:\u000b\u0005%Q\u0011\u0001B5na2T!a\u0003\u0007\u0002\u000f\r|gN^3si*\u0011QBD\u0001\u000bG>dG.Z2uS>t'\"A\b\u0002\u000bM\u001c\u0017\r\\1\u0016\u0005Ei2c\u0001\u0001\u0013-A!1\u0003\u0006\f\u001b\u001b\u0005A\u0011BA\u000b\t\u0005IIe\u000eZ3yK\u0012\u001cF/\u001a9qKJ\u0014\u0015m]3\u0011\u0005]AR\"\u0001\u0007\n\u0005ea!a\u0003'p]\u001e\u001cF/\u001a9qKJ\u00042a\u0005\u0001\u001c!\taR\u0004\u0004\u0001\u0005\u000by\u0001!\u0019\u0001\u0011\u0003\u0005\r\u001b5\u0001A\t\u0003C\u0015\u0002\"AI\u0012\u000e\u00039I!\u0001\n\b\u0003\u000f9{G\u000f[5oOB\u0012ae\u000f\t\u0006/\u001dJCFO\u0005\u0003Q1\u0011Q\"\u00138eKb,GmU3r\u001fB\u001c\bC\u0001\u0012+\u0013\tYcB\u0001\u0003M_:<\u0007CA\u00178\u001d\tqSG\u0004\u00020i9\u0011\u0001gM\u0007\u0002c)\u0011!gH\u0001\u0007yI|w\u000e\u001e \n\u0003=I!!\u0004\b\n\u0005Yb\u0011a\u00029bG.\fw-Z\u0005\u0003qe\u0012\u0011\"\u00118z\u0007>t7\u000f\u001e:\u000b\u0005Yb\u0001C\u0001\u000f<\t%aT$!A\u0001\u0002\u000b\u0005QHA\u0002`IQ\n\"!\t \u0011\u0005\tz\u0014B\u0001!\u000f\u0005\r\te._\u0001\u000bk:$WM\u001d7zS:<\u0017aA0jaA\u0011!\u0005R\u0005\u0003\u000b:\u00111!\u00138u\u0003\ry\u0016NT\u0001\u0007y%t\u0017\u000e\u001e \u0015\tiI%j\u0013\u0005\u0006\u0003\u0012\u0001\ra\u0007\u0005\u0006\u0005\u0012\u0001\ra\u0011\u0005\u0006\r\u0012\u0001\raQ\u0001\t]\u0016DHo\u0015;faR\t\u0011&A\u0005tK6L7\r\\8oKR\u0011!\u0004\u0015\u0005\u0006#\u001a\u0001\raQ\u0001\u0005Q\u0006dg\r")
public class LongIndexedSeqStepper<CC extends IndexedSeqOps<Object, Object, ?>>
extends IndexedStepperBase<LongStepper, LongIndexedSeqStepper<CC>>
implements LongStepper {
    public final CC scala$collection$convert$impl$LongIndexedSeqStepper$$underlying;

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
    public LongIndexedSeqStepper<CC> semiclone(int half) {
        return new LongIndexedSeqStepper<CC>(this.scala$collection$convert$impl$LongIndexedSeqStepper$$underlying, this.i0(), half);
    }

    @Override
    public long nextStep$mcJ$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return BoxesRunTime.unboxToLong(this.scala$collection$convert$impl$LongIndexedSeqStepper$$underlying.apply(j));
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public LongIndexedSeqStepper(CC underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$LongIndexedSeqStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

