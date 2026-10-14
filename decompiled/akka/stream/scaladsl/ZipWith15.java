/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape15;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith15$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function15;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005uf\u0001\u0002\u0015*\u0001AB\u0001b\u001e\u0001\u0003\u0006\u0004%\t\u0001\u001f\u0005\ty\u0002\u0011\t\u0011)A\u0005s\")Q\u0010\u0001C\u0001}\"9\u0011Q\u0001\u0001\u0005B\u0005\u001d\u0001\"CA\b\u0001\t\u0007I\u0011IA\t\u0011\u001d\t\u0019\u0002\u0001Q\u0001\naBq!!\u0006\u0001\t\u0003\t9\u0002C\u0005\u0002 \u0001\u0011\r\u0011\"\u0001\u0002\"!A\u0011\u0011\u0006\u0001!\u0002\u0013\t\u0019\u0003C\u0005\u0002,\u0001\u0011\r\u0011\"\u0001\u0002.!A\u0011\u0011\u0007\u0001!\u0002\u0013\ty\u0003C\u0005\u00024\u0001\u0011\r\u0011\"\u0001\u00026!A\u0011\u0011\b\u0001!\u0002\u0013\t9\u0004C\u0005\u0002<\u0001\u0011\r\u0011\"\u0001\u0002>!A\u0011\u0011\t\u0001!\u0002\u0013\ty\u0004C\u0005\u0002D\u0001\u0011\r\u0011\"\u0001\u0002F!A\u0011\u0011\n\u0001!\u0002\u0013\t9\u0005C\u0005\u0002L\u0001\u0011\r\u0011\"\u0001\u0002N!A\u0011\u0011\u000b\u0001!\u0002\u0013\ty\u0005C\u0005\u0002T\u0001\u0011\r\u0011\"\u0001\u0002V!A\u0011\u0011\f\u0001!\u0002\u0013\t9\u0006C\u0005\u0002\\\u0001\u0011\r\u0011\"\u0001\u0002^!A\u0011\u0011\r\u0001!\u0002\u0013\ty\u0006C\u0005\u0002d\u0001\u0011\r\u0011\"\u0001\u0002f!A\u0011\u0011\u000e\u0001!\u0002\u0013\t9\u0007C\u0005\u0002l\u0001\u0011\r\u0011\"\u0001\u0002n!A\u0011\u0011\u000f\u0001!\u0002\u0013\ty\u0007C\u0005\u0002t\u0001\u0011\r\u0011\"\u0001\u0002v!A\u0011\u0011\u0010\u0001!\u0002\u0013\t9\bC\u0005\u0002|\u0001\u0011\r\u0011\"\u0001\u0002~!A\u0011\u0011\u0011\u0001!\u0002\u0013\ty\bC\u0005\u0002\u0004\u0002\u0011\r\u0011\"\u0001\u0002\u0006\"A\u0011\u0011\u0012\u0001!\u0002\u0013\t9\tC\u0005\u0002\f\u0002\u0011\r\u0011\"\u0001\u0002\u000e\"A\u0011\u0011\u0013\u0001!\u0002\u0013\ty\tC\u0005\u0002\u0014\u0002\u0011\r\u0011\"\u0001\u0002\u0016\"A\u0011\u0011\u0014\u0001!\u0002\u0013\t9\nC\u0004\u0002\u001c\u0002!\t%!(\t\u000f\u0005%\u0006\u0001\"\u0011\u0002,\nI!,\u001b9XSRD\u0017'\u000e\u0006\u0003U-\n\u0001b]2bY\u0006$7\u000f\u001c\u0006\u0003Y5\naa\u001d;sK\u0006l'\"\u0001\u0018\u0002\t\u0005\\7.Y\u0002\u0001+E\tdh\u0013(R)^SV\fY2gS2|'/^\n\u0003\u0001I\u00022a\r\u001c9\u001b\u0005!$BA\u001b,\u0003\u0015\u0019H/Y4f\u0013\t9DG\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004\"#\u000f\u001e=\u00156\u00036KV-]?\n,\u0007n\u001b8ri6\t1&\u0003\u0002<W\taa)\u00198J]NC\u0017\r]32kA\u0011QH\u0010\u0007\u0001\t\u0015y\u0004A1\u0001A\u0005\t\t\u0015'\u0005\u0002B\u000fB\u0011!)R\u0007\u0002\u0007*\tA)A\u0003tG\u0006d\u0017-\u0003\u0002G\u0007\n9aj\u001c;iS:<\u0007C\u0001\"I\u0013\tI5IA\u0002B]f\u0004\"!P&\u0005\u000b1\u0003!\u0019\u0001!\u0003\u0005\u0005\u0013\u0004CA\u001fO\t\u0015y\u0005A1\u0001A\u0005\t\t5\u0007\u0005\u0002>#\u0012)!\u000b\u0001b\u0001\u0001\n\u0011\u0011\t\u000e\t\u0003{Q#Q!\u0016\u0001C\u0002\u0001\u0013!!Q\u001b\u0011\u0005u:F!\u0002-\u0001\u0005\u0004\u0001%AA!7!\ti$\fB\u0003\\\u0001\t\u0007\u0001I\u0001\u0002BoA\u0011Q(\u0018\u0003\u0006=\u0002\u0011\r\u0001\u0011\u0002\u0003\u0003b\u0002\"!\u00101\u0005\u000b\u0005\u0004!\u0019\u0001!\u0003\u0005\u0005K\u0004CA\u001fd\t\u0015!\u0007A1\u0001A\u0005\r\t\u0015\u0007\r\t\u0003{\u0019$Qa\u001a\u0001C\u0002\u0001\u00131!Q\u00192!\ti\u0014\u000eB\u0003k\u0001\t\u0007\u0001IA\u0002BcI\u0002\"!\u00107\u0005\u000b5\u0004!\u0019\u0001!\u0003\u0007\u0005\u000b4\u0007\u0005\u0002>_\u0012)\u0001\u000f\u0001b\u0001\u0001\n\u0019\u0011)\r\u001b\u0011\u0005u\u0012H!B:\u0001\u0005\u0004\u0001%aA!2kA\u0011Q(\u001e\u0003\u0006m\u0002\u0011\r\u0001\u0011\u0002\u0002\u001f\u00061!0\u001b9qKJ,\u0012!\u001f\t\u0013\u0005jd$*\u0014)T-fcvLY3iW:\fH/\u0003\u0002|\u0007\nQa)\u001e8di&|g.M\u001b\u0002\u000fiL\u0007\u000f]3sA\u00051A(\u001b8jiz\"2a`A\u0002!M\t\t\u0001\u0001\u001fK\u001bB\u001bf+\u0017/`E\u0016D7N\\9u\u001b\u0005I\u0003\"B<\u0004\u0001\u0004I\u0018!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\u0011\u0011\u0011\u0002\t\u0004s\u0005-\u0011bAA\u0007W\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003a\naa\u001d5ba\u0016\u0004\u0013aA8viV\u0011\u0011\u0011\u0004\t\u0005s\u0005mA/C\u0002\u0002\u001e-\u0012aaT;uY\u0016$\u0018aA5oaU\u0011\u00111\u0005\t\u0005s\u0005\u0015B(C\u0002\u0002(-\u0012Q!\u00138mKR\fA!\u001b81A\u0005\u0019\u0011N\\\u0019\u0016\u0005\u0005=\u0002\u0003B\u001d\u0002&)\u000bA!\u001b82A\u0005\u0019\u0011N\u001c\u001a\u0016\u0005\u0005]\u0002\u0003B\u001d\u0002&5\u000bA!\u001b83A\u0005\u0019\u0011N\\\u001a\u0016\u0005\u0005}\u0002\u0003B\u001d\u0002&A\u000bA!\u001b84A\u0005\u0019\u0011N\u001c\u001b\u0016\u0005\u0005\u001d\u0003\u0003B\u001d\u0002&M\u000bA!\u001b85A\u0005\u0019\u0011N\\\u001b\u0016\u0005\u0005=\u0003\u0003B\u001d\u0002&Y\u000bA!\u001b86A\u0005\u0019\u0011N\u001c\u001c\u0016\u0005\u0005]\u0003\u0003B\u001d\u0002&e\u000bA!\u001b87A\u0005\u0019\u0011N\\\u001c\u0016\u0005\u0005}\u0003\u0003B\u001d\u0002&q\u000bA!\u001b88A\u0005\u0019\u0011N\u001c\u001d\u0016\u0005\u0005\u001d\u0004\u0003B\u001d\u0002&}\u000bA!\u001b89A\u0005\u0019\u0011N\\\u001d\u0016\u0005\u0005=\u0004\u0003B\u001d\u0002&\t\fA!\u001b8:A\u0005!\u0011N\\\u00191+\t\t9\b\u0005\u0003:\u0003K)\u0017!B5ocA\u0002\u0013\u0001B5ocE*\"!a \u0011\te\n)\u0003[\u0001\u0006S:\f\u0014\u0007I\u0001\u0005S:\f$'\u0006\u0002\u0002\bB!\u0011(!\nl\u0003\u0015Ig.\r\u001a!\u0003\u0011Ig.M\u001a\u0016\u0005\u0005=\u0005\u0003B\u001d\u0002&9\fQ!\u001b82g\u0001\nA!\u001b82iU\u0011\u0011q\u0013\t\u0005s\u0005\u0015\u0012/A\u0003j]F\"\u0004%A\u0006de\u0016\fG/\u001a'pO&\u001cG\u0003BAP\u0003K\u00032aMAQ\u0013\r\t\u0019\u000b\u000e\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"9\u0011q\u0015\u0014A\u0002\u0005%\u0011aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u00055\u0006\u0003BAX\u0003sk!!!-\u000b\t\u0005M\u0016QW\u0001\u0005Y\u0006twM\u0003\u0002\u00028\u0006!!.\u0019<b\u0013\u0011\tY,!-\u0003\rM#(/\u001b8h\u0001")
public class ZipWith15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O>
extends GraphStage<FanInShape15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O>> {
    private final Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> zipper;
    private final FanInShape15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> shape;
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

    public Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith15");
    }

    @Override
    public FanInShape15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith15$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith15$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith15$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith15 $outer;

            public int akka$stream$scaladsl$ZipWith15$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith15$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith15$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith15$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith15$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith15$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith15$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11()), this.grab(this.$outer.in12()), this.grab(this.$outer.in13()), this.grab(this.$outer.in14())));
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
            }

            public /* synthetic */ ZipWith15 akka$stream$scaladsl$ZipWith15$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith15$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith15$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in12())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in13())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().in14())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$131 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith15$$anon$$pushAll();
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
        return "ZipWith15";
    }

    public ZipWith15(Function15<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape15("ZipWith15");
        this.in0 = ((FanInShape15)this.shape()).in0();
        this.in1 = ((FanInShape15)this.shape()).in1();
        this.in2 = ((FanInShape15)this.shape()).in2();
        this.in3 = ((FanInShape15)this.shape()).in3();
        this.in4 = ((FanInShape15)this.shape()).in4();
        this.in5 = ((FanInShape15)this.shape()).in5();
        this.in6 = ((FanInShape15)this.shape()).in6();
        this.in7 = ((FanInShape15)this.shape()).in7();
        this.in8 = ((FanInShape15)this.shape()).in8();
        this.in9 = ((FanInShape15)this.shape()).in9();
        this.in10 = ((FanInShape15)this.shape()).in10();
        this.in11 = ((FanInShape15)this.shape()).in11();
        this.in12 = ((FanInShape15)this.shape()).in12();
        this.in13 = ((FanInShape15)this.shape()).in13();
        this.in14 = ((FanInShape15)this.shape()).in14();
    }
}

