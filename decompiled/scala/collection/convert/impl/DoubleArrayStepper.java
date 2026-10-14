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

@ScalaSignature(bytes="\u0006\u0005M2Qa\u0002\u0005\u0001\u0019AA\u0001B\u0007\u0001\u0003\u0002\u0003\u0006I\u0001\b\u0005\tG\u0001\u0011\t\u0011)A\u0005I!Aq\u0005\u0001B\u0001B\u0003%A\u0005C\u0003)\u0001\u0011\u0005\u0011\u0006C\u0003.\u0001\u0011\u0005a\u0006C\u00030\u0001\u0011E\u0001G\u0001\nE_V\u0014G.Z!se\u0006L8\u000b^3qa\u0016\u0014(BA\u0005\u000b\u0003\u0011IW\u000e\u001d7\u000b\u0005-a\u0011aB2p]Z,'\u000f\u001e\u0006\u0003\u001b9\t!bY8mY\u0016\u001cG/[8o\u0015\u0005y\u0011!B:dC2\f7c\u0001\u0001\u0012+A!!cE\u000b\u001a\u001b\u0005A\u0011B\u0001\u000b\t\u0005IIe\u000eZ3yK\u0012\u001cF/\u001a9qKJ\u0014\u0015m]3\u0011\u0005Y9R\"\u0001\u0007\n\u0005aa!!\u0004#pk\ndWm\u0015;faB,'\u000f\u0005\u0002\u0013\u0001\u0005QQO\u001c3fe2L\u0018N\\4\u0004\u0001A\u0019QD\b\u0011\u000e\u00039I!a\b\b\u0003\u000b\u0005\u0013(/Y=\u0011\u0005u\t\u0013B\u0001\u0012\u000f\u0005\u0019!u.\u001e2mK\u0006\u0019q,\u001b\u0019\u0011\u0005u)\u0013B\u0001\u0014\u000f\u0005\rIe\u000e^\u0001\u0004?&t\u0015A\u0002\u001fj]&$h\b\u0006\u0003\u001aU-b\u0003\"\u0002\u000e\u0005\u0001\u0004a\u0002\"B\u0012\u0005\u0001\u0004!\u0003\"B\u0014\u0005\u0001\u0004!\u0013\u0001\u00038fqR\u001cF/\u001a9\u0015\u0003\u0001\n\u0011b]3nS\u000edwN\\3\u0015\u0005e\t\u0004\"\u0002\u001a\u0007\u0001\u0004!\u0013\u0001\u00025bY\u001a\u0004")
public class DoubleArrayStepper
extends IndexedStepperBase<DoubleStepper, DoubleArrayStepper>
implements DoubleStepper {
    public final double[] scala$collection$convert$impl$DoubleArrayStepper$$underlying;

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
    public DoubleArrayStepper semiclone(int half) {
        return new DoubleArrayStepper(this.scala$collection$convert$impl$DoubleArrayStepper$$underlying, this.i0(), half);
    }

    @Override
    public double nextStep$mcD$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return this.scala$collection$convert$impl$DoubleArrayStepper$$underlying[j];
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public DoubleArrayStepper(double[] underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$DoubleArrayStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

