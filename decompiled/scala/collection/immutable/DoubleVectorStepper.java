/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.DoubleStepper;
import scala.collection.immutable.NewVectorIterator;
import scala.collection.immutable.VectorStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u001d2A!\u0002\u0004\u0005\u001b!A1\u0004\u0001B\u0001B\u0003%A\u0004C\u0003 \u0001\u0011\u0005\u0001\u0005\u0003\u0004#\u0001\u0001&\tb\t\u0005\u0006K\u0001!\tA\n\u0002\u0014\t>,(\r\\3WK\u000e$xN]*uKB\u0004XM\u001d\u0006\u0003\u000f!\t\u0011\"[7nkR\f'\r\\3\u000b\u0005%Q\u0011AC2pY2,7\r^5p]*\t1\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001qa\u0003E\u0003\u0010!I1\"$D\u0001\u0007\u0013\t\tbAA\tWK\u000e$xN]*uKB\u0004XM\u001d\"bg\u0016\u0004\"a\u0005\u000b\u000e\u0003)I!!\u0006\u0006\u0003\r\u0011{WO\u00197f!\t9\u0002$D\u0001\t\u0013\tI\u0002BA\u0007E_V\u0014G.Z*uKB\u0004XM\u001d\t\u0003\u001f\u0001\t!!\u001b;\u0011\u0007=i\"#\u0003\u0002\u001f\r\t\tb*Z<WK\u000e$xN]%uKJ\fGo\u001c:\u0002\rqJg.\u001b;?)\tQ\u0012\u0005C\u0003\u001c\u0005\u0001\u0007A$A\u0003ck&dG\r\u0006\u0002\u001bI!)1d\u0001a\u00019\u0005Aa.\u001a=u'R,\u0007\u000fF\u0001\u0013\u0001")
public class DoubleVectorStepper
extends VectorStepperBase<Object, DoubleStepper, DoubleVectorStepper>
implements DoubleStepper {
    public final NewVectorIterator<Object> scala$collection$immutable$DoubleVectorStepper$$it;

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
    public <B> Iterator<?> javaIterator$mcD$sp() {
        return DoubleStepper.javaIterator$mcD$sp$(this);
    }

    @Override
    public DoubleVectorStepper build(NewVectorIterator<Object> it) {
        return new DoubleVectorStepper(it);
    }

    @Override
    public double nextStep() {
        return this.nextStep$mcD$sp();
    }

    @Override
    public double nextStep$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this.scala$collection$immutable$DoubleVectorStepper$$it.next());
    }

    public DoubleVectorStepper(NewVectorIterator<Object> it) {
        this.scala$collection$immutable$DoubleVectorStepper$$it = it;
        super(it);
    }
}

