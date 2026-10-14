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
import akka.stream.scaladsl.MergeLatest$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.HashSet;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005Us!\u0002\u000b\u0016\u0011\u0003ab!\u0002\u0010\u0016\u0011\u0003y\u0002\"\u0002\u0014\u0002\t\u00039\u0003\"\u0002\u0015\u0002\t\u0003I\u0003b\u0002,\u0002#\u0003%\ta\u0016\u0004\u0005=U\u0011A\r\u0003\u0005N\u000b\t\u0015\r\u0011\"\u0001n\u0011!qWA!A!\u0002\u0013q\u0005\u0002C8\u0006\u0005\u000b\u0007I\u0011\u00019\t\u0011E,!\u0011!Q\u0001\nMC\u0001B]\u0003\u0003\u0002\u0003\u0006Ia\u001d\u0005\u0006M\u0015!\t!\u001f\u0005\t\u007f\u0016\u0011\r\u0011\"\u0001\u0002\u0002!A\u0011\u0011D\u0003!\u0002\u0013\t\u0019\u0001C\u0005\u0002\u001c\u0015\u0011\r\u0011\"\u0001\u0002\u001e!A\u0011QE\u0003!\u0002\u0013\ty\u0002C\u0005\u0002(\u0015\u0011\r\u0011\"\u0011\u0002*!9\u00111F\u0003!\u0002\u00139\u0007bBA\u0017\u000b\u0011\u0005\u0013q\u0006\u0005\b\u0003\u0003*A\u0011IA\"\u0003-iUM]4f\u0019\u0006$Xm\u001d;\u000b\u0005Y9\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005aI\u0012AB:ue\u0016\fWNC\u0001\u001b\u0003\u0011\t7n[1\u0004\u0001A\u0011Q$A\u0007\u0002+\tYQ*\u001a:hK2\u000bG/Z:u'\t\t\u0001\u0005\u0005\u0002\"I5\t!EC\u0001$\u0003\u0015\u00198-\u00197b\u0013\t)#E\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003q\tQ!\u00199qYf,\"AK\u001c\u0015\u0007-b\u0015\u000bE\u0002-_Ej\u0011!\f\u0006\u0003]]\tQa\u001d;bO\u0016L!\u0001M\u0017\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\r\u0005\u00033gU\u0002U\"A\f\n\u0005Q:\"!E+oS\u001a|'/\u001c$b]&s7\u000b[1qKB\u0011ag\u000e\u0007\u0001\t\u0015A4A1\u0001:\u0005\u0005!\u0016C\u0001\u001e>!\t\t3(\u0003\u0002=E\t9aj\u001c;iS:<\u0007CA\u0011?\u0013\ty$EA\u0002B]f\u00042!Q%6\u001d\t\u0011uI\u0004\u0002D\r6\tAI\u0003\u0002F7\u00051AH]8pizJ\u0011aI\u0005\u0003\u0011\n\nq\u0001]1dW\u0006<W-\u0003\u0002K\u0017\n!A*[:u\u0015\tA%\u0005C\u0003N\u0007\u0001\u0007a*\u0001\u0006j]B,H\u000fU8siN\u0004\"!I(\n\u0005A\u0013#aA%oi\"9!k\u0001I\u0001\u0002\u0004\u0019\u0016!D3bO\u0016\u00148i\\7qY\u0016$X\r\u0005\u0002\")&\u0011QK\t\u0002\b\u0005>|G.Z1o\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u0012TC\u0001-d+\u0005I&FA*[W\u0005Y\u0006C\u0001/b\u001b\u0005i&B\u00010`\u0003%)hn\u00195fG.,GM\u0003\u0002aE\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\tl&!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)\u0001\b\u0002b\u0001sU\u0019Q-[6\u0014\u0005\u00151\u0007c\u0001\u00170OB!!g\r5k!\t1\u0014\u000eB\u00039\u000b\t\u0007\u0011\b\u0005\u00027W\u0012)A.\u0002b\u0001s\t\tQ*F\u0001O\u0003-Ig\u000e];u!>\u0014Ho\u001d\u0011\u0002\u0015\u0015\fw-\u001a:DY>\u001cX-F\u0001T\u0003-)\u0017mZ3s\u00072|7/\u001a\u0011\u0002\u0013\t,\u0018\u000e\u001c3FY\u0016l\u0007\u0003B\u0011um*L!!\u001e\u0012\u0003\u0013\u0019+hn\u0019;j_:\f\u0004cA\u0011xQ&\u0011\u0001P\t\u0002\u0006\u0003J\u0014\u0018-\u001f\u000b\u0004uvtHCA>}!\u0011iR\u0001\u001b6\t\u000bI\\\u0001\u0019A:\t\u000b5[\u0001\u0019\u0001(\t\u000b=\\\u0001\u0019A*\u0002\u0005%tWCAA\u0002!\u0019\t)!a\u0004\u0002\u00145\u0011\u0011q\u0001\u0006\u0005\u0003\u0013\tY!A\u0005j[6,H/\u00192mK*\u0019\u0011Q\u0002\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u0012\u0005\u001d!AC%oI\u0016DX\rZ*fcB!!'!\u0006i\u0013\r\t9b\u0006\u0002\u0006\u0013:dW\r^\u0001\u0004S:\u0004\u0013aA8viV\u0011\u0011q\u0004\t\u0005e\u0005\u0005\".C\u0002\u0002$]\u0011aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\u0012aZ\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0005\u0003c\t9\u0004E\u0002-\u0003gI1!!\u000e.\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007bBA\u001d%\u0001\u0007\u00111H\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\t\u0004e\u0005u\u0012bAA /\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0012\u0011\t\u0005\u001d\u0013\u0011K\u0007\u0003\u0003\u0013RA!a\u0013\u0002N\u0005!A.\u00198h\u0015\t\ty%\u0001\u0003kCZ\f\u0017\u0002BA*\u0003\u0013\u0012aa\u0015;sS:<\u0007")
public final class MergeLatest<T, M>
extends GraphStage<UniformFanInShape<T, M>> {
    private final int inputPorts;
    private final boolean eagerClose;
    public final Function1<Object, M> akka$stream$scaladsl$MergeLatest$$buildElem;
    private final IndexedSeq<Inlet<T>> in;
    private final Outlet<M> out;
    private final UniformFanInShape<T, M> shape;

    public static <T> boolean apply$default$2() {
        return MergeLatest$.MODULE$.apply$default$2();
    }

    public static <T> GraphStage<UniformFanInShape<T, List<T>>> apply(int inputPorts, boolean eagerComplete) {
        return MergeLatest$.MODULE$.apply(inputPorts, eagerComplete);
    }

    public int inputPorts() {
        return this.inputPorts;
    }

    public boolean eagerClose() {
        return this.eagerClose;
    }

    public IndexedSeq<Inlet<T>> in() {
        return this.in;
    }

    public Outlet<M> out() {
        return this.out;
    }

    @Override
    public UniformFanInShape<T, M> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private final HashSet<Object> akka$stream$scaladsl$MergeLatest$$anon$$activeStreams;
            private int akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams;
            private final Object[] akka$stream$scaladsl$MergeLatest$$anon$$messages;
            private final /* synthetic */ MergeLatest $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public HashSet<Object> akka$stream$scaladsl$MergeLatest$$anon$$activeStreams() {
                return this.akka$stream$scaladsl$MergeLatest$$anon$$activeStreams;
            }

            public int akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams() {
                return this.akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams;
            }

            public void akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams_$eq(int x$1) {
                this.akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams = x$1;
            }

            public boolean akka$stream$scaladsl$MergeLatest$$anon$$upstreamsClosed() {
                return this.akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams() == 0;
            }

            public boolean akka$stream$scaladsl$MergeLatest$$anon$$allMessagesReady() {
                return this.akka$stream$scaladsl$MergeLatest$$anon$$activeStreams().size() == this.$outer.inputPorts();
            }

            public Object[] akka$stream$scaladsl$MergeLatest$$anon$$messages() {
                return this.akka$stream$scaladsl$MergeLatest$$anon$$messages;
            }

            public void preStart() {
                this.$outer.in().foreach((Function1<Inlet, Object> & Serializable)in -> {
                    $anon$1.$anonfun$preStart$1(this, in);
                    return BoxedUnit.UNIT;
                });
            }

            public void onPull() {
                for (int i = 0; i < this.$outer.inputPorts(); ++i) {
                    if (this.hasBeenPulled((Inlet)this.$outer.in().apply(i))) continue;
                    this.tryPull((Inlet)this.$outer.in().apply(i));
                }
            }

            public /* synthetic */ MergeLatest akka$stream$scaladsl$MergeLatest$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$1 $this, Inlet in) {
                $this.tryPull(in);
            }

            public static final /* synthetic */ void $anonfun$new$2($anon$1 $this, Tuple2 x0$1) {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    Inlet input = (Inlet)tuple2._1();
                    int index = tuple2._2$mcI$sp();
                    $this.setHandler(input, new InHandler($this, index, input){
                        private final /* synthetic */ $anon$1 $outer;
                        private final int index$1;
                        private final Inlet input$1;

                        public void onUpstreamFailure(Throwable ex) throws Exception {
                            InHandler.onUpstreamFailure$(this, ex);
                        }

                        public void onPush() {
                            this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$messages()[this.index$1] = this.$outer.grab(this.input$1);
                            this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$activeStreams().add(BoxesRunTime.boxToInteger(this.index$1));
                            if (this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$allMessagesReady()) {
                                this.$outer.emit(this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$$outer().out(), this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$$outer().akka$stream$scaladsl$MergeLatest$$buildElem.apply(this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$messages()));
                            }
                            this.$outer.tryPull(this.input$1);
                        }

                        public void onUpstreamFinish() {
                            if (!this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$$outer().eagerClose()) {
                                this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams_$eq(this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams() - 1);
                                if (this.$outer.akka$stream$scaladsl$MergeLatest$$anon$$upstreamsClosed()) {
                                    this.$outer.completeStage();
                                    return;
                                }
                                return;
                            }
                            this.$outer.completeStage();
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.index$1 = index$1;
                            this.input$1 = input$1;
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
                this.akka$stream$scaladsl$MergeLatest$$anon$$activeStreams = new HashSet<E>();
                this.akka$stream$scaladsl$MergeLatest$$anon$$runningUpstreams = $outer.inputPorts();
                this.akka$stream$scaladsl$MergeLatest$$anon$$messages = new Object[$outer.inputPorts()];
                ((IterableOnceOps)$outer.in().zipWithIndex()).foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$new$2(this, x0$1);
                    return BoxedUnit.UNIT;
                });
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$new$2$adapted(akka.stream.scaladsl.MergeLatest$$anon$1 scala.Tuple2 ), $anonfun$preStart$1$adapted(akka.stream.scaladsl.MergeLatest$$anon$1 akka.stream.Inlet )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "MergeLatest";
    }

    public MergeLatest(int inputPorts, boolean eagerClose, Function1<Object, M> buildElem) {
        this.inputPorts = inputPorts;
        this.eagerClose = eagerClose;
        this.akka$stream$scaladsl$MergeLatest$$buildElem = buildElem;
        Predef$.MODULE$.require(inputPorts >= 1, (Function0<Object>)(Function0<String> & Serializable)() -> "input ports must be >= 1");
        this.in = (IndexedSeq)package$.MODULE$.Vector().tabulate(inputPorts, (Function1<Object, Inlet> & Serializable)i -> Inlet$.MODULE$.apply(new StringBuilder(14).append("MergeLatest.in").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.out = Outlet$.MODULE$.apply("MergeLatest.out");
        this.shape = UniformFanInShape$.MODULE$.apply(this.out(), this.in());
    }
}

