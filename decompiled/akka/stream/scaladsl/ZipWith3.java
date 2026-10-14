/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape3;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith3$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function3;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I4A\u0001E\t\u00011!A1\b\u0001BC\u0002\u0013\u0005A\b\u0003\u0005A\u0001\t\u0005\t\u0015!\u0003>\u0011\u0015\t\u0005\u0001\"\u0001C\u0011\u00151\u0005\u0001\"\u0011H\u0011\u001dY\u0005A1A\u0005B1Ca!\u0014\u0001!\u0002\u0013\u0001\u0003\"\u0002(\u0001\t\u0003y\u0005bB*\u0001\u0005\u0004%\t\u0001\u0016\u0005\u00071\u0002\u0001\u000b\u0011B+\t\u000fe\u0003!\u0019!C\u00015\"1A\f\u0001Q\u0001\nmCq!\u0018\u0001C\u0002\u0013\u0005a\f\u0003\u0004a\u0001\u0001\u0006Ia\u0018\u0005\u0006C\u0002!\tE\u0019\u0005\u0006Q\u0002!\t%\u001b\u0002\t5&\u0004x+\u001b;ig)\u0011!cE\u0001\tg\u000e\fG.\u00193tY*\u0011A#F\u0001\u0007gR\u0014X-Y7\u000b\u0003Y\tA!Y6lC\u000e\u0001Q#B\r'gYJ4C\u0001\u0001\u001b!\rYb\u0004I\u0007\u00029)\u0011QdE\u0001\u0006gR\fw-Z\u0005\u0003?q\u0011!b\u0012:ba\"\u001cF/Y4f!\u0019\t#\u0005\n\u001a6q5\t1#\u0003\u0002$'\tYa)\u00198J]NC\u0017\r]34!\t)c\u0005\u0004\u0001\u0005\u000b\u001d\u0002!\u0019\u0001\u0015\u0003\u0005\u0005\u000b\u0014CA\u00150!\tQS&D\u0001,\u0015\u0005a\u0013!B:dC2\f\u0017B\u0001\u0018,\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u000b\u0019\n\u0005EZ#aA!osB\u0011Qe\r\u0003\u0006i\u0001\u0011\r\u0001\u000b\u0002\u0003\u0003J\u0002\"!\n\u001c\u0005\u000b]\u0002!\u0019\u0001\u0015\u0003\u0005\u0005\u001b\u0004CA\u0013:\t\u0015Q\u0004A1\u0001)\u0005\u0005y\u0015A\u0002>jaB,'/F\u0001>!\u0019Qc\b\n\u001a6q%\u0011qh\u000b\u0002\n\rVt7\r^5p]N\nqA_5qa\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0003\u0007\u0016\u0003b\u0001\u0012\u0001%eUBT\"A\t\t\u000bm\u001a\u0001\u0019A\u001f\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001I!\t\t\u0013*\u0003\u0002K'\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003\u0001\naa\u001d5ba\u0016\u0004\u0013aA8viV\t\u0001\u000bE\u0002\"#bJ!AU\n\u0003\r=+H\u000f\\3u\u0003\rIg\u000eM\u000b\u0002+B\u0019\u0011E\u0016\u0013\n\u0005]\u001b\"!B%oY\u0016$\u0018\u0001B5oa\u0001\n1!\u001b82+\u0005Y\u0006cA\u0011We\u0005!\u0011N\\\u0019!\u0003\rIgNM\u000b\u0002?B\u0019\u0011EV\u001b\u0002\t%t'\u0007I\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002dMB\u00111\u0004Z\u0005\u0003Kr\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0006O:\u0001\r\u0001S\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\t!\u000e\u0005\u0002la6\tAN\u0003\u0002n]\u0006!A.\u00198h\u0015\u0005y\u0017\u0001\u00026bm\u0006L!!\u001d7\u0003\rM#(/\u001b8h\u0001")
public class ZipWith3<A1, A2, A3, O>
extends GraphStage<FanInShape3<A1, A2, A3, O>> {
    private final Function3<A1, A2, A3, O> zipper;
    private final FanInShape3<A1, A2, A3, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;

    public Function3<A1, A2, A3, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith3");
    }

    @Override
    public FanInShape3<A1, A2, A3, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith3$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith3$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith3$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith3 $outer;

            public int akka$stream$scaladsl$ZipWith3$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith3$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith3$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith3$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith3$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith3$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith3$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith3$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith3$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith3$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith3$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2())));
                if (this.willShutDown()) {
                    this.completeStage();
                    return;
                }
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
            }

            public /* synthetic */ ZipWith3 akka$stream$scaladsl$ZipWith3$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith3$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith3$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith3$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$5 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$5 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$5 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$5 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith3$$anon$$pushAll();
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
        return "ZipWith3";
    }

    public ZipWith3(Function3<A1, A2, A3, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape3("ZipWith3");
        this.in0 = ((FanInShape3)this.shape()).in0();
        this.in1 = ((FanInShape3)this.shape()).in1();
        this.in2 = ((FanInShape3)this.shape()).in2();
    }
}

