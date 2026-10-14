/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape19;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith19$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function19;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\u0015a\u0001\u0002\u00192\u0001aB!\"a\u0006\u0001\u0005\u000b\u0007I\u0011AA\r\u0011)\t\t\u0003\u0001B\u0001B\u0003%\u00111\u0004\u0005\b\u0003G\u0001A\u0011AA\u0013\u0011\u001d\ti\u0003\u0001C!\u0003_A\u0011\"a\u000e\u0001\u0005\u0004%\t%!\u000f\t\u000f\u0005m\u0002\u0001)A\u0005\u0001\"9\u0011Q\b\u0001\u0005\u0002\u0005}\u0002\"CA$\u0001\t\u0007I\u0011AA%\u0011!\t\t\u0006\u0001Q\u0001\n\u0005-\u0003\"CA*\u0001\t\u0007I\u0011AA+\u0011!\tI\u0006\u0001Q\u0001\n\u0005]\u0003\"CA.\u0001\t\u0007I\u0011AA/\u0011!\t\t\u0007\u0001Q\u0001\n\u0005}\u0003\"CA2\u0001\t\u0007I\u0011AA3\u0011!\tI\u0007\u0001Q\u0001\n\u0005\u001d\u0004\"CA6\u0001\t\u0007I\u0011AA7\u0011!\t\t\b\u0001Q\u0001\n\u0005=\u0004\"CA:\u0001\t\u0007I\u0011AA;\u0011!\tI\b\u0001Q\u0001\n\u0005]\u0004\"CA>\u0001\t\u0007I\u0011AA?\u0011!\t\t\t\u0001Q\u0001\n\u0005}\u0004\"CAB\u0001\t\u0007I\u0011AAC\u0011!\tI\t\u0001Q\u0001\n\u0005\u001d\u0005\"CAF\u0001\t\u0007I\u0011AAG\u0011!\t\t\n\u0001Q\u0001\n\u0005=\u0005\"CAJ\u0001\t\u0007I\u0011AAK\u0011!\tI\n\u0001Q\u0001\n\u0005]\u0005\"CAN\u0001\t\u0007I\u0011AAO\u0011!\t\t\u000b\u0001Q\u0001\n\u0005}\u0005\"CAR\u0001\t\u0007I\u0011AAS\u0011!\tI\u000b\u0001Q\u0001\n\u0005\u001d\u0006\"CAV\u0001\t\u0007I\u0011AAW\u0011!\t\t\f\u0001Q\u0001\n\u0005=\u0006\"CAZ\u0001\t\u0007I\u0011AA[\u0011!\tI\f\u0001Q\u0001\n\u0005]\u0006\"CA^\u0001\t\u0007I\u0011AA_\u0011!\t\t\r\u0001Q\u0001\n\u0005}\u0006\"CAb\u0001\t\u0007I\u0011AAc\u0011!\tI\r\u0001Q\u0001\n\u0005\u001d\u0007\"CAf\u0001\t\u0007I\u0011AAg\u0011!\t\t\u000e\u0001Q\u0001\n\u0005=\u0007\"CAj\u0001\t\u0007I\u0011AAk\u0011!\tI\u000e\u0001Q\u0001\n\u0005]\u0007\"CAn\u0001\t\u0007I\u0011AAo\u0011!\t\t\u000f\u0001Q\u0001\n\u0005}\u0007bBAr\u0001\u0011\u0005\u0013Q\u001d\u0005\b\u0003c\u0004A\u0011IAz\u0005%Q\u0016\u000e],ji\"\f\u0014H\u0003\u00023g\u0005A1oY1mC\u0012\u001cHN\u0003\u00025k\u000511\u000f\u001e:fC6T\u0011AN\u0001\u0005C.\\\u0017m\u0001\u0001\u00163e25KV-]?\n,\u0007n\u001b8ri^TX0!\u0001\u0002\b\u00055\u00111C\n\u0003\u0001i\u00022a\u000f A\u001b\u0005a$BA\u001f4\u0003\u0015\u0019H/Y4f\u0013\tyDH\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004\u0012$\u0011\"E%VC6LX1eO*l\u0007o\u001d<zy~\f)!a\u0003\u0002\u00125\t1'\u0003\u0002Dg\taa)\u00198J]NC\u0017\r]32sA\u0011QI\u0012\u0007\u0001\t\u00159\u0005A1\u0001I\u0005\t\t\u0015'\u0005\u0002J\u001fB\u0011!*T\u0007\u0002\u0017*\tA*A\u0003tG\u0006d\u0017-\u0003\u0002O\u0017\n9aj\u001c;iS:<\u0007C\u0001&Q\u0013\t\t6JA\u0002B]f\u0004\"!R*\u0005\u000bQ\u0003!\u0019\u0001%\u0003\u0005\u0005\u0013\u0004CA#W\t\u00159\u0006A1\u0001I\u0005\t\t5\u0007\u0005\u0002F3\u0012)!\f\u0001b\u0001\u0011\n\u0011\u0011\t\u000e\t\u0003\u000br#Q!\u0018\u0001C\u0002!\u0013!!Q\u001b\u0011\u0005\u0015{F!\u00021\u0001\u0005\u0004A%AA!7!\t)%\rB\u0003d\u0001\t\u0007\u0001J\u0001\u0002BoA\u0011Q)\u001a\u0003\u0006M\u0002\u0011\r\u0001\u0013\u0002\u0003\u0003b\u0002\"!\u00125\u0005\u000b%\u0004!\u0019\u0001%\u0003\u0005\u0005K\u0004CA#l\t\u0015a\u0007A1\u0001I\u0005\r\t\u0015\u0007\r\t\u0003\u000b:$Qa\u001c\u0001C\u0002!\u00131!Q\u00192!\t)\u0015\u000fB\u0003s\u0001\t\u0007\u0001JA\u0002BcI\u0002\"!\u0012;\u0005\u000bU\u0004!\u0019\u0001%\u0003\u0007\u0005\u000b4\u0007\u0005\u0002Fo\u0012)\u0001\u0010\u0001b\u0001\u0011\n\u0019\u0011)\r\u001b\u0011\u0005\u0015SH!B>\u0001\u0005\u0004A%aA!2kA\u0011Q) \u0003\u0006}\u0002\u0011\r\u0001\u0013\u0002\u0004\u0003F2\u0004cA#\u0002\u0002\u00111\u00111\u0001\u0001C\u0002!\u00131!Q\u00198!\r)\u0015q\u0001\u0003\u0007\u0003\u0013\u0001!\u0019\u0001%\u0003\u0007\u0005\u000b\u0004\bE\u0002F\u0003\u001b!a!a\u0004\u0001\u0005\u0004A%aA!2sA\u0019Q)a\u0005\u0005\r\u0005U\u0001A1\u0001I\u0005\u0005y\u0015A\u0002>jaB,'/\u0006\u0002\u0002\u001cAQ\"*!\bE%VC6LX1eO*l\u0007o\u001d<zy~\f)!a\u0003\u0002\u0012%\u0019\u0011qD&\u0003\u0015\u0019+hn\u0019;j_:\f\u0014(A\u0004{SB\u0004XM\u001d\u0011\u0002\rqJg.\u001b;?)\u0011\t9#a\u000b\u00115\u0005%\u0002\u0001\u0012*V1ns\u0016\rZ4k[B\u001ch/\u001f?\u0000\u0003\u000b\tY!!\u0005\u000e\u0003EBq!a\u0006\u0004\u0001\u0004\tY\"A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\"!!\r\u0011\u0007\u0005\u000b\u0019$C\u0002\u00026M\u0012!\"\u0011;ue&\u0014W\u000f^3t\u0003\u0015\u0019\b.\u00199f+\u0005\u0001\u0015AB:iCB,\u0007%A\u0002pkR,\"!!\u0011\u0011\u000b\u0005\u000b\u0019%!\u0005\n\u0007\u0005\u00153G\u0001\u0004PkRdW\r^\u0001\u0004S:\u0004TCAA&!\u0011\t\u0015Q\n#\n\u0007\u0005=3GA\u0003J]2,G/\u0001\u0003j]B\u0002\u0013aA5ocU\u0011\u0011q\u000b\t\u0005\u0003\u00065#+\u0001\u0003j]F\u0002\u0013aA5oeU\u0011\u0011q\f\t\u0005\u0003\u00065S+\u0001\u0003j]J\u0002\u0013aA5ogU\u0011\u0011q\r\t\u0005\u0003\u00065\u0003,\u0001\u0003j]N\u0002\u0013aA5oiU\u0011\u0011q\u000e\t\u0005\u0003\u000653,\u0001\u0003j]R\u0002\u0013aA5okU\u0011\u0011q\u000f\t\u0005\u0003\u00065c,\u0001\u0003j]V\u0002\u0013aA5omU\u0011\u0011q\u0010\t\u0005\u0003\u00065\u0013-\u0001\u0003j]Z\u0002\u0013aA5ooU\u0011\u0011q\u0011\t\u0005\u0003\u00065C-\u0001\u0003j]^\u0002\u0013aA5oqU\u0011\u0011q\u0012\t\u0005\u0003\u00065s-\u0001\u0003j]b\u0002\u0013aA5osU\u0011\u0011q\u0013\t\u0005\u0003\u00065#.\u0001\u0003j]f\u0002\u0013\u0001B5ocA*\"!a(\u0011\t\u0005\u000bi%\\\u0001\u0006S:\f\u0004\u0007I\u0001\u0005S:\f\u0014'\u0006\u0002\u0002(B!\u0011)!\u0014q\u0003\u0015Ig.M\u0019!\u0003\u0011Ig.\r\u001a\u0016\u0005\u0005=\u0006\u0003B!\u0002NM\fQ!\u001b82e\u0001\nA!\u001b82gU\u0011\u0011q\u0017\t\u0005\u0003\u00065c/A\u0003j]F\u001a\u0004%\u0001\u0003j]F\"TCAA`!\u0011\t\u0015QJ=\u0002\u000b%t\u0017\u0007\u000e\u0011\u0002\t%t\u0017'N\u000b\u0003\u0003\u000f\u0004B!QA'y\u0006)\u0011N\\\u00196A\u0005!\u0011N\\\u00197+\t\ty\r\u0005\u0003B\u0003\u001bz\u0018!B5ocY\u0002\u0013\u0001B5oc]*\"!a6\u0011\u000b\u0005\u000bi%!\u0002\u0002\u000b%t\u0017g\u000e\u0011\u0002\t%t\u0017\u0007O\u000b\u0003\u0003?\u0004R!QA'\u0003\u0017\tQ!\u001b82q\u0001\n1b\u0019:fCR,Gj\\4jGR!\u0011q]Aw!\rY\u0014\u0011^\u0005\u0004\u0003Wd$aD$sCBD7\u000b^1hK2{w-[2\t\u000f\u0005=h\u00061\u0001\u00022\u0005\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u0002vB!\u0011q\u001fB\u0001\u001b\t\tIP\u0003\u0003\u0002|\u0006u\u0018\u0001\u00027b]\u001eT!!a@\u0002\t)\fg/Y\u0005\u0005\u0005\u0007\tIP\u0001\u0004TiJLgn\u001a")
public class ZipWith19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O>
extends GraphStage<FanInShape19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O>> {
    private final Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> zipper;
    private final FanInShape19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> shape;
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

    public Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith19");
    }

    @Override
    public FanInShape19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith19$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith19$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith19$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith19 $outer;

            public int akka$stream$scaladsl$ZipWith19$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith19$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith19$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith19$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith19$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith19$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith19$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11()), this.grab(this.$outer.in12()), this.grab(this.$outer.in13()), this.grab(this.$outer.in14()), this.grab(this.$outer.in15()), this.grab(this.$outer.in16()), this.grab(this.$outer.in17()), this.grab(this.$outer.in18())));
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
            }

            public /* synthetic */ ZipWith19 akka$stream$scaladsl$ZipWith19$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith19$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith19$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in12())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in13())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in14())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in15())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in16())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in17())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().in18())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$205 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith19$$anon$$pushAll();
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
        return "ZipWith19";
    }

    public ZipWith19(Function19<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape19("ZipWith19");
        this.in0 = ((FanInShape19)this.shape()).in0();
        this.in1 = ((FanInShape19)this.shape()).in1();
        this.in2 = ((FanInShape19)this.shape()).in2();
        this.in3 = ((FanInShape19)this.shape()).in3();
        this.in4 = ((FanInShape19)this.shape()).in4();
        this.in5 = ((FanInShape19)this.shape()).in5();
        this.in6 = ((FanInShape19)this.shape()).in6();
        this.in7 = ((FanInShape19)this.shape()).in7();
        this.in8 = ((FanInShape19)this.shape()).in8();
        this.in9 = ((FanInShape19)this.shape()).in9();
        this.in10 = ((FanInShape19)this.shape()).in10();
        this.in11 = ((FanInShape19)this.shape()).in11();
        this.in12 = ((FanInShape19)this.shape()).in12();
        this.in13 = ((FanInShape19)this.shape()).in13();
        this.in14 = ((FanInShape19)this.shape()).in14();
        this.in15 = ((FanInShape19)this.shape()).in15();
        this.in16 = ((FanInShape19)this.shape()).in16();
        this.in17 = ((FanInShape19)this.shape()).in17();
        this.in18 = ((FanInShape19)this.shape()).in18();
    }
}

