/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Attributes$SourceLocation$;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.ContextPropagation;
import akka.stream.impl.ContextPropagation$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.collection.Iterator;
import scala.package$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U4Q\u0001D\u0007\u0003'UA\u0001b\r\u0001\u0003\u0006\u0004%\t\u0001\u000e\u0005\t\t\u0002\u0011\t\u0011)A\u0005k!)Q\t\u0001C\u0001\r\"9!\n\u0001b\u0001\n\u0013Y\u0005BB(\u0001A\u0003%A\nC\u0004Q\u0001\t\u0007I\u0011B)\t\rU\u0003\u0001\u0015!\u0003S\u0011\u00151\u0006\u0001\"\u0011X\u0011\u001dY\u0006A1A\u0005BqCa!\u0018\u0001!\u0002\u0013i\u0002\"\u00020\u0001\t\u0003z&AB#ya\u0006tGM\u0003\u0002\u000f\u001f\u00051a-^:j]\u001eT!\u0001E\t\u0002\t%l\u0007\u000f\u001c\u0006\u0003%M\taa\u001d;sK\u0006l'\"\u0001\u000b\u0002\t\u0005\\7.Y\u000b\u0004-\r\n4C\u0001\u0001\u0018!\rA2$H\u0007\u00023)\u0011!$E\u0001\u0006gR\fw-Z\u0005\u00039e\u0011!b\u0012:ba\"\u001cF/Y4f!\u0011qr$\t\u0019\u000e\u0003EI!\u0001I\t\u0003\u0013\u0019cwn^*iCB,\u0007C\u0001\u0012$\u0019\u0001!Q\u0001\n\u0001C\u0002\u0019\u0012!!\u00138\u0004\u0001E\u0011q%\f\t\u0003Q-j\u0011!\u000b\u0006\u0002U\u0005)1oY1mC&\u0011A&\u000b\u0002\b\u001d>$\b.\u001b8h!\tAc&\u0003\u00020S\t\u0019\u0011I\\=\u0011\u0005\t\nD!\u0002\u001a\u0001\u0005\u00041#aA(vi\u0006YQ\r\u001f;sCB|G.\u0019;f+\u0005)\u0004\u0003\u0002\u00157CaJ!aN\u0015\u0003\u0013\u0019+hn\u0019;j_:\f\u0004cA\u001dBa9\u0011!h\u0010\b\u0003wyj\u0011\u0001\u0010\u0006\u0003{\u0015\na\u0001\u0010:p_Rt\u0014\"\u0001\u0016\n\u0005\u0001K\u0013a\u00029bG.\fw-Z\u0005\u0003\u0005\u000e\u0013\u0001\"\u0013;fe\u0006$xN\u001d\u0006\u0003\u0001&\nA\"\u001a=ue\u0006\u0004x\u000e\\1uK\u0002\na\u0001P5oSRtDCA$J!\u0011A\u0005!\t\u0019\u000e\u00035AQaM\u0002A\u0002U\n!!\u001b8\u0016\u00031\u00032AH'\"\u0013\tq\u0015CA\u0003J]2,G/A\u0002j]\u0002\n1a\\;u+\u0005\u0011\u0006c\u0001\u0010Ta%\u0011A+\u0005\u0002\u0007\u001fV$H.\u001a;\u0002\t=,H\u000fI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#\u0001-\u0011\u0005yI\u0016B\u0001.\u0012\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002;\u000511\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR\u0011\u0001\r\u001c\n\u0005C\u000e4\u0017N\u0002\u0003c\u0017\u0001\u0001'\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004C\u0001\re\u0013\t)\u0017DA\bHe\u0006\u0004\bn\u0015;bO\u0016dunZ5d!\tAr-\u0003\u0002i3\tI\u0011J\u001c%b]\u0012dWM\u001d\t\u00031)L!a[\r\u0003\u0015=+H\u000fS1oI2,'\u000fC\u0003n\u0017\u0001\u0007\u0001,\u0001\u0003biR\u0014\bF\u0001\u0001p!\t\u00018/D\u0001r\u0015\t\u00118#\u0001\u0006b]:|G/\u0019;j_:L!\u0001^9\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b")
@InternalApi
public final class Expand<In, Out>
extends GraphStage<FlowShape<In, Out>> {
    private final Function1<In, Iterator<Out>> extrapolate;
    private final Inlet<In> akka$stream$impl$fusing$Expand$$in;
    private final Outlet<Out> akka$stream$impl$fusing$Expand$$out;
    private final FlowShape<In, Out> shape;

    public Function1<In, Iterator<Out>> extrapolate() {
        return this.extrapolate;
    }

    public Inlet<In> akka$stream$impl$fusing$Expand$$in() {
        return this.akka$stream$impl$fusing$Expand$$in;
    }

    public Outlet<Out> akka$stream$impl$fusing$Expand$$out() {
        return this.akka$stream$impl$fusing$Expand$$out;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.expand().and(Attributes$SourceLocation$.MODULE$.forLambda(this.extrapolate()));
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes attr) {
        return new InHandler(this){
            private Iterator<Out> iterator;
            private boolean expanded;
            private final ContextPropagation contextPropagation;
            private final /* synthetic */ Expand $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            private Iterator<Out> iterator() {
                return this.iterator;
            }

            private void iterator_$eq(Iterator<Out> x$1) {
                this.iterator = x$1;
            }

            private boolean expanded() {
                return this.expanded;
            }

            private void expanded_$eq(boolean x$1) {
                this.expanded = x$1;
            }

            private ContextPropagation contextPropagation() {
                return this.contextPropagation;
            }

            public void preStart() {
                this.pull(this.$outer.akka$stream$impl$fusing$Expand$$in());
            }

            public void onPush() {
                this.iterator_$eq(this.$outer.extrapolate().apply(this.grab(this.$outer.akka$stream$impl$fusing$Expand$$in())));
                if (this.iterator().hasNext()) {
                    this.contextPropagation().suspendContext();
                    if (this.isAvailable(this.$outer.akka$stream$impl$fusing$Expand$$out())) {
                        this.expanded_$eq(true);
                        this.pull(this.$outer.akka$stream$impl$fusing$Expand$$in());
                        this.push(this.$outer.akka$stream$impl$fusing$Expand$$out(), this.iterator().next());
                        return;
                    }
                    this.expanded_$eq(false);
                    return;
                }
                this.pull(this.$outer.akka$stream$impl$fusing$Expand$$in());
            }

            public void onUpstreamFinish() {
                if (this.iterator().hasNext() && !this.expanded()) {
                    return;
                }
                this.completeStage();
            }

            public void onPull() {
                if (this.iterator().hasNext()) {
                    this.contextPropagation().resumeContext();
                    if (!this.expanded()) {
                        this.expanded_$eq(true);
                        if (this.isClosed(this.$outer.akka$stream$impl$fusing$Expand$$in())) {
                            this.push(this.$outer.akka$stream$impl$fusing$Expand$$out(), this.iterator().next());
                            this.completeStage();
                            return;
                        }
                        this.pull(this.$outer.akka$stream$impl$fusing$Expand$$in());
                        this.push(this.$outer.akka$stream$impl$fusing$Expand$$out(), this.iterator().next());
                        return;
                    }
                    this.push(this.$outer.akka$stream$impl$fusing$Expand$$out(), this.iterator().next());
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.iterator = package$.MODULE$.Iterator().empty();
                this.expanded = false;
                this.contextPropagation = ContextPropagation$.MODULE$.apply();
                this.setHandler($outer.akka$stream$impl$fusing$Expand$$in(), this);
                this.setHandler($outer.akka$stream$impl$fusing$Expand$$out(), this);
            }
        };
    }

    public Expand(Function1<In, Iterator<Out>> extrapolate) {
        this.extrapolate = extrapolate;
        this.akka$stream$impl$fusing$Expand$$in = Inlet$.MODULE$.apply("expand.in");
        this.akka$stream$impl$fusing$Expand$$out = Outlet$.MODULE$.apply("expand.out");
        this.shape = new FlowShape<In, Out>(this.akka$stream$impl$fusing$Expand$$in(), this.akka$stream$impl$fusing$Expand$$out());
    }
}

