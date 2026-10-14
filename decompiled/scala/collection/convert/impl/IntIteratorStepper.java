/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.convert.impl.IteratorStepperBase;
import scala.jdk.Accumulator;
import scala.jdk.IntAccumulator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005Y2QAB\u0004\u0001\u0017=A\u0011\"\b\u0001\u0003\u0002\u0003\u0006IaH\u0016\t\u000b5\u0002A\u0011\u0001\u0018\t\u000bA\u0002A\u0011C\u0019\t\u000bI\u0002A\u0011A\u001a\t\u000bQ\u0002A\u0011A\u001b\u0003%%sG/\u0013;fe\u0006$xN]*uKB\u0004XM\u001d\u0006\u0003\u0011%\tA![7qY*\u0011!bC\u0001\bG>tg/\u001a:u\u0015\taQ\"\u0001\u0006d_2dWm\u0019;j_:T\u0011AD\u0001\u0006g\u000e\fG.Y\n\u0004\u0001AA\u0002#B\t\u0013)aaR\"A\u0004\n\u0005M9!aE%uKJ\fGo\u001c:Ti\u0016\u0004\b/\u001a:CCN,\u0007CA\u000b\u0017\u001b\u0005i\u0011BA\f\u000e\u0005\rIe\u000e\u001e\t\u00033ii\u0011aC\u0005\u00037-\u0011!\"\u00138u'R,\u0007\u000f]3s!\t\t\u0002!A\u0006`k:$WM\u001d7zS:<7\u0001\u0001\t\u0004A!\"bBA\u0011'\u001d\t\u0011S%D\u0001$\u0015\t!c$\u0001\u0004=e>|GOP\u0005\u0002\u001d%\u0011q%D\u0001\ba\u0006\u001c7.Y4f\u0013\tI#F\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0015\t9S\"\u0003\u0002-%\u0005QQO\u001c3fe2L\u0018N\\4\u0002\rqJg.\u001b;?)\tar\u0006C\u0003\u001e\u0005\u0001\u0007q$A\u0005tK6L7\r\\8oKR\tA$\u0001\u0005oKb$8\u000b^3q)\u0005!\u0012\u0001\u0003;ssN\u0003H.\u001b;\u0015\u0003a\u0001")
public class IntIteratorStepper
extends IteratorStepperBase<Object, IntStepper, IntIteratorStepper>
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
    public IntIteratorStepper semiclone() {
        return new IntIteratorStepper(null);
    }

    @Override
    public int nextStep() {
        return this.nextStep$mcI$sp();
    }

    @Override
    public IntStepper trySplit() {
        int i;
        if (this.proxied() != null) {
            return ((IntStepper)this.proxied()).trySplit();
        }
        IntAccumulator acc = new IntAccumulator();
        int n = this.nextChunkSize() & 0xFFFFFFFC;
        for (i = 0; i < n && this.underlying().hasNext(); ++i) {
            Object $plus$eq_elem = this.underlying().next();
            acc.addOne(BoxesRunTime.unboxToInt($plus$eq_elem));
            Object var5_4 = null;
        }
        if (i < n || !this.underlying().hasNext()) {
            StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
            Object var6_5 = null;
            this.proxied_$eq(((Accumulator)acc).efficientStepper(stepper_shape));
            return ((IntStepper)this.proxied()).trySplit();
        }
        IntIteratorStepper ans = this.semiclone();
        StepperShape<Object, IntStepper> stepper_shape = StepperShape$.MODULE$.intStepperShape();
        Object var7_7 = null;
        ans.proxied_$eq(((Accumulator)acc).efficientStepper(stepper_shape));
        this.nextChunkSize_$eq((this.nextChunkSize() & 3) == 3 ? (n < 0x40000000 ? n * 2 : n) : this.nextChunkSize() + 1);
        return ans;
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.proxied() != null) {
            return this.proxied().nextStep$mcI$sp();
        }
        return BoxesRunTime.unboxToInt(this.underlying().next());
    }

    public IntIteratorStepper(Iterator<Object> _underlying) {
        super(_underlying);
    }
}

