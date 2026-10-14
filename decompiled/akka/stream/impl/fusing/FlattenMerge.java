/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.Buffer;
import akka.stream.impl.Buffer$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.impl.fusing.FlattenMerge$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005u4Q!\u0004\b\u0003)YA\u0001B\u000f\u0001\u0003\u0006\u0004%\ta\u000f\u0005\t\u007f\u0001\u0011\t\u0011)A\u0005y!)\u0001\t\u0001C\u0001\u0003\"9Q\t\u0001b\u0001\n\u00131\u0005B\u0002&\u0001A\u0003%q\tC\u0004L\u0001\t\u0007I\u0011\u0002'\t\rA\u0003\u0001\u0015!\u0003N\u0011\u0015\t\u0006\u0001\"\u0011S\u0011\u001d1\u0006A1A\u0005B]Ca\u0001\u0017\u0001!\u0002\u0013q\u0002\"B-\u0001\t\u0003R\u0006\"B5\u0001\t\u0003R'\u0001\u0004$mCR$XM\\'fe\u001e,'BA\b\u0011\u0003\u00191Wo]5oO*\u0011\u0011CE\u0001\u0005S6\u0004HN\u0003\u0002\u0014)\u000511\u000f\u001e:fC6T\u0011!F\u0001\u0005C.\\\u0017-F\u0002\u0018Ua\u001a\"\u0001\u0001\r\u0011\u0007eab$D\u0001\u001b\u0015\tY\"#A\u0003ti\u0006<W-\u0003\u0002\u001e5\tQqI]1qQN#\u0018mZ3\u0011\t}\u0001#\u0005K\u0007\u0002%%\u0011\u0011E\u0005\u0002\n\r2|wo\u00155ba\u0016\u0004BaH\u0012&o%\u0011AE\u0005\u0002\u0006\u000fJ\f\u0007\u000f\u001b\t\u0004?\u0019B\u0013BA\u0014\u0013\u0005-\u0019v.\u001e:dKNC\u0017\r]3\u0011\u0005%RC\u0002\u0001\u0003\u0006W\u0001\u0011\r!\f\u0002\u0002)\u000e\u0001\u0011C\u0001\u00185!\ty#'D\u00011\u0015\u0005\t\u0014!B:dC2\f\u0017BA\u001a1\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aL\u001b\n\u0005Y\u0002$aA!osB\u0011\u0011\u0006\u000f\u0003\u0006s\u0001\u0011\r!\f\u0002\u0002\u001b\u00069!M]3bIRDW#\u0001\u001f\u0011\u0005=j\u0014B\u0001 1\u0005\rIe\u000e^\u0001\tEJ,\u0017\r\u001a;iA\u00051A(\u001b8jiz\"\"A\u0011#\u0011\t\r\u0003\u0001fN\u0007\u0002\u001d!)!h\u0001a\u0001y\u0005\u0011\u0011N\\\u000b\u0002\u000fB\u0019q\u0004\u0013\u0012\n\u0005%\u0013\"!B%oY\u0016$\u0018aA5oA\u0005\u0019q.\u001e;\u0016\u00035\u00032a\b()\u0013\ty%C\u0001\u0004PkRdW\r^\u0001\u0005_V$\b%A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012a\u0015\t\u0003?QK!!\u0016\n\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-F\u0001\u001f\u0003\u0019\u0019\b.\u00199fA\u0005Y1M]3bi\u0016dunZ5d)\tYvM\u0005\u0003]=\u0006$g\u0001B/\f\u0001m\u0013A\u0002\u0010:fM&tW-\\3oiz\u0002\"!G0\n\u0005\u0001T\"aD$sCBD7\u000b^1hK2{w-[2\u0011\u0005e\u0011\u0017BA2\u001b\u0005)yU\u000f\u001e%b]\u0012dWM\u001d\t\u00033\u0015L!A\u001a\u000e\u0003\u0013%s\u0007*\u00198eY\u0016\u0014\b\"\u00025\f\u0001\u0004\u0019\u0016aE3oG2|7/\u001b8h\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003-\u0004\"\u0001\\:\u000f\u00055\f\bC\u000181\u001b\u0005y'B\u00019-\u0003\u0019a$o\\8u}%\u0011!\u000fM\u0001\u0007!J,G-\u001a4\n\u0005Q,(AB*ue&twM\u0003\u0002sa!\u0012\u0001a\u001e\t\u0003qnl\u0011!\u001f\u0006\u0003uR\t!\"\u00198o_R\fG/[8o\u0013\ta\u0018PA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class FlattenMerge<T, M>
extends GraphStage<FlowShape<Graph<SourceShape<T>, M>, T>> {
    private final int breadth;
    private final Inlet<Graph<SourceShape<T>, M>> akka$stream$impl$fusing$FlattenMerge$$in;
    private final Outlet<T> akka$stream$impl$fusing$FlattenMerge$$out;
    private final FlowShape<Graph<SourceShape<T>, M>, T> shape;

    public int breadth() {
        return this.breadth;
    }

    public Inlet<Graph<SourceShape<T>, M>> akka$stream$impl$fusing$FlattenMerge$$in() {
        return this.akka$stream$impl$fusing$FlattenMerge$$in;
    }

    public Outlet<T> akka$stream$impl$fusing$FlattenMerge$$out() {
        return this.akka$stream$impl$fusing$FlattenMerge$$out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.flattenMerge();
    }

    @Override
    public FlowShape<Graph<SourceShape<T>, M>, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes enclosingAttributes) {
        return new OutHandler(this, enclosingAttributes){
            private Set<GraphStageLogic.SubSinkInlet<T>> sources;
            private int pendingSingleSources;
            private Buffer<Object> akka$stream$impl$fusing$FlattenMerge$$anon$$queue;
            private final /* synthetic */ FlattenMerge $outer;
            private final Attributes enclosingAttributes$1;

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            private Set<GraphStageLogic.SubSinkInlet<T>> sources() {
                return this.sources;
            }

            private void sources_$eq(Set<GraphStageLogic.SubSinkInlet<T>> x$1) {
                this.sources = x$1;
            }

            private int pendingSingleSources() {
                return this.pendingSingleSources;
            }

            private void pendingSingleSources_$eq(int x$1) {
                this.pendingSingleSources = x$1;
            }

            private int activeSources() {
                return this.sources().size() + this.pendingSingleSources();
            }

            public Buffer<Object> akka$stream$impl$fusing$FlattenMerge$$anon$$queue() {
                return this.akka$stream$impl$fusing$FlattenMerge$$anon$$queue;
            }

            private void queue_$eq(Buffer<Object> x$1) {
                this.akka$stream$impl$fusing$FlattenMerge$$anon$$queue = x$1;
            }

            public void preStart() {
                this.queue_$eq(Buffer$.MODULE$.apply(this.$outer.breadth(), this.enclosingAttributes$1));
            }

            public void akka$stream$impl$fusing$FlattenMerge$$anon$$pushOut() {
                Object object = this.akka$stream$impl$fusing$FlattenMerge$$anon$$queue().dequeue();
                if (object instanceof GraphStageLogic.SubSinkInlet && ((GraphStageLogic.SubSinkInlet)object).akka$stream$stage$GraphStageLogic$SubSinkInlet$$$outer() == this) {
                    GraphStageLogic.SubSinkInlet subSinkInlet = (GraphStageLogic.SubSinkInlet)object;
                    this.push(this.$outer.akka$stream$impl$fusing$FlattenMerge$$out(), subSinkInlet.grab());
                    if (!subSinkInlet.isClosed()) {
                        subSinkInlet.pull();
                        return;
                    }
                    this.akka$stream$impl$fusing$FlattenMerge$$anon$$removeSource(subSinkInlet);
                    return;
                }
                if (object instanceof GraphStages.SingleSource) {
                    GraphStages.SingleSource singleSource = (GraphStages.SingleSource)object;
                    this.push(this.$outer.akka$stream$impl$fusing$FlattenMerge$$out(), singleSource.elem());
                    this.akka$stream$impl$fusing$FlattenMerge$$anon$$removeSource(singleSource);
                    return;
                }
                throw new IllegalStateException(new StringBuilder(35).append("Unexpected source type in queue: '").append(object.getClass()).append("'").toString());
            }

            public void onPush() {
                Graph<SourceShape<T>, M> source2 = this.grab(this.$outer.akka$stream$impl$fusing$FlattenMerge$$in());
                this.addSource(source2);
                if (this.activeSources() < this.$outer.breadth()) {
                    this.tryPull(this.$outer.akka$stream$impl$fusing$FlattenMerge$$in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                if (this.activeSources() == 0) {
                    this.completeStage();
                    return;
                }
            }

            public void onPull() {
                this.pull(this.$outer.akka$stream$impl$fusing$FlattenMerge$$in());
                this.setHandler(this.$outer.akka$stream$impl$fusing$FlattenMerge$$out(), new OutHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        if (this.$outer.akka$stream$impl$fusing$FlattenMerge$$anon$$queue().nonEmpty() && this.$outer.isAvailable(this.$outer.akka$stream$impl$fusing$FlattenMerge$$anon$$$outer().akka$stream$impl$fusing$FlattenMerge$$out())) {
                            this.$outer.akka$stream$impl$fusing$FlattenMerge$$anon$$pushOut();
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
            }

            private void addSource(Graph<SourceShape<T>, M> source2) {
                GraphStages.SingleSource<T> singleSource = TraversalBuilder$.MODULE$.getSingleSource(source2);
                GraphStages.SingleSource<T> singleSource2 = OptionVal$Some$.MODULE$.unapply(singleSource);
                if (!OptionVal$.MODULE$.isEmpty$extension(singleSource2)) {
                    GraphStages.SingleSource<T> single = OptionVal$.MODULE$.get$extension(singleSource2);
                    if (this.isAvailable(this.$outer.akka$stream$impl$fusing$FlattenMerge$$out()) && this.akka$stream$impl$fusing$FlattenMerge$$anon$$queue().isEmpty()) {
                        this.push(this.$outer.akka$stream$impl$fusing$FlattenMerge$$out(), single.elem());
                        return;
                    }
                    this.akka$stream$impl$fusing$FlattenMerge$$anon$$queue().enqueue(single);
                    this.pendingSingleSources_$eq(this.pendingSingleSources() + 1);
                    return;
                }
                GraphStageLogic.SubSinkInlet<T> sinkIn = new GraphStageLogic.SubSinkInlet<T>(this, "FlattenMergeSink");
                sinkIn.setHandler(new InHandler(this, sinkIn){
                    private final /* synthetic */ $anon$1 $outer;
                    private final GraphStageLogic.SubSinkInlet sinkIn$1;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        if (this.$outer.isAvailable(this.$outer.akka$stream$impl$fusing$FlattenMerge$$anon$$$outer().akka$stream$impl$fusing$FlattenMerge$$out())) {
                            this.$outer.push(this.$outer.akka$stream$impl$fusing$FlattenMerge$$anon$$$outer().akka$stream$impl$fusing$FlattenMerge$$out(), this.sinkIn$1.grab());
                            this.sinkIn$1.pull();
                            return;
                        }
                        this.$outer.akka$stream$impl$fusing$FlattenMerge$$anon$$queue().enqueue(this.sinkIn$1);
                    }

                    public void onUpstreamFinish() {
                        if (!this.sinkIn$1.isAvailable()) {
                            this.$outer.akka$stream$impl$fusing$FlattenMerge$$anon$$removeSource(this.sinkIn$1);
                            return;
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.sinkIn$1 = sinkIn$1;
                        InHandler.$init$(this);
                    }
                });
                sinkIn.pull();
                this.sources_$eq((Set)this.sources().$plus(sinkIn));
                Object graph = Source$.MODULE$.fromGraph(source2).to(sinkIn.sink());
                this.interpreter().subFusingMaterializer().materialize(graph, this.enclosingAttributes$1);
            }

            public void akka$stream$impl$fusing$FlattenMerge$$anon$$removeSource(Object src) {
                boolean pullSuppressed = this.activeSources() == this.$outer.breadth();
                Object object = src;
                if (object instanceof GraphStageLogic.SubSinkInlet && ((GraphStageLogic.SubSinkInlet)object).akka$stream$stage$GraphStageLogic$SubSinkInlet$$$outer() == this) {
                    GraphStageLogic.SubSinkInlet subSinkInlet = (GraphStageLogic.SubSinkInlet)object;
                    this.sources_$eq((Set)this.sources().$minus(subSinkInlet));
                } else if (object instanceof GraphStages.SingleSource) {
                    this.pendingSingleSources_$eq(this.pendingSingleSources() - 1);
                } else {
                    throw new IllegalArgumentException(new StringBuilder(26).append("Unexpected source type: '").append(object.getClass()).append("'").toString());
                }
                if (pullSuppressed) {
                    this.tryPull(this.$outer.akka$stream$impl$fusing$FlattenMerge$$in());
                }
                if (this.activeSources() == 0 && this.isClosed(this.$outer.akka$stream$impl$fusing$FlattenMerge$$in())) {
                    this.completeStage();
                    return;
                }
            }

            public void postStop() {
                this.sources().foreach((Function1<GraphStageLogic.SubSinkInlet, Object> & Serializable)x$2 -> {
                    $anon$1.$anonfun$postStop$1(x$2);
                    return BoxedUnit.UNIT;
                });
            }

            public /* synthetic */ FlattenMerge akka$stream$impl$fusing$FlattenMerge$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$postStop$1(GraphStageLogic.SubSinkInlet x$2) {
                x$2.cancel();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.enclosingAttributes$1 = enclosingAttributes$1;
                super($outer.shape());
                OutHandler.$init$(this);
                InHandler.$init$(this);
                this.sources = Predef$.MODULE$.Set().empty();
                this.pendingSingleSources = 0;
                this.setHandlers($outer.akka$stream$impl$fusing$FlattenMerge$$in(), $outer.akka$stream$impl$fusing$FlattenMerge$$out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$postStop$1$adapted(akka.stream.stage.GraphStageLogic$SubSinkInlet )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return new StringBuilder(14).append("FlattenMerge(").append(this.breadth()).append(")").toString();
    }

    public FlattenMerge(int breadth) {
        this.breadth = breadth;
        this.akka$stream$impl$fusing$FlattenMerge$$in = Inlet$.MODULE$.apply("flatten.in");
        this.akka$stream$impl$fusing$FlattenMerge$$out = Outlet$.MODULE$.apply("flatten.out");
        this.shape = new FlowShape<Graph<SourceShape<T>, M>, T>(this.akka$stream$impl$fusing$FlattenMerge$$in(), this.akka$stream$impl$fusing$FlattenMerge$$out());
    }
}

