/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.Function2;
import scala.collection.DoubleStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.ChampStepperBase;
import scala.collection.immutable.Node;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005e3Qa\u0003\u0007\u0003!QA\u0001B\r\u0001\u0003\u0002\u0003\u0006Ia\r\u0005\tm\u0001\u0011)\u0019!C\to!A1\b\u0001B\u0001B\u0003%\u0001\bC\u0003=\u0001\u0011\u0005Q\bC\u0003A\u0001\u0011\u0005\u0011\tC\u0003C\u0001\u0011\u00051i\u0002\u0004E\u0019!\u0005\u0001#\u0012\u0004\u0007\u00171A\t\u0001\u0005$\t\u000bqBA\u0011\u0001&\t\u000b-CA\u0011\u0001'\u0003%\u0011{WO\u00197f\u0007\"\fW\u000e]*uKB\u0004XM\u001d\u0006\u0003\u001b9\tA![7qY*\u0011q\u0002E\u0001\bG>tg/\u001a:u\u0015\t\t\"#\u0001\u0006d_2dWm\u0019;j_:T\u0011aE\u0001\u0006g\u000e\fG.Y\u000b\u0003+\u0001\u001a2\u0001\u0001\f.!\u00199\u0002D\u0007\u0010.c5\tA\"\u0003\u0002\u001a\u0019\t\u00012\t[1naN#X\r\u001d9fe\n\u000b7/\u001a\t\u00037qi\u0011AE\u0005\u0003;I\u0011a\u0001R8vE2,\u0007CA\u0010!\u0019\u0001!Q!\t\u0001C\u0002\r\u0012\u0011\u0001V\u0002\u0001#\t!s\u0005\u0005\u0002\u001cK%\u0011aE\u0005\u0002\u0005\u001dVdG\u000eE\u0002)Wyi\u0011!\u000b\u0006\u0003UA\t\u0011\"[7nkR\f'\r\\3\n\u00051J#\u0001\u0002(pI\u0016\u0004\"AL\u0018\u000e\u0003AI!\u0001\r\t\u0003\u001b\u0011{WO\u00197f'R,\u0007\u000f]3s!\r9\u0002AH\u0001\t?6\f\u0007pU5{KB\u00111\u0004N\u0005\u0003kI\u00111!\u00138u\u0003\u001d)\u0007\u0010\u001e:bGR,\u0012\u0001\u000f\t\u00067er2GG\u0005\u0003uI\u0011\u0011BR;oGRLwN\u001c\u001a\u0002\u0011\u0015DHO]1di\u0002\na\u0001P5oSRtDcA\u0019?\u007f!)!\u0007\u0002a\u0001g!)a\u0007\u0002a\u0001q\u0005Aa.\u001a=u'R,\u0007\u000fF\u0001\u001b\u0003%\u0019X-\\5dY>tW\rF\u00012\u0003I!u.\u001e2mK\u000eC\u0017-\u001c9Ti\u0016\u0004\b/\u001a:\u0011\u0005]A1C\u0001\u0005H!\tY\u0002*\u0003\u0002J%\t1\u0011I\\=SK\u001a$\u0012!R\u0001\u0005MJ|W.\u0006\u0002N!R!ajU+X!\r9\u0002a\u0014\t\u0003?A#Q!\t\u0006C\u0002E\u000b\"\u0001\n*\u0011\u0007!Zs\nC\u0003U\u0015\u0001\u00071'A\u0004nCb\u001c\u0016N_3\t\u000bYS\u0001\u0019A(\u0002\tI|w\u000e\u001e\u0005\u0006m)\u0001\r\u0001\u0017\t\u00067ez5G\u0007")
public final class DoubleChampStepper<T extends Node<T>>
extends ChampStepperBase<Object, T, DoubleStepper, DoubleChampStepper<T>>
implements DoubleStepper {
    private final Function2<T, Object, Object> extract;

    /*
     * WARNING - void declaration
     */
    public static <T extends Node<T>> DoubleChampStepper<T> from(int maxSize, T root, Function2<T, Object, Object> extract) {
        void var3_3;
        DoubleChampStepper<T> from_ans = new DoubleChampStepper<T>(maxSize, extract);
        from_ans.initRoot(root);
        return var3_3;
    }

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

    public Function2<T, Object, Object> extract() {
        return this.extract;
    }

    @Override
    public double nextStep() {
        return this.nextStep$mcD$sp();
    }

    @Override
    public DoubleChampStepper<T> semiclone() {
        return new DoubleChampStepper<T>(0, this.extract());
    }

    @Override
    public double nextStep$mcD$sp() {
        if (this.hasStep()) {
            double ans = BoxesRunTime.unboxToDouble(this.extract().apply(this.currentValueNode(), this.currentValueCursor()));
            this.currentValueCursor_$eq(this.currentValueCursor() + 1);
            this.maxSize_$eq(this.maxSize() - 1);
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public DoubleChampStepper(int _maxSize, Function2<T, Object, Object> extract) {
        this.extract = extract;
        super(_maxSize);
    }
}

