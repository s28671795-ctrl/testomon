/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.routing.ConsistentHashingPool$;
import akka.routing.ConsistentHashingRouter;
import akka.routing.ConsistentHashingRouter$;
import akka.routing.ConsistentHashingRoutingLogic;
import akka.routing.FromConfig;
import akka.routing.NoRouter;
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
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple7;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t]f\u0001\u0002 @\u0005\u0012C\u0001B\u0019\u0001\u0003\u0016\u0004%\ta\u0019\u0005\tO\u0002\u0011\t\u0012)A\u0005I\"A\u0001\u000e\u0001BK\u0002\u0013\u0005\u0013\u000e\u0003\u0005q\u0001\tE\t\u0015!\u0003k\u0011!\t\bA!f\u0001\n\u0003\u0019\u0007\u0002\u0003:\u0001\u0005#\u0005\u000b\u0011\u00023\t\u0011M\u0004!Q3A\u0005\u0002QD\u0001\u0002 \u0001\u0003\u0012\u0003\u0006I!\u001e\u0005\t{\u0002\u0011)\u001a!C!}\"I\u00111\u0002\u0001\u0003\u0012\u0003\u0006Ia \u0005\u000b\u0003\u001b\u0001!Q3A\u0005B\u0005=\u0001BCA\u0011\u0001\tE\t\u0015!\u0003\u0002\u0012!Q\u00111\u0005\u0001\u0003\u0016\u0004%\t%!\n\t\u0015\u00055\u0002A!E!\u0002\u0013\t9\u0003C\u0004\u00020\u0001!\t!!\r\t\u000f\u0005=\u0002\u0001\"\u0001\u0002B!9\u0011q\u0006\u0001\u0005\u0002\u0005e\u0003bBA0\u0001\u0011\u0005\u0013\u0011\r\u0005\u0007E\u0002!\t%a\u001d\t\u000f\u0005e\u0004\u0001\"\u0001\u0002|!9\u0011\u0011\u0011\u0001\u0005\u0002\u0005\r\u0005bBAD\u0001\u0011\u0005\u0011\u0011\u0012\u0005\b\u0003\u001f\u0003A\u0011AAI\u0011\u001d\t9\n\u0001C\u0001\u00033Cq!!*\u0001\t\u0003\n9\u000bC\u0005\u00024\u0002\t\t\u0011\"\u0001\u00026\"I\u0011Q\u0019\u0001\u0012\u0002\u0013\u0005\u0011q\u0019\u0005\n\u0003;\u0004\u0011\u0013!C\u0001\u0003?D\u0011\"a9\u0001#\u0003%\t!a2\t\u0013\u0005\u0015\b!%A\u0005\u0002\u0005\u001d\b\"CAv\u0001E\u0005I\u0011AAw\u0011%\t\t\u0010AI\u0001\n\u0003\t\u0019\u0010C\u0005\u0002x\u0002\t\n\u0011\"\u0001\u0002z\"I\u0011Q \u0001\u0002\u0002\u0013\u0005\u0013q \u0005\t\u0005\u001f\u0001\u0011\u0011!C\u0001G\"I!\u0011\u0003\u0001\u0002\u0002\u0013\u0005!1\u0003\u0005\n\u0005?\u0001\u0011\u0011!C!\u0005CA\u0011Ba\f\u0001\u0003\u0003%\tA!\r\t\u0013\tU\u0002!!A\u0005B\t]\u0002\"\u0003B\u001e\u0001\u0005\u0005I\u0011\tB\u001f\u0011%\u0011y\u0004AA\u0001\n\u0003\u0012\t\u0005C\u0005\u0003D\u0001\t\t\u0011\"\u0011\u0003F\u001dI!QK \u0002\u0002#\u0005!q\u000b\u0004\t}}\n\t\u0011#\u0001\u0003Z!9\u0011q\u0006\u0017\u0005\u0002\tE\u0004\"\u0003B Y\u0005\u0005IQ\tB!\u0011%\u0011\u0019\bLA\u0001\n\u0003\u0013)\bC\u0005\u0003\u00062\n\n\u0011\"\u0001\u0002`\"I!q\u0011\u0017\u0012\u0002\u0013\u0005\u0011q\u0019\u0005\n\u0005\u0013c\u0013\u0013!C\u0001\u0003OD\u0011Ba#-#\u0003%\t!!<\t\u0013\t5E&%A\u0005\u0002\u0005M\b\"\u0003BHYE\u0005I\u0011AA}\u0011%\u0011\t\nLA\u0001\n\u0003\u0013\u0019\nC\u0005\u0003\"2\n\n\u0011\"\u0001\u0002`\"I!1\u0015\u0017\u0012\u0002\u0013\u0005\u0011q\u0019\u0005\n\u0005Kc\u0013\u0013!C\u0001\u0003OD\u0011Ba*-#\u0003%\t!!<\t\u0013\t%F&%A\u0005\u0002\u0005M\b\"\u0003BVYE\u0005I\u0011AA}\u0011%\u0011i\u000bLA\u0001\n\u0013\u0011yKA\u000bD_:\u001c\u0018n\u001d;f]RD\u0015m\u001d5j]\u001e\u0004vn\u001c7\u000b\u0005\u0001\u000b\u0015a\u0002:pkRLgn\u001a\u0006\u0002\u0005\u0006!\u0011m[6b\u0007\u0001\u0019b\u0001A#L\u001fN3\u0006C\u0001$J\u001b\u00059%\"\u0001%\u0002\u000bM\u001c\u0017\r\\1\n\u0005);%AB!osJ+g\r\u0005\u0002M\u001b6\tq(\u0003\u0002O\u007f\t!\u0001k\\8m!\ra\u0005KU\u0005\u0003#~\u0012q\u0003U8pY>3XM\u001d:jI\u0016,fn]3u\u0007>tg-[4\u0011\u00051\u0003\u0001C\u0001$U\u0013\t)vIA\u0004Qe>$Wo\u0019;\u0011\u0005]{fB\u0001-^\u001d\tIF,D\u0001[\u0015\tY6)\u0001\u0004=e>|GOP\u0005\u0002\u0011&\u0011alR\u0001\ba\u0006\u001c7.Y4f\u0013\t\u0001\u0017M\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002_\u000f\u0006iaN](g\u0013:\u001cH/\u00198dKN,\u0012\u0001\u001a\t\u0003\r\u0016L!AZ$\u0003\u0007%sG/\u0001\boe>3\u0017J\\:uC:\u001cWm\u001d\u0011\u0002\u000fI,7/\u001b>feV\t!\u000eE\u0002GW6L!\u0001\\$\u0003\r=\u0003H/[8o!\tae.\u0003\u0002p\u007f\t9!+Z:ju\u0016\u0014\u0018\u0001\u0003:fg&TXM\u001d\u0011\u0002%YL'\u000f^;bY:{G-Z:GC\u000e$xN]\u0001\u0014m&\u0014H/^1m\u001d>$Wm\u001d$bGR|'\u000fI\u0001\fQ\u0006\u001c\b.T1qa&tw-F\u0001v!\t1\u0018P\u0004\u0002Mo&\u0011\u0001pP\u0001\u0018\u0007>t7/[:uK:$\b*Y:iS:<'k\\;uKJL!A_>\u0003+\r{gn]5ti\u0016tG\u000fS1tQ6\u000b\u0007\u000f]5oO*\u0011\u0001pP\u0001\rQ\u0006\u001c\b.T1qa&tw\rI\u0001\u0013gV\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u00170F\u0001\u0000!\u0011\t\t!a\u0002\u000e\u0005\u0005\r!bAA\u0003\u0003\u0006)\u0011m\u0019;pe&!\u0011\u0011BA\u0002\u0005I\u0019V\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0002'M,\b/\u001a:wSN|'o\u0015;sCR,w-\u001f\u0011\u0002!I|W\u000f^3s\t&\u001c\b/\u0019;dQ\u0016\u0014XCAA\t!\u0011\t\u0019\"a\u0007\u000f\t\u0005U\u0011q\u0003\t\u00033\u001eK1!!\u0007H\u0003\u0019\u0001&/\u001a3fM&!\u0011QDA\u0010\u0005\u0019\u0019FO]5oO*\u0019\u0011\u0011D$\u0002#I|W\u000f^3s\t&\u001c\b/\u0019;dQ\u0016\u0014\b%A\tvg\u0016\u0004vn\u001c7ESN\u0004\u0018\r^2iKJ,\"!a\n\u0011\u0007\u0019\u000bI#C\u0002\u0002,\u001d\u0013qAQ8pY\u0016\fg.\u0001\nvg\u0016\u0004vn\u001c7ESN\u0004\u0018\r^2iKJ\u0004\u0013A\u0002\u001fj]&$h\bF\bS\u0003g\t)$a\u000e\u0002:\u0005m\u0012QHA \u0011\u0015\u0011w\u00021\u0001e\u0011\u001dAw\u0002%AA\u0002)Dq!]\b\u0011\u0002\u0003\u0007A\rC\u0004t\u001fA\u0005\t\u0019A;\t\u000fu|\u0001\u0013!a\u0001\u007f\"I\u0011QB\b\u0011\u0002\u0003\u0007\u0011\u0011\u0003\u0005\n\u0003Gy\u0001\u0013!a\u0001\u0003O!2AUA\"\u0011\u001d\t)\u0005\u0005a\u0001\u0003\u000f\naaY8oM&<\u0007\u0003BA%\u0003+j!!a\u0013\u000b\t\u0005\u0015\u0013Q\n\u0006\u0005\u0003\u001f\n\t&\u0001\u0005usB,7/\u00194f\u0015\t\t\u0019&A\u0002d_6LA!a\u0016\u0002L\t11i\u001c8gS\u001e$2AUA.\u0011\u0019\ti&\u0005a\u0001I\u0006\u0011aN]\u0001\rGJ,\u0017\r^3S_V$XM\u001d\u000b\u0005\u0003G\nI\u0007E\u0002M\u0003KJ1!a\u001a@\u0005\u0019\u0011v.\u001e;fe\"9\u00111\u000e\nA\u0002\u00055\u0014AB:zgR,W\u000e\u0005\u0003\u0002\u0002\u0005=\u0014\u0002BA9\u0003\u0007\u00111\"Q2u_J\u001c\u0016p\u001d;f[R\u0019A-!\u001e\t\u000f\u0005]4\u00031\u0001\u0002n\u0005\u00191/_:\u0002-]LG\u000f[*va\u0016\u0014h/[:peN#(/\u0019;fOf$2AUA?\u0011\u0019\ty\b\u0006a\u0001\u007f\u0006A1\u000f\u001e:bi\u0016<\u00170A\u0006xSRD'+Z:ju\u0016\u0014Hc\u0001*\u0002\u0006\")\u0001.\u0006a\u0001[\u0006qq/\u001b;i\t&\u001c\b/\u0019;dQ\u0016\u0014Hc\u0001*\u0002\f\"9\u0011Q\u0012\fA\u0002\u0005E\u0011\u0001\u00043jgB\fGo\u00195fe&#\u0017AF<ji\"4\u0016N\u001d;vC2tu\u000eZ3t\r\u0006\u001cGo\u001c:\u0015\u0007I\u000b\u0019\n\u0003\u0004\u0002\u0016^\u0001\r\u0001Z\u0001\u0007m:|G-Z:\u0002\u001d]LG\u000f\u001b%bg\"l\u0015\r\u001d9feR\u0019!+a'\t\u000f\u0005u\u0005\u00041\u0001\u0002 \u00061Q.\u00199qKJ\u00042A^AQ\u0013\r\t\u0019k\u001f\u0002\u0015\u0007>t7/[:uK:$\b*Y:i\u001b\u0006\u0004\b/\u001a:\u0002\u0019]LG\u000f\u001b$bY2\u0014\u0017mY6\u0015\t\u0005%\u0016q\u0016\t\u0004\u0019\u0006-\u0016bAAW\u007f\ta!k\\;uKJ\u001cuN\u001c4jO\"9\u0011\u0011W\rA\u0002\u0005%\u0016!B8uQ\u0016\u0014\u0018\u0001B2paf$rBUA\\\u0003s\u000bY,!0\u0002@\u0006\u0005\u00171\u0019\u0005\bEj\u0001\n\u00111\u0001e\u0011\u001dA'\u0004%AA\u0002)Dq!\u001d\u000e\u0011\u0002\u0003\u0007A\rC\u0004t5A\u0005\t\u0019A;\t\u000fuT\u0002\u0013!a\u0001\u007f\"I\u0011Q\u0002\u000e\u0011\u0002\u0003\u0007\u0011\u0011\u0003\u0005\n\u0003GQ\u0002\u0013!a\u0001\u0003O\tabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002J*\u001aA-a3,\u0005\u00055\u0007\u0003BAh\u00033l!!!5\u000b\t\u0005M\u0017Q[\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a6H\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u00037\f\tNA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002b*\u001a!.a3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%g\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\"TCAAuU\r)\u00181Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+\t\tyOK\u0002\u0000\u0003\u0017\fabY8qs\u0012\"WMZ1vYR$c'\u0006\u0002\u0002v*\"\u0011\u0011CAf\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*\"!a?+\t\u0005\u001d\u00121Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\t\u0005\u0001\u0003\u0002B\u0002\u0005\u001bi!A!\u0002\u000b\t\t\u001d!\u0011B\u0001\u0005Y\u0006twM\u0003\u0002\u0003\f\u0005!!.\u0019<b\u0013\u0011\tiB!\u0002\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!!Q\u0003B\u000e!\r1%qC\u0005\u0004\u000539%aA!os\"A!Q\u0004\u0013\u0002\u0002\u0003\u0007A-A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0005G\u0001bA!\n\u0003,\tUQB\u0001B\u0014\u0015\r\u0011IcR\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002B\u0017\u0005O\u0011\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011q\u0005B\u001a\u0011%\u0011iBJA\u0001\u0002\u0004\u0011)\"\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003\u0002B\u0001\u0005sA\u0001B!\b(\u0003\u0003\u0005\r\u0001Z\u0001\tQ\u0006\u001c\bnQ8eKR\tA-\u0001\u0005u_N#(/\u001b8h)\t\u0011\t!\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003O\u00119\u0005C\u0005\u0003\u001e)\n\t\u00111\u0001\u0003\u0016!:\u0001Aa\u0013\u0003R\tM\u0003c\u0001$\u0003N%\u0019!qJ$\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001\u0002+\r{gn]5ti\u0016tG\u000fS1tQ&tw\rU8pYB\u0011A\nL\n\u0006Y\tm#q\r\t\u000f\u0005;\u0012\u0019\u0007\u001a6ek~\f\t\"a\nS\u001b\t\u0011yFC\u0002\u0003b\u001d\u000bqA];oi&lW-\u0003\u0003\u0003f\t}#!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ooA!!\u0011\u000eB8\u001b\t\u0011YG\u0003\u0003\u0003n\t%\u0011AA5p\u0013\r\u0001'1\u000e\u000b\u0003\u0005/\nQ!\u00199qYf$rB\u0015B<\u0005s\u0012YH! \u0003\u0000\t\u0005%1\u0011\u0005\u0006E>\u0002\r\u0001\u001a\u0005\bQ>\u0002\n\u00111\u0001k\u0011\u001d\tx\u0006%AA\u0002\u0011Dqa]\u0018\u0011\u0002\u0003\u0007Q\u000fC\u0004~_A\u0005\t\u0019A@\t\u0013\u00055q\u0006%AA\u0002\u0005E\u0001\"CA\u0012_A\u0005\t\u0019AA\u0014\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u0012\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIQ\nq\"\u00199qYf$C-\u001a4bk2$H%N\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%m\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$s'A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\tU%Q\u0014\t\u0005\r.\u00149\n\u0005\u0007G\u00053#'\u000eZ;\u0000\u0003#\t9#C\u0002\u0003\u001c\u001e\u0013a\u0001V;qY\u0016<\u0004\u0002\u0003BPm\u0005\u0005\t\u0019\u0001*\u0002\u0007a$\u0003'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001a\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00135\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%k\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIY\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012:\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001BY!\u0011\u0011\u0019Aa-\n\t\tU&Q\u0001\u0002\u0007\u001f\nTWm\u0019;")
public final class ConsistentHashingPool
implements PoolOverrideUnsetConfig<ConsistentHashingPool>,
Product {
    private static final long serialVersionUID = 1L;
    private final int nrOfInstances;
    private final Option<Resizer> resizer;
    private final int virtualNodesFactor;
    private final PartialFunction<Object, Object> hashMapping;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;
    private final boolean usePoolDispatcher;

    public static boolean $lessinit$greater$default$7() {
        return ConsistentHashingPool$.MODULE$.$lessinit$greater$default$7();
    }

    public static String $lessinit$greater$default$6() {
        return ConsistentHashingPool$.MODULE$.$lessinit$greater$default$6();
    }

    public static SupervisorStrategy $lessinit$greater$default$5() {
        return ConsistentHashingPool$.MODULE$.$lessinit$greater$default$5();
    }

    public static PartialFunction<Object, Object> $lessinit$greater$default$4() {
        return ConsistentHashingPool$.MODULE$.$lessinit$greater$default$4();
    }

    public static int $lessinit$greater$default$3() {
        return ConsistentHashingPool$.MODULE$.$lessinit$greater$default$3();
    }

    public static Option<Resizer> $lessinit$greater$default$2() {
        return ConsistentHashingPool$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple7<Object, Option<Resizer>, Object, PartialFunction<Object, Object>, SupervisorStrategy, String, Object>> unapply(ConsistentHashingPool x$0) {
        return ConsistentHashingPool$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$7() {
        return ConsistentHashingPool$.MODULE$.apply$default$7();
    }

    public static String apply$default$6() {
        return ConsistentHashingPool$.MODULE$.apply$default$6();
    }

    public static SupervisorStrategy apply$default$5() {
        return ConsistentHashingPool$.MODULE$.apply$default$5();
    }

    public static PartialFunction<Object, Object> apply$default$4() {
        return ConsistentHashingPool$.MODULE$.apply$default$4();
    }

    public static int apply$default$3() {
        return ConsistentHashingPool$.MODULE$.apply$default$3();
    }

    public static Option<Resizer> apply$default$2() {
        return ConsistentHashingPool$.MODULE$.apply$default$2();
    }

    public static ConsistentHashingPool apply(int nrOfInstances, Option<Resizer> resizer, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return ConsistentHashingPool$.MODULE$.apply(nrOfInstances, resizer, virtualNodesFactor, hashMapping, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public static Function1<Tuple7<Object, Option<Resizer>, Object, PartialFunction<Object, Object>, SupervisorStrategy, String, Object>, ConsistentHashingPool> tupled() {
        return ConsistentHashingPool$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Option<Resizer>, Function1<Object, Function1<PartialFunction<Object, Object>, Function1<SupervisorStrategy, Function1<String, Function1<Object, ConsistentHashingPool>>>>>>> curried() {
        return ConsistentHashingPool$.MODULE$.curried();
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

    public int virtualNodesFactor() {
        return this.virtualNodesFactor;
    }

    public PartialFunction<Object, Object> hashMapping() {
        return this.hashMapping;
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
        return new Router(new ConsistentHashingRoutingLogic(system, this.virtualNodesFactor(), this.hashMapping()));
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return this.nrOfInstances();
    }

    @Override
    public ConsistentHashingPool withSupervisorStrategy(SupervisorStrategy strategy) {
        SupervisorStrategy x$1 = strategy;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        PartialFunction<Object, Object> x$5 = this.copy$default$4();
        String x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6, x$7);
    }

    @Override
    public ConsistentHashingPool withResizer(Resizer resizer) {
        Some<Resizer> x$1 = new Some<Resizer>(resizer);
        int x$2 = this.copy$default$1();
        int x$3 = this.copy$default$3();
        PartialFunction<Object, Object> x$4 = this.copy$default$4();
        SupervisorStrategy x$5 = this.copy$default$5();
        String x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        return this.copy(x$2, x$1, x$3, x$4, x$5, x$6, x$7);
    }

    public ConsistentHashingPool withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        PartialFunction<Object, Object> x$5 = this.copy$default$4();
        SupervisorStrategy x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$7();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7);
    }

    public ConsistentHashingPool withVirtualNodesFactor(int vnodes) {
        int x$1 = vnodes;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        PartialFunction<Object, Object> x$4 = this.copy$default$4();
        SupervisorStrategy x$5 = this.copy$default$5();
        String x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        return this.copy(x$2, x$3, x$1, x$4, x$5, x$6, x$7);
    }

    public ConsistentHashingPool withHashMapper(ConsistentHashingRouter.ConsistentHashMapper mapper2) {
        PartialFunction<Object, Object> x$1 = ConsistentHashingRouter$.MODULE$.hashMappingAdapter(mapper2);
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        SupervisorStrategy x$5 = this.copy$default$5();
        String x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        return this.copy(x$2, x$3, x$4, x$1, x$5, x$6, x$7);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        RouterConfig routerConfig = other;
        if (routerConfig instanceof FromConfig ? true : routerConfig instanceof NoRouter) {
            return this.overrideUnsetConfig(other);
        }
        if (routerConfig instanceof ConsistentHashingPool) {
            ConsistentHashingPool consistentHashingPool = (ConsistentHashingPool)routerConfig;
            PartialFunction<Object, Object> x$1 = consistentHashingPool.hashMapping();
            int x$2 = this.copy$default$1();
            Option<Resizer> x$3 = this.copy$default$2();
            int x$4 = this.copy$default$3();
            SupervisorStrategy x$5 = this.copy$default$5();
            String x$6 = this.copy$default$6();
            boolean x$7 = this.copy$default$7();
            return this.copy(x$2, x$3, x$4, x$1, x$5, x$6, x$7).overrideUnsetConfig(other);
        }
        throw new IllegalArgumentException(StringOps$.MODULE$.format$extension(Predef$.MODULE$.augmentString("Expected ConsistentHashingPool, got [%s]"), ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{other})));
    }

    public ConsistentHashingPool copy(int nrOfInstances, Option<Resizer> resizer, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new ConsistentHashingPool(nrOfInstances, resizer, virtualNodesFactor, hashMapping, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public int copy$default$1() {
        return this.nrOfInstances();
    }

    public Option<Resizer> copy$default$2() {
        return this.resizer();
    }

    public int copy$default$3() {
        return this.virtualNodesFactor();
    }

    public PartialFunction<Object, Object> copy$default$4() {
        return this.hashMapping();
    }

    public SupervisorStrategy copy$default$5() {
        return this.supervisorStrategy();
    }

    public String copy$default$6() {
        return this.routerDispatcher();
    }

    public boolean copy$default$7() {
        return this.usePoolDispatcher();
    }

    @Override
    public String productPrefix() {
        return "ConsistentHashingPool";
    }

    @Override
    public int productArity() {
        return 7;
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
                return BoxesRunTime.boxToInteger(this.virtualNodesFactor());
            }
            case 3: {
                return this.hashMapping();
            }
            case 4: {
                return this.supervisorStrategy();
            }
            case 5: {
                return this.routerDispatcher();
            }
            case 6: {
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
        return x$1 instanceof ConsistentHashingPool;
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
                return "virtualNodesFactor";
            }
            case 3: {
                return "hashMapping";
            }
            case 4: {
                return "supervisorStrategy";
            }
            case 5: {
                return "routerDispatcher";
            }
            case 6: {
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
        n = Statics.mix(n, this.virtualNodesFactor());
        n = Statics.mix(n, Statics.anyHash(this.hashMapping()));
        n = Statics.mix(n, Statics.anyHash(this.supervisorStrategy()));
        n = Statics.mix(n, Statics.anyHash(this.routerDispatcher()));
        n = Statics.mix(n, this.usePoolDispatcher() ? 1231 : 1237);
        return Statics.finalizeHash(n, 7);
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
        if (!(object instanceof ConsistentHashingPool)) return false;
        boolean bl = true;
        if (!bl) return false;
        ConsistentHashingPool consistentHashingPool = (ConsistentHashingPool)x$1;
        if (this.nrOfInstances() != consistentHashingPool.nrOfInstances()) return false;
        if (this.virtualNodesFactor() != consistentHashingPool.virtualNodesFactor()) return false;
        if (this.usePoolDispatcher() != consistentHashingPool.usePoolDispatcher()) return false;
        Option<Resizer> option = this.resizer();
        Option<Resizer> option2 = consistentHashingPool.resizer();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        PartialFunction<Object, Object> partialFunction = this.hashMapping();
        PartialFunction<Object, Object> partialFunction2 = consistentHashingPool.hashMapping();
        if (partialFunction == null) {
            if (partialFunction2 != null) {
                return false;
            }
        } else if (!partialFunction.equals(partialFunction2)) return false;
        SupervisorStrategy supervisorStrategy = this.supervisorStrategy();
        SupervisorStrategy supervisorStrategy2 = consistentHashingPool.supervisorStrategy();
        if (supervisorStrategy == null) {
            if (supervisorStrategy2 != null) {
                return false;
            }
        } else if (!supervisorStrategy.equals(supervisorStrategy2)) return false;
        String string = this.routerDispatcher();
        String string2 = consistentHashingPool.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public ConsistentHashingPool(int nrOfInstances, Option<Resizer> resizer, int virtualNodesFactor, PartialFunction<Object, Object> hashMapping, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        this.nrOfInstances = nrOfInstances;
        this.resizer = resizer;
        this.virtualNodesFactor = virtualNodesFactor;
        this.hashMapping = hashMapping;
        this.supervisorStrategy = supervisorStrategy;
        this.routerDispatcher = routerDispatcher;
        this.usePoolDispatcher = usePoolDispatcher;
        RouterConfig.$init$(this);
        Pool.$init$(this);
        PoolOverrideUnsetConfig.$init$(this);
        Product.$init$(this);
    }

    public ConsistentHashingPool(Config config) {
        int x$1 = config.getInt("nr-of-instances");
        Option<Resizer> x$2 = Resizer$.MODULE$.fromConfig(config);
        boolean x$3 = config.hasPath("pool-dispatcher");
        int x$4 = ConsistentHashingPool$.MODULE$.$lessinit$greater$default$3();
        PartialFunction<Object, Object> x$5 = ConsistentHashingPool$.MODULE$.$lessinit$greater$default$4();
        SupervisorStrategy x$6 = ConsistentHashingPool$.MODULE$.$lessinit$greater$default$5();
        String x$7 = ConsistentHashingPool$.MODULE$.$lessinit$greater$default$6();
        this(x$1, x$2, x$4, x$5, x$6, x$7, x$3);
    }

    public ConsistentHashingPool(int nr) {
        this(nr, ConsistentHashingPool$.MODULE$.$lessinit$greater$default$2(), ConsistentHashingPool$.MODULE$.$lessinit$greater$default$3(), ConsistentHashingPool$.MODULE$.$lessinit$greater$default$4(), ConsistentHashingPool$.MODULE$.$lessinit$greater$default$5(), ConsistentHashingPool$.MODULE$.$lessinit$greater$default$6(), ConsistentHashingPool$.MODULE$.$lessinit$greater$default$7());
    }
}

