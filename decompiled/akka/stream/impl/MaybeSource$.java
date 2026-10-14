/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.MaybeSource$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@InternalApi
public final class MaybeSource$
extends GraphStageWithMaterializedValue<SourceShape<Object>, Promise<Option<Object>>> {
    public static final MaybeSource$ MODULE$ = new MaybeSource$();
    private static final Outlet<Object> out = Outlet$.MODULE$.apply("MaybeSource.out");
    private static final SourceShape<Object> shape = new SourceShape<Object>(MODULE$.out());

    public Outlet<Object> out() {
        return out;
    }

    @Override
    public SourceShape<Object> shape() {
        return shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.maybeSource();
    }

    @Override
    public Tuple2<GraphStageLogic, Promise<Option<Object>>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        Promise promise = Promise$.MODULE$.apply();
        OutHandler logic = new OutHandler(promise){
            private Object arrivedEarly;
            private final Promise promise$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            private Object arrivedEarly() {
                return this.arrivedEarly;
            }

            private void arrivedEarly_$eq(Object x$1) {
                this.arrivedEarly = x$1;
            }

            public void preStart() {
                Option<Try<T>> option = this.promise$1.future().value();
                if (option instanceof Some) {
                    Some some = (Some)option;
                    Try value = (Try)some.value();
                    this.handleCompletion(value);
                    return;
                }
                if (None$.MODULE$.equals(option)) {
                    AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)elem -> {
                        anon.1.$anonfun$preStart$1(this, elem);
                        return BoxedUnit.UNIT;
                    });
                    this.promise$1.future().onComplete((Function1<Try, Object> & Serializable)t2 -> {
                        anon.1.$anonfun$preStart$2(asyncCallback, t2);
                        return BoxedUnit.UNIT;
                    }, ExecutionContexts$.MODULE$.parasitic());
                    return;
                }
                throw new MatchError(option);
            }

            public void onPull() {
                Object object = this.arrivedEarly();
                Object object2 = OptionVal$Some$.MODULE$.unapply(object);
                if (!OptionVal$.MODULE$.isEmpty$extension(object2)) {
                    Object value = OptionVal$.MODULE$.get$extension(object2);
                    this.push(MaybeSource$.MODULE$.out(), value);
                    this.completeStage();
                    return;
                }
            }

            private void handleCompletion(Try<Option<Object>> elem) {
                Option option;
                boolean bl = false;
                Success success = null;
                Try<Option<Object>> try_ = elem;
                if (try_ instanceof Success) {
                    bl = true;
                    success = (Success)try_;
                    Option option2 = (Option)success.value();
                    if (None$.MODULE$.equals(option2)) {
                        this.completeStage();
                        return;
                    }
                }
                if (bl && (option = (Option)success.value()) instanceof Some) {
                    Some some = (Some)option;
                    A value = some.value();
                    if (this.isAvailable(MaybeSource$.MODULE$.out())) {
                        this.push(MaybeSource$.MODULE$.out(), value);
                        this.completeStage();
                        return;
                    }
                    this.arrivedEarly_$eq(OptionVal$Some$.MODULE$.apply(value));
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable ex = failure.exception();
                    this.failStage(ex);
                    return;
                }
                throw new MatchError(try_);
            }

            public void onDownstreamFinish(Throwable cause) {
                this.promise$1.tryComplete(new Success<None$>(None$.MODULE$));
            }

            public void postStop() {
                if (!this.promise$1.isCompleted()) {
                    this.promise$1.tryFailure(new AbruptStageTerminationException(this));
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$preStart$1(anon.1 $this, Try elem) {
                $this.handleCompletion(elem);
            }

            public static final /* synthetic */ void $anonfun$preStart$2(AsyncCallback eta$0$1$1, Try t2) {
                eta$0$1$1.invoke(t2);
            }
            {
                this.promise$1 = promise$1;
                super(MaybeSource$.MODULE$.shape());
                OutHandler.$init$(this);
                OptionVal$.MODULE$.None();
                this.arrivedEarly = null;
                this.setHandler(MaybeSource$.MODULE$.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$1$adapted(akka.stream.impl.MaybeSource$$anon$1 scala.util.Try ), $anonfun$preStart$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
        return new Tuple2<GraphStageLogic, Promise<Option<Object>>>((GraphStageLogic)((Object)logic), promise);
    }

    public String toString() {
        return "MaybeSource";
    }

    private MaybeSource$() {
    }
}

