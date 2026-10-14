/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.dispatch.BalancingDispatcherConfigurator;
import akka.dispatch.Dispatchers;
import akka.routing.ActorRefRoutee;
import akka.routing.BalancingPool$;
import akka.routing.BalancingPoolDeploy$;
import akka.routing.BalancingRoutingLogic$;
import akka.routing.NoRouter$;
import akka.routing.Pool;
import akka.routing.Pool$;
import akka.routing.Resizer;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Tuple3;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tEb\u0001\u0002\u0016,\u0005BB\u0001B\u0013\u0001\u0003\u0016\u0004%\ta\u0013\u0005\t\u001f\u0002\u0011\t\u0012)A\u0005\u0019\"A\u0001\u000b\u0001BK\u0002\u0013\u0005\u0013\u000b\u0003\u0005Y\u0001\tE\t\u0015!\u0003S\u0011!I\u0006A!f\u0001\n\u0003R\u0006\u0002C2\u0001\u0005#\u0005\u000b\u0011B.\t\u000b\u0011\u0004A\u0011A3\t\u000b\u0011\u0004A\u0011\u00016\t\u000b\u0011\u0004A\u0011\u0001<\t\u000be\u0004A\u0011\t>\t\u000f\u0005\u001d\u0001\u0001\"\u0001\u0002\n!9\u0011q\u0002\u0001\u0005\u0002\u0005E\u0001B\u0002&\u0001\t\u0003\t9\u0002\u0003\u0005\u0002\u001e\u0001!\t%LA\u0010\u0011\u001d\tY\u0004\u0001C!\u0003{A\u0011\"!\u0013\u0001\u0005\u0004%\t%a\u0013\t\u0011\u0005e\u0003\u0001)A\u0005\u0003\u001bB\u0011\"a\u0017\u0001\u0003\u0003%\t!!\u0018\t\u0013\u0005\u0015\u0004!%A\u0005\u0002\u0005\u001d\u0004\"CA?\u0001E\u0005I\u0011AA@\u0011%\t\u0019\tAI\u0001\n\u0003\t)\tC\u0005\u0002\n\u0002\t\t\u0011\"\u0011\u0002\f\"A\u00111\u0014\u0001\u0002\u0002\u0013\u00051\nC\u0005\u0002\u001e\u0002\t\t\u0011\"\u0001\u0002 \"I\u00111\u0016\u0001\u0002\u0002\u0013\u0005\u0013Q\u0016\u0005\n\u0003w\u0003\u0011\u0011!C\u0001\u0003{C\u0011\"a2\u0001\u0003\u0003%\t%!3\t\u0013\u00055\u0007!!A\u0005B\u0005=\u0007\"CAi\u0001\u0005\u0005I\u0011IAj\u0011%\t)\u000eAA\u0001\n\u0003\n9nB\u0005\u0002h.\n\t\u0011#\u0001\u0002j\u001aA!fKA\u0001\u0012\u0003\tY\u000f\u0003\u0004eA\u0011\u0005!1\u0001\u0005\n\u0003#\u0004\u0013\u0011!C#\u0003'D\u0011B!\u0002!\u0003\u0003%\tIa\u0002\t\u0013\t=\u0001%%A\u0005\u0002\u0005}\u0004\"\u0003B\tAE\u0005I\u0011AAC\u0011%\u0011\u0019\u0002IA\u0001\n\u0003\u0013)\u0002C\u0005\u0003$\u0001\n\n\u0011\"\u0001\u0002\u0000!I!Q\u0005\u0011\u0012\u0002\u0013\u0005\u0011Q\u0011\u0005\n\u0005O\u0001\u0013\u0011!C\u0005\u0005S\u0011QBQ1mC:\u001c\u0017N\\4Q_>d'B\u0001\u0017.\u0003\u001d\u0011x.\u001e;j]\u001eT\u0011AL\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u000b\u0001\ttg\u000f \u0011\u0005I*T\"A\u001a\u000b\u0003Q\nQa]2bY\u0006L!AN\u001a\u0003\r\u0005s\u0017PU3g!\tA\u0014(D\u0001,\u0013\tQ4F\u0001\u0003Q_>d\u0007C\u0001\u001a=\u0013\ti4GA\u0004Qe>$Wo\u0019;\u0011\u0005}:eB\u0001!F\u001d\t\tE)D\u0001C\u0015\t\u0019u&\u0001\u0004=e>|GOP\u0005\u0002i%\u0011aiM\u0001\ba\u0006\u001c7.Y4f\u0013\tA\u0015J\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002Gg\u0005iaN](g\u0013:\u001cH/\u00198dKN,\u0012\u0001\u0014\t\u0003e5K!AT\u001a\u0003\u0007%sG/\u0001\boe>3\u0017J\\:uC:\u001cWm\u001d\u0011\u0002%M,\b/\u001a:wSN|'o\u0015;sCR,w-_\u000b\u0002%B\u00111KV\u0007\u0002)*\u0011Q+L\u0001\u0006C\u000e$xN]\u0005\u0003/R\u0013!cU;qKJ4\u0018n]8s'R\u0014\u0018\r^3hs\u0006\u00192/\u001e9feZL7o\u001c:TiJ\fG/Z4zA\u0005\u0001\"o\\;uKJ$\u0015n\u001d9bi\u000eDWM]\u000b\u00027B\u0011A\f\u0019\b\u0003;z\u0003\"!Q\u001a\n\u0005}\u001b\u0014A\u0002)sK\u0012,g-\u0003\u0002bE\n11\u000b\u001e:j]\u001eT!aX\u001a\u0002#I|W\u000f^3s\t&\u001c\b/\u0019;dQ\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0005M\u001eD\u0017\u000e\u0005\u00029\u0001!)!j\u0002a\u0001\u0019\"9\u0001k\u0002I\u0001\u0002\u0004\u0011\u0006bB-\b!\u0003\u0005\ra\u0017\u000b\u0003M.DQ\u0001\u001c\u0005A\u00025\faaY8oM&<\u0007C\u00018u\u001b\u0005y'B\u00017q\u0015\t\t(/\u0001\u0005usB,7/\u00194f\u0015\u0005\u0019\u0018aA2p[&\u0011Qo\u001c\u0002\u0007\u0007>tg-[4\u0015\u0005\u0019<\b\"\u0002=\n\u0001\u0004a\u0015A\u00018s\u00031\u0019'/Z1uKJ{W\u000f^3s)\tYh\u0010\u0005\u00029y&\u0011Qp\u000b\u0002\u0007%>,H/\u001a:\t\r}T\u0001\u0019AA\u0001\u0003\u0019\u0019\u0018p\u001d;f[B\u00191+a\u0001\n\u0007\u0005\u0015AKA\u0006BGR|'oU=ti\u0016l\u0017AF<ji\"\u001cV\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0015\u0007\u0019\fY\u0001\u0003\u0004\u0002\u000e-\u0001\rAU\u0001\tgR\u0014\u0018\r^3hs\u0006qq/\u001b;i\t&\u001c\b/\u0019;dQ\u0016\u0014Hc\u00014\u0002\u0014!1\u0011Q\u0003\u0007A\u0002m\u000bA\u0002Z5ta\u0006$8\r[3s\u0013\u0012$2\u0001TA\r\u0011\u001d\tY\"\u0004a\u0001\u0003\u0003\t1a]=t\u0003%qWm\u001e*pkR,W\r\u0006\u0004\u0002\"\u0005\u001d\u0012\u0011\u0007\t\u0004q\u0005\r\u0012bAA\u0013W\t1!k\\;uK\u0016Dq!!\u000b\u000f\u0001\u0004\tY#A\u0006s_V$X-\u001a)s_B\u001c\bcA*\u0002.%\u0019\u0011q\u0006+\u0003\u000bA\u0013x\u000e]:\t\u000f\u0005Mb\u00021\u0001\u00026\u000591m\u001c8uKb$\bcA*\u00028%\u0019\u0011\u0011\b+\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;\u0002\u0019]LG\u000f\u001b$bY2\u0014\u0017mY6\u0015\t\u0005}\u0012Q\t\t\u0004q\u0005\u0005\u0013bAA\"W\ta!k\\;uKJ\u001cuN\u001c4jO\"9\u0011qI\bA\u0002\u0005}\u0012!B8uQ\u0016\u0014\u0018a\u0002:fg&TXM]\u000b\u0003\u0003\u001b\u0002RAMA(\u0003'J1!!\u00154\u0005\u0019y\u0005\u000f^5p]B\u0019\u0001(!\u0016\n\u0007\u0005]3FA\u0004SKNL'0\u001a:\u0002\u0011I,7/\u001b>fe\u0002\nAaY8qsR9a-a\u0018\u0002b\u0005\r\u0004b\u0002&\u0013!\u0003\u0005\r\u0001\u0014\u0005\b!J\u0001\n\u00111\u0001S\u0011\u001dI&\u0003%AA\u0002m\u000babY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002j)\u001aA*a\u001b,\u0005\u00055\u0004\u0003BA8\u0003sj!!!\u001d\u000b\t\u0005M\u0014QO\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\u001e4\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003w\n\tHA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002\u0002*\u001a!+a\u001b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011\u0011q\u0011\u0016\u00047\u0006-\u0014!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002\u000eB!\u0011qRAM\u001b\t\t\tJ\u0003\u0003\u0002\u0014\u0006U\u0015\u0001\u00027b]\u001eT!!a&\u0002\t)\fg/Y\u0005\u0004C\u0006E\u0015\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003C\u000b9\u000bE\u00023\u0003GK1!!*4\u0005\r\te.\u001f\u0005\t\u0003SC\u0012\u0011!a\u0001\u0019\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a,\u0011\r\u0005E\u0016qWAQ\u001b\t\t\u0019LC\u0002\u00026N\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\tI,a-\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003\u007f\u000b)\rE\u00023\u0003\u0003L1!a14\u0005\u001d\u0011un\u001c7fC:D\u0011\"!+\u001b\u0003\u0003\u0005\r!!)\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003\u001b\u000bY\r\u0003\u0005\u0002*n\t\t\u00111\u0001M\u0003!A\u0017m\u001d5D_\u0012,G#\u0001'\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!$\u0002\r\u0015\fX/\u00197t)\u0011\ty,!7\t\u0013\u0005%f$!AA\u0002\u0005\u0005\u0006f\u0002\u0001\u0002^\u0006\r\u0018Q\u001d\t\u0004e\u0005}\u0017bAAqg\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005i!)\u00197b]\u000eLgn\u001a)p_2\u0004\"\u0001\u000f\u0011\u0014\u000b\u0001\ni/!?\u0011\u0011\u0005=\u0018Q\u001f'S7\u001al!!!=\u000b\u0007\u0005M8'A\u0004sk:$\u0018.\\3\n\t\u0005]\u0018\u0011\u001f\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\u001c\u0004\u0003BA~\u0005\u0003i!!!@\u000b\t\u0005}\u0018QS\u0001\u0003S>L1\u0001SA\u007f)\t\tI/A\u0003baBd\u0017\u0010F\u0004g\u0005\u0013\u0011YA!\u0004\t\u000b)\u001b\u0003\u0019\u0001'\t\u000fA\u001b\u0003\u0013!a\u0001%\"9\u0011l\tI\u0001\u0002\u0004Y\u0016aD1qa2LH\u0005Z3gCVdG\u000f\n\u001a\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIM\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003\u0018\t}\u0001#\u0002\u001a\u0002P\te\u0001C\u0002\u001a\u0003\u001c1\u00136,C\u0002\u0003\u001eM\u0012a\u0001V;qY\u0016\u001c\u0004\u0002\u0003B\u0011M\u0005\u0005\t\u0019\u00014\u0002\u0007a$\u0003'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001a\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t-\u0002\u0003BAH\u0005[IAAa\f\u0002\u0012\n1qJ\u00196fGR\u0004")
public final class BalancingPool
implements Pool,
Product {
    private static final long serialVersionUID = 1L;
    private final int nrOfInstances;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;
    private final Option<Resizer> resizer;

    public static String $lessinit$greater$default$3() {
        return BalancingPool$.MODULE$.$lessinit$greater$default$3();
    }

    public static SupervisorStrategy $lessinit$greater$default$2() {
        return BalancingPool$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple3<Object, SupervisorStrategy, String>> unapply(BalancingPool x$0) {
        return BalancingPool$.MODULE$.unapply(x$0);
    }

    public static String apply$default$3() {
        return BalancingPool$.MODULE$.apply$default$3();
    }

    public static SupervisorStrategy apply$default$2() {
        return BalancingPool$.MODULE$.apply$default$2();
    }

    public static BalancingPool apply(int nrOfInstances, SupervisorStrategy supervisorStrategy, String routerDispatcher) {
        return BalancingPool$.MODULE$.apply(nrOfInstances, supervisorStrategy, routerDispatcher);
    }

    public static Function1<Tuple3<Object, SupervisorStrategy, String>, BalancingPool> tupled() {
        return BalancingPool$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<SupervisorStrategy, Function1<String, BalancingPool>>> curried() {
        return BalancingPool$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public boolean usePoolDispatcher() {
        return Pool.usePoolDispatcher$(this);
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
    public SupervisorStrategy supervisorStrategy() {
        return this.supervisorStrategy;
    }

    @Override
    public String routerDispatcher() {
        return this.routerDispatcher;
    }

    @Override
    public Router createRouter(ActorSystem system) {
        return new Router(BalancingRoutingLogic$.MODULE$.apply());
    }

    public BalancingPool withSupervisorStrategy(SupervisorStrategy strategy) {
        SupervisorStrategy x$1 = strategy;
        int x$2 = this.copy$default$1();
        String x$3 = this.copy$default$3();
        return this.copy(x$2, x$1, x$3);
    }

    public BalancingPool withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        int x$2 = this.copy$default$1();
        SupervisorStrategy x$3 = this.copy$default$2();
        return this.copy(x$2, x$3, x$1);
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return this.nrOfInstances();
    }

    @Override
    public Routee newRoutee(Props routeeProps, ActorContext context) {
        Serializable serializable;
        String rawDeployPath = ((IterableOnceOps)context.self().path().elements().drop(1)).mkString("/", "/", "");
        String deployPath = BalancingPoolDeploy$.MODULE$.invalidConfigKeyChars().foldLeft(rawDeployPath, (Function2<String, Object, String> & Serializable)(replaced, c) -> replaced.replace(BoxesRunTime.unboxToChar(c), '_'));
        String dispatcherId = new StringBuilder(14).append("BalancingPool-").append(deployPath).toString();
        if (!BalancingPool.dispatchers$1(context).hasDispatcher(dispatcherId)) {
            String deployDispatcherConfigPath = new StringBuilder(38).append("akka.actor.deployment.").append(deployPath).append(".pool-dispatcher").toString();
            Config systemConfig = context.system().settings().config();
            Config dispatcherConfig = context.system().dispatchers().config(dispatcherId, systemConfig.hasPath(deployDispatcherConfigPath) ? systemConfig.getConfig(deployDispatcherConfigPath) : ConfigFactory.empty());
            serializable = BoxesRunTime.boxToBoolean(BalancingPool.dispatchers$1(context).registerConfigurator(dispatcherId, new BalancingDispatcherConfigurator(dispatcherConfig, BalancingPool.dispatchers$1(context).prerequisites())));
        } else {
            serializable = BoxedUnit.UNIT;
        }
        Props routeePropsWithDispatcher = routeeProps.withDispatcher(dispatcherId);
        return new ActorRefRoutee(context.actorOf(routeePropsWithDispatcher));
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        RouterConfig routerConfig = other;
        NoRouter$ noRouter$ = NoRouter$.MODULE$;
        if (!(routerConfig != null ? !routerConfig.equals(noRouter$) : noRouter$ != null)) {
            return this;
        }
        RouterConfig routerConfig2 = other;
        if (routerConfig2 instanceof Pool) {
            Pool pool = (Pool)routerConfig2;
            if (this.supervisorStrategy() == Pool$.MODULE$.defaultSupervisorStrategy() && pool.supervisorStrategy() != Pool$.MODULE$.defaultSupervisorStrategy()) {
                return this.withSupervisorStrategy(pool.supervisorStrategy());
            }
            return this;
        }
        return this;
    }

    @Override
    public Option<Resizer> resizer() {
        return this.resizer;
    }

    public BalancingPool copy(int nrOfInstances, SupervisorStrategy supervisorStrategy, String routerDispatcher) {
        return new BalancingPool(nrOfInstances, supervisorStrategy, routerDispatcher);
    }

    public int copy$default$1() {
        return this.nrOfInstances();
    }

    public SupervisorStrategy copy$default$2() {
        return this.supervisorStrategy();
    }

    public String copy$default$3() {
        return this.routerDispatcher();
    }

    @Override
    public String productPrefix() {
        return "BalancingPool";
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
                return BoxesRunTime.boxToInteger(this.nrOfInstances());
            }
            case 1: {
                return this.supervisorStrategy();
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
        return x$1 instanceof BalancingPool;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "nrOfInstances";
            }
            case 1: {
                return "supervisorStrategy";
            }
            case 2: {
                return "routerDispatcher";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.nrOfInstances());
        n = Statics.mix(n, Statics.anyHash(this.supervisorStrategy()));
        n = Statics.mix(n, Statics.anyHash(this.routerDispatcher()));
        return Statics.finalizeHash(n, 3);
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
        if (!(object instanceof BalancingPool)) return false;
        boolean bl = true;
        if (!bl) return false;
        BalancingPool balancingPool = (BalancingPool)x$1;
        if (this.nrOfInstances() != balancingPool.nrOfInstances()) return false;
        SupervisorStrategy supervisorStrategy = this.supervisorStrategy();
        SupervisorStrategy supervisorStrategy2 = balancingPool.supervisorStrategy();
        if (supervisorStrategy == null) {
            if (supervisorStrategy2 != null) {
                return false;
            }
        } else if (!supervisorStrategy.equals(supervisorStrategy2)) return false;
        String string = this.routerDispatcher();
        String string2 = balancingPool.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    private static final Dispatchers dispatchers$1(ActorContext context$1) {
        return context$1.system().dispatchers();
    }

    public BalancingPool(int nrOfInstances, SupervisorStrategy supervisorStrategy, String routerDispatcher) {
        this.nrOfInstances = nrOfInstances;
        this.supervisorStrategy = supervisorStrategy;
        this.routerDispatcher = routerDispatcher;
        RouterConfig.$init$(this);
        Pool.$init$(this);
        Product.$init$(this);
        this.resizer = None$.MODULE$;
    }

    public BalancingPool(Config config) {
        this(config.getInt("nr-of-instances"), BalancingPool$.MODULE$.$lessinit$greater$default$2(), BalancingPool$.MODULE$.$lessinit$greater$default$3());
    }

    public BalancingPool(int nr) {
        this(nr, BalancingPool$.MODULE$.$lessinit$greater$default$2(), BalancingPool$.MODULE$.$lessinit$greater$default$3());
    }
}

