/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.EncounterImpl;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.MethodAspect;
import com.google.inject.internal.ProvisionListenerStackCallback;
import com.google.inject.internal.SingleMemberInjector;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.InjectionPoint;
import javax.annotation.Nullable;

final class MembersInjectorImpl<T>
implements MembersInjector<T> {
    private final TypeLiteral<T> typeLiteral;
    private final InjectorImpl injector;
    @Nullable
    private final ImmutableList<SingleMemberInjector> memberInjectors;
    @Nullable
    private final ImmutableList<MembersInjector<? super T>> userMembersInjectors;
    @Nullable
    private final ImmutableList<InjectionListener<? super T>> injectionListeners;
    @Nullable
    private final ImmutableList<MethodAspect> addedAspects;

    MembersInjectorImpl(InjectorImpl injector, TypeLiteral<T> typeLiteral, EncounterImpl<T> encounter, ImmutableList<SingleMemberInjector> memberInjectors) {
        this.injector = injector;
        this.typeLiteral = typeLiteral;
        this.memberInjectors = memberInjectors.isEmpty() ? null : memberInjectors;
        this.userMembersInjectors = encounter.getMembersInjectors().isEmpty() ? null : encounter.getMembersInjectors().asList();
        this.injectionListeners = encounter.getInjectionListeners().isEmpty() ? null : encounter.getInjectionListeners().asList();
        this.addedAspects = InternalFlags.isBytecodeGenEnabled() && !encounter.getAspects().isEmpty() ? encounter.getAspects() : null;
    }

    public ImmutableList<SingleMemberInjector> getMemberInjectors() {
        return this.memberInjectors == null ? ImmutableList.of() : this.memberInjectors;
    }

    @Override
    public void injectMembers(T instance) {
        TypeLiteral<T> localTypeLiteral = this.typeLiteral;
        try {
            this.injectAndNotify(instance, null, null, localTypeLiteral, false);
        }
        catch (InternalProvisionException ipe) {
            throw ipe.addSource(localTypeLiteral).toProvisionException();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void injectAndNotify(final T instance, Key<T> key, ProvisionListenerStackCallback<T> provisionCallback, Object source2, final boolean toolableOnly) throws InternalProvisionException {
        if (instance == null) {
            return;
        }
        try (final InternalContext context = this.injector.enterContext();){
            if (provisionCallback != null && provisionCallback.hasListeners()) {
                provisionCallback.provision(context, new ProvisionListenerStackCallback.ProvisionCallback<T>(){

                    @Override
                    public T call() throws InternalProvisionException {
                        MembersInjectorImpl.this.injectMembers(instance, context, toolableOnly);
                        return instance;
                    }
                });
            } else {
                this.injectMembers(instance, context, toolableOnly);
            }
        }
        if (!toolableOnly) {
            this.notifyListeners(instance);
        }
    }

    void notifyListeners(T instance) throws InternalProvisionException {
        ImmutableList<InjectionListener<T>> localInjectionListeners = this.injectionListeners;
        if (localInjectionListeners == null) {
            return;
        }
        for (int i = 0; i < localInjectionListeners.size(); ++i) {
            InjectionListener injectionListener = (InjectionListener)localInjectionListeners.get(i);
            try {
                injectionListener.afterInjection(instance);
                continue;
            }
            catch (RuntimeException e) {
                throw InternalProvisionException.errorNotifyingInjectionListener(injectionListener, this.typeLiteral, e);
            }
        }
    }

    void injectMembers(T t2, InternalContext context, boolean toolableOnly) throws InternalProvisionException {
        ImmutableList<MembersInjector<T>> localUsersMembersInjectors;
        ImmutableList<SingleMemberInjector> localMembersInjectors = this.memberInjectors;
        if (localMembersInjectors != null) {
            int size = localMembersInjectors.size();
            for (int i = 0; i < size; ++i) {
                SingleMemberInjector injector = (SingleMemberInjector)localMembersInjectors.get(i);
                if (toolableOnly && !injector.getInjectionPoint().isToolable()) continue;
                injector.inject(context, t2);
            }
        }
        if (!toolableOnly && (localUsersMembersInjectors = this.userMembersInjectors) != null) {
            for (int i = 0; i < localUsersMembersInjectors.size(); ++i) {
                MembersInjector userMembersInjector = (MembersInjector)localUsersMembersInjectors.get(i);
                try {
                    userMembersInjector.injectMembers(t2);
                    continue;
                }
                catch (RuntimeException e) {
                    throw InternalProvisionException.errorInUserInjector(userMembersInjector, this.typeLiteral, e);
                }
            }
        }
    }

    public String toString() {
        return "MembersInjector<" + this.typeLiteral + ">";
    }

    public ImmutableSet<InjectionPoint> getInjectionPoints() {
        ImmutableList<SingleMemberInjector> localMemberInjectors = this.memberInjectors;
        if (localMemberInjectors != null) {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            for (SingleMemberInjector memberInjector : localMemberInjectors) {
                builder.add(memberInjector.getInjectionPoint());
            }
            return builder.build();
        }
        return ImmutableSet.of();
    }

    public ImmutableList<MethodAspect> getAddedAspects() {
        return this.addedAspects == null ? ImmutableList.of() : this.addedAspects;
    }
}

