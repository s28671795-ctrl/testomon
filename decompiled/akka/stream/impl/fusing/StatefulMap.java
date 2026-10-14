/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.fusing.StatefulMap$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.LambdaDeserialize;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dsAB\n\u0015\u0011\u0003QBD\u0002\u0004\u001f)!\u0005!d\b\u0005\u0006M\u0005!\t\u0001\u000b\u0004\u0005S\u00051!\u0006\u0003\u00058\u0007\t\u0005\t\u0015!\u00039\u0011\u001513\u0001\"\u0001A\r\u0015qBC\u0001\u000eM\u0011!1gA!A!\u0002\u00139\u0007\u0002C7\u0007\u0005\u0003\u0005\u000b\u0011\u00028\t\u0011Q4!\u0011!Q\u0001\nUDQA\n\u0004\u0005\u0002mD\u0011\"!\u0001\u0007\u0005\u0004%I!a\u0001\t\u0011\u0005-a\u0001)A\u0005\u0003\u000bA\u0011\"!\u0004\u0007\u0005\u0004%I!a\u0004\t\u0011\u0005]a\u0001)A\u0005\u0003#A\u0011\"!\u0007\u0007\u0005\u0004%\t%a\u0007\t\u000f\u0005ua\u0001)A\u0005)\"9\u0011q\u0004\u0004\u0005B\u0005\u0005\u0002bBA\u001a\r\u0011\u0005\u0013QG\u0001\f'R\fG/\u001a4vY6\u000b\u0007O\u0003\u0002\u0016-\u00051a-^:j]\u001eT!a\u0006\r\u0002\t%l\u0007\u000f\u001c\u0006\u00033i\taa\u001d;sK\u0006l'\"A\u000e\u0002\t\u0005\\7.\u0019\t\u0003;\u0005i\u0011\u0001\u0006\u0002\f'R\fG/\u001a4vY6\u000b\u0007o\u0005\u0002\u0002AA\u0011\u0011\u0005J\u0007\u0002E)\t1%A\u0003tG\u0006d\u0017-\u0003\u0002&E\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u00029\t\u0011b*\u001e7m'R\fG/Z#yG\u0016\u0004H/[8o'\t\u00191\u0006\u0005\u0002-i9\u0011QF\r\b\u0003]Ej\u0011a\f\u0006\u0003a\u001d\na\u0001\u0010:p_Rt\u0014\"A\u0012\n\u0005M\u0012\u0013a\u00029bG.\fw-Z\u0005\u0003kY\u0012ACT;mYB{\u0017N\u001c;fe\u0016C8-\u001a9uS>t'BA\u001a#\u0003\ri7o\u001a\t\u0003sur!AO\u001e\u0011\u00059\u0012\u0013B\u0001\u001f#\u0003\u0019\u0001&/\u001a3fM&\u0011ah\u0010\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005q\u0012CCA!D!\t\u00115!D\u0001\u0002\u0011\u00159T\u00011\u00019Q\t\tQ\t\u0005\u0002G\u00136\tqI\u0003\u0002I5\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005);%aC%oi\u0016\u0014h.\u00197Ba&D#\u0001A#\u0016\t5['\fZ\n\u0003\r9\u00032a\u0014*U\u001b\u0005\u0001&BA)\u0019\u0003\u0015\u0019H/Y4f\u0013\t\u0019\u0006K\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004B!\u0016,YG6\t\u0001$\u0003\u0002X1\tIa\t\\8x'\"\f\u0007/\u001a\t\u00033jc\u0001\u0001B\u0003\\\r\t\u0007AL\u0001\u0002J]F\u0011Q\f\u0019\t\u0003CyK!a\u0018\u0012\u0003\u000f9{G\u000f[5oOB\u0011\u0011%Y\u0005\u0003E\n\u00121!\u00118z!\tIF\rB\u0003f\r\t\u0007ALA\u0002PkR\faa\u0019:fCR,\u0007cA\u0011iU&\u0011\u0011N\t\u0002\n\rVt7\r^5p]B\u0002\"!W6\u0005\u000b14!\u0019\u0001/\u0003\u0003M\u000b\u0011A\u001a\t\u0006C=T\u0007,]\u0005\u0003a\n\u0012\u0011BR;oGRLwN\u001c\u001a\u0011\t\u0005\u0012(nY\u0005\u0003g\n\u0012a\u0001V;qY\u0016\u0014\u0014AC8o\u0007>l\u0007\u000f\\3uKB!\u0011E\u001e6y\u0013\t9(EA\u0005Gk:\u001cG/[8ocA\u0019\u0011%_2\n\u0005i\u0014#AB(qi&|g\u000e\u0006\u0003}{z|\b#B\u000f\u0007Ub\u001b\u0007\"\u00024\u000b\u0001\u00049\u0007\"B7\u000b\u0001\u0004q\u0007\"\u0002;\u000b\u0001\u0004)\u0018AA5o+\t\t)\u0001\u0005\u0003V\u0003\u000fA\u0016bAA\u00051\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002\u0007=,H/\u0006\u0002\u0002\u0012A!Q+a\u0005d\u0013\r\t)\u0002\u0007\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0006g\"\f\u0007/Z\u000b\u0002)\u000611\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR!\u00111EA\u0015!\ry\u0015QE\u0005\u0004\u0003O\u0001&aD$sCBD7\u000b^1hK2{w-[2\t\u000f\u0005-\u0012\u00031\u0001\u0002.\u0005\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fgB\u0019Q+a\f\n\u0007\u0005E\u0002D\u0001\u0006BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003o\u0001B!!\u000f\u0002D5\u0011\u00111\b\u0006\u0005\u0003{\ty$\u0001\u0003mC:<'BAA!\u0003\u0011Q\u0017M^1\n\u0007y\nY\u0004\u000b\u0002\u0007\u000b\u0002")
@InternalApi
public final class StatefulMap<S, In, Out>
extends GraphStage<FlowShape<In, Out>> {
    public final Function0<S> akka$stream$impl$fusing$StatefulMap$$create;
    public final Function2<S, In, Tuple2<S, Out>> akka$stream$impl$fusing$StatefulMap$$f;
    public final Function1<S, Option<Out>> akka$stream$impl$fusing$StatefulMap$$onComplete;
    private final Inlet<In> akka$stream$impl$fusing$StatefulMap$$in;
    private final Outlet<Out> akka$stream$impl$fusing$StatefulMap$$out;
    private final FlowShape<In, Out> shape;

    public Inlet<In> akka$stream$impl$fusing$StatefulMap$$in() {
        return this.akka$stream$impl$fusing$StatefulMap$$in;
    }

    public Outlet<Out> akka$stream$impl$fusing$StatefulMap$$out() {
        return this.akka$stream$impl$fusing$StatefulMap$$out;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this, inheritedAttributes){
            private Function1<Throwable, Supervision.Directive> decider;
            private S state;
            private volatile boolean bitmap$0;
            private final /* synthetic */ StatefulMap $outer;
            private Attributes inheritedAttributes$19;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                $anon$46 var1_1 = this;
                synchronized (var1_1) {
                    if (!this.bitmap$0) {
                        this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$19.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                        this.bitmap$0 = true;
                    }
                }
                this.inheritedAttributes$19 = null;
                return this.decider;
            }

            private Function1<Throwable, Supervision.Directive> decider() {
                if (!this.bitmap$0) {
                    return this.decider$lzycompute();
                }
                return this.decider;
            }

            private S state() {
                return this.state;
            }

            private void state_$eq(S x$1) {
                this.state = x$1;
            }

            public void preStart() {
                this.createNewState();
            }

            public void onPush() {
                try {
                    In elem = this.grab(this.$outer.akka$stream$impl$fusing$StatefulMap$$in());
                    Tuple2<S, Out> tuple2 = this.$outer.akka$stream$impl$fusing$StatefulMap$$f.apply(OptionVal$.MODULE$.get$extension(this.state()), elem);
                    if (tuple2 == null) {
                        throw new MatchError(tuple2);
                    }
                    S newState = tuple2._1();
                    Out newElem = tuple2._2();
                    Tuple2<S, Out> tuple22 = new Tuple2<S, Out>(newState, newElem);
                    S newState2 = tuple22._1();
                    Out newElem2 = tuple22._2();
                    this.state_$eq(OptionVal$Some$.MODULE$.apply(newState2));
                    this.throwIfNoState();
                    this.push(this.$outer.akka$stream$impl$fusing$StatefulMap$$out(), newElem2);
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable2 = throwable;
                    if (throwable2 instanceof NullStateException) {
                        NullStateException nullStateException = (NullStateException)throwable2;
                        throw nullStateException;
                    }
                    if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                        Throwable ex = option.get();
                        Supervision.Directive directive = this.decider().apply(ex);
                        if (Supervision$Stop$.MODULE$.equals(directive)) {
                            this.closeStateAndFail(ex);
                        }
                        if (Supervision$Resume$.MODULE$.equals(directive)) {
                            this.pull(this.$outer.akka$stream$impl$fusing$StatefulMap$$in());
                        }
                        if (Supervision$Restart$.MODULE$.equals(directive)) {
                            this.restartState();
                        }
                        throw new MatchError(directive);
                    }
                    throw throwable;
                }
            }

            public void onUpstreamFinish() {
                Option<Out> option = this.completeStateIfNeeded();
                if (option instanceof Some) {
                    Some some = (Some)option;
                    A elem = some.value();
                    this.emit(this.$outer.akka$stream$impl$fusing$StatefulMap$$out(), elem, () -> this.completeStage());
                    return;
                }
                if (None$.MODULE$.equals(option)) {
                    this.completeStage();
                    return;
                }
                throw new MatchError(option);
            }

            public void onUpstreamFailure(Throwable ex) {
                this.closeStateAndFail(ex);
            }

            public void onDownstreamFinish(Throwable cause) {
                this.completeStateIfNeeded();
                OutHandler.onDownstreamFinish$(this, cause);
            }

            private void createNewState() {
                this.state_$eq(OptionVal$Some$.MODULE$.apply(this.$outer.akka$stream$impl$fusing$StatefulMap$$create.apply()));
                this.throwIfNoState();
            }

            private void restartState() {
                Option<Out> option = this.completeStateIfNeeded();
                if (option instanceof Some) {
                    Some some = (Some)option;
                    A elem = some.value();
                    this.push(this.$outer.akka$stream$impl$fusing$StatefulMap$$out(), elem);
                    this.createNewState();
                    return;
                }
                if (None$.MODULE$.equals(option)) {
                    this.createNewState();
                    if (!this.hasBeenPulled(this.$outer.akka$stream$impl$fusing$StatefulMap$$in())) {
                        this.pull(this.$outer.akka$stream$impl$fusing$StatefulMap$$in());
                        return;
                    }
                    return;
                }
                throw new MatchError(option);
            }

            private void closeStateAndFail(Throwable ex) {
                Option<Out> option = this.completeStateIfNeeded();
                if (option instanceof Some) {
                    Some some = (Some)option;
                    A elem = some.value();
                    this.emit(this.$outer.akka$stream$impl$fusing$StatefulMap$$out(), elem, () -> this.failStage(ex));
                    return;
                }
                if (None$.MODULE$.equals(option)) {
                    this.failStage(ex);
                    return;
                }
                throw new MatchError(option);
            }

            private Option<Out> completeStateIfNeeded() {
                S s2 = this.state();
                S s3 = OptionVal$Some$.MODULE$.unapply(s2);
                if (!OptionVal$.MODULE$.isEmpty$extension(s3)) {
                    S s4 = OptionVal$.MODULE$.get$extension(s3);
                    this.state_$eq(OptionVal$.MODULE$.none());
                    return this.$outer.akka$stream$impl$fusing$StatefulMap$$onComplete.apply(s4);
                }
                return None$.MODULE$;
            }

            public void onPull() {
                this.pull(this.$outer.akka$stream$impl$fusing$StatefulMap$$in());
            }

            public void postStop() {
                this.completeStateIfNeeded();
            }

            private void throwIfNoState() {
                if (OptionVal$.MODULE$.isEmpty$extension(this.state())) {
                    throw new NullStateException("State returned by stateFulMap create lambda or mapping function was null, which is not allowed. Use Option or Optional to represent presence of state if needed.");
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.inheritedAttributes$19 = inheritedAttributes$19;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.state = OptionVal$.MODULE$.none();
                this.setHandlers($outer.akka$stream$impl$fusing$StatefulMap$$in(), $outer.akka$stream$impl$fusing$StatefulMap$$out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$closeStateAndFail$1(akka.stream.impl.fusing.StatefulMap$$anon$46 java.lang.Throwable ), $anonfun$onUpstreamFinish$1(akka.stream.impl.fusing.StatefulMap$$anon$46 )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "StatefulMap";
    }

    public StatefulMap(Function0<S> create, Function2<S, In, Tuple2<S, Out>> f, Function1<S, Option<Out>> onComplete) {
        this.akka$stream$impl$fusing$StatefulMap$$create = create;
        this.akka$stream$impl$fusing$StatefulMap$$f = f;
        this.akka$stream$impl$fusing$StatefulMap$$onComplete = onComplete;
        Predef$.MODULE$.require(create != null, (Function0<Object>)(Function0<String> & Serializable)() -> "create function should not be null");
        Predef$.MODULE$.require(f != null, (Function0<Object>)(Function0<String> & Serializable)() -> "f function should not be null");
        Predef$.MODULE$.require(onComplete != null, (Function0<Object>)(Function0<String> & Serializable)() -> "onComplete function should not be null");
        this.akka$stream$impl$fusing$StatefulMap$$in = Inlet$.MODULE$.apply("StatefulMap.in");
        this.akka$stream$impl$fusing$StatefulMap$$out = Outlet$.MODULE$.apply("StatefulMap.out");
        this.shape = new FlowShape<In, Out>(this.akka$stream$impl$fusing$StatefulMap$$in(), this.akka$stream$impl$fusing$StatefulMap$$out());
    }

    private static final class NullStateException
    extends NullPointerException {
        public NullStateException(String msg) {
            super(msg);
        }
    }
}

