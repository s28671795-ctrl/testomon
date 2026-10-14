/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape10;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith10$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function10;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\rd\u0001\u0002\u0010 \u0001\u0019B\u0001B\u0018\u0001\u0003\u0006\u0004%\ta\u0018\u0005\tG\u0002\u0011\t\u0011)A\u0005A\")A\r\u0001C\u0001K\")\u0011\u000e\u0001C!U\"9a\u000e\u0001b\u0001\n\u0003z\u0007B\u00029\u0001A\u0003%a\u0006C\u0003r\u0001\u0011\u0005!\u000fC\u0004w\u0001\t\u0007I\u0011A<\t\rm\u0004\u0001\u0015!\u0003y\u0011\u001da\bA1A\u0005\u0002uDaa \u0001!\u0002\u0013q\b\"CA\u0001\u0001\t\u0007I\u0011AA\u0002\u0011!\t9\u0001\u0001Q\u0001\n\u0005\u0015\u0001\"CA\u0005\u0001\t\u0007I\u0011AA\u0006\u0011!\ty\u0001\u0001Q\u0001\n\u00055\u0001\"CA\t\u0001\t\u0007I\u0011AA\n\u0011!\t9\u0002\u0001Q\u0001\n\u0005U\u0001\"CA\r\u0001\t\u0007I\u0011AA\u000e\u0011!\ty\u0002\u0001Q\u0001\n\u0005u\u0001\"CA\u0011\u0001\t\u0007I\u0011AA\u0012\u0011!\t9\u0003\u0001Q\u0001\n\u0005\u0015\u0002\"CA\u0015\u0001\t\u0007I\u0011AA\u0016\u0011!\ty\u0003\u0001Q\u0001\n\u00055\u0002\"CA\u0019\u0001\t\u0007I\u0011AA\u001a\u0011!\t9\u0004\u0001Q\u0001\n\u0005U\u0002\"CA\u001d\u0001\t\u0007I\u0011AA\u001e\u0011!\ty\u0004\u0001Q\u0001\n\u0005u\u0002bBA!\u0001\u0011\u0005\u00131\t\u0005\b\u0003\u001f\u0002A\u0011IA)\u0005%Q\u0016\u000e],ji\"\f\u0004G\u0003\u0002!C\u0005A1oY1mC\u0012\u001cHN\u0003\u0002#G\u000511\u000f\u001e:fC6T\u0011\u0001J\u0001\u0005C.\\\u0017m\u0001\u0001\u0016\u0019\u001d\"\u0014\tR$K\u001bB\u001bf+\u0017/\u0014\u0005\u0001A\u0003cA\u0015-]5\t!F\u0003\u0002,C\u0005)1\u000f^1hK&\u0011QF\u000b\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007#D\u00181e\u0001\u001be)\u0013'P%VC6,D\u0001\"\u0013\t\t\u0014E\u0001\u0007GC:Len\u00155ba\u0016\f\u0004\u0007\u0005\u00024i1\u0001A!B\u001b\u0001\u0005\u00041$AA!2#\t9T\b\u0005\u00029w5\t\u0011HC\u0001;\u0003\u0015\u00198-\u00197b\u0013\ta\u0014HA\u0004O_RD\u0017N\\4\u0011\u0005ar\u0014BA :\u0005\r\te.\u001f\t\u0003g\u0005#QA\u0011\u0001C\u0002Y\u0012!!\u0011\u001a\u0011\u0005M\"E!B#\u0001\u0005\u00041$AA!4!\t\u0019t\tB\u0003I\u0001\t\u0007aG\u0001\u0002BiA\u00111G\u0013\u0003\u0006\u0017\u0002\u0011\rA\u000e\u0002\u0003\u0003V\u0002\"aM'\u0005\u000b9\u0003!\u0019\u0001\u001c\u0003\u0005\u00053\u0004CA\u001aQ\t\u0015\t\u0006A1\u00017\u0005\t\tu\u0007\u0005\u00024'\u0012)A\u000b\u0001b\u0001m\t\u0011\u0011\t\u000f\t\u0003gY#Qa\u0016\u0001C\u0002Y\u0012!!Q\u001d\u0011\u0005MJF!\u0002.\u0001\u0005\u00041$aA!2aA\u00111\u0007\u0018\u0003\u0006;\u0002\u0011\rA\u000e\u0002\u0002\u001f\u00061!0\u001b9qKJ,\u0012\u0001\u0019\t\u000eq\u0005\u0014\u0004i\u0011$J\u0019>\u0013V\u000bW.\n\u0005\tL$A\u0003$v]\u000e$\u0018n\u001c82a\u00059!0\u001b9qKJ\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002gQBiq\r\u0001\u001aA\u0007\u001aKEj\u0014*V1nk\u0011a\b\u0005\u0006=\u000e\u0001\r\u0001Y\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A6\u0011\u0005=b\u0017BA7\"\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002]\u000511\u000f[1qK\u0002\n1a\\;u+\u0005\u0019\bcA\u0018u7&\u0011Q/\t\u0002\u0007\u001fV$H.\u001a;\u0002\u0007%t\u0007'F\u0001y!\ry\u0013PM\u0005\u0003u\u0006\u0012Q!\u00138mKR\fA!\u001b81A\u0005\u0019\u0011N\\\u0019\u0016\u0003y\u00042aL=A\u0003\u0011Ig.\r\u0011\u0002\u0007%t''\u0006\u0002\u0002\u0006A\u0019q&_\"\u0002\t%t'\u0007I\u0001\u0004S:\u001cTCAA\u0007!\ry\u0013PR\u0001\u0005S:\u001c\u0004%A\u0002j]R*\"!!\u0006\u0011\u0007=J\u0018*\u0001\u0003j]R\u0002\u0013aA5okU\u0011\u0011Q\u0004\t\u0004_ed\u0015\u0001B5ok\u0001\n1!\u001b87+\t\t)\u0003E\u00020s>\u000bA!\u001b87A\u0005\u0019\u0011N\\\u001c\u0016\u0005\u00055\u0002cA\u0018z%\u0006!\u0011N\\\u001c!\u0003\rIg\u000eO\u000b\u0003\u0003k\u00012aL=V\u0003\u0011Ig\u000e\u000f\u0011\u0002\u0007%t\u0017(\u0006\u0002\u0002>A\u0019q&\u001f-\u0002\t%t\u0017\bI\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002F\u0005-\u0003cA\u0015\u0002H%\u0019\u0011\u0011\n\u0016\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDa!!\u0014\u001d\u0001\u0004Y\u0017aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005M\u0003\u0003BA+\u0003?j!!a\u0016\u000b\t\u0005e\u00131L\u0001\u0005Y\u0006twM\u0003\u0002\u0002^\u0005!!.\u0019<b\u0013\u0011\t\t'a\u0016\u0003\rM#(/\u001b8h\u0001")
public class ZipWith10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O>
extends GraphStage<FanInShape10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O>> {
    private final Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> zipper;
    private final FanInShape10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> shape;
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

    public Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith10");
    }

    @Override
    public FanInShape10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> shape() {
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

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith10$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith10$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith10$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith10 $outer;

            public int akka$stream$scaladsl$ZipWith10$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith10$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith10$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith10$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith10$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith10$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith10$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9())));
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
            }

            public /* synthetic */ ZipWith10 akka$stream$scaladsl$ZipWith10$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith10$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith10$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$61 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith10$$anon$$pushAll();
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
        return "ZipWith10";
    }

    public ZipWith10(Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape10("ZipWith10");
        this.in0 = ((FanInShape10)this.shape()).in0();
        this.in1 = ((FanInShape10)this.shape()).in1();
        this.in2 = ((FanInShape10)this.shape()).in2();
        this.in3 = ((FanInShape10)this.shape()).in3();
        this.in4 = ((FanInShape10)this.shape()).in4();
        this.in5 = ((FanInShape10)this.shape()).in5();
        this.in6 = ((FanInShape10)this.shape()).in6();
        this.in7 = ((FanInShape10)this.shape()).in7();
        this.in8 = ((FanInShape10)this.shape()).in8();
        this.in9 = ((FanInShape10)this.shape()).in9();
    }
}

