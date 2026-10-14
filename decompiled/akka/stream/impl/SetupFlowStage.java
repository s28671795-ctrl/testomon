/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Materializer;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.SetupFlowStage$;
import akka.stream.impl.SetupStage$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005Ea!\u0002\u0007\u000e\u0005=\u0019\u0002\u0002\u0003\u001e\u0001\u0005\u0003\u0005\u000b\u0011B\u001e\t\u000b)\u0003A\u0011A&\t\u000f=\u0003!\u0019!C\u0005!\"1A\u000b\u0001Q\u0001\nECq!\u0016\u0001C\u0002\u0013%a\u000b\u0003\u0004[\u0001\u0001\u0006Ia\u0016\u0005\b7\u0002\u0011\r\u0011\"\u0011]\u0011\u0019i\u0006\u0001)A\u00057!)a\f\u0001C)?\")\u0001\r\u0001C!C\")!\u000e\u0001C\u0005W\nq1+\u001a;va\u001acwn^*uC\u001e,'B\u0001\b\u0010\u0003\u0011IW\u000e\u001d7\u000b\u0005A\t\u0012AB:ue\u0016\fWNC\u0001\u0013\u0003\u0011\t7n[1\u0016\tQ\ts\u0006O\n\u0003\u0001U\u0001BAF\r\u001cc5\tqC\u0003\u0002\u0019\u001f\u0005)1\u000f^1hK&\u0011!d\u0006\u0002 \u000fJ\f\u0007\u000f[*uC\u001e,w+\u001b;i\u001b\u0006$XM]5bY&TX\r\u001a,bYV,\u0007\u0003\u0002\u000f\u001e?9j\u0011aD\u0005\u0003==\u0011\u0011B\u00127poNC\u0017\r]3\u0011\u0005\u0001\nC\u0002\u0001\u0003\u0006E\u0001\u0011\r\u0001\n\u0002\u0002)\u000e\u0001\u0011CA\u0013,!\t1\u0013&D\u0001(\u0015\u0005A\u0013!B:dC2\f\u0017B\u0001\u0016(\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\n\u0017\n\u00055:#aA!osB\u0011\u0001e\f\u0003\u0006a\u0001\u0011\r\u0001\n\u0002\u0002+B\u0019!'N\u001c\u000e\u0003MR!\u0001N\u0014\u0002\u0015\r|gnY;se\u0016tG/\u0003\u00027g\t1a)\u001e;ve\u0016\u0004\"\u0001\t\u001d\u0005\u000be\u0002!\u0019\u0001\u0013\u0003\u00035\u000bqAZ1di>\u0014\u0018\u0010E\u0003'yy\nE)\u0003\u0002>O\tIa)\u001e8di&|gN\r\t\u00039}J!\u0001Q\b\u0003\u00195\u000bG/\u001a:jC2L'0\u001a:\u0011\u0005q\u0011\u0015BA\"\u0010\u0005)\tE\u000f\u001e:jEV$Xm\u001d\t\u0006\u000b\"{bfN\u0007\u0002\r*\u0011qiD\u0001\tg\u000e\fG.\u00193tY&\u0011\u0011J\u0012\u0002\u0005\r2|w/\u0001\u0004=S:LGO\u0010\u000b\u0003\u0019:\u0003R!\u0014\u0001 ]]j\u0011!\u0004\u0005\u0006u\t\u0001\raO\u0001\u0003S:,\u0012!\u0015\t\u00049I{\u0012BA*\u0010\u0005\u0015Ie\u000e\\3u\u0003\rIg\u000eI\u0001\u0004_V$X#A,\u0011\u0007qAf&\u0003\u0002Z\u001f\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\t1$\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A!\u0002?\r\u0014X-\u0019;f\u0019><\u0017nY!oI6\u000bG/\u001a:jC2L'0\u001a3WC2,X\r\u0006\u0002cQB!aeY32\u0013\t!wE\u0001\u0004UkBdWM\r\t\u0003-\u0019L!aZ\f\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDQ!\u001b\u0006A\u0002\u0005\u000b1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001c\u0019:fCR,7\u000b^1hK2{w-[2\u0015\u00051d(CA7f\r\u0011q7\u0002\u00017\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \t\u000fAl'\u0019!C\u0001c\u0006A1/\u001e2J]2,G/F\u0001s!\r\u0019HOL\u0007\u0002[&\u0011QO\u001a\u0002\r'V\u00147+\u001b8l\u0013:dW\r\u001e\u0005\bo6\u0014\r\u0011\"\u0001y\u0003%\u0019XOY(vi2,G/F\u0001z!\r\u0019(pH\u0005\u0003w\u001a\u0014qbU;c'>,(oY3PkRdW\r\u001e\u0005\u0006{.\u0001\rA`\u0001\u000b[\u0006$\bK]8nSN,\u0007c\u0001\u001a\u0000o%\u0019\u0011\u0011A\u001a\u0003\u000fA\u0013x.\\5tK\"\u001a\u0001!!\u0002\u0011\t\u0005\u001d\u0011QB\u0007\u0003\u0003\u0013Q1!a\u0003\u0012\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u001f\tIAA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class SetupFlowStage<T, U, M>
extends GraphStageWithMaterializedValue<FlowShape<T, U>, Future<M>> {
    public final Function2<Materializer, Attributes, Flow<T, U, M>> akka$stream$impl$SetupFlowStage$$factory;
    private final Inlet<T> akka$stream$impl$SetupFlowStage$$in;
    private final Outlet<U> akka$stream$impl$SetupFlowStage$$out;
    private final FlowShape<T, U> shape;

    public Inlet<T> akka$stream$impl$SetupFlowStage$$in() {
        return this.akka$stream$impl$SetupFlowStage$$in;
    }

    public Outlet<U> akka$stream$impl$SetupFlowStage$$out() {
        return this.akka$stream$impl$SetupFlowStage$$out;
    }

    @Override
    public FlowShape<T, U> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("setup").and(Attributes$SourceLocation$.MODULE$.forLambda(this.akka$stream$impl$SetupFlowStage$$factory));
    }

    @Override
    public Tuple2<GraphStageLogic, Future<M>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise matPromise = Promise$.MODULE$.apply();
        return new Tuple2<GraphStageLogic, Future<M>>(this.createStageLogic(matPromise), matPromise.future());
    }

    private GraphStageLogic createStageLogic(Promise<M> matPromise) {
        return new GraphStageLogic(this, matPromise){
            private final GraphStageLogic.SubSinkInlet<U> subInlet;
            private final GraphStageLogic.SubSourceOutlet<T> subOutlet;
            private final /* synthetic */ SetupFlowStage $outer;
            private final Promise matPromise$1;

            public GraphStageLogic.SubSinkInlet<U> subInlet() {
                return this.subInlet;
            }

            public GraphStageLogic.SubSourceOutlet<T> subOutlet() {
                return this.subOutlet;
            }

            public void preStart() {
                try {
                    Flow<T, U, M> flow = this.$outer.akka$stream$impl$SetupFlowStage$$factory.apply(this.materializer(), this.attributes());
                    Mat mat = this.subFusingMaterializer().materialize(((Source)Source$.MODULE$.fromGraph(this.subOutlet().source()).viaMat(flow, Keep$.MODULE$.right())).to(Sink$.MODULE$.fromGraph(this.subInlet().sink())), this.attributes());
                    this.matPromise$1.success(mat);
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        this.matPromise$1.failure(ex);
                        throw ex;
                    }
                    throw throwable;
                }
            }

            public static final /* synthetic */ void $anonfun$new$1($anon$1 $this, Object x$1) {
                $this.push($this.$outer.akka$stream$impl$SetupFlowStage$$out(), x$1);
            }

            public static final /* synthetic */ void $anonfun$new$3($anon$1 $this, Throwable x$2) {
                $this.fail($this.$outer.akka$stream$impl$SetupFlowStage$$out(), x$2);
            }

            public static final /* synthetic */ void $anonfun$new$5($anon$1 $this, Throwable cause) {
                $this.cancel($this.$outer.akka$stream$impl$SetupFlowStage$$in(), cause);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.matPromise$1 = matPromise$1;
                super($outer.shape());
                this.subInlet = new GraphStageLogic.SubSinkInlet<T>(this, "SetupFlowStage");
                this.subOutlet = new GraphStageLogic.SubSourceOutlet<T>(this, "SetupFlowStage");
                this.subInlet().setHandler(SetupStage$.MODULE$.delegateToOutlet((Function1<Object, Object> & Serializable)x$1 -> {
                    $anon$1.$anonfun$new$1(this, x$1);
                    return BoxedUnit.UNIT;
                }, () -> this.complete($this.$outer.akka$stream$impl$SetupFlowStage$$out()), (Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)x$2 -> {
                    $anon$1.$anonfun$new$3(this, x$2);
                    return BoxedUnit.UNIT;
                }, this.subInlet()));
                this.subOutlet().setHandler(SetupStage$.MODULE$.delegateToInlet(() -> this.pull($this.$outer.akka$stream$impl$SetupFlowStage$$in()), (Function1<Throwable, BoxedUnit>)(Function1<Throwable, Object> & Serializable)cause -> {
                    $anon$1.$anonfun$new$5(this, cause);
                    return BoxedUnit.UNIT;
                }));
                this.setHandler($outer.akka$stream$impl$SetupFlowStage$$in(), SetupStage$.MODULE$.delegateToSubOutlet((Function0<Object> & Serializable)() -> this.grab($this.$outer.akka$stream$impl$SetupFlowStage$$in()), this.subOutlet()));
                this.setHandler($outer.akka$stream$impl$SetupFlowStage$$out(), SetupStage$.MODULE$.delegateToSubInlet(this.subInlet()));
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$new$1$adapted(akka.stream.impl.SetupFlowStage$$anon$1 java.lang.Object ), $anonfun$new$2(akka.stream.impl.SetupFlowStage$$anon$1 ), $anonfun$new$3$adapted(akka.stream.impl.SetupFlowStage$$anon$1 java.lang.Throwable ), $anonfun$new$4(akka.stream.impl.SetupFlowStage$$anon$1 ), $anonfun$new$5$adapted(akka.stream.impl.SetupFlowStage$$anon$1 java.lang.Throwable ), $anonfun$new$6(akka.stream.impl.SetupFlowStage$$anon$1 )}, serializedLambda);
            }
        };
    }

    public SetupFlowStage(Function2<Materializer, Attributes, Flow<T, U, M>> factory2) {
        this.akka$stream$impl$SetupFlowStage$$factory = factory2;
        this.akka$stream$impl$SetupFlowStage$$in = Inlet$.MODULE$.apply("SetupFlowStage.in");
        this.akka$stream$impl$SetupFlowStage$$out = Outlet$.MODULE$.apply("SetupFlowStage.out");
        this.shape = new FlowShape<T, U>(this.akka$stream$impl$SetupFlowStage$$in(), this.akka$stream$impl$SetupFlowStage$$out());
    }
}

