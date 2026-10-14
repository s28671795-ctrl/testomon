/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.OutHandler;
import java.util.Iterator;
import java.util.stream.BaseStream;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i3Q\u0001C\u0005\u0003\u0017=A\u0001B\u000b\u0001\u0003\u0002\u0003\u0006Ia\u000b\u0005\u0006w\u0001!\t\u0001\u0010\u0005\b\u0001\u0002\u0011\r\u0011\"\u0001B\u0011\u0019)\u0005\u0001)A\u0005\u0005\"9a\t\u0001b\u0001\n\u0003:\u0005B\u0002%\u0001A\u0003%q\u0003C\u0003J\u0001\u0011\u0005#J\u0001\tKCZ\f7\u000b\u001e:fC6\u001cv.\u001e:dK*\u0011!bC\u0001\u0005S6\u0004HN\u0003\u0002\r\u001b\u000511\u000f\u001e:fC6T\u0011AD\u0001\u0005C.\\\u0017-F\u0002\u0011;a\u001a\"\u0001A\t\u0011\u0007I)r#D\u0001\u0014\u0015\t!2\"A\u0003ti\u0006<W-\u0003\u0002\u0017'\tQqI]1qQN#\u0018mZ3\u0011\u0007aI2$D\u0001\f\u0013\tQ2BA\u0006T_V\u00148-Z*iCB,\u0007C\u0001\u000f\u001e\u0019\u0001!QA\b\u0001C\u0002\u0001\u0012\u0011\u0001V\u0002\u0001#\t\ts\u0005\u0005\u0002#K5\t1EC\u0001%\u0003\u0015\u00198-\u00197b\u0013\t13EA\u0004O_RD\u0017N\\4\u0011\u0005\tB\u0013BA\u0015$\u0005\r\te._\u0001\u0005_B,g\u000eE\u0002#Y9J!!L\u0012\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004\u0003B\u001867]j\u0011\u0001\r\u0006\u0003\u0019ER!AM\u001a\u0002\tU$\u0018\u000e\u001c\u0006\u0002i\u0005!!.\u0019<b\u0013\t1\u0004G\u0001\u0006CCN,7\u000b\u001e:fC6\u0004\"\u0001\b\u001d\u0005\u000be\u0002!\u0019\u0001\u001e\u0003\u0003M\u000b\"!\t\u0018\u0002\rqJg.\u001b;?)\tit\b\u0005\u0003?\u0001m9T\"A\u0005\t\u000b)\u0012\u0001\u0019A\u0016\u0002\u0007=,H/F\u0001C!\rA2iG\u0005\u0003\t.\u0011aaT;uY\u0016$\u0018\u0001B8vi\u0002\nQa\u001d5ba\u0016,\u0012aF\u0001\u0007g\"\f\u0007/\u001a\u0011\u0002\u0017\r\u0014X-\u0019;f\u0019><\u0017n\u0019\u000b\u0003\u0017:\u0003\"A\u0005'\n\u00055\u001b\"aD$sCBD7\u000b^1hK2{w-[2\t\u000b=;\u0001\u0019\u0001)\u0002'%t\u0007.\u001a:ji\u0016$\u0017\t\u001e;sS\n,H/Z:\u0011\u0005a\t\u0016B\u0001*\f\u0005)\tE\u000f\u001e:jEV$Xm\u001d\u0015\u0003\u0001Q\u0003\"!\u0016-\u000e\u0003YS!aV\u0007\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002Z-\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class JavaStreamSource<T, S extends BaseStream<T, S>>
extends GraphStage<SourceShape<T>> {
    public final Function0<BaseStream<T, S>> akka$stream$impl$JavaStreamSource$$open;
    private final Outlet<T> out;
    private final SourceShape<T> shape;

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public SourceShape<T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private BaseStream<T, S> stream;
            private Iterator<T> iter;
            private final /* synthetic */ JavaStreamSource $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void preStart() {
                this.stream = this.$outer.akka$stream$impl$JavaStreamSource$$open.apply();
                this.iter = this.stream.iterator();
            }

            public void postStop() {
                if (this.stream != null) {
                    this.stream.close();
                    return;
                }
            }

            public void onPull() {
                if (this.iter.hasNext()) {
                    this.push(this.$outer.out(), this.iter.next());
                    return;
                }
                this.complete(this.$outer.out());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                this.setHandler($outer.out(), this);
            }
        };
    }

    public JavaStreamSource(Function0<BaseStream<T, S>> open) {
        this.akka$stream$impl$JavaStreamSource$$open = open;
        this.out = Outlet$.MODULE$.apply("JavaStreamSource");
        this.shape = new SourceShape<T>(this.out());
    }
}

