/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape7;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith7$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function7;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00055b\u0001\u0002\r\u001a\u0001\u0001B\u0001b\u0014\u0001\u0003\u0006\u0004%\t\u0001\u0015\u0005\t)\u0002\u0011\t\u0011)A\u0005#\")Q\u000b\u0001C\u0001-\")!\f\u0001C!7\"9q\f\u0001b\u0001\n\u0003\u0002\u0007BB1\u0001A\u0003%\u0001\u0006C\u0003c\u0001\u0011\u00051\rC\u0004h\u0001\t\u0007I\u0011\u00015\t\r1\u0004\u0001\u0015!\u0003j\u0011\u001di\u0007A1A\u0005\u00029Da\u0001\u001d\u0001!\u0002\u0013y\u0007bB9\u0001\u0005\u0004%\tA\u001d\u0005\u0007i\u0002\u0001\u000b\u0011B:\t\u000fU\u0004!\u0019!C\u0001m\"1\u0001\u0010\u0001Q\u0001\n]Dq!\u001f\u0001C\u0002\u0013\u0005!\u0010\u0003\u0004}\u0001\u0001\u0006Ia\u001f\u0005\b{\u0002\u0011\r\u0011\"\u0001\u007f\u0011\u001d\t\t\u0001\u0001Q\u0001\n}D\u0011\"a\u0001\u0001\u0005\u0004%\t!!\u0002\t\u0011\u0005%\u0001\u0001)A\u0005\u0003\u000fAq!a\u0003\u0001\t\u0003\ni\u0001C\u0004\u0002\u001a\u0001!\t%a\u0007\u0003\u0011iK\u0007oV5uQ^R!AG\u000e\u0002\u0011M\u001c\u0017\r\\1eg2T!\u0001H\u000f\u0002\rM$(/Z1n\u0015\u0005q\u0012\u0001B1lW\u0006\u001c\u0001!F\u0005\"]mr\u0014\tR$K\u001bN\u0011\u0001A\t\t\u0004G\u0019BS\"\u0001\u0013\u000b\u0005\u0015Z\u0012!B:uC\u001e,\u0017BA\u0014%\u0005)9%/\u00199i'R\fw-\u001a\t\u000bS)b#(\u0010!D\r&cU\"A\u000e\n\u0005-Z\"a\u0003$b]&s7\u000b[1qK^\u0002\"!\f\u0018\r\u0001\u0011)q\u0006\u0001b\u0001a\t\u0011\u0011)M\t\u0003c]\u0002\"AM\u001b\u000e\u0003MR\u0011\u0001N\u0001\u0006g\u000e\fG.Y\u0005\u0003mM\u0012qAT8uQ&tw\r\u0005\u00023q%\u0011\u0011h\r\u0002\u0004\u0003:L\bCA\u0017<\t\u0015a\u0004A1\u00011\u0005\t\t%\u0007\u0005\u0002.}\u0011)q\b\u0001b\u0001a\t\u0011\u0011i\r\t\u0003[\u0005#QA\u0011\u0001C\u0002A\u0012!!\u0011\u001b\u0011\u00055\"E!B#\u0001\u0005\u0004\u0001$AA!6!\tis\tB\u0003I\u0001\t\u0007\u0001G\u0001\u0002BmA\u0011QF\u0013\u0003\u0006\u0017\u0002\u0011\r\u0001\r\u0002\u0003\u0003^\u0002\"!L'\u0005\u000b9\u0003!\u0019\u0001\u0019\u0003\u0003=\u000baA_5qa\u0016\u0014X#A)\u0011\u0015I\u0012FFO\u001fA\u0007\u001aKE*\u0003\u0002Tg\tIa)\u001e8di&|gnN\u0001\bu&\u0004\b/\u001a:!\u0003\u0019a\u0014N\\5u}Q\u0011q+\u0017\t\u000b1\u0002a#(\u0010!D\r&cU\"A\r\t\u000b=\u001b\u0001\u0019A)\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001]!\tIS,\u0003\u0002_7\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003!\naa\u001d5ba\u0016\u0004\u0013aA8viV\tA\rE\u0002*K2K!AZ\u000e\u0003\r=+H\u000f\\3u\u0003\rIg\u000eM\u000b\u0002SB\u0019\u0011F\u001b\u0017\n\u0005-\\\"!B%oY\u0016$\u0018\u0001B5oa\u0001\n1!\u001b82+\u0005y\u0007cA\u0015ku\u0005!\u0011N\\\u0019!\u0003\rIgNM\u000b\u0002gB\u0019\u0011F[\u001f\u0002\t%t'\u0007I\u0001\u0004S:\u001cT#A<\u0011\u0007%R\u0007)\u0001\u0003j]N\u0002\u0013aA5oiU\t1\u0010E\u0002*U\u000e\u000bA!\u001b85A\u0005\u0019\u0011N\\\u001b\u0016\u0003}\u00042!\u000b6G\u0003\u0011Ig.\u000e\u0011\u0002\u0007%tg'\u0006\u0002\u0002\bA\u0019\u0011F[%\u0002\t%tg\u0007I\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002\u0010\u0005U\u0001cA\u0012\u0002\u0012%\u0019\u00111\u0003\u0013\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDa!a\u0006\u0017\u0001\u0004a\u0016aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005u\u0001\u0003BA\u0010\u0003Si!!!\t\u000b\t\u0005\r\u0012QE\u0001\u0005Y\u0006twM\u0003\u0002\u0002(\u0005!!.\u0019<b\u0013\u0011\tY#!\t\u0003\rM#(/\u001b8h\u0001")
public class ZipWith7<A1, A2, A3, A4, A5, A6, A7, O>
extends GraphStage<FanInShape7<A1, A2, A3, A4, A5, A6, A7, O>> {
    private final Function7<A1, A2, A3, A4, A5, A6, A7, O> zipper;
    private final FanInShape7<A1, A2, A3, A4, A5, A6, A7, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;

    public Function7<A1, A2, A3, A4, A5, A6, A7, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith7");
    }

    @Override
    public FanInShape7<A1, A2, A3, A4, A5, A6, A7, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith7$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith7$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith7$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith7 $outer;

            public int akka$stream$scaladsl$ZipWith7$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith7$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith7$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith7$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith7$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith7$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith7$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith7$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6())));
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
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
                this.pull(this.$outer.in6());
            }

            public /* synthetic */ ZipWith7 akka$stream$scaladsl$ZipWith7$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith7$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith7$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$31 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith7$$anon$$pushAll();
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
        return "ZipWith7";
    }

    public ZipWith7(Function7<A1, A2, A3, A4, A5, A6, A7, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape7("ZipWith7");
        this.in0 = ((FanInShape7)this.shape()).in0();
        this.in1 = ((FanInShape7)this.shape()).in1();
        this.in2 = ((FanInShape7)this.shape()).in2();
        this.in3 = ((FanInShape7)this.shape()).in3();
        this.in4 = ((FanInShape7)this.shape()).in4();
        this.in5 = ((FanInShape7)this.shape()).in5();
        this.in6 = ((FanInShape7)this.shape()).in6();
    }
}

