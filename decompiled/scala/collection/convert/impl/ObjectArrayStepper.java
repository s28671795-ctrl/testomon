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

@ScalaSignature(bytes="\u0006\u0005\u00053Qa\u0002\u0005\u0001\u0019AA\u0001\"\f\u0001\u0003\u0002\u0003\u0006IA\f\u0005\tc\u0001\u0011\t\u0011)A\u0005e!AQ\u0007\u0001B\u0001B\u0003%!\u0007C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005A\bC\u0003>\u0001\u0011EaH\u0001\nPE*,7\r^!se\u0006L8\u000b^3qa\u0016\u0014(BA\u0005\u000b\u0003\u0011IW\u000e\u001d7\u000b\u0005-a\u0011aB2p]Z,'\u000f\u001e\u0006\u0003\u001b9\t!bY8mY\u0016\u001cG/[8o\u0015\u0005y\u0011!B:dC2\fWCA\t\u001d'\r\u0001!C\u0006\t\u0005'Q1B&D\u0001\t\u0013\t)\u0002B\u0001\nJ]\u0012,\u00070\u001a3Ti\u0016\u0004\b/\u001a:CCN,\u0007cA\f\u001955\tA\"\u0003\u0002\u001a\u0019\tQ\u0011I\\=Ti\u0016\u0004\b/\u001a:\u0011\u0005maB\u0002\u0001\u0003\u0006;\u0001\u0011\ra\b\u0002\u0002\u0003\u000e\u0001\u0011C\u0001\u0011%!\t\t#%D\u0001\u000f\u0013\t\u0019cBA\u0004O_RD\u0017N\\4\u0011\u0005\u0015RS\"\u0001\u0014\u000b\u0005\u001dB\u0013\u0001\u00027b]\u001eT\u0011!K\u0001\u0005U\u00064\u0018-\u0003\u0002,M\t1qJ\u00196fGR\u00042a\u0005\u0001\u001b\u0003))h\u000eZ3sYfLgn\u001a\t\u0004C=R\u0012B\u0001\u0019\u000f\u0005\u0015\t%O]1z\u0003\ry\u0016\u000e\r\t\u0003CMJ!\u0001\u000e\b\u0003\u0007%sG/A\u0002`S:\u000ba\u0001P5oSRtD\u0003\u0002\u00179siBQ!\f\u0003A\u00029BQ!\r\u0003A\u0002IBQ!\u000e\u0003A\u0002I\n\u0001B\\3yiN#X\r\u001d\u000b\u00025\u0005I1/Z7jG2|g.\u001a\u000b\u0003Y}BQ\u0001\u0011\u0004A\u0002I\nA\u0001[1mM\u0002")
public class ObjectArrayStepper<A>
extends IndexedStepperBase<AnyStepper<A>, ObjectArrayStepper<A>>
implements AnyStepper<A> {
    private final A[] underlying;

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
            return this.underlying[j];
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    @Override
    public ObjectArrayStepper<A> semiclone(int half) {
        return new ObjectArrayStepper<A>(this.underlying, this.i0(), half);
    }

    public ObjectArrayStepper(A[] underlying, int _i0, int _iN) {
        this.underlying = underlying;
        super(_i0, _iN);
    }
}

