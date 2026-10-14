/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape5;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith5$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function5;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%a\u0001\u0002\u000b\u0016\u0001qA\u0001\"\u0012\u0001\u0003\u0006\u0004%\tA\u0012\u0005\t\u0015\u0002\u0011\t\u0011)A\u0005\u000f\")1\n\u0001C\u0001\u0019\")\u0001\u000b\u0001C!#\"9Q\u000b\u0001b\u0001\n\u00032\u0006BB,\u0001A\u0003%A\u0005C\u0003Y\u0001\u0011\u0005\u0011\fC\u0004^\u0001\t\u0007I\u0011\u00010\t\r\t\u0004\u0001\u0015!\u0003`\u0011\u001d\u0019\u0007A1A\u0005\u0002\u0011DaA\u001a\u0001!\u0002\u0013)\u0007bB4\u0001\u0005\u0004%\t\u0001\u001b\u0005\u0007U\u0002\u0001\u000b\u0011B5\t\u000f-\u0004!\u0019!C\u0001Y\"1a\u000e\u0001Q\u0001\n5Dqa\u001c\u0001C\u0002\u0013\u0005\u0001\u000f\u0003\u0004s\u0001\u0001\u0006I!\u001d\u0005\u0006g\u0002!\t\u0005\u001e\u0005\u0006u\u0002!\te\u001f\u0002\t5&\u0004x+\u001b;ik)\u0011acF\u0001\tg\u000e\fG.\u00193tY*\u0011\u0001$G\u0001\u0007gR\u0014X-Y7\u000b\u0003i\tA!Y6lC\u000e\u0001QcB\u000f+oij\u0004iQ\n\u0003\u0001y\u00012a\b\u0012%\u001b\u0005\u0001#BA\u0011\u0018\u0003\u0015\u0019H/Y4f\u0013\t\u0019\u0003E\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004\u0002\"\n\u0014)mebtHQ\u0007\u0002/%\u0011qe\u0006\u0002\f\r\u0006t\u0017J\\*iCB,W\u0007\u0005\u0002*U1\u0001A!B\u0016\u0001\u0005\u0004a#AA!2#\ti3\u0007\u0005\u0002/c5\tqFC\u00011\u0003\u0015\u00198-\u00197b\u0013\t\u0011tFA\u0004O_RD\u0017N\\4\u0011\u00059\"\u0014BA\u001b0\u0005\r\te.\u001f\t\u0003S]\"Q\u0001\u000f\u0001C\u00021\u0012!!\u0011\u001a\u0011\u0005%RD!B\u001e\u0001\u0005\u0004a#AA!4!\tIS\bB\u0003?\u0001\t\u0007AF\u0001\u0002BiA\u0011\u0011\u0006\u0011\u0003\u0006\u0003\u0002\u0011\r\u0001\f\u0002\u0003\u0003V\u0002\"!K\"\u0005\u000b\u0011\u0003!\u0019\u0001\u0017\u0003\u0003=\u000baA_5qa\u0016\u0014X#A$\u0011\u00119B\u0005FN\u001d=\u007f\tK!!S\u0018\u0003\u0013\u0019+hn\u0019;j_:,\u0014a\u0002>jaB,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00055{\u0005\u0003\u0003(\u0001QYJDh\u0010\"\u000e\u0003UAQ!R\u0002A\u0002\u001d\u000b\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005\u0011\u0006CA\u0013T\u0013\t!vC\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u001d5ba\u0016,\u0012\u0001J\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0007=,H/F\u0001[!\r)3LQ\u0005\u00039^\u0011aaT;uY\u0016$\u0018aA5oaU\tq\fE\u0002&A\"J!!Y\f\u0003\u000b%sG.\u001a;\u0002\t%t\u0007\u0007I\u0001\u0004S:\fT#A3\u0011\u0007\u0015\u0002g'\u0001\u0003j]F\u0002\u0013aA5oeU\t\u0011\u000eE\u0002&Af\nA!\u001b83A\u0005\u0019\u0011N\\\u001a\u0016\u00035\u00042!\n1=\u0003\u0011Ign\r\u0011\u0002\u0007%tG'F\u0001r!\r)\u0003mP\u0001\u0005S:$\u0004%A\u0006de\u0016\fG/\u001a'pO&\u001cGCA;y!\tyb/\u0003\u0002xA\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0003z%\u0001\u0007!+A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\u0005a\bcA?\u0002\u00065\taPC\u0002\u0000\u0003\u0003\tA\u0001\\1oO*\u0011\u00111A\u0001\u0005U\u00064\u0018-C\u0002\u0002\by\u0014aa\u0015;sS:<\u0007")
public class ZipWith5<A1, A2, A3, A4, A5, O>
extends GraphStage<FanInShape5<A1, A2, A3, A4, A5, O>> {
    private final Function5<A1, A2, A3, A4, A5, O> zipper;
    private final FanInShape5<A1, A2, A3, A4, A5, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;

    public Function5<A1, A2, A3, A4, A5, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith5");
    }

    @Override
    public FanInShape5<A1, A2, A3, A4, A5, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith5$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith5$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith5$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith5 $outer;

            public int akka$stream$scaladsl$ZipWith5$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith5$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith5$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith5$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith5$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith5$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith5$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith5$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith5$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith5$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith5$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4())));
                if (this.willShutDown()) {
                    this.completeStage();
                    return;
                }
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
            }

            public /* synthetic */ ZipWith5 akka$stream$scaladsl$ZipWith5$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith5$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith5$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith5$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$16 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith5$$anon$$pushAll();
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
        return "ZipWith5";
    }

    public ZipWith5(Function5<A1, A2, A3, A4, A5, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape5("ZipWith5");
        this.in0 = ((FanInShape5)this.shape()).in0();
        this.in1 = ((FanInShape5)this.shape()).in1();
        this.in2 = ((FanInShape5)this.shape()).in2();
        this.in3 = ((FanInShape5)this.shape()).in3();
        this.in4 = ((FanInShape5)this.shape()).in4();
    }
}

