/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.Done;
import akka.dispatch.ExecutionContexts$;
import akka.stream.BidiShape;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.KillSwitches$;
import akka.stream.Shape;
import akka.stream.SharedKillSwitch;
import akka.stream.UniqueKillSwitch;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Some;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005-u!B\u0010!\u0011\u0003)c!B\u0014!\u0011\u0003A\u0003\"B\u0018\u0002\t\u0003\u0001\u0004\"B\u0019\u0002\t\u0003\u0011\u0004\"B\"\u0002\t\u0003!\u0005\"\u0002.\u0002\t\u0003Yf!B4\u0002\u0003\u0003A\u0007\u0002C8\u0007\u0005\u000b\u0007I\u0011\u00019\t\u0011m4!\u0011!Q\u0001\nED\u0001\u0002 \u0004\u0003\u0002\u0003\u0006I! \u0005\u0007_\u0019!\t!!\u0001\t\u000f\u0005-a\u0001\"\u0011\u0002\u000e!9\u0011Q\u0003\u0004\u0005\n\u0005]q\u0001CA\u0015\u0003!\u0005\u0001%a\u000b\u0007\u0011\u00055\u0012\u0001#\u0001!\u0003_Aaa\f\b\u0005\u0002\u0005e\u0002\"CA\u001e\u001d\t\u0007I\u0011IA\u001f\u0011!\t)E\u0004Q\u0001\n\u0005}\u0002\"CA$\u001d\t\u0007I\u0011IA%\u0011!\tYE\u0004Q\u0001\n\u0005]\u0002bBA'\u001d\u0011\u0005\u0013q\n\u0005\b\u0003#rA\u0011IA*\u000f!\t)(\u0001E\u0001A\u0005]d\u0001CA=\u0003!\u0005\u0001%a\u001f\t\r=:B\u0011AAA\u0011%\tYd\u0006b\u0001\n\u0003\ni\u0004\u0003\u0005\u0002F]\u0001\u000b\u0011BA \u0011%\t9e\u0006b\u0001\n\u0003\n\u0019\t\u0003\u0005\u0002L]\u0001\u000b\u0011BA@\u0011\u001d\tie\u0006C!\u0003\u001fBq!!\u0015\u0018\t\u0003\n))\u0001\u0007LS2d7k^5uG\",7O\u0003\u0002\"E\u000511\u000f\u001e:fC6T\u0011aI\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005\u0019\nQ\"\u0001\u0011\u0003\u0019-KG\u000e\\*xSR\u001c\u0007.Z:\u0014\u0005\u0005I\u0003C\u0001\u0016.\u001b\u0005Y#\"\u0001\u0017\u0002\u000bM\u001c\u0017\r\\1\n\u00059Z#AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002K\u000511\u000f[1sK\u0012$\"a\r\u001c\u0011\u0005\u0019\"\u0014BA\u001b!\u0005A\u0019\u0006.\u0019:fI.KG\u000e\\*xSR\u001c\u0007\u000eC\u00038\u0007\u0001\u0007\u0001(\u0001\u0003oC6,\u0007CA\u001dA\u001d\tQd\b\u0005\u0002<W5\tAH\u0003\u0002>I\u00051AH]8pizJ!aP\u0016\u0002\rA\u0013X\rZ3g\u0013\t\t%I\u0001\u0004TiJLgn\u001a\u0006\u0003\u007f-\naa]5oO2,WCA#O+\u00051\u0005\u0003\u0002\u0014H\u0013^K!\u0001\u0013\u0011\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\t\u0019RE\nT\u0005\u0003\u0017\u0002\u0012\u0011B\u00127poNC\u0017\r]3\u0011\u00055sE\u0002\u0001\u0003\u0006\u001f\u0012\u0011\r\u0001\u0015\u0002\u0002)F\u0011\u0011\u000b\u0016\t\u0003UIK!aU\u0016\u0003\u000f9{G\u000f[5oOB\u0011!&V\u0005\u0003-.\u00121!\u00118z!\t1\u0003,\u0003\u0002ZA\t\u0001RK\\5rk\u0016\\\u0015\u000e\u001c7To&$8\r[\u0001\u000bg&tw\r\\3CS\u0012LWc\u0001/cKV\tQ\f\u0005\u0003'\u000fz;\u0006C\u0002\u0014`C\u0006$G-\u0003\u0002aA\tI!)\u001b3j'\"\f\u0007/\u001a\t\u0003\u001b\n$QaY\u0003C\u0002A\u0013!\u0001V\u0019\u0011\u00055+G!\u00024\u0006\u0005\u0004\u0001&A\u0001+3\u0005]Y\u0015\u000e\u001c7bE2,wI]1qQN#\u0018mZ3M_\u001eL7m\u0005\u0002\u0007SB\u0011!.\\\u0007\u0002W*\u0011A\u000eI\u0001\u0006gR\fw-Z\u0005\u0003].\u0014qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017nY\u0001\u0012i\u0016\u0014X.\u001b8bi&|gnU5h]\u0006dW#A9\u0011\u0007I,x/D\u0001t\u0015\t!8&\u0001\u0006d_:\u001cWO\u001d:f]RL!A^:\u0003\r\u0019+H/\u001e:f!\tA\u00180D\u0001#\u0013\tQ(E\u0001\u0003E_:,\u0017A\u0005;fe6Lg.\u0019;j_:\u001c\u0016n\u001a8bY\u0002\naaX:iCB,\u0007C\u0001\u0014\u007f\u0013\ty\bEA\u0003TQ\u0006\u0004X\r\u0006\u0004\u0002\u0004\u0005\u001d\u0011\u0011\u0002\t\u0004\u0003\u000b1Q\"A\u0001\t\u000b=T\u0001\u0019A9\t\u000bqT\u0001\u0019A?\u0002\u0011A\u0014Xm\u0015;beR$\"!a\u0004\u0011\u0007)\n\t\"C\u0002\u0002\u0014-\u0012A!\u00168ji\u0006AqN\\*xSR\u001c\u0007\u000e\u0006\u0003\u0002\u0010\u0005e\u0001bBA\u000e\u0019\u0001\u0007\u0011QD\u0001\u0005[>$W\rE\u0003\u0002 \u0005\u0015r/\u0004\u0002\u0002\")\u0019\u00111E\u0016\u0002\tU$\u0018\u000e\\\u0005\u0005\u0003O\t\tCA\u0002Uef\fQ#\u00168jcV,7*\u001b7m'^LGo\u00195Ti\u0006<W\rE\u0002\u0002\u00069\u0011Q#\u00168jcV,7*\u001b7m'^LGo\u00195Ti\u0006<WmE\u0002\u000f\u0003c\u0001bA[A\u001a\u0003o9\u0016bAA\u001bW\nyrI]1qQN#\u0018mZ3XSRDW*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0011\t\u0019RE\u000b\u0016\u000b\u0003\u0003W\t\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\t\ty\u0004E\u0002'\u0003\u0003J1!a\u0011!\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0013S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001c\b%A\u0003tQ\u0006\u0004X-\u0006\u0002\u00028\u000511\u000f[1qK\u0002\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002q\u0005y2M]3bi\u0016dunZ5d\u0003:$W*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0015\t\u0005U\u0013\u0011\u000f\t\u0007U\u0005]\u00131L,\n\u0007\u0005e3F\u0001\u0004UkBdWM\r\n\t\u0003;\n\u0019!!\u001a\u0002l\u00199\u0011qLA1\u0001\u0005m#\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004BBA2+\u0001\tY&A\u0003m_\u001eL7\rE\u0002k\u0003OJ1!!\u001bl\u0005%Ie\u000eS1oI2,'\u000fE\u0002k\u0003[J1!a\u001cl\u0005)yU\u000f\u001e%b]\u0012dWM\u001d\u0005\b\u0003g*\u0002\u0019AA \u0003\u0011\tG\u000f\u001e:\u00023Us\u0017.];f\u0005&$\u0017nS5mYN;\u0018\u000e^2i'R\fw-\u001a\t\u0004\u0003\u000b9\"!G+oSF,XMQ5eS.KG\u000e\\*xSR\u001c\u0007n\u0015;bO\u0016\u001c2aFA?!\u0019Q\u00171GA@/B1ae\u0018+U)R#\"!a\u001e\u0016\u0005\u0005}D\u0003BAD\u0003\u0013\u0003bAKA,\u0003\u00079\u0006bBA:=\u0001\u0007\u0011q\b")
public final class KillSwitches {
    public static <T1, T2> Graph<BidiShape<T1, T1, T2, T2>, UniqueKillSwitch> singleBidi() {
        return KillSwitches$.MODULE$.singleBidi();
    }

