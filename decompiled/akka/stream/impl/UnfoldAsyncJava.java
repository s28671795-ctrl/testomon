/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.japi.Pair;
import akka.japi.function.Function;
import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.UnfoldAsyncJava$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import scala.Function1;
import scala.MatchError;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u000554QAC\u0006\u0003\u001fEA\u0001\u0002\f\u0001\u0003\u0002\u0003\u0006I!\f\u0005\ta\u0001\u0011\t\u0011)A\u0005c!)1\n\u0001C\u0001\u0019\"9\u0011\u000b\u0001b\u0001\n\u0003\u0011\u0006B\u0002,\u0001A\u0003%1\u000bC\u0004X\u0001\t\u0007I\u0011\t-\t\re\u0003\u0001\u0015!\u0003\u001a\u0011\u0015Q\u0006\u0001\"\u0011\\\u0011\u0015y\u0006\u0001\"\u0011a\u0005=)fNZ8mI\u0006\u001b\u0018P\\2KCZ\f'B\u0001\u0007\u000e\u0003\u0011IW\u000e\u001d7\u000b\u00059y\u0011AB:ue\u0016\fWNC\u0001\u0011\u0003\u0011\t7n[1\u0016\u0007Iqsd\u0005\u0002\u0001'A\u0019AcF\r\u000e\u0003UQ!AF\u0007\u0002\u000bM$\u0018mZ3\n\u0005a)\"AC$sCBD7\u000b^1hKB\u0019!dG\u000f\u000e\u00035I!\u0001H\u0007\u0003\u0017M{WO]2f'\"\f\u0007/\u001a\t\u0003=}a\u0001\u0001B\u0003!\u0001\t\u0007!EA\u0001F\u0007\u0001\t\"aI\u0015\u0011\u0005\u0011:S\"A\u0013\u000b\u0003\u0019\nQa]2bY\u0006L!\u0001K\u0013\u0003\u000f9{G\u000f[5oOB\u0011AEK\u0005\u0003W\u0015\u00121!\u00118z\u0003\u0005\u0019\bC\u0001\u0010/\t\u0015y\u0003A1\u0001#\u0005\u0005\u0019\u0016!\u00014\u0011\tI:T&O\u0007\u0002g)\u0011A'N\u0001\tMVt7\r^5p]*\u0011agD\u0001\u0005U\u0006\u0004\u0018.\u0003\u00029g\tAa)\u001e8di&|g\u000eE\u0002;\u0003\u000ek\u0011a\u000f\u0006\u0003yu\n!bY8oGV\u0014(/\u001a8u\u0015\tqt(\u0001\u0003vi&d'\"\u0001!\u0002\t)\fg/Y\u0005\u0003\u0005n\u0012qbQ8na2,G/[8o'R\fw-\u001a\t\u0004\t\u0016;U\"A\u001f\n\u0005\u0019k$\u0001C(qi&|g.\u00197\u0011\t!KU&H\u0007\u0002k%\u0011!*\u000e\u0002\u0005!\u0006L'/\u0001\u0004=S:LGO\u0010\u000b\u0004\u001b>\u0003\u0006\u0003\u0002(\u0001[ui\u0011a\u0003\u0005\u0006Y\r\u0001\r!\f\u0005\u0006a\r\u0001\r!M\u0001\u0004_V$X#A*\u0011\u0007i!V$\u0003\u0002V\u001b\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\t\u0011$\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#\u0001/\u0011\u0005ii\u0016B\u00010\u000e\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002bIB\u0011ACY\u0005\u0003GV\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0006K&\u0001\r\u0001X\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\u0015\u0003\u0001\u001d\u0004\"\u0001[6\u000e\u0003%T!A[\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002mS\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class UnfoldAsyncJava<S, E>
extends GraphStage<SourceShape<E>> {
    public final S akka$stream$impl$UnfoldAsyncJava$$s;
    public final Function<S, CompletionStage<Optional<Pair<S, E>>>> akka$stream$impl$UnfoldAsyncJava$$f;
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
            private Function1<Try<Optional<Pair<S, E>>>, BoxedUnit> asyncHandler;
            private final /* synthetic */ UnfoldAsyncJava $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void preStart() {
                AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)result -> {
                    $anon$3.$anonfun$preStart$3(this, result);
                    return BoxedUnit.UNIT;
                });
                this.asyncHandler = (Function1<Try, Object> & Serializable)t2 -> {
                    $anon$3.$anonfun$preStart$4(asyncCallback, t2);
                    return BoxedUnit.UNIT;
                };
            }

            private void handle(Try<Optional<Pair<S, E>>> result) {
                Try<Optional<Pair<S, E>>> try_ = result;
                if (try_ instanceof Success) {
                    Success success = (Success)try_;
                    Optional maybeValue = (Optional)success.value();
                    this.handle(maybeValue);
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

            private void handle(Optional<Pair<S, E>> maybeValue) {
                if (maybeValue.isPresent()) {
                    Pair<S, E> pair = maybeValue.get();
                    this.push(this.$outer.out(), pair.second());
                    this.state = pair.first();
                    return;
                }
                this.complete(this.$outer.out());
            }

            public void onPull() {
                CompletableFuture<Optional<Pair<S, E>>> future = this.$outer.akka$stream$impl$UnfoldAsyncJava$$f.apply(this.state).toCompletableFuture();
                if (future.isDone() && !future.isCompletedExceptionally()) {
                    this.handle((Optional<Pair<S, E>>)future.getNow(null));
                    return;
                }
                future.handle((T r, U ex) -> {
                    BoxedUnit boxedUnit = ex != null ? $this.asyncHandler.apply(new Failure<T>((Throwable)ex)) : $this.asyncHandler.apply(new Success<Optional>((Optional)r));
                    return null;
                });
            }

            public static final /* synthetic */ void $anonfun$preStart$3($anon$3 $this, Try result) {
                $this.handle(result);
            }

            public static final /* synthetic */ void $anonfun$preStart$4(AsyncCallback eta$0$1$2, Try t2) {
                eta$0$1$2.invoke(t2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.state = $outer.akka$stream$impl$UnfoldAsyncJava$$s;
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$3$adapted(akka.stream.impl.UnfoldAsyncJava$$anon$3 scala.util.Try ), $anonfun$preStart$4$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
    }

    public UnfoldAsyncJava(S s2, Function<S, CompletionStage<Optional<Pair<S, E>>>> f) {
        this.akka$stream$impl$UnfoldAsyncJava$$s = s2;
        this.akka$stream$impl$UnfoldAsyncJava$$f = f;
        this.out = Outlet$.MODULE$.apply("UnfoldAsync.out");
        this.shape = new SourceShape<E>(this.out());
    }
}

