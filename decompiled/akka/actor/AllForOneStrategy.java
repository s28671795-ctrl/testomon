/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.AllForOneStrategy$;
import akka.actor.ChildRestartStats;
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
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t5f\u0001\u0002\u001a4\u0001bB\u0001b\u0014\u0001\u0003\u0016\u0004%\t\u0001\u0015\u0005\t)\u0002\u0011\t\u0012)A\u0005#\"AQ\u000b\u0001BK\u0002\u0013\u0005a\u000b\u0003\u0005`\u0001\tE\t\u0015!\u0003X\u0011!\u0001\u0007A!f\u0001\n\u0003\n\u0007\u0002C3\u0001\u0005#\u0005\u000b\u0011\u00022\t\u0011\u0019\u0004!Q1A\u0005\u0002\u001dD\u0001b\u001c\u0001\u0003\u0002\u0003\u0006I\u0001\u001b\u0005\u0006a\u0002!\t!\u001d\u0005\u0006a\u0002!\t\u0001\u001f\u0005\u0007a\u0002!\t!!\u0001\t\rA\u0004A\u0011AA\r\u0011\u0019\u0001\b\u0001\"\u0001\u0002\"!1\u0001\u000f\u0001C\u0001\u0003SAa\u0001\u001d\u0001\u0005\u0002\u0005\u001d\u0004B\u00029\u0001\t\u0003\tY\b\u0003\u0004q\u0001\u0011\u0005\u00111\u0011\u0005\u0007a\u0002!\t!a#\t\rA\u0004A\u0011AAI\u0011%\t)\n\u0001b\u0001\n\u0013\t9\n\u0003\u0005\u0002&\u0002\u0001\u000b\u0011BAM\u0011\u001d\t9\u000b\u0001C\u0001\u0003SCq!!4\u0001\t\u0003\ty\rC\u0005\u0002l\u0002\t\t\u0011\"\u0001\u0002n\"I\u0011\u0011 \u0001\u0012\u0002\u0013\u0005\u00111 \u0005\n\u0005#\u0001\u0011\u0013!C\u0001\u0005'A\u0011Ba\u0006\u0001#\u0003%\tA!\u0007\t\u0013\tu\u0001!!A\u0005B\t}\u0001\u0002\u0003B\u0014\u0001\u0005\u0005I\u0011\u0001)\t\u0013\t%\u0002!!A\u0005\u0002\t-\u0002\"\u0003B\u001c\u0001\u0005\u0005I\u0011\tB\u001d\u0011%\u00119\u0005AA\u0001\n\u0003\u0011I\u0005C\u0005\u0003N\u0001\t\t\u0011\"\u0011\u0003P!I!1\u000b\u0001\u0002\u0002\u0013\u0005#Q\u000b\u0005\n\u0005/\u0002\u0011\u0011!C!\u00053B\u0011Ba\u0017\u0001\u0003\u0003%\tE!\u0018\b\u0013\t\u00054'!A\t\u0002\t\rd\u0001\u0003\u001a4\u0003\u0003E\tA!\u001a\t\rA4C\u0011\u0001B<\u0011%\u00119FJA\u0001\n\u000b\u0012I\u0006C\u0005\u0003z\u0019\n\t\u0011\"!\u0003|!I!q\u0011\u0014\u0012\u0002\u0013\u0005\u00111 \u0005\n\u0005\u00133\u0013\u0013!C\u0001\u0005'A\u0011Ba#'#\u0003%\tA!\u0007\t\u0013\t5e%!A\u0005\u0002\n=\u0005\"\u0003BOME\u0005I\u0011AA~\u0011%\u0011yJJI\u0001\n\u0003\u0011\u0019\u0002C\u0005\u0003\"\u001a\n\n\u0011\"\u0001\u0003\u001a!I!1\u0015\u0014\u0002\u0002\u0013%!Q\u0015\u0002\u0012\u00032dgi\u001c:P]\u0016\u001cFO]1uK\u001eL(B\u0001\u001b6\u0003\u0015\t7\r^8s\u0015\u00051\u0014\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001su\u001a\u0005C\u0001\u001e<\u001b\u0005\u0019\u0014B\u0001\u001f4\u0005I\u0019V\u000f]3sm&\u001cxN]*ue\u0006$XmZ=\u0011\u0005y\nU\"A \u000b\u0003\u0001\u000bQa]2bY\u0006L!AQ \u0003\u000fA\u0013x\u000eZ;diB\u0011A\t\u0014\b\u0003\u000b*s!AR%\u000e\u0003\u001dS!\u0001S\u001c\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0001\u0015BA&@\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u0014(\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005-{\u0014AD7bq:\u0013xJ\u001a*fiJLWm]\u000b\u0002#B\u0011aHU\u0005\u0003'~\u00121!\u00138u\u0003=i\u0017\r\u001f(s\u001f\u001a\u0014V\r\u001e:jKN\u0004\u0013aD<ji\"Lg\u000eV5nKJ\u000bgnZ3\u0016\u0003]\u0003\"\u0001W/\u000e\u0003eS!AW.\u0002\u0011\u0011,(/\u0019;j_:T!\u0001X \u0002\u0015\r|gnY;se\u0016tG/\u0003\u0002_3\nAA)\u001e:bi&|g.\u0001\txSRD\u0017N\u001c+j[\u0016\u0014\u0016M\\4fA\u0005qAn\\4hS:<WI\\1cY\u0016$W#\u00012\u0011\u0005y\u001a\u0017B\u00013@\u0005\u001d\u0011un\u001c7fC:\fq\u0002\\8hO&tw-\u00128bE2,G\rI\u0001\bI\u0016\u001c\u0017\u000eZ3s+\u0005A\u0007CA5m\u001d\tQ$.\u0003\u0002lg\u0005\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0013\tigNA\u0004EK\u000eLG-\u001a:\u000b\u0005-\u001c\u0014\u0001\u00033fG&$WM\u001d\u0011\u0002\rqJg.\u001b;?)\u0011\u0011XO^<\u0015\u0005M$\bC\u0001\u001e\u0001\u0011\u00151\u0017\u00021\u0001i\u0011\u001dy\u0015\u0002%AA\u0002ECq!V\u0005\u0011\u0002\u0003\u0007q\u000bC\u0004a\u0013A\u0005\t\u0019\u00012\u0015\u000bML(p_@\t\u000b=S\u0001\u0019A)\t\u000bUS\u0001\u0019A,\t\u000b\u0019T\u0001\u0019\u0001?\u0011\u0005%l\u0018B\u0001@o\u0005!QE)Z2jI\u0016\u0014\b\"\u00021\u000b\u0001\u0004\u0011G#C:\u0002\u0004\u0005\u0015\u0011QCA\f\u0011\u0015y5\u00021\u0001R\u0011\u0019)6\u00021\u0001\u0002\bA!\u0011\u0011BA\n\u001b\t\tYA\u0003\u0003\u0002\u000e\u0005=\u0011\u0001\u0002;j[\u0016T!!!\u0005\u0002\t)\fg/Y\u0005\u0004=\u0006-\u0001\"\u00024\f\u0001\u0004a\b\"\u00021\f\u0001\u0004\u0011GcB:\u0002\u001c\u0005u\u0011q\u0004\u0005\u0006\u001f2\u0001\r!\u0015\u0005\u0006+2\u0001\ra\u0016\u0005\u0006M2\u0001\r\u0001 \u000b\bg\u0006\r\u0012QEA\u0014\u0011\u0015yU\u00021\u0001R\u0011\u0019)V\u00021\u0001\u0002\b!)a-\u0004a\u0001yR91/a\u000b\u0002.\u0005=\u0002\"B(\u000f\u0001\u0004\t\u0006\"B+\u000f\u0001\u00049\u0006bBA\u0019\u001d\u0001\u0007\u00111G\u0001\tiJ\f\u0007/\u0012=jiB1\u0011QGA\u001e\u0003\u007fi!!a\u000e\u000b\t\u0005e\u0012qB\u0001\u0005Y\u0006tw-\u0003\u0003\u0002>\u0005]\"\u0001C%uKJ\f'\r\\31\t\u0005\u0005\u0013Q\u000b\t\u0007\u0003\u0007\nY%!\u0015\u000f\t\u0005\u0015\u0013q\t\t\u0003\r~J1!!\u0013@\u0003\u0019\u0001&/\u001a3fM&!\u0011QJA(\u0005\u0015\u0019E.Y:t\u0015\r\tIe\u0010\t\u0005\u0003'\n)\u0006\u0004\u0001\u0005\u0019\u0005]\u0013qFA\u0001\u0002\u0003\u0015\t!!\u0017\u0003\u0007}#S'\u0005\u0003\u0002\\\u0005\u0005\u0004c\u0001 \u0002^%\u0019\u0011qL \u0003\u000f9{G\u000f[5oOB\u0019A)a\u0019\n\u0007\u0005\u0015dJA\u0005UQJ|w/\u00192mKR91/!\u001b\u0002l\u00055\u0004\"B(\u0010\u0001\u0004\t\u0006BB+\u0010\u0001\u0004\t9\u0001C\u0004\u00022=\u0001\r!a\u001c\u0011\r\u0005U\u00121HA9a\u0011\t\u0019(a\u001e\u0011\r\u0005\r\u00131JA;!\u0011\t\u0019&a\u001e\u0005\u0019\u0005e\u0014QNA\u0001\u0002\u0003\u0015\t!!\u0017\u0003\u0007}#c\u0007F\u0004t\u0003{\ny(!!\t\u000b=\u0003\u0002\u0019A)\t\u000bU\u0003\u0002\u0019A,\t\u000b\u0019\u0004\u0002\u0019\u00015\u0015\u000fM\f))a\"\u0002\n\")q*\u0005a\u0001#\"1Q+\u0005a\u0001\u0003\u000fAQAZ\tA\u0002!$Ra]AG\u0003\u001fCQ\u0001\u0019\nA\u0002\tDQA\u001a\nA\u0002!$2a]AJ\u0011\u001517\u00031\u0001i\u00035\u0011X\r\u001e:jKN<\u0016N\u001c3poV\u0011\u0011\u0011\u0014\t\b}\u0005m\u0015qTAP\u0013\r\tij\u0010\u0002\u0007)V\u0004H.\u001a\u001a\u0011\ty\n\t+U\u0005\u0004\u0003G{$AB(qi&|g.\u0001\bsKR\u0014\u0018.Z:XS:$wn\u001e\u0011\u0002+!\fg\u000e\u001a7f\u0007\"LG\u000e\u001a+fe6Lg.\u0019;fIRA\u00111VAY\u0003w\u000b)\rE\u0002?\u0003[K1!a,@\u0005\u0011)f.\u001b;\t\u000f\u0005Mf\u00031\u0001\u00026\u000691m\u001c8uKb$\bc\u0001\u001e\u00028&\u0019\u0011\u0011X\u001a\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;\t\u000f\u0005uf\u00031\u0001\u0002@\u0006)1\r[5mIB\u0019!(!1\n\u0007\u0005\r7G\u0001\u0005BGR|'OU3g\u0011\u001d\t9M\u0006a\u0001\u0003\u0013\f\u0001b\u00195jY\u0012\u0014XM\u001c\t\u0006\t\u0006-\u0017qX\u0005\u0004\u0003{q\u0015A\u00049s_\u000e,7o\u001d$bS2,(/\u001a\u000b\u000f\u0003W\u000b\t.a5\u0002X\u0006e\u0017Q\\At\u0011\u001d\t\u0019l\u0006a\u0001\u0003kCa!!6\u0018\u0001\u0004\u0011\u0017a\u0002:fgR\f'\u000f\u001e\u0005\b\u0003{;\u0002\u0019AA`\u0011\u001d\tYn\u0006a\u0001\u0003C\nQaY1vg\u0016Dq!a8\u0018\u0001\u0004\t\t/A\u0003ti\u0006$8\u000fE\u0002;\u0003GL1!!:4\u0005E\u0019\u0005.\u001b7e%\u0016\u001cH/\u0019:u'R\fGo\u001d\u0005\b\u0003\u000f<\u0002\u0019AAu!\u0015!\u00151ZAq\u0003\u0011\u0019w\u000e]=\u0015\u0011\u0005=\u00181_A{\u0003o$2a]Ay\u0011\u00151\u0007\u00041\u0001i\u0011\u001dy\u0005\u0004%AA\u0002ECq!\u0016\r\u0011\u0002\u0003\u0007q\u000bC\u0004a1A\u0005\t\u0019\u00012\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011Q \u0016\u0004#\u0006}8F\u0001B\u0001!\u0011\u0011\u0019A!\u0004\u000e\u0005\t\u0015!\u0002\u0002B\u0004\u0005\u0013\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\t-q(\u0001\u0006b]:|G/\u0019;j_:LAAa\u0004\u0003\u0006\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%eU\u0011!Q\u0003\u0016\u0004/\u0006}\u0018AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u00057Q3AYA\u0000\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011!\u0011\u0005\t\u0005\u0003k\u0011\u0019#\u0003\u0003\u0003&\u0005]\"AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\t5\"1\u0007\t\u0004}\t=\u0012b\u0001B\u0019\u007f\t\u0019\u0011I\\=\t\u0011\tUb$!AA\u0002E\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001B\u001e!\u0019\u0011iDa\u0011\u0003.5\u0011!q\b\u0006\u0004\u0005\u0003z\u0014AC2pY2,7\r^5p]&!!Q\tB \u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007\t\u0014Y\u0005C\u0005\u00036\u0001\n\t\u00111\u0001\u0003.\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u0011\tC!\u0015\t\u0011\tU\u0012%!AA\u0002E\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002#\u0006AAo\\*ue&tw\r\u0006\u0002\u0003\"\u00051Q-];bYN$2A\u0019B0\u0011%\u0011)\u0004JA\u0001\u0002\u0004\u0011i#A\tBY24uN](oKN#(/\u0019;fOf\u0004\"A\u000f\u0014\u0014\u000b\u0019\u00129G!\u001c\u0011\u0007y\u0012I'C\u0002\u0003l}\u0012a!\u00118z%\u00164\u0007\u0003\u0002B8\u0005kj!A!\u001d\u000b\t\tM\u0014qB\u0001\u0003S>L1!\u0014B9)\t\u0011\u0019'A\u0003baBd\u0017\u0010\u0006\u0005\u0003~\t\u0005%1\u0011BC)\r\u0019(q\u0010\u0005\u0006M&\u0002\r\u0001\u001b\u0005\b\u001f&\u0002\n\u00111\u0001R\u0011\u001d)\u0016\u0006%AA\u0002]Cq\u0001Y\u0015\u0011\u0002\u0003\u0007!-A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00132\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u0012\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0002\u000fUt\u0017\r\u001d9msR!!\u0011\u0013BM!\u0015q\u0014\u0011\u0015BJ!\u0019q$QS)XE&\u0019!qS \u0003\rQ+\b\u000f\\34\u0011!\u0011Y*LA\u0001\u0002\u0004\u0019\u0018a\u0001=%a\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$3'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0003(B!\u0011Q\u0007BU\u0013\u0011\u0011Y+a\u000e\u0003\r=\u0013'.Z2u\u0001")
public class AllForOneStrategy
extends SupervisorStrategy
implements Product,
Serializable {
    private final int maxNrOfRetries;
    private final Duration withinTimeRange;
    private final boolean loggingEnabled;
    private final PartialFunction<Throwable, SupervisorStrategy.Directive> decider;
    private final Tuple2<Option<Object>, Option<Object>> retriesWindow;

    public static boolean $lessinit$greater$default$3() {
        return AllForOneStrategy$.MODULE$.$lessinit$greater$default$3();
    }

    public static Duration $lessinit$greater$default$2() {
        return AllForOneStrategy$.MODULE$.$lessinit$greater$default$2();
    }

    public static int $lessinit$greater$default$1() {
        return AllForOneStrategy$.MODULE$.$lessinit$greater$default$1();
    }

    public static Option<Tuple3<Object, Duration, Object>> unapply(AllForOneStrategy x$0) {
        return AllForOneStrategy$.MODULE$.unapply(x$0);
    }

    public static boolean apply$default$3() {
        return AllForOneStrategy$.MODULE$.apply$default$3();
    }

    public static Duration apply$default$2() {
        return AllForOneStrategy$.MODULE$.apply$default$2();
    }

    public static int apply$default$1() {
        return AllForOneStrategy$.MODULE$.apply$default$1();
    }

    public static AllForOneStrategy apply(int maxNrOfRetries, Duration withinTimeRange, boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        return AllForOneStrategy$.MODULE$.apply(maxNrOfRetries, withinTimeRange, loggingEnabled, decider);
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

    private Tuple2<Option<Object>, Option<Object>> retriesWindow() {
        return this.retriesWindow;
    }

    @Override
    public void handleChildTerminated(ActorContext context, ActorRef child, Iterable<ActorRef> children) {
    }

    @Override
    public void processFailure(ActorContext context, boolean restart, ActorRef child, Throwable cause, ChildRestartStats stats, Iterable<ChildRestartStats> children) {
        if (children.nonEmpty()) {
            if (restart && children.forall((Function1<ChildRestartStats, Object> & Serializable)x$5 -> BoxesRunTime.boxToBoolean(x$5.requestRestartPermission(this.retriesWindow())))) {
                children.foreach((Function1<ChildRestartStats, Object> & Serializable)crs -> {
                    AllForOneStrategy.$anonfun$processFailure$2(this, cause, child, crs);
                    return BoxedUnit.UNIT;
                });
                return;
            }
            children.foreach((Function1<ChildRestartStats, Object> & Serializable)c -> {
                context.stop(c.child());
                return BoxedUnit.UNIT;
            });
            return;
        }
    }

    public AllForOneStrategy copy(int maxNrOfRetries, Duration withinTimeRange, boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        return new AllForOneStrategy(maxNrOfRetries, withinTimeRange, loggingEnabled, decider);
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
        return "AllForOneStrategy";
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
        return x$1 instanceof AllForOneStrategy;
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
        if (!(object instanceof AllForOneStrategy)) return false;
        boolean bl = true;
        if (!bl) return false;
        AllForOneStrategy allForOneStrategy = (AllForOneStrategy)x$1;
        if (this.maxNrOfRetries() != allForOneStrategy.maxNrOfRetries()) return false;
        if (this.loggingEnabled() != allForOneStrategy.loggingEnabled()) return false;
        Duration duration = this.withinTimeRange();
        Duration duration2 = allForOneStrategy.withinTimeRange();
        if (duration == null) {
            if (duration2 != null) {
                return false;
            }
        } else if (!duration.equals(duration2)) return false;
        if (!allForOneStrategy.canEqual(this)) return false;
        return true;
    }

    public static final /* synthetic */ int $anonfun$retriesWindow$1(Duration x$4) {
        return (int)x$4.toMillis();
    }

    public static final /* synthetic */ void $anonfun$processFailure$2(AllForOneStrategy $this, Throwable cause$1, ActorRef child$1, ChildRestartStats crs) {
        ActorRef actorRef2 = crs.child();
        ActorRef actorRef3 = child$1;
        $this.restartChild(crs.child(), cause$1, actorRef2 == null ? actorRef3 != null : !((Object)actorRef2).equals(actorRef3));
    }

    public AllForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        this.maxNrOfRetries = maxNrOfRetries;
        this.withinTimeRange = withinTimeRange;
        this.loggingEnabled = loggingEnabled;
        this.decider = decider;
        Product.$init$(this);
        this.retriesWindow = new Tuple2(SupervisorStrategy$.MODULE$.maxNrOfRetriesOption(maxNrOfRetries), SupervisorStrategy$.MODULE$.withinTimeRangeOption(withinTimeRange).map((Function1<Duration, Object> & Serializable)x$4 -> BoxesRunTime.boxToInteger(AllForOneStrategy.$anonfun$retriesWindow$1(x$4))));
    }

    public AllForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, Function<Throwable, SupervisorStrategy.Directive> decider, boolean loggingEnabled) {
        this(maxNrOfRetries, withinTimeRange, loggingEnabled, SupervisorStrategy$.MODULE$.makeDecider(decider));
    }

    public AllForOneStrategy(int maxNrOfRetries, java.time.Duration withinTimeRange, Function<Throwable, SupervisorStrategy.Directive> decider, boolean loggingEnabled) {
        this(maxNrOfRetries, (Duration)JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(withinTimeRange)), loggingEnabled, SupervisorStrategy$.MODULE$.makeDecider(decider));
    }

    public AllForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, Function<Throwable, SupervisorStrategy.Directive> decider) {
        this(maxNrOfRetries, withinTimeRange, AllForOneStrategy$.MODULE$.$lessinit$greater$default$3(), SupervisorStrategy$.MODULE$.makeDecider(decider));
    }

    public AllForOneStrategy(int maxNrOfRetries, java.time.Duration withinTimeRange, Function<Throwable, SupervisorStrategy.Directive> decider) {
        this(maxNrOfRetries, (Duration)JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(withinTimeRange)), AllForOneStrategy$.MODULE$.$lessinit$greater$default$3(), SupervisorStrategy$.MODULE$.makeDecider(decider));
    }

    public AllForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, java.lang.Iterable<Class<? extends Throwable>> trapExit) {
        this(maxNrOfRetries, withinTimeRange, AllForOneStrategy$.MODULE$.$lessinit$greater$default$3(), SupervisorStrategy$.MODULE$.makeDecider(trapExit));
    }

    public AllForOneStrategy(int maxNrOfRetries, java.time.Duration withinTimeRange, java.lang.Iterable<Class<? extends Throwable>> trapExit) {
        this(maxNrOfRetries, (Duration)JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(withinTimeRange)), AllForOneStrategy$.MODULE$.$lessinit$greater$default$3(), SupervisorStrategy$.MODULE$.makeDecider(trapExit));
    }

    public AllForOneStrategy(int maxNrOfRetries, Duration withinTimeRange, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        this(maxNrOfRetries, withinTimeRange, AllForOneStrategy$.MODULE$.$lessinit$greater$default$3(), decider);
    }

    public AllForOneStrategy(int maxNrOfRetries, java.time.Duration withinTimeRange, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        this(maxNrOfRetries, (Duration)JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(withinTimeRange)), AllForOneStrategy$.MODULE$.$lessinit$greater$default$3(), decider);
    }

    public AllForOneStrategy(boolean loggingEnabled, PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        boolean x$1 = loggingEnabled;
        int x$2 = AllForOneStrategy$.MODULE$.$lessinit$greater$default$1();
        Duration x$3 = AllForOneStrategy$.MODULE$.$lessinit$greater$default$2();
        PartialFunction<Throwable, SupervisorStrategy.Directive> x$4 = decider;
        this(x$2, x$3, x$1, x$4);
    }

    public AllForOneStrategy(PartialFunction<Throwable, SupervisorStrategy.Directive> decider) {
        this(AllForOneStrategy$.MODULE$.$lessinit$greater$default$1(), AllForOneStrategy$.MODULE$.$lessinit$greater$default$2(), AllForOneStrategy$.MODULE$.$lessinit$greater$default$3(), decider);
    }
}

