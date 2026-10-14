/*
 * Decompiled with CFR 0.152.
 */
package io.grpc;

import io.grpc.Deadline;
import io.grpc.PersistentHashArrayMappedTrie;
import io.grpc.ThreadLocalContextStorage;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

@CheckReturnValue
public class Context {
    static final Logger log = Logger.getLogger(Context.class.getName());
    static final int CONTEXT_DEPTH_WARN_THRESH = 1000;
    public static final Context ROOT = new Context();
    final CancellableContext cancellableAncestor;
    final PersistentHashArrayMappedTrie.Node<Key<?>, Object> keyValueEntries;
    final int generation;

    static Storage storage() {
        return LazyStorage.storage;
    }

    public static <T> Key<T> key(String debugString) {
        return new Key(debugString);
    }

    public static <T> Key<T> keyWithDefault(String debugString, T defaultValue) {
        return new Key<T>(debugString, defaultValue);
    }

    public static Context current() {
        Context current = Context.storage().current();
        if (current == null) {
            return ROOT;
        }
        return current;
    }

    private Context(PersistentHashArrayMappedTrie.Node<Key<?>, Object> keyValueEntries, int generation) {
        this.cancellableAncestor = null;
        this.keyValueEntries = keyValueEntries;
        this.generation = generation;
        Context.validateGeneration(generation);
    }

    private Context(Context parent, PersistentHashArrayMappedTrie.Node<Key<?>, Object> keyValueEntries) {
        this.cancellableAncestor = Context.cancellableAncestor(parent);
        this.keyValueEntries = keyValueEntries;
        this.generation = parent.generation + 1;
        Context.validateGeneration(this.generation);
    }

    private Context() {
        this.cancellableAncestor = null;
        this.keyValueEntries = null;
        this.generation = 0;
        Context.validateGeneration(this.generation);
    }

    public CancellableContext withCancellation() {
        return new CancellableContext(this);
    }

    public CancellableContext withDeadlineAfter(long duration, TimeUnit unit, ScheduledExecutorService scheduler) {
        return this.withDeadline(Deadline.after(duration, unit), scheduler);
    }

    public CancellableContext withDeadline(Deadline newDeadline, ScheduledExecutorService scheduler) {
        Context.checkNotNull(newDeadline, "deadline");
        Context.checkNotNull(scheduler, "scheduler");
        Deadline existingDeadline = this.getDeadline();
        boolean scheduleDeadlineCancellation = true;
        if (existingDeadline != null && existingDeadline.compareTo(newDeadline) <= 0) {
            newDeadline = existingDeadline;
            scheduleDeadlineCancellation = false;
        }
        CancellableContext newCtx = new CancellableContext(this, newDeadline);
        if (scheduleDeadlineCancellation) {
            newCtx.setUpDeadlineCancellation(newDeadline, scheduler);
        }
        return newCtx;
    }

    public <V> Context withValue(Key<V> k1, V v1) {
        PersistentHashArrayMappedTrie.Node<Key<?>, Object> newKeyValueEntries = PersistentHashArrayMappedTrie.put(this.keyValueEntries, k1, v1);
        return new Context(this, newKeyValueEntries);
    }

    public <V1, V2> Context withValues(Key<V1> k1, V1 v1, Key<V2> k2, V2 v2) {
        PersistentHashArrayMappedTrie.Node<Key<?>, Object> newKeyValueEntries = PersistentHashArrayMappedTrie.put(this.keyValueEntries, k1, v1);
        newKeyValueEntries = PersistentHashArrayMappedTrie.put(newKeyValueEntries, k2, v2);
        return new Context(this, newKeyValueEntries);
    }

    public <V1, V2, V3> Context withValues(Key<V1> k1, V1 v1, Key<V2> k2, V2 v2, Key<V3> k3, V3 v3) {
        PersistentHashArrayMappedTrie.Node<Key<?>, Object> newKeyValueEntries = PersistentHashArrayMappedTrie.put(this.keyValueEntries, k1, v1);
        newKeyValueEntries = PersistentHashArrayMappedTrie.put(newKeyValueEntries, k2, v2);
        newKeyValueEntries = PersistentHashArrayMappedTrie.put(newKeyValueEntries, k3, v3);
        return new Context(this, newKeyValueEntries);
    }

