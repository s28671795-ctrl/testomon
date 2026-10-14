/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.Spliterator;
import scala.collection.AnyStepper;
import scala.collection.IndexedSeqOps;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I3Qa\u0002\u0005\u0001\u0019AA\u0001\u0002\u000b\u0001\u0003\u0002\u0003\u0006I!\u000b\u0005\t}\u0001\u0011\t\u0011)A\u0005\u007f!A!\t\u0001B\u0001B\u0003%q\bC\u0003D\u0001\u0011\u0005A\tC\u0003M\u0001\u0011\u0005Q\nC\u0003O\u0001\u0011EqJ\u0001\u000bB]fLe\u000eZ3yK\u0012\u001cV-]*uKB\u0004XM\u001d\u0006\u0003\u0013)\tA![7qY*\u00111\u0002D\u0001\bG>tg/\u001a:u\u0015\tia\"\u0001\u0006d_2dWm\u0019;j_:T\u0011aD\u0001\u0006g\u000e\fG.Y\u000b\u0003#q\u00192\u0001\u0001\n\u0017!\u0011\u0019BCF\u0014\u000e\u0003!I!!\u0006\u0005\u0003%%sG-\u001a=fIN#X\r\u001d9fe\n\u000b7/\u001a\t\u0004/aQR\"\u0001\u0007\n\u0005ea!AC!osN#X\r\u001d9feB\u00111\u0004\b\u0007\u0001\t\u0015i\u0002A1\u0001 \u0005\u0005\t5\u0001A\t\u0003A\u0011\u0002\"!\t\u0012\u000e\u00039I!a\t\b\u0003\u000f9{G\u000f[5oOB\u0011\u0011%J\u0005\u0003M9\u00111!\u00118z!\r\u0019\u0002AG\u0001\u000bk:$WM\u001d7zS:<\u0007G\u0001\u0016=!\u001592FG\u0017<\u0013\taCBA\u0007J]\u0012,\u00070\u001a3TKF|\u0005o\u001d\t\u0003]ar!a\f\u001c\u000f\u0005A*dBA\u00195\u001b\u0005\u0011$BA\u001a\u001f\u0003\u0019a$o\\8u}%\tq\"\u0003\u0002\u000e\u001d%\u0011q\u0007D\u0001\ba\u0006\u001c7.Y4f\u0013\tI$HA\u0005B]f\u001cuN\\:ue*\u0011q\u0007\u0004\t\u00037q\"\u0011\"P\u0001\u0002\u0002\u0003\u0005)\u0011A\u0010\u0003\u0007}#\u0013'A\u0002`SB\u0002\"!\t!\n\u0005\u0005s!aA%oi\u0006\u0019q,\u001b(\u0002\rqJg.\u001b;?)\u00119SIS&\t\u000b!\"\u0001\u0019\u0001$1\u0005\u001dK\u0005#B\f,55B\u0005CA\u000eJ\t%iT)!A\u0001\u0002\u000b\u0005q\u0004C\u0003?\t\u0001\u0007q\bC\u0003C\t\u0001\u0007q(\u0001\u0005oKb$8\u000b^3q)\u0005Q\u0012!C:f[&\u001cGn\u001c8f)\t9\u0003\u000bC\u0003R\r\u0001\u0007q(\u0001\u0003iC24\u0007")
public class AnyIndexedSeqStepper<A>
extends IndexedStepperBase<AnyStepper<A>, AnyIndexedSeqStepper<A>>
implements AnyStepper<A> {
    private final IndexedSeqOps<A, Object, ?> underlying;

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
    public Iterator<A> iterator() {
        return Stepper.iterator$(this);
    }

    @Override
    public A nextStep() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return this.underlying.apply(j);
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    @Override
    public AnyIndexedSeqStepper<A> semiclone(int half) {
        return new AnyIndexedSeqStepper<A>(this.underlying, this.i0(), half);
    }

    public AnyIndexedSeqStepper(IndexedSeqOps<A, Object, ?> underlying, int _i0, int _iN) {
        this.underlying = underlying;
        super(_i0, _iN);
    }
}

