/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.ExecutorServiceConfigurator;
import akka.dispatch.ExecutorServiceFactory;
import akka.dispatch.LoadMetrics;
import akka.dispatch.MonitorableThreadFactory;
import akka.dispatch.MonitorableThreadFactory$;
import akka.dispatch.ThreadPoolConfig$;
import com.typesafe.config.Config;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadFactory;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005\rv!\u0002\u0012$\u0011\u0003Ac!\u0002\u0016$\u0011\u0003Y\u0003\"\u0002\u001a\u0002\t\u0003\u0019d\u0001\u0002\u001b\u0002\u0005UB\u0001bQ\u0002\u0003\u0002\u0003\u0006I\u0001\u0012\u0005\t\u000f\u000e\u0011\t\u0011)A\u0005\u0011\"Aqj\u0001B\u0001B\u0003%\u0001\u000b\u0003\u0005[\u0007\t\u0005\t\u0015!\u0003\\\u0011\u0015\u00114\u0001\"\u0001_\u0011\u0015\u00114\u0001\"\u0001f\u0011\u0015I7\u0001\"\u0011k\u0011\u0015\u00198\u0001\"\u0001u\r\u0011)\u0018A\u0001<\t\u0011id!\u0011!Q\u0001\nADQA\r\u0007\u0005\u0002mDQA \u0007\u0005B}Dq!!\u0001\r\t\u0003\n\u0019\u0001\u0003\u0004\u0002\n1!\t\u0005\u001e\u0004\u0006U\r\u0002\u0011q\u0003\u0005\u000b\u0003?\u0011\"\u0011!Q\u0001\n\u0005\u0005\u0002BCA\u001a%\t\u0005\t\u0015!\u0003\u00026!1!G\u0005C\u0001\u0003wAq!a\u0011\u0013\t\u0003\t)E\u0002\u0004\u0002RI\u0001\u00111\u000b\u0005\n\u000f^\u0011)\u0019!C\u0001\u00037B\u0011\"!\u0018\u0018\u0005\u0003\u0005\u000b\u0011\u0002%\t\u0013\r;\"Q1A\u0005\u0002\u0005}\u0003\"CA1/\t\u0005\t\u0015!\u0003E\u0011%QvC!b\u0001\n\u0003\t\u0019\u0007C\u0005\u0002f]\u0011\t\u0011)A\u00057\"1!g\u0006C\u0001\u0003OBaAM\f\u0005\u0002\u0005M\u0004bBA=/\u0011\u0005\u00111\u0010\u0005\b\u0003\u0007\u0013BQAAC\u0003q1uN]6K_&tW\t_3dkR|'oQ8oM&<WO]1u_JT!\u0001J\u0013\u0002\u0011\u0011L7\u000f]1uG\"T\u0011AJ\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005%\nQ\"A\u0012\u00039\u0019{'o\u001b&pS:,\u00050Z2vi>\u00148i\u001c8gS\u001e,(/\u0019;peN\u0011\u0011\u0001\f\t\u0003[Aj\u0011A\f\u0006\u0002_\u0005)1oY1mC&\u0011\u0011G\f\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005A#\u0001E!lW\u00064uN]6K_&t\u0007k\\8m'\r\u0019a\u0007\u0011\t\u0003oyj\u0011\u0001\u000f\u0006\u0003si\n!bY8oGV\u0014(/\u001a8u\u0015\tYD(\u0001\u0003vi&d'\"A\u001f\u0002\t)\fg/Y\u0005\u0003\u007fa\u0012ABR8sW*{\u0017N\u001c)p_2\u0004\"!K!\n\u0005\t\u001b#a\u0003'pC\u0012lU\r\u001e:jGN\f1\u0002]1sC2dW\r\\5t[B\u0011Q&R\u0005\u0003\r:\u00121!\u00138u\u00035!\bN]3bI\u001a\u000b7\r^8ssB\u0011\u0011\n\u0014\b\u0003o)K!a\u0013\u001d\u0002\u0019\u0019{'o\u001b&pS:\u0004vn\u001c7\n\u00055s%a\u0007$pe.Tu.\u001b8X_J\\WM\u001d+ie\u0016\fGMR1di>\u0014\u0018P\u0003\u0002Lq\u0005IRO\u001c5b]\u0012dW\rZ#yG\u0016\u0004H/[8o\u0011\u0006tG\r\\3s!\t\tvK\u0004\u0002S+6\t1K\u0003\u0002Uy\u0005!A.\u00198h\u0013\t16+\u0001\u0004UQJ,\u0017\rZ\u0005\u00031f\u0013\u0001$\u00168dCV<\u0007\u000e^#yG\u0016\u0004H/[8o\u0011\u0006tG\r\\3s\u0015\t16+A\u0005bgft7-T8eKB\u0011Q\u0006X\u0005\u0003;:\u0012qAQ8pY\u0016\fg\u000eF\u0003`C\n\u001cG\r\u0005\u0002a\u00075\t\u0011\u0001C\u0003D\u0011\u0001\u0007A\tC\u0003H\u0011\u0001\u0007\u0001\nC\u0003P\u0011\u0001\u0007\u0001\u000bC\u0003[\u0011\u0001\u00071\f\u0006\u0003`M\u001eD\u0007\"B\"\n\u0001\u0004!\u0005\"B$\n\u0001\u0004A\u0005\"B(\n\u0001\u0004\u0001\u0016aB3yK\u000e,H/\u001a\u000b\u0003W:\u0004\"!\f7\n\u00055t#\u0001B+oSRDQa\u001c\u0006A\u0002A\f\u0011A\u001d\t\u0003%FL!A]*\u0003\u0011I+hN\\1cY\u0016\fa\"\u0019;Gk2dG\u000b\u001b:piRdW\rF\u0001\\\u0005A\t5n[1G_J\\'j\\5o)\u0006\u001c8n\u0005\u0002\roB\u0019q\u0007_6\n\u0005eD$\u0001\u0004$pe.Tu.\u001b8UCN\\\u0017\u0001\u0003:v]:\f'\r\\3\u0015\u0005ql\bC\u00011\r\u0011\u0015Qh\u00021\u0001q\u000319W\r\u001e*boJ+7/\u001e7u)\u0005Y\u0017\u0001D:fiJ\u000bwOU3tk2$HcA6\u0002\u0006!1\u0011q\u0001\tA\u0002-\fA!\u001e8ji\u0006!Q\r_3dQ\u001da\u0011QBA\n\u0003+\u00012!LA\b\u0013\r\t\tB\f\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\n\u0004%\u0005e\u0001cA\u0015\u0002\u001c%\u0019\u0011QD\u0012\u00037\u0015CXmY;u_J\u001cVM\u001d<jG\u0016\u001cuN\u001c4jOV\u0014\u0018\r^8s\u0003\u0019\u0019wN\u001c4jOB!\u00111EA\u0018\u001b\t\t)C\u0003\u0003\u0002 \u0005\u001d\"\u0002BA\u0015\u0003W\t\u0001\u0002^=qKN\fg-\u001a\u0006\u0003\u0003[\t1aY8n\u0013\u0011\t\t$!\n\u0003\r\r{gNZ5h\u00035\u0001(/\u001a:fcVL7/\u001b;fgB\u0019\u0011&a\u000e\n\u0007\u0005e2EA\fESN\u0004\u0018\r^2iKJ\u0004&/\u001a:fcVL7/\u001b;fgR1\u0011QHA \u0003\u0003\u0002\"!\u000b\n\t\u000f\u0005}Q\u00031\u0001\u0002\"!9\u00111G\u000bA\u0002\u0005U\u0012\u0001\u0003<bY&$\u0017\r^3\u0015\u0007!\u000b9\u0005C\u0004\u0002JY\u0001\r!a\u0013\u0002\u0003Q\u00042aNA'\u0013\r\ty\u0005\u000f\u0002\u000e)\"\u0014X-\u00193GC\u000e$xN]=\u0003=\u0019{'o\u001b&pS:,\u00050Z2vi>\u00148+\u001a:wS\u000e,g)Y2u_JL8\u0003B\f-\u0003+\u00022!KA,\u0013\r\tIf\t\u0002\u0017\u000bb,7-\u001e;peN+'O^5dK\u001a\u000b7\r^8ssV\t\u0001*\u0001\buQJ,\u0017\r\u001a$bGR|'/\u001f\u0011\u0016\u0003\u0011\u000bA\u0002]1sC2dW\r\\5t[\u0002*\u0012aW\u0001\u000bCNLhnY'pI\u0016\u0004C\u0003CA5\u0003[\ny'!\u001d\u0011\u0007\u0005-t#D\u0001\u0013\u0011\u00159e\u00041\u0001I\u0011\u0015\u0019e\u00041\u0001E\u0011\u0015Qf\u00041\u0001\\)\u0019\tI'!\u001e\u0002x!)qi\ba\u0001\u0011\")1i\ba\u0001\t\u0006)2M]3bi\u0016,\u00050Z2vi>\u00148+\u001a:wS\u000e,WCAA?!\r9\u0014qP\u0005\u0004\u0003\u0003C$aD#yK\u000e,Ho\u001c:TKJ4\u0018nY3\u00029\r\u0014X-\u0019;f\u000bb,7-\u001e;peN+'O^5dK\u001a\u000b7\r^8ssR1\u0011QKAD\u0003CCq!!#\"\u0001\u0004\tY)\u0001\u0002jIB!\u0011QRAN\u001d\u0011\ty)a&\u0011\u0007\u0005Ee&\u0004\u0002\u0002\u0014*\u0019\u0011QS\u0014\u0002\rq\u0012xn\u001c;?\u0013\r\tIJL\u0001\u0007!J,G-\u001a4\n\t\u0005u\u0015q\u0014\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005ee\u0006\u0003\u0004HC\u0001\u0007\u00111\n")
public class ForkJoinExecutorConfigurator
extends ExecutorServiceConfigurator {
    private final Config config;

    public ForkJoinPool.ForkJoinWorkerThreadFactory validate(ThreadFactory t2) {
        ThreadFactory threadFactory = t2;
        if (threadFactory instanceof ForkJoinPool.ForkJoinWorkerThreadFactory) {
            ThreadFactory threadFactory2 = threadFactory;
            return (ForkJoinPool.ForkJoinWorkerThreadFactory)((Object)threadFactory2);
        }
        throw new IllegalStateException("The prerequisites for the ForkJoinExecutorConfigurator is a ForkJoinPool.ForkJoinWorkerThreadFactory!");
    }

    @Override
    public final ExecutorServiceFactory createExecutorServiceFactory(String id, ThreadFactory threadFactory) {
        boolean bl;
        ThreadFactory tf;
        block6: {
            ThreadFactory threadFactory2;
            ThreadFactory threadFactory3 = threadFactory;
            if (threadFactory3 instanceof MonitorableThreadFactory) {
                MonitorableThreadFactory monitorableThreadFactory = (MonitorableThreadFactory)threadFactory3;
                threadFactory2 = monitorableThreadFactory.withName(new StringBuilder(1).append(monitorableThreadFactory.name()).append("-").append(id).toString());
            } else {
                threadFactory2 = threadFactory3;
            }
            tf = threadFactory2;
            String string = this.config.getString("task-peeking-mode");
            switch (string == null ? 0 : string.hashCode()) {
                case 2157772: {
                    if (!"FIFO".equals(string)) break;
                    bl = true;
                    break block6;
                }
                case 2336518: {
                    if (!"LIFO".equals(string)) break;
                    bl = false;
                    break block6;
                }
            }
            throw new IllegalArgumentException("Cannot instantiate ForkJoinExecutorServiceFactory. \"task-peeking-mode\" in \"fork-join-executor\" section could only set to \"FIFO\" or \"LIFO\".");
        }
        boolean asyncMode = bl;
        return new ForkJoinExecutorServiceFactory(this, this.validate(tf), ThreadPoolConfig$.MODULE$.scaledPoolSize(this.config.getInt("parallelism-min"), this.config.getDouble("parallelism-factor"), this.config.getInt("parallelism-max")), asyncMode);
    }

    public ForkJoinExecutorConfigurator(Config config, DispatcherPrerequisites prerequisites) {
        this.config = config;
        super(config, prerequisites);
    }

    public static final class AkkaForkJoinPool
    extends ForkJoinPool
    implements LoadMetrics {
        @Override
        public void execute(Runnable r) {
            if (r != null) {
                super.execute((ForkJoinTask)(r instanceof ForkJoinTask ? r : new AkkaForkJoinTask(r)));
                return;
            }
            throw new NullPointerException("Runnable was null");
        }

        @Override
        public boolean atFullThrottle() {
            return this.getActiveThreadCount() >= this.getParallelism();
        }

        public AkkaForkJoinPool(int parallelism, ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory, Thread.UncaughtExceptionHandler unhandledExceptionHandler, boolean asyncMode) {
            super(parallelism, threadFactory, unhandledExceptionHandler, asyncMode);
        }

        public AkkaForkJoinPool(int parallelism, ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory, Thread.UncaughtExceptionHandler unhandledExceptionHandler) {
            this(parallelism, threadFactory, unhandledExceptionHandler, true);
        }
    }

    public static final class AkkaForkJoinTask
    extends ForkJoinTask<BoxedUnit> {
        private static final long serialVersionUID = 1L;
        private final Runnable runnable;

        @Override
        public void getRawResult() {
        }

        @Override
        public void setRawResult(BoxedUnit unit) {
        }

        @Override
        public boolean exec() {
            boolean bl;
            try {
                this.runnable.run();
                bl = true;
            }
            catch (InterruptedException interruptedException) {
                Thread.currentThread().interrupt();
                bl = false;
            }
            catch (Throwable anything) {
                Thread t2 = Thread.currentThread();
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler = t2.getUncaughtExceptionHandler();
                if (uncaughtExceptionHandler == null) {
                } else {
                    uncaughtExceptionHandler.uncaughtException(t2, anything);
                }
                throw anything;
            }
            return bl;
        }

        public AkkaForkJoinTask(Runnable runnable2) {
            this.runnable = runnable2;
        }
    }

    public class ForkJoinExecutorServiceFactory
    implements ExecutorServiceFactory {
        private final ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory;
        private final int parallelism;
        private final boolean asyncMode;
        public final /* synthetic */ ForkJoinExecutorConfigurator $outer;

        public ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory() {
            return this.threadFactory;
        }

        public int parallelism() {
            return this.parallelism;
        }

        public boolean asyncMode() {
            return this.asyncMode;
        }

        @Override
        public ExecutorService createExecutorService() {
            return new AkkaForkJoinPool(this.parallelism(), this.threadFactory(), MonitorableThreadFactory$.MODULE$.doNothing(), this.asyncMode());
        }

        public /* synthetic */ ForkJoinExecutorConfigurator akka$dispatch$ForkJoinExecutorConfigurator$ForkJoinExecutorServiceFactory$$$outer() {
            return this.$outer;
        }

        public ForkJoinExecutorServiceFactory(ForkJoinExecutorConfigurator $outer, ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory, int parallelism, boolean asyncMode) {
            this.threadFactory = threadFactory;
            this.parallelism = parallelism;
            this.asyncMode = asyncMode;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }

        public ForkJoinExecutorServiceFactory(ForkJoinExecutorConfigurator $outer, ForkJoinPool.ForkJoinWorkerThreadFactory threadFactory, int parallelism) {
            this($outer, threadFactory, parallelism, true);
        }
    }
}

