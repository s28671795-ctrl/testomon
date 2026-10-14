/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.ConfigurationException;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.DynamicAccess;
import akka.actor.Props;
import akka.annotation.InternalStableApi;
import akka.dispatch.BoundedMailbox;
import akka.dispatch.Dispatchers$;
import akka.dispatch.Envelope;
import akka.dispatch.Mailbox;
import akka.dispatch.MailboxType;
import akka.dispatch.Mailboxes$;
import akka.dispatch.MessageQueue;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.ProducesPushTimeoutSemanticsMailbox;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMailbox;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.SystemMessageList$;
import akka.event.EventStream;
import akka.event.Logging;
import akka.util.Reflect$;
import akka.util.ccompat.package$JavaConverters$;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import scala.$less$colon$less$;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.Tuple2;
import scala.collection.ArrayOps$;
import scala.collection.Map;
import scala.collection.StringOps$;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.concurrent.duration.Duration$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.LazyRef;
import scala.runtime.Nothing$;
import scala.runtime.Null$;
import scala.runtime.ScalaRunTime$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\tmv!B\u001a5\u0011\u0003Id!B\u001e5\u0011\u0003a\u0004\"B\"\u0002\t\u0003!\u0005bB#\u0002\u0005\u0004%)A\u0012\u0005\u0007\u0015\u0006\u0001\u000bQB$\t\u000f-\u000b!\u0019!C\u0003\u0019\"1\u0001+\u0001Q\u0001\u000e5Cq!U\u0001C\u0002\u0013\u0015!\u000b\u0003\u0004W\u0003\u0001\u0006ia\u0015\u0004\u0006wQ\u0002ag\u0016\u0005\t1&\u0011)\u0019!C\u00013\"AA-\u0003B\u0001B\u0003%!\f\u0003\u0005f\u0013\t\u0015\r\u0011\"\u0001g\u0011!i\u0017B!A!\u0002\u00139\u0007\u0002\u00038\n\u0005\u0003\u0005\u000b\u0011B8\t\u0011IL!\u0011!Q\u0001\nMDQaQ\u0005\u0005\u0002YDq\u0001`\u0005C\u0002\u0013\u0005Q\u0010C\u0004\u0002\u0004%\u0001\u000b\u0011\u0002@\t\u0011\u0005\u0015\u0011\u0002\"\u00017\u0003\u000fA\u0011\"a\u000b\n\u0005\u0004%I!!\f\t\u0011\u0005}\u0013\u0002)A\u0005\u0003_A\u0011\"!\u0019\n\u0005\u0004%I!a\u0019\t\u0011\u0005m\u0014\u0002)A\u0005\u0003KBq!a#\n\t\u0003\ti\tC\u0004\u0002\u0014&!\t!!&\t\u0013\u0005\u0015\u0016B1A\u0005\u000e\u0005\u001d\u0006\u0002CA^\u0013\u0001\u0006i!!+\t\u000f\u0005u\u0016\u0002\"\u0001\u0002@\"I\u0011\u0011]\u0005A\u0002\u0013%\u00111\u001d\u0005\n\u0003WL\u0001\u0019!C\u0005\u0003[D\u0001\"a=\nA\u0003&\u0011Q\u001d\u0005\n\u0003kL\u0001\u0019!C\u0005\u0003GD\u0011\"a>\n\u0001\u0004%I!!?\t\u0011\u0005u\u0018\u0002)Q\u0005\u0003KDq!a@\n\t\u0003\u0011\t\u0001C\u0004\u0003,%!\tA!\f\t\u0011\tu\u0012\u0002\"\u00057\u0005\u007fAqAa\u0014\n\t\u0003\u0011\t\u0006C\u0004\u0003`%!IA!\u0019\t\u000f\t=\u0014\u0002\"\u0003\u0003r!I!QO\u0005C\u0002\u0013%!q\u000f\u0005\t\u0005sJ\u0001\u0015!\u0003\u0003\u001a!9!1P\u0005\u0005\u000e\tu\u0004b\u0002B\f\u0013\u0011%!1\u0011\u0005\n\u0005\u000fK!\u0019!C\u0005\u0005\u0013C\u0001Ba(\nA\u0003%!1\u0012\u0005\n\u0005CK!\u0019!C\u0005\u0005GC\u0001B!*\nA\u0003%!\u0011\u0014\u0005\t\u0005OKAQ\u0001\u001c\u0003*\"9!1W\u0005\u0005\n\tU\u0016!C'bS2\u0014w\u000e_3t\u0015\t)d'\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u00059\u0014\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002;\u00035\tAGA\u0005NC&d'm\u001c=fgN\u0011\u0011!\u0010\t\u0003}\u0005k\u0011a\u0010\u0006\u0002\u0001\u0006)1oY1mC&\u0011!i\u0010\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005I\u0014\u0001\u0005#fM\u0006,H\u000e^'bS2\u0014w\u000e_%e+\u00059u\"\u0001%\"\u0003%\u000b!$Y6lC:\n7\r^8s]\u0011,g-Y;mi6j\u0017-\u001b7c_b\f\u0011\u0003R3gCVdG/T1jY\n|\u00070\u00133!\u0003Qqu.T1jY\n|\u0007PU3rk&\u0014X-\\3oiV\tQjD\u0001OC\u0005y\u0015\u0001A\u0001\u0016\u001d>l\u0015-\u001b7c_b\u0014V-];je\u0016lWM\u001c;!\u0003U\u0011u.\u001e8eK\u0012\u001c\u0015\r]1dSRL\bK]3gSb,\u0012aU\b\u0002)\u0006\nQ+A\tc_VtG-\u001a3.G\u0006\u0004\u0018mY5usj\naCQ8v]\u0012,GmQ1qC\u000eLG/\u001f)sK\u001aL\u0007\u0010I\n\u0003\u0013u\n\u0001b]3ui&twm]\u000b\u00025B\u00111,\u0019\b\u00039~k\u0011!\u0018\u0006\u0003=Z\nQ!Y2u_JL!\u0001Y/\u0002\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0005\u0003E\u000e\u0014\u0001bU3ui&twm\u001d\u0006\u0003Av\u000b\u0011b]3ui&twm\u001d\u0011\u0002\u0017\u00154XM\u001c;TiJ,\u0017-\\\u000b\u0002OB\u0011\u0001n[\u0007\u0002S*\u0011!NN\u0001\u0006KZ,g\u000e^\u0005\u0003Y&\u00141\"\u0012<f]R\u001cFO]3b[\u0006aQM^3oiN#(/Z1nA\u0005iA-\u001f8b[&\u001c\u0017iY2fgN\u0004\"\u0001\u00189\n\u0005El&!\u0004#z]\u0006l\u0017nY!dG\u0016\u001c8/A\u0006eK\u0006$G*\u001a;uKJ\u001c\bC\u0001/u\u0013\t)XL\u0001\u0005BGR|'OU3g)\u00159\b0\u001f>|!\tQ\u0014\u0002C\u0003Y!\u0001\u0007!\fC\u0003f!\u0001\u0007q\rC\u0003o!\u0001\u0007q\u000eC\u0003s!\u0001\u00071/A\teK\u0006$G*\u001a;uKJl\u0015-\u001b7c_b,\u0012A \t\u0003u}L1!!\u00015\u0005\u001di\u0015-\u001b7c_b\f!\u0003Z3bI2+G\u000f^3s\u001b\u0006LGNY8yA\u0005QrN\u001c#fC\u0012dU\r\u001e;fe6\u000b\u0017\u000e\u001c2pq\u0016s\u0017/^3vKR1\u0011\u0011BA\b\u0003'\u00012APA\u0006\u0013\r\tia\u0010\u0002\u0005+:LG\u000f\u0003\u0004\u0002\u0012M\u0001\ra]\u0001\te\u0016\u001cW-\u001b<fe\"9\u0011QC\nA\u0002\u0005]\u0011\u0001C3om\u0016dw\u000e]3\u0011\u0007i\nI\"C\u0002\u0002\u001cQ\u0012\u0001\"\u00128wK2|\u0007/\u001a\u0015\u0004'\u0005}\u0001\u0003BA\u0011\u0003Oi!!a\t\u000b\u0007\u0005\u0015b'\u0001\u0006b]:|G/\u0019;j_:LA!!\u000b\u0002$\t\t\u0012J\u001c;fe:\fGn\u0015;bE2,\u0017\t]5\u000215\f\u0017\u000e\u001c2pqRK\b/Z\"p]\u001aLw-\u001e:bi>\u00148/\u0006\u0002\u00020AA\u0011\u0011GA \u0003\u0007\nI&\u0004\u0002\u00024)!\u0011QGA\u001c\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0005\u0003s\tY$\u0001\u0003vi&d'BAA\u001f\u0003\u0011Q\u0017M^1\n\t\u0005\u0005\u00131\u0007\u0002\u0012\u0007>t7-\u001e:sK:$\b*Y:i\u001b\u0006\u0004\b\u0003BA#\u0003'rA!a\u0012\u0002PA\u0019\u0011\u0011J \u000e\u0005\u0005-#bAA'q\u00051AH]8pizJ1!!\u0015@\u0003\u0019\u0001&/\u001a3fM&!\u0011QKA,\u0005\u0019\u0019FO]5oO*\u0019\u0011\u0011K \u0011\u0007i\nY&C\u0002\u0002^Q\u00121\"T1jY\n|\u0007\u0010V=qK\u0006IR.Y5mE>DH+\u001f9f\u0007>tg-[4ve\u0006$xN]:!\u0003=i\u0017-\u001b7c_b\u0014\u0015N\u001c3j]\u001e\u001cXCAA3!!\t)%a\u001a\u0002l\u0005\r\u0013\u0002BA5\u0003/\u00121!T1qa\u0011\ti'a\u001e\u0011\r\u0005\u0015\u0013qNA:\u0013\u0011\t\t(a\u0016\u0003\u000b\rc\u0017m]:\u0011\t\u0005U\u0014q\u000f\u0007\u0001\t-\tIhFA\u0001\u0002\u0003\u0015\t!! \u0003\u0007}#\u0013'\u0001\tnC&d'm\u001c=CS:$\u0017N\\4tAE!\u0011qPAC!\rq\u0014\u0011Q\u0005\u0004\u0003\u0007{$a\u0002(pi\"Lgn\u001a\t\u0004}\u0005\u001d\u0015bAAE\u007f\t\u0019\u0011I\\=\u0002\r1|wn[;q)\u0011\tI&a$\t\u000f\u0005E\u0005\u00041\u0001\u0002D\u0005\u0011\u0011\u000eZ\u0001\u0012Y>|7.\u001e9CsF+X-^3UsB,G\u0003BA-\u0003/Cq!!'\u001a\u0001\u0004\tY*A\u0005rk\u0016,X\rV=qKB\"\u0011QTAQ!\u0019\t)%a\u001c\u0002 B!\u0011QOAQ\t1\t\u0019+a&\u0002\u0002\u0003\u0005)\u0011AA?\u0005\ryFeM\u0001\te6\f8\t\\1tgV\u0011\u0011\u0011V\b\u0003\u0003W\u001b#!!,1\t\u0005=\u0016q\u0017\t\u0006u\u0005E\u0016QW\u0005\u0004\u0003g#$\u0001\u0006*fcVL'/Z:NKN\u001c\u0018mZ3Rk\u0016,X\r\u0005\u0003\u0002v\u0005]FaCA]7\u0005\u0005\t\u0011!B\u0001\u0003{\u00121a\u0018\u00135\u0003%\u0011X.]\"mCN\u001c\b%A\bhKR\u0014V-];je\u0016$G+\u001f9f)\u0011\t\t-a31\t\u0005\r\u0017q\u0019\t\u0007\u0003\u000b\ny'!2\u0011\t\u0005U\u0014q\u0019\u0003\f\u0003\u0013d\u0012\u0011!A\u0001\u0006\u0003\tiHA\u0002`IYBq!!4\u001d\u0001\u0004\ty-\u0001\u0006bGR|'o\u00117bgN\u0004D!!5\u0002VB1\u0011QIA8\u0003'\u0004B!!\u001e\u0002V\u0012a\u0011q[Af\u0003\u0003\u0005\tQ!\u0001\u0002Z\n\u0019q\fJ\u001b\u0012\t\u0005}\u00141\u001c\t\u00049\u0006u\u0017bAAp;\n)\u0011i\u0019;pe\u0006AR.Y5mE>D8+\u001b>f/\u0006\u0014h.\u001b8h\u0013N\u001cX/\u001a3\u0016\u0005\u0005\u0015\bc\u0001 \u0002h&\u0019\u0011\u0011^ \u0003\u000f\t{w\u000e\\3b]\u0006aR.Y5mE>D8+\u001b>f/\u0006\u0014h.\u001b8h\u0013N\u001cX/\u001a3`I\u0015\fH\u0003BA\u0005\u0003_D\u0011\"!=\u001f\u0003\u0003\u0005\r!!:\u0002\u0007a$\u0013'A\rnC&d'm\u001c=TSj,w+\u0019:oS:<\u0017j]:vK\u0012\u0004\u0013AJ7bS2\u0014w\u000e\u001f(p]j+'o\u001c)vg\"$\u0016.\\3pkR<\u0016M\u001d8j]\u001eL5o];fI\u0006QS.Y5mE>Dhj\u001c8[KJ|\u0007+^:i)&lWm\\;u/\u0006\u0014h.\u001b8h\u0013N\u001cX/\u001a3`I\u0015\fH\u0003BA\u0005\u0003wD\u0011\"!=\"\u0003\u0003\u0005\r!!:\u0002O5\f\u0017\u000e\u001c2pq:{gNW3s_B+8\u000f\u001b+j[\u0016|W\u000f^,be:LgnZ%tgV,G\rI\u0001\u0016O\u0016$X*Y5mE>D(+Z9vSJ,W.\u001a8u)\u0011\u0011\u0019A!\u00061\t\t\u0015!\u0011\u0003\t\u0007\u0005\u000f\u0011iAa\u0004\u000e\u0005\t%!\u0002\u0002B\u0006\u0003w\tA\u0001\\1oO&!\u0011\u0011\u000fB\u0005!\u0011\t)H!\u0005\u0005\u0017\u0005\r6%!A\u0001\u0002\u000b\u0005!1C\t\u0004\u0003\u007fj\u0004b\u0002B\fG\u0001\u0007!\u0011D\u0001\u0007G>tg-[4\u0011\t\tm!qE\u0007\u0003\u0005;QAAa\u0006\u0003 )!!\u0011\u0005B\u0012\u0003!!\u0018\u0010]3tC\u001a,'B\u0001B\u0013\u0003\r\u0019w.\\\u0005\u0005\u0005S\u0011iB\u0001\u0004D_:4\u0017nZ\u0001\u001cO\u0016$\bK]8ek\u000e,G-T3tg\u0006<W-U;fk\u0016$\u0016\u0010]3\u0015\t\t=\"\u0011\b\u0019\u0005\u0005c\u0011)\u0004\u0005\u0004\u0002F\u0005=$1\u0007\t\u0005\u0003k\u0012)\u0004B\u0006\u00038\u0011\n\t\u0011!A\u0003\u0002\u0005u$aA0%o!9!1\b\u0013A\u0002\u0005e\u0013aC7bS2\u0014w\u000e\u001f+za\u0016\fabZ3u\u001b\u0006LGNY8y)f\u0004X\r\u0006\u0004\u0002Z\t\u0005#1\n\u0005\b\u0005\u0007*\u0003\u0019\u0001B#\u0003\u0015\u0001(o\u001c9t!\ra&qI\u0005\u0004\u0005\u0013j&!\u0002)s_B\u001c\bb\u0002B'K\u0001\u0007!\u0011D\u0001\u0011I&\u001c\b/\u0019;dQ\u0016\u00148i\u001c8gS\u001e\fq\u0002[1t%\u0016\fX/\u001b:fIRK\b/\u001a\u000b\u0005\u0003K\u0014\u0019\u0006C\u0004\u0002N\u001a\u0002\rA!\u00161\t\t]#1\f\t\u0007\u0003\u000b\nyG!\u0017\u0011\t\u0005U$1\f\u0003\r\u0005;\u0012\u0019&!A\u0001\u0002\u000b\u0005\u0011\u0011\u001c\u0002\u0005?\u0012\n\u0014'\u0001\u0005m_>\\W\u000f]%e)\u0011\t\u0019Ea\u0019\t\u000f\u0005eu\u00051\u0001\u0003fA\"!q\rB6!\u0019\t)%a\u001c\u0003jA!\u0011Q\u000fB6\t1\u0011iGa\u0019\u0002\u0002\u0003\u0005)\u0011AA?\u0005\u0011yF%\r\u001a\u0002%1|wn[;q\u0007>tg-[4ve\u0006$xN\u001d\u000b\u0005\u00033\u0012\u0019\bC\u0004\u0002\u0012\"\u0002\r!a\u0011\u0002)\u0011,g-Y;mi6\u000b\u0017\u000e\u001c2pq\u000e{gNZ5h+\t\u0011I\"A\u000beK\u001a\fW\u000f\u001c;NC&d'm\u001c=D_:4\u0017n\u001a\u0011\u0002\t]\f'O\u001c\u000b\u0005\u0003\u0013\u0011y\bC\u0004\u0003\u0002.\u0002\r!a\u0011\u0002\u00075\u001cx\r\u0006\u0003\u0003\u001a\t\u0015\u0005bBAIY\u0001\u0007\u00111I\u0001\u0013gR\f7\u000f[\"ba\u0006\u001c\u0017\u000e^=DC\u000eDW-\u0006\u0002\u0003\fB1!Q\u0012BJ\u0005/k!Aa$\u000b\t\tE\u00151G\u0001\u0007CR|W.[2\n\t\tU%q\u0012\u0002\u0010\u0003R|W.[2SK\u001a,'/\u001a8dKBA\u0011QIA4\u0003\u0007\u0012I\nE\u0002?\u00057K1A!(@\u0005\rIe\u000e^\u0001\u0014gR\f7\u000f[\"ba\u0006\u001c\u0017\u000e^=DC\u000eDW\rI\u0001\u0015I\u00164\u0017-\u001e7u'R\f7\u000f[\"ba\u0006\u001c\u0017\u000e^=\u0016\u0005\te\u0015!\u00063fM\u0006,H\u000e^*uCND7)\u00199bG&$\u0018\u0010I\u0001\u000egR\f7\u000f[\"ba\u0006\u001c\u0017\u000e^=\u0015\r\te%1\u0016BX\u0011\u001d\u0011i+\ra\u0001\u0003\u0007\n!\u0002Z5ta\u0006$8\r[3s\u0011\u001d\u0011\t,\ra\u0001\u0003\u0007\nq!\\1jY\n|\u00070A\fti\u0006\u001c\bnQ1qC\u000eLG/\u001f$s_6\u001cuN\u001c4jOR1!\u0011\u0014B\\\u0005sCqA!,3\u0001\u0004\t\u0019\u0005C\u0004\u00032J\u0002\r!a\u0011")
public class Mailboxes {
    private final ActorSystem.Settings settings;
    private final EventStream eventStream;
    private final DynamicAccess dynamicAccess;
    public final ActorRef akka$dispatch$Mailboxes$$deadLetters;
    private final Mailbox deadLetterMailbox;
    private final ConcurrentHashMap<String, MailboxType> mailboxTypeConfigurators;
    private final scala.collection.immutable.Map<Class<?>, String> mailboxBindings;
    private boolean mailboxSizeWarningIssued;
    private boolean mailboxNonZeroPushTimeoutWarningIssued;
    private final Config defaultMailboxConfig;
    private final AtomicReference<scala.collection.immutable.Map<String, Object>> stashCapacityCache;
    private final int defaultStashCapacity;

