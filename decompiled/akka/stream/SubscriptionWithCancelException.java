/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.DoNotInherit;
import akka.stream.SubscriptionWithCancelException$NoMoreElementsNeeded$;
import org.reactivestreams.Subscription;
import scala.reflect.ScalaSignature;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\u0005-caB\u000f\u001f!\u0003\r\ta\t\u0005\u0006i\u0001!\t!\u000e\u0005\u0006y\u0001!)%\u000e\u0005\u0006y\u00011\t!P\u0004\u0006'zA\t\u0001\u0016\u0004\u0006;yA\tA\u0016\u0005\u00065\u0016!\ta\u0017\u0004\u00069\u0016\t\t#\u0018\u0005\u00065\u001e!\t![\u0004\u0007\u0003\u000f*\u0001\u0012\u0011<\u0007\u000b5,\u0001\u0012\u00118\t\u000biSA\u0011A;\t\u000f]T\u0011\u0011!C!q\"9APCA\u0001\n\u0003i\b\"CA\u0002\u0015\u0005\u0005I\u0011AA\u0003\u0011%\t\tBCA\u0001\n\u0003\n\u0019\u0002C\u0005\u0002\")\t\t\u0011\"\u0001\u0002$!I\u0011Q\u0006\u0006\u0002\u0002\u0013\u0005\u0013q\u0006\u0005\n\u0003cQ\u0011\u0011!C\u0005\u0003g9q!!\u0013\u0006\u0011\u0003\u000bYDB\u0004\u00026\u0015A\t)a\u000e\t\ri#B\u0011AA\u001d\u0011\u001d9H#!A\u0005BaDq\u0001 \u000b\u0002\u0002\u0013\u0005Q\u0010C\u0005\u0002\u0004Q\t\t\u0011\"\u0001\u0002>!I\u0011\u0011\u0003\u000b\u0002\u0002\u0013\u0005\u00131\u0003\u0005\n\u0003C!\u0012\u0011!C\u0001\u0003\u0003B\u0011\"!\f\u0015\u0003\u0003%\t%a\f\t\u0013\u0005EB#!A\u0005\n\u0005M\"aH*vEN\u001c'/\u001b9uS>tw+\u001b;i\u0007\u0006t7-\u001a7Fq\u000e,\u0007\u000f^5p]*\u0011q\u0004I\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0005\nA!Y6lC\u000e\u00011c\u0001\u0001%YA\u0011QEK\u0007\u0002M)\u0011q\u0005K\u0001\u0005Y\u0006twMC\u0001*\u0003\u0011Q\u0017M^1\n\u0005-2#AB(cU\u0016\u001cG\u000f\u0005\u0002.e5\taF\u0003\u00020a\u0005y!/Z1di&4Xm\u001d;sK\u0006l7OC\u00012\u0003\ry'oZ\u0005\u0003g9\u0012AbU;cg\u000e\u0014\u0018\u000e\u001d;j_:\fa\u0001J5oSR$C#\u0001\u001c\u0011\u0005]RT\"\u0001\u001d\u000b\u0003e\nQa]2bY\u0006L!a\u000f\u001d\u0003\tUs\u0017\u000e^\u0001\u0007G\u0006t7-\u001a7\u0015\u0005Yr\u0004\"B \u0004\u0001\u0004\u0001\u0015!B2bkN,\u0007CA!J\u001d\t\u0011uI\u0004\u0002D\r6\tAI\u0003\u0002FE\u00051AH]8pizJ\u0011!O\u0005\u0003\u0011b\nq\u0001]1dW\u0006<W-\u0003\u0002K\u0017\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003\u0011bB#\u0001A'\u0011\u00059\u000bV\"A(\u000b\u0005A\u0003\u0013AC1o]>$\u0018\r^5p]&\u0011!k\u0014\u0002\r\t>tu\u000e^%oQ\u0016\u0014\u0018\u000e^\u0001 'V\u00147o\u0019:jaRLwN\\,ji\"\u001c\u0015M\\2fY\u0016C8-\u001a9uS>t\u0007CA+\u0006\u001b\u0005q2CA\u0003X!\t9\u0004,\u0003\u0002Zq\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001+\u0003-9{gNR1jYV\u0014XmQ1oG\u0016dG.\u0019;j_:\u001c2a\u00020b!\t\tu,\u0003\u0002a\u0017\n\u0001\"+\u001e8uS6,W\t_2faRLwN\u001c\t\u0003E\u001el\u0011a\u0019\u0006\u0003I\u0016\fqaY8oiJ|GN\u0003\u0002gq\u0005!Q\u000f^5m\u0013\tA7M\u0001\u0007O_N#\u0018mY6Ue\u0006\u001cW\rF\u0001k!\tYw!D\u0001\u0006S\r9!\u0002\u0006\u0002\u0015\u001d>luN]3FY\u0016lWM\u001c;t\u001d\u0016,G-\u001a3\u0014\t)QwN\u001d\t\u0003oAL!!\u001d\u001d\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0011i]\u0005\u0003i.\u0013AbU3sS\u0006d\u0017N_1cY\u0016$\u0012A\u001e\t\u0003W*\tQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DX#A=\u0011\u0005\u0015R\u0018BA>'\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\ta\u0010\u0005\u00028\u007f&\u0019\u0011\u0011\u0001\u001d\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005\u001d\u0011Q\u0002\t\u0004o\u0005%\u0011bAA\u0006q\t\u0019\u0011I\\=\t\u0011\u0005=a\"!AA\u0002y\f1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\u000b!\u0019\t9\"!\b\u0002\b5\u0011\u0011\u0011\u0004\u0006\u0004\u00037A\u0014AC2pY2,7\r^5p]&!\u0011qDA\r\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\u0015\u00121\u0006\t\u0004o\u0005\u001d\u0012bAA\u0015q\t9!i\\8mK\u0006t\u0007\"CA\b!\u0005\u0005\t\u0019AA\u0004\u0003!A\u0017m\u001d5D_\u0012,G#\u0001@\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003\u0011\u0012\u0011c\u0015;bO\u0016<\u0016m]\"p[BdW\r^3e'\u0011!\"n\u001c:\u0015\u0005\u0005m\u0002CA6\u0015)\u0011\t9!a\u0010\t\u0011\u0005=\u0001$!AA\u0002y$B!!\n\u0002D!I\u0011q\u0002\u000e\u0002\u0002\u0003\u0007\u0011q\u0001\u0015\u0003\u000f5\u000bACT8N_J,W\t\\3nK:$8OT3fI\u0016$\u0017!E*uC\u001e,w+Y:D_6\u0004H.\u001a;fI\u0002")
@DoNotInherit
public interface SubscriptionWithCancelException
extends Subscription {
    public static /* synthetic */ void cancel$(SubscriptionWithCancelException $this) {
        $this.cancel();
    }

    @Override
    default public void cancel() {
        this.cancel(SubscriptionWithCancelException$NoMoreElementsNeeded$.MODULE$);
    }

    public void cancel(Throwable var1);

    public static void $init$(SubscriptionWithCancelException $this) {
    }

    @DoNotInherit
    public static abstract class NonFailureCancellation
    extends RuntimeException
    implements NoStackTrace {
        @Override
        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        @Override
        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }

        public NonFailureCancellation() {
            NoStackTrace.$init$(this);
        }
    }
}

