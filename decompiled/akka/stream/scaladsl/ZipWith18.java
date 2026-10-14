/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape18;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith18$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function18;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Mh\u0001\u0002\u00180\u0001YB!\"!\u0004\u0001\u0005\u000b\u0007I\u0011AA\b\u0011)\t9\u0002\u0001B\u0001B\u0003%\u0011\u0011\u0003\u0005\b\u00033\u0001A\u0011AA\u000e\u0011\u001d\t\u0019\u0003\u0001C!\u0003KA\u0011\"!\f\u0001\u0005\u0004%\t%a\f\t\u000f\u0005E\u0002\u0001)A\u0005}!9\u00111\u0007\u0001\u0005\u0002\u0005U\u0002\"CA\u001f\u0001\t\u0007I\u0011AA \u0011!\t9\u0005\u0001Q\u0001\n\u0005\u0005\u0003\"CA%\u0001\t\u0007I\u0011AA&\u0011!\ty\u0005\u0001Q\u0001\n\u00055\u0003\"CA)\u0001\t\u0007I\u0011AA*\u0011!\t9\u0006\u0001Q\u0001\n\u0005U\u0003\"CA-\u0001\t\u0007I\u0011AA.\u0011!\ty\u0006\u0001Q\u0001\n\u0005u\u0003\"CA1\u0001\t\u0007I\u0011AA2\u0011!\t9\u0007\u0001Q\u0001\n\u0005\u0015\u0004\"CA5\u0001\t\u0007I\u0011AA6\u0011!\ty\u0007\u0001Q\u0001\n\u00055\u0004\"CA9\u0001\t\u0007I\u0011AA:\u0011!\t9\b\u0001Q\u0001\n\u0005U\u0004\"CA=\u0001\t\u0007I\u0011AA>\u0011!\ty\b\u0001Q\u0001\n\u0005u\u0004\"CAA\u0001\t\u0007I\u0011AAB\u0011!\t9\t\u0001Q\u0001\n\u0005\u0015\u0005\"CAE\u0001\t\u0007I\u0011AAF\u0011!\ty\t\u0001Q\u0001\n\u00055\u0005\"CAI\u0001\t\u0007I\u0011AAJ\u0011!\t9\n\u0001Q\u0001\n\u0005U\u0005\"CAM\u0001\t\u0007I\u0011AAN\u0011!\ty\n\u0001Q\u0001\n\u0005u\u0005\"CAQ\u0001\t\u0007I\u0011AAR\u0011!\t9\u000b\u0001Q\u0001\n\u0005\u0015\u0006\"CAU\u0001\t\u0007I\u0011AAV\u0011!\ty\u000b\u0001Q\u0001\n\u00055\u0006\"CAY\u0001\t\u0007I\u0011AAZ\u0011!\t9\f\u0001Q\u0001\n\u0005U\u0006\"CA]\u0001\t\u0007I\u0011AA^\u0011!\ty\f\u0001Q\u0001\n\u0005u\u0006\"CAa\u0001\t\u0007I\u0011AAb\u0011!\t9\r\u0001Q\u0001\n\u0005\u0015\u0007\"CAe\u0001\t\u0007I\u0011AAf\u0011!\ty\r\u0001Q\u0001\n\u00055\u0007bBAi\u0001\u0011\u0005\u00131\u001b\u0005\b\u0003?\u0004A\u0011IAq\u0005%Q\u0016\u000e],ji\"\f\u0004H\u0003\u00021c\u0005A1oY1mC\u0012\u001cHN\u0003\u00023g\u000511\u000f\u001e:fC6T\u0011\u0001N\u0001\u0005C.\\\u0017m\u0001\u0001\u0016-]\"\u0015\u000bV,[;\u0002\u001cg-\u001b7peVD8P`A\u0002\u0003\u0013\u0019\"\u0001\u0001\u001d\u0011\u0007ebd(D\u0001;\u0015\tY\u0014'A\u0003ti\u0006<W-\u0003\u0002>u\tQqI]1qQN#\u0018mZ3\u0011/}\u0002%\tU*W3r{&-\u001a5l]F$xO_?\u0002\u0002\u0005\u001dQ\"A\u0019\n\u0005\u0005\u000b$\u0001\u0004$b]&s7\u000b[1qKFB\u0004CA\"E\u0019\u0001!Q!\u0012\u0001C\u0002\u0019\u0013!!Q\u0019\u0012\u0005\u001dk\u0005C\u0001%L\u001b\u0005I%\"\u0001&\u0002\u000bM\u001c\u0017\r\\1\n\u00051K%a\u0002(pi\"Lgn\u001a\t\u0003\u0011:K!aT%\u0003\u0007\u0005s\u0017\u0010\u0005\u0002D#\u0012)!\u000b\u0001b\u0001\r\n\u0011\u0011I\r\t\u0003\u0007R#Q!\u0016\u0001C\u0002\u0019\u0013!!Q\u001a\u0011\u0005\r;F!\u0002-\u0001\u0005\u00041%AA!5!\t\u0019%\fB\u0003\\\u0001\t\u0007aI\u0001\u0002BkA\u00111)\u0018\u0003\u0006=\u0002\u0011\rA\u0012\u0002\u0003\u0003Z\u0002\"a\u00111\u0005\u000b\u0005\u0004!\u0019\u0001$\u0003\u0005\u0005;\u0004CA\"d\t\u0015!\u0007A1\u0001G\u0005\t\t\u0005\b\u0005\u0002DM\u0012)q\r\u0001b\u0001\r\n\u0011\u0011)\u000f\t\u0003\u0007&$QA\u001b\u0001C\u0002\u0019\u00131!Q\u00191!\t\u0019E\u000eB\u0003n\u0001\t\u0007aIA\u0002BcE\u0002\"aQ8\u0005\u000bA\u0004!\u0019\u0001$\u0003\u0007\u0005\u000b$\u0007\u0005\u0002De\u0012)1\u000f\u0001b\u0001\r\n\u0019\u0011)M\u001a\u0011\u0005\r+H!\u0002<\u0001\u0005\u00041%aA!2iA\u00111\t\u001f\u0003\u0006s\u0002\u0011\rA\u0012\u0002\u0004\u0003F*\u0004CA\"|\t\u0015a\bA1\u0001G\u0005\r\t\u0015G\u000e\t\u0003\u0007z$Qa \u0001C\u0002\u0019\u00131!Q\u00198!\r\u0019\u00151\u0001\u0003\u0007\u0003\u000b\u0001!\u0019\u0001$\u0003\u0007\u0005\u000b\u0004\bE\u0002D\u0003\u0013!a!a\u0003\u0001\u0005\u00041%!A(\u0002\riL\u0007\u000f]3s+\t\t\t\u0002\u0005\rI\u0003'\u0011\u0005k\u0015,Z9~\u0013W\r[6ocR<(0`A\u0001\u0003\u000fI1!!\u0006J\u0005)1UO\\2uS>t\u0017\u0007O\u0001\bu&\u0004\b/\u001a:!\u0003\u0019a\u0014N\\5u}Q!\u0011QDA\u0011!a\ty\u0002\u0001\"Q'ZKFl\u00182fQ.t\u0017\u000f^<{{\u0006\u0005\u0011qA\u0007\u0002_!9\u0011QB\u0002A\u0002\u0005E\u0011!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\u0011\u0011q\u0005\t\u0004\u007f\u0005%\u0012bAA\u0016c\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003y\naa\u001d5ba\u0016\u0004\u0013aA8viV\u0011\u0011q\u0007\t\u0006\u007f\u0005e\u0012qA\u0005\u0004\u0003w\t$AB(vi2,G/A\u0002j]B*\"!!\u0011\u0011\t}\n\u0019EQ\u0005\u0004\u0003\u000b\n$!B%oY\u0016$\u0018\u0001B5oa\u0001\n1!\u001b82+\t\ti\u0005\u0005\u0003@\u0003\u0007\u0002\u0016\u0001B5oc\u0001\n1!\u001b83+\t\t)\u0006\u0005\u0003@\u0003\u0007\u001a\u0016\u0001B5oe\u0001\n1!\u001b84+\t\ti\u0006\u0005\u0003@\u0003\u00072\u0016\u0001B5og\u0001\n1!\u001b85+\t\t)\u0007\u0005\u0003@\u0003\u0007J\u0016\u0001B5oi\u0001\n1!\u001b86+\t\ti\u0007\u0005\u0003@\u0003\u0007b\u0016\u0001B5ok\u0001\n1!\u001b87+\t\t)\b\u0005\u0003@\u0003\u0007z\u0016\u0001B5om\u0001\n1!\u001b88+\t\ti\b\u0005\u0003@\u0003\u0007\u0012\u0017\u0001B5oo\u0001\n1!\u001b89+\t\t)\t\u0005\u0003@\u0003\u0007*\u0017\u0001B5oq\u0001\n1!\u001b8:+\t\ti\t\u0005\u0003@\u0003\u0007B\u0017\u0001B5os\u0001\nA!\u001b82aU\u0011\u0011Q\u0013\t\u0005\u007f\u0005\r3.A\u0003j]F\u0002\u0004%\u0001\u0003j]F\nTCAAO!\u0011y\u00141\t8\u0002\u000b%t\u0017'\r\u0011\u0002\t%t\u0017GM\u000b\u0003\u0003K\u0003BaPA\"c\u0006)\u0011N\\\u00193A\u0005!\u0011N\\\u00194+\t\ti\u000b\u0005\u0003@\u0003\u0007\"\u0018!B5ocM\u0002\u0013\u0001B5ocQ*\"!!.\u0011\t}\n\u0019e^\u0001\u0006S:\fD\u0007I\u0001\u0005S:\fT'\u0006\u0002\u0002>B!q(a\u0011{\u0003\u0015Ig.M\u001b!\u0003\u0011Ig.\r\u001c\u0016\u0005\u0005\u0015\u0007\u0003B \u0002Du\fQ!\u001b82m\u0001\nA!\u001b82oU\u0011\u0011Q\u001a\t\u0006\u007f\u0005\r\u0013\u0011A\u0001\u0006S:\ft\u0007I\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002V\u0006m\u0007cA\u001d\u0002X&\u0019\u0011\u0011\u001c\u001e\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDq!!8-\u0001\u0004\t9#A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\t\t\u0019\u000f\u0005\u0003\u0002f\u0006=XBAAt\u0015\u0011\tI/a;\u0002\t1\fgn\u001a\u0006\u0003\u0003[\fAA[1wC&!\u0011\u0011_At\u0005\u0019\u0019FO]5oO\u0002")
public class ZipWith18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O>
extends GraphStage<FanInShape18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O>> {
    private final Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> zipper;
    private final FanInShape18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> shape;
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
    private final Inlet<A18> in17;

    public Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith18");
    }

    @Override
    public FanInShape18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> shape() {
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

    public Inlet<A18> in17() {
        return this.in17;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith18$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith18$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith18$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith18 $outer;

            public int akka$stream$scaladsl$ZipWith18$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith18$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith18$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith18$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith18$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith18$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith18$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11()), this.grab(this.$outer.in12()), this.grab(this.$outer.in13()), this.grab(this.$outer.in14()), this.grab(this.$outer.in15()), this.grab(this.$outer.in16()), this.grab(this.$outer.in17())));
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
                this.pull(this.$outer.in17());
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
                this.pull(this.$outer.in17());
            }

            public /* synthetic */ ZipWith18 akka$stream$scaladsl$ZipWith18$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith18$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith18$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in12())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in13())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in14())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in15())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in16())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in17(), new InHandler(this){
                    private final /* synthetic */ $anon$185 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().in17())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$185 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith18$$anon$$pushAll();
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
        return "ZipWith18";
    }

    public ZipWith18(Function18<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape18("ZipWith18");
        this.in0 = ((FanInShape18)this.shape()).in0();
        this.in1 = ((FanInShape18)this.shape()).in1();
        this.in2 = ((FanInShape18)this.shape()).in2();
        this.in3 = ((FanInShape18)this.shape()).in3();
        this.in4 = ((FanInShape18)this.shape()).in4();
        this.in5 = ((FanInShape18)this.shape()).in5();
        this.in6 = ((FanInShape18)this.shape()).in6();
        this.in7 = ((FanInShape18)this.shape()).in7();
        this.in8 = ((FanInShape18)this.shape()).in8();
        this.in9 = ((FanInShape18)this.shape()).in9();
        this.in10 = ((FanInShape18)this.shape()).in10();
        this.in11 = ((FanInShape18)this.shape()).in11();
        this.in12 = ((FanInShape18)this.shape()).in12();
        this.in13 = ((FanInShape18)this.shape()).in13();
        this.in14 = ((FanInShape18)this.shape()).in14();
        this.in15 = ((FanInShape18)this.shape()).in15();
        this.in16 = ((FanInShape18)this.shape()).in16();
        this.in17 = ((FanInShape18)this.shape()).in17();
    }
}

