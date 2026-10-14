/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape6;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith6$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function6;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005ma\u0001\u0002\f\u0018\u0001yA\u0001B\u0013\u0001\u0003\u0006\u0004%\ta\u0013\u0005\t\u001f\u0002\u0011\t\u0011)A\u0005\u0019\")\u0001\u000b\u0001C\u0001#\")Q\u000b\u0001C!-\"9!\f\u0001b\u0001\n\u0003Z\u0006B\u0002/\u0001A\u0003%a\u0005C\u0003^\u0001\u0011\u0005a\fC\u0004c\u0001\t\u0007I\u0011A2\t\r\u001d\u0004\u0001\u0015!\u0003e\u0011\u001dA\u0007A1A\u0005\u0002%Daa\u001b\u0001!\u0002\u0013Q\u0007b\u00027\u0001\u0005\u0004%\t!\u001c\u0005\u0007_\u0002\u0001\u000b\u0011\u00028\t\u000fA\u0004!\u0019!C\u0001c\"11\u000f\u0001Q\u0001\nIDq\u0001\u001e\u0001C\u0002\u0013\u0005Q\u000f\u0003\u0004x\u0001\u0001\u0006IA\u001e\u0005\bq\u0002\u0011\r\u0011\"\u0001z\u0011\u0019Y\b\u0001)A\u0005u\")A\u0010\u0001C!{\"9\u0011q\u0001\u0001\u0005B\u0005%!\u0001\u0003.ja^KG\u000f\u001b\u001c\u000b\u0005aI\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005iY\u0012AB:ue\u0016\fWNC\u0001\u001d\u0003\u0011\t7n[1\u0004\u0001UAq\u0004L\u001d=\u007f\t+\u0005j\u0005\u0002\u0001AA\u0019\u0011\u0005\n\u0014\u000e\u0003\tR!aI\r\u0002\u000bM$\u0018mZ3\n\u0005\u0015\u0012#AC$sCBD7\u000b^1hKBIq\u0005\u000b\u00169wy\nEiR\u0007\u00023%\u0011\u0011&\u0007\u0002\f\r\u0006t\u0017J\\*iCB,g\u0007\u0005\u0002,Y1\u0001A!B\u0017\u0001\u0005\u0004q#AA!2#\tyS\u0007\u0005\u00021g5\t\u0011GC\u00013\u0003\u0015\u00198-\u00197b\u0013\t!\u0014GA\u0004O_RD\u0017N\\4\u0011\u0005A2\u0014BA\u001c2\u0005\r\te.\u001f\t\u0003We\"QA\u000f\u0001C\u00029\u0012!!\u0011\u001a\u0011\u0005-bD!B\u001f\u0001\u0005\u0004q#AA!4!\tYs\bB\u0003A\u0001\t\u0007aF\u0001\u0002BiA\u00111F\u0011\u0003\u0006\u0007\u0002\u0011\rA\f\u0002\u0003\u0003V\u0002\"aK#\u0005\u000b\u0019\u0003!\u0019\u0001\u0018\u0003\u0005\u00053\u0004CA\u0016I\t\u0015I\u0005A1\u0001/\u0005\u0005y\u0015A\u0002>jaB,'/F\u0001M!%\u0001TJ\u000b\u001d<}\u0005#u)\u0003\u0002Oc\tIa)\u001e8di&|gNN\u0001\bu&\u0004\b/\u001a:!\u0003\u0019a\u0014N\\5u}Q\u0011!\u000b\u0016\t\n'\u0002Q\u0003h\u000f B\t\u001ek\u0011a\u0006\u0005\u0006\u0015\u000e\u0001\r\u0001T\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A,\u0011\u0005\u001dB\u0016BA-\u001a\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002M\u000511\u000f[1qK\u0002\n1a\\;u+\u0005y\u0006cA\u0014a\u000f&\u0011\u0011-\u0007\u0002\u0007\u001fV$H.\u001a;\u0002\u0007%t\u0007'F\u0001e!\r9SMK\u0005\u0003Mf\u0011Q!\u00138mKR\fA!\u001b81A\u0005\u0019\u0011N\\\u0019\u0016\u0003)\u00042aJ39\u0003\u0011Ig.\r\u0011\u0002\u0007%t''F\u0001o!\r9SmO\u0001\u0005S:\u0014\u0004%A\u0002j]N*\u0012A\u001d\t\u0004O\u0015t\u0014\u0001B5og\u0001\n1!\u001b85+\u00051\bcA\u0014f\u0003\u0006!\u0011N\u001c\u001b!\u0003\rIg.N\u000b\u0002uB\u0019q%\u001a#\u0002\t%tW\u0007I\u0001\fGJ,\u0017\r^3M_\u001eL7\rF\u0002\u007f\u0003\u0007\u0001\"!I@\n\u0007\u0005\u0005!EA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0019\t)\u0001\u0006a\u0001/\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\fA!\u0011QBA\f\u001b\t\tyA\u0003\u0003\u0002\u0012\u0005M\u0011\u0001\u00027b]\u001eT!!!\u0006\u0002\t)\fg/Y\u0005\u0005\u00033\tyA\u0001\u0004TiJLgn\u001a")
public class ZipWith6<A1, A2, A3, A4, A5, A6, O>
extends GraphStage<FanInShape6<A1, A2, A3, A4, A5, A6, O>> {
    private final Function6<A1, A2, A3, A4, A5, A6, O> zipper;
    private final FanInShape6<A1, A2, A3, A4, A5, A6, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;

    public Function6<A1, A2, A3, A4, A5, A6, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith6");
    }

    @Override
    public FanInShape6<A1, A2, A3, A4, A5, A6, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith6$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith6$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith6$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith6 $outer;

            public int akka$stream$scaladsl$ZipWith6$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith6$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith6$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith6$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith6$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith6$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith6$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith6$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith6$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5())));
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
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
            }

            public /* synthetic */ ZipWith6 akka$stream$scaladsl$ZipWith6$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith6$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith6$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$23 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith6$$anon$$pushAll();
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
        return "ZipWith6";
    }

    public ZipWith6(Function6<A1, A2, A3, A4, A5, A6, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape6("ZipWith6");
        this.in0 = ((FanInShape6)this.shape()).in0();
        this.in1 = ((FanInShape6)this.shape()).in1();
        this.in2 = ((FanInShape6)this.shape()).in2();
        this.in3 = ((FanInShape6)this.shape()).in3();
        this.in4 = ((FanInShape6)this.shape()).in4();
        this.in5 = ((FanInShape6)this.shape()).in5();
    }
}

