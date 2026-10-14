/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.routing.BroadcastPool$;
import akka.routing.BroadcastRoutingLogic$;
import akka.routing.Pool;
import akka.routing.PoolOverrideUnsetConfig;
import akka.routing.Resizer;
import akka.routing.Resizer$;
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

@ScalaSignature(bytes="\u0006\u0005\tUc\u0001\u0002\u001a4\u0005bB\u0001B\u0016\u0001\u0003\u0016\u0004%\ta\u0016\u0005\t7\u0002\u0011\t\u0012)A\u00051\"AA\f\u0001BK\u0002\u0013\u0005S\f\u0003\u0005e\u0001\tE\t\u0015!\u0003_\u0011!)\u0007A!f\u0001\n\u00032\u0007\u0002C7\u0001\u0005#\u0005\u000b\u0011B4\t\u00119\u0004!Q3A\u0005B=D\u0001\u0002\u001f\u0001\u0003\u0012\u0003\u0006I\u0001\u001d\u0005\ts\u0002\u0011)\u001a!C!u\"Aa\u0010\u0001B\tB\u0003%1\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0011\u0011\u0001\u0005\u0007\u007f\u0002!\t!!\u0004\t\r}\u0004A\u0011AA\u0013\u0011\u001d\tY\u0003\u0001C!\u0003[AaA\u0016\u0001\u0005B\u0005}\u0002bBA#\u0001\u0011\u0005\u0011q\t\u0005\b\u0003\u001b\u0002A\u0011AA(\u0011\u001d\t\u0019\u0006\u0001C\u0001\u0003+Bq!a\u0017\u0001\t\u0003\ni\u0006C\u0005\u0002j\u0001\t\t\u0011\"\u0001\u0002l!I\u0011q\u000f\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u0010\u0005\n\u0003\u001f\u0003\u0011\u0013!C\u0001\u0003#C\u0011\"!&\u0001#\u0003%\t!a&\t\u0013\u0005m\u0005!%A\u0005\u0002\u0005u\u0005\"CAQ\u0001E\u0005I\u0011AAR\u0011%\t9\u000bAA\u0001\n\u0003\nI\u000b\u0003\u0005\u0002:\u0002\t\t\u0011\"\u0001X\u0011%\tY\fAA\u0001\n\u0003\ti\fC\u0005\u0002J\u0002\t\t\u0011\"\u0011\u0002L\"I\u0011\u0011\u001c\u0001\u0002\u0002\u0013\u0005\u00111\u001c\u0005\n\u0003?\u0004\u0011\u0011!C!\u0003CD\u0011\"!:\u0001\u0003\u0003%\t%a:\t\u0013\u0005%\b!!A\u0005B\u0005-\b\"CAw\u0001\u0005\u0005I\u0011IAx\u000f%\typMA\u0001\u0012\u0003\u0011\tA\u0002\u00053g\u0005\u0005\t\u0012\u0001B\u0002\u0011\u0019yH\u0005\"\u0001\u0003\u001c!I\u0011\u0011\u001e\u0013\u0002\u0002\u0013\u0015\u00131\u001e\u0005\n\u0005;!\u0013\u0011!CA\u0005?A\u0011Ba\u000b%#\u0003%\t!!%\t\u0013\t5B%%A\u0005\u0002\u0005]\u0005\"\u0003B\u0018IE\u0005I\u0011AAO\u0011%\u0011\t\u0004JI\u0001\n\u0003\t\u0019\u000bC\u0005\u00034\u0011\n\t\u0011\"!\u00036!I!1\t\u0013\u0012\u0002\u0013\u0005\u0011\u0011\u0013\u0005\n\u0005\u000b\"\u0013\u0013!C\u0001\u0003/C\u0011Ba\u0012%#\u0003%\t!!(\t\u0013\t%C%%A\u0005\u0002\u0005\r\u0006\"\u0003B&I\u0005\u0005I\u0011\u0002B'\u00055\u0011%o\\1eG\u0006\u001cH\u000fU8pY*\u0011A'N\u0001\be>,H/\u001b8h\u0015\u00051\u0014\u0001B1lW\u0006\u001c\u0001a\u0005\u0004\u0001s}\u001auI\u0013\t\u0003uuj\u0011a\u000f\u0006\u0002y\u0005)1oY1mC&\u0011ah\u000f\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0001\u000bU\"A\u001a\n\u0005\t\u001b$\u0001\u0002)p_2\u00042\u0001\u0011#G\u0013\t)5GA\fQ_>dwJ^3se&$W-\u00168tKR\u001cuN\u001c4jOB\u0011\u0001\t\u0001\t\u0003u!K!!S\u001e\u0003\u000fA\u0013x\u000eZ;diB\u00111j\u0015\b\u0003\u0019Fs!!\u0014)\u000e\u00039S!aT\u001c\u0002\rq\u0012xn\u001c;?\u0013\u0005a\u0014B\u0001*<\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001V+\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005I[\u0014!\u00048s\u001f\u001aLen\u001d;b]\u000e,7/F\u0001Y!\tQ\u0014,\u0003\u0002[w\t\u0019\u0011J\u001c;\u0002\u001d9\u0014xJZ%ogR\fgnY3tA\u00059!/Z:ju\u0016\u0014X#\u00010\u0011\u0007iz\u0016-\u0003\u0002aw\t1q\n\u001d;j_:\u0004\"\u0001\u00112\n\u0005\r\u001c$a\u0002*fg&TXM]\u0001\te\u0016\u001c\u0018N_3sA\u0005\u00112/\u001e9feZL7o\u001c:TiJ\fG/Z4z+\u00059\u0007C\u00015l\u001b\u0005I'B\u000166\u0003\u0015\t7\r^8s\u0013\ta\u0017N\u0001\nTkB,'O^5t_J\u001cFO]1uK\u001eL\u0018aE:va\u0016\u0014h/[:peN#(/\u0019;fOf\u0004\u0013\u0001\u0005:pkR,'\u000fR5ta\u0006$8\r[3s+\u0005\u0001\bCA9v\u001d\t\u00118\u000f\u0005\u0002Nw%\u0011AoO\u0001\u0007!J,G-\u001a4\n\u0005Y<(AB*ue&twM\u0003\u0002uw\u0005\t\"o\\;uKJ$\u0015n\u001d9bi\u000eDWM\u001d\u0011\u0002#U\u001cX\rU8pY\u0012K7\u000f]1uG\",'/F\u0001|!\tQD0\u0003\u0002~w\t9!i\\8mK\u0006t\u0017AE;tKB{w\u000e\u001c#jgB\fGo\u00195fe\u0002\na\u0001P5oSRtDc\u0003$\u0002\u0004\u0005\u0015\u0011qAA\u0005\u0003\u0017AQAV\u0006A\u0002aCq\u0001X\u0006\u0011\u0002\u0003\u0007a\fC\u0004f\u0017A\u0005\t\u0019A4\t\u000f9\\\u0001\u0013!a\u0001a\"9\u0011p\u0003I\u0001\u0002\u0004YHc\u0001$\u0002\u0010!9\u0011\u0011\u0003\u0007A\u0002\u0005M\u0011AB2p]\u001aLw\r\u0005\u0003\u0002\u0016\u0005\u0005RBAA\f\u0015\u0011\t\t\"!\u0007\u000b\t\u0005m\u0011QD\u0001\tif\u0004Xm]1gK*\u0011\u0011qD\u0001\u0004G>l\u0017\u0002BA\u0012\u0003/\u0011aaQ8oM&<Gc\u0001$\u0002(!1\u0011\u0011F\u0007A\u0002a\u000b!A\u001c:\u0002\u0019\r\u0014X-\u0019;f%>,H/\u001a:\u0015\t\u0005=\u0012Q\u0007\t\u0004\u0001\u0006E\u0012bAA\u001ag\t1!k\\;uKJDq!a\u000e\u000f\u0001\u0004\tI$\u0001\u0004tsN$X-\u001c\t\u0004Q\u0006m\u0012bAA\u001fS\nY\u0011i\u0019;peNK8\u000f^3n)\rA\u0016\u0011\t\u0005\b\u0003\u0007z\u0001\u0019AA\u001d\u0003\r\u0019\u0018p]\u0001\u0017o&$\bnU;qKJ4\u0018n]8s'R\u0014\u0018\r^3hsR\u0019a)!\u0013\t\r\u0005-\u0003\u00031\u0001h\u0003!\u0019HO]1uK\u001eL\u0018aC<ji\"\u0014Vm]5{KJ$2ARA)\u0011\u0015a\u0016\u00031\u0001b\u000399\u0018\u000e\u001e5ESN\u0004\u0018\r^2iKJ$2ARA,\u0011\u0019\tIF\u0005a\u0001a\u0006aA-[:qCR\u001c\u0007.\u001a:JI\u0006aq/\u001b;i\r\u0006dGNY1dWR!\u0011qLA3!\r\u0001\u0015\u0011M\u0005\u0004\u0003G\u001a$\u0001\u0004*pkR,'oQ8oM&<\u0007bBA4'\u0001\u0007\u0011qL\u0001\u0006_RDWM]\u0001\u0005G>\u0004\u0018\u0010F\u0006G\u0003[\ny'!\u001d\u0002t\u0005U\u0004b\u0002,\u0015!\u0003\u0005\r\u0001\u0017\u0005\b9R\u0001\n\u00111\u0001_\u0011\u001d)G\u0003%AA\u0002\u001dDqA\u001c\u000b\u0011\u0002\u0003\u0007\u0001\u000fC\u0004z)A\u0005\t\u0019A>\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u00111\u0010\u0016\u00041\u0006u4FAA@!\u0011\t\t)a#\u000e\u0005\u0005\r%\u0002BAC\u0003\u000f\u000b\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005%5(\u0001\u0006b]:|G/\u0019;j_:LA!!$\u0002\u0004\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u00111\u0013\u0016\u0004=\u0006u\u0014AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u00033S3aZA?\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"!a(+\u0007A\fi(\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016\u0005\u0005\u0015&fA>\u0002~\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!a+\u0011\t\u00055\u0016qW\u0007\u0003\u0003_SA!!-\u00024\u0006!A.\u00198h\u0015\t\t),\u0001\u0003kCZ\f\u0017b\u0001<\u00020\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BA`\u0003\u000b\u00042AOAa\u0013\r\t\u0019m\u000f\u0002\u0004\u0003:L\b\u0002CAd9\u0005\u0005\t\u0019\u0001-\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\ti\r\u0005\u0004\u0002P\u0006U\u0017qX\u0007\u0003\u0003#T1!a5<\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003/\f\tN\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dGcA>\u0002^\"I\u0011q\u0019\u0010\u0002\u0002\u0003\u0007\u0011qX\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0002,\u0006\r\b\u0002CAd?\u0005\u0005\t\u0019\u0001-\u0002\u0011!\f7\u000f[\"pI\u0016$\u0012\u0001W\u0001\ti>\u001cFO]5oOR\u0011\u00111V\u0001\u0007KF,\u0018\r\\:\u0015\u0007m\f\t\u0010C\u0005\u0002H\n\n\t\u00111\u0001\u0002@\":\u0001!!>\u0002|\u0006u\bc\u0001\u001e\u0002x&\u0019\u0011\u0011`\u001e\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001\u0002\u001b\t\u0013x.\u00193dCN$\bk\\8m!\t\u0001EeE\u0003%\u0005\u000b\u0011\t\u0002\u0005\u0006\u0003\b\t5\u0001LX4qw\u001ak!A!\u0003\u000b\u0007\t-1(A\u0004sk:$\u0018.\\3\n\t\t=!\u0011\u0002\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:,\u0004\u0003\u0002B\n\u00053i!A!\u0006\u000b\t\t]\u00111W\u0001\u0003S>L1\u0001\u0016B\u000b)\t\u0011\t!A\u0003baBd\u0017\u0010F\u0006G\u0005C\u0011\u0019C!\n\u0003(\t%\u0002\"\u0002,(\u0001\u0004A\u0006b\u0002/(!\u0003\u0005\rA\u0018\u0005\bK\u001e\u0002\n\u00111\u0001h\u0011\u001dqw\u0005%AA\u0002ADq!_\u0014\u0011\u0002\u0003\u000710A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u001a\u0014aD1qa2LH\u0005Z3gCVdG\u000f\n\u001b\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIU\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u00038\t}\u0002\u0003\u0002\u001e`\u0005s\u0001\u0002B\u000fB\u001e1z;\u0007o_\u0005\u0004\u0005{Y$A\u0002+va2,W\u0007\u0003\u0005\u0003B1\n\t\u00111\u0001G\u0003\rAH\u0005M\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000f\n\u001a\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00134\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%i\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIU\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"Aa\u0014\u0011\t\u00055&\u0011K\u0005\u0005\u0005'\nyK\u0001\u0004PE*,7\r\u001e")
public final class BroadcastPool
implements PoolOverrideUnsetConfig<BroadcastPool>,
Product {
    private static final long serialVersionUID = 1L;
    private final int nrOfInstances;
    private final Option<Resizer> resizer;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;
    private final boolean usePoolDispatcher;

    public static boolean $lessinit$greater$default$5() {
        return BroadcastPool$.MODULE$.$lessinit$greater$default$5();
    }

    public static String $lessinit$greater$default$4() {
        return BroadcastPool$.MODULE$.$lessinit$greater$default$4();
    }

    public static SupervisorStrategy $lessinit$greater$default$3() {
        return BroadcastPool$.MODULE$.$lessinit$greater$default$3();
    }

    public static Option<Resizer> $lessinit$greater$default$2() {
        return BroadcastPool$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>> unapply(BroadcastPool x$0) {
        return BroadcastPool$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$5() {
        return BroadcastPool$.MODULE$.apply$default$5();
    }

    public static String apply$default$4() {
        return BroadcastPool$.MODULE$.apply$default$4();
    }

    public static SupervisorStrategy apply$default$3() {
        return BroadcastPool$.MODULE$.apply$default$3();
    }

    public static Option<Resizer> apply$default$2() {
        return BroadcastPool$.MODULE$.apply$default$2();
    }

    public static BroadcastPool apply(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return BroadcastPool$.MODULE$.apply(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public static Function1<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>, BroadcastPool> tupled() {
        return BroadcastPool$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Option<Resizer>, Function1<SupervisorStrategy, Function1<String, Function1<Object, BroadcastPool>>>>> curried() {
        return BroadcastPool$.MODULE$.curried();
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
        return new Router(BroadcastRoutingLogic$.MODULE$.apply());
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return this.nrOfInstances();
    }

    @Override
    public BroadcastPool withSupervisorStrategy(SupervisorStrategy strategy) {
        SupervisorStrategy x$1 = strategy;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        String x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$1, x$4, x$5);
    }

    @Override
    public BroadcastPool withResizer(Resizer resizer) {
        Some<Resizer> x$1 = new Some<Resizer>(resizer);
        int x$2 = this.copy$default$1();
        SupervisorStrategy x$3 = this.copy$default$3();
        String x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$1, x$3, x$4, x$5);
    }

    public BroadcastPool withDispatcher(String dispatcherId) {
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

    public BroadcastPool copy(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new BroadcastPool(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
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
        return "BroadcastPool";
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
        return x$1 instanceof BroadcastPool;
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
        if (!(object instanceof BroadcastPool)) return false;
        boolean bl = true;
        if (!bl) return false;
        BroadcastPool broadcastPool = (BroadcastPool)x$1;
        if (this.nrOfInstances() != broadcastPool.nrOfInstances()) return false;
        if (this.usePoolDispatcher() != broadcastPool.usePoolDispatcher()) return false;
        Option<Resizer> option = this.resizer();
        Option<Resizer> option2 = broadcastPool.resizer();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        SupervisorStrategy supervisorStrategy = this.supervisorStrategy();
        SupervisorStrategy supervisorStrategy2 = broadcastPool.supervisorStrategy();
        if (supervisorStrategy == null) {
            if (supervisorStrategy2 != null) {
                return false;
            }
        } else if (!supervisorStrategy.equals(supervisorStrategy2)) return false;
        String string = this.routerDispatcher();
        String string2 = broadcastPool.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public BroadcastPool(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
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

    public BroadcastPool(Config config) {
        int x$1 = config.getInt("nr-of-instances");
        Option<Resizer> x$2 = Resizer$.MODULE$.fromConfig(config);
        boolean x$3 = config.hasPath("pool-dispatcher");
        SupervisorStrategy x$4 = BroadcastPool$.MODULE$.$lessinit$greater$default$3();
        String x$5 = BroadcastPool$.MODULE$.$lessinit$greater$default$4();
        this(x$1, x$2, x$4, x$5, x$3);
    }

    public BroadcastPool(int nr) {
        this(nr, BroadcastPool$.MODULE$.$lessinit$greater$default$2(), BroadcastPool$.MODULE$.$lessinit$greater$default$3(), BroadcastPool$.MODULE$.$lessinit$greater$default$4(), BroadcastPool$.MODULE$.$lessinit$greater$default$5());
    }
}

