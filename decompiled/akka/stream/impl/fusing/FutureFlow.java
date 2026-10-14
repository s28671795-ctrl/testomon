/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.FutureFlow$;
import akka.stream.impl.fusing.FutureFlow$$anon$1$Initializing$;
import akka.stream.scaladsl.Flow;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u000554Qa\u0003\u0007\u0003%QA\u0001b\u000f\u0001\u0003\u0002\u0003\u0006I\u0001\u0010\u0005\u0006\u0007\u0002!\t\u0001\u0012\u0005\b\u0011\u0002\u0011\r\u0011\"\u0001J\u0011\u0019i\u0005\u0001)A\u0005\u0015\"9a\n\u0001b\u0001\n\u0003y\u0005BB*\u0001A\u0003%\u0001\u000bC\u0003U\u0001\u0011ES\u000bC\u0004Z\u0001\t\u0007I\u0011\t.\t\rm\u0003\u0001\u0015!\u0003\u001d\u0011\u0015a\u0006\u0001\"\u0011^\u0005)1U\u000f^;sK\u001acwn\u001e\u0006\u0003\u001b9\taAZ;tS:<'BA\b\u0011\u0003\u0011IW\u000e\u001d7\u000b\u0005E\u0011\u0012AB:ue\u0016\fWNC\u0001\u0014\u0003\u0011\t7n[1\u0016\tU\u0011\u0003'O\n\u0003\u0001Y\u0001Ba\u0006\u000e\u001de5\t\u0001D\u0003\u0002\u001a!\u0005)1\u000f^1hK&\u00111\u0004\u0007\u0002 \u000fJ\f\u0007\u000f[*uC\u001e,w+\u001b;i\u001b\u0006$XM]5bY&TX\r\u001a,bYV,\u0007\u0003B\u000f\u001fA=j\u0011\u0001E\u0005\u0003?A\u0011\u0011B\u00127poNC\u0017\r]3\u0011\u0005\u0005\u0012C\u0002\u0001\u0003\u0006G\u0001\u0011\r!\n\u0002\u0003\u0013:\u001c\u0001!\u0005\u0002'YA\u0011qEK\u0007\u0002Q)\t\u0011&A\u0003tG\u0006d\u0017-\u0003\u0002,Q\t9aj\u001c;iS:<\u0007CA\u0014.\u0013\tq\u0003FA\u0002B]f\u0004\"!\t\u0019\u0005\u000bE\u0002!\u0019A\u0013\u0003\u0007=+H\u000fE\u00024maj\u0011\u0001\u000e\u0006\u0003k!\n!bY8oGV\u0014(/\u001a8u\u0013\t9DG\u0001\u0004GkR,(/\u001a\t\u0003Ce\"QA\u000f\u0001C\u0002\u0015\u0012\u0011!T\u0001\u000bMV$XO]3GY><\bcA\u001a7{A)a(\u0011\u00110q5\tqH\u0003\u0002A!\u0005A1oY1mC\u0012\u001cH.\u0003\u0002C\u007f\t!a\t\\8x\u0003\u0019a\u0014N\\5u}Q\u0011Qi\u0012\t\u0006\r\u0002\u0001s\u0006O\u0007\u0002\u0019!)1H\u0001a\u0001y\u0005\u0011\u0011N\\\u000b\u0002\u0015B\u0019Qd\u0013\u0011\n\u00051\u0003\"!B%oY\u0016$\u0018aA5oA\u0005\u0019q.\u001e;\u0016\u0003A\u00032!H)0\u0013\t\u0011\u0006C\u0001\u0004PkRdW\r^\u0001\u0005_V$\b%A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012A\u0016\t\u0003;]K!\u0001\u0017\t\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-F\u0001\u001d\u0003\u0019\u0019\b.\u00199fA\u0005y2M]3bi\u0016dunZ5d\u0003:$W*\u0019;fe&\fG.\u001b>fIZ\u000bG.^3\u0015\u0005y#\u0007\u0003B\u0014`CJJ!\u0001\u0019\u0015\u0003\rQ+\b\u000f\\33!\t9\"-\u0003\u0002d1\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0003f\u0015\u0001\u0007a+A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000f\u000b\u0002\u0001OB\u0011\u0001n[\u0007\u0002S*\u0011!NE\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00017j\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class FutureFlow<In, Out, M>
extends GraphStageWithMaterializedValue<FlowShape<In, Out>, Future<M>> {
    public final Future<Flow<In, Out, M>> akka$stream$impl$fusing$FutureFlow$$futureFlow;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;

    public Inlet<In> in() {
        return this.in;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.futureFlow().and(Attributes$SourceLocation$.MODULE$.forLambda(this.akka$stream$impl$fusing$FutureFlow$$futureFlow));
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public Tuple2<GraphStageLogic, Future<M>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        boolean propagateToNestedMaterialization = ((Attributes.NestedMaterializationCancellationPolicy)inheritedAttributes.mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.NestedMaterializationCancellationPolicy.class))).propagateToNestedMaterialization();
        Promise innerMatValue = Promise$.MODULE$.apply();
        GraphStageLogic logic = new GraphStageLogic(this, innerMatValue, propagateToNestedMaterialization, inheritedAttributes){
            private volatile FutureFlow$$anon$1$Initializing$ Initializing$module;
            private final /* synthetic */ FutureFlow $outer;
            public final Promise innerMatValue$1;
            public final boolean propagateToNestedMaterialization$1;
            public final Attributes inheritedAttributes$1;

            public FutureFlow$$anon$1$Initializing$ Initializing() {
                if (this.Initializing$module == null) {
                    this.Initializing$lzycompute$1();
                }
                return this.Initializing$module;
            }

            public void preStart() {
                Option<Try<Flow<In, Out, M>>> option = this.$outer.akka$stream$impl$fusing$FutureFlow$$futureFlow.value();
                if (option instanceof Some) {
                    Some some = (Some)option;
                    Try tryFlow = (Try)some.value();
                    this.Initializing().onFuture(tryFlow);
                    return;
                }
                if (None$.MODULE$.equals(option)) {
                    AsyncCallback<T> cb = this.getAsyncCallback((Function1<Try, Object> & Serializable)futureRes -> {
                        $anon$1.$anonfun$preStart$1(this, futureRes);
                        return BoxedUnit.UNIT;
                    });
                    this.$outer.akka$stream$impl$fusing$FutureFlow$$futureFlow.onComplete((Function1<Try, Object> & Serializable)t2 -> {
                        $anon$1.$anonfun$preStart$2(cb, t2);
                        return BoxedUnit.UNIT;
                    }, ExecutionContexts$.MODULE$.parasitic());
                    this.setKeepGoing(true);
                    return;
                }
                throw new MatchError(option);
            }

            public void postStop() {
                if (!this.innerMatValue$1.isCompleted()) {
                    this.innerMatValue$1.failure(new AbruptStageTerminationException(this));
                    return;
                }
            }

            public /* synthetic */ FutureFlow akka$stream$impl$fusing$FutureFlow$$anon$$$outer() {
                return this.$outer;
            }

            private final void Initializing$lzycompute$1() {
                $anon$1 var1_1 = this;
                synchronized (var1_1) {
                    if (this.Initializing$module == null) {
                        this.Initializing$module = new FutureFlow$$anon$1$Initializing$(this);
                    }
                }
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$1 $this, Try futureRes) {
                $this.Initializing().onFuture(futureRes);
            }

            public static final /* synthetic */ void $anonfun$preStart$2(AsyncCallback cb$1, Try t2) {
                cb$1.invoke(t2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.innerMatValue$1 = innerMatValue$1;
                this.propagateToNestedMaterialization$1 = propagateToNestedMaterialization$1;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                this.setHandlers($outer.in(), $outer.out(), this.Initializing());
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$1$adapted(akka.stream.impl.fusing.FutureFlow$$anon$1 scala.util.Try ), $anonfun$preStart$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, Future<M>>(logic, innerMatValue.future());
    }

    public FutureFlow(Future<Flow<In, Out, M>> futureFlow) {
        this.akka$stream$impl$fusing$FutureFlow$$futureFlow = futureFlow;
        this.in = Inlet$.MODULE$.apply("FutureFlow.in");
        this.out = Outlet$.MODULE$.apply("FutureFlow.out");
        this.shape = new FlowShape<In, Out>(this.in(), this.out());
    }
}

