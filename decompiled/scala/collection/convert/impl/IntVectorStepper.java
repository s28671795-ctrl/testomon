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
import scala.collection.convert.impl.VectorStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005i2Q\u0001C\u0005\u0001\u001bEA\u0001b\u0007\u0001\u0003\u0002\u0003\u0006I!\b\u0005\tC\u0001\u0011\t\u0011)A\u0005;!I!\u0005\u0001B\u0001B\u0003%Qd\t\u0005\nK\u0001\u0011\t\u0011)A\u0005M1BQA\f\u0001\u0005\u0002=BQ\u0001\u000e\u0001\u0005\u0002UBQA\u000e\u0001\u0005\u0002]\u0012\u0001#\u00138u-\u0016\u001cGo\u001c:Ti\u0016\u0004\b/\u001a:\u000b\u0005)Y\u0011\u0001B5na2T!\u0001D\u0007\u0002\u000f\r|gN^3si*\u0011abD\u0001\u000bG>dG.Z2uS>t'\"\u0001\t\u0002\u000bM\u001c\u0017\r\\1\u0014\u0007\u0001\u0011b\u0003\u0005\u0003\u0014)YQR\"A\u0005\n\u0005UI!!\u0005,fGR|'o\u0015;faB,'OQ1tKB\u0011q\u0003G\u0007\u0002\u001b%\u0011\u0011$\u0004\u0002\u000b\u0013:$8\u000b^3qa\u0016\u0014\bCA\n\u0001\u0003\ry\u0016\u000eM\u0002\u0001!\tqr$D\u0001\u0010\u0013\t\u0001sBA\u0002J]R\f1aX5O\u0003%yF-[:qY\u0006Lh*\u0003\u0002%)\u0005AA-[:qY\u0006Lh*\u0001\u0004`iJ,hn\u001b\t\u0004=\u001dJ\u0013B\u0001\u0015\u0010\u0005\u0015\t%O]1z!\tq\"&\u0003\u0002,\u001f\t1\u0011I\\=SK\u001aL!!\f\u000b\u0002\u000bQ\u0014XO\\6\u0002\rqJg.\u001b;?)\u0015Q\u0002'\r\u001a4\u0011\u0015YR\u00011\u0001\u001e\u0011\u0015\tS\u00011\u0001\u001e\u0011\u0015\u0011S\u00011\u0001\u001e\u0011\u0015)S\u00011\u0001'\u0003!qW\r\u001f;Ti\u0016\u0004H#A\u000f\u0002\u0013M,W.[2m_:,GC\u0001\u000e9\u0011\u0015It\u00011\u0001\u001e\u0003\u0011A\u0017\r\u001c4")
public class IntVectorStepper
extends VectorStepperBase<IntStepper, IntVectorStepper>
implements IntStepper {
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
    public IntVectorStepper semiclone(int half) {
        IntVectorStepper ans = new IntVectorStepper(this.i0(), half, this.displayN(), this.trunk());
        this.index_$eq(32);
        this.index1_$eq(32);
        this.i0_$eq(half);
        return ans;
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.hasStep()) {
            this.index_$eq(this.index() + 1);
            if (this.index() >= 32) {
                this.advanceData(this.i0());
            }
            this.i0_$eq(this.i0() + 1);
            return BoxesRunTime.unboxToInt(this.leaves()[this.index()]);
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public IntVectorStepper(int _i0, int _iN, int _displayN, Object[] _trunk) {
        super(_i0, _iN, _displayN, _trunk);
    }
}

