/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape21;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith21$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function21;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t%b\u0001\u0002\u001b6\u0001qB!\"a\u000b\u0001\u0005\u000b\u0007I\u0011AA\u0017\u0011)\t)\u0004\u0001B\u0001B\u0003%\u0011q\u0006\u0005\b\u0003o\u0001A\u0011AA\u001d\u0011\u001d\t\t\u0005\u0001C!\u0003\u0007B\u0011\"a\u0013\u0001\u0005\u0004%\t%!\u0014\t\u000f\u0005=\u0003\u0001)A\u0005\t\"9\u0011\u0011\u000b\u0001\u0005\u0002\u0005M\u0003\"CA.\u0001\t\u0007I\u0011AA/\u0011!\t)\u0007\u0001Q\u0001\n\u0005}\u0003\"CA4\u0001\t\u0007I\u0011AA5\u0011!\ti\u0007\u0001Q\u0001\n\u0005-\u0004\"CA8\u0001\t\u0007I\u0011AA9\u0011!\t)\b\u0001Q\u0001\n\u0005M\u0004\"CA<\u0001\t\u0007I\u0011AA=\u0011!\ti\b\u0001Q\u0001\n\u0005m\u0004\"CA@\u0001\t\u0007I\u0011AAA\u0011!\t)\t\u0001Q\u0001\n\u0005\r\u0005\"CAD\u0001\t\u0007I\u0011AAE\u0011!\ti\t\u0001Q\u0001\n\u0005-\u0005\"CAH\u0001\t\u0007I\u0011AAI\u0011!\t)\n\u0001Q\u0001\n\u0005M\u0005\"CAL\u0001\t\u0007I\u0011AAM\u0011!\ti\n\u0001Q\u0001\n\u0005m\u0005\"CAP\u0001\t\u0007I\u0011AAQ\u0011!\t)\u000b\u0001Q\u0001\n\u0005\r\u0006\"CAT\u0001\t\u0007I\u0011AAU\u0011!\ti\u000b\u0001Q\u0001\n\u0005-\u0006\"CAX\u0001\t\u0007I\u0011AAY\u0011!\t)\f\u0001Q\u0001\n\u0005M\u0006\"CA\\\u0001\t\u0007I\u0011AA]\u0011!\ti\f\u0001Q\u0001\n\u0005m\u0006\"CA`\u0001\t\u0007I\u0011AAa\u0011!\t)\r\u0001Q\u0001\n\u0005\r\u0007\"CAd\u0001\t\u0007I\u0011AAe\u0011!\ti\r\u0001Q\u0001\n\u0005-\u0007\"CAh\u0001\t\u0007I\u0011AAi\u0011!\t)\u000e\u0001Q\u0001\n\u0005M\u0007\"CAl\u0001\t\u0007I\u0011AAm\u0011!\ti\u000e\u0001Q\u0001\n\u0005m\u0007\"CAp\u0001\t\u0007I\u0011AAq\u0011!\t)\u000f\u0001Q\u0001\n\u0005\r\b\"CAt\u0001\t\u0007I\u0011AAu\u0011!\ti\u000f\u0001Q\u0001\n\u0005-\b\"CAx\u0001\t\u0007I\u0011AAy\u0011!\t)\u0010\u0001Q\u0001\n\u0005M\b\"CA|\u0001\t\u0007I\u0011AA}\u0011!\ti\u0010\u0001Q\u0001\n\u0005m\b\"CA\u0000\u0001\t\u0007I\u0011\u0001B\u0001\u0011!\u0011)\u0001\u0001Q\u0001\n\t\r\u0001b\u0002B\u0004\u0001\u0011\u0005#\u0011\u0002\u0005\b\u0005+\u0001A\u0011\tB\f\u0005%Q\u0016\u000e],ji\"\u0014\u0014G\u0003\u00027o\u0005A1oY1mC\u0012\u001cHN\u0003\u00029s\u000511\u000f\u001e:fC6T\u0011AO\u0001\u0005C.\\\u0017m\u0001\u0001\u0016=uRuKW/aG\u001aLGn\u001c:vqnt\u00181AA\u0005\u0003\u001f\t)\"a\u0007\u0002\"\u0005\u001d2C\u0001\u0001?!\ry$\tR\u0007\u0002\u0001*\u0011\u0011iN\u0001\u0006gR\fw-Z\u0005\u0003\u0007\u0002\u0013!b\u0012:ba\"\u001cF/Y4f!})e\t\u0013,Z9~\u0013W\r[6ocR<(0`A\u0001\u0003\u000f\ti!a\u0005\u0002\u001a\u0005}\u0011QE\u0007\u0002o%\u0011qi\u000e\u0002\r\r\u0006t\u0017J\\*iCB,''\r\t\u0003\u0013*c\u0001\u0001B\u0003L\u0001\t\u0007AJ\u0001\u0002BcE\u0011Qj\u0015\t\u0003\u001dFk\u0011a\u0014\u0006\u0002!\u0006)1oY1mC&\u0011!k\u0014\u0002\b\u001d>$\b.\u001b8h!\tqE+\u0003\u0002V\u001f\n\u0019\u0011I\\=\u0011\u0005%;F!\u0002-\u0001\u0005\u0004a%AA!3!\tI%\fB\u0003\\\u0001\t\u0007AJ\u0001\u0002BgA\u0011\u0011*\u0018\u0003\u0006=\u0002\u0011\r\u0001\u0014\u0002\u0003\u0003R\u0002\"!\u00131\u0005\u000b\u0005\u0004!\u0019\u0001'\u0003\u0005\u0005+\u0004CA%d\t\u0015!\u0007A1\u0001M\u0005\t\te\u0007\u0005\u0002JM\u0012)q\r\u0001b\u0001\u0019\n\u0011\u0011i\u000e\t\u0003\u0013&$QA\u001b\u0001C\u00021\u0013!!\u0011\u001d\u0011\u0005%cG!B7\u0001\u0005\u0004a%AA!:!\tIu\u000eB\u0003q\u0001\t\u0007AJA\u0002BcA\u0002\"!\u0013:\u0005\u000bM\u0004!\u0019\u0001'\u0003\u0007\u0005\u000b\u0014\u0007\u0005\u0002Jk\u0012)a\u000f\u0001b\u0001\u0019\n\u0019\u0011)\r\u001a\u0011\u0005%CH!B=\u0001\u0005\u0004a%aA!2gA\u0011\u0011j\u001f\u0003\u0006y\u0002\u0011\r\u0001\u0014\u0002\u0004\u0003F\"\u0004CA%\u007f\t\u0015y\bA1\u0001M\u0005\r\t\u0015'\u000e\t\u0004\u0013\u0006\rAABA\u0003\u0001\t\u0007AJA\u0002BcY\u00022!SA\u0005\t\u0019\tY\u0001\u0001b\u0001\u0019\n\u0019\u0011)M\u001c\u0011\u0007%\u000by\u0001\u0002\u0004\u0002\u0012\u0001\u0011\r\u0001\u0014\u0002\u0004\u0003FB\u0004cA%\u0002\u0016\u00111\u0011q\u0003\u0001C\u00021\u00131!Q\u0019:!\rI\u00151\u0004\u0003\u0007\u0003;\u0001!\u0019\u0001'\u0003\u0007\u0005\u0013\u0004\u0007E\u0002J\u0003C!a!a\t\u0001\u0005\u0004a%aA!3cA\u0019\u0011*a\n\u0005\r\u0005%\u0002A1\u0001M\u0005\u0005y\u0015A\u0002>jaB,'/\u0006\u0002\u00020A\u0001c*!\rI-fcvLY3iW:\fHo\u001e>~\u0003\u0003\t9!!\u0004\u0002\u0014\u0005e\u0011qDA\u0013\u0013\r\t\u0019d\u0014\u0002\u000b\rVt7\r^5p]J\n\u0014a\u0002>jaB,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\t\u0005m\u0012q\b\t!\u0003{\u0001\u0001JV-]?\n,\u0007n\u001b8ri^TX0!\u0001\u0002\b\u00055\u00111CA\r\u0003?\t)#D\u00016\u0011\u001d\tYc\u0001a\u0001\u0003_\t\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\t\t)\u0005E\u0002F\u0003\u000fJ1!!\u00138\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002\t\u000611\u000f[1qK\u0002\n1a\\;u+\t\t)\u0006E\u0003F\u0003/\n)#C\u0002\u0002Z]\u0012aaT;uY\u0016$\u0018aA5oaU\u0011\u0011q\f\t\u0005\u000b\u0006\u0005\u0004*C\u0002\u0002d]\u0012Q!\u00138mKR\fA!\u001b81A\u0005\u0019\u0011N\\\u0019\u0016\u0005\u0005-\u0004\u0003B#\u0002bY\u000bA!\u001b82A\u0005\u0019\u0011N\u001c\u001a\u0016\u0005\u0005M\u0004\u0003B#\u0002be\u000bA!\u001b83A\u0005\u0019\u0011N\\\u001a\u0016\u0005\u0005m\u0004\u0003B#\u0002bq\u000bA!\u001b84A\u0005\u0019\u0011N\u001c\u001b\u0016\u0005\u0005\r\u0005\u0003B#\u0002b}\u000bA!\u001b85A\u0005\u0019\u0011N\\\u001b\u0016\u0005\u0005-\u0005\u0003B#\u0002b\t\fA!\u001b86A\u0005\u0019\u0011N\u001c\u001c\u0016\u0005\u0005M\u0005\u0003B#\u0002b\u0015\fA!\u001b87A\u0005\u0019\u0011N\\\u001c\u0016\u0005\u0005m\u0005\u0003B#\u0002b!\fA!\u001b88A\u0005\u0019\u0011N\u001c\u001d\u0016\u0005\u0005\r\u0006\u0003B#\u0002b-\fA!\u001b89A\u0005\u0019\u0011N\\\u001d\u0016\u0005\u0005-\u0006\u0003B#\u0002b9\fA!\u001b8:A\u0005!\u0011N\\\u00191+\t\t\u0019\f\u0005\u0003F\u0003C\n\u0018!B5ocA\u0002\u0013\u0001B5ocE*\"!a/\u0011\t\u0015\u000b\t\u0007^\u0001\u0006S:\f\u0014\u0007I\u0001\u0005S:\f$'\u0006\u0002\u0002DB!Q)!\u0019x\u0003\u0015Ig.\r\u001a!\u0003\u0011Ig.M\u001a\u0016\u0005\u0005-\u0007\u0003B#\u0002bi\fQ!\u001b82g\u0001\nA!\u001b82iU\u0011\u00111\u001b\t\u0005\u000b\u0006\u0005T0A\u0003j]F\"\u0004%\u0001\u0003j]F*TCAAn!\u0015)\u0015\u0011MA\u0001\u0003\u0015Ig.M\u001b!\u0003\u0011Ig.\r\u001c\u0016\u0005\u0005\r\b#B#\u0002b\u0005\u001d\u0011!B5ocY\u0002\u0013\u0001B5oc]*\"!a;\u0011\u000b\u0015\u000b\t'!\u0004\u0002\u000b%t\u0017g\u000e\u0011\u0002\t%t\u0017\u0007O\u000b\u0003\u0003g\u0004R!RA1\u0003'\tQ!\u001b82q\u0001\nA!\u001b82sU\u0011\u00111 \t\u0006\u000b\u0006\u0005\u0014\u0011D\u0001\u0006S:\f\u0014\bI\u0001\u0005S:\u0014\u0004'\u0006\u0002\u0003\u0004A)Q)!\u0019\u0002 \u0005)\u0011N\u001c\u001a1A\u0005Y1M]3bi\u0016dunZ5d)\u0011\u0011YA!\u0005\u0011\u0007}\u0012i!C\u0002\u0003\u0010\u0001\u0013qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\b\u0005'\u0011\u0004\u0019AA#\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<GC\u0001B\r!\u0011\u0011YB!\n\u000e\u0005\tu!\u0002\u0002B\u0010\u0005C\tA\u0001\\1oO*\u0011!1E\u0001\u0005U\u00064\u0018-\u0003\u0003\u0003(\tu!AB*ue&tw\r")
public class ZipWith21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O>
extends GraphStage<FanInShape21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O>> {
    private final Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> zipper;
    private final FanInShape21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> shape;
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
    private final Inlet<A19> in18;
    private final Inlet<A20> in19;
    private final Inlet<A21> in20;

    public Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith21");
    }

    @Override
    public FanInShape21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> shape() {
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

    public Inlet<A19> in18() {
        return this.in18;
    }

    public Inlet<A20> in19() {
        return this.in19;
    }

    public Inlet<A21> in20() {
        return this.in20;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith21$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith21$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith21$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith21 $outer;

            public int akka$stream$scaladsl$ZipWith21$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith21$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith21$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith21$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith21$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith21$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith21$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11()), this.grab(this.$outer.in12()), this.grab(this.$outer.in13()), this.grab(this.$outer.in14()), this.grab(this.$outer.in15()), this.grab(this.$outer.in16()), this.grab(this.$outer.in17()), this.grab(this.$outer.in18()), this.grab(this.$outer.in19()), this.grab(this.$outer.in20())));
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
                this.pull(this.$outer.in18());
                this.pull(this.$outer.in19());
                this.pull(this.$outer.in20());
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
                this.pull(this.$outer.in18());
                this.pull(this.$outer.in19());
                this.pull(this.$outer.in20());
            }

            public /* synthetic */ ZipWith21 akka$stream$scaladsl$ZipWith21$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith21$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith21$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in12())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in13())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in14())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in15())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in16())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in17())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in18(), new InHandler(this){
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in18())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in19(), new InHandler(this){
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in19())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in20(), new InHandler(this){
                    private final /* synthetic */ $anon$248 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().in20())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$248 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith21$$anon$$pushAll();
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
        return "ZipWith21";
    }

    public ZipWith21(Function21<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape21("ZipWith21");
        this.in0 = ((FanInShape21)this.shape()).in0();
        this.in1 = ((FanInShape21)this.shape()).in1();
        this.in2 = ((FanInShape21)this.shape()).in2();
        this.in3 = ((FanInShape21)this.shape()).in3();
        this.in4 = ((FanInShape21)this.shape()).in4();
        this.in5 = ((FanInShape21)this.shape()).in5();
        this.in6 = ((FanInShape21)this.shape()).in6();
        this.in7 = ((FanInShape21)this.shape()).in7();
        this.in8 = ((FanInShape21)this.shape()).in8();
        this.in9 = ((FanInShape21)this.shape()).in9();
        this.in10 = ((FanInShape21)this.shape()).in10();
        this.in11 = ((FanInShape21)this.shape()).in11();
        this.in12 = ((FanInShape21)this.shape()).in12();
        this.in13 = ((FanInShape21)this.shape()).in13();
        this.in14 = ((FanInShape21)this.shape()).in14();
        this.in15 = ((FanInShape21)this.shape()).in15();
        this.in16 = ((FanInShape21)this.shape()).in16();
        this.in17 = ((FanInShape21)this.shape()).in17();
        this.in18 = ((FanInShape21)this.shape()).in18();
        this.in19 = ((FanInShape21)this.shape()).in19();
        this.in20 = ((FanInShape21)this.shape()).in20();
    }
}

