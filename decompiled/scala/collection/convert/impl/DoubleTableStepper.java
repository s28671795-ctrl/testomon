/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.Function1;
import scala.collection.DoubleStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.TableStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005A3QAC\u0006\u0003\u001fMA\u0001B\f\u0001\u0003\u0002\u0003\u0006Ia\f\u0005\ne\u0001\u0011\t\u0011)A\u0005gYB\u0001\u0002\u000f\u0001\u0003\u0002\u0003\u0006I!\u000f\u0005\ty\u0001\u0011\t\u0011)A\u0005{!Aa\b\u0001B\u0001B\u0003%q\u0006C\u0005@\u0001\t\u0005\t\u0015!\u00030\u0001\")!\t\u0001C\u0001\u0007\")!\n\u0001C\u0001\u0017\")A\n\u0001C\u0001\u001b\n\u0011Bi\\;cY\u0016$\u0016M\u00197f'R,\u0007\u000f]3s\u0015\taQ\"\u0001\u0003j[Bd'B\u0001\b\u0010\u0003\u001d\u0019wN\u001c<feRT!\u0001E\t\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u0013\u0003\u0015\u00198-\u00197b+\t!rdE\u0002\u0001+%\u0002bAF\f\u001a;%jS\"A\u0006\n\u0005aY!\u0001\u0005+bE2,7\u000b^3qa\u0016\u0014()Y:f!\tQ2$D\u0001\u0012\u0013\ta\u0012C\u0001\u0004E_V\u0014G.\u001a\t\u0003=}a\u0001\u0001B\u0003!\u0001\t\u0007!EA\u0001J\u0007\u0001\t\"a\t\u0014\u0011\u0005i!\u0013BA\u0013\u0012\u0005\u0011qU\u000f\u001c7\u0011\u0005i9\u0013B\u0001\u0015\u0012\u0005\u0019\te.\u001f*fMB\u0011!fK\u0007\u0002\u001f%\u0011Af\u0004\u0002\u000e\t>,(\r\\3Ti\u0016\u0004\b/\u001a:\u0011\u0007Y\u0001Q$\u0001\u0006`[\u0006DH*\u001a8hi\"\u0004\"A\u0007\u0019\n\u0005E\n\"aA%oi\u00061q\f^1cY\u0016\u00042A\u0007\u001b\u001e\u0013\t)\u0014CA\u0003BeJ\f\u00170\u0003\u00028/\u0005)A/\u00192mK\u00069\u0011\u000e^3sCR,\u0007\u0003\u0002\u000e;;uI!aO\t\u0003\u0013\u0019+hn\u0019;j_:\f\u0014aB3yiJ\f7\r\u001e\t\u00055ij\u0012$A\u0002`SB\n1aX5O\u0013\t\tu#\u0001\u0002j\u001d\u00061A(\u001b8jiz\"r!\f#F\r\u001eC\u0015\nC\u0003/\u000f\u0001\u0007q\u0006C\u00033\u000f\u0001\u00071\u0007C\u00039\u000f\u0001\u0007\u0011\bC\u0003=\u000f\u0001\u0007Q\bC\u0003?\u000f\u0001\u0007q\u0006C\u0003@\u000f\u0001\u0007q&\u0001\u0005oKb$8\u000b^3q)\u0005I\u0012!C:f[&\u001cGn\u001c8f)\tic\nC\u0003P\u0013\u0001\u0007q&\u0001\u0003iC24\u0007")
public final class DoubleTableStepper<I>
extends TableStepperBase<Object, I, DoubleStepper, DoubleTableStepper<I>>
implements DoubleStepper {
    public final Function1<I, I> scala$collection$convert$impl$DoubleTableStepper$$iterate;
    public final Function1<I, Object> scala$collection$convert$impl$DoubleTableStepper$$extract;

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
    public DoubleTableStepper<I> semiclone(int half) {
        return new DoubleTableStepper(this.maxLength(), this.table(), this.scala$collection$convert$impl$DoubleTableStepper$$iterate, this.scala$collection$convert$impl$DoubleTableStepper$$extract, this.i0(), half);
    }

    @Override
    public double nextStep$mcD$sp() {
        if (this.hasStep()) {
            double ans = BoxesRunTime.unboxToDouble(this.scala$collection$convert$impl$DoubleTableStepper$$extract.apply(this.myCurrent()));
            this.myCurrent_$eq(this.scala$collection$convert$impl$DoubleTableStepper$$iterate.apply(this.myCurrent()));
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public DoubleTableStepper(int _maxLength, I[] _table, Function1<I, I> iterate, Function1<I, Object> extract, int _i0, int _iN) {
        this.scala$collection$convert$impl$DoubleTableStepper$$iterate = iterate;
        this.scala$collection$convert$impl$DoubleTableStepper$$extract = extract;
        super(_maxLength, _table, _i0, _iN);
    }
}

