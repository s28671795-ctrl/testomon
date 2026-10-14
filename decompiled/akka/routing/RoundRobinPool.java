/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.routing.Pool;
import akka.routing.PoolOverrideUnsetConfig;
import akka.routing.Resizer;
import akka.routing.Resizer$;
import akka.routing.RoundRobinPool$;
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
import scala.Some;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tUc\u0001\u0002\u001a4\u0005bB\u0001B\u0016\u0001\u0003\u0016\u0004%\ta\u0016\u0005\t7\u0002\u0011\t\u0012)A\u00051\"AA\f\u0001BK\u0002\u0013\u0005S\f\u0003\u0005e\u0001\tE\t\u0015!\u0003_\u0011!)\u0007A!f\u0001\n\u00032\u0007\u0002C7\u0001\u0005#\u0005\u000b\u0011B4\t\u00119\u0004!Q3A\u0005B=D\u0001\u0002\u001f\u0001\u0003\u0012\u0003\u0006I\u0001\u001d\u0005\ts\u0002\u0011)\u001a!C!u\"Aa\u0010\u0001B\tB\u0003%1\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0011\u0011\u0001\u0005\u0007\u007f\u0002!\t!!\u0004\t\r}\u0004A\u0011AA\u0013\u0011\u001d\tY\u0003\u0001C!\u0003[AaA\u0016\u0001\u0005B\u0005}\u0002bBA#\u0001\u0011\u0005\u0011q\t\u0005\b\u0003\u001b\u0002A\u0011AA(\u0011\u001d\t\u0019\u0006\u0001C\u0001\u0003+Bq!a\u0017\u0001\t\u0003\ni\u0006C\u0005\u0002j\u0001\t\t\u0011\"\u0001\u0002l!I\u0011q\u000f\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u0010\u0005\n\u0003\u001f\u0003\u0011\u0013!C\u0001\u0003#C\u0011\"!&\u0001#\u0003%\t!a&\t\u0013\u0005m\u0005!%A\u0005\u0002\u0005u\u0005\"CAQ\u0001E\u0005I\u0011AAR\u0011%\t9\u000bAA\u0001\n\u0003\nI\u000b\u0003\u0005\u0002:\u0002\t\t\u0011\"\u0001X\u0011%\tY\fAA\u0001\n\u0003\ti\fC\u0005\u0002J\u0002\t\t\u0011\"\u0011\u0002L\"I\u0011\u0011\u001c\u0001\u0002\u0002\u0013\u0005\u00111\u001c\u0005\n\u0003?\u0004\u0011\u0011!C!\u0003CD\u0011\"!:\u0001\u0003\u0003%\t%a:\t\u0013\u0005%\b!!A\u0005B\u0005-\b\"CAw\u0001\u0005\u0005I\u0011IAx\u000f%\typMA\u0001\u0012\u0003\u0011\tA\u0002\u00053g\u0005\u0005\t\u0012\u0001B\u0002\u0011\u0019yH\u0005\"\u0001\u0003\u001c!I\u0011\u0011\u001e\u0013\u0002\u0002\u0013\u0015\u00131\u001e\u0005\n\u0005;!\u0013\u0011!CA\u0005?A\u0011Ba\u000b%#\u0003%\t!!%\t\u0013\t5B%%A\u0005\u0002\u0005]\u0005\"\u0003B\u0018IE\u0005I\u0011AAO\u0011%\u0011\t\u0004JI\u0001\n\u0003\t\u0019\u000bC\u0005\u00034\u0011\n\t\u0011\"!\u00036!I!1\t\u0013\u0012\u0002\u0013\u0005\u0011\u0011\u0013\u0005\n\u0005\u000b\"\u0013\u0013!C\u0001\u0003/C\u0011Ba\u0012%#\u0003%\t!!(\t\u0013\t%C%%A\u0005\u0002\u0005\r\u0006\"\u0003B&I\u0005\u0005I\u0011\u0002B'\u00059\u0011v.\u001e8e%>\u0014\u0017N\u001c)p_2T!\u0001N\u001b\u0002\u000fI|W\u000f^5oO*\ta'\u0001\u0003bW.\f7\u0001A\n\u0007\u0001ez4i\u0012&\u0011\u0005ijT\"A\u001e\u000b\u0003q\nQa]2bY\u0006L!AP\u001e\u0003\r\u0005s\u0017PU3g!\t\u0001\u0015)D\u00014\u0013\t\u00115G\u0001\u0003Q_>d\u0007c\u0001!E\r&\u0011Qi\r\u0002\u0018!>|Gn\u0014<feJLG-Z+og\u0016$8i\u001c8gS\u001e\u0004\"\u0001\u0011\u0001\u0011\u0005iB\u0015BA%<\u0005\u001d\u0001&o\u001c3vGR\u0004\"aS*\u000f\u00051\u000bfBA'Q\u001b\u0005q%BA(8\u0003\u0019a$o\\8u}%\tA(\u0003\u0002Sw\u00059\u0001/Y2lC\u001e,\u0017B\u0001+V\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\t\u00116(A\u0007oe>3\u0017J\\:uC:\u001cWm]\u000b\u00021B\u0011!(W\u0005\u00035n\u00121!\u00138u\u00039q'o\u00144J]N$\u0018M\\2fg\u0002\nqA]3tSj,'/F\u0001_!\rQt,Y\u0005\u0003An\u0012aa\u00149uS>t\u0007C\u0001!c\u0013\t\u00197GA\u0004SKNL'0\u001a:\u0002\u0011I,7/\u001b>fe\u0002\n!c];qKJ4\u0018n]8s'R\u0014\u0018\r^3hsV\tq\r\u0005\u0002iW6\t\u0011N\u0003\u0002kk\u0005)\u0011m\u0019;pe&\u0011A.\u001b\u0002\u0013'V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u00170A\ntkB,'O^5t_J\u001cFO]1uK\u001eL\b%\u0001\ts_V$XM\u001d#jgB\fGo\u00195feV\t\u0001\u000f\u0005\u0002rk:\u0011!o\u001d\t\u0003\u001bnJ!\u0001^\u001e\u0002\rA\u0013X\rZ3g\u0013\t1xO\u0001\u0004TiJLgn\u001a\u0006\u0003in\n\u0011C]8vi\u0016\u0014H)[:qCR\u001c\u0007.\u001a:!\u0003E)8/\u001a)p_2$\u0015n\u001d9bi\u000eDWM]\u000b\u0002wB\u0011!\b`\u0005\u0003{n\u0012qAQ8pY\u0016\fg.\u0001\nvg\u0016\u0004vn\u001c7ESN\u0004\u0018\r^2iKJ\u0004\u0013A\u0002\u001fj]&$h\bF\u0006G\u0003\u0007\t)!a\u0002\u0002\n\u0005-\u0001\"\u0002,\f\u0001\u0004A\u0006b\u0002/\f!\u0003\u0005\rA\u0018\u0005\bK.\u0001\n\u00111\u0001h\u0011\u001dq7\u0002%AA\u0002ADq!_\u0006\u0011\u0002\u0003\u00071\u0010F\u0002G\u0003\u001fAq!!\u0005\r\u0001\u0004\t\u0019\"\u0001\u0004d_:4\u0017n\u001a\t\u0005\u0003+\t\t#\u0004\u0002\u0002\u0018)!\u0011\u0011CA\r\u0015\u0011\tY\"!\b\u0002\u0011QL\b/Z:bM\u0016T!!a\b\u0002\u0007\r|W.\u0003\u0003\u0002$\u0005]!AB\"p]\u001aLw\rF\u0002G\u0003OAa!!\u000b\u000e\u0001\u0004A\u0016A\u00018s\u00031\u0019'/Z1uKJ{W\u000f^3s)\u0011\ty#!\u000e\u0011\u0007\u0001\u000b\t$C\u0002\u00024M\u0012aAU8vi\u0016\u0014\bbBA\u001c\u001d\u0001\u0007\u0011\u0011H\u0001\u0007gf\u001cH/Z7\u0011\u0007!\fY$C\u0002\u0002>%\u00141\"Q2u_J\u001c\u0016p\u001d;f[R\u0019\u0001,!\u0011\t\u000f\u0005\rs\u00021\u0001\u0002:\u0005\u00191/_:\u0002-]LG\u000f[*va\u0016\u0014h/[:peN#(/\u0019;fOf$2ARA%\u0011\u0019\tY\u0005\u0005a\u0001O\u0006A1\u000f\u001e:bi\u0016<\u00170A\u0006xSRD'+Z:ju\u0016\u0014Hc\u0001$\u0002R!)A,\u0005a\u0001C\u0006qq/\u001b;i\t&\u001c\b/\u0019;dQ\u0016\u0014Hc\u0001$\u0002X!1\u0011\u0011\f\nA\u0002A\fA\u0002Z5ta\u0006$8\r[3s\u0013\u0012\fAb^5uQ\u001a\u000bG\u000e\u001c2bG.$B!a\u0018\u0002fA\u0019\u0001)!\u0019\n\u0007\u0005\r4G\u0001\u0007S_V$XM]\"p]\u001aLw\rC\u0004\u0002hM\u0001\r!a\u0018\u0002\u000b=$\b.\u001a:\u0002\t\r|\u0007/\u001f\u000b\f\r\u00065\u0014qNA9\u0003g\n)\bC\u0004W)A\u0005\t\u0019\u0001-\t\u000fq#\u0002\u0013!a\u0001=\"9Q\r\u0006I\u0001\u0002\u00049\u0007b\u00028\u0015!\u0003\u0005\r\u0001\u001d\u0005\bsR\u0001\n\u00111\u0001|\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!a\u001f+\u0007a\u000bih\u000b\u0002\u0002\u0000A!\u0011\u0011QAF\u001b\t\t\u0019I\u0003\u0003\u0002\u0006\u0006\u001d\u0015!C;oG\",7m[3e\u0015\r\tIiO\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAG\u0003\u0007\u0013\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"!a%+\u0007y\u000bi(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005e%fA4\u0002~\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCAAPU\r\u0001\u0018QP\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+\t\t)KK\u0002|\u0003{\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAAV!\u0011\ti+a.\u000e\u0005\u0005=&\u0002BAY\u0003g\u000bA\u0001\\1oO*\u0011\u0011QW\u0001\u0005U\u00064\u0018-C\u0002w\u0003_\u000bA\u0002\u001d:pIV\u001cG/\u0011:jif\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002@\u0006\u0015\u0007c\u0001\u001e\u0002B&\u0019\u00111Y\u001e\u0003\u0007\u0005s\u0017\u0010\u0003\u0005\u0002Hr\t\t\u00111\u0001Y\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\u001a\t\u0007\u0003\u001f\f).a0\u000e\u0005\u0005E'bAAjw\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005]\u0017\u0011\u001b\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000eF\u0002|\u0003;D\u0011\"a2\u001f\u0003\u0003\u0005\r!a0\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003W\u000b\u0019\u000f\u0003\u0005\u0002H~\t\t\u00111\u0001Y\u0003!A\u0017m\u001d5D_\u0012,G#\u0001-\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a+\u0002\r\u0015\fX/\u00197t)\rY\u0018\u0011\u001f\u0005\n\u0003\u000f\u0014\u0013\u0011!a\u0001\u0003\u007fCs\u0001AA{\u0003w\fi\u0010E\u0002;\u0003oL1!!?<\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u00039\u0011v.\u001e8e%>\u0014\u0017N\u001c)p_2\u0004\"\u0001\u0011\u0013\u0014\u000b\u0011\u0012)A!\u0005\u0011\u0015\t\u001d!Q\u0002-_OB\\h)\u0004\u0002\u0003\n)\u0019!1B\u001e\u0002\u000fI,h\u000e^5nK&!!q\u0002B\u0005\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\u000e\t\u0005\u0005'\u0011I\"\u0004\u0002\u0003\u0016)!!qCAZ\u0003\tIw.C\u0002U\u0005+!\"A!\u0001\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0017\u0019\u0013\tCa\t\u0003&\t\u001d\"\u0011\u0006\u0005\u0006-\u001e\u0002\r\u0001\u0017\u0005\b9\u001e\u0002\n\u00111\u0001_\u0011\u001d)w\u0005%AA\u0002\u001dDqA\\\u0014\u0011\u0002\u0003\u0007\u0001\u000fC\u0004zOA\u0005\t\u0019A>\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII\nq\"\u00199qYf$C-\u001a4bk2$HeM\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%i\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$S'A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\t]\"q\b\t\u0005u}\u0013I\u0004\u0005\u0005;\u0005wAfl\u001a9|\u0013\r\u0011id\u000f\u0002\u0007)V\u0004H.Z\u001b\t\u0011\t\u0005C&!AA\u0002\u0019\u000b1\u0001\u001f\u00131\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%e\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIM\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\"\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$S'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003PA!\u0011Q\u0016B)\u0013\u0011\u0011\u0019&a,\u0003\r=\u0013'.Z2u\u0001")
public final class RoundRobinPool
implements PoolOverrideUnsetConfig<RoundRobinPool>,
Product {
    private static final long serialVersionUID = 1L;
    private final int nrOfInstances;
    private final Option<Resizer> resizer;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;
    private final boolean usePoolDispatcher;

    public static boolean $lessinit$greater$default$5() {
        return RoundRobinPool$.MODULE$.$lessinit$greater$default$5();
    }

    public static String $lessinit$greater$default$4() {
        return RoundRobinPool$.MODULE$.$lessinit$greater$default$4();
    }

    public static SupervisorStrategy $lessinit$greater$default$3() {
        return RoundRobinPool$.MODULE$.$lessinit$greater$default$3();
    }

    public static Option<Resizer> $lessinit$greater$default$2() {
        return RoundRobinPool$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>> unapply(RoundRobinPool x$0) {
        return RoundRobinPool$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$5() {
        return RoundRobinPool$.MODULE$.apply$default$5();
    }

    public static String apply$default$4() {
        return RoundRobinPool$.MODULE$.apply$default$4();
    }

    public static SupervisorStrategy apply$default$3() {
        return RoundRobinPool$.MODULE$.apply$default$3();
    }

    public static Option<Resizer> apply$default$2() {
        return RoundRobinPool$.MODULE$.apply$default$2();
    }

    public static RoundRobinPool apply(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return RoundRobinPool$.MODULE$.apply(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public static Function1<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>, RoundRobinPool> tupled() {
        return RoundRobinPool$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Option<Resizer>, Function1<SupervisorStrategy, Function1<String, Function1<Object, RoundRobinPool>>>>> curried() {
        return RoundRobinPool$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public final RouterConfig overrideUnsetConfig(RouterConfig other) {
        return PoolOverrideUnsetConfig.overrideUnsetConfig$(this, other);
    }

    @Override
    public Routee newRoutee(Props routeeProps, ActorContext context) {
        return Pool.newRoutee$(this, routeeProps, context);
    }

    @Override
    public Props enrichWithPoolDispatcher(Props routeeProps, ActorContext context) {
        return Pool.enrichWithPoolDispatcher$(this, routeeProps, context);
    }

    @Override
    public Props props(Props routeeProps) {
        return Pool.props$(this, routeeProps);
    }

    @Override
    public boolean stopRouterWhenAllRouteesRemoved() {
        return Pool.stopRouterWhenAllRouteesRemoved$(this);
    }

    @Override
    public RouterActor createRouterActor() {
        return Pool.createRouterActor$(this);
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
    public void verifyConfig(ActorPath path) {
        RouterConfig.verifyConfig$(this, path);
    }

    public int nrOfInstances() {
        return this.nrOfInstances;
    }

    @Override
    public Option<Resizer> resizer() {
        return this.resizer;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return this.supervisorStrategy;
    }

    @Override
    public String routerDispatcher() {
        return this.routerDispatcher;
    }

    @Override
    public boolean usePoolDispatcher() {
        return this.usePoolDispatcher;
    }

    @Override
    public Router createRouter(ActorSystem system) {
        return new Router(RoundRobinRoutingLogic$.MODULE$.apply());
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return this.nrOfInstances();
    }

    @Override
    public RoundRobinPool withSupervisorStrategy(SupervisorStrategy strategy) {
        SupervisorStrategy x$1 = strategy;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        String x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$1, x$4, x$5);
    }

    @Override
    public RoundRobinPool withResizer(Resizer resizer) {
        Some<Resizer> x$1 = new Some<Resizer>(resizer);
        int x$2 = this.copy$default$1();
        SupervisorStrategy x$3 = this.copy$default$3();
        String x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$1, x$3, x$4, x$5);
    }

    public RoundRobinPool withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        SupervisorStrategy x$4 = this.copy$default$3();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$4, x$1, x$5);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return this.overrideUnsetConfig(other);
    }

    public RoundRobinPool copy(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new RoundRobinPool(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public int copy$default$1() {
        return this.nrOfInstances();
    }

    public Option<Resizer> copy$default$2() {
        return this.resizer();
    }

    public SupervisorStrategy copy$default$3() {
        return this.supervisorStrategy();
    }

    public String copy$default$4() {
        return this.routerDispatcher();
    }

    public boolean copy$default$5() {
        return this.usePoolDispatcher();
    }

    @Override
    public String productPrefix() {
        return "RoundRobinPool";
    }

    @Override
    public int productArity() {
        return 5;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.nrOfInstances());
            }
            case 1: {
                return this.resizer();
            }
            case 2: {
                return this.supervisorStrategy();
            }
            case 3: {
                return this.routerDispatcher();
            }
            case 4: {
                return BoxesRunTime.boxToBoolean(this.usePoolDispatcher());
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
        return x$1 instanceof RoundRobinPool;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "nrOfInstances";
            }
            case 1: {
                return "resizer";
            }
            case 2: {
                return "supervisorStrategy";
            }
            case 3: {
                return "routerDispatcher";
            }
            case 4: {
                return "usePoolDispatcher";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.nrOfInstances());
        n = Statics.mix(n, Statics.anyHash(this.resizer()));
        n = Statics.mix(n, Statics.anyHash(this.supervisorStrategy()));
        n = Statics.mix(n, Statics.anyHash(this.routerDispatcher()));
        n = Statics.mix(n, this.usePoolDispatcher() ? 1231 : 1237);
        return Statics.finalizeHash(n, 5);
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
        if (!(object instanceof RoundRobinPool)) return false;
        boolean bl = true;
        if (!bl) return false;
        RoundRobinPool roundRobinPool = (RoundRobinPool)x$1;
        if (this.nrOfInstances() != roundRobinPool.nrOfInstances()) return false;
        if (this.usePoolDispatcher() != roundRobinPool.usePoolDispatcher()) return false;
        Option<Resizer> option = this.resizer();
        Option<Resizer> option2 = roundRobinPool.resizer();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        SupervisorStrategy supervisorStrategy = this.supervisorStrategy();
        SupervisorStrategy supervisorStrategy2 = roundRobinPool.supervisorStrategy();
        if (supervisorStrategy == null) {
            if (supervisorStrategy2 != null) {
                return false;
            }
        } else if (!supervisorStrategy.equals(supervisorStrategy2)) return false;
        String string = this.routerDispatcher();
        String string2 = roundRobinPool.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public RoundRobinPool(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        this.nrOfInstances = nrOfInstances;
        this.resizer = resizer;
        this.supervisorStrategy = supervisorStrategy;
        this.routerDispatcher = routerDispatcher;
        this.usePoolDispatcher = usePoolDispatcher;
        RouterConfig.$init$(this);
        Pool.$init$(this);
        PoolOverrideUnsetConfig.$init$(this);
        Product.$init$(this);
    }

    public RoundRobinPool(Config config) {
        int x$1 = config.getInt("nr-of-instances");
        Option<Resizer> x$2 = Resizer$.MODULE$.fromConfig(config);
        boolean x$3 = config.hasPath("pool-dispatcher");
        SupervisorStrategy x$4 = RoundRobinPool$.MODULE$.$lessinit$greater$default$3();
        String x$5 = RoundRobinPool$.MODULE$.$lessinit$greater$default$4();
        this(x$1, x$2, x$4, x$5, x$3);
    }

    public RoundRobinPool(int nr) {
        this(nr, RoundRobinPool$.MODULE$.$lessinit$greater$default$2(), RoundRobinPool$.MODULE$.$lessinit$greater$default$3(), RoundRobinPool$.MODULE$.$lessinit$greater$default$4(), RoundRobinPool$.MODULE$.$lessinit$greater$default$5());
    }
}

