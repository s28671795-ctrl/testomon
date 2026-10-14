/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape9;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith9$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple9;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Mb\u0001B\n\u0015\u0001mA\u0001\u0002\u0015\u0001\u0003\u0006\u0004%\t!\u0015\u0005\t1\u0002\u0011\t\u0011)A\u0005%\")\u0011\f\u0001C\u00015\")a\f\u0001C!?\"91\r\u0001b\u0001\n\u0003\"\u0007BB3\u0001A\u0003%1\u0005C\u0003g\u0001\u0011\u0005q\rC\u0003l\u0001\u0011\u0005A\u000eC\u0003q\u0001\u0011\u0005\u0011\u000fC\u0003t\u0001\u0011\u0005A\u000fC\u0003w\u0001\u0011\u0005q\u000fC\u0003z\u0001\u0011\u0005!\u0010C\u0003}\u0001\u0011\u0005Q\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0011\u0011\u0001\u0005\b\u0003\u000b\u0001A\u0011AA\u0004\u0011\u001d\tY\u0001\u0001C\u0001\u0003\u001bAq!!\u0005\u0001\t\u0003\n\u0019\u0002C\u0004\u0002 \u0001!\t%!\t\u0003\u0015Us'0\u001b9XSRD\u0017H\u0003\u0002\u0016-\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u00181\u000511\u000f\u001e:fC6T\u0011!G\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0017qIc'\u000f\u001f@\u0005\u0016C5JT\n\u0003\u0001u\u00012AH\u0011$\u001b\u0005y\"B\u0001\u0011\u0017\u0003\u0015\u0019H/Y4f\u0013\t\u0011sD\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004B\u0002J\u0013(kaZd(\u0011#H\u00156k\u0011AF\u0005\u0003MY\u0011ABR1o\u001fV$8\u000b[1qKf\u0002\"\u0001K\u0015\r\u0001\u0011)!\u0006\u0001b\u0001W\t\u0011\u0011J\\\t\u0003YI\u0002\"!\f\u0019\u000e\u00039R\u0011aL\u0001\u0006g\u000e\fG.Y\u0005\u0003c9\u0012qAT8uQ&tw\r\u0005\u0002.g%\u0011AG\f\u0002\u0004\u0003:L\bC\u0001\u00157\t\u00159\u0004A1\u0001,\u0005\t\t\u0015\u0007\u0005\u0002)s\u0011)!\b\u0001b\u0001W\t\u0011\u0011I\r\t\u0003Qq\"Q!\u0010\u0001C\u0002-\u0012!!Q\u001a\u0011\u0005!zD!\u0002!\u0001\u0005\u0004Y#AA!5!\tA#\tB\u0003D\u0001\t\u00071F\u0001\u0002BkA\u0011\u0001&\u0012\u0003\u0006\r\u0002\u0011\ra\u000b\u0002\u0003\u0003Z\u0002\"\u0001\u000b%\u0005\u000b%\u0003!\u0019A\u0016\u0003\u0005\u0005;\u0004C\u0001\u0015L\t\u0015a\u0005A1\u0001,\u0005\t\t\u0005\b\u0005\u0002)\u001d\u0012)q\n\u0001b\u0001W\t\u0011\u0011)O\u0001\tk:T\u0018\u000e\u001d9feV\t!\u000b\u0005\u0003.'\u001e*\u0016B\u0001+/\u0005%1UO\\2uS>t\u0017\u0007E\u0006.-VB4HP!E\u000f*k\u0015BA,/\u0005\u0019!V\u000f\u001d7fs\u0005IQO\u001c>jaB,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005mk\u0006\u0003\u0004/\u0001OUB4HP!E\u000f*kU\"\u0001\u000b\t\u000bA\u001b\u0001\u0019\u0001*\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001a!\t!\u0013-\u0003\u0002c-\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003\r\naa\u001d5ba\u0016\u0004\u0013AA5o+\u0005A\u0007c\u0001\u0013jO%\u0011!N\u0006\u0002\u0006\u0013:dW\r^\u0001\u0005_V$\b'F\u0001n!\r!c.N\u0005\u0003_Z\u0011aaT;uY\u0016$\u0018\u0001B8viF*\u0012A\u001d\t\u0004I9D\u0014\u0001B8viJ*\u0012!\u001e\t\u0004I9\\\u0014\u0001B8viN*\u0012\u0001\u001f\t\u0004I9t\u0014\u0001B8viR*\u0012a\u001f\t\u0004I9\f\u0015\u0001B8viV*\u0012A \t\u0004I9$\u0015\u0001B8viZ*\"!a\u0001\u0011\u0007\u0011rw)\u0001\u0003pkR<TCAA\u0005!\r!cNS\u0001\u0005_V$\b(\u0006\u0002\u0002\u0010A\u0019AE\\'\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0005\u0003+\tY\u0002E\u0002\u001f\u0003/I1!!\u0007 \u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007BBA\u000f#\u0001\u0007\u0001-A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7/\u0001\u0005u_N#(/\u001b8h)\t\t\u0019\u0003\u0005\u0003\u0002&\u0005=RBAA\u0014\u0015\u0011\tI#a\u000b\u0002\t1\fgn\u001a\u0006\u0003\u0003[\tAA[1wC&!\u0011\u0011GA\u0014\u0005\u0019\u0019FO]5oO\u0002")
public class UnzipWith9<In, A1, A2, A3, A4, A5, A6, A7, A8, A9>
extends GraphStage<FanOutShape9<In, A1, A2, A3, A4, A5, A6, A7, A8, A9>> {
    private final Function1<In, Tuple9<A1, A2, A3, A4, A5, A6, A7, A8, A9>> unzipper;
    private final FanOutShape9<In, A1, A2, A3, A4, A5, A6, A7, A8, A9> shape;

    public Function1<In, Tuple9<A1, A2, A3, A4, A5, A6, A7, A8, A9>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith9");
    }

    @Override
    public FanOutShape9<In, A1, A2, A3, A4, A5, A6, A7, A8, A9> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape9)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape9)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape9)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape9)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape9)this.shape()).out4();
    }

    public Outlet<A6> out5() {
        return ((FanOutShape9)this.shape()).out5();
    }

    public Outlet<A7> out6() {
        return ((FanOutShape9)this.shape()).out6();
    }

    public Outlet<A8> out7() {
        return ((FanOutShape9)this.shape()).out7();
    }

    public Outlet<A9> out8() {
        return ((FanOutShape9)this.shape()).out8();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending4;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending5;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending6;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending7;
            private boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending8;
            private final /* synthetic */ UnzipWith9 $outer;

            public int akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending4 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending5() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending5;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending5_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending5 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending6() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending6;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending6_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending6 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending7() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending7;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending7_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending7 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith9$$anon$$pending8() {
                return this.akka$stream$scaladsl$UnzipWith9$$anon$$pending8;
            }

            public void akka$stream$scaladsl$UnzipWith9$$anon$$pending8_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending8 = x$1;
            }

            public /* synthetic */ UnzipWith9 akka$stream$scaladsl$UnzipWith9$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount = 9;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning = 9;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending4 = true;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending5 = true;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending6 = true;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending7 = true;
                this.akka$stream$scaladsl$UnzipWith9$$anon$$pending8 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple9<A1, A2, A3, A4, A5, A6, A7, A8, A9> elem = this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending4_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out5())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out5(), elem._6());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending5_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out6())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out6(), elem._7());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending6_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out7())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out7(), elem._8());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending7_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out8())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().out8(), elem._9());
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending8_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending5_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending5()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending6_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending6()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending7_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending7()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
                this.setHandler($outer.out8(), new OutHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending8_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pending8()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith9$$anon$$$outer().in());
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
        return "UnzipWith9";
    }

    public UnzipWith9(Function1<In, Tuple9<A1, A2, A3, A4, A5, A6, A7, A8, A9>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape9("UnzipWith9");
    }
}

