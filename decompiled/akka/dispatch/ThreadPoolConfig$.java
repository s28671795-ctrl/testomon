/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.SaneRejectedExecutionHandler;
import akka.dispatch.ThreadPoolConfig;
import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import scala.Function0;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple6;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.math.package$;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.RichDouble$;

public final class ThreadPoolConfig$
implements Serializable {
    public static final ThreadPoolConfig$ MODULE$ = new ThreadPoolConfig$();
    private static final boolean defaultAllowCoreThreadTimeout = false;
    private static final int defaultCorePoolSize = 16;
    private static final int defaultMaxPoolSize = 128;
    private static final Duration defaultTimeout = Duration$.MODULE$.apply(60000L, TimeUnit.MILLISECONDS);
    private static final RejectedExecutionHandler defaultRejectionPolicy = new SaneRejectedExecutionHandler();

    public boolean $lessinit$greater$default$1() {
        return this.defaultAllowCoreThreadTimeout();
    }

    public int $lessinit$greater$default$2() {
        return this.defaultCorePoolSize();
    }

    public int $lessinit$greater$default$3() {
        return this.defaultMaxPoolSize();
    }

    public Duration $lessinit$greater$default$4() {
        return this.defaultTimeout();
    }

    public Function0<BlockingQueue<Runnable>> $lessinit$greater$default$5() {
        return this.linkedBlockingQueue();
    }

    public RejectedExecutionHandler $lessinit$greater$default$6() {
        return this.defaultRejectionPolicy();
    }

    public boolean defaultAllowCoreThreadTimeout() {
        return defaultAllowCoreThreadTimeout;
    }

    public int defaultCorePoolSize() {
        return defaultCorePoolSize;
    }

    public int defaultMaxPoolSize() {
        return defaultMaxPoolSize;
    }

    public Duration defaultTimeout() {
        return defaultTimeout;
    }

    public RejectedExecutionHandler defaultRejectionPolicy() {
        return defaultRejectionPolicy;
    }

    public int scaledPoolSize(int floor, double multiplier, int ceiling) {
        return package$.MODULE$.min(package$.MODULE$.max((int)RichDouble$.MODULE$.ceil$extension(Predef$.MODULE$.doubleWrapper((double)Runtime.getRuntime().availableProcessors() * multiplier)), floor), ceiling);
    }

    public Function0<BlockingQueue<Runnable>> arrayBlockingQueue(int capacity, boolean fair) {
        return (Function0<ArrayBlockingQueue> & Serializable)() -> new ArrayBlockingQueue(capacity, fair);
    }

    public Function0<BlockingQueue<Runnable>> synchronousQueue(boolean fair) {
        return (Function0<SynchronousQueue> & Serializable)() -> new SynchronousQueue(fair);
    }

    public Function0<BlockingQueue<Runnable>> linkedBlockingQueue() {
        return (Function0<LinkedBlockingQueue> & Serializable)() -> new LinkedBlockingQueue();
    }

    public Function0<BlockingQueue<Runnable>> linkedBlockingQueue(int capacity) {
        return (Function0<LinkedBlockingQueue> & Serializable)() -> new LinkedBlockingQueue(capacity);
    }

    public Function0<BlockingQueue<Runnable>> reusableQueue(BlockingQueue<Runnable> queue) {
        return (Function0<BlockingQueue> & Serializable)() -> queue;
    }

    public Function0<BlockingQueue<Runnable>> reusableQueue(Function0<BlockingQueue<Runnable>> queueFactory) {
        return this.reusableQueue(queueFactory.apply());
    }

    public ThreadPoolConfig apply(boolean allowCorePoolTimeout, int corePoolSize, int maxPoolSize, Duration threadTimeout, Function0<BlockingQueue<Runnable>> queueFactory, RejectedExecutionHandler rejectionPolicy) {
        return new ThreadPoolConfig(allowCorePoolTimeout, corePoolSize, maxPoolSize, threadTimeout, queueFactory, rejectionPolicy);
    }

    public boolean apply$default$1() {
        return this.defaultAllowCoreThreadTimeout();
    }

    public int apply$default$2() {
        return this.defaultCorePoolSize();
    }

    public int apply$default$3() {
        return this.defaultMaxPoolSize();
    }

    public Duration apply$default$4() {
        return this.defaultTimeout();
    }

    public Function0<BlockingQueue<Runnable>> apply$default$5() {
        return this.linkedBlockingQueue();
    }

    public RejectedExecutionHandler apply$default$6() {
        return this.defaultRejectionPolicy();
    }

    public Option<Tuple6<Object, Object, Object, Duration, Function0<BlockingQueue<Runnable>>, RejectedExecutionHandler>> unapply(ThreadPoolConfig x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple6<Object, Object, Object, Duration, Function0<BlockingQueue<Runnable>>, RejectedExecutionHandler>>(new Tuple6<Boolean, Integer, Integer, Duration, Function0<BlockingQueue<Runnable>>, RejectedExecutionHandler>(BoxesRunTime.boxToBoolean(x$0.allowCorePoolTimeout()), BoxesRunTime.boxToInteger(x$0.corePoolSize()), BoxesRunTime.boxToInteger(x$0.maxPoolSize()), x$0.threadTimeout(), x$0.queueFactory(), x$0.rejectionPolicy()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ThreadPoolConfig$.class);
    }

    private ThreadPoolConfig$() {
    }
}

