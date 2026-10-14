/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.Attributes;
import akka.stream.BufferOverflowException;
import akka.stream.CompletionStrategy;
import akka.stream.CompletionStrategy$Draining$;
import akka.stream.CompletionStrategy$Immediately$;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.OverflowStrategies;
import akka.stream.OverflowStrategy;
import akka.stream.SourceShape;
import akka.stream.SourceShape$;
import akka.stream.impl.ActorRefSource$;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.OutHandler;
import akka.stream.stage.StageLogging;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.PartialFunction;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005]r!B\t\u0013\u0011\u0013Ib!B\u000e\u0013\u0011\u0013a\u0002\"B\u0012\u0002\t\u0003!caB\u0013\u0002!\u0003\rJC\n\u0005\u0006O\r1\t\u0001\u000b\u0004\u00067I\u0011a#\u0010\u0005\t)\u0016\u0011\t\u0011)A\u0005+\"A\u0001,\u0002B\u0001B\u0003%\u0011\f\u0003\u0005]\u000b\t\u0005\t\u0015!\u0003^\u0011!\u0019WA!A!\u0002\u0013!\u0007\"B\u0012\u0006\t\u0003\t\bbB<\u0006\u0005\u0004%\t\u0001\u001f\u0005\u0007y\u0016\u0001\u000b\u0011B=\t\u000fu,!\u0019!C!}\"1q0\u0002Q\u0001\n\u0015Cq!!\u0001\u0006\t\u0003\t\u0019\u0001\u0003\u0005\u0002\u0002\u0015!\tEFA\u000e\u00039\t5\r^8s%\u001647k\\;sG\u0016T!a\u0005\u000b\u0002\t%l\u0007\u000f\u001c\u0006\u0003+Y\taa\u001d;sK\u0006l'\"A\f\u0002\t\u0005\\7.Y\u0002\u0001!\tQ\u0012!D\u0001\u0013\u00059\t5\r^8s%\u001647k\\;sG\u0016\u001c\"!A\u000f\u0011\u0005y\tS\"A\u0010\u000b\u0003\u0001\nQa]2bY\u0006L!AI\u0010\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t\u0011DA\u0007BGR|'OU3g'R\fw-Z\n\u0003\u0007u\t1A]3g+\u0005I\u0003C\u0001\u0016.\u001b\u0005Y#B\u0001\u0017\u0017\u0003\u0015\t7\r^8s\u0013\tq3F\u0001\u0005BGR|'OU3gS\t\u0019\u0001G\u0002\u00032\u0007\u0001\u0011$!\u0004\u001fm_\u000e\fG\u000eI2iS2$ghE\u00021gm\u0002\"\u0001N\u001d\u000e\u0003UR!AN\u001c\u0002\t1\fgn\u001a\u0006\u0002q\u0005!!.\u0019<b\u0013\tQTG\u0001\u0004PE*,7\r\u001e\t\u0003y\ri\u0011!A\u000b\u0003}-\u001b\"!B \u0011\t\u0001\u001bU)K\u0007\u0002\u0003*\u0011!\tF\u0001\u0006gR\fw-Z\u0005\u0003\t\u0006\u0013qd\u0012:ba\"\u001cF/Y4f/&$\b.T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f!\r1u)S\u0007\u0002)%\u0011\u0001\n\u0006\u0002\f'>,(oY3TQ\u0006\u0004X\r\u0005\u0002K\u00172\u0001A!\u0002'\u0006\u0005\u0004i%!\u0001+\u0012\u00059\u000b\u0006C\u0001\u0010P\u0013\t\u0001vDA\u0004O_RD\u0017N\\4\u0011\u0005y\u0011\u0016BA* \u0005\r\te._\u0001\n[\u0006D()\u001e4gKJ\u0004\"A\b,\n\u0005]{\"aA%oi\u0006\u0001rN^3sM2|wo\u0015;sCR,w-\u001f\t\u0003\rjK!a\u0017\u000b\u0003!=3XM\u001d4m_^\u001cFO]1uK\u001eL\u0018!E2p[BdW\r^5p]6\u000bGo\u00195feB!aDX)a\u0013\tyvDA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o!\t1\u0015-\u0003\u0002c)\t\u00112i\\7qY\u0016$\u0018n\u001c8TiJ\fG/Z4z\u000391\u0017-\u001b7ve\u0016l\u0015\r^2iKJ\u0004BA\b0RKB\u0011aM\u001c\b\u0003O2t!\u0001[6\u000e\u0003%T!A\u001b\r\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0001\u0013BA7 \u0003\u001d\u0001\u0018mY6bO\u0016L!a\u001c9\u0003\u0013QC'o\\<bE2,'BA7 )\u0015\u00118\u000f^;w!\rQR!\u0013\u0005\u0006)*\u0001\r!\u0016\u0005\u00061*\u0001\r!\u0017\u0005\u00069*\u0001\r!\u0018\u0005\u0006G*\u0001\r\u0001Z\u0001\u0004_V$X#A=\u0011\u0007\u0019S\u0018*\u0003\u0002|)\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\tQ)\u0001\u0004tQ\u0006\u0004X\rI\u0001 GJ,\u0017\r^3M_\u001eL7-\u00118e\u001b\u0006$XM]5bY&TX\r\u001a,bYV,G\u0003BA\u0003\u0003#\u0001bAHA\u0004\u0003\u0017I\u0013bAA\u0005?\t1A+\u001e9mKJ\u00022\u0001QA\u0007\u0013\r\ty!\u0011\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"9\u00111C\bA\u0002\u0005U\u0011aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bc\u0001$\u0002\u0018%\u0019\u0011\u0011\u0004\u000b\u0003\u0015\u0005#HO]5ckR,7\u000f\u0006\u0004\u0002\u0006\u0005u\u0011q\u0004\u0005\b\u0003'\u0001\u0002\u0019AA\u000b\u0011\u001d\t\t\u0003\u0005a\u0001\u0003G\t\u0011#Z1hKJl\u0015\r^3sS\u0006d\u0017N_3s!\r1\u0015QE\u0005\u0004\u0003O!\"\u0001D'bi\u0016\u0014\u0018.\u00197ju\u0016\u0014\bfA\u0003\u0002,A!\u0011QFA\u001a\u001b\t\tyCC\u0002\u00022Y\t!\"\u00198o_R\fG/[8o\u0013\u0011\t)$a\f\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class ActorRefSource<T>
extends GraphStageWithMaterializedValue<SourceShape<T>, ActorRef> {
    public final int akka$stream$impl$ActorRefSource$$maxBuffer;
    public final OverflowStrategy akka$stream$impl$ActorRefSource$$overflowStrategy;
    public final PartialFunction<Object, CompletionStrategy> akka$stream$impl$ActorRefSource$$completionMatcher;
    public final PartialFunction<Object, Throwable> akka$stream$impl$ActorRefSource$$failureMatcher;
    private final Outlet<T> out;
    private final SourceShape<T> shape;

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, ActorRef> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        throw new IllegalStateException("Not supported");
    }

    @Override
    public Tuple2<GraphStageLogic, ActorRef> createLogicAndMaterializedValue(Attributes inheritedAttributes, Materializer eagerMaterializer) {
        OutHandler stage = new OutHandler(this, inheritedAttributes, eagerMaterializer){
            private final Buffer<T> buffer;
            private boolean isCompleting;
            private final String name;
            private final ActorRef ref;
            private LoggingAdapter akka$stream$stage$StageLogging$$_log;
            private final /* synthetic */ ActorRefSource $outer;
            private final Attributes inheritedAttributes$1;

            public LoggingAdapter log() {
                return StageLogging.log$(this);
            }

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public LoggingAdapter akka$stream$stage$StageLogging$$_log() {
                return this.akka$stream$stage$StageLogging$$_log;
            }

            public void akka$stream$stage$StageLogging$$_log_$eq(LoggingAdapter x$1) {
                this.akka$stream$stage$StageLogging$$_log = x$1;
            }

            public Class<?> logSource() {
                return ActorRefSource.class;
            }

            private Buffer<T> buffer() {
                return this.buffer;
            }

            private boolean isCompleting() {
                return this.isCompleting;
            }

            private void isCompleting_$eq(boolean x$1) {
                this.isCompleting = x$1;
            }

            public String stageActorName() {
                return this.inheritedAttributes$1.nameForActorRef(super.stageActorName());
            }

            private String name() {
                return this.name;
            }

            public ActorRef ref() {
                return this.ref;
            }

            private void tryPush() {
                if (this.isAvailable(this.$outer.out()) && OptionVal$.MODULE$.isDefined$extension(this.buffer()) && OptionVal$.MODULE$.get$extension(this.buffer()).nonEmpty()) {
                    T msg = OptionVal$.MODULE$.get$extension(this.buffer()).dequeue();
                    this.push(this.$outer.out(), msg);
                }
                if (this.isCompleting() && (OptionVal$.MODULE$.isEmpty$extension(this.buffer()) || OptionVal$.MODULE$.get$extension(this.buffer()).isEmpty())) {
                    this.completeStage();
                    return;
                }
            }

            public void onPull() {
                this.tryPush();
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public static final /* synthetic */ void $anonfun$ref$1($anon$1 $this, Tuple2 x0$1) {
                T2 m4;
                T2 m5;
                T2 m6;
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null && $this.$outer.akka$stream$impl$ActorRefSource$$failureMatcher.isDefinedAt(m6 = tuple2._2())) {
                    $this.failStage((Throwable)$this.$outer.akka$stream$impl$ActorRefSource$$failureMatcher.apply(m6));
                    return;
                }
                if (tuple2 != null && $this.$outer.akka$stream$impl$ActorRefSource$$completionMatcher.isDefinedAt(m5 = tuple2._2())) {
                    CompletionStrategy completionStrategy = (CompletionStrategy)$this.$outer.akka$stream$impl$ActorRefSource$$completionMatcher.apply(m5);
                    if (CompletionStrategy$Draining$.MODULE$.equals(completionStrategy)) {
                        $this.isCompleting_$eq(true);
                        $this.tryPush();
                        return;
                    } else {
                        if (!CompletionStrategy$Immediately$.MODULE$.equals(completionStrategy)) throw new MatchError(completionStrategy);
                        $this.completeStage();
                    }
                    return;
                }
                if (tuple2 == null || !((m4 = tuple2._2()) instanceof Object)) throw new IllegalArgumentException();
                T2 T2 = m4;
                Buffer<T> buffer = $this.buffer();
                Buffer<T> buffer2 = OptionVal$Some$.MODULE$.unapply(buffer);
                if (!OptionVal$.MODULE$.isEmpty$extension(buffer2)) {
                    Buffer<T2> buf = OptionVal$.MODULE$.get$extension(buffer2);
                    if ($this.isCompleting()) {
                        $this.log().warning("Dropping element because Status.Success received already, only draining already buffered elements: [{}] (pending: [{}] in stream [{}])", T2, (Object)BoxesRunTime.boxToInteger(buf.used()), (Object)$this.name());
                        return;
                    } else if (!buf.isFull()) {
                        buf.enqueue(T2);
                        $this.tryPush();
                        return;
                    } else {
                        OverflowStrategy overflowStrategy = $this.$outer.akka$stream$impl$ActorRefSource$$overflowStrategy;
                        if (overflowStrategy instanceof OverflowStrategies.DropHead) {
                            OverflowStrategies.DropHead dropHead = (OverflowStrategies.DropHead)overflowStrategy;
                            $this.log().log(dropHead.logLevel(), "Dropping the head element because buffer is full and overflowStrategy is: [DropHead] in stream [{}]", $this.name());
                            buf.dropHead();
                            buf.enqueue(T2);
                            $this.tryPush();
                            return;
                        } else if (overflowStrategy instanceof OverflowStrategies.DropTail) {
                            OverflowStrategies.DropTail dropTail = (OverflowStrategies.DropTail)overflowStrategy;
                            $this.log().log(dropTail.logLevel(), "Dropping the tail element because buffer is full and overflowStrategy is: [DropTail] in stream [{}]", $this.name());
                            buf.dropTail();
                            buf.enqueue(T2);
                            $this.tryPush();
                            return;
                        } else if (overflowStrategy instanceof OverflowStrategies.DropBuffer) {
                            OverflowStrategies.DropBuffer dropBuffer = (OverflowStrategies.DropBuffer)overflowStrategy;
                            $this.log().log(dropBuffer.logLevel(), "Dropping all the buffered elements because buffer is full and overflowStrategy is: [DropBuffer] in stream [{}]", $this.name());
                            buf.clear();
                            buf.enqueue(T2);
                            $this.tryPush();
                            return;
                        } else if (overflowStrategy instanceof OverflowStrategies.DropNew) {
                            OverflowStrategies.DropNew dropNew = (OverflowStrategies.DropNew)overflowStrategy;
                            $this.log().log(dropNew.logLevel(), "Dropping the new element because buffer is full and overflowStrategy is: [DropNew] in stream [{}]", $this.name());
                            return;
                        } else if (overflowStrategy instanceof OverflowStrategies.Fail) {
                            OverflowStrategies.Fail fail = (OverflowStrategies.Fail)overflowStrategy;
                            $this.log().log(fail.logLevel(), "Failing because buffer is full and overflowStrategy is: [Fail] in stream [{}]", $this.name());
                            BufferOverflowException bufferOverflowException = new BufferOverflowException(new StringBuilder(37).append("Buffer overflow (max capacity was: ").append($this.$outer.akka$stream$impl$ActorRefSource$$maxBuffer).append(")!").toString());
                            $this.failStage(bufferOverflowException);
                            return;
                        } else {
                            if (!(overflowStrategy instanceof OverflowStrategies.Backpressure)) throw new MatchError(overflowStrategy);
                            $this.failStage(new IllegalStateException("Backpressure is not supported"));
                        }
                    }
                    return;
                } else if ($this.isCompleting()) {
                    $this.log().warning("Dropping element because Status.Success received already: [{}] in stream [{}]", T2, (Object)$this.name());
                    return;
                } else if ($this.isAvailable($this.$outer.out())) {
                    $this.push($this.$outer.out(), T2);
                    return;
                } else {
                    $this.log().debug("Dropping element because there is no downstream demand and no buffer: [{}] in stream [{}]", T2, $this.name());
                }
            }
            {
                Buffer<T> buffer;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                OutHandler.$init$(this);
                StageLogging.$init$(this);
                if ($outer.akka$stream$impl$ActorRefSource$$maxBuffer != 0) {
                    buffer = OptionVal$.MODULE$.apply(Buffer$.MODULE$.apply($outer.akka$stream$impl$ActorRefSource$$maxBuffer, inheritedAttributes$1));
                } else {
                    OptionVal$.MODULE$.None();
                    buffer = null;
                }
                this.buffer = buffer;
                this.isCompleting = false;
                this.name = inheritedAttributes$1.nameOrDefault(this.getClass().toString());
                this.ref = this.getEagerStageActor(eagerMaterializer$1, (Function1<Tuple2<ActorRef, Object>, BoxedUnit>)(Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$ref$1(this, x0$1);
                    return BoxedUnit.UNIT;
                }).ref();
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$ref$1$adapted(akka.stream.impl.ActorRefSource$$anon$1 scala.Tuple2 )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, ActorRef>((GraphStageLogic)((Object)stage), ((ActorRefStage)((Object)stage)).ref());
    }

    public ActorRefSource(int maxBuffer, OverflowStrategy overflowStrategy, PartialFunction<Object, CompletionStrategy> completionMatcher, PartialFunction<Object, Throwable> failureMatcher) {
        this.akka$stream$impl$ActorRefSource$$maxBuffer = maxBuffer;
        this.akka$stream$impl$ActorRefSource$$overflowStrategy = overflowStrategy;
        this.akka$stream$impl$ActorRefSource$$completionMatcher = completionMatcher;
        this.akka$stream$impl$ActorRefSource$$failureMatcher = failureMatcher;
        this.out = Outlet$.MODULE$.apply("actorRefSource.out");
        this.shape = SourceShape$.MODULE$.of(this.out());
    }

    private static interface ActorRefStage {
        public ActorRef ref();
    }
}

