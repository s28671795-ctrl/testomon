/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.UniformFanInShape;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.MergePreferred$;
import akka.stream.scaladsl.MergePreferred$$anon$3$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;

@ScalaSignature(bytes="\u0006\u0005\u0005]t!\u0002\u000f\u001e\u0011\u0003!c!\u0002\u0014\u001e\u0011\u00039\u0003\"\u0002\u0018\u0002\t\u0003yc\u0001\u0002\u0019\u0002\u0005EB\u0001BQ\u0002\u0003\u0006\u0004%\ta\u0011\u0005\n\u000f\u000e\u0011\t\u0011)A\u0005\t\"C\u0001BS\u0002\u0003\u0002\u0003\u0006Ia\u0013\u0005\u0006]\r!\tA\u0015\u0005\u0006]\r!\ta\u0016\u0005\u0006M\u000e!\tf\u001a\u0005\u0006[\u000e!\tE\u001c\u0005\b_\u000e\u0011\r\u0011\"\u0001q\u0011\u0019!8\u0001)A\u0005c\")Q/\u0001C\u0001m\"I\u00111L\u0001\u0012\u0002\u0013\u0005\u0011Q\f\u0004\u0005Mu\u0011\u0011\u0010\u0003\u0005C\u001f\t\u0015\r\u0011\"\u0001D\u0011!9uB!A!\u0002\u0013!\u0005BCA\u0006\u001f\t\u0015\r\u0011\"\u0001\u0002\u000e!Q\u0011QC\b\u0003\u0002\u0003\u0006I!a\u0004\t\r9zA\u0011AA\f\u0011\u001d\tyb\u0004C!\u0003CA\u0011\"!\u000b\u0010\u0005\u0004%\t%a\u000b\t\u0011\u00055r\u0002)A\u0005\u0003\u0007Aq!a\f\u0010\t\u0003\t\t\u0004C\u0004\u0002:=!\t!a\u000f\t\r=|A\u0011AA\"\u0011\u001d\t)e\u0004C!\u0003\u000f\na\"T3sO\u0016\u0004&/\u001a4feJ,GM\u0003\u0002\u001f?\u0005A1oY1mC\u0012\u001cHN\u0003\u0002!C\u000511\u000f\u001e:fC6T\u0011AI\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005\u0015\nQ\"A\u000f\u0003\u001d5+'oZ3Qe\u00164WM\u001d:fIN\u0011\u0011\u0001\u000b\t\u0003S1j\u0011A\u000b\u0006\u0002W\u0005)1oY1mC&\u0011QF\u000b\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005!#aE'fe\u001e,\u0007K]3gKJ\u0014X\rZ*iCB,WC\u0001\u001a:'\t\u00191\u0007\u0005\u00035k]:T\"A\u0010\n\u0005Yz\"!E+oS\u001a|'/\u001c$b]&s7\u000b[1qKB\u0011\u0001(\u000f\u0007\u0001\t\u0015Q4A1\u0001<\u0005\u0005!\u0016C\u0001\u001f@!\tIS(\u0003\u0002?U\t9aj\u001c;iS:<\u0007CA\u0015A\u0013\t\t%FA\u0002B]f\fab]3d_:$\u0017M]=Q_J$8/F\u0001E!\tIS)\u0003\u0002GU\t\u0019\u0011J\u001c;\u0002\u001fM,7m\u001c8eCJL\bk\u001c:ug\u0002J!!S\u001b\u0002\u00039\fQaX5oSR\u00042\u0001T(8\u001d\t!T*\u0003\u0002O?\u0005Qa)\u00198J]NC\u0017\r]3\n\u0005A\u000b&\u0001B%oSRT!AT\u0010\u0015\u0007M+f\u000bE\u0002U\u0007]j\u0011!\u0001\u0005\u0006\u0005\u001e\u0001\r\u0001\u0012\u0005\u0006\u0015\u001e\u0001\ra\u0013\u000b\u0004'bK\u0006\"\u0002\"\t\u0001\u0004!\u0005\"\u0002.\t\u0001\u0004Y\u0016\u0001\u00028b[\u0016\u0004\"\u0001X2\u000f\u0005u\u000b\u0007C\u00010+\u001b\u0005y&B\u00011$\u0003\u0019a$o\\8u}%\u0011!MK\u0001\u0007!J,G-\u001a4\n\u0005\u0011,'AB*ue&twM\u0003\u0002cU\u0005I1m\u001c8tiJ,8\r\u001e\u000b\u0003Q.\u00042\u0001N58\u0013\tQwD\u0001\u0006GC:Len\u00155ba\u0016DQ\u0001\\\u0005A\u0002-\u000bA!\u001b8ji\u0006AA-Z3q\u0007>\u0004\u0018\u0010F\u0001T\u0003%\u0001(/\u001a4feJ,G-F\u0001r!\r!$oN\u0005\u0003g~\u0011Q!\u00138mKR\f!\u0002\u001d:fM\u0016\u0014(/\u001a3!\u0003\u0015\t\u0007\u000f\u001d7z+\r9\u0018Q\u000b\u000b\u0006q\u0006]\u0013\u0011\f\t\u0005K=\t\u0019&F\u0002{\u0003\u0013\u0019\"aD>\u0011\tq|\u00181A\u0007\u0002{*\u0011apH\u0001\u0006gR\fw-Z\u0005\u0004\u0003\u0003i(AC$sCBD7\u000b^1hKB)\u0011QA\u0002\u0002\b9\u0011Q\u0005\u0001\t\u0004q\u0005%A!\u0002\u001e\u0010\u0005\u0004Y\u0014!D3bO\u0016\u00148i\\7qY\u0016$X-\u0006\u0002\u0002\u0010A\u0019\u0011&!\u0005\n\u0007\u0005M!FA\u0004C_>dW-\u00198\u0002\u001d\u0015\fw-\u001a:D_6\u0004H.\u001a;fAQ1\u0011\u0011DA\u000e\u0003;\u0001B!J\b\u0002\b!)!\t\u0006a\u0001\t\"9\u00111\u0002\u000bA\u0002\u0005=\u0011!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\u0011\u00111\u0005\t\u0004i\u0005\u0015\u0012bAA\u0014?\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0005\u0005\r\u0011AB:iCB,\u0007%\u0001\u0002j]R!\u00111GA\u001b!\u0011!$/a\u0002\t\r\u0005]\u0002\u00041\u0001E\u0003\tIG-A\u0002pkR,\"!!\u0010\u0011\u000bQ\ny$a\u0002\n\u0007\u0005\u0005sD\u0001\u0004PkRdW\r^\u000b\u0003\u0003g\t1b\u0019:fCR,Gj\\4jGR!\u0011\u0011JA(!\ra\u00181J\u0005\u0004\u0003\u001bj(aD$sCBD7\u000b^1hK2{w-[2\t\u000f\u0005E3\u00041\u0001\u0002$\u0005\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fgB\u0019\u0001(!\u0016\u0005\u000bij!\u0019A\u001e\t\u000b\tk\u0001\u0019\u0001#\t\u0013\u0005-Q\u0002%AA\u0002\u0005=\u0011aD1qa2LH\u0005Z3gCVdG\u000f\n\u001a\u0016\t\u0005}\u0013QO\u000b\u0003\u0003CRC!a\u0004\u0002d-\u0012\u0011Q\r\t\u0005\u0003O\n\t(\u0004\u0002\u0002j)!\u00111NA7\u0003%)hn\u00195fG.,GMC\u0002\u0002p)\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019(!\u001b\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rB\u0003;\u001d\t\u00071\b")
public final class MergePreferred<T>
extends GraphStage<MergePreferredShape<T>> {
    private final int secondaryPorts;
    private final boolean eagerComplete;
    private final MergePreferredShape<T> shape;

    public static <T> boolean apply$default$2() {
        return MergePreferred$.MODULE$.apply$default$2();
    }

    public static <T> MergePreferred<T> apply(int secondaryPorts, boolean eagerComplete) {
        return MergePreferred$.MODULE$.apply(secondaryPorts, eagerComplete);
    }

    public int secondaryPorts() {
        return this.secondaryPorts;
    }

    public boolean eagerComplete() {
        return this.eagerComplete;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.mergePreferred();
    }

    @Override
    public MergePreferredShape<T> shape() {
        return this.shape;
    }

    public Inlet<T> in(int id) {
        return ((UniformFanInShape)this.shape()).in(id);
    }

    public Outlet<T> out() {
        return ((FanInShape)this.shape()).out();
    }

    public Inlet<T> preferred() {
        return ((MergePreferredShape)this.shape()).preferred();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int openInputs;
            private final Function0<BoxedUnit>[] akka$stream$scaladsl$MergePreferred$$anon$$pullMe;
            private final int akka$stream$scaladsl$MergePreferred$$anon$$maxEmitting;
            private int akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting;
            private int i;
            private final /* synthetic */ MergePreferred $outer;

            private int openInputs() {
                return this.openInputs;
            }

            private void openInputs_$eq(int x$1) {
                this.openInputs = x$1;
            }

            public void akka$stream$scaladsl$MergePreferred$$anon$$onComplete() {
                this.openInputs_$eq(this.openInputs() - 1);
                if (this.$outer.eagerComplete() || this.openInputs() == 0) {
                    this.completeStage();
                    return;
                }
            }

            public void preStart() {
                ((UniformFanInShape)this.$outer.shape()).inlets().foreach((Function1<Inlet, Object> & Serializable)in -> {
                    $anon$3.$anonfun$preStart$1(this, in);
                    return BoxedUnit.UNIT;
                });
            }

            public Function0<BoxedUnit>[] akka$stream$scaladsl$MergePreferred$$anon$$pullMe() {
                return this.akka$stream$scaladsl$MergePreferred$$anon$$pullMe;
            }

            public int akka$stream$scaladsl$MergePreferred$$anon$$maxEmitting() {
                return this.akka$stream$scaladsl$MergePreferred$$anon$$maxEmitting;
            }

            public int akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting() {
                return this.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting;
            }

            public void akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting_$eq(int x$1) {
                this.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting = x$1;
            }

            private int i() {
                return this.i;
            }

            private void i_$eq(int x$1) {
                this.i = x$1;
            }

            public /* synthetic */ MergePreferred akka$stream$scaladsl$MergePreferred$$anon$$$outer() {
                return this.$outer;
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$3 $this, Inlet in) {
                $this.tryPull(in);
            }

            public static final /* synthetic */ Function0 $anonfun$pullMe$1($anon$3 $this, int i) {
                Inlet<T> port = $this.$outer.in(i);
                return () -> $this.tryPull(port);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.openInputs = $outer.secondaryPorts() + 1;
                this.setHandler($outer.out(), this.eagerTerminateOutput());
                this.akka$stream$scaladsl$MergePreferred$$anon$$pullMe = (Function0[])Array$.MODULE$.tabulate($outer.secondaryPorts(), (Function1<Object, Function0> & Serializable)i -> $anon$3.$anonfun$pullMe$1(this, BoxesRunTime.unboxToInt(i)), ClassTag$.MODULE$.apply(Function0.class));
                this.akka$stream$scaladsl$MergePreferred$$anon$$maxEmitting = 2;
                this.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting = 0;
                this.setHandler($outer.preferred(), new InHandler(this){
                    private final Function0<BoxedUnit> emitted;
                    private final /* synthetic */ $anon$3 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onUpstreamFinish() {
                        this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$onComplete();
                    }

                    public void onPush() {
                        if (this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting() == this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$maxEmitting()) {
                            return;
                        }
                        this.emitPreferred();
                    }

                    public void emitPreferred() {
                        this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting_$eq(this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting() + 1);
                        this.$outer.emit(this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$$outer().out(), this.$outer.grab(this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$$outer().preferred()), this.emitted());
                        this.$outer.tryPull(this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$$outer().preferred());
                    }

                    public Function0<BoxedUnit> emitted() {
                        return this.emitted;
                    }

                    public void emitSecondary() {
                        for (int i = 0; i < this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$$outer().secondaryPorts(); ++i) {
                            Inlet<T> port = this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$$outer().in(i);
                            if (!this.$outer.isAvailable(port)) continue;
                            this.$outer.emit(this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$$outer().out(), this.$outer.grab(port), this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$pullMe()[i]);
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                        this.emitted = () -> {
                            $this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting_$eq($this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting() - 1);
                            if ($this.$outer.isAvailable($this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$$outer().preferred())) {
                                this.emitPreferred();
                                return;
                            }
                            if ($this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting() == 0) {
                                this.emitSecondary();
                                return;
                            }
                        };
                    }

                    private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                        return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$emitted$1(akka.stream.scaladsl.MergePreferred$$anon$3$$anon$4 )}, serializedLambda);
                    }
                });
                this.i = 0;
                while (this.i() < $outer.secondaryPorts()) {
                    Inlet<T> port = $outer.in(this.i());
                    Function0<BoxedUnit> pullPort = this.akka$stream$scaladsl$MergePreferred$$anon$$pullMe()[this.i()];
                    this.setHandler(port, new InHandler(this, port, pullPort){
                        private final /* synthetic */ $anon$3 $outer;
                        private final Inlet port$2;
                        private final Function0 pullPort$1;

                        public void onUpstreamFailure(Throwable ex) throws Exception {
                            InHandler.onUpstreamFailure$(this, ex);
                        }

                        public void onPush() {
                            if (this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$preferredEmitting() > 0) {
                                return;
                            }
                            this.$outer.emit(this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$$outer().out(), this.$outer.grab(this.port$2), this.pullPort$1);
                        }

                        public void onUpstreamFinish() {
                            this.$outer.akka$stream$scaladsl$MergePreferred$$anon$$onComplete();
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.port$2 = port$2;
                            this.pullPort$1 = pullPort$1;
                            InHandler.$init$(this);
                        }
                    });
                    this.i_$eq(this.i() + 1);
                }
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$preStart$1$adapted(akka.stream.scaladsl.MergePreferred$$anon$3 akka.stream.Inlet ), $anonfun$pullMe$1$adapted(akka.stream.scaladsl.MergePreferred$$anon$3 java.lang.Object ), $anonfun$pullMe$2(akka.stream.scaladsl.MergePreferred$$anon$3 akka.stream.Inlet )}, serializedLambda);
            }
        };
    }

    public MergePreferred(int secondaryPorts, boolean eagerComplete) {
        this.secondaryPorts = secondaryPorts;
        this.eagerComplete = eagerComplete;
        Predef$.MODULE$.require(secondaryPorts >= 1, (Function0<Object>)(Function0<String> & Serializable)() -> "A MergePreferred must have 1 or more secondary input ports");
        this.shape = new MergePreferredShape(secondaryPorts, "MergePreferred");
    }

    public static final class MergePreferredShape<T>
    extends UniformFanInShape<T, T> {
        private final Inlet<T> preferred = this.newInlet("preferred");

        public int secondaryPorts() {
            return super.n();
        }

        @Override
        public FanInShape<T> construct(FanInShape.Init<T> init2) {
            return new MergePreferredShape<T>(this.secondaryPorts(), init2);
        }

        @Override
        public MergePreferredShape<T> deepCopy() {
            return (MergePreferredShape)super.deepCopy();
        }

        public Inlet<T> preferred() {
            return this.preferred;
        }

        public MergePreferredShape(int secondaryPorts, FanInShape.Init<T> _init) {
            super(secondaryPorts, _init);
        }

        public MergePreferredShape(int secondaryPorts, String name) {
            this(secondaryPorts, new FanInShape.Name(name));
        }
    }
}

