/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape4;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith4$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple4;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y4AAD\b\u0001-!AA\b\u0001BC\u0002\u0013\u0005Q\b\u0003\u0005E\u0001\t\u0005\t\u0015!\u0003?\u0011\u0015)\u0005\u0001\"\u0001G\u0011\u0015Q\u0005\u0001\"\u0011L\u0011\u001dy\u0005A1A\u0005BACa!\u0015\u0001!\u0002\u0013q\u0002\"\u0002*\u0001\t\u0003\u0019\u0006\"B,\u0001\t\u0003A\u0006\"\u0002/\u0001\t\u0003i\u0006\"B0\u0001\t\u0003\u0001\u0007\"\u00022\u0001\t\u0003\u0019\u0007\"B3\u0001\t\u00032\u0007\"\u00027\u0001\t\u0003j'AC+ou&\u0004x+\u001b;ii)\u0011\u0001#E\u0001\tg\u000e\fG.\u00193tY*\u0011!cE\u0001\u0007gR\u0014X-Y7\u000b\u0003Q\tA!Y6lC\u000e\u0001QCB\f%cQ:$h\u0005\u0002\u00011A\u0019\u0011\u0004\b\u0010\u000e\u0003iQ!aG\t\u0002\u000bM$\u0018mZ3\n\u0005uQ\"AC$sCBD7\u000b^1hKB9q\u0004\t\u00121gYJT\"A\t\n\u0005\u0005\n\"\u0001\u0004$b]>+Ho\u00155ba\u0016$\u0004CA\u0012%\u0019\u0001!Q!\n\u0001C\u0002\u0019\u0012!!\u00138\u0012\u0005\u001dj\u0003C\u0001\u0015,\u001b\u0005I#\"\u0001\u0016\u0002\u000bM\u001c\u0017\r\\1\n\u00051J#a\u0002(pi\"Lgn\u001a\t\u0003Q9J!aL\u0015\u0003\u0007\u0005s\u0017\u0010\u0005\u0002$c\u0011)!\u0007\u0001b\u0001M\t\u0011\u0011)\r\t\u0003GQ\"Q!\u000e\u0001C\u0002\u0019\u0012!!\u0011\u001a\u0011\u0005\r:D!\u0002\u001d\u0001\u0005\u00041#AA!4!\t\u0019#\bB\u0003<\u0001\t\u0007aE\u0001\u0002Bi\u0005AQO\u001c>jaB,'/F\u0001?!\u0011AsHI!\n\u0005\u0001K#!\u0003$v]\u000e$\u0018n\u001c82!\u0019A#\tM\u001a7s%\u00111)\u000b\u0002\u0007)V\u0004H.\u001a\u001b\u0002\u0013Ut'0\u001b9qKJ\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002H\u0013B9\u0001\n\u0001\u00121gYJT\"A\b\t\u000bq\u001a\u0001\u0019\u0001 \u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001M!\tyR*\u0003\u0002O#\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003y\taa\u001d5ba\u0016\u0004\u0013AA5o+\u0005!\u0006cA\u0010VE%\u0011a+\u0005\u0002\u0006\u0013:dW\r^\u0001\u0005_V$\b'F\u0001Z!\ry\"\fM\u0005\u00037F\u0011aaT;uY\u0016$\u0018\u0001B8viF*\u0012A\u0018\t\u0004?i\u001b\u0014\u0001B8viJ*\u0012!\u0019\t\u0004?i3\u0014\u0001B8viN*\u0012\u0001\u001a\t\u0004?iK\u0014aC2sK\u0006$X\rT8hS\u000e$\"a\u001a6\u0011\u0005eA\u0017BA5\u001b\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"B6\r\u0001\u0004a\u0015aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u00039\u0004\"a\u001c;\u000e\u0003AT!!\u001d:\u0002\t1\fgn\u001a\u0006\u0002g\u0006!!.\u0019<b\u0013\t)\bO\u0001\u0004TiJLgn\u001a")
public class UnzipWith4<In, A1, A2, A3, A4>
extends GraphStage<FanOutShape4<In, A1, A2, A3, A4>> {
    private final Function1<In, Tuple4<A1, A2, A3, A4>> unzipper;
    private final FanOutShape4<In, A1, A2, A3, A4> shape;

    public Function1<In, Tuple4<A1, A2, A3, A4>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith4");
    }

    @Override
    public FanOutShape4<In, A1, A2, A3, A4> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape4)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape4)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape4)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape4)this.shape()).out3();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith4$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith4$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith4$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith4$$anon$$pending3;
            private final /* synthetic */ UnzipWith4 $outer;

            public int akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith4$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith4$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith4$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith4$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith4$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith4$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith4$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith4$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith4$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith4$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith4$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith4$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pending3 = x$1;
            }

            public /* synthetic */ UnzipWith4 akka$stream$scaladsl$UnzipWith4$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount = 4;
                this.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning = 4;
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith4$$anon$$pending3 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$10 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple4<A1, A2, A3, A4> elem = this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending3_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$10 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$10 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$10 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in());
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
                this.setHandler($outer.out3(), new OutHandler(this){
                    private final /* synthetic */ $anon$10 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith4$$anon$$$outer().in());
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
        return "UnzipWith4";
    }

    public UnzipWith4(Function1<In, Tuple4<A1, A2, A3, A4>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape4("UnzipWith4");
    }
}

