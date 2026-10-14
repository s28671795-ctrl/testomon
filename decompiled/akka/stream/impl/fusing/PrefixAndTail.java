/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.NotUsed;
import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$CancelTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$NoopTermination$;
import akka.stream.StreamSubscriptionTimeoutTerminationMode$WarnTermination$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.PrefixAndTail$PrefixAndTailLogic$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.stream.stage.TimerGraphStageLogic;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.collection.mutable.ReusableBuilder;
import scala.concurrent.duration.FiniteDuration;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t\u0015a!B\u0013'\u00051r\u0003\u0002\u00030\u0001\u0005\u000b\u0007I\u0011A0\t\u0011\r\u0004!\u0011!Q\u0001\n\u0001DQ\u0001\u001a\u0001\u0005\u0002\u0015Dq!\u001b\u0001C\u0002\u0013\u0005!\u000e\u0003\u0004o\u0001\u0001\u0006Ia\u001b\u0005\b_\u0002\u0011\r\u0011\"\u0001q\u0011\u0019!\b\u0001)A\u0005c\"9Q\u000f\u0001b\u0001\n\u00032\bBB<\u0001A\u0003%a\u0007C\u0003y\u0001\u0011\u0005\u0013P\u0002\u0003~\u0001\u0019q\bBCA\t\u0017\t\u0005\t\u0015!\u0003\u0002\u0014!I\u0011qD\u0006\u0003\u0002\u0003\u0006IA\u001f\u0005\u0007I.!\t!!\t\t\u0011\u0005%2\u00021A\u0005\n}C\u0011\"a\u000b\f\u0001\u0004%I!!\f\t\u000f\u0005e2\u0002)Q\u0005A\"I\u00111H\u0006A\u0002\u0013%\u0011Q\b\u0005\n\u0003#Z\u0001\u0019!C\u0005\u0003'B\u0001\"a\u0016\fA\u0003&\u0011q\b\u0005\n\u00033Z\u0001\u0019!C\u0005\u00037B\u0011\"!\u001b\f\u0001\u0004%I!a\u001b\t\u0011\u0005=4\u0002)Q\u0005\u0003;B\u0011\"!\u001d\f\u0005\u0004%I!a\u001d\t\u0011\u0005\u00155\u0002)A\u0005\u0003kBq!a\"\f\t#\nI\tC\u0004\u0002\u0010.!I!!%\t\u000f\u0005e5\u0002\"\u0003\u0002\u001c\"9\u0011\u0011V\u0006\u0005\n\u0005-\u0006bBAW\u0017\u0011\u0005\u0013q\u0016\u0005\b\u0003c[A\u0011IAX\u0011\u001d\t\u0019l\u0003C!\u0003_Cq!!.\f\t\u0003\n9\fC\u0004\u0002V.!\t%a6\t\u000f\u0005u\u0007\u0001\"\u0011\u0002`\"9\u0011Q\u001d\u0001\u0005B\u0005\u001d(!\u0004)sK\u001aL\u00070\u00118e)\u0006LGN\u0003\u0002(Q\u00051a-^:j]\u001eT!!\u000b\u0016\u0002\t%l\u0007\u000f\u001c\u0006\u0003W1\naa\u001d;sK\u0006l'\"A\u0017\u0002\t\u0005\\7.Y\u000b\u0003_q\u001a\"\u0001\u0001\u0019\u0011\u0007E\"d'D\u00013\u0015\t\u0019$&A\u0003ti\u0006<W-\u0003\u00026e\tQqI]1qQN#\u0018mZ3\u0011\t]B$(S\u0007\u0002U%\u0011\u0011H\u000b\u0002\n\r2|wo\u00155ba\u0016\u0004\"a\u000f\u001f\r\u0001\u0011)Q\b\u0001b\u0001\u007f\t\tAk\u0001\u0001\u0012\u0005\u00013\u0005CA!E\u001b\u0005\u0011%\"A\"\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0015\u0013%a\u0002(pi\"Lgn\u001a\t\u0003\u0003\u001eK!\u0001\u0013\"\u0003\u0007\u0005s\u0017\u0010\u0005\u0003B\u00152#\u0016BA&C\u0005\u0019!V\u000f\u001d7feA\u0019QJ\u0015\u001e\u000e\u00039S!a\u0014)\u0002\u0013%lW.\u001e;bE2,'BA)C\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003':\u00131aU3r!\u0011)\u0006L\u000f.\u000e\u0003YS!a\u0016\u0016\u0002\u0011M\u001c\u0017\r\\1eg2L!!\u0017,\u0003\rM{WO]2f!\tYF,D\u0001-\u0013\tiFFA\u0004O_R,6/\u001a3\u0002\u00039,\u0012\u0001\u0019\t\u0003\u0003\u0006L!A\u0019\"\u0003\u0007%sG/\u0001\u0002oA\u00051A(\u001b8jiz\"\"A\u001a5\u0011\u0007\u001d\u0004!(D\u0001'\u0011\u0015q6\u00011\u0001a\u0003\tIg.F\u0001l!\r9DNO\u0005\u0003[*\u0012Q!\u00138mKR\f1!\u001b8!\u0003\ryW\u000f^\u000b\u0002cB\u0019qG]%\n\u0005MT#AB(vi2,G/\u0001\u0003pkR\u0004\u0013!B:iCB,W#\u0001\u001c\u0002\rMD\u0017\r]3!\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002uB\u0011qg_\u0005\u0003y*\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0005I\u0001&/\u001a4jq\u0006sG\rV1jY2{w-[2\u0014\r-y\u0018QAA\u0006!\r\t\u0014\u0011A\u0005\u0004\u0003\u0007\u0011$\u0001\u0006+j[\u0016\u0014xI]1qQN#\u0018mZ3M_\u001eL7\rE\u00022\u0003\u000fI1!!\u00033\u0005)yU\u000f\u001e%b]\u0012dWM\u001d\t\u0004c\u00055\u0011bAA\be\tI\u0011J\u001c%b]\u0012dWM]\u0001\u0007?ND\u0017\r]3\u0011\t\u0005U\u0011qC\u0007\u0002\u0001%!\u0011\u0011DA\u000e\u0005\u0015\u0019\u0006.\u00199f\u0013\r\tiB\u000b\u0002\u0006\u000fJ\f\u0007\u000f[\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\u000b\u0007\u0003G\t)#a\n\u0011\u0007\u0005U1\u0002C\u0004\u0002\u00129\u0001\r!a\u0005\t\r\u0005}a\u00021\u0001{\u0003\u0011aWM\u001a;\u0002\u00111,g\r^0%KF$B!a\f\u00026A\u0019\u0011)!\r\n\u0007\u0005M\"I\u0001\u0003V]&$\b\u0002CA\u001c!\u0005\u0005\t\u0019\u00011\u0002\u0007a$\u0013'A\u0003mK\u001a$\b%A\u0004ck&dG-\u001a:\u0016\u0005\u0005}\u0002cBA!\u0003\u000fR\u00141J\u0007\u0003\u0003\u0007R1!!\u0012Q\u0003\u001diW\u000f^1cY\u0016LA!!\u0013\u0002D\ty!+Z;tC\ndWMQ;jY\u0012,'\u000f\u0005\u0003N\u0003\u001bR\u0014bAA(\u001d\n1a+Z2u_J\f1BY;jY\u0012,'o\u0018\u0013fcR!\u0011qFA+\u0011%\t9dEA\u0001\u0002\u0004\ty$\u0001\u0005ck&dG-\u001a:!\u0003)!\u0018-\u001b7T_V\u00148-Z\u000b\u0003\u0003;\u0002R!a\u0018\u0002bij\u0011aC\u0005\u0005\u0003G\n)GA\bTk\n\u001cv.\u001e:dK>+H\u000f\\3u\u0013\r\t9G\r\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\u0006qA/Y5m'>,(oY3`I\u0015\fH\u0003BA\u0018\u0003[B\u0011\"a\u000e\u0017\u0003\u0003\u0005\r!!\u0018\u0002\u0017Q\f\u0017\u000e\\*pkJ\u001cW\rI\u0001\u0012'V\u00147o\u0019:jaRLwN\u001c+j[\u0016\u0014XCAA;!\u0011\t9(!!\u000e\u0005\u0005e$\u0002BA>\u0003{\nA\u0001\\1oO*\u0011\u0011qP\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002\u0004\u0006e$AB*ue&tw-\u0001\nTk\n\u001c8M]5qi&|g\u000eV5nKJ\u0004\u0013aB8o)&lWM\u001d\u000b\u0005\u0003_\tY\t\u0003\u0004\u0002\u000ej\u0001\rAR\u0001\ti&lWM]&fs\u0006q\u0001O]3gSb\u001cu.\u001c9mKR,WCAAJ!\r\t\u0015QS\u0005\u0004\u0003/\u0013%a\u0002\"p_2,\u0017M\\\u0001\u000bgV\u0014\u0007*\u00198eY\u0016\u0014XCAAO%\u0019\ty*a)\u0002\u0006\u00191\u0011\u0011\u0015\u000f\u0001\u0003;\u0013A\u0002\u0010:fM&tW-\\3oiz\u00022!QAS\u0013\r\t9K\u0011\u0002\u0007\u0003:L(+\u001a4\u0002\u001b=\u0004XM\\*vEN$(/Z1n)\u0005!\u0016AB8o!V\u001c\b\u000e\u0006\u0002\u00020\u00051qN\u001c)vY2\f\u0001c\u001c8VaN$(/Z1n\r&t\u0017n\u001d5\u0002#=tW\u000b]:ue\u0016\fWNR1jYV\u0014X\r\u0006\u0003\u00020\u0005e\u0006bBA^C\u0001\u0007\u0011QX\u0001\u0003Kb\u0004B!a0\u0002P:!\u0011\u0011YAf\u001d\u0011\t\u0019-!3\u000e\u0005\u0005\u0015'bAAd}\u00051AH]8pizJ\u0011aQ\u0005\u0004\u0003\u001b\u0014\u0015a\u00029bG.\fw-Z\u0005\u0005\u0003#\f\u0019NA\u0005UQJ|w/\u00192mK*\u0019\u0011Q\u001a\"\u0002%=tGi\\<ogR\u0014X-Y7GS:L7\u000f\u001b\u000b\u0005\u0003_\tI\u000eC\u0004\u0002\\\n\u0002\r!!0\u0002\u000b\r\fWo]3\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0005\u0003C\f\u0019\u000fE\u00022\u0003KBa!a\b$\u0001\u0004Q\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005%\b\u0003BAv\u0003gtA!!<\u0002pB\u0019\u00111\u0019\"\n\u0007\u0005E()\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003\u0007\u000b)PC\u0002\u0002r\nC3\u0001AA}!\u0011\tYP!\u0001\u000e\u0005\u0005u(bAA\u0000Y\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\t\r\u0011Q \u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public final class PrefixAndTail<T>
extends GraphStage<FlowShape<T, Tuple2<Seq<T>, Source<T, NotUsed>>>> {
    private final int n;
    private final Inlet<T> in;
    private final Outlet<Tuple2<Seq<T>, Source<T, NotUsed>>> out;
    private final FlowShape<T, Tuple2<Seq<T>, Source<T, NotUsed>>> shape;

    public int n() {
        return this.n;
    }

    public Inlet<T> in() {
        return this.in;
    }

    public Outlet<Tuple2<Seq<T>, Source<T, NotUsed>>> out() {
        return this.out;
    }

    @Override
    public FlowShape<T, Tuple2<Seq<T>, Source<T, NotUsed>>> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.prefixAndTail();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new PrefixAndTailLogic(this, this.shape(), inheritedAttributes);
    }

    public String toString() {
        return new StringBuilder(15).append("PrefixAndTail(").append(this.n()).append(")").toString();
    }

    public PrefixAndTail(int n) {
        this.n = n;
        this.in = Inlet$.MODULE$.apply("PrefixAndTail.in");
        this.out = Outlet$.MODULE$.apply("PrefixAndTail.out");
        this.shape = new FlowShape<T, Tuple2<Seq<T>, Source<T, NotUsed>>>(this.in(), this.out());
    }

    private final class PrefixAndTailLogic
    extends TimerGraphStageLogic
    implements OutHandler,
    InHandler {
        private final Attributes inheritedAttributes;
        private int left;
        private ReusableBuilder<T, Vector<T>> builder;
        private GraphStageLogic.SubSourceOutlet<T> akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource;
        private final String akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$SubscriptionTimer;
        private final /* synthetic */ PrefixAndTail $outer;

        @Override
        public void onDownstreamFinish() throws Exception {
            OutHandler.onDownstreamFinish$(this);
        }

        private int left() {
            return this.left;
        }

        private void left_$eq(int x$1) {
            this.left = x$1;
        }

        private ReusableBuilder<T, Vector<T>> builder() {
            return this.builder;
        }

        private void builder_$eq(ReusableBuilder<T, Vector<T>> x$1) {
            this.builder = x$1;
        }

        public GraphStageLogic.SubSourceOutlet<T> akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource() {
            return this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource;
        }

        private void tailSource_$eq(GraphStageLogic.SubSourceOutlet<T> x$1) {
            this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource = x$1;
        }

        public String akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$SubscriptionTimer() {
            return this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$SubscriptionTimer;
        }

        @Override
        public void onTimer(Object timerKey) {
            StreamSubscriptionTimeoutTerminationMode mode;
            Materializer materializer = this.interpreter().materializer();
            ActorAttributes.StreamSubscriptionTimeout streamSubscriptionTimeout = (ActorAttributes.StreamSubscriptionTimeout)this.inheritedAttributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.StreamSubscriptionTimeout.class));
            if (streamSubscriptionTimeout == null) {
                throw new MatchError(streamSubscriptionTimeout);
            }
            FiniteDuration timeout2 = streamSubscriptionTimeout.timeout();
            StreamSubscriptionTimeoutTerminationMode mode2 = streamSubscriptionTimeout.mode();
            Tuple2<FiniteDuration, StreamSubscriptionTimeoutTerminationMode> tuple2 = new Tuple2<FiniteDuration, StreamSubscriptionTimeoutTerminationMode>(timeout2, mode2);
            FiniteDuration timeout3 = tuple2._1();
            StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = mode = tuple2._2();
            if (StreamSubscriptionTimeoutTerminationMode$CancelTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
                this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().timeout(timeout3);
                if (this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().isClosed()) {
                    this.completeStage();
                    return;
                }
                return;
            }
            if (StreamSubscriptionTimeoutTerminationMode$NoopTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
                return;
            }
            if (StreamSubscriptionTimeoutTerminationMode$WarnTermination$.MODULE$.equals(streamSubscriptionTimeoutTerminationMode)) {
                materializer.logger().warning("Substream subscription timeout triggered after {} in prefixAndTail({}).", timeout3, (Object)BoxesRunTime.boxToInteger(this.$outer.n()));
                return;
            }
            throw new MatchError(streamSubscriptionTimeoutTerminationMode);
        }

        private boolean prefixComplete() {
            return this.builder() == null;
        }

        private OutHandler subHandler() {
            return new OutHandler(this){
                private final /* synthetic */ PrefixAndTailLogic $outer;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onDownstreamFinish(Throwable cause) throws Exception {
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                public void onPull() {
                    this.$outer.setKeepGoing(false);
                    this.$outer.cancelTimer(this.$outer.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$SubscriptionTimer());
                    this.$outer.pull(this.$outer.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$$outer().in());
                    this.$outer.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().setHandler(new OutHandler(this){
                        private final /* synthetic */ PrefixAndTailLogic$$anon$4 $outer;

                        public void onDownstreamFinish() throws Exception {
                            OutHandler.onDownstreamFinish$(this);
                        }

                        public void onDownstreamFinish(Throwable cause) throws Exception {
                            OutHandler.onDownstreamFinish$(this, cause);
                        }

                        public void onPull() {
                            this.$outer.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$anon$$$outer().pull(this.$outer.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$anon$$$outer().akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$$outer().in());
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            OutHandler.$init$(this);
                        }
                    });
                }

                public /* synthetic */ PrefixAndTailLogic akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$anon$$$outer() {
                    return this.$outer;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    OutHandler.$init$(this);
                }
            };
        }

        private Source<T, NotUsed> openSubstream() {
            FiniteDuration timeout2 = ((ActorAttributes.StreamSubscriptionTimeout)this.inheritedAttributes.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.StreamSubscriptionTimeout.class))).timeout();
            this.tailSource_$eq(new GraphStageLogic.SubSourceOutlet(this, "TailSource"));
            this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().setHandler(this.subHandler());
            this.setKeepGoing(true);
            this.scheduleOnce((Object)this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$SubscriptionTimer(), timeout2);
            this.builder_$eq(null);
            return Source$.MODULE$.fromGraph(this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().source());
        }

        @Override
        public void onPush() {
            if (this.prefixComplete()) {
                this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().push(this.grab(this.$outer.in()));
                return;
            }
            this.builder().$plus$eq(this.grab(this.$outer.in()));
            this.left_$eq(this.left() - 1);
            if (this.left() == 0) {
                this.push(this.$outer.out(), new Tuple2(this.builder().result(), this.openSubstream()));
                this.complete(this.$outer.out());
                return;
            }
            this.pull(this.$outer.in());
        }

        @Override
        public void onPull() {
            if (this.left() == 0) {
                this.push(this.$outer.out(), new Tuple2(Nil$.MODULE$, this.openSubstream()));
                this.complete(this.$outer.out());
                return;
            }
            this.pull(this.$outer.in());
        }

        @Override
        public void onUpstreamFinish() {
            if (!this.prefixComplete()) {
                this.emit(this.$outer.out(), new Tuple2(this.builder().result(), Source$.MODULE$.empty()), () -> this.completeStage());
                return;
            }
            if (!this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().isClosed()) {
                this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().complete();
            }
            this.completeStage();
        }

        @Override
        public void onUpstreamFailure(Throwable ex) {
            if (this.prefixComplete()) {
                if (!this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().isClosed()) {
                    this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource().fail(ex);
                }
                this.completeStage();
                return;
            }
            this.failStage(ex);
        }

        @Override
        public void onDownstreamFinish(Throwable cause) {
            if (!this.prefixComplete()) {
                this.cancelStage(cause);
                return;
            }
        }

        public /* synthetic */ PrefixAndTail akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$$outer() {
            return this.$outer;
        }

        public PrefixAndTailLogic(PrefixAndTail $outer, FlowShape<T, Tuple2<Seq<T>, Source<T, NotUsed>>> _shape, Attributes inheritedAttributes) {
            this.inheritedAttributes = inheritedAttributes;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            super(_shape);
            OutHandler.$init$(this);
            InHandler.$init$(this);
            this.left = $outer.n() < 0 ? 0 : $outer.n();
            this.builder = package$.MODULE$.Vector().newBuilder();
            this.builder().sizeHint(this.left());
            this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$tailSource = null;
            this.akka$stream$impl$fusing$PrefixAndTail$PrefixAndTailLogic$$SubscriptionTimer = "SubstreamSubscriptionTimer";
            this.setHandlers($outer.in(), $outer.out(), this);
        }
    }
}

