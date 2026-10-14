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
import akka.routing.ScatterGatherFirstCompletedGroup$;
import akka.routing.ScatterGatherFirstCompletedRoutingLogic;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import com.typesafe.config.Config;
import java.time.Duration;
import scala.Function1;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t5a\u0001\u0002\u0013&\u0005*B\u0001\u0002\u0012\u0001\u0003\u0016\u0004%\t!\u0012\u0005\t-\u0002\u0011\t\u0012)A\u0005\r\"Aq\u000b\u0001BK\u0002\u0013\u0005\u0001\f\u0003\u0005b\u0001\tE\t\u0015!\u0003Z\u0011!\u0011\u0007A!f\u0001\n\u0003\u001a\u0007\u0002\u00033\u0001\u0005#\u0005\u000b\u0011\u0002(\t\u000b\u0015\u0004A\u0011\u00014\t\u000b\u0015\u0004A\u0011A6\t\u000b\u0015\u0004A\u0011A<\t\r\u0015\u0004A\u0011AA\u0003\u0011\u0019!\u0005\u0001\"\u0011\u0002\u0018!9\u0011\u0011\u0006\u0001\u0005B\u0005-\u0002bBA\u001b\u0001\u0011\u0005\u0011q\u0007\u0005\n\u0003{\u0001\u0011\u0011!C\u0001\u0003\u007fA\u0011\"a\u0012\u0001#\u0003%\t!!\u0013\t\u0013\u0005}\u0003!%A\u0005\u0002\u0005\u0005\u0004\"CA3\u0001E\u0005I\u0011AA4\u0011%\tY\u0007AA\u0001\n\u0003\ni\u0007C\u0005\u0002t\u0001\t\t\u0011\"\u0001\u0002v!I\u0011Q\u0010\u0001\u0002\u0002\u0013\u0005\u0011q\u0010\u0005\n\u0003\u0017\u0003\u0011\u0011!C!\u0003\u001bC\u0011\"a&\u0001\u0003\u0003%\t!!'\t\u0013\u0005\r\u0006!!A\u0005B\u0005\u0015\u0006\"CAU\u0001\u0005\u0005I\u0011IAV\u0011%\ti\u000bAA\u0001\n\u0003\ny\u000bC\u0005\u00022\u0002\t\t\u0011\"\u0011\u00024\u001eI\u00111Y\u0013\u0002\u0002#\u0005\u0011Q\u0019\u0004\tI\u0015\n\t\u0011#\u0001\u0002H\"1Q\r\bC\u0001\u0003?D\u0011\"!,\u001d\u0003\u0003%)%a,\t\u0013\u0005\u0005H$!A\u0005\u0002\u0006\r\b\"CAv9E\u0005I\u0011AA4\u0011%\ti\u000fHA\u0001\n\u0003\u000by\u000fC\u0005\u0003\u0002q\t\n\u0011\"\u0001\u0002h!I!1\u0001\u000f\u0002\u0002\u0013%!Q\u0001\u0002!'\u000e\fG\u000f^3s\u000f\u0006$\b.\u001a:GSJ\u001cHoQ8na2,G/\u001a3He>,\bO\u0003\u0002'O\u00059!o\\;uS:<'\"\u0001\u0015\u0002\t\u0005\\7.Y\u0002\u0001'\u0015\u00011&M\u001b9!\tas&D\u0001.\u0015\u0005q\u0013!B:dC2\f\u0017B\u0001\u0019.\u0005\u0019\te.\u001f*fMB\u0011!gM\u0007\u0002K%\u0011A'\n\u0002\u0006\u000fJ|W\u000f\u001d\t\u0003YYJ!aN\u0017\u0003\u000fA\u0013x\u000eZ;diB\u0011\u0011(\u0011\b\u0003u}r!a\u000f \u000e\u0003qR!!P\u0015\u0002\rq\u0012xn\u001c;?\u0013\u0005q\u0013B\u0001!.\u0003\u001d\u0001\u0018mY6bO\u0016L!AQ\"\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005\u0001k\u0013!\u00029bi\"\u001cX#\u0001$\u0011\u0007\u001dce*D\u0001I\u0015\tI%*A\u0005j[6,H/\u00192mK*\u00111*L\u0001\u000bG>dG.Z2uS>t\u0017BA'I\u0005!IE/\u001a:bE2,\u0007CA(T\u001d\t\u0001\u0016\u000b\u0005\u0002<[%\u0011!+L\u0001\u0007!J,G-\u001a4\n\u0005Q+&AB*ue&twM\u0003\u0002S[\u00051\u0001/\u0019;ig\u0002\naa^5uQ&tW#A-\u0011\u0005i{V\"A.\u000b\u0005qk\u0016\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005yk\u0013AC2p]\u000e,(O]3oi&\u0011\u0001m\u0017\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0003\u001d9\u0018\u000e\u001e5j]\u0002\n\u0001C]8vi\u0016\u0014H)[:qCR\u001c\u0007.\u001a:\u0016\u00039\u000b\u0011C]8vi\u0016\u0014H)[:qCR\u001c\u0007.\u001a:!\u0003\u0019a\u0014N\\5u}Q!q\r[5k!\t\u0011\u0004\u0001C\u0003E\u000f\u0001\u0007a\tC\u0003X\u000f\u0001\u0007\u0011\fC\u0004c\u000fA\u0005\t\u0019\u0001(\u0015\u0005\u001dd\u0007\"B7\t\u0001\u0004q\u0017AB2p]\u001aLw\r\u0005\u0002pk6\t\u0001O\u0003\u0002nc*\u0011!o]\u0001\tif\u0004Xm]1gK*\tA/A\u0002d_6L!A\u001e9\u0003\r\r{gNZ5h)\u00119\u00070a\u0001\t\u000beL\u0001\u0019\u0001>\u0002\u0017I|W\u000f^3f!\u0006$\bn\u001d\t\u0005w\u0006\u0005a*D\u0001}\u0015\tih0\u0001\u0003mC:<'\"A@\u0002\t)\fg/Y\u0005\u0003\u001brDQaV\u0005A\u0002e#RaZA\u0004\u0003\u0013AQ!\u001f\u0006A\u0002iDaa\u0016\u0006A\u0002\u0005-\u0001\u0003BA\u0007\u0003'i!!a\u0004\u000b\u0007\u0005Ea0\u0001\u0003uS6,\u0017\u0002BA\u000b\u0003\u001f\u0011\u0001\u0002R;sCRLwN\u001c\u000b\u0004\r\u0006e\u0001bBA\u000e\u0017\u0001\u0007\u0011QD\u0001\u0007gf\u001cH/Z7\u0011\t\u0005}\u0011QE\u0007\u0003\u0003CQ1!a\t(\u0003\u0015\t7\r^8s\u0013\u0011\t9#!\t\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0001\rGJ,\u0017\r^3S_V$XM\u001d\u000b\u0005\u0003[\t\u0019\u0004E\u00023\u0003_I1!!\r&\u0005\u0019\u0011v.\u001e;fe\"9\u00111\u0004\u0007A\u0002\u0005u\u0011AD<ji\"$\u0015n\u001d9bi\u000eDWM\u001d\u000b\u0004O\u0006e\u0002BBA\u001e\u001b\u0001\u0007a*\u0001\u0007eSN\u0004\u0018\r^2iKJLE-\u0001\u0003d_BLHcB4\u0002B\u0005\r\u0013Q\t\u0005\b\t:\u0001\n\u00111\u0001G\u0011\u001d9f\u0002%AA\u0002eCqA\u0019\b\u0011\u0002\u0003\u0007a*\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005-#f\u0001$\u0002N-\u0012\u0011q\n\t\u0005\u0003#\nY&\u0004\u0002\u0002T)!\u0011QKA,\u0003%)hn\u00195fG.,GMC\u0002\u0002Z5\n!\"\u00198o_R\fG/[8o\u0013\u0011\ti&a\u0015\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005\r$fA-\u0002N\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTCAA5U\rq\u0015QJ\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005=\u0004cA>\u0002r%\u0011A\u000b`\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003o\u00022\u0001LA=\u0013\r\tY(\f\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003\u0003\u000b9\tE\u0002-\u0003\u0007K1!!\".\u0005\r\te.\u001f\u0005\n\u0003\u0013#\u0012\u0011!a\u0001\u0003o\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAAH!\u0019\t\t*a%\u0002\u00026\t!*C\u0002\u0002\u0016*\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u00111TAQ!\ra\u0013QT\u0005\u0004\u0003?k#a\u0002\"p_2,\u0017M\u001c\u0005\n\u0003\u00133\u0012\u0011!a\u0001\u0003\u0003\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011qNAT\u0011%\tIiFA\u0001\u0002\u0004\t9(\u0001\u0005iCND7i\u001c3f)\t\t9(\u0001\u0005u_N#(/\u001b8h)\t\ty'\u0001\u0004fcV\fGn\u001d\u000b\u0005\u00037\u000b)\fC\u0005\u0002\nj\t\t\u00111\u0001\u0002\u0002\":\u0001!!/\u0002@\u0006\u0005\u0007c\u0001\u0017\u0002<&\u0019\u0011QX\u0017\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001\u0002AM\u001b\u0017\r\u001e;fe\u001e\u000bG\u000f[3s\r&\u00148\u000f^\"p[BdW\r^3e\u000fJ|W\u000f\u001d\t\u0003eq\u0019R\u0001HAe\u0003+\u0004\u0002\"a3\u0002R\u001aKfjZ\u0007\u0003\u0003\u001bT1!a4.\u0003\u001d\u0011XO\u001c;j[\u0016LA!a5\u0002N\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u001a\u0011\t\u0005]\u0017Q\\\u0007\u0003\u00033T1!a7\u007f\u0003\tIw.C\u0002C\u00033$\"!!2\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000f\u001d\f)/a:\u0002j\")Ai\ba\u0001\r\")qk\ba\u00013\"9!m\bI\u0001\u0002\u0004q\u0015aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0002\u000fUt\u0017\r\u001d9msR!\u0011\u0011_A\u007f!\u0015a\u00131_A|\u0013\r\t)0\f\u0002\u0007\u001fB$\u0018n\u001c8\u0011\r1\nIPR-O\u0013\r\tY0\f\u0002\u0007)V\u0004H.Z\u001a\t\u0011\u0005}\u0018%!AA\u0002\u001d\f1\u0001\u001f\u00131\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%g\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011!q\u0001\t\u0004w\n%\u0011b\u0001B\u0006y\n1qJ\u00196fGR\u0004")
public final class ScatterGatherFirstCompletedGroup
implements Group,
Product {
    private static final long serialVersionUID = 1L;
    private final Iterable<String> paths;
    private final FiniteDuration within;
    private final String routerDispatcher;

    public static String $lessinit$greater$default$3() {
        return ScatterGatherFirstCompletedGroup$.MODULE$.$lessinit$greater$default$3();
    }

    public static Option<Tuple3<Iterable<String>, FiniteDuration, String>> unapply(ScatterGatherFirstCompletedGroup x$0) {
        return ScatterGatherFirstCompletedGroup$.MODULE$.unapply(x$0);
    }

    public static String apply$default$3() {
        return ScatterGatherFirstCompletedGroup$.MODULE$.apply$default$3();
    }

    public static ScatterGatherFirstCompletedGroup apply(Iterable<String> paths, FiniteDuration within, String routerDispatcher) {
        return ScatterGatherFirstCompletedGroup$.MODULE$.apply(paths, within, routerDispatcher);
    }

    public static Function1<Tuple3<Iterable<String>, FiniteDuration, String>, ScatterGatherFirstCompletedGroup> tupled() {
        return ScatterGatherFirstCompletedGroup$.MODULE$.tupled();
    }

    public static Function1<Iterable<String>, Function1<FiniteDuration, Function1<String, ScatterGatherFirstCompletedGroup>>> curried() {
        return ScatterGatherFirstCompletedGroup$.MODULE$.curried();
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
        return new Router(new ScatterGatherFirstCompletedRoutingLogic(this.within()));
    }

    public ScatterGatherFirstCompletedGroup withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        Iterable<String> x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$2();
        return this.copy(x$2, x$3, x$1);
    }

    public ScatterGatherFirstCompletedGroup copy(Iterable<String> paths, FiniteDuration within, String routerDispatcher) {
        return new ScatterGatherFirstCompletedGroup(paths, within, routerDispatcher);
    }

    public Iterable<String> copy$default$1() {
        return this.paths();
    }

    public FiniteDuration copy$default$2() {
        return this.within();
    }

    public String copy$default$3() {
        return this.routerDispatcher();
    }

    @Override
    public String productPrefix() {
        return "ScatterGatherFirstCompletedGroup";
    }

    @Override
    public int productArity() {
        return 3;
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
        return x$1 instanceof ScatterGatherFirstCompletedGroup;
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
        if (!(object instanceof ScatterGatherFirstCompletedGroup)) return false;
        boolean bl = true;
        if (!bl) return false;
        ScatterGatherFirstCompletedGroup scatterGatherFirstCompletedGroup = (ScatterGatherFirstCompletedGroup)x$1;
        Iterable<String> iterable = this.paths();
        Iterable<String> iterable2 = scatterGatherFirstCompletedGroup.paths();
        if (iterable == null) {
            if (iterable2 != null) {
                return false;
            }
        } else if (!iterable.equals(iterable2)) return false;
        FiniteDuration finiteDuration = this.within();
        FiniteDuration finiteDuration2 = scatterGatherFirstCompletedGroup.within();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        String string = this.routerDispatcher();
        String string2 = scatterGatherFirstCompletedGroup.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public ScatterGatherFirstCompletedGroup(Iterable<String> paths, FiniteDuration within, String routerDispatcher) {
        this.paths = paths;
        this.within = within;
        this.routerDispatcher = routerDispatcher;
        RouterConfig.$init$(this);
        Group.$init$(this);
        Product.$init$(this);
    }

    public ScatterGatherFirstCompletedGroup(Config config) {
        this(Util$.MODULE$.immutableSeq(config.getStringList("routees.paths")), Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "within"), ScatterGatherFirstCompletedGroup$.MODULE$.$lessinit$greater$default$3());
    }

    public ScatterGatherFirstCompletedGroup(java.lang.Iterable<String> routeePaths, FiniteDuration within) {
        this(Util$.MODULE$.immutableSeq(routeePaths), within, ScatterGatherFirstCompletedGroup$.MODULE$.$lessinit$greater$default$3());
    }

    public ScatterGatherFirstCompletedGroup(java.lang.Iterable<String> routeePaths, Duration within) {
        this(Util$.MODULE$.immutableSeq(routeePaths), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(within)), ScatterGatherFirstCompletedGroup$.MODULE$.$lessinit$greater$default$3());
    }
}

