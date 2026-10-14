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
import akka.routing.RandomGroup$;
import akka.routing.RandomRoutingLogic$;
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

@ScalaSignature(bytes="\u0006\u0005\u0005=g\u0001\u0002\u0011\"\u0005\u001aB\u0001\u0002\u0011\u0001\u0003\u0016\u0004%\t!\u0011\u0005\t%\u0002\u0011\t\u0012)A\u0005\u0005\"A1\u000b\u0001BK\u0002\u0013\u0005C\u000b\u0003\u0005V\u0001\tE\t\u0015!\u0003K\u0011\u00151\u0006\u0001\"\u0001X\u0011\u00151\u0006\u0001\"\u0001\\\u0011\u00151\u0006\u0001\"\u0001h\u0011\u0015\u0001\u0005\u0001\"\u0011r\u0011\u0015Q\b\u0001\"\u0011|\u0011\u001d\t\t\u0001\u0001C\u0001\u0003\u0007A\u0011\"!\u0003\u0001\u0003\u0003%\t!a\u0003\t\u0013\u0005E\u0001!%A\u0005\u0002\u0005M\u0001\"CA\u0015\u0001E\u0005I\u0011AA\u0016\u0011%\ty\u0003AA\u0001\n\u0003\n\t\u0004C\u0005\u00028\u0001\t\t\u0011\"\u0001\u0002:!I\u0011\u0011\t\u0001\u0002\u0002\u0013\u0005\u00111\t\u0005\n\u0003\u001f\u0002\u0011\u0011!C!\u0003#B\u0011\"a\u0017\u0001\u0003\u0003%\t!!\u0018\t\u0013\u0005\u001d\u0004!!A\u0005B\u0005%\u0004\"CA7\u0001\u0005\u0005I\u0011IA8\u0011%\t\t\bAA\u0001\n\u0003\n\u0019\bC\u0005\u0002v\u0001\t\t\u0011\"\u0011\u0002x\u001dI\u0011qQ\u0011\u0002\u0002#\u0005\u0011\u0011\u0012\u0004\tA\u0005\n\t\u0011#\u0001\u0002\f\"1a\u000b\u0007C\u0001\u0003GC\u0011\"!\u001d\u0019\u0003\u0003%)%a\u001d\t\u0013\u0005\u0015\u0006$!A\u0005\u0002\u0006\u001d\u0006\"CAW1E\u0005I\u0011AA\u0016\u0011%\ty\u000bGA\u0001\n\u0003\u000b\t\fC\u0005\u0002Db\t\n\u0011\"\u0001\u0002,!I\u0011Q\u0019\r\u0002\u0002\u0013%\u0011q\u0019\u0002\f%\u0006tGm\\7He>,\bO\u0003\u0002#G\u00059!o\\;uS:<'\"\u0001\u0013\u0002\t\u0005\\7.Y\u0002\u0001'\u0015\u0001q%L\u00195!\tA3&D\u0001*\u0015\u0005Q\u0013!B:dC2\f\u0017B\u0001\u0017*\u0005\u0019\te.\u001f*fMB\u0011afL\u0007\u0002C%\u0011\u0001'\t\u0002\u0006\u000fJ|W\u000f\u001d\t\u0003QIJ!aM\u0015\u0003\u000fA\u0013x\u000eZ;diB\u0011Q'\u0010\b\u0003mmr!a\u000e\u001e\u000e\u0003aR!!O\u0013\u0002\rq\u0012xn\u001c;?\u0013\u0005Q\u0013B\u0001\u001f*\u0003\u001d\u0001\u0018mY6bO\u0016L!AP \u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005qJ\u0013!\u00029bi\"\u001cX#\u0001\"\u0011\u0007\rC%*D\u0001E\u0015\t)e)A\u0005j[6,H/\u00192mK*\u0011q)K\u0001\u000bG>dG.Z2uS>t\u0017BA%E\u0005!IE/\u001a:bE2,\u0007CA&P\u001d\taU\n\u0005\u00028S%\u0011a*K\u0001\u0007!J,G-\u001a4\n\u0005A\u000b&AB*ue&twM\u0003\u0002OS\u00051\u0001/\u0019;ig\u0002\n\u0001C]8vi\u0016\u0014H)[:qCR\u001c\u0007.\u001a:\u0016\u0003)\u000b\u0011C]8vi\u0016\u0014H)[:qCR\u001c\u0007.\u001a:!\u0003\u0019a\u0014N\\5u}Q\u0019\u0001,\u0017.\u0011\u00059\u0002\u0001\"\u0002!\u0006\u0001\u0004\u0011\u0005bB*\u0006!\u0003\u0005\rA\u0013\u000b\u00031rCQ!\u0018\u0004A\u0002y\u000baaY8oM&<\u0007CA0f\u001b\u0005\u0001'BA/b\u0015\t\u00117-\u0001\u0005usB,7/\u00194f\u0015\u0005!\u0017aA2p[&\u0011a\r\u0019\u0002\u0007\u0007>tg-[4\u0015\u0005aC\u0007\"B5\b\u0001\u0004Q\u0017a\u0003:pkR,W\rU1uQN\u00042a\u001b9K\u001b\u0005a'BA7o\u0003\u0011a\u0017M\\4\u000b\u0003=\fAA[1wC&\u0011\u0011\n\u001c\u000b\u0003\u0005JDQa\u001d\u0005A\u0002Q\faa]=ti\u0016l\u0007CA;y\u001b\u00051(BA<$\u0003\u0015\t7\r^8s\u0013\tIhOA\u0006BGR|'oU=ti\u0016l\u0017\u0001D2sK\u0006$XMU8vi\u0016\u0014HC\u0001?\u0000!\tqS0\u0003\u0002\u007fC\t1!k\\;uKJDQa]\u0005A\u0002Q\fab^5uQ\u0012K7\u000f]1uG\",'\u000fF\u0002Y\u0003\u000bAa!a\u0002\u000b\u0001\u0004Q\u0015\u0001\u00043jgB\fGo\u00195fe&#\u0017\u0001B2paf$R\u0001WA\u0007\u0003\u001fAq\u0001Q\u0006\u0011\u0002\u0003\u0007!\tC\u0004T\u0017A\u0005\t\u0019\u0001&\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011Q\u0003\u0016\u0004\u0005\u0006]1FAA\r!\u0011\tY\"!\n\u000e\u0005\u0005u!\u0002BA\u0010\u0003C\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005\r\u0012&\u0001\u0006b]:|G/\u0019;j_:LA!a\n\u0002\u001e\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u0011Q\u0006\u0016\u0004\u0015\u0006]\u0011!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u00024A\u00191.!\u000e\n\u0005Ac\u0017\u0001\u00049s_\u0012,8\r^!sSRLXCAA\u001e!\rA\u0013QH\u0005\u0004\u0003\u007fI#aA%oi\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA#\u0003\u0017\u00022\u0001KA$\u0013\r\tI%\u000b\u0002\u0004\u0003:L\b\"CA'!\u0005\u0005\t\u0019AA\u001e\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u00111\u000b\t\u0007\u0003+\n9&!\u0012\u000e\u0003\u0019K1!!\u0017G\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005}\u0013Q\r\t\u0004Q\u0005\u0005\u0014bAA2S\t9!i\\8mK\u0006t\u0007\"CA'%\u0005\u0005\t\u0019AA#\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005M\u00121\u000e\u0005\n\u0003\u001b\u001a\u0012\u0011!a\u0001\u0003w\t\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003w\t\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003g\ta!Z9vC2\u001cH\u0003BA0\u0003sB\u0011\"!\u0014\u0017\u0003\u0003\u0005\r!!\u0012)\u000f\u0001\ti(a!\u0002\u0006B\u0019\u0001&a \n\u0007\u0005\u0005\u0015F\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!A\u0006SC:$w.\\$s_V\u0004\bC\u0001\u0018\u0019'\u0015A\u0012QRAM!\u001d\ty)!&C\u0015bk!!!%\u000b\u0007\u0005M\u0015&A\u0004sk:$\u0018.\\3\n\t\u0005]\u0015\u0011\u0013\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u0014\u0004\u0003BAN\u0003Ck!!!(\u000b\u0007\u0005}e.\u0001\u0002j_&\u0019a(!(\u0015\u0005\u0005%\u0015!B1qa2LH#\u0002-\u0002*\u0006-\u0006\"\u0002!\u001c\u0001\u0004\u0011\u0005bB*\u001c!\u0003\u0005\rAS\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u00059QO\\1qa2LH\u0003BAZ\u0003\u007f\u0003R\u0001KA[\u0003sK1!a.*\u0005\u0019y\u0005\u000f^5p]B)\u0001&a/C\u0015&\u0019\u0011QX\u0015\u0003\rQ+\b\u000f\\33\u0011!\t\t-HA\u0001\u0002\u0004A\u0016a\u0001=%a\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!3\u0011\u0007-\fY-C\u0002\u0002N2\u0014aa\u00142kK\u000e$\b")
public final class RandomGroup
implements Group,
Product {
    private static final long serialVersionUID = 1L;
    private final Iterable<String> paths;
    private final String routerDispatcher;

    public static String $lessinit$greater$default$2() {
        return RandomGroup$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple2<Iterable<String>, String>> unapply(RandomGroup x$0) {
        return RandomGroup$.MODULE$.unapply(x$0);
    }

    public static String apply$default$2() {
        return RandomGroup$.MODULE$.apply$default$2();
    }

    public static RandomGroup apply(Iterable<String> paths, String routerDispatcher) {
        return RandomGroup$.MODULE$.apply(paths, routerDispatcher);
    }

    public static Function1<Tuple2<Iterable<String>, String>, RandomGroup> tupled() {
        return RandomGroup$.MODULE$.tupled();
    }

    public static Function1<Iterable<String>, Function1<String, RandomGroup>> curried() {
        return RandomGroup$.MODULE$.curried();
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
        return new Router(RandomRoutingLogic$.MODULE$.apply());
    }

    public RandomGroup withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        Iterable<String> x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    public RandomGroup copy(Iterable<String> paths, String routerDispatcher) {
        return new RandomGroup(paths, routerDispatcher);
    }

    public Iterable<String> copy$default$1() {
        return this.paths();
    }

    public String copy$default$2() {
        return this.routerDispatcher();
    }

    @Override
    public String productPrefix() {
        return "RandomGroup";
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
        return x$1 instanceof RandomGroup;
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
        if (!(object instanceof RandomGroup)) return false;
        boolean bl = true;
        if (!bl) return false;
        RandomGroup randomGroup = (RandomGroup)x$1;
        Iterable<String> iterable = this.paths();
        Iterable<String> iterable2 = randomGroup.paths();
        if (iterable == null) {
            if (iterable2 != null) {
                return false;
            }
        } else if (!iterable.equals(iterable2)) return false;
        String string = this.routerDispatcher();
        String string2 = randomGroup.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public RandomGroup(Iterable<String> paths, String routerDispatcher) {
        this.paths = paths;
        this.routerDispatcher = routerDispatcher;
        RouterConfig.$init$(this);
        Group.$init$(this);
        Product.$init$(this);
    }

    public RandomGroup(Config config) {
        this(Util$.MODULE$.immutableSeq(config.getStringList("routees.paths")), RandomGroup$.MODULE$.$lessinit$greater$default$2());
    }

    public RandomGroup(java.lang.Iterable<String> routeePaths) {
        this(Util$.MODULE$.immutableSeq(routeePaths), RandomGroup$.MODULE$.$lessinit$greater$default$2());
    }
}

