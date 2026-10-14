/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.Cell;
import akka.actor.Scheduler;
import akka.annotation.InternalStableApi;
import akka.dispatch.AbstractMessageDispatcher;
import akka.dispatch.BatchingExecutor;
import akka.dispatch.Envelope;
import akka.dispatch.Mailbox;
import akka.dispatch.MailboxType;
import akka.dispatch.Mailboxes;
import akka.dispatch.MessageDispatcher$;
import akka.dispatch.MessageDispatcherConfigurator;
import akka.dispatch.TaskInvocation;
import akka.dispatch.sysmsg.SystemMessage;
import akka.event.EventStream;
import akka.event.Logging;
import akka.util.Index;
import akka.util.Unsafe;
import scala.Function0;
import scala.concurrent.BlockContext;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t\u0005vAB\u001c9\u0011\u0003QDH\u0002\u0004?q!\u0005!h\u0010\u0005\u0006\r\u0006!\t\u0001\u0013\u0005\b\u0013\u0006\u0011\r\u0011\"\u0001K\u0011\u0019q\u0015\u0001)A\u0005\u0017\"9q*\u0001b\u0001\n\u0003Q\u0005B\u0002)\u0002A\u0003%1\nC\u0004R\u0003\t\u0007I\u0011\u0001&\t\rI\u000b\u0001\u0015!\u0003L\u0011\u001d\u0019\u0016A1A\u0005\u0006QCaaV\u0001!\u0002\u001b)\u0006\u0002\u0003-\u0002\u0011\u000b\u0007I\u0011A-\t\u000f\t}\u0015\u0001\"\u0001\u0002N\u001a)a\bOA\u0001C\"Aa.\u0004BC\u0002\u0013\u0005q\u000e\u0003\u0005t\u001b\t\u0005\t\u0015!\u0003q\u0011\u00151U\u0002\"\u0001u\u0011\u001d1XB1A\u0005\u0002]Daa_\u0007!\u0002\u0013A\bb\u0002?\u000e\u0005\u0004%\t! \u0005\b\u0003\u0013i\u0001\u0015!\u0003\u007f\u0011-\tY!\u0004a\u0001\u0002\u0003\u0006K!!\u0004\t\u0015\u0005mQ\u00021A\u0001B\u0003&1\n\u0003\u0004\u0002 5!IA\u0013\u0005\b\u0003CiAQBA\u0012\u0011\u001d\tI#\u0004C\u0003\u0003WAa!!\f\u000e\t\u001bQ\u0005bBA\u0018\u001b\u00115\u0011\u0011\u0007\u0005\t\u0003\u0003ja\u0011\u0003\u001e\u0002D!9\u00111M\u0007\u0007\u0002\u0005\u0015\u0004bBA?\u001b\u0011\u0015\u0011q\u0010\u0005\b\u0003\u001fkAQAAI\u0011\u001d\t)*\u0004C\u000b\u0003/Cq!!'\u000e\t+\nY\nC\u0004\u000226!\t%a-\t\u000f\u0005-W\u0002\"\u0004\u0002N\"9\u0011Q\\\u0007\u0005\n\u00055\u0007\"CAp\u001b\t\u0007IQBAq\u0011!\tI/\u0004Q\u0001\u000e\u0005\r\b\u0002CAv\u001b\u0011E!(!<\t\u0011\u0005EX\u0002\"\u0005;\u0003gD\u0011\"a>\u000e\u0005\u0004%I!!?\t\u0011\t\u0005Q\u0002)A\u0005\u0003wD\u0001Ba\u0006\u000e\r#Q$\u0011\u0004\u0005\t\u0005OiA\u0011\u0003\u001e\u0003*!A!QF\u0007\u0005\u0012i\u0012y\u0003\u0003\u0005\u0003451\tB\u000fB\u001b\u0011\u001dITB\"\u0005;\u0005\u0017B\u0001Ba\u0016\u000e\r#Q$\u0011\f\u0005\b\u0005Oja\u0011\u0003\u001eK\u0011!\u0011I'\u0004D\tu\t-\u0004B\u0003B:\u001b\t\u0007IQ\u0003\u001e\u0002\u0018\"A!QO\u0007!\u0002\u001b\t\u0019\u0004\u0003\u0005\u0003\u000051\tB\u000fBA\u0011!\u0011Y)\u0004D\tu\u00055\u0017!E'fgN\fw-\u001a#jgB\fGo\u00195fe*\u0011\u0011HO\u0001\tI&\u001c\b/\u0019;dQ*\t1(\u0001\u0003bW.\f\u0007CA\u001f\u0002\u001b\u0005A$!E'fgN\fw-\u001a#jgB\fGo\u00195feN\u0011\u0011\u0001\u0011\t\u0003\u0003\u0012k\u0011A\u0011\u0006\u0002\u0007\u0006)1oY1mC&\u0011QI\u0011\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?\u0007\u0001!\u0012\u0001P\u0001\f+:\u001b6\tS#E+2+E)F\u0001L!\t\tE*\u0003\u0002N\u0005\n\u0019\u0011J\u001c;\u0002\u0019Us5k\u0011%F\tVcU\t\u0012\u0011\u0002\u0013M\u001b\u0005*\u0012#V\u0019\u0016#\u0015AC*D\u0011\u0016#U\u000bT#EA\u0005Y!+R*D\u0011\u0016#U\u000bT#E\u00031\u0011ViU\"I\u000b\u0012+F*\u0012#!\u0003\u0015!WMY;h+\u0005)v\"\u0001,\u001a\u0003\u0001\ta\u0001Z3ck\u001e\u0004\u0013AB1di>\u00148/F\u0001[!\u0015Yf\f\u0019BM\u001b\u0005a&BA/;\u0003\u0011)H/\u001b7\n\u0005}c&!B%oI\u0016D\bCA\u001f\u000e'\u0011i!-\u001a5\u0011\u0005u\u001a\u0017B\u000139\u0005e\t%m\u001d;sC\u000e$X*Z:tC\u001e,G)[:qCR\u001c\u0007.\u001a:\u0011\u0005u2\u0017BA49\u0005A\u0011\u0015\r^2iS:<W\t_3dkR|'\u000f\u0005\u0002jY6\t!N\u0003\u0002l\u0005\u0006Q1m\u001c8dkJ\u0014XM\u001c;\n\u00055T'\u0001G#yK\u000e,H/[8o\u0007>tG/\u001a=u\u000bb,7-\u001e;pe\u0006a1m\u001c8gS\u001e,(/\u0019;peV\t\u0001\u000f\u0005\u0002>c&\u0011!\u000f\u000f\u0002\u001e\u001b\u0016\u001c8/Y4f\t&\u001c\b/\u0019;dQ\u0016\u00148i\u001c8gS\u001e,(/\u0019;pe\u0006i1m\u001c8gS\u001e,(/\u0019;pe\u0002\"\"\u0001Y;\t\u000b9\u0004\u0002\u0019\u00019\u0002\u00135\f\u0017\u000e\u001c2pq\u0016\u001cX#\u0001=\u0011\u0005uJ\u0018B\u0001>9\u0005%i\u0015-\u001b7c_b,7/\u0001\u0006nC&d'm\u001c=fg\u0002\n1\"\u001a<f]R\u001cFO]3b[V\ta\u0010E\u0002\u0000\u0003\u000bi!!!\u0001\u000b\u0007\u0005\r!(A\u0003fm\u0016tG/\u0003\u0003\u0002\b\u0005\u0005!aC#wK:$8\u000b\u001e:fC6\fA\"\u001a<f]R\u001cFO]3b[\u0002\nqdX5oQ\u0006\u0014\u0017\u000e^1oiN$uNT8u\u0007\u0006dG.T3ESJ,7\r\u001e7z!\r\t\u0015qB\u0005\u0004\u0003#\u0011%\u0001\u0002'p]\u001eD3!FA\u000b!\r\t\u0015qC\u0005\u0004\u00033\u0011%\u0001\u0003<pY\u0006$\u0018\u000e\\3\u0002I}\u001b\b.\u001e;e_^t7k\u00195fIVdW\rR8O_R\u001c\u0015\r\u001c7NK\u0012K'/Z2uYfD3AFA\u000b\u0003qy\u0006O]3wK:$\bK]5wCR,WK\\;tK\u0012,%/Y:ve\u0016\fa\"\u00193e\u0013:D\u0017MY5uC:$8\u000f\u0006\u0003\u0002\u000e\u0005\u0015\u0002bBA\u00141\u0001\u0007\u0011QB\u0001\u0004C\u0012$\u0017aC5oQ\u0006\u0014\u0017\u000e^1oiN,\"!!\u0004\u0002!MDW\u000f\u001e3po:\u001c6\r[3ek2,\u0017AF;qI\u0006$Xm\u00155vi\u0012|wO\\*dQ\u0016$W\u000f\\3\u0015\r\u0005M\u0012\u0011HA\u001f!\r\t\u0015QG\u0005\u0004\u0003o\u0011%a\u0002\"p_2,\u0017M\u001c\u0005\u0007\u0003wY\u0002\u0019A&\u0002\r\u0015D\b/Z2u\u0011\u0019\tyd\u0007a\u0001\u0017\u00061Q\u000f\u001d3bi\u0016\fQb\u0019:fCR,W*Y5mE>DHCBA#\u0003\u0017\nI\u0006E\u0002>\u0003\u000fJ1!!\u00139\u0005\u001di\u0015-\u001b7c_bDq!!\u0014\u001d\u0001\u0004\ty%A\u0003bGR|'\u000f\u0005\u0003\u0002R\u0005USBAA*\u0015\r\tiEO\u0005\u0005\u0003/\n\u0019F\u0001\u0003DK2d\u0007bBA.9\u0001\u0007\u0011QL\u0001\f[\u0006LGNY8y)f\u0004X\rE\u0002>\u0003?J1!!\u00199\u0005-i\u0015-\u001b7c_b$\u0016\u0010]3\u0002\u0005%$WCAA4!\u0011\tI'a\u001e\u000f\t\u0005-\u00141\u000f\t\u0004\u0003[\u0012UBAA8\u0015\r\t\thR\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005U$)\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003s\nYH\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003k\u0012\u0015AB1ui\u0006\u001c\u0007\u000e\u0006\u0003\u0002\u0002\u0006\u001d\u0005cA!\u0002\u0004&\u0019\u0011Q\u0011\"\u0003\tUs\u0017\u000e\u001e\u0005\b\u0003\u001br\u0002\u0019AAE!\u0011\t\t&a#\n\t\u00055\u00151\u000b\u0002\n\u0003\u000e$xN]\"fY2\fa\u0001Z3uC\u000eDG\u0003BAA\u0003'Cq!!\u0014 \u0001\u0004\tI)A\bsKN,(-\\5u\u001f:\u0014En\\2l+\t\t\u0019$\u0001\tv]\n\fGo\u00195fI\u0016CXmY;uKR!\u0011\u0011QAO\u0011\u001d\ty*\ta\u0001\u0003C\u000b\u0011A\u001d\t\u0005\u0003G\u000bi+\u0004\u0002\u0002&*!\u0011qUAU\u0003\u0011a\u0017M\\4\u000b\u0005\u0005-\u0016\u0001\u00026bm\u0006LA!a,\u0002&\nA!+\u001e8oC\ndW-A\u0007sKB|'\u000f\u001e$bS2,(/\u001a\u000b\u0005\u0003\u0003\u000b)\fC\u0004\u00028\n\u0002\r!!/\u0002\u0003Q\u0004B!a/\u0002F:!\u0011QXAa\u001d\u0011\ti'a0\n\u0003\rK1!a1C\u0003\u001d\u0001\u0018mY6bO\u0016LA!a2\u0002J\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0004\u0003\u0007\u0014\u0015\u0001J5g'\u0016t7/\u001b2mKR{Gi\\*p)\",gnU2iK\u0012,H.Z*ikR$wn\u001e8\u0015\u0005\u0005\u0005\u0005fA\u0012\u0002RB!\u00111[Am\u001b\t\t)NC\u0002\u0002X\n\u000b!\"\u00198o_R\fG/[8o\u0013\u0011\tY.!6\u0003\u000fQ\f\u0017\u000e\u001c:fG\u000612o\u00195fIVdWm\u00155vi\u0012|wO\\!di&|g.A\u0006uCN\\7\t\\3b]V\u0004XCAAr!\u0015\t\u0015Q]AA\u0013\r\t9O\u0011\u0002\n\rVt7\r^5p]B\nA\u0002^1tW\u000ecW-\u00198va\u0002\n\u0001B]3hSN$XM\u001d\u000b\u0005\u0003\u0003\u000by\u000fC\u0004\u0002N\u001d\u0002\r!!#\u0002\u0015Ut'/Z4jgR,'\u000f\u0006\u0003\u0002\u0002\u0006U\bbBA'Q\u0001\u0007\u0011\u0011R\u0001\u000fg\",H\u000fZ8x]\u0006\u001bG/[8o+\t\tYP\u0005\u0004\u0002~\n\r!\u0011\u0002\u0004\u0007\u0003\u007fT\u0003!a?\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0002\u001fMDW\u000f\u001e3po:\f5\r^5p]\u0002\u0002B!a)\u0003\u0006%!!qAAS\u0005\u0019y%M[3diB!!1\u0002B\t\u001d\u0011\t\tF!\u0004\n\t\t=\u00111K\u0001\n'\u000eDW\rZ;mKJLAAa\u0005\u0003\u0016\tqA+Y:l%VtwJ\\\"m_N,'\u0002\u0002B\b\u0003'\nqb\u001d5vi\u0012|wO\u001c+j[\u0016|W\u000f^\u000b\u0003\u00057\u0001BA!\b\u0003$5\u0011!q\u0004\u0006\u0004\u0005CQ\u0017\u0001\u00033ve\u0006$\u0018n\u001c8\n\t\t\u0015\"q\u0004\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0003\u001d\u0019Xo\u001d9f]\u0012$B!!!\u0003,!9\u0011Q\n\u0017A\u0002\u0005%\u0015A\u0002:fgVlW\r\u0006\u0003\u0002\u0002\nE\u0002bBA'[\u0001\u0007\u0011\u0011R\u0001\u000fgf\u001cH/Z7ESN\u0004\u0018\r^2i)\u0019\t\tIa\u000e\u0003<!9!\u0011\b\u0018A\u0002\u0005%\u0015\u0001\u0003:fG\u0016Lg/\u001a:\t\u000f\tub\u00061\u0001\u0003@\u0005Q\u0011N\u001c<pG\u0006$\u0018n\u001c8\u0011\t\t\u0005#qI\u0007\u0003\u0005\u0007R1A!\u00129\u0003\u0019\u0019\u0018p]7tO&!!\u0011\nB\"\u00055\u0019\u0016p\u001d;f[6+7o]1hKR1\u0011\u0011\u0011B'\u0005\u001fBqA!\u000f0\u0001\u0004\tI\tC\u0004\u0003>=\u0002\rA!\u0015\u0011\u0007u\u0012\u0019&C\u0002\u0003Va\u0012\u0001\"\u00128wK2|\u0007/Z\u0001\u0015e\u0016<\u0017n\u001d;fe\u001a{'/\u0012=fGV$\u0018n\u001c8\u0015\u0011\u0005M\"1\fB0\u0005GBqA!\u00181\u0001\u0004\t)%\u0001\u0003nE>D\bb\u0002B1a\u0001\u0007\u00111G\u0001\u000fQ\u0006\u001cX*Z:tC\u001e,\u0007*\u001b8u\u0011\u001d\u0011)\u0007\ra\u0001\u0003g\tA\u0003[1t'f\u001cH/Z7NKN\u001c\u0018mZ3IS:$\u0018A\u0003;ie>,x\r\u001b9vi\u00061B\u000f\u001b:pk\u001eD\u0007/\u001e;EK\u0006$G.\u001b8f)&lW-\u0006\u0002\u0003nA!!Q\u0004B8\u0013\u0011\u0011\tHa\b\u0003\u0011\u0011+(/\u0019;j_:\fq$[:UQJ|Wo\u001a5qkR$U-\u00193mS:,G+[7f\t\u00164\u0017N\\3e\u0003\u0001J7\u000f\u00165s_V<\u0007\u000e];u\t\u0016\fG\r\\5oKRKW.\u001a#fM&tW\r\u001a\u0011)\u0007Q\u0012I\bE\u0002B\u0005wJ1A! C\u0005\u0019Ig\u000e\\5oK\u0006YQ\r_3dkR,G+Y:l)\u0011\t\tIa!\t\u000f\tuR\u00071\u0001\u0003\u0006B\u0019QHa\"\n\u0007\t%\u0005H\u0001\bUCN\\\u0017J\u001c<pG\u0006$\u0018n\u001c8\u0002\u0011MDW\u000f\u001e3po:D3A\u000eBH!\u0011\u0011\tJ!&\u000e\u0005\tM%bAAlu%!!q\u0013BJ\u0005EIe\u000e^3s]\u0006d7\u000b^1cY\u0016\f\u0005/\u001b\t\u0005\u0003#\u0012Y*\u0003\u0003\u0003\u001e\u0006M#\u0001C!di>\u0014(+\u001a4\u0002\u0017A\u0014\u0018N\u001c;BGR|'o\u001d")
public abstract class MessageDispatcher
extends AbstractMessageDispatcher
implements BatchingExecutor,
ExecutionContextExecutor {
    private final MessageDispatcherConfigurator configurator;
    private final Mailboxes mailboxes;
    private final EventStream eventStream;
    private volatile long _inhabitantsDoNotCallMeDirectly;
    private volatile int _shutdownScheduleDoNotCallMeDirectly;
    private final Function0<BoxedUnit> taskCleanup;
    private final Scheduler.TaskRunOnClose shutdownAction;
    private final boolean isThroughputDeadlineTimeDefined;
    private ThreadLocal<BatchingExecutor.AbstractBatch> akka$dispatch$BatchingExecutor$$_tasksLocal;
    private ThreadLocal<BlockContext> akka$dispatch$BatchingExecutor$$_blockContext;

    public static void printActors() {
        MessageDispatcher$.MODULE$.printActors();
    }

    public static Index<MessageDispatcher, ActorRef> actors() {
        return MessageDispatcher$.MODULE$.actors();
    }

    public static boolean debug() {
        return MessageDispatcher$.MODULE$.debug();
    }

    public static int RESCHEDULED() {
        return MessageDispatcher$.MODULE$.RESCHEDULED();
    }

    public static int SCHEDULED() {
        return MessageDispatcher$.MODULE$.SCHEDULED();
    }

    public static int UNSCHEDULED() {
        return MessageDispatcher$.MODULE$.UNSCHEDULED();
    }

    @Override
    public ExecutionContext prepare() {
        return ExecutionContext.prepare$(this);
    }

    @Override
    public void execute(Runnable runnable2) {
        BatchingExecutor.execute$(this, runnable2);
    }

    @Override
    public boolean batchable(Runnable runnable2) {
        return BatchingExecutor.batchable$(this, runnable2);
    }

    @Override
    public ThreadLocal<BatchingExecutor.AbstractBatch> akka$dispatch$BatchingExecutor$$_tasksLocal() {
        return this.akka$dispatch$BatchingExecutor$$_tasksLocal;
    }

    @Override
    public ThreadLocal<BlockContext> akka$dispatch$BatchingExecutor$$_blockContext() {
        return this.akka$dispatch$BatchingExecutor$$_blockContext;
    }

    @Override
    public final void akka$dispatch$BatchingExecutor$_setter_$akka$dispatch$BatchingExecutor$$_tasksLocal_$eq(ThreadLocal<BatchingExecutor.AbstractBatch> x$1) {
        this.akka$dispatch$BatchingExecutor$$_tasksLocal = x$1;
    }

    @Override
    public final void akka$dispatch$BatchingExecutor$_setter_$akka$dispatch$BatchingExecutor$$_blockContext_$eq(ThreadLocal<BlockContext> x$1) {
        this.akka$dispatch$BatchingExecutor$$_blockContext = x$1;
    }

    public MessageDispatcherConfigurator configurator() {
        return this.configurator;
    }

    public Mailboxes mailboxes() {
        return this.mailboxes;
    }

    public EventStream eventStream() {
        return this.eventStream;
    }

    private int _preventPrivateUnusedErasure() {
        return this._shutdownScheduleDoNotCallMeDirectly;
    }

    private final long addInhabitants(long add) {
        long old = Unsafe.instance.getAndAddLong(this, AbstractMessageDispatcher.inhabitantsOffset, add);
        long ret = old + add;
        if (ret < 0L) {
            IllegalStateException e = new IllegalStateException("ACTOR SYSTEM CORRUPTED!!! A dispatcher can't have less than 0 inhabitants!");
            this.reportFailure(e);
            throw e;
        }
        return ret;
    }

    public final long inhabitants() {
        return Unsafe.instance.getLongVolatile(this, AbstractMessageDispatcher.inhabitantsOffset);
    }

    public final int akka$dispatch$MessageDispatcher$$shutdownSchedule() {
        return Unsafe.instance.getIntVolatile(this, AbstractMessageDispatcher.shutdownScheduleOffset);
    }

    public final boolean akka$dispatch$MessageDispatcher$$updateShutdownSchedule(int expect, int update) {
        return Unsafe.instance.compareAndSwapInt(this, AbstractMessageDispatcher.shutdownScheduleOffset, expect, update);
    }

    public abstract Mailbox createMailbox(Cell var1, MailboxType var2);

    public abstract String id();

    public final void attach(ActorCell actor) {
        this.register(actor);
        this.registerForExecution(actor.mailbox(), false, true);
    }

    public final void detach(ActorCell actor) {
        try {
            this.unregister(actor);
        }
        finally {
            this.ifSensibleToDoSoThenScheduleShutdown();
        }
    }

    @Override
    public final boolean resubmitOnBlock() {
        return true;
    }

    @Override
    public final void unbatchedExecute(Runnable r) {
        TaskInvocation invocation = new TaskInvocation(this.eventStream(), r, this.taskCleanup());
        this.addInhabitants(1L);
        try {
            this.executeTask(invocation);
        }
        catch (Throwable t2) {
            this.addInhabitants(-1L);
            throw t2;
        }
    }

    @Override
    public void reportFailure(Throwable t2) {
        Throwable throwable = t2;
        if (throwable instanceof Logging.LogEventException) {
            Logging.LogEventException logEventException = (Logging.LogEventException)throwable;
            this.eventStream().publish(logEventException.event());
            return;
        }
        this.eventStream().publish(new Logging.Error(t2, this.getClass().getName(), this.getClass(), t2.getMessage()));
    }

    private final void ifSensibleToDoSoThenScheduleShutdown() {
        block7: {
            BoxedUnit boxedUnit;
            while (this.inhabitants() <= 0L) {
                int n = this.akka$dispatch$MessageDispatcher$$shutdownSchedule();
                if (MessageDispatcher$.MODULE$.UNSCHEDULED() == n) {
                    if (!this.akka$dispatch$MessageDispatcher$$updateShutdownSchedule(MessageDispatcher$.MODULE$.UNSCHEDULED(), MessageDispatcher$.MODULE$.SCHEDULED())) continue;
                    this.akka$dispatch$MessageDispatcher$$scheduleShutdownAction();
                } else if (MessageDispatcher$.MODULE$.SCHEDULED() == n) {
                    if (!this.akka$dispatch$MessageDispatcher$$updateShutdownSchedule(MessageDispatcher$.MODULE$.SCHEDULED(), MessageDispatcher$.MODULE$.RESCHEDULED())) continue;
                } else if (MessageDispatcher$.MODULE$.RESCHEDULED() == n) {
                } else {
                    throw new IllegalArgumentException(new StringBuilder(31).append("Unexpected actor class marker: ").append(n).toString());
                }
                boxedUnit = BoxedUnit.UNIT;
                break block7;
            }
            boxedUnit = BoxedUnit.UNIT;
        }
    }

    public void akka$dispatch$MessageDispatcher$$scheduleShutdownAction() {
        try {
            this.configurator().prerequisites().scheduler().scheduleOnce(this.shutdownTimeout(), (Runnable)this.shutdownAction(), new ExecutionContext(this){
                private final /* synthetic */ MessageDispatcher $outer;

                public ExecutionContext prepare() {
                    return ExecutionContext.prepare$(this);
                }

                public void execute(Runnable runnable2) {
                    runnable2.run();
                }

                public void reportFailure(Throwable t2) {
                    this.$outer.reportFailure(t2);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    ExecutionContext.$init$(this);
                }
            });
        }
        catch (IllegalStateException illegalStateException) {
            this.shutdown();
            this.akka$dispatch$MessageDispatcher$$updateShutdownSchedule(MessageDispatcher$.MODULE$.SCHEDULED(), MessageDispatcher$.MODULE$.UNSCHEDULED());
        }
    }

    private final Function0<BoxedUnit> taskCleanup() {
        return this.taskCleanup;
    }

    public void register(ActorCell actor) {
        this.addInhabitants(1L);
    }

    public void unregister(ActorCell actor) {
        this.addInhabitants(-1L);
        Mailbox mailBox = actor.swapMailbox(this.mailboxes().deadLetterMailbox());
        mailBox.becomeClosed();
        mailBox.cleanUp();
    }

    private Scheduler.TaskRunOnClose shutdownAction() {
        return this.shutdownAction;
    }

    public abstract FiniteDuration shutdownTimeout();

    public void suspend(ActorCell actor) {
        Mailbox mbox = actor.mailbox();
        if (mbox.actor() == actor && mbox.dispatcher() == this) {
            mbox.suspend();
            return;
        }
    }

    public void resume(ActorCell actor) {
        Mailbox mbox = actor.mailbox();
        if (mbox.actor() == actor && mbox.dispatcher() == this && mbox.resume()) {
            this.registerForExecution(mbox, false, false);
            return;
        }
    }

    public abstract void systemDispatch(ActorCell var1, SystemMessage var2);

    public abstract void dispatch(ActorCell var1, Envelope var2);

    public abstract boolean registerForExecution(Mailbox var1, boolean var2, boolean var3);

    public abstract int throughput();

    public abstract Duration throughputDeadlineTime();

    public final boolean isThroughputDeadlineTimeDefined() {
        return this.isThroughputDeadlineTimeDefined;
    }

    public abstract void executeTask(TaskInvocation var1);

    @InternalStableApi
    public abstract void shutdown();

    public MessageDispatcher(MessageDispatcherConfigurator configurator2) {
        this.configurator = configurator2;
        BatchingExecutor.$init$(this);
        ExecutionContext.$init$(this);
        this.mailboxes = configurator2.prerequisites().mailboxes();
        this.eventStream = configurator2.prerequisites().eventStream();
        this.taskCleanup = () -> {
            if (this.addInhabitants(-1L) == 0L) {
                this.ifSensibleToDoSoThenScheduleShutdown();
                return;
            }
        };
        this.shutdownAction = new Scheduler.TaskRunOnClose(this){
            private final /* synthetic */ MessageDispatcher $outer;

            public final void run() {
                block10: {
                    int n;
                    block11: {
                        do {
                            n = this.$outer.akka$dispatch$MessageDispatcher$$shutdownSchedule();
                            if (MessageDispatcher$.MODULE$.SCHEDULED() == n) {
                                try {
                                    BoxedUnit boxedUnit;
                                    if (this.$outer.inhabitants() == 0L) {
                                        this.$outer.shutdown();
                                        boxedUnit = BoxedUnit.UNIT;
                                    } else {
                                        boxedUnit = BoxedUnit.UNIT;
                                    }
                                    break block10;
                                }
                                finally {
                                    while (!this.$outer.akka$dispatch$MessageDispatcher$$updateShutdownSchedule(this.$outer.akka$dispatch$MessageDispatcher$$shutdownSchedule(), MessageDispatcher$.MODULE$.UNSCHEDULED())) {
                                    }
                                }
                            }
                            if (MessageDispatcher$.MODULE$.RESCHEDULED() != n) break block11;
                        } while (!this.$outer.akka$dispatch$MessageDispatcher$$updateShutdownSchedule(MessageDispatcher$.MODULE$.RESCHEDULED(), MessageDispatcher$.MODULE$.SCHEDULED()));
                        this.$outer.akka$dispatch$MessageDispatcher$$scheduleShutdownAction();
                        break block10;
                    }
                    if (MessageDispatcher$.MODULE$.UNSCHEDULED() == n) {
                    } else {
                        throw new IllegalArgumentException(new StringBuilder(31).append("Unexpected actor class marker: ").append(n).toString());
                    }
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
        this.isThroughputDeadlineTimeDefined = this.throughputDeadlineTime().toMillis() > 0L;
        Statics.releaseFence();
    }
}

