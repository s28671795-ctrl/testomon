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
import scala.collection.immutable.NumericRange;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}2Qa\u0002\u0005\u0001\u0019AA\u0001\u0002\u000b\u0001\u0003\u0002\u0003\u0006I!\u000b\u0005\t_\u0001\u0011\t\u0011)A\u0005a!A1\u0007\u0001B\u0001B\u0003%\u0001\u0007C\u00035\u0001\u0011\u0005Q\u0007C\u0003:\u0001\u0011\u0005!\bC\u0003<\u0001\u0011\u0005AH\u0001\fB]ftU/\\3sS\u000e\u0014\u0016M\\4f'R,\u0007\u000f]3s\u0015\tI!\"\u0001\u0003j[Bd'BA\u0006\r\u0003\u001d\u0019wN\u001c<feRT!!\u0004\b\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u0010\u0003\u0015\u00198-\u00197b+\t\tBdE\u0002\u0001%Y\u0001Ba\u0005\u000b\u0017O5\t\u0001\"\u0003\u0002\u0016\u0011\t\u0011\u0012J\u001c3fq\u0016$7\u000b^3qa\u0016\u0014()Y:f!\r9\u0002DG\u0007\u0002\u0019%\u0011\u0011\u0004\u0004\u0002\u000b\u0003:L8\u000b^3qa\u0016\u0014\bCA\u000e\u001d\u0019\u0001!Q!\b\u0001C\u0002}\u0011\u0011!Q\u0002\u0001#\t\u0001C\u0005\u0005\u0002\"E5\ta\"\u0003\u0002$\u001d\t9aj\u001c;iS:<\u0007CA\u0011&\u0013\t1cBA\u0002B]f\u00042a\u0005\u0001\u001b\u0003))h\u000eZ3sYfLgn\u001a\t\u0004U5RR\"A\u0016\u000b\u00051b\u0011!C5n[V$\u0018M\u00197f\u0013\tq3F\u0001\u0007Ok6,'/[2SC:<W-A\u0002`SB\u0002\"!I\u0019\n\u0005Ir!aA%oi\u0006\u0019q,\u001b(\u0002\rqJg.\u001b;?)\u00119cg\u000e\u001d\t\u000b!\"\u0001\u0019A\u0015\t\u000b=\"\u0001\u0019\u0001\u0019\t\u000bM\"\u0001\u0019\u0001\u0019\u0002\u00119,\u0007\u0010^*uKB$\u0012AG\u0001\ng\u0016l\u0017n\u00197p]\u0016$\"aJ\u001f\t\u000by2\u0001\u0019\u0001\u0019\u0002\t!\fGN\u001a")
public class AnyNumericRangeStepper<A>
extends IndexedStepperBase<AnyStepper<A>, AnyNumericRangeStepper<A>>
implements AnyStepper<A> {
    private final NumericRange<A> underlying;

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
    public AnyNumericRangeStepper<A> semiclone(int half) {
        return new AnyNumericRangeStepper<A>(this.underlying, this.i0(), half);
    }

    public AnyNumericRangeStepper(NumericRange<A> underlying, int _i0, int _iN) {
        this.underlying = underlying;
        super(_i0, _iN);
    }
}

