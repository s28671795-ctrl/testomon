/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.DoubleStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.convert.impl.IteratorStepperBase;
import scala.jdk.Accumulator;
import scala.jdk.DoubleAccumulator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005Y2QAB\u0004\u0001\u0017=A\u0011\"\b\u0001\u0003\u0002\u0003\u0006IaH\u0016\t\u000b5\u0002A\u0011\u0001\u0018\t\u000bA\u0002A\u0011C\u0019\t\u000bI\u0002A\u0011A\u001a\t\u000bQ\u0002A\u0011A\u001b\u0003+\u0011{WO\u00197f\u0013R,'/\u0019;peN#X\r\u001d9fe*\u0011\u0001\"C\u0001\u0005S6\u0004HN\u0003\u0002\u000b\u0017\u000591m\u001c8wKJ$(B\u0001\u0007\u000e\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u001d\u0005)1oY1mCN\u0019\u0001\u0001\u0005\r\u0011\u000bE\u0011B\u0003\u0007\u000f\u000e\u0003\u001dI!aE\u0004\u0003'%#XM]1u_J\u001cF/\u001a9qKJ\u0014\u0015m]3\u0011\u0005U1R\"A\u0007\n\u0005]i!A\u0002#pk\ndW\r\u0005\u0002\u001a55\t1\"\u0003\u0002\u001c\u0017\tiAi\\;cY\u0016\u001cF/\u001a9qKJ\u0004\"!\u0005\u0001\u0002\u0017}+h\u000eZ3sYfLgnZ\u0002\u0001!\r\u0001\u0003\u0006\u0006\b\u0003C\u0019r!AI\u0013\u000e\u0003\rR!\u0001\n\u0010\u0002\rq\u0012xn\u001c;?\u0013\u0005q\u0011BA\u0014\u000e\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u000b\u0016\u0003\u0011%#XM]1u_JT!aJ\u0007\n\u00051\u0012\u0012AC;oI\u0016\u0014H._5oO\u00061A(\u001b8jiz\"\"\u0001H\u0018\t\u000bu\u0011\u0001\u0019A\u0010\u0002\u0013M,W.[2m_:,G#\u0001\u000f\u0002\u00119,\u0007\u0010^*uKB$\u0012\u0001F\u0001\tiJL8\u000b\u001d7jiR\t\u0001\u0004")
public class DoubleIteratorStepper
extends IteratorStepperBase<Object, DoubleStepper, DoubleIteratorStepper>
implements DoubleStepper {
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
    public DoubleIteratorStepper semiclone() {
        return new DoubleIteratorStepper(null);
    }

    @Override
    public double nextStep() {
        return this.nextStep$mcD$sp();
    }

    @Override
    public DoubleStepper trySplit() {
        int i;
        if (this.proxied() != null) {
            return ((DoubleStepper)this.proxied()).trySplit();
        }
        DoubleAccumulator acc = new DoubleAccumulator();
        int n = this.nextChunkSize() & 0xFFFFFFFC;
        for (i = 0; i < n && this.underlying().hasNext(); ++i) {
            Object $plus$eq_elem = this.underlying().next();
            acc.addOne(BoxesRunTime.unboxToDouble($plus$eq_elem));
            Object var5_4 = null;
        }
        if (i < n || !this.underlying().hasNext()) {
            StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
            Object var6_5 = null;
            this.proxied_$eq(((Accumulator)acc).efficientStepper(stepper_shape));
            return ((DoubleStepper)this.proxied()).trySplit();
        }
        DoubleIteratorStepper ans = this.semiclone();
        StepperShape<Object, DoubleStepper> stepper_shape = StepperShape$.MODULE$.doubleStepperShape();
        Object var7_7 = null;
        ans.proxied_$eq(((Accumulator)acc).efficientStepper(stepper_shape));
        this.nextChunkSize_$eq((this.nextChunkSize() & 3) == 3 ? (n < 0x40000000 ? n * 2 : n) : this.nextChunkSize() + 1);
        return ans;
    }

    @Override
    public double nextStep$mcD$sp() {
        if (this.proxied() != null) {
            return this.proxied().nextStep$mcD$sp();
        }
        return BoxesRunTime.unboxToDouble(this.underlying().next());
    }

    public DoubleIteratorStepper(Iterator<Object> _underlying) {
        super(_underlying);
    }
}

