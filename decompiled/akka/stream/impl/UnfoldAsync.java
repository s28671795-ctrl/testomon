/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.UnfoldAsync$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
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
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\t4QAC\u0006\u0003\u001fEA\u0001\u0002\f\u0001\u0003\u0002\u0003\u0006I!\f\u0005\ta\u0001\u0011\t\u0011)A\u0005c!)\u0001\t\u0001C\u0001\u0003\"9a\t\u0001b\u0001\n\u00039\u0005BB&\u0001A\u0003%\u0001\nC\u0004M\u0001\t\u0007I\u0011I'\t\r9\u0003\u0001\u0015!\u0003\u001a\u0011\u0015y\u0005\u0001\"\u0011Q\u0011\u0015!\u0006\u0001\"\u0011V\u0005-)fNZ8mI\u0006\u001b\u0018P\\2\u000b\u00051i\u0011\u0001B5na2T!AD\b\u0002\rM$(/Z1n\u0015\u0005\u0001\u0012\u0001B1lW\u0006,2A\u0005\u0018 '\t\u00011\u0003E\u0002\u0015/ei\u0011!\u0006\u0006\u0003-5\tQa\u001d;bO\u0016L!\u0001G\u000b\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rE\u0002\u001b7ui\u0011!D\u0005\u000395\u00111bU8ve\u000e,7\u000b[1qKB\u0011ad\b\u0007\u0001\t\u0015\u0001\u0003A1\u0001#\u0005\u0005)5\u0001A\t\u0003G%\u0002\"\u0001J\u0014\u000e\u0003\u0015R\u0011AJ\u0001\u0006g\u000e\fG.Y\u0005\u0003Q\u0015\u0012qAT8uQ&tw\r\u0005\u0002%U%\u00111&\n\u0002\u0004\u0003:L\u0018!A:\u0011\u0005yqC!B\u0018\u0001\u0005\u0004\u0011#!A*\u0002\u0003\u0019\u0004B\u0001\n\u001a.i%\u00111'\n\u0002\n\rVt7\r^5p]F\u00022!\u000e\u001d;\u001b\u00051$BA\u001c&\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003sY\u0012aAR;ukJ,\u0007c\u0001\u0013<{%\u0011A(\n\u0002\u0007\u001fB$\u0018n\u001c8\u0011\t\u0011rT&H\u0005\u0003\u007f\u0015\u0012a\u0001V;qY\u0016\u0014\u0014A\u0002\u001fj]&$h\bF\u0002C\t\u0016\u0003Ba\u0011\u0001.;5\t1\u0002C\u0003-\u0007\u0001\u0007Q\u0006C\u00031\u0007\u0001\u0007\u0011'A\u0002pkR,\u0012\u0001\u0013\t\u00045%k\u0012B\u0001&\u000e\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;!\u0003\u0015\u0019\b.\u00199f+\u0005I\u0012AB:iCB,\u0007%A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012!\u0015\t\u00035IK!aU\u0007\u0003\u0015\u0005#HO]5ckR,7/A\u0006de\u0016\fG/\u001a'pO&\u001cGC\u0001,Z!\t!r+\u0003\u0002Y+\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0003[\u0013\u0001\u0007\u0011+A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000f\u000b\u0002\u00019B\u0011Q\fY\u0007\u0002=*\u0011qlD\u0001\u000bC:tw\u000e^1uS>t\u0017BA1_\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class UnfoldAsync<S, E>
extends GraphStage<SourceShape<E>> {
    public final S akka$stream$impl$UnfoldAsync$$s;
    public final Function1<S, Future<Option<Tuple2<S, E>>>> akka$stream$impl$UnfoldAsync$$f;
    private final Outlet<E> out;
    private final SourceShape<E> shape;

    public Outlet<E> out() {
        return this.out;
    }

    @Override
    public SourceShape<E> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.unfoldAsync();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private S state;
            private Function1<Try<Option<Tuple2<S, E>>>, BoxedUnit> asyncHandler;
            private final /* synthetic */ UnfoldAsync $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void preStart() {
                AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)result -> {
                    $anon$2.$anonfun$preStart$1(this, result);
                    return BoxedUnit.UNIT;
                });
                this.asyncHandler = (Function1<Try, Object> & Serializable)t2 -> {
                    $anon$2.$anonfun$preStart$2(asyncCallback, t2);
                    return BoxedUnit.UNIT;
                };
            }

            private void handle(Try<Option<Tuple2<S, E>>> result) {
                Option option;
                boolean bl = false;
                Success success = null;
                Try<Option<Tuple2<S, E>>> try_ = result;
                if (try_ instanceof Success) {
                    Some some;
                    Tuple2 tuple2;
                    bl = true;
                    success = (Success)try_;
                    Option option2 = (Option)success.value();
                    if (option2 instanceof Some && (tuple2 = (Tuple2)(some = (Some)option2).value()) != null) {
                        T1 newS = tuple2._1();
                        T2 elem = tuple2._2();
                        this.push(this.$outer.out(), elem);
                        this.state = newS;
                        return;
                    }
                }
                if (bl && None$.MODULE$.equals(option = (Option)success.value())) {
                    this.complete(this.$outer.out());
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable ex = failure.exception();
                    this.fail(this.$outer.out(), ex);
                    return;
                }
                throw new MatchError(try_);
            }

            public void onPull() {
                Future<Option<Tuple2<S, E>>> future = this.$outer.akka$stream$impl$UnfoldAsync$$f.apply(this.state);
                Option<Try<Option<Tuple2<S, E>>>> option = future.value();
                if (option instanceof Some) {
                    Some some = (Some)option;
                    Try value = (Try)some.value();
                    this.handle(value);
                    return;
                }
                if (None$.MODULE$.equals(option)) {
                    future.onComplete(this.asyncHandler, ExecutionContexts$.MODULE$.parasitic());
                    return;
                }
                throw new MatchError(option);
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$2 $this, Try result) {
                $this.handle(result);
            }

            public static final /* synthetic */ void $anonfun$preStart$2(AsyncCallback eta$0$1$1, Try t2) {
                eta$0$1$1.invoke(t2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.state = $outer.akka$stream$impl$UnfoldAsync$$s;
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$1$adapted(akka.stream.impl.UnfoldAsync$$anon$2 scala.util.Try ), $anonfun$preStart$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
    }

    public UnfoldAsync(S s2, Function1<S, Future<Option<Tuple2<S, E>>>> f) {
        this.akka$stream$impl$UnfoldAsync$$s = s2;
        this.akka$stream$impl$UnfoldAsync$$f = f;
        this.out = Outlet$.MODULE$.apply("UnfoldAsync.out");
        this.shape = new SourceShape<E>(this.out());
    }
}

