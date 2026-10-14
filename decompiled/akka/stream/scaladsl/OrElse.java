/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.UniformFanInShape;
import akka.stream.UniformFanInShape$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.OrElse$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005m<Qa\u0005\u000b\t\u0002m1Q!\b\u000b\t\u0002yAQ!J\u0001\u0005\u0002\u0019BqaJ\u0001C\u0002\u0013%\u0001\u0006\u0003\u0004u\u0003\u0001\u0006I!\u000b\u0005\u0006k\u0006!\tA\u001e\u0004\u0006;Q\u0011aC\u000b\u0005\u0006K\u0019!\t!\u0011\u0005\b\u0007\u001a\u0011\r\u0011\"\u0001E\u0011\u0019Ae\u0001)A\u0005\u000b\"9\u0011J\u0002b\u0001\n\u0003!\u0005B\u0002&\u0007A\u0003%Q\tC\u0004L\r\t\u0007I\u0011\u0001'\t\rA3\u0001\u0015!\u0003N\u0011\u001d\tfA1A\u0005BICaa\u0015\u0004!\u0002\u0013\u0011\u0004\"\u0002+\u0007\t#*\u0006\"B-\u0007\t\u0003R\u0006\"\u00021\u0007\t\u0003\n\u0017AB(s\u000b2\u001cXM\u0003\u0002\u0016-\u0005A1oY1mC\u0012\u001cHN\u0003\u0002\u00181\u000511\u000f\u001e:fC6T\u0011!G\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005q\tQ\"\u0001\u000b\u0003\r=\u0013X\t\\:f'\t\tq\u0004\u0005\u0002!G5\t\u0011EC\u0001#\u0003\u0015\u00198-\u00197b\u0013\t!\u0013E\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003m\t\u0011b]5oO2,Go\u001c8\u0016\u0003%\u00022\u0001\b\u0004<+\tY\u0003h\u0005\u0002\u0007YA\u0019Q\u0006\r\u001a\u000e\u00039R!a\f\f\u0002\u000bM$\u0018mZ3\n\u0005Er#AC$sCBD7\u000b^1hKB!1\u0007\u000e\u001c7\u001b\u00051\u0012BA\u001b\u0017\u0005E)f.\u001b4pe64\u0015M\\%o'\"\f\u0007/\u001a\t\u0003oab\u0001\u0001B\u0003:\r\t\u0007!HA\u0001U#\tYd\b\u0005\u0002!y%\u0011Q(\t\u0002\b\u001d>$\b.\u001b8h!\t\u0001s(\u0003\u0002AC\t\u0019\u0011I\\=\u0015\u0003\t\u00032\u0001\b\u00047\u0003\u001d\u0001(/[7bef,\u0012!\u0012\t\u0004g\u00193\u0014BA$\u0017\u0005\u0015Ie\u000e\\3u\u0003!\u0001(/[7bef\u0004\u0013!C:fG>tG-\u0019:z\u0003)\u0019XmY8oI\u0006\u0014\u0018\u0010I\u0001\u0004_V$X#A'\u0011\u0007Mre'\u0003\u0002P-\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\t!'\u0001\u0004tQ\u0006\u0004X\rI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#\u0001,\u0011\u0005M:\u0016B\u0001-\u0017\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0002\\=B\u0011Q\u0006X\u0005\u0003;:\u0012qb\u0012:ba\"\u001cF/Y4f\u0019><\u0017n\u0019\u0005\u0006?F\u0001\rAV\u0001\u0014S:DWM]5uK\u0012\fE\u000f\u001e:jEV$Xm]\u0001\ti>\u001cFO]5oOR\t!\r\u0005\u0002dU:\u0011A\r\u001b\t\u0003K\u0006j\u0011A\u001a\u0006\u0003Oj\ta\u0001\u0010:p_Rt\u0014BA5\"\u0003\u0019\u0001&/\u001a3fM&\u00111\u000e\u001c\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005%\f\u0003F\u0001\u0004o!\ty'/D\u0001q\u0015\t\t\b$\u0001\u0006b]:|G/\u0019;j_:L!a\u001d9\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u000bg&tw\r\\3u_:\u0004\u0013!B1qa2LXCA<{)\u0005A\bc\u0001\u000f\u0007sB\u0011qG\u001f\u0003\u0006s\u0015\u0011\rA\u000f")
@InternalApi
public final class OrElse<T>
extends GraphStage<UniformFanInShape<T, T>> {
    private final Inlet<T> primary = Inlet$.MODULE$.apply("OrElse.primary");
    private final Inlet<T> secondary = Inlet$.MODULE$.apply("OrElse.secondary");
    private final Outlet<T> out = Outlet$.MODULE$.apply("OrElse.out");
    private final UniformFanInShape<T, T> shape = UniformFanInShape$.MODULE$.apply(this.out(), ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Inlet[]{this.primary(), this.secondary()}));

    public static <T> OrElse<T> apply() {
        return OrElse$.MODULE$.apply();
    }

    public Inlet<T> primary() {
        return this.primary;
    }

    public Inlet<T> secondary() {
        return this.secondary;
    }

    public Outlet<T> out() {
        return this.out;
    }

    @Override
    public UniformFanInShape<T, T> shape() {
        return this.shape;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.orElse();
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new OutHandler(this){
            private Inlet<T> currentIn;
            private boolean primaryPushed;
            private final /* synthetic */ OrElse $outer;

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onPull() {
                this.pull(this.currentIn);
            }

            public void onPush() {
                if (!this.primaryPushed) {
                    this.primaryPushed = true;
                    this.cancel(this.$outer.secondary());
                }
                T elem = this.grab(this.$outer.primary());
                this.push(this.$outer.out(), elem);
            }

            public void onUpstreamFinish() {
                if (!this.primaryPushed && !this.isClosed(this.$outer.secondary())) {
                    this.currentIn = this.$outer.secondary();
                    if (this.isAvailable(this.$outer.out())) {
                        this.pull(this.$outer.secondary());
                        return;
                    }
                    return;
                }
                this.completeStage();
            }

            public /* synthetic */ OrElse akka$stream$scaladsl$OrElse$$anon$$$outer() {
                return this.$outer;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                OutHandler.$init$(this);
                InHandler.$init$(this);
                this.currentIn = $outer.primary();
                this.primaryPushed = false;
                this.setHandler($outer.secondary(), new InHandler(this){
                    private final /* synthetic */ $anon$24 $outer;

                    public void onUpstreamFailure(Throwable ex) throws Exception {
                        InHandler.onUpstreamFailure$(this, ex);
                    }

                    public void onPush() {
                        this.$outer.push(this.$outer.akka$stream$scaladsl$OrElse$$anon$$$outer().out(), this.$outer.grab(this.$outer.akka$stream$scaladsl$OrElse$$anon$$$outer().secondary()));
                    }

                    public void onUpstreamFinish() {
                        if (this.$outer.isClosed(this.$outer.akka$stream$scaladsl$OrElse$$anon$$$outer().primary())) {
                            this.$outer.completeStage();
                            return;
                        }
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        InHandler.$init$(this);
                    }
                });
                this.setHandlers($outer.primary(), $outer.out(), this);
            }
        };
    }

    public String toString() {
        return "OrElse";
    }
}

