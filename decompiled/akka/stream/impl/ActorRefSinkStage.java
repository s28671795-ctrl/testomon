/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.impl.ActorRefSinkStage$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.StageLogging;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u00194QAC\u0006\u0003\u001fEA\u0001\u0002\f\u0001\u0003\u0002\u0003\u0006I!\f\u0005\tg\u0001\u0011\t\u0011)A\u0005S!AA\u0007\u0001B\u0001B\u0003%Q\u0007C\u0003E\u0001\u0011\u0005Q\tC\u0004L\u0001\t\u0007I\u0011\u0001'\t\rA\u0003\u0001\u0015!\u0003N\u0011\u0015\t\u0006\u0001\"\u0011S\u0011\u0015\u0019\u0006\u0001\"\u0015U\u0011\u0015A\u0006\u0001\"\u0011Z\u0005E\t5\r^8s%\u001647+\u001b8l'R\fw-\u001a\u0006\u0003\u00195\tA![7qY*\u0011abD\u0001\u0007gR\u0014X-Y7\u000b\u0003A\tA!Y6lCV\u0011!cH\n\u0003\u0001M\u00012\u0001F\f\u001a\u001b\u0005)\"B\u0001\f\u000e\u0003\u0015\u0019H/Y4f\u0013\tARC\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u00042AG\u000e\u001e\u001b\u0005i\u0011B\u0001\u000f\u000e\u0005%\u0019\u0016N\\6TQ\u0006\u0004X\r\u0005\u0002\u001f?1\u0001A!\u0002\u0011\u0001\u0005\u0004\u0011#!\u0001+\u0004\u0001E\u00111%\u000b\t\u0003I\u001dj\u0011!\n\u0006\u0002M\u0005)1oY1mC&\u0011\u0001&\n\u0002\b\u001d>$\b.\u001b8h!\t!#&\u0003\u0002,K\t\u0019\u0011I\\=\u0002\u0007I,g\r\u0005\u0002/c5\tqF\u0003\u00021\u001f\u0005)\u0011m\u0019;pe&\u0011!g\f\u0002\t\u0003\u000e$xN\u001d*fM\u0006\trN\\\"p[BdW\r^3NKN\u001c\u0018mZ3\u0002!=tg)Y5mkJ,W*Z:tC\u001e,\u0007\u0003\u0002\u00137q%J!aN\u0013\u0003\u0013\u0019+hn\u0019;j_:\f\u0004CA\u001dB\u001d\tQtH\u0004\u0002<}5\tAH\u0003\u0002>C\u00051AH]8pizJ\u0011AJ\u0005\u0003\u0001\u0016\nq\u0001]1dW\u0006<W-\u0003\u0002C\u0007\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003\u0001\u0016\na\u0001P5oSRtD\u0003\u0002$I\u0013*\u00032a\u0012\u0001\u001e\u001b\u0005Y\u0001\"\u0002\u0017\u0005\u0001\u0004i\u0003\"B\u001a\u0005\u0001\u0004I\u0003\"\u0002\u001b\u0005\u0001\u0004)\u0014AA5o+\u0005i\u0005c\u0001\u000eO;%\u0011q*\u0004\u0002\u0006\u0013:dW\r^\u0001\u0004S:\u0004\u0013!B:iCB,W#A\r\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001V!\tQb+\u0003\u0002X\u001b\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u00035v\u0003\"\u0001F.\n\u0005q+\"aD$sCBD7\u000b^1hK2{w-[2\t\u000byK\u0001\u0019A+\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:)\u0005\u0001\u0001\u0007CA1e\u001b\u0005\u0011'BA2\u0010\u0003)\tgN\\8uCRLwN\\\u0005\u0003K\n\u00141\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public final class ActorRefSinkStage<T>
extends GraphStage<SinkShape<T>> {
    public final ActorRef akka$stream$impl$ActorRefSinkStage$$ref;
    public final Object akka$stream$impl$ActorRefSinkStage$$onCompleteMessage;
    public final Function1<Throwable, Object> akka$stream$impl$ActorRefSinkStage$$onFailureMessage;
    private final Inlet<T> in;

    public Inlet<T> in() {
        return this.in;
    }

    @Override
    public SinkShape<T> shape() {
        return new SinkShape<T>(this.in());
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.actorRefSink();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private boolean completionSignalled;
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ ActorRefSinkStage $outer;

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            public Class<?> logSource() {
                return ActorRefSinkStage.class;
            }

            private boolean completionSignalled() {
                return this.completionSignalled;
            }

            private void completionSignalled_$eq(boolean x$1) {
                this.completionSignalled = x$1;
            }

            public void preStart() {
                this.getStageActor((Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$preStart$1(this, x0$1);
                    return BoxedUnit.UNIT;
                }).watch(this.$outer.akka$stream$impl$ActorRefSinkStage$$ref);
                this.pull(this.$outer.in());
            }

            public void onPush() {
                T next = this.grab(this.$outer.in());
                this.$outer.akka$stream$impl$ActorRefSinkStage$$ref.tell(next, ActorRef$.MODULE$.noSender());
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                this.completionSignalled_$eq(true);
                this.$outer.akka$stream$impl$ActorRefSinkStage$$ref.tell(this.$outer.akka$stream$impl$ActorRefSinkStage$$onCompleteMessage, ActorRef$.MODULE$.noSender());
                this.completeStage();
            }

            public void onUpstreamFailure(Throwable ex) {
                this.completionSignalled_$eq(true);
                this.$outer.akka$stream$impl$ActorRefSinkStage$$ref.tell(this.$outer.akka$stream$impl$ActorRefSinkStage$$onFailureMessage.apply(ex), ActorRef$.MODULE$.noSender());
                this.failStage(ex);
            }

            public void postStop() {
                if (!this.completionSignalled()) {
                    Object x$1 = this.$outer.akka$stream$impl$ActorRefSinkStage$$onFailureMessage.apply(new AbruptStageTerminationException(this));
                    ActorRef x$2 = this.$outer.akka$stream$impl$ActorRefSinkStage$$ref.$bang$default$2(x$1);
                    this.$outer.akka$stream$impl$ActorRefSinkStage$$ref.$bang(x$1, x$2);
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$1 $this, Tuple2 x0$1) {
                T2 T2;
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null && (T2 = tuple2._2()) instanceof Terminated) {
                    Terminated terminated = (Terminated)T2;
                    ActorRef actorRef2 = terminated.actor();
                    ActorRef actorRef3 = $this.$outer.akka$stream$impl$ActorRefSinkStage$$ref;
                    ActorRef actorRef4 = actorRef2;
                    if (!(actorRef3 != null ? !((Object)actorRef3).equals(actorRef4) : actorRef4 != null)) {
                        $this.completeStage();
                        return;
                    }
                }
                $this.log().error("Unexpected message to stage actor {}", tuple2.getClass());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                StageLogging.$init$(this);
                this.completionSignalled = false;
                this.setHandler($outer.in(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$1$adapted(akka.stream.impl.ActorRefSinkStage$$anon$1 scala.Tuple2 )}, serializedLambda);
            }
        };
    }

    public ActorRefSinkStage(ActorRef ref, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        this.akka$stream$impl$ActorRefSinkStage$$ref = ref;
        this.akka$stream$impl$ActorRefSinkStage$$onCompleteMessage = onCompleteMessage;
        this.akka$stream$impl$ActorRefSinkStage$$onFailureMessage = onFailureMessage;
        this.in = Inlet$.MODULE$.apply("ActorRefSink.in");
    }
}

