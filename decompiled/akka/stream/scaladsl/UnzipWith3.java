/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape3;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith3$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple3;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=4A!\u0004\b\u0001+!A\u0001\b\u0001BC\u0002\u0013\u0005\u0011\b\u0003\u0005A\u0001\t\u0005\t\u0015!\u0003;\u0011\u0015\t\u0005\u0001\"\u0001C\u0011\u00151\u0005\u0001\"\u0011H\u0011\u001dY\u0005A1A\u0005B1Ca!\u0014\u0001!\u0002\u0013i\u0002\"\u0002(\u0001\t\u0003y\u0005\"B*\u0001\t\u0003!\u0006\"\u0002-\u0001\t\u0003I\u0006\"B.\u0001\t\u0003a\u0006\"\u00020\u0001\t\u0003z\u0006\"B3\u0001\t\u00032'AC+ou&\u0004x+\u001b;ig)\u0011q\u0002E\u0001\tg\u000e\fG.\u00193tY*\u0011\u0011CE\u0001\u0007gR\u0014X-Y7\u000b\u0003M\tA!Y6lC\u000e\u0001Q#\u0002\f$aM24C\u0001\u0001\u0018!\rA2$H\u0007\u00023)\u0011!\u0004E\u0001\u0006gR\fw-Z\u0005\u00039e\u0011!b\u0012:ba\"\u001cF/Y4f!\u0019qr$I\u00183k5\t\u0001#\u0003\u0002!!\taa)\u00198PkR\u001c\u0006.\u00199fgA\u0011!e\t\u0007\u0001\t\u0015!\u0003A1\u0001&\u0005\tIe.\u0005\u0002'YA\u0011qEK\u0007\u0002Q)\t\u0011&A\u0003tG\u0006d\u0017-\u0003\u0002,Q\t9aj\u001c;iS:<\u0007CA\u0014.\u0013\tq\u0003FA\u0002B]f\u0004\"A\t\u0019\u0005\u000bE\u0002!\u0019A\u0013\u0003\u0005\u0005\u000b\u0004C\u0001\u00124\t\u0015!\u0004A1\u0001&\u0005\t\t%\u0007\u0005\u0002#m\u0011)q\u0007\u0001b\u0001K\t\u0011\u0011iM\u0001\tk:T\u0018\u000e\u001d9feV\t!\b\u0005\u0003(w\u0005j\u0014B\u0001\u001f)\u0005%1UO\\2uS>t\u0017\u0007E\u0003(}=\u0012T'\u0003\u0002@Q\t1A+\u001e9mKN\n\u0011\"\u001e8{SB\u0004XM\u001d\u0011\u0002\rqJg.\u001b;?)\t\u0019U\t\u0005\u0004E\u0001\u0005z#'N\u0007\u0002\u001d!)\u0001h\u0001a\u0001u\u0005\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u0003!\u0003\"AH%\n\u0005)\u0003\"AC!uiJL'-\u001e;fg\u0006)1\u000f[1qKV\tQ$\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0003S:,\u0012\u0001\u0015\t\u0004=E\u000b\u0013B\u0001*\u0011\u0005\u0015Ie\u000e\\3u\u0003\u0011yW\u000f\u001e\u0019\u0016\u0003U\u00032A\b,0\u0013\t9\u0006C\u0001\u0004PkRdW\r^\u0001\u0005_V$\u0018'F\u0001[!\rqbKM\u0001\u0005_V$('F\u0001^!\rqb+N\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002aGB\u0011\u0001$Y\u0005\u0003Ef\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0006I.\u0001\r\u0001S\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\tq\r\u0005\u0002i[6\t\u0011N\u0003\u0002kW\u0006!A.\u00198h\u0015\u0005a\u0017\u0001\u00026bm\u0006L!A\\5\u0003\rM#(/\u001b8h\u0001")
public class UnzipWith3<In, A1, A2, A3>
extends GraphStage<FanOutShape3<In, A1, A2, A3>> {
    private final Function1<In, Tuple3<A1, A2, A3>> unzipper;
    private final FanOutShape3<In, A1, A2, A3> shape;

    public Function1<In, Tuple3<A1, A2, A3>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith3");
    }

    @Override
    public FanOutShape3<In, A1, A2, A3> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape3)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape3)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape3)this.shape()).out2();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith3$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith3$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith3$$anon$$pending2;
            private final /* synthetic */ UnzipWith3 $outer;

            public int akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith3$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith3$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith3$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith3$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith3$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith3$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith3$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith3$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith3$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith3$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith3$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith3$$anon$$pending2 = x$1;
            }

            public /* synthetic */ UnzipWith3 akka$stream$scaladsl$UnzipWith3$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount = 3;
                this.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning = 3;
                this.akka$stream$scaladsl$UnzipWith3$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith3$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith3$$anon$$pending2 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$5 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple3<A1, A2, A3> elem = this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending2_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning());
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.out0(), new OutHandler(this){
                    private final /* synthetic */ $anon$5 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in());
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
                this.setHandler($outer.out1(), new OutHandler(this){
                    private final /* synthetic */ $anon$5 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in());
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
                this.setHandler($outer.out2(), new OutHandler(this){
                    private final /* synthetic */ $anon$5 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith3$$anon$$$outer().in());
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
        return "UnzipWith3";
    }

    public UnzipWith3(Function1<In, Tuple3<A1, A2, A3>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape3("UnzipWith3");
    }
}

