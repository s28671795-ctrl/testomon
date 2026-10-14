/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape16;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith16$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple16;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ue\u0001\u0002\u000e\u001c\u0001\tB\u0001\u0002\u001c\u0001\u0003\u0006\u0004%\t!\u001c\u0005\ti\u0002\u0011\t\u0011)A\u0005]\")Q\u000f\u0001C\u0001m\")!\u0010\u0001C!w\"Aq\u0010\u0001b\u0001\n\u0003\n\t\u0001C\u0004\u0002\u0004\u0001\u0001\u000b\u0011\u0002\u0016\t\u000f\u0005\u0015\u0001\u0001\"\u0001\u0002\b!9\u0011q\u0002\u0001\u0005\u0002\u0005E\u0001bBA\r\u0001\u0011\u0005\u00111\u0004\u0005\b\u0003?\u0001A\u0011AA\u0011\u0011\u001d\t)\u0003\u0001C\u0001\u0003OAq!a\u000b\u0001\t\u0003\ti\u0003C\u0004\u00022\u0001!\t!a\r\t\u000f\u0005]\u0002\u0001\"\u0001\u0002:!9\u0011Q\b\u0001\u0005\u0002\u0005}\u0002bBA\"\u0001\u0011\u0005\u0011Q\t\u0005\b\u0003\u0013\u0002A\u0011AA&\u0011\u001d\ty\u0005\u0001C\u0001\u0003#Bq!!\u0016\u0001\t\u0003\t9\u0006C\u0004\u0002\\\u0001!\t!!\u0018\t\u000f\u0005\u0005\u0004\u0001\"\u0001\u0002d!9\u0011q\r\u0001\u0005\u0002\u0005%\u0004bBA7\u0001\u0011\u0005\u0011q\u000e\u0005\b\u0003g\u0002A\u0011IA;\u0011\u001d\t\t\t\u0001C!\u0003\u0007\u00131\"\u00168{SB<\u0016\u000e\u001e52m)\u0011A$H\u0001\tg\u000e\fG.\u00193tY*\u0011adH\u0001\u0007gR\u0014X-Y7\u000b\u0003\u0001\nA!Y6lC\u000e\u0001QCE\u00121{\u0001\u001be)\u0013'P%VC6LX1eO*\u001c\"\u0001\u0001\u0013\u0011\u0007\u0015B#&D\u0001'\u0015\t9S$A\u0003ti\u0006<W-\u0003\u0002*M\tQqI]1qQN#\u0018mZ3\u0011'-bc\u0006P C\u000b\"[e*\u0015+X5v\u00037MZ5\u000e\u0003uI!!L\u000f\u0003\u001b\u0019\u000bgnT;u'\"\f\u0007/Z\u00197!\ty\u0003\u0007\u0004\u0001\u0005\u000bE\u0002!\u0019\u0001\u001a\u0003\u0005%s\u0017CA\u001a:!\t!t'D\u00016\u0015\u00051\u0014!B:dC2\f\u0017B\u0001\u001d6\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u000e\u001e\n\u0005m*$aA!osB\u0011q&\u0010\u0003\u0006}\u0001\u0011\rA\r\u0002\u0003\u0003F\u0002\"a\f!\u0005\u000b\u0005\u0003!\u0019\u0001\u001a\u0003\u0005\u0005\u0013\u0004CA\u0018D\t\u0015!\u0005A1\u00013\u0005\t\t5\u0007\u0005\u00020\r\u0012)q\t\u0001b\u0001e\t\u0011\u0011\t\u000e\t\u0003_%#QA\u0013\u0001C\u0002I\u0012!!Q\u001b\u0011\u0005=bE!B'\u0001\u0005\u0004\u0011$AA!7!\tys\nB\u0003Q\u0001\t\u0007!G\u0001\u0002BoA\u0011qF\u0015\u0003\u0006'\u0002\u0011\rA\r\u0002\u0003\u0003b\u0002\"aL+\u0005\u000bY\u0003!\u0019\u0001\u001a\u0003\u0005\u0005K\u0004CA\u0018Y\t\u0015I\u0006A1\u00013\u0005\r\t\u0015\u0007\r\t\u0003_m#Q\u0001\u0018\u0001C\u0002I\u00121!Q\u00192!\tyc\fB\u0003`\u0001\t\u0007!GA\u0002BcI\u0002\"aL1\u0005\u000b\t\u0004!\u0019\u0001\u001a\u0003\u0007\u0005\u000b4\u0007\u0005\u00020I\u0012)Q\r\u0001b\u0001e\t\u0019\u0011)\r\u001b\u0011\u0005=:G!\u00025\u0001\u0005\u0004\u0011$aA!2kA\u0011qF\u001b\u0003\u0006W\u0002\u0011\rA\r\u0002\u0004\u0003F2\u0014\u0001C;ou&\u0004\b/\u001a:\u0016\u00039\u0004B\u0001N8/c&\u0011\u0001/\u000e\u0002\n\rVt7\r^5p]F\u0002\"\u0003\u000e:=\u007f\t+\u0005j\u0013(R)^SV\fY2gS&\u00111/\u000e\u0002\b)V\u0004H.Z\u00197\u0003%)hN_5qa\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0003of\u00042\u0003\u001f\u0001/y}\u0012U\tS&O#R;&,\u00181dM&l\u0011a\u0007\u0005\u0006Y\u000e\u0001\rA\\\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#\u0001?\u0011\u0005-j\u0018B\u0001@\u001e\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002U\u000511\u000f[1qK\u0002\n!!\u001b8\u0016\u0005\u0005%\u0001\u0003B\u0016\u0002\f9J1!!\u0004\u001e\u0005\u0015Ie\u000e\\3u\u0003\u0011yW\u000f\u001e\u0019\u0016\u0005\u0005M\u0001\u0003B\u0016\u0002\u0016qJ1!a\u0006\u001e\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;2+\t\ti\u0002\u0005\u0003,\u0003+y\u0014\u0001B8viJ*\"!a\t\u0011\t-\n)BQ\u0001\u0005_V$8'\u0006\u0002\u0002*A!1&!\u0006F\u0003\u0011yW\u000f\u001e\u001b\u0016\u0005\u0005=\u0002\u0003B\u0016\u0002\u0016!\u000bAa\\;ukU\u0011\u0011Q\u0007\t\u0005W\u0005U1*\u0001\u0003pkR4TCAA\u001e!\u0011Y\u0013Q\u0003(\u0002\t=,HoN\u000b\u0003\u0003\u0003\u0002BaKA\u000b#\u0006!q.\u001e;9+\t\t9\u0005\u0005\u0003,\u0003+!\u0016\u0001B8vif*\"!!\u0014\u0011\t-\n)bV\u0001\u0006_V$\u0018\u0007M\u000b\u0003\u0003'\u0002BaKA\u000b5\u0006)q.\u001e;2cU\u0011\u0011\u0011\f\t\u0005W\u0005UQ,A\u0003pkR\f$'\u0006\u0002\u0002`A!1&!\u0006a\u0003\u0015yW\u000f^\u00194+\t\t)\u0007\u0005\u0003,\u0003+\u0019\u0017!B8viF\"TCAA6!\u0011Y\u0013Q\u00034\u0002\u000b=,H/M\u001b\u0016\u0005\u0005E\u0004\u0003B\u0016\u0002\u0016%\f1b\u0019:fCR,Gj\\4jGR!\u0011qOA?!\r)\u0013\u0011P\u0005\u0004\u0003w2#aD$sCBD7\u000b^1hK2{w-[2\t\r\u0005}\u0004\u00041\u0001}\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<GCAAC!\u0011\t9)!%\u000e\u0005\u0005%%\u0002BAF\u0003\u001b\u000bA\u0001\\1oO*\u0011\u0011qR\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002\u0014\u0006%%AB*ue&tw\r")
public class UnzipWith16<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>
extends GraphStage<FanOutShape16<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>> {
    private final Function1<In, Tuple16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>> unzipper;
    private final FanOutShape16<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> shape;

    public Function1<In, Tuple16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith16");
    }

    @Override
    public FanOutShape16<In, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape16)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape16)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape16)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape16)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape16)this.shape()).out4();
    }

    public Outlet<A6> out5() {
        return ((FanOutShape16)this.shape()).out5();
    }

    public Outlet<A7> out6() {
        return ((FanOutShape16)this.shape()).out6();
    }

    public Outlet<A8> out7() {
        return ((FanOutShape16)this.shape()).out7();
    }

    public Outlet<A9> out8() {
        return ((FanOutShape16)this.shape()).out8();
    }

    public Outlet<A10> out9() {
        return ((FanOutShape16)this.shape()).out9();
    }

    public Outlet<A11> out10() {
        return ((FanOutShape16)this.shape()).out10();
    }

    public Outlet<A12> out11() {
        return ((FanOutShape16)this.shape()).out11();
    }

    public Outlet<A13> out12() {
        return ((FanOutShape16)this.shape()).out12();
    }

    public Outlet<A14> out13() {
        return ((FanOutShape16)this.shape()).out13();
    }

    public Outlet<A15> out14() {
        return ((FanOutShape16)this.shape()).out14();
    }

    public Outlet<A16> out15() {
        return ((FanOutShape16)this.shape()).out15();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending4;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending5;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending6;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending7;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending8;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending9;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending10;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending11;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending12;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending13;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending14;
            private boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending15;
            private final /* synthetic */ UnzipWith16 $outer;

            public int akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending4 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending5() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending5;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending5_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending5 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending6() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending6;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending6_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending6 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending7() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending7;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending7_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending7 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending8() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending8;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending8_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending8 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending9() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending9;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending9_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending9 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending10() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending10;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending10_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending10 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending11() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending11;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending11_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending11 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending12() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending12;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending12_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending12 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending13() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending13;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending13_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending13 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending14() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending14;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending14_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending14 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith16$$anon$$pending15() {
                return this.akka$stream$scaladsl$UnzipWith16$$anon$$pending15;
            }

            public void akka$stream$scaladsl$UnzipWith16$$anon$$pending15_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending15 = x$1;
            }

            public /* synthetic */ UnzipWith16 akka$stream$scaladsl$UnzipWith16$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount = 16;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning = 16;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending4 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending5 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending6 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending7 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending8 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending9 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending10 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending11 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending12 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending13 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending14 = true;
                this.akka$stream$scaladsl$UnzipWith16$$anon$$pending15 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16> elem = this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending4_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out5())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out5(), elem._6());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending5_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out6())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out6(), elem._7());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending6_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out7())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out7(), elem._8());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending7_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out8())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out8(), elem._9());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending8_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out9())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out9(), elem._10());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending9_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out10())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out10(), elem._11());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending10_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out11())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out11(), elem._12());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending11_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out12())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out12(), elem._13());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending12_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out13())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out13(), elem._14());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending13_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out14())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out14(), elem._15());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending14_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out15())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().out15(), elem._16());
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending15_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending5_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending5()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending6_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending6()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending7_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending7()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending8_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending8()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending9_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending9()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending10_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending10()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending11_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending11()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                this.setHandler($outer.out12(), new OutHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending12_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending12()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                this.setHandler($outer.out13(), new OutHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending13_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending13()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                this.setHandler($outer.out14(), new OutHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending14_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending14()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
                this.setHandler($outer.out15(), new OutHandler(this){
                    private final /* synthetic */ $anon$148 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending15_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pending15()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith16$$anon$$$outer().in());
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
        return "UnzipWith16";
    }

    public UnzipWith16(Function1<In, Tuple16<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape16("UnzipWith16");
    }
}

