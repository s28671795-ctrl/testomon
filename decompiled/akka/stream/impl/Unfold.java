/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q3QAC\u0006\u0003\u001fEA\u0001\u0002\f\u0001\u0003\u0002\u0003\u0006I!\f\u0005\ta\u0001\u0011\t\u0011)A\u0005c!)!\b\u0001C\u0001w!9\u0001\t\u0001b\u0001\n\u0003\t\u0005BB#\u0001A\u0003%!\tC\u0004G\u0001\t\u0007I\u0011I$\t\r!\u0003\u0001\u0015!\u0003\u001a\u0011\u0015I\u0005\u0001\"\u0011K\u0011\u0015q\u0005\u0001\"\u0011P\u0005\u0019)fNZ8mI*\u0011A\"D\u0001\u0005S6\u0004HN\u0003\u0002\u000f\u001f\u000511\u000f\u001e:fC6T\u0011\u0001E\u0001\u0005C.\\\u0017-F\u0002\u0013]}\u0019\"\u0001A\n\u0011\u0007Q9\u0012$D\u0001\u0016\u0015\t1R\"A\u0003ti\u0006<W-\u0003\u0002\u0019+\tQqI]1qQN#\u0018mZ3\u0011\u0007iYR$D\u0001\u000e\u0013\taRBA\u0006T_V\u00148-Z*iCB,\u0007C\u0001\u0010 \u0019\u0001!Q\u0001\t\u0001C\u0002\t\u0012\u0011!R\u0002\u0001#\t\u0019\u0013\u0006\u0005\u0002%O5\tQEC\u0001'\u0003\u0015\u00198-\u00197b\u0013\tASEA\u0004O_RD\u0017N\\4\u0011\u0005\u0011R\u0013BA\u0016&\u0005\r\te._\u0001\u0002gB\u0011aD\f\u0003\u0006_\u0001\u0011\rA\t\u0002\u0002'\u0006\ta\r\u0005\u0003%e5\"\u0014BA\u001a&\u0005%1UO\\2uS>t\u0017\u0007E\u0002%k]J!AN\u0013\u0003\r=\u0003H/[8o!\u0011!\u0003(L\u000f\n\u0005e*#A\u0002+va2,''\u0001\u0004=S:LGO\u0010\u000b\u0004yyz\u0004\u0003B\u001f\u0001[ui\u0011a\u0003\u0005\u0006Y\r\u0001\r!\f\u0005\u0006a\r\u0001\r!M\u0001\u0004_V$X#\u0001\"\u0011\u0007i\u0019U$\u0003\u0002E\u001b\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\t\u0011$\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A&\u0011\u0005ia\u0015BA'\u000e\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002Q'B\u0011A#U\u0005\u0003%V\u0011qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0006)&\u0001\raS\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm\u001d\u0015\u0003\u0001Y\u0003\"a\u0016.\u000e\u0003aS!!W\b\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002\\1\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class Unfold<S, E>
extends GraphStage<SourceShape<E>> {
    public final S akka$stream$impl$Unfold$$s;
    public final Function1<S, Option<Tuple2<S, E>>> akka$stream$impl$Unfold$$f;
    private final Outlet<E> out;
    private final SourceShape<E> shape;

    public Outlet<E> out() {
        return this.out;
    }

    @Override
    public SourceShape<E> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.unfold();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private S state;
            private final /* synthetic */ Unfold $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onPull() {
                Some some;
                Tuple2 tuple2;
                Option<Tuple2<S, E>> option = this.$outer.akka$stream$impl$Unfold$$f.apply(this.state);
                if (option instanceof Some && (tuple2 = (Tuple2)(some = (Some)option).value()) != null) {
                    T1 newState = tuple2._1();
                    T2 v = tuple2._2();
                    this.push(this.$outer.out(), v);
                    this.state = newState;
                    return;
                }
                if (None$.MODULE$.equals(option)) {
                    this.complete(this.$outer.out());
                    return;
                }
                throw new MatchError(option);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.state = $outer.akka$stream$impl$Unfold$$s;
                this.setHandler($outer.out(), this);
            }
        };
    }

    public Unfold(S s2, Function1<S, Option<Tuple2<S, E>>> f) {
        this.akka$stream$impl$Unfold$$s = s2;
        this.akka$stream$impl$Unfold$$f = f;
        this.out = Outlet$.MODULE$.apply("Unfold.out");
        this.shape = new SourceShape<E>(this.out());
    }
}

