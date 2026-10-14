/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape5;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith5$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple5;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u4Aa\u0004\t\u0001/!A\u0001\t\u0001BC\u0002\u0013\u0005\u0011\t\u0003\u0005I\u0001\t\u0005\t\u0015!\u0003C\u0011\u0015I\u0005\u0001\"\u0001K\u0011\u0015q\u0005\u0001\"\u0011P\u0011\u001d\u0019\u0006A1A\u0005BQCa!\u0016\u0001!\u0002\u0013y\u0002\"\u0002,\u0001\t\u00039\u0006\"B.\u0001\t\u0003a\u0006\"\u00021\u0001\t\u0003\t\u0007\"B2\u0001\t\u0003!\u0007\"\u00024\u0001\t\u00039\u0007\"B5\u0001\t\u0003Q\u0007\"\u00027\u0001\t\u0003j\u0007\"B:\u0001\t\u0003\"(AC+ou&\u0004x+\u001b;ik)\u0011\u0011CE\u0001\tg\u000e\fG.\u00193tY*\u00111\u0003F\u0001\u0007gR\u0014X-Y7\u000b\u0003U\tA!Y6lC\u000e\u0001Qc\u0002\r&eUB4HP\n\u0003\u0001e\u00012AG\u000f \u001b\u0005Y\"B\u0001\u000f\u0013\u0003\u0015\u0019H/Y4f\u0013\tq2D\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004\u0002\u0002I\u0011$cQ:$(P\u0007\u0002%%\u0011!E\u0005\u0002\r\r\u0006tw*\u001e;TQ\u0006\u0004X-\u000e\t\u0003I\u0015b\u0001\u0001B\u0003'\u0001\t\u0007qE\u0001\u0002J]F\u0011\u0001F\f\t\u0003S1j\u0011A\u000b\u0006\u0002W\u0005)1oY1mC&\u0011QF\u000b\u0002\b\u001d>$\b.\u001b8h!\tIs&\u0003\u00021U\t\u0019\u0011I\\=\u0011\u0005\u0011\u0012D!B\u001a\u0001\u0005\u00049#AA!2!\t!S\u0007B\u00037\u0001\t\u0007qE\u0001\u0002BeA\u0011A\u0005\u000f\u0003\u0006s\u0001\u0011\ra\n\u0002\u0003\u0003N\u0002\"\u0001J\u001e\u0005\u000bq\u0002!\u0019A\u0014\u0003\u0005\u0005#\u0004C\u0001\u0013?\t\u0015y\u0004A1\u0001(\u0005\t\tU'\u0001\u0005v]jL\u0007\u000f]3s+\u0005\u0011\u0005\u0003B\u0015DG\u0015K!\u0001\u0012\u0016\u0003\u0013\u0019+hn\u0019;j_:\f\u0004cB\u0015GcQ:$(P\u0005\u0003\u000f*\u0012a\u0001V;qY\u0016,\u0014!C;ou&\u0004\b/\u001a:!\u0003\u0019a\u0014N\\5u}Q\u00111*\u0014\t\t\u0019\u0002\u0019\u0013\u0007N\u001c;{5\t\u0001\u0003C\u0003A\u0007\u0001\u0007!)A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012\u0001\u0015\t\u0003AEK!A\u0015\n\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-F\u0001 \u0003\u0019\u0019\b.\u00199fA\u0005\u0011\u0011N\\\u000b\u00021B\u0019\u0001%W\u0012\n\u0005i\u0013\"!B%oY\u0016$\u0018\u0001B8viB*\u0012!\u0018\t\u0004Ay\u000b\u0014BA0\u0013\u0005\u0019yU\u000f\u001e7fi\u0006!q.\u001e;2+\u0005\u0011\u0007c\u0001\u0011_i\u0005!q.\u001e;3+\u0005)\u0007c\u0001\u0011_o\u0005!q.\u001e;4+\u0005A\u0007c\u0001\u0011_u\u0005!q.\u001e;5+\u0005Y\u0007c\u0001\u0011_{\u0005Y1M]3bi\u0016dunZ5d)\tq\u0017\u000f\u0005\u0002\u001b_&\u0011\u0001o\u0007\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\")!/\u0004a\u0001!\u0006\u0019\u0012N\u001c5fe&$X\rZ!uiJL'-\u001e;fg\u0006AAo\\*ue&tw\rF\u0001v!\t180D\u0001x\u0015\tA\u00180\u0001\u0003mC:<'\"\u0001>\u0002\t)\fg/Y\u0005\u0003y^\u0014aa\u0015;sS:<\u0007")
public class UnzipWith5<In, A1, A2, A3, A4, A5>
extends GraphStage<FanOutShape5<In, A1, A2, A3, A4, A5>> {
    private final Function1<In, Tuple5<A1, A2, A3, A4, A5>> unzipper;
    private final FanOutShape5<In, A1, A2, A3, A4, A5> shape;

    public Function1<In, Tuple5<A1, A2, A3, A4, A5>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith5");
    }

    @Override
    public FanOutShape5<In, A1, A2, A3, A4, A5> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape5)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape5)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape5)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape5)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape5)this.shape()).out4();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending4;
            private final /* synthetic */ UnzipWith5 $outer;

            public int akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith5$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith5$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith5$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith5$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith5$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith5$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith5$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith5$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith5$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith5$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith5$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending4 = x$1;
            }

            public /* synthetic */ UnzipWith5 akka$stream$scaladsl$UnzipWith5$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount = 5;
                this.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning = 5;
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith5$$anon$$pending4 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$16 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple5<A1, A2, A3, A4, A5> elem = this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending4_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$16 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith5$$anon$$$outer().in());
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
        return "UnzipWith5";
    }

    public UnzipWith5(Function1<In, Tuple5<A1, A2, A3, A4, A5>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape5("UnzipWith5");
    }
}

