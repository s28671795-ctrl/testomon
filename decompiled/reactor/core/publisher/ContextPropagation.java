/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.context.ContextAccessor
 *  io.micrometer.context.ContextRegistry
 *  io.micrometer.context.ContextSnapshot
 *  io.micrometer.context.ContextSnapshot$Scope
 *  io.micrometer.context.ThreadLocalAccessor
 */
package reactor.core.publisher;

import io.micrometer.context.ContextAccessor;
import io.micrometer.context.ContextRegistry;
import io.micrometer.context.ContextSnapshot;
import io.micrometer.context.ThreadLocalAccessor;
import java.util.AbstractQueue;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import reactor.core.observability.SignalListener;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.SynchronousSink;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class ContextPropagation {
    static final Logger LOGGER;
    static final boolean isContextPropagationOnClasspath;
    static boolean propagateContextToThreadLocals;
    static final Predicate<Object> PREDICATE_TRUE;
    static final Function<Context, Context> NO_OP;
    static final Function<Context, Context> WITH_GLOBAL_REGISTRY_NO_PREDICATE;

    ContextPropagation() {
    }

    static boolean isContextPropagationAvailable() {
        return isContextPropagationOnClasspath;
    }

    static boolean shouldPropagateContextToThreadLocals() {
        return isContextPropagationOnClasspath && propagateContextToThreadLocals;
    }

    public static Function<Runnable, Runnable> scopePassingOnScheduleHook() {
        return delegate -> {
            ContextSnapshot contextSnapshot = ContextSnapshot.captureAll((Object[])new Object[0]);
            return contextSnapshot.wrap(delegate);
        };
    }

    static Function<Context, Context> contextCapture() {
        if (!isContextPropagationOnClasspath) {
            return NO_OP;
        }
        return WITH_GLOBAL_REGISTRY_NO_PREDICATE;
    }

    static Function<Context, Context> contextCapture(Predicate<Object> captureKeyPredicate) {
        if (!isContextPropagationOnClasspath) {
            return NO_OP;
        }
        return target -> (Context)ContextSnapshot.captureAllUsing((Predicate)captureKeyPredicate, (ContextRegistry)ContextRegistry.getInstance(), (Object[])new Object[0]).updateContext(target);
    }

    static <T, R> BiConsumer<T, SynchronousSink<R>> contextRestoreForHandle(BiConsumer<T, SynchronousSink<R>> handler, Supplier<Context> contextSupplier) {
        if (propagateContextToThreadLocals || !ContextPropagation.isContextPropagationAvailable()) {
            return handler;
        }
        Context ctx = contextSupplier.get();
        if (ctx.isEmpty()) {
            return handler;
        }
        return (v, sink2) -> {
            try (ContextSnapshot.Scope ignored = ContextSnapshot.setAllThreadLocalsFrom((Object)ctx);){
                handler.accept((Object)v, (SynchronousSink)sink2);
            }
        };
    }

    static <T> SignalListener<T> contextRestoreForTap(SignalListener<T> original, Supplier<Context> contextSupplier) {
        if (propagateContextToThreadLocals || !ContextPropagation.isContextPropagationAvailable()) {
            return original;
        }
        Context ctx = contextSupplier.get();
        if (ctx.isEmpty()) {
            return original;
        }
        return new ContextRestoreSignalListener<T>(original, ctx, null);
    }

    static <C> ContextSnapshot.Scope setThreadLocals(Object context) {
        ContextRegistry registry = ContextRegistry.getInstance();
        ContextAccessor contextAccessor = registry.getContextAccessorForRead(context);
        Map<Object, Object> previousValues = null;
        for (ThreadLocalAccessor threadLocalAccessor : registry.getThreadLocalAccessors()) {
            Object key = threadLocalAccessor.key();
            Object value = contextAccessor.readValue(context, key);
            previousValues = ContextPropagation.setThreadLocal(key, value, threadLocalAccessor, previousValues);
        }
        return ReactorScopeImpl.from(previousValues, registry);
    }

    private static <V> Map<Object, Object> setThreadLocal(Object key, @Nullable V value, ThreadLocalAccessor<?> accessor, @Nullable Map<Object, Object> previousValues) {
        previousValues = previousValues != null ? previousValues : new HashMap<Object, Object>();
        previousValues.put(key, accessor.getValue());
        if (value != null) {
            accessor.setValue(value);
        } else {
            accessor.reset();
        }
        return previousValues;
    }

    static {
        boolean contextPropagation;
        ContextCaptureNoPredicate contextCaptureFunction;
        propagateContextToThreadLocals = false;
        PREDICATE_TRUE = v -> true;
        NO_OP = c -> c;
        LOGGER = Loggers.getLogger(ContextPropagation.class);
        try {
            ContextRegistry globalRegistry = ContextRegistry.getInstance();
            contextCaptureFunction = new ContextCaptureNoPredicate(globalRegistry);
            contextPropagation = true;
        }
        catch (LinkageError t2) {
            contextCaptureFunction = NO_OP;
            contextPropagation = false;
        }
        catch (Throwable t3) {
            contextCaptureFunction = NO_OP;
            contextPropagation = false;
            LOGGER.error("Unexpected exception while detecting ContextPropagation feature. The feature is considered disabled due to this:", t3);
        }
        isContextPropagationOnClasspath = contextPropagation;
        WITH_GLOBAL_REGISTRY_NO_PREDICATE = contextCaptureFunction;
    }

    private static class ReactorScopeImpl
    implements ContextSnapshot.Scope {
        private final Map<Object, Object> previousValues;
        private final ContextRegistry contextRegistry;

        private ReactorScopeImpl(Map<Object, Object> previousValues, ContextRegistry contextRegistry) {
            this.previousValues = previousValues;
            this.contextRegistry = contextRegistry;
        }

        public void close() {
            for (ThreadLocalAccessor accessor : this.contextRegistry.getThreadLocalAccessors()) {
                if (!this.previousValues.containsKey(accessor.key())) continue;
                Object previousValue = this.previousValues.get(accessor.key());
                this.resetThreadLocalValue(accessor, previousValue);
            }
        }

        private <V> void resetThreadLocalValue(ThreadLocalAccessor<?> accessor, @Nullable V previousValue) {
            if (previousValue != null) {
                accessor.restore(previousValue);
            } else {
                accessor.reset();
            }
        }

        public static ContextSnapshot.Scope from(@Nullable Map<Object, Object> previousValues, ContextRegistry registry) {
            return previousValues != null ? new ReactorScopeImpl(previousValues, registry) : () -> {};
        }
    }

    static final class ContextCaptureNoPredicate
    implements Function<Context, Context> {
        final ContextRegistry globalRegistry;

        ContextCaptureNoPredicate(ContextRegistry globalRegistry) {
            this.globalRegistry = globalRegistry;
        }

        @Override
        public Context apply(Context context) {
            return (Context)ContextSnapshot.captureAllUsing(PREDICATE_TRUE, (ContextRegistry)this.globalRegistry, (Object[])new Object[0]).updateContext((Object)context);
        }
    }

    static class Envelope<T> {
        final T body;
        final ContextSnapshot contextSnapshot;

        Envelope(T body2, ContextSnapshot contextSnapshot) {
            this.body = body2;
            this.contextSnapshot = contextSnapshot;
        }
    }

    static final class ContextQueue<T>
    extends AbstractQueue<T> {
        static final String NOT_SUPPORTED_MESSAGE = "ContextQueue wrapper is intended for use with instances returned by Queues class. Iterator based methods are usually unsupported.";
        final Queue<Envelope<T>> envelopeQueue;
        boolean cleanOnNull;
        boolean hasPrevious = false;
        Thread lastReader;
        ContextSnapshot.Scope scope;

        ContextQueue(Queue<?> queue) {
            this.envelopeQueue = queue;
        }

        @Override
        public int size() {
            return this.envelopeQueue.size();
        }

        @Override
        public boolean offer(T o) {
            ContextSnapshot contextSnapshot = ContextSnapshot.captureAll((Object[])new Object[0]);
            return this.envelopeQueue.offer(new Envelope<T>(o, contextSnapshot));
        }

        @Override
        public T poll() {
            Envelope<T> envelope = this.envelopeQueue.poll();
            if (envelope == null) {
                if (this.cleanOnNull && this.scope != null) {
                    this.scope.close();
                }
                this.cleanOnNull = true;
                this.lastReader = Thread.currentThread();
                this.hasPrevious = false;
                return null;
            }
            this.restoreTheContext(envelope);
            this.hasPrevious = true;
            return envelope.body;
        }

        private void restoreTheContext(Envelope<T> envelope) {
            ContextSnapshot contextSnapshot = envelope.contextSnapshot;
            ContextSnapshot currentContextSnapshot = ContextSnapshot.captureAll((Object[])new Object[0]);
            if (!contextSnapshot.equals(currentContextSnapshot)) {
                if (!this.hasPrevious || !Thread.currentThread().equals(this.lastReader)) {
                    this.cleanOnNull = true;
                    this.lastReader = Thread.currentThread();
                }
                this.scope = contextSnapshot.setThreadLocals();
            } else if (!this.hasPrevious || !Thread.currentThread().equals(this.lastReader)) {
                this.cleanOnNull = false;
                this.lastReader = Thread.currentThread();
            }
        }

        @Override
        @Nullable
        public T peek() {
            Envelope<T> envelope = this.envelopeQueue.peek();
            return envelope == null ? null : (T)envelope.body;
        }

        @Override
        public Iterator<T> iterator() {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }
    }

    static final class ContextRestoreSignalListener<T>
    implements SignalListener<T> {
        final SignalListener<T> original;
        final ContextView context;
        final ContextRegistry registry;

        public ContextRestoreSignalListener(SignalListener<T> original, ContextView context, @Nullable ContextRegistry registry) {
            this.original = original;
            this.context = context;
            this.registry = registry == null ? ContextRegistry.getInstance() : registry;
        }

        ContextSnapshot.Scope restoreThreadLocals() {
            return ContextSnapshot.setAllThreadLocalsFrom((Object)this.context, (ContextRegistry)this.registry);
        }

        @Override
        public void doFirst() throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doFirst();
            }
        }

        @Override
        public void doFinally(SignalType terminationType) throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doFinally(terminationType);
            }
        }

        @Override
        public void doOnSubscription() throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnSubscription();
            }
        }

        @Override
        public void doOnFusion(int negotiatedFusion) throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnFusion(negotiatedFusion);
            }
        }

        @Override
        public void doOnRequest(long requested) throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnRequest(requested);
            }
        }

        @Override
        public void doOnCancel() throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnCancel();
            }
        }

        @Override
        public void doOnNext(T value) throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnNext(value);
            }
        }

        @Override
        public void doOnComplete() throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnComplete();
            }
        }

        @Override
        public void doOnError(Throwable error) throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnError(error);
            }
        }

        @Override
        public void doAfterComplete() throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doAfterComplete();
            }
        }

        @Override
        public void doAfterError(Throwable error) throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doAfterError(error);
            }
        }

        @Override
        public void doOnMalformedOnNext(T value) throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnMalformedOnNext(value);
            }
        }

        @Override
        public void doOnMalformedOnError(Throwable error) throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnMalformedOnError(error);
            }
        }

        @Override
        public void doOnMalformedOnComplete() throws Throwable {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.doOnMalformedOnComplete();
            }
        }

        @Override
        public void handleListenerError(Throwable listenerError) {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                this.original.handleListenerError(listenerError);
            }
        }

        @Override
        public Context addToContext(Context originalContext) {
            try (ContextSnapshot.Scope ignored = this.restoreThreadLocals();){
                Context context = this.original.addToContext(originalContext);
                return context;
            }
        }
    }
}

