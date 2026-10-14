/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Graph;
import akka.stream.SourceShape;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.TraversalBuilder$;
import akka.stream.impl.fusing.Collect$;
import akka.stream.impl.fusing.GraphStages;
import akka.stream.impl.fusing.RecoverWith$;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}qA\u0002\u0007\u000e\u0011\u0003\tRC\u0002\u0004\u0018\u001b!\u0005\u0011\u0003\u0007\u0005\u0006?\u0005!\t!\t\u0004\u0006/5\u00111C\u000b\u0005\t\u0013\u000e\u0011)\u0019!C\u0001\u0015\"Aaj\u0001B\u0001B\u0003%1\n\u0003\u0005P\u0007\t\u0015\r\u0011\"\u0001Q\u0011!97A!A!\u0002\u0013\t\u0006\"B\u0010\u0004\t\u0003A\u0007\"\u00027\u0004\t\u0003j\u0007\"B9\u0004\t\u0003\u0012\bbBA\u0005\u0007\u0011\u0005\u00131B\u0001\f%\u0016\u001cwN^3s/&$\bN\u0003\u0002\u000f\u001f\u00051a-^:j]\u001eT!\u0001E\t\u0002\t%l\u0007\u000f\u001c\u0006\u0003%M\taa\u001d;sK\u0006l'\"\u0001\u000b\u0002\t\u0005\\7.\u0019\t\u0003-\u0005i\u0011!\u0004\u0002\f%\u0016\u001cwN^3s/&$\bn\u0005\u0002\u00023A\u0011!$H\u0007\u00027)\tA$A\u0003tG\u0006d\u0017-\u0003\u0002\u001f7\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002+!\u0012\u0011a\t\t\u0003I\u001dj\u0011!\n\u0006\u0003MM\t!\"\u00198o_R\fG/[8o\u0013\tASEA\u0006J]R,'O\\1m\u0003BL\u0007F\u0001\u0001$+\rY\u0003)Z\n\u0003\u00071\u00022!L\u001e?\u001d\tq\u0013H\u0004\u00020q9\u0011\u0001g\u000e\b\u0003cYr!AM\u001b\u000e\u0003MR!\u0001\u000e\u0011\u0002\rq\u0012xn\u001c;?\u0013\u0005!\u0012B\u0001\n\u0014\u0013\t\u0001\u0012#\u0003\u0002\u000f\u001f%\u0011!(D\u0001\f\u000fJ\f\u0007\u000f[*uC\u001e,7/\u0003\u0002={\t12+[7qY\u0016d\u0015N\\3be\u001e\u0013\u0018\r\u001d5Ti\u0006<WM\u0003\u0002;\u001bA\u0011q\b\u0011\u0007\u0001\t\u0015\t5A1\u0001C\u0005\u0005!\u0016CA\"G!\tQB)\u0003\u0002F7\t9aj\u001c;iS:<\u0007C\u0001\u000eH\u0013\tA5DA\u0002B]f\fa\"\\1yS6,XNU3ue&,7/F\u0001L!\tQB*\u0003\u0002N7\t\u0019\u0011J\u001c;\u0002\u001f5\f\u00070[7v[J+GO]5fg\u0002\n!\u0001\u001d4\u0016\u0003E\u0003BA\u0007*U;&\u00111k\u0007\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0011QK\u0017\b\u0003-bs!AM,\n\u0003qI!!W\u000e\u0002\u000fA\f7m[1hK&\u00111\f\u0018\u0002\n)\"\u0014xn^1cY\u0016T!!W\u000e\u0011\ty{\u0016\rZ\u0007\u0002#%\u0011\u0001-\u0005\u0002\u0006\u000fJ\f\u0007\u000f\u001b\t\u0004=\nt\u0014BA2\u0012\u0005-\u0019v.\u001e:dKNC\u0017\r]3\u0011\u0005}*G!\u00024\u0004\u0005\u0004\u0011%!A'\u0002\u0007A4\u0007\u0005F\u0002jU.\u0004BAF\u0002?I\")\u0011\n\u0003a\u0001\u0017\")q\n\u0003a\u0001#\u0006\t\u0012N\\5uS\u0006d\u0017\t\u001e;sS\n,H/Z:\u0016\u00039\u0004\"AX8\n\u0005A\f\"AC!uiJL'-\u001e;fg\u0006Y1M]3bi\u0016dunZ5d)\r\u0019\u0018Q\u0001\n\u0005iZdxP\u0002\u0003v\u0015\u0001\u0019(\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004CA<{\u001b\u0005A(BA=\u0012\u0003\u0015\u0019H/Y4f\u0013\tY\bPA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d!\t9X0\u0003\u0002\u007fq\nI\u0011J\u001c%b]\u0012dWM\u001d\t\u0004o\u0006\u0005\u0011bAA\u0002q\nQq*\u001e;IC:$G.\u001a:\t\r\u0005\u001d!\u00021\u0001o\u0003\u0011\tG\u000f\u001e:\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0004\u0011\t\u0005=\u0011q\u0003\b\u0005\u0003#\t\u0019\u0002\u0005\u000237%\u0019\u0011QC\u000e\u0002\rA\u0013X\rZ3g\u0013\u0011\tI\"a\u0007\u0003\rM#(/\u001b8h\u0015\r\t)b\u0007\u0015\u0003\u0007\r\u0002")
@InternalApi
public final class RecoverWith<T, M>
extends GraphStages.SimpleLinearGraphStage<T> {
    private final int maximumRetries;
    private final PartialFunction<Throwable, Graph<SourceShape<T>, M>> pf;

    public int maximumRetries() {
        return this.maximumRetries;
    }

    public PartialFunction<Throwable, Graph<SourceShape<T>, M>> pf() {
        return this.pf;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.recoverWith();
    }

    @Override
    public GraphStageLogic createLogic(Attributes attr) {
        return new InHandler(this){
            private int attempt;
            private final /* synthetic */ RecoverWith $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            private int attempt() {
                return this.attempt;
            }

            private void attempt_$eq(int x$1) {
                this.attempt = x$1;
            }

            public void onPush() {
                this.push(this.$outer.out(), this.grab(this.$outer.in()));
            }

            public void onUpstreamFailure(Throwable ex) {
                this.akka$stream$impl$fusing$RecoverWith$$anon$$onFailure(ex);
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void akka$stream$impl$fusing$RecoverWith$$anon$$onFailure(Throwable ex) {
                if (this.$outer.maximumRetries() < 0 || this.attempt() < this.$outer.maximumRetries()) {
                    boolean bl = false;
                    Graph graph = null;
                    Object object = this.$outer.pf().applyOrElse(ex, Collect$.MODULE$.NotApplied());
                    Function1<Object, Object> function1 = Collect$.MODULE$.NotApplied();
                    Object object2 = object;
                    if (!(function1 != null ? !function1.equals(object2) : object2 != null)) {
                        this.failStage(ex);
                        return;
                    }
                    if (object instanceof Graph) {
                        bl = true;
                        graph = (Graph)object;
                        if (TraversalBuilder$.MODULE$.isEmptySource(graph)) {
                            this.completeStage();
                            return;
                        }
                    }
                    if (bl) {
                        this.switchTo(graph);
                        this.attempt_$eq(this.attempt() + 1);
                        return;
                    }
                    throw new IllegalStateException();
                }
                this.failStage(ex);
            }

            private void switchTo(Graph<SourceShape<T>, M> source2) {
                GraphStageLogic.SubSinkInlet<T> sinkIn = new GraphStageLogic.SubSinkInlet<T>(this, "RecoverWithSink");
                sinkIn.setHandler(new InHandler(this, sinkIn){
                    private final /* synthetic */ $anon$43 $outer;
                    private final GraphStageLogic.SubSinkInlet sinkIn$1;

                    public void onPush() {
                        this.$outer.push(this.$outer.akka$stream$impl$fusing$RecoverWith$$anon$$$outer().out(), this.sinkIn$1.grab());
                    }

                    public void onUpstreamFinish() {
                        this.$outer.completeStage();
                    }

                    public void onUpstreamFailure(Throwable ex) {
                        this.$outer.akka$stream$impl$fusing$RecoverWith$$anon$$onFailure(ex);
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        this.sinkIn$1 = sinkIn$1;
                        InHandler.$init$(this);
                    }
                });
                OutHandler outHandler = new OutHandler(null, sinkIn){
                    private final GraphStageLogic.SubSinkInlet sinkIn$1;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        this.sinkIn$1.pull();
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.sinkIn$1.cancel(cause);
                    }
                    {
                        this.sinkIn$1 = sinkIn$1;
                        OutHandler.$init$(this);
                    }
                };
                Source$.MODULE$.fromGraph(source2).runWith(sinkIn.sink(), this.interpreter().subFusingMaterializer());
                this.setHandler(this.$outer.out(), outHandler);
                if (this.isAvailable(this.$outer.out())) {
                    sinkIn.pull();
                    return;
                }
            }

            public /* synthetic */ RecoverWith akka$stream$impl$fusing$RecoverWith$$anon$$$outer() {
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
                this.attempt = 0;
                this.setHandlers($outer.in(), $outer.out(), this);
            }
        };
    }

    public String toString() {
        return "RecoverWith";
    }

    public RecoverWith(int maximumRetries, PartialFunction<Throwable, Graph<SourceShape<T>, M>> pf) {
        this.maximumRetries = maximumRetries;
        this.pf = pf;
    }
}

