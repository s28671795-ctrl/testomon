/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.FlowShape$;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.FoldAsync$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.Some;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005Y4QAD\b\u0003+]A\u0001\"\u000e\u0001\u0003\u0002\u0003\u0006IA\r\u0005\tm\u0001\u0011\t\u0011)A\u0005o!)\u0001\t\u0001C\u0001\u0003\"9a\t\u0001b\u0001\n\u00039\u0005BB&\u0001A\u0003%\u0001\nC\u0004M\u0001\t\u0007I\u0011A'\t\rE\u0003\u0001\u0015!\u0003O\u0011\u001d\u0011\u0006A1A\u0005\u0002MCa\u0001\u0016\u0001!\u0002\u0013y\u0002\"B+\u0001\t\u00032\u0006b\u00022\u0001\u0005\u0004%\te\u0019\u0005\u0007O\u0002\u0001\u000b\u0011\u00023\t\u000b!\u0004A\u0011A5\u0003\u0013\u0019{G\u000eZ!ts:\u001c'B\u0001\t\u0012\u0003\u00191Wo]5oO*\u0011!cE\u0001\u0005S6\u0004HN\u0003\u0002\u0015+\u000511\u000f\u001e:fC6T\u0011AF\u0001\u0005C.\\\u0017-F\u0002\u0019KM\u001a\"\u0001A\r\u0011\u0007iir$D\u0001\u001c\u0015\ta2#A\u0003ti\u0006<W-\u0003\u0002\u001f7\tQqI]1qQN#\u0018mZ3\u0011\t\u0001\n3EM\u0007\u0002'%\u0011!e\u0005\u0002\n\r2|wo\u00155ba\u0016\u0004\"\u0001J\u0013\r\u0001\u0011)a\u0005\u0001b\u0001Q\t\u0011\u0011J\\\u0002\u0001#\tIs\u0006\u0005\u0002+[5\t1FC\u0001-\u0003\u0015\u00198-\u00197b\u0013\tq3FA\u0004O_RD\u0017N\\4\u0011\u0005)\u0002\u0014BA\u0019,\u0005\r\te.\u001f\t\u0003IM\"Q\u0001\u000e\u0001C\u0002!\u00121aT;u\u0003\u0011QXM]8\u0002\u0003\u0019\u0004RA\u000b\u001d3GiJ!!O\u0016\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004cA\u001e?e5\tAH\u0003\u0002>W\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005}b$A\u0002$viV\u0014X-\u0001\u0004=S:LGO\u0010\u000b\u0004\u0005\u0012+\u0005\u0003B\"\u0001GIj\u0011a\u0004\u0005\u0006k\r\u0001\rA\r\u0005\u0006m\r\u0001\raN\u0001\u0003S:,\u0012\u0001\u0013\t\u0004A%\u001b\u0013B\u0001&\u0014\u0005\u0015Ie\u000e\\3u\u0003\rIg\u000eI\u0001\u0004_V$X#\u0001(\u0011\u0007\u0001z%'\u0003\u0002Q'\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\tq$\u0001\u0004tQ\u0006\u0004X\rI\u0001\ti>\u001cFO]5oOR\tq\u000b\u0005\u0002Y?:\u0011\u0011,\u0018\t\u00035.j\u0011a\u0017\u0006\u00039\u001e\na\u0001\u0010:p_Rt\u0014B\u00010,\u0003\u0019\u0001&/\u001a3fM&\u0011\u0001-\u0019\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005y[\u0013!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\tA\r\u0005\u0002!K&\u0011am\u0005\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018AE5oSRL\u0017\r\\!uiJL'-\u001e;fg\u0002\n1b\u0019:fCR,Gj\\4jGR\u0011!.\u001c\t\u00035-L!\u0001\\\u000e\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDQA\\\u0007A\u0002\u0011\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKND#\u0001\u00019\u0011\u0005E$X\"\u0001:\u000b\u0005M,\u0012AC1o]>$\u0018\r^5p]&\u0011QO\u001d\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018\u000e")
@InternalApi
public final class FoldAsync<In, Out>
extends GraphStage<FlowShape<In, Out>> {
    public final Out akka$stream$impl$fusing$FoldAsync$$zero;
    public final Function2<Out, In, Future<Out>> akka$stream$impl$fusing$FoldAsync$$f;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;
    private final Attributes initialAttributes;

    public Inlet<In> in() {
        return this.in;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    public String toString() {
        return "FoldAsync";
    }

    @Override
    public Attributes initialAttributes() {
        return this.initialAttributes;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private final Function1<Throwable, Supervision.Directive> decider;
            private Out aggregator;
            private Future<Out> aggregating;
            private final Function1<Try<Out>, BoxedUnit> futureCB;
            private final /* synthetic */ FoldAsync $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                return this.decider;
            }

            private Out aggregator() {
                return this.aggregator;
            }

            private void aggregator_$eq(Out x$1) {
                this.aggregator = x$1;
            }

            private Future<Out> aggregating() {
                return this.aggregating;
            }

            private void aggregating_$eq(Future<Out> x$1) {
                this.aggregating = x$1;
            }

            private void onRestart(Throwable t2) {
                this.aggregator_$eq(this.$outer.akka$stream$impl$fusing$FoldAsync$$zero);
            }

            private Function1<Try<Out>, BoxedUnit> futureCB() {
                return this.futureCB;
            }

            public void onPush() {
                try {
                    this.aggregating_$eq(this.$outer.akka$stream$impl$fusing$FoldAsync$$f.apply(this.aggregator(), this.grab(this.$outer.in())));
                    this.handleAggregatingValue();
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        Supervision.Directive directive = this.decider().apply(ex);
                        if (Supervision$Stop$.MODULE$.equals(directive)) {
                            this.failStage(ex);
                        }
                        Supervision.Directive directive2 = directive;
                        if (Supervision$Restart$.MODULE$.equals(directive2)) {
                            this.onRestart(ex);
                        }
                        this.tryPull(this.$outer.in());
                    }
                    throw throwable;
                }
            }

            public void onUpstreamFinish() {
                this.handleAggregatingValue();
            }

            public void onPull() {
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.tryPull(this.$outer.in());
                    return;
                }
            }

            private void handleAggregatingValue() {
                Option<Try<Out>> option = this.aggregating().value();
                if (option instanceof Some) {
                    Some some = (Some)option;
                    Try result = (Try)some.value();
                    this.futureCB().apply(result);
                    return;
                }
                this.aggregating().onComplete(this.futureCB(), ExecutionContexts$.MODULE$.parasitic());
            }

            public String toString() {
                return new StringBuilder(27).append("FoldAsync.Logic(completed=").append(this.aggregating().isCompleted()).append(")").toString();
            }

            /*
             * Unable to fully structure code
             */
            public static final /* synthetic */ void $anonfun$futureCB$3($anon$18 $this, Try x0$1) {
                block11: {
                    block10: {
                        var4_2 = x0$1;
                        if (var4_2 instanceof Success && (update = (var5_3 = (Success)var4_2).value()) != null) {
                            $this.aggregator_$eq(update);
                            if ($this.isClosed($this.$outer.in())) {
                                $this.push($this.$outer.out(), update);
                                $this.completeStage();
                                return;
                            }
                            if ($this.isAvailable($this.$outer.out()) && !$this.hasBeenPulled($this.$outer.in())) {
                                $this.tryPull($this.$outer.in());
                                return;
                            }
                            return;
                        }
                        var8_5 = false;
                        var9_6 = null;
                        var10_7 = var4_2;
                        if (!(var10_7 instanceof Failure)) break block10;
                        var11_8 = (Failure)var10_7;
                        v0 = t = var11_8.exception();
                        break block11;
                    }
                    if (!(var10_7 instanceof Success)) ** GOTO lbl-1000
                    var8_5 = true;
                    var9_6 = (Success)var10_7;
                    var13_10 = var9_6.value();
                    if (var13_10 == null) {
                        v0 = ReactiveStreamsCompliance$.MODULE$.elementMustNotBeNullException();
                    } else lbl-1000:
                    // 2 sources

                    {
                        if (var8_5) {
                            throw new IllegalStateException();
                        }
                        throw new MatchError(var10_7);
                    }
                }
                ex = v0;
                v1 = supervision = $this.decider().apply(ex);
                var15_13 = Supervision$Stop$.MODULE$;
                if (!(v1 != null ? v1.equals(var15_13) == false : var15_13 != null)) {
                    $this.failStage(ex);
                    return;
                }
                v2 = supervision;
                var16_14 = Supervision$Restart$.MODULE$;
                if (!(v2 != null ? v2.equals(var16_14) == false : var16_14 != null)) {
                    $this.onRestart(ex);
                }
                if ($this.isClosed($this.$outer.in())) {
                    $this.push($this.$outer.out(), $this.aggregator());
                    $this.completeStage();
                    return;
                }
                if ($this.isAvailable($this.$outer.out()) && !$this.hasBeenPulled($this.$outer.in())) {
                    $this.tryPull($this.$outer.in());
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$futureCB$4(AsyncCallback eta$0$1$2, Try t2) {
                eta$0$1$2.invoke(t2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.decider = ((ActorAttributes.SupervisionStrategy)inheritedAttributes$9.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                this.aggregator = $outer.akka$stream$impl$fusing$FoldAsync$$zero;
                this.aggregating = Future$.MODULE$.successful(this.aggregator());
                AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)x0$1 -> {
                    $anon$18.$anonfun$futureCB$3(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.futureCB = (Function1<Try, Object> & Serializable)t2 -> {
                    $anon$18.$anonfun$futureCB$4(asyncCallback, t2);
                    return BoxedUnit.UNIT;
                };
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$futureCB$3$adapted(akka.stream.impl.fusing.FoldAsync$$anon$18 scala.util.Try ), $anonfun$futureCB$4$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
            }
        };
    }

    public FoldAsync(Out zero, Function2<Out, In, Future<Out>> f) {
        this.akka$stream$impl$fusing$FoldAsync$$zero = zero;
        this.akka$stream$impl$fusing$FoldAsync$$f = f;
        this.in = Inlet$.MODULE$.apply("FoldAsync.in");
        this.out = Outlet$.MODULE$.apply("FoldAsync.out");
        this.shape = FlowShape$.MODULE$.of(this.in(), this.out());
        this.initialAttributes = Stages$DefaultAttributes$.MODULE$.foldAsync().and(Attributes$SourceLocation$.MODULE$.forLambda(f));
    }
}

