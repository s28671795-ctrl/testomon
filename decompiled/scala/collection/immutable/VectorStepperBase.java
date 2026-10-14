/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Spliterator;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.immutable.NewVectorIterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00054Q!\u0003\u0006\u0002\nEA\u0001\u0002\u000e\u0001\u0003\u0002\u0003\u0006I!\u000e\u0005\u0006s\u0001!\tA\u000f\u0005\u0007\u0011\u0002\u0001k\u0011C%\t\u000b-\u0003AQ\u0001'\t\u000bA\u0003AQA)\t\u000bU\u0003AQ\u0001,\t\u000bi\u0003A\u0011A.\t\u000bq\u0003AQI/\u0003#Y+7\r^8s'R,\u0007\u000f]3s\u0005\u0006\u001cXM\u0003\u0002\f\u0019\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u001b9\t!bY8mY\u0016\u001cG/[8o\u0015\u0005y\u0011!B:dC2\f7\u0001A\u000b\u0005%uiDi\u0005\u0003\u0001']1\u0003C\u0001\u000b\u0016\u001b\u0005q\u0011B\u0001\f\u000f\u0005\u0019\te.\u001f*fMB\u0019\u0001$G\u000e\u000e\u00031I!A\u0007\u0007\u0003\u000fM#X\r\u001d9feB\u0011A$\b\u0007\u0001\t\u0015q\u0002A1\u0001 \u0005\u0005\t\u0015C\u0001\u0011$!\t!\u0012%\u0003\u0002#\u001d\t9aj\u001c;iS:<\u0007C\u0001\u000b%\u0013\t)cBA\u0002B]f\u0004\"aJ\u0019\u000f\u0005!zcBA\u0015/\u001d\tQS&D\u0001,\u0015\ta\u0003#\u0001\u0004=e>|GOP\u0005\u0002\u001f%\u0011QBD\u0005\u0003a1\tqa\u0015;faB,'/\u0003\u00023g\tqQI\u001a4jG&,g\u000e^*qY&$(B\u0001\u0019\r\u0003\tIG\u000fE\u00027omi\u0011AC\u0005\u0003q)\u0011\u0011CT3x-\u0016\u001cGo\u001c:Ji\u0016\u0014\u0018\r^8s\u0003\u0019a\u0014N\\5u}Q\u00111h\u0012\t\u0006m\u0001YBh\u0011\t\u00039u\"QA\u0010\u0001C\u0002}\u00121aU;c#\t\u0001u\u0003\u0005\u0002\u0015\u0003&\u0011!I\u0004\u0002\u0005\u001dVdG\u000e\u0005\u0002\u001d\t\u0012)Q\t\u0001b\u0001\r\n!1+Z7j#\t\u0001C\bC\u00035\u0005\u0001\u0007Q'A\u0003ck&dG\r\u0006\u0002D\u0015\")Ag\u0001a\u0001k\u00059\u0001.Y:Ti\u0016\u0004X#A'\u0011\u0005Qq\u0015BA(\u000f\u0005\u001d\u0011un\u001c7fC:\fqb\u00195be\u0006\u001cG/\u001a:jgRL7m]\u000b\u0002%B\u0011AcU\u0005\u0003):\u00111!\u00138u\u00031)7\u000f^5nCR,7+\u001b>f+\u00059\u0006C\u0001\u000bY\u0013\tIfB\u0001\u0003M_:<\u0017\u0001\u0003;ssN\u0003H.\u001b;\u0015\u0003q\n\u0001\"\u001b;fe\u0006$xN]\u000b\u0002=B\u0019\u0001dX\u000e\n\u0005\u0001d!\u0001C%uKJ\fGo\u001c:")
public abstract class VectorStepperBase<A, Sub extends Stepper<A>, Semi extends Sub>
implements Stepper<A>,
Stepper.EfficientSplit {
    private final NewVectorIterator<A> it;

    @Override
    public double nextStep$mcD$sp() {
        return Stepper.nextStep$mcD$sp$(this);
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
    public <B> Spliterator<?> spliterator$mcD$sp() {
        return Stepper.spliterator$mcD$sp$(this);
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
    public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
        return Stepper.javaIterator$mcD$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return Stepper.javaIterator$mcI$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
        return Stepper.javaIterator$mcJ$sp$(this);
    }

    public abstract Semi build(NewVectorIterator<A> var1);

    @Override
    public final boolean hasStep() {
        NewVectorIterator<A> newVectorIterator = this.it;
        if (newVectorIterator == null) {
            throw null;
        }
        NewVectorIterator<A> hasNext_this = newVectorIterator;
        return hasNext_this.scala$collection$immutable$NewVectorIterator$$len1 > hasNext_this.scala$collection$immutable$NewVectorIterator$$i1;
    }

    @Override
    public final int characteristics() {
        return 16464;
    }

    @Override
    public final long estimateSize() {
        NewVectorIterator<A> newVectorIterator = this.it;
        if (newVectorIterator == null) {
            throw null;
        }
        NewVectorIterator<A> knownSize_this = newVectorIterator;
        return knownSize_this.scala$collection$immutable$NewVectorIterator$$len1 - knownSize_this.scala$collection$immutable$NewVectorIterator$$i1;
    }

    public Sub trySplit() {
        NewVectorIterator<A> newVectorIterator = this.it;
        if (newVectorIterator == null) {
            throw null;
        }
        NewVectorIterator<A> knownSize_this = newVectorIterator;
        Object var2_1 = null;
        int len = knownSize_this.scala$collection$immutable$NewVectorIterator$$len1 - knownSize_this.scala$collection$immutable$NewVectorIterator$$i1;
        if (len > 1) {
            return (Sub)this.build(this.it.split(len >>> 1));
        }
        return null;
    }

    @Override
    public final Iterator<A> iterator() {
        return this.it;
    }

    public VectorStepperBase(NewVectorIterator<A> it) {
        this.it = it;
    }
}

