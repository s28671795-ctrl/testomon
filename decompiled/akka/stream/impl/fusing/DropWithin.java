/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.impl.fusing.DropWithin$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00054QAB\u0004\u0003\u001b=A\u0001B\r\u0001\u0003\u0006\u0004%\ta\r\u0005\ty\u0001\u0011\t\u0011)A\u0005i!)Q\b\u0001C\u0001}!)!\t\u0001C!\u0007\")\u0001\u000b\u0001C!#\nQAI]8q/&$\b.\u001b8\u000b\u0005!I\u0011A\u00024vg&twM\u0003\u0002\u000b\u0017\u0005!\u0011.\u001c9m\u0015\taQ\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001d\u0005!\u0011m[6b+\t\u0001be\u0005\u0002\u0001#A\u0019!#\t\u0013\u000f\u0005MybB\u0001\u000b\u001f\u001d\t)RD\u0004\u0002\u001799\u0011qcG\u0007\u00021)\u0011\u0011DG\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\ta\"\u0003\u0002\r\u001b%\u0011!bC\u0005\u0003\u0011%I!\u0001I\u0004\u0002\u0017\u001d\u0013\u0018\r\u001d5Ti\u0006<Wm]\u0005\u0003E\r\u0012acU5na2,G*\u001b8fCJ<%/\u00199i'R\fw-\u001a\u0006\u0003A\u001d\u0001\"!\n\u0014\r\u0001\u0011)q\u0005\u0001b\u0001Q\t\tA+\u0005\u0002*_A\u0011!&L\u0007\u0002W)\tA&A\u0003tG\u0006d\u0017-\u0003\u0002/W\t9aj\u001c;iS:<\u0007C\u0001\u00161\u0013\t\t4FA\u0002B]f\fq\u0001^5nK>,H/F\u00015!\t)$(D\u00017\u0015\t9\u0004(\u0001\u0005ekJ\fG/[8o\u0015\tI4&\u0001\u0006d_:\u001cWO\u001d:f]RL!a\u000f\u001c\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006AA/[7f_V$\b%\u0001\u0004=S:LGO\u0010\u000b\u0003\u007f\u0005\u00032\u0001\u0011\u0001%\u001b\u00059\u0001\"\u0002\u001a\u0004\u0001\u0004!\u0014aC2sK\u0006$X\rT8hS\u000e$\"\u0001\u0012&\u0011\u0005\u0015CU\"\u0001$\u000b\u0005\u001d[\u0011!B:uC\u001e,\u0017BA%G\u0005=9%/\u00199i'R\fw-\u001a'pO&\u001c\u0007\"B&\u0005\u0001\u0004a\u0015aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\bCA'O\u001b\u0005Y\u0011BA(\f\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\t!\u000b\u0005\u0002T16\tAK\u0003\u0002V-\u0006!A.\u00198h\u0015\u00059\u0016\u0001\u00026bm\u0006L!!\u0017+\u0003\rM#(/\u001b8hQ\t\u00011\f\u0005\u0002]?6\tQL\u0003\u0002_\u001b\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0001l&aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public final class DropWithin<T>
extends GraphStages.SimpleLinearGraphStage<T> {
    private final FiniteDuration timeout;

    public FiniteDuration timeout() {
        return this.timeout;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private final long startNanoTime;
            private final long timeoutInNano;
            private final /* synthetic */ DropWithin $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private long startNanoTime() {
                return this.startNanoTime;
            }

            private long timeoutInNano() {
                return this.timeoutInNano;
            }

            public void onPush() {
                if (System.nanoTime() - this.startNanoTime() <= this.timeoutInNano()) {
                    this.pull(this.$outer.in());
                    return;
                }
                this.push(this.$outer.out(), this.grab(this.$outer.in()));
                this.setHandler(this.$outer.in(), new InHandler(this){
                    private final /* synthetic */ $anon$39 $outer;

                    public void onUpstreamFinish() throws Exception {
                        InHandler.onUpstreamFinish$(this);
                    }

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.push(this.$outer.akka$stream$impl$fusing$DropWithin$$anon$$$outer().out(), this.$outer.grab(this.$outer.akka$stream$impl$fusing$DropWithin$$anon$$$outer().in()));
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public /* synthetic */ DropWithin akka$stream$impl$fusing$DropWithin$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.startNanoTime = System.nanoTime();
                this.timeoutInNano = $outer.timeout().toNanos();
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public String toString() {
        return "DropWithin";
    }

    public DropWithin(FiniteDuration timeout2) {
        this.timeout = timeout2;
    }
}

