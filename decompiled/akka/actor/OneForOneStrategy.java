/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ChildRestartStats;
import akka.actor.OneForOneStrategy$;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$;
import akka.japi.Function;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tUf\u0001B\u001a5\u0001fB\u0001\u0002\u0015\u0001\u0003\u0016\u0004%\t!\u0015\u0005\t+\u0002\u0011\t\u0012)A\u0005%\"Aa\u000b\u0001BK\u0002\u0013\u0005q\u000b\u0003\u0005a\u0001\tE\t\u0015!\u0003Y\u0011!\t\u0007A!f\u0001\n\u0003\u0012\u0007\u0002\u00034\u0001\u0005#\u0005\u000b\u0011B2\t\u0011\u001d\u0004!Q1A\u0005\u0002!D\u0001\u0002\u001d\u0001\u0003\u0002\u0003\u0006I!\u001b\u0005\u0006c\u0002!\tA\u001d\u0005\u0006c\u0002!\t!\u001f\u0005\u0007c\u0002!\t!a\u0001\t\rE\u0004A\u0011AA\u000e\u0011\u0019\t\b\u0001\"\u0001\u0002$!1\u0011\u000f\u0001C\u0001\u0003WAa!\u001d\u0001\u0005\u0002\u0005%\u0004BB9\u0001\t\u0003\ti\b\u0003\u0004r\u0001\u0011\u0005\u0011Q\u0011\u0005\u0007c\u0002!\t!!$\t\rE\u0004A\u0011AAJ\u0011\u001d\t9\n\u0001C\u0001\u00033C\u0011\"!(\u0001\u0005\u0004%I!a(\t\u0011\u00055\u0006\u0001)A\u0005\u0003CCq!a,\u0001\t\u0003\t\t\fC\u0004\u0002V\u0002!\t!a6\t\u0013\u0005M\b!!A\u0005\u0002\u0005U\b\"\u0003B\u0001\u0001E\u0005I\u0011\u0001B\u0002\u0011%\u0011I\u0002AI\u0001\n\u0003\u0011Y\u0002C\u0005\u0003 \u0001\t\n\u0011\"\u0001\u0003\"!I!Q\u0005\u0001\u0002\u0002\u0013\u0005#q\u0005\u0005\t\u0005_\u0001\u0011\u0011!C\u0001#\"I!\u0011\u0007\u0001\u0002\u0002\u0013\u0005!1\u0007\u0005\n\u0005\u007f\u0001\u0011\u0011!C!\u0005\u0003B\u0011Ba\u0014\u0001\u0003\u0003%\tA!\u0015\t\u0013\tU\u0003!!A\u0005B\t]\u0003\"\u0003B.\u0001\u0005\u0005I\u0011\tB/\u0011%\u0011y\u0006AA\u0001\n\u0003\u0012\t\u0007C\u0005\u0003d\u0001\t\t\u0011\"\u0011\u0003f\u001dI!\u0011\u000e\u001b\u0002\u0002#\u0005!1\u000e\u0004\tgQ\n\t\u0011#\u0001\u0003n!1\u0011o\nC\u0001\u0005\u007fB\u0011Ba\u0018(\u0003\u0003%)E!\u0019\t\u0013\t\u0005u%!A\u0005\u0002\n\r\u0005\"\u0003BHOE\u0005I\u0011\u0001B\u0002\u0011%\u0011\tjJI\u0001\n\u0003\u0011Y\u0002C\u0005\u0003\u0014\u001e\n\n\u0011\"\u0001\u0003\"!I!QS\u0014\u0002\u0002\u0013\u0005%q\u0013\u0005\n\u0005K;\u0013\u0013!C\u0001\u0005\u0007A\u0011Ba*(#\u0003%\tAa\u0007\t\u0013\t%v%%A\u0005\u0002\t\u0005\u0002\"\u0003BVO\u0005\u0005I\u0011\u0002BW\u0005Eye.\u001a$pe>sWm\u0015;sCR,w-\u001f\u0006\u0003kY\nQ!Y2u_JT\u0011aN\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u0001Qd\b\u0012\t\u0003wqj\u0011\u0001N\u0005\u0003{Q\u0012!cU;qKJ4\u0018n]8s'R\u0014\u0018\r^3hsB\u0011qHQ\u0007\u0002\u0001*\t\u0011)A\u0003tG\u0006d\u0017-\u0003\u0002D\u0001\n9\u0001K]8ek\u000e$\bCA#N\u001d\t15J\u0004\u0002H\u00156\t\u0001J\u0003\u0002Jq\u00051AH]8pizJ\u0011!Q\u0005\u0003\u0019\u0002\u000bq\u0001]1dW\u0006<W-\u0003\u0002O\u001f\na1+\u001a:jC2L'0\u00192mK*\u0011A\nQ\u0001\u000f[\u0006DhJ](g%\u0016$(/[3t+\u0005\u0011\u0006CA T\u0013\t!\u0006IA\u0002J]R\fq\"\\1y\u001dJ|eMU3ue&,7\u000fI\u0001\u0010o&$\b.\u001b8US6,'+\u00198hKV\t\u0001\f\u0005\u0002Z=6\t!L\u0003\u0002\\9\u0006AA-\u001e:bi&|gN\u0003\u0002^\u0001\u0006Q1m\u001c8dkJ\u0014XM\u001c;\n\u0005}S&\u0001\u0003#ve\u0006$\u0018n\u001c8\u0002!]LG\u000f[5o)&lWMU1oO\u0016\u0004\u0013A\u00047pO\u001eLgnZ#oC\ndW\rZ\u000b\u0002GB\u0011q\bZ\u0005\u0003K\u0002\u0013qAQ8pY\u0016\fg.A\bm_\u001e<\u0017N\\4F]\u0006\u0014G.\u001a3!\u0003\u001d!WmY5eKJ,\u0012!\u001b\t\u0003U6t!aO6\n\u00051$\u0014AE*va\u0016\u0014h/[:peN#(/\u0019;fOfL!A\\8\u0003\u000f\u0011+7-\u001b3fe*\u0011A\u000eN\u0001\tI\u0016\u001c\u0017\u000eZ3sA\u00051A(\u001b8jiz\"Ba\u001d<xqR\u0011A/\u001e\t\u0003w\u0001AQaZ\u0005A\u0002%Dq\u0001U\u0005\u0011\u0002\u0003\u0007!\u000bC\u0004W\u0013A\u0005\t\u0019\u0001-\t\u000f\u0005L\u0001\u0013!a\u0001GR1AO_>}\u0003\u0003AQ\u0001\u0015\u0006A\u0002ICQA\u0016\u0006A\u0002aCQa\u001a\u0006A\u0002u\u0004\"A\u001b@\n\u0005}|'\u0001\u0003&EK\u000eLG-\u001a:\t\u000b\u0005T\u0001\u0019A2\u0015\u0013Q\f)!a\u0002\u0002\u0018\u0005e\u0001\"\u0002)\f\u0001\u0004\u0011\u0006B\u0002,\f\u0001\u0004\tI\u0001\u0005\u0003\u0002\f\u0005UQBAA\u0007\u0015\u0011\ty!!\u0005\u0002\tQLW.\u001a\u0006\u0003\u0003'\tAA[1wC&\u0019q,!\u0004\t\u000b\u001d\\\u0001\u0019A?\t\u000b\u0005\\\u0001\u0019A2\u0015\u000fQ\fi\"a\b\u0002\"!)\u0001\u000b\u0004a\u0001%\")a\u000b\u0004a\u00011\")q\r\u0004a\u0001{R9A/!\n\u0002(\u0005%\u0002\"\u0002)\u000e\u0001\u0004\u0011\u0006B\u0002,\u000e\u0001\u0004\tI\u0001C\u0003h\u001b\u0001\u0007Q\u0010F\u0004u\u0003[\ty#!\r\t\u000bAs\u0001\u0019\u0001*\t\u000bYs\u0001\u0019\u0001-\t\u000f\u0005Mb\u00021\u0001\u00026\u0005AAO]1q\u000bbLG\u000f\u0005\u0004\u00028\u0005u\u0012\u0011I\u0007\u0003\u0003sQA!a\u000f\u0002\u0012\u0005!A.\u00198h\u0013\u0011\ty$!\u000f\u0003\u0011%#XM]1cY\u0016\u0004D!a\u0011\u0002XA1\u0011QIA'\u0003'rA!a\u0012\u0002JA\u0011q\tQ\u0005\u0004\u0003\u0017\u0002\u0015A\u0002)sK\u0012,g-\u0003\u0003\u0002P\u0005E#!B\"mCN\u001c(bAA&\u0001B!\u0011QKA,\u0019\u0001!A\"!\u0017\u00022\u0005\u0005\t\u0011!B\u0001\u00037\u00121a\u0018\u00138#\u0011\ti&a\u0019\u0011\u0007}\ny&C\u0002\u0002b\u0001\u0013qAT8uQ&tw\rE\u0002F\u0003KJ1!a\u001aP\u0005%!\u0006N]8xC\ndW\rF\u0004u\u0003W\ni'a\u001c\t\u000bA{\u0001\u0019\u0001*\t\rY{\u0001\u0019AA\u0005\u0011\u001d\t\u0019d\u0004a\u0001\u0003c\u0002b!a\u000e\u0002>\u0005M\u0004\u0007BA;\u0003s\u0002b!!\u0012\u0002N\u0005]\u0004\u0003BA+\u0003s\"A\"a\u001f\u0002p\u0005\u0005\t\u0011!B\u0001\u00037\u00121a\u0018\u00139)\u001d!\u0018qPAA\u0003\u0007CQ\u0001\u0015\tA\u0002ICQA\u0016\tA\u0002aCQa\u001a\tA\u0002%$r\u0001^AD\u0003\u0013\u000bY\tC\u0003Q#\u0001\u0007!\u000b\u0003\u0004W#\u0001\u0007\u0011\u0011\u0002\u0005\u0006OF\u0001\r!\u001b\u000b\u0006i\u0006=\u0015\u0011\u0013\u0005\u0006CJ\u0001\ra\u0019\u0005\u0006OJ\u0001\r!\u001b\u000b\u0004i\u0006U\u0005\"B4\u0014\u0001\u0004I\u0017AE<ji\"l\u0015\r\u001f(s\u001f\u001a\u0014V\r\u001e:jKN$2\u0001^AN\u0011\u0015\u0001F\u00031\u0001S\u00035\u0011X\r\u001e:jKN<\u0016N\u001c3poV\u0011\u0011\u0011\u0015\t\b\u007f\u0005\r\u0016qUAT\u0013\r\t)\u000b\u0011\u0002\u0007)V\u0004H.\u001a\u001a\u0011\t}\nIKU\u0005\u0004\u0003W\u0003%AB(qi&|g.\u0001\bsKR\u0014\u0018.Z:XS:$wn\u001e\u0011\u0002+!\fg\u000e\u001a7f\u0007\"LG\u000e\u001a+fe6Lg.\u0019;fIRA\u00111WA]\u0003\u0007\fi\rE\u0002@\u0003kK1!a.A\u0005\u0011)f.\u001b;\t\u000f\u0005mv\u00031\u0001\u0002>\u000691m\u001c8uKb$\bcA\u001e\u0002@&\u0019\u0011\u0011\u0019\u001b\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;\t\u000f\u0005\u0015w\u00031\u0001\u0002H\u0006)1\r[5mIB\u00191(!3\n\u0007\u0005-GG\u0001\u0005BGR|'OU3g\u0011\u001d\tym\u0006a\u0001\u0003#\f\u0001b\u00195jY\u0012\u0014XM\u001c\t\u0006\u000b\u0006M\u0017qY\u0005\u0004\u0003\u007fy\u0015A\u00049s_\u000e,7o\u001d$bS2,(/\u001a\u000b\u000f\u0003g\u000bI.a7\u0002`\u0006\u0005\u0018Q]Ax\u0011\u001d\tY\f\u0007a\u0001\u0003{Ca!!8\u0019\u0001\u0004\u0019\u0017a\u0002:fgR\f'\u000f\u001e\u0005\b\u0003\u000bD\u0002\u0019AAd\u0011\u001d\t\u0019\u000f\u0007a\u0001\u0003G\nQaY1vg\u0016Dq!a:\u0019\u0001\u0004\tI/A\u0003ti\u0006$8\u000fE\u0002<\u0003WL1!!<5\u0005E\u0019\u0005.\u001b7e%\u0016\u001cH/\u0019:u'R\fGo\u001d\u0005\b\u0003\u001fD\u0002\u0019AAy!\u0015)\u00151[Au\u0003\u0011\u0019w\u000e]=\u0015\u0011\u0005]\u00181`A\u007f\u0003\u007f$2\u0001^A}\u0011\u00159\u0017\u00041\u0001j\u0011\u001d\u0001\u0016\u0004%AA\u0002ICqAV\r\u0011\u0002\u0003\u0007\u0001\fC\u0004b3A\u0005\t\u0019A2\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011!Q\u0001\u0016\u0004%\n\u001d1F\u0001B\u0005!\u0011\u0011YA!\u0006\u000e\u0005\t5!\u0002\u0002B\b\u0005#\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\tM\u0001)\u0001\u0006b]:|G/\u0019;j_:LAAa\u0006\u0003\u000e\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011!Q\u0004\u0016\u00041\n\u001d\u0011AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0005GQ3a\u0019B\u0004\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011!\u0011\u0006\t\u0005\u0003o\u0011Y#\u0003\u0003\u0003.\u0005e\"AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\tU\"1\b\t\u0004\u007f\t]\u0012b\u0001B\u001d\u0001\n\u0019\u0011I\\=\t\u0011\tur$!AA\u0002I\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B\"!\u0019\u0011)Ea\u0013\u000365\u0011!q\t\u0006\u0004\u0005\u0013\u0002\u0015AC2pY2,7\r^5p]&!!Q\nB$\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007\r\u0014\u0019\u0006C\u0005\u0003>\u0005\n\t\u00111\u0001\u00036\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u0011IC!\u0017\t\u0011\tu\"%!AA\u0002I\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002%\u0006AAo\\*ue&tw\r\u0006\u0002\u0003*\u00051Q-];bYN$2a\u0019B4\u0011%\u0011i$JA\u0001\u0002\u0004\u0011)$A\tP]\u00164uN](oKN#(/\u0019;fOf\u0004\"aO\u0014\u0014\u000b\u001d\u0012yG!\u001e\u0011\u0007}\u0012\t(C\u0002\u0003t\u0001\u0013a!\u00118z%\u00164\u0007\u0003\u0002B<\u0005{j!A!\u001f\u000b\t\tm\u0014\u0011C\u0001\u0003S>L1A\u0014B=)\t\u0011Y'A\u0003baBd\u0017\u0010\u0006\u0005\u0003\u0006\n%%1\u0012BG)\r!(q\u0011\u0005\u0006O*\u0002\r!\u001b\u0005\b!*\u0002\n\u00111\u0001S\u0011\u001d1&\u0006%AA\u0002aCq!\u0019\u0016\u0011\u0002\u0003\u00071-A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00132\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u0012\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0002\u000fUt\u0017\r\u001d9msR!!\u0011\u0014BQ!\u0015y\u0014\u0011\u0016BN!\u0019y$Q\u0014*YG&\u0019!q\u0014!\u0003\rQ+\b\u000f\\34\u0011!\u0011\u0019KLA\u0001\u0002\u0004!\u0018a\u0001=%a\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$3'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u00030B!\u0011q\u0007BY\u0013\u0011\u0011\u0019,!\u000f\u0003\r=\u0013'.Z2u\u0001")
public class OneForOneStrategy
extends SupervisorStrategy
implements Product,
Serializable {
    private final int maxNrOfRetries;
    private final Duration withinTimeRange;
    private final boolean loggingEnabled;
    private final PartialFunction<Throwable, SupervisorStrategy.Directive> decider;
    private final Tuple2<Option<Object>, Option<Object>> retriesWindow;

    public static boolean $lessinit$greater$default$3() {
        return OneForOneStrategy$.MODULE$.$lessinit$greater$default$3();
    }

    public static Duration $lessinit$greater$default$2() {
        return OneForOneStrategy$.MODULE$.$lessinit$greater$default$2();
    }

    public static int $lessinit$greater$default$1() {
        return OneForOneStrategy$.MODULE$.$lessinit$greater$default$1();
    }

    public static Option<Tuple3<Object, Duration, Object>> unapply(OneForOneStrategy x$0) {
        return OneForOneStrategy$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$3() {
        return OneForOneStrategy$.MODULE$.apply$default$3();
    }

    public static Duration apply$default$2() {
        return OneForOneStrategy$.MODULE$.apply$default$2();
    }

    public static int apply$default$1() {
        return OneForOneStrategy$.MODULE$.apply$default$1();
    }

    public static OneForOneStrategy apply(int maxNrOfRetries, Duration withinTimeRange, boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        return OneForOneStrategy$.MODULE$.apply(maxNrOfRetries, withinTimeRange, loggingEnabled, decider);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int maxNrOfRetries() {
        return this.maxNrOfRetries;
    }

    public Duration withinTimeRange() {
        return this.withinTimeRange;
    }

    @Override
    public boolean loggingEnabled() {
        return this.loggingEnabled;
    }

    @Override
    public PartialFunction<Throwable, SupervisorStrategy.Directive> decider() {
        return this.decider;
    }

    public OneForOneStrategy withMaxNrOfRetries(int maxNrOfRetries) {
        return this.copy(maxNrOfRetries, this.copy$default$2(), this.copy$default$3(), this.decider());
    }

    private Tuple2<Option<Object>, Option<Object>> retriesWindow() {
        return this.retriesWindow;
    }

    @Override
    public void handleChildTerminated(ActorContext context, ActorRef child, Iterable<ActorRef> children) {
    }

    @Override
    public void processFailure(ActorContext context, boolean restart, ActorRef child, Throwable cause, ChildRestartStats stats, Iterable<ChildRestartStats> children) {
        if (restart && stats.requestRestartPermission(this.retriesWindow())) {
            this.restartChild(child, cause, false);
            return;
        }
        context.stop(child);
    }

    public OneForOneStrategy copy(int maxNrOfRetries, Duration withinTimeRange, boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        return new OneForOneStrategy(maxNrOfRetries, withinTimeRange, loggingEnabled, decider);
    }

    public int copy$default$1() {
        return this.maxNrOfRetries();
    }

    public Duration copy$default$2() {
        return this.withinTimeRange();
    }

    public boolean copy$default$3() {
        return this.loggingEnabled();
    }

    @Override
    public String productPrefix() {
        return "OneForOneStrategy";
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
                return BoxesRunTime.boxToInteger(this.maxNrOfRetries());
            }
            case 1: {
                return this.withinTimeRange();
            }
            case 2: {
                return BoxesRunTime.boxToBoolean(this.loggingEnabled());
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
        return x$1 instanceof OneForOneStrategy;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "maxNrOfRetries";
            }
            case 1: {
                return "withinTimeRange";
            }
            case 2: {
                return "loggingEnabled";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.maxNrOfRetries());
        n = Statics.mix(n, Statics.anyHash(this.withinTimeRange()));
        n = Statics.mix(n, this.loggingEnabled() ? 1231 : 1237);
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
        if (!(object instanceof OneForOneStrategy)) return false;
        boolean bl = true;
        if (!bl) return false;
        OneForOneStrategy oneForOneStrategy = (OneForOneStrategy)x$1;
        if (this.maxNrOfRetries() != oneForOneStrategy.maxNrOfRetries()) return false;
        if (this.loggingEnabled() != oneForOneStrategy.loggingEnabled()) return false;
        Duration duration = this.withinTimeRange();
        Duration duration2 = oneForOneStrategy.withinTimeRange();
        if (duration == null) {
            if (duration2 != null) {
                return false;
            }
        } else if (!duration.equals(duration2)) return false;
        if (!oneForOneStrategy.canEqual(this)) return false;
        return true;
    }

    public static final /* synthetic */ int $anonfun$retriesWindow$2(Duration x$6) {
        return (int)x$6.toMillis();
    }

    public OneForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        this.maxNrOfRetries = maxNrOfRetries;
        this.withinTimeRange = withinTimeRange;
        this.loggingEnabled = loggingEnabled;
        this.decider = decider;
        Product.$init$(this);
        this.retriesWindow = new Tuple2(SupervisorStrategy$.MODULE$.maxNrOfRetriesOption(maxNrOfRetries), SupervisorStrategy$.MODULE$.withinTimeRangeOption(withinTimeRange).map((Function1<Duration, Object> & Serializable)x$6 -> BoxesRunTime.boxToInteger(OneForOneStrategy.$anonfun$retriesWindow$2(x$6))));
    }

    public OneForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, Function<Throwable, SupervisorStrategy.Directive> decider, boolean loggingEnabled) {
        this(maxNrOfRetries, withinTimeRange, loggingEnabled, SupervisorStrategy$.MODULE$.makeDecider(decider));
    }

    public OneForOneStrategy(int maxNrOfRetries, java.time.Duration withinTimeRange, Function<Throwable, SupervisorStrategy.Directive> decider, boolean loggingEnabled) {
        this(maxNrOfRetries, (Duration)JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(withinTimeRange)), loggingEnabled, SupervisorStrategy$.MODULE$.makeDecider(decider));
    }

    public OneForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, Function<Throwable, SupervisorStrategy.Directive> decider) {
        this(maxNrOfRetries, withinTimeRange, OneForOneStrategy$.MODULE$.$lessinit$greater$default$3(), SupervisorStrategy$.MODULE$.makeDecider(decider));
    }

    public OneForOneStrategy(int maxNrOfRetries, java.time.Duration withinTimeRange, Function<Throwable, SupervisorStrategy.Directive> decider) {
        this(maxNrOfRetries, (Duration)JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(withinTimeRange)), OneForOneStrategy$.MODULE$.$lessinit$greater$default$3(), SupervisorStrategy$.MODULE$.makeDecider(decider));
    }

    public OneForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, java.lang.Iterable<Class<? extends Throwable>> trapExit) {
        this(maxNrOfRetries, withinTimeRange, OneForOneStrategy$.MODULE$.$lessinit$greater$default$3(), SupervisorStrategy$.MODULE$.makeDecider(trapExit));
    }

    public OneForOneStrategy(int maxNrOfRetries, java.time.Duration withinTimeRange, java.lang.Iterable<Class<? extends Throwable>> trapExit) {
        this(maxNrOfRetries, (Duration)JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(withinTimeRange)), OneForOneStrategy$.MODULE$.$lessinit$greater$default$3(), SupervisorStrategy$.MODULE$.makeDecider(trapExit));
    }

    public OneForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        this(maxNrOfRetries, withinTimeRange, OneForOneStrategy$.MODULE$.$lessinit$greater$default$3(), decider);
    }

    public OneForOneStrategy(int maxNrOfRetries, java.time.Duration withinTimeRange, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        this(maxNrOfRetries, (Duration)JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(withinTimeRange)), OneForOneStrategy$.MODULE$.$lessinit$greater$default$3(), decider);
    }

    public OneForOneStrategy(boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        boolean x$1 = loggingEnabled;
        int x$2 = OneForOneStrategy$.MODULE$.$lessinit$greater$default$1();
        Duration x$3 = OneForOneStrategy$.MODULE$.$lessinit$greater$default$2();
        PartialFunction<Throwable, SupervisorStrategy.Directive> x$4 = decider;
        this(x$2, x$3, x$1, x$4);
    }

    public OneForOneStrategy(PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        this(OneForOneStrategy$.MODULE$.$lessinit$greater$default$1(), OneForOneStrategy$.MODULE$.$lessinit$greater$default$2(), OneForOneStrategy$.MODULE$.$lessinit$greater$default$3(), decider);
    }
}

