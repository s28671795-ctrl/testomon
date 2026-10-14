/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape12;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith12$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple12;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005uc\u0001\u0002\f\u0018\u0001yA\u0001\u0002\u0018\u0001\u0003\u0006\u0004%\t!\u0018\u0005\tI\u0002\u0011\t\u0011)A\u0005=\")Q\r\u0001C\u0001M\")!\u000e\u0001C!W\"9q\u000e\u0001b\u0001\n\u0003\u0002\bBB9\u0001A\u0003%a\u0005C\u0003s\u0001\u0011\u00051\u000fC\u0003x\u0001\u0011\u0005\u0001\u0010C\u0003}\u0001\u0011\u0005Q\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0011\u0011\u0001\u0005\b\u0003\u000b\u0001A\u0011AA\u0004\u0011\u001d\tY\u0001\u0001C\u0001\u0003\u001bAq!!\u0005\u0001\t\u0003\t\u0019\u0002C\u0004\u0002\u0018\u0001!\t!!\u0007\t\u000f\u0005u\u0001\u0001\"\u0001\u0002 !9\u00111\u0005\u0001\u0005\u0002\u0005\u0015\u0002bBA\u0015\u0001\u0011\u0005\u00111\u0006\u0005\b\u0003_\u0001A\u0011AA\u0019\u0011\u001d\t)\u0004\u0001C\u0001\u0003oAq!a\u000f\u0001\t\u0003\ni\u0004C\u0004\u0002J\u0001!\t%a\u0013\u0003\u0017Us'0\u001b9XSRD\u0017G\r\u0006\u00031e\t\u0001b]2bY\u0006$7\u000f\u001c\u0006\u00035m\taa\u001d;sK\u0006l'\"\u0001\u000f\u0002\t\u0005\\7.Y\u0002\u0001+9yB&\u000f\u001f@\u0005\u0016C5JT)U/j\u001b\"\u0001\u0001\u0011\u0011\u0007\u0005\"c%D\u0001#\u0015\t\u0019\u0013$A\u0003ti\u0006<W-\u0003\u0002&E\tQqI]1qQN#\u0018mZ3\u0011\u001f\u001dB#\u0006O\u001e?\u0003\u0012;%*\u0014)T-fk\u0011!G\u0005\u0003Se\u0011QBR1o\u001fV$8\u000b[1qKF\u0012\u0004CA\u0016-\u0019\u0001!Q!\f\u0001C\u00029\u0012!!\u00138\u0012\u0005=*\u0004C\u0001\u00194\u001b\u0005\t$\"\u0001\u001a\u0002\u000bM\u001c\u0017\r\\1\n\u0005Q\n$a\u0002(pi\"Lgn\u001a\t\u0003aYJ!aN\u0019\u0003\u0007\u0005s\u0017\u0010\u0005\u0002,s\u0011)!\b\u0001b\u0001]\t\u0011\u0011)\r\t\u0003Wq\"Q!\u0010\u0001C\u00029\u0012!!\u0011\u001a\u0011\u0005-zD!\u0002!\u0001\u0005\u0004q#AA!4!\tY#\tB\u0003D\u0001\t\u0007aF\u0001\u0002BiA\u00111&\u0012\u0003\u0006\r\u0002\u0011\rA\f\u0002\u0003\u0003V\u0002\"a\u000b%\u0005\u000b%\u0003!\u0019\u0001\u0018\u0003\u0005\u00053\u0004CA\u0016L\t\u0015a\u0005A1\u0001/\u0005\t\tu\u0007\u0005\u0002,\u001d\u0012)q\n\u0001b\u0001]\t\u0011\u0011\t\u000f\t\u0003WE#QA\u0015\u0001C\u00029\u0012!!Q\u001d\u0011\u0005-\"F!B+\u0001\u0005\u0004q#aA!2aA\u00111f\u0016\u0003\u00061\u0002\u0011\rA\f\u0002\u0004\u0003F\n\u0004CA\u0016[\t\u0015Y\u0006A1\u0001/\u0005\r\t\u0015GM\u0001\tk:T\u0018\u000e\u001d9feV\ta\f\u0005\u00031?*\n\u0017B\u000112\u0005%1UO\\2uS>t\u0017\u0007\u0005\b1EbZd(\u0011#H\u00156\u00036KV-\n\u0005\r\f$a\u0002+va2,\u0017GM\u0001\nk:T\u0018\u000e\u001d9fe\u0002\na\u0001P5oSRtDCA4j!=A\u0007A\u000b\u001d<}\u0005#uIS'Q'ZKV\"A\f\t\u000bq\u001b\u0001\u0019\u00010\u0002#%t\u0017\u000e^5bY\u0006#HO]5ckR,7/F\u0001m!\t9S.\u0003\u0002o3\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u000bMD\u0017\r]3\u0016\u0003\u0019\naa\u001d5ba\u0016\u0004\u0013AA5o+\u0005!\bcA\u0014vU%\u0011a/\u0007\u0002\u0006\u0013:dW\r^\u0001\u0005_V$\b'F\u0001z!\r9#\u0010O\u0005\u0003wf\u0011aaT;uY\u0016$\u0018\u0001B8viF*\u0012A \t\u0004Oi\\\u0014\u0001B8viJ*\"!a\u0001\u0011\u0007\u001dRh(\u0001\u0003pkR\u001cTCAA\u0005!\r9#0Q\u0001\u0005_V$H'\u0006\u0002\u0002\u0010A\u0019qE\u001f#\u0002\t=,H/N\u000b\u0003\u0003+\u00012a\n>H\u0003\u0011yW\u000f\u001e\u001c\u0016\u0005\u0005m\u0001cA\u0014{\u0015\u0006!q.\u001e;8+\t\t\t\u0003E\u0002(u6\u000bAa\\;uqU\u0011\u0011q\u0005\t\u0004Oi\u0004\u0016\u0001B8vif*\"!!\f\u0011\u0007\u001dR8+A\u0003pkR\f\u0004'\u0006\u0002\u00024A\u0019qE\u001f,\u0002\u000b=,H/M\u0019\u0016\u0005\u0005e\u0002cA\u0014{3\u0006Y1M]3bi\u0016dunZ5d)\u0011\ty$!\u0012\u0011\u0007\u0005\n\t%C\u0002\u0002D\t\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0007\u0003\u000f\"\u0002\u0019\u00017\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0014\u0011\t\u0005=\u0013\u0011L\u0007\u0003\u0003#RA!a\u0015\u0002V\u0005!A.\u00198h\u0015\t\t9&\u0001\u0003kCZ\f\u0017\u0002BA.\u0003#\u0012aa\u0015;sS:<\u0007")
public class UnzipWith12<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>
extends GraphStage<FanOutShape12<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>> {
    private final Function1<In, Tuple12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>> unzipper;
    private final FanOutShape12<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> shape;

    public Function1<In, Tuple12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith12");
    }

    @Override
    public FanOutShape12<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape12)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape12)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape12)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape12)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape12)this.shape()).out4();
    }

    public Outlet<A6> out5() {
        return ((FanOutShape12)this.shape()).out5();
    }

    public Outlet<A7> out6() {
        return ((FanOutShape12)this.shape()).out6();
    }

    public Outlet<A8> out7() {
        return ((FanOutShape12)this.shape()).out7();
    }

    public Outlet<A9> out8() {
        return ((FanOutShape12)this.shape()).out8();
    }

    public Outlet<A10> out9() {
        return ((FanOutShape12)this.shape()).out9();
    }

    public Outlet<A11> out10() {
        return ((FanOutShape12)this.shape()).out10();
    }

    public Outlet<A12> out11() {
        return ((FanOutShape12)this.shape()).out11();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending4;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending5;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending6;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending7;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending8;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending9;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending10;
            private boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending11;
            private final /* synthetic */ UnzipWith12 $outer;

            public int akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending4 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending5() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending5;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending5_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending5 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending6() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending6;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending6_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending6 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending7() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending7;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending7_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending7 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending8() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending8;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending8_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending8 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending9() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending9;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending9_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending9 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending10() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending10;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending10_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending10 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith12$$anon$$pending11() {
                return this.akka$stream$scaladsl$UnzipWith12$$anon$$pending11;
            }

            public void akka$stream$scaladsl$UnzipWith12$$anon$$pending11_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending11 = x$1;
            }

            public /* synthetic */ UnzipWith12 akka$stream$scaladsl$UnzipWith12$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount = 12;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning = 12;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending4 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending5 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending6 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending7 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending8 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending9 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending10 = true;
                this.akka$stream$scaladsl$UnzipWith12$$anon$$pending11 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12> elem = this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending4_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out5())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out5(), elem._6());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending5_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out6())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out6(), elem._7());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending6_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out7())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out7(), elem._8());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending7_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out8())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out8(), elem._9());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending8_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out9())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out9(), elem._10());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending9_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out10())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out10(), elem._11());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending10_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out11())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().out11(), elem._12());
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending11_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending5_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending5()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending6_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending6()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending7_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending7()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending8_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending8()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending9_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending9()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                this.setHandler($outer.out10(), new OutHandler(this){
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending10_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending10()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
                this.setHandler($outer.out11(), new OutHandler(this){
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending11_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pending11()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith12$$anon$$$outer().in());
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
        return "UnzipWith12";
    }

    public UnzipWith12(Function1<In, Tuple12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape12("UnzipWith12");
    }
}

