/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.BufferOverflowException;
import akka.stream.DelayOverflowStrategy;
import akka.stream.OverflowStrategies;
import akka.stream.OverflowStrategies$EmitEarly$;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.Delay$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.scaladsl.DelayStrategy;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.TimeUnit;
import scala.Function0;
import scala.MatchError;
import scala.Tuple2;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;

@ScalaSignature(bytes="\u0006\u0005\u0005\rq!\u0002\b\u0010\u0011\u0003Ab!\u0002\u000e\u0010\u0011\u0003Y\u0002\"\u0002\u0012\u0002\t\u0003\u0019\u0003b\u0002\u0013\u0002\u0005\u0004%I!\n\u0005\u0007]\u0005\u0001\u000b\u0011\u0002\u0014\t\u000f=\n!\u0019!C\u0005a!1A'\u0001Q\u0001\nE2QAG\b\u0003+uB\u0001\u0002X\u0004\u0003\u0002\u0003\u0006I!\u0018\u0005\tM\u001e\u0011\t\u0011)A\u0005O\")!e\u0002C\u0001W\")qn\u0002C!a\")Ao\u0002C!k\")ap\u0002C!\u007f\u0006)A)\u001a7bs*\u0011\u0001#E\u0001\u0007MV\u001c\u0018N\\4\u000b\u0005I\u0019\u0012\u0001B5na2T!\u0001F\u000b\u0002\rM$(/Z1n\u0015\u00051\u0012\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u001a\u00035\tqBA\u0003EK2\f\u0017p\u0005\u0002\u00029A\u0011Q\u0004I\u0007\u0002=)\tq$A\u0003tG\u0006d\u0017-\u0003\u0002\"=\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\r\u0002\u0013QKW.\u001a:OC6,W#\u0001\u0014\u0011\u0005\u001dbS\"\u0001\u0015\u000b\u0005%R\u0013\u0001\u00027b]\u001eT\u0011aK\u0001\u0005U\u00064\u0018-\u0003\u0002.Q\t11\u000b\u001e:j]\u001e\f!\u0002V5nKJt\u0015-\\3!\u0003A!U\r\\1z!J,7-[:j_:l5+F\u00012!\ti\"'\u0003\u00024=\t\u0019\u0011J\u001c;\u0002#\u0011+G.Y=Qe\u0016\u001c\u0017n]5p]6\u001b\u0006\u0005\u000b\u0002\u0002mA\u0011qGO\u0007\u0002q)\u0011\u0011(F\u0001\u000bC:tw\u000e^1uS>t\u0017BA\u001e9\u0005-Ie\u000e^3s]\u0006d\u0017\t]5)\u0005\u00011TC\u0001 T'\t9q\bE\u0002A\u001dFs!!\u0011'\u000f\u0005\t[eBA\"K\u001d\t!\u0015J\u0004\u0002F\u00116\taI\u0003\u0002H/\u00051AH]8pizJ\u0011AF\u0005\u0003)UI!AE\n\n\u0005A\t\u0012BA'\u0010\u0003-9%/\u00199i'R\fw-Z:\n\u0005=\u0003&AF*j[BdW\rT5oK\u0006\u0014xI]1qQN#\u0018mZ3\u000b\u00055{\u0001C\u0001*T\u0019\u0001!Q\u0001V\u0004C\u0002U\u0013\u0011\u0001V\t\u0003-f\u0003\"!H,\n\u0005as\"a\u0002(pi\"Lgn\u001a\t\u0003;iK!a\u0017\u0010\u0003\u0007\u0005s\u00170A\u000beK2\f\u0017p\u0015;sCR,w-_*vaBd\u0017.\u001a:\u0011\u0007uq\u0006-\u0003\u0002`=\tIa)\u001e8di&|g\u000e\r\t\u0004C\u0012\fV\"\u00012\u000b\u0005\r\u001c\u0012\u0001C:dC2\fGm\u001d7\n\u0005\u0015\u0014'!\u0004#fY\u0006L8\u000b\u001e:bi\u0016<\u00170\u0001\tpm\u0016\u0014h\r\\8x'R\u0014\u0018\r^3hsB\u0011\u0001.[\u0007\u0002'%\u0011!n\u0005\u0002\u0016\t\u0016d\u0017-_(wKJ4Gn\\<TiJ\fG/Z4z)\raWN\u001c\t\u00043\u001d\t\u0006\"\u0002/\u000b\u0001\u0004i\u0006\"\u00024\u000b\u0001\u00049\u0017!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\t\u0011\u000f\u0005\u0002ie&\u00111o\u0005\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018aC2sK\u0006$X\rT8hS\u000e$\"A\u001e?\u0011\u0005]TX\"\u0001=\u000b\u0005e\u001c\u0012!B:uC\u001e,\u0017BA>y\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"B?\r\u0001\u0004\t\u0018aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003\u0019B#a\u0002\u001c")
@InternalApi
public final class Delay<T>
extends GraphStages.SimpleLinearGraphStage<T> {
    public final Function0<DelayStrategy<T>> akka$stream$impl$fusing$Delay$$delayStrategySupplier;
    public final DelayOverflowStrategy akka$stream$impl$fusing$Delay$$overflowStrategy;

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.delay();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final int size;
            private final DelayStrategy<T> delayStrategy;
            private final Buffer<Tuple2<Object, T>> buffer;
            private final Function0<BoxedUnit> onPushWhenBufferFull;
            private final /* synthetic */ Delay $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public void onPush() {
                if (this.buffer.isFull()) {
                    this.onPushWhenBufferFull.apply$mcV$sp();
                    return;
                }
                this.grabAndPull();
                if (!this.isTimerActive(Delay$.MODULE$.akka$stream$impl$fusing$Delay$$TimerName())) {
                    long waitTime = this.nextElementWaitTime();
                    if (waitTime <= (long)Delay$.MODULE$.akka$stream$impl$fusing$Delay$$DelayPrecisionMS() && this.isAvailable(this.$outer.out())) {
                        this.push(this.$outer.out(), this.buffer.dequeue()._2());
                        this.completeIfReady();
                        return;
                    }
                    this.scheduleOnce((Object)Delay$.MODULE$.akka$stream$impl$fusing$Delay$$TimerName(), new package.DurationLong(package$.MODULE$.DurationLong(waitTime)).millis());
                    return;
                }
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            private boolean shouldPull() {
                if (this.buffer.used() < this.size) return true;
                if (!this.$outer.akka$stream$impl$fusing$Delay$$overflowStrategy.isBackpressure()) return true;
                DelayOverflowStrategy delayOverflowStrategy = this.$outer.akka$stream$impl$fusing$Delay$$overflowStrategy;
                OverflowStrategies$EmitEarly$ overflowStrategies$EmitEarly$ = OverflowStrategies$EmitEarly$.MODULE$;
                if (delayOverflowStrategy == null) {
                    if (overflowStrategies$EmitEarly$ != null) {
                        return false;
                    }
                } else if (!delayOverflowStrategy.equals(overflowStrategies$EmitEarly$)) return false;
                if (!this.isAvailable(this.$outer.out())) return false;
                return true;
            }

            private void grabAndPull() {
                T element = this.grab(this.$outer.in());
                this.buffer.enqueue(new Tuple2<Long, T>(BoxesRunTime.boxToLong(System.nanoTime() + this.delayStrategy.nextDelay(element).toNanos()), element));
                if (this.shouldPull()) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                this.completeIfReady();
            }

            public void onPull() {
                if (!this.isTimerActive(Delay$.MODULE$.akka$stream$impl$fusing$Delay$$TimerName()) && !this.buffer.isEmpty()) {
                    long waitTime = this.nextElementWaitTime();
                    if (waitTime <= (long)Delay$.MODULE$.akka$stream$impl$fusing$Delay$$DelayPrecisionMS()) {
                        this.push(this.$outer.out(), this.buffer.dequeue()._2());
                    } else {
                        this.scheduleOnce((Object)Delay$.MODULE$.akka$stream$impl$fusing$Delay$$TimerName(), new package.DurationLong(package$.MODULE$.DurationLong(waitTime)).millis());
                    }
                }
                if (!this.isClosed(this.$outer.in()) && !this.hasBeenPulled(this.$outer.in()) && this.shouldPull()) {
                    this.pull(this.$outer.in());
                }
                this.completeIfReady();
            }

            private void completeIfReady() {
                if (this.isClosed(this.$outer.in()) && this.buffer.isEmpty()) {
                    this.completeStage();
                    return;
                }
            }

            private long nextElementWaitTime() {
                return TimeUnit.NANOSECONDS.toMillis(this.buffer.peek()._1$mcJ$sp() - System.nanoTime());
            }

            public final void onTimer(Object key) {
                if (this.isAvailable(this.$outer.out())) {
                    this.push(this.$outer.out(), this.buffer.dequeue()._2());
                }
                this.completeIfReady();
            }
            {
                Function0<Nothing$> & Serializable intersect;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.size = ((Attributes.InputBuffer)inheritedAttributes$17.mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.InputBuffer.class))).max();
                this.delayStrategy = $outer.akka$stream$impl$fusing$Delay$$delayStrategySupplier.apply();
                this.buffer = Buffer$.MODULE$.apply(this.size, inheritedAttributes$17);
                DelayOverflowStrategy delayOverflowStrategy = $outer.akka$stream$impl$fusing$Delay$$overflowStrategy;
                if (OverflowStrategies$EmitEarly$.MODULE$.equals(delayOverflowStrategy)) {
                    intersect = (Function0<Nothing$> & Serializable)() -> {
                        if (this.isAvailable($this.$outer.out())) {
                            if (this.isTimerActive(Delay$.MODULE$.akka$stream$impl$fusing$Delay$$TimerName())) {
                                this.cancelTimer(Delay$.MODULE$.akka$stream$impl$fusing$Delay$$TimerName());
                            }
                            this.push($this.$outer.out(), $this.buffer.dequeue()._2());
                            this.grabAndPull();
                            this.completeIfReady();
                            return;
                        }
                        throw new IllegalStateException("Was configured to emitEarly and got element when out is not ready and buffer is full, should not be possible.");
                    };
                } else if (delayOverflowStrategy instanceof OverflowStrategies.DropHead) {
                    intersect = () -> {
                        $this.buffer.dropHead();
                        this.grabAndPull();
                    };
                } else if (delayOverflowStrategy instanceof OverflowStrategies.DropTail) {
                    intersect = () -> {
                        $this.buffer.dropTail();
                        this.grabAndPull();
                    };
                } else if (delayOverflowStrategy instanceof OverflowStrategies.DropNew) {
                    intersect = () -> {
                        this.grab($this.$outer.in());
                        if (this.shouldPull()) {
                            this.pull($this.$outer.in());
                            return;
                        }
                    };
                } else if (delayOverflowStrategy instanceof OverflowStrategies.DropBuffer) {
                    intersect = () -> {
                        $this.buffer.clear();
                        this.grabAndPull();
                    };
                } else if (delayOverflowStrategy instanceof OverflowStrategies.Fail) {
                    intersect = () -> this.failStage(new BufferOverflowException(new StringBuilder(56).append("Buffer overflow for delay operator (max capacity was: ").append($this.size).append(")!").toString()));
                } else if (delayOverflowStrategy instanceof OverflowStrategies.Backpressure) {
                    intersect = (Function0<Nothing$> & Serializable)() -> {
                        throw new IllegalStateException("Delay buffer must never overflow in Backpressure mode");
                    };
                } else {
                    throw new MatchError(delayOverflowStrategy);
                }
                this.onPushWhenBufferFull = intersect;
                this.setHandler($outer.in(), this);
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onPushWhenBufferFull$1(akka.stream.impl.fusing.Delay$$anon$37 ), $anonfun$onPushWhenBufferFull$2(akka.stream.impl.fusing.Delay$$anon$37 ), $anonfun$onPushWhenBufferFull$3(akka.stream.impl.fusing.Delay$$anon$37 ), $anonfun$onPushWhenBufferFull$4(akka.stream.impl.fusing.Delay$$anon$37 ), $anonfun$onPushWhenBufferFull$5(akka.stream.impl.fusing.Delay$$anon$37 ), $anonfun$onPushWhenBufferFull$6(akka.stream.impl.fusing.Delay$$anon$37 ), $anonfun$onPushWhenBufferFull$7()}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "Delay";
    }

    public Delay(Function0<DelayStrategy<T>> delayStrategySupplier, DelayOverflowStrategy overflowStrategy) {
        this.akka$stream$impl$fusing$Delay$$delayStrategySupplier = delayStrategySupplier;
        this.akka$stream$impl$fusing$Delay$$overflowStrategy = overflowStrategy;
    }
}

