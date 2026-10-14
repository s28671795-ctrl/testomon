/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape11;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith11$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple11;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=c\u0001B\u000b\u0017\u0001uA\u0001\u0002\u0017\u0001\u0003\u0006\u0004%\t!\u0017\u0005\tA\u0002\u0011\t\u0011)A\u00055\")\u0011\r\u0001C\u0001E\")a\r\u0001C!O\"91\u000e\u0001b\u0001\n\u0003b\u0007BB7\u0001A\u0003%Q\u0005C\u0003o\u0001\u0011\u0005q\u000eC\u0003t\u0001\u0011\u0005A\u000fC\u0003y\u0001\u0011\u0005\u0011\u0010C\u0003|\u0001\u0011\u0005A\u0010C\u0003\u007f\u0001\u0011\u0005q\u0010C\u0004\u0002\u0004\u0001!\t!!\u0002\t\u000f\u0005%\u0001\u0001\"\u0001\u0002\f!9\u0011q\u0002\u0001\u0005\u0002\u0005E\u0001bBA\u000b\u0001\u0011\u0005\u0011q\u0003\u0005\b\u00037\u0001A\u0011AA\u000f\u0011\u001d\t\t\u0003\u0001C\u0001\u0003GAq!a\n\u0001\t\u0003\tI\u0003C\u0004\u0002.\u0001!\t%a\f\t\u000f\u0005m\u0002\u0001\"\u0011\u0002>\tYQK\u001c>ja^KG\u000f[\u00192\u0015\t9\u0002$\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\tI\"$\u0001\u0004tiJ,\u0017-\u001c\u0006\u00027\u0005!\u0011m[6b\u0007\u0001)RBH\u00169wy\nEi\u0012&N!N36C\u0001\u0001 !\r\u00013%J\u0007\u0002C)\u0011!\u0005G\u0001\u0006gR\fw-Z\u0005\u0003I\u0005\u0012!b\u0012:ba\"\u001cF/Y4f!91s%K\u001c;{\u0001\u001be)\u0013'P%Vk\u0011\u0001G\u0005\u0003Qa\u0011QBR1o\u001fV$8\u000b[1qKF\n\u0004C\u0001\u0016,\u0019\u0001!Q\u0001\f\u0001C\u00025\u0012!!\u00138\u0012\u00059\"\u0004CA\u00183\u001b\u0005\u0001$\"A\u0019\u0002\u000bM\u001c\u0017\r\\1\n\u0005M\u0002$a\u0002(pi\"Lgn\u001a\t\u0003_UJ!A\u000e\u0019\u0003\u0007\u0005s\u0017\u0010\u0005\u0002+q\u0011)\u0011\b\u0001b\u0001[\t\u0011\u0011)\r\t\u0003Um\"Q\u0001\u0010\u0001C\u00025\u0012!!\u0011\u001a\u0011\u0005)rD!B \u0001\u0005\u0004i#AA!4!\tQ\u0013\tB\u0003C\u0001\t\u0007QF\u0001\u0002BiA\u0011!\u0006\u0012\u0003\u0006\u000b\u0002\u0011\r!\f\u0002\u0003\u0003V\u0002\"AK$\u0005\u000b!\u0003!\u0019A\u0017\u0003\u0005\u00053\u0004C\u0001\u0016K\t\u0015Y\u0005A1\u0001.\u0005\t\tu\u0007\u0005\u0002+\u001b\u0012)a\n\u0001b\u0001[\t\u0011\u0011\t\u000f\t\u0003UA#Q!\u0015\u0001C\u00025\u0012!!Q\u001d\u0011\u0005)\u001aF!\u0002+\u0001\u0005\u0004i#aA!2aA\u0011!F\u0016\u0003\u0006/\u0002\u0011\r!\f\u0002\u0004\u0003F\n\u0014\u0001C;ou&\u0004\b/\u001a:\u0016\u0003i\u0003BaL.*;&\u0011A\f\r\u0002\n\rVt7\r^5p]F\u0002Rb\f08uu\u00025IR%M\u001fJ+\u0016BA01\u0005\u001d!V\u000f\u001d7fcE\n\u0011\"\u001e8{SB\u0004XM\u001d\u0011\u0002\rqJg.\u001b;?)\t\u0019W\r\u0005\be\u0001%:$(\u0010!D\r&cuJU+\u000e\u0003YAQ\u0001W\u0002A\u0002i\u000b\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005A\u0007C\u0001\u0014j\u0013\tQ\u0007D\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u001d5ba\u0016,\u0012!J\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0005%tW#\u00019\u0011\u0007\u0019\n\u0018&\u0003\u0002s1\t)\u0011J\u001c7fi\u0006!q.\u001e;1+\u0005)\bc\u0001\u0014wo%\u0011q\u000f\u0007\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H/M\u000b\u0002uB\u0019aE\u001e\u001e\u0002\t=,HOM\u000b\u0002{B\u0019aE^\u001f\u0002\t=,HoM\u000b\u0003\u0003\u0003\u00012A\n<A\u0003\u0011yW\u000f\u001e\u001b\u0016\u0005\u0005\u001d\u0001c\u0001\u0014w\u0007\u0006!q.\u001e;6+\t\ti\u0001E\u0002'm\u001a\u000bAa\\;umU\u0011\u00111\u0003\t\u0004MYL\u0015\u0001B8vi^*\"!!\u0007\u0011\u0007\u00192H*\u0001\u0003pkRDTCAA\u0010!\r1coT\u0001\u0005_V$\u0018(\u0006\u0002\u0002&A\u0019aE\u001e*\u0002\u000b=,H/\r\u0019\u0016\u0005\u0005-\u0002c\u0001\u0014w+\u0006Y1M]3bi\u0016dunZ5d)\u0011\t\t$a\u000e\u0011\u0007\u0001\n\u0019$C\u0002\u00026\u0005\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0007\u0003s\u0019\u0002\u0019\u00015\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u0010\u0011\t\u0005\u0005\u00131J\u0007\u0003\u0003\u0007RA!!\u0012\u0002H\u0005!A.\u00198h\u0015\t\tI%\u0001\u0003kCZ\f\u0017\u0002BA'\u0003\u0007\u0012aa\u0015;sS:<\u0007")
public class UnzipWith11<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>
extends GraphStage<FanOutShape11<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> {
    private final Function1<In, Tuple11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> unzipper;
    private final FanOutShape11<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> shape;

    public Function1<In, Tuple11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith11");
    }

    @Override
    public FanOutShape11<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape11)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape11)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape11)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape11)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape11)this.shape()).out4();
    }

    public Outlet<A6> out5() {
        return ((FanOutShape11)this.shape()).out5();
    }

    public Outlet<A7> out6() {
        return ((FanOutShape11)this.shape()).out6();
    }

    public Outlet<A8> out7() {
        return ((FanOutShape11)this.shape()).out7();
    }

    public Outlet<A9> out8() {
        return ((FanOutShape11)this.shape()).out8();
    }

    public Outlet<A10> out9() {
        return ((FanOutShape11)this.shape()).out9();
    }

    public Outlet<A11> out10() {
        return ((FanOutShape11)this.shape()).out10();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending4;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending5;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending6;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending7;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending8;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending9;
            private boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending10;
            private final /* synthetic */ UnzipWith11 $outer;

            public int akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending4 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending5() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending5;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending5_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending5 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending6() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending6;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending6_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending6 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending7() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending7;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending7_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending7 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending8() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending8;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending8_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending8 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending9() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending9;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending9_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending9 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith11$$anon$$pending10() {
                return this.akka$stream$scaladsl$UnzipWith11$$anon$$pending10;
            }

            public void akka$stream$scaladsl$UnzipWith11$$anon$$pending10_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending10 = x$1;
            }

            public /* synthetic */ UnzipWith11 akka$stream$scaladsl$UnzipWith11$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount = 11;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning = 11;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending4 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending5 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending6 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending7 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending8 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending9 = true;
                this.akka$stream$scaladsl$UnzipWith11$$anon$$pending10 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11> elem = this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending4_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out5())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out5(), elem._6());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending5_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out6())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out6(), elem._7());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending6_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out7())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out7(), elem._8());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending7_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out8())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out8(), elem._9());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending8_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out9())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out9(), elem._10());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending9_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out10())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().out10(), elem._11());
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending10_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending5_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending5()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending6_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending6()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending7_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending7()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending8_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending8()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending9_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending9()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending10_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pending10()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith11$$anon$$$outer().in());
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
        return "UnzipWith11";
    }

    public UnzipWith11(Function1<In, Tuple11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape11("UnzipWith11");
    }
}

