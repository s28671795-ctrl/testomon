/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.convert.impl.AnyIteratorStepper;
import scala.collection.convert.impl.DoubleIteratorStepper;
import scala.collection.convert.impl.IntIteratorStepper;
import scala.collection.convert.impl.LongIteratorStepper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=ba\u0002\b\u0010!\u0003\r\t\u0001\u0006\u0005\u00065\u0001!\ta\u0007\u0005\u0006?\u00011\t\u0001\t\u0005\u0006[\u0001!\tA\f\u0005\u0006\u0001\u0002!\t!Q\u0004\u0006\u000b>A\tA\u0012\u0004\u0006\u001d=A\ta\u0012\u0005\u0006\u0017\u001a!\t\u0001\u0014\u0005\u0006\u001b\u001a!\u0019A\u0014\u0005\u00079\u001a!\taD/\t\r\u001d4A\u0011A\bi\u0011!Yh!%A\u0005\u0002=a\bBCA\r\rE\u0005I\u0011A\b\u0002\u001c!A\u0011Q\u0005\u0004\u0005\u0002=\t9C\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cWM\u0003\u0002\u0011#\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003I\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u0016OM\u0011\u0001A\u0006\t\u0003/ai\u0011!E\u0005\u00033E\u00111!\u00118z\u0003\u0019!\u0013N\\5uIQ\tA\u0004\u0005\u0002\u0018;%\u0011a$\u0005\u0002\u0005+:LG/\u0001\u0005ji\u0016\u0014\u0018\r^8s+\u0005\t\u0003c\u0001\u0012$K5\tq\"\u0003\u0002%\u001f\tA\u0011\n^3sCR|'\u000f\u0005\u0002'O1\u0001AA\u0002\u0015\u0001\t\u000b\u0007\u0011FA\u0001B#\tQc\u0003\u0005\u0002\u0018W%\u0011A&\u0005\u0002\b\u001d>$\b.\u001b8h\u0003\u001d\u0019H/\u001a9qKJ,\"aL\u0019\u0015\u0005AZ\u0004C\u0001\u00142\t\u0015\u00114A1\u00014\u0005\u0005\u0019\u0016C\u0001\u00165a\t)\u0014\bE\u0002#maJ!aN\b\u0003\u000fM#X\r\u001d9feB\u0011a%\u000f\u0003\nuE\n\t\u0011!A\u0003\u0002%\u00121a\u0018\u00132\u0011\u0015a4\u0001q\u0001>\u0003\u0015\u0019\b.\u00199f!\u0011\u0011c(\n\u0019\n\u0005}z!\u0001D*uKB\u0004XM]*iCB,\u0017!C6o_^t7+\u001b>f+\u0005\u0011\u0005CA\fD\u0013\t!\u0015CA\u0002J]R\fA\"\u0013;fe\u0006\u0014G.Z(oG\u0016\u0004\"A\t\u0004\u0014\u0005\u0019A\u0005CA\fJ\u0013\tQ\u0015C\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\u0019\u000bA$\u001b;fe\u0006\u0014G.Z(oG\u0016,\u0005\u0010^3og&|g.T3uQ>$7/\u0006\u0002P)R\u0011\u0001+\u0016\t\u0004EE\u001b\u0016B\u0001*\u0010\u0005qIE/\u001a:bE2,wJ\\2f\u000bb$XM\\:j_:lU\r\u001e5pIN\u0004\"A\n+\u0005\u000b!B!\u0019A\u0015\t\u000bYC\u0001\u0019A,\u0002\u0005%$\bc\u0001\u0012\u0001'\"\u0012\u0001\"\u0017\t\u0003/iK!aW\t\u0003\r%tG.\u001b8f\u0003I)G.Z7t)>\u001cu\u000e]=U_\u0006\u0013(/Y=\u0015\u000b\ts\u0006M\u00193\t\u000b}K\u0001\u0019\u0001\"\u0002\rM\u00148\rT3o\u0011\u0015\t\u0017\u00021\u0001C\u0003\u001d!Wm\u001d;MK:DQaY\u0005A\u0002\t\u000bQa\u001d;beRDQ!Z\u0005A\u0002\t\u000b1\u0001\\3oQ\tI\u0011,\u0001\td_BLX\t\\3ngR{\u0017I\u001d:bsV\u0019\u0011N\\;\u0015\u000b\tSw\u000e_=\t\u000b-T\u0001\u0019\u00017\u0002\u000b\u0015dW-\\:\u0011\u0007\t\u0002Q\u000e\u0005\u0002']\u0012)\u0001F\u0003b\u0001S!)\u0001O\u0003a\u0001c\u0006\u0011\u0001p\u001d\t\u0004/I$\u0018BA:\u0012\u0005\u0015\t%O]1z!\t1S\u000fB\u0003w\u0015\t\u0007qOA\u0001C#\tig\u0003C\u0004d\u0015A\u0005\t\u0019\u0001\"\t\u000f\u0015T\u0001\u0013!a\u0001\u0005\"\u0012!\"W\u0001\u001bG>\u0004\u00180\u00127f[N$v.\u0011:sCf$C-\u001a4bk2$HeM\u000b\u0006{\u0006E\u00111C\u000b\u0002}*\u0012!i`\u0016\u0003\u0003\u0003\u0001B!a\u0001\u0002\u000e5\u0011\u0011Q\u0001\u0006\u0005\u0003\u000f\tI!A\u0005v]\u000eDWmY6fI*\u0019\u00111B\t\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\u0010\u0005\u0015!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)\u0001f\u0003b\u0001S\u00111ao\u0003b\u0001\u0003+\t2!a\u0006\u0017!\r1\u0013\u0011C\u0001\u001bG>\u0004\u00180\u00127f[N$v.\u0011:sCf$C-\u001a4bk2$H\u0005N\u000b\u0006{\u0006u\u0011q\u0004\u0003\u0006Q1\u0011\r!\u000b\u0003\u0007m2\u0011\r!!\t\u0012\u0007\u0005\rb\u0003E\u0002'\u0003;\t1d\u00195fG.\f%O]1z'&TXmV5uQ&tg+\u0014'j[&$Hc\u0001\u000f\u0002*!1\u00111F\u0007A\u0002\t\u000bAa]5{K\"\u0012Q\"\u0017")
public interface IterableOnce<A> {
    public static IterableOnce iterableOnceExtensionMethods(IterableOnce it) {
        return it;
    }

    public Iterator<A> iterator();

    public static /* synthetic */ Stepper stepper$(IterableOnce $this, StepperShape shape) {
        return $this.stepper(shape);
    }

    default public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            return (S)new IntIteratorStepper(this.iterator());
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            return (S)new LongIteratorStepper(this.iterator());
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            return (S)new DoubleIteratorStepper(this.iterator());
        }
        return shape.seqUnbox(new AnyIteratorStepper<A>(this.iterator()));
    }

    public static /* synthetic */ int knownSize$(IterableOnce $this) {
        return $this.knownSize();
    }

    default public int knownSize() {
        return -1;
    }

    public static void $init$(IterableOnce $this) {
    }
}

