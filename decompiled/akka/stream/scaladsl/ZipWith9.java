/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape9;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith9$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function9;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ec\u0001\u0002\u000f\u001e\u0001\u0011B\u0001\"\u0017\u0001\u0003\u0006\u0004%\tA\u0017\u0005\t=\u0002\u0011\t\u0011)A\u00057\")q\f\u0001C\u0001A\")A\r\u0001C!K\"9\u0011\u000e\u0001b\u0001\n\u0003R\u0007BB6\u0001A\u0003%A\u0006C\u0003m\u0001\u0011\u0005Q\u000eC\u0004r\u0001\t\u0007I\u0011\u0001:\t\rY\u0004\u0001\u0015!\u0003t\u0011\u001d9\bA1A\u0005\u0002aDaA\u001f\u0001!\u0002\u0013I\bbB>\u0001\u0005\u0004%\t\u0001 \u0005\u0007}\u0002\u0001\u000b\u0011B?\t\u0011}\u0004!\u0019!C\u0001\u0003\u0003A\u0001\"!\u0002\u0001A\u0003%\u00111\u0001\u0005\n\u0003\u000f\u0001!\u0019!C\u0001\u0003\u0013A\u0001\"!\u0004\u0001A\u0003%\u00111\u0002\u0005\n\u0003\u001f\u0001!\u0019!C\u0001\u0003#A\u0001\"!\u0006\u0001A\u0003%\u00111\u0003\u0005\n\u0003/\u0001!\u0019!C\u0001\u00033A\u0001\"!\b\u0001A\u0003%\u00111\u0004\u0005\n\u0003?\u0001!\u0019!C\u0001\u0003CA\u0001\"!\n\u0001A\u0003%\u00111\u0005\u0005\n\u0003O\u0001!\u0019!C\u0001\u0003SA\u0001\"!\f\u0001A\u0003%\u00111\u0006\u0005\b\u0003_\u0001A\u0011IA\u0019\u0011\u001d\ti\u0004\u0001C!\u0003\u007f\u0011\u0001BW5q/&$\b.\u000f\u0006\u0003=}\t\u0001b]2bY\u0006$7\u000f\u001c\u0006\u0003A\u0005\naa\u001d;sK\u0006l'\"\u0001\u0012\u0002\t\u0005\\7.Y\u0002\u0001+-)#g\u0010\"F\u0011.s\u0015\u000bV,\u0014\u0005\u00011\u0003cA\u0014+Y5\t\u0001F\u0003\u0002*?\u0005)1\u000f^1hK&\u00111\u0006\u000b\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007\u0003D\u0017/ay\nEi\u0012&N!N3V\"A\u0010\n\u0005=z\"a\u0003$b]&s7\u000b[1qKf\u0002\"!\r\u001a\r\u0001\u0011)1\u0007\u0001b\u0001i\t\u0011\u0011)M\t\u0003km\u0002\"AN\u001d\u000e\u0003]R\u0011\u0001O\u0001\u0006g\u000e\fG.Y\u0005\u0003u]\u0012qAT8uQ&tw\r\u0005\u00027y%\u0011Qh\u000e\u0002\u0004\u0003:L\bCA\u0019@\t\u0015\u0001\u0005A1\u00015\u0005\t\t%\u0007\u0005\u00022\u0005\u0012)1\t\u0001b\u0001i\t\u0011\u0011i\r\t\u0003c\u0015#QA\u0012\u0001C\u0002Q\u0012!!\u0011\u001b\u0011\u0005EBE!B%\u0001\u0005\u0004!$AA!6!\t\t4\nB\u0003M\u0001\t\u0007AG\u0001\u0002BmA\u0011\u0011G\u0014\u0003\u0006\u001f\u0002\u0011\r\u0001\u000e\u0002\u0003\u0003^\u0002\"!M)\u0005\u000bI\u0003!\u0019\u0001\u001b\u0003\u0005\u0005C\u0004CA\u0019U\t\u0015)\u0006A1\u00015\u0005\t\t\u0015\b\u0005\u00022/\u0012)\u0001\f\u0001b\u0001i\t\tq*\u0001\u0004{SB\u0004XM]\u000b\u00027Baa\u0007\u0018\u0019?\u0003\u0012;%*\u0014)T-&\u0011Ql\u000e\u0002\n\rVt7\r^5p]f\nqA_5qa\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0003C\u000e\u0004BB\u0019\u00011}\u0005#uIS'Q'Zk\u0011!\b\u0005\u00063\u000e\u0001\raW\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#\u00014\u0011\u00055:\u0017B\u00015 \u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002Y\u000511\u000f[1qK\u0002\n1a\\;u+\u0005q\u0007cA\u0017p-&\u0011\u0001o\b\u0002\u0007\u001fV$H.\u001a;\u0002\u0007%t\u0007'F\u0001t!\riC\u000fM\u0005\u0003k~\u0011Q!\u00138mKR\fA!\u001b81A\u0005\u0019\u0011N\\\u0019\u0016\u0003e\u00042!\f;?\u0003\u0011Ig.\r\u0011\u0002\u0007%t''F\u0001~!\riC/Q\u0001\u0005S:\u0014\u0004%A\u0002j]N*\"!a\u0001\u0011\u00075\"H)\u0001\u0003j]N\u0002\u0013aA5oiU\u0011\u00111\u0002\t\u0004[Q<\u0015\u0001B5oi\u0001\n1!\u001b86+\t\t\u0019\u0002E\u0002.i*\u000bA!\u001b86A\u0005\u0019\u0011N\u001c\u001c\u0016\u0005\u0005m\u0001cA\u0017u\u001b\u0006!\u0011N\u001c\u001c!\u0003\rIgnN\u000b\u0003\u0003G\u00012!\f;Q\u0003\u0011Ign\u000e\u0011\u0002\u0007%t\u0007(\u0006\u0002\u0002,A\u0019Q\u0006^*\u0002\t%t\u0007\bI\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u00024\u0005e\u0002cA\u0014\u00026%\u0019\u0011q\u0007\u0015\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDa!a\u000f\u001b\u0001\u00041\u0017aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005\u0005\u0003\u0003BA\"\u0003\u001bj!!!\u0012\u000b\t\u0005\u001d\u0013\u0011J\u0001\u0005Y\u0006twM\u0003\u0002\u0002L\u0005!!.\u0019<b\u0013\u0011\ty%!\u0012\u0003\rM#(/\u001b8h\u0001")
public class ZipWith9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O>
extends GraphStage<FanInShape9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O>> {
    private final Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> zipper;
    private final FanInShape9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;
    private final Inlet<A8> in7;
    private final Inlet<A9> in8;

    public Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith9");
    }

    @Override
    public FanInShape9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> shape() {
        return this.shape;
    }

    public Outlet<O> out() {
        return ((FanInShape)this.shape()).out();
    }

    public Inlet<A1> in0() {
        return this.in0;
    }

    public Inlet<A2> in1() {
        return this.in1;
    }

    public Inlet<A3> in2() {
        return this.in2;
    }

    public Inlet<A4> in3() {
        return this.in3;
    }

    public Inlet<A5> in4() {
        return this.in4;
    }

    public Inlet<A6> in5() {
        return this.in5;
    }

    public Inlet<A7> in6() {
        return this.in6;
    }

    public Inlet<A8> in7() {
        return this.in7;
    }

    public Inlet<A9> in8() {
        return this.in8;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith9$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith9$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith9$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith9 $outer;

            public int akka$stream$scaladsl$ZipWith9$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith9$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith9$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith9$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith9$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith9$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith9$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8())));
                if (this.willShutDown()) {
                    this.completeStage();
                    return;
                }
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
                this.pull(this.$outer.in6());
                this.pull(this.$outer.in7());
                this.pull(this.$outer.in8());
            }

            public void preStart() {
                this.pull(this.$outer.in0());
                this.pull(this.$outer.in1());
                this.pull(this.$outer.in2());
                this.pull(this.$outer.in3());
                this.pull(this.$outer.in4());
                this.pull(this.$outer.in5());
                this.pull(this.$outer.in6());
                this.pull(this.$outer.in7());
                this.pull(this.$outer.in8());
            }

            public /* synthetic */ ZipWith9 akka$stream$scaladsl$ZipWith9$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith9$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith9$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in1(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in2(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in3(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in4(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in5(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in6(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in7(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in8(), new InHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.out(), new OutHandler(this){
                    private final /* synthetic */ $anon$50 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith9$$anon$$pushAll();
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
        return "ZipWith9";
    }

    public ZipWith9(Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape9("ZipWith9");
        this.in0 = ((FanInShape9)this.shape()).in0();
        this.in1 = ((FanInShape9)this.shape()).in1();
        this.in2 = ((FanInShape9)this.shape()).in2();
        this.in3 = ((FanInShape9)this.shape()).in3();
        this.in4 = ((FanInShape9)this.shape()).in4();
        this.in5 = ((FanInShape9)this.shape()).in5();
        this.in6 = ((FanInShape9)this.shape()).in6();
        this.in7 = ((FanInShape9)this.shape()).in7();
        this.in8 = ((FanInShape9)this.shape()).in8();
    }
}

