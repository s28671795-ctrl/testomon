/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.ProvisionException;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.internal.ConstructionContext;
import com.google.inject.internal.CycleDetectingLock;
import com.google.inject.internal.Errors;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.ProviderToInternalFactoryAdapter;
import com.google.inject.spi.Message;
import java.util.Collection;
import java.util.Formatter;
import javax.annotation.Nullable;

public class SingletonScope
implements Scope {
    private static final Object NULL = new Object();
    private static final CycleDetectingLock.CycleDetectingLockFactory<Key<?>> cycleDetectingLockFactory = new CycleDetectingLock.CycleDetectingLockFactory();

    @Override
    public <T> Provider<T> scope(final Key<T> key, final Provider<T> creator) {
        return new Provider<T>(this){
            volatile Object instance;
            final ConstructionContext<T> constructionContext = new ConstructionContext();
            final CycleDetectingLock<Key<?>> creationLock = SingletonScope.access$000().create(key);
            @Nullable
            final InjectorImpl injector = creator instanceof ProviderToInternalFactoryAdapter ? ((ProviderToInternalFactoryAdapter)creator).getInjector() : null;

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             * Unable to fully structure code
             */
            @Override
            public T get() {
                initialInstance = this.instance;
                if (initialInstance == null) {
                    context = this.injector == null ? null : this.injector.getLocalContext();
                    locksCycle = this.creationLock.lockOrDetectPotentialLocksCycle();
                    if (locksCycle.isEmpty()) {
                        try {
                            if (this.instance != null) ** GOTO lbl54
                            provided = creator.get();
                            v0 = providedNotNull = provided == null ? SingletonScope.access$100() : provided;
                            if (this.instance == null) {
                                if (Scopes.isCircularProxy(provided)) {
                                    var6_9 = provided;
                                    return var6_9;
                                }
                                var6_10 = this.constructionContext;
                                synchronized (var6_10) {
                                    this.instance = providedNotNull;
                                    this.constructionContext.setProxyDelegates(provided);
                                }
                            }
                            Preconditions.checkState(this.instance == providedNotNull, "Singleton is called recursively returning different results");
                        }
                        catch (RuntimeException e) {
                            providedNotNull = this.constructionContext;
                            synchronized (providedNotNull) {
                                this.constructionContext.finishConstruction();
                            }
                            throw e;
                        }
                        finally {
                            this.creationLock.unlock();
                        }
                    } else {
                        if (context == null) {
                            throw new ProvisionException(ImmutableList.of(this.createCycleDependenciesMessage(locksCycle, null)));
                        }
                        e = this.constructionContext;
                        synchronized (e) {
                            if (this.instance == null) {
                                dependency = Preconditions.checkNotNull(context.getDependency(), "internalContext.getDependency()");
                                rawType = dependency.getKey().getTypeLiteral().getRawType();
                                try {
                                    proxy = this.constructionContext.createProxy(context.getInjectorOptions(), rawType);
                                    return (T)proxy;
                                }
                                catch (InternalProvisionException e) {
                                    proxyCreationError = Iterables.getOnlyElement(e.getErrors());
                                    cycleDependenciesMessage = this.createCycleDependenciesMessage(locksCycle, proxyCreationError);
                                    throw new ProvisionException(ImmutableList.of(cycleDependenciesMessage, proxyCreationError));
                                }
                            }
                        }
                    }
lbl54:
                    // 4 sources

                    initializedInstance = this.instance;
                    Preconditions.checkState(initializedInstance != null, "Internal error: Singleton is not initialized contrary to our expectations");
                    initializedTypedInstance = initializedInstance;
                    return (T)(initializedInstance == SingletonScope.access$100() ? null : initializedTypedInstance);
                }
                typedInitialIntance = initialInstance;
                return (T)(initialInstance == SingletonScope.access$100() ? null : typedInitialIntance);
            }

            private Message createCycleDependenciesMessage(ListMultimap<Thread, Key<?>> locksCycle, @Nullable Message proxyCreationError) {
                StringBuilder sb = new StringBuilder();
                Formatter fmt = new Formatter(sb);
                fmt.format("Encountered circular dependency spanning several threads.", new Object[0]);
                if (proxyCreationError != null) {
                    fmt.format(" %s", proxyCreationError.getMessage());
                }
                fmt.format("\n", new Object[0]);
                for (Thread lockedThread : locksCycle.keySet()) {
                    Collection lockedKeys = locksCycle.get((Object)lockedThread);
                    fmt.format("%s is holding locks the following singletons in the cycle:\n", lockedThread);
                    for (Key lockedKey : lockedKeys) {
                        fmt.format("%s\n", Errors.convert(lockedKey));
                    }
                    for (StackTraceElement traceElement : lockedThread.getStackTrace()) {
                        fmt.format("\tat %s\n", traceElement);
                    }
                }
                fmt.close();
                return new Message(Thread.currentThread(), sb.toString());
            }

            public String toString() {
                return String.format("%s[%s]", creator, Scopes.SINGLETON);
            }
        };
    }

    @Override
    public String toString() {
        return "Scopes.SINGLETON";
    }

    static /* synthetic */ CycleDetectingLock.CycleDetectingLockFactory access$000() {
        return cycleDetectingLockFactory;
    }

    static /* synthetic */ Object access$100() {
        return NULL;
    }
}

