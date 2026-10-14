/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorCell;
import akka.actor.ActorRef;
import akka.actor.Cell;
import akka.dispatch.DefaultSystemMessageQueue;
import akka.dispatch.Envelope;
import akka.dispatch.ExecutorServiceDelegate;
import akka.dispatch.ExecutorServiceFactory;
import akka.dispatch.ExecutorServiceFactoryProvider;
import akka.dispatch.Mailbox;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.MessageDispatcherConfigurator;
import akka.dispatch.TaskInvocation;
import akka.dispatch.sysmsg.SystemMessage;
import akka.event.Logging;
import akka.event.Logging$;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Mf\u0001B\u0010!\u0001\u0015B\u0011B\u000b\u0001\u0003\u0002\u0003\u0006Ia\u000b\u0018\t\u0011A\u0002!Q1A\u0005\u0002EB\u0001b\u0010\u0001\u0003\u0002\u0003\u0006IA\r\u0005\t\u0001\u0002\u0011)\u0019!C\u0001\u0003\"Aa\t\u0001B\u0001B\u0003%!\t\u0003\u0005H\u0001\t\u0015\r\u0011\"\u0001I\u0011!\t\u0006A!A!\u0002\u0013I\u0005\u0002\u0003*\u0001\u0005\u0003\u0005\u000b\u0011B*\t\u0011Y\u0003!Q1A\u0005\u0002]C\u0001b\u0017\u0001\u0003\u0002\u0003\u0006I\u0001\u0017\u0005\u00069\u0002!\t!\u0018\u0004\u0005K\u0002!a\r\u0003\u0005s\u0019\t\u0005\t\u0015!\u0003t\u0011\u0015aF\u0002\"\u0001w\u0011!QH\u0002#b\u0001\n\u0003Y\bbBA\u0004\u0019\u0011\u0005\u0011\u0011\u0002\u0005\n\u0003\u0017\u0001\u0001\u0019!C\u0005\u0003\u001bA\u0011\"a\u0004\u0001\u0001\u0004%I!!\u0005\t\u000f\u0005u\u0001\u0001)Q\u0005o\"9\u0011q\u0005\u0001\u0005\u0016\u0005%\u0002bB\u0011\u0001\t#\u0011\u00131\u0006\u0005\t\u0003\u000f\u0002A\u0011\u0003\u0012\u0002J!A\u00111\f\u0001\u0005\u0012\t\ni\u0006\u0003\u0005\u0002h\u0001!\tBIA5\u0011%\t\u0019\t\u0001b\u0001\n\u0013\t)\t\u0003\u0005\u0002\u0014\u0002\u0001\u000b\u0011BAD\u0011!\t)\n\u0001C\tE\u0005]\u0005\u0002CAM\u0001\u0011E#%a'\t\u0011\u0005=\u0006A1A\u0005BEBq!!-\u0001A\u0003%!G\u0001\u0006ESN\u0004\u0018\r^2iKJT!!\t\u0012\u0002\u0011\u0011L7\u000f]1uG\"T\u0011aI\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u00011\u0003CA\u0014)\u001b\u0005\u0001\u0013BA\u0015!\u0005EiUm]:bO\u0016$\u0015n\u001d9bi\u000eDWM]\u0001\u000e?\u000e|gNZ5hkJ\fGo\u001c:\u0011\u0005\u001db\u0013BA\u0017!\u0005uiUm]:bO\u0016$\u0015n\u001d9bi\u000eDWM]\"p]\u001aLw-\u001e:bi>\u0014\u0018BA\u0018)\u00031\u0019wN\u001c4jOV\u0014\u0018\r^8s\u0003\tIG-F\u00013!\t\u0019DH\u0004\u00025uA\u0011Q\u0007O\u0007\u0002m)\u0011q\u0007J\u0001\u0007yI|w\u000e\u001e \u000b\u0003e\nQa]2bY\u0006L!a\u000f\u001d\u0002\rA\u0013X\rZ3g\u0013\tidH\u0001\u0004TiJLgn\u001a\u0006\u0003wa\n1!\u001b3!\u0003)!\bN]8vO\"\u0004X\u000f^\u000b\u0002\u0005B\u00111\tR\u0007\u0002q%\u0011Q\t\u000f\u0002\u0004\u0013:$\u0018a\u0003;ie>,x\r\u001b9vi\u0002\na\u0003\u001e5s_V<\u0007\u000e];u\t\u0016\fG\r\\5oKRKW.Z\u000b\u0002\u0013B\u0011!jT\u0007\u0002\u0017*\u0011A*T\u0001\tIV\u0014\u0018\r^5p]*\u0011a\nO\u0001\u000bG>t7-\u001e:sK:$\u0018B\u0001)L\u0005!!UO]1uS>t\u0017a\u0006;ie>,x\r\u001b9vi\u0012+\u0017\r\u001a7j]\u0016$\u0016.\\3!\u0003y)\u00070Z2vi>\u00148+\u001a:wS\u000e,g)Y2u_JL\bK]8wS\u0012,'\u000f\u0005\u0002()&\u0011Q\u000b\t\u0002\u001f\u000bb,7-\u001e;peN+'O^5dK\u001a\u000b7\r^8ssB\u0013xN^5eKJ\fqb\u001d5vi\u0012|wO\u001c+j[\u0016|W\u000f^\u000b\u00021B\u0011!*W\u0005\u00035.\u0013aBR5oSR,G)\u001e:bi&|g.\u0001\ttQV$Hm\\<o)&lWm\\;uA\u00051A(\u001b8jiz\"rAX0aC\n\u001cG\r\u0005\u0002(\u0001!)!f\u0003a\u0001W!)\u0001g\u0003a\u0001e!)\u0001i\u0003a\u0001\u0005\")qi\u0003a\u0001\u0013\")!k\u0003a\u0001'\")ak\u0003a\u00011\nYB*\u0019>z\u000bb,7-\u001e;peN+'O^5dK\u0012+G.Z4bi\u0016\u001c2\u0001D4p!\tAW.D\u0001j\u0015\tQ7.\u0001\u0003mC:<'\"\u00017\u0002\t)\fg/Y\u0005\u0003]&\u0014aa\u00142kK\u000e$\bCA\u0014q\u0013\t\t\bEA\fFq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\t\u0016dWmZ1uK\u00069a-Y2u_JL\bCA\u0014u\u0013\t)\bE\u0001\fFq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\r\u0006\u001cGo\u001c:z)\t9\u0018\u0010\u0005\u0002y\u00195\t\u0001\u0001C\u0003s\u001d\u0001\u00071/\u0001\u0005fq\u0016\u001cW\u000f^8s+\u0005a\bcA?\u0002\u00045\taP\u0003\u0002O\u007f*\u0019\u0011\u0011A6\u0002\tU$\u0018\u000e\\\u0005\u0004\u0003\u000bq(aD#yK\u000e,Ho\u001c:TKJ4\u0018nY3\u0002\t\r|\u0007/\u001f\u000b\u0002o\u00069R\r_3dkR|'oU3sm&\u001cW\rR3mK\u001e\fG/Z\u000b\u0002o\u0006YR\r_3dkR|'oU3sm&\u001cW\rR3mK\u001e\fG/Z0%KF$B!a\u0005\u0002\u001aA\u00191)!\u0006\n\u0007\u0005]\u0001H\u0001\u0003V]&$\b\u0002CA\u000e%\u0005\u0005\t\u0019A<\u0002\u0007a$\u0013'\u0001\rfq\u0016\u001cW\u000f^8s'\u0016\u0014h/[2f\t\u0016dWmZ1uK\u0002B3aEA\u0011!\r\u0019\u00151E\u0005\u0004\u0003KA$\u0001\u0003<pY\u0006$\u0018\u000e\\3\u0002\u001f\u0015DXmY;u_J\u001cVM\u001d<jG\u0016,\u0012a\u001c\u000b\u0007\u0003'\ti#!\u0010\t\u000f\u0005=R\u00031\u0001\u00022\u0005A!/Z2fSZ,'\u000f\u0005\u0003\u00024\u0005eRBAA\u001b\u0015\r\t9DI\u0001\u0006C\u000e$xN]\u0005\u0005\u0003w\t)DA\u0005BGR|'oQ3mY\"9\u0011qH\u000bA\u0002\u0005\u0005\u0013AC5om>\u001c\u0017\r^5p]B\u0019q%a\u0011\n\u0007\u0005\u0015\u0003E\u0001\u0005F]Z,Gn\u001c9f\u00039\u0019\u0018p\u001d;f[\u0012K7\u000f]1uG\"$b!a\u0005\u0002L\u00055\u0003bBA\u0018-\u0001\u0007\u0011\u0011\u0007\u0005\b\u0003\u007f1\u0002\u0019AA(!\u0011\t\t&a\u0016\u000e\u0005\u0005M#bAA+A\u000511/_:ng\u001eLA!!\u0017\u0002T\ti1+_:uK6lUm]:bO\u0016\f1\"\u001a=fGV$X\rV1tWR!\u00111CA0\u0011\u001d\tyd\u0006a\u0001\u0003C\u00022aJA2\u0013\r\t)\u0007\t\u0002\u000f)\u0006\u001c8.\u00138w_\u000e\fG/[8o\u00035\u0019'/Z1uK6\u000b\u0017\u000e\u001c2pqR1\u00111NA9\u0003s\u00022aJA7\u0013\r\ty\u0007\t\u0002\b\u001b\u0006LGNY8y\u0011\u001d\t9\u0004\u0007a\u0001\u0003g\u0002B!a\r\u0002v%!\u0011qOA\u001b\u0005\u0011\u0019U\r\u001c7\t\u000f\u0005m\u0004\u00041\u0001\u0002~\u0005YQ.Y5mE>DH+\u001f9f!\r9\u0013qP\u0005\u0004\u0003\u0003\u0003#aC'bS2\u0014w\u000e\u001f+za\u0016\f\u0011\"Z:Va\u0012\fG/\u001a:\u0016\u0005\u0005\u001d\u0005CBAE\u0003\u001fsv/\u0004\u0002\u0002\f*\u0019\u0011Q\u0012@\u0002\r\u0005$x.\\5d\u0013\u0011\t\t*a#\u00037\u0005#x.\\5d%\u00164WM]3oG\u00164\u0015.\u001a7e+B$\u0017\r^3s\u0003))7/\u00169eCR,'\u000fI\u0001\tg\",H\u000fZ8x]R\u0011\u00111C\u0001\u0015e\u0016<\u0017n\u001d;fe\u001a{'/\u0012=fGV$\u0018n\u001c8\u0015\u0011\u0005u\u00151UAT\u0003W\u00032aQAP\u0013\r\t\t\u000b\u000f\u0002\b\u0005>|G.Z1o\u0011\u001d\t)\u000b\ba\u0001\u0003W\nA!\u001c2pq\"9\u0011\u0011\u0016\u000fA\u0002\u0005u\u0015A\u00045bg6+7o]1hK\"Kg\u000e\u001e\u0005\b\u0003[c\u0002\u0019AAO\u0003QA\u0017m]*zgR,W.T3tg\u0006<W\rS5oi\u0006AAo\\*ue&tw-A\u0005u_N#(/\u001b8hA\u0001")
public class Dispatcher
extends MessageDispatcher {
    private final String id;
    private final int throughput;
    private final Duration throughputDeadlineTime;
    private final FiniteDuration shutdownTimeout;
    private volatile LazyExecutorServiceDelegate executorServiceDelegate;
    private final AtomicReferenceFieldUpdater<Dispatcher, LazyExecutorServiceDelegate> esUpdater;
    private final String toString;

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public int throughput() {
        return this.throughput;
    }

    @Override
    public Duration throughputDeadlineTime() {
        return this.throughputDeadlineTime;
    }

    @Override
    public FiniteDuration shutdownTimeout() {
        return this.shutdownTimeout;
    }

    private LazyExecutorServiceDelegate executorServiceDelegate() {
        return this.executorServiceDelegate;
    }

    private void executorServiceDelegate_$eq(LazyExecutorServiceDelegate x$1) {
        this.executorServiceDelegate = x$1;
    }

    public final ExecutorServiceDelegate executorService() {
        return this.executorServiceDelegate();
    }

    @Override
    public void dispatch(ActorCell receiver, Envelope invocation) {
        Mailbox mbox = receiver.mailbox();
        mbox.enqueue(receiver.self(), invocation);
        this.registerForExecution(mbox, true, false);
    }

    @Override
    public void systemDispatch(ActorCell receiver, SystemMessage invocation) {
        Mailbox mbox = receiver.mailbox();
        mbox.systemEnqueue(receiver.self(), invocation);
        this.registerForExecution(mbox, false, true);
    }

    @Override
    public void executeTask(TaskInvocation invocation) {
        try {
            this.executorService().execute(invocation);
        }
        catch (RejectedExecutionException e) {
            try {
                this.executorService().execute(invocation);
            }
            catch (RejectedExecutionException e2) {
                this.eventStream().publish(new Logging.Error(e, this.getClass().getName(), this.getClass(), "executeTask was rejected twice!"));
                throw e2;
            }
        }
    }

    @Override
    public Mailbox createMailbox(Cell actor, MailboxType mailboxType) {
        return new DefaultSystemMessageQueue(null, mailboxType, actor){

            public final void systemEnqueue(ActorRef receiver, SystemMessage message) {
                DefaultSystemMessageQueue.systemEnqueue$(this, receiver, message);
            }

            public final SystemMessage systemDrain(SystemMessage newContents) {
                return DefaultSystemMessageQueue.systemDrain$(this, newContents);
            }

            public boolean hasSystemMessages() {
                return DefaultSystemMessageQueue.hasSystemMessages$(this);
            }
            {
                DefaultSystemMessageQueue.$init$(this);
            }
        };
    }

    private AtomicReferenceFieldUpdater<Dispatcher, LazyExecutorServiceDelegate> esUpdater() {
        return this.esUpdater;
    }

    @Override
    public void shutdown() {
        LazyExecutorServiceDelegate newDelegate = this.executorServiceDelegate().copy();
        LazyExecutorServiceDelegate es = this.esUpdater().getAndSet(this, newDelegate);
        es.shutdown();
    }

    @Override
    public boolean registerForExecution(Mailbox mbox, boolean hasMessageHint, boolean hasSystemMessageHint) {
        if (mbox.canBeScheduledForExecution(hasMessageHint, hasSystemMessageHint)) {
            if (mbox.setAsScheduled()) {
                boolean bl;
                try {
                    this.executorService().execute(mbox);
                    bl = true;
                }
                catch (RejectedExecutionException rejectedExecutionException) {
                    try {
                        this.executorService().execute(mbox);
                        bl = true;
                    }
                    catch (RejectedExecutionException e) {
                        mbox.setAsIdle();
                        this.eventStream().publish(new Logging.Error(e, this.getClass().getName(), this.getClass(), "registerForExecution was rejected twice!"));
                        throw e;
                    }
                }
                return bl;
            }
            return false;
        }
        return false;
    }

    public String toString() {
        return this.toString;
    }

    public Dispatcher(MessageDispatcherConfigurator _configurator, String id, int throughput, Duration throughputDeadlineTime, ExecutorServiceFactoryProvider executorServiceFactoryProvider, FiniteDuration shutdownTimeout) {
        this.id = id;
        this.throughput = throughput;
        this.throughputDeadlineTime = throughputDeadlineTime;
        this.shutdownTimeout = shutdownTimeout;
        super(_configurator);
        this.executorServiceDelegate = new LazyExecutorServiceDelegate(this, executorServiceFactoryProvider.createExecutorServiceFactory(id, this.configurator().prerequisites().threadFactory()));
        this.esUpdater = AtomicReferenceFieldUpdater.newUpdater(Dispatcher.class, LazyExecutorServiceDelegate.class, "executorServiceDelegate");
        this.toString = new StringBuilder(2).append(Logging$.MODULE$.simpleName(this)).append("[").append(id).append("]").toString();
    }

    private class LazyExecutorServiceDelegate
    implements ExecutorServiceDelegate {
        private ExecutorService executor;
        private final ExecutorServiceFactory factory;
        private volatile boolean bitmap$0;
        public final /* synthetic */ Dispatcher $outer;

        @Override
        public void execute(Runnable command) {
            ExecutorServiceDelegate.execute$(this, command);
        }

        @Override
        public void shutdown() {
            ExecutorServiceDelegate.shutdown$(this);
        }

        @Override
        public List<Runnable> shutdownNow() {
            return ExecutorServiceDelegate.shutdownNow$(this);
        }

        @Override
        public boolean isShutdown() {
            return ExecutorServiceDelegate.isShutdown$(this);
        }

        @Override
        public boolean isTerminated() {
            return ExecutorServiceDelegate.isTerminated$(this);
        }

        @Override
        public boolean awaitTermination(long l, TimeUnit timeUnit) {
            return ExecutorServiceDelegate.awaitTermination$(this, l, timeUnit);
        }

        @Override
        public <T> Future<T> submit(Callable<T> callable) {
            return ExecutorServiceDelegate.submit$((ExecutorServiceDelegate)this, callable);
        }

        @Override
        public <T> Future<T> submit(Runnable runnable2, T t2) {
            return ExecutorServiceDelegate.submit$(this, runnable2, t2);
        }

        @Override
        public Future<?> submit(Runnable runnable2) {
            return ExecutorServiceDelegate.submit$((ExecutorServiceDelegate)this, runnable2);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables) {
            return ExecutorServiceDelegate.invokeAll$(this, callables);
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> callables, long l, TimeUnit timeUnit) {
            return ExecutorServiceDelegate.invokeAll$(this, callables, l, timeUnit);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> callables) {
            return (T)ExecutorServiceDelegate.invokeAny$(this, callables);
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> callables, long l, TimeUnit timeUnit) {
            return (T)ExecutorServiceDelegate.invokeAny$(this, callables, l, timeUnit);
        }

        private ExecutorService executor$lzycompute() {
            LazyExecutorServiceDelegate lazyExecutorServiceDelegate = this;
            synchronized (lazyExecutorServiceDelegate) {
                if (!this.bitmap$0) {
                    this.executor = this.factory.createExecutorService();
                    this.bitmap$0 = true;
                }
            }
            return this.executor;
        }

        @Override
        public ExecutorService executor() {
            if (!this.bitmap$0) {
                return this.executor$lzycompute();
            }
            return this.executor;
        }

        public LazyExecutorServiceDelegate copy() {
            return new LazyExecutorServiceDelegate(this.akka$dispatch$Dispatcher$LazyExecutorServiceDelegate$$$outer(), this.factory);
        }

        public /* synthetic */ Dispatcher akka$dispatch$Dispatcher$LazyExecutorServiceDelegate$$$outer() {
            return this.$outer;
        }

        public LazyExecutorServiceDelegate(Dispatcher $outer, ExecutorServiceFactory factory2) {
            this.factory = factory2;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            ExecutorServiceDelegate.$init$(this);
        }
    }
}

