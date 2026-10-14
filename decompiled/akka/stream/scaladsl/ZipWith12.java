/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.FanInShape;
import akka.stream.FanInShape12;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.scaladsl.ZipWith12$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function12;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001de\u0001\u0002\u0012$\u0001)B\u0001\u0002\u001b\u0001\u0003\u0006\u0004%\t!\u001b\u0005\t[\u0002\u0011\t\u0011)A\u0005U\")a\u000e\u0001C\u0001_\")1\u000f\u0001C!i\"9\u0001\u0010\u0001b\u0001\n\u0003J\bB\u0002>\u0001A\u0003%!\u0007C\u0003|\u0001\u0011\u0005A\u0010C\u0005\u0002\u0002\u0001\u0011\r\u0011\"\u0001\u0002\u0004!A\u00111\u0002\u0001!\u0002\u0013\t)\u0001C\u0005\u0002\u000e\u0001\u0011\r\u0011\"\u0001\u0002\u0010!A\u00111\u0003\u0001!\u0002\u0013\t\t\u0002C\u0005\u0002\u0016\u0001\u0011\r\u0011\"\u0001\u0002\u0018!A\u00111\u0004\u0001!\u0002\u0013\tI\u0002C\u0005\u0002\u001e\u0001\u0011\r\u0011\"\u0001\u0002 !A\u00111\u0005\u0001!\u0002\u0013\t\t\u0003C\u0005\u0002&\u0001\u0011\r\u0011\"\u0001\u0002(!A\u00111\u0006\u0001!\u0002\u0013\tI\u0003C\u0005\u0002.\u0001\u0011\r\u0011\"\u0001\u00020!A\u00111\u0007\u0001!\u0002\u0013\t\t\u0004C\u0005\u00026\u0001\u0011\r\u0011\"\u0001\u00028!A\u00111\b\u0001!\u0002\u0013\tI\u0004C\u0005\u0002>\u0001\u0011\r\u0011\"\u0001\u0002@!A\u00111\t\u0001!\u0002\u0013\t\t\u0005C\u0005\u0002F\u0001\u0011\r\u0011\"\u0001\u0002H!A\u00111\n\u0001!\u0002\u0013\tI\u0005C\u0005\u0002N\u0001\u0011\r\u0011\"\u0001\u0002P!A\u00111\u000b\u0001!\u0002\u0013\t\t\u0006C\u0005\u0002V\u0001\u0011\r\u0011\"\u0001\u0002X!A\u00111\f\u0001!\u0002\u0013\tI\u0006C\u0005\u0002^\u0001\u0011\r\u0011\"\u0001\u0002`!A\u00111\r\u0001!\u0002\u0013\t\t\u0007C\u0004\u0002f\u0001!\t%a\u001a\t\u000f\u0005M\u0004\u0001\"\u0011\u0002v\tI!,\u001b9XSRD\u0017G\r\u0006\u0003I\u0015\n\u0001b]2bY\u0006$7\u000f\u001c\u0006\u0003M\u001d\naa\u001d;sK\u0006l'\"\u0001\u0015\u0002\t\u0005\\7.Y\u0002\u0001+9Y\u0003(\u0012%L\u001dF#vKW/aG\u001a\u001c\"\u0001\u0001\u0017\u0011\u00075\u0002$'D\u0001/\u0015\tyS%A\u0003ti\u0006<W-\u0003\u00022]\tQqI]1qQN#\u0018mZ3\u0011\u001fM\"d\u0007R$K\u001bB\u001bf+\u0017/`E\u0016l\u0011!J\u0005\u0003k\u0015\u0012ABR1o\u0013:\u001c\u0006.\u00199fcI\u0002\"a\u000e\u001d\r\u0001\u0011)\u0011\b\u0001b\u0001u\t\u0011\u0011)M\t\u0003w\u0005\u0003\"\u0001P \u000e\u0003uR\u0011AP\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0001v\u0012qAT8uQ&tw\r\u0005\u0002=\u0005&\u00111)\u0010\u0002\u0004\u0003:L\bCA\u001cF\t\u00151\u0005A1\u0001;\u0005\t\t%\u0007\u0005\u00028\u0011\u0012)\u0011\n\u0001b\u0001u\t\u0011\u0011i\r\t\u0003o-#Q\u0001\u0014\u0001C\u0002i\u0012!!\u0011\u001b\u0011\u0005]rE!B(\u0001\u0005\u0004Q$AA!6!\t9\u0014\u000bB\u0003S\u0001\t\u0007!H\u0001\u0002BmA\u0011q\u0007\u0016\u0003\u0006+\u0002\u0011\rA\u000f\u0002\u0003\u0003^\u0002\"aN,\u0005\u000ba\u0003!\u0019\u0001\u001e\u0003\u0005\u0005C\u0004CA\u001c[\t\u0015Y\u0006A1\u0001;\u0005\t\t\u0015\b\u0005\u00028;\u0012)a\f\u0001b\u0001u\t\u0019\u0011)\r\u0019\u0011\u0005]\u0002G!B1\u0001\u0005\u0004Q$aA!2cA\u0011qg\u0019\u0003\u0006I\u0002\u0011\rA\u000f\u0002\u0004\u0003F\u0012\u0004CA\u001cg\t\u00159\u0007A1\u0001;\u0005\u0005y\u0015A\u0002>jaB,'/F\u0001k!=a4N\u000e#H\u00156\u00036KV-]?\n,\u0017B\u00017>\u0005)1UO\\2uS>t\u0017GM\u0001\bu&\u0004\b/\u001a:!\u0003\u0019a\u0014N\\5u}Q\u0011\u0001O\u001d\t\u0010c\u00021Di\u0012&N!N3\u0016\fX0cK6\t1\u0005C\u0003i\u0007\u0001\u0007!.A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\u0012!\u001e\t\u0003gYL!a^\u0013\u0003\u0015\u0005#HO]5ckR,7/A\u0003tQ\u0006\u0004X-F\u00013\u0003\u0019\u0019\b.\u00199fA\u0005\u0019q.\u001e;\u0016\u0003u\u00042a\r@f\u0013\tyXE\u0001\u0004PkRdW\r^\u0001\u0004S:\u0004TCAA\u0003!\u0011\u0019\u0014q\u0001\u001c\n\u0007\u0005%QEA\u0003J]2,G/\u0001\u0003j]B\u0002\u0013aA5ocU\u0011\u0011\u0011\u0003\t\u0005g\u0005\u001dA)\u0001\u0003j]F\u0002\u0013aA5oeU\u0011\u0011\u0011\u0004\t\u0005g\u0005\u001dq)\u0001\u0003j]J\u0002\u0013aA5ogU\u0011\u0011\u0011\u0005\t\u0005g\u0005\u001d!*\u0001\u0003j]N\u0002\u0013aA5oiU\u0011\u0011\u0011\u0006\t\u0005g\u0005\u001dQ*\u0001\u0003j]R\u0002\u0013aA5okU\u0011\u0011\u0011\u0007\t\u0005g\u0005\u001d\u0001+\u0001\u0003j]V\u0002\u0013aA5omU\u0011\u0011\u0011\b\t\u0005g\u0005\u001d1+\u0001\u0003j]Z\u0002\u0013aA5ooU\u0011\u0011\u0011\t\t\u0005g\u0005\u001da+\u0001\u0003j]^\u0002\u0013aA5oqU\u0011\u0011\u0011\n\t\u0005g\u0005\u001d\u0011,\u0001\u0003j]b\u0002\u0013aA5osU\u0011\u0011\u0011\u000b\t\u0005g\u0005\u001dA,\u0001\u0003j]f\u0002\u0013\u0001B5ocA*\"!!\u0017\u0011\tM\n9aX\u0001\u0006S:\f\u0004\u0007I\u0001\u0005S:\f\u0014'\u0006\u0002\u0002bA!1'a\u0002c\u0003\u0015Ig.M\u0019!\u0003-\u0019'/Z1uK2{w-[2\u0015\t\u0005%\u0014q\u000e\t\u0004[\u0005-\u0014bAA7]\tyqI]1qQN#\u0018mZ3M_\u001eL7\r\u0003\u0004\u0002r\u0001\u0002\r!^\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\u0011\u0011q\u000f\t\u0005\u0003s\n\u0019)\u0004\u0002\u0002|)!\u0011QPA@\u0003\u0011a\u0017M\\4\u000b\u0005\u0005\u0005\u0015\u0001\u00026bm\u0006LA!!\"\u0002|\t11\u000b\u001e:j]\u001e\u0004")
public class ZipWith12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O>
extends GraphStage<FanInShape12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O>> {
    private final Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> zipper;
    private final FanInShape12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> shape;
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
    private final Inlet<A12> in11;

    public Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> zipper() {
        return this.zipper;
    }

    @Override
    public Attributes initialAttributes() {
        return Attributes$.MODULE$.name("ZipWith12");
    }

    @Override
    public FanInShape12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> shape() {
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

    public Inlet<A12> in11() {
        return this.in11;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private int akka$stream$scaladsl$ZipWith12$$anon$$pending;
            private boolean akka$stream$scaladsl$ZipWith12$$anon$$willShutDown;
            private final ContextPropagation akka$stream$scaladsl$ZipWith12$$anon$$contextPropagation;
            private final /* synthetic */ ZipWith12 $outer;

            public int akka$stream$scaladsl$ZipWith12$$anon$$pending() {
                return this.akka$stream$scaladsl$ZipWith12$$anon$$pending;
            }

            public void akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(int x$1) {
                this.akka$stream$scaladsl$ZipWith12$$anon$$pending = x$1;
            }

            private boolean willShutDown() {
                return this.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown;
            }

            public void akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(boolean x$1) {
                this.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown = x$1;
            }

            public ContextPropagation akka$stream$scaladsl$ZipWith12$$anon$$contextPropagation() {
                return this.akka$stream$scaladsl$ZipWith12$$anon$$contextPropagation;
            }

            public void akka$stream$scaladsl$ZipWith12$$anon$$pushAll() {
                this.akka$stream$scaladsl$ZipWith12$$anon$$contextPropagation().resumeContext();
                this.push(this.$outer.out(), this.$outer.zipper().apply(this.grab(this.$outer.in0()), this.grab(this.$outer.in1()), this.grab(this.$outer.in2()), this.grab(this.$outer.in3()), this.grab(this.$outer.in4()), this.grab(this.$outer.in5()), this.grab(this.$outer.in6()), this.grab(this.$outer.in7()), this.grab(this.$outer.in8()), this.grab(this.$outer.in9()), this.grab(this.$outer.in10()), this.grab(this.$outer.in11())));
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
                this.pull(this.$outer.in11());
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
                this.pull(this.$outer.in11());
            }

            public /* synthetic */ ZipWith12 akka$stream$scaladsl$ZipWith12$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.akka$stream$scaladsl$ZipWith12$$anon$$pending = 0;
                this.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown = false;
                this.akka$stream$scaladsl$ZipWith12$$anon$$contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.in0(), new InHandler(this){
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$contextPropagation().suspendContext();
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in0())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in1())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in2())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in3())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in4())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in5())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in6())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in7())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in8())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in9())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in10())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandler($outer.in11(), new InHandler(this){
                    private final /* synthetic */ $anon$86 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() - 1);
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
                            return;
                        }
                    }

                    public void onUpstreamFinish() {
                        if (!this.$outer.isAvailable(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().in11())) {
                            this.$outer.completeStage();
                        }
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$willShutDown_$eq(true);
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
                    private final /* synthetic */ $anon$86 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onDownstreamFinish(Throwable cause) throws Exception {
                        OutHandler.onDownstreamFinish$(this, cause);
                    }

                    public void onPull() {
                        this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending_$eq(this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() + ((FanInShape)this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$$outer().shape()).inlets().size());
                        if (this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pending() == 0) {
                            this.$outer.akka$stream$scaladsl$ZipWith12$$anon$$pushAll();
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
        return "ZipWith12";
    }

    public ZipWith12(Function12<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, O> zipper) {
        this.zipper = zipper;
        this.shape = new FanInShape12("ZipWith12");
        this.in0 = ((FanInShape12)this.shape()).in0();
        this.in1 = ((FanInShape12)this.shape()).in1();
        this.in2 = ((FanInShape12)this.shape()).in2();
        this.in3 = ((FanInShape12)this.shape()).in3();
        this.in4 = ((FanInShape12)this.shape()).in4();
        this.in5 = ((FanInShape12)this.shape()).in5();
        this.in6 = ((FanInShape12)this.shape()).in6();
        this.in7 = ((FanInShape12)this.shape()).in7();
        this.in8 = ((FanInShape12)this.shape()).in8();
        this.in9 = ((FanInShape12)this.shape()).in9();
        this.in10 = ((FanInShape12)this.shape()).in10();
        this.in11 = ((FanInShape12)this.shape()).in11();
    }
}

