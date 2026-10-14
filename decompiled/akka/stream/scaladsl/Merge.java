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
import akka.stream.impl.FixedSizeBuffer;
import akka.stream.impl.FixedSizeBuffer$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.Merge$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.IndexedSeq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015r!\u0002\u000b\u0016\u0011\u0003ab!\u0002\u0010\u0016\u0011\u0003y\u0002\"\u0002\u0014\u0002\t\u00039\u0003\"\u0002\u0015\u0002\t\u0003I\u0003\"CA\u0005\u0003E\u0005I\u0011AA\u0006\r\u0011qRC\u0001\u0017\t\u0011\r+!Q1A\u0005\u0002\u0011C\u0001\u0002S\u0003\u0003\u0002\u0003\u0006I!\u0012\u0005\t\u0013\u0016\u0011)\u0019!C\u0001\u0015\"Aa*\u0002B\u0001B\u0003%1\nC\u0003'\u000b\u0011\u0005q\nC\u0004T\u000b\t\u0007I\u0011\u0001+\t\r\u0001,\u0001\u0015!\u0003V\u0011\u001d\tWA1A\u0005\u0002\tDaAZ\u0003!\u0002\u0013\u0019\u0007\"B4\u0006\t\u0003B\u0007b\u00027\u0006\u0005\u0004%\t%\u001c\u0005\u0007]\u0016\u0001\u000b\u0011\u0002\u001b\t\u000b=,A\u0011\t9\t\u000bY,A\u0011I<\u0002\u000b5+'oZ3\u000b\u0005Y9\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005aI\u0012AB:ue\u0016\fWNC\u0001\u001b\u0003\u0011\t7n[1\u0004\u0001A\u0011Q$A\u0007\u0002+\t)Q*\u001a:hKN\u0011\u0011\u0001\t\t\u0003C\u0011j\u0011A\t\u0006\u0002G\u0005)1oY1mC&\u0011QE\t\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005a\u0012!B1qa2LXc\u0001\u0016\u0002\u0004Q)1&!\u0002\u0002\bA!Q$BA\u0001+\ti#h\u0005\u0002\u0006]A\u0019qF\r\u001b\u000e\u0003AR!!M\f\u0002\u000bM$\u0018mZ3\n\u0005M\u0002$AC$sCBD7\u000b^1hKB!QG\u000e\u001d9\u001b\u00059\u0012BA\u001c\u0018\u0005E)f.\u001b4pe64\u0015M\\%o'\"\f\u0007/\u001a\t\u0003sib\u0001\u0001B\u0003<\u000b\t\u0007AHA\u0001U#\ti\u0004\t\u0005\u0002\"}%\u0011qH\t\u0002\b\u001d>$\b.\u001b8h!\t\t\u0013)\u0003\u0002CE\t\u0019\u0011I\\=\u0002\u0015%t\u0007/\u001e;Q_J$8/F\u0001F!\t\tc)\u0003\u0002HE\t\u0019\u0011J\u001c;\u0002\u0017%t\u0007/\u001e;Q_J$8\u000fI\u0001\u000eK\u0006<WM]\"p[BdW\r^3\u0016\u0003-\u0003\"!\t'\n\u00055\u0013#a\u0002\"p_2,\u0017M\\\u0001\u000fK\u0006<WM]\"p[BdW\r^3!)\r\u0001\u0016K\u0015\t\u0004;\u0015A\u0004\"B\"\u000b\u0001\u0004)\u0005\"B%\u000b\u0001\u0004Y\u0015AA5o+\u0005)\u0006c\u0001,\\;6\tqK\u0003\u0002Y3\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u00035\n\n!bY8mY\u0016\u001cG/[8o\u0013\tavK\u0001\u0006J]\u0012,\u00070\u001a3TKF\u00042!\u000e09\u0013\tyvCA\u0003J]2,G/A\u0002j]\u0002\n1a\\;u+\u0005\u0019\u0007cA\u001beq%\u0011Qm\u0006\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A5\u0011\u0005UR\u0017BA6\u0018\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002i\u000511\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR\u0011\u0011\u000f\u001e\t\u0003_IL!a\u001d\u0019\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDQ!\u001e\nA\u0002%\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002qB\u0011\u0011P`\u0007\u0002u*\u00111\u0010`\u0001\u0005Y\u0006twMC\u0001~\u0003\u0011Q\u0017M^1\n\u0005}T(AB*ue&tw\rE\u0002:\u0003\u0007!QaO\u0002C\u0002qBQaQ\u0002A\u0002\u0015Cq!S\u0002\u0011\u0002\u0003\u00071*A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0011\ti!a\t\u0016\u0005\u0005=!fA&\u0002\u0012-\u0012\u00111\u0003\t\u0005\u0003+\ty\"\u0004\u0002\u0002\u0018)!\u0011\u0011DA\u000e\u0003%)hn\u00195fG.,GMC\u0002\u0002\u001e\t\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\t#a\u0006\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003<\t\t\u0007A\b")
public final class Merge<T>
extends GraphStage<UniformFanInShape<T, T>> {
    private final int inputPorts;
    private final boolean eagerComplete;
    private final IndexedSeq<Inlet<T>> in;
    private final Outlet<T> out;
    private final UniformFanInShape<T, T> shape;

    public static <T> boolean apply$default$2() {
        return Merge$.MODULE$.apply$default$2();
    }

    public static <T> Merge<T> apply(int inputPorts, boolean eagerComplete) {
        return Merge$.MODULE$.apply(inputPorts, eagerComplete);
    }

    public int inputPorts() {
        return this.inputPorts;
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
        return Stages$DefaultAttributes$.MODULE$.merge();
    }

    @Override
    public UniformFanInShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private final FixedSizeBuffer.FixedSizeBuffer<Inlet<T>> akka$stream$scaladsl$Merge$$anon$$pendingQueue;
            private int akka$stream$scaladsl$Merge$$anon$$runningUpstreams;
            private int ix;
            private final /* synthetic */ Merge $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public FixedSizeBuffer.FixedSizeBuffer<Inlet<T>> akka$stream$scaladsl$Merge$$anon$$pendingQueue() {
                return this.akka$stream$scaladsl$Merge$$anon$$pendingQueue;
            }

            public boolean akka$stream$scaladsl$Merge$$anon$$pending() {
                return this.akka$stream$scaladsl$Merge$$anon$$pendingQueue().nonEmpty();
            }

            public int akka$stream$scaladsl$Merge$$anon$$runningUpstreams() {
                return this.akka$stream$scaladsl$Merge$$anon$$runningUpstreams;
            }

            public void akka$stream$scaladsl$Merge$$anon$$runningUpstreams_$eq(int x$1) {
                this.akka$stream$scaladsl$Merge$$anon$$runningUpstreams = x$1;
            }

            public boolean akka$stream$scaladsl$Merge$$anon$$upstreamsClosed() {
                return this.akka$stream$scaladsl$Merge$$anon$$runningUpstreams() == 0;
            }

            public void preStart() {
                for (int ix = 0; ix < this.$outer.in().size(); ++ix) {
                    this.tryPull((Inlet)this.$outer.in().apply(ix));
                }
            }

            private void dequeueAndDispatch() {
                block5: {
                    BoxedUnit boxedUnit;
                    Inlet<T> in;
                    do {
                        if ((in = this.akka$stream$scaladsl$Merge$$anon$$pendingQueue().dequeue()) != null) continue;
                        if (this.akka$stream$scaladsl$Merge$$anon$$upstreamsClosed()) {
                            this.completeStage();
                            boxedUnit = BoxedUnit.UNIT;
                        } else {
                            boxedUnit = BoxedUnit.UNIT;
                        }
                        break block5;
                    } while (!this.isAvailable(in));
                    this.push(this.$outer.out(), this.grab(in));
                    if (this.akka$stream$scaladsl$Merge$$anon$$upstreamsClosed() && !this.akka$stream$scaladsl$Merge$$anon$$pending()) {
                        this.completeStage();
                        boxedUnit = BoxedUnit.UNIT;
                    } else {
                        this.tryPull(in);
                        boxedUnit = BoxedUnit.UNIT;
                    }
                }
            }

            private int ix() {
                return this.ix;
            }

            private void ix_$eq(int x$1) {
                this.ix = x$1;
            }

            public void onPull() {
                if (this.akka$stream$scaladsl$Merge$$anon$$pending()) {
                    this.dequeueAndDispatch();
                    return;
                }
            }

            public /* synthetic */ Merge akka$stream$scaladsl$Merge$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.akka$stream$scaladsl$Merge$$anon$$pendingQueue = FixedSizeBuffer$.MODULE$.apply($outer.inputPorts());
                this.akka$stream$scaladsl$Merge$$anon$$runningUpstreams = $outer.inputPorts();
                this.ix = 0;
                while (this.ix() < $outer.in().size()) {
                    Inlet i = (Inlet)$outer.in().apply(this.ix());
                    this.ix_$eq(this.ix() + 1);
                    this.setHandler(i, new InHandler(this, i){
                        private final /* synthetic */ $anon$1 $outer;
                        private final Inlet i$1;

                        public void onUpstreamFailure(Throwable ex) throws Exception {
                            InHandler.onUpstreamFailure$(this, ex);
                        }

                        public void onPush() {
                            if (this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$Merge$$anon$$$outer().out())) {
                                this.$outer.push(this.$outer.akka$stream$scaladsl$Merge$$anon$$$outer().out(), this.$outer.grab(this.i$1));
                                this.$outer.tryPull(this.i$1);
                                return;
                            }
                            this.$outer.akka$stream$scaladsl$Merge$$anon$$pendingQueue().enqueue(this.i$1);
                        }

                        public void onUpstreamFinish() {
                            if (this.$outer.akka$stream$scaladsl$Merge$$anon$$$outer().eagerComplete()) {
                                for (int ix2 = 0; ix2 < this.$outer.akka$stream$scaladsl$Merge$$anon$$$outer().in().size(); ++ix2) {
                                    this.$outer.cancel((Inlet)this.$outer.akka$stream$scaladsl$Merge$$anon$$$outer().in().apply(ix2));
                                }
                                this.$outer.akka$stream$scaladsl$Merge$$anon$$runningUpstreams_$eq(0);
                                if (!this.$outer.akka$stream$scaladsl$Merge$$anon$$pending()) {
                                    this.$outer.completeStage();
                                    return;
                                }
                                return;
                            }
                            this.$outer.akka$stream$scaladsl$Merge$$anon$$runningUpstreams_$eq(this.$outer.akka$stream$scaladsl$Merge$$anon$$runningUpstreams() - 1);
                            if (this.$outer.akka$stream$scaladsl$Merge$$anon$$upstreamsClosed() && !this.$outer.akka$stream$scaladsl$Merge$$anon$$pending()) {
                                this.$outer.completeStage();
                                return;
                            }
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.i$1 = i$1;
                            InHandler.$init$(this);
                        }
                    });
                }
                this.setHandler($outer.out(), this);
            }
        };
    }

    public String toString() {
        return "Merge";
    }

    public Merge(int inputPorts, boolean eagerComplete) {
        this.inputPorts = inputPorts;
        this.eagerComplete = eagerComplete;
        Predef$.MODULE$.require(inputPorts >= 1, (Function0<Object>)(Function0<String> & Serializable)() -> "A Merge must have one or more input ports");
        this.in = (IndexedSeq)package$.MODULE$.Vector().tabulate(inputPorts, (Function1<Object, Inlet> & Serializable)i -> Inlet$.MODULE$.apply(new StringBuilder(8).append("Merge.in").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.out = Outlet$.MODULE$.apply("Merge.out");
        this.shape = UniformFanInShape$.MODULE$.apply(this.out(), this.in());
    }
}

