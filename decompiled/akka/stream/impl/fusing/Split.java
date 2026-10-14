/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SubstreamCancelStrategies$Drain$;
import akka.stream.SubstreamCancelStrategies$Propagate$;
import akka.stream.SubstreamCancelStrategy;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Split$;
import akka.stream.impl.fusing.Split$SplitAfter$;
import akka.stream.impl.fusing.Split$SplitBefore$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.TimerGraphStageLogic;
import scala.Function1;
import scala.MatchError;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t\rrA\u0002\u00192\u0011\u00039\u0014H\u0002\u0004<c!\u0005q\u0007\u0010\u0005\u0006\u0007\u0006!\t!\u0012\u0004\u0006\r\u0006\t\tc\u0012\u0005\u0006\u0007\u000e!\t\u0001S\u0004\b\u0003S\t\u0001\u0012QA\u0010\r\u001d\tI\"\u0001EA\u00037Aaa\u0011\u0004\u0005\u0002\u0005u\u0001bB0\u0007\u0003\u0003%\t\u0005\u0019\u0005\bS\u001a\t\t\u0011\"\u0001k\u0011!qg!!A\u0005\u0002\u0005\u0005\u0002bB;\u0007\u0003\u0003%\tE\u001e\u0005\t{\u001a\t\t\u0011\"\u0001\u0002&!I\u0011q\u0001\u0004\u0002\u0002\u0013\u0005\u0013\u0011\u0002\u0005\n\u0003\u00171\u0011\u0011!C!\u0003\u001bA\u0011\"a\u0004\u0007\u0003\u0003%I!!\u0005\b\r\u0005-\u0012\u0001#!_\r\u0015a\u0015\u0001#!N\u0011\u0015\u0019\u0015\u0003\"\u0001^\u0011\u001dy\u0016#!A\u0005B\u0001Dq![\t\u0002\u0002\u0013\u0005!\u000eC\u0004o#\u0005\u0005I\u0011A8\t\u000fU\f\u0012\u0011!C!m\"9Q0EA\u0001\n\u0003q\b\"CA\u0004#\u0005\u0005I\u0011IA\u0005\u0011%\tY!EA\u0001\n\u0003\ni\u0001C\u0005\u0002\u0010E\t\t\u0011\"\u0003\u0002\u0012!9\u0011QF\u0001\u0005\u0002\u0005=\u0002bBA=\u0003\u0011\u0005\u00111\u0010\u0004\u0007wE\u0012q'a(\t\u0015\u0005]VD!b\u0001\n\u0003\tI\f\u0003\u0006\u0002@v\u0011\t\u0011)A\u0005\u0003wC!\"a\u001a\u001e\u0005\u000b\u0007I\u0011AAa\u0011)\t)-\bB\u0001B\u0003%\u00111\u0019\u0005\u000b\u0003cj\"Q1A\u0005\u0002\u0005\u001d\u0007BCAe;\t\u0005\t\u0015!\u0003\u0002t!11)\bC\u0001\u0003\u0017D\u0011\"!6\u001e\u0005\u0004%\t!a6\t\u0011\u0005}W\u0004)A\u0005\u00033D\u0011\"!9\u001e\u0005\u0004%\t!a9\t\u0011\u0005-X\u0004)A\u0005\u0003KD\u0011\"!<\u001e\u0005\u0004%\t%a<\t\u0011\u0005EX\u0004)A\u0005\u0003_C\u0011\"a=\u001e\u0005\u0004%I!!>\t\u000f\u0005]X\u0004)A\u0005\u007f\"9\u0011\u0011`\u000f\u0005R\u0005m\bb\u0002B\u0002;\u0011\u0005#Q\u0001\u0005\b\u0003\u0017iB\u0011\tB\t\u0003\u0015\u0019\u0006\u000f\\5u\u0015\t\u00114'\u0001\u0004gkNLgn\u001a\u0006\u0003iU\nA![7qY*\u0011agN\u0001\u0007gR\u0014X-Y7\u000b\u0003a\nA!Y6lCB\u0011!(A\u0007\u0002c\t)1\u000b\u001d7jiN\u0011\u0011!\u0010\t\u0003}\u0005k\u0011a\u0010\u0006\u0002\u0001\u0006)1oY1mC&\u0011!i\u0010\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012!\u000f\u0002\u000e'Bd\u0017\u000e\u001e#fG&\u001c\u0018n\u001c8\u0014\u0005\riD#A%\u0011\u0005)\u001bQ\"A\u0001*\u0007\r\tbA\u0001\u0006Ta2LG/\u00114uKJ\u001cB!E%O#B\u0011ahT\u0005\u0003!~\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002S5:\u00111\u000b\u0017\b\u0003)^k\u0011!\u0016\u0006\u0003-\u0012\u000ba\u0001\u0010:p_Rt\u0014\"\u0001!\n\u0005e{\u0014a\u00029bG.\fw-Z\u0005\u00037r\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!!W \u0015\u0003y\u0003\"AS\t\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\u0005\t\u0007C\u00012h\u001b\u0005\u0019'B\u00013f\u0003\u0011a\u0017M\\4\u000b\u0003\u0019\fAA[1wC&\u0011\u0001n\u0019\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0003-\u0004\"A\u00107\n\u00055|$aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$HC\u00019t!\tq\u0014/\u0003\u0002s\u007f\t\u0019\u0011I\\=\t\u000fQ,\u0012\u0011!a\u0001W\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\u0012a\u001e\t\u0004qn\u0004X\"A=\u000b\u0005i|\u0014AC2pY2,7\r^5p]&\u0011A0\u001f\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000eF\u0002\u0000\u0003\u000b\u00012APA\u0001\u0013\r\t\u0019a\u0010\u0002\b\u0005>|G.Z1o\u0011\u001d!x#!AA\u0002A\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002W\u0006AAo\\*ue&tw\rF\u0001b\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\t\u0019\u0002E\u0002c\u0003+I1!a\u0006d\u0005\u0019y%M[3di\nY1\u000b\u001d7ji\n+gm\u001c:f'\u00111\u0011JT)\u0015\u0005\u0005}\u0001C\u0001&\u0007)\r\u0001\u00181\u0005\u0005\bi*\t\t\u00111\u0001l)\ry\u0018q\u0005\u0005\bi2\t\t\u00111\u0001q\u0003-\u0019\u0006\u000f\\5u\u0005\u00164wN]3\u0002\u0015M\u0003H.\u001b;BMR,'/\u0001\u0003xQ\u0016tW\u0003BA\u0019\u0003\u000b\"b!a\r\u0002f\u0005=\u0004\u0003CA\u001b\u0003o\tY$!\u0018\u000e\u0003UJ1!!\u000f6\u0005\u00159%/\u00199i!!\t)$!\u0010\u0002B\u0005E\u0013bAA k\tIa\t\\8x'\"\f\u0007/\u001a\t\u0005\u0003\u0007\n)\u0005\u0004\u0001\u0005\u000f\u0005\u001d3D1\u0001\u0002J\t\tA+E\u0002\u0002LA\u00042APA'\u0013\r\tye\u0010\u0002\b\u001d>$\b.\u001b8h!!\t\u0019&!\u0017\u0002B\u0005uSBAA+\u0015\r\t9&N\u0001\tg\u000e\fG.\u00193tY&!\u00111LA+\u0005\u0019\u0019v.\u001e:dKB!\u0011qLA1\u001b\u00059\u0014bAA2o\t9aj\u001c;Vg\u0016$\u0007bBA47\u0001\u0007\u0011\u0011N\u0001\u0002aB1a(a\u001b\u0002B}L1!!\u001c@\u0005%1UO\\2uS>t\u0017\u0007C\u0004\u0002rm\u0001\r!a\u001d\u0002/M,(m\u001d;sK\u0006l7)\u00198dK2\u001cFO]1uK\u001eL\b\u0003BA\u001b\u0003kJ1!a\u001e6\u0005]\u0019VOY:ue\u0016\fWnQ1oG\u0016d7\u000b\u001e:bi\u0016<\u00170A\u0003bMR,'/\u0006\u0003\u0002~\u0005\u0015ECBA@\u0003\u0013\u000bi\t\u0005\u0005\u00026\u0005]\u0012\u0011QA/!!\t)$!\u0010\u0002\u0004\u0006\u001d\u0005\u0003BA\"\u0003\u000b#q!a\u0012\u001d\u0005\u0004\tI\u0005\u0005\u0005\u0002T\u0005e\u00131QA/\u0011\u001d\t9\u0007\ba\u0001\u0003\u0017\u0003bAPA6\u0003\u0007{\bbBA99\u0001\u0007\u00111\u000f\u0015\u0004\u0003\u0005E\u0005\u0003BAJ\u00033k!!!&\u000b\u0007\u0005]u'\u0001\u0006b]:|G/\u0019;j_:LA!a'\u0002\u0016\nY\u0011J\u001c;fe:\fG.\u00119jQ\r\u0001\u0011\u0011S\u000b\u0005\u0003C\u000b\u0019lE\u0002\u001e\u0003G\u0003b!!*\u0002,\u0006=VBAAT\u0015\r\tI+N\u0001\u0006gR\fw-Z\u0005\u0005\u0003[\u000b9K\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004\u0002\"!\u000e\u0002>\u0005E\u0016Q\u0017\t\u0005\u0003\u0007\n\u0019\fB\u0004\u0002Hu\u0011\r!!\u0013\u0011\u0011\u0005M\u0013\u0011LAY\u0003;\n\u0001\u0002Z3dSNLwN\\\u000b\u0003\u0003w\u00032!!0\u0004\u001d\tQ\u0004!A\u0005eK\u000eL7/[8oAU\u0011\u00111\u0019\t\u0007}\u0005-\u0014\u0011W@\u0002\u0005A\u0004SCAA:\u0003a\u0019XOY:ue\u0016\fWnQ1oG\u0016d7\u000b\u001e:bi\u0016<\u0017\u0010\t\u000b\t\u0003\u001b\fy-!5\u0002TB!!(HAY\u0011\u001d\t9\f\na\u0001\u0003wCq!a\u001a%\u0001\u0004\t\u0019\rC\u0004\u0002r\u0011\u0002\r!a\u001d\u0002\u0005%tWCAAm!\u0019\t)$a7\u00022&\u0019\u0011Q\\\u001b\u0003\u000b%sG.\u001a;\u0002\u0007%t\u0007%A\u0002pkR,\"!!:\u0011\r\u0005U\u0012q]A[\u0013\r\tI/\u000e\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0006g\"\f\u0007/Z\u000b\u0003\u0003_\u000baa\u001d5ba\u0016\u0004\u0013\u0001\u00079s_B\fw-\u0019;f'V\u00147\u000f\u001e:fC6\u001c\u0015M\\2fYV\tq0A\rqe>\u0004\u0018mZ1uKN+(m\u001d;sK\u0006l7)\u00198dK2\u0004\u0013!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\u0011\u0011Q \t\u0005\u0003k\ty0C\u0002\u0003\u0002U\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003-\u0019'/Z1uK2{w-[2\u0015\t\t\u001d!Q\u0002\t\u0005\u0003K\u0013I!\u0003\u0003\u0003\f\u0005\u001d&aD$sCBD7\u000b^1hK2{w-[2\t\u000f\t=a\u00061\u0001\u0002~\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fgR\u0011!1\u0003\t\u0005\u0005+\u0011iB\u0004\u0003\u0003\u0018\te\u0001C\u0001+@\u0013\r\u0011YbP\u0001\u0007!J,G-\u001a4\n\u0007!\u0014yBC\u0002\u0003\u001c}B3!HAI\u0001")
@InternalApi
public final class Split<T>
extends GraphStage<FlowShape<T, Source<T, NotUsed>>> {
    private final SplitDecision decision;
    private final Function1<T, Object> p;
    private final SubstreamCancelStrategy substreamCancelStrategy;
    private final Inlet<T> in;
    private final Outlet<Source<T, NotUsed>> out;
    private final FlowShape<T, Source<T, NotUsed>> shape;
    private final boolean akka$stream$impl$fusing$Split$$propagateSubstreamCancel;

    public static <T> Graph<FlowShape<T, Source<T, NotUsed>>, NotUsed> after(Function1<T, Object> p, SubstreamCancelStrategy substreamCancelStrategy) {
        return Split$.MODULE$.after(p, substreamCancelStrategy);
    }

    public static <T> Graph<FlowShape<T, Source<T, NotUsed>>, NotUsed> when(Function1<T, Object> p, SubstreamCancelStrategy substreamCancelStrategy) {
        return Split$.MODULE$.when(p, substreamCancelStrategy);
    }

    public SplitDecision decision() {
        return this.decision;
    }

    public Function1<T, Object> p() {
        return this.p;
    }

    public SubstreamCancelStrategy substreamCancelStrategy() {
        return this.substreamCancelStrategy;
    }

    public Inlet<T> in() {
        return this.in;
    }

    public Outlet<Source<T, NotUsed>> out() {
        return this.out;
    }

    @Override
    public FlowShape<T, Source<T, NotUsed>> shape() {
        return this.shape;
    }

    public boolean akka$stream$impl$fusing$Split$$propagateSubstreamCancel() {
        return this.akka$stream$impl$fusing$Split$$propagateSubstreamCancel;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.split().and(Attributes$SourceLocation$.MODULE$.forLambda(this.p()));
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new TimerGraphStageLogic(this, inheritedAttributes){
            private final String akka$stream$impl$fusing$Split$$anon$$SubscriptionTimer;
            private final FiniteDuration timeout;
            private GraphStageLogic.SubSourceOutlet<T> akka$stream$impl$fusing$Split$$anon$$substreamSource;
            private boolean akka$stream$impl$fusing$Split$$anon$$substreamWaitingToBePushed;
            private boolean akka$stream$impl$fusing$Split$$anon$$substreamCancelled;
            private final InHandler akka$stream$impl$fusing$Split$$anon$$initInHandler;
            private final /* synthetic */ Split $outer;

            public String akka$stream$impl$fusing$Split$$anon$$SubscriptionTimer() {
                return this.akka$stream$impl$fusing$Split$$anon$$SubscriptionTimer;
            }

            private FiniteDuration timeout() {
                return this.timeout;
            }

            public GraphStageLogic.SubSourceOutlet<T> akka$stream$impl$fusing$Split$$anon$$substreamSource() {
                return this.akka$stream$impl$fusing$Split$$anon$$substreamSource;
            }

            public void akka$stream$impl$fusing$Split$$anon$$substreamSource_$eq(GraphStageLogic.SubSourceOutlet<T> x$1) {
                this.akka$stream$impl$fusing$Split$$anon$$substreamSource = x$1;
            }

            public boolean akka$stream$impl$fusing$Split$$anon$$substreamWaitingToBePushed() {
                return this.akka$stream$impl$fusing$Split$$anon$$substreamWaitingToBePushed;
            }

            private void substreamWaitingToBePushed_$eq(boolean x$1) {
                this.akka$stream$impl$fusing$Split$$anon$$substreamWaitingToBePushed = x$1;
            }

            public boolean akka$stream$impl$fusing$Split$$anon$$substreamCancelled() {
                return this.akka$stream$impl$fusing$Split$$anon$$substreamCancelled;
            }

            public void akka$stream$impl$fusing$Split$$anon$$substreamCancelled_$eq(boolean x$1) {
                this.akka$stream$impl$fusing$Split$$anon$$substreamCancelled = x$1;
            }

            public InHandler akka$stream$impl$fusing$Split$$anon$$initInHandler() {
                return this.akka$stream$impl$fusing$Split$$anon$$initInHandler;
            }

            public void akka$stream$impl$fusing$Split$$anon$$handOver($anon$7$SubstreamHandler handler) {
                if (this.isClosed(this.$outer.out())) {
                    this.completeStage();
                    return;
                }
                this.akka$stream$impl$fusing$Split$$anon$$substreamSource_$eq(new GraphStageLogic.SubSourceOutlet<T>(this, "SplitSource"));
                this.akka$stream$impl$fusing$Split$$anon$$substreamSource().setHandler(handler);
                this.akka$stream$impl$fusing$Split$$anon$$substreamCancelled_$eq(false);
                this.setHandler(this.$outer.in(), handler);
                this.setKeepGoing(handler.hasInitialElement());
                if (this.isAvailable(this.$outer.out())) {
                    SplitDecision splitDecision = this.$outer.decision();
                    Split$SplitBefore$ split$SplitBefore$ = Split$SplitBefore$.MODULE$;
                    if (!(splitDecision == null ? split$SplitBefore$ != null : !splitDecision.equals(split$SplitBefore$)) || handler.hasInitialElement()) {
                        this.akka$stream$impl$fusing$Split$$anon$$pushSubstreamSource();
                        return;
                    }
                    this.pull(this.$outer.in());
                    return;
                }
                this.substreamWaitingToBePushed_$eq(true);
            }

            public void akka$stream$impl$fusing$Split$$anon$$pushSubstreamSource() {
                this.push(this.$outer.out(), Source$.MODULE$.fromGraph(this.akka$stream$impl$fusing$Split$$anon$$substreamSource().source()));
                this.scheduleOnce((Object)this.akka$stream$impl$fusing$Split$$anon$$SubscriptionTimer(), this.timeout());
                this.substreamWaitingToBePushed_$eq(false);
            }

            public void onTimer(Object timerKey) {
                this.akka$stream$impl$fusing$Split$$anon$$substreamSource().timeout(this.timeout());
            }

            public /* synthetic */ Split akka$stream$impl$fusing$Split$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$impl$fusing$Split$$anon$$SubscriptionTimer = "SubstreamSubscriptionTimer";
                this.timeout = ((ActorAttributes.StreamSubscriptionTimeout)inheritedAttributes$2.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.StreamSubscriptionTimeout.class))).timeout();
                this.akka$stream$impl$fusing$Split$$anon$$substreamSource = null;
                this.akka$stream$impl$fusing$Split$$anon$$substreamWaitingToBePushed = false;
                this.akka$stream$impl$fusing$Split$$anon$$substreamCancelled = false;
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$7 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$impl$fusing$Split$$anon$$substreamSource() == null) {
                            if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$impl$fusing$Split$$anon$$$outer().in())) {
                                this.$outer.pull(this.$outer.akka$stream$impl$fusing$Split$$anon$$$outer().in());
                                return;
                            }
                            return;
                        }
                        if (this.$outer.akka$stream$impl$fusing$Split$$anon$$substreamWaitingToBePushed()) {
                            this.$outer.akka$stream$impl$fusing$Split$$anon$$pushSubstreamSource();
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        if (this.$outer.akka$stream$impl$fusing$Split$$anon$$substreamSource() == null || this.$outer.akka$stream$impl$fusing$Split$$anon$$substreamWaitingToBePushed() || this.$outer.akka$stream$impl$fusing$Split$$anon$$substreamCancelled()) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        OutHandler.$init$(this);
                    }
                });
                this.akka$stream$impl$fusing$Split$$anon$$initInHandler = new InHandler(this){
                    private final /* synthetic */ $anon$7 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        $anon$7$SubstreamHandler handler = new $anon$7$SubstreamHandler(this.$outer);
                        T elem = this.$outer.grab(this.$outer.akka$stream$impl$fusing$Split$$anon$$$outer().in());
                        SplitDecision splitDecision = this.$outer.akka$stream$impl$fusing$Split$$anon$$$outer().decision();
                        if (Split$SplitAfter$.MODULE$.equals(splitDecision) && BoxesRunTime.unboxToBoolean(this.$outer.akka$stream$impl$fusing$Split$$anon$$$outer().p().apply(elem))) {
                            this.$outer.push(this.$outer.akka$stream$impl$fusing$Split$$anon$$$outer().out(), Source$.MODULE$.single(elem));
                        } else {
                            handler.firstElem_$eq(elem);
                        }
                        this.$outer.akka$stream$impl$fusing$Split$$anon$$handOver(handler);
                    }

                    public void onUpstreamFinish() {
                        this.$outer.completeStage();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                };
                this.setHandler($outer.in(), this.akka$stream$impl$fusing$Split$$anon$$initInHandler());
            }
        };
    }

    public String toString() {
        return "Split";
    }

    public Split(SplitDecision decision, Function1<T, Object> p, SubstreamCancelStrategy substreamCancelStrategy) {
        boolean bl;
        this.decision = decision;
        this.p = p;
        this.substreamCancelStrategy = substreamCancelStrategy;
        this.in = Inlet$.MODULE$.apply("Split.in");
        this.out = Outlet$.MODULE$.apply("Split.out");
        this.shape = new FlowShape<T, Source<T, NotUsed>>(this.in(), this.out());
        SubstreamCancelStrategy substreamCancelStrategy2 = substreamCancelStrategy;
        if (SubstreamCancelStrategies$Propagate$.MODULE$.equals(substreamCancelStrategy2)) {
            bl = true;
        } else if (SubstreamCancelStrategies$Drain$.MODULE$.equals(substreamCancelStrategy2)) {
            bl = false;
        } else {
            throw new MatchError(substreamCancelStrategy2);
        }
        this.akka$stream$impl$fusing$Split$$propagateSubstreamCancel = bl;
    }

    public static abstract class SplitDecision {
    }
}

