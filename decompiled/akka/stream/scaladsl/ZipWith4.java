/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape4;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith4$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function4;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m4AAE\n\u00015!A\u0001\t\u0001BC\u0002\u0013\u0005\u0011\t\u0003\u0005F\u0001\t\u0005\t\u0015!\u0003C\u0011\u00151\u0005\u0001\"\u0001H\u0011\u0015Y\u0005\u0001\"\u0011M\u0011\u001d\u0001\u0006A1A\u0005BECaA\u0015\u0001!\u0002\u0013\u0011\u0003\"B*\u0001\t\u0003!\u0006b\u0002-\u0001\u0005\u0004%\t!\u0017\u0005\u0007;\u0002\u0001\u000b\u0011\u0002.\t\u000fy\u0003!\u0019!C\u0001?\"1\u0011\r\u0001Q\u0001\n\u0001DqA\u0019\u0001C\u0002\u0013\u00051\r\u0003\u0004f\u0001\u0001\u0006I\u0001\u001a\u0005\bM\u0002\u0011\r\u0011\"\u0001h\u0011\u0019I\u0007\u0001)A\u0005Q\")!\u000e\u0001C!W\")\u0011\u000f\u0001C!e\nA!,\u001b9XSRDGG\u0003\u0002\u0015+\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u0017/\u000511\u000f\u001e:fC6T\u0011\u0001G\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\rmAS\u0007O\u001e?'\t\u0001A\u0004E\u0002\u001eA\tj\u0011A\b\u0006\u0003?U\tQa\u001d;bO\u0016L!!\t\u0010\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rE\u0004$I\u0019\"tGO\u001f\u000e\u0003UI!!J\u000b\u0003\u0017\u0019\u000bg.\u00138TQ\u0006\u0004X\r\u000e\t\u0003O!b\u0001\u0001B\u0003*\u0001\t\u0007!F\u0001\u0002BcE\u00111&\r\t\u0003Y=j\u0011!\f\u0006\u0002]\u0005)1oY1mC&\u0011\u0001'\f\u0002\b\u001d>$\b.\u001b8h!\ta#'\u0003\u00024[\t\u0019\u0011I\\=\u0011\u0005\u001d*D!\u0002\u001c\u0001\u0005\u0004Q#AA!3!\t9\u0003\bB\u0003:\u0001\t\u0007!F\u0001\u0002BgA\u0011qe\u000f\u0003\u0006y\u0001\u0011\rA\u000b\u0002\u0003\u0003R\u0002\"a\n \u0005\u000b}\u0002!\u0019\u0001\u0016\u0003\u0003=\u000baA_5qa\u0016\u0014X#\u0001\"\u0011\u000f1\u001ae\u0005N\u001c;{%\u0011A)\f\u0002\n\rVt7\r^5p]R\nqA_5qa\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0003\u0011*\u0003r!\u0013\u0001'i]RT(D\u0001\u0014\u0011\u0015\u00015\u00011\u0001C\u0003EIg.\u001b;jC2\fE\u000f\u001e:jEV$Xm]\u000b\u0002\u001bB\u00111ET\u0005\u0003\u001fV\u0011!\"\u0011;ue&\u0014W\u000f^3t\u0003\u0015\u0019\b.\u00199f+\u0005\u0011\u0013AB:iCB,\u0007%A\u0002pkR,\u0012!\u0016\t\u0004GYk\u0014BA,\u0016\u0005\u0019yU\u000f\u001e7fi\u0006\u0019\u0011N\u001c\u0019\u0016\u0003i\u00032aI.'\u0013\taVCA\u0003J]2,G/\u0001\u0003j]B\u0002\u0013aA5ocU\t\u0001\rE\u0002$7R\nA!\u001b82A\u0005\u0019\u0011N\u001c\u001a\u0016\u0003\u0011\u00042aI.8\u0003\u0011IgN\r\u0011\u0002\u0007%t7'F\u0001i!\r\u00193LO\u0001\u0005S:\u001c\u0004%A\u0006de\u0016\fG/\u001a'pO&\u001cGC\u00017p!\tiR.\u0003\u0002o=\tyqI]1qQN#\u0018mZ3M_\u001eL7\rC\u0003q!\u0001\u0007Q*A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\u0005\u0019\bC\u0001;z\u001b\u0005)(B\u0001<x\u0003\u0011a\u0017M\\4\u000b\u0003a\fAA[1wC&\u0011!0\u001e\u0002\u0007'R\u0014\u0018N\\4")
public class ZipWith4<A1, A2, A3, A4, O>
extends GraphStage<FanInShape4<A1, A2, A3, A4, O>> {
    private final Function4<A1, A2, A3, A4, O> zipper;
    private final FanInShape4<A1, A2, A3, A4, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;

    public Function4<A1, A2, A3, A4, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith4");
    }

    @Override
    public FanInShape4<A1, A2, A3, A4, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith4$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith4$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith4$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith4 $outer;

            public int akka$stream$scaladsl$ZipWith4$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith4$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith4$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith4$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith4$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith4$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith4$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith4$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith4$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith4$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith4$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3())));
                if (this.willShutDown()) {
                    this.completeStage();
                    return;
                }
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
            }

            public /* synthetic */ ZipWith4 akka$stream$scaladsl$ZipWith4$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith4$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith4$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith4$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$10 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$10 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$10 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$10 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$10 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith4$$anon$$pushAll();
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
        return "ZipWith4";
    }

    public ZipWith4(Function4<A1, A2, A3, A4, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape4("ZipWith4");
        this.in0 = ((FanInShape4)this.shape()).in0();
        this.in1 = ((FanInShape4)this.shape()).in1();
        this.in2 = ((FanInShape4)this.shape()).in2();
        this.in3 = ((FanInShape4)this.shape()).in3();
    }
}

