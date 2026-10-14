/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.annotation.InternalApi;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.SinkShape;
import akka.stream.impl.ActorRefBackpressureSinkStage$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.ArrayDeque;
import java.util.Deque;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015a!B\b\u0011\u0001Q1\u0002\u0002C\u0019\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001a\t\u0011a\u0002!\u0011!Q\u0001\neB\u0001\"\u0010\u0001\u0003\u0002\u0003\u0006IA\u0010\u0005\t\u007f\u0001\u0011\t\u0011)A\u0005\u0001\"A1\t\u0001B\u0001B\u0003%a\u0006\u0003\u0005E\u0001\t\u0005\t\u0015!\u0003F\u0011\u0015\u0011\u0006\u0001\"\u0001T\u0011\u001da\u0006A1A\u0005\u0002uCa!\u0019\u0001!\u0002\u0013q\u0006\"\u00022\u0001\t\u0003\u001a\u0007bB4\u0001\u0005\u0004%\t\u0005\u001b\u0005\u0007S\u0002\u0001\u000b\u0011\u0002\u0010\t\u000b)\u0004A\u0011I6\t\u000bE\u0004A\u0011\t:\u0003;\u0005\u001bGo\u001c:SK\u001a\u0014\u0015mY6qe\u0016\u001c8/\u001e:f'&t7n\u0015;bO\u0016T!!\u0005\n\u0002\t%l\u0007\u000f\u001c\u0006\u0003'Q\taa\u001d;sK\u0006l'\"A\u000b\u0002\t\u0005\\7.Y\u000b\u0003/\u0011\u001a\"\u0001\u0001\r\u0011\u0007eab$D\u0001\u001b\u0015\tY\"#A\u0003ti\u0006<W-\u0003\u0002\u001e5\tQqI]1qQN#\u0018mZ3\u0011\u0007}\u0001#%D\u0001\u0013\u0013\t\t#CA\u0005TS:\\7\u000b[1qKB\u00111\u0005\n\u0007\u0001\t\u0015)\u0003A1\u0001(\u0005\tIen\u0001\u0001\u0012\u0005!r\u0003CA\u0015-\u001b\u0005Q#\"A\u0016\u0002\u000bM\u001c\u0017\r\\1\n\u00055R#a\u0002(pi\"Lgn\u001a\t\u0003S=J!\u0001\r\u0016\u0003\u0007\u0005s\u00170A\u0002sK\u001a\u0004\"a\r\u001c\u000e\u0003QR!!\u000e\u000b\u0002\u000b\u0005\u001cGo\u001c:\n\u0005]\"$\u0001C!di>\u0014(+\u001a4\u0002\u001d5,7o]1hK\u0006#\u0017\r\u001d;feB!\u0011F\u000f\u001a=\u0013\tY$FA\u0005Gk:\u001cG/[8ocA!\u0011F\u000f\u0012/\u00035yg.\u00138ji6+7o]1hKB!\u0011F\u000f\u001a/\u0003)\t7m['fgN\fw-\u001a\t\u0004S\u0005s\u0013B\u0001\"+\u0005\u0019y\u0005\u000f^5p]\u0006\trN\\\"p[BdW\r^3NKN\u001c\u0018mZ3\u0002!=tg)Y5mkJ,W*Z:tC\u001e,\u0007\u0003B\u0015;\r:\u0002\"aR(\u000f\u0005!keBA%M\u001b\u0005Q%BA&'\u0003\u0019a$o\\8u}%\t1&\u0003\u0002OU\u00059\u0001/Y2lC\u001e,\u0017B\u0001)R\u0005%!\u0006N]8xC\ndWM\u0003\u0002OU\u00051A(\u001b8jiz\"r\u0001\u0016,X1fS6\fE\u0002V\u0001\tj\u0011\u0001\u0005\u0005\u0006c\u001d\u0001\rA\r\u0005\u0006q\u001d\u0001\r!\u000f\u0005\u0006{\u001d\u0001\rA\u0010\u0005\u0006\u007f\u001d\u0001\r\u0001\u0011\u0005\u0006\u0007\u001e\u0001\rA\f\u0005\u0006\t\u001e\u0001\r!R\u0001\u0003S:,\u0012A\u0018\t\u0004?}\u0013\u0013B\u00011\u0013\u0005\u0015Ie\u000e\\3u\u0003\rIg\u000eI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#\u00013\u0011\u0005})\u0017B\u00014\u0013\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002=\u000511\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR\u0011An\u001c\t\u000335L!A\u001c\u000e\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDQ\u0001]\u0007A\u0002\u0011\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002gB\u0011A/_\u0007\u0002k*\u0011ao^\u0001\u0005Y\u0006twMC\u0001y\u0003\u0011Q\u0017M^1\n\u0005i,(AB*ue&tw\r\u000b\u0002\u0001yB\u0019Q0!\u0001\u000e\u0003yT!a \u000b\u0002\u0015\u0005tgn\u001c;bi&|g.C\u0002\u0002\u0004y\u00141\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public class ActorRefBackpressureSinkStage<In>
extends GraphStage<SinkShape<In>> {
    public final ActorRef akka$stream$impl$ActorRefBackpressureSinkStage$$ref;
    public final Function1<ActorRef, Function1<In, Object>> akka$stream$impl$ActorRefBackpressureSinkStage$$messageAdapter;
    public final Function1<ActorRef, Object> akka$stream$impl$ActorRefBackpressureSinkStage$$onInitMessage;
    public final Option<Object> akka$stream$impl$ActorRefBackpressureSinkStage$$ackMessage;
    public final Object akka$stream$impl$ActorRefBackpressureSinkStage$$onCompleteMessage;
    public final Function1<Throwable, Object> akka$stream$impl$ActorRefBackpressureSinkStage$$onFailureMessage;
    private final Inlet<In> in;
    private final SinkShape<In> shape;

    public Inlet<In> in() {
        return this.in;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.actorRefWithBackpressureSink();
    }

    @Override
    public SinkShape<In> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final int maxBuffer;
            private final Deque<In> buffer;
            private boolean acknowledgementReceived;
            private boolean completeReceived;
            private boolean completionSignalled;
            private final /* synthetic */ ActorRefBackpressureSinkStage $outer;

            private ActorRef self() {
                return this.stageActor().ref();
            }

            private int maxBuffer() {
                return this.maxBuffer;
            }

            private Deque<In> buffer() {
                return this.buffer;
            }

            private boolean acknowledgementReceived() {
                return this.acknowledgementReceived;
            }

            private void acknowledgementReceived_$eq(boolean x$1) {
                this.acknowledgementReceived = x$1;
            }

            private boolean completeReceived() {
                return this.completeReceived;
            }

            private void completeReceived_$eq(boolean x$1) {
                this.completeReceived = x$1;
            }

            private boolean completionSignalled() {
                return this.completionSignalled;
            }

            private void completionSignalled_$eq(boolean x$1) {
                this.completionSignalled = x$1;
            }

            private void receive(Tuple2<ActorRef, Object> evt) {
                Object object = evt._2();
                if (object instanceof Terminated) {
                    Terminated terminated = (Terminated)object;
                    ActorRef actorRef2 = terminated.actor();
                    ActorRef actorRef3 = this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ref;
                    ActorRef actorRef4 = actorRef2;
                    if (!(actorRef3 != null ? !((Object)actorRef3).equals(actorRef4) : actorRef4 != null)) {
                        this.completeStage();
                        return;
                    }
                }
                if (this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ackMessage.isEmpty() || this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ackMessage.contains(object)) {
                    if (this.buffer().isEmpty()) {
                        this.acknowledgementReceived_$eq(true);
                        if (this.completeReceived()) {
                            this.finish();
                            return;
                        }
                        return;
                    }
                    if (this.buffer().size() == this.maxBuffer()) {
                        this.tryPull(this.$outer.in());
                    }
                    this.dequeueAndSend();
                    return;
                }
            }

            public void preStart() {
                this.setKeepGoing(true);
                this.getStageActor((Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)evt -> {
                    $anon$1.$anonfun$preStart$1(this, evt);
                    return BoxedUnit.UNIT;
                }).watch(this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ref);
                this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ref.$bang(this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$onInitMessage.apply(this.self()), this.self());
                this.pull(this.$outer.in());
            }

            private void dequeueAndSend() {
                this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ref.$bang(this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$messageAdapter.apply(this.self()).apply(this.buffer().poll()), this.self());
            }

            private void finish() {
                this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ref.$bang(this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$onCompleteMessage, this.self());
                this.completionSignalled_$eq(true);
                this.completeStage();
            }

            public void onPush() {
                this.buffer().offer(this.grab(this.$outer.in()));
                if (this.acknowledgementReceived()) {
                    this.dequeueAndSend();
                    this.acknowledgementReceived_$eq(false);
                }
                if (this.buffer().size() < this.maxBuffer()) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                if (this.buffer().isEmpty()) {
                    this.finish();
                    return;
                }
                this.completeReceived_$eq(true);
            }

            public void onUpstreamFailure(Throwable ex) {
                this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ref.$bang(this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$onFailureMessage.apply(ex), this.self());
                this.completionSignalled_$eq(true);
                this.failStage(ex);
            }

            public void postStop() {
                if (!this.completionSignalled()) {
                    this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$ref.$bang(this.$outer.akka$stream$impl$ActorRefBackpressureSinkStage$$onFailureMessage.apply(new AbruptStageTerminationException(this)), this.self());
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$1 $this, Tuple2 evt) {
                $this.receive(evt);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                this.maxBuffer = inheritedAttributes$1.get(new Attributes.InputBuffer(16, 16), ClassTag$.MODULE$.apply(Attributes.InputBuffer.class)).max();
                Predef$.MODULE$.require(this.maxBuffer() > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "Buffer size must be greater than 0");
                this.buffer = new ArrayDeque<In>();
                this.acknowledgementReceived = false;
                this.completeReceived = false;
                this.completionSignalled = false;
                this.setHandler($outer.in(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$new$1(), $anonfun$preStart$1$adapted(akka.stream.impl.ActorRefBackpressureSinkStage$$anon$1 scala.Tuple2 )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "ActorRefBackpressureSink";
    }

    public ActorRefBackpressureSinkStage(ActorRef ref, Function1<ActorRef, Function1<In, Object>> messageAdapter, Function1<ActorRef, Object> onInitMessage, Option<Object> ackMessage, Object onCompleteMessage, Function1<Throwable, Object> onFailureMessage) {
        this.akka$stream$impl$ActorRefBackpressureSinkStage$$ref = ref;
        this.akka$stream$impl$ActorRefBackpressureSinkStage$$messageAdapter = messageAdapter;
        this.akka$stream$impl$ActorRefBackpressureSinkStage$$onInitMessage = onInitMessage;
        this.akka$stream$impl$ActorRefBackpressureSinkStage$$ackMessage = ackMessage;
        this.akka$stream$impl$ActorRefBackpressureSinkStage$$onCompleteMessage = onCompleteMessage;
        this.akka$stream$impl$ActorRefBackpressureSinkStage$$onFailureMessage = onFailureMessage;
        this.in = Inlet$.MODULE$.apply("ActorRefBackpressureSink.in");
        this.shape = new SinkShape<In>(this.in());
    }
}

