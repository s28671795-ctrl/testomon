/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape2;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith2$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%4AAD\b\u0001-!Aa\u0007\u0001BC\u0002\u0013\u0005q\u0007\u0003\u0005<\u0001\t\u0005\t\u0015!\u00039\u0011\u0015a\u0004\u0001\"\u0001>\u0011\u0015\t\u0005\u0001\"\u0011C\u0011\u001d1\u0005A1A\u0005B\u001dCa\u0001\u0013\u0001!\u0002\u0013q\u0002\"B%\u0001\t\u0003Q\u0005b\u0002(\u0001\u0005\u0004%\ta\u0014\u0005\u0007'\u0002\u0001\u000b\u0011\u0002)\t\u000fQ\u0003!\u0019!C\u0001+\"1q\u000b\u0001Q\u0001\nYCQ\u0001\u0017\u0001\u0005BeCQa\u0018\u0001\u0005B\u0001\u0014\u0001BW5q/&$\bN\r\u0006\u0003!E\t\u0001b]2bY\u0006$7\u000f\u001c\u0006\u0003%M\taa\u001d;sK\u0006l'\"\u0001\u000b\u0002\t\u0005\\7.Y\u0002\u0001+\u00119B%\r\u001b\u0014\u0005\u0001A\u0002cA\r\u001d=5\t!D\u0003\u0002\u001c#\u0005)1\u000f^1hK&\u0011QD\u0007\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007#B\u0010!EA\u001aT\"A\t\n\u0005\u0005\n\"a\u0003$b]&s7\u000b[1qKJ\u0002\"a\t\u0013\r\u0001\u0011)Q\u0005\u0001b\u0001M\t\u0011\u0011)M\t\u0003O5\u0002\"\u0001K\u0016\u000e\u0003%R\u0011AK\u0001\u0006g\u000e\fG.Y\u0005\u0003Y%\u0012qAT8uQ&tw\r\u0005\u0002)]%\u0011q&\u000b\u0002\u0004\u0003:L\bCA\u00122\t\u0015\u0011\u0004A1\u0001'\u0005\t\t%\u0007\u0005\u0002$i\u0011)Q\u0007\u0001b\u0001M\t\tq*\u0001\u0004{SB\u0004XM]\u000b\u0002qA)\u0001&\u000f\u00121g%\u0011!(\u000b\u0002\n\rVt7\r^5p]J\nqA_5qa\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0003}\u0001\u0003Ra\u0010\u0001#aMj\u0011a\u0004\u0005\u0006m\r\u0001\r\u0001O\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A\"\u0011\u0005}!\u0015BA#\u0012\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002=\u000511\u000f[1qK\u0002\n1a\\;u+\u0005Y\u0005cA\u0010Mg%\u0011Q*\u0005\u0002\u0007\u001fV$H.\u001a;\u0002\u0007%t\u0007'F\u0001Q!\ry\u0012KI\u0005\u0003%F\u0011Q!\u00138mKR\fA!\u001b81A\u0005\u0019\u0011N\\\u0019\u0016\u0003Y\u00032aH)1\u0003\u0011Ig.\r\u0011\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u00035v\u0003\"!G.\n\u0005qS\"aD$sCBD7\u000b^1hK2{w-[2\t\u000byc\u0001\u0019A\"\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012!\u0019\t\u0003E\u001el\u0011a\u0019\u0006\u0003I\u0016\fA\u0001\\1oO*\ta-\u0001\u0003kCZ\f\u0017B\u00015d\u0005\u0019\u0019FO]5oO\u0002")
public class ZipWith2<A1, A2, O>
extends GraphStage<FanInShape2<A1, A2, O>> {
    private final Function2<A1, A2, O> zipper;
    private final FanInShape2<A1, A2, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;

    public Function2<A1, A2, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith2");
    }

    @Override
    public FanInShape2<A1, A2, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith2$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith2$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith2$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith2 $outer;

            public int akka$stream$scaladsl$ZipWith2$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith2$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith2$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith2$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith2$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith2$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith2$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith2$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith2$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith2$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith2$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1())));
                if (this.willShutDown()) {
                    this.completeStage();
                    return;
                }
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
            }

            public /* synthetic */ ZipWith2 akka$stream$scaladsl$ZipWith2$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith2$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith2$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith2$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$1 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith2$$anon$$pushAll();
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
        return "ZipWith2";
    }

    public ZipWith2(Function2<A1, A2, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape2("ZipWith2");
        this.in0 = ((FanInShape2)this.shape()).in0();
        this.in1 = ((FanInShape2)this.shape()).in1();
    }
}

