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
import akka.routing.RandomPool$;
import akka.routing.RandomRoutingLogic$;
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

@ScalaSignature(bytes="\u0006\u0005\tUc\u0001\u0002\u001a4\u0005bB\u0001B\u0016\u0001\u0003\u0016\u0004%\ta\u0016\u0005\t7\u0002\u0011\t\u0012)A\u00051\"AA\f\u0001BK\u0002\u0013\u0005S\f\u0003\u0005e\u0001\tE\t\u0015!\u0003_\u0011!)\u0007A!f\u0001\n\u00032\u0007\u0002C7\u0001\u0005#\u0005\u000b\u0011B4\t\u00119\u0004!Q3A\u0005B=D\u0001\u0002\u001f\u0001\u0003\u0012\u0003\u0006I\u0001\u001d\u0005\ts\u0002\u0011)\u001a!C!u\"Aa\u0010\u0001B\tB\u0003%1\u0010\u0003\u0004\u0000\u0001\u0011\u0005\u0011\u0011\u0001\u0005\u0007\u007f\u0002!\t!!\u0004\t\r}\u0004A\u0011AA\u0013\u0011\u001d\tY\u0003\u0001C!\u0003[AaA\u0016\u0001\u0005B\u0005}\u0002bBA#\u0001\u0011\u0005\u0011q\t\u0005\b\u0003\u001b\u0002A\u0011AA(\u0011\u001d\t\u0019\u0006\u0001C\u0001\u0003+Bq!a\u0017\u0001\t\u0003\ni\u0006C\u0005\u0002j\u0001\t\t\u0011\"\u0001\u0002l!I\u0011q\u000f\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u0010\u0005\n\u0003\u001f\u0003\u0011\u0013!C\u0001\u0003#C\u0011\"!&\u0001#\u0003%\t!a&\t\u0013\u0005m\u0005!%A\u0005\u0002\u0005u\u0005\"CAQ\u0001E\u0005I\u0011AAR\u0011%\t9\u000bAA\u0001\n\u0003\nI\u000b\u0003\u0005\u0002:\u0002\t\t\u0011\"\u0001X\u0011%\tY\fAA\u0001\n\u0003\ti\fC\u0005\u0002J\u0002\t\t\u0011\"\u0011\u0002L\"I\u0011\u0011\u001c\u0001\u0002\u0002\u0013\u0005\u00111\u001c\u0005\n\u0003?\u0004\u0011\u0011!C!\u0003CD\u0011\"!:\u0001\u0003\u0003%\t%a:\t\u0013\u0005%\b!!A\u0005B\u0005-\b\"CAw\u0001\u0005\u0005I\u0011IAx\u000f%\typMA\u0001\u0012\u0003\u0011\tA\u0002\u00053g\u0005\u0005\t\u0012\u0001B\u0002\u0011\u0019yH\u0005\"\u0001\u0003\u001c!I\u0011\u0011\u001e\u0013\u0002\u0002\u0013\u0015\u00131\u001e\u0005\n\u0005;!\u0013\u0011!CA\u0005?A\u0011Ba\u000b%#\u0003%\t!!%\t\u0013\t5B%%A\u0005\u0002\u0005]\u0005\"\u0003B\u0018IE\u0005I\u0011AAO\u0011%\u0011\t\u0004JI\u0001\n\u0003\t\u0019\u000bC\u0005\u00034\u0011\n\t\u0011\"!\u00036!I!1\t\u0013\u0012\u0002\u0013\u0005\u0011\u0011\u0013\u0005\n\u0005\u000b\"\u0013\u0013!C\u0001\u0003/C\u0011Ba\u0012%#\u0003%\t!!(\t\u0013\t%C%%A\u0005\u0002\u0005\r\u0006\"\u0003B&I\u0005\u0005I\u0011\u0002B'\u0005)\u0011\u0016M\u001c3p[B{w\u000e\u001c\u0006\u0003iU\nqA]8vi&twMC\u00017\u0003\u0011\t7n[1\u0004\u0001M1\u0001!O D\u000f*\u0003\"AO\u001f\u000e\u0003mR\u0011\u0001P\u0001\u0006g\u000e\fG.Y\u0005\u0003}m\u0012a!\u00118z%\u00164\u0007C\u0001!B\u001b\u0005\u0019\u0014B\u0001\"4\u0005\u0011\u0001vn\u001c7\u0011\u0007\u0001#e)\u0003\u0002Fg\t9\u0002k\\8m\u001fZ,'O]5eKVs7/\u001a;D_:4\u0017n\u001a\t\u0003\u0001\u0002\u0001\"A\u000f%\n\u0005%[$a\u0002)s_\u0012,8\r\u001e\t\u0003\u0017Ns!\u0001T)\u000f\u00055\u0003V\"\u0001(\u000b\u0005=;\u0014A\u0002\u001fs_>$h(C\u0001=\u0013\t\u00116(A\u0004qC\u000e\\\u0017mZ3\n\u0005Q+&\u0001D*fe&\fG.\u001b>bE2,'B\u0001*<\u00035q'o\u00144J]N$\u0018M\\2fgV\t\u0001\f\u0005\u0002;3&\u0011!l\u000f\u0002\u0004\u0013:$\u0018A\u00048s\u001f\u001aLen\u001d;b]\u000e,7\u000fI\u0001\be\u0016\u001c\u0018N_3s+\u0005q\u0006c\u0001\u001e`C&\u0011\u0001m\u000f\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005\u0001\u0013\u0017BA24\u0005\u001d\u0011Vm]5{KJ\f\u0001B]3tSj,'\u000fI\u0001\u0013gV\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u00170F\u0001h!\tA7.D\u0001j\u0015\tQW'A\u0003bGR|'/\u0003\u0002mS\n\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0003M\u0019X\u000f]3sm&\u001cxN]*ue\u0006$XmZ=!\u0003A\u0011x.\u001e;fe\u0012K7\u000f]1uG\",'/F\u0001q!\t\tXO\u0004\u0002sgB\u0011QjO\u0005\u0003in\na\u0001\u0015:fI\u00164\u0017B\u0001<x\u0005\u0019\u0019FO]5oO*\u0011AoO\u0001\u0012e>,H/\u001a:ESN\u0004\u0018\r^2iKJ\u0004\u0013!E;tKB{w\u000e\u001c#jgB\fGo\u00195feV\t1\u0010\u0005\u0002;y&\u0011Qp\u000f\u0002\b\u0005>|G.Z1o\u0003I)8/\u001a)p_2$\u0015n\u001d9bi\u000eDWM\u001d\u0011\u0002\rqJg.\u001b;?)-1\u00151AA\u0003\u0003\u000f\tI!a\u0003\t\u000bY[\u0001\u0019\u0001-\t\u000fq[\u0001\u0013!a\u0001=\"9Qm\u0003I\u0001\u0002\u00049\u0007b\u00028\f!\u0003\u0005\r\u0001\u001d\u0005\bs.\u0001\n\u00111\u0001|)\r1\u0015q\u0002\u0005\b\u0003#a\u0001\u0019AA\n\u0003\u0019\u0019wN\u001c4jOB!\u0011QCA\u0011\u001b\t\t9B\u0003\u0003\u0002\u0012\u0005e!\u0002BA\u000e\u0003;\t\u0001\u0002^=qKN\fg-\u001a\u0006\u0003\u0003?\t1aY8n\u0013\u0011\t\u0019#a\u0006\u0003\r\r{gNZ5h)\r1\u0015q\u0005\u0005\u0007\u0003Si\u0001\u0019\u0001-\u0002\u00059\u0014\u0018\u0001D2sK\u0006$XMU8vi\u0016\u0014H\u0003BA\u0018\u0003k\u00012\u0001QA\u0019\u0013\r\t\u0019d\r\u0002\u0007%>,H/\u001a:\t\u000f\u0005]b\u00021\u0001\u0002:\u000511/_:uK6\u00042\u0001[A\u001e\u0013\r\ti$\u001b\u0002\f\u0003\u000e$xN]*zgR,W\u000eF\u0002Y\u0003\u0003Bq!a\u0011\u0010\u0001\u0004\tI$A\u0002tsN\fac^5uQN+\b/\u001a:wSN|'o\u0015;sCR,w-\u001f\u000b\u0004\r\u0006%\u0003BBA&!\u0001\u0007q-\u0001\u0005tiJ\fG/Z4z\u0003-9\u0018\u000e\u001e5SKNL'0\u001a:\u0015\u0007\u0019\u000b\t\u0006C\u0003]#\u0001\u0007\u0011-\u0001\bxSRDG)[:qCR\u001c\u0007.\u001a:\u0015\u0007\u0019\u000b9\u0006\u0003\u0004\u0002ZI\u0001\r\u0001]\u0001\rI&\u001c\b/\u0019;dQ\u0016\u0014\u0018\nZ\u0001\ro&$\bNR1mY\n\f7m\u001b\u000b\u0005\u0003?\n)\u0007E\u0002A\u0003CJ1!a\u00194\u00051\u0011v.\u001e;fe\u000e{gNZ5h\u0011\u001d\t9g\u0005a\u0001\u0003?\nQa\u001c;iKJ\fAaY8qsRYa)!\u001c\u0002p\u0005E\u00141OA;\u0011\u001d1F\u0003%AA\u0002aCq\u0001\u0018\u000b\u0011\u0002\u0003\u0007a\fC\u0004f)A\u0005\t\u0019A4\t\u000f9$\u0002\u0013!a\u0001a\"9\u0011\u0010\u0006I\u0001\u0002\u0004Y\u0018AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003wR3\u0001WA?W\t\ty\b\u0005\u0003\u0002\u0002\u0006-UBAAB\u0015\u0011\t))a\"\u0002\u0013Ut7\r[3dW\u0016$'bAAEw\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u00055\u00151\u0011\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003'S3AXA?\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"!!'+\u0007\u001d\fi(\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\u0005}%f\u00019\u0002~\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012*TCAASU\rY\u0018QP\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005-\u0006\u0003BAW\u0003ok!!a,\u000b\t\u0005E\u00161W\u0001\u0005Y\u0006twM\u0003\u0002\u00026\u0006!!.\u0019<b\u0013\r1\u0018qV\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\ty,!2\u0011\u0007i\n\t-C\u0002\u0002Dn\u00121!\u00118z\u0011!\t9\rHA\u0001\u0002\u0004A\u0016a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002NB1\u0011qZAk\u0003\u007fk!!!5\u000b\u0007\u0005M7(\u0001\u0006d_2dWm\u0019;j_:LA!a6\u0002R\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\rY\u0018Q\u001c\u0005\n\u0003\u000ft\u0012\u0011!a\u0001\u0003\u007f\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111VAr\u0011!\t9mHA\u0001\u0002\u0004A\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003a\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003W\u000ba!Z9vC2\u001cHcA>\u0002r\"I\u0011q\u0019\u0012\u0002\u0002\u0003\u0007\u0011q\u0018\u0015\b\u0001\u0005U\u00181`A\u007f!\rQ\u0014q_\u0005\u0004\u0003s\\$\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011A\u0003*b]\u0012|W\u000eU8pYB\u0011\u0001\tJ\n\u0006I\t\u0015!\u0011\u0003\t\u000b\u0005\u000f\u0011i\u0001\u00170han4UB\u0001B\u0005\u0015\r\u0011YaO\u0001\beVtG/[7f\u0013\u0011\u0011yA!\u0003\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>tW\u0007\u0005\u0003\u0003\u0014\teQB\u0001B\u000b\u0015\u0011\u00119\"a-\u0002\u0005%|\u0017b\u0001+\u0003\u0016Q\u0011!\u0011A\u0001\u0006CB\u0004H.\u001f\u000b\f\r\n\u0005\"1\u0005B\u0013\u0005O\u0011I\u0003C\u0003WO\u0001\u0007\u0001\fC\u0004]OA\u0005\t\u0019\u00010\t\u000f\u0015<\u0003\u0013!a\u0001O\"9an\nI\u0001\u0002\u0004\u0001\bbB=(!\u0003\u0005\ra_\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$3'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00135\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012*\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0005o\u0011y\u0004\u0005\u0003;?\ne\u0002\u0003\u0003\u001e\u0003<asv\r]>\n\u0007\tu2H\u0001\u0004UkBdW-\u000e\u0005\t\u0005\u0003b\u0013\u0011!a\u0001\r\u0006\u0019\u0001\u0010\n\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00133\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%g\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIQ\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012*\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B(!\u0011\tiK!\u0015\n\t\tM\u0013q\u0016\u0002\u0007\u001f\nTWm\u0019;")
public final class RandomPool
implements PoolOverrideUnsetConfig<RandomPool>,
Product {
    private static final long serialVersionUID = 1L;
    private final int nrOfInstances;
    private final Option<Resizer> resizer;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;
    private final boolean usePoolDispatcher;

    public static boolean $lessinit$greater$default$5() {
        return RandomPool$.MODULE$.$lessinit$greater$default$5();
    }

    public static String $lessinit$greater$default$4() {
        return RandomPool$.MODULE$.$lessinit$greater$default$4();
    }

    public static SupervisorStrategy $lessinit$greater$default$3() {
        return RandomPool$.MODULE$.$lessinit$greater$default$3();
    }

    public static Option<Resizer> $lessinit$greater$default$2() {
        return RandomPool$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>> unapply(RandomPool x$0) {
        return RandomPool$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$5() {
        return RandomPool$.MODULE$.apply$default$5();
    }

    public static String apply$default$4() {
        return RandomPool$.MODULE$.apply$default$4();
    }

    public static SupervisorStrategy apply$default$3() {
        return RandomPool$.MODULE$.apply$default$3();
    }

    public static Option<Resizer> apply$default$2() {
        return RandomPool$.MODULE$.apply$default$2();
    }

    public static RandomPool apply(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return RandomPool$.MODULE$.apply(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public static Function1<Tuple5<Object, Option<Resizer>, SupervisorStrategy, String, Object>, RandomPool> tupled() {
        return RandomPool$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Option<Resizer>, Function1<SupervisorStrategy, Function1<String, Function1<Object, RandomPool>>>>> curried() {
        return RandomPool$.MODULE$.curried();
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
        return new Router(RandomRoutingLogic$.MODULE$.apply());
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return this.nrOfInstances();
    }

    @Override
    public RandomPool withSupervisorStrategy(SupervisorStrategy strategy) {
        SupervisorStrategy x$1 = strategy;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        String x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$3, x$1, x$4, x$5);
    }

    @Override
    public RandomPool withResizer(Resizer resizer) {
        Some<Resizer> x$1 = new Some<Resizer>(resizer);
        int x$2 = this.copy$default$1();
        SupervisorStrategy x$3 = this.copy$default$3();
        String x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        return this.copy(x$2, x$1, x$3, x$4, x$5);
    }

    public RandomPool withDispatcher(String dispatcherId) {
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

    public RandomPool copy(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new RandomPool(nrOfInstances, resizer, supervisorStrategy, routerDispatcher, usePoolDispatcher);
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
        return "RandomPool";
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
        return x$1 instanceof RandomPool;
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
        if (!(object instanceof RandomPool)) return false;
        boolean bl = true;
        if (!bl) return false;
        RandomPool randomPool = (RandomPool)x$1;
        if (this.nrOfInstances() != randomPool.nrOfInstances()) return false;
        if (this.usePoolDispatcher() != randomPool.usePoolDispatcher()) return false;
        Option<Resizer> option = this.resizer();
        Option<Resizer> option2 = randomPool.resizer();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        SupervisorStrategy supervisorStrategy = this.supervisorStrategy();
        SupervisorStrategy supervisorStrategy2 = randomPool.supervisorStrategy();
        if (supervisorStrategy == null) {
            if (supervisorStrategy2 != null) {
                return false;
            }
        } else if (!supervisorStrategy.equals(supervisorStrategy2)) return false;
        String string = this.routerDispatcher();
        String string2 = randomPool.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public RandomPool(int nrOfInstances, Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
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

    public RandomPool(Config config) {
        int x$1 = config.getInt("nr-of-instances");
        Option<Resizer> x$2 = Resizer$.MODULE$.fromConfig(config);
        boolean x$3 = config.hasPath("pool-dispatcher");
        SupervisorStrategy x$4 = RandomPool$.MODULE$.$lessinit$greater$default$3();
        String x$5 = RandomPool$.MODULE$.$lessinit$greater$default$4();
        this(x$1, x$2, x$4, x$5, x$3);
    }

    public RandomPool(int nr) {
        this(nr, RandomPool$.MODULE$.$lessinit$greater$default$2(), RandomPool$.MODULE$.$lessinit$greater$default$3(), RandomPool$.MODULE$.$lessinit$greater$default$4(), RandomPool$.MODULE$.$lessinit$greater$default$5());
    }
}

