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
import akka.routing.TailChoppingPool$;
import akka.routing.TailChoppingRoutingLogic;
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
import scala.Tuple7;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t%f\u0001B\u001d;\u0005~B\u0001\"\u0018\u0001\u0003\u0016\u0004%\tA\u0018\u0005\tE\u0002\u0011\t\u0012)A\u0005?\"A1\r\u0001BK\u0002\u0013\u0005C\r\u0003\u0005l\u0001\tE\t\u0015!\u0003f\u0011!a\u0007A!f\u0001\n\u0003i\u0007\u0002\u0003<\u0001\u0005#\u0005\u000b\u0011\u00028\t\u0011]\u0004!Q3A\u0005\u00025D\u0001\u0002\u001f\u0001\u0003\u0012\u0003\u0006IA\u001c\u0005\ts\u0002\u0011)\u001a!C!u\"I\u00111\u0001\u0001\u0003\u0012\u0003\u0006Ia\u001f\u0005\u000b\u0003\u000b\u0001!Q3A\u0005B\u0005\u001d\u0001BCA\r\u0001\tE\t\u0015!\u0003\u0002\n!Q\u00111\u0004\u0001\u0003\u0016\u0004%\t%!\b\t\u0015\u0005\u0015\u0002A!E!\u0002\u0013\ty\u0002C\u0004\u0002(\u0001!\t!!\u000b\t\u000f\u0005\u001d\u0002\u0001\"\u0001\u0002:!9\u0011q\u0005\u0001\u0005\u0002\u0005E\u0003bBA\u0014\u0001\u0011\u0005\u00111\f\u0005\b\u0003g\u0002A\u0011IA;\u0011\u0019i\u0006\u0001\"\u0011\u0002\b\"9\u0011Q\u0012\u0001\u0005\u0002\u0005=\u0005bBAK\u0001\u0011\u0005\u0011q\u0013\u0005\b\u00037\u0003A\u0011AAO\u0011\u001d\t\u0019\u000b\u0001C!\u0003KC\u0011\"!-\u0001\u0003\u0003%\t!a-\t\u0013\u0005\r\u0007!%A\u0005\u0002\u0005\u0015\u0007\"CAn\u0001E\u0005I\u0011AAo\u0011%\t\t\u000fAI\u0001\n\u0003\t\u0019\u000fC\u0005\u0002h\u0002\t\n\u0011\"\u0001\u0002d\"I\u0011\u0011\u001e\u0001\u0012\u0002\u0013\u0005\u00111\u001e\u0005\n\u0003_\u0004\u0011\u0013!C\u0001\u0003cD\u0011\"!>\u0001#\u0003%\t!a>\t\u0013\u0005m\b!!A\u0005B\u0005u\b\u0002\u0003B\u0005\u0001\u0005\u0005I\u0011\u00010\t\u0013\t-\u0001!!A\u0005\u0002\t5\u0001\"\u0003B\r\u0001\u0005\u0005I\u0011\tB\u000e\u0011%\u0011I\u0003AA\u0001\n\u0003\u0011Y\u0003C\u0005\u00030\u0001\t\t\u0011\"\u0011\u00032!I!Q\u0007\u0001\u0002\u0002\u0013\u0005#q\u0007\u0005\n\u0005s\u0001\u0011\u0011!C!\u0005wA\u0011B!\u0010\u0001\u0003\u0003%\tEa\u0010\b\u0013\t=#(!A\t\u0002\tEc\u0001C\u001d;\u0003\u0003E\tAa\u0015\t\u000f\u0005\u001d2\u0006\"\u0001\u0003l!I!\u0011H\u0016\u0002\u0002\u0013\u0015#1\b\u0005\n\u0005[Z\u0013\u0011!CA\u0005_B\u0011Ba ,#\u0003%\t!!8\t\u0013\t\u00055&%A\u0005\u0002\u0005-\b\"\u0003BBWE\u0005I\u0011AAy\u0011%\u0011)iKI\u0001\n\u0003\t9\u0010C\u0005\u0003\b.\n\t\u0011\"!\u0003\n\"I!qS\u0016\u0012\u0002\u0013\u0005\u0011Q\u001c\u0005\n\u00053[\u0013\u0013!C\u0001\u0003WD\u0011Ba',#\u0003%\t!!=\t\u0013\tu5&%A\u0005\u0002\u0005]\b\"\u0003BPW\u0005\u0005I\u0011\u0002BQ\u0005A!\u0016-\u001b7DQ>\u0004\b/\u001b8h!>|GN\u0003\u0002<y\u00059!o\\;uS:<'\"A\u001f\u0002\t\u0005\\7.Y\u0002\u0001'\u0019\u0001\u0001I\u0012&O#B\u0011\u0011\tR\u0007\u0002\u0005*\t1)A\u0003tG\u0006d\u0017-\u0003\u0002F\u0005\n1\u0011I\\=SK\u001a\u0004\"a\u0012%\u000e\u0003iJ!!\u0013\u001e\u0003\tA{w\u000e\u001c\t\u0004\u000f.k\u0015B\u0001';\u0005]\u0001vn\u001c7Pm\u0016\u0014(/\u001b3f+:\u001cX\r^\"p]\u001aLw\r\u0005\u0002H\u0001A\u0011\u0011iT\u0005\u0003!\n\u0013q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002S5:\u00111\u000b\u0017\b\u0003)^k\u0011!\u0016\u0006\u0003-z\na\u0001\u0010:p_Rt\u0014\"A\"\n\u0005e\u0013\u0015a\u00029bG.\fw-Z\u0005\u00037r\u0013AbU3sS\u0006d\u0017N_1cY\u0016T!!\u0017\"\u0002\u001b9\u0014xJZ%ogR\fgnY3t+\u0005y\u0006CA!a\u0013\t\t'IA\u0002J]R\faB\u001c:PM&s7\u000f^1oG\u0016\u001c\b%A\u0004sKNL'0\u001a:\u0016\u0003\u0015\u00042!\u00114i\u0013\t9'I\u0001\u0004PaRLwN\u001c\t\u0003\u000f&L!A\u001b\u001e\u0003\u000fI+7/\u001b>fe\u0006A!/Z:ju\u0016\u0014\b%\u0001\u0004xSRD\u0017N\\\u000b\u0002]B\u0011q\u000e^\u0007\u0002a*\u0011\u0011O]\u0001\tIV\u0014\u0018\r^5p]*\u00111OQ\u0001\u000bG>t7-\u001e:sK:$\u0018BA;q\u000591\u0015N\\5uK\u0012+(/\u0019;j_:\fqa^5uQ&t\u0007%\u0001\u0005j]R,'O^1m\u0003%Ig\u000e^3sm\u0006d\u0007%\u0001\ntkB,'O^5t_J\u001cFO]1uK\u001eLX#A>\u0011\u0005q|X\"A?\u000b\u0005yd\u0014!B1di>\u0014\u0018bAA\u0001{\n\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0003M\u0019X\u000f]3sm&\u001cxN]*ue\u0006$XmZ=!\u0003A\u0011x.\u001e;fe\u0012K7\u000f]1uG\",'/\u0006\u0002\u0002\nA!\u00111BA\n\u001d\u0011\ti!a\u0004\u0011\u0005Q\u0013\u0015bAA\t\u0005\u00061\u0001K]3eK\u001aLA!!\u0006\u0002\u0018\t11\u000b\u001e:j]\u001eT1!!\u0005C\u0003E\u0011x.\u001e;fe\u0012K7\u000f]1uG\",'\u000fI\u0001\u0012kN,\u0007k\\8m\t&\u001c\b/\u0019;dQ\u0016\u0014XCAA\u0010!\r\t\u0015\u0011E\u0005\u0004\u0003G\u0011%a\u0002\"p_2,\u0017M\\\u0001\u0013kN,\u0007k\\8m\t&\u001c\b/\u0019;dQ\u0016\u0014\b%\u0001\u0004=S:LGO\u0010\u000b\u0010\u001b\u0006-\u0012QFA\u0018\u0003c\t\u0019$!\u000e\u00028!)Ql\u0004a\u0001?\"91m\u0004I\u0001\u0002\u0004)\u0007\"\u00027\u0010\u0001\u0004q\u0007\"B<\u0010\u0001\u0004q\u0007bB=\u0010!\u0003\u0005\ra\u001f\u0005\n\u0003\u000by\u0001\u0013!a\u0001\u0003\u0013A\u0011\"a\u0007\u0010!\u0003\u0005\r!a\b\u0015\u00075\u000bY\u0004C\u0004\u0002>A\u0001\r!a\u0010\u0002\r\r|gNZ5h!\u0011\t\t%!\u0014\u000e\u0005\u0005\r#\u0002BA\u001f\u0003\u000bRA!a\u0012\u0002J\u0005AA/\u001f9fg\u00064WM\u0003\u0002\u0002L\u0005\u00191m\\7\n\t\u0005=\u00131\t\u0002\u0007\u0007>tg-[4\u0015\u000f5\u000b\u0019&a\u0016\u0002Z!1\u0011QK\tA\u0002}\u000b!A\u001c:\t\u000b1\f\u0002\u0019\u00018\t\u000b]\f\u0002\u0019\u00018\u0015\u000f5\u000bi&a\u0018\u0002r!1\u0011Q\u000b\nA\u0002}Ca\u0001\u001c\nA\u0002\u0005\u0005\u0004\u0003BA2\u0003[j!!!\u001a\u000b\t\u0005\u001d\u0014\u0011N\u0001\u0005i&lWM\u0003\u0002\u0002l\u0005!!.\u0019<b\u0013\u0011\ty'!\u001a\u0003\u0011\u0011+(/\u0019;j_:Daa\u001e\nA\u0002\u0005\u0005\u0014\u0001D2sK\u0006$XMU8vi\u0016\u0014H\u0003BA<\u0003{\u00022aRA=\u0013\r\tYH\u000f\u0002\u0007%>,H/\u001a:\t\u000f\u0005}4\u00031\u0001\u0002\u0002\u000611/_:uK6\u00042\u0001`AB\u0013\r\t)) \u0002\f\u0003\u000e$xN]*zgR,W\u000eF\u0002`\u0003\u0013Cq!a#\u0015\u0001\u0004\t\t)A\u0002tsN\fac^5uQN+\b/\u001a:wSN|'o\u0015;sCR,w-\u001f\u000b\u0004\u001b\u0006E\u0005BBAJ+\u0001\u000710\u0001\u0005tiJ\fG/Z4z\u0003-9\u0018\u000e\u001e5SKNL'0\u001a:\u0015\u00075\u000bI\nC\u0003d-\u0001\u0007\u0001.\u0001\bxSRDG)[:qCR\u001c\u0007.\u001a:\u0015\u00075\u000by\nC\u0004\u0002\"^\u0001\r!!\u0003\u0002\u0019\u0011L7\u000f]1uG\",'/\u00133\u0002\u0019]LG\u000f\u001b$bY2\u0014\u0017mY6\u0015\t\u0005\u001d\u0016Q\u0016\t\u0004\u000f\u0006%\u0016bAAVu\ta!k\\;uKJ\u001cuN\u001c4jO\"9\u0011q\u0016\rA\u0002\u0005\u001d\u0016!B8uQ\u0016\u0014\u0018\u0001B2paf$r\"TA[\u0003o\u000bI,a/\u0002>\u0006}\u0016\u0011\u0019\u0005\b;f\u0001\n\u00111\u0001`\u0011\u001d\u0019\u0017\u0004%AA\u0002\u0015Dq\u0001\\\r\u0011\u0002\u0003\u0007a\u000eC\u0004x3A\u0005\t\u0019\u00018\t\u000feL\u0002\u0013!a\u0001w\"I\u0011QA\r\u0011\u0002\u0003\u0007\u0011\u0011\u0002\u0005\n\u00037I\u0002\u0013!a\u0001\u0003?\tabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002H*\u001aq,!3,\u0005\u0005-\u0007\u0003BAg\u0003/l!!a4\u000b\t\u0005E\u00171[\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!6C\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u00033\fyMA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002`*\u001aQ-!3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011\u0011Q\u001d\u0016\u0004]\u0006%\u0017AD2paf$C-\u001a4bk2$H\u0005N\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+\t\tiOK\u0002|\u0003\u0013\fabY8qs\u0012\"WMZ1vYR$c'\u0006\u0002\u0002t*\"\u0011\u0011BAe\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*\"!!?+\t\u0005}\u0011\u0011Z\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005}\b\u0003\u0002B\u0001\u0005\u000fi!Aa\u0001\u000b\t\t\u0015\u0011\u0011N\u0001\u0005Y\u0006tw-\u0003\u0003\u0002\u0016\t\r\u0011\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0005\u001f\u0011)\u0002E\u0002B\u0005#I1Aa\u0005C\u0005\r\te.\u001f\u0005\t\u0005/\u0019\u0013\u0011!a\u0001?\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"A!\b\u0011\r\t}!Q\u0005B\b\u001b\t\u0011\tCC\u0002\u0003$\t\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\u00119C!\t\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003?\u0011i\u0003C\u0005\u0003\u0018\u0015\n\t\u00111\u0001\u0003\u0010\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\tyPa\r\t\u0011\t]a%!AA\u0002}\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002?\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0000\u00061Q-];bYN$B!a\b\u0003B!I!qC\u0015\u0002\u0002\u0003\u0007!q\u0002\u0015\b\u0001\t\u0015#1\nB'!\r\t%qI\u0005\u0004\u0005\u0013\u0012%\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\t\u0011\u0001\u0005+bS2\u001c\u0005n\u001c9qS:<\u0007k\\8m!\t95fE\u0003,\u0005+\u0012\t\u0007\u0005\b\u0003X\tus,\u001a8ow\u0006%\u0011qD'\u000e\u0005\te#b\u0001B.\u0005\u00069!/\u001e8uS6,\u0017\u0002\u0002B0\u00053\u0012\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c88!\u0011\u0011\u0019G!\u001b\u000e\u0005\t\u0015$\u0002\u0002B4\u0003S\n!![8\n\u0007m\u0013)\u0007\u0006\u0002\u0003R\u0005)\u0011\r\u001d9msRyQJ!\u001d\u0003t\tU$q\u000fB=\u0005w\u0012i\bC\u0003^]\u0001\u0007q\fC\u0004d]A\u0005\t\u0019A3\t\u000b1t\u0003\u0019\u00018\t\u000b]t\u0003\u0019\u00018\t\u000fet\u0003\u0013!a\u0001w\"I\u0011Q\u0001\u0018\u0011\u0002\u0003\u0007\u0011\u0011\u0002\u0005\n\u00037q\u0003\u0013!a\u0001\u0003?\tq\"\u00199qYf$C-\u001a4bk2$HEM\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%k\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$c'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00138\u0003\u001d)h.\u00199qYf$BAa#\u0003\u0014B!\u0011I\u001aBG!1\t%qR0f]:\\\u0018\u0011BA\u0010\u0013\r\u0011\tJ\u0011\u0002\u0007)V\u0004H.Z\u001c\t\u0011\tU5'!AA\u00025\u000b1\u0001\u001f\u00131\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%e\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIU\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u00122\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$s'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003$B!!\u0011\u0001BS\u0013\u0011\u00119Ka\u0001\u0003\r=\u0013'.Z2u\u0001")
public final class TailChoppingPool
implements PoolOverrideUnsetConfig<TailChoppingPool>,
Product {
    private static final long serialVersionUID = 1L;
    private final int nrOfInstances;
    private final Option<Resizer> resizer;
    private final FiniteDuration within;
    private final FiniteDuration interval;
    private final SupervisorStrategy supervisorStrategy;
    private final String routerDispatcher;
    private final boolean usePoolDispatcher;

    public static boolean $lessinit$greater$default$7() {
        return TailChoppingPool$.MODULE$.$lessinit$greater$default$7();
    }

    public static String $lessinit$greater$default$6() {
        return TailChoppingPool$.MODULE$.$lessinit$greater$default$6();
    }

    public static SupervisorStrategy $lessinit$greater$default$5() {
        return TailChoppingPool$.MODULE$.$lessinit$greater$default$5();
    }

    public static Option<Resizer> $lessinit$greater$default$2() {
        return TailChoppingPool$.MODULE$.$lessinit$greater$default$2();
    }

    public static Option<Tuple7<Object, Option<Resizer>, FiniteDuration, FiniteDuration, SupervisorStrategy, String, Object>> unapply(TailChoppingPool x$0) {
        return TailChoppingPool$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$7() {
        return TailChoppingPool$.MODULE$.apply$default$7();
    }

    public static String apply$default$6() {
        return TailChoppingPool$.MODULE$.apply$default$6();
    }

    public static SupervisorStrategy apply$default$5() {
        return TailChoppingPool$.MODULE$.apply$default$5();
    }

    public static Option<Resizer> apply$default$2() {
        return TailChoppingPool$.MODULE$.apply$default$2();
    }

    public static TailChoppingPool apply(int nrOfInstances, Option<Resizer> resizer, FiniteDuration within, FiniteDuration interval, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return TailChoppingPool$.MODULE$.apply(nrOfInstances, resizer, within, interval, supervisorStrategy, routerDispatcher, usePoolDispatcher);
    }

    public static Function1<Tuple7<Object, Option<Resizer>, FiniteDuration, FiniteDuration, SupervisorStrategy, String, Object>, TailChoppingPool> tupled() {
        return TailChoppingPool$.MODULE$.tupled();
    }

    public static Function1<Object, Function1<Option<Resizer>, Function1<FiniteDuration, Function1<FiniteDuration, Function1<SupervisorStrategy, Function1<String, Function1<Object, TailChoppingPool>>>>>>> curried() {
        return TailChoppingPool$.MODULE$.curried();
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

    public FiniteDuration interval() {
        return this.interval;
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
        return new Router(new TailChoppingRoutingLogic(system.scheduler(), this.within(), this.interval(), system.dispatchers().lookup(this.routerDispatcher())));
    }

    @Override
    public int nrOfInstances(ActorSystem sys) {
        return this.nrOfInstances();
    }

    @Override
    public TailChoppingPool withSupervisorStrategy(SupervisorStrategy strategy) {
        SupervisorStrategy x$1 = strategy;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        String x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6, x$7);
    }

    @Override
    public TailChoppingPool withResizer(Resizer resizer) {
        Some<Resizer> x$1 = new Some<Resizer>(resizer);
        int x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$3();
        FiniteDuration x$4 = this.copy$default$4();
        SupervisorStrategy x$5 = this.copy$default$5();
        String x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        return this.copy(x$2, x$1, x$3, x$4, x$5, x$6, x$7);
    }

    public TailChoppingPool withDispatcher(String dispatcherId) {
        String x$1 = dispatcherId;
        int x$2 = this.copy$default$1();
        Option<Resizer> x$3 = this.copy$default$2();
        FiniteDuration x$4 = this.copy$default$3();
        FiniteDuration x$5 = this.copy$default$4();
        SupervisorStrategy x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$7();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7);
    }

    @Override
    public RouterConfig withFallback(RouterConfig other) {
        return this.overrideUnsetConfig(other);
    }

    public TailChoppingPool copy(int nrOfInstances, Option<Resizer> resizer, FiniteDuration within, FiniteDuration interval, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        return new TailChoppingPool(nrOfInstances, resizer, within, interval, supervisorStrategy, routerDispatcher, usePoolDispatcher);
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

    public FiniteDuration copy$default$4() {
        return this.interval();
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
        return "TailChoppingPool";
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
                return this.within();
            }
            case 3: {
                return this.interval();
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
        return x$1 instanceof TailChoppingPool;
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
                return "interval";
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
        n = Statics.mix(n, Statics.anyHash(this.within()));
        n = Statics.mix(n, Statics.anyHash(this.interval()));
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
        if (!(object instanceof TailChoppingPool)) return false;
        boolean bl = true;
        if (!bl) return false;
        TailChoppingPool tailChoppingPool = (TailChoppingPool)x$1;
        if (this.nrOfInstances() != tailChoppingPool.nrOfInstances()) return false;
        if (this.usePoolDispatcher() != tailChoppingPool.usePoolDispatcher()) return false;
        Option<Resizer> option = this.resizer();
        Option<Resizer> option2 = tailChoppingPool.resizer();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        FiniteDuration finiteDuration = this.within();
        FiniteDuration finiteDuration2 = tailChoppingPool.within();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        FiniteDuration finiteDuration3 = this.interval();
        FiniteDuration finiteDuration4 = tailChoppingPool.interval();
        if (finiteDuration3 == null) {
            if (finiteDuration4 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration3).equals(finiteDuration4)) return false;
        SupervisorStrategy supervisorStrategy = this.supervisorStrategy();
        SupervisorStrategy supervisorStrategy2 = tailChoppingPool.supervisorStrategy();
        if (supervisorStrategy == null) {
            if (supervisorStrategy2 != null) {
                return false;
            }
        } else if (!supervisorStrategy.equals(supervisorStrategy2)) return false;
        String string = this.routerDispatcher();
        String string2 = tailChoppingPool.routerDispatcher();
        if (string == null) {
            if (string2 == null) return true;
            return false;
        } else {
            if (!string.equals(string2)) return false;
            return true;
        }
    }

    public TailChoppingPool(int nrOfInstances, Option<Resizer> resizer, FiniteDuration within, FiniteDuration interval, SupervisorStrategy supervisorStrategy, String routerDispatcher, boolean usePoolDispatcher) {
        this.nrOfInstances = nrOfInstances;
        this.resizer = resizer;
        this.within = within;
        this.interval = interval;
        this.supervisorStrategy = supervisorStrategy;
        this.routerDispatcher = routerDispatcher;
        this.usePoolDispatcher = usePoolDispatcher;
        RouterConfig.$init$(this);
        Pool.$init$(this);
        PoolOverrideUnsetConfig.$init$(this);
        Product.$init$(this);
    }

    public TailChoppingPool(Config config) {
        int x$1 = config.getInt("nr-of-instances");
        FiniteDuration x$2 = Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "within");
        FiniteDuration x$3 = Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "tail-chopping-router.interval");
        Option<Resizer> x$4 = Resizer$.MODULE$.fromConfig(config);
        boolean x$5 = config.hasPath("pool-dispatcher");
        SupervisorStrategy x$6 = TailChoppingPool$.MODULE$.$lessinit$greater$default$5();
        String x$7 = TailChoppingPool$.MODULE$.$lessinit$greater$default$6();
        this(x$1, x$4, x$2, x$3, x$6, x$7, x$5);
    }

    public TailChoppingPool(int nr, FiniteDuration within, FiniteDuration interval) {
        int x$1 = nr;
        FiniteDuration x$2 = within;
        FiniteDuration x$3 = interval;
        Option<Resizer> x$4 = TailChoppingPool$.MODULE$.$lessinit$greater$default$2();
        SupervisorStrategy x$5 = TailChoppingPool$.MODULE$.$lessinit$greater$default$5();
        String x$6 = TailChoppingPool$.MODULE$.$lessinit$greater$default$6();
        boolean x$7 = TailChoppingPool$.MODULE$.$lessinit$greater$default$7();
        this(x$1, x$4, x$2, x$3, x$5, x$6, x$7);
    }

    public TailChoppingPool(int nr, Duration within, Duration interval) {
        this(nr, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(within)), JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(interval)));
    }
}

