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
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.Concat$;
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
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dt!\u0002\f\u0018\u0011\u0003qb!\u0002\u0011\u0018\u0011\u0003\t\u0003\"\u0002\u0015\u0002\t\u0003I\u0003b\u0002\u0016\u0002\u0005\u0004%Ia\u000b\u0005\b\u0003\u000f\t\u0001\u0015!\u0003-\u0011\u001d\tI!\u0001C\u0005\u0003\u0017Aq!a\u0006\u0002\t\u0003\tI\u0002C\u0005\u00024\u0005\t\n\u0011\"\u0001\u00026!9\u0011qC\u0001\u0005\u0002\u0005=c\u0001\u0002\u0011\u0018\u00055B\u0001\u0002R\u0005\u0003\u0006\u0004%\t!\u0012\u0005\t\u0013&\u0011\t\u0011)A\u0005\r\")\u0001&\u0003C\u0001\u0015\"9Q*\u0003b\u0001\n\u0003q\u0005B\u0002.\nA\u0003%q\nC\u0004\\\u0013\t\u0007I\u0011\u0001/\t\r\u0001L\u0001\u0015!\u0003^\u0011\u0015\t\u0017\u0002\"\u0011c\u0011\u001d1\u0017B1A\u0005B\u001dDa\u0001[\u0005!\u0002\u0013)\u0004\"B5\n\t\u0003R\u0007\"\u0002<\n\t\u0003:\u0018AB\"p]\u000e\fGO\u0003\u0002\u00193\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u001b7\u000511\u000f\u001e:fC6T\u0011\u0001H\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005}\tQ\"A\f\u0003\r\r{gnY1u'\t\t!\u0005\u0005\u0002$M5\tAEC\u0001&\u0003\u0015\u00198-\u00197b\u0013\t9CE\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003y\t!bX2p]\u000e\fG\u000fV<p+\u0005a\u0003cA\u0010\n\u0003V\u0011afO\n\u0003\u0013=\u00022\u0001M\u001a6\u001b\u0005\t$B\u0001\u001a\u001a\u0003\u0015\u0019H/Y4f\u0013\t!\u0014G\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004BAN\u001c:s5\t\u0011$\u0003\u000293\t\tRK\\5g_Jlg)\u00198J]NC\u0017\r]3\u0011\u0005iZD\u0002\u0001\u0003\u0006y%\u0011\r!\u0010\u0002\u0002)F\u0011a(\u0011\t\u0003G}J!\u0001\u0011\u0013\u0003\u000f9{G\u000f[5oOB\u00111EQ\u0005\u0003\u0007\u0012\u00121!\u00118z\u0003)Ig\u000e];u!>\u0014Ho]\u000b\u0002\rB\u00111eR\u0005\u0003\u0011\u0012\u00121!\u00138u\u0003-Ig\u000e];u!>\u0014Ho\u001d\u0011\u0015\u0005-c\u0005cA\u0010\ns!)A\t\u0004a\u0001\r\u0006\u0011\u0011N\\\u000b\u0002\u001fB\u0019\u0001+V,\u000e\u0003ES!AU*\u0002\u0013%lW.\u001e;bE2,'B\u0001+%\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003-F\u0013!\"\u00138eKb,GmU3r!\r1\u0004,O\u0005\u00033f\u0011Q!\u00138mKR\f1!\u001b8!\u0003\ryW\u000f^\u000b\u0002;B\u0019aGX\u001d\n\u0005}K\"AB(vi2,G/\u0001\u0003pkR\u0004\u0013!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\t1\r\u0005\u00027I&\u0011Q-\u0007\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#A\u001b\u0002\rMD\u0017\r]3!\u0003-\u0019'/Z1uK2{w-[2\u0015\u0005-$(c\u00017oc\u001a!Q\u000e\u0006\u0001l\u00051a$/\u001a4j]\u0016lWM\u001c;?!\t\u0001t.\u0003\u0002qc\tyqI]1qQN#\u0018mZ3M_\u001eL7\r\u0005\u00021e&\u00111/\r\u0002\u000b\u001fV$\b*\u00198eY\u0016\u0014\b\"B;\u0015\u0001\u0004\u0019\u0017aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003a\u00042!_A\u0001\u001d\tQh\u0010\u0005\u0002|I5\tAP\u0003\u0002~;\u00051AH]8pizJ!a \u0013\u0002\rA\u0013X\rZ3g\u0013\u0011\t\u0019!!\u0002\u0003\rM#(/\u001b8h\u0015\tyH%A\u0006`G>t7-\u0019;Uo>\u0004\u0013!C2p]\u000e\fG\u000fV<p+\u0011\ti!!\u0006\u0016\u0005\u0005=\u0001\u0003\u0002\u00194\u0003#\u0001bAN\u001c\u0002\u0014\u0005M\u0001c\u0001\u001e\u0002\u0016\u0011)A(\u0002b\u0001{\u0005)\u0011\r\u001d9msV!\u00111DA\u0014)\u0011\ti\"!\r\u0011\u000fY\ny\"a\t\u0002*%\u0019\u0011\u0011E\r\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0011\rY:\u0014QEA\u0013!\rQ\u0014q\u0005\u0003\u0006y\u0019\u0011\r!\u0010\t\u0005\u0003W\ti#D\u0001\u001c\u0013\r\tyc\u0007\u0002\b\u001d>$Xk]3e\u0011\u001d!e\u0001%AA\u0002\u0019\u000bq\"\u00199qYf$C-\u001a4bk2$H%M\u000b\u0005\u0003o\ti%\u0006\u0002\u0002:)\u001aa)a\u000f,\u0005\u0005u\u0002\u0003BA \u0003\u0013j!!!\u0011\u000b\t\u0005\r\u0013QI\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\u0012%\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u0017\n\tEA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$Q\u0001P\u0004C\u0002u*B!!\u0015\u0002ZQ1\u00111KA.\u0003;\u0002rANA\u0010\u0003+\nI\u0003\u0005\u00047o\u0005]\u0013q\u000b\t\u0004u\u0005eC!\u0002\u001f\t\u0005\u0004i\u0004\"\u0002#\t\u0001\u00041\u0005bBA0\u0011\u0001\u0007\u0011\u0011M\u0001\u000fI\u0016$\u0018m\u00195fI&s\u0007/\u001e;t!\r\u0019\u00131M\u0005\u0004\u0003K\"#a\u0002\"p_2,\u0017M\u001c")
public final class Concat<T>
extends GraphStage<UniformFanInShape<T, T>> {
    private final int inputPorts;
    private final IndexedSeq<Inlet<T>> in;
    private final Outlet<T> out;
    private final UniformFanInShape<T, T> shape;

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> apply(int inputPorts, boolean detachedInputs) {
        return Concat$.MODULE$.apply(inputPorts, detachedInputs);
    }

    public static <T> int apply$default$1() {
        return Concat$.MODULE$.apply$default$1();
    }

    public static <T> Graph<UniformFanInShape<T, T>, NotUsed> apply(int inputPorts) {
        return Concat$.MODULE$.apply(inputPorts);
    }

    public int inputPorts() {
        return this.inputPorts;
    }

    public IndexedSeq<Inlet<T>> in() {
        return this.in;
    }

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.concat();
    }

    @Override
    public UniformFanInShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private int akka$stream$scaladsl$Concat$$anon$$activeStream;
            private final /* synthetic */ Concat $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public int akka$stream$scaladsl$Concat$$anon$$activeStream() {
                return this.akka$stream$scaladsl$Concat$$anon$$activeStream;
            }

            public void akka$stream$scaladsl$Concat$$anon$$activeStream_$eq(int x$1) {
                this.akka$stream$scaladsl$Concat$$anon$$activeStream = x$1;
            }

            public void onPull() {
                this.pull((Inlet)this.$outer.in().apply(this.akka$stream$scaladsl$Concat$$anon$$activeStream()));
            }

            public /* synthetic */ Concat akka$stream$scaladsl$Concat$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.akka$stream$scaladsl$Concat$$anon$$activeStream = 0;
                int idxx = 0;
                int size = $outer.in().size();
                while (idxx < size) {
                    Inlet i = (Inlet)$outer.in().apply(idxx);
                    int idx = idxx++;
                    this.setHandler(i, new InHandler(this, i, idx){
                        private final /* synthetic */ $anon$22 $outer;
                        private final Inlet i$5;
                        private final int idx$2;

                        public void onUpstreamFailure(Throwable ex) throws Exception {
                            InHandler.onUpstreamFailure$(this, ex);
                        }

                        public void onPush() {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$Concat$$anon$$$outer().out(), this.$outer.grab(this.i$5));
                        }

                        public void onUpstreamFinish() {
                            if (this.idx$2 == this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream()) {
                                this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream_$eq(this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream() + 1);
                                while (this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream() < this.$outer.akka$stream$scaladsl$Concat$$anon$$$outer().inputPorts() && this.$outer.isClosed((Inlet)this.$outer.akka$stream$scaladsl$Concat$$anon$$$outer().in().apply(this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream()))) {
                                    this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream_$eq(this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream() + 1);
                                }
                                if (this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream() == this.$outer.akka$stream$scaladsl$Concat$$anon$$$outer().inputPorts()) {
                                    this.$outer.completeStage();
                                    return;
                                }
                                if (this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$Concat$$anon$$$outer().out())) {
                                    this.$outer.pull((Inlet)this.$outer.akka$stream$scaladsl$Concat$$anon$$$outer().in().apply(this.$outer.akka$stream$scaladsl$Concat$$anon$$activeStream()));
                                    return;
                                }
                                return;
                            }
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.i$5 = i$5;
                            this.idx$2 = idx$2;
                            InHandler.$init$(this);
                        }
                    });
                }
                this.setHandler($outer.out(), this);
            }
        };
    }

    public String toString() {
        return new StringBuilder(8).append("Concat(").append(this.inputPorts()).append(")").toString();
    }

    public Concat(int inputPorts) {
        this.inputPorts = inputPorts;
        Predef$.MODULE$.require(inputPorts > 1, (Function0<Object>)(Function0<String> & Serializable)() -> "A Concat must have more than 1 input ports");
        this.in = (IndexedSeq)package$.MODULE$.Vector().tabulate(inputPorts, (Function1<Object, Inlet> & Serializable)i -> Inlet$.MODULE$.apply(new StringBuilder(9).append("Concat.in").append(BoxesRunTime.unboxToInt(i)).toString()));
        this.out = Outlet$.MODULE$.apply("Concat.out");
        this.shape = UniformFanInShape$.MODULE$.apply(this.out(), this.in());
    }
}

