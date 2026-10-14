/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.Util$;
import akka.routing.ConsistentHashingGroup$;
import akka.routing.ConsistentHashingRouter;
import akka.routing.ConsistentHashingRouter$;
import akka.routing.ConsistentHashingRoutingLogic;
import akka.routing.FromConfig;
import akka.routing.Group;
import akka.routing.NoRouter;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RouterActor;
import akka.routing.RouterConfig;
import akka.routing.RoutingLogic;
import com.typesafe.config.Config;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u0015c\u0001B\u0017/\u0005NB\u0001\"\u0014\u0001\u0003\u0016\u0004%\tA\u0014\u0005\t?\u0002\u0011\t\u0012)A\u0005\u001f\"A\u0001\r\u0001BK\u0002\u0013\u0005\u0011\r\u0003\u0005f\u0001\tE\t\u0015!\u0003c\u0011!1\u0007A!f\u0001\n\u00039\u0007\u0002C8\u0001\u0005#\u0005\u000b\u0011\u00025\t\u0011A\u0004!Q3A\u0005BED\u0001B\u001d\u0001\u0003\u0012\u0003\u0006Ia\u0016\u0005\u0006g\u0002!\t\u0001\u001e\u0005\u0006g\u0002!\tA\u001f\u0005\u0007g\u0002!\t!!\u0004\t\r5\u0003A\u0011IA\u0011\u0011\u001d\t\u0019\u0004\u0001C!\u0003kAq!a\u0010\u0001\t\u0003\t\t\u0005C\u0004\u0002H\u0001!\t!!\u0013\t\u000f\u0005=\u0003\u0001\"\u0001\u0002R!9\u0011Q\f\u0001\u0005B\u0005}\u0003\"CA6\u0001\u0005\u0005I\u0011AA7\u0011%\t9\bAI\u0001\n\u0003\tI\bC\u0005\u0002\u0010\u0002\t\n\u0011\"\u0001\u0002\u0012\"I\u0011Q\u0013\u0001\u0012\u0002\u0013\u0005\u0011q\u0013\u0005\n\u00037\u0003\u0011\u0013!C\u0001\u0003;C\u0011\"!)\u0001\u0003\u0003%\t%a)\t\u0011\u0005%\u0006!!A\u0005\u0002\u0005D\u0011\"a+\u0001\u0003\u0003%\t!!,\t\u0013\u0005e\u0006!!A\u0005B\u0005m\u0006\"CAc\u0001\u0005\u0005I\u0011AAd\u0011%\t\t\u000eAA\u0001\n\u0003\n\u0019\u000eC\u0005\u0002X\u0002\t\t\u0011\"\u0011\u0002Z\"I\u00111\u001c\u0001\u0002\u0002\u0013\u0005\u0013Q\u001c\u0005\n\u0003?\u0004\u0011\u0011!C!\u0003C<\u0011\"!=/\u0003\u0003E\t!a=\u0007\u00115r\u0013\u0011!E\u0001\u0003kDaa]\u0011\u0005\u0002\t5\u0001\"CAnC\u0005\u0005IQIAo\u0011%\u0011y!IA\u0001\n\u0003\u0013\t\u0002C\u0005\u0003\u001c\u0005\n\n\u0011\"\u0001\u0002\u0012\"I!QD\u0011\u0012\u0002\u0013\u0005\u0011q\u0013\u0005\n\u0005?\t\u0013\u0013!C\u0001\u0003;C\u0011B!\t\"\u0003\u0003%\tIa\t\t\u0013\tU\u0012%%A\u0005\u0002\u0005E\u0005\"\u0003B\u001cCE\u0005I\u0011AAL\u0011%\u0011I$II\u0001\n\u0003\ti\nC\u0005\u0003<\u0005\n\t\u0011\"\u0003\u0003>\t12i\u001c8tSN$XM\u001c;ICND\u0017N\\4He>,\bO\u0003\u00020a\u00059!o\\;uS:<'\"A\u0019\u0002\t\u0005\\7.Y\u0002\u0001'\u0015\u0001AG\u000f B!\t)\u0004(D\u00017\u0015\u00059\u0014!B:dC2\f\u0017BA\u001d7\u0005\u0019\te.\u001f*fMB\u00111\bP\u0007\u0002]%\u0011QH\f\u0002\u0006\u000fJ|W\u000f\u001d\t\u0003k}J!\u0001\u0011\u001c\u0003\u000fA\u0013x\u000eZ;diB\u0011!I\u0013\b\u0003\u0007\"s!\u0001R$\u000e\u0003\u0015S!A\u0012\u001a\u0002\rq\u0012xn\u001c;?\u0013\u00059\u0014BA%7\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u0013'\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005%3\u0014!\u00029bi\"\u001cX#A(\u0011\u0007A+v+D\u0001R\u0015\t\u00116+A\u0005j[6,H/\u00192mK*\u0011AKN\u0001\u000bG>dG.Z2uS>t\u0017B\u0001,R\u0005!IE/\u001a:bE2,\u0007C\u0001-]\u001d\tI&\f\u0005\u0002Em%\u00111LN\u0001\u0007!J,G-\u001a4\n\u0005us&AB*ue&twM\u0003\u0002\\m\u00051\u0001/\u0019;ig\u0002\n!C^5siV\fGNT8eKN4\u0015m\u0019;peV\t!\r\u0005\u00026G&\u0011AM\u000e\u0002\u0004\u0013:$\u0018a\u0005<jeR,\u0018\r\u001c(pI\u0016\u001ch)Y2u_J\u0004\u0013a\u00035bg\"l\u0015\r\u001d9j]\u001e,\u0012\u0001\u001b\t\u0003S2t!a\u000f6\n\u0005-t\u0013aF\"p]NL7\u000f^3oi\"\u000b7\u000f[5oOJ{W\u000f^3s\u0013\tigNA\u000bD_:\u001c\u0018n\u001d;f]RD\u0015m\u001d5NCB\u0004\u0018N\\4\u000b\u0005-t\u0013\u0001\u00045bg\"l\u0015\r\u001d9j]\u001e\u0004\u0013\u0001\u0005:pkR,'\u000fR5ta\u0006$8\r[3s+\u00059\u0016!\u0005:pkR,'\u000fR5ta\u0006$8\r[3sA\u00051A(\u001b8jiz\"R!\u001e<xqf\u0004\"a\u000f\u0001\t\u000b5K\u0001\u0019A(\t\u000f\u0001L\u0001\u0013!a\u0001E\"9a-\u0003I\u0001\u0002\u0004A\u0007b\u00029\n!\u0003\u0005\ra\u0016\u000b\u0003knDQ\u0001 \u0006A\u0002u\faaY8oM&<\u0007c\u0001@\u0002\n5\tqPC\u0002}\u0003\u0003QA!a\u0001\u0002\u0006\u0005AA/\u001f9fg\u00064WM\u0003\u0002\u0002\b\u0005\u00191m\\7\n\u0007\u0005-qP\u0001\u0004D_:4\u0017n\u001a\u000b\u0004k\u0006=\u0001bBA\t\u0017\u0001\u0007\u00111C\u0001\fe>,H/Z3QCRD7\u000fE\u0003\u0002\u0016\u0005}q+\u0004\u0002\u0002\u0018)!\u0011\u0011DA\u000e\u0003\u0011a\u0017M\\4\u000b\u0005\u0005u\u0011\u0001\u00026bm\u0006L1AVA\f)\ry\u00151\u0005\u0005\b\u0003Ka\u0001\u0019AA\u0014\u0003\u0019\u0019\u0018p\u001d;f[B!\u0011\u0011FA\u0018\u001b\t\tYCC\u0002\u0002.A\nQ!Y2u_JLA!!\r\u0002,\tY\u0011i\u0019;peNK8\u000f^3n\u00031\u0019'/Z1uKJ{W\u000f^3s)\u0011\t9$!\u0010\u0011\u0007m\nI$C\u0002\u0002<9\u0012aAU8vi\u0016\u0014\bbBA\u0013\u001b\u0001\u0007\u0011qE\u0001\u000fo&$\b\u000eR5ta\u0006$8\r[3s)\r)\u00181\t\u0005\u0007\u0003\u000br\u0001\u0019A,\u0002\u0019\u0011L7\u000f]1uG\",'/\u00133\u0002-]LG\u000f\u001b,jeR,\u0018\r\u001c(pI\u0016\u001ch)Y2u_J$2!^A&\u0011\u0019\tie\u0004a\u0001E\u00061aO\\8eKN\fab^5uQ\"\u000b7\u000f['baB,'\u000fF\u0002v\u0003'Bq!!\u0016\u0011\u0001\u0004\t9&\u0001\u0004nCB\u0004XM\u001d\t\u0004S\u0006e\u0013bAA.]\n!2i\u001c8tSN$XM\u001c;ICNDW*\u00199qKJ\fAb^5uQ\u001a\u000bG\u000e\u001c2bG.$B!!\u0019\u0002hA\u00191(a\u0019\n\u0007\u0005\u0015dF\u0001\u0007S_V$XM]\"p]\u001aLw\rC\u0004\u0002jE\u0001\r!!\u0019\u0002\u000b=$\b.\u001a:\u0002\t\r|\u0007/\u001f\u000b\nk\u0006=\u0014\u0011OA:\u0003kBq!\u0014\n\u0011\u0002\u0003\u0007q\nC\u0004a%A\u0005\t\u0019\u00012\t\u000f\u0019\u0014\u0002\u0013!a\u0001Q\"9\u0001O\u0005I\u0001\u0002\u00049\u0016AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0003wR3aTA?W\t\ty\b\u0005\u0003\u0002\u0002\u0006-UBAAB\u0015\u0011\t))a\"\u0002\u0013Ut7\r[3dW\u0016$'bAAEm\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u00055\u00151\u0011\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0003'S3AYA?\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"!!'+\u0007!\fi(\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\u0005}%fA,\u0002~\u0005i\u0001O]8ek\u000e$\bK]3gSb,\"!!*\u0011\t\u0005U\u0011qU\u0005\u0004;\u0006]\u0011\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003_\u000b)\fE\u00026\u0003cK1!a-7\u0005\r\te.\u001f\u0005\t\u0003oK\u0012\u0011!a\u0001E\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!!0\u0011\r\u0005}\u0016\u0011YAX\u001b\u0005\u0019\u0016bAAb'\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\tI-a4\u0011\u0007U\nY-C\u0002\u0002NZ\u0012qAQ8pY\u0016\fg\u000eC\u0005\u00028n\t\t\u00111\u0001\u00020\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t)+!6\t\u0011\u0005]F$!AA\u0002\t\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002E\u0006AAo\\*ue&tw\r\u0006\u0002\u0002&\u00061Q-];bYN$B!!3\u0002d\"I\u0011qW\u0010\u0002\u0002\u0003\u0007\u0011q\u0016\u0015\b\u0001\u0005\u001d\u0018Q^Ax!\r)\u0014\u0011^\u0005\u0004\u0003W4$\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011AF\"p]NL7\u000f^3oi\"\u000b7\u000f[5oO\u001e\u0013x.\u001e9\u0011\u0005m\n3#B\u0011\u0002x\n\r\u0001#CA}\u0003\u007f|%\r[,v\u001b\t\tYPC\u0002\u0002~Z\nqA];oi&lW-\u0003\u0003\u0003\u0002\u0005m(!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oiA!!Q\u0001B\u0006\u001b\t\u00119A\u0003\u0003\u0003\n\u0005m\u0011AA5p\u0013\rY%q\u0001\u000b\u0003\u0003g\fQ!\u00199qYf$\u0012\"\u001eB\n\u0005+\u00119B!\u0007\t\u000b5#\u0003\u0019A(\t\u000f\u0001$\u0003\u0013!a\u0001E\"9a\r\nI\u0001\u0002\u0004A\u0007b\u00029%!\u0003\u0005\raV\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$3'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00135\u0003\u001d)h.\u00199qYf$BA!\n\u00032A)QGa\n\u0003,%\u0019!\u0011\u0006\u001c\u0003\r=\u0003H/[8o!\u001d)$QF(cQ^K1Aa\f7\u0005\u0019!V\u000f\u001d7fi!A!1\u0007\u0015\u0002\u0002\u0003\u0007Q/A\u0002yIA\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$3'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H\u0005N\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005\u007f\u0001B!!\u0006\u0003B%!!1IA\f\u0005\u0019y%M[3di\u0002")
public final class ConsistentHashingGroup
implements Group,
Product {
    private static final long serialVersionUID = 1L;
    private final Iterable<String> paths;
    private final int virtualNodesFactor;
    private final PartialFunction<Object, Object> hashMapping;
    private final String routerDispatcher;

    public static String $lessinit$greater$default$4() {
        return ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$4();
    }

    public static PartialFunction<Object, Object> $lessinit$greater$default$3() {
        return ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$3();
    }

    public static int $lessinit$greater$default$2() {
        return ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple4<Iterable<String>, Object, PartialFunction<Object, Object>, String>> unapply(ConsistentHashingGroup x$0) {
        return ConsistentHashingGroup$.MODULE$.unapply(x$0);
    }

    public static String apply$default$4() {
        return ConsistentHashingGroup$.MODULE$.apply$default$4();
    }

    public static PartialFunction<Object, Object> apply$default$3() {
        return ConsistentHashingGroup$.MODULE$.apply$default$3();
    }

    public static int apply$default$2() {
        return ConsistentHashingGroup$.MODULE$.apply$default$2();
    }

    public static ConsistentHashingGroup apply(Iterable<String> paths, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping, String routerDispatcher) {
        return ConsistentHashingGroup$.MODULE$.apply(paths, virtualNodesFactor, hashMapping, routerDispatcher);
    }

    public static Function1<Tuple4<Iterable<String>, Object, PartialFunction<Object, Object>, String>, ConsistentHashingGroup> tupled() {
        return ConsistentHashingGroup$.MODULE$.tupled();
    }

    public static Function1<Iterable<String>, Function1<Object, Function1<PartialFunction<Object, Object>, Function1<String, ConsistentHashingGroup>>>> curried() {
        return ConsistentHashingGroup$.MODULE$.curried();
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
    public void verifyConfig(ActorPath path) {
        RouterConfig.verifyConfig$(this, path);
    }

    public Iterable<String> paths() {
        return this.paths;
    }

    public int virtualNodesFactor() {
        return this.virtualNodesFactor;
    }

    public PartialFunction<Object, Object> hashMapping() {
        return this.hashMapping;
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
        return new Router(new ConsistentHashingRoutingLogic(system, this.virtualNodesFactor(), this.hashMapping()));
    }

    public ConsistentHashingGroup withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        Iterable<String> x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        PartialFunction<Object, Object> x$4 = this.copy$default$3();
        return this.copy(x$2, x$3, x$4, x$1);
    }

    public ConsistentHashingGroup withVirtualNodesFactor(int vnodes) {
        int x$1 = vnodes;
        Iterable<String> x$2 = this.copy$default$1();
        PartialFunction<Object, Object> x$3 = this.copy$default$3();
        String x$4 = this.copy$default$4();
        return this.copy(x$2, x$1, x$3, x$4);
    }

    public ConsistentHashingGroup withHashMapper(ConsistentHashingRouter.ConsistentHashMapper mapper2) {
        PartialFunction<Object, Object> x$1 = ConsistentHashingRouter$.MODULE$.hashMappingAdapter(mapper2);
        Iterable<String> x$2 = this.copy$default$1();
        int x$3 = this.copy$default$2();
        String x$4 = this.copy$default$4();
        return this.copy(x$2, x$3, x$1, x$4);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        RouterConfig routerConfig = other;
        if (routerConfig instanceof FromConfig ? true : routerConfig instanceof NoRouter) {
            return RouterConfig.withFallback$(this, other);
        }
        if (routerConfig instanceof ConsistentHashingGroup) {
            ConsistentHashingGroup consistentHashingGroup = (ConsistentHashingGroup)routerConfig;
            PartialFunction<Object, Object> x$1 = consistentHashingGroup.hashMapping();
            Iterable<String> x$2 = this.copy$default$1();
            int x$3 = this.copy$default$2();
            String x$4 = this.copy$default$4();
            return this.copy(x$2, x$3, x$1, x$4);
        }
        throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("Expected ConsistentHashingGroup, got [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{other})));
    }

    public ConsistentHashingGroup copy(Iterable<String> paths, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping, String routerDispatcher) {
        return new ConsistentHashingGroup(paths, virtualNodesFactor, hashMapping, routerDispatcher);
    }

    public Iterable<String> copy$default$1() {
        return this.paths();
    }

    public int copy$default$2() {
        return this.virtualNodesFactor();
    }

    public PartialFunction<Object, Object> copy$default$3() {
        return this.hashMapping();
    }

    public String copy$default$4() {
        return this.routerDispatcher();
    }

    @Override
    public String productPrefix() {
        return "ConsistentHashingGroup";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.paths();
            }
            case 1: {
                return BoxesRunTime.boxToInteger(this.virtualNodesFactor());
            }
            case 2: {
                return this.hashMapping();
            }
            case 3: {
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
        return x$1 instanceof ConsistentHashingGroup;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "paths";
            }
            case 1: {
                return "virtualNodesFactor";
            }
            case 2: {
                return "hashMapping";
            }
            case 3: {
                return "routerDispatcher";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.paths()));
        n = Statics.mix(n, this.virtualNodesFactor());
        n = Statics.mix(n, Statics.anyHash(this.hashMapping()));
        n = Statics.mix(n, Statics.anyHash(this.routerDispatcher()));
        return Statics.finalizeHash(n, 4);
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
        if (!(object instanceof ConsistentHashingGroup)) return false;
        boolean bl = true;
        if (!bl) return false;
        ConsistentHashingGroup consistentHashingGroup = (ConsistentHashingGroup)x$1;
        if (this.virtualNodesFactor() != consistentHashingGroup.virtualNodesFactor()) return false;
        Iterable<String> iterable = this.paths();
        Iterable<String> iterable2 = consistentHashingGroup.paths();
        if (iterable == null) {
            if (iterable2 != null) {
                return false;
            }
        } else if (!iterable.equals(iterable2)) return false;
        PartialFunction<Object, Object> partialFunction = this.hashMapping();
        PartialFunction<Object, Object> partialFunction2 = consistentHashingGroup.hashMapping();
        if (partialFunction == null) {
            if (partialFunction2 != null) {
                return false;
            }
        } else if (!partialFunction.equals(partialFunction2)) return false;
        String string = this.routerDispatcher();
        String string2 = consistentHashingGroup.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public ConsistentHashingGroup(Iterable<String> paths, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping, String routerDispatcher) {
        this.paths = paths;
        this.virtualNodesFactor = virtualNodesFactor;
        this.hashMapping = hashMapping;
        this.routerDispatcher = routerDispatcher;
        RouterConfig.$init$(this);
        Group.$init$(this);
        Product.$init$(this);
    }

    public ConsistentHashingGroup(Config config) {
        this(Util$.MODULE$.immutableSeq(config.getStringList("routees.paths")), ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$2(), ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$3(), ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$4());
    }

    public ConsistentHashingGroup(java.lang.Iterable<String> routeePaths) {
        this(Util$.MODULE$.immutableSeq(routeePaths), ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$2(), ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$3(), ConsistentHashingGroup$.MODULE$.$lessinit$greater$default$4());
    }
}

