/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.CycleDetectingLock;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.Initializable;
import com.google.inject.internal.Initializables;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.MembersInjectorImpl;
import com.google.inject.internal.ProvisionListenerStackCallback;
import com.google.inject.spi.InjectionPoint;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

final class Initializer {
    private volatile boolean validationStarted = false;
    private final CycleDetectingLock.CycleDetectingLockFactory<Class<?>> cycleDetectingLockFactory = new CycleDetectingLock.CycleDetectingLockFactory();
    private final List<InjectableReference<?>> pendingInjections = Lists.newArrayList();
    private final IdentityHashMap<Object, InjectableReference<?>> initializablesCache = Maps.newIdentityHashMap();

    Initializer() {
    }

    <T> Initializable<T> requestInjection(InjectorImpl injector, TypeLiteral<T> type, T instance, Binding<T> binding, Object source2, Set<InjectionPoint> injectionPoints, Errors errors2) {
        ProvisionListenerStackCallback<T> provisionCallback;
        Preconditions.checkNotNull(source2);
        Preconditions.checkState(!this.validationStarted, "Member injection could not be requested after validation is started");
        ProvisionListenerStackCallback<T> provisionListenerStackCallback = provisionCallback = binding == null ? null : injector.provisionListenerStore.get(binding);
        if (instance == null || injectionPoints.isEmpty() && !injector.membersInjectorStore.hasTypeListeners() && provisionCallback == null) {
            return Initializables.of(instance);
        }
        if (type == null) {
            TypeLiteral<?> instanceType = TypeLiteral.get(instance.getClass());
            type = instanceType;
        }
        if (this.initializablesCache.containsKey(instance)) {
            InjectableReference<?> cached = this.initializablesCache.get(instance);
            if (!((InjectableReference)cached).type.equals(type)) {
                errors2.requestInjectionWithDifferentTypes(instance, ((InjectableReference)cached).type, ((InjectableReference)cached).source, type);
            }
            return cached;
        }
        InjectableReference<T> injectableReference = new InjectableReference<T>(injector, instance, type, binding == null ? null : binding.getKey(), provisionCallback, source2, this.cycleDetectingLockFactory.create(instance.getClass()));
        this.initializablesCache.put(instance, injectableReference);
        this.pendingInjections.add(injectableReference);
        return injectableReference;
    }

    void validateOustandingInjections(Errors errors2) {
        this.validationStarted = true;
        this.initializablesCache.clear();
        for (InjectableReference<?> reference : this.pendingInjections) {
            try {
                reference.validate(errors2);
            }
            catch (ErrorsException e) {
                errors2.merge(e.getErrors());
            }
        }
    }

    void injectAll(Errors errors2) {
        Preconditions.checkState(this.validationStarted, "Validation should be done before injection");
        for (InjectableReference<?> reference : this.pendingInjections) {
            try {
                reference.get();
            }
            catch (InternalProvisionException ipe) {
                errors2.merge(ipe);
            }
        }
        this.pendingInjections.clear();
    }

    private static class InjectableReference<T>
    implements Initializable<T> {
        private volatile InjectableReferenceState state = InjectableReferenceState.NEW;
        private volatile MembersInjectorImpl<T> membersInjector = null;
        private final InjectorImpl injector;
        private final T instance;
        private final TypeLiteral<T> type;
        private final Object source;
        private final Key<T> key;
        private final ProvisionListenerStackCallback<T> provisionCallback;
        private final CycleDetectingLock<?> lock;

        public InjectableReference(InjectorImpl injector, T instance, TypeLiteral<T> type, Key<T> key, ProvisionListenerStackCallback<T> provisionCallback, Object source2, CycleDetectingLock<?> lock) {
            this.injector = injector;
            this.key = key;
            this.provisionCallback = provisionCallback;
            this.instance = Preconditions.checkNotNull(instance, "instance");
            this.type = Preconditions.checkNotNull(type, "type");
            this.source = Preconditions.checkNotNull(source2, "source");
            this.lock = Preconditions.checkNotNull(lock, "lock");
        }

        public void validate(Errors errors2) throws ErrorsException {
            this.membersInjector = this.injector.membersInjectorStore.get(this.type, errors2.withSource(this.source));
            Preconditions.checkNotNull(this.membersInjector, "No membersInjector available for type: %s, from key: %s", this.type, this.key);
            this.state = InjectableReferenceState.VALIDATED;
        }

        @Override
        public T get() throws InternalProvisionException {
            if (this.state == InjectableReferenceState.READY) {
                return this.instance;
            }
            ListMultimap<Thread, ?> lockCycle = this.lock.lockOrDetectPotentialLocksCycle();
            if (!lockCycle.isEmpty()) {
                return this.instance;
            }
            try {
                switch (this.state) {
                    case READY: {
                        T t2 = this.instance;
                        return t2;
                    }
                    case INJECTING: {
                        T t3 = this.instance;
                        return t3;
                    }
                    case VALIDATED: {
                        this.state = InjectableReferenceState.INJECTING;
                        break;
                    }
                    case NEW: {
                        throw new IllegalStateException("InjectableReference is not validated yet");
                    }
                    default: {
                        throw new IllegalStateException("Unknown state: " + (Object)((Object)this.state));
                    }
                }
                try {
                    this.membersInjector.injectAndNotify(this.instance, this.key, this.provisionCallback, this.source, this.injector.options.stage == Stage.TOOL);
                }
                catch (InternalProvisionException ipe) {
                    throw ipe.addSource(this.source);
                }
                this.state = InjectableReferenceState.READY;
                T t4 = this.instance;
                return t4;
            }
            finally {
                this.lock.unlock();
            }
        }

        public String toString() {
            return this.instance.toString();
        }
    }

    private static enum InjectableReferenceState {
        NEW,
        VALIDATED,
        INJECTING,
        READY;

    }
}

