/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.NeverMaterializedException;
import akka.stream.SinkShape;
import akka.stream.SinkShape$;
import akka.stream.impl.LazySink$;
import akka.stream.impl.LazySink$$anon$5$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.Sink;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005E4QAC\u0006\u0003\u001bEA\u0001\"\u000e\u0001\u0003\u0002\u0003\u0006IA\u000e\u0005\u0006\u0001\u0002!\t!\u0011\u0005\b\u000b\u0002\u0011\r\u0011\"\u0001G\u0011\u0019Q\u0005\u0001)A\u0005\u000f\")1\n\u0001C!\u0019\"9\u0001\u000b\u0001b\u0001\n\u0003\n\u0006B\u0002*\u0001A\u0003%\u0011\u0004C\u0003T\u0001\u0011\u0005C\u000bC\u0003a\u0001\u0011\u0005\u0013M\u0001\u0005MCjL8+\u001b8l\u0015\taQ\"\u0001\u0003j[Bd'B\u0001\b\u0010\u0003\u0019\u0019HO]3b[*\t\u0001#\u0001\u0003bW.\fWc\u0001\n gM\u0011\u0001a\u0005\t\u0005)]IB&D\u0001\u0016\u0015\t1R\"A\u0003ti\u0006<W-\u0003\u0002\u0019+\tyrI]1qQN#\u0018mZ3XSRDW*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0011\u0007iYR$D\u0001\u000e\u0013\taRBA\u0005TS:\\7\u000b[1qKB\u0011ad\b\u0007\u0001\t\u0015\u0001\u0003A1\u0001#\u0005\u0005!6\u0001A\t\u0003G%\u0002\"\u0001J\u0014\u000e\u0003\u0015R\u0011AJ\u0001\u0006g\u000e\fG.Y\u0005\u0003Q\u0015\u0012qAT8uQ&tw\r\u0005\u0002%U%\u00111&\n\u0002\u0004\u0003:L\bcA\u00171e5\taF\u0003\u00020K\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005Er#A\u0002$viV\u0014X\r\u0005\u0002\u001fg\u0011)A\u0007\u0001b\u0001E\t\tQ*A\u0006tS:\\g)Y2u_JL\b\u0003\u0002\u00138;eJ!\u0001O\u0013\u0003\u0013\u0019+hn\u0019;j_:\f\u0004cA\u00171uA!1HP\u000f3\u001b\u0005a$BA\u001f\u000e\u0003!\u00198-\u00197bINd\u0017BA =\u0005\u0011\u0019\u0016N\\6\u0002\rqJg.\u001b;?)\t\u0011E\t\u0005\u0003D\u0001u\u0011T\"A\u0006\t\u000bU\u0012\u0001\u0019\u0001\u001c\u0002\u0005%tW#A$\u0011\u0007iAU$\u0003\u0002J\u001b\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001N!\tQb*\u0003\u0002P\u001b\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003e\taa\u001d5ba\u0016\u0004\u0013\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003U\u0003\"AV/\u000f\u0005][\u0006C\u0001-&\u001b\u0005I&B\u0001.\"\u0003\u0019a$o\\8u}%\u0011A,J\u0001\u0007!J,G-\u001a4\n\u0005y{&AB*ue&twM\u0003\u0002]K\u0005y2M]3bi\u0016dunZ5d\u0003:$W*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0015\u0005\tD\u0007\u0003\u0002\u0013dK2J!\u0001Z\u0013\u0003\rQ+\b\u000f\\33!\t!b-\u0003\u0002h+\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0003j\u0013\u0001\u0007Q*A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000f\u000b\u0002\u0001WB\u0011An\\\u0007\u0002[*\u0011anD\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00019n\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class LazySink<T, M>
extends GraphStageWithMaterializedValue<SinkShape<T>, Future<M>> {
    public final Function1<T, Future<Sink<T, M>>> akka$stream$impl$LazySink$$sinkFactory;
    private final Inlet<T> in;
    private final SinkShape<T> shape;

    public Inlet<T> in() {
        return this.in;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.lazySink().and(Attributes$SourceLocation$.MODULE$.forLambda(this.akka$stream$impl$LazySink$$sinkFactory));
    }

    @Override
    public SinkShape<T> shape() {
        return this.shape;
    }

    public String toString() {
        return "LazySink";
    }

    @Override
    public Tuple2<GraphStageLogic, Future<M>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise promise = Promise$.MODULE$.apply();
        InHandler stageLogic = new InHandler(this, promise, inheritedAttributes){
            private boolean switching;
            private final /* synthetic */ LazySink $outer;
            private final Promise promise$1;
            private final Attributes inheritedAttributes$2;

            public boolean switching() {
                return this.switching;
            }

            public void switching_$eq(boolean x$1) {
                this.switching = x$1;
            }

            public void preStart() {
                this.pull(this.$outer.in());
            }

            public void onPush() {
                T element = this.grab(this.$outer.in());
                this.switching_$eq(true);
                AsyncCallback<T> cb = this.getAsyncCallback((Function1<Try, Object> & Serializable)x0$1 -> {
                    $anon$5.$anonfun$onPush$1(this, element, x0$1);
                    return BoxedUnit.UNIT;
                });
                try {
                    this.$outer.akka$stream$impl$LazySink$$sinkFactory.apply(element).onComplete((Function1<Try, Object> & Serializable)t2 -> {
                        $anon$5.$anonfun$onPush$2(cb, t2);
                        return BoxedUnit.UNIT;
                    }, ExecutionContexts$.MODULE$.parasitic());
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable e = option.get();
                        this.promise$1.failure(e);
                        this.failStage(e);
                    }
                    throw throwable;
                }
            }

            public void onUpstreamFinish() {
                if (this.switching()) {
                    this.setKeepGoing(true);
                    return;
                }
                this.promise$1.failure(new NeverMaterializedException());
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) {
                this.promise$1.failure(ex);
                InHandler.onUpstreamFailure$(this, ex);
            }

            private M switchTo(Sink<T, M> sink2, T firstElement) {
                BooleanRef firstElementPushed = BooleanRef.create(false);
                GraphStageLogic.SubSourceOutlet<T> subOutlet = new GraphStageLogic.SubSourceOutlet<T>(this, "LazySink");
                Mat matVal = this.interpreter().subFusingMaterializer().materialize(Source$.MODULE$.fromGraph(subOutlet.source()).toMat(sink2, Keep$.MODULE$.right()), this.inheritedAttributes$2);
                this.setKeepGoing(true);
                this.setHandler(this.$outer.in(), new InHandler(this, subOutlet, firstElementPushed){
                    private final /* synthetic */ $anon$5 $outer;
                    private final GraphStageLogic.SubSourceOutlet subOutlet$1;
                    private final BooleanRef firstElementPushed$1;

                    public void onPush() {
                        this.subOutlet$1.push(this.$outer.grab(this.$outer.akka$stream$impl$LazySink$$anon$$$outer().in()));
                    }

                    public void onUpstreamFinish() {
                        if (this.firstElementPushed$1.elem) {
                            this.subOutlet$1.complete();
                            this.$outer.akka$stream$impl$LazySink$$nestedInanon$5$$maybeCompleteStage$1(this.subOutlet$1);
                            return;
                        }
                    }

                    public void onUpstreamFailure(Throwable ex2) {
                        this.subOutlet$1.fail(ex2);
                        this.$outer.getAsyncCallback((Function1<Throwable, Object> & Serializable)ex -> {
                            $anon$5$$anon$6.$anonfun$onUpstreamFailure$1(this, ex);
                            return BoxedUnit.UNIT;
                        }).invoke(ex2);
                    }

                    public static final /* synthetic */ void $anonfun$onUpstreamFailure$1($anon$5$$anon$6 $this, Throwable ex) {
                        $this.$outer.failStage(ex);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.subOutlet$1 = subOutlet$1;
                        this.firstElementPushed$1 = firstElementPushed$1;
                        InHandler.$init$(this);
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onUpstreamFailure$1$adapted(akka.stream.impl.LazySink$$anon$5$$anon$6 java.lang.Throwable )}, serializedLambda);
                    }
                });
                subOutlet.setHandler(new OutHandler(this, firstElementPushed, subOutlet, firstElement){
                    private final /* synthetic */ $anon$5 $outer;
                    private final BooleanRef firstElementPushed$1;
                    private final GraphStageLogic.SubSourceOutlet subOutlet$1;
                    private final Object firstElement$1;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        if (this.firstElementPushed$1.elem) {
                            this.$outer.pull(this.$outer.akka$stream$impl$LazySink$$anon$$$outer().in());
                            return;
                        }
                        this.firstElementPushed$1.elem = true;
                        this.subOutlet$1.push(this.firstElement$1);
                        if (this.$outer.isClosed(this.$outer.akka$stream$impl$LazySink$$anon$$$outer().in())) {
                            this.subOutlet$1.complete();
                            this.$outer.akka$stream$impl$LazySink$$nestedInanon$5$$maybeCompleteStage$1(this.subOutlet$1);
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        if (!this.$outer.isClosed(this.$outer.akka$stream$impl$LazySink$$anon$$$outer().in())) {
                            this.$outer.cancel(this.$outer.akka$stream$impl$LazySink$$anon$$$outer().in(), cause);
                        }
                        this.$outer.akka$stream$impl$LazySink$$nestedInanon$5$$maybeCompleteStage$1(this.subOutlet$1);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.firstElementPushed$1 = firstElementPushed$1;
                        this.subOutlet$1 = subOutlet$1;
                        this.firstElement$1 = firstElement$1;
                        OutHandler.$init$(this);
                    }
                });
                return (M)matVal;
            }

            public /* synthetic */ LazySink akka$stream$impl$LazySink$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$onPush$1($anon$5 $this, Object element$1, Try x0$1) {
                Try try_ = x0$1;
                if (try_ instanceof Success) {
                    Success success = (Success)try_;
                    Sink sink2 = (Sink)success.value();
                    if (!$this.promise$1.isCompleted()) {
                        BoxedUnit boxedUnit;
                        try {
                            M mat = $this.switchTo(sink2, element$1);
                            $this.promise$1.success(mat);
                            $this.setKeepGoing(true);
                            boxedUnit = BoxedUnit.UNIT;
                        }
                        catch (Throwable throwable) {
                            Option<Throwable> option;
                            Throwable throwable2 = throwable;
                            if (throwable2 == null || (option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                                throw throwable;
                            }
                            Throwable e = option.get();
                            $this.promise$1.failure(e);
                            $this.failStage(e);
                            boxedUnit = BoxedUnit.UNIT;
                        }
                        return;
                    }
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable e = failure.exception();
                    $this.promise$1.failure(e);
                    $this.failStage(e);
                    return;
                }
                throw new MatchError(try_);
            }

            public static final /* synthetic */ void $anonfun$onPush$2(AsyncCallback cb$1, Try t2) {
                cb$1.invoke(t2);
            }

            public final void akka$stream$impl$LazySink$$nestedInanon$5$$maybeCompleteStage$1(GraphStageLogic.SubSourceOutlet subOutlet$1) {
                if (this.isClosed(this.$outer.in()) && subOutlet$1.isClosed()) {
                    this.completeStage();
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.promise$1 = promise$1;
                this.inheritedAttributes$2 = inheritedAttributes$2;
                super($outer.shape());
                InHandler.$init$(this);
                this.switching = false;
                this.setHandler($outer.in(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onPush$1$adapted(akka.stream.impl.LazySink$$anon$5 java.lang.Object scala.util.Try ), $anonfun$onPush$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, Future<M>>((GraphStageLogic)((Object)stageLogic), promise.future());
    }

    public LazySink(Function1<T, Future<Sink<T, M>>> sinkFactory) {
        this.akka$stream$impl$LazySink$$sinkFactory = sinkFactory;
        this.in = Inlet$.MODULE$.apply("lazySink.in");
        this.shape = SinkShape$.MODULE$.of(this.in());
    }
}

