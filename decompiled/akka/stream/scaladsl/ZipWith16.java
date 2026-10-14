/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape16;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith16$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function16;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=g\u0001\u0002\u0016,\u0001IB\u0001\u0002 \u0001\u0003\u0006\u0004%\t! \u0005\n\u0003\u0007\u0001!\u0011!Q\u0001\nyDq!!\u0002\u0001\t\u0003\t9\u0001C\u0004\u0002\u0010\u0001!\t%!\u0005\t\u0013\u0005e\u0001A1A\u0005B\u0005m\u0001bBA\u000f\u0001\u0001\u0006IA\u000f\u0005\b\u0003?\u0001A\u0011AA\u0011\u0011%\tI\u0003\u0001b\u0001\n\u0003\tY\u0003\u0003\u0005\u00024\u0001\u0001\u000b\u0011BA\u0017\u0011%\t)\u0004\u0001b\u0001\n\u0003\t9\u0004\u0003\u0005\u0002<\u0001\u0001\u000b\u0011BA\u001d\u0011%\ti\u0004\u0001b\u0001\n\u0003\ty\u0004\u0003\u0005\u0002D\u0001\u0001\u000b\u0011BA!\u0011%\t)\u0005\u0001b\u0001\n\u0003\t9\u0005\u0003\u0005\u0002L\u0001\u0001\u000b\u0011BA%\u0011%\ti\u0005\u0001b\u0001\n\u0003\ty\u0005\u0003\u0005\u0002T\u0001\u0001\u000b\u0011BA)\u0011%\t)\u0006\u0001b\u0001\n\u0003\t9\u0006\u0003\u0005\u0002\\\u0001\u0001\u000b\u0011BA-\u0011%\ti\u0006\u0001b\u0001\n\u0003\ty\u0006\u0003\u0005\u0002d\u0001\u0001\u000b\u0011BA1\u0011%\t)\u0007\u0001b\u0001\n\u0003\t9\u0007\u0003\u0005\u0002l\u0001\u0001\u000b\u0011BA5\u0011%\ti\u0007\u0001b\u0001\n\u0003\ty\u0007\u0003\u0005\u0002t\u0001\u0001\u000b\u0011BA9\u0011%\t)\b\u0001b\u0001\n\u0003\t9\b\u0003\u0005\u0002|\u0001\u0001\u000b\u0011BA=\u0011%\ti\b\u0001b\u0001\n\u0003\ty\b\u0003\u0005\u0002\u0004\u0002\u0001\u000b\u0011BAA\u0011%\t)\t\u0001b\u0001\n\u0003\t9\t\u0003\u0005\u0002\f\u0002\u0001\u000b\u0011BAE\u0011%\ti\t\u0001b\u0001\n\u0003\ty\t\u0003\u0005\u0002\u0014\u0002\u0001\u000b\u0011BAI\u0011%\t)\n\u0001b\u0001\n\u0003\t9\n\u0003\u0005\u0002\u001c\u0002\u0001\u000b\u0011BAM\u0011%\ti\n\u0001b\u0001\n\u0003\ty\n\u0003\u0005\u0002$\u0002\u0001\u000b\u0011BAQ\u0011%\t)\u000b\u0001b\u0001\n\u0003\t9\u000b\u0003\u0005\u0002,\u0002\u0001\u000b\u0011BAU\u0011\u001d\ti\u000b\u0001C!\u0003_Cq!a/\u0001\t\u0003\niLA\u0005[SB<\u0016\u000e\u001e52m)\u0011A&L\u0001\tg\u000e\fG.\u00193tY*\u0011afL\u0001\u0007gR\u0014X-Y7\u000b\u0003A\nA!Y6lC\u000e\u0001QCE\u001aA\u001bB\u001bf+\u0017/`E\u0016D7N\\9uoj\u001c\"\u0001\u0001\u001b\u0011\u0007UB$(D\u00017\u0015\t9T&A\u0003ti\u0006<W-\u0003\u0002:m\tQqI]1qQN#\u0018mZ3\u0011'mbd\bT(S+b[f,\u00193hU6\u00048O^=\u000e\u00035J!!P\u0017\u0003\u0019\u0019\u000bg.\u00138TQ\u0006\u0004X-\r\u001c\u0011\u0005}\u0002E\u0002\u0001\u0003\u0006\u0003\u0002\u0011\rA\u0011\u0002\u0003\u0003F\n\"aQ%\u0011\u0005\u0011;U\"A#\u000b\u0003\u0019\u000bQa]2bY\u0006L!\u0001S#\u0003\u000f9{G\u000f[5oOB\u0011AIS\u0005\u0003\u0017\u0016\u00131!\u00118z!\tyT\nB\u0003O\u0001\t\u0007!I\u0001\u0002BeA\u0011q\b\u0015\u0003\u0006#\u0002\u0011\rA\u0011\u0002\u0003\u0003N\u0002\"aP*\u0005\u000bQ\u0003!\u0019\u0001\"\u0003\u0005\u0005#\u0004CA W\t\u00159\u0006A1\u0001C\u0005\t\tU\u0007\u0005\u0002@3\u0012)!\f\u0001b\u0001\u0005\n\u0011\u0011I\u000e\t\u0003\u007fq#Q!\u0018\u0001C\u0002\t\u0013!!Q\u001c\u0011\u0005}zF!\u00021\u0001\u0005\u0004\u0011%AA!9!\ty$\rB\u0003d\u0001\t\u0007!I\u0001\u0002BsA\u0011q(\u001a\u0003\u0006M\u0002\u0011\rA\u0011\u0002\u0004\u0003F\u0002\u0004CA i\t\u0015I\u0007A1\u0001C\u0005\r\t\u0015'\r\t\u0003\u007f-$Q\u0001\u001c\u0001C\u0002\t\u00131!Q\u00193!\tyd\u000eB\u0003p\u0001\t\u0007!IA\u0002BcM\u0002\"aP9\u0005\u000bI\u0004!\u0019\u0001\"\u0003\u0007\u0005\u000bD\u0007\u0005\u0002@i\u0012)Q\u000f\u0001b\u0001\u0005\n\u0019\u0011)M\u001b\u0011\u0005}:H!\u0002=\u0001\u0005\u0004\u0011%aA!2mA\u0011qH\u001f\u0003\u0006w\u0002\u0011\rA\u0011\u0002\u0002\u001f\u00061!0\u001b9qKJ,\u0012A \t\u0014\t~tDj\u0014*V1ns\u0016\rZ4k[B\u001ch/_\u0005\u0004\u0003\u0003)%A\u0003$v]\u000e$\u0018n\u001c82m\u00059!0\u001b9qKJ\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0003\u0002\n\u00055\u0001\u0003FA\u0006\u0001ybuJU+Y7z\u000bGm\u001a6naN4\u00180D\u0001,\u0011\u0015a8\u00011\u0001\u007f\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0003'\u00012aOA\u000b\u0013\r\t9\"\f\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#\u0001\u001e\u0002\rMD\u0017\r]3!\u0003\ryW\u000f^\u000b\u0003\u0003G\u0001BaOA\u0013s&\u0019\u0011qE\u0017\u0003\r=+H\u000f\\3u\u0003\rIg\u000eM\u000b\u0003\u0003[\u0001BaOA\u0018}%\u0019\u0011\u0011G\u0017\u0003\u000b%sG.\u001a;\u0002\t%t\u0007\u0007I\u0001\u0004S:\fTCAA\u001d!\u0011Y\u0014q\u0006'\u0002\t%t\u0017\u0007I\u0001\u0004S:\u0014TCAA!!\u0011Y\u0014qF(\u0002\t%t'\u0007I\u0001\u0004S:\u001cTCAA%!\u0011Y\u0014q\u0006*\u0002\t%t7\u0007I\u0001\u0004S:$TCAA)!\u0011Y\u0014qF+\u0002\t%tG\u0007I\u0001\u0004S:,TCAA-!\u0011Y\u0014q\u0006-\u0002\t%tW\u0007I\u0001\u0004S:4TCAA1!\u0011Y\u0014qF.\u0002\t%tg\u0007I\u0001\u0004S:<TCAA5!\u0011Y\u0014q\u00060\u0002\t%tw\u0007I\u0001\u0004S:DTCAA9!\u0011Y\u0014qF1\u0002\t%t\u0007\bI\u0001\u0004S:LTCAA=!\u0011Y\u0014q\u00063\u0002\t%t\u0017\bI\u0001\u0005S:\f\u0004'\u0006\u0002\u0002\u0002B!1(a\fh\u0003\u0015Ig.\r\u0019!\u0003\u0011Ig.M\u0019\u0016\u0005\u0005%\u0005\u0003B\u001e\u00020)\fQ!\u001b82c\u0001\nA!\u001b82eU\u0011\u0011\u0011\u0013\t\u0005w\u0005=R.A\u0003j]F\u0012\u0004%\u0001\u0003j]F\u001aTCAAM!\u0011Y\u0014q\u00069\u0002\u000b%t\u0017g\r\u0011\u0002\t%t\u0017\u0007N\u000b\u0003\u0003C\u0003BaOA\u0018g\u0006)\u0011N\\\u00195A\u0005!\u0011N\\\u00196+\t\tI\u000b\u0005\u0003<\u0003_1\u0018!B5ocU\u0002\u0013aC2sK\u0006$X\rT8hS\u000e$B!!-\u00028B\u0019Q'a-\n\u0007\u0005UfGA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u001d\tI\f\u000ba\u0001\u0003'\t1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u007f\u0003B!!1\u0002L6\u0011\u00111\u0019\u0006\u0005\u0003\u000b\f9-\u0001\u0003mC:<'BAAe\u0003\u0011Q\u0017M^1\n\t\u00055\u00171\u0019\u0002\u0007'R\u0014\u0018N\\4")
public class ZipWith16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O>
extends GraphStage<FanInShape16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O>> {
    private final Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> zipper;
    private final FanInShape16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;
    private final Inlet<A8> in7;
    private final Inlet<A9> in8;
    private final Inlet<A10> in9;
    private final Inlet<A11> in10;
    private final Inlet<A12> in11;
    private final Inlet<A13> in12;
    private final Inlet<A14> in13;
    private final Inlet<A15> in14;
    private final Inlet<A16> in15;

    public Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith16");
    }

    @Override
    public FanInShape16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> shape() {
        return this.shape;
    }

    public Outlet<O> out() {
        return ((FanInShape)this.shape()).out();
    }

    public Inlet<A1> in0() {
        return this.in0;
    }

    public Inlet<A2> in1() {
        return this.in1;
    }

    public Inlet<A3> in2() {
        return this.in2;
    }

    public Inlet<A4> in3() {
        return this.in3;
    }

    public Inlet<A5> in4() {
        return this.in4;
    }

    public Inlet<A6> in5() {
        return this.in5;
    }

    public Inlet<A7> in6() {
        return this.in6;
    }

    public Inlet<A8> in7() {
        return this.in7;
    }

    public Inlet<A9> in8() {
        return this.in8;
    }

    public Inlet<A10> in9() {
        return this.in9;
    }

    public Inlet<A11> in10() {
        return this.in10;
    }

    public Inlet<A12> in11() {
        return this.in11;
    }

    public Inlet<A13> in12() {
        return this.in12;
    }

    public Inlet<A14> in13() {
        return this.in13;
    }

    public Inlet<A15> in14() {
        return this.in14;
    }

    public Inlet<A16> in15() {
        return this.in15;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith16$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith16$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith16$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith16 $outer;

            public int akka$stream$scaladsl$ZipWith16$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith16$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith16$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith16$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith16$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith16$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith16$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11()), this.grab(this.$outer.in12()), this.grab(this.$outer.in13()), this.grab(this.$outer.in14()), this.grab(this.$outer.in15())));
                if (this.willShutDown()) {
                    this.completeStage();
                    return;
                }
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
                this.pull(this.$outer.in6());
                this.pull(this.$outer.in7());
                this.pull(this.$outer.in8());
                this.pull(this.$outer.in9());
                this.pull(this.$outer.in10());
                this.pull(this.$outer.in11());
                this.pull(this.$outer.in12());
                this.pull(this.$outer.in13());
                this.pull(this.$outer.in14());
                this.pull(this.$outer.in15());
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
                this.pull(this.$outer.in6());
                this.pull(this.$outer.in7());
                this.pull(this.$outer.in8());
                this.pull(this.$outer.in9());
                this.pull(this.$outer.in10());
                this.pull(this.$outer.in11());
                this.pull(this.$outer.in12());
                this.pull(this.$outer.in13());
                this.pull(this.$outer.in14());
                this.pull(this.$outer.in15());
            }

            public /* synthetic */ ZipWith16 akka$stream$scaladsl$ZipWith16$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith16$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith16$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in1(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in2(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in3(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in4(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in5(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in6(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in7(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in8(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in9(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in10(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in11(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in12(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in12())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in13(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in13())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in14(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in14())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in15(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().in15())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith16$$anon$$pushAll();
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
        };
    }

    public String toString() {
        return "ZipWith16";
    }

    public ZipWith16(Function16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape16("ZipWith16");
        this.in0 = ((FanInShape16)this.shape()).in0();
        this.in1 = ((FanInShape16)this.shape()).in1();
        this.in2 = ((FanInShape16)this.shape()).in2();
        this.in3 = ((FanInShape16)this.shape()).in3();
        this.in4 = ((FanInShape16)this.shape()).in4();
        this.in5 = ((FanInShape16)this.shape()).in5();
        this.in6 = ((FanInShape16)this.shape()).in6();
        this.in7 = ((FanInShape16)this.shape()).in7();
        this.in8 = ((FanInShape16)this.shape()).in8();
        this.in9 = ((FanInShape16)this.shape()).in9();
        this.in10 = ((FanInShape16)this.shape()).in10();
        this.in11 = ((FanInShape16)this.shape()).in11();
        this.in12 = ((FanInShape16)this.shape()).in12();
        this.in13 = ((FanInShape16)this.shape()).in13();
        this.in14 = ((FanInShape16)this.shape()).in14();
        this.in15 = ((FanInShape16)this.shape()).in15();
    }
}

