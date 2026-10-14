/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape17;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith17$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function17;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005h\u0001\u0002\u0017.\u0001QB!\"a\u0001\u0001\u0005\u000b\u0007I\u0011AA\u0003\u0011)\ti\u0001\u0001B\u0001B\u0003%\u0011q\u0001\u0005\b\u0003\u001f\u0001A\u0011AA\t\u0011\u001d\tI\u0002\u0001C!\u00037A\u0011\"a\t\u0001\u0005\u0004%\t%!\n\t\u000f\u0005\u001d\u0002\u0001)A\u0005y!9\u0011\u0011\u0006\u0001\u0005\u0002\u0005-\u0002\"CA\u001a\u0001\t\u0007I\u0011AA\u001b\u0011!\ti\u0004\u0001Q\u0001\n\u0005]\u0002\"CA \u0001\t\u0007I\u0011AA!\u0011!\t)\u0005\u0001Q\u0001\n\u0005\r\u0003\"CA$\u0001\t\u0007I\u0011AA%\u0011!\ti\u0005\u0001Q\u0001\n\u0005-\u0003\"CA(\u0001\t\u0007I\u0011AA)\u0011!\t)\u0006\u0001Q\u0001\n\u0005M\u0003\"CA,\u0001\t\u0007I\u0011AA-\u0011!\ti\u0006\u0001Q\u0001\n\u0005m\u0003\"CA0\u0001\t\u0007I\u0011AA1\u0011!\t)\u0007\u0001Q\u0001\n\u0005\r\u0004\"CA4\u0001\t\u0007I\u0011AA5\u0011!\ti\u0007\u0001Q\u0001\n\u0005-\u0004\"CA8\u0001\t\u0007I\u0011AA9\u0011!\t)\b\u0001Q\u0001\n\u0005M\u0004\"CA<\u0001\t\u0007I\u0011AA=\u0011!\ti\b\u0001Q\u0001\n\u0005m\u0004\"CA@\u0001\t\u0007I\u0011AAA\u0011!\t)\t\u0001Q\u0001\n\u0005\r\u0005\"CAD\u0001\t\u0007I\u0011AAE\u0011!\ti\t\u0001Q\u0001\n\u0005-\u0005\"CAH\u0001\t\u0007I\u0011AAI\u0011!\t)\n\u0001Q\u0001\n\u0005M\u0005\"CAL\u0001\t\u0007I\u0011AAM\u0011!\ti\n\u0001Q\u0001\n\u0005m\u0005\"CAP\u0001\t\u0007I\u0011AAQ\u0011!\t)\u000b\u0001Q\u0001\n\u0005\r\u0006\"CAT\u0001\t\u0007I\u0011AAU\u0011!\ti\u000b\u0001Q\u0001\n\u0005-\u0006\"CAX\u0001\t\u0007I\u0011AAY\u0011!\t)\f\u0001Q\u0001\n\u0005M\u0006\"CA\\\u0001\t\u0007I\u0011AA]\u0011!\ti\f\u0001Q\u0001\n\u0005m\u0006bBA`\u0001\u0011\u0005\u0013\u0011\u0019\u0005\b\u0003\u001b\u0004A\u0011IAh\u0005%Q\u0016\u000e],ji\"\ftG\u0003\u0002/_\u0005A1oY1mC\u0012\u001cHN\u0003\u00021c\u000511\u000f\u001e:fC6T\u0011AM\u0001\u0005C.\\\u0017m\u0001\u0001\u0016'U\u0012uJU+Y7z\u000bGm\u001a6naN4\u0018\u0010`@\u0014\u0005\u00011\u0004cA\u001c;y5\t\u0001H\u0003\u0002:_\u0005)1\u000f^1hK&\u00111\b\u000f\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007\u0003F\u001f?\u0001:\u000bFk\u0016.^A\u000e4\u0017\u000e\\8skb\\h0D\u00010\u0013\tytF\u0001\u0007GC:Len\u00155ba\u0016\ft\u0007\u0005\u0002B\u00052\u0001A!B\"\u0001\u0005\u0004!%AA!2#\t)5\n\u0005\u0002G\u00136\tqIC\u0001I\u0003\u0015\u00198-\u00197b\u0013\tQuIA\u0004O_RD\u0017N\\4\u0011\u0005\u0019c\u0015BA'H\u0005\r\te.\u001f\t\u0003\u0003>#Q\u0001\u0015\u0001C\u0002\u0011\u0013!!\u0011\u001a\u0011\u0005\u0005\u0013F!B*\u0001\u0005\u0004!%AA!4!\t\tU\u000bB\u0003W\u0001\t\u0007AI\u0001\u0002BiA\u0011\u0011\t\u0017\u0003\u00063\u0002\u0011\r\u0001\u0012\u0002\u0003\u0003V\u0002\"!Q.\u0005\u000bq\u0003!\u0019\u0001#\u0003\u0005\u00053\u0004CA!_\t\u0015y\u0006A1\u0001E\u0005\t\tu\u0007\u0005\u0002BC\u0012)!\r\u0001b\u0001\t\n\u0011\u0011\t\u000f\t\u0003\u0003\u0012$Q!\u001a\u0001C\u0002\u0011\u0013!!Q\u001d\u0011\u0005\u0005;G!\u00025\u0001\u0005\u0004!%aA!2aA\u0011\u0011I\u001b\u0003\u0006W\u0002\u0011\r\u0001\u0012\u0002\u0004\u0003F\n\u0004CA!n\t\u0015q\u0007A1\u0001E\u0005\r\t\u0015G\r\t\u0003\u0003B$Q!\u001d\u0001C\u0002\u0011\u00131!Q\u00194!\t\t5\u000fB\u0003u\u0001\t\u0007AIA\u0002BcQ\u0002\"!\u0011<\u0005\u000b]\u0004!\u0019\u0001#\u0003\u0007\u0005\u000bT\u0007\u0005\u0002Bs\u0012)!\u0010\u0001b\u0001\t\n\u0019\u0011)\r\u001c\u0011\u0005\u0005cH!B?\u0001\u0005\u0004!%aA!2oA\u0011\u0011i \u0003\u0007\u0003\u0003\u0001!\u0019\u0001#\u0003\u0003=\u000baA_5qa\u0016\u0014XCAA\u0004!U1\u0015\u0011\u0002!O#R;&,\u00181dM&dwN];ywzL1!a\u0003H\u0005)1UO\\2uS>t\u0017gN\u0001\bu&\u0004\b/\u001a:!\u0003\u0019a\u0014N\\5u}Q!\u00111CA\f!U\t)\u0002\u0001!O#R;&,\u00181dM&dwN];ywzl\u0011!\f\u0005\b\u0003\u0007\u0019\u0001\u0019AA\u0004\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0003\u0003;\u00012!PA\u0010\u0013\r\t\tc\f\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#\u0001\u001f\u0002\rMD\u0017\r]3!\u0003\ryW\u000f^\u000b\u0003\u0003[\u0001B!PA\u0018}&\u0019\u0011\u0011G\u0018\u0003\r=+H\u000f\\3u\u0003\rIg\u000eM\u000b\u0003\u0003o\u0001B!PA\u001d\u0001&\u0019\u00111H\u0018\u0003\u000b%sG.\u001a;\u0002\t%t\u0007\u0007I\u0001\u0004S:\fTCAA\"!\u0011i\u0014\u0011\b(\u0002\t%t\u0017\u0007I\u0001\u0004S:\u0014TCAA&!\u0011i\u0014\u0011H)\u0002\t%t'\u0007I\u0001\u0004S:\u001cTCAA*!\u0011i\u0014\u0011\b+\u0002\t%t7\u0007I\u0001\u0004S:$TCAA.!\u0011i\u0014\u0011H,\u0002\t%tG\u0007I\u0001\u0004S:,TCAA2!\u0011i\u0014\u0011\b.\u0002\t%tW\u0007I\u0001\u0004S:4TCAA6!\u0011i\u0014\u0011H/\u0002\t%tg\u0007I\u0001\u0004S:<TCAA:!\u0011i\u0014\u0011\b1\u0002\t%tw\u0007I\u0001\u0004S:DTCAA>!\u0011i\u0014\u0011H2\u0002\t%t\u0007\bI\u0001\u0004S:LTCAAB!\u0011i\u0014\u0011\b4\u0002\t%t\u0017\bI\u0001\u0005S:\f\u0004'\u0006\u0002\u0002\fB!Q(!\u000fj\u0003\u0015Ig.\r\u0019!\u0003\u0011Ig.M\u0019\u0016\u0005\u0005M\u0005\u0003B\u001f\u0002:1\fQ!\u001b82c\u0001\nA!\u001b82eU\u0011\u00111\u0014\t\u0005{\u0005er.A\u0003j]F\u0012\u0004%\u0001\u0003j]F\u001aTCAAR!\u0011i\u0014\u0011\b:\u0002\u000b%t\u0017g\r\u0011\u0002\t%t\u0017\u0007N\u000b\u0003\u0003W\u0003B!PA\u001dk\u0006)\u0011N\\\u00195A\u0005!\u0011N\\\u00196+\t\t\u0019\f\u0005\u0003>\u0003sA\u0018!B5ocU\u0002\u0013\u0001B5ocY*\"!a/\u0011\tu\nId_\u0001\u0006S:\fd\u0007I\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002D\u0006%\u0007cA\u001c\u0002F&\u0019\u0011q\u0019\u001d\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDq!a3+\u0001\u0004\ti\"A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\t\t\t\u000e\u0005\u0003\u0002T\u0006uWBAAk\u0015\u0011\t9.!7\u0002\t1\fgn\u001a\u0006\u0003\u00037\fAA[1wC&!\u0011q\\Ak\u0005\u0019\u0019FO]5oO\u0002")
public class ZipWith17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O>
extends GraphStage<FanInShape17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O>> {
    private final Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> zipper;
    private final FanInShape17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> shape;
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
    private final Inlet<A17> in16;

    public Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith17");
    }

    @Override
    public FanInShape17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> shape() {
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

    public Inlet<A17> in16() {
        return this.in16;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith17$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith17$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith17$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith17 $outer;

            public int akka$stream$scaladsl$ZipWith17$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith17$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith17$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith17$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith17$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith17$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith17$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11()), this.grab(this.$outer.in12()), this.grab(this.$outer.in13()), this.grab(this.$outer.in14()), this.grab(this.$outer.in15()), this.grab(this.$outer.in16())));
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
                this.pull(this.$outer.in16());
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
                this.pull(this.$outer.in16());
            }

            public /* synthetic */ ZipWith17 akka$stream$scaladsl$ZipWith17$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith17$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith17$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in12())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in13())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in14())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in15())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in16(), new InHandler(this){
                    private final /* synthetic */ $anon$166 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().in16())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$166 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith17$$anon$$pushAll();
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
        return "ZipWith17";
    }

    public ZipWith17(Function17<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape17("ZipWith17");
        this.in0 = ((FanInShape17)this.shape()).in0();
        this.in1 = ((FanInShape17)this.shape()).in1();
        this.in2 = ((FanInShape17)this.shape()).in2();
        this.in3 = ((FanInShape17)this.shape()).in3();
        this.in4 = ((FanInShape17)this.shape()).in4();
        this.in5 = ((FanInShape17)this.shape()).in5();
        this.in6 = ((FanInShape17)this.shape()).in6();
        this.in7 = ((FanInShape17)this.shape()).in7();
        this.in8 = ((FanInShape17)this.shape()).in8();
        this.in9 = ((FanInShape17)this.shape()).in9();
        this.in10 = ((FanInShape17)this.shape()).in10();
        this.in11 = ((FanInShape17)this.shape()).in11();
        this.in12 = ((FanInShape17)this.shape()).in12();
        this.in13 = ((FanInShape17)this.shape()).in13();
        this.in14 = ((FanInShape17)this.shape()).in14();
        this.in15 = ((FanInShape17)this.shape()).in15();
        this.in16 = ((FanInShape17)this.shape()).in16();
    }
}

