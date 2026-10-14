/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.Done;
import akka.Done$;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.StreamDetachedException;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.UnfoldResourceSourceAsync$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Some;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005u4Q\u0001D\u0007\u0003#MA\u0001B\f\u0001\u0003\u0002\u0003\u0006Ia\f\u0005\tw\u0001\u0011\t\u0011)A\u0005y!A1\t\u0001B\u0001B\u0003%A\tC\u0003K\u0001\u0011\u00051\nC\u0004R\u0001\t\u0007I\u0011\u0001*\t\rY\u0003\u0001\u0015!\u0003T\u0011\u001d9\u0006A1A\u0005BaCa!\u0017\u0001!\u0002\u0013Y\u0002\"\u0002.\u0001\t\u0003Z\u0006\"B0\u0001\t\u0003\u0001\u0007\"\u00027\u0001\t\u0003j'!G+oM>dGMU3t_V\u00148-Z*pkJ\u001cW-Q:z]\u000eT!AD\b\u0002\t%l\u0007\u000f\u001c\u0006\u0003!E\taa\u001d;sK\u0006l'\"\u0001\n\u0002\t\u0005\\7.Y\u000b\u0004)e\n3C\u0001\u0001\u0016!\r1\u0012dG\u0007\u0002/)\u0011\u0001dD\u0001\u0006gR\fw-Z\u0005\u00035]\u0011!b\u0012:ba\"\u001cF/Y4f!\raRdH\u0007\u0002\u001f%\u0011ad\u0004\u0002\f'>,(oY3TQ\u0006\u0004X\r\u0005\u0002!C1\u0001A!\u0002\u0012\u0001\u0005\u0004!#!\u0001+\u0004\u0001E\u0011Qe\u000b\t\u0003M%j\u0011a\n\u0006\u0002Q\u0005)1oY1mC&\u0011!f\n\u0002\b\u001d>$\b.\u001b8h!\t1C&\u0003\u0002.O\t\u0019\u0011I\\=\u0002\r\r\u0014X-\u0019;f!\r1\u0003GM\u0005\u0003c\u001d\u0012\u0011BR;oGRLwN\u001c\u0019\u0011\u0007M2\u0004(D\u00015\u0015\t)t%\u0001\u0006d_:\u001cWO\u001d:f]RL!a\u000e\u001b\u0003\r\u0019+H/\u001e:f!\t\u0001\u0013\bB\u0003;\u0001\t\u0007AEA\u0001S\u0003!\u0011X-\u00193ECR\f\u0007\u0003\u0002\u0014>q}J!AP\u0014\u0003\u0013\u0019+hn\u0019;j_:\f\u0004cA\u001a7\u0001B\u0019a%Q\u0010\n\u0005\t;#AB(qi&|g.A\u0003dY>\u001cX\r\u0005\u0003'{a*\u0005cA\u001a7\rB\u0011q\tS\u0007\u0002#%\u0011\u0011*\u0005\u0002\u0005\t>tW-\u0001\u0004=S:LGO\u0010\u000b\u0005\u0019:{\u0005\u000b\u0005\u0003N\u0001azR\"A\u0007\t\u000b9\"\u0001\u0019A\u0018\t\u000bm\"\u0001\u0019\u0001\u001f\t\u000b\r#\u0001\u0019\u0001#\u0002\u0007=,H/F\u0001T!\raBkH\u0005\u0003+>\u0011aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\u0012aG\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001]!\taR,\u0003\u0002_\u001f\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0003C*\u00142A\u00193h\r\u0011\u0019'\u0002A1\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0011\u0005Y)\u0017B\u00014\u0018\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007C\u0001\fi\u0013\tIwC\u0001\u0006PkRD\u0015M\u001c3mKJDQa\u001b\u0006A\u0002q\u000b1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002]B\u0011q\u000e^\u0007\u0002a*\u0011\u0011O]\u0001\u0005Y\u0006twMC\u0001t\u0003\u0011Q\u0017M^1\n\u0005U\u0004(AB*ue&tw\r\u000b\u0002\u0001oB\u0011\u0001p_\u0007\u0002s*\u0011!0E\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001?z\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class UnfoldResourceSourceAsync<R, T>
extends GraphStage<SourceShape<T>> {
    public final Function0<Future<R>> akka$stream$impl$UnfoldResourceSourceAsync$$create;
    public final Function1<R, Future<Option<T>>> akka$stream$impl$UnfoldResourceSourceAsync$$readData;
    public final Function1<R, Future<Done>> akka$stream$impl$UnfoldResourceSourceAsync$$close;
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
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.unfoldResourceSourceAsync().and(Attributes$SourceLocation$.MODULE$.forLambda(this.akka$stream$impl$UnfoldResourceSourceAsync$$create));
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> akka$stream$impl$UnfoldResourceSourceAsync$$anon$$decider;
            private R maybeResource;
            private final Function1<Try<R>, Future<Done>> createdCallback;
            private final PartialFunction<Throwable, BoxedUnit> errorHandler;
            private final Function1<Try<Option<T>>, BoxedUnit> readCallback;
            private volatile boolean bitmap$0;
            private final /* synthetic */ UnfoldResourceSourceAsync $outer;
            private Attributes inheritedAttributes$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$1 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.akka$stream$impl$UnfoldResourceSourceAsync$$anon$$decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$1.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$1 = null;
                return this.akka$stream$impl$UnfoldResourceSourceAsync$$anon$$decider;
            }

            public Function1<Throwable, Supervision.Directive> akka$stream$impl$UnfoldResourceSourceAsync$$anon$$decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.akka$stream$impl$UnfoldResourceSourceAsync$$anon$$decider;
            }

            private ExecutionContext ec() {
                return this.materializer().executionContext();
            }

            private R maybeResource() {
                return this.maybeResource;
            }

            private void maybeResource_$eq(R x$1) {
                this.maybeResource = x$1;
            }

            private Function1<Try<R>, Future<Done>> createdCallback() {
                return this.createdCallback;
            }

            private PartialFunction<Throwable, BoxedUnit> errorHandler() {
                return this.errorHandler;
            }

            private Function1<Try<Option<T>>, BoxedUnit> readCallback() {
                return this.readCallback;
            }

            private void handle(Try<Option<T>> result) {
                Try<Option<T>> try_ = result;
                if (try_ instanceof Success) {
                    Success success = (Success)try_;
                    Option data = (Option)success.value();
                    Option option = data;
                    if (option instanceof Some) {
                        Some some = (Some)option;
                        A d = some.value();
                        this.push(this.$outer.out(), d);
                    } else if (None$.MODULE$.equals(option)) {
                        R r = this.maybeResource();
                        R r2 = OptionVal$Some$.MODULE$.unapply(r);
                        if (OptionVal$.MODULE$.isEmpty$extension(r2)) {
                            throw new IllegalStateException("Reached end of data but there is no open resource");
                        }
                        R resource = OptionVal$.MODULE$.get$extension(r2);
                        AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)x0$1 -> {
                            $anon$1.$anonfun$handle$1(this, x0$1);
                            return BoxedUnit.UNIT;
                        });
                        this.$outer.akka$stream$impl$UnfoldResourceSourceAsync$$close.apply(resource).onComplete((Function1<Try, Object> & Serializable)t2 -> {
                            $anon$1.$anonfun$handle$2(asyncCallback, t2);
                            return BoxedUnit.UNIT;
                        }, ExecutionContexts$.MODULE$.parasitic());
                        this.maybeResource_$eq(OptionVal$.MODULE$.none());
                    } else {
                        throw new MatchError(option);
                    }
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable t3 = failure.exception();
                    BoxedUnit cfr_ignored_0 = (BoxedUnit)this.errorHandler().apply(t3);
                    return;
                }
                throw new MatchError(try_);
            }

            public void preStart() {
                this.createResource();
            }

            public void onPull() {
                R r = this.maybeResource();
                R r2 = OptionVal$Some$.MODULE$.unapply(r);
                if (!OptionVal$.MODULE$.isEmpty$extension(r2)) {
                    BoxedUnit boxedUnit;
                    R resource = OptionVal$.MODULE$.get$extension(r2);
                    try {
                        Future<Option<Option<T>>> future = this.$outer.akka$stream$impl$UnfoldResourceSourceAsync$$readData.apply(resource);
                        Option<Try<Option<T>>> option = future.value();
                        if (option instanceof Some) {
                            Some some = (Some)option;
                            Try value = (Try)some.value();
                            this.handle(value);
                        } else if (None$.MODULE$.equals(option)) {
                            future.onComplete(this.readCallback(), ExecutionContexts$.MODULE$.parasitic());
                        } else {
                            throw new MatchError(option);
                        }
                        boxedUnit = BoxedUnit.UNIT;
                    }
                    catch (Throwable x$1) {
                        PartialFunction<Throwable, BoxedUnit> catchExpr$1 = this.errorHandler();
                        if (catchExpr$1.isDefinedAt(x$1)) {
                            boxedUnit = (BoxedUnit)catchExpr$1.apply(x$1);
                        }
                        throw x$1;
                    }
                    return;
                }
            }

            public void postStop() {
                R r = this.maybeResource();
                R r2 = OptionVal$Some$.MODULE$.unapply(r);
                if (!OptionVal$.MODULE$.isEmpty$extension(r2)) {
                    R resource = OptionVal$.MODULE$.get$extension(r2);
                    this.$outer.akka$stream$impl$UnfoldResourceSourceAsync$$close.apply(resource);
                    return;
                }
            }

            public void akka$stream$impl$UnfoldResourceSourceAsync$$anon$$restartResource() {
                R r = this.maybeResource();
                R r2 = OptionVal$Some$.MODULE$.unapply(r);
                if (!OptionVal$.MODULE$.isEmpty$extension(r2)) {
                    R resource = OptionVal$.MODULE$.get$extension(r2);
                    AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)x0$1 -> {
                        $anon$1.$anonfun$restartResource$1(this, x0$1);
                        return BoxedUnit.UNIT;
                    });
                    this.$outer.akka$stream$impl$UnfoldResourceSourceAsync$$close.apply(resource).onComplete((Function1<Try, Object> & Serializable)t2 -> {
                        $anon$1.$anonfun$restartResource$2(asyncCallback, t2);
                        return BoxedUnit.UNIT;
                    }, ExecutionContexts$.MODULE$.parasitic());
                    this.maybeResource_$eq(OptionVal$.MODULE$.none());
                    return;
                }
                this.createResource();
            }

            private void createResource() {
                this.$outer.akka$stream$impl$UnfoldResourceSourceAsync$$create.apply().onComplete((Function1<Try, Object> & Serializable)resource -> {
                    $anon$1.$anonfun$createResource$1(this, resource);
                    return BoxedUnit.UNIT;
                }, ExecutionContexts$.MODULE$.parasitic());
            }

            public static final /* synthetic */ void $anonfun$createdCallback$1($anon$1 $this, Try x0$1) {
                Try try_ = x0$1;
                if (try_ instanceof Success) {
                    Success success = (Success)try_;
                    T resource = success.value();
                    Predef$.MODULE$.require(resource != null, (Function0<Object>)(Function0<String> & Serializable)() -> "`create` method must not return a null resource.");
                    $this.maybeResource_$eq(OptionVal$.MODULE$.apply(resource));
                    if ($this.isAvailable($this.$outer.out())) {
                        $this.onPull();
                        return;
                    }
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable t2 = failure.exception();
                    $this.failStage(t2);
                    return;
                }
                throw new MatchError(try_);
            }

            public static final /* synthetic */ void $anonfun$readCallback$1($anon$1 $this, Try result) {
                $this.handle(result);
            }

            public static final /* synthetic */ void $anonfun$readCallback$2(AsyncCallback eta$0$2$1, Try t2) {
                eta$0$2$1.invoke(t2);
            }

            public static final /* synthetic */ void $anonfun$handle$1($anon$1 $this, Try x0$1) {
                Success success;
                Done done;
                Try try_ = x0$1;
                if (try_ instanceof Success && Done$.MODULE$.equals(done = (Done)(success = (Success)try_).value())) {
                    $this.completeStage();
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable ex = failure.exception();
                    $this.failStage(ex);
                    return;
                }
                throw new MatchError(try_);
            }

            public static final /* synthetic */ void $anonfun$handle$2(AsyncCallback eta$0$1$2, Try t2) {
                eta$0$1$2.invoke(t2);
            }

            public static final /* synthetic */ void $anonfun$restartResource$1($anon$1 $this, Try x0$1) {
                Success success;
                Done done;
                Try try_ = x0$1;
                if (try_ instanceof Success && Done$.MODULE$.equals(done = (Done)(success = (Success)try_).value())) {
                    $this.createResource();
                    return;
                }
                if (try_ instanceof Failure) {
                    Failure failure = (Failure)try_;
                    Throwable ex = failure.exception();
                    $this.failStage(ex);
                    return;
                }
                throw new MatchError(try_);
            }

            public static final /* synthetic */ void $anonfun$restartResource$2(AsyncCallback eta$0$1$3, Try t2) {
                eta$0$1$3.invoke(t2);
            }

            public static final /* synthetic */ void $anonfun$createResource$1($anon$1 $this, Try resource) {
                $this.createdCallback().apply(resource).failed().foreach((Function1<Throwable, Object> & Serializable)x0$1 -> {
                    Throwable throwable = x0$1;
                    if (throwable instanceof StreamDetachedException) {
                        Try try_ = resource;
                        if (try_ instanceof Success) {
                            Success success = (Success)try_;
                            T r = success.value();
                            return $this.$outer.akka$stream$impl$UnfoldResourceSourceAsync$$close.apply(r);
                        }
                        if (try_ instanceof Failure) {
                            Failure failure = (Failure)try_;
                            Throwable ex = failure.exception();
                            throw ex;
                        }
                        throw new MatchError(try_);
                    }
                    return BoxedUnit.UNIT;
                }, $this.ec());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                OutHandler.$init$(this);
                this.maybeResource = OptionVal$.MODULE$.none();
                AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$createdCallback$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.createdCallback = (Function1<Try, Future> & Serializable)t2 -> asyncCallback.invokeWithFeedback(t2);
                this.errorHandler = new Serializable(this){
                    private static final long serialVersionUID = 0L;
                    private final /* synthetic */ $anon$1 $outer;

                    public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                        Option<Throwable> option;
                        A1 A1 = x1;
                        if (A1 != null && !(option = NonFatal$.MODULE$.unapply(A1)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.$outer.akka$stream$impl$UnfoldResourceSourceAsync$$anon$$decider().apply(ex);
                            if (Supervision$Stop$.MODULE$.equals(directive)) {
                                this.$outer.failStage(ex);
                            } else if (Supervision$Restart$.MODULE$.equals(directive)) {
                                BoxedUnit boxedUnit;
                                try {
                                    this.$outer.akka$stream$impl$UnfoldResourceSourceAsync$$anon$$restartResource();
                                    boxedUnit = BoxedUnit.UNIT;
                                }
                                catch (Throwable throwable) {
                                    Option<Throwable> option2;
                                    Throwable throwable2 = throwable;
                                    if (throwable2 == null || (option2 = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                                        throw throwable;
                                    }
                                    Throwable ex2 = option2.get();
                                    this.$outer.failStage(ex2);
                                    boxedUnit = BoxedUnit.UNIT;
                                }
                            } else if (Supervision$Resume$.MODULE$.equals(directive)) {
                                this.$outer.onPull();
                            } else {
                                throw new MatchError(directive);
                            }
                            return (B1)BoxedUnit.UNIT;
                        }
                        return function1.apply(x1);
                    }

                    public final boolean isDefinedAt(Throwable x1) {
                        Option<Throwable> option;
                        Throwable throwable = x1;
                        return throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                    }
                };
                AsyncCallback<T> asyncCallback2 = this.getAsyncCallback((Function1<Try, Object> & Serializable)result -> {
                    $anon$1.$anonfun$readCallback$1(this, result);
                    return BoxedUnit.UNIT;
                });
                this.readCallback = (Function1<Try, Object> & Serializable)t2 -> {
                    $anon$1.$anonfun$readCallback$2(asyncCallback2, t2);
                    return BoxedUnit.UNIT;
                };
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$createResource$1$adapted(akka.stream.impl.UnfoldResourceSourceAsync$$anon$1 scala.util.Try ), $anonfun$createResource$2(akka.stream.impl.UnfoldResourceSourceAsync$$anon$1 scala.util.Try java.lang.Throwable ), $anonfun$createdCallback$1$adapted(akka.stream.impl.UnfoldResourceSourceAsync$$anon$1 scala.util.Try ), $anonfun$createdCallback$2(), $anonfun$createdCallback$3(akka.stream.stage.AsyncCallback scala.util.Try ), $anonfun$handle$1$adapted(akka.stream.impl.UnfoldResourceSourceAsync$$anon$1 scala.util.Try ), $anonfun$handle$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try ), $anonfun$readCallback$1$adapted(akka.stream.impl.UnfoldResourceSourceAsync$$anon$1 scala.util.Try ), $anonfun$readCallback$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try ), $anonfun$restartResource$1$adapted(akka.stream.impl.UnfoldResourceSourceAsync$$anon$1 scala.util.Try ), $anonfun$restartResource$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "UnfoldResourceSourceAsync";
    }

    public UnfoldResourceSourceAsync(Function0<Future<R>> create, Function1<R, Future<Option<T>>> readData, Function1<R, Future<Done>> close) {
        this.akka$stream$impl$UnfoldResourceSourceAsync$$create = create;
        this.akka$stream$impl$UnfoldResourceSourceAsync$$readData = readData;
        this.akka$stream$impl$UnfoldResourceSourceAsync$$close = close;
        this.out = Outlet$.MODULE$.apply("UnfoldResourceSourceAsync.out");
        this.shape = new SourceShape<T>(this.out());
    }
}

