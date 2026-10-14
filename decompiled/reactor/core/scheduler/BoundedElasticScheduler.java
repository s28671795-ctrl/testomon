/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.ExecutorServiceWorker;
import reactor.core.scheduler.ReactorThreadFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerState;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;

final class BoundedElasticScheduler
implements Scheduler,
SchedulerState.DisposeAwaiter<BoundedServices>,
Scannable {
    static final Logger LOGGER = Loggers.getLogger(BoundedElasticScheduler.class);
    static final int DEFAULT_TTL_SECONDS = 60;
    static final AtomicLong COUNTER = new AtomicLong();
    final int maxThreads;
    final int maxTaskQueuedPerThread;
    final Clock clock;
    final ThreadFactory factory;
    final long ttlMillis;
    volatile SchedulerState<BoundedServices> state;
    static final AtomicReferenceFieldUpdater<BoundedElasticScheduler, SchedulerState> STATE = AtomicReferenceFieldUpdater.newUpdater(BoundedElasticScheduler.class, SchedulerState.class, "state");
    private static final SchedulerState<BoundedServices> INIT = SchedulerState.init(BoundedServices.SHUTDOWN);

    BoundedElasticScheduler(int maxThreads, int maxTaskQueuedPerThread, ThreadFactory threadFactory, long ttlMillis, Clock clock) {
        if (ttlMillis <= 0L) {
            throw new IllegalArgumentException("TTL must be strictly positive, was " + ttlMillis + "ms");
        }
        if (maxThreads <= 0) {
            throw new IllegalArgumentException("maxThreads must be strictly positive, was " + maxThreads);
        }
        if (maxTaskQueuedPerThread <= 0) {
            throw new IllegalArgumentException("maxTaskQueuedPerThread must be strictly positive, was " + maxTaskQueuedPerThread);
        }
        this.maxThreads = maxThreads;
        this.maxTaskQueuedPerThread = maxTaskQueuedPerThread;
        this.factory = threadFactory;
        this.clock = Objects.requireNonNull(clock, "A Clock must be provided");
        this.ttlMillis = ttlMillis;
        STATE.lazySet(this, INIT);
    }

    BoundedElasticScheduler(int maxThreads, int maxTaskQueuedPerThread, ThreadFactory factory2, int ttlSeconds) {
        this(maxThreads, maxTaskQueuedPerThread, factory2, (long)ttlSeconds * 1000L, Clock.tickSeconds(BoundedServices.ZONE_UTC));
    }

    BoundedScheduledExecutorService createBoundedExecutorService() {
        return new BoundedScheduledExecutorService(this.maxTaskQueuedPerThread, this.factory);
    }

    @Override
    public boolean isDisposed() {
        SchedulerState<BoundedServices> current = this.state;
        return current != INIT && current.currentResource == BoundedServices.SHUTDOWN;
    }

    @Override
    public void init() {
        SchedulerState<BoundedServices> a = this.state;
        if (a != INIT) {
            if (a.currentResource == BoundedServices.SHUTDOWN) {
                throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
            }
            return;
        }
        SchedulerState<BoundedServices> b = SchedulerState.init(new BoundedServices(this));
        if (STATE.compareAndSet(this, INIT, b)) {
            try {
                ((BoundedServices)b.currentResource).evictor.scheduleAtFixedRate(((BoundedServices)b.currentResource)::eviction, this.ttlMillis, this.ttlMillis, TimeUnit.MILLISECONDS);
                return;
            }
            catch (RejectedExecutionException ree) {
                throw new IllegalStateException("Scheduler disposed during initialization");
            }
        }
        ((BoundedServices)b.currentResource).evictor.shutdownNow();
        if (this.isDisposed()) {
            throw new IllegalStateException("Initializing a disposed scheduler is not permitted");
        }
    }

    @Override
    public void start() {
        SchedulerState<BoundedServices> a = this.state;
        if (a.currentResource != BoundedServices.SHUTDOWN) {
            return;
        }
        SchedulerState<BoundedServices> b = SchedulerState.init(new BoundedServices(this));
        if (STATE.compareAndSet(this, a, b)) {
            try {
                ((BoundedServices)b.currentResource).evictor.scheduleAtFixedRate(((BoundedServices)b.currentResource)::eviction, this.ttlMillis, this.ttlMillis, TimeUnit.MILLISECONDS);
                return;
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                // empty catch block
            }
        }
        ((BoundedServices)b.currentResource).evictor.shutdownNow();
    }

    @Override
    public boolean await(BoundedServices boundedServices, long timeout2, TimeUnit timeUnit) throws InterruptedException {
        if (!boundedServices.evictor.awaitTermination(timeout2, timeUnit)) {
            return false;
        }
        for (BoundedState bs : boundedServices.busyStates.array) {
            if (bs.executor.awaitTermination(timeout2, timeUnit)) continue;
            return false;
        }
        return true;
    }

    @Override
    public void dispose() {
        SchedulerState<BoundedServices> previous = this.state;
        if (previous.currentResource == BoundedServices.SHUTDOWN) {
            if (previous.initialResource != null) {
                ((BoundedServices)previous.initialResource).evictor.shutdownNow();
                for (BoundedState bs : ((BoundedServices)previous.initialResource).busyStates.array) {
                    bs.shutdown(true);
                }
            }
            return;
        }
        BoundedState[] toAwait = ((BoundedServices)previous.currentResource).dispose();
        SchedulerState<BoundedServices> shutDown = SchedulerState.transition(previous.currentResource, BoundedServices.SHUTDOWN, this);
        STATE.compareAndSet(this, previous, shutDown);
        assert (shutDown.initialResource != null);
        ((BoundedServices)shutDown.initialResource).evictor.shutdownNow();
        for (BoundedState bs : toAwait) {
            bs.shutdown(true);
        }
    }

    @Override
    public Mono<Void> disposeGracefully() {
        return Mono.defer(() -> {
            SchedulerState<BoundedServices> previous = this.state;
            if (previous.currentResource == BoundedServices.SHUTDOWN) {
                return previous.onDispose;
            }
            BoundedState[] toAwait = ((BoundedServices)previous.currentResource).dispose();
            SchedulerState<BoundedServices> shutDown = SchedulerState.transition(previous.currentResource, BoundedServices.SHUTDOWN, this);
            STATE.compareAndSet(this, previous, shutDown);
            assert (shutDown.initialResource != null);
            ((BoundedServices)shutDown.initialResource).evictor.shutdown();
            for (BoundedState bs : toAwait) {
                bs.shutdown(false);
            }
            return shutDown.onDispose;
        });
    }

    @Override
    public Disposable schedule(Runnable task) {
        BoundedState picked = ((BoundedServices)this.state.currentResource).pick();
        try {
            return Schedulers.directSchedule(picked.executor, task, picked, 0L, TimeUnit.MILLISECONDS);
        }
        catch (RejectedExecutionException ex) {
            picked.dispose();
            throw ex;
        }
    }

    @Override
    public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        BoundedState picked = ((BoundedServices)this.state.currentResource).pick();
        try {
            return Schedulers.directSchedule(picked.executor, task, picked, delay, unit);
        }
        catch (RejectedExecutionException ex) {
            picked.dispose();
            throw ex;
        }
    }

    @Override
    public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        BoundedState picked = ((BoundedServices)this.state.currentResource).pick();
        try {
            Disposable scheduledTask = Schedulers.directSchedulePeriodically(picked.executor, task, initialDelay, period, unit);
            return Disposables.composite(scheduledTask, picked);
        }
        catch (RejectedExecutionException ex) {
            picked.dispose();
            throw ex;
        }
    }

    public String toString() {
        StringBuilder ts = new StringBuilder("boundedElastic").append('(');
        if (this.factory instanceof ReactorThreadFactory) {
            ts.append('\"').append(((ReactorThreadFactory)this.factory).get()).append("\",");
        }
        ts.append("maxThreads=").append(this.maxThreads).append(",maxTaskQueuedPerThread=").append(this.maxTaskQueuedPerThread == Integer.MAX_VALUE ? "unbounded" : Integer.valueOf(this.maxTaskQueuedPerThread)).append(",ttl=");
        if (this.ttlMillis < 1000L) {
            ts.append(this.ttlMillis).append("ms)");
        } else {
            ts.append(this.ttlMillis / 1000L).append("s)");
        }
        return ts.toString();
    }

    int estimateSize() {
        return ((BoundedServices)this.state.currentResource).get();
    }

    int estimateBusy() {
        return ((BoundedServices)this.state.currentResource).busyStates.array.length;
    }

    int estimateIdle() {
        return ((BoundedServices)this.state.currentResource).idleQueue.size();
    }

    int estimateRemainingTaskCapacity() {
        BoundedState[] busyArray = ((BoundedServices)this.state.currentResource).busyStates.array;
        int totalTaskCapacity = this.maxTaskQueuedPerThread * this.maxThreads;
        for (BoundedState state : busyArray) {
            int stateQueueSize = state.estimateQueueSize();
            if (stateQueueSize >= 0) {
                totalTaskCapacity -= stateQueueSize;
                continue;
            }
            return -1;
        }
        return totalTaskCapacity;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.BUFFERED) {
            return this.estimateSize();
        }
        if (key == Scannable.Attr.CAPACITY) {
            return this.maxThreads;
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        return null;
    }

    @Override
    public Stream<? extends Scannable> inners() {
        BoundedServices services = (BoundedServices)this.state.currentResource;
        return Stream.concat(Stream.of(services.busyStates.array), services.idleQueue.stream()).filter(obj -> obj != null && obj != BoundedServices.CREATING);
    }

    @Override
    public Scheduler.Worker createWorker() {
        BoundedState picked = ((BoundedServices)this.state.currentResource).pick();
        ExecutorServiceWorker worker = new ExecutorServiceWorker(picked.executor);
        worker.disposables.add(picked);
        return worker;
    }

    static final class BoundedScheduledExecutorService
    extends ScheduledThreadPoolExecutor
    implements Scannable {
        final int queueCapacity;

        BoundedScheduledExecutorService(int queueCapacity, ThreadFactory factory2) {
            super(1, factory2);
            this.setMaximumPoolSize(1);
            this.setRemoveOnCancelPolicy(true);
            if (queueCapacity < 1) {
                throw new IllegalArgumentException("was expecting a non-zero positive queue capacity");
            }
            this.queueCapacity = queueCapacity;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (Scannable.Attr.TERMINATED == key) {
                return this.isTerminated();
            }
            if (Scannable.Attr.BUFFERED == key) {
                return this.getQueue().size();
            }
            if (Scannable.Attr.CAPACITY == key) {
                return this.queueCapacity;
            }
            return null;
        }

        @Override
        public String toString() {
            String state;
            int queued = this.getQueue().size();
            long completed2 = this.getCompletedTaskCount();
            String string = state = this.getActiveCount() > 0 ? "ACTIVE" : "IDLE";
            if (this.queueCapacity == Integer.MAX_VALUE) {
                return "BoundedScheduledExecutorService{" + state + ", queued=" + queued + "/unbounded, completed=" + completed2 + '}';
            }
            return "BoundedScheduledExecutorService{" + state + ", queued=" + queued + "/" + this.queueCapacity + ", completed=" + completed2 + '}';
        }

        void ensureQueueCapacity(int taskCount) {
            if (this.queueCapacity == Integer.MAX_VALUE) {
                return;
            }
            int queueSize = super.getQueue().size();
            if (queueSize + taskCount > this.queueCapacity) {
                throw Exceptions.failWithRejected("Task capacity of bounded elastic scheduler reached while scheduling " + taskCount + " tasks (" + (queueSize + taskCount) + "/" + this.queueCapacity + ")");
            }
        }

        @Override
        public synchronized ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            this.ensureQueueCapacity(1);
            return super.schedule(command, delay, unit);
        }

        @Override
        public synchronized <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            this.ensureQueueCapacity(1);
            return super.schedule(callable, delay, unit);
        }

        @Override
        public synchronized ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            this.ensureQueueCapacity(1);
            return super.scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            this.ensureQueueCapacity(1);
            return super.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }

        @Override
        public void shutdown() {
            super.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            return super.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return super.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return super.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout2, TimeUnit unit) throws InterruptedException {
            return super.awaitTermination(timeout2, unit);
        }

        @Override
        public synchronized <T> Future<T> submit(Callable<T> task) {
            this.ensureQueueCapacity(1);
            return super.submit(task);
        }

        @Override
        public synchronized <T> Future<T> submit(Runnable task, T result) {
            this.ensureQueueCapacity(1);
            return super.submit(task, result);
        }

        @Override
        public synchronized Future<?> submit(Runnable task) {
            this.ensureQueueCapacity(1);
            return super.submit(task);
        }

        @Override
        public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks2) throws InterruptedException {
            this.ensureQueueCapacity(tasks2.size());
            return super.invokeAll(tasks2);
        }

        @Override
        public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks2, long timeout2, TimeUnit unit) throws InterruptedException {
            this.ensureQueueCapacity(tasks2.size());
            return super.invokeAll(tasks2, timeout2, unit);
        }

        @Override
        public synchronized <T> T invokeAny(Collection<? extends Callable<T>> tasks2) throws InterruptedException, ExecutionException {
            this.ensureQueueCapacity(tasks2.size());
            return super.invokeAny(tasks2);
        }

        @Override
        public synchronized <T> T invokeAny(Collection<? extends Callable<T>> tasks2, long timeout2, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            this.ensureQueueCapacity(tasks2.size());
            return super.invokeAny(tasks2, timeout2, unit);
        }

        @Override
        public synchronized void execute(Runnable command) {
            this.ensureQueueCapacity(1);
            super.submit(command);
        }
    }

    static class BoundedState
    implements Disposable,
    Scannable {
        static final int EVICTED = -1;
        final BoundedServices parent;
        final ScheduledExecutorService executor;
        long idleSinceTimestamp = -1L;
        volatile int markCount;
        static final AtomicIntegerFieldUpdater<BoundedState> MARK_COUNT = AtomicIntegerFieldUpdater.newUpdater(BoundedState.class, "markCount");

        BoundedState(BoundedServices parent, ScheduledExecutorService executor2) {
            this.parent = parent;
            this.executor = executor2;
        }

        int estimateQueueSize() {
            if (this.executor instanceof ScheduledThreadPoolExecutor) {
                return ((ScheduledThreadPoolExecutor)this.executor).getQueue().size();
            }
            return -1;
        }

        boolean markPicked() {
            int i;
            do {
                if ((i = MARK_COUNT.get(this)) != -1) continue;
                return false;
            } while (!MARK_COUNT.compareAndSet(this, i, i + 1));
            return true;
        }

        boolean tryEvict(long evictionTimestamp, long ttlMillis) {
            long idleSince = this.idleSinceTimestamp;
            if (idleSince < 0L) {
                return false;
            }
            long elapsed = evictionTimestamp - idleSince;
            if (elapsed >= ttlMillis && MARK_COUNT.compareAndSet(this, 0, -1)) {
                this.executor.shutdownNow();
                return true;
            }
            return false;
        }

        void release() {
            int picked = MARK_COUNT.decrementAndGet(this);
            if (picked < 0) {
                return;
            }
            if (picked == 0) {
                this.idleSinceTimestamp = this.parent.clock.millis();
                this.parent.setIdle(this);
            } else {
                this.idleSinceTimestamp = -1L;
            }
        }

        void shutdown(boolean now) {
            this.idleSinceTimestamp = -1L;
            MARK_COUNT.set(this, -1);
            if (now) {
                this.executor.shutdownNow();
            } else {
                this.executor.shutdown();
            }
        }

        @Override
        public void dispose() {
            this.release();
        }

        @Override
        public boolean isDisposed() {
            return MARK_COUNT.get(this) <= 0;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            return Schedulers.scanExecutor(this.executor, key);
        }

        public String toString() {
            return "BoundedState@" + System.identityHashCode(this) + "{ backing=" + MARK_COUNT.get(this) + ", idleSince=" + this.idleSinceTimestamp + ", executor=" + this.executor + '}';
        }
    }

    static final class BoundedServices
    extends AtomicInteger {
        static final ZoneId ZONE_UTC = ZoneId.of("UTC");
        static final BusyStates ALL_IDLE = new BusyStates(new BoundedState[0], false);
        static final BusyStates ALL_SHUTDOWN = new BusyStates(new BoundedState[0], true);
        static final ScheduledExecutorService EVICTOR_SHUTDOWN = Executors.newSingleThreadScheduledExecutor();
        static final BoundedServices SHUTDOWN;
        static final BoundedServices SHUTTING_DOWN;
        static final BoundedState CREATING;
        static final AtomicLong EVICTOR_COUNTER;
        static final ThreadFactory EVICTOR_FACTORY;
        final BoundedElasticScheduler parent;
        final Clock clock;
        final ScheduledExecutorService evictor;
        final Deque<BoundedState> idleQueue;
        volatile BusyStates busyStates;
        static final AtomicReferenceFieldUpdater<BoundedServices, BusyStates> BUSY_STATES;

        private BoundedServices() {
            this.parent = null;
            this.clock = Clock.fixed(Instant.EPOCH, ZONE_UTC);
            this.idleQueue = new ConcurrentLinkedDeque<BoundedState>();
            this.busyStates = ALL_SHUTDOWN;
            this.evictor = EVICTOR_SHUTDOWN;
        }

        BoundedServices(BoundedElasticScheduler parent) {
            this.parent = parent;
            this.clock = parent.clock;
            this.idleQueue = new ConcurrentLinkedDeque<BoundedState>();
            this.busyStates = ALL_IDLE;
            this.evictor = Executors.newSingleThreadScheduledExecutor(EVICTOR_FACTORY);
        }

        void eviction() {
            long evictionTimestamp = this.parent.clock.millis();
            ArrayList<BoundedState> idleCandidates = new ArrayList<BoundedState>(this.idleQueue);
            for (BoundedState candidate : idleCandidates) {
                if (!candidate.tryEvict(evictionTimestamp, this.parent.ttlMillis)) continue;
                this.idleQueue.remove(candidate);
                this.decrementAndGet();
            }
        }

        boolean setBusy(BoundedState bs) {
            BoundedState[] replacement;
            BusyStates previous;
            do {
                previous = this.busyStates;
                if (previous.shutdown) {
                    return false;
                }
                int len = previous.array.length;
                replacement = new BoundedState[len + 1];
                System.arraycopy(previous.array, 0, replacement, 0, len);
                replacement[len] = bs;
            } while (!BUSY_STATES.compareAndSet(this, previous, new BusyStates(replacement, false)));
            return true;
        }

        void setIdle(BoundedState boundedState) {
            BusyStates replacement;
            BusyStates current;
            do {
                block6: {
                    int len;
                    BoundedState[] arr;
                    block5: {
                        current = this.busyStates;
                        arr = this.busyStates.array;
                        len = arr.length;
                        if (len == 0 || current.shutdown) {
                            return;
                        }
                        replacement = null;
                        if (len != 1) break block5;
                        if (arr[0] != boundedState) break block6;
                        replacement = ALL_IDLE;
                        break block6;
                    }
                    for (int i = 0; i < len; ++i) {
                        BoundedState state = arr[i];
                        if (state != boundedState) continue;
                        replacement = new BusyStates(new BoundedState[len - 1], false);
                        System.arraycopy(arr, 0, replacement.array, 0, i);
                        System.arraycopy(arr, i + 1, replacement.array, i, len - i - 1);
                        break;
                    }
                }
                if (replacement != null) continue;
                return;
            } while (!BUSY_STATES.compareAndSet(this, current, replacement));
            this.idleQueue.add(boundedState);
            if (this.busyStates.shutdown) {
                boundedState.shutdown(true);
                while ((boundedState = this.idleQueue.pollLast()) != null) {
                    boundedState.shutdown(true);
                }
            }
        }

        BoundedState pick() {
            Object s2;
            while (true) {
                if (this.busyStates == ALL_SHUTDOWN) {
                    return CREATING;
                }
                int a = this.get();
                if (!this.idleQueue.isEmpty()) {
                    BoundedState bs = this.idleQueue.pollLast();
                    if (bs == null || !bs.markPicked()) continue;
                    boolean accepted = this.setBusy(bs);
                    if (!accepted) {
                        bs.shutdown(true);
                        return CREATING;
                    }
                    return bs;
                }
                if (a < this.parent.maxThreads) {
                    BoundedState newState;
                    if (!this.compareAndSet(a, a + 1) || !(newState = new BoundedState(this, (ScheduledExecutorService)(s2 = Schedulers.decorateExecutorService(this.parent, this.parent.createBoundedExecutorService())))).markPicked()) continue;
                    boolean accepted = this.setBusy(newState);
                    if (!accepted) {
                        newState.shutdown(true);
                        return CREATING;
                    }
                    return newState;
                }
                s2 = this.choseOneBusy();
                if (s2 != null && ((BoundedState)s2).markPicked()) break;
            }
            return s2;
        }

        @Nullable
        private BoundedState choseOneBusy() {
            BoundedState[] arr = this.busyStates.array;
            int len = arr.length;
            if (len == 0) {
                return null;
            }
            if (len == 1) {
                return arr[0];
            }
            BoundedState choice = arr[0];
            int leastBusy = Integer.MAX_VALUE;
            for (int i = 0; i < arr.length; ++i) {
                BoundedState state = arr[i];
                int busy = state.markCount;
                if (busy >= leastBusy) continue;
                leastBusy = busy;
                choice = state;
            }
            return choice;
        }

        public BoundedState[] dispose() {
            BoundedState bs;
            BusyStates current;
            do {
                current = this.busyStates;
                if (!current.shutdown) continue;
                return current.array;
            } while (!BUSY_STATES.compareAndSet(this, current, new BusyStates(current.array, true)));
            BoundedState[] arr = current.array;
            ArrayList<BoundedState> toAwait = new ArrayList<BoundedState>(this.idleQueue.size() + arr.length);
            while ((bs = this.idleQueue.pollLast()) != null) {
                toAwait.add(bs);
            }
            Collections.addAll(toAwait, arr);
            return toAwait.toArray(new BoundedState[0]);
        }

        static {
            EVICTOR_SHUTDOWN.shutdownNow();
            SHUTDOWN = new BoundedServices();
            SHUTTING_DOWN = new BoundedServices();
            SHUTDOWN.dispose();
            SHUTTING_DOWN.dispose();
            ScheduledExecutorService s2 = Executors.newSingleThreadScheduledExecutor();
            s2.shutdownNow();
            CREATING = new BoundedState(SHUTDOWN, s2){

                @Override
                public String toString() {
                    return "CREATING BoundedState";
                }
            };
            BoundedServices.CREATING.markCount = -1;
            BoundedServices.CREATING.idleSinceTimestamp = -1L;
            EVICTOR_COUNTER = new AtomicLong();
            EVICTOR_FACTORY = r -> {
                Thread t2 = new Thread(r, "boundedElastic-evictor-" + EVICTOR_COUNTER.incrementAndGet());
                t2.setDaemon(true);
                return t2;
            };
            BUSY_STATES = AtomicReferenceFieldUpdater.newUpdater(BoundedServices.class, BusyStates.class, "busyStates");
        }

        static final class BusyStates {
            final BoundedState[] array;
            final boolean shutdown;

            public BusyStates(BoundedState[] array, boolean shutdown) {
                this.array = array;
                this.shutdown = shutdown;
            }
        }
    }
}

