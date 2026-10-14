/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape2;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith2$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!4A\u0001D\u0007\u0001)!AA\u0007\u0001BC\u0002\u0013\u0005Q\u0007\u0003\u0005=\u0001\t\u0005\t\u0015!\u00037\u0011\u0015i\u0004\u0001\"\u0001?\u0011\u0015\u0011\u0005\u0001\"\u0011D\u0011\u001d9\u0005A1A\u0005B!Ca!\u0013\u0001!\u0002\u0013a\u0002\"\u0002&\u0001\t\u0003Y\u0005\"B(\u0001\t\u0003\u0001\u0006\"\u0002+\u0001\t\u0003)\u0006\"B,\u0001\t\u0003B\u0006\"\u00020\u0001\t\u0003z&AC+ou&\u0004x+\u001b;ie)\u0011abD\u0001\tg\u000e\fG.\u00193tY*\u0011\u0001#E\u0001\u0007gR\u0014X-Y7\u000b\u0003I\tA!Y6lC\u000e\u0001Q\u0003B\u000b#_I\u001a\"\u0001\u0001\f\u0011\u0007]QB$D\u0001\u0019\u0015\tIr\"A\u0003ti\u0006<W-\u0003\u0002\u001c1\tQqI]1qQN#\u0018mZ3\u0011\u000buq\u0002EL\u0019\u000e\u0003=I!aH\b\u0003\u0019\u0019\u000bgnT;u'\"\f\u0007/\u001a\u001a\u0011\u0005\u0005\u0012C\u0002\u0001\u0003\u0006G\u0001\u0011\r\u0001\n\u0002\u0003\u0013:\f\"!J\u0016\u0011\u0005\u0019JS\"A\u0014\u000b\u0003!\nQa]2bY\u0006L!AK\u0014\u0003\u000f9{G\u000f[5oOB\u0011a\u0005L\u0005\u0003[\u001d\u00121!\u00118z!\t\ts\u0006B\u00031\u0001\t\u0007AE\u0001\u0002BcA\u0011\u0011E\r\u0003\u0006g\u0001\u0011\r\u0001\n\u0002\u0003\u0003J\n\u0001\"\u001e8{SB\u0004XM]\u000b\u0002mA!ae\u000e\u0011:\u0013\tAtEA\u0005Gk:\u001cG/[8ocA!aE\u000f\u00182\u0013\tYtE\u0001\u0004UkBdWMM\u0001\nk:T\u0018\u000e\u001d9fe\u0002\na\u0001P5oSRtDCA B!\u0015\u0001\u0005\u0001\t\u00182\u001b\u0005i\u0001\"\u0002\u001b\u0004\u0001\u00041\u0014!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\tA\t\u0005\u0002\u001e\u000b&\u0011ai\u0004\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#\u0001\u000f\u0002\rMD\u0017\r]3!\u0003\tIg.F\u0001M!\riR\nI\u0005\u0003\u001d>\u0011Q!\u00138mKR\fAa\\;uaU\t\u0011\u000bE\u0002\u001e%:J!aU\b\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f^\u0019\u0016\u0003Y\u00032!\b*2\u0003-\u0019'/Z1uK2{w-[2\u0015\u0005ec\u0006CA\f[\u0013\tY\u0006DA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015i&\u00021\u0001E\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<G#\u00011\u0011\u0005\u00054W\"\u00012\u000b\u0005\r$\u0017\u0001\u00027b]\u001eT\u0011!Z\u0001\u0005U\u00064\u0018-\u0003\u0002hE\n11\u000b\u001e:j]\u001e\u0004")
public class UnzipWith2<In, A1, A2>
extends GraphStage<FanOutShape2<In, A1, A2>> {
    private final Function1<In, Tuple2<A1, A2>> unzipper;
    private final FanOutShape2<In, A1, A2> shape;

    public Function1<In, Tuple2<A1, A2>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith2");
    }

    @Override
    public FanOutShape2<In, A1, A2> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape2)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape2)this.shape()).out1();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith2$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith2$$anon$$pending1;
            private final /* synthetic */ UnzipWith2 $outer;

            public int akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith2$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith2$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith2$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith2$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith2$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith2$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith2$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith2$$anon$$pending1 = x$1;
            }

            public /* synthetic */ UnzipWith2 akka$stream$scaladsl$UnzipWith2$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount = 2;
                this.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning = 2;
                this.akka$stream$scaladsl$UnzipWith2$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith2$$anon$$pending1 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple2<A1, A2> elem = this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pending1_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith2$$anon$$$outer().in());
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
        return "UnzipWith2";
    }

    public UnzipWith2(Function1<In, Tuple2<A1, A2>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape2("UnzipWith2");
    }
}

