/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.workqueue;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.workqueue.AutomaticWorkQueue;

@NoJSR250Annotations
public class AutomaticWorkQueueImpl
implements AutomaticWorkQueue {
    public static final String PROPERTY_NAME = "name";
    static final int DEFAULT_MAX_QUEUE_SIZE = 256;
    private static final Logger LOG = LogUtils.getL7dLogger(AutomaticWorkQueueImpl.class);
    String name = "default";
    int maxQueueSize;
    int initialThreads;
    int lowWaterMark;
    int highWaterMark;
    long dequeueTimeout;
    volatile int approxThreadCount;
    ThreadPoolExecutor executor;
    Method addWorkerMethod;
    Object[] addWorkerArgs;
    AWQThreadFactory threadFactory;
    ReentrantLock mainLock;
    final ReentrantLock addThreadLock = new ReentrantLock();
    DelayQueue<DelayedTaskWrapper> delayQueue;
    WatchDog watchDog;
    boolean shared;
    int sharedCount;
    private List<PropertyChangeListener> changeListenerList;

    public AutomaticWorkQueueImpl() {
        this(256);
    }

    public AutomaticWorkQueueImpl(String name) {
        this(256, name);
    }

    public AutomaticWorkQueueImpl(int max) {
        this(max, "default");
    }

    public AutomaticWorkQueueImpl(int max, String name) {
        this(max, 0, 25, 5, 120000L, name);
    }

    public AutomaticWorkQueueImpl(int mqs, int initialThreads, int highWaterMark, int lowWaterMark, long dequeueTimeout) {
        this(mqs, initialThreads, highWaterMark, lowWaterMark, dequeueTimeout, "default");
    }

    public AutomaticWorkQueueImpl(int mqs, int initialThreads, int highWaterMark, int lowWaterMark, long dequeueTimeout, String name) {
        this.maxQueueSize = mqs == -1 ? 256 : mqs;
        this.initialThreads = initialThreads;
        this.highWaterMark = -1 == highWaterMark ? Integer.MAX_VALUE : highWaterMark;
        this.lowWaterMark = -1 == lowWaterMark ? Integer.MAX_VALUE : lowWaterMark;
        this.dequeueTimeout = dequeueTimeout;
        this.name = name;
        this.changeListenerList = new ArrayList<PropertyChangeListener>();
    }

    public void addChangeListener(PropertyChangeListener listener) {
        this.changeListenerList.add(listener);
    }

    public void removeChangeListener(PropertyChangeListener listener) {
        this.changeListenerList.remove(listener);
    }

    public void notifyChangeListeners(PropertyChangeEvent event) {
        for (PropertyChangeListener listener : this.changeListenerList) {
            listener.propertyChange(event);
        }
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isShared() {
        return this.shared;
    }

    public void addSharedUser() {
        ++this.sharedCount;
    }

    public void removeSharedUser() {
        --this.sharedCount;
    }

    public int getShareCount() {
        return this.sharedCount;
    }

    protected synchronized ThreadPoolExecutor getExecutor() {
        if (this.executor == null) {
            this.threadFactory = this.createThreadFactory(this.name);
            this.executor = new ThreadPoolExecutor(this.lowWaterMark, this.highWaterMark, TimeUnit.MILLISECONDS.toMillis(this.dequeueTimeout), TimeUnit.MILLISECONDS, new LinkedBlockingQueue(this.maxQueueSize), this.threadFactory){

                @Override
                protected void terminated() {
                    ThreadFactory f = AutomaticWorkQueueImpl.this.executor.getThreadFactory();
                    if (f instanceof AWQThreadFactory) {
                        ((AWQThreadFactory)f).shutdown();
                    }
                    if (AutomaticWorkQueueImpl.this.watchDog != null) {
                        AutomaticWorkQueueImpl.this.watchDog.shutdown();
                    }
                }
            };
            if (LOG.isLoggable(Level.FINE)) {
                StringBuilder buf = new StringBuilder(128).append("Constructing automatic work queue with:\n").append("max queue size: ").append(this.maxQueueSize).append("\n").append("initialThreads: ").append(this.initialThreads).append("\n").append("lowWaterMark: ").append(this.lowWaterMark).append("\n").append("highWaterMark: ").append(this.highWaterMark).append("\n");
                LOG.fine(buf.toString());
            }
            if (this.initialThreads > this.highWaterMark) {
                this.initialThreads = this.highWaterMark;
            }
            if (this.initialThreads < Integer.MAX_VALUE && this.initialThreads > 0) {
                this.executor.setCorePoolSize(this.initialThreads);
                int started = this.executor.prestartAllCoreThreads();
                if (started < this.initialThreads) {
                    LOG.log(Level.WARNING, "THREAD_START_FAILURE_MSG", new Object[]{started, this.initialThreads});
                }
                this.executor.setCorePoolSize(this.lowWaterMark);
            }
            ReentrantLock l = null;
            try {
                Field f = ThreadPoolExecutor.class.getDeclaredField("mainLock");
                ReflectionUtil.setAccessible(f);
                l = (ReentrantLock)f.get(this.executor);
            }
            catch (Throwable t2) {
                l = new ReentrantLock();
            }
            this.mainLock = l;
            try {
                this.addWorkerMethod = ThreadPoolExecutor.class.getDeclaredMethod("addWorker", Runnable.class, Boolean.TYPE);
                this.addWorkerArgs = new Object[]{null, Boolean.FALSE};
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return this.executor;
    }

    private AWQThreadFactory createThreadFactory(final String nm) {
        ThreadGroup group;
        try {
            group = AccessController.doPrivileged(new PrivilegedAction<ThreadGroup>(){

                @Override
                public ThreadGroup run() {
                    ThreadGroup group;
                    try {
                        for (ThreadGroup parent = group = Thread.currentThread().getThreadGroup(); parent != null; parent = parent.getParent()) {
                            group = parent;
                        }
                    }
                    catch (SecurityException securityException) {
                        // empty catch block
                    }
                    return new ThreadGroup(group, nm + "-workqueue");
                }
            });
        }
        catch (SecurityException e) {
            group = new ThreadGroup(nm + "-workqueue");
        }
        return new AWQThreadFactory(group, nm);
    }

    public void setName(String s2) {
        this.name = s2;
        if (this.threadFactory != null) {
            this.threadFactory.setName(s2);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String toString() {
        return super.toString() + " [queue size: " + this.getSize() + ", max size: " + this.maxQueueSize + ", threads: " + this.getPoolSize() + ", active threads: " + this.getActiveCount() + ", low water mark: " + this.getLowWaterMark() + ", high water mark: " + this.getHighWaterMark() + ']';
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void execute(final Runnable command) {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Runnable r = new Runnable(){

            @Override
            public void run() {
                ClassLoaderUtils.ClassLoaderHolder orig = ClassLoaderUtils.setThreadContextClassloader(loader);
                try {
                    command.run();
                }
                finally {
                    if (orig != null) {
                        orig.reset();
                    }
                }
            }
        };
        ThreadPoolExecutor ex = this.getExecutor();
        ex.execute(r);
        if (this.addWorkerMethod == null) return;
        if (ex.getQueue().isEmpty()) return;
        if (this.approxThreadCount >= this.highWaterMark) return;
        if (!this.addThreadLock.tryLock()) return;
        try {
            this.mainLock.lock();
            try {
                int ps = this.getPoolSize();
                int sz = this.executor.getQueue().size();
                int sz2 = this.getActiveCount();
                if (sz + sz2 > ps) {
                    ReflectionUtil.setAccessible(this.addWorkerMethod).invoke((Object)this.executor, this.addWorkerArgs);
                }
                this.mainLock.unlock();
                return;
            }
            catch (Exception exception) {
                return;
            }
            finally {
                this.mainLock.unlock();
            }
        }
        finally {
            this.addThreadLock.unlock();
        }
    }

    @Override
    public void execute(Runnable work, long timeout2) {
        try {
            this.execute(work);
        }
        catch (RejectedExecutionException ree) {
            try {
                if (!this.getExecutor().getQueue().offer(work, timeout2, TimeUnit.MILLISECONDS)) {
                    throw ree;
                }
            }
            catch (InterruptedException ie) {
                throw ree;
            }
        }
    }

    @Override
    public synchronized void schedule(Runnable work, long delay) {
        if (this.delayQueue == null) {
            this.delayQueue = new DelayQueue();
            this.watchDog = new WatchDog(this.delayQueue);
            this.watchDog.setDaemon(true);
            this.watchDog.start();
        }
        this.delayQueue.put(new DelayedTaskWrapper(work, delay));
    }

    @Override
    public void shutdown(boolean processRemainingWorkItems) {
        if (this.executor != null) {
            if (!processRemainingWorkItems) {
                this.executor.getQueue().clear();
            }
            this.executor.shutdown();
        }
    }

    public long getMaxSize() {
        return this.maxQueueSize;
    }

    public long getSize() {
        return this.executor == null ? 0L : (long)this.executor.getQueue().size();
    }

    public boolean isEmpty() {
        return this.executor == null || this.executor.getQueue().isEmpty();
    }

    public boolean isFull() {
        return this.executor != null && this.executor.getQueue().remainingCapacity() == 0;
    }

    public int getHighWaterMark() {
        int hwm = this.executor == null ? this.highWaterMark : this.executor.getMaximumPoolSize();
        return hwm == Integer.MAX_VALUE ? -1 : hwm;
    }

    public int getLowWaterMark() {
        int lwm = this.executor == null ? this.lowWaterMark : this.executor.getCorePoolSize();
        return lwm == Integer.MAX_VALUE ? -1 : lwm;
    }

    public int getInitialSize() {
        return this.initialThreads;
    }

    public void setHighWaterMark(int hwm) {
        int n = this.highWaterMark = hwm < 0 ? Integer.MAX_VALUE : hwm;
        if (this.executor != null) {
            this.notifyChangeListeners(new PropertyChangeEvent(this, "highWaterMark", this.executor.getMaximumPoolSize(), hwm));
            this.executor.setMaximumPoolSize(this.highWaterMark);
        }
    }

    public void setLowWaterMark(int lwm) {
        int n = this.lowWaterMark = lwm < 0 ? 0 : lwm;
        if (this.executor != null) {
            this.notifyChangeListeners(new PropertyChangeEvent(this, "lowWaterMark", this.executor.getCorePoolSize(), lwm));
            this.executor.setCorePoolSize(this.lowWaterMark);
        }
    }

    public void setInitialSize(int initialSize) {
        this.notifyChangeListeners(new PropertyChangeEvent(this, "initialSize", this.initialThreads, initialSize));
        this.initialThreads = initialSize;
    }

    public void setQueueSize(int size) {
        this.notifyChangeListeners(new PropertyChangeEvent(this, "queueSize", this.maxQueueSize, size));
        this.maxQueueSize = size;
    }

    public void setDequeueTimeout(long l) {
        this.notifyChangeListeners(new PropertyChangeEvent(this, "dequeueTimeout", this.dequeueTimeout, l));
        this.dequeueTimeout = l;
    }

    @Override
    public boolean isShutdown() {
        if (this.executor == null) {
            return false;
        }
        return this.executor.isShutdown();
    }

    public int getLargestPoolSize() {
        if (this.executor == null) {
            return 0;
        }
        return this.executor.getLargestPoolSize();
    }

    public int getPoolSize() {
        if (this.executor == null) {
            return 0;
        }
        return this.executor.getPoolSize();
    }

    public int getActiveCount() {
        if (this.executor == null) {
            return 0;
        }
        return this.executor.getActiveCount();
    }

    public void update(Dictionary<String, String> config) {
        String s2 = config.get("highWaterMark");
        if (s2 != null) {
            this.highWaterMark = Integer.parseInt(s2);
        }
        if ((s2 = config.get("lowWaterMark")) != null) {
            this.lowWaterMark = Integer.parseInt(s2);
        }
        if ((s2 = config.get("initialSize")) != null) {
            this.initialThreads = Integer.parseInt(s2);
        }
        if ((s2 = config.get("dequeueTimeout")) != null) {
            this.dequeueTimeout = Long.parseLong(s2);
        }
        if ((s2 = config.get("queueSize")) != null) {
            this.maxQueueSize = Integer.parseInt(s2);
        }
    }

    public Dictionary<String, String> getProperties() {
        Hashtable<String, String> properties = new Hashtable<String, String>();
        NumberFormat nf = NumberFormat.getIntegerInstance();
        ((Dictionary)properties).put(PROPERTY_NAME, nf.format(this.getName()));
        ((Dictionary)properties).put("highWaterMark", nf.format(this.getHighWaterMark()));
        ((Dictionary)properties).put("lowWaterMark", nf.format(this.getLowWaterMark()));
        ((Dictionary)properties).put("initialSize", nf.format(this.getLowWaterMark()));
        ((Dictionary)properties).put("dequeueTimeout", nf.format(this.getLowWaterMark()));
        ((Dictionary)properties).put("queueSize", nf.format(this.getLowWaterMark()));
        return properties;
    }

    class AWQThreadFactory
    implements ThreadFactory {
        final AtomicInteger threadNumber = new AtomicInteger(1);
        ThreadGroup group;
        String name;
        ClassLoader loader;

        AWQThreadFactory(ThreadGroup gp, String nm) {
            this.group = gp;
            this.name = nm;
            this.loader = AutomaticWorkQueueImpl.class.getClassLoader();
        }

        @Override
        public Thread newThread(final Runnable r) {
            if (this.group.isDestroyed()) {
                this.group = new ThreadGroup(this.group.getParent(), this.name + "-workqueue");
            }
            Runnable wrapped = new Runnable(){

                @Override
                public void run() {
                    ++AutomaticWorkQueueImpl.this.approxThreadCount;
                    try {
                        r.run();
                    }
                    finally {
                        --AutomaticWorkQueueImpl.this.approxThreadCount;
                    }
                }
            };
            final Thread t2 = new Thread(this.group, wrapped, this.name + "-workqueue-" + this.threadNumber.getAndIncrement(), 0L);
            AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

                @Override
                public Boolean run() {
                    t2.setContextClassLoader(AWQThreadFactory.this.loader);
                    return true;
                }
            });
            t2.setDaemon(true);
            if (t2.getPriority() != 5) {
                t2.setPriority(5);
            }
            return t2;
        }

        public void setName(String s2) {
            this.name = s2;
        }

        public void shutdown() {
            if (!this.group.isDestroyed()) {
                try {
                    this.group.destroy();
                    this.group.setDaemon(true);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
    }

    class WatchDog
    extends Thread {
        DelayQueue<DelayedTaskWrapper> delayQueue;
        AtomicBoolean shutdown = new AtomicBoolean(false);

        WatchDog(DelayQueue<DelayedTaskWrapper> queue) {
            this.delayQueue = queue;
        }

        public void shutdown() {
            this.shutdown.set(true);
            this.interrupt();
        }

        @Override
        public void run() {
            block5: {
                try {
                    while (!this.shutdown.get()) {
                        DelayedTaskWrapper task = (DelayedTaskWrapper)this.delayQueue.take();
                        if (task == null) continue;
                        try {
                            AutomaticWorkQueueImpl.this.execute(task);
                        }
                        catch (Exception ex) {
                            LOG.warning("Executing the task from DelayQueue with exception: " + ex);
                        }
                    }
                }
                catch (InterruptedException e) {
                    if (!LOG.isLoggable(Level.FINE)) break block5;
                    LOG.finer("The DelayQueue watchdog Task is stopping");
                }
            }
        }
    }

    static class DelayedTaskWrapper
    implements Delayed,
    Runnable {
        long trigger;
        Runnable work;

        DelayedTaskWrapper(Runnable work, long delay) {
            this.work = work;
            this.trigger = System.currentTimeMillis() + delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long n = this.trigger - System.currentTimeMillis();
            return unit.convert(n, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed delayed) {
            long other = ((DelayedTaskWrapper)delayed).trigger;
            int returnValue = this.trigger < other ? -1 : (this.trigger > other ? 1 : 0);
            return returnValue;
        }

        @Override
        public void run() {
            this.work.run();
        }
    }
}

