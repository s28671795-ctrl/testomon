/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.AbruptStageTerminationException;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.NeverMaterializedException;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.FlatMapPrefix$;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.Buffer$;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005u4Q\u0001D\u0007\u0003'UA\u0001\u0002\u0010\u0001\u0003\u0002\u0003\u0006I!\u0010\u0005\t\u0001\u0002\u0011\t\u0011)A\u0005\u0003\")!\u000b\u0001C\u0001'\"9\u0001\f\u0001b\u0001\n\u0003I\u0006BB/\u0001A\u0003%!\fC\u0004_\u0001\t\u0007I\u0011A0\t\r\r\u0004\u0001\u0015!\u0003a\u0011\u001d!\u0007A1A\u0005B\u0015DaA\u001a\u0001!\u0002\u0013i\u0002\"B4\u0001\t\u0003B\u0007\"\u00027\u0001\t\u0003j'!\u0004$mCRl\u0015\r\u001d)sK\u001aL\u0007P\u0003\u0002\u000f\u001f\u00051a-^:j]\u001eT!\u0001E\t\u0002\t%l\u0007\u000f\u001c\u0006\u0003%M\taa\u001d;sK\u0006l'\"\u0001\u000b\u0002\t\u0005\\7.Y\u000b\u0005-\r\n$h\u0005\u0002\u0001/A!\u0001dG\u000f4\u001b\u0005I\"B\u0001\u000e\u0012\u0003\u0015\u0019H/Y4f\u0013\ta\u0012DA\u0010He\u0006\u0004\bn\u0015;bO\u0016<\u0016\u000e\u001e5NCR,'/[1mSj,GMV1mk\u0016\u0004BAH\u0010\"a5\t\u0011#\u0003\u0002!#\tIa\t\\8x'\"\f\u0007/\u001a\t\u0003E\rb\u0001\u0001B\u0003%\u0001\t\u0007aE\u0001\u0002J]\u000e\u0001\u0011CA\u0014.!\tA3&D\u0001*\u0015\u0005Q\u0013!B:dC2\f\u0017B\u0001\u0017*\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u000b\u0018\n\u0005=J#aA!osB\u0011!%\r\u0003\u0006e\u0001\u0011\rA\n\u0002\u0004\u001fV$\bc\u0001\u001b8s5\tQG\u0003\u00027S\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005a*$A\u0002$viV\u0014X\r\u0005\u0002#u\u0011)1\b\u0001b\u0001M\t\tQ*A\u0001o!\tAc(\u0003\u0002@S\t\u0019\u0011J\u001c;\u0002\u0003\u0019\u0004B\u0001\u000b\"E\u0019&\u00111)\u000b\u0002\n\rVt7\r^5p]F\u00022!\u0012&\"\u001b\u00051%BA$I\u0003%IW.\\;uC\ndWM\u0003\u0002JS\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005-3%aA*fcB)Q\nU\u00111s5\taJ\u0003\u0002P#\u0005A1oY1mC\u0012\u001cH.\u0003\u0002R\u001d\n!a\t\\8x\u0003\u0019a\u0014N\\5u}Q\u0019AKV,\u0011\u000bU\u0003\u0011\u0005M\u001d\u000e\u00035AQ\u0001P\u0002A\u0002uBQ\u0001Q\u0002A\u0002\u0005\u000b!!\u001b8\u0016\u0003i\u00032AH.\"\u0013\ta\u0016CA\u0003J]2,G/A\u0002j]\u0002\n1a\\;u+\u0005\u0001\u0007c\u0001\u0010ba%\u0011!-\u0005\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0006g\"\f\u0007/Z\u000b\u0002;\u000511\u000f[1qK\u0002\n\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005I\u0007C\u0001\u0010k\u0013\tY\u0017C\u0001\u0006BiR\u0014\u0018NY;uKN\fqd\u0019:fCR,Gj\\4jG\u0006sG-T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f)\tqG\u000f\u0005\u0003)_F\u001c\u0014B\u00019*\u0005\u0019!V\u000f\u001d7feA\u0011\u0001D]\u0005\u0003gf\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0006k.\u0001\r![\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\u0015\u0003\u0001]\u0004\"\u0001_>\u000e\u0003eT!A_\n\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002}s\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class FlatMapPrefix<In, Out, M>
extends GraphStageWithMaterializedValue<FlowShape<In, Out>, Future<M>> {
    public final int akka$stream$impl$fusing$FlatMapPrefix$$n;
    public final Function1<Seq<In>, Flow<In, Out, M>> akka$stream$impl$fusing$FlatMapPrefix$$f;
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
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.flatMapPrefix().and(Attributes$SourceLocation$.MODULE$.forLambda(this.akka$stream$impl$fusing$FlatMapPrefix$$f));
    }

    @Override
    public Tuple2<GraphStageLogic, Future<M>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
        boolean propagateToNestedMaterialization = ((Attributes.NestedMaterializationCancellationPolicy)inheritedAttributes.mandatoryAttribute(ClassTag$.MODULE$.apply(Attributes.NestedMaterializationCancellationPolicy.class))).propagateToNestedMaterialization();
        Promise matPromise = Promise$.MODULE$.apply();
        InHandler logic = new InHandler(this, matPromise, propagateToNestedMaterialization, inheritedAttributes){
            private final Buffer<In> accumulated;
            private GraphStageLogic.SubSourceOutlet<In> subSource;
            private GraphStageLogic.SubSinkInlet<Out> subSink;
            private Throwable downstreamCause;
            private final /* synthetic */ FlatMapPrefix $outer;
            private final Promise matPromise$1;
            private final boolean propagateToNestedMaterialization$1;
            private final Attributes inheritedAttributes$1;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public Buffer<In> accumulated() {
                return this.accumulated;
            }

            private GraphStageLogic.SubSourceOutlet<In> subSource() {
                return this.subSource;
            }

            private void subSource_$eq(GraphStageLogic.SubSourceOutlet<In> x$1) {
                this.subSource = x$1;
            }

            private GraphStageLogic.SubSinkInlet<Out> subSink() {
                return this.subSink;
            }

            private void subSink_$eq(GraphStageLogic.SubSinkInlet<Out> x$1) {
                this.subSink = x$1;
            }

            private Throwable downstreamCause() {
                return this.downstreamCause;
            }

            private void downstreamCause_$eq(Throwable x$1) {
                this.downstreamCause = x$1;
            }

            public void postStop() {
                Object object = !this.matPromise$1.isCompleted() ? this.matPromise$1.failure(new AbruptStageTerminationException(this)) : BoxedUnit.UNIT;
                super.postStop();
            }

            public void onPush() {
                GraphStageLogic.SubSourceOutlet<In> subSourceOutlet = this.subSource();
                GraphStageLogic.SubSourceOutlet<In> subSourceOutlet2 = OptionVal$Some$.MODULE$.unapply(subSourceOutlet);
                if (!OptionVal$.MODULE$.isEmpty$extension(subSourceOutlet2)) {
                    GraphStageLogic.SubSourceOutlet<In> s2 = OptionVal$.MODULE$.get$extension(subSourceOutlet2);
                    s2.push(this.grab(this.$outer.in()));
                    return;
                }
                this.accumulated().append(this.grab(this.$outer.in()));
                if (this.accumulated().size() == this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$n) {
                    this.materializeFlow();
                    return;
                }
                this.pull(this.$outer.in());
            }

            public void onUpstreamFinish() {
                GraphStageLogic.SubSourceOutlet<In> subSourceOutlet = this.subSource();
                GraphStageLogic.SubSourceOutlet<In> subSourceOutlet2 = OptionVal$Some$.MODULE$.unapply(subSourceOutlet);
                if (!OptionVal$.MODULE$.isEmpty$extension(subSourceOutlet2)) {
                    GraphStageLogic.SubSourceOutlet<In> s2 = OptionVal$.MODULE$.get$extension(subSourceOutlet2);
                    s2.complete();
                    return;
                }
                this.materializeFlow();
            }

            public void onUpstreamFailure(Throwable ex) {
                GraphStageLogic.SubSourceOutlet<In> subSourceOutlet = this.subSource();
                GraphStageLogic.SubSourceOutlet<In> subSourceOutlet2 = OptionVal$Some$.MODULE$.unapply(subSourceOutlet);
                if (!OptionVal$.MODULE$.isEmpty$extension(subSourceOutlet2)) {
                    GraphStageLogic.SubSourceOutlet<In> s2 = OptionVal$.MODULE$.get$extension(subSourceOutlet2);
                    s2.fail(ex);
                    return;
                }
                this.matPromise$1.failure(new NeverMaterializedException(ex));
                InHandler.onUpstreamFailure$(this, ex);
            }

            public void onPull() {
                GraphStageLogic.SubSinkInlet<Out> subSinkInlet = this.subSink();
                GraphStageLogic.SubSinkInlet<Out> subSinkInlet2 = OptionVal$Some$.MODULE$.unapply(subSinkInlet);
                if (!OptionVal$.MODULE$.isEmpty$extension(subSinkInlet2)) {
                    GraphStageLogic.SubSinkInlet<Out> s2 = OptionVal$.MODULE$.get$extension(subSinkInlet2);
                    s2.pull();
                    return;
                }
                if (this.accumulated().size() < this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$n) {
                    this.pull(this.$outer.in());
                    return;
                }
                if (this.accumulated().size() == this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$n) {
                    this.materializeFlow();
                    return;
                }
                throw new IllegalStateException(new StringBuilder(35).append("Unexpected accumulated size: ").append(this.accumulated().size()).append(" (n: ").append(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$n).append(")").toString());
            }

            public void onDownstreamFinish(Throwable cause) {
                GraphStageLogic.SubSinkInlet<Out> subSinkInlet = this.subSink();
                GraphStageLogic.SubSinkInlet<Out> subSinkInlet2 = OptionVal$Some$.MODULE$.unapply(subSinkInlet);
                if (!OptionVal$.MODULE$.isEmpty$extension(subSinkInlet2)) {
                    GraphStageLogic.SubSinkInlet<Out> s2 = OptionVal$.MODULE$.get$extension(subSinkInlet2);
                    s2.cancel(cause);
                    return;
                }
                if (this.propagateToNestedMaterialization$1) {
                    this.downstreamCause_$eq(OptionVal$Some$.MODULE$.apply(cause));
                    if (this.accumulated().size() == this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$n) {
                        this.materializeFlow();
                        return;
                    }
                    if (!this.hasBeenPulled(this.$outer.in())) {
                        this.pull(this.$outer.in());
                        return;
                    }
                    return;
                }
                this.matPromise$1.failure(new NeverMaterializedException(cause));
                this.cancelStage(cause);
            }

            public void materializeFlow() {
                try {
                    Mat Mat;
                    Vector<A> prefix = this.accumulated().toVector();
                    this.accumulated().clear();
                    this.subSource_$eq(OptionVal$Some$.MODULE$.apply(new GraphStageLogic.SubSourceOutlet<T>(this, "FlatMapPrefix.subSource")));
                    GraphStageLogic.SubSourceOutlet<In> theSubSource = OptionVal$.MODULE$.get$extension(this.subSource());
                    theSubSource.setHandler(new OutHandler(this){
                        private final /* synthetic */ $anon$1 $outer;

                        public void onDownstreamFinish() throws Exception {
                            OutHandler.onDownstreamFinish$(this);
                        }

                        public void onPull() {
                            if (!this.$outer.isClosed(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer().in()) && !this.$outer.hasBeenPulled(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer().in())) {
                                this.$outer.pull(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer().in());
                                return;
                            }
                        }

                        public void onDownstreamFinish(Throwable cause) {
                            if (!this.$outer.isClosed(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer().in())) {
                                this.$outer.cancel(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer().in(), cause);
                                return;
                            }
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            OutHandler.$init$(this);
                        }
                    });
                    this.subSink_$eq(OptionVal$Some$.MODULE$.apply(new GraphStageLogic.SubSinkInlet<T>(this, "FlatMapPrefix.subSink")));
                    GraphStageLogic.SubSinkInlet<Out> theSubSink = OptionVal$.MODULE$.get$extension(this.subSink());
                    theSubSink.setHandler(new InHandler(this, theSubSink){
                        private final /* synthetic */ $anon$1 $outer;
                        private final GraphStageLogic.SubSinkInlet theSubSink$1;

                        public void onPush() {
                            this.$outer.push(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer().out(), this.theSubSink$1.grab());
                        }

                        public void onUpstreamFinish() {
                            this.$outer.complete(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer().out());
                        }

                        public void onUpstreamFailure(Throwable ex) {
                            this.$outer.fail(this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer().out(), ex);
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.theSubSink$1 = theSubSink$1;
                            InHandler.$init$(this);
                        }
                    });
                    try {
                        Flow<In, Out, M> flow = this.$outer.akka$stream$impl$fusing$FlatMapPrefix$$f.apply(prefix);
                        Object runnableGraph = ((Source)Source$.MODULE$.fromGraph(theSubSource.source()).viaMat(flow, Keep$.MODULE$.right())).to(theSubSink.sink());
                        Mat = this.interpreter().subFusingMaterializer().materialize(runnableGraph, this.inheritedAttributes$1);
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex = option.get();
                            this.matPromise$1.failure(new NeverMaterializedException(ex));
                            OptionVal$.MODULE$.None();
                            this.subSource_$eq(null);
                            OptionVal$.MODULE$.None();
                            this.subSink_$eq(null);
                            throw ex;
                        }
                        throw throwable;
                    }
                    Mat matVal = Mat;
                    this.matPromise$1.success(matVal);
                    Throwable throwable = this.downstreamCause();
                    Throwable throwable3 = OptionVal$Some$.MODULE$.unapply(throwable);
                    if (!OptionVal$.MODULE$.isEmpty$extension(throwable3)) {
                        Throwable ex = OptionVal$.MODULE$.get$extension(throwable3);
                        theSubSink.cancel(ex);
                    }
                    if (this.isClosed(this.$outer.in())) {
                        theSubSource.complete();
                    }
                    if (this.isAvailable(this.$outer.out())) {
                        theSubSink.pull();
                    }
                }
                catch (Throwable throwable) {
                    Option<Throwable> option;
                    Throwable throwable4 = throwable;
                    if (throwable4 != null && !(option = NonFatal$.MODULE$.unapply(throwable4)).isEmpty()) {
                        Throwable ex = option.get();
                        this.failStage(ex);
                    }
                    throw throwable;
                }
            }

            public /* synthetic */ FlatMapPrefix akka$stream$impl$fusing$FlatMapPrefix$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.matPromise$1 = matPromise$1;
                this.propagateToNestedMaterialization$1 = propagateToNestedMaterialization$1;
                this.inheritedAttributes$1 = inheritedAttributes$1;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.accumulated = (Buffer)Buffer$.MODULE$.empty();
                this.subSource = (GraphStageLogic.SubSourceOutlet)OptionVal$.MODULE$.none();
                this.subSink = (GraphStageLogic.SubSinkInlet)OptionVal$.MODULE$.none();
                this.downstreamCause = (Throwable)OptionVal$.MODULE$.none();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
        return new Tuple2<GraphStageLogic, Future<M>>((GraphStageLogic)((Object)logic), matPromise.future());
    }

    public FlatMapPrefix(int n, Function1<Seq<In>, Flow<In, Out, M>> f) {
        this.akka$stream$impl$fusing$FlatMapPrefix$$n = n;
        this.akka$stream$impl$fusing$FlatMapPrefix$$f = f;
        Predef$.MODULE$.require(n >= 0, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(41).append("FlatMapPrefix: n (").append($this.akka$stream$impl$fusing$FlatMapPrefix$$n).append(") must be non-negative.").toString());
        this.in = Inlet$.MODULE$.apply("FlatMapPrefix.in");
        this.out = Outlet$.MODULE$.apply("FlatMapPrefix.out");
        this.shape = new FlowShape<In, Out>(this.in(), this.out());
    }
}

