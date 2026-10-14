/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.LongStepper;
import scala.collection.immutable.NewVectorIterator;
import scala.collection.immutable.VectorStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u001d2A!\u0002\u0004\u0005\u001b!A1\u0004\u0001B\u0001B\u0003%A\u0004C\u0003 \u0001\u0011\u0005\u0001\u0005\u0003\u0004#\u0001\u0001&\tb\t\u0005\u0006K\u0001!\tA\n\u0002\u0012\u0019>twMV3di>\u00148\u000b^3qa\u0016\u0014(BA\u0004\t\u0003%IW.\\;uC\ndWM\u0003\u0002\n\u0015\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003-\tQa]2bY\u0006\u001c\u0001aE\u0002\u0001\u001dY\u0001Ra\u0004\t\u0013-ii\u0011AB\u0005\u0003#\u0019\u0011\u0011CV3di>\u00148\u000b^3qa\u0016\u0014()Y:f!\t\u0019B#D\u0001\u000b\u0013\t)\"B\u0001\u0003M_:<\u0007CA\f\u0019\u001b\u0005A\u0011BA\r\t\u0005-auN\\4Ti\u0016\u0004\b/\u001a:\u0011\u0005=\u0001\u0011AA5u!\ryQDE\u0005\u0003=\u0019\u0011\u0011CT3x-\u0016\u001cGo\u001c:Ji\u0016\u0014\u0018\r^8s\u0003\u0019a\u0014N\\5u}Q\u0011!$\t\u0005\u00067\t\u0001\r\u0001H\u0001\u0006EVLG\u000e\u001a\u000b\u00035\u0011BQaG\u0002A\u0002q\t\u0001B\\3yiN#X\r\u001d\u000b\u0002%\u0001")
public class LongVectorStepper
extends VectorStepperBase<Object, LongStepper, LongVectorStepper>
implements LongStepper {
    public final NewVectorIterator<Object> scala$collection$immutable$LongVectorStepper$$it;

    @Override
    public <B> Spliterator.OfLong spliterator() {
        return LongStepper.spliterator$(this);
    }

    @Override
    public <B> PrimitiveIterator.OfLong javaIterator() {
        return LongStepper.javaIterator$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcJ$sp() {
        return LongStepper.spliterator$mcJ$sp$(this);
    }

    @Override
    public <B> Iterator<?> javaIterator$mcJ$sp() {
        return LongStepper.javaIterator$mcJ$sp$(this);
    }

    @Override
    public LongVectorStepper build(NewVectorIterator<Object> it) {
        return new LongVectorStepper(it);
    }

    @Override
    public long nextStep() {
        return this.nextStep$mcJ$sp();
    }

    @Override
    public long nextStep$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this.scala$collection$immutable$LongVectorStepper$$it.next());
    }

    public LongVectorStepper(NewVectorIterator<Object> it) {
        this.scala$collection$immutable$LongVectorStepper$$it = it;
        super(it);
    }
}

