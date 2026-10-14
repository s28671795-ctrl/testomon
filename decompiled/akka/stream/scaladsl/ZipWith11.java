/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape11;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith11$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function11;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Ud\u0001\u0002\u0011\"\u0001!B\u0001b\u0019\u0001\u0003\u0006\u0004%\t\u0001\u001a\u0005\tQ\u0002\u0011\t\u0011)A\u0005K\")\u0011\u000e\u0001C\u0001U\")a\u000e\u0001C!_\"91\u000f\u0001b\u0001\n\u0003\"\bBB;\u0001A\u0003%\u0001\u0007C\u0003w\u0001\u0011\u0005q\u000fC\u0004|\u0001\t\u0007I\u0011\u0001?\t\u000f\u0005\u0005\u0001\u0001)A\u0005{\"I\u00111\u0001\u0001C\u0002\u0013\u0005\u0011Q\u0001\u0005\t\u0003\u0013\u0001\u0001\u0015!\u0003\u0002\b!I\u00111\u0002\u0001C\u0002\u0013\u0005\u0011Q\u0002\u0005\t\u0003#\u0001\u0001\u0015!\u0003\u0002\u0010!I\u00111\u0003\u0001C\u0002\u0013\u0005\u0011Q\u0003\u0005\t\u00033\u0001\u0001\u0015!\u0003\u0002\u0018!I\u00111\u0004\u0001C\u0002\u0013\u0005\u0011Q\u0004\u0005\t\u0003C\u0001\u0001\u0015!\u0003\u0002 !I\u00111\u0005\u0001C\u0002\u0013\u0005\u0011Q\u0005\u0005\t\u0003S\u0001\u0001\u0015!\u0003\u0002(!I\u00111\u0006\u0001C\u0002\u0013\u0005\u0011Q\u0006\u0005\t\u0003c\u0001\u0001\u0015!\u0003\u00020!I\u00111\u0007\u0001C\u0002\u0013\u0005\u0011Q\u0007\u0005\t\u0003s\u0001\u0001\u0015!\u0003\u00028!I\u00111\b\u0001C\u0002\u0013\u0005\u0011Q\b\u0005\t\u0003\u0003\u0002\u0001\u0015!\u0003\u0002@!I\u00111\t\u0001C\u0002\u0013\u0005\u0011Q\t\u0005\t\u0003\u0013\u0002\u0001\u0015!\u0003\u0002H!I\u00111\n\u0001C\u0002\u0013\u0005\u0011Q\n\u0005\t\u0003#\u0002\u0001\u0015!\u0003\u0002P!9\u00111\u000b\u0001\u0005B\u0005U\u0003bBA1\u0001\u0011\u0005\u00131\r\u0002\n5&\u0004x+\u001b;icER!AI\u0012\u0002\u0011M\u001c\u0017\r\\1eg2T!\u0001J\u0013\u0002\rM$(/Z1n\u0015\u00051\u0013\u0001B1lW\u0006\u001c\u0001!F\u0007*m\r3\u0015\nT(S+b[f,Y\n\u0003\u0001)\u00022a\u000b\u00181\u001b\u0005a#BA\u0017$\u0003\u0015\u0019H/Y4f\u0013\tyCF\u0001\u0006He\u0006\u0004\bn\u0015;bO\u0016\u0004b\"\r\u001a5\u0005\u0016C5JT)U/jk\u0006-D\u0001$\u0013\t\u00194E\u0001\u0007GC:Len\u00155ba\u0016\f\u0014\u0007\u0005\u00026m1\u0001A!B\u001c\u0001\u0005\u0004A$AA!2#\tIt\b\u0005\u0002;{5\t1HC\u0001=\u0003\u0015\u00198-\u00197b\u0013\tq4HA\u0004O_RD\u0017N\\4\u0011\u0005i\u0002\u0015BA!<\u0005\r\te.\u001f\t\u0003k\r#Q\u0001\u0012\u0001C\u0002a\u0012!!\u0011\u001a\u0011\u0005U2E!B$\u0001\u0005\u0004A$AA!4!\t)\u0014\nB\u0003K\u0001\t\u0007\u0001H\u0001\u0002BiA\u0011Q\u0007\u0014\u0003\u0006\u001b\u0002\u0011\r\u0001\u000f\u0002\u0003\u0003V\u0002\"!N(\u0005\u000bA\u0003!\u0019\u0001\u001d\u0003\u0005\u00053\u0004CA\u001bS\t\u0015\u0019\u0006A1\u00019\u0005\t\tu\u0007\u0005\u00026+\u0012)a\u000b\u0001b\u0001q\t\u0011\u0011\t\u000f\t\u0003ka#Q!\u0017\u0001C\u0002a\u0012!!Q\u001d\u0011\u0005UZF!\u0002/\u0001\u0005\u0004A$aA!2aA\u0011QG\u0018\u0003\u0006?\u0002\u0011\r\u0001\u000f\u0002\u0004\u0003F\n\u0004CA\u001bb\t\u0015\u0011\u0007A1\u00019\u0005\u0005y\u0015A\u0002>jaB,'/F\u0001f!9Qd\r\u000e\"F\u0011.s\u0015\u000bV,[;\u0002L!aZ\u001e\u0003\u0015\u0019+hn\u0019;j_:\f\u0014'A\u0004{SB\u0004XM\u001d\u0011\u0002\rqJg.\u001b;?)\tYW\u000e\u0005\bm\u0001Q\u0012U\tS&O#R;&,\u00181\u000e\u0003\u0005BQaY\u0002A\u0002\u0015\f\u0011#\u001b8ji&\fG.\u0011;ue&\u0014W\u000f^3t+\u0005\u0001\bCA\u0019r\u0013\t\u00118E\u0001\u0006BiR\u0014\u0018NY;uKN\fQa\u001d5ba\u0016,\u0012\u0001M\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0007=,H/F\u0001y!\r\t\u0014\u0010Y\u0005\u0003u\u000e\u0012aaT;uY\u0016$\u0018aA5oaU\tQ\u0010E\u00022}RJ!a`\u0012\u0003\u000b%sG.\u001a;\u0002\t%t\u0007\u0007I\u0001\u0004S:\fTCAA\u0004!\r\tdPQ\u0001\u0005S:\f\u0004%A\u0002j]J*\"!a\u0004\u0011\u0007ErX)\u0001\u0003j]J\u0002\u0013aA5ogU\u0011\u0011q\u0003\t\u0004cyD\u0015\u0001B5og\u0001\n1!\u001b85+\t\ty\u0002E\u00022}.\u000bA!\u001b85A\u0005\u0019\u0011N\\\u001b\u0016\u0005\u0005\u001d\u0002cA\u0019\u007f\u001d\u0006!\u0011N\\\u001b!\u0003\rIgNN\u000b\u0003\u0003_\u00012!\r@R\u0003\u0011IgN\u000e\u0011\u0002\u0007%tw'\u0006\u0002\u00028A\u0019\u0011G +\u0002\t%tw\u0007I\u0001\u0004S:DTCAA !\r\tdpV\u0001\u0005S:D\u0004%A\u0002j]f*\"!a\u0012\u0011\u0007Er(,\u0001\u0003j]f\u0002\u0013\u0001B5ocA*\"!a\u0014\u0011\u0007ErX,A\u0003j]F\u0002\u0004%A\u0006de\u0016\fG/\u001a'pO&\u001cG\u0003BA,\u0003;\u00022aKA-\u0013\r\tY\u0006\f\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"1\u0011q\f\u0010A\u0002A\f1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003K\u0002B!a\u001a\u0002r5\u0011\u0011\u0011\u000e\u0006\u0005\u0003W\ni'\u0001\u0003mC:<'BAA8\u0003\u0011Q\u0017M^1\n\t\u0005M\u0014\u0011\u000e\u0002\u0007'R\u0014\u0018N\\4")
public class ZipWith11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O>
extends GraphStage<FanInShape11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O>> {
    private final Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> zipper;
    private final FanInShape11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> shape;
    private final Inlet<A1> in0;
    private final Inlet<A2> in1;
    private final Inlet<A3> in2;
    private final Inlet<A4> in3;
    private final Inlet<A5> in4;
    private final Inlet<A6> in5;
    private final Inlet<A7> in6;
    private final Inlet<A8> in7;
    private final Inlet<A9> in8;
    private final Inlet<A10> in9;
    private final Inlet<A11> in10;

    public Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith11");
    }

    @Override
    public FanInShape11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> shape() {
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

    public Inlet<A10> in9() {
        return this.in9;
    }

    public Inlet<A11> in10() {
        return this.in10;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith11$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith11$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith11$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith11 $outer;

            public int akka$stream$scaladsl$ZipWith11$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith11$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith11$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith11$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith11$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith11$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith11$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10())));
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
                this.pull(this.$outer.in9());
                this.pull(this.$outer.in10());
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
                this.pull(this.$outer.in9());
                this.pull(this.$outer.in10());
            }

            public /* synthetic */ ZipWith11 akka$stream$scaladsl$ZipWith11$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith11$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith11$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in9(), new InHandler(this){
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in10(), new InHandler(this){
                    private final /* synthetic */ $anon$73 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$73 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith11$$anon$$pushAll();
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
        return "ZipWith11";
    }

    public ZipWith11(Function11<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape11("ZipWith11");
        this.in0 = ((FanInShape11)this.shape()).in0();
        this.in1 = ((FanInShape11)this.shape()).in1();
        this.in2 = ((FanInShape11)this.shape()).in2();
        this.in3 = ((FanInShape11)this.shape()).in3();
        this.in4 = ((FanInShape11)this.shape()).in4();
        this.in5 = ((FanInShape11)this.shape()).in5();
        this.in6 = ((FanInShape11)this.shape()).in6();
        this.in7 = ((FanInShape11)this.shape()).in7();
        this.in8 = ((FanInShape11)this.shape()).in8();
        this.in9 = ((FanInShape11)this.shape()).in9();
        this.in10 = ((FanInShape11)this.shape()).in10();
    }
}

