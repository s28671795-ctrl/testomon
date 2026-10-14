/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.IntStepper;
import scala.collection.immutable.NewVectorIterator;
import scala.collection.immutable.VectorStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u001d2A!\u0002\u0004\u0005\u001b!A1\u0004\u0001B\u0001B\u0003%A\u0004C\u0003 \u0001\u0011\u0005\u0001\u0005\u0003\u0004#\u0001\u0001&\tb\t\u0005\u0006K\u0001!\tA\n\u0002\u0011\u0013:$h+Z2u_J\u001cF/\u001a9qKJT!a\u0002\u0005\u0002\u0013%lW.\u001e;bE2,'BA\u0005\u000b\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0017\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u000f-A)q\u0002\u0005\n\u001755\ta!\u0003\u0002\u0012\r\t\tb+Z2u_J\u001cF/\u001a9qKJ\u0014\u0015m]3\u0011\u0005M!R\"\u0001\u0006\n\u0005UQ!aA%oiB\u0011q\u0003G\u0007\u0002\u0011%\u0011\u0011\u0004\u0003\u0002\u000b\u0013:$8\u000b^3qa\u0016\u0014\bCA\b\u0001\u0003\tIG\u000fE\u0002\u0010;II!A\b\u0004\u0003#9+wOV3di>\u0014\u0018\n^3sCR|'/\u0001\u0004=S:LGO\u0010\u000b\u00035\u0005BQa\u0007\u0002A\u0002q\tQAY;jY\u0012$\"A\u0007\u0013\t\u000bm\u0019\u0001\u0019\u0001\u000f\u0002\u00119,\u0007\u0010^*uKB$\u0012A\u0005")
public class IntVectorStepper
extends VectorStepperBase<Object, IntStepper, IntVectorStepper>
implements IntStepper {
    public final NewVectorIterator<Object> scala$collection$immutable$IntVectorStepper$$it;

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
    public <B> Iterator<?> javaIterator$mcI$sp() {
        return IntStepper.javaIterator$mcI$sp$(this);
    }

    @Override
    public IntVectorStepper build(NewVectorIterator<Object> it) {
        return new IntVectorStepper(it);
    }

    @Override
    public int nextStep() {
        return this.nextStep$mcI$sp();
    }

    @Override
    public int nextStep$mcI$sp() {
        return BoxesRunTime.unboxToInt(this.scala$collection$immutable$IntVectorStepper$$it.next());
    }

    public IntVectorStepper(NewVectorIterator<Object> it) {
        this.scala$collection$immutable$IntVectorStepper$$it = it;
        super(it);
    }
}