    public static <T> Graph<FlowShape<T, T>, UniqueKillSwitch> single() {
        return KillSwitches$.MODULE$.single();
    }

    public static SharedKillSwitch shared(String name) {
        return KillSwitches$.MODULE$.shared(name);
    }

    public static abstract class KillableGraphStageLogic
    extends GraphStageLogic {
        private final Future<Done> terminationSignal;

        public Future<Done> terminationSignal() {
            return this.terminationSignal;
        }

        @Override
        public void preStart() {
            Option<Try<Done>> option = this.terminationSignal().value();
            if (option instanceof Some) {
                Some some = (Some)option;
                Try status = (Try)some.value();
                this.onSwitch(status);
                return;
            }
            AsyncCallback asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)mode -> {
                this.onSwitch(mode);
                return BoxedUnit.UNIT;
            });
            this.terminationSignal().onComplete((Function1<Try, Object> & Serializable)t2 -> {
                asyncCallback.invoke(t2);
                return BoxedUnit.UNIT;
            }, ExecutionContexts$.MODULE$.parasitic());
        }

        private void onSwitch(Try<Done> mode) {
            Try<Done> try_ = mode;
            if (try_ instanceof Success) {
                this.completeStage();
                return;
            }
            if (try_ instanceof Failure) {
                Failure failure = (Failure)try_;
                Throwable ex = failure.exception();
                this.failStage(ex);
                return;
            }
            throw new MatchError(try_);
        }

        public KillableGraphStageLogic(Future<Done> terminationSignal, Shape _shape) {
            this.terminationSignal = terminationSignal;
            super(_shape);
        }
    }
}

