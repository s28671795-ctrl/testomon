/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.ConfigurationException;
import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.SupervisorStrategy;
import akka.routing.FromConfig$;
import akka.routing.Pool;
import akka.routing.Pool$;
import akka.routing.Resizer;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005ew!\u0002\u0012$\u0011\u0003Cc!\u0002\u0016$\u0011\u0003[\u0003BB,\u0002\t\u0003\ty\u0004C\u0004\u0002B\u0005!\t!a\u0011\t\u000f\u0005\u0015\u0013\u0001\"\u0002\u0002H!I\u0011qK\u0001\u0012\u0002\u0013\u0015\u0011\u0011\f\u0005\n\u0003_\n\u0011\u0013!C\u0003\u0003cB\u0011\"!\u001e\u0002#\u0003%)!a\u001e\t\u000f\u0005m\u0014\u0001\"\u0002\u0002~!I\u0011qQ\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0012\u0005\n\u00033\u000b\u0011\u0011!C\u0001\u00037C\u0011\"!(\u0002\u0003\u0003%\t!a(\t\u0013\u0005-\u0016!!A\u0005B\u00055\u0006\"CA^\u0003\u0005\u0005I\u0011AA_\u0011%\t9-AA\u0001\n\u0003\nI\rC\u0005\u0002L\u0006\t\t\u0011\"\u0011\u0002N\"I\u0011qZ\u0001\u0002\u0002\u0013%\u0011\u0011\u001b\u0004\u0005U\r\u0002Q\u0006\u0003\u00058#\t\u0015\r\u0011\"\u00119\u0011!y\u0014C!A!\u0002\u0013I\u0004\u0002\u0003!\u0012\u0005\u000b\u0007I\u0011I!\t\u0011!\u000b\"\u0011!Q\u0001\n\tC\u0001\"S\t\u0003\u0006\u0004%\tE\u0013\u0005\t-F\u0011\t\u0011)A\u0005\u0017\")q+\u0005C\u00011\")q+\u0005C\u00019\")Q,\u0005C!=\"1q-\u0005C!K!DQ\u0001\\\t\u0005B5DQA^\t\u0005\u0002]DQA_\t\u0005\u0002mDQ!`\t\u0005\u0002yDq!a\u0001\u0012\t\u0003\n)\u0001C\u0004\u0002\u0012E!\t!a\u0005\u0002\u0015\u0019\u0013x.\\\"p]\u001aLwM\u0003\u0002%K\u00059!o\\;uS:<'\"\u0001\u0014\u0002\t\u0005\\7.Y\u0002\u0001!\tI\u0013!D\u0001$\u0005)1%o\\7D_:4\u0017nZ\n\u0007\u00031\n9#!\f\u0011\u0005%\n2cA\t/iA\u0011qFM\u0007\u0002a)\t\u0011'A\u0003tG\u0006d\u0017-\u0003\u00024a\t1\u0011I\\=SK\u001a\u0004\"!K\u001b\n\u0005Y\u001a#\u0001\u0002)p_2\fqA]3tSj,'/F\u0001:!\ry#\bP\u0005\u0003wA\u0012aa\u00149uS>t\u0007CA\u0015>\u0013\tq4EA\u0004SKNL'0\u001a:\u0002\u0011I,7/\u001b>fe\u0002\n!c];qKJ4\u0018n]8s'R\u0014\u0018\r^3hsV\t!\t\u0005\u0002D\r6\tAI\u0003\u0002FK\u0005)\u0011m\u0019;pe&\u0011q\t\u0012\u0002\u0013'V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u00170A\ntkB,'O^5t_J\u001cFO]1uK\u001eL\b%\u0001\ts_V$XM\u001d#jgB\fGo\u00195feV\t1\n\u0005\u0002M':\u0011Q*\u0015\t\u0003\u001dBj\u0011a\u0014\u0006\u0003!\u001e\na\u0001\u0010:p_Rt\u0014B\u0001*1\u0003\u0019\u0001&/\u001a3fM&\u0011A+\u0016\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005I\u0003\u0014!\u0005:pkR,'\u000fR5ta\u0006$8\r[3sA\u00051A(\u001b8jiz\"B\u0001L-[7\")q\u0007\u0007a\u0001s!)\u0001\t\u0007a\u0001\u0005\")\u0011\n\u0007a\u0001\u0017R\tA&\u0001\u0007de\u0016\fG/\u001a*pkR,'\u000f\u0006\u0002`EB\u0011\u0011\u0006Y\u0005\u0003C\u000e\u0012aAU8vi\u0016\u0014\b\"B2\u001b\u0001\u0004!\u0017AB:zgR,W\u000e\u0005\u0002DK&\u0011a\r\u0012\u0002\f\u0003\u000e$xN]*zgR,W.A\tde\u0016\fG/\u001a*pkR,'/Q2u_J$\u0012!\u001b\t\u0003S)L!a[\u0012\u0003\u0017I{W\u000f^3s\u0003\u000e$xN]\u0001\rm\u0016\u0014\u0018NZ=D_:4\u0017n\u001a\u000b\u0003]F\u0004\"aL8\n\u0005A\u0004$\u0001B+oSRDQA\u001d\u000fA\u0002M\fA\u0001]1uQB\u00111\t^\u0005\u0003k\u0012\u0013\u0011\"Q2u_J\u0004\u0016\r\u001e5\u0002-]LG\u000f[*va\u0016\u0014h/[:peN#(/\u0019;fOf$\"\u0001\f=\t\u000bel\u0002\u0019\u0001\"\u0002\u0011M$(/\u0019;fOf\f1b^5uQJ+7/\u001b>feR\u0011A\u0006 \u0005\u0006oy\u0001\r\u0001P\u0001\u000fo&$\b\u000eR5ta\u0006$8\r[3s)\tas\u0010\u0003\u0004\u0002\u0002}\u0001\raS\u0001\rI&\u001c\b/\u0019;dQ\u0016\u0014\u0018\nZ\u0001\u000e]J|e-\u00138ti\u0006t7-Z:\u0015\t\u0005\u001d\u0011Q\u0002\t\u0004_\u0005%\u0011bAA\u0006a\t\u0019\u0011J\u001c;\t\r\u0005=\u0001\u00051\u0001e\u0003\r\u0019\u0018p]\u0001\u0006aJ|\u0007o\u001d\u000b\u0003\u0003+\u00012aQA\f\u0013\r\tI\u0002\u0012\u0002\u0006!J|\u0007o\u001d\u0015\b#\u0005u\u00111EA\u0013!\ry\u0013qD\u0005\u0004\u0003C\u0001$\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0001cA\u0018\u0002*%\u0019\u00111\u0006\u0019\u0003\u000fA\u0013x\u000eZ;diB!\u0011qFA\u001d\u001d\u0011\t\t$!\u000e\u000f\u00079\u000b\u0019$C\u00012\u0013\r\t9\u0004M\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\tY$!\u0010\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0007\u0005]\u0002\u0007F\u0001)\u0003-9W\r^%ogR\fgnY3\u0016\u0003!\nQ!\u00199qYf$r\u0001LA%\u0003\u0017\ni\u0005C\u00048\tA\u0005\t\u0019A\u001d\t\u000f\u0001#\u0001\u0013!a\u0001\u0005\"9\u0011\n\u0002I\u0001\u0002\u0004Y\u0005f\u0001\u0003\u0002RA\u0019q&a\u0015\n\u0007\u0005U\u0003G\u0001\u0004j]2Lg.Z\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u00111\f\u0016\u0004s\u0005u3FAA0!\u0011\t\t'a\u001b\u000e\u0005\u0005\r$\u0002BA3\u0003O\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005%\u0004'\u0001\u0006b]:|G/\u0019;j_:LA!!\u001c\u0002d\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII*\"!a\u001d+\u0007\t\u000bi&A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00134+\t\tIHK\u0002L\u0003;\nq!\u001e8baBd\u0017\u0010\u0006\u0003\u0002\u0000\u0005\u0005\u0005cA\u0018;\u0017\"1\u00111\u0011\u0005A\u00021\n!AZ2)\u0007!\t\t&A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u0017\u0003B!!$\u0002\u00186\u0011\u0011q\u0012\u0006\u0005\u0003#\u000b\u0019*\u0001\u0003mC:<'BAAK\u0003\u0011Q\u0017M^1\n\u0007Q\u000by)\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0002\b\u0005q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003BAQ\u0003O\u00032aLAR\u0013\r\t)\u000b\r\u0002\u0004\u0003:L\b\"CAU\u0017\u0005\u0005\t\u0019AA\u0004\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011q\u0016\t\u0007\u0003c\u000b9,!)\u000e\u0005\u0005M&bAA[a\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0005e\u00161\u0017\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002@\u0006\u0015\u0007cA\u0018\u0002B&\u0019\u00111\u0019\u0019\u0003\u000f\t{w\u000e\\3b]\"I\u0011\u0011V\u0007\u0002\u0002\u0003\u0007\u0011\u0011U\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011qA\u0001\ti>\u001cFO]5oOR\u0011\u00111R\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003'\u0004B!!$\u0002V&!\u0011q[AH\u0005\u0019y%M[3di\u0002")
public class FromConfig
implements Pool {
    private static final long serialVersionUID = 1L;
    private final Option<Resizer> resizer;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;

    public static boolean canEqual(Object x$1) {
        return FromConfig$.MODULE$.canEqual(x$1);
    }

    public static Iterator<Object> productIterator() {
        return FromConfig$.MODULE$.productIterator();
    }

    public static Object productElement(int x$1) {
        return FromConfig$.MODULE$.productElement(x$1);
    }

    public static int productArity() {
        return FromConfig$.MODULE$.productArity();
    }

    public static String productPrefix() {
        return FromConfig$.MODULE$.productPrefix();
    }

    public static Option<String> unapply(FromConfig fc) {
        return FromConfig$.MODULE$.unapply(fc);
    }

    public static String apply$default$3() {
        return FromConfig$.MODULE$.apply$default$3();
    }

    public static SupervisorStrategy apply$default$2() {
        return FromConfig$.MODULE$.apply$default$2();
    }

    public static Option<Resizer> apply$default$1() {
        return FromConfig$.MODULE$.apply$default$1();
    }

    public static FromConfig apply(Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher) {
        return FromConfig$.MODULE$.apply(resizer, supervisorStrategy, routerDispatcher);
    }

    public static FromConfig$ getInstance() {
        return FromConfig$.MODULE$.getInstance();
    }

    public static Iterator<String> productElementNames() {
        return FromConfig$.MODULE$.productElementNames();
    }

    public static String productElementName(int n) {
        return FromConfig$.MODULE$.productElementName(n);
    }

    @Override
    public boolean usePoolDispatcher() {
        return Pool.usePoolDispatcher$(this);
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
    public Option<Props> routingLogicController(RoutingLogic routingLogic) {
        return RouterConfig.routingLogicController$(this, routingLogic);
    }

    @Override
    public boolean isManagementMessage(Object msg) {
        return RouterConfig.isManagementMessage$(this, msg);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return RouterConfig.withFallback$(this, other);
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
    public Router createRouter(ActorSystem system) {
        throw new UnsupportedOperationException("FromConfig must not create Router");
    }

    @Override
    public RouterActor createRouterActor() {
        throw new UnsupportedOperationException("FromConfig must not create RouterActor");
    }

    @Override
    public void verifyConfig(ActorPath path) {
        throw new ConfigurationException(new StringBuilder(71).append("Configuration missing for router [").append(path).append("] in 'akka.actor.deployment' section.").toString());
    }

    public FromConfig withSupervisorStrategy(SupervisorStrategy strategy) {
        return new FromConfig(this.resizer(), strategy, this.routerDispatcher());
    }

    public FromConfig withResizer(Resizer resizer) {
        return new FromConfig(new Some<Resizer>(resizer), this.supervisorStrategy(), this.routerDispatcher());
    }

    public FromConfig withDispatcher(String dispatcherId) {
        return new FromConfig(this.resizer(), this.supervisorStrategy(), dispatcherId);
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return 0;
    }

    public Props props() {
        return Props$.MODULE$.empty().withRouter(this);
    }

    public FromConfig(Option<Resizer> resizer, SupervisorStrategy supervisorStrategy, String routerDispatcher) {
        this.resizer = resizer;
        this.supervisorStrategy = supervisorStrategy;
        this.routerDispatcher = routerDispatcher;
        RouterConfig.$init$(this);
        Pool.$init$(this);
    }

    public FromConfig() {
        this(None$.MODULE$, Pool$.MODULE$.defaultSupervisorStrategy(), "akka.actor.default-dispatcher");
    }
}

