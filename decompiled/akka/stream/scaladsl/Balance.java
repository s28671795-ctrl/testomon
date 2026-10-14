/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.UniformFanOutShape;
import akka.stream.UniformFanOutShape$;
import akka.stream.impl.FixedSizeBuffer;
import akka.stream.impl.FixedSizeBuffer$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.Balance$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.IndexedSeq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005=r!\u0002\f\u0018\u0011\u0003qb!\u0002\u0011\u0018\u0011\u0003\t\u0003\"\u0002\u0015\u0002\t\u0003I\u0003\"\u0002\u0016\u0002\t\u0003Y\u0003\"CA\n\u0003E\u0005I\u0011AA\u000b\r\u0011\u0001sC\u0001\u0018\t\u0011\u0015+!Q1A\u0005\u0002\u0019C\u0001BS\u0003\u0003\u0002\u0003\u0006Ia\u0012\u0005\t\u0017\u0016\u0011)\u0019!C\u0001\u0019\"A\u0001+\u0002B\u0001B\u0003%Q\n\u0003\u0005R\u000b\t\u0015\r\u0011\"\u0001M\u0011!\u0011VA!A!\u0002\u0013i\u0005\"\u0002\u0015\u0006\t\u0003\u0019\u0006b\u0002-\u0006\u0005\u0004%\t!\u0017\u0005\u0007;\u0016\u0001\u000b\u0011\u0002.\t\u000fy+!\u0019!C\u0001?\"11.\u0002Q\u0001\n\u0001DQ\u0001\\\u0003\u0005B5Dq!]\u0003C\u0002\u0013\u0005#\u000f\u0003\u0004t\u000b\u0001\u0006IA\u000e\u0005\u0006i\u0016!\t%\u001e\u0005\u0006w\u0016!\t\u0005`\u0001\b\u0005\u0006d\u0017M\\2f\u0015\tA\u0012$\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\tQ2$\u0001\u0004tiJ,\u0017-\u001c\u0006\u00029\u0005!\u0011m[6b\u0007\u0001\u0001\"aH\u0001\u000e\u0003]\u0011qAQ1mC:\u001cWm\u0005\u0002\u0002EA\u00111EJ\u0007\u0002I)\tQ%A\u0003tG\u0006d\u0017-\u0003\u0002(I\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\u0010\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u00071\ni\u0001F\u0003.\u0003\u001f\t\t\u0002\u0005\u0003 \u000b\u0005-QCA\u0018='\t)\u0001\u0007E\u00022iYj\u0011A\r\u0006\u0003ge\tQa\u001d;bO\u0016L!!\u000e\u001a\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\r\u0005\u00038qiRT\"A\r\n\u0005eJ\"AE+oS\u001a|'/\u001c$b]>+Ho\u00155ba\u0016\u0004\"a\u000f\u001f\r\u0001\u0011)Q(\u0002b\u0001}\t\tA+\u0005\u0002@\u0005B\u00111\u0005Q\u0005\u0003\u0003\u0012\u0012qAT8uQ&tw\r\u0005\u0002$\u0007&\u0011A\t\n\u0002\u0004\u0003:L\u0018aC8viB,H\u000fU8siN,\u0012a\u0012\t\u0003G!K!!\u0013\u0013\u0003\u0007%sG/\u0001\u0007pkR\u0004X\u000f\u001e)peR\u001c\b%A\u000bxC&$hi\u001c:BY2$un\u001e8tiJ,\u0017-\\:\u0016\u00035\u0003\"a\t(\n\u0005=##a\u0002\"p_2,\u0017M\\\u0001\u0017o\u0006LGOR8s\u00032dGi\\<ogR\u0014X-Y7tA\u0005YQ-Y4fe\u000e\u000bgnY3m\u00031)\u0017mZ3s\u0007\u0006t7-\u001a7!)\u0011!VKV,\u0011\u0007})!\bC\u0003F\u0019\u0001\u0007q\tC\u0003L\u0019\u0001\u0007Q\nC\u0003R\u0019\u0001\u0007Q*\u0001\u0002j]V\t!\fE\u000287jJ!\u0001X\r\u0003\u000b%sG.\u001a;\u0002\u0007%t\u0007%A\u0002pkR,\u0012\u0001\u0019\t\u0004C\u001aDW\"\u00012\u000b\u0005\r$\u0017!C5n[V$\u0018M\u00197f\u0015\t)G%\u0001\u0006d_2dWm\u0019;j_:L!a\u001a2\u0003\u0015%sG-\u001a=fIN+\u0017\u000fE\u00028SjJ!A[\r\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f\u001e\u0011\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001o!\t9t.\u0003\u0002q3\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003Y\naa\u001d5ba\u0016\u0004\u0013aC2sK\u0006$X\rT8hS\u000e$\"A^=\u0011\u0005E:\u0018B\u0001=3\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"\u0002>\u0015\u0001\u0004q\u0017aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003u\u00042A`A\u0004\u001b\u0005y(\u0002BA\u0001\u0003\u0007\tA\u0001\\1oO*\u0011\u0011QA\u0001\u0005U\u00064\u0018-C\u0002\u0002\n}\u0014aa\u0015;sS:<\u0007cA\u001e\u0002\u000e\u0011)Qh\u0001b\u0001}!)Qi\u0001a\u0001\u000f\"91j\u0001I\u0001\u0002\u0004i\u0015aD1qa2LH\u0005Z3gCVdG\u000f\n\u001a\u0016\t\u0005]\u0011QF\u000b\u0003\u00033Q3!TA\u000eW\t\ti\u0002\u0005\u0003\u0002 \u0005%RBAA\u0011\u0015\u0011\t\u0019#!\n\u0002\u0013Ut7\r[3dW\u0016$'bAA\u0014I\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005-\u0012\u0011\u0005\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,G!B\u001f\u0005\u0005\u0004q\u0004")
public final class Balance<T>
extends GraphStage<UniformFanOutShape<T, T>> {
    private final int outputPorts;
    private final boolean waitForAllDownstreams;
    private final boolean eagerCancel;
    private final Inlet<T> in;
    private final IndexedSeq<Outlet<T>> out;
    private final UniformFanOutShape<T, T> shape;

    public static <T> boolean apply$default$2() {
        return Balance$.MODULE$.apply$default$2();
    }

    public static <T> Balance<T> apply(int outputPorts, boolean waitForAllDownstreams) {
        return Balance$.MODULE$.apply(outputPorts, waitForAllDownstreams);
    }

    public int outputPorts() {
        return this.outputPorts;
    }

    public boolean waitForAllDownstreams() {
        return this.waitForAllDownstreams;
    }

    public boolean eagerCancel() {
        return this.eagerCancel;
    }

    public Inlet<T> in() {
        return this.in;
    }

    public IndexedSeq<Outlet<T>> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.balance();
    }

    @Override
    public UniformFanOutShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private final FixedSizeBuffer.FixedSizeBuffer<Outlet<T>> akka$stream$scaladsl$Balance$$anon$$pendingQueue;
            private int akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls;
            private int akka$stream$scaladsl$Balance$$anon$$downstreamsRunning;
            private final /* synthetic */ Balance $outer;

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public FixedSizeBuffer.FixedSizeBuffer<Outlet<T>> akka$stream$scaladsl$Balance$$anon$$pendingQueue() {
                return this.akka$stream$scaladsl$Balance$$anon$$pendingQueue;
            }

            public boolean akka$stream$scaladsl$Balance$$anon$$noPending() {
                return this.akka$stream$scaladsl$Balance$$anon$$pendingQueue().isEmpty();
            }

            public int akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls() {
                return this.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls;
            }

            public void akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls_$eq(int x$1) {
                this.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls = x$1;
            }

            public int akka$stream$scaladsl$Balance$$anon$$downstreamsRunning() {
                return this.akka$stream$scaladsl$Balance$$anon$$downstreamsRunning;
            }

            public void akka$stream$scaladsl$Balance$$anon$$downstreamsRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$Balance$$anon$$downstreamsRunning = x$1;
            }

            private void dequeueAndDispatch() {
                block5: {
                    BoxedUnit boxedUnit;
                    Outlet<T> out;
                    while ((out = this.akka$stream$scaladsl$Balance$$anon$$pendingQueue().dequeue()) != null) {
                        if (!this.isClosed(out)) {
                            this.push(out, this.grab(this.$outer.in()));
                            if (!this.akka$stream$scaladsl$Balance$$anon$$noPending()) {
                                this.pull(this.$outer.in());
                                boxedUnit = BoxedUnit.UNIT;
                            } else {
                                boxedUnit = BoxedUnit.UNIT;
                            }
                        } else {
                            if (!this.akka$stream$scaladsl$Balance$$anon$$noPending()) continue;
                            boxedUnit = BoxedUnit.UNIT;
                        }
                        break block5;
                    }
                    boxedUnit = BoxedUnit.UNIT;
                }
            }

            public void onPush() {
                this.dequeueAndDispatch();
            }

            public /* synthetic */ Balance akka$stream$scaladsl$Balance$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$new$13($anon$18 $this, Outlet o) {
                $this.setHandler(o, new OutHandler($this, o){
                    private boolean hasPulled;
                    private final /* synthetic */ $anon$18 $outer;
                    private final Outlet o$2;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    private boolean hasPulled() {
                        return this.hasPulled;
                    }

                    private void hasPulled_$eq(boolean x$1) {
                        this.hasPulled = x$1;
                    }

                    public void onPull() {
                        if (!this.hasPulled()) {
                            this.hasPulled_$eq(true);
                            if (this.$outer.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls() > 0) {
                                this.$outer.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls_$eq(this.$outer.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls() - 1);
                            }
                        }
                        if (this.$outer.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls() == 0) {
                            if (this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$Balance$$anon$$$outer().in())) {
                                if (this.$outer.akka$stream$scaladsl$Balance$$anon$$noPending()) {
                                    this.$outer.push(this.o$2, this.$outer.grab(this.$outer.akka$stream$scaladsl$Balance$$anon$$$outer().in()));
                                    return;
                                }
                                return;
                            }
                            if (!this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$Balance$$anon$$$outer().in())) {
                                this.$outer.pull(this.$outer.akka$stream$scaladsl$Balance$$anon$$$outer().in());
                            }
                            this.$outer.akka$stream$scaladsl$Balance$$anon$$pendingQueue().enqueue(this.o$2);
                            return;
                        }
                        this.$outer.akka$stream$scaladsl$Balance$$anon$$pendingQueue().enqueue(this.o$2);
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        if (this.$outer.akka$stream$scaladsl$Balance$$anon$$$outer().eagerCancel()) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        this.$outer.akka$stream$scaladsl$Balance$$anon$$downstreamsRunning_$eq(this.$outer.akka$stream$scaladsl$Balance$$anon$$downstreamsRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$Balance$$anon$$downstreamsRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (!this.hasPulled() && this.$outer.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls() > 0) {
                            this.$outer.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls_$eq(this.$outer.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls() - 1);
                            if (this.$outer.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$Balance$$anon$$$outer().in())) {
                                this.$outer.pull(this.$outer.akka$stream$scaladsl$Balance$$anon$$$outer().in());
                                return;
                            }
                            return;
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.o$2 = o$2;
                        OutHandler.$init$(this);
                        this.hasPulled = false;
                    }
                });
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                this.akka$stream$scaladsl$Balance$$anon$$pendingQueue = FixedSizeBuffer$.MODULE$.apply($outer.outputPorts());
                this.akka$stream$scaladsl$Balance$$anon$$needDownstreamPulls = $outer.waitForAllDownstreams() ? $outer.outputPorts() : 0;
                this.akka$stream$scaladsl$Balance$$anon$$downstreamsRunning = $outer.outputPorts();
                this.setHandler($outer.in(), this);
                $outer.out().foreach((Function1<Outlet, Object> & Serializable)o -> {
                    $anon$18.$anonfun$new$13(this, o);
                    return BoxedUnit.UNIT;
                });
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$new$13$adapted(akka.stream.scaladsl.Balance$$anon$18 akka.stream.Outlet )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "Balance";
    }

    public Balance(int outputPorts, boolean waitForAllDownstreams, boolean eagerCancel) {
        this.outputPorts = outputPorts;
        this.waitForAllDownstreams = waitForAllDownstreams;
        this.eagerCancel = eagerCancel;
        Predef$.MODULE$.require(outputPorts >= 1, (Function0<Object>)(Function0<String> & Serializable)() -> "A Balance must have one or more output ports");
        this.in = Inlet$.MODULE$.apply("Balance.in");
        this.out = (IndexedSeq)package$.MODULE$.Vector().tabulate(outputPorts, (Function1<Object, Outlet> & Serializable)i -> Outlet$.MODULE$.apply(new StringBuilder(11).append("Balance.out").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.shape = UniformFanOutShape$.MODULE$.apply(this.in(), this.out());
    }
}

