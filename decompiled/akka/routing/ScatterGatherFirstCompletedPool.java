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
import akka.routing.ScatterGatherFirstCompletedPool$;
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
import scala.Some;
import scala.Tuple6;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tMe\u0001\u0002\u001c8\u0005rB\u0001B\u0017\u0001\u0003\u0016\u0004%\ta\u0017\u0005\t?\u0002\u0011\t\u0012)A\u00059\"A\u0001\r\u0001BK\u0002\u0013\u0005\u0013\r\u0003\u0005i\u0001\tE\t\u0015!\u0003c\u0011!I\u0007A!f\u0001\n\u0003Q\u0007\u0002C:\u0001\u0005#\u0005\u000b\u0011B6\t\u0011Q\u0004!Q3A\u0005BUD\u0001\u0002 \u0001\u0003\u0012\u0003\u0006IA\u001e\u0005\t{\u0002\u0011)\u001a!C!}\"I\u0011q\u0002\u0001\u0003\u0012\u0003\u0006Ia \u0005\u000b\u0003#\u0001!Q3A\u0005B\u0005M\u0001BCA\u000e\u0001\tE\t\u0015!\u0003\u0002\u0016!9\u0011Q\u0004\u0001\u0005\u0002\u0005}\u0001bBA\u000f\u0001\u0011\u0005\u0011Q\u0006\u0005\b\u0003;\u0001A\u0011AA#\u0011\u001d\ti\u0002\u0001C\u0001\u0003\u001bBq!a\u0019\u0001\t\u0003\n)\u0007\u0003\u0004[\u0001\u0011\u0005\u0013q\u000f\u0005\b\u0003{\u0002A\u0011AA@\u0011\u001d\t)\t\u0001C\u0001\u0003\u000fCq!a#\u0001\t\u0003\ti\tC\u0004\u0002\u0014\u0002!\t%!&\t\u0013\u0005\u0005\u0006!!A\u0005\u0002\u0005\r\u0006\"CAY\u0001E\u0005I\u0011AAZ\u0011%\tI\rAI\u0001\n\u0003\tY\rC\u0005\u0002P\u0002\t\n\u0011\"\u0001\u0002R\"I\u0011Q\u001b\u0001\u0012\u0002\u0013\u0005\u0011q\u001b\u0005\n\u00037\u0004\u0011\u0013!C\u0001\u0003;D\u0011\"!9\u0001#\u0003%\t!a9\t\u0013\u0005\u001d\b!!A\u0005B\u0005%\b\u0002CA{\u0001\u0005\u0005I\u0011A.\t\u0013\u0005]\b!!A\u0005\u0002\u0005e\b\"\u0003B\u0003\u0001\u0005\u0005I\u0011\tB\u0004\u0011%\u0011)\u0002AA\u0001\n\u0003\u00119\u0002C\u0005\u0003\u001c\u0001\t\t\u0011\"\u0011\u0003\u001e!I!\u0011\u0005\u0001\u0002\u0002\u0013\u0005#1\u0005\u0005\n\u0005K\u0001\u0011\u0011!C!\u0005OA\u0011B!\u000b\u0001\u0003\u0003%\tEa\u000b\b\u0013\tmr'!A\t\u0002\tub\u0001\u0003\u001c8\u0003\u0003E\tAa\u0010\t\u000f\u0005u\u0001\u0006\"\u0001\u0003X!I!Q\u0005\u0015\u0002\u0002\u0013\u0015#q\u0005\u0005\n\u00053B\u0013\u0011!CA\u00057B\u0011B!\u001b)#\u0003%\t!a3\t\u0013\t-\u0004&%A\u0005\u0002\u0005]\u0007\"\u0003B7QE\u0005I\u0011AAo\u0011%\u0011y\u0007KI\u0001\n\u0003\t\u0019\u000fC\u0005\u0003r!\n\t\u0011\"!\u0003t!I!\u0011\u0011\u0015\u0012\u0002\u0013\u0005\u00111\u001a\u0005\n\u0005\u0007C\u0013\u0013!C\u0001\u0003/D\u0011B!\")#\u0003%\t!!8\t\u0013\t\u001d\u0005&%A\u0005\u0002\u0005\r\b\"\u0003BEQ\u0005\u0005I\u0011\u0002BF\u0005}\u00196-\u0019;uKJ<\u0015\r\u001e5fe\u001aK'o\u001d;D_6\u0004H.\u001a;fIB{w\u000e\u001c\u0006\u0003qe\nqA]8vi&twMC\u0001;\u0003\u0011\t7n[1\u0004\u0001M1\u0001!P\"H\u0017:\u0003\"AP!\u000e\u0003}R\u0011\u0001Q\u0001\u0006g\u000e\fG.Y\u0005\u0003\u0005~\u0012a!\u00118z%\u00164\u0007C\u0001#F\u001b\u00059\u0014B\u0001$8\u0005\u0011\u0001vn\u001c7\u0011\u0007\u0011C%*\u0003\u0002Jo\t9\u0002k\\8m\u001fZ,'O]5eKVs7/\u001a;D_:4\u0017n\u001a\t\u0003\t\u0002\u0001\"A\u0010'\n\u00055{$a\u0002)s_\u0012,8\r\u001e\t\u0003\u001f^s!\u0001U+\u000f\u0005E#V\"\u0001*\u000b\u0005M[\u0014A\u0002\u001fs_>$h(C\u0001A\u0013\t1v(A\u0004qC\u000e\\\u0017mZ3\n\u0005aK&\u0001D*fe&\fG.\u001b>bE2,'B\u0001,@\u00035q'o\u00144J]N$\u0018M\\2fgV\tA\f\u0005\u0002?;&\u0011al\u0010\u0002\u0004\u0013:$\u0018A\u00048s\u001f\u001aLen\u001d;b]\u000e,7\u000fI\u0001\be\u0016\u001c\u0018N_3s+\u0005\u0011\u0007c\u0001 dK&\u0011Am\u0010\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005\u00113\u0017BA48\u0005\u001d\u0011Vm]5{KJ\f\u0001B]3tSj,'\u000fI\u0001\u0007o&$\b.\u001b8\u0016\u0003-\u0004\"\u0001\\9\u000e\u00035T!A\\8\u0002\u0011\u0011,(/\u0019;j_:T!\u0001] \u0002\u0015\r|gnY;se\u0016tG/\u0003\u0002s[\nqa)\u001b8ji\u0016$UO]1uS>t\u0017aB<ji\"Lg\u000eI\u0001\u0013gV\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u00170F\u0001w!\t9(0D\u0001y\u0015\tI\u0018(A\u0003bGR|'/\u0003\u0002|q\n\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0003M\u0019X\u000f]3sm&\u001cxN]*ue\u0006$XmZ=!\u0003A\u0011x.\u001e;fe\u0012K7\u000f]1uG\",'/F\u0001\u0000!\u0011\t\t!!\u0003\u000f\t\u0005\r\u0011Q\u0001\t\u0003#~J1!a\u0002@\u0003\u0019\u0001&/\u001a3fM&!\u00111BA\u0007\u0005\u0019\u0019FO]5oO*\u0019\u0011qA \u0002#I|W\u000f^3s\t&\u001c\b/\u0019;dQ\u0016\u0014\b%A\tvg\u0016\u0004vn\u001c7ESN\u0004\u0018\r^2iKJ,\"!!\u0006\u0011\u0007y\n9\"C\u0002\u0002\u001a}\u0012qAQ8pY\u0016\fg.\u0001\nvg\u0016\u0004vn\u001c7ESN\u0004\u0018\r^2iKJ\u0004\u0013A\u0002\u001fj]&$h\bF\u0007K\u0003C\t\u0019#!\n\u0002(\u0005%\u00121\u0006\u0005\u000656\u0001\r\u0001\u0018\u0005\bA6\u0001\n\u00111\u0001c\u0011\u0015IW\u00021\u0001l\u0011\u001d!X\u0002%AA\u0002YDq!`\u0007\u0011\u0002\u0003\u0007q\u0010C\u0005\u0002\u00125\u0001\n\u00111\u0001\u0002\u0016Q\u0019!*a\f\t\u000f\u0005Eb\u00021\u0001\u00024\u000511m\u001c8gS\u001e\u0004B!!\u000e\u0002B5\u0011\u0011q\u0007\u0006\u0005\u0003c\tID\u0003\u0003\u0002<\u0005u\u0012\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0005\u0005}\u0012aA2p[&!\u00111IA\u001c\u0005\u0019\u0019uN\u001c4jOR)!*a\u0012\u0002L!1\u0011\u0011J\bA\u0002q\u000b!A\u001c:\t\u000b%|\u0001\u0019A6\u0015\u000b)\u000by%!\u0015\t\r\u0005%\u0003\u00031\u0001]\u0011\u0019I\u0007\u00031\u0001\u0002TA!\u0011QKA0\u001b\t\t9F\u0003\u0003\u0002Z\u0005m\u0013\u0001\u0002;j[\u0016T!!!\u0018\u0002\t)\fg/Y\u0005\u0005\u0003C\n9F\u0001\u0005EkJ\fG/[8o\u00031\u0019'/Z1uKJ{W\u000f^3s)\u0011\t9'!\u001c\u0011\u0007\u0011\u000bI'C\u0002\u0002l]\u0012aAU8vi\u0016\u0014\bbBA8#\u0001\u0007\u0011\u0011O\u0001\u0007gf\u001cH/Z7\u0011\u0007]\f\u0019(C\u0002\u0002va\u00141\"Q2u_J\u001c\u0016p\u001d;f[R\u0019A,!\u001f\t\u000f\u0005m$\u00031\u0001\u0002r\u0005\u00191/_:\u0002-]LG\u000f[*va\u0016\u0014h/[:peN#(/\u0019;fOf$2ASAA\u0011\u0019\t\u0019i\u0005a\u0001m\u0006A1\u000f\u001e:bi\u0016<\u00170A\u0006xSRD'+Z:ju\u0016\u0014Hc\u0001&\u0002\n\")\u0001\r\u0006a\u0001K\u0006qq/\u001b;i\t&\u001c\b/\u0019;dQ\u0016\u0014Hc\u0001&\u0002\u0010\"1\u0011\u0011S\u000bA\u0002}\fA\u0002Z5ta\u0006$8\r[3s\u0013\u0012\fAb^5uQ\u001a\u000bG\u000e\u001c2bG.$B!a&\u0002\u001eB\u0019A)!'\n\u0007\u0005muG\u0001\u0007S_V$XM]\"p]\u001aLw\rC\u0004\u0002 Z\u0001\r!a&\u0002\u000b=$\b.\u001a:\u0002\t\r|\u0007/\u001f\u000b\u000e\u0015\u0006\u0015\u0016qUAU\u0003W\u000bi+a,\t\u000fi;\u0002\u0013!a\u00019\"9\u0001m\u0006I\u0001\u0002\u0004\u0011\u0007bB5\u0018!\u0003\u0005\ra\u001b\u0005\bi^\u0001\n\u00111\u0001w\u0011\u001dix\u0003%AA\u0002}D\u0011\"!\u0005\u0018!\u0003\u0005\r!!\u0006\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011Q\u0017\u0016\u00049\u0006]6FAA]!\u0011\tY,!2\u000e\u0005\u0005u&\u0002BA`\u0003\u0003\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005\rw(\u0001\u0006b]:|G/\u0019;j_:LA!a2\u0002>\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011\u0011Q\u001a\u0016\u0004E\u0006]\u0016AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0003'T3a[A\\\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"!!7+\u0007Y\f9,\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0016\u0005\u0005}'fA@\u00028\u0006q1m\u001c9zI\u0011,g-Y;mi\u00122TCAAsU\u0011\t)\"a.\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\tY\u000f\u0005\u0003\u0002n\u0006MXBAAx\u0015\u0011\t\t0a\u0017\u0002\t1\fgnZ\u0005\u0005\u0003\u0017\ty/\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005m(\u0011\u0001\t\u0004}\u0005u\u0018bAA\u0000\u007f\t\u0019\u0011I\\=\t\u0011\t\r\u0001%!AA\u0002q\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B\u0005!\u0019\u0011YA!\u0005\u0002|6\u0011!Q\u0002\u0006\u0004\u0005\u001fy\u0014AC2pY2,7\r^5p]&!!1\u0003B\u0007\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005U!\u0011\u0004\u0005\n\u0005\u0007\u0011\u0013\u0011!a\u0001\u0003w\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111\u001eB\u0010\u0011!\u0011\u0019aIA\u0001\u0002\u0004a\u0016\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003q\u000b\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003W\fa!Z9vC2\u001cH\u0003BA\u000b\u0005[A\u0011Ba\u0001'\u0003\u0003\u0005\r!a?)\u000f\u0001\u0011\tDa\u000e\u0003:A\u0019aHa\r\n\u0007\tUrH\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t\u0011!A\u0010TG\u0006$H/\u001a:HCRDWM\u001d$jeN$8i\\7qY\u0016$X\r\u001a)p_2\u0004\"\u0001\u0012\u0015\u0014\u000b!\u0012\tE!\u0014\u0011\u0019\t\r#\u0011\n/cWZ|\u0018Q\u0003&\u000e\u0005\t\u0015#b\u0001B$\u007f\u00059!/\u001e8uS6,\u0017\u0002\u0002B&\u0005\u000b\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c87!\u0011\u0011yE!\u0016\u000e\u0005\tE#\u0002\u0002B*\u00037\n!![8\n\u0007a\u0013\t\u0006\u0006\u0002\u0003>\u0005)\u0011\r\u001d9msRi!J!\u0018\u0003`\t\u0005$1\rB3\u0005OBQAW\u0016A\u0002qCq\u0001Y\u0016\u0011\u0002\u0003\u0007!\rC\u0003jW\u0001\u00071\u000eC\u0004uWA\u0005\t\u0019\u0001<\t\u000fu\\\u0003\u0013!a\u0001\u007f\"I\u0011\u0011C\u0016\u0011\u0002\u0003\u0007\u0011QC\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$C'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00136\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u00122\u0014aB;oCB\u0004H.\u001f\u000b\u0005\u0005k\u0012i\b\u0005\u0003?G\n]\u0004C\u0003 \u0003zq\u00137N^@\u0002\u0016%\u0019!1P \u0003\rQ+\b\u000f\\37\u0011!\u0011y\bMA\u0001\u0002\u0004Q\u0015a\u0001=%a\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uII\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\"\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$S'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEN\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005\u001b\u0003B!!<\u0003\u0010&!!\u0011SAx\u0005\u0019y%M[3di\u0002")
public final class ScatterGatherFirstCompletedPool
implements PoolOverrideUnsetConfig<ScatterGatherFirstCompletedPool>,
Product {
    private static final long serialVersionUID = 1L;
    private final int nrOfInstances;
    private final Option<Resizer> resizer;
    private final FiniteDuration within;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;
    private final boolean usePoolDispatcher;

    public static boolean $lessinit$greater$default$6() {
        return ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$6();
    }

    public static String $lessinit$greater$default$5() {
        return ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$5();
    }

    public static SupervisorStrategy $lessinit$greater$default$4() {
        return ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$4();
    }

    public static Option<Resizer> $lessinit$greater$default$2() {
        return ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple6<Object, Option<Resizer>, FiniteDuration, SupervisorStrategy, String, Object>> unapply(ScatterGatherFirstCompletedPool x$0) {
        return ScatterGatherFirstCompletedPool$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$6() {
        return ScatterGatherFirstCompletedPool$.MODULE$.apply$default$6();
    }

    public static String apply$default$5() {
        return ScatterGatherFirstCompletedPool$.MODULE$.apply$default$5();
    }

    public static SupervisorStrategy apply$default$4() {
        return ScatterGatherFirstCompletedPool$.MODULE$.apply$default$4();
    }

    public static Option<Resizer> apply$default$2() {
        return ScatterGatherFirstCompletedPool$.MODULE$.apply$default$2();
    }

    public static ScatterGatherFirstCompletedPool apply(int nrOfInstances, Option<Resizer> resizer, FiniteDuration within, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return ScatterGatherFirstCompletedPool$.MODULE$.apply(nrOfInstances, resizer, within, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public static Function1<Tuple6<Object, Option<Resizer>, FiniteDuration, SupervisorStrategy, String, Object>, ScatterGatherFirstCompletedPool> tupled() {
        return ScatterGatherFirstCompletedPool$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Option<Resizer>, Function1<FiniteDuration, Function1<SupervisorStrategy, Function1<String, Function1<Object, ScatterGatherFirstCompletedPool>>>>>> curried() {
        return ScatterGatherFirstCompletedPool$.MODULE$.curried();
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

    public FiniteDuration within() {
        return this.within;
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
        return new Router(new ScatterGatherFirstCompletedRoutingLogic(this.within()));
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return this.nrOfInstances();
    }

    @Override
    public ScatterGatherFirstCompletedPool withSupervisorStrategy(SupervisorStrategy strategy) {
        SupervisorStrategy x$1 = strategy;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        String x$5 = this.copy$default$5();
        boolean x$6 = this.copy$default$6();
        return this.copy(x$2, x$3, x$4, x$1, x$5, x$6);
    }

    @Override
    public ScatterGatherFirstCompletedPool withResizer(Resizer resizer) {
        Some<Resizer> x$1 = new Some<Resizer>(resizer);
        int x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$3();
        SupervisorStrategy x$4 = this.copy$default$4();
        String x$5 = this.copy$default$5();
        boolean x$6 = this.copy$default$6();
        return this.copy(x$2, x$1, x$3, x$4, x$5, x$6);
    }

    public ScatterGatherFirstCompletedPool withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        SupervisorStrategy x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$6();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return this.overrideUnsetConfig(other);
    }

    public ScatterGatherFirstCompletedPool copy(int nrOfInstances, Option<Resizer> resizer, FiniteDuration within, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new ScatterGatherFirstCompletedPool(nrOfInstances, resizer, within, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public int copy$default$1() {
        return this.nrOfInstances();
    }

    public Option<Resizer> copy$default$2() {
        return this.resizer();
    }

    public FiniteDuration copy$default$3() {
        return this.within();
    }

    public SupervisorStrategy copy$default$4() {
        return this.supervisorStrategy();
    }

    public String copy$default$5() {
        return this.routerDispatcher();
    }

    public boolean copy$default$6() {
        return this.usePoolDispatcher();
    }

    @Override
    public String productPrefix() {
        return "ScatterGatherFirstCompletedPool";
    }

    @Override
    public int productArity() {
        return 6;
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
                return this.within();
            }
            case 3: {
                return this.supervisorStrategy();
            }
            case 4: {
                return this.routerDispatcher();
            }
            case 5: {
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
        return x$1 instanceof ScatterGatherFirstCompletedPool;
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
                return "within";
            }
            case 3: {
                return "supervisorStrategy";
            }
            case 4: {
                return "routerDispatcher";
            }
            case 5: {
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
        n = Statics.mix(n, Statics.anyHash(this.within()));
        n = Statics.mix(n, Statics.anyHash(this.supervisorStrategy()));
        n = Statics.mix(n, Statics.anyHash(this.routerDispatcher()));
        n = Statics.mix(n, this.usePoolDispatcher() ? 1231 : 1237);
        return Statics.finalizeHash(n, 6);
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
        if (!(object instanceof ScatterGatherFirstCompletedPool)) return false;
        boolean bl = true;
        if (!bl) return false;
        ScatterGatherFirstCompletedPool scatterGatherFirstCompletedPool = (ScatterGatherFirstCompletedPool)x$1;
        if (this.nrOfInstances() != scatterGatherFirstCompletedPool.nrOfInstances()) return false;
        if (this.usePoolDispatcher() != scatterGatherFirstCompletedPool.usePoolDispatcher()) return false;
        Option<Resizer> option = this.resizer();
        Option<Resizer> option2 = scatterGatherFirstCompletedPool.resizer();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        FiniteDuration finiteDuration = this.within();
        FiniteDuration finiteDuration2 = scatterGatherFirstCompletedPool.within();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        SupervisorStrategy supervisorStrategy = this.supervisorStrategy();
        SupervisorStrategy supervisorStrategy2 = scatterGatherFirstCompletedPool.supervisorStrategy();
        if (supervisorStrategy == null) {
            if (supervisorStrategy2 != null) {
                return false;
            }
        } else if (!supervisorStrategy.equals(supervisorStrategy2)) return false;
        String string = this.routerDispatcher();
        String string2 = scatterGatherFirstCompletedPool.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public ScatterGatherFirstCompletedPool(int nrOfInstances, Option<Resizer> resizer, FiniteDuration within, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        this.nrOfInstances = nrOfInstances;
        this.resizer = resizer;
        this.within = within;
        this.supervisorStrategy = supervisorStrategy;
        this.routerDispatcher = routerDispatcher;
        this.usePoolDispatcher = usePoolDispatcher;
        RouterConfig.$init$(this);
        Pool.$init$(this);
        PoolOverrideUnsetConfig.$init$(this);
        Product.$init$(this);
    }

    public ScatterGatherFirstCompletedPool(Config config) {
        int x$1 = config.getInt("nr-of-instances");
        FiniteDuration x$2 = Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "within");
        Option<Resizer> x$3 = Resizer$.MODULE$.fromConfig(config);
        boolean x$4 = config.hasPath("pool-dispatcher");
        SupervisorStrategy x$5 = ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$4();
        String x$6 = ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$5();
        this(x$1, x$3, x$2, x$5, x$6, x$4);
    }

    public ScatterGatherFirstCompletedPool(int nr, FiniteDuration within) {
        int x$1 = nr;
        FiniteDuration x$2 = within;
        Option<Resizer> x$3 = ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$2();
        SupervisorStrategy x$4 = ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$4();
        String x$5 = ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$5();
        boolean x$6 = ScatterGatherFirstCompletedPool$.MODULE$.$lessinit$greater$default$6();
        this(x$1, x$3, x$2, x$4, x$5, x$6);
    }

    public ScatterGatherFirstCompletedPool(int nr, Duration within) {
        this(nr, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(within)));
    }
}

