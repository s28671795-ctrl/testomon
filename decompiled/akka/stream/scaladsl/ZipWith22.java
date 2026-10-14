/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape22;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith22$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function22;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tmb\u0001\u0002\u001c8\u0001yB!\"!\u000e\u0001\u0005\u000b\u0007I\u0011AA\u001c\u0011)\ty\u0004\u0001B\u0001B\u0003%\u0011\u0011\b\u0005\b\u0003\u0003\u0002A\u0011AA\"\u0011\u001d\tY\u0005\u0001C!\u0003\u001bB\u0011\"!\u0016\u0001\u0005\u0004%\t%a\u0016\t\u000f\u0005e\u0003\u0001)A\u0005\r\"9\u00111\f\u0001\u0005\u0002\u0005u\u0003\"CA3\u0001\t\u0007I\u0011AA4\u0011!\ty\u0007\u0001Q\u0001\n\u0005%\u0004\"CA9\u0001\t\u0007I\u0011AA:\u0011!\t9\b\u0001Q\u0001\n\u0005U\u0004\"CA=\u0001\t\u0007I\u0011AA>\u0011!\ty\b\u0001Q\u0001\n\u0005u\u0004\"CAA\u0001\t\u0007I\u0011AAB\u0011!\t9\t\u0001Q\u0001\n\u0005\u0015\u0005\"CAE\u0001\t\u0007I\u0011AAF\u0011!\ty\t\u0001Q\u0001\n\u00055\u0005\"CAI\u0001\t\u0007I\u0011AAJ\u0011!\t9\n\u0001Q\u0001\n\u0005U\u0005\"CAM\u0001\t\u0007I\u0011AAN\u0011!\ty\n\u0001Q\u0001\n\u0005u\u0005\"CAQ\u0001\t\u0007I\u0011AAR\u0011!\t9\u000b\u0001Q\u0001\n\u0005\u0015\u0006\"CAU\u0001\t\u0007I\u0011AAV\u0011!\ty\u000b\u0001Q\u0001\n\u00055\u0006\"CAY\u0001\t\u0007I\u0011AAZ\u0011!\t9\f\u0001Q\u0001\n\u0005U\u0006\"CA]\u0001\t\u0007I\u0011AA^\u0011!\ty\f\u0001Q\u0001\n\u0005u\u0006\"CAa\u0001\t\u0007I\u0011AAb\u0011!\t9\r\u0001Q\u0001\n\u0005\u0015\u0007\"CAe\u0001\t\u0007I\u0011AAf\u0011!\ty\r\u0001Q\u0001\n\u00055\u0007\"CAi\u0001\t\u0007I\u0011AAj\u0011!\t9\u000e\u0001Q\u0001\n\u0005U\u0007\"CAm\u0001\t\u0007I\u0011AAn\u0011!\ty\u000e\u0001Q\u0001\n\u0005u\u0007\"CAq\u0001\t\u0007I\u0011AAr\u0011!\t9\u000f\u0001Q\u0001\n\u0005\u0015\b\"CAu\u0001\t\u0007I\u0011AAv\u0011!\ty\u000f\u0001Q\u0001\n\u00055\b\"CAy\u0001\t\u0007I\u0011AAz\u0011!\t9\u0010\u0001Q\u0001\n\u0005U\b\"CA}\u0001\t\u0007I\u0011AA~\u0011!\ty\u0010\u0001Q\u0001\n\u0005u\b\"\u0003B\u0001\u0001\t\u0007I\u0011\u0001B\u0002\u0011!\u00119\u0001\u0001Q\u0001\n\t\u0015\u0001\"\u0003B\u0005\u0001\t\u0007I\u0011\u0001B\u0006\u0011!\u0011y\u0001\u0001Q\u0001\n\t5\u0001\"\u0003B\t\u0001\t\u0007I\u0011\u0001B\n\u0011!\u00119\u0002\u0001Q\u0001\n\tU\u0001b\u0002B\r\u0001\u0011\u0005#1\u0004\u0005\b\u0005O\u0001A\u0011\tB\u0015\u0005%Q\u0016\u000e],ji\"\u0014$G\u0003\u00029s\u0005A1oY1mC\u0012\u001cHN\u0003\u0002;w\u000511\u000f\u001e:fC6T\u0011\u0001P\u0001\u0005C.\\\u0017m\u0001\u0001\u0016C}b\u0015\fX0cK\"\\g.\u001d;xuv\f\t!a\u0002\u0002\u000e\u0005M\u0011\u0011DA\u0010\u0003K\tY#!\r\u0014\u0005\u0001\u0001\u0005cA!E\r6\t!I\u0003\u0002Ds\u0005)1\u000f^1hK&\u0011QI\u0011\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007#I$I\u0015b[f,\u00193hU6\u00048O^=}\u007f\u0006\u0015\u00111BA\t\u0003/\ti\"a\t\u0002*\u0005=R\"A\u001d\n\u0005%K$\u0001\u0004$b]&s7\u000b[1qKJ\u0012\u0004CA&M\u0019\u0001!Q!\u0014\u0001C\u00029\u0013!!Q\u0019\u0012\u0005=+\u0006C\u0001)T\u001b\u0005\t&\"\u0001*\u0002\u000bM\u001c\u0017\r\\1\n\u0005Q\u000b&a\u0002(pi\"Lgn\u001a\t\u0003!ZK!aV)\u0003\u0007\u0005s\u0017\u0010\u0005\u0002L3\u0012)!\f\u0001b\u0001\u001d\n\u0011\u0011I\r\t\u0003\u0017r#Q!\u0018\u0001C\u00029\u0013!!Q\u001a\u0011\u0005-{F!\u00021\u0001\u0005\u0004q%AA!5!\tY%\rB\u0003d\u0001\t\u0007aJ\u0001\u0002BkA\u00111*\u001a\u0003\u0006M\u0002\u0011\rA\u0014\u0002\u0003\u0003Z\u0002\"a\u00135\u0005\u000b%\u0004!\u0019\u0001(\u0003\u0005\u0005;\u0004CA&l\t\u0015a\u0007A1\u0001O\u0005\t\t\u0005\b\u0005\u0002L]\u0012)q\u000e\u0001b\u0001\u001d\n\u0011\u0011)\u000f\t\u0003\u0017F$QA\u001d\u0001C\u00029\u00131!Q\u00191!\tYE\u000fB\u0003v\u0001\t\u0007aJA\u0002BcE\u0002\"aS<\u0005\u000ba\u0004!\u0019\u0001(\u0003\u0007\u0005\u000b$\u0007\u0005\u0002Lu\u0012)1\u0010\u0001b\u0001\u001d\n\u0019\u0011)M\u001a\u0011\u0005-kH!\u0002@\u0001\u0005\u0004q%aA!2iA\u00191*!\u0001\u0005\r\u0005\r\u0001A1\u0001O\u0005\r\t\u0015'\u000e\t\u0004\u0017\u0006\u001dAABA\u0005\u0001\t\u0007aJA\u0002BcY\u00022aSA\u0007\t\u0019\ty\u0001\u0001b\u0001\u001d\n\u0019\u0011)M\u001c\u0011\u0007-\u000b\u0019\u0002\u0002\u0004\u0002\u0016\u0001\u0011\rA\u0014\u0002\u0004\u0003FB\u0004cA&\u0002\u001a\u00111\u00111\u0004\u0001C\u00029\u00131!Q\u0019:!\rY\u0015q\u0004\u0003\u0007\u0003C\u0001!\u0019\u0001(\u0003\u0007\u0005\u0013\u0004\u0007E\u0002L\u0003K!a!a\n\u0001\u0005\u0004q%aA!3cA\u00191*a\u000b\u0005\r\u00055\u0002A1\u0001O\u0005\r\t%G\r\t\u0004\u0017\u0006EBABA\u001a\u0001\t\u0007aJA\u0001P\u0003\u0019Q\u0018\u000e\u001d9feV\u0011\u0011\u0011\b\t#!\u0006m\"\nW._C\u0012<'.\u001c9tmfdx0!\u0002\u0002\f\u0005E\u0011qCA\u000f\u0003G\tI#a\f\n\u0007\u0005u\u0012K\u0001\u0006Gk:\u001cG/[8oeI\nqA_5qa\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0005\u0003\u000b\nI\u0005\u0005\u0012\u0002H\u0001Q\u0005l\u00170bI\u001eTW\u000e]:wsr|\u0018QAA\u0006\u0003#\t9\"!\b\u0002$\u0005%\u0012qF\u0007\u0002o!9\u0011QG\u0002A\u0002\u0005e\u0012!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\u0011\u0011q\n\t\u0004\u000f\u0006E\u0013bAA*s\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003\u0019\u000baa\u001d5ba\u0016\u0004\u0013aA8viV\u0011\u0011q\f\t\u0006\u000f\u0006\u0005\u0014qF\u0005\u0004\u0003GJ$AB(vi2,G/A\u0002j]B*\"!!\u001b\u0011\t\u001d\u000bYGS\u0005\u0004\u0003[J$!B%oY\u0016$\u0018\u0001B5oa\u0001\n1!\u001b82+\t\t)\b\u0005\u0003H\u0003WB\u0016\u0001B5oc\u0001\n1!\u001b83+\t\ti\b\u0005\u0003H\u0003WZ\u0016\u0001B5oe\u0001\n1!\u001b84+\t\t)\t\u0005\u0003H\u0003Wr\u0016\u0001B5og\u0001\n1!\u001b85+\t\ti\t\u0005\u0003H\u0003W\n\u0017\u0001B5oi\u0001\n1!\u001b86+\t\t)\n\u0005\u0003H\u0003W\"\u0017\u0001B5ok\u0001\n1!\u001b87+\t\ti\n\u0005\u0003H\u0003W:\u0017\u0001B5om\u0001\n1!\u001b88+\t\t)\u000b\u0005\u0003H\u0003WR\u0017\u0001B5oo\u0001\n1!\u001b89+\t\ti\u000b\u0005\u0003H\u0003Wj\u0017\u0001B5oq\u0001\n1!\u001b8:+\t\t)\f\u0005\u0003H\u0003W\u0002\u0018\u0001B5os\u0001\nA!\u001b82aU\u0011\u0011Q\u0018\t\u0005\u000f\u0006-4/A\u0003j]F\u0002\u0004%\u0001\u0003j]F\nTCAAc!\u00119\u00151\u000e<\u0002\u000b%t\u0017'\r\u0011\u0002\t%t\u0017GM\u000b\u0003\u0003\u001b\u0004BaRA6s\u0006)\u0011N\\\u00193A\u0005!\u0011N\\\u00194+\t\t)\u000e\u0005\u0003H\u0003Wb\u0018!B5ocM\u0002\u0013\u0001B5ocQ*\"!!8\u0011\t\u001d\u000bYg`\u0001\u0006S:\fD\u0007I\u0001\u0005S:\fT'\u0006\u0002\u0002fB)q)a\u001b\u0002\u0006\u0005)\u0011N\\\u00196A\u0005!\u0011N\\\u00197+\t\ti\u000fE\u0003H\u0003W\nY!A\u0003j]F2\u0004%\u0001\u0003j]F:TCAA{!\u00159\u00151NA\t\u0003\u0015Ig.M\u001c!\u0003\u0011Ig.\r\u001d\u0016\u0005\u0005u\b#B$\u0002l\u0005]\u0011!B5oca\u0002\u0013\u0001B5oce*\"A!\u0002\u0011\u000b\u001d\u000bY'!\b\u0002\u000b%t\u0017'\u000f\u0011\u0002\t%t'\u0007M\u000b\u0003\u0005\u001b\u0001RaRA6\u0003G\tQ!\u001b83a\u0001\nA!\u001b83cU\u0011!Q\u0003\t\u0006\u000f\u0006-\u0014\u0011F\u0001\u0006S:\u0014\u0014\u0007I\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0003\u001e\t\r\u0002cA!\u0003 %\u0019!\u0011\u0005\"\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDqA!\n5\u0001\u0004\ty%A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\t\u0011Y\u0003\u0005\u0003\u0003.\t]RB\u0001B\u0018\u0015\u0011\u0011\tDa\r\u0002\t1\fgn\u001a\u0006\u0003\u0005k\tAA[1wC&!!\u0011\bB\u0018\u0005\u0019\u0019FO]5oO\u0002")
public class ZipWith22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O>
extends GraphStage<FanInShape22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O>> {
    private final Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> zipper;
    private final FanInShape22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> shape;
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
    private final Inlet<A22> in21;

    public Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith22");
    }

    @Override
    public FanInShape22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> shape() {
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

    public Inlet<A22> in21() {
        return this.in21;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith22$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith22$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith22$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith22 $outer;

            public int akka$stream$scaladsl$ZipWith22$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith22$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith22$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith22$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith22$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith22$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith22$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11()), this.grab(this.$outer.in12()), this.grab(this.$outer.in13()), this.grab(this.$outer.in14()), this.grab(this.$outer.in15()), this.grab(this.$outer.in16()), this.grab(this.$outer.in17()), this.grab(this.$outer.in18()), this.grab(this.$outer.in19()), this.grab(this.$outer.in20()), this.grab(this.$outer.in21())));
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
                this.pull(this.$outer.in21());
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
                this.pull(this.$outer.in21());
            }

            public /* synthetic */ ZipWith22 akka$stream$scaladsl$ZipWith22$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith22$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith22$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in12())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in13())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in14())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in15())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in16())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in17())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in18())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in19())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in20())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in21(), new InHandler(this){
                    private final /* synthetic */ $anon$271 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().in21())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$271 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith22$$anon$$pushAll();
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
        return "ZipWith22";
    }

    public ZipWith22(Function22<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, A22, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape22("ZipWith22");
        this.in0 = ((FanInShape22)this.shape()).in0();
        this.in1 = ((FanInShape22)this.shape()).in1();
        this.in2 = ((FanInShape22)this.shape()).in2();
        this.in3 = ((FanInShape22)this.shape()).in3();
        this.in4 = ((FanInShape22)this.shape()).in4();
        this.in5 = ((FanInShape22)this.shape()).in5();
        this.in6 = ((FanInShape22)this.shape()).in6();
        this.in7 = ((FanInShape22)this.shape()).in7();
        this.in8 = ((FanInShape22)this.shape()).in8();
        this.in9 = ((FanInShape22)this.shape()).in9();
        this.in10 = ((FanInShape22)this.shape()).in10();
        this.in11 = ((FanInShape22)this.shape()).in11();
        this.in12 = ((FanInShape22)this.shape()).in12();
        this.in13 = ((FanInShape22)this.shape()).in13();
        this.in14 = ((FanInShape22)this.shape()).in14();
        this.in15 = ((FanInShape22)this.shape()).in15();
        this.in16 = ((FanInShape22)this.shape()).in16();
        this.in17 = ((FanInShape22)this.shape()).in17();
        this.in18 = ((FanInShape22)this.shape()).in18();
        this.in19 = ((FanInShape22)this.shape()).in19();
        this.in20 = ((FanInShape22)this.shape()).in20();
        this.in21 = ((FanInShape22)this.shape()).in21();
    }
}

