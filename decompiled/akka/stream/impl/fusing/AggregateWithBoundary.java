/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.impl.fusing.AggregateWithBoundary$;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple2;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tme!\u0002\u0014(\u00056z\u0003\u0002\u0003/\u0001\u0005+\u0007I\u0011A/\t\u0011\u0011\u0004!\u0011#Q\u0001\nyC\u0001\"\u001a\u0001\u0003\u0016\u0004%\tA\u001a\u0005\ta\u0002\u0011\t\u0012)A\u0005O\"A\u0011\u000f\u0001BK\u0002\u0013\u0005!\u000f\u0003\u0005w\u0001\tE\t\u0015!\u0003t\u0011!9\bA!f\u0001\n\u0003A\b\"CA\u0007\u0001\tE\t\u0015!\u0003z\u0011\u001d\ty\u0001\u0001C\u0001\u0003#A\u0011\"a\b\u0001\u0005\u0004%\t!!\t\t\u0011\u0005%\u0002\u0001)A\u0005\u0003GA\u0011\"a\u000b\u0001\u0005\u0004%\t!!\f\t\u0011\u0005U\u0002\u0001)A\u0005\u0003_A\u0011\"a\u000e\u0001\u0005\u0004%\t%!\u000f\t\u000f\u0005m\u0002\u0001)A\u0005o!9\u0011Q\b\u0001\u0005B\u0005}\u0002\"CA)\u0001\u0005\u0005I\u0011AA*\u0011%\tY\bAI\u0001\n\u0003\ti\bC\u0005\u0002\u001c\u0002\t\n\u0011\"\u0001\u0002\u001e\"I\u0011\u0011\u0016\u0001\u0012\u0002\u0013\u0005\u00111\u0016\u0005\n\u0003o\u0003\u0011\u0013!C\u0001\u0003sC\u0011\"!2\u0001\u0003\u0003%\t%a2\t\u0013\u0005e\u0007!!A\u0005\u0002\u0005m\u0007\"CAr\u0001\u0005\u0005I\u0011AAs\u0011%\tY\u000fAA\u0001\n\u0003\ni\u000fC\u0005\u0002|\u0002\t\t\u0011\"\u0001\u0002~\"I!\u0011\u0001\u0001\u0002\u0002\u0013\u0005#1\u0001\u0005\n\u0005\u000f\u0001\u0011\u0011!C!\u0005\u0013A\u0011Ba\u0003\u0001\u0003\u0003%\tE!\u0004\t\u0013\t=\u0001!!A\u0005B\tEqA\u0003B\u0011O\u0005\u0005\t\u0012A\u0017\u0003$\u0019IaeJA\u0001\u0012\u0003i#Q\u0005\u0005\b\u0003\u001f\u0001C\u0011\u0001B\u001c\u0011%\u0011Y\u0001IA\u0001\n\u000b\u0012i\u0001C\u0005\u0003:\u0001\n\t\u0011\"!\u0003<!I!1\r\u0011\u0002\u0002\u0013\u0005%Q\r\u0005\n\u0005#\u0003\u0013\u0011!C\u0005\u0005'\u0013Q#Q4he\u0016<\u0017\r^3XSRD'i\\;oI\u0006\u0014\u0018P\u0003\u0002)S\u00051a-^:j]\u001eT!AK\u0016\u0002\t%l\u0007\u000f\u001c\u0006\u0003Y5\naa\u001d;sK\u0006l'\"\u0001\u0018\u0002\t\u0005\\7.Y\u000b\u0005au\u00127j\u0005\u0003\u0001c5\u0003\u0006c\u0001\u001a6o5\t1G\u0003\u00025W\u0005)1\u000f^1hK&\u0011ag\r\u0002\u000b\u000fJ\f\u0007\u000f[*uC\u001e,\u0007\u0003\u0002\u001d:w)k\u0011aK\u0005\u0003u-\u0012\u0011B\u00127poNC\u0017\r]3\u0011\u0005qjD\u0002\u0001\u0003\u0006}\u0001\u0011\r\u0001\u0011\u0002\u0003\u0013:\u001c\u0001!\u0005\u0002B\u000fB\u0011!)R\u0007\u0002\u0007*\tA)A\u0003tG\u0006d\u0017-\u0003\u0002G\u0007\n9aj\u001c;iS:<\u0007C\u0001\"I\u0013\tI5IA\u0002B]f\u0004\"\u0001P&\u0005\u000b1\u0003!\u0019\u0001!\u0003\u0007=+H\u000f\u0005\u0002C\u001d&\u0011qj\u0011\u0002\b!J|G-^2u!\t\t\u0016L\u0004\u0002S/:\u00111KV\u0007\u0002)*\u0011QkP\u0001\u0007yI|w\u000e\u001e \n\u0003\u0011K!\u0001W\"\u0002\u000fA\f7m[1hK&\u0011!l\u0017\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u00031\u000e\u000b\u0001\"\u00197m_\u000e\fG/Z\u000b\u0002=B\u0019!iX1\n\u0005\u0001\u001c%!\u0003$v]\u000e$\u0018n\u001c81!\ta$\rB\u0003d\u0001\t\u0007\u0001IA\u0002BO\u001e\f\u0011\"\u00197m_\u000e\fG/\u001a\u0011\u0002\u0013\u0005<wM]3hCR,W#A4\u0011\u000b\tC\u0017m\u000f6\n\u0005%\u001c%!\u0003$v]\u000e$\u0018n\u001c83!\u0011\u00115.Y7\n\u00051\u001c%A\u0002+va2,'\u0007\u0005\u0002C]&\u0011qn\u0011\u0002\b\u0005>|G.Z1o\u0003)\twm\u001a:fO\u0006$X\rI\u0001\bQ\u0006\u0014h/Z:u+\u0005\u0019\b\u0003\u0002\"uC*K!!^\"\u0003\u0013\u0019+hn\u0019;j_:\f\u0014\u0001\u00035beZ,7\u000f\u001e\u0011\u0002\u0017\u0015l\u0017\u000e^(o)&lWM]\u000b\u0002sB\u0019!I\u001f?\n\u0005m\u001c%AB(qi&|g\u000e\u0005\u0003CWvt\b\u0003\u0002\"uC6\u00042a`A\u0005\u001b\t\t\tA\u0003\u0003\u0002\u0004\u0005\u0015\u0011\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0007\u0005\u001d1)\u0001\u0006d_:\u001cWO\u001d:f]RLA!a\u0003\u0002\u0002\tqa)\u001b8ji\u0016$UO]1uS>t\u0017\u0001D3nSR|e\u000eV5nKJ\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0006\u0002\u0014\u0005]\u0011\u0011DA\u000e\u0003;\u0001b!!\u0006\u0001w\u0005TU\"A\u0014\t\u000bqK\u0001\u0019\u00010\t\u000b\u0015L\u0001\u0019A4\t\u000bEL\u0001\u0019A:\t\u000b]L\u0001\u0019A=\u0002\u0005%tWCAA\u0012!\u0011A\u0014QE\u001e\n\u0007\u0005\u001d2FA\u0003J]2,G/A\u0002j]\u0002\n1a\\;u+\t\ty\u0003\u0005\u00039\u0003cQ\u0015bAA\u001aW\t1q*\u001e;mKR\fAa\\;uA\u0005)1\u000f[1qKV\tq'\u0001\u0004tQ\u0006\u0004X\rI\u0001\fGJ,\u0017\r^3M_\u001eL7\r\u0006\u0003\u0002B\u0005\u001d\u0003c\u0001\u001a\u0002D%\u0019\u0011QI\u001a\u0003\u001f\u001d\u0013\u0018\r\u001d5Ti\u0006<W\rT8hS\u000eDq!!\u0013\u0011\u0001\u0004\tY%A\nj]\",'/\u001b;fI\u0006#HO]5ckR,7\u000fE\u00029\u0003\u001bJ1!a\u0014,\u0005)\tE\u000f\u001e:jEV$Xm]\u0001\u0005G>\u0004\u00180\u0006\u0005\u0002V\u0005m\u0013qLA2))\t9&!\u001a\u0002j\u0005=\u00141\u000f\t\n\u0003+\u0001\u0011\u0011LA/\u0003C\u00022\u0001PA.\t\u0015q\u0014C1\u0001A!\ra\u0014q\f\u0003\u0006GF\u0011\r\u0001\u0011\t\u0004y\u0005\rD!\u0002'\u0012\u0005\u0004\u0001\u0005\u0002\u0003/\u0012!\u0003\u0005\r!a\u001a\u0011\t\t{\u0016Q\f\u0005\tKF\u0001\n\u00111\u0001\u0002lAA!\t[A/\u00033\ni\u0007E\u0003CW\u0006uS\u000e\u0003\u0005r#A\u0005\t\u0019AA9!\u0019\u0011E/!\u0018\u0002b!Aq/\u0005I\u0001\u0002\u0004\t)\b\u0005\u0003Cu\u0006]\u0004#\u0002\"l\u0003sr\b#\u0002\"u\u0003;j\u0017AD2paf$C-\u001a4bk2$H%M\u000b\t\u0003\u007f\n)*a&\u0002\u001aV\u0011\u0011\u0011\u0011\u0016\u0004=\u0006\r5FAAC!\u0011\t9)!%\u000e\u0005\u0005%%\u0002BAF\u0003\u001b\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005=5)\u0001\u0006b]:|G/\u0019;j_:LA!a%\u0002\n\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000by\u0012\"\u0019\u0001!\u0005\u000b\r\u0014\"\u0019\u0001!\u0005\u000b1\u0013\"\u0019\u0001!\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eUA\u0011qTAR\u0003K\u000b9+\u0006\u0002\u0002\"*\u001aq-a!\u0005\u000by\u001a\"\u0019\u0001!\u0005\u000b\r\u001c\"\u0019\u0001!\u0005\u000b1\u001b\"\u0019\u0001!\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gUA\u0011QVAY\u0003g\u000b),\u0006\u0002\u00020*\u001a1/a!\u0005\u000by\"\"\u0019\u0001!\u0005\u000b\r$\"\u0019\u0001!\u0005\u000b1#\"\u0019\u0001!\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iUA\u00111XA`\u0003\u0003\f\u0019-\u0006\u0002\u0002>*\u001a\u00110a!\u0005\u000by*\"\u0019\u0001!\u0005\u000b\r,\"\u0019\u0001!\u0005\u000b1+\"\u0019\u0001!\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tI\r\u0005\u0003\u0002L\u0006UWBAAg\u0015\u0011\ty-!5\u0002\t1\fgn\u001a\u0006\u0003\u0003'\fAA[1wC&!\u0011q[Ag\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u0011Q\u001c\t\u0004\u0005\u0006}\u0017bAAq\u0007\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR\u0019q)a:\t\u0013\u0005%\b$!AA\u0002\u0005u\u0017a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002pB)\u0011\u0011_A|\u000f6\u0011\u00111\u001f\u0006\u0004\u0003k\u001c\u0015AC2pY2,7\r^5p]&!\u0011\u0011`Az\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u00075\fy\u0010\u0003\u0005\u0002jj\t\t\u00111\u0001H\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005%'Q\u0001\u0005\n\u0003S\\\u0012\u0011!a\u0001\u0003;\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003;\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u0013\fa!Z9vC2\u001cHcA7\u0003\u0014!A\u0011\u0011\u001e\u0010\u0002\u0002\u0003\u0007q\tK\u0002\u0001\u0005/\u0001BA!\u0007\u0003\u001e5\u0011!1\u0004\u0006\u0004\u0003\u001fk\u0013\u0002\u0002B\u0010\u00057\u00111\"\u00138uKJt\u0017\r\\!qS\u0006)\u0012iZ4sK\u001e\fG/Z,ji\"\u0014u.\u001e8eCJL\bcAA\u000bAM)\u0001Ea\n\u0003.A\u0019!I!\u000b\n\u0007\t-2I\u0001\u0004B]f\u0014VM\u001a\t\u0005\u0005_\u0011)$\u0004\u0002\u00032)!!1GAi\u0003\tIw.C\u0002[\u0005c!\"Aa\t\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0011\tu\"1\tB$\u0005\u0017\"\"Ba\u0010\u0003N\tE#q\u000bB.!%\t)\u0002\u0001B!\u0005\u000b\u0012I\u0005E\u0002=\u0005\u0007\"QAP\u0012C\u0002\u0001\u00032\u0001\u0010B$\t\u0015\u00197E1\u0001A!\ra$1\n\u0003\u0006\u0019\u000e\u0012\r\u0001\u0011\u0005\u00079\u000e\u0002\rAa\u0014\u0011\t\t{&Q\t\u0005\u0007K\u000e\u0002\rAa\u0015\u0011\u0011\tC'Q\tB!\u0005+\u0002RAQ6\u0003F5Da!]\u0012A\u0002\te\u0003C\u0002\"u\u0005\u000b\u0012I\u0005\u0003\u0004xG\u0001\u0007!Q\f\t\u0005\u0005j\u0014y\u0006E\u0003CW\n\u0005d\u0010E\u0003Ci\n\u0015S.A\u0004v]\u0006\u0004\b\u000f\\=\u0016\u0011\t\u001d$1\u0010B;\u0005\u0007#BA!\u001b\u0003\fB!!I\u001fB6!-\u0011%Q\u000eB9\u0005o\u0012yH!\"\n\u0007\t=4I\u0001\u0004UkBdW\r\u000e\t\u0005\u0005~\u0013\u0019\bE\u0002=\u0005k\"Qa\u0019\u0013C\u0002\u0001\u0003\u0002B\u00115\u0003t\te$Q\u0010\t\u0004y\tmD!\u0002 %\u0005\u0004\u0001\u0005#\u0002\"l\u0005gj\u0007C\u0002\"u\u0005g\u0012\t\tE\u0002=\u0005\u0007#Q\u0001\u0014\u0013C\u0002\u0001\u0003BA\u0011>\u0003\bB)!i\u001bBE}B)!\t\u001eB:[\"I!Q\u0012\u0013\u0002\u0002\u0003\u0007!qR\u0001\u0004q\u0012\u0002\u0004#CA\u000b\u0001\te$1\u000fBA\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011)\n\u0005\u0003\u0002L\n]\u0015\u0002\u0002BM\u0003\u001b\u0014aa\u00142kK\u000e$\b")
@InternalApi
public final class AggregateWithBoundary<In, Agg, Out>
extends GraphStage<FlowShape<In, Out>>
implements Product,
Serializable {
    private final Function0<Agg> allocate;
    private final Function2<Agg, In, Tuple2<Agg, Object>> aggregate;
    private final Function1<Agg, Out> harvest;
    private final Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer;
    private final Inlet<In> in;
    private final Outlet<Out> out;
    private final FlowShape<In, Out> shape;

    public static <In, Agg, Out> Option<Tuple4<Function0<Agg>, Function2<Agg, In, Tuple2<Agg, Object>>, Function1<Agg, Out>, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>>>> unapply(AggregateWithBoundary<In, Agg, Out> x$0) {
        return AggregateWithBoundary$.MODULE$.unapply(x$0);
    }

    public static <In, Agg, Out> AggregateWithBoundary<In, Agg, Out> apply(Function0<Agg> allocate, Function2<Agg, In, Tuple2<Agg, Object>> aggregate, Function1<Agg, Out> harvest, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer) {
        return AggregateWithBoundary$.MODULE$.apply(allocate, aggregate, harvest, emitOnTimer);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Function0<Agg> allocate() {
        return this.allocate;
    }

    public Function2<Agg, In, Tuple2<Agg, Object>> aggregate() {
        return this.aggregate;
    }

    public Function1<Agg, Out> harvest() {
        return this.harvest;
    }

    public Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer() {
        return this.emitOnTimer;
    }

    public Inlet<In> in() {
        return this.in;
    }

    public Outlet<Out> out() {
        return this.out;
    }

    @Override
    public FlowShape<In, Out> shape() {
        return this.shape;
    }

    @Override
    public GraphStageLogic createLogic(Attributes inheritedAttributes) {
        return new InHandler(this){
            private Agg aggregated;
            private final /* synthetic */ AggregateWithBoundary $outer;

            public void onDownstreamFinish() throws Exception {
                OutHandler.onDownstreamFinish$(this);
            }

            public void onDownstreamFinish(Throwable cause) throws Exception {
                OutHandler.onDownstreamFinish$(this, cause);
            }

            public void onUpstreamFailure(Throwable ex) throws Exception {
                InHandler.onUpstreamFailure$(this, ex);
            }

            public void preStart() {
                this.$outer.emitOnTimer().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$preStart$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
            }

            public void onTimer(Object timerKey) {
                this.$outer.emitOnTimer().foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
                    $anon$1.$anonfun$onTimer$1(this, x0$1);
                    return BoxedUnit.UNIT;
                });
            }

            public void onPush() {
                Tuple2<Agg, Object> tuple2;
                if (this.aggregated == null) {
                    this.aggregated = this.$outer.allocate().apply();
                }
                if ((tuple2 = this.$outer.aggregate().apply(this.aggregated, this.grab(this.$outer.in()))) == null) {
                    throw new MatchError(tuple2);
                }
                Agg updated = tuple2._1();
                boolean result = tuple2._2$mcZ$sp();
                Tuple2<Agg, Boolean> tuple22 = new Tuple2<Agg, Boolean>(updated, BoxesRunTime.boxToBoolean(result));
                Agg updated2 = tuple22._1();
                boolean result2 = tuple22._2$mcZ$sp();
                this.aggregated = updated2;
                if (result2) {
                    this.harvestAndEmit();
                }
                if (this.isAvailable(this.$outer.out())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            public void onUpstreamFinish() {
                if (this.aggregated != null) {
                    this.emit(this.$outer.out(), this.$outer.harvest().apply(this.aggregated));
                }
                this.completeStage();
            }

            public void onPull() {
                if (!this.hasBeenPulled(this.$outer.in())) {
                    this.pull(this.$outer.in());
                    return;
                }
            }

            private void harvestAndEmit() {
                this.emit(this.$outer.out(), this.$outer.harvest().apply(this.aggregated));
                this.aggregated = null;
            }

            public static final /* synthetic */ void $anonfun$preStart$1($anon$1 $this, Tuple2 x0$1) {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    FiniteDuration interval = (FiniteDuration)tuple2._2();
                    $this.scheduleWithFixedDelay((Object)new StringBuilder(5).append($this.getClass().getSimpleName()).append("Timer").toString(), interval, interval);
                    return;
                }
                throw new MatchError(tuple2);
            }

            public static final /* synthetic */ void $anonfun$onTimer$1($anon$1 $this, Tuple2 x0$1) {
                Tuple2 tuple2 = x0$1;
                if (tuple2 != null) {
                    Function1 isReadyOnTimer = (Function1)tuple2._1();
                    if ($this.aggregated != null && BoxesRunTime.unboxToBoolean(isReadyOnTimer.apply($this.aggregated))) {
                        $this.harvestAndEmit();
                        return;
                    }
                    return;
                }
                throw new MatchError(tuple2);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer.shape());
                InHandler.$init$(this);
                OutHandler.$init$(this);
                this.aggregated = null;
                this.setHandlers($outer.in(), $outer.out(), this);
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onTimer$1$adapted(akka.stream.impl.fusing.AggregateWithBoundary$$anon$1 scala.Tuple2 ), $anonfun$preStart$1$adapted(akka.stream.impl.fusing.AggregateWithBoundary$$anon$1 scala.Tuple2 )}, serializedLambda);
            }
        };
    }

    public <In, Agg, Out> AggregateWithBoundary<In, Agg, Out> copy(Function0<Agg> allocate, Function2<Agg, In, Tuple2<Agg, Object>> aggregate, Function1<Agg, Out> harvest, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer) {
        return new AggregateWithBoundary<In, Agg, Out>(allocate, aggregate, harvest, emitOnTimer);
    }

    public <In, Agg, Out> Function0<Agg> copy$default$1() {
        return this.allocate();
    }

    public <In, Agg, Out> Function2<Agg, In, Tuple2<Agg, Object>> copy$default$2() {
        return this.aggregate();
    }

    public <In, Agg, Out> Function1<Agg, Out> copy$default$3() {
        return this.harvest();
    }

    public <In, Agg, Out> Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> copy$default$4() {
        return this.emitOnTimer();
    }

    @Override
    public String productPrefix() {
        return "AggregateWithBoundary";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.allocate();
            }
            case 1: {
                return this.aggregate();
            }
            case 2: {
                return this.harvest();
            }
            case 3: {
                return this.emitOnTimer();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof AggregateWithBoundary;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "allocate";
            }
            case 1: {
                return "aggregate";
            }
            case 2: {
                return "harvest";
            }
            case 3: {
                return "emitOnTimer";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof AggregateWithBoundary)) return false;
        boolean bl = true;
        if (!bl) return false;
        AggregateWithBoundary aggregateWithBoundary = (AggregateWithBoundary)x$1;
        Function0<Agg> function0 = this.allocate();
        Function0<Agg> function02 = aggregateWithBoundary.allocate();
        if (function0 == null) {
            if (function02 != null) {
                return false;
            }
        } else if (!function0.equals(function02)) return false;
        Function2<Agg, In, Tuple2<Agg, Object>> function2 = this.aggregate();
        Function2<Agg, In, Tuple2<Agg, Object>> function22 = aggregateWithBoundary.aggregate();
        if (function2 == null) {
            if (function22 != null) {
                return false;
            }
        } else if (!function2.equals(function22)) return false;
        Function1<Agg, Out> function1 = this.harvest();
        Function1<Agg, Out> function12 = aggregateWithBoundary.harvest();
        if (function1 == null) {
            if (function12 != null) {
                return false;
            }
        } else if (!function1.equals(function12)) return false;
        Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> option = this.emitOnTimer();
        Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> option2 = aggregateWithBoundary.emitOnTimer();
        if (option == null) {
            if (option2 == null) return true;
            return false;
        } else {
            if (!option.equals(option2)) return false;
            return true;
        }
    }

    public static final /* synthetic */ void $anonfun$new$1(Tuple2 x0$1) {
        Tuple2 tuple2 = x0$1;
        if (tuple2 != null) {
            FiniteDuration interval = (FiniteDuration)tuple2._2();
            Predef$.MODULE$.require(interval.gteq(new package.DurationInt(package$.MODULE$.DurationInt(1)).milli()), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(36).append("timer(").append(interval.toCoarsest()).append(") must not be smaller than 1ms").toString());
            return;
        }
        throw new MatchError(tuple2);
    }

    public AggregateWithBoundary(Function0<Agg> allocate, Function2<Agg, In, Tuple2<Agg, Object>> aggregate, Function1<Agg, Out> harvest, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer) {
        this.allocate = allocate;
        this.aggregate = aggregate;
        this.harvest = harvest;
        this.emitOnTimer = emitOnTimer;
        Product.$init$(this);
        emitOnTimer.foreach((Function1<Tuple2, Object> & Serializable)x0$1 -> {
            AggregateWithBoundary.$anonfun$new$1(x0$1);
            return BoxedUnit.UNIT;
        });
        this.in = Inlet$.MODULE$.apply(new StringBuilder(3).append(this.getClass().getName()).append(".in").toString());
        this.out = Outlet$.MODULE$.apply(new StringBuilder(4).append(this.getClass().getName()).append(".out").toString());
        this.shape = new FlowShape<In, Out>(this.in(), this.out());
    }
}

