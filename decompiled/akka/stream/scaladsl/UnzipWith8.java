/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape8;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith8$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple8;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015b\u0001\u0002\n\u0014\u0001iA\u0001\u0002\u0014\u0001\u0003\u0006\u0004%\t!\u0014\u0005\t)\u0002\u0011\t\u0011)A\u0005\u001d\")Q\u000b\u0001C\u0001-\")!\f\u0001C!7\"9q\f\u0001b\u0001\n\u0003\u0002\u0007BB1\u0001A\u0003%!\u0005C\u0003c\u0001\u0011\u00051\rC\u0003h\u0001\u0011\u0005\u0001\u000eC\u0003m\u0001\u0011\u0005Q\u000eC\u0003p\u0001\u0011\u0005\u0001\u000fC\u0003s\u0001\u0011\u00051\u000fC\u0003v\u0001\u0011\u0005a\u000fC\u0003y\u0001\u0011\u0005\u0011\u0010C\u0003|\u0001\u0011\u0005A\u0010C\u0003\u007f\u0001\u0011\u0005q\u0010C\u0004\u0002\u0004\u0001!\t%!\u0002\t\u000f\u0005E\u0001\u0001\"\u0011\u0002\u0014\tQQK\u001c>ja^KG\u000f\u001b\u001d\u000b\u0005Q)\u0012\u0001C:dC2\fGm\u001d7\u000b\u0005Y9\u0012AB:ue\u0016\fWNC\u0001\u0019\u0003\u0011\t7n[1\u0004\u0001UQ1\u0004K\u001b9wy\nEi\u0012&\u0014\u0005\u0001a\u0002cA\u000f!E5\taD\u0003\u0002 +\u0005)1\u000f^1hK&\u0011\u0011E\b\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007cC\u0012%MQ:$(\u0010!D\r&k\u0011!F\u0005\u0003KU\u0011ABR1o\u001fV$8\u000b[1qKb\u0002\"a\n\u0015\r\u0001\u0011)\u0011\u0006\u0001b\u0001U\t\u0011\u0011J\\\t\u0003WE\u0002\"\u0001L\u0018\u000e\u00035R\u0011AL\u0001\u0006g\u000e\fG.Y\u0005\u0003a5\u0012qAT8uQ&tw\r\u0005\u0002-e%\u00111'\f\u0002\u0004\u0003:L\bCA\u00146\t\u00151\u0004A1\u0001+\u0005\t\t\u0015\u0007\u0005\u0002(q\u0011)\u0011\b\u0001b\u0001U\t\u0011\u0011I\r\t\u0003Om\"Q\u0001\u0010\u0001C\u0002)\u0012!!Q\u001a\u0011\u0005\u001drD!B \u0001\u0005\u0004Q#AA!5!\t9\u0013\tB\u0003C\u0001\t\u0007!F\u0001\u0002BkA\u0011q\u0005\u0012\u0003\u0006\u000b\u0002\u0011\rA\u000b\u0002\u0003\u0003Z\u0002\"aJ$\u0005\u000b!\u0003!\u0019\u0001\u0016\u0003\u0005\u0005;\u0004CA\u0014K\t\u0015Y\u0005A1\u0001+\u0005\t\t\u0005(\u0001\u0005v]jL\u0007\u000f]3s+\u0005q\u0005\u0003\u0002\u0017PMEK!\u0001U\u0017\u0003\u0013\u0019+hn\u0019;j_:\f\u0004C\u0003\u0017Si]RT\bQ\"G\u0013&\u00111+\f\u0002\u0007)V\u0004H.\u001a\u001d\u0002\u0013Ut'0\u001b9qKJ\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002X3BY\u0001\f\u0001\u00145oij\u0004i\u0011$J\u001b\u0005\u0019\u0002\"\u0002'\u0004\u0001\u0004q\u0015!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\tA\f\u0005\u0002$;&\u0011a,\u0006\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#\u0001\u0012\u0002\rMD\u0017\r]3!\u0003\tIg.F\u0001e!\r\u0019SMJ\u0005\u0003MV\u0011Q!\u00138mKR\fAa\\;uaU\t\u0011\u000eE\u0002$URJ!a[\u000b\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f^\u0019\u0016\u00039\u00042a\t68\u0003\u0011yW\u000f\u001e\u001a\u0016\u0003E\u00042a\t6;\u0003\u0011yW\u000f^\u001a\u0016\u0003Q\u00042a\t6>\u0003\u0011yW\u000f\u001e\u001b\u0016\u0003]\u00042a\t6A\u0003\u0011yW\u000f^\u001b\u0016\u0003i\u00042a\t6D\u0003\u0011yW\u000f\u001e\u001c\u0016\u0003u\u00042a\t6G\u0003\u0011yW\u000f^\u001c\u0016\u0005\u0005\u0005\u0001cA\u0012k\u0013\u0006Y1M]3bi\u0016dunZ5d)\u0011\t9!!\u0004\u0011\u0007u\tI!C\u0002\u0002\fy\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0007\u0003\u001f\u0001\u0002\u0019\u0001/\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0006\u0011\t\u0005]\u0011\u0011E\u0007\u0003\u00033QA!a\u0007\u0002\u001e\u0005!A.\u00198h\u0015\t\ty\"\u0001\u0003kCZ\f\u0017\u0002BA\u0012\u00033\u0011aa\u0015;sS:<\u0007")
public class UnzipWith8<In, A1, A2, A3, A4, A5, A6, A7, A8>
extends GraphStage<FanOutShape8<In, A1, A2, A3, A4, A5, A6, A7, A8>> {
    private final Function1<In, Tuple8<A1, A2, A3, A4, A5, A6, A7, A8>> unzipper;
    private final FanOutShape8<In, A1, A2, A3, A4, A5, A6, A7, A8> shape;

    public Function1<In, Tuple8<A1, A2, A3, A4, A5, A6, A7, A8>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith8");
    }

    @Override
    public FanOutShape8<In, A1, A2, A3, A4, A5, A6, A7, A8> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape8)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape8)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape8)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape8)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape8)this.shape()).out4();
    }

    public Outlet<A6> out5() {
        return ((FanOutShape8)this.shape()).out5();
    }

    public Outlet<A7> out6() {
        return ((FanOutShape8)this.shape()).out6();
    }

    public Outlet<A8> out7() {
        return ((FanOutShape8)this.shape()).out7();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending4;
            private boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending5;
            private boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending6;
            private boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending7;
            private final /* synthetic */ UnzipWith8 $outer;

            public int akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending4 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending5() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pending5;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pending5_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending5 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending6() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pending6;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pending6_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending6 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith8$$anon$$pending7() {
                return this.akka$stream$scaladsl$UnzipWith8$$anon$$pending7;
            }

            public void akka$stream$scaladsl$UnzipWith8$$anon$$pending7_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending7 = x$1;
            }

            public /* synthetic */ UnzipWith8 akka$stream$scaladsl$UnzipWith8$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount = 8;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning = 8;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending4 = true;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending5 = true;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending6 = true;
                this.akka$stream$scaladsl$UnzipWith8$$anon$$pending7 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$40 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple8<A1, A2, A3, A4, A5, A6, A7, A8> elem = this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending4_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out5())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out5(), elem._6());
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending5_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out6())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out6(), elem._7());
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending6_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out7())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().out7(), elem._8());
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending7_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
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
                this.setHandler($outer.out4(), new OutHandler(this){
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
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
                this.setHandler($outer.out5(), new OutHandler(this){
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending5_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending5()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
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
                this.setHandler($outer.out6(), new OutHandler(this){
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending6_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending6()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
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
                this.setHandler($outer.out7(), new OutHandler(this){
                    private final /* synthetic */ $anon$40 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending7_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pending7()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith8$$anon$$$outer().in());
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
        return "UnzipWith8";
    }

    public UnzipWith8(Function1<In, Tuple8<A1, A2, A3, A4, A5, A6, A7, A8>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape8("UnzipWith8");
    }
}

