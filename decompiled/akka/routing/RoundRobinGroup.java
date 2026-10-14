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
import akka.routing.RoundRobinGroup$;
import akka.routing.RoundRobinRoutingLogic$;
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

@ScalaSignature(bytes="\u0006\u0005\u0005=g\u0001\u0002\u0011\"\u0005\u001aB\u0001\u0002\u0011\u0001\u0003\u0016\u0004%\t!\u0011\u0005\t%\u0002\u0011\t\u0012)A\u0005\u0005\"A1\u000b\u0001BK\u0002\u0013\u0005C\u000b\u0003\u0005V\u0001\tE\t\u0015!\u0003K\u0011\u00151\u0006\u0001\"\u0001X\u0011\u00151\u0006\u0001\"\u0001\\\u0011\u00151\u0006\u0001\"\u0001h\u0011\u0015\u0001\u0005\u0001\"\u0011r\u0011\u0015Q\b\u0001\"\u0011|\u0011\u001d\t\t\u0001\u0001C\u0001\u0003\u0007A\u0011\"!\u0003\u0001\u0003\u0003%\t!a\u0003\t\u0013\u0005E\u0001!%A\u0005\u0002\u0005M\u0001\"CA\u0015\u0001E\u0005I\u0011AA\u0016\u0011%\ty\u0003AA\u0001\n\u0003\n\t\u0004C\u0005\u00028\u0001\t\t\u0011\"\u0001\u0002:!I\u0011\u0011\t\u0001\u0002\u0002\u0013\u0005\u00111\t\u0005\n\u0003\u001f\u0002\u0011\u0011!C!\u0003#B\u0011\"a\u0017\u0001\u0003\u0003%\t!!\u0018\t\u0013\u0005\u001d\u0004!!A\u0005B\u0005%\u0004\"CA7\u0001\u0005\u0005I\u0011IA8\u0011%\t\t\bAA\u0001\n\u0003\n\u0019\bC\u0005\u0002v\u0001\t\t\u0011\"\u0011\u0002x\u001dI\u0011qQ\u0011\u0002\u0002#\u0005\u0011\u0011\u0012\u0004\tA\u0005\n\t\u0011#\u0001\u0002\f\"1a\u000b\u0007C\u0001\u0003GC\u0011\"!\u001d\u0019\u0003\u0003%)%a\u001d\t\u0013\u0005\u0015\u0006$!A\u0005\u0002\u0006\u001d\u0006\"CAW1E\u0005I\u0011AA\u0016\u0011%\ty\u000bGA\u0001\n\u0003\u000b\t\fC\u0005\u0002Db\t\n\u0011\"\u0001\u0002,!I\u0011Q\u0019\r\u0002\u0002\u0013%\u0011q\u0019\u0002\u0010%>,h\u000e\u001a*pE&twI]8va*\u0011!eI\u0001\be>,H/\u001b8h\u0015\u0005!\u0013\u0001B1lW\u0006\u001c\u0001aE\u0003\u0001O5\nD\u0007\u0005\u0002)W5\t\u0011FC\u0001+\u0003\u0015\u00198-\u00197b\u0013\ta\u0013F\u0001\u0004B]f\u0014VM\u001a\t\u0003]=j\u0011!I\u0005\u0003a\u0005\u0012Qa\u0012:pkB\u0004\"\u0001\u000b\u001a\n\u0005MJ#a\u0002)s_\u0012,8\r\u001e\t\u0003kur!AN\u001e\u000f\u0005]RT\"\u0001\u001d\u000b\u0005e*\u0013A\u0002\u001fs_>$h(C\u0001+\u0013\ta\u0014&A\u0004qC\u000e\\\u0017mZ3\n\u0005yz$\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u001f*\u0003\u0015\u0001\u0018\r\u001e5t+\u0005\u0011\u0005cA\"I\u00156\tAI\u0003\u0002F\r\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u000f&\n!bY8mY\u0016\u001cG/[8o\u0013\tIEI\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\tYuJ\u0004\u0002M\u001bB\u0011q'K\u0005\u0003\u001d&\na\u0001\u0015:fI\u00164\u0017B\u0001)R\u0005\u0019\u0019FO]5oO*\u0011a*K\u0001\u0007a\u0006$\bn\u001d\u0011\u0002!I|W\u000f^3s\t&\u001c\b/\u0019;dQ\u0016\u0014X#\u0001&\u0002#I|W\u000f^3s\t&\u001c\b/\u0019;dQ\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u00041fS\u0006C\u0001\u0018\u0001\u0011\u0015\u0001U\u00011\u0001C\u0011\u001d\u0019V\u0001%AA\u0002)#\"\u0001\u0017/\t\u000bu3\u0001\u0019\u00010\u0002\r\r|gNZ5h!\tyV-D\u0001a\u0015\ti\u0016M\u0003\u0002cG\u0006AA/\u001f9fg\u00064WMC\u0001e\u0003\r\u0019w.\\\u0005\u0003M\u0002\u0014aaQ8oM&<GC\u0001-i\u0011\u0015Iw\u00011\u0001k\u0003-\u0011x.\u001e;fKB\u000bG\u000f[:\u0011\u0007-\u0004(*D\u0001m\u0015\tig.\u0001\u0003mC:<'\"A8\u0002\t)\fg/Y\u0005\u0003\u00132$\"A\u0011:\t\u000bMD\u0001\u0019\u0001;\u0002\rML8\u000f^3n!\t)\b0D\u0001w\u0015\t98%A\u0003bGR|'/\u0003\u0002zm\nY\u0011i\u0019;peNK8\u000f^3n\u00031\u0019'/Z1uKJ{W\u000f^3s)\tax\u0010\u0005\u0002/{&\u0011a0\t\u0002\u0007%>,H/\u001a:\t\u000bML\u0001\u0019\u0001;\u0002\u001d]LG\u000f\u001b#jgB\fGo\u00195feR\u0019\u0001,!\u0002\t\r\u0005\u001d!\u00021\u0001K\u00031!\u0017n\u001d9bi\u000eDWM]%e\u0003\u0011\u0019w\u000e]=\u0015\u000ba\u000bi!a\u0004\t\u000f\u0001[\u0001\u0013!a\u0001\u0005\"91k\u0003I\u0001\u0002\u0004Q\u0015AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003+Q3AQA\fW\t\tI\u0002\u0005\u0003\u0002\u001c\u0005\u0015RBAA\u000f\u0015\u0011\ty\"!\t\u0002\u0013Ut7\r[3dW\u0016$'bAA\u0012S\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\u001d\u0012Q\u0004\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003[Q3ASA\f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u0007\t\u0004W\u0006U\u0012B\u0001)m\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\tY\u0004E\u0002)\u0003{I1!a\u0010*\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\t)%a\u0013\u0011\u0007!\n9%C\u0002\u0002J%\u00121!\u00118z\u0011%\ti\u0005EA\u0001\u0002\u0004\tY$A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003'\u0002b!!\u0016\u0002X\u0005\u0015S\"\u0001$\n\u0007\u0005ecI\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003BA0\u0003K\u00022\u0001KA1\u0013\r\t\u0019'\u000b\u0002\b\u0005>|G.Z1o\u0011%\tiEEA\u0001\u0002\u0004\t)%\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA\u001a\u0003WB\u0011\"!\u0014\u0014\u0003\u0003\u0005\r!a\u000f\u0002\u0011!\f7\u000f[\"pI\u0016$\"!a\u000f\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\r\u0002\r\u0015\fX/\u00197t)\u0011\ty&!\u001f\t\u0013\u00055c#!AA\u0002\u0005\u0015\u0003f\u0002\u0001\u0002~\u0005\r\u0015Q\u0011\t\u0004Q\u0005}\u0014bAAAS\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005y!k\\;oIJ{'-\u001b8He>,\b\u000f\u0005\u0002/1M)\u0001$!$\u0002\u001aB9\u0011qRAK\u0005*CVBAAI\u0015\r\t\u0019*K\u0001\beVtG/[7f\u0013\u0011\t9*!%\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t'\u0007\u0005\u0003\u0002\u001c\u0006\u0005VBAAO\u0015\r\tyJ\\\u0001\u0003S>L1APAO)\t\tI)A\u0003baBd\u0017\u0010F\u0003Y\u0003S\u000bY\u000bC\u0003A7\u0001\u0007!\tC\u0004T7A\u0005\t\u0019\u0001&\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u00024\u0006}\u0006#\u0002\u0015\u00026\u0006e\u0016bAA\\S\t1q\n\u001d;j_:\u0004R\u0001KA^\u0005*K1!!0*\u0005\u0019!V\u000f\u001d7fe!A\u0011\u0011Y\u000f\u0002\u0002\u0003\u0007\u0001,A\u0002yIA\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAAe!\rY\u00171Z\u0005\u0004\u0003\u001bd'AB(cU\u0016\u001cG\u000f")
public final class RoundRobinGroup
implements Group,
Product {
    private static final long serialVersionUID = 1L;
    private final Iterable<String> paths;
    private final String routerDispatcher;

    public static String $lessinit$greater$default$2() {
        return RoundRobinGroup$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple2<Iterable<String>, String>> unapply(RoundRobinGroup x$0) {
        return RoundRobinGroup$.MODULE$.unapply(x$0);
    }

    public static String apply$default$2() {
        return RoundRobinGroup$.MODULE$.apply$default$2();
    }

    public static RoundRobinGroup apply(Iterable<String> paths, String routerDispatcher) {
        return RoundRobinGroup$.MODULE$.apply(paths, routerDispatcher);
    }

    public static Function1<Tuple2<Iterable<String>, String>, RoundRobinGroup> tupled() {
        return RoundRobinGroup$.MODULE$.tupled();
    }

    public static Function1<Iterable<String>, Function1<String, RoundRobinGroup>> curried() {
        return RoundRobinGroup$.MODULE$.curried();
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
        return new Router(RoundRobinRoutingLogic$.MODULE$.apply());
    }

    public RoundRobinGroup withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        Iterable<String> x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    public RoundRobinGroup copy(Iterable<String> paths, String routerDispatcher) {
        return new RoundRobinGroup(paths, routerDispatcher);
    }

    public Iterable<String> copy$default$1() {
        return this.paths();
    }

    public String copy$default$2() {
        return this.routerDispatcher();
    }

    @Override
    public String productPrefix() {
        return "RoundRobinGroup";
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
        return x$1 instanceof RoundRobinGroup;
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
        if (!(object instanceof RoundRobinGroup)) return false;
        boolean bl = true;
        if (!bl) return false;
        RoundRobinGroup roundRobinGroup = (RoundRobinGroup)x$1;
        Iterable<String> iterable = this.paths();
        Iterable<String> iterable2 = roundRobinGroup.paths();
        if (iterable == null) {
            if (iterable2 != null) {
                return false;
            }
        } else if (!iterable.equals(iterable2)) return false;
        String string = this.routerDispatcher();
        String string2 = roundRobinGroup.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public RoundRobinGroup(Iterable<String> paths, String routerDispatcher) {
        this.paths = paths;
        this.routerDispatcher = routerDispatcher;
        RouterConfig.$init$(this);
        Group.$init$(this);
        Product.$init$(this);
    }

    public RoundRobinGroup(Config config) {
        this(Util$.MODULE$.immutableSeq(config.getStringList("routees.paths")), RoundRobinGroup$.MODULE$.$lessinit$greater$default$2());
    }

    public RoundRobinGroup(java.lang.Iterable<String> routeePaths) {
        this(Util$.MODULE$.immutableSeq(routeePaths), RoundRobinGroup$.MODULE$.$lessinit$greater$default$2());
    }
}

