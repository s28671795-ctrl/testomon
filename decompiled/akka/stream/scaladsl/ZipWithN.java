/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.UniformFanInShape;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.ZipWithN$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function1;
import scala.MatchError;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005q<QAD\b\t\u0002Y1Q\u0001G\b\t\u0002eAQ\u0001I\u0001\u0005\u0002\u0005BQAI\u0001\u0005\u0002\r2A\u0001G\b\u0001O!A\u0011\t\u0002B\u0001B\u0003%!\t\u0003\u0005N\t\t\u0005\t\u0015!\u0003O\u0011\u0015\u0001C\u0001\"\u0001R\u0011\u00151F\u0001\"\u0011X\u0011\u001dYFA1A\u0005BqCa!\u0018\u0003!\u0002\u0013y\u0003\"\u00020\u0005\t\u0003y\u0006\"B2\u0005\t\u0003\"\u0007\"\u00026\u0005\t\u0003Z\u0017\u0001\u0003.ja^KG\u000f\u001b(\u000b\u0005A\t\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005I\u0019\u0012AB:ue\u0016\fWNC\u0001\u0015\u0003\u0011\t7n[1\u0004\u0001A\u0011q#A\u0007\u0002\u001f\tA!,\u001b9XSRDgj\u0005\u0002\u00025A\u00111DH\u0007\u00029)\tQ$A\u0003tG\u0006d\u0017-\u0003\u0002 9\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001\f\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0007\u0011*x\u000f\u0006\u0002&sR\u0011a\u0005\u001f\t\u0005/\u0011!h/F\u0002)k}\u001a\"\u0001B\u0015\u0011\u0007)js&D\u0001,\u0015\ta\u0013#A\u0003ti\u0006<W-\u0003\u0002/W\tQqI]1qQN#\u0018mZ3\u0011\tA\n4GP\u0007\u0002#%\u0011!'\u0005\u0002\u0012+:Lgm\u001c:n\r\u0006t\u0017J\\*iCB,\u0007C\u0001\u001b6\u0019\u0001!QA\u000e\u0003C\u0002]\u0012\u0011!Q\t\u0003qm\u0002\"aG\u001d\n\u0005ib\"a\u0002(pi\"Lgn\u001a\t\u00037qJ!!\u0010\u000f\u0003\u0007\u0005s\u0017\u0010\u0005\u00025\u007f\u0011)\u0001\t\u0002b\u0001o\t\tq*\u0001\u0004{SB\u0004XM\u001d\t\u00057\r+e(\u0003\u0002E9\tIa)\u001e8di&|g.\r\t\u0004\r.\u001bT\"A$\u000b\u0005!K\u0015!C5n[V$\u0018M\u00197f\u0015\tQE$\u0001\u0006d_2dWm\u0019;j_:L!\u0001T$\u0003\u0007M+\u0017/A\u0001o!\tYr*\u0003\u0002Q9\t\u0019\u0011J\u001c;\u0015\u0005I+FCA*U!\u00119Ba\r \t\u000b5;\u0001\u0019\u0001(\t\u000b\u0005;\u0001\u0019\u0001\"\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001Y!\t\u0001\u0014,\u0003\u0002[#\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003=\naa\u001d5ba\u0016\u0004\u0013aA8viV\t\u0001\rE\u00021CzJ!AY\t\u0003\r=+H\u000f\\3u\u0003-\u0019'/Z1uK2{w-[2\u0015\u0005\u0015D\u0007C\u0001\u0016g\u0013\t97FA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015IG\u00021\u0001Y\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<G#\u00017\u0011\u00055\u0014X\"\u00018\u000b\u0005=\u0004\u0018\u0001\u00027b]\u001eT\u0011!]\u0001\u0005U\u00064\u0018-\u0003\u0002t]\n11\u000b\u001e:j]\u001e\u0004\"\u0001N;\u0005\u000bY\u001a!\u0019A\u001c\u0011\u0005Q:H!\u0002!\u0004\u0005\u00049\u0004\"B'\u0004\u0001\u0004q\u0005\"B!\u0004\u0001\u0004Q\b\u0003B\u000eDwZ\u00042AR&u\u0001")
public class ZipWithN<A, O>
extends GraphStage<UniformFanInShape<A, O>> {
    public final Function1<Seq<A>, O> akka$stream$scaladsl$ZipWithN$$zipper;
    public final int akka$stream$scaladsl$ZipWithN$$n;
    private final UniformFanInShape<A, O> shape;

    public static <A, O> ZipWithN<A, O> apply(Function1<Seq<A>, O> zipper, int n) {
        return ZipWithN$.MODULE$.apply(zipper, n);
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.zipWithN();
    }

    @Override
    public UniformFanInShape<A, O> shape() {
        return this.shape;
    }

    public Outlet<O> out() {
        return ((FanInShape)this.shape()).out();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private int akka$stream$scaladsl$ZipWithN$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWithN$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWithN$$anon$$contextPropagation;
            private final Function1<Inlet<A>, A> grabInlet;
            private final Function1<Inlet<A>, BoxedUnit> pullInlet;
            private final /* synthetic */ ZipWithN $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public int akka$stream$scaladsl$ZipWithN$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWithN$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWithN$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWithN$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWithN$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWithN$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWithN$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWithN$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWithN$$anon$$contextPropagation;
            }

            private Function1<Inlet<A>, A> grabInlet() {
                return this.grabInlet;
            }

            private Function1<Inlet<A>, BoxedUnit> pullInlet() {
                return this.pullInlet;
            }

            public void akka$stream$scaladsl$ZipWithN$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWithN$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.akka$stream$scaladsl$ZipWithN$$zipper.apply(((UniformFanInShape)this.$outer.shape()).inlets().map(this.grabInlet())));
                if (this.willShutDown()) {
                    this.completeStage();
                    return;
                }
                ((UniformFanInShape)this.$outer.shape()).inlets().foreach(this.pullInlet());
            }

            public void preStart() {
                ((UniformFanInShape)this.$outer.shape()).inlets().foreach(this.pullInlet());
            }

            public void onPull() {
                this.akka$stream$scaladsl$ZipWithN$$anon$$pending_$eq(this.akka$stream$scaladsl$ZipWithN$$anon$$pending() + this.$outer.akka$stream$scaladsl$ZipWithN$$n);
                if (this.akka$stream$scaladsl$ZipWithN$$anon$$pending() == 0) {
                    this.akka$stream$scaladsl$ZipWithN$$anon$$pushAll();
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$pullInlet$1($anon$20 $this, Inlet in) {
                $this.pull(in);
            }

            public static final /* synthetic */ void $anonfun$new$14($anon$20 $this, Tuple2 x0$1) {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    Inlet in = (Inlet)tuple2._1();
                    int i = tuple2._2$mcI$sp();
                    $this.setHandler(in, new InHandler($this, i, in){
                        private final /* synthetic */ $anon$20 $outer;
                        private final int i$4;
                        private final Inlet in$1;

                        public void onUpstreamFailure(Throwable ex) throws Exception {
                            InHandler.onUpstreamFailure$(this, ex);
                        }

                        public void onPush() {
                            if (this.i$4 == 0) {
                                this.$outer.akka$stream$scaladsl$ZipWithN$$anon$$contextPropagation().suspendContext();
                            }
                            this.$outer.akka$stream$scaladsl$ZipWithN$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWithN$$anon$$pending() - 1);
                            if (this.$outer.akka$stream$scaladsl$ZipWithN$$anon$$pending() == 0) {
                                this.$outer.akka$stream$scaladsl$ZipWithN$$anon$$pushAll();
                                return;
                            }
                        }

                        public void onUpstreamFinish() {
                            if (!this.$outer.isAvailable(this.in$1)) {
                                this.$outer.completeStage();
                            }
                            this.$outer.akka$stream$scaladsl$ZipWithN$$anon$$willShutDown_$eq(true);
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.i$4 = i$4;
                            this.in$1 = in$1;
                            InHandler.$init$(this);
                        }
                    });
                    return;
                }
                throw new MatchError(tuple2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.akka$stream$scaladsl$ZipWithN$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWithN$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWithN$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.grabInlet = (Function1<Inlet, Object> & Serializable)in -> this.grab(in);
                this.pullInlet = (Function1<Inlet, Object> & Serializable)in -> {
                    $anon$20.$anonfun$pullInlet$1(this, in);
                    return BoxedUnit.UNIT;
                };
                ((IterableOnceOps)((UniformFanInShape)$outer.shape()).inlets().zipWithIndex()).foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$20.$anonfun$new$14(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$grabInlet$1(akka.stream.scaladsl.ZipWithN$$anon$20 akka.stream.Inlet ), $anonfun$new$14$adapted(akka.stream.scaladsl.ZipWithN$$anon$20 scala.Tuple2 ), $anonfun$pullInlet$1$adapted(akka.stream.scaladsl.ZipWithN$$anon$20 akka.stream.Inlet )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "ZipWithN";
    }

    public ZipWithN(Function1<Seq<A>, O> zipper, int n) {
        this.akka$stream$scaladsl$ZipWithN$$zipper = zipper;
        this.akka$stream$scaladsl$ZipWithN$$n = n;
        this.shape = new UniformFanInShape(n);
    }
}

