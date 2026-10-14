/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.activemq.thread.DedicatedTaskRunner;
import org.apache.activemq.thread.PooledTaskRunner;
import org.apache.activemq.thread.Task;
import org.apache.activemq.thread.TaskRunner;
import org.apache.activemq.util.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskRunnerFactory
implements Executor {
    private static final Logger LOG = LoggerFactory.getLogger(TaskRunnerFactory.class);
    private final AtomicReference<ExecutorService> executorRef = new AtomicReference();
    private int maxIterationsPerRun;
    private String name;
    private int priority;
    private boolean daemon;
    private final AtomicLong id = new AtomicLong(0L);
    private boolean dedicatedTaskRunner;
    private long shutdownAwaitTermination = 30000L;
    private final AtomicBoolean initDone = new AtomicBoolean(false);
    private int maxThreadPoolSize = TaskRunnerFactory.getDefaultMaximumPoolSize();
    private RejectedExecutionHandler rejectedTaskHandler = null;
    private ClassLoader threadClassLoader;

    public TaskRunnerFactory() {
        this("ActiveMQ Task");
    }

    public TaskRunnerFactory(String name) {
        this(name, 5, true, 1000);
    }

    private TaskRunnerFactory(String name, int priority, boolean daemon, int maxIterationsPerRun) {
        this(name, priority, daemon, maxIterationsPerRun, false);
    }

    public TaskRunnerFactory(String name, int priority, boolean daemon, int maxIterationsPerRun, boolean dedicatedTaskRunner) {
        this(name, priority, daemon, maxIterationsPerRun, dedicatedTaskRunner, TaskRunnerFactory.getDefaultMaximumPoolSize());
    }

    public TaskRunnerFactory(String name, int priority, boolean daemon, int maxIterationsPerRun, boolean dedicatedTaskRunner, int maxThreadPoolSize) {
        this.name = name;
        this.priority = priority;
        this.daemon = daemon;
        this.maxIterationsPerRun = maxIterationsPerRun;
        this.dedicatedTaskRunner = dedicatedTaskRunner;
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void init() {
        if (!this.initDone.get()) {
            TaskRunnerFactory taskRunnerFactory = this;
            synchronized (taskRunnerFactory) {
                if (!this.initDone.get()) {
                    if (this.dedicatedTaskRunner || "true".equalsIgnoreCase(System.getProperty("org.apache.activemq.UseDedicatedTaskRunner"))) {
                        this.executorRef.set(null);
                    } else {
                        this.executorRef.compareAndSet(null, this.createDefaultExecutor());
                    }
                    LOG.debug("Initialized TaskRunnerFactory[{}] using ExecutorService: {}", (Object)this.name, (Object)this.executorRef.get());
                    this.initDone.set(true);
                }
            }
        }
    }

    public void shutdown() {
        ExecutorService executor2 = this.executorRef.get();
        if (executor2 != null) {
            ThreadPoolUtils.shutdown(executor2);
        }
        this.clearExecutor();
    }

    public void shutdownNow() {
        ExecutorService executor2 = this.executorRef.get();
        if (executor2 != null) {
            ThreadPoolUtils.shutdownNow(executor2);
        }
        this.clearExecutor();
    }

    public void shutdownGraceful() {
        ExecutorService executor2 = this.executorRef.get();
        if (executor2 != null) {
            ThreadPoolUtils.shutdownGraceful(executor2, this.shutdownAwaitTermination);
        }
        this.clearExecutor();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void clearExecutor() {
        TaskRunnerFactory taskRunnerFactory = this;
        synchronized (taskRunnerFactory) {
            this.executorRef.set(null);
            this.initDone.set(false);
        }
    }

    public TaskRunner createTaskRunner(Task task, String name) {
        this.init();
        ExecutorService executor2 = this.executorRef.get();
        if (executor2 != null) {
            return new PooledTaskRunner(executor2, task, this.maxIterationsPerRun);
        }
        return new DedicatedTaskRunner(task, name, this.priority, this.daemon);
    }

    @Override
    public void execute(Runnable runnable2) {
        this.execute(runnable2, this.name);
    }

    public void execute(Runnable runnable2, String name) {
        this.init();
        LOG.trace("Execute[{}] runnable: {}", (Object)name, (Object)runnable2);
        ExecutorService executor2 = this.executorRef.get();
        if (executor2 != null) {
            executor2.execute(runnable2);
        } else {
            this.doExecuteNewThread(runnable2, name);
        }
    }

    private void doExecuteNewThread(Runnable runnable2, String name) {
        String threadName = name + "-" + this.id.incrementAndGet();
        Thread thread2 = new Thread(runnable2, threadName);
        thread2.setDaemon(this.daemon);
        LOG.trace("Created and running thread[{}]: {}", (Object)threadName, (Object)thread2);
        thread2.start();
    }

    protected ExecutorService createDefaultExecutor() {
        ThreadPoolExecutor rc = new ThreadPoolExecutor(TaskRunnerFactory.getDefaultCorePoolSize(), this.getMaxThreadPoolSize(), (long)TaskRunnerFactory.getDefaultKeepAliveTime(), TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory(){

            @Override
            public Thread newThread(Runnable runnable2) {
                String threadName = TaskRunnerFactory.this.name + "-" + TaskRunnerFactory.this.id.incrementAndGet();
                Thread thread2 = new Thread(runnable2, threadName);
                thread2.setDaemon(TaskRunnerFactory.this.daemon);
                thread2.setPriority(TaskRunnerFactory.this.priority);
                if (TaskRunnerFactory.this.threadClassLoader != null) {
                    thread2.setContextClassLoader(TaskRunnerFactory.this.threadClassLoader);
                }
                thread2.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

                    @Override
                    public void uncaughtException(Thread t2, Throwable e) {
                        LOG.error("Error in thread '{}'", (Object)t2.getName(), (Object)e);
                    }
                });
                LOG.trace("Created thread[{}]: {}", (Object)threadName, (Object)thread2);
                return thread2;
            }
        });
        if (this.rejectedTaskHandler != null) {
            rc.setRejectedExecutionHandler(this.rejectedTaskHandler);
        } else {
            rc.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        }
        return rc;
    }

    public ExecutorService getExecutor() {
        return this.executorRef.get();
    }

    public void setExecutor(ExecutorService executor2) {
        this.executorRef.set(executor2);
    }

    public int getMaxIterationsPerRun() {
        return this.maxIterationsPerRun;
    }

    public void setMaxIterationsPerRun(int maxIterationsPerRun) {
        this.maxIterationsPerRun = maxIterationsPerRun;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDaemon() {
        return this.daemon;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public boolean isDedicatedTaskRunner() {
        return this.dedicatedTaskRunner;
    }

    public void setDedicatedTaskRunner(boolean dedicatedTaskRunner) {
        this.dedicatedTaskRunner = dedicatedTaskRunner;
    }

    public int getMaxThreadPoolSize() {
        return this.maxThreadPoolSize;
    }

    public void setMaxThreadPoolSize(int maxThreadPoolSize) {
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

    public void setThreadClassLoader(ClassLoader threadClassLoader) {
        this.threadClassLoader = threadClassLoader;
    }

    public RejectedExecutionHandler getRejectedTaskHandler() {
        return this.rejectedTaskHandler;
    }

    public void setRejectedTaskHandler(RejectedExecutionHandler rejectedTaskHandler) {
        this.rejectedTaskHandler = rejectedTaskHandler;
    }

    public long getShutdownAwaitTermination() {
        return this.shutdownAwaitTermination;
    }

    public void setShutdownAwaitTermination(long shutdownAwaitTermination) {
        this.shutdownAwaitTermination = shutdownAwaitTermination;
    }

    private static int getDefaultCorePoolSize() {
        return Integer.getInteger("org.apache.activemq.thread.TaskRunnerFactory.corePoolSize", 0);
    }

    private static int getDefaultMaximumPoolSize() {
        return Integer.getInteger("org.apache.activemq.thread.TaskRunnerFactory.maximumPoolSize", Integer.MAX_VALUE);
    }

    private static int getDefaultKeepAliveTime() {
        return Integer.getInteger("org.apache.activemq.thread.TaskRunnerFactory.keepAliveTime", 30);
    }
}

