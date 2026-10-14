/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.UniformFanOutShape;
import akka.stream.UniformFanOutShape$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.Broadcast$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.IndexedSeq;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015r!\u0002\u000b\u0016\u0011\u0003ab!\u0002\u0010\u0016\u0011\u0003y\u0002\"\u0002\u0014\u0002\t\u00039\u0003\"\u0002\u0015\u0002\t\u0003I\u0003\"CA\u0005\u0003E\u0005I\u0011AA\u0006\r\u0011qRC\u0001\u0017\t\u0011\r+!Q1A\u0005\u0002\u0011C\u0001\u0002S\u0003\u0003\u0002\u0003\u0006I!\u0012\u0005\t\u0013\u0016\u0011)\u0019!C\u0001\u0015\"Aa*\u0002B\u0001B\u0003%1\nC\u0003'\u000b\u0011\u0005q\nC\u0004T\u000b\t\u0007I\u0011\u0001+\t\ra+\u0001\u0015!\u0003V\u0011\u001dIVA1A\u0005\u0002iCaAZ\u0003!\u0002\u0013Y\u0006\"B4\u0006\t\u0003B\u0007b\u00027\u0006\u0005\u0004%\t%\u001c\u0005\u0007]\u0016\u0001\u000b\u0011\u0002\u001b\t\u000b=,A\u0011\t9\t\u000bY,A\u0011I<\u0002\u0013\t\u0013x.\u00193dCN$(B\u0001\f\u0018\u0003!\u00198-\u00197bINd'B\u0001\r\u001a\u0003\u0019\u0019HO]3b[*\t!$\u0001\u0003bW.\f7\u0001\u0001\t\u0003;\u0005i\u0011!\u0006\u0002\n\u0005J|\u0017\rZ2bgR\u001c\"!\u0001\u0011\u0011\u0005\u0005\"S\"\u0001\u0012\u000b\u0003\r\nQa]2bY\u0006L!!\n\u0012\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tA$A\u0003baBd\u00170F\u0002+\u0003\u0007!RaKA\u0003\u0003\u000f\u0001B!H\u0003\u0002\u0002U\u0011QFO\n\u0003\u000b9\u00022a\f\u001a5\u001b\u0005\u0001$BA\u0019\u0018\u0003\u0015\u0019H/Y4f\u0013\t\u0019\u0004G\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004B!\u000e\u001c9q5\tq#\u0003\u00028/\t\u0011RK\\5g_Jlg)\u00198PkR\u001c\u0006.\u00199f!\tI$\b\u0004\u0001\u0005\u000bm*!\u0019\u0001\u001f\u0003\u0003Q\u000b\"!\u0010!\u0011\u0005\u0005r\u0014BA #\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!I!\n\u0005\t\u0013#aA!os\u0006Yq.\u001e;qkR\u0004vN\u001d;t+\u0005)\u0005CA\u0011G\u0013\t9%EA\u0002J]R\fAb\\;uaV$\bk\u001c:ug\u0002\n1\"Z1hKJ\u001c\u0015M\\2fYV\t1\n\u0005\u0002\"\u0019&\u0011QJ\t\u0002\b\u0005>|G.Z1o\u00031)\u0017mZ3s\u0007\u0006t7-\u001a7!)\r\u0001\u0016K\u0015\t\u0004;\u0015A\u0004\"B\"\u000b\u0001\u0004)\u0005\"B%\u000b\u0001\u0004Y\u0015AA5o+\u0005)\u0006cA\u001bWq%\u0011qk\u0006\u0002\u0006\u0013:dW\r^\u0001\u0004S:\u0004\u0013aA8viV\t1\fE\u0002]C\u000el\u0011!\u0018\u0006\u0003=~\u000b\u0011\"[7nkR\f'\r\\3\u000b\u0005\u0001\u0014\u0013AC2pY2,7\r^5p]&\u0011!-\u0018\u0002\u000b\u0013:$W\r_3e'\u0016\f\bcA\u001beq%\u0011Qm\u0006\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A5\u0011\u0005UR\u0017BA6\u0018\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002i\u000511\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR\u0011\u0011\u000f\u001e\t\u0003_IL!a\u001d\u0019\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDQ!\u001e\nA\u0002%\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002qB\u0011\u0011P`\u0007\u0002u*\u00111\u0010`\u0001\u0005Y\u0006twMC\u0001~\u0003\u0011Q\u0017M^1\n\u0005}T(AB*ue&tw\rE\u0002:\u0003\u0007!QaO\u0002C\u0002qBQaQ\u0002A\u0002\u0015Cq!S\u0002\u0011\u0002\u0003\u00071*A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133+\u0011\ti!a\t\u0016\u0005\u0005=!fA&\u0002\u0012-\u0012\u00111\u0003\t\u0005\u0003+\ty\"\u0004\u0002\u0002\u0018)!\u0011\u0011DA\u000e\u0003%)hn\u00195fG.,GMC\u0002\u0002\u001e\t\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\t#a\u0006\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003<\t\t\u0007A\b")
public final class Broadcast<T>
extends GraphStage<UniformFanOutShape<T, T>> {
    private final int outputPorts;
    private final boolean eagerCancel;
    private final Inlet<T> in;
    private final IndexedSeq<Outlet<T>> out;
    private final UniformFanOutShape<T, T> shape;

    public static <T> boolean apply$default$2() {
        return Broadcast$.MODULE$.apply$default$2();
    }

    public static <T> Broadcast<T> apply(int outputPorts, boolean eagerCancel) {
        return Broadcast$.MODULE$.apply(outputPorts, eagerCancel);
    }

    public int outputPorts() {
        return this.outputPorts;
    }

    public boolean eagerCancel() {
        return this.eagerCancel;
    }

    public Inlet<T> in() {
        return this.in;
    }

    public IndexedSeq<Outlet<T>> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.broadcast();
    }

    @Override
    public UniformFanOutShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private int akka$stream$scaladsl$Broadcast$$anon$$pendingCount;
            private final boolean[] akka$stream$scaladsl$Broadcast$$anon$$pending;
            private int akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning;
            private final /* synthetic */ Broadcast $outer;

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public int akka$stream$scaladsl$Broadcast$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$Broadcast$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$Broadcast$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$Broadcast$$anon$$pendingCount = x$1;
            }

            public boolean[] akka$stream$scaladsl$Broadcast$$anon$$pending() {
                return this.akka$stream$scaladsl$Broadcast$$anon$$pending;
            }

            public int akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning() {
                return this.akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning;
            }

            public void akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning = x$1;
            }

            public void onPush() {
                this.akka$stream$scaladsl$Broadcast$$anon$$pendingCount_$eq(this.akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning());
                T elem = this.grab(this.$outer.in());
                int size = this.$outer.out().size();
                for (int idx = 0; idx < size; ++idx) {
                    Outlet o = (Outlet)this.$outer.out().apply(idx);
                    if (this.isClosed(o)) continue;
                    this.push(o, elem);
                    this.akka$stream$scaladsl$Broadcast$$anon$$pending()[idx] = true;
                }
            }

            public void akka$stream$scaladsl$Broadcast$$anon$$tryPull() {
                if (this.akka$stream$scaladsl$Broadcast$$anon$$pendingCount() == 0 && !this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public /* synthetic */ Broadcast akka$stream$scaladsl$Broadcast$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                this.akka$stream$scaladsl$Broadcast$$anon$$pendingCount = $outer.outputPorts();
                this.akka$stream$scaladsl$Broadcast$$anon$$pending = (boolean[])Array$.MODULE$.fill($outer.outputPorts(), () -> true, ClassTag$.MODULE$.Boolean());
                this.akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning = $outer.outputPorts();
                this.setHandler($outer.in(), this);
                int size = $outer.out().size();
                int idx = 0;
                while (idx < size) {
                    Outlet o = (Outlet)$outer.out().apply(idx);
                    int i = idx++;
                    this.setHandler(o, new OutHandler(this, i){
                        private final /* synthetic */ $anon$11 $outer;
                        private final int i$3;

                        public void onDownstreamFinish() throws Exception {
                            OutHandler.onDownstreamFinish$(this);
                        }

                        public void onPull() {
                            this.$outer.akka$stream$scaladsl$Broadcast$$anon$$pending()[this.i$3] = false;
                            this.$outer.akka$stream$scaladsl$Broadcast$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$Broadcast$$anon$$pendingCount() - 1);
                            this.$outer.akka$stream$scaladsl$Broadcast$$anon$$tryPull();
                        }

                        public void onDownstreamFinish(Throwable cause) {
                            if (this.$outer.akka$stream$scaladsl$Broadcast$$anon$$$outer().eagerCancel()) {
                                this.$outer.cancelStage(cause);
                                return;
                            }
                            this.$outer.akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning_$eq(this.$outer.akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning() - 1);
                            if (this.$outer.akka$stream$scaladsl$Broadcast$$anon$$downstreamsRunning() == 0) {
                                this.$outer.cancelStage(cause);
                                return;
                            }
                            if (this.$outer.akka$stream$scaladsl$Broadcast$$anon$$pending()[this.i$3]) {
                                this.$outer.akka$stream$scaladsl$Broadcast$$anon$$pending()[this.i$3] = false;
                                this.$outer.akka$stream$scaladsl$Broadcast$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$Broadcast$$anon$$pendingCount() - 1);
                                this.$outer.akka$stream$scaladsl$Broadcast$$anon$$tryPull();
                                return;
                            }
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.i$3 = i$3;
                            OutHandler.$init$(this);
                        }
                    });
                }
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$pending$1()}, serializedLambda);
            }
        };
    }

    public String toString() {
        return "Broadcast";
    }

    public Broadcast(int outputPorts, boolean eagerCancel) {
        this.outputPorts = outputPorts;
        this.eagerCancel = eagerCancel;
        Predef$.MODULE$.require(outputPorts >= 1, (Function0<Object>)(Function0<String> & Serializable)() -> "A Broadcast must have one or more output ports");
        this.in = Inlet$.MODULE$.apply("Broadcast.in");
        this.out = (IndexedSeq)package$.MODULE$.Vector().tabulate(outputPorts, (Function1<Object, Outlet> & Serializable)i -> Outlet$.MODULE$.apply(new StringBuilder(13).append("Broadcast.out").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.shape = UniformFanOutShape$.MODULE$.apply(this.in(), this.out());
    }
}

