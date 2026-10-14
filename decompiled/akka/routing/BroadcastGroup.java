/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Util$;
import akka.routing.BroadcastGroup$;
import akka.routing.BroadcastRoutingLogic$;
import akka.routing.Group;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import com.typesafe.config.Config;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=g\u0001\u0002\u0011\"\u0005\u001aB\u0001\u0002\u0011\u0001\u0003\u0016\u0004%\t!\u0011\u0005\t%\u0002\u0011\t\u0012)A\u0005\u0005\"A1\u000b\u0001BK\u0002\u0013\u0005C\u000b\u0003\u0005V\u0001\tE\t\u0015!\u0003K\u0011\u00151\u0006\u0001\"\u0001X\u0011\u00151\u0006\u0001\"\u0001\\\u0011\u00151\u0006\u0001\"\u0001h\u0011\u0015\u0001\u0005\u0001\"\u0011r\u0011\u0015Q\b\u0001\"\u0011|\u0011\u001d\t\t\u0001\u0001C\u0001\u0003\u0007A\u0011\"!\u0003\u0001\u0003\u0003%\t!a\u0003\t\u0013\u0005E\u0001!%A\u0005\u0002\u0005M\u0001\"CA\u0015\u0001E\u0005I\u0011AA\u0016\u0011%\ty\u0003AA\u0001\n\u0003\n\t\u0004C\u0005\u00028\u0001\t\t\u0011\"\u0001\u0002:!I\u0011\u0011\t\u0001\u0002\u0002\u0013\u0005\u00111\t\u0005\n\u0003\u001f\u0002\u0011\u0011!C!\u0003#B\u0011\"a\u0017\u0001\u0003\u0003%\t!!\u0018\t\u0013\u0005\u001d\u0004!!A\u0005B\u0005%\u0004\"CA7\u0001\u0005\u0005I\u0011IA8\u0011%\t\t\bAA\u0001\n\u0003\n\u0019\bC\u0005\u0002v\u0001\t\t\u0011\"\u0011\u0002x\u001dI\u0011qQ\u0011\u0002\u0002#\u0005\u0011\u0011\u0012\u0004\tA\u0005\n\t\u0011#\u0001\u0002\f\"1a\u000b\u0007C\u0001\u0003GC\u0011\"!\u001d\u0019\u0003\u0003%)%a\u001d\t\u0013\u0005\u0015\u0006$!A\u0005\u0002\u0006\u001d\u0006\"CAW1E\u0005I\u0011AA\u0016\u0011%\ty\u000bGA\u0001\n\u0003\u000b\t\fC\u0005\u0002Db\t\n\u0011\"\u0001\u0002,!I\u0011Q\u0019\r\u0002\u0002\u0013%\u0011q\u0019\u0002\u000f\u0005J|\u0017\rZ2bgR<%o\\;q\u0015\t\u00113%A\u0004s_V$\u0018N\\4\u000b\u0003\u0011\nA!Y6lC\u000e\u00011#\u0002\u0001([E\"\u0004C\u0001\u0015,\u001b\u0005I#\"\u0001\u0016\u0002\u000bM\u001c\u0017\r\\1\n\u00051J#AB!osJ+g\r\u0005\u0002/_5\t\u0011%\u0003\u00021C\t)qI]8vaB\u0011\u0001FM\u0005\u0003g%\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u00026{9\u0011ag\u000f\b\u0003oij\u0011\u0001\u000f\u0006\u0003s\u0015\na\u0001\u0010:p_Rt\u0014\"\u0001\u0016\n\u0005qJ\u0013a\u00029bG.\fw-Z\u0005\u0003}}\u0012AbU3sS\u0006d\u0017N_1cY\u0016T!\u0001P\u0015\u0002\u000bA\fG\u000f[:\u0016\u0003\t\u00032a\u0011%K\u001b\u0005!%BA#G\u0003%IW.\\;uC\ndWM\u0003\u0002HS\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005%#%\u0001C%uKJ\f'\r\\3\u0011\u0005-{eB\u0001'N!\t9\u0014&\u0003\u0002OS\u00051\u0001K]3eK\u001aL!\u0001U)\u0003\rM#(/\u001b8h\u0015\tq\u0015&\u0001\u0004qCRD7\u000fI\u0001\u0011e>,H/\u001a:ESN\u0004\u0018\r^2iKJ,\u0012AS\u0001\u0012e>,H/\u001a:ESN\u0004\u0018\r^2iKJ\u0004\u0013A\u0002\u001fj]&$h\bF\u0002Y3j\u0003\"A\f\u0001\t\u000b\u0001+\u0001\u0019\u0001\"\t\u000fM+\u0001\u0013!a\u0001\u0015R\u0011\u0001\f\u0018\u0005\u0006;\u001a\u0001\rAX\u0001\u0007G>tg-[4\u0011\u0005}+W\"\u00011\u000b\u0005u\u000b'B\u00012d\u0003!!\u0018\u0010]3tC\u001a,'\"\u00013\u0002\u0007\r|W.\u0003\u0002gA\n11i\u001c8gS\u001e$\"\u0001\u00175\t\u000b%<\u0001\u0019\u00016\u0002\u0017I|W\u000f^3f!\u0006$\bn\u001d\t\u0004WBTU\"\u00017\u000b\u00055t\u0017\u0001\u00027b]\u001eT\u0011a\\\u0001\u0005U\u00064\u0018-\u0003\u0002JYR\u0011!I\u001d\u0005\u0006g\"\u0001\r\u0001^\u0001\u0007gf\u001cH/Z7\u0011\u0005UDX\"\u0001<\u000b\u0005]\u001c\u0013!B1di>\u0014\u0018BA=w\u0005-\t5\r^8s'f\u001cH/Z7\u0002\u0019\r\u0014X-\u0019;f%>,H/\u001a:\u0015\u0005q|\bC\u0001\u0018~\u0013\tq\u0018E\u0001\u0004S_V$XM\u001d\u0005\u0006g&\u0001\r\u0001^\u0001\u000fo&$\b\u000eR5ta\u0006$8\r[3s)\rA\u0016Q\u0001\u0005\u0007\u0003\u000fQ\u0001\u0019\u0001&\u0002\u0019\u0011L7\u000f]1uG\",'/\u00133\u0002\t\r|\u0007/\u001f\u000b\u00061\u00065\u0011q\u0002\u0005\b\u0001.\u0001\n\u00111\u0001C\u0011\u001d\u00196\u0002%AA\u0002)\u000babY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002\u0016)\u001a!)a\u0006,\u0005\u0005e\u0001\u0003BA\u000e\u0003Ki!!!\b\u000b\t\u0005}\u0011\u0011E\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\t*\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003O\tiBA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002.)\u001a!*a\u0006\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\t\u0019\u0004E\u0002l\u0003kI!\u0001\u00157\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005m\u0002c\u0001\u0015\u0002>%\u0019\u0011qH\u0015\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005\u0015\u00131\n\t\u0004Q\u0005\u001d\u0013bAA%S\t\u0019\u0011I\\=\t\u0013\u00055\u0003#!AA\u0002\u0005m\u0012a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002TA1\u0011QKA,\u0003\u000bj\u0011AR\u0005\u0004\u000332%\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a\u0018\u0002fA\u0019\u0001&!\u0019\n\u0007\u0005\r\u0014FA\u0004C_>dW-\u00198\t\u0013\u00055##!AA\u0002\u0005\u0015\u0013A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!a\r\u0002l!I\u0011QJ\n\u0002\u0002\u0003\u0007\u00111H\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111H\u0001\ti>\u001cFO]5oOR\u0011\u00111G\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005}\u0013\u0011\u0010\u0005\n\u0003\u001b2\u0012\u0011!a\u0001\u0003\u000bBs\u0001AA?\u0003\u0007\u000b)\tE\u0002)\u0003\u007fJ1!!!*\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u00039\u0011%o\\1eG\u0006\u001cHo\u0012:pkB\u0004\"A\f\r\u0014\u000ba\ti)!'\u0011\u000f\u0005=\u0015Q\u0013\"K16\u0011\u0011\u0011\u0013\u0006\u0004\u0003'K\u0013a\u0002:v]RLW.Z\u0005\u0005\u0003/\u000b\tJA\tBEN$(/Y2u\rVt7\r^5p]J\u0002B!a'\u0002\"6\u0011\u0011Q\u0014\u0006\u0004\u0003?s\u0017AA5p\u0013\rq\u0014Q\u0014\u000b\u0003\u0003\u0013\u000bQ!\u00199qYf$R\u0001WAU\u0003WCQ\u0001Q\u000eA\u0002\tCqaU\u000e\u0011\u0002\u0003\u0007!*A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133\u0003\u001d)h.\u00199qYf$B!a-\u0002@B)\u0001&!.\u0002:&\u0019\u0011qW\u0015\u0003\r=\u0003H/[8o!\u0015A\u00131\u0018\"K\u0013\r\ti,\u000b\u0002\u0007)V\u0004H.\u001a\u001a\t\u0011\u0005\u0005W$!AA\u0002a\u000b1\u0001\u001f\u00131\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%e\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011\u0011\u001a\t\u0004W\u0006-\u0017bAAgY\n1qJ\u00196fGR\u0004")
public final class BroadcastGroup
implements Group,
Product {
    private static final long serialVersionUID = 1L;
    private final Iterable<String> paths;
    private final String routerDispatcher;

    public static String $lessinit$greater$default$2() {
        return BroadcastGroup$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple2<Iterable<String>, String>> unapply(BroadcastGroup x$0) {
        return BroadcastGroup$.MODULE$.unapply(x$0);
    }

    public static String apply$default$2() {
        return BroadcastGroup$.MODULE$.apply$default$2();
    }

    public static BroadcastGroup apply(Iterable<String> paths, String routerDispatcher) {
        return BroadcastGroup$.MODULE$.apply(paths, routerDispatcher);
    }

    public static Function1<Tuple2<Iterable<String>, String>, BroadcastGroup> tupled() {
        return BroadcastGroup$.MODULE$.tupled();
    }

    public static Function1<Iterable<String>, Function1<String, BroadcastGroup>> curried() {
        return BroadcastGroup$.MODULE$.curried();
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

    @Override
    public String routerDispatcher() {
        return this.routerDispatcher;
    }

    @Override
    public Iterable<String> paths(ActorSystem system) {
        return this.paths();
    }

    @Override
    public Router createRouter(ActorSystem system) {
        return new Router(BroadcastRoutingLogic$.MODULE$.apply());
    }

    public BroadcastGroup withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        Iterable<String> x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    public BroadcastGroup copy(Iterable<String> paths, String routerDispatcher) {
        return new BroadcastGroup(paths, routerDispatcher);
    }

    public Iterable<String> copy$default$1() {
        return this.paths();
    }

    public String copy$default$2() {
        return this.routerDispatcher();
    }

    @Override
    public String productPrefix() {
        return "BroadcastGroup";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.paths();
            }
            case 1: {
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
        return x$1 instanceof BroadcastGroup;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "paths";
            }
            case 1: {
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
        if (!(object instanceof BroadcastGroup)) return false;
        boolean bl = true;
        if (!bl) return false;
        BroadcastGroup broadcastGroup = (BroadcastGroup)x$1;
        Iterable<String> iterable = this.paths();
        Iterable<String> iterable2 = broadcastGroup.paths();
        if (iterable == null) {
            if (iterable2 != null) {
                return false;
            }
        } else if (!iterable.equals(iterable2)) return false;
        String string = this.routerDispatcher();
        String string2 = broadcastGroup.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public BroadcastGroup(Iterable<String> paths, String routerDispatcher) {
        this.paths = paths;
        this.routerDispatcher = routerDispatcher;
        RouterConfig.$init$(this);
        Group.$init$(this);
        Product.$init$(this);
    }

    public BroadcastGroup(Config config) {
        this(Util$.MODULE$.immutableSeq(config.getStringList("routees.paths")), BroadcastGroup$.MODULE$.$lessinit$greater$default$2());
    }

    public BroadcastGroup(java.lang.Iterable<String> routeePaths) {
        this(Util$.MODULE$.immutableSeq(routeePaths), BroadcastGroup$.MODULE$.$lessinit$greater$default$2());
    }
}

