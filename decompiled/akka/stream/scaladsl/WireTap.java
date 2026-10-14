/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.FanOutShape2;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.scaladsl.WireTap$;
import akka.stream.scaladsl.WireTap$$anon$13$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a<Qa\u0005\u000b\t\u0002m1Q!\b\u000b\t\u0002yAQ!J\u0001\u0005\u0002\u0019BqaJ\u0001C\u0002\u0013%\u0001\u0006\u0003\u0004r\u0003\u0001\u0006I!\u000b\u0005\u0006e\u0006!\ta\u001d\u0004\u0006;Q\u0011aC\u000b\u0005\u0006K\u0019!\t!\u0011\u0005\b\u0007\u001a\u0011\r\u0011\"\u0001E\u0011\u0019Ae\u0001)A\u0005\u000b\"9\u0011J\u0002b\u0001\n\u0003Q\u0005B\u0002(\u0007A\u0003%1\nC\u0004P\r\t\u0007I\u0011\u0001&\t\rA3\u0001\u0015!\u0003L\u0011\u0015\tf\u0001\"\u0011S\u0011\u001d1fA1A\u0005B]Ca\u0001\u0017\u0004!\u0002\u0013\u0011\u0004\"B-\u0007\t\u0003R\u0006\"\u00021\u0007\t\u0003\n\u0017aB,je\u0016$\u0016\r\u001d\u0006\u0003+Y\t\u0001b]2bY\u0006$7\u000f\u001c\u0006\u0003/a\taa\u001d;sK\u0006l'\"A\r\u0002\t\u0005\\7.Y\u0002\u0001!\ta\u0012!D\u0001\u0015\u0005\u001d9\u0016N]3UCB\u001c\"!A\u0010\u0011\u0005\u0001\u001aS\"A\u0011\u000b\u0003\t\nQa]2bY\u0006L!\u0001J\u0011\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t1$A\u0005tS:<G.\u001a;p]V\t\u0011\u0006E\u0002\u001d\rm*\"a\u000b\u001d\u0014\u0005\u0019a\u0003cA\u00171e5\taF\u0003\u00020-\u0005)1\u000f^1hK&\u0011\u0011G\f\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007#B\u001a5mY2T\"\u0001\f\n\u0005U2\"\u0001\u0004$b]>+Ho\u00155ba\u0016\u0014\u0004CA\u001c9\u0019\u0001!Q!\u000f\u0004C\u0002i\u0012\u0011\u0001V\t\u0003wy\u0002\"\u0001\t\u001f\n\u0005u\n#a\u0002(pi\"Lgn\u001a\t\u0003A}J!\u0001Q\u0011\u0003\u0007\u0005s\u0017\u0010F\u0001C!\rabAN\u0001\u0003S:,\u0012!\u0012\t\u0004g\u00193\u0014BA$\u0017\u0005\u0015Ie\u000e\\3u\u0003\rIg\u000eI\u0001\b_V$X*Y5o+\u0005Y\u0005cA\u001aMm%\u0011QJ\u0006\u0002\u0007\u001fV$H.\u001a;\u0002\u0011=,H/T1j]\u0002\naa\\;u)\u0006\u0004\u0018aB8viR\u000b\u0007\u000fI\u0001\u0012S:LG/[1m\u0003R$(/\u001b2vi\u0016\u001cX#A*\u0011\u0005M\"\u0016BA+\u0017\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0006g\"\f\u0007/Z\u000b\u0002e\u000511\u000f[1qK\u0002\n1b\u0019:fCR,Gj\\4jGR\u00111L\u0018\t\u0003[qK!!\u0018\u0018\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDQaX\tA\u0002M\u000b1#\u001b8iKJLG/\u001a3BiR\u0014\u0018NY;uKN\f\u0001\u0002^8TiJLgn\u001a\u000b\u0002EB\u00111\r[\u0007\u0002I*\u0011QMZ\u0001\u0005Y\u0006twMC\u0001h\u0003\u0011Q\u0017M^1\n\u0005%$'AB*ue&tw\r\u000b\u0002\u0007WB\u0011An\\\u0007\u0002[*\u0011a\u000eG\u0001\u000bC:tw\u000e^1uS>t\u0017B\u00019n\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002\u0015MLgn\u001a7fi>t\u0007%A\u0003baBd\u00170\u0006\u0002uoR\tQ\u000fE\u0002\u001d\rY\u0004\"aN<\u0005\u000be*!\u0019\u0001\u001e")
@InternalApi
public final class WireTap<T>
extends GraphStage<FanOutShape2<T, T, T>> {
    private final Inlet<T> in = Inlet$.MODULE$.apply("WireTap.in");
    private final Outlet<T> outMain = Outlet$.MODULE$.apply("WireTap.outMain");
    private final Outlet<T> outTap = Outlet$.MODULE$.apply("WireTap.outTap");
    private final FanOutShape2<T, T, T> shape = new FanOutShape2<T, T, T>(this.in(), this.outMain(), this.outTap());

    public static <T> WireTap<T> apply() {
        return WireTap$.MODULE$.apply();
    }

    public Inlet<T> in() {
        return this.in;
    }

    public Outlet<T> outMain() {
        return this.outMain;
    }

    public Outlet<T> outTap() {
        return this.outTap;
    }

    @Override
    public Attributes initialAttributes() {
        return Stages$DefaultAttributes$.MODULE$.wireTap();
    }

    @Override
    public FanOutShape2<T, T, T> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private Option<T> akka$stream$scaladsl$WireTap$$anon$$pendingTap;
            private final /* synthetic */ WireTap $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onUpstreamFinish() throws Exception {
                InHandler.onUpstreamFinish$(this);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public Option<T> akka$stream$scaladsl$WireTap$$anon$$pendingTap() {
                return this.akka$stream$scaladsl$WireTap$$anon$$pendingTap;
            }

            public void akka$stream$scaladsl$WireTap$$anon$$pendingTap_$eq(Option<T> x$1) {
                this.akka$stream$scaladsl$WireTap$$anon$$pendingTap = x$1;
            }

            public void onPush() {
                T elem = this.grab(this.$outer.in());
                this.push(this.$outer.outMain(), elem);
                if (this.isAvailable(this.$outer.outTap())) {
                    this.push(this.$outer.outTap(), elem);
                    return;
                }
                this.akka$stream$scaladsl$WireTap$$anon$$pendingTap_$eq(new Some<T>(elem));
            }

            public void onPull() {
                this.pull(this.$outer.in());
            }

            public void onDownstreamFinish(Throwable cause) {
                this.cancelStage(cause);
            }

            public /* synthetic */ WireTap akka$stream$scaladsl$WireTap$$anon$$$outer() {
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
                this.akka$stream$scaladsl$WireTap$$anon$$pendingTap = None$.MODULE$;
                this.setHandler($outer.outTap(), new OutHandler(this){
                    private final /* synthetic */ $anon$13 $outer;

                    public void onDownstreamFinish() throws Exception {
                        OutHandler.onDownstreamFinish$(this);
                    }

                    public void onPull() {
                        Option<T> option = this.$outer.akka$stream$scaladsl$WireTap$$anon$$pendingTap();
                        if (option instanceof Some) {
                            Some some = (Some)option;
                            A elem = some.value();
                            this.$outer.push(this.$outer.akka$stream$scaladsl$WireTap$$anon$$$outer().outTap(), elem);
                            this.$outer.akka$stream$scaladsl$WireTap$$anon$$pendingTap_$eq(None$.MODULE$);
                            return;
                        }
                        if (None$.MODULE$.equals(option)) {
                            return;
                        }
                        throw new MatchError(option);
                    }

                    public void onDownstreamFinish(Throwable cause) {
                        this.$outer.setHandler(this.$outer.akka$stream$scaladsl$WireTap$$anon$$$outer().in(), new InHandler(this){
                            private final /* synthetic */ $anon$13$$anon$14 $outer;

                            public void onUpstreamFinish() throws Exception {
                                InHandler.onUpstreamFinish$(this);
                            }

                            public void onUpstreamFailure(Throwable ex) throws Exception {
                                InHandler.onUpstreamFailure$(this, ex);
                            }

                            public void onPush() {
                                this.$outer.akka$stream$scaladsl$WireTap$$anon$$anon$$$outer().push(this.$outer.akka$stream$scaladsl$WireTap$$anon$$anon$$$outer().akka$stream$scaladsl$WireTap$$anon$$$outer().outMain(), this.$outer.akka$stream$scaladsl$WireTap$$anon$$anon$$$outer().grab(this.$outer.akka$stream$scaladsl$WireTap$$anon$$anon$$$outer().akka$stream$scaladsl$WireTap$$anon$$$outer().in()));
                            }
                            {
                                if ($outer == null) {
                                    throw null;
                                }
                                this.$outer = $outer;
                                InHandler.$init$(this);
                            }
                        });
                        this.$outer.akka$stream$scaladsl$WireTap$$anon$$pendingTap_$eq(None$.MODULE$);
                    }

                    public /* synthetic */ $anon$13 akka$stream$scaladsl$WireTap$$anon$$anon$$$outer() {
                        return this.$outer;
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                        OutHandler.$init$(this);
                    }
                });
                this.setHandlers($outer.in(), $outer.outMain(), this);
            }
        };
    }

    public String toString() {
        return "WireTap";
    }
}

