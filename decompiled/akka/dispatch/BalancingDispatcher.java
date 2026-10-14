/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.ActorSystemImpl;
import akka.actor.Cell;
import akka.actor.dungeon.Dispatch;
import akka.dispatch.DefaultSystemMessageQueue;
import akka.dispatch.Dispatcher;
import akka.dispatch.Envelope;
import akka.dispatch.ExecutorServiceFactoryProvider;
import akka.dispatch.LoadMetrics;
import akka.dispatch.Mailbox;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageDispatcherConfigurator;
import akka.dispatch.MessageQueue;
import akka.dispatch.sysmsg.EarliestFirstSystemMessageList$;
import akka.dispatch.sysmsg.NoMessage$;
import akka.dispatch.sysmsg.SystemMessage;
import akka.util.Helpers$;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import scala.None$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005ed!B\r\u001b\u0001qq\u0002\"C\u0012\u0001\u0005\u0003\u0005\u000b\u0011B\u0013)\u0011%a\u0003A!A!\u0002\u0013i#\bC\u0005=\u0001\t\u0005\t\u0015!\u0003>\u0003\"I!\t\u0001B\u0001B\u0003%1i\u0013\u0005\t\u0019\u0002\u0011\t\u0011)A\u0005\u001b\"A\u0001\u000b\u0001B\u0001B\u0003%\u0011\u000bC\u0005U\u0001\t\u0005\t\u0015!\u0003V1\"A!\f\u0001B\u0001B\u0003%1\fC\u0003_\u0001\u0011\u0005q\f\u0003\u0005j\u0001\t\u0007I\u0011\u0001\u000fk\u0011\u0019Q\b\u0001)A\u0005W\"A1\u0010\u0001b\u0001\n\u0003aB\u0010C\u0004\u0002\u0002\u0001\u0001\u000b\u0011B?\u0007\r\u0005\r\u0001\u0001BA\u0003\u0011)\t\u0019B\u0004BC\u0002\u0013\u0005\u0011Q\u0003\u0005\u000b\u0003;q!\u0011!Q\u0001\n\u0005]\u0001bCA\u0010\u001d\t\u0005\t\u0015!\u0003~\u0003CAaA\u0018\b\u0005\u0002\u0005\r\u0002bBA\u0017\u001d\u0011\u0005\u0013q\u0006\u0005\t\u0003o\u0001A\u0011\u000b\u000f\u0002:!A\u0011q\t\u0001\u0005Rq\tI\u0005\u0003\u0005\u0002N\u0001!\t\u0006HA(\u0011\u001dY\u0002\u0001\"\u0015\u001d\u0003'Bq!a\u0019\u0001\t#\tyCA\nCC2\fgnY5oO\u0012K7\u000f]1uG\",'O\u0003\u0002\u001c9\u0005AA-[:qCR\u001c\u0007NC\u0001\u001e\u0003\u0011\t7n[1\u0014\u0005\u0001y\u0002C\u0001\u0011\"\u001b\u0005Q\u0012B\u0001\u0012\u001b\u0005)!\u0015n\u001d9bi\u000eDWM]\u0001\u000e?\u000e|gNZ5hkJ\fGo\u001c:\u0004\u0001A\u0011\u0001EJ\u0005\u0003Oi\u0011Q$T3tg\u0006<W\rR5ta\u0006$8\r[3s\u0007>tg-[4ve\u0006$xN]\u0005\u0003S)\nAbY8oM&<WO]1u_JL!a\u000b\u000e\u0003#5+7o]1hK\u0012K7\u000f]1uG\",'/A\u0002`S\u0012\u0004\"AL\u001c\u000f\u0005=*\u0004C\u0001\u00194\u001b\u0005\t$B\u0001\u001a%\u0003\u0019a$o\\8u})\tA'A\u0003tG\u0006d\u0017-\u0003\u00027g\u00051\u0001K]3eK\u001aL!\u0001O\u001d\u0003\rM#(/\u001b8h\u0015\t14'\u0003\u0002<C\u0005\u0011\u0011\u000eZ\u0001\u000bi\"\u0014x.^4iaV$\bC\u0001 @\u001b\u0005\u0019\u0014B\u0001!4\u0005\rIe\u000e^\u0005\u0003y\u0005\na\u0003\u001e5s_V<\u0007\u000e];u\t\u0016\fG\r\\5oKRKW.\u001a\t\u0003\t&k\u0011!\u0012\u0006\u0003\r\u001e\u000b\u0001\u0002Z;sCRLwN\u001c\u0006\u0003\u0011N\n!bY8oGV\u0014(/\u001a8u\u0013\tQUI\u0001\u0005EkJ\fG/[8o\u0013\t\u0011\u0015%\u0001\u0007`[\u0006LGNY8y)f\u0004X\r\u0005\u0002!\u001d&\u0011qJ\u0007\u0002\f\u001b\u0006LGNY8y)f\u0004X-A\u0010`Kb,7-\u001e;peN+'O^5dK\u001a\u000b7\r^8ssB\u0013xN^5eKJ\u0004\"\u0001\t*\n\u0005MS\"AH#yK\u000e,Ho\u001c:TKJ4\u0018nY3GC\u000e$xN]=Qe>4\u0018\u000eZ3s\u0003Ay6\u000f[;uI><h\u000eV5nK>,H\u000f\u0005\u0002E-&\u0011q+\u0012\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0013\tI\u0016%A\btQV$Hm\\<o)&lWm\\;u\u0003=\tG\u000f^3naR$V-Y7X_J\\\u0007C\u0001 ]\u0013\ti6GA\u0004C_>dW-\u00198\u0002\rqJg.\u001b;?)%\u0001\u0017MY2eK\u001a<\u0007\u000e\u0005\u0002!\u0001!)1%\u0003a\u0001K!)A&\u0003a\u0001[!)A(\u0003a\u0001{!)!)\u0003a\u0001\u0007\")A*\u0003a\u0001\u001b\")\u0001+\u0003a\u0001#\")A+\u0003a\u0001+\")!,\u0003a\u00017\u0006!A/Z1n+\u0005Y\u0007c\u00017si6\tQN\u0003\u0002I]*\u0011q\u000e]\u0001\u0005kRLGNC\u0001r\u0003\u0011Q\u0017M^1\n\u0005Ml'!F\"p]\u000e,(O]3oiN[\u0017\u000e\u001d'jgR\u001cV\r\u001e\t\u0003kbl\u0011A\u001e\u0006\u0003or\tQ!Y2u_JL!!\u001f<\u0003\u0013\u0005\u001bGo\u001c:DK2d\u0017!\u0002;fC6\u0004\u0013\u0001D7fgN\fw-Z)vKV,W#A?\u0011\u0005\u0001r\u0018BA@\u001b\u00051iUm]:bO\u0016\fV/Z;f\u00035iWm]:bO\u0016\fV/Z;fA\tq1\u000b[1sS:<W*Y5mE>D8#\u0002\b\u0002\b\u00055\u0001c\u0001\u0011\u0002\n%\u0019\u00111\u0002\u000e\u0003\u000f5\u000b\u0017\u000e\u001c2pqB\u0019\u0001%a\u0004\n\u0007\u0005E!DA\rEK\u001a\fW\u000f\u001c;TsN$X-\\'fgN\fw-Z)vKV,\u0017AB:zgR,W.\u0006\u0002\u0002\u0018A\u0019Q/!\u0007\n\u0007\u0005maOA\bBGR|'oU=ti\u0016l\u0017*\u001c9m\u0003\u001d\u0019\u0018p\u001d;f[\u0002\nQbX7fgN\fw-Z)vKV,\u0017bA>\u0002\nQ1\u0011QEA\u0015\u0003W\u00012!a\n\u000f\u001b\u0005\u0001\u0001bBA\n%\u0001\u0007\u0011q\u0003\u0005\u0007\u0003?\u0011\u0002\u0019A?\u0002\u000f\rdW-\u00198VaR\u0011\u0011\u0011\u0007\t\u0004}\u0005M\u0012bAA\u001bg\t!QK\\5u\u00035\u0019'/Z1uK6\u000b\u0017\u000e\u001c2pqR1\u0011qAA\u001e\u0003\u0007Baa\u001e\u000bA\u0002\u0005u\u0002cA;\u0002@%\u0019\u0011\u0011\t<\u0003\t\r+G\u000e\u001c\u0005\u0007\u0003\u000b\"\u0002\u0019A'\u0002\u00175\f\u0017\u000e\u001c2pqRK\b/Z\u0001\te\u0016<\u0017n\u001d;feR!\u0011\u0011GA&\u0011\u00159X\u00031\u0001u\u0003))hN]3hSN$XM\u001d\u000b\u0005\u0003c\t\t\u0006C\u0003x-\u0001\u0007A\u000f\u0006\u0004\u00022\u0005U\u0013\u0011\f\u0005\u0007\u0003/:\u0002\u0019\u0001;\u0002\u0011I,7-Z5wKJDq!a\u0017\u0018\u0001\u0004\ti&\u0001\u0006j]Z|7-\u0019;j_:\u00042\u0001IA0\u0013\r\t\tG\u0007\u0002\t\u000b:4X\r\\8qK\u0006AA/Z1n/>\u00148\u000eK\u0006\u0001\u0003O\ni'a\u001c\u0002t\u0005U\u0004c\u0001 \u0002j%\u0019\u00111N\u001a\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0005\u0005E\u0014\u0001M+tK\u0002\u0012\u0015\r\\1oG&tw\rU8pY\u0002Jgn\u001d;fC\u0012\u0004sN\u001a\u0011CC2\fgnY5oO\u0012K7\u000f]1uG\",'/A\u0003tS:\u001cW-\t\u0002\u0002x\u0005\u0019!GL\u001a")
public class BalancingDispatcher
extends Dispatcher {
    private final boolean attemptTeamWork;
    private final ConcurrentSkipListSet<ActorCell> team;
    private final MessageQueue messageQueue;

    public ConcurrentSkipListSet<ActorCell> team() {
        return this.team;
    }

    public MessageQueue messageQueue() {
        return this.messageQueue;
    }

    @Override
    public Mailbox createMailbox(Cell actor, MailboxType mailboxType) {
        return new SharingMailbox(this, actor.systemImpl(), this.messageQueue());
    }

    @Override
    public void register(ActorCell actor) {
        super.register(actor);
        this.team().add(actor);
    }

    @Override
    public void unregister(ActorCell actor) {
        this.team().remove(actor);
        super.unregister(actor);
        this.teamWork();
    }

    @Override
    public void dispatch(ActorCell receiver, Envelope invocation) {
        this.messageQueue().enqueue(receiver.self(), invocation);
        if (!this.registerForExecution(receiver.mailbox(), false, false)) {
            this.teamWork();
            return;
        }
    }

    public void teamWork() {
        if (this.attemptTeamWork) {
            this.scheduleOne$1(this.scheduleOne$default$1$1());
            return;
        }
    }

    private final void scheduleOne$1(Iterator i) {
        while (this.messageQueue().hasMessages() && i.hasNext()) {
            ExecutorService executorService2;
            ExecutorService executorService3 = this.executorService().executor();
            if ((executorService3 instanceof LoadMetrics ? !((LoadMetrics)((Object)(executorService2 = executorService3))).atFullThrottle() : true) && !this.registerForExecution(((Dispatch)i.next()).mailbox(), false, false)) continue;
        }
    }

    private final Iterator scheduleOne$default$1$1() {
        return this.team().iterator();
    }

    public BalancingDispatcher(MessageDispatcherConfigurator _configurator, String _id, int throughput, Duration throughputDeadlineTime, MailboxType _mailboxType, ExecutorServiceFactoryProvider _executorServiceFactoryProvider, FiniteDuration _shutdownTimeout, boolean attemptTeamWork) {
        this.attemptTeamWork = attemptTeamWork;
        super(_configurator, _id, throughput, throughputDeadlineTime, _executorServiceFactoryProvider, _shutdownTimeout);
        this.team = new ConcurrentSkipListSet<ActorCell>(Helpers$.MODULE$.identityHashComparator(new Comparator<ActorCell>(null){

            public Comparator<ActorCell> reversed() {
                return Comparator.super.reversed();
            }

            public Comparator<ActorCell> thenComparing(Comparator<? super ActorCell> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public <U> Comparator<ActorCell> thenComparing(Function<? super ActorCell, ? extends U> x$1, Comparator<? super U> x$2) {
                return Comparator.super.thenComparing(x$1, x$2);
            }

            public <U extends Comparable<? super U>> Comparator<ActorCell> thenComparing(Function<? super ActorCell, ? extends U> x$1) {
                return Comparator.super.thenComparing(x$1);
            }

            public Comparator<ActorCell> thenComparingInt(ToIntFunction<? super ActorCell> x$1) {
                return Comparator.super.thenComparingInt(x$1);
            }

            public Comparator<ActorCell> thenComparingLong(ToLongFunction<? super ActorCell> x$1) {
                return Comparator.super.thenComparingLong(x$1);
            }

            public Comparator<ActorCell> thenComparingDouble(ToDoubleFunction<? super ActorCell> x$1) {
                return Comparator.super.thenComparingDouble(x$1);
            }

            public int compare(ActorCell l, ActorCell r) {
                return l.self().path().compareTo(r.self().path());
            }
        }));
        this.messageQueue = _mailboxType.create(None$.MODULE$, None$.MODULE$);
    }

    private class SharingMailbox
    extends Mailbox
    implements DefaultSystemMessageQueue {
        private final ActorSystemImpl system;
        public final /* synthetic */ BalancingDispatcher $outer;

        @Override
        public final void systemEnqueue(ActorRef receiver, SystemMessage message) {
            DefaultSystemMessageQueue.systemEnqueue$(this, receiver, message);
        }

        @Override
        public final SystemMessage systemDrain(SystemMessage newContents) {
            return DefaultSystemMessageQueue.systemDrain$(this, newContents);
        }

        @Override
        public boolean hasSystemMessages() {
            return DefaultSystemMessageQueue.hasSystemMessages$(this);
        }

        public ActorSystemImpl system() {
            return this.system;
        }

        @Override
        public void cleanUp() {
            Mailbox dlq = this.akka$dispatch$BalancingDispatcher$SharingMailbox$$$outer().mailboxes().deadLetterMailbox();
            SystemMessage messages = this.systemDrain(NoMessage$.MODULE$);
            while (EarliestFirstSystemMessageList$.MODULE$.nonEmpty$extension(messages)) {
                SystemMessage message = messages;
                messages = EarliestFirstSystemMessageList$.MODULE$.tail$extension(messages);
                message.unlink();
                dlq.systemEnqueue(this.system().deadLetters(), message);
            }
        }

        public /* synthetic */ BalancingDispatcher akka$dispatch$BalancingDispatcher$SharingMailbox$$$outer() {
            return this.$outer;
        }

        public SharingMailbox(BalancingDispatcher $outer, ActorSystemImpl system, MessageQueue _messageQueue) {
            this.system = system;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            super(_messageQueue);
            DefaultSystemMessageQueue.$init$(this);
        }
    }
}

