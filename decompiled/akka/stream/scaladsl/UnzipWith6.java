/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanOutShape;
import akka.stream.FanOutShape6;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.scaladsl.UnzipWith6$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.Tuple6;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%a\u0001\u0002\t\u0012\u0001aA\u0001\u0002\u0012\u0001\u0003\u0006\u0004%\t!\u0012\u0005\t\u0019\u0002\u0011\t\u0011)A\u0005\r\")Q\n\u0001C\u0001\u001d\")!\u000b\u0001C!'\"9q\u000b\u0001b\u0001\n\u0003B\u0006BB-\u0001A\u0003%\u0001\u0005C\u0003[\u0001\u0011\u00051\fC\u0003`\u0001\u0011\u0005\u0001\rC\u0003e\u0001\u0011\u0005Q\rC\u0003h\u0001\u0011\u0005\u0001\u000eC\u0003k\u0001\u0011\u00051\u000eC\u0003n\u0001\u0011\u0005a\u000eC\u0003q\u0001\u0011\u0005\u0011\u000fC\u0003t\u0001\u0011\u0005C\u000fC\u0003{\u0001\u0011\u00053P\u0001\u0006V]jL\u0007oV5uQZR!AE\n\u0002\u0011M\u001c\u0017\r\\1eg2T!\u0001F\u000b\u0002\rM$(/Z1n\u0015\u00051\u0012\u0001B1lW\u0006\u001c\u0001!\u0006\u0005\u001aMM2\u0014\bP C'\t\u0001!\u0004E\u0002\u001c=\u0001j\u0011\u0001\b\u0006\u0003;M\tQa\u001d;bO\u0016L!a\b\u000f\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rE\u0005\"E\u0011\u0012T\u0007O\u001e?\u00036\t1#\u0003\u0002$'\taa)\u00198PkR\u001c\u0006.\u00199fmA\u0011QE\n\u0007\u0001\t\u00159\u0003A1\u0001)\u0005\tIe.\u0005\u0002*_A\u0011!&L\u0007\u0002W)\tA&A\u0003tG\u0006d\u0017-\u0003\u0002/W\t9aj\u001c;iS:<\u0007C\u0001\u00161\u0013\t\t4FA\u0002B]f\u0004\"!J\u001a\u0005\u000bQ\u0002!\u0019\u0001\u0015\u0003\u0005\u0005\u000b\u0004CA\u00137\t\u00159\u0004A1\u0001)\u0005\t\t%\u0007\u0005\u0002&s\u0011)!\b\u0001b\u0001Q\t\u0011\u0011i\r\t\u0003Kq\"Q!\u0010\u0001C\u0002!\u0012!!\u0011\u001b\u0011\u0005\u0015zD!\u0002!\u0001\u0005\u0004A#AA!6!\t)#\tB\u0003D\u0001\t\u0007\u0001F\u0001\u0002Bm\u0005AQO\u001c>jaB,'/F\u0001G!\u0011Qs\tJ%\n\u0005![#!\u0003$v]\u000e$\u0018n\u001c82!!Q#JM\u001b9wy\n\u0015BA&,\u0005\u0019!V\u000f\u001d7fm\u0005IQO\u001c>jaB,'\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005=\u000b\u0006#\u0003)\u0001II*\u0004h\u000f B\u001b\u0005\t\u0002\"\u0002#\u0004\u0001\u00041\u0015!E5oSRL\u0017\r\\!uiJL'-\u001e;fgV\tA\u000b\u0005\u0002\"+&\u0011ak\u0005\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018!B:iCB,W#\u0001\u0011\u0002\rMD\u0017\r]3!\u0003\tIg.F\u0001]!\r\tS\fJ\u0005\u0003=N\u0011Q!\u00138mKR\fAa\\;uaU\t\u0011\rE\u0002\"EJJ!aY\n\u0003\r=+H\u000f\\3u\u0003\u0011yW\u000f^\u0019\u0016\u0003\u0019\u00042!\t26\u0003\u0011yW\u000f\u001e\u001a\u0016\u0003%\u00042!\t29\u0003\u0011yW\u000f^\u001a\u0016\u00031\u00042!\t2<\u0003\u0011yW\u000f\u001e\u001b\u0016\u0003=\u00042!\t2?\u0003\u0011yW\u000f^\u001b\u0016\u0003I\u00042!\t2B\u0003-\u0019'/Z1uK2{w-[2\u0015\u0005UD\bCA\u000ew\u0013\t9HDA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d\u0011\u0015Ih\u00021\u0001U\u0003MIg\u000e[3sSR,G-\u0011;ue&\u0014W\u000f^3t\u0003!!xn\u0015;sS:<G#\u0001?\u0011\u0007u\f)!D\u0001\u007f\u0015\ry\u0018\u0011A\u0001\u0005Y\u0006twM\u0003\u0002\u0002\u0004\u0005!!.\u0019<b\u0013\r\t9A \u0002\u0007'R\u0014\u0018N\\4")
public class UnzipWith6<In, A1, A2, A3, A4, A5, A6>
extends GraphStage<FanOutShape6<In, A1, A2, A3, A4, A5, A6>> {
    private final Function1<In, Tuple6<A1, A2, A3, A4, A5, A6>> unzipper;
    private final FanOutShape6<In, A1, A2, A3, A4, A5, A6> shape;

    public Function1<In, Tuple6<A1, A2, A3, A4, A5, A6>> unzipper() {
        return this.unzipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("UnzipWith6");
    }

    @Override
    public FanOutShape6<In, A1, A2, A3, A4, A5, A6> shape() {
        return this.shape;
    }

    public Inlet<In> in() {
        return ((FanOutShape)this.shape()).in();
    }

    public Outlet<A1> out0() {
        return ((FanOutShape6)this.shape()).out0();
    }

    public Outlet<A2> out1() {
        return ((FanOutShape6)this.shape()).out1();
    }

    public Outlet<A3> out2() {
        return ((FanOutShape6)this.shape()).out2();
    }

    public Outlet<A4> out3() {
        return ((FanOutShape6)this.shape()).out3();
    }

    public Outlet<A5> out4() {
        return ((FanOutShape6)this.shape()).out4();
    }

    public Outlet<A6> out5() {
        return ((FanOutShape6)this.shape()).out5();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount;
            private int akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning;
            private boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending0;
            private boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending1;
            private boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending2;
            private boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending3;
            private boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending4;
            private boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending5;
            private final /* synthetic */ UnzipWith6 $outer;

            public int akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() {
                return this.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount;
            }

            public void akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount = x$1;
            }

            public int akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() {
                return this.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning;
            }

            public void akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning_$eq(int x$1) {
                this.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending0() {
                return this.akka$stream$scaladsl$UnzipWith6$$anon$$pending0;
            }

            public void akka$stream$scaladsl$UnzipWith6$$anon$$pending0_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending0 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending1() {
                return this.akka$stream$scaladsl$UnzipWith6$$anon$$pending1;
            }

            public void akka$stream$scaladsl$UnzipWith6$$anon$$pending1_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending1 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending2() {
                return this.akka$stream$scaladsl$UnzipWith6$$anon$$pending2;
            }

            public void akka$stream$scaladsl$UnzipWith6$$anon$$pending2_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending2 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending3() {
                return this.akka$stream$scaladsl$UnzipWith6$$anon$$pending3;
            }

            public void akka$stream$scaladsl$UnzipWith6$$anon$$pending3_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending3 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending4() {
                return this.akka$stream$scaladsl$UnzipWith6$$anon$$pending4;
            }

            public void akka$stream$scaladsl$UnzipWith6$$anon$$pending4_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending4 = x$1;
            }

            public boolean akka$stream$scaladsl$UnzipWith6$$anon$$pending5() {
                return this.akka$stream$scaladsl$UnzipWith6$$anon$$pending5;
            }

            public void akka$stream$scaladsl$UnzipWith6$$anon$$pending5_$eq(boolean x$1) {
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending5 = x$1;
            }

            public /* synthetic */ UnzipWith6 akka$stream$scaladsl$UnzipWith6$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount = 6;
                this.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning = 6;
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending0 = true;
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending1 = true;
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending2 = true;
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending3 = true;
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending4 = true;
                this.akka$stream$scaladsl$UnzipWith6$$anon$$pending5 = true;
                this.setHandler($outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$23 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        Tuple6<A1, A2, A3, A4, A5, A6> elem = this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().unzipper().apply(this.$outer.grab(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in()));
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out0())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out0(), elem._1());
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending0_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out1())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out1(), elem._2());
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending1_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out2())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out2(), elem._3());
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending2_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out3())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out3(), elem._4());
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending3_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out4())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out4(), elem._5());
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending4_$eq(true);
                        }
                        if (!this.$outer.isClosed(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out5())) {
                            this.$outer.push(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().out5(), elem._6());
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending5_$eq(true);
                        }
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning());
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending0_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending0()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending1_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending1()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending2_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending2()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending3_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending3()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending4_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending4()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
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
                    private final /* synthetic */ $anon$23 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending5_$eq(false);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
                            return;
                        }
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() - 1);
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$downstreamRunning() == 0) {
                            this.$outer.cancelStage(cause);
                            return;
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pending5()) {
                            this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount_$eq(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() - 1);
                        }
                        if (this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$pendingCount() == 0 && !this.$outer.hasBeenPulled(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in())) {
                            this.$outer.pull(this.$outer.akka$stream$scaladsl$UnzipWith6$$anon$$$outer().in());
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
        return "UnzipWith6";
    }

    public UnzipWith6(Function1<In, Tuple6<A1, A2, A3, A4, A5, A6>> unzipper) {
        this.unzipper = unzipper;
        this.shape = new FanOutShape6("UnzipWith6");
    }
}