    public <V1, V2, V3, V4> Context withValues(Key<V1> k1, V1 v1, Key<V2> k2, V2 v2, Key<V3> k3, V3 v3, Key<V4> k4, V4 v4) {
        PersistentHashArrayMappedTrie.Node<Key<?>, Object> newKeyValueEntries = PersistentHashArrayMappedTrie.put(this.keyValueEntries, k1, v1);
        newKeyValueEntries = PersistentHashArrayMappedTrie.put(newKeyValueEntries, k2, v2);
        newKeyValueEntries = PersistentHashArrayMappedTrie.put(newKeyValueEntries, k3, v3);
        newKeyValueEntries = PersistentHashArrayMappedTrie.put(newKeyValueEntries, k4, v4);
        return new Context(this, newKeyValueEntries);
    }

    public Context fork() {
        return new Context(this.keyValueEntries, this.generation + 1);
    }

    public Context attach() {
        Context prev = Context.storage().doAttach(this);
        if (prev == null) {
            return ROOT;
        }
        return prev;
    }

    public void detach(Context toAttach) {
        Context.checkNotNull(toAttach, "toAttach");
        Context.storage().detach(this, toAttach);
    }

    boolean isCurrent() {
        return Context.current() == this;
    }

    public boolean isCancelled() {
        if (this.cancellableAncestor == null) {
            return false;
        }
        return this.cancellableAncestor.isCancelled();
    }

    public Throwable cancellationCause() {
        if (this.cancellableAncestor == null) {
            return null;
        }
        return this.cancellableAncestor.cancellationCause();
    }

    public Deadline getDeadline() {
        if (this.cancellableAncestor == null) {
            return null;
        }
        return this.cancellableAncestor.getDeadline();
    }

    public void addListener(CancellationListener cancellationListener, Executor executor2) {
        Context.checkNotNull(cancellationListener, "cancellationListener");
        Context.checkNotNull(executor2, "executor");
        if (this.cancellableAncestor == null) {
            return;
        }
        this.cancellableAncestor.addListenerInternal(new ExecutableListener(executor2, cancellationListener, this));
    }

    public void removeListener(CancellationListener cancellationListener) {
        if (this.cancellableAncestor == null) {
            return;
        }
        this.cancellableAncestor.removeListenerInternal(cancellationListener, this);
    }

    int listenerCount() {
        if (this.cancellableAncestor == null) {
            return 0;
        }
        return this.cancellableAncestor.listenerCount();
    }

