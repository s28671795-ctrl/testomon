/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.DoubleStepper;
import scala.collection.IndexedSeqOps;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.IndexedStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005I3Qa\u0002\u0005\u0001\u0019AA\u0001\"\u0011\u0001\u0003\u0002\u0003\u0006Ia\u0007\u0005\t\u0005\u0002\u0011\t\u0011)A\u0005\u0007\"Aa\t\u0001B\u0001B\u0003%1\tC\u0003H\u0001\u0011\u0005\u0001\nC\u0003M\u0001\u0011\u0005Q\nC\u0003O\u0001\u0011EqJA\fE_V\u0014G.Z%oI\u0016DX\rZ*fcN#X\r\u001d9fe*\u0011\u0011BC\u0001\u0005S6\u0004HN\u0003\u0002\f\u0019\u000591m\u001c8wKJ$(BA\u0007\u000f\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u001f\u0005)1oY1mCV\u0011\u0011#H\n\u0004\u0001I1\u0002\u0003B\n\u0015-ii\u0011\u0001C\u0005\u0003+!\u0011!#\u00138eKb,Gm\u0015;faB,'OQ1tKB\u0011q\u0003G\u0007\u0002\u0019%\u0011\u0011\u0004\u0004\u0002\u000e\t>,(\r\\3Ti\u0016\u0004\b/\u001a:\u0011\u0007M\u00011\u0004\u0005\u0002\u001d;1\u0001A!\u0002\u0010\u0001\u0005\u0004\u0001#AA\"D\u0007\u0001\t\"!I\u0013\u0011\u0005\t\u001aS\"\u0001\b\n\u0005\u0011r!a\u0002(pi\"Lgn\u001a\u0019\u0003Mm\u0002RaF\u0014*YiJ!\u0001\u000b\u0007\u0003\u001b%sG-\u001a=fIN+\u0017o\u00149t!\t\u0011#&\u0003\u0002,\u001d\t1Ai\\;cY\u0016\u0004\"!L\u001c\u000f\u00059*dBA\u00185\u001d\t\u00014'D\u00012\u0015\t\u0011t$\u0001\u0004=e>|GOP\u0005\u0002\u001f%\u0011QBD\u0005\u0003m1\tq\u0001]1dW\u0006<W-\u0003\u00029s\tI\u0011I\\=D_:\u001cHO\u001d\u0006\u0003m1\u0001\"\u0001H\u001e\u0005\u0013qj\u0012\u0011!A\u0001\u0006\u0003i$aA0%eE\u0011\u0011E\u0010\t\u0003E}J!\u0001\u0011\b\u0003\u0007\u0005s\u00170\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\f1aX51!\t\u0011C)\u0003\u0002F\u001d\t\u0019\u0011J\u001c;\u0002\u0007}Kg*\u0001\u0004=S:LGO\u0010\u000b\u00055%S5\nC\u0003B\t\u0001\u00071\u0004C\u0003C\t\u0001\u00071\tC\u0003G\t\u0001\u00071)\u0001\u0005oKb$8\u000b^3q)\u0005I\u0013!C:f[&\u001cGn\u001c8f)\tQ\u0002\u000bC\u0003R\r\u0001\u00071)\u0001\u0003iC24\u0007")
public class DoubleIndexedSeqStepper<CC extends IndexedSeqOps<Object, Object, ?>>
extends IndexedStepperBase<DoubleStepper, DoubleIndexedSeqStepper<CC>>
implements DoubleStepper {
    public final CC scala$collection$convert$impl$DoubleIndexedSeqStepper$$underlying;

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
    public DoubleIndexedSeqStepper<CC> semiclone(int half) {
        return new DoubleIndexedSeqStepper<CC>(this.scala$collection$convert$impl$DoubleIndexedSeqStepper$$underlying, this.i0(), half);
    }

    @Override
    public double nextStep$mcD$sp() {
        if (this.hasStep()) {
            int j = this.i0();
            this.i0_$eq(this.i0() + 1);
            return BoxesRunTime.unboxToDouble(this.scala$collection$convert$impl$DoubleIndexedSeqStepper$$underlying.apply(j));
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public DoubleIndexedSeqStepper(CC underlying, int _i0, int _iN) {
        this.scala$collection$convert$impl$DoubleIndexedSeqStepper$$underlying = underlying;
        super(_i0, _iN);
    }
}

