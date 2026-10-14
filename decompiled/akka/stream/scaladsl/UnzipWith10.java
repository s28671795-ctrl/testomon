/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape10;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith10$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple10;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005c\u0001\u0002\u000b\u0016\u0001qA\u0001\u0002\u0016\u0001\u0003\u0006\u0004%\t!\u0016\u0005\t9\u0002\u0011\t\u0011)A\u0005-\")Q\f\u0001C\u0001=\")!\r\u0001C!G\"9q\r\u0001b\u0001\n\u0003B\u0007BB5\u0001A\u0003%A\u0005C\u0003k\u0001\u0011\u00051\u000eC\u0003p\u0001\u0011\u0005\u0001\u000fC\u0003u\u0001\u0011\u0005Q\u000fC\u0003x\u0001\u0011\u0005\u0001\u0010C\u0003{\u0001\u0011\u00051\u0010C\u0003~\u0001\u0011\u0005a\u0010C\u0004\u0002\u0002\u0001!\t!a\u0001\t\u000f\u0005\u001d\u0001\u0001\"\u0001\u0002\n!9\u0011Q\u0002\u0001\u0005\u0002\u0005=\u0001bBA\n\u0001\u0011\u0005\u0011Q\u0003\u0005\b\u00033\u0001A\u0011AA\u000e\u0011\u001d\ty\u0002\u0001C!\u0003CAq!!\f\u0001\t\u0003\nyCA\u0006V]jL\u0007oV5uQF\u0002$B\u0001\f\u0018\u0003!\u00198-\u00197bINd'B\u0001\r\u001a\u0003\u0019\u0019HO]3b[*\t!$\u0001\u0003bW.\f7\u0001A\u000b\r;):$(\u0010!D\r&cuJU\n\u0003\u0001y\u00012a\b\u0012%\u001b\u0005\u0001#BA\u0011\u0018\u0003\u0015\u0019H/Y4f\u0013\t\u0019\u0003E\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004R\"\n\u0014)mebtHQ#I\u0017:\u000bV\"A\f\n\u0005\u001d:\"!\u0004$b]>+Ho\u00155ba\u0016\f\u0004\u0007\u0005\u0002*U1\u0001A!B\u0016\u0001\u0005\u0004a#AA%o#\ti3\u0007\u0005\u0002/c5\tqFC\u00011\u0003\u0015\u00198-\u00197b\u0013\t\u0011tFA\u0004O_RD\u0017N\\4\u0011\u00059\"\u0014BA\u001b0\u0005\r\te.\u001f\t\u0003S]\"Q\u0001\u000f\u0001C\u00021\u0012!!Q\u0019\u0011\u0005%RD!B\u001e\u0001\u0005\u0004a#AA!3!\tIS\bB\u0003?\u0001\t\u0007AF\u0001\u0002BgA\u0011\u0011\u0006\u0011\u0003\u0006\u0003\u0002\u0011\r\u0001\f\u0002\u0003\u0003R\u0002\"!K\"\u0005\u000b\u0011\u0003!\u0019\u0001\u0017\u0003\u0005\u0005+\u0004CA\u0015G\t\u00159\u0005A1\u0001-\u0005\t\te\u0007\u0005\u0002*\u0013\u0012)!\n\u0001b\u0001Y\t\u0011\u0011i\u000e\t\u0003S1#Q!\u0014\u0001C\u00021\u0012!!\u0011\u001d\u0011\u0005%zE!\u0002)\u0001\u0005\u0004a#AA!:!\tI#\u000bB\u0003T\u0001\t\u0007AFA\u0002BcA\n\u0001\"\u001e8{SB\u0004XM]\u000b\u0002-B!af\u0016\u0015Z\u0013\tAvFA\u0005Gk:\u001cG/[8ocAaaF\u0017\u001c:y}\u0012U\tS&O#&\u00111l\f\u0002\b)V\u0004H.Z\u00191\u0003%)hN_5qa\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0003?\u0006\u0004R\u0002\u0019\u0001)mebtHQ#I\u0017:\u000bV\"A\u000b\t\u000bQ\u001b\u0001\u0019\u0001,\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001e!\t)S-\u0003\u0002g/\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003\u0011\naa\u001d5ba\u0016\u0004\u0013AA5o+\u0005a\u0007cA\u0013nQ%\u0011an\u0006\u0002\u0006\u0013:dW\r^\u0001\u0005_V$\b'F\u0001r!\r)#ON\u0005\u0003g^\u0011aaT;uY\u0016$\u0018\u0001B8viF*\u0012A\u001e\t\u0004KIL\u0014\u0001B8viJ*\u0012!\u001f\t\u0004KId\u0014\u0001B8viN*\u0012\u0001 \t\u0004KI|\u0014\u0001B8viR*\u0012a \t\u0004KI\u0014\u0015\u0001B8viV*\"!!\u0002\u0011\u0007\u0015\u0012X)\u0001\u0003pkR4TCAA\u0006!\r)#\u000fS\u0001\u0005_V$x'\u0006\u0002\u0002\u0012A\u0019QE]&\u0002\t=,H\u000fO\u000b\u0003\u0003/\u00012!\n:O\u0003\u0011yW\u000f^\u001d\u0016\u0005\u0005u\u0001cA\u0013s#\u0006Y1M]3bi\u0016dunZ5d)\u0011\t\u0019#!\u000b\u0011\u0007}\t)#C\u0002\u0002(\u0001\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0007\u0003W\u0011\u0002\u0019\u00013\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\r\u0011\t\u0005M\u0012QH\u0007\u0003\u0003kQA!a\u000e\u0002:\u0005!A.\u00198h\u0015\t\tY$\u0001\u0003kCZ\f\u0017\u0002BA \u0003k\u0011aa\u0015;sS:<\u0007")
public class UnzipWith10<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>
extends GraphStage<FanOutShape10<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> {
    private final Function1<In, Tuple10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> unzipper;
    private final FanOutShape10<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> shape;

    public Function1<In, Tuple10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith10");
    }

    @Override
    public FanOutShape10<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape10)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape10)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape10)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape10)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape10)this.shape()).out4();
    }

    public Outlet<A6> out5() {
        return ((FanOutShape10)this.shape()).out5();
    }

    public Outlet<A7> out6() {
        return ((FanOutShape10)this.shape()).out6();
    }

    public Outlet<A8> out7() {
        return ((FanOutShape10)this.shape()).out7();
    }

    public Outlet<A9> out8() {
        return ((FanOutShape10)this.shape()).out8();
    }

    public Outlet<A10> out9() {
        return ((FanOutShape10)this.shape()).out9();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending4;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending5;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending6;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending7;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending8;
            private boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending9;
            private final /* synthetic */ UnzipWith10 $outer;

            public int akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending4 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending5() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending5;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending5_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending5 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending6() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending6;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending6_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending6 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending7() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending7;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending7_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending7 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending8() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending8;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending8_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending8 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith10$$anon$$pending9() {
                return this.akka$stream$scaladsl$UnzipWith10$$anon$$pending9;
            }

            public void akka$stream$scaladsl$UnzipWith10$$anon$$pending9_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending9 = x$1;
            }

            public /* synthetic */ UnzipWith10 akka$stream$scaladsl$UnzipWith10$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount = 10;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning = 10;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending4 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending5 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending6 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending7 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending8 = true;
                this.akka$stream$scaladsl$UnzipWith10$$anon$$pending9 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10> elem = this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending4_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out5())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out5(), elem._6());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending5_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out6())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out6(), elem._7());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending6_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out7())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out7(), elem._8());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending7_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out8())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out8(), elem._9());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending8_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out9())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().out9(), elem._10());
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending9_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending5_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending5()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending6_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending6()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending7_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending7()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending8_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending8()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
                this.setHandler($outer.out9(), new OutHandler(this){
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending9_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pending9()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith10$$anon$$$outer().in());
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
        return "UnzipWith10";
    }

    public UnzipWith10(Function1<In, Tuple10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape10("UnzipWith10");
    }
}