    public void run(Runnable r) {
        Context previous = this.attach();
        try {
            r.run();
        }
        finally {
            this.detach(previous);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CanIgnoreReturnValue
    public <V> V call(Callable<V> c) throws Exception {
        Context previous = this.attach();
        try {
            V v = c.call();
            return v;
        }
        finally {
            this.detach(previous);
        }
    }

    public Runnable wrap(final Runnable r) {
        return new Runnable(){

            @Override
            public void run() {
                Context previous = Context.this.attach();
                try {
                    r.run();
                }
                finally {
                    Context.this.detach(previous);
                }
            }
        };
    }

    public <C> Callable<C> wrap(final Callable<C> c) {
        return new Callable<C>(){

            @Override
            public C call() throws Exception {
                Context previous = Context.this.attach();
                try {
                    Object v = c.call();
                    return v;
                }
                finally {
                    Context.this.detach(previous);
                }
            }
        };
    }

    public Executor fixedContextExecutor(final Executor e) {
        final class FixedContextExecutor
        implements Executor {
            FixedContextExecutor() {
            }

            @Override
            public void execute(Runnable r) {
                e.execute(Context.this.wrap(r));
            }
        }
        return new FixedContextExecutor();
    }

    public static Executor currentContextExecutor(Executor e) {
        final class CurrentContextExecutor
        implements Executor {
            final /* synthetic */ Executor val$e;

            CurrentContextExecutor(Executor executor2) {
                this.val$e = executor2;
            }

            @Override
            public void execute(Runnable r) {
                this.val$e.execute(Context.current().wrap(r));
            }
        }
        return new CurrentContextExecutor(e);
    }

    @CanIgnoreReturnValue
    static <T> T checkNotNull(T reference, Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    static CancellableContext cancellableAncestor(Context parent) {
        if (parent instanceof CancellableContext) {
            return (CancellableContext)parent;
        }
        return parent.cancellableAncestor;
    }

    private static void validateGeneration(int generation) {
        if (generation == 1000) {
            log.log(Level.SEVERE, "Context ancestry chain length is abnormally long. This suggests an error in application code. Length exceeded: 1000", new Exception());
        }
    }

    static @interface CanIgnoreReturnValue {
    }

    static @interface CheckReturnValue {
    }

    private static enum DirectExecutor implements Executor
    {
        INSTANCE;


        @Override
        public void execute(Runnable command) {
            command.run();
        }

        public String toString() {
            return "Context.DirectExecutor";
        }
    }

    private static final class ExecutableListener
    implements Runnable {
        private final Executor executor;
        final CancellationListener listener;
        private final Context context;

        ExecutableListener(Executor executor2, CancellationListener listener, Context context) {
            this.executor = executor2;
            this.listener = listener;
            this.context = context;
        }

        void deliver() {
            try {
                this.executor.execute(this);
            }
            catch (Throwable t2) {
                log.log(Level.INFO, "Exception notifying context listener", t2);
            }
        }

        @Override
        public void run() {
            this.listener.cancelled(this.context);
        }
    }

    public static abstract class Storage {
        public abstract Context doAttach(Context var1);

        public abstract void detach(Context var1, Context var2);

        public abstract Context current();
    }

    public static final class Key<T> {
        private final String name;
        private final T defaultValue;

        Key(String name) {
            this(name, null);
        }

        Key(String name, T defaultValue) {
            this.name = Context.checkNotNull(name, "name");
            this.defaultValue = defaultValue;
        }

        public T get() {
            return this.get(Context.current());
        }

        public T get(Context context) {
            Object value = PersistentHashArrayMappedTrie.get(context.keyValueEntries, this);
            return (T)(value == null ? this.defaultValue : value);
        }

        public String toString() {
            return this.name;
        }
    }

    public static interface CancellationListener {
        public void cancelled(Context var1);
    }

    public static final class CancellableContext
    extends Context
    implements Closeable {
        private final Deadline deadline;
        private final Context uncancellableSurrogate;
        private ArrayList<ExecutableListener> listeners;
        private CancellationListener parentListener;
        private Throwable cancellationCause;
        private ScheduledFuture<?> pendingDeadline;
        private boolean cancelled;

        private CancellableContext(Context parent) {
            super(parent, parent.keyValueEntries);
            this.deadline = parent.getDeadline();
            this.uncancellableSurrogate = new Context(this, this.keyValueEntries);
        }

        private CancellableContext(Context parent, Deadline deadline) {
            super(parent, parent.keyValueEntries);
            this.deadline = deadline;
            this.uncancellableSurrogate = new Context(this, this.keyValueEntries);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void setUpDeadlineCancellation(Deadline deadline, ScheduledExecutorService scheduler) {
            if (!deadline.isExpired()) {
                CancellableContext cancellableContext = this;
                synchronized (cancellableContext) {
                    final class CancelOnExpiration
                    implements Runnable {
                        CancelOnExpiration() {
                        }

                        @Override
                        public void run() {
                            try {
                                CancellableContext.this.cancel(new TimeoutException("context timed out"));
                            }
                            catch (Throwable t2) {
                                log.log(Level.SEVERE, "Cancel threw an exception, which should not happen", t2);
                            }
                        }
                    }
                    this.pendingDeadline = deadline.runOnExpiration(new CancelOnExpiration(), scheduler);
                }
            } else {
                this.cancel(new TimeoutException("context timed out"));
            }
        }

        @Override
        public Context attach() {
            return this.uncancellableSurrogate.attach();
        }

        @Override
        public void detach(Context toAttach) {
            this.uncancellableSurrogate.detach(toAttach);
        }

        @Override
        public void addListener(CancellationListener cancellationListener, Executor executor2) {
            CancellableContext.checkNotNull(cancellationListener, "cancellationListener");
            CancellableContext.checkNotNull(executor2, "executor");
            this.addListenerInternal(new ExecutableListener(executor2, cancellationListener, this));
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void addListenerInternal(ExecutableListener executableListener) {
            CancellableContext cancellableContext = this;
            synchronized (cancellableContext) {
                if (this.isCancelled()) {
                    executableListener.deliver();
                } else if (this.listeners == null) {
                    this.listeners = new ArrayList();
                    this.listeners.add(executableListener);
                    if (this.cancellableAncestor != null) {
                        this.parentListener = new CancellationListener(){

                            @Override
                            public void cancelled(Context context) {
                                CancellableContext.this.cancel(context.cancellationCause());
                            }
                        };
                        this.cancellableAncestor.addListenerInternal(new ExecutableListener(DirectExecutor.INSTANCE, this.parentListener, this));
                    }
                } else {
                    this.listeners.add(executableListener);
                }
            }
        }

        @Override
        public void removeListener(CancellationListener cancellationListener) {
            this.removeListenerInternal(cancellationListener, this);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void removeListenerInternal(CancellationListener cancellationListener, Context context) {
            CancellableContext cancellableContext = this;
            synchronized (cancellableContext) {
                if (this.listeners != null) {
                    for (int i = this.listeners.size() - 1; i >= 0; --i) {
                        ExecutableListener executableListener = this.listeners.get(i);
                        if (executableListener.listener != cancellationListener || executableListener.context != context) continue;
                        this.listeners.remove(i);
                        break;
                    }
                    if (this.listeners.isEmpty()) {
                        if (this.cancellableAncestor != null) {
                            this.cancellableAncestor.removeListener(this.parentListener);
                        }
                        this.parentListener = null;
                        this.listeners = null;
                    }
                }
            }
        }

        @Override
        @Deprecated
        public boolean isCurrent() {
            return this.uncancellableSurrogate.isCurrent();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CanIgnoreReturnValue
        public boolean cancel(Throwable cause) {
            boolean triggeredCancel = false;
            ScheduledFuture<?> localPendingDeadline = null;
            CancellableContext cancellableContext = this;
            synchronized (cancellableContext) {
                if (!this.cancelled) {
                    this.cancelled = true;
                    if (this.pendingDeadline != null) {
                        localPendingDeadline = this.pendingDeadline;
                        this.pendingDeadline = null;
                    }
                    this.cancellationCause = cause;
                    triggeredCancel = true;
                }
            }
            if (localPendingDeadline != null) {
                localPendingDeadline.cancel(false);
            }
            if (triggeredCancel) {
                this.notifyAndClearListeners();
            }
            return triggeredCancel;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void notifyAndClearListeners() {
            ArrayList<ExecutableListener> tmpListeners;
            CancellationListener tmpParentListener;
            Iterator<ExecutableListener> iterator2 = this;
            synchronized (iterator2) {
                if (this.listeners == null) {
                    return;
                }
                tmpParentListener = this.parentListener;
                this.parentListener = null;
                tmpListeners = this.listeners;
                this.listeners = null;
            }
            for (ExecutableListener tmpListener : tmpListeners) {
                if (tmpListener.context != this) continue;
                tmpListener.deliver();
            }
            for (ExecutableListener tmpListener : tmpListeners) {
                if (tmpListener.context == this) continue;
                tmpListener.deliver();
            }
            if (this.cancellableAncestor != null) {
                this.cancellableAncestor.removeListener(tmpParentListener);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        int listenerCount() {
            CancellableContext cancellableContext = this;
            synchronized (cancellableContext) {
                return this.listeners == null ? 0 : this.listeners.size();
            }
        }

        public void detachAndCancel(Context toAttach, Throwable cause) {
            try {
                this.detach(toAttach);
            }
            finally {
                this.cancel(cause);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isCancelled() {
            CancellableContext cancellableContext = this;
            synchronized (cancellableContext) {
                if (this.cancelled) {
                    return true;
                }
            }
            if (super.isCancelled()) {
                this.cancel(super.cancellationCause());
                return true;
            }
            return false;
        }

        @Override
        public Throwable cancellationCause() {
            if (this.isCancelled()) {
                return this.cancellationCause;
            }
            return null;
        }

        @Override
        public Deadline getDeadline() {
            return this.deadline;
        }

        @Override
        public void close() {
            this.cancel(null);
        }
    }

    private static final class LazyStorage {
        static final Storage storage;

        private LazyStorage() {
        }

        private static Storage createStorage(AtomicReference<? super ClassNotFoundException> deferredStorageFailure) {
            try {
                Class<?> clazz = Class.forName("io.grpc.override.ContextStorageOverride");
                return clazz.asSubclass(Storage.class).getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (ClassNotFoundException e) {
                deferredStorageFailure.set(e);
                return new ThreadLocalContextStorage();
            }
            catch (Exception e) {
                throw new RuntimeException("Storage override failed to initialize", e);
            }
        }

        static {
            AtomicReference deferredStorageFailure = new AtomicReference();
            storage = LazyStorage.createStorage(deferredStorageFailure);
            Throwable failure = (Throwable)deferredStorageFailure.get();
            if (failure != null) {
                log.log(Level.FINE, "Storage override doesn't exist. Using default", failure);
            }
        }
    }
}

