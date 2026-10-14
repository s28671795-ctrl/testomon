/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape7;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith7$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple7;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]a\u0001B\t\u0013\u0001eA\u0001\u0002\u0013\u0001\u0003\u0006\u0004%\t!\u0013\u0005\t!\u0002\u0011\t\u0011)A\u0005\u0015\")\u0011\u000b\u0001C\u0001%\")a\u000b\u0001C!/\"91\f\u0001b\u0001\n\u0003b\u0006BB/\u0001A\u0003%\u0011\u0005C\u0003_\u0001\u0011\u0005q\fC\u0003d\u0001\u0011\u0005A\rC\u0003i\u0001\u0011\u0005\u0011\u000eC\u0003l\u0001\u0011\u0005A\u000eC\u0003o\u0001\u0011\u0005q\u000eC\u0003r\u0001\u0011\u0005!\u000fC\u0003u\u0001\u0011\u0005Q\u000fC\u0003x\u0001\u0011\u0005\u0001\u0010C\u0003{\u0001\u0011\u00053\u0010C\u0004\u0002\u0004\u0001!\t%!\u0002\u0003\u0015Us'0\u001b9XSRDwG\u0003\u0002\u0014)\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u0016-\u000511\u000f\u001e:fC6T\u0011aF\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0013i9Cg\u000e\u001e>\u0001\u000e35C\u0001\u0001\u001c!\rar$I\u0007\u0002;)\u0011a\u0004F\u0001\u0006gR\fw-Z\u0005\u0003Au\u0011!b\u0012:ba\"\u001cF/Y4f!)\u00113%J\u001a7sqz$)R\u0007\u0002)%\u0011A\u0005\u0006\u0002\r\r\u0006tw*\u001e;TQ\u0006\u0004Xm\u000e\t\u0003M\u001db\u0001\u0001B\u0003)\u0001\t\u0007\u0011F\u0001\u0002J]F\u0011!\u0006\r\t\u0003W9j\u0011\u0001\f\u0006\u0002[\u0005)1oY1mC&\u0011q\u0006\f\u0002\b\u001d>$\b.\u001b8h!\tY\u0013'\u0003\u00023Y\t\u0019\u0011I\\=\u0011\u0005\u0019\"D!B\u001b\u0001\u0005\u0004I#AA!2!\t1s\u0007B\u00039\u0001\t\u0007\u0011F\u0001\u0002BeA\u0011aE\u000f\u0003\u0006w\u0001\u0011\r!\u000b\u0002\u0003\u0003N\u0002\"AJ\u001f\u0005\u000by\u0002!\u0019A\u0015\u0003\u0005\u0005#\u0004C\u0001\u0014A\t\u0015\t\u0005A1\u0001*\u0005\t\tU\u0007\u0005\u0002'\u0007\u0012)A\t\u0001b\u0001S\t\u0011\u0011I\u000e\t\u0003M\u0019#Qa\u0012\u0001C\u0002%\u0012!!Q\u001c\u0002\u0011Ut'0\u001b9qKJ,\u0012A\u0013\t\u0005W-+S*\u0003\u0002MY\tIa)\u001e8di&|g.\r\t\nW9\u001bd'\u000f\u001f@\u0005\u0016K!a\u0014\u0017\u0003\rQ+\b\u000f\\38\u0003%)hN_5qa\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0003'V\u0003\"\u0002\u0016\u0001&gYJDh\u0010\"F\u001b\u0005\u0011\u0002\"\u0002%\u0004\u0001\u0004Q\u0015!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\t\u0001\f\u0005\u0002#3&\u0011!\f\u0006\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#A\u0011\u0002\rMD\u0017\r]3!\u0003\tIg.F\u0001a!\r\u0011\u0013-J\u0005\u0003ER\u0011Q!\u00138mKR\fAa\\;uaU\tQ\rE\u0002#MNJ!a\u001a\u000b\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f^\u0019\u0016\u0003)\u00042A\t47\u0003\u0011yW\u000f\u001e\u001a\u0016\u00035\u00042A\t4:\u0003\u0011yW\u000f^\u001a\u0016\u0003A\u00042A\t4=\u0003\u0011yW\u000f\u001e\u001b\u0016\u0003M\u00042A\t4@\u0003\u0011yW\u000f^\u001b\u0016\u0003Y\u00042A\t4C\u0003\u0011yW\u000f\u001e\u001c\u0016\u0003e\u00042A\t4F\u0003-\u0019'/Z1uK2{w-[2\u0015\u0005q|\bC\u0001\u000f~\u0013\tqXDA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0019\t\ta\u0004a\u00011\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\bA!\u0011\u0011BA\n\u001b\t\tYA\u0003\u0003\u0002\u000e\u0005=\u0011\u0001\u00027b]\u001eT!!!\u0005\u0002\t)\fg/Y\u0005\u0005\u0003+\tYA\u0001\u0004TiJLgn\u001a")
public class UnzipWith7<In, A1, A2, A3, A4, A5, A6, A7>
extends GraphStage<FanOutShape7<In, A1, A2, A3, A4, A5, A6, A7>> {
    private final Function1<In, Tuple7<A1, A2, A3, A4, A5, A6, A7>> unzipper;
    private final FanOutShape7<In, A1, A2, A3, A4, A5, A6, A7> shape;

    public Function1<In, Tuple7<A1, A2, A3, A4, A5, A6, A7>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith7");
    }

    @Override
    public FanOutShape7<In, A1, A2, A3, A4, A5, A6, A7> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape7)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape7)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape7)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape7)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape7)this.shape()).out4();
    }

    public Outlet<A6> out5() {
        return ((FanOutShape7)this.shape()).out5();
    }

    public Outlet<A7> out6() {
        return ((FanOutShape7)this.shape()).out6();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending4;
            private boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending5;
            private boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending6;
            private final /* synthetic */ UnzipWith7 $outer;

            public int akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending4 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending5() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$pending5;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$pending5_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending5 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith7$$anon$$pending6() {
                return this.akka$stream$scaladsl$UnzipWith7$$anon$$pending6;
            }

            public void akka$stream$scaladsl$UnzipWith7$$anon$$pending6_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending6 = x$1;
            }

            public /* synthetic */ UnzipWith7 akka$stream$scaladsl$UnzipWith7$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount = 7;
                this.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning = 7;
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending4 = true;
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending5 = true;
                this.akka$stream$scaladsl$UnzipWith7$$anon$$pending6 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$31 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple7<A1, A2, A3, A4, A5, A6, A7> elem = this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending4_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out5())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out5(), elem._6());
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending5_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out6())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().out6(), elem._7());
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending6_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending5_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending5()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$31 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending6_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pending6()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith7$$anon$$$outer().in());
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
        return "UnzipWith7";
    }

    public UnzipWith7(Function1<In, Tuple7<A1, A2, A3, A4, A5, A6, A7>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape7("UnzipWith7");
    }
}

