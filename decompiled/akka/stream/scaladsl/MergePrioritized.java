/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.UniformFanInShape;
import akka.stream.UniformFanInShape$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.MergePrioritized$;
import akka.stream.scaladsl.MergePrioritized$$anon$6$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.SplittableRandom;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005}r!\u0002\u000b\u0016\u0011\u0003ab!\u0002\u0010\u0016\u0011\u0003y\u0002\"\u0002\u0014\u0002\t\u00039\u0003\"\u0002\u0015\u0002\t\u0003I\u0003b\u0002,\u0002#\u0003%\ta\u0016\u0004\u0005=U\u0011A\r\u0003\u0005B\u000b\t\u0015\r\u0011\"\u0001k\u0011!YWA!A!\u0002\u0013\u0011\u0005\u0002\u0003*\u0006\u0005\u000b\u0007I\u0011\u00017\t\u00115,!\u0011!Q\u0001\nMCQAJ\u0003\u0005\n9DqA]\u0003C\u0002\u0013\u00051\u000f\u0003\u0004\u0000\u000b\u0001\u0006I\u0001\u001e\u0005\n\u0003\u0003)!\u0019!C\u0001\u0003\u0007A\u0001\"a\u0003\u0006A\u0003%\u0011Q\u0001\u0005\b\u0003\u001b)A\u0011IA\b\u0011%\t9\"\u0002b\u0001\n\u0003\nI\u0002C\u0004\u0002\u001c\u0015\u0001\u000b\u0011B4\t\u000f\u0005uQ\u0001\"\u0011\u0002 !9\u00111F\u0003\u0005B\u00055\u0012\u0001E'fe\u001e,\u0007K]5pe&$\u0018N_3e\u0015\t1r#\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\tA\u0012$\u0001\u0004tiJ,\u0017-\u001c\u0006\u00025\u0005!\u0011m[6b\u0007\u0001\u0001\"!H\u0001\u000e\u0003U\u0011\u0001#T3sO\u0016\u0004&/[8sSRL'0\u001a3\u0014\u0005\u0005\u0001\u0003CA\u0011%\u001b\u0005\u0011#\"A\u0012\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0015\u0012#AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u00029\u0005)\u0011\r\u001d9msV\u0011!f\u000e\u000b\u0004W\u0001\u000b\u0006c\u0001\u00170c5\tQF\u0003\u0002//\u0005)1\u000f^1hK&\u0011\u0001'\f\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007\u0003\u0002\u001a4kUj\u0011aF\u0005\u0003i]\u0011\u0011#\u00168jM>\u0014XNR1o\u0013:\u001c\u0006.\u00199f!\t1t\u0007\u0004\u0001\u0005\u000ba\u001a!\u0019A\u001d\u0003\u0003Q\u000b\"AO\u001f\u0011\u0005\u0005Z\u0014B\u0001\u001f#\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\t \n\u0005}\u0012#aA!os\")\u0011i\u0001a\u0001\u0005\u0006Q\u0001O]5pe&$\u0018.Z:\u0011\u0007\r[eJ\u0004\u0002E\u0013:\u0011Q\tS\u0007\u0002\r*\u0011qiG\u0001\u0007yI|w\u000e\u001e \n\u0003\rJ!A\u0013\u0012\u0002\u000fA\f7m[1hK&\u0011A*\u0014\u0002\u0004'\u0016\f(B\u0001&#!\t\ts*\u0003\u0002QE\t\u0019\u0011J\u001c;\t\u000fI\u001b\u0001\u0013!a\u0001'\u0006iQ-Y4fe\u000e{W\u000e\u001d7fi\u0016\u0004\"!\t+\n\u0005U\u0013#a\u0002\"p_2,\u0017M\\\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u0001lY\u000b\u00023*\u00121KW\u0016\u00027B\u0011A,Y\u0007\u0002;*\u0011alX\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001\u0019\u0012\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002c;\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000ba\"!\u0019A\u001d\u0016\u0005\u0015L7CA\u0003g!\rasf\u001a\t\u0005eMB\u0007\u000e\u0005\u00027S\u0012)\u0001(\u0002b\u0001sU\t!)A\u0006qe&|'/\u001b;jKN\u0004S#A*\u0002\u001d\u0015\fw-\u001a:D_6\u0004H.\u001a;fAQ\u0019q\u000e]9\u0011\u0007u)\u0001\u000eC\u0003B\u0015\u0001\u0007!\tC\u0003S\u0015\u0001\u00071+\u0001\u0002j]V\tA\u000fE\u0002vurl\u0011A\u001e\u0006\u0003ob\f\u0011\"[7nkR\f'\r\\3\u000b\u0005e\u0014\u0013AC2pY2,7\r^5p]&\u00111P\u001e\u0002\u000b\u0013:$W\r_3e'\u0016\f\bc\u0001\u001a~Q&\u0011ap\u0006\u0002\u0006\u0013:dW\r^\u0001\u0004S:\u0004\u0013aA8viV\u0011\u0011Q\u0001\t\u0005e\u0005\u001d\u0001.C\u0002\u0002\n]\u0011aaT;uY\u0016$\u0018\u0001B8vi\u0002\n\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\t\t\t\u0002E\u00023\u0003'I1!!\u0006\u0018\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002O\u000611\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR!\u0011\u0011EA\u0014!\ra\u00131E\u0005\u0004\u0003Ki#aD$sCBD7\u000b^1hK2{w-[2\t\u000f\u0005%\"\u00031\u0001\u0002\u0012\u0005\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u00020A!\u0011\u0011GA\u001e\u001b\t\t\u0019D\u0003\u0003\u00026\u0005]\u0012\u0001\u00027b]\u001eT!!!\u000f\u0002\t)\fg/Y\u0005\u0005\u0003{\t\u0019D\u0001\u0004TiJLgn\u001a")
public final class MergePrioritized<T>
extends GraphStage<UniformFanInShape<T, T>> {
    private final Seq<Object> priorities;
    private final boolean eagerComplete;
    private final IndexedSeq<Inlet<T>> in;
    private final Outlet<T> out;
    private final UniformFanInShape<T, T> shape;

    public static <T> boolean apply$default$2() {
        return MergePrioritized$.MODULE$.apply$default$2();
    }

    public static <T> GraphStage<UniformFanInShape<T, T>> apply(Seq<Object> priorities, boolean eagerComplete) {
        return MergePrioritized$.MODULE$.apply(priorities, eagerComplete);
    }

    public Seq<Object> priorities() {
        return this.priorities;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    public IndexedSeq<Inlet<T>> in() {
        return this.in;
    }

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.mergePrioritized();
    }

    @Override
    public UniformFanInShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private int akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams;
            private final SplittableRandom randomGen;
            private final /* synthetic */ MergePrioritized $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public int akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams() {
                return this.akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams;
            }

            public void akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams_$eq(int x$1) {
                this.akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams = x$1;
            }

            private SplittableRandom randomGen() {
                return this.randomGen;
            }

            public void preStart() {
                this.$outer.in().foreach((Function1<Inlet, Object> & Serializable)in -> {
                    $anon$6.$anonfun$preStart$2(this, in);
                    return BoxedUnit.UNIT;
                });
            }

            public void onPull() {
                Inlet<T> in = this.select();
                if (in != null) {
                    this.push(this.$outer.out(), this.grab(in));
                    if (this.akka$stream$scaladsl$MergePrioritized$$anon$$upstreamsClosed() && !this.akka$stream$scaladsl$MergePrioritized$$anon$$hasPending()) {
                        this.completeStage();
                        return;
                    }
                    this.tryPull(in);
                    return;
                }
            }

            public boolean akka$stream$scaladsl$MergePrioritized$$anon$$hasPending() {
                return this.$outer.in().exists((Function1<Inlet, Object> & Serializable)inlet -> BoxesRunTime.boxToBoolean($anon$6.$anonfun$hasPending$1(this, inlet)));
            }

            public boolean akka$stream$scaladsl$MergePrioritized$$anon$$hasOtherInletAvailable(Inlet<T> excludeInlet) {
                return this.$outer.in().exists((Function1<Inlet, Object> & Serializable)inlet -> BoxesRunTime.boxToBoolean($anon$6.$anonfun$hasOtherInletAvailable$1(this, excludeInlet, inlet)));
            }

            public boolean akka$stream$scaladsl$MergePrioritized$$anon$$upstreamsClosed() {
                return this.akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams() == 0;
            }

            private Inlet<T> select() {
                int ix;
                int tp = 0;
                for (ix = 0; ix < this.$outer.in().length(); ++ix) {
                    if (!this.isAvailable((Inlet)this.$outer.in().apply(ix))) continue;
                    tp += BoxesRunTime.unboxToInt(this.$outer.priorities().apply(ix));
                }
                if (tp == 0) {
                    return null;
                }
                int r = this.randomGen().nextInt(tp);
                Inlet next = null;
                for (ix = 0; ix < this.$outer.in().length() && next == null; ++ix) {
                    if (!this.isAvailable((Inlet)this.$outer.in().apply(ix)) || (r -= BoxesRunTime.unboxToInt(this.$outer.priorities().apply(ix))) >= 0) continue;
                    next = (Inlet)this.$outer.in().apply(ix);
                }
                return next;
            }

            public /* synthetic */ MergePrioritized akka$stream$scaladsl$MergePrioritized$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$preStart$2($anon$6 $this, Inlet in) {
                $this.tryPull(in);
            }

            public static final /* synthetic */ void $anonfun$new$6($anon$6 $this, Inlet inlet) {
                $this.setHandler(inlet, new InHandler($this, inlet){
                    private final /* synthetic */ $anon$6 $outer;
                    private final Inlet inlet$1;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        if (this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$$outer().out()) && !this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$hasOtherInletAvailable(this.inlet$1)) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$$outer().out(), this.$outer.grab(this.inlet$1));
                            this.$outer.tryPull(this.inlet$1);
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$$outer().eagerComplete()) {
                            this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$$outer().in().foreach((Function1<Inlet, Object> & Serializable)x$2 -> {
                                $anon$6$$anon$7.$anonfun$onUpstreamFinish$1(this, x$2);
                                return BoxedUnit.UNIT;
                            });
                            this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams_$eq(0);
                            if (!this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$hasPending()) {
                                this.$outer.completeStage();
                                return;
                            }
                            return;
                        }
                        this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams_$eq(this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams() - 1);
                        if (this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$upstreamsClosed() && !this.$outer.akka$stream$scaladsl$MergePrioritized$$anon$$hasPending()) {
                            this.$outer.completeStage();
                            return;
                        }
                    }

                    public static final /* synthetic */ void $anonfun$onUpstreamFinish$1($anon$6$$anon$7 $this, Inlet x$2) {
                        $this.$outer.cancel(x$2);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.inlet$1 = inlet$1;
                        InHandler.$init$(this);
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onUpstreamFinish$1$adapted(akka.stream.scaladsl.MergePrioritized$$anon$6$$anon$7 akka.stream.Inlet )}, serializedLambda);
                    }
                });
            }

            public static final /* synthetic */ boolean $anonfun$hasPending$1($anon$6 $this, Inlet inlet) {
                return $this.isAvailable(inlet);
            }

            public static final /* synthetic */ boolean $anonfun$hasOtherInletAvailable$1($anon$6 $this, Inlet excludeInlet$1, Inlet inlet) {
                return inlet != excludeInlet$1 && $this.isAvailable(inlet);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.akka$stream$scaladsl$MergePrioritized$$anon$$runningUpstreams = $outer.priorities().size();
                this.randomGen = new SplittableRandom();
                $outer.in().foreach((Function1<Inlet, Object> & Serializable)inlet -> {
                    $anon$6.$anonfun$new$6(this, inlet);
                    return BoxedUnit.UNIT;
                });
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$hasOtherInletAvailable$1$adapted(akka.stream.scaladsl.MergePrioritized$$anon$6 akka.stream.Inlet akka.stream.Inlet ), $anonfun$hasPending$1$adapted(akka.stream.scaladsl.MergePrioritized$$anon$6 akka.stream.Inlet ), $anonfun$new$6$adapted(akka.stream.scaladsl.MergePrioritized$$anon$6 akka.stream.Inlet ), $anonfun$preStart$2$adapted(akka.stream.scaladsl.MergePrioritized$$anon$6 akka.stream.Inlet )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "MergePrioritized";
    }

    public MergePrioritized(Seq<Object> priorities, boolean eagerComplete) {
        this.priorities = priorities;
        this.eagerComplete = eagerComplete;
        Predef$.MODULE$.require(priorities.nonEmpty(), (Function0<Object>)(Function0<String> & Serializable)() -> "A Merge must have one or more input ports");
        Predef$.MODULE$.require(priorities.forall(x$1 -> x$1 > 0), (Function0<Object>)(Function0<String> & Serializable)() -> "Priorities should be positive integers");
        this.in = (IndexedSeq)package$.MODULE$.Vector().tabulate(priorities.size(), (Function1<Object, Inlet> & Serializable)i -> Inlet$.MODULE$.apply(new StringBuilder(19).append("MergePrioritized.in").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.out = Outlet$.MODULE$.apply("MergePrioritized.out");
        this.shape = UniformFanInShape$.MODULE$.apply(this.out(), this.in());
    }
}

