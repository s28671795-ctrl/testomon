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
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import akka.routing.SmallestMailboxPool$;
import akka.routing.SmallestMailboxRoutingLogic$;
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

@ScalaSignature(bytes="\u0006\u0005\tUc\u0001\u0002\u001a4\u0005bB\u0001B\u0016\u0001\u0003\u0016\u0004%\ta\u0016\u0005\t7\u0002\u0011\t\u0012)A\u00051\"AA\f\u0001BK\u0002\u0013\u0005S\f\u0003\u0005e\u0001\tE\t\u0015!\u0003_\u0011!)\u0007A!f\u0001\n\u00032\u0007\u0002C7\u0001\u0005#\u0005\u000b\u0011B4\t\u00119\u0004!Q3A\u0005B=D\u0001\u0002\u001f\u0001\u0003\u0012\u0003\u0006I\u0001\u001d\u0005\ts\u0002\u0011)\u001a!C!u\"Aa\u0010\u0001B\tB\u0003%1\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0011\u0011\u0001\u0005\u0007\u007f\u0002!\t!!\u0004\t\r}\u0004A\u0011AA\u0013\u0011\u001d\tY\u0003\u0001C!\u0003[AaA\u0016\u0001\u0005B\u0005}\u0002bBA#\u0001\u0011\u0005\u0011q\t\u0005\b\u0003\u001b\u0002A\u0011AA(\u0011\u001d\t\u0019\u0006\u0001C\u0001\u0003+Bq!a\u0017\u0001\t\u0003\ni\u0006C\u0005\u0002j\u0001\t\t\u0011\"\u0001\u0002l!I\u0011q\u000f\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u0010\u0005\n\u0003\u001f\u0003\u0011\u0013!C\u0001\u0003#C\u0011\"!&\u0001#\u0003%\t!a&\t\u0013\u0005m\u0005!%A\u0005\u0002\u0005u\u0005\"CAQ\u0001E\u0005I\u0011AAR\u0011%\t9\u000bAA\u0001\n\u0003\nI\u000b\u0003\u0005\u0002:\u0002\t\t\u0011\"\u0001X\u0011%\tY\fAA\u0001\n\u0003\ti\fC\u0005\u0002J\u0002\t\t\u0011\"\u0011\u0002L\"I\u0011\u0011\u001c\u0001\u0002\u0002\u0013\u0005\u00111\u001c\u0005\n\u0003?\u0004\u0011\u0011!C!\u0003CD\u0011\"!:\u0001\u0003\u0003%\t%a:\t\u0013\u0005%\b!!A\u0005B\u0005-\b\"CAw\u0001\u0005\u0005I\u0011IAx\u000f%\typMA\u0001\u0012\u0003\u0011\tA\u0002\u00053g\u0005\u0005\t\u0012\u0001B\u0002\u0011\u0019yH\u0005\"\u0001\u0003\u001c!I\u0011\u0011\u001e\u0013\u0002\u0002\u0013\u0015\u00131\u001e\u0005\n\u0005;!\u0013\u0011!CA\u0005?A\u0011Ba\u000b%#\u0003%\t!!%\t\u0013\t5B%%A\u0005\u0002\u0005]\u0005\"\u0003B\u0018IE\u0005I\u0011AAO\u0011%\u0011\t\u0004JI\u0001\n\u0003\t\u0019\u000bC\u0005\u00034\u0011\n\t\u0011\"!\u00036!I!1\t\u0013\u0012\u0002\u0013\u0005\u0011\u0011\u0013\u0005\n\u0005\u000b\"\u0013\u0013!C\u0001\u0003/C\u0011Ba\u0012%#\u0003%\t!!(\t\u0013\t%C%%A\u0005\u0002\u0005\r\u0006\"\u0003B&I\u0005\u0005I\u0011\u0002B'\u0005M\u0019V.\u00197mKN$X*Y5mE>D\bk\\8m\u0015\t!T'A\u0004s_V$\u0018N\\4\u000b\u0003Y\nA!Y6lC\u000e\u00011C\u0002\u0001:\u007f\r;%\n\u0005\u0002;{5\t1HC\u0001=\u0003\u0015\u00198-\u00197b\u0013\tq4H\u0001\u0004B]f\u0014VM\u001a\t\u0003\u0001\u0006k\u0011aM\u0005\u0003\u0005N\u0012A\u0001U8pYB\u0019\u0001\t\u0012$\n\u0005\u0015\u001b$a\u0006)p_2|e/\u001a:sS\u0012,WK\\:fi\u000e{gNZ5h!\t\u0001\u0005\u0001\u0005\u0002;\u0011&\u0011\u0011j\u000f\u0002\b!J|G-^2u!\tY5K\u0004\u0002M#:\u0011Q\nU\u0007\u0002\u001d*\u0011qjN\u0001\u0007yI|w\u000e\u001e \n\u0003qJ!AU\u001e\u0002\u000fA\f7m[1hK&\u0011A+\u0016\u0002\r'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.\u001a\u0006\u0003%n\nQB\u001c:PM&s7\u000f^1oG\u0016\u001cX#\u0001-\u0011\u0005iJ\u0016B\u0001.<\u0005\rIe\u000e^\u0001\u000f]J|e-\u00138ti\u0006t7-Z:!\u0003\u001d\u0011Xm]5{KJ,\u0012A\u0018\t\u0004u}\u000b\u0017B\u00011<\u0005\u0019y\u0005\u000f^5p]B\u0011\u0001IY\u0005\u0003GN\u0012qAU3tSj,'/\u0001\u0005sKNL'0\u001a:!\u0003I\u0019X\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0016\u0003\u001d\u0004\"\u0001[6\u000e\u0003%T!A[\u001b\u0002\u000b\u0005\u001cGo\u001c:\n\u00051L'AE*va\u0016\u0014h/[:peN#(/\u0019;fOf\f1c];qKJ4\u0018n]8s'R\u0014\u0018\r^3hs\u0002\n\u0001C]8vi\u0016\u0014H)[:qCR\u001c\u0007.\u001a:\u0016\u0003A\u0004\"!];\u000f\u0005I\u001c\bCA'<\u0013\t!8(\u0001\u0004Qe\u0016$WMZ\u0005\u0003m^\u0014aa\u0015;sS:<'B\u0001;<\u0003E\u0011x.\u001e;fe\u0012K7\u000f]1uG\",'\u000fI\u0001\u0012kN,\u0007k\\8m\t&\u001c\b/\u0019;dQ\u0016\u0014X#A>\u0011\u0005ib\u0018BA?<\u0005\u001d\u0011un\u001c7fC:\f!#^:f!>|G\u000eR5ta\u0006$8\r[3sA\u00051A(\u001b8jiz\"2BRA\u0002\u0003\u000b\t9!!\u0003\u0002\f!)ak\u0003a\u00011\"9Al\u0003I\u0001\u0002\u0004q\u0006bB3\f!\u0003\u0005\ra\u001a\u0005\b].\u0001\n\u00111\u0001q\u0011\u001dI8\u0002%AA\u0002m$2ARA\b\u0011\u001d\t\t\u0002\u0004a\u0001\u0003'\taaY8oM&<\u0007\u0003BA\u000b\u0003Ci!!a\u0006\u000b\t\u0005E\u0011\u0011\u0004\u0006\u0005\u00037\ti\"\u0001\u0005usB,7/\u00194f\u0015\t\ty\"A\u0002d_6LA!a\t\u0002\u0018\t11i\u001c8gS\u001e$2ARA\u0014\u0011\u0019\tI#\u0004a\u00011\u0006\u0011aN]\u0001\rGJ,\u0017\r^3S_V$XM\u001d\u000b\u0005\u0003_\t)\u0004E\u0002A\u0003cI1!a\r4\u0005\u0019\u0011v.\u001e;fe\"9\u0011q\u0007\bA\u0002\u0005e\u0012AB:zgR,W\u000eE\u0002i\u0003wI1!!\u0010j\u0005-\t5\r^8s'f\u001cH/Z7\u0015\u0007a\u000b\t\u0005C\u0004\u0002D=\u0001\r!!\u000f\u0002\u0007ML8/\u0001\fxSRD7+\u001e9feZL7o\u001c:TiJ\fG/Z4z)\r1\u0015\u0011\n\u0005\u0007\u0003\u0017\u0002\u0002\u0019A4\u0002\u0011M$(/\u0019;fOf\f1b^5uQJ+7/\u001b>feR\u0019a)!\u0015\t\u000bq\u000b\u0002\u0019A1\u0002\u001d]LG\u000f\u001b#jgB\fGo\u00195feR\u0019a)a\u0016\t\r\u0005e#\u00031\u0001q\u00031!\u0017n\u001d9bi\u000eDWM]%e\u000319\u0018\u000e\u001e5GC2d'-Y2l)\u0011\ty&!\u001a\u0011\u0007\u0001\u000b\t'C\u0002\u0002dM\u0012ABU8vi\u0016\u00148i\u001c8gS\u001eDq!a\u001a\u0014\u0001\u0004\ty&A\u0003pi\",'/\u0001\u0003d_BLHc\u0003$\u0002n\u0005=\u0014\u0011OA:\u0003kBqA\u0016\u000b\u0011\u0002\u0003\u0007\u0001\fC\u0004])A\u0005\t\u0019\u00010\t\u000f\u0015$\u0002\u0013!a\u0001O\"9a\u000e\u0006I\u0001\u0002\u0004\u0001\bbB=\u0015!\u0003\u0005\ra_\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\tYHK\u0002Y\u0003{Z#!a \u0011\t\u0005\u0005\u00151R\u0007\u0003\u0003\u0007SA!!\"\u0002\b\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003\u0013[\u0014AC1o]>$\u0018\r^5p]&!\u0011QRAB\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\t\u0019JK\u0002_\u0003{\nabY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0002\u001a*\u001aq-! \u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%iU\u0011\u0011q\u0014\u0016\u0004a\u0006u\u0014AD2paf$C-\u001a4bk2$H%N\u000b\u0003\u0003KS3a_A?\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u00111\u0016\t\u0005\u0003[\u000b9,\u0004\u0002\u00020*!\u0011\u0011WAZ\u0003\u0011a\u0017M\\4\u000b\u0005\u0005U\u0016\u0001\u00026bm\u0006L1A^AX\u00031\u0001(o\u001c3vGR\f%/\u001b;z\u00039\u0001(o\u001c3vGR,E.Z7f]R$B!a0\u0002FB\u0019!(!1\n\u0007\u0005\r7HA\u0002B]fD\u0001\"a2\u001d\u0003\u0003\u0005\r\u0001W\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u00055\u0007CBAh\u0003+\fy,\u0004\u0002\u0002R*\u0019\u00111[\u001e\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002X\u0006E'\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$2a_Ao\u0011%\t9MHA\u0001\u0002\u0004\ty,\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BAV\u0003GD\u0001\"a2 \u0003\u0003\u0005\r\u0001W\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001,\u0001\u0005u_N#(/\u001b8h)\t\tY+\u0001\u0004fcV\fGn\u001d\u000b\u0004w\u0006E\b\"CAdE\u0005\u0005\t\u0019AA`Q\u001d\u0001\u0011Q_A~\u0003{\u00042AOA|\u0013\r\tIp\u000f\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\u0014'6\fG\u000e\\3ti6\u000b\u0017\u000e\u001c2pqB{w\u000e\u001c\t\u0003\u0001\u0012\u001aR\u0001\nB\u0003\u0005#\u0001\"Ba\u0002\u0003\u000easv\r]>G\u001b\t\u0011IAC\u0002\u0003\fm\nqA];oi&lW-\u0003\u0003\u0003\u0010\t%!!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8okA!!1\u0003B\r\u001b\t\u0011)B\u0003\u0003\u0003\u0018\u0005M\u0016AA5p\u0013\r!&Q\u0003\u000b\u0003\u0005\u0003\tQ!\u00199qYf$2B\u0012B\u0011\u0005G\u0011)Ca\n\u0003*!)ak\na\u00011\"9Al\nI\u0001\u0002\u0004q\u0006bB3(!\u0003\u0005\ra\u001a\u0005\b]\u001e\u0002\n\u00111\u0001q\u0011\u001dIx\u0005%AA\u0002m\fq\"\u00199qYf$C-\u001a4bk2$HEM\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%g\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$C'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00136\u0003\u001d)h.\u00199qYf$BAa\u000e\u0003@A!!h\u0018B\u001d!!Q$1\b-_OB\\\u0018b\u0001B\u001fw\t1A+\u001e9mKVB\u0001B!\u0011-\u0003\u0003\u0005\rAR\u0001\u0004q\u0012\u0002\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$#'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HeM\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001b\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00136\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011y\u0005\u0005\u0003\u0002.\nE\u0013\u0002\u0002B*\u0003_\u0013aa\u00142kK\u000e$\b")
public final class SmallestMailboxPool
implements PoolOverrideUnsetConfig<SmallestMailboxPool>,
Product {
    private static final long serialVersionUID = 1L;
    private final int nrOfInstances;
    private final Option<Resizer> resizer;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;
    private final boolean usePoolDispatcher;

    public static boolean $lessinit$greater$default$5() {
        return SmallestMailboxPool$.MODULE$.$lessinit$greater$default$5();
    }

    public static String $lessinit$greater$default$4() {
        return SmallestMailboxPool$.MODULE$.$lessinit$greater$default$4();
    }

    public static SupervisorStrategy $lessinit$greater$default$3() {
        return SmallestMailboxPool$.MODULE$.$lessinit$greater$default$3();
    }

    public static Option<Resizer> $lessinit$greater$default$2() {
        return SmallestMailboxPool$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>> unapply(SmallestMailboxPool x$0) {
        return SmallestMailboxPool$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$5() {
        return SmallestMailboxPool$.MODULE$.apply$default$5();
    }

    public static String apply$default$4() {
        return SmallestMailboxPool$.MODULE$.apply$default$4();
    }

    public static SupervisorStrategy apply$default$3() {
        return SmallestMailboxPool$.MODULE$.apply$default$3();
    }

    public static Option<Resizer> apply$default$2() {
        return SmallestMailboxPool$.MODULE$.apply$default$2();
    }

    public static SmallestMailboxPool apply(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return SmallestMailboxPool$.MODULE$.apply(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public static Function1<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>, SmallestMailboxPool> tupled() {
        return SmallestMailboxPool$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Option<Resizer>, Function1<SupervisorStrategy, Function1<String, Function1<Object, SmallestMailboxPool>>>>> curried() {
        return SmallestMailboxPool$.MODULE$.curried();
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
        return new Router(SmallestMailboxRoutingLogic$.MODULE$.apply());
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return this.nrOfInstances();
    }

    @Override
    public SmallestMailboxPool withSupervisorStrategy(SupervisorStrategy strategy) {
        SupervisorStrategy x$1 = strategy;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        String x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$1, x$4, x$5);
    }

    @Override
    public SmallestMailboxPool withResizer(Resizer resizer) {
        Some<Resizer> x$1 = new Some<Resizer>(resizer);
        int x$2 = this.copy$default$1();
        SupervisorStrategy x$3 = this.copy$default$3();
        String x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$1, x$3, x$4, x$5);
    }

    public SmallestMailboxPool withDispatcher(String dispatcherId) {
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

    public SmallestMailboxPool copy(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new SmallestMailboxPool(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
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
        return "SmallestMailboxPool";
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
        return x$1 instanceof SmallestMailboxPool;
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
        if (!(object instanceof SmallestMailboxPool)) return false;
        boolean bl = true;
        if (!bl) return false;
        SmallestMailboxPool smallestMailboxPool = (SmallestMailboxPool)x$1;
        if (this.nrOfInstances() != smallestMailboxPool.nrOfInstances()) return false;
        if (this.usePoolDispatcher() != smallestMailboxPool.usePoolDispatcher()) return false;
        Option<Resizer> option = this.resizer();
        Option<Resizer> option2 = smallestMailboxPool.resizer();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        SupervisorStrategy supervisorStrategy = this.supervisorStrategy();
        SupervisorStrategy supervisorStrategy2 = smallestMailboxPool.supervisorStrategy();
        if (supervisorStrategy == null) {
            if (supervisorStrategy2 != null) {
                return false;
            }
        } else if (!supervisorStrategy.equals(supervisorStrategy2)) return false;
        String string = this.routerDispatcher();
        String string2 = smallestMailboxPool.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public SmallestMailboxPool(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
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

    public SmallestMailboxPool(Config config) {
        int x$1 = config.getInt("nr-of-instances");
        Option<Resizer> x$2 = Resizer$.MODULE$.fromConfig(config);
        boolean x$3 = config.hasPath("pool-dispatcher");
        SupervisorStrategy x$4 = SmallestMailboxPool$.MODULE$.$lessinit$greater$default$3();
        String x$5 = SmallestMailboxPool$.MODULE$.$lessinit$greater$default$4();
        this(x$1, x$2, x$4, x$5, x$3);
    }

    public SmallestMailboxPool(int nr) {
        this(nr, SmallestMailboxPool$.MODULE$.$lessinit$greater$default$2(), SmallestMailboxPool$.MODULE$.$lessinit$greater$default$3(), SmallestMailboxPool$.MODULE$.$lessinit$greater$default$4(), SmallestMailboxPool$.MODULE$.$lessinit$greater$default$5());
    }
}

