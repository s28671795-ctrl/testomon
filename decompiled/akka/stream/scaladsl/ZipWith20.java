/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape20;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith20$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function20;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t]a\u0001\u0002\u001a4\u0001iB!\"!\t\u0001\u0005\u000b\u0007I\u0011AA\u0012\u0011)\tY\u0003\u0001B\u0001B\u0003%\u0011Q\u0005\u0005\b\u0003[\u0001A\u0011AA\u0018\u0011\u001d\t9\u0004\u0001C!\u0003sA\u0011\"!\u0011\u0001\u0005\u0004%\t%a\u0011\t\u000f\u0005\u0015\u0003\u0001)A\u0005\u0005\"9\u0011q\t\u0001\u0005\u0002\u0005%\u0003\"CA)\u0001\t\u0007I\u0011AA*\u0011!\tY\u0006\u0001Q\u0001\n\u0005U\u0003\"CA/\u0001\t\u0007I\u0011AA0\u0011!\t\u0019\u0007\u0001Q\u0001\n\u0005\u0005\u0004\"CA3\u0001\t\u0007I\u0011AA4\u0011!\tY\u0007\u0001Q\u0001\n\u0005%\u0004\"CA7\u0001\t\u0007I\u0011AA8\u0011!\t\u0019\b\u0001Q\u0001\n\u0005E\u0004\"CA;\u0001\t\u0007I\u0011AA<\u0011!\tY\b\u0001Q\u0001\n\u0005e\u0004\"CA?\u0001\t\u0007I\u0011AA@\u0011!\t\u0019\t\u0001Q\u0001\n\u0005\u0005\u0005\"CAC\u0001\t\u0007I\u0011AAD\u0011!\tY\t\u0001Q\u0001\n\u0005%\u0005\"CAG\u0001\t\u0007I\u0011AAH\u0011!\t\u0019\n\u0001Q\u0001\n\u0005E\u0005\"CAK\u0001\t\u0007I\u0011AAL\u0011!\tY\n\u0001Q\u0001\n\u0005e\u0005\"CAO\u0001\t\u0007I\u0011AAP\u0011!\t\u0019\u000b\u0001Q\u0001\n\u0005\u0005\u0006\"CAS\u0001\t\u0007I\u0011AAT\u0011!\tY\u000b\u0001Q\u0001\n\u0005%\u0006\"CAW\u0001\t\u0007I\u0011AAX\u0011!\t\u0019\f\u0001Q\u0001\n\u0005E\u0006\"CA[\u0001\t\u0007I\u0011AA\\\u0011!\tY\f\u0001Q\u0001\n\u0005e\u0006\"CA_\u0001\t\u0007I\u0011AA`\u0011!\t\u0019\r\u0001Q\u0001\n\u0005\u0005\u0007\"CAc\u0001\t\u0007I\u0011AAd\u0011!\tY\r\u0001Q\u0001\n\u0005%\u0007\"CAg\u0001\t\u0007I\u0011AAh\u0011!\t\u0019\u000e\u0001Q\u0001\n\u0005E\u0007\"CAk\u0001\t\u0007I\u0011AAl\u0011!\tY\u000e\u0001Q\u0001\n\u0005e\u0007\"CAo\u0001\t\u0007I\u0011AAp\u0011!\t\u0019\u000f\u0001Q\u0001\n\u0005\u0005\b\"CAs\u0001\t\u0007I\u0011AAt\u0011!\tY\u000f\u0001Q\u0001\n\u0005%\b\"CAw\u0001\t\u0007I\u0011AAx\u0011!\t\u0019\u0010\u0001Q\u0001\n\u0005E\bbBA{\u0001\u0011\u0005\u0013q\u001f\u0005\b\u0005\u0007\u0001A\u0011\tB\u0003\u0005%Q\u0016\u000e],ji\"\u0014\u0004G\u0003\u00025k\u0005A1oY1mC\u0012\u001cHN\u0003\u00027o\u000511\u000f\u001e:fC6T\u0011\u0001O\u0001\u0005C.\\\u0017m\u0001\u0001\u00167mBU\u000bW._C\u0012<'.\u001c9tmfdx0!\u0002\u0002\f\u0005E\u0011qCA\u000f'\t\u0001A\bE\u0002>\u0001\nk\u0011A\u0010\u0006\u0003\u007fU\nQa\u001d;bO\u0016L!!\u0011 \u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\r\u0005\u000fD\t\u001a#vKW/aG\u001aLGn\u001c:vqnt\u00181AA\u0005\u0003\u001f\t)\"a\u0007\u000e\u0003UJ!!R\u001b\u0003\u0019\u0019\u000bg.\u00138TQ\u0006\u0004XM\r\u0019\u0011\u0005\u001dCE\u0002\u0001\u0003\u0006\u0013\u0002\u0011\rA\u0013\u0002\u0003\u0003F\n\"aS)\u0011\u00051{U\"A'\u000b\u00039\u000bQa]2bY\u0006L!\u0001U'\u0003\u000f9{G\u000f[5oOB\u0011AJU\u0005\u0003'6\u00131!\u00118z!\t9U\u000bB\u0003W\u0001\t\u0007!J\u0001\u0002BeA\u0011q\t\u0017\u0003\u00063\u0002\u0011\rA\u0013\u0002\u0003\u0003N\u0002\"aR.\u0005\u000bq\u0003!\u0019\u0001&\u0003\u0005\u0005#\u0004CA$_\t\u0015y\u0006A1\u0001K\u0005\t\tU\u0007\u0005\u0002HC\u0012)!\r\u0001b\u0001\u0015\n\u0011\u0011I\u000e\t\u0003\u000f\u0012$Q!\u001a\u0001C\u0002)\u0013!!Q\u001c\u0011\u0005\u001d;G!\u00025\u0001\u0005\u0004Q%AA!9!\t9%\u000eB\u0003l\u0001\t\u0007!J\u0001\u0002BsA\u0011q)\u001c\u0003\u0006]\u0002\u0011\rA\u0013\u0002\u0004\u0003F\u0002\u0004CA$q\t\u0015\t\bA1\u0001K\u0005\r\t\u0015'\r\t\u0003\u000fN$Q\u0001\u001e\u0001C\u0002)\u00131!Q\u00193!\t9e\u000fB\u0003x\u0001\t\u0007!JA\u0002BcM\u0002\"aR=\u0005\u000bi\u0004!\u0019\u0001&\u0003\u0007\u0005\u000bD\u0007\u0005\u0002Hy\u0012)Q\u0010\u0001b\u0001\u0015\n\u0019\u0011)M\u001b\u0011\u0005\u001d{HABA\u0001\u0001\t\u0007!JA\u0002BcY\u00022aRA\u0003\t\u0019\t9\u0001\u0001b\u0001\u0015\n\u0019\u0011)M\u001c\u0011\u0007\u001d\u000bY\u0001\u0002\u0004\u0002\u000e\u0001\u0011\rA\u0013\u0002\u0004\u0003FB\u0004cA$\u0002\u0012\u00111\u00111\u0003\u0001C\u0002)\u00131!Q\u0019:!\r9\u0015q\u0003\u0003\u0007\u00033\u0001!\u0019\u0001&\u0003\u0007\u0005\u0013\u0004\u0007E\u0002H\u0003;!a!a\b\u0001\u0005\u0004Q%!A(\u0002\riL\u0007\u000f]3s+\t\t)\u0003E\u000fM\u0003O1Ek\u0016.^A\u000e4\u0017\u000e\\8skb\\h0a\u0001\u0002\n\u0005=\u0011QCA\u000e\u0013\r\tI#\u0014\u0002\u000b\rVt7\r^5p]J\u0002\u0014a\u0002>jaB,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\t\u0005E\u0012Q\u0007\t\u001e\u0003g\u0001a\tV,[;\u0002\u001cg-\u001b7peVD8P`A\u0002\u0003\u0013\ty!!\u0006\u0002\u001c5\t1\u0007C\u0004\u0002\"\r\u0001\r!!\n\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/\u0006\u0002\u0002<A\u00191)!\u0010\n\u0007\u0005}RG\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u001d5ba\u0016,\u0012AQ\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0007=,H/\u0006\u0002\u0002LA)1)!\u0014\u0002\u001c%\u0019\u0011qJ\u001b\u0003\r=+H\u000f\\3u\u0003\rIg\u000eM\u000b\u0003\u0003+\u0002BaQA,\r&\u0019\u0011\u0011L\u001b\u0003\u000b%sG.\u001a;\u0002\t%t\u0007\u0007I\u0001\u0004S:\fTCAA1!\u0011\u0019\u0015q\u000b+\u0002\t%t\u0017\u0007I\u0001\u0004S:\u0014TCAA5!\u0011\u0019\u0015qK,\u0002\t%t'\u0007I\u0001\u0004S:\u001cTCAA9!\u0011\u0019\u0015q\u000b.\u0002\t%t7\u0007I\u0001\u0004S:$TCAA=!\u0011\u0019\u0015qK/\u0002\t%tG\u0007I\u0001\u0004S:,TCAAA!\u0011\u0019\u0015q\u000b1\u0002\t%tW\u0007I\u0001\u0004S:4TCAAE!\u0011\u0019\u0015qK2\u0002\t%tg\u0007I\u0001\u0004S:<TCAAI!\u0011\u0019\u0015q\u000b4\u0002\t%tw\u0007I\u0001\u0004S:DTCAAM!\u0011\u0019\u0015qK5\u0002\t%t\u0007\bI\u0001\u0004S:LTCAAQ!\u0011\u0019\u0015q\u000b7\u0002\t%t\u0017\bI\u0001\u0005S:\f\u0004'\u0006\u0002\u0002*B!1)a\u0016p\u0003\u0015Ig.\r\u0019!\u0003\u0011Ig.M\u0019\u0016\u0005\u0005E\u0006\u0003B\"\u0002XI\fQ!\u001b82c\u0001\nA!\u001b82eU\u0011\u0011\u0011\u0018\t\u0005\u0007\u0006]S/A\u0003j]F\u0012\u0004%\u0001\u0003j]F\u001aTCAAa!\u0011\u0019\u0015q\u000b=\u0002\u000b%t\u0017g\r\u0011\u0002\t%t\u0017\u0007N\u000b\u0003\u0003\u0013\u0004BaQA,w\u0006)\u0011N\\\u00195A\u0005!\u0011N\\\u00196+\t\t\t\u000e\u0005\u0003D\u0003/r\u0018!B5ocU\u0002\u0013\u0001B5ocY*\"!!7\u0011\u000b\r\u000b9&a\u0001\u0002\u000b%t\u0017G\u000e\u0011\u0002\t%t\u0017gN\u000b\u0003\u0003C\u0004RaQA,\u0003\u0013\tQ!\u001b82o\u0001\nA!\u001b82qU\u0011\u0011\u0011\u001e\t\u0006\u0007\u0006]\u0013qB\u0001\u0006S:\f\u0004\bI\u0001\u0005S:\f\u0014(\u0006\u0002\u0002rB)1)a\u0016\u0002\u0016\u0005)\u0011N\\\u0019:A\u0005Y1M]3bi\u0016dunZ5d)\u0011\tI0a@\u0011\u0007u\nY0C\u0002\u0002~z\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\b\u0005\u0003\u0001\u0004\u0019AA\u001e\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<GC\u0001B\u0004!\u0011\u0011IAa\u0005\u000e\u0005\t-!\u0002\u0002B\u0007\u0005\u001f\tA\u0001\\1oO*\u0011!\u0011C\u0001\u0005U\u00064\u0018-\u0003\u0003\u0003\u0016\t-!AB*ue&tw\r")
public class ZipWith20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O>
extends GraphStage<FanInShape20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O>> {
    private final Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> zipper;
    private final FanInShape20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> shape;
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

    public Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith20");
    }

    @Override
    public FanInShape20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith20$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith20$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith20$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith20 $outer;

            public int akka$stream$scaladsl$ZipWith20$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith20$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith20$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith20$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith20$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith20$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith20$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11()), this.grab(this.$outer.in12()), this.grab(this.$outer.in13()), this.grab(this.$outer.in14()), this.grab(this.$outer.in15()), this.grab(this.$outer.in16()), this.grab(this.$outer.in17()), this.grab(this.$outer.in18()), this.grab(this.$outer.in19())));
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
            }

            public /* synthetic */ ZipWith20 akka$stream$scaladsl$ZipWith20$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith20$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith20$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in12())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in13())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in14())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in15())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in16())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in17())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in18())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().in19())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$226 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith20$$anon$$pushAll();
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
        return "ZipWith20";
    }

    public ZipWith20(Function20<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape20("ZipWith20");
        this.in0 = ((FanInShape20)this.shape()).in0();
        this.in1 = ((FanInShape20)this.shape()).in1();
        this.in2 = ((FanInShape20)this.shape()).in2();
        this.in3 = ((FanInShape20)this.shape()).in3();
        this.in4 = ((FanInShape20)this.shape()).in4();
        this.in5 = ((FanInShape20)this.shape()).in5();
        this.in6 = ((FanInShape20)this.shape()).in6();
        this.in7 = ((FanInShape20)this.shape()).in7();
        this.in8 = ((FanInShape20)this.shape()).in8();
        this.in9 = ((FanInShape20)this.shape()).in9();
        this.in10 = ((FanInShape20)this.shape()).in10();
        this.in11 = ((FanInShape20)this.shape()).in11();
        this.in12 = ((FanInShape20)this.shape()).in12();
        this.in13 = ((FanInShape20)this.shape()).in13();
        this.in14 = ((FanInShape20)this.shape()).in14();
        this.in15 = ((FanInShape20)this.shape()).in15();
        this.in16 = ((FanInShape20)this.shape()).in16();
        this.in17 = ((FanInShape20)this.shape()).in17();
        this.in18 = ((FanInShape20)this.shape()).in18();
        this.in19 = ((FanInShape20)this.shape()).in19();
    }
}

