/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.BidiShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.CoupledTerminationBidi$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3Q!\u0004\b\u0003!QAQA\r\u0001\u0005\u0002MBqA\u000e\u0001C\u0002\u0013\u0005q\u0007\u0003\u0004<\u0001\u0001\u0006I\u0001\u000f\u0005\by\u0001\u0011\r\u0011\"\u0001>\u0011\u0019\t\u0005\u0001)A\u0005}!9!\t\u0001b\u0001\n\u0003\u0019\u0005BB#\u0001A\u0003%A\tC\u0004G\u0001\t\u0007I\u0011A$\t\r%\u0003\u0001\u0015!\u0003I\u0011\u001dQ\u0005A1A\u0005B-Ca\u0001\u0014\u0001!\u0002\u0013a\u0002\"B'\u0001\t\u0003r%AF\"pkBdW\r\u001a+fe6Lg.\u0019;j_:\u0014\u0015\u000eZ5\u000b\u0005=\u0001\u0012\u0001B5na2T!!\u0005\n\u0002\rM$(/Z1n\u0015\u0005\u0019\u0012\u0001B1lW\u0006,2!\u0006\u00121'\t\u0001a\u0003E\u0002\u00185qi\u0011\u0001\u0007\u0006\u00033A\tQa\u001d;bO\u0016L!a\u0007\r\u0003\u0015\u001d\u0013\u0018\r\u001d5Ti\u0006<W\r\u0005\u0004\u001e=\u0001\u0002sfL\u0007\u0002!%\u0011q\u0004\u0005\u0002\n\u0005&$\u0017n\u00155ba\u0016\u0004\"!\t\u0012\r\u0001\u0011)1\u0005\u0001b\u0001K\t\t\u0011j\u0001\u0001\u0012\u0005\u0019b\u0003CA\u0014+\u001b\u0005A#\"A\u0015\u0002\u000bM\u001c\u0017\r\\1\n\u0005-B#a\u0002(pi\"Lgn\u001a\t\u0003O5J!A\f\u0015\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\"a\u0011)\u0011\u0007\u0001b\u0001K\t\tq*\u0001\u0004=S:LGO\u0010\u000b\u0002iA!Q\u0007\u0001\u00110\u001b\u0005q\u0011aA5ocU\t\u0001\bE\u0002\u001es\u0001J!A\u000f\t\u0003\u000b%sG.\u001a;\u0002\t%t\u0017\u0007I\u0001\u0005_V$\u0018'F\u0001?!\rir\bI\u0005\u0003\u0001B\u0011aaT;uY\u0016$\u0018!B8viF\u0002\u0013aA5oeU\tA\tE\u0002\u001es=\nA!\u001b83A\u0005!q.\u001e;3+\u0005A\u0005cA\u000f@_\u0005)q.\u001e;3A\u0005)1\u000f[1qKV\tA$\u0001\u0004tQ\u0006\u0004X\rI\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002P%B\u0011q\u0003U\u0005\u0003#b\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0006'2\u0001\r\u0001V\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\t\u0003;UK!A\u0016\t\u0003\u0015\u0005#HO]5ckR,7\u000f\u000b\u0002\u00011B\u0011\u0011\fX\u0007\u00025*\u00111LE\u0001\u000bC:tw\u000e^1uS>t\u0017BA/[\u0005-Ie\u000e^3s]\u0006d\u0017\t]5")
@InternalApi
public final class CoupledTerminationBidi<I, O>
extends GraphStage<BidiShape<I, I, O, O>> {
    private final Inlet<I> in1 = Inlet$.MODULE$.apply("CoupledCompletion.in1");
    private final Outlet<I> out1 = Outlet$.MODULE$.apply("CoupledCompletion.out1");
    private final Inlet<O> in2 = Inlet$.MODULE$.apply("CoupledCompletion.in2");
    private final Outlet<O> out2 = Outlet$.MODULE$.apply("CoupledCompletion.out2");
    private final BidiShape<I, I, O, O> shape = new BidiShape<I, I, O, O>(this.in1(), this.out1(), this.in2(), this.out2());

    public Inlet<I> in1() {
        return this.in1;
    }

    public Outlet<I> out1() {
        return this.out1;
    }

    public Inlet<O> in2() {
        return this.in2;
    }

    public Outlet<O> out2() {
        return this.out2;
    }

    @Override
    public BidiShape<I, I, O, O> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new GraphStageLogic(this){
            private final InHandler handler1;
            private final InHandler handler2;
            private final /* synthetic */ CoupledTerminationBidi $outer;

            private InHandler handler1() {
                return this.handler1;
            }

            private InHandler handler2() {
                return this.handler2;
            }

            public /* synthetic */ CoupledTerminationBidi akka$stream$impl$CoupledTerminationBidi$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                this.handler1 = new InHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPush() {
                        this.$outer.push(this.$outer.akka$stream$impl$CoupledTerminationBidi$$anon$$$outer().out1(), this.$outer.grab(this.$outer.akka$stream$impl$CoupledTerminationBidi$$anon$$$outer().in1()));
                    }

                    public void onPull() {
                        this.$outer.pull(this.$outer.akka$stream$impl$CoupledTerminationBidi$$anon$$$outer().in1());
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.cancelStage(cause);
                    }

                    public void onUpstreamFinish() {
                        this.$outer.completeStage();
                    }

                    public void onUpstreamFailure(Throwable ex) {
                        this.$outer.failStage(ex);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                        OutHandler.$init$(this);
                    }
                };
                this.handler2 = new InHandler(this){
                    private final /* synthetic */ $anon$1 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPush() {
                        this.$outer.push(this.$outer.akka$stream$impl$CoupledTerminationBidi$$anon$$$outer().out2(), this.$outer.grab(this.$outer.akka$stream$impl$CoupledTerminationBidi$$anon$$$outer().in2()));
                    }

                    public void onPull() {
                        this.$outer.pull(this.$outer.akka$stream$impl$CoupledTerminationBidi$$anon$$$outer().in2());
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.cancelStage(cause);
                    }

                    public void onUpstreamFinish() {
                        this.$outer.completeStage();
                    }

                    public void onUpstreamFailure(Throwable ex) {
                        this.$outer.failStage(ex);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                        OutHandler.$init$(this);
                    }
                };
                this.setHandlers($outer.in1(), $outer.out1(), this.handler1());
                this.setHandlers($outer.in2(), $outer.out2(), this.handler2());
            }
        };
    }
}