    public static String BoundedCapacityPrefix() {
        return Mailboxes$.MODULE$.BoundedCapacityPrefix();
    }

    public static String NoMailboxRequirement() {
        return Mailboxes$.MODULE$.NoMailboxRequirement();
    }

    public static String DefaultMailboxId() {
        return Mailboxes$.MODULE$.DefaultMailboxId();
    }

    public ActorSystem.Settings settings() {
        return this.settings;
    }

    public EventStream eventStream() {
        return this.eventStream;
    }

    public Mailbox deadLetterMailbox() {
        return this.deadLetterMailbox;
    }

    @InternalStableApi
    public void onDeadLetterMailboxEnqueue(ActorRef receiver, Envelope envelope) {
        Object object = envelope.message();
        if (object instanceof DeadLetter) {
            return;
        }
        this.akka$dispatch$Mailboxes$$deadLetters.tell(new DeadLetter(object, envelope.sender(), receiver), envelope.sender());
    }

    private ConcurrentHashMap<String, MailboxType> mailboxTypeConfigurators() {
        return this.mailboxTypeConfigurators;
    }

    private scala.collection.immutable.Map<Class<?>, String> mailboxBindings() {
        return this.mailboxBindings;
    }

    public MailboxType lookup(String id) {
        return this.lookupConfigurator(id);
    }

