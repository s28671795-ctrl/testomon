/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape8;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith8$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function8;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}b\u0001\u0002\u000e\u001c\u0001\tB\u0001\u0002\u0016\u0001\u0003\u0006\u0004%\t!\u0016\u0005\t3\u0002\u0011\t\u0011)A\u0005-\")!\f\u0001C\u00017\")q\f\u0001C!A\"9A\r\u0001b\u0001\n\u0003*\u0007B\u00024\u0001A\u0003%!\u0006C\u0003h\u0001\u0011\u0005\u0001\u000eC\u0004m\u0001\t\u0007I\u0011A7\t\rE\u0004\u0001\u0015!\u0003o\u0011\u001d\u0011\bA1A\u0005\u0002MDa!\u001e\u0001!\u0002\u0013!\bb\u0002<\u0001\u0005\u0004%\ta\u001e\u0005\u0007s\u0002\u0001\u000b\u0011\u0002=\t\u000fi\u0004!\u0019!C\u0001w\"1Q\u0010\u0001Q\u0001\nqDqA \u0001C\u0002\u0013\u0005q\u0010\u0003\u0005\u0002\u0004\u0001\u0001\u000b\u0011BA\u0001\u0011%\t)\u0001\u0001b\u0001\n\u0003\t9\u0001\u0003\u0005\u0002\f\u0001\u0001\u000b\u0011BA\u0005\u0011%\ti\u0001\u0001b\u0001\n\u0003\ty\u0001\u0003\u0005\u0002\u0014\u0001\u0001\u000b\u0011BA\t\u0011%\t)\u0002\u0001b\u0001\n\u0003\t9\u0002\u0003\u0005\u0002\u001c\u0001\u0001\u000b\u0011BA\r\u0011\u001d\ti\u0002\u0001C!\u0003?Aq!a\u000b\u0001\t\u0003\niC\u0001\u0005[SB<\u0016\u000e\u001e59\u0015\taR$\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\tqr$\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002A\u0005!\u0011m[6b\u0007\u0001)\"b\t\u0019>\u0001\u000e3\u0015\nT(S'\t\u0001A\u0005E\u0002&Q)j\u0011A\n\u0006\u0003Ou\tQa\u001d;bO\u0016L!!\u000b\u0014\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rE\u0006,Y9btHQ#I\u0017:\u000bV\"A\u000f\n\u00055j\"a\u0003$b]&s7\u000b[1qKb\u0002\"a\f\u0019\r\u0001\u0011)\u0011\u0007\u0001b\u0001e\t\u0011\u0011)M\t\u0003ge\u0002\"\u0001N\u001c\u000e\u0003UR\u0011AN\u0001\u0006g\u000e\fG.Y\u0005\u0003qU\u0012qAT8uQ&tw\r\u0005\u00025u%\u00111(\u000e\u0002\u0004\u0003:L\bCA\u0018>\t\u0015q\u0004A1\u00013\u0005\t\t%\u0007\u0005\u00020\u0001\u0012)\u0011\t\u0001b\u0001e\t\u0011\u0011i\r\t\u0003_\r#Q\u0001\u0012\u0001C\u0002I\u0012!!\u0011\u001b\u0011\u0005=2E!B$\u0001\u0005\u0004\u0011$AA!6!\ty\u0013\nB\u0003K\u0001\t\u0007!G\u0001\u0002BmA\u0011q\u0006\u0014\u0003\u0006\u001b\u0002\u0011\rA\r\u0002\u0003\u0003^\u0002\"aL(\u0005\u000bA\u0003!\u0019\u0001\u001a\u0003\u0005\u0005C\u0004CA\u0018S\t\u0015\u0019\u0006A1\u00013\u0005\u0005y\u0015A\u0002>jaB,'/F\u0001W!-!tK\f\u001f@\u0005\u0016C5JT)\n\u0005a+$!\u0003$v]\u000e$\u0018n\u001c89\u0003\u001dQ\u0018\u000e\u001d9fe\u0002\na\u0001P5oSRtDC\u0001/_!-i\u0006A\f\u001f@\u0005\u0016C5JT)\u000e\u0003mAQ\u0001V\u0002A\u0002Y\u000b\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005\t\u0007CA\u0016c\u0013\t\u0019WD\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u001d5ba\u0016,\u0012AK\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0007=,H/F\u0001j!\rY#.U\u0005\u0003Wv\u0011aaT;uY\u0016$\u0018aA5oaU\ta\u000eE\u0002,_:J!\u0001]\u000f\u0003\u000b%sG.\u001a;\u0002\t%t\u0007\u0007I\u0001\u0004S:\fT#\u0001;\u0011\u0007-zG(\u0001\u0003j]F\u0002\u0013aA5oeU\t\u0001\u0010E\u0002,_~\nA!\u001b83A\u0005\u0019\u0011N\\\u001a\u0016\u0003q\u00042aK8C\u0003\u0011Ign\r\u0011\u0002\u0007%tG'\u0006\u0002\u0002\u0002A\u00191f\\#\u0002\t%tG\u0007I\u0001\u0004S:,TCAA\u0005!\rYs\u000eS\u0001\u0005S:,\u0004%A\u0002j]Z*\"!!\u0005\u0011\u0007-z7*\u0001\u0003j]Z\u0002\u0013aA5ooU\u0011\u0011\u0011\u0004\t\u0004W=t\u0015\u0001B5oo\u0001\n1b\u0019:fCR,Gj\\4jGR!\u0011\u0011EA\u0014!\r)\u00131E\u0005\u0004\u0003K1#aD$sCBD7\u000b^1hK2{w-[2\t\r\u0005%\u0002\u00041\u0001b\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<GCAA\u0018!\u0011\t\t$a\u000f\u000e\u0005\u0005M\"\u0002BA\u001b\u0003o\tA\u0001\\1oO*\u0011\u0011\u0011H\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002>\u0005M\"AB*ue&tw\r")
public class ZipWith8<A1, A2, A3, A4, A5, A6, A7, A8, O>
extends GraphStage<FanInShape8<A1, A2, A3, A4, A5, A6, A7, A8, O>> {
    private final Function8<A1, A2, A3, A4, A5, A6, A7, A8, O> zipper;
    private final FanInShape8<A1, A2, A3, A4, A5, A6, A7, A8, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;
    private final Inlet<A8> in7;

    public Function8<A1, A2, A3, A4, A5, A6, A7, A8, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith8");
    }

    @Override
    public FanInShape8<A1, A2, A3, A4, A5, A6, A7, A8, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith8$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith8$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith8$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith8 $outer;

            public int akka$stream$scaladsl$ZipWith8$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith8$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith8$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith8$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith8$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith8$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith8$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7())));
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
            }

            public /* synthetic */ ZipWith8 akka$stream$scaladsl$ZipWith8$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith8$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith8$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith8$$anon$$pushAll();
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
        return "ZipWith8";
    }

    public ZipWith8(Function8<A1, A2, A3, A4, A5, A6, A7, A8, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape8("ZipWith8");
        this.in0 = ((FanInShape8)this.shape()).in0();
        this.in1 = ((FanInShape8)this.shape()).in1();
        this.in2 = ((FanInShape8)this.shape()).in2();
        this.in3 = ((FanInShape8)this.shape()).in3();
        this.in4 = ((FanInShape8)this.shape()).in4();
        this.in5 = ((FanInShape8)this.shape()).in5();
        this.in6 = ((FanInShape8)this.shape()).in6();
        this.in7 = ((FanInShape8)this.shape()).in7();
    }
}

