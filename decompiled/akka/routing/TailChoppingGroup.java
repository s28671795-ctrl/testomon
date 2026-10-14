/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Util$;
import akka.routing.Group;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import akka.routing.TailChoppingGroup$;
import akka.routing.TailChoppingRoutingLogic;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import com.typesafe.config.Config;
import java.time.Duration;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t]a\u0001B\u0014)\u00056B\u0001b\u0012\u0001\u0003\u0016\u0004%\t\u0001\u0013\u0005\t3\u0002\u0011\t\u0012)A\u0005\u0013\"A!\f\u0001BK\u0002\u0013\u00051\f\u0003\u0005e\u0001\tE\t\u0015!\u0003]\u0011!)\u0007A!f\u0001\n\u0003Y\u0006\u0002\u00034\u0001\u0005#\u0005\u000b\u0011\u0002/\t\u0011\u001d\u0004!Q3A\u0005B!D\u0001\"\u001b\u0001\u0003\u0012\u0003\u0006I!\u0015\u0005\u0006U\u0002!\ta\u001b\u0005\u0006U\u0002!\t!\u001d\u0005\u0006U\u0002!\t! \u0005\u0007U\u0002!\t!a\u0005\t\u000f\u0005\u001d\u0002\u0001\"\u0011\u0002*!1q\t\u0001C!\u0003\u0003Bq!!\u0012\u0001\t\u0003\t9\u0005C\u0005\u0002N\u0001\t\t\u0011\"\u0001\u0002P!I\u0011\u0011\f\u0001\u0012\u0002\u0013\u0005\u00111\f\u0005\n\u0003c\u0002\u0011\u0013!C\u0001\u0003gB\u0011\"a\u001e\u0001#\u0003%\t!a\u001d\t\u0013\u0005e\u0004!%A\u0005\u0002\u0005m\u0004\"CA@\u0001\u0005\u0005I\u0011IAA\u0011%\t9\tAA\u0001\n\u0003\tI\tC\u0005\u0002\u0012\u0002\t\t\u0011\"\u0001\u0002\u0014\"I\u0011q\u0014\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0015\u0005\n\u0003W\u0003\u0011\u0011!C\u0001\u0003[C\u0011\"a.\u0001\u0003\u0003%\t%!/\t\u0013\u0005u\u0006!!A\u0005B\u0005}\u0006\"CAa\u0001\u0005\u0005I\u0011IAb\u0011%\t)\rAA\u0001\n\u0003\n9mB\u0005\u0002L\"\n\t\u0011#\u0001\u0002N\u001aAq\u0005KA\u0001\u0012\u0003\ty\r\u0003\u0004k?\u0011\u0005\u0011q\u001d\u0005\n\u0003\u0003|\u0012\u0011!C#\u0003\u0007D\u0011\"!; \u0003\u0003%\t)a;\t\u0013\u0005Ux$%A\u0005\u0002\u0005m\u0004\"CA|?\u0005\u0005I\u0011QA}\u0011%\u0011YaHI\u0001\n\u0003\tY\bC\u0005\u0003\u000e}\t\t\u0011\"\u0003\u0003\u0010\t\tB+Y5m\u0007\"|\u0007\u000f]5oO\u001e\u0013x.\u001e9\u000b\u0005%R\u0013a\u0002:pkRLgn\u001a\u0006\u0002W\u0005!\u0011m[6b\u0007\u0001\u0019R\u0001\u0001\u00185qm\u0002\"a\f\u001a\u000e\u0003AR\u0011!M\u0001\u0006g\u000e\fG.Y\u0005\u0003gA\u0012a!\u00118z%\u00164\u0007CA\u001b7\u001b\u0005A\u0013BA\u001c)\u0005\u00159%o\\;q!\ty\u0013(\u0003\u0002;a\t9\u0001K]8ek\u000e$\bC\u0001\u001fE\u001d\ti$I\u0004\u0002?\u00036\tqH\u0003\u0002AY\u00051AH]8pizJ\u0011!M\u0005\u0003\u0007B\nq\u0001]1dW\u0006<W-\u0003\u0002F\r\na1+\u001a:jC2L'0\u00192mK*\u00111\tM\u0001\u0006a\u0006$\bn]\u000b\u0002\u0013B\u0019!jT)\u000e\u0003-S!\u0001T'\u0002\u0013%lW.\u001e;bE2,'B\u0001(1\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003!.\u0013\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0003%Zs!a\u0015+\u0011\u0005y\u0002\u0014BA+1\u0003\u0019\u0001&/\u001a3fM&\u0011q\u000b\u0017\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005U\u0003\u0014A\u00029bi\"\u001c\b%\u0001\u0004xSRD\u0017N\\\u000b\u00029B\u0011QLY\u0007\u0002=*\u0011q\fY\u0001\tIV\u0014\u0018\r^5p]*\u0011\u0011\rM\u0001\u000bG>t7-\u001e:sK:$\u0018BA2_\u000591\u0015N\\5uK\u0012+(/\u0019;j_:\fqa^5uQ&t\u0007%\u0001\u0005j]R,'O^1m\u0003%Ig\u000e^3sm\u0006d\u0007%\u0001\ts_V$XM\u001d#jgB\fGo\u00195feV\t\u0011+A\ts_V$XM\u001d#jgB\fGo\u00195fe\u0002\na\u0001P5oSRtD#\u00027n]>\u0004\bCA\u001b\u0001\u0011\u00159\u0015\u00021\u0001J\u0011\u0015Q\u0016\u00021\u0001]\u0011\u0015)\u0017\u00021\u0001]\u0011\u001d9\u0017\u0002%AA\u0002E#\"\u0001\u001c:\t\u000bMT\u0001\u0019\u0001;\u0002\r\r|gNZ5h!\t)80D\u0001w\u0015\t\u0019xO\u0003\u0002ys\u0006AA/\u001f9fg\u00064WMC\u0001{\u0003\r\u0019w.\\\u0005\u0003yZ\u0014aaQ8oM&<GC\u00027\u007f\u0003\u001f\t\t\u0002\u0003\u0004\u0000\u0017\u0001\u0007\u0011\u0011A\u0001\fe>,H/Z3QCRD7\u000fE\u0003\u0002\u0004\u00055\u0011+\u0004\u0002\u0002\u0006)!\u0011qAA\u0005\u0003\u0011a\u0017M\\4\u000b\u0005\u0005-\u0011\u0001\u00026bm\u0006L1\u0001UA\u0003\u0011\u0015Q6\u00021\u0001]\u0011\u0015)7\u00021\u0001])\u001da\u0017QCA\f\u0003KAaa \u0007A\u0002\u0005\u0005\u0001B\u0002.\r\u0001\u0004\tI\u0002\u0005\u0003\u0002\u001c\u0005\u0005RBAA\u000f\u0015\u0011\ty\"!\u0003\u0002\tQLW.Z\u0005\u0005\u0003G\tiB\u0001\u0005EkJ\fG/[8o\u0011\u0019)G\u00021\u0001\u0002\u001a\u0005a1M]3bi\u0016\u0014v.\u001e;feR!\u00111FA\u0019!\r)\u0014QF\u0005\u0004\u0003_A#A\u0002*pkR,'\u000fC\u0004\u000245\u0001\r!!\u000e\u0002\rML8\u000f^3n!\u0011\t9$!\u0010\u000e\u0005\u0005e\"bAA\u001eU\u0005)\u0011m\u0019;pe&!\u0011qHA\u001d\u0005-\t5\r^8s'f\u001cH/Z7\u0015\u0007%\u000b\u0019\u0005C\u0004\u000249\u0001\r!!\u000e\u0002\u001d]LG\u000f\u001b#jgB\fGo\u00195feR\u0019A.!\u0013\t\r\u0005-s\u00021\u0001R\u00031!\u0017n\u001d9bi\u000eDWM]%e\u0003\u0011\u0019w\u000e]=\u0015\u00131\f\t&a\u0015\u0002V\u0005]\u0003bB$\u0011!\u0003\u0005\r!\u0013\u0005\b5B\u0001\n\u00111\u0001]\u0011\u001d)\u0007\u0003%AA\u0002qCqa\u001a\t\u0011\u0002\u0003\u0007\u0011+\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005u#fA%\u0002`-\u0012\u0011\u0011\r\t\u0005\u0003G\ni'\u0004\u0002\u0002f)!\u0011qMA5\u0003%)hn\u00195fG.,GMC\u0002\u0002lA\n!\"\u00198o_R\fG/[8o\u0013\u0011\ty'!\u001a\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005U$f\u0001/\u0002`\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001a\u0014AD2paf$C-\u001a4bk2$H\u0005N\u000b\u0003\u0003{R3!UA0\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u0011\t\u0005\u0003\u0007\t))C\u0002X\u0003\u000b\tA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a#\u0011\u0007=\ni)C\u0002\u0002\u0010B\u00121!\u00138u\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!!&\u0002\u001cB\u0019q&a&\n\u0007\u0005e\u0005GA\u0002B]fD\u0011\"!(\u0018\u0003\u0003\u0005\r!a#\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\t\u0019\u000b\u0005\u0004\u0002&\u0006\u001d\u0016QS\u0007\u0002\u001b&\u0019\u0011\u0011V'\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003_\u000b)\fE\u00020\u0003cK1!a-1\u0005\u001d\u0011un\u001c7fC:D\u0011\"!(\u001a\u0003\u0003\u0005\r!!&\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u0007\u000bY\fC\u0005\u0002\u001ej\t\t\u00111\u0001\u0002\f\u0006A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\f\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0004\u00061Q-];bYN$B!a,\u0002J\"I\u0011QT\u000f\u0002\u0002\u0003\u0007\u0011QS\u0001\u0012)\u0006LGn\u00115paBLgnZ$s_V\u0004\bCA\u001b '\u0015y\u0012\u0011[Ao!%\t\u0019.!7J9r\u000bF.\u0004\u0002\u0002V*\u0019\u0011q\u001b\u0019\u0002\u000fI,h\u000e^5nK&!\u00111\\Ak\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g\u000e\u000e\t\u0005\u0003?\f)/\u0004\u0002\u0002b*!\u00111]A\u0005\u0003\tIw.C\u0002F\u0003C$\"!!4\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u00131\fi/a<\u0002r\u0006M\b\"B$#\u0001\u0004I\u0005\"\u0002.#\u0001\u0004a\u0006\"B3#\u0001\u0004a\u0006bB4#!\u0003\u0005\r!U\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%i\u00059QO\\1qa2LH\u0003BA~\u0005\u000f\u0001RaLA\u007f\u0005\u0003I1!a@1\u0005\u0019y\u0005\u000f^5p]B9qFa\u0001J9r\u000b\u0016b\u0001B\u0003a\t1A+\u001e9mKRB\u0001B!\u0003%\u0003\u0003\u0005\r\u0001\\\u0001\u0004q\u0012\u0002\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$C'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003\u0012A!\u00111\u0001B\n\u0013\u0011\u0011)\"!\u0002\u0003\r=\u0013'.Z2u\u0001")
public final class TailChoppingGroup
implements Group,
Product {
    private final Iterable<String> paths;
    private final FiniteDuration within;
    private final FiniteDuration interval;
    private final String routerDispatcher;

    public static String $lessinit$greater$default$4() {
        return TailChoppingGroup$.MODULE$.$lessinit$greater$default$4();
    }

    public static Option<Tuple4<Iterable<String>, FiniteDuration, FiniteDuration, String>> unapply(TailChoppingGroup x$0) {
        return TailChoppingGroup$.MODULE$.unapply(x$0);
    }

    public static String apply$default$4() {
        return TailChoppingGroup$.MODULE$.apply$default$4();
    }

    public static TailChoppingGroup apply(Iterable<String> paths, FiniteDuration within, FiniteDuration interval, String routerDispatcher) {
        return TailChoppingGroup$.MODULE$.apply(paths, within, interval, routerDispatcher);
    }

    public static Function1<Tuple4<Iterable<String>, FiniteDuration, FiniteDuration, String>, TailChoppingGroup> tupled() {
        return TailChoppingGroup$.MODULE$.tupled();
    }

    public static Function1<Iterable<String>, Function1<FiniteDuration, Function1<FiniteDuration, Function1<String, TailChoppingGroup>>>> curried() {
        return TailChoppingGroup$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public Props props() {
        return Group.props$(this);
    }

    @Override
    public Routee routeeFor(String path, ActorContext context) {
        return Group.routeeFor$(this, path, context);
    }

    @Override
    public RouterActor createRouterActor() {
        return Group.createRouterActor$(this);
    }

    @Override
    public Option<Props> routingLogicController(RoutingLogic routingLogic) {
        return RouterConfig.routingLogicController$(this, routingLogic);
    }

    @Override
    public boolean isManagementMessage(Object msg) {
        return RouterConfig.isManagementMessage$(this, msg);
    }

    @Override
    public boolean stopRouterWhenAllRouteesRemoved() {
        return RouterConfig.stopRouterWhenAllRouteesRemoved$(this);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return RouterConfig.withFallback$(this, other);
    }

    @Override
    public void verifyConfig(ActorPath path) {
        RouterConfig.verifyConfig$(this, path);
    }

    public Iterable<String> paths() {
        return this.paths;
    }

    public FiniteDuration within() {
        return this.within;
    }

    public FiniteDuration interval() {
        return this.interval;
    }

    @Override
    public String routerDispatcher() {
        return this.routerDispatcher;
    }

    @Override
    public Router createRouter(ActorSystem system) {
        return new Router(new TailChoppingRoutingLogic(system.scheduler(), this.within(), this.interval(), system.dispatchers().lookup(this.routerDispatcher())));
    }

    @Override
    public Iterable<String> paths(ActorSystem system) {
        return this.paths();
    }

    public TailChoppingGroup withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        Iterable<String> x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        return this.copy(x$2, x$3, x$4, x$1);
    }

    public TailChoppingGroup copy(Iterable<String> paths, FiniteDuration within, FiniteDuration interval, String routerDispatcher) {
        return new TailChoppingGroup(paths, within, interval, routerDispatcher);
    }

    public Iterable<String> copy$default$1() {
        return this.paths();
    }

    public FiniteDuration copy$default$2() {
        return this.within();
    }

    public FiniteDuration copy$default$3() {
        return this.interval();
    }

    public String copy$default$4() {
        return this.routerDispatcher();
    }

    @Override
    public String productPrefix() {
        return "TailChoppingGroup";
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
                return this.paths();
            }
            case 1: {
                return this.within();
            }
            case 2: {
                return this.interval();
            }
            case 3: {
                return this.routerDispatcher();
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
        return x$1 instanceof TailChoppingGroup;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "paths";
            }
            case 1: {
                return "within";
            }
            case 2: {
                return "interval";
            }
            case 3: {
                return "routerDispatcher";
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
        if (!(object instanceof TailChoppingGroup)) return false;
        boolean bl = true;
        if (!bl) return false;
        TailChoppingGroup tailChoppingGroup = (TailChoppingGroup)x$1;
        Iterable<String> iterable = this.paths();
        Iterable<String> iterable2 = tailChoppingGroup.paths();
        if (iterable == null) {
            if (iterable2 != null) {
                return false;
            }
        } else if (!iterable.equals(iterable2)) return false;
        FiniteDuration finiteDuration = this.within();
        FiniteDuration finiteDuration2 = tailChoppingGroup.within();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        FiniteDuration finiteDuration3 = this.interval();
        FiniteDuration finiteDuration4 = tailChoppingGroup.interval();
        if (finiteDuration3 == null) {
            if (finiteDuration4 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration3).equals(finiteDuration4)) return false;
        String string = this.routerDispatcher();
        String string2 = tailChoppingGroup.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public TailChoppingGroup(Iterable<String> paths, FiniteDuration within, FiniteDuration interval, String routerDispatcher) {
        this.paths = paths;
        this.within = within;
        this.interval = interval;
        this.routerDispatcher = routerDispatcher;
        RouterConfig.$init$(this);
        Group.$init$(this);
        Product.$init$(this);
    }

    public TailChoppingGroup(Config config) {
        this(Util$.MODULE$.immutableSeq(config.getStringList("routees.paths")), Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "within"), Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "tail-chopping-router.interval"), TailChoppingGroup$.MODULE$.$lessinit$greater$default$4());
    }

    public TailChoppingGroup(java.lang.Iterable<String> routeePaths, FiniteDuration within, FiniteDuration interval) {
        this(Util$.MODULE$.immutableSeq(routeePaths), within, interval, TailChoppingGroup$.MODULE$.$lessinit$greater$default$4());
    }

    public TailChoppingGroup(java.lang.Iterable<String> routeePaths, Duration within, Duration interval) {
        this(Util$.MODULE$.immutableSeq(routeePaths), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(within)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)), TailChoppingGroup$.MODULE$.$lessinit$greater$default$4());
    }
}