    public MailboxType lookupByQueueType(Class<?> queueType) {
        return this.lookup(this.lookupId(queueType));
    }

    private final Class<RequiresMessageQueue<?>> rmqClass() {
        return RequiresMessageQueue.class;
    }

    public Class<?> getRequiredType(Class<? extends Actor> actorClass) {
        Type type = Reflect$.MODULE$.findMarker(actorClass, RequiresMessageQueue.class);
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type type2 = (Type)ArrayOps$.MODULE$.head$extension(Predef$.MODULE$.refArrayOps(parameterizedType.getActualTypeArguments()));
            if (type2 instanceof Class) {
                Class clazz = (Class)type2;
                return clazz;
            }
            throw new IllegalArgumentException(new StringBuilder(65).append("no wildcard type allowed in RequireMessageQueue argument (was [").append(type2).append("])").toString());
        }
        throw new IllegalArgumentException(new StringBuilder(31).append("Unexpected actor class marker: ").append(type).toString());
    }

    private boolean mailboxSizeWarningIssued() {
        return this.mailboxSizeWarningIssued;
    }

    private void mailboxSizeWarningIssued_$eq(boolean x$1) {
        this.mailboxSizeWarningIssued = x$1;
    }

    private boolean mailboxNonZeroPushTimeoutWarningIssued() {
        return this.mailboxNonZeroPushTimeoutWarningIssued;
    }

    private void mailboxNonZeroPushTimeoutWarningIssued_$eq(boolean x$1) {
        this.mailboxNonZeroPushTimeoutWarningIssued = x$1;
    }

    public Class<?> getMailboxRequirement(Config config) {
        String string = config.getString("mailbox-requirement");
        switch (string == null ? 0 : string.hashCode()) {
            case 0: {
                if (!"".equals(string)) break;
                return MessageQueue.class;
            }
        }
        return this.dynamicAccess.getClassFor(string, ClassTag$.MODULE$.AnyRef()).get();
    }

    public Class<?> getProducedMessageQueueType(MailboxType mailboxType) {
        Class<ProducesMessageQueue> pmqClass = ProducesMessageQueue.class;
        if (!pmqClass.isAssignableFrom(mailboxType.getClass())) {
            return MessageQueue.class;
        }
        Type type = Reflect$.MODULE$.findMarker(mailboxType.getClass(), pmqClass);
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type type2 = (Type)ArrayOps$.MODULE$.head$extension(Predef$.MODULE$.refArrayOps(parameterizedType.getActualTypeArguments()));
            if (type2 instanceof Class) {
                Class clazz = (Class)type2;
                return clazz;
            }
            throw new IllegalArgumentException(new StringBuilder(66).append("no wildcard type allowed in ProducesMessageQueue argument (was [").append(type2).append("])").toString());
        }
        throw new IllegalArgumentException(new StringBuilder(38).append("Unexpected message queue type marker: ").append(type).toString());
    }

    /*
     * Unable to fully structure code
     */
    public MailboxType getMailboxType(Props props, Config dispatcherConfig) {
        actorRequirement$lzy = new LazyRef<T>();
        id = dispatcherConfig.getString("id");
        deploy = props.deploy();
        actorClass = props.actorClass();
        v0 = mailboxRequirement = this.getMailboxRequirement(dispatcherConfig);
        var10_8 = MessageQueue.class;
        v1 = (v0 == null ? var10_8 != null : v0.equals(var10_8) == false) ? true : (hasMailboxRequirement = false);
        if (!dispatcherConfig.hasPath("mailbox-type")) ** GOTO lbl-1000
        v2 = dispatcherConfig.getString("mailbox-type");
        var12_10 = "";
        if (v2 == null ? var12_10 != null : v2.equals(var12_10) == false) {
            v3 = true;
        } else lbl-1000:
        // 2 sources

        {
            v3 = hasMailboxType = false;
        }
        if (!hasMailboxType && !this.mailboxSizeWarningIssued() && dispatcherConfig.hasPath("mailbox-size")) {
            this.eventStream().publish(new Logging.Warning("mailboxes", this.getClass(), new StringBuilder(93).append("ignoring setting 'mailbox-size' for dispatcher [").append(id).append("], you need to specify 'mailbox-type=bounded'").toString()));
            this.mailboxSizeWarningIssued_$eq(true);
        }
        v4 = deploy.mailbox();
        var13_12 = "";
        if (v4 == null ? var13_12 != null : v4.equals(var13_12) == false) {
            return this.verifyRequirements$1(this.lookup(deploy.mailbox()), hasMailboxRequirement, mailboxRequirement, id, actorClass, actorRequirement$lzy);
        }
        v5 = deploy.dispatcher();
        var14_13 = "";
        if (v5 == null ? var14_13 != null : v5.equals(var14_13) == false) {
            v6 = deploy.dispatcher();
            var15_14 = "..";
            if ((v6 == null ? var15_14 != null : v6.equals(var15_14) == false) && hasMailboxType) {
                return this.verifyRequirements$1(this.lookup(dispatcherConfig.getString("id")), hasMailboxRequirement, mailboxRequirement, id, actorClass, actorRequirement$lzy);
            }
        }
        if (this.hasRequiredType(actorClass)) {
            try {
                v7 = this.verifyRequirements$1(this.lookupByQueueType(this.getRequiredType(actorClass)), hasMailboxRequirement, mailboxRequirement, id, actorClass, actorRequirement$lzy);
            }
            catch (Throwable var16_15) {
                var17_16 = var16_15;
                if (var17_16 != null && !(var18_17 = NonFatal$.MODULE$.unapply(var17_16)).isEmpty() && hasMailboxRequirement) {
                    v7 = this.verifyRequirements$1(this.lookupByQueueType(mailboxRequirement), hasMailboxRequirement, mailboxRequirement, id, actorClass, actorRequirement$lzy);
                }
                throw var16_15;
            }
            return v7;
        }
        if (hasMailboxRequirement) {
            return this.verifyRequirements$1(this.lookupByQueueType(mailboxRequirement), hasMailboxRequirement, mailboxRequirement, id, actorClass, actorRequirement$lzy);
        }
        return this.verifyRequirements$1(this.lookup("akka.actor.default-mailbox"), hasMailboxRequirement, mailboxRequirement, id, actorClass, actorRequirement$lzy);
    }

    public boolean hasRequiredType(Class<? extends Actor> actorClass) {
        return RequiresMessageQueue.class.isAssignableFrom(actorClass);
    }

    private String lookupId(Class<?> queueType) {
        Option option = this.mailboxBindings().get(queueType);
        if (None$.MODULE$.equals(option)) {
            throw new ConfigurationException(new StringBuilder(37).append("Mailbox Mapping for [").append(queueType).append("] not configured").toString());
        }
        if (option instanceof Some) {
            Some some = (Some)option;
            String s2 = (String)some.value();
            return s2;
        }
        throw new MatchError(option);
    }

    private MailboxType lookupConfigurator(String id) {
        MailboxType mailboxType;
        block13: {
            MailboxType mailboxType2;
            block14: {
                mailboxType = this.mailboxTypeConfigurators().get(id);
                if (mailboxType != null) break block13;
                String string = id;
                switch (string == null ? 0 : string.hashCode()) {
                    case 70311581: {
                        if (!"bounded".equals(string)) break;
                        mailboxType2 = new BoundedMailbox(this.settings(), this.config(id));
                        break block14;
                    }
                    case 1782112740: {
                        if (!"unbounded".equals(string)) break;
                        mailboxType2 = new UnboundedMailbox();
                        break block14;
                    }
                }
                if (id.startsWith("bounded-capacity:")) {
                    int capacity = StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(StringOps$.MODULE$.split$extension(Predef$.MODULE$.augmentString(id), ':')[1]));
                    mailboxType2 = new BoundedMailbox(capacity, Duration$.MODULE$.Zero());
                } else {
                    if (!this.settings().config().hasPath(id)) {
                        throw new ConfigurationException(new StringBuilder(30).append("Mailbox Type [").append(id).append("] not configured").toString());
                    }
                    Config conf = this.config(id);
                    String string2 = conf.getString("mailbox-type");
                    switch (string2 == null ? 0 : string2.hashCode()) {
                        case 0: {
                            if (!"".equals(string2)) break;
                            throw new ConfigurationException(new StringBuilder(48).append("The setting mailbox-type, defined in [").append(id).append("] is empty").toString());
                        }
                    }
                    $colon$colon<Nothing$> args2 = new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(ActorSystem.Settings.class), this.settings())), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(Config.class), conf)), Nil$.MODULE$));
                    MailboxType mailboxType3 = (MailboxType)((Object)this.dynamicAccess.createInstanceFor(string2, args2, ClassTag$.MODULE$.apply(MailboxType.class)).recover(new Serializable(null, string2, id){
                        private static final long serialVersionUID = 0L;
                        private final String x1$1;
                        private final String id$2;

                        public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                            A1 A1 = x1;
                            throw new IllegalArgumentException(new StringBuilder(165).append("Cannot instantiate MailboxType [").append(this.x1$1).append("], defined in [").append(this.id$2).append("], make sure it has a public").append(" constructor with [akka.actor.ActorSystem.Settings, com.typesafe.config.Config] parameters").toString(), A1);
                        }

                        public final boolean isDefinedAt(Throwable x1) {
                            Throwable throwable = x1;
                            return true;
                        }
                        {
                            this.x1$1 = x1$1;
                            this.id$2 = id$2;
                        }
                    }).get());
                    if (!this.mailboxNonZeroPushTimeoutWarningIssued()) {
                        MailboxType mailboxType4;
                        MailboxType mailboxType5 = mailboxType3;
                        if (mailboxType5 instanceof ProducesPushTimeoutSemanticsMailbox && ((ProducesPushTimeoutSemanticsMailbox)((Object)(mailboxType4 = mailboxType5))).pushTimeOut().toNanos() > 0L) {
                            this.warn(new StringBuilder(217).append("Configured potentially-blocking mailbox [").append(id).append("] configured with non-zero pushTimeOut (").append(((ProducesPushTimeoutSemanticsMailbox)((Object)mailboxType4)).pushTimeOut()).append("), ").append("which can lead to blocking behavior when sending messages to this mailbox. ").append("Avoid this by setting `").append(id).append(".mailbox-push-timeout-time` to `0`.").toString());
                            this.mailboxNonZeroPushTimeoutWarningIssued_$eq(true);
                        }
                    }
                    mailboxType2 = mailboxType3;
                }
            }
            MailboxType newConfigurator = mailboxType2;
            MailboxType mailboxType6 = this.mailboxTypeConfigurators().putIfAbsent(id, newConfigurator);
            if (mailboxType6 == null) {
                return newConfigurator;
            }
            return mailboxType6;
        }
        return mailboxType;
    }

    private Config defaultMailboxConfig() {
        return this.defaultMailboxConfig;
    }

    private final void warn(String msg) {
        this.eventStream().publish(new Logging.Warning("mailboxes", this.getClass(), msg));
    }

    private Config config(String id) {
        return ConfigFactory.parseMap(package$JavaConverters$.MODULE$.MapHasAsJava((Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("id"), id)}))).asJava()).withFallback(this.settings().config().getConfig(id)).withFallback(this.defaultMailboxConfig());
    }

    private AtomicReference<scala.collection.immutable.Map<String, Object>> stashCapacityCache() {
        return this.stashCapacityCache;
    }

    private int defaultStashCapacity() {
        return this.defaultStashCapacity;
    }

    public final int stashCapacity(String dispatcher, String mailbox) {
        String key;
        scala.collection.immutable.Map<String, Object> cache;
        Option option;
        String string = dispatcher;
        String string2 = "akka.actor.default-dispatcher";
        if (!(string != null ? !string.equals(string2) : string2 != null)) {
            String string3 = mailbox;
            String string4 = "akka.actor.default-mailbox";
            if (!(string3 != null ? !string3.equals(string4) : string4 != null)) {
                return this.defaultStashCapacity();
            }
        }
        if ((option = (cache = this.stashCapacityCache().get()).get(key = new StringBuilder(1).append(dispatcher).append("-").append(mailbox).toString())) instanceof Some) {
            Some some = (Some)option;
            int value = BoxesRunTime.unboxToInt(some.value());
            return value;
        }
        if (None$.MODULE$.equals(option)) {
            int value = this.stashCapacityFromConfig(dispatcher, mailbox);
            this.updateCache$1(cache, key, value);
            return value;
        }
        throw new MatchError(option);
    }

    private int stashCapacityFromConfig(String dispatcher, String mailbox) {
        Config disp = Dispatchers$.MODULE$.getConfig(this.settings().config(), dispatcher, Dispatchers$.MODULE$.getConfig$default$3());
        Config fallback = disp.withFallback(this.settings().config().getConfig("akka.actor.default-mailbox"));
        String string = mailbox;
        String string2 = "akka.actor.default-mailbox";
        Config config = !(string != null ? !string.equals(string2) : string2 != null) ? fallback : this.settings().config().getConfig(mailbox).withFallback(fallback);
        return config.getInt("stash-capacity");
    }

    private final /* synthetic */ Class actorRequirement$lzycompute$1(LazyRef actorRequirement$lzy$1, Class actorClass$1) {
        Class<?> clazz;
        LazyRef lazyRef = actorRequirement$lzy$1;
        synchronized (lazyRef) {
            clazz = actorRequirement$lzy$1.initialized() ? (Class<?>)actorRequirement$lzy$1.value() : actorRequirement$lzy$1.initialize(this.getRequiredType(actorClass$1));
        }
        return clazz;
    }

    private final Class actorRequirement$1(LazyRef actorRequirement$lzy$1, Class actorClass$1) {
        if (actorRequirement$lzy$1.initialized()) {
            return (Class)actorRequirement$lzy$1.value();
        }
        return this.actorRequirement$lzycompute$1(actorRequirement$lzy$1, actorClass$1);
    }

    private final /* synthetic */ Class mqType$lzycompute$1(LazyRef mqType$lzy$1, MailboxType mailboxType$1) {
        Class<?> clazz;
        LazyRef lazyRef = mqType$lzy$1;
        synchronized (lazyRef) {
            clazz = mqType$lzy$1.initialized() ? (Class<?>)mqType$lzy$1.value() : mqType$lzy$1.initialize(this.getProducedMessageQueueType(mailboxType$1));
        }
        return clazz;
    }

    private final Class mqType$1(LazyRef mqType$lzy$1, MailboxType mailboxType$1) {
        if (mqType$lzy$1.initialized()) {
            return (Class)mqType$lzy$1.value();
        }
        return this.mqType$lzycompute$1(mqType$lzy$1, mailboxType$1);
    }

    private final MailboxType verifyRequirements$1(MailboxType mailboxType, boolean hasMailboxRequirement$1, Class mailboxRequirement$1, String id$1, Class actorClass$1, LazyRef actorRequirement$lzy$1) {
        LazyRef mqType$lzy = new LazyRef();
        if (hasMailboxRequirement$1 && !mailboxRequirement$1.isAssignableFrom(this.mqType$1(mqType$lzy, mailboxType))) {
            throw new IllegalArgumentException(new StringBuilder(104).append("produced message queue type [").append(this.mqType$1(mqType$lzy, mailboxType)).append("] does not fulfill requirement for dispatcher [").append(id$1).append("]. ").append("Must be a subclass of [").append(mailboxRequirement$1).append("].").toString());
        }
        if (this.hasRequiredType(actorClass$1) && !this.actorRequirement$1(actorRequirement$lzy$1, actorClass$1).isAssignableFrom(this.mqType$1(mqType$lzy, mailboxType))) {
            throw new IllegalArgumentException(new StringBuilder(105).append("produced message queue type [").append(this.mqType$1(mqType$lzy, mailboxType)).append("] does not fulfill requirement for actor class [").append(actorClass$1).append("]. ").append("Must be a subclass of [").append(this.actorRequirement$1(actorRequirement$lzy$1, actorClass$1)).append("].").toString());
        }
        return mailboxType;
    }

    private final boolean updateCache$1(scala.collection.immutable.Map cache, String key, int value) {
        while (!this.stashCapacityCache().compareAndSet(cache, (scala.collection.immutable.Map<String, Object>)cache.updated(key, BoxesRunTime.boxToInteger(value)))) {
            cache = this.stashCapacityCache().get();
        }
        return true;
    }

    public Mailboxes(ActorSystem.Settings settings, EventStream eventStream, DynamicAccess dynamicAccess, ActorRef deadLetters) {
        this.settings = settings;
        this.eventStream = eventStream;
        this.dynamicAccess = dynamicAccess;
        this.akka$dispatch$Mailboxes$$deadLetters = deadLetters;
        this.deadLetterMailbox = new Mailbox(this){
            private final /* synthetic */ Mailboxes $outer;

            public void systemEnqueue(ActorRef receiver, SystemMessage handle2) {
                DeadLetter x$1 = new DeadLetter(handle2, receiver, receiver);
                ActorRef x$2 = this.$outer.akka$dispatch$Mailboxes$$deadLetters.$bang$default$2(x$1);
                this.$outer.akka$dispatch$Mailboxes$$deadLetters.$bang(x$1, x$2);
            }

            public SystemMessage systemDrain(SystemMessage newContents) {
                return SystemMessageList$.MODULE$.ENil();
            }

            public boolean hasSystemMessages() {
                return false;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super(new MessageQueue($outer){
                    private final /* synthetic */ Mailboxes $outer;

                    public void enqueue(ActorRef receiver, Envelope envelope) {
                        this.$outer.onDeadLetterMailboxEnqueue(receiver, envelope);
                    }

                    public Null$ dequeue() {
                        return null;
                    }

                    public boolean hasMessages() {
                        return false;
                    }

                    public int numberOfMessages() {
                        return 0;
                    }

                    public void cleanUp(ActorRef owner, MessageQueue deadLetters) {
                    }
                    {
                        if ($outer == null) {
                            throw null;
                        }
                        this.$outer = $outer;
                    }
                });
                this.becomeClosed();
            }
        };
        this.mailboxTypeConfigurators = new ConcurrentHashMap();
        this.mailboxBindings = (scala.collection.immutable.Map)package$JavaConverters$.MODULE$.MapHasAsScala(settings.config().getConfig("akka.actor.mailbox.requirements").root().unwrapped()).asScala().toMap($less$colon$less$.MODULE$.refl()).foldLeft(Predef$.MODULE$.Map().empty(), (Function2<scala.collection.immutable.Map, Tuple2, scala.collection.immutable.Map> & Serializable)(x0$1, x1$1) -> {
            Tuple2<scala.collection.immutable.Map, Tuple2> tuple2 = new Tuple2<scala.collection.immutable.Map, Tuple2>((scala.collection.immutable.Map)x0$1, (Tuple2)x1$1);
            if (tuple2 != null) {
                scala.collection.immutable.Map m4 = tuple2._1();
                Tuple2 tuple22 = tuple2._2();
                if (tuple22 != null) {
                    String k = (String)tuple22._1();
                    Object v = tuple22._2();
                    return (scala.collection.immutable.Map)((Object)$this.dynamicAccess.getClassFor(k, ClassTag$.MODULE$.Any()).map((Function1<Class, scala.collection.immutable.Map> & Serializable)x -> (scala.collection.immutable.Map)m4.updated(x, v.toString())).recover(new Serializable(null, k, v){
                        private static final long serialVersionUID = 0L;
                        private final String k$1;
                        private final Object v$1;

                        public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                            A1 A1 = x1;
                            throw new ConfigurationException(new StringBuilder(90).append("Type [").append(this.k$1).append("] specified as akka.actor.mailbox.requirement ").append("[").append(this.v$1).append("] in config can't be loaded due to [").append(A1.getMessage()).append("]").toString(), A1);
                        }

                        public final boolean isDefinedAt(Throwable x1) {
                            Throwable throwable = x1;
                            return true;
                        }
                        {
                            this.k$1 = k$1;
                            this.v$1 = v$1;
                        }
                    }).get());
                }
            }
            throw new MatchError(tuple2);
        });
        this.mailboxSizeWarningIssued = false;
        this.mailboxNonZeroPushTimeoutWarningIssued = false;
        this.defaultMailboxConfig = settings.config().getConfig("akka.actor.default-mailbox");
        this.stashCapacityCache = new AtomicReference<Object>(Predef$.MODULE$.Map().empty());
        this.defaultStashCapacity = this.stashCapacityFromConfig("akka.actor.default-dispatcher", "akka.actor.default-mailbox");
    }
}

