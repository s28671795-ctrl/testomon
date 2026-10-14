/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.UniformFanInShape;
import akka.stream.UniformFanInShape$;
import akka.stream.scaladsl.Interleave$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.IndexedSeq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005er!B\u000b\u0017\u0011\u0003ib!B\u0010\u0017\u0011\u0003\u0001\u0003\"B\u0014\u0002\t\u0003A\u0003\"B\u0015\u0002\t\u0003Q\u0003b\u0002(\u0002#\u0003%\ta\u0014\u0004\u0005?Y\u0011A\f\u0003\u0005D\u000b\t\u0015\r\u0011\"\u0001h\u0011!AWA!A!\u0002\u0013!\u0005\u0002\u0003%\u0006\u0005\u000b\u0007I\u0011A4\t\u0011%,!\u0011!Q\u0001\n\u0011C\u0001BS\u0003\u0003\u0006\u0004%\tA\u001b\u0005\tW\u0016\u0011\t\u0011)A\u0005\u0017\")q%\u0002C\u0001Y\"9\u0011/\u0002b\u0001\n\u0003\u0011\bB\u0002@\u0006A\u0003%1\u000f\u0003\u0005\u0000\u000b\t\u0007I\u0011AA\u0001\u0011!\tI!\u0002Q\u0001\n\u0005\r\u0001\"CA\u0006\u000b\t\u0007I\u0011IA\u0007\u0011\u001d\ty!\u0002Q\u0001\n\u0011Dq!!\u0005\u0006\t\u0003\n\u0019\u0002C\u0004\u0002&\u0015!\t%a\n\u0002\u0015%sG/\u001a:mK\u00064XM\u0003\u0002\u00181\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u001a5\u000511\u000f\u001e:fC6T\u0011aG\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005y\tQ\"\u0001\f\u0003\u0015%sG/\u001a:mK\u00064Xm\u0005\u0002\u0002CA\u0011!%J\u0007\u0002G)\tA%A\u0003tG\u0006d\u0017-\u0003\u0002'G\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\u000f\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0005-*D\u0003\u0002\u0017C\u000f&\u0003B!\f\u00181}5\t\u0001$\u0003\u000201\t)qI]1qQB!Q&M\u001a4\u0013\t\u0011\u0004DA\tV]&4wN]7GC:Len\u00155ba\u0016\u0004\"\u0001N\u001b\r\u0001\u0011)ag\u0001b\u0001o\t\tA+\u0005\u00029wA\u0011!%O\u0005\u0003u\r\u0012qAT8uQ&tw\r\u0005\u0002#y%\u0011Qh\t\u0002\u0004\u0003:L\bCA A\u001b\u0005Q\u0012BA!\u001b\u0005\u001dqu\u000e^+tK\u0012DQaQ\u0002A\u0002\u0011\u000b!\"\u001b8qkR\u0004vN\u001d;t!\t\u0011S)\u0003\u0002GG\t\u0019\u0011J\u001c;\t\u000b!\u001b\u0001\u0019\u0001#\u0002\u0017M,w-\\3oiNK'0\u001a\u0005\b\u0015\u000e\u0001\n\u00111\u0001L\u0003))\u0017mZ3s\u00072|7/\u001a\t\u0003E1K!!T\u0012\u0003\u000f\t{w\u000e\\3b]\u0006y\u0011\r\u001d9ms\u0012\"WMZ1vYR$3'\u0006\u0002Q7V\t\u0011K\u000b\u0002L%.\n1\u000b\u0005\u0002U36\tQK\u0003\u0002W/\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u00031\u000e\n!\"\u00198o_R\fG/[8o\u0013\tQVKA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$QA\u000e\u0003C\u0002]*\"!\u00184\u0014\u0005\u0015q\u0006cA0cI6\t\u0001M\u0003\u0002b1\u0005)1\u000f^1hK&\u00111\r\u0019\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007\u0003B\u00172K\u0016\u0004\"\u0001\u000e4\u0005\u000bY*!\u0019A\u001c\u0016\u0003\u0011\u000b1\"\u001b8qkR\u0004vN\u001d;tA\u0005a1/Z4nK:$8+\u001b>fAU\t1*A\u0006fC\u001e,'o\u00117pg\u0016\u0004C\u0003B7o_B\u00042AH\u0003f\u0011\u0015\u0019E\u00021\u0001E\u0011\u0015AE\u00021\u0001E\u0011\u0015QE\u00021\u0001L\u0003\tIg.F\u0001t!\r!\u0018p_\u0007\u0002k*\u0011ao^\u0001\nS6lW\u000f^1cY\u0016T!\u0001_\u0012\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002{k\nQ\u0011J\u001c3fq\u0016$7+Z9\u0011\u00075bX-\u0003\u0002~1\t)\u0011J\u001c7fi\u0006\u0019\u0011N\u001c\u0011\u0002\u0007=,H/\u0006\u0002\u0002\u0004A!Q&!\u0002f\u0013\r\t9\u0001\u0007\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0006g\"\f\u0007/Z\u000b\u0002I\u000611\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR!\u0011QCA\u000e!\ry\u0016qC\u0005\u0004\u00033\u0001'aD$sCBD7\u000b^1hK2{w-[2\t\u000f\u0005u1\u00031\u0001\u0002 \u0005\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fgB\u0019Q&!\t\n\u0007\u0005\r\u0002D\u0001\u0006BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003S\u0001B!a\u000b\u000265\u0011\u0011Q\u0006\u0006\u0005\u0003_\t\t$\u0001\u0003mC:<'BAA\u001a\u0003\u0011Q\u0017M^1\n\t\u0005]\u0012Q\u0006\u0002\u0007'R\u0014\u0018N\\4")
public final class Interleave<T>
extends GraphStage<UniformFanInShape<T, T>> {
    private final int inputPorts;
    private final int segmentSize;
    private final boolean eagerClose;
    private final IndexedSeq<Inlet<T>> in;
    private final Outlet<T> out;
    private final UniformFanInShape<T, T> shape;

    public static <T> boolean apply$default$3() {
        return Interleave$.MODULE$.apply$default$3();
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> apply(int inputPorts, int segmentSize, boolean eagerClose) {
        return Interleave$.MODULE$.apply(inputPorts, segmentSize, eagerClose);
    }

    public int inputPorts() {
        return this.inputPorts;
    }

    public int segmentSize() {
        return this.segmentSize;
    }

    public boolean eagerClose() {
        return this.eagerClose;
    }

    public IndexedSeq<Inlet<T>> in() {
        return this.in;
    }

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public UniformFanInShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private int akka$stream$scaladsl$Interleave$$anon$$counter;
            private int currentUpstreamIndex;
            private int akka$stream$scaladsl$Interleave$$anon$$runningUpstreams;
            private final /* synthetic */ Interleave $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public int akka$stream$scaladsl$Interleave$$anon$$counter() {
                return this.akka$stream$scaladsl$Interleave$$anon$$counter;
            }

            public void akka$stream$scaladsl$Interleave$$anon$$counter_$eq(int x$1) {
                this.akka$stream$scaladsl$Interleave$$anon$$counter = x$1;
            }

            private int currentUpstreamIndex() {
                return this.currentUpstreamIndex;
            }

            private void currentUpstreamIndex_$eq(int x$1) {
                this.currentUpstreamIndex = x$1;
            }

            public int akka$stream$scaladsl$Interleave$$anon$$runningUpstreams() {
                return this.akka$stream$scaladsl$Interleave$$anon$$runningUpstreams;
            }

            public void akka$stream$scaladsl$Interleave$$anon$$runningUpstreams_$eq(int x$1) {
                this.akka$stream$scaladsl$Interleave$$anon$$runningUpstreams = x$1;
            }

            public boolean akka$stream$scaladsl$Interleave$$anon$$upstreamsClosed() {
                return this.akka$stream$scaladsl$Interleave$$anon$$runningUpstreams() == 0;
            }

            public Inlet<T> akka$stream$scaladsl$Interleave$$anon$$currentUpstream() {
                return (Inlet)this.$outer.in().apply(this.currentUpstreamIndex());
            }

            public void akka$stream$scaladsl$Interleave$$anon$$switchToNextInput() {
                this.akka$stream$scaladsl$Interleave$$anon$$counter_$eq(0);
                this.currentUpstreamIndex_$eq(this.nextInletIndex$1(this.currentUpstreamIndex()));
            }

            public void onPull() {
                if (!this.hasBeenPulled(this.akka$stream$scaladsl$Interleave$$anon$$currentUpstream())) {
                    this.tryPull(this.akka$stream$scaladsl$Interleave$$anon$$currentUpstream());
                    return;
                }
            }

            public /* synthetic */ Interleave akka$stream$scaladsl$Interleave$$anon$$$outer() {
                return this.$outer;
            }

            private final int nextInletIndex$1(int index) {
                while (true) {
                    int successor;
                    int n = index + 1;
                    int n2 = successor = this.$outer.inputPorts() == n ? 0 : n;
                    if (!this.isClosed((Inlet)this.$outer.in().apply(successor))) {
                        return successor;
                    }
                    if (successor == this.currentUpstreamIndex()) break;
                    index = successor;
                }
                this.completeStage();
                return 0;
            }

            public static final /* synthetic */ void $anonfun$new$9($anon$8 $this, Inlet i) {
                $this.setHandler(i, new InHandler($this, i){
                    private final /* synthetic */ $anon$8 $outer;
                    private final Inlet i$2;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.push(this.$outer.akka$stream$scaladsl$Interleave$$anon$$$outer().out(), this.$outer.grab(this.i$2));
                        this.$outer.akka$stream$scaladsl$Interleave$$anon$$counter_$eq(this.$outer.akka$stream$scaladsl$Interleave$$anon$$counter() + 1);
                        if (this.$outer.akka$stream$scaladsl$Interleave$$anon$$counter() == this.$outer.akka$stream$scaladsl$Interleave$$anon$$$outer().segmentSize()) {
                            this.$outer.akka$stream$scaladsl$Interleave$$anon$$switchToNextInput();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.akka$stream$scaladsl$Interleave$$anon$$$outer().eagerClose()) {
                            this.$outer.akka$stream$scaladsl$Interleave$$anon$$runningUpstreams_$eq(this.$outer.akka$stream$scaladsl$Interleave$$anon$$runningUpstreams() - 1);
                            if (!this.$outer.akka$stream$scaladsl$Interleave$$anon$$upstreamsClosed()) {
                                Inlet inlet = this.i$2;
                                Inlet<T> inlet2 = this.$outer.akka$stream$scaladsl$Interleave$$anon$$currentUpstream();
                                if (!(inlet != null ? !((Object)inlet).equals(inlet2) : inlet2 != null)) {
                                    this.$outer.akka$stream$scaladsl$Interleave$$anon$$switchToNextInput();
                                    if (this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$Interleave$$anon$$$outer().out())) {
                                        this.$outer.pull(this.$outer.akka$stream$scaladsl$Interleave$$anon$$currentUpstream());
                                        return;
                                    }
                                    return;
                                }
                                return;
                            }
                            this.$outer.completeStage();
                            return;
                        }
                        this.$outer.completeStage();
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.i$2 = i$2;
                        InHandler.$init$(this);
                    }
                });
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.akka$stream$scaladsl$Interleave$$anon$$counter = 0;
                this.currentUpstreamIndex = 0;
                this.akka$stream$scaladsl$Interleave$$anon$$runningUpstreams = $outer.inputPorts();
                $outer.in().foreach((Function1<Inlet, Object> & Serializable)i -> {
                    $anon$8.$anonfun$new$9(this, i);
                    return BoxedUnit.UNIT;
                });
                this.setHandler($outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$new$9$adapted(akka.stream.scaladsl.Interleave$$anon$8 akka.stream.Inlet )}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "Interleave";
    }

    public Interleave(int inputPorts, int segmentSize, boolean eagerClose) {
        this.inputPorts = inputPorts;
        this.segmentSize = segmentSize;
        this.eagerClose = eagerClose;
        Predef$.MODULE$.require(inputPorts > 1, (Function0<Object>)(Function0<String> & Serializable)() -> "input ports must be > 1");
        Predef$.MODULE$.require(segmentSize > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "segmentSize must be > 0");
        this.in = (IndexedSeq)package$.MODULE$.Vector().tabulate(inputPorts, (Function1<Object, Inlet> & Serializable)i -> Inlet$.MODULE$.apply(new StringBuilder(13).append("Interleave.in").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.out = Outlet$.MODULE$.apply("Interleave.out");
        this.shape = UniformFanInShape$.MODULE$.apply(this.out(), this.in());
    }
}

