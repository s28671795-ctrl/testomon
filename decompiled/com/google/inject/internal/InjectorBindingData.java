/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.Errors;
import com.google.inject.spi.InjectionRequest;
import com.google.inject.spi.InterceptorBinding;
import com.google.inject.spi.MembersInjectorLookup;
import com.google.inject.spi.ModuleAnnotatedMethodScannerBinding;
import com.google.inject.spi.ProviderLookup;
import com.google.inject.spi.ProvisionListenerBinding;
import com.google.inject.spi.ScopeBinding;
import com.google.inject.spi.StaticInjectionRequest;
import com.google.inject.spi.TypeConverterBinding;
import com.google.inject.spi.TypeListenerBinding;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class InjectorBindingData {
    private final Optional<InjectorBindingData> parent;
    private final Map<Key<?>, Binding<?>> explicitBindingsMutable = Maps.newLinkedHashMap();
    private final Map<Key<?>, Binding<?>> explicitBindings = Collections.unmodifiableMap(this.explicitBindingsMutable);
    private final Map<Class<? extends Annotation>, ScopeBinding> scopes = Maps.newHashMap();
    private final Set<ProviderLookup<?>> providerLookups = Sets.newLinkedHashSet();
    private final Set<StaticInjectionRequest> staticInjectionRequests = Sets.newLinkedHashSet();
    private final Set<MembersInjectorLookup<?>> membersInjectorLookups = Sets.newLinkedHashSet();
    private final Set<InjectionRequest<?>> injectionRequests = Sets.newLinkedHashSet();
    private final List<TypeConverterBinding> converters = Lists.newArrayList();
    private final List<InterceptorBinding> interceptorBindings = Lists.newArrayList();
    private final List<TypeListenerBinding> typeListenerBindings = Lists.newArrayList();
    private final List<ProvisionListenerBinding> provisionListenerBindings = Lists.newArrayList();
    private final List<ModuleAnnotatedMethodScannerBinding> scannerBindings = Lists.newArrayList();
    private final ListMultimap<TypeLiteral<?>, Binding<?>> indexedExplicitBindings = ArrayListMultimap.create();

    InjectorBindingData(Optional<InjectorBindingData> parent) {
        this.parent = parent;
    }

    public Optional<InjectorBindingData> parent() {
        return this.parent;
    }

    public <T> BindingImpl<T> getExplicitBinding(Key<T> key) {
        Binding<?> binding = this.explicitBindings.get(key);
        if (binding == null && this.parent.isPresent()) {
            return this.parent.get().getExplicitBinding(key);
        }
        return (BindingImpl)binding;
    }

    public Map<Key<?>, Binding<?>> getExplicitBindingsThisLevel() {
        return this.explicitBindings;
    }

    public void putBinding(Key<?> key, BindingImpl<?> binding) {
        this.explicitBindingsMutable.put(key, binding);
    }

    public void putProviderLookup(ProviderLookup<?> lookup) {
        this.providerLookups.add(lookup);
    }

    public Set<ProviderLookup<?>> getProviderLookupsThisLevel() {
        return this.providerLookups;
    }

    public void putStaticInjectionRequest(StaticInjectionRequest staticInjectionRequest) {
        this.staticInjectionRequests.add(staticInjectionRequest);
    }

    public Set<StaticInjectionRequest> getStaticInjectionRequestsThisLevel() {
        return this.staticInjectionRequests;
    }

    public void putInjectionRequest(InjectionRequest<?> injectionRequest) {
        this.injectionRequests.add(injectionRequest);
    }

    public Set<InjectionRequest<?>> getInjectionRequestsThisLevel() {
        return this.injectionRequests;
    }

    public void putMembersInjectorLookup(MembersInjectorLookup<?> membersInjectorLookup) {
        this.membersInjectorLookups.add(membersInjectorLookup);
    }

    public Set<MembersInjectorLookup<?>> getMembersInjectorLookupsThisLevel() {
        return this.membersInjectorLookups;
    }

    public ScopeBinding getScopeBinding(Class<? extends Annotation> annotationType) {
        ScopeBinding scopeBinding = this.scopes.get(annotationType);
        if (scopeBinding == null && this.parent.isPresent()) {
            return this.parent.get().getScopeBinding(annotationType);
        }
        return scopeBinding;
    }

    public void putScopeBinding(Class<? extends Annotation> annotationType, ScopeBinding scope) {
        this.scopes.put(annotationType, scope);
    }

    public Collection<ScopeBinding> getScopeBindingsThisLevel() {
        return this.scopes.values();
    }

    public Iterable<TypeConverterBinding> getConvertersThisLevel() {
        return this.converters;
    }

    public void addConverter(TypeConverterBinding typeConverterBinding) {
        this.converters.add(typeConverterBinding);
    }

    public TypeConverterBinding getConverter(String stringValue, TypeLiteral<?> type, Errors errors2, Object source2) {
        TypeConverterBinding matchingConverter = null;
        InjectorBindingData b = this;
        while (b != null) {
            for (TypeConverterBinding converter : b.getConvertersThisLevel()) {
                if (!converter.getTypeMatcher().matches(type)) continue;
                if (matchingConverter != null) {
                    errors2.ambiguousTypeConversion(stringValue, source2, type, matchingConverter, converter);
                }
                matchingConverter = converter;
            }
            b = b.parent().orElse(null);
        }
        return matchingConverter;
    }

    public void addInterceptorBinding(InterceptorBinding interceptorBinding) {
        this.interceptorBindings.add(interceptorBinding);
    }

    public ImmutableList<InterceptorBinding> getInterceptorBindings() {
        if (this.parent.isPresent()) {
            return ((ImmutableList.Builder)((ImmutableList.Builder)new ImmutableList.Builder().addAll(this.parent.get().getInterceptorBindings())).addAll(this.interceptorBindings)).build();
        }
        return ImmutableList.copyOf(this.interceptorBindings);
    }

    public ImmutableList<InterceptorBinding> getInterceptorBindingsThisLevel() {
        return ImmutableList.copyOf(this.interceptorBindings);
    }

    public void addTypeListener(TypeListenerBinding listenerBinding) {
        this.typeListenerBindings.add(listenerBinding);
    }

    public ImmutableList<TypeListenerBinding> getTypeListenerBindings() {
        if (this.parent.isPresent()) {
            return ((ImmutableList.Builder)((ImmutableList.Builder)new ImmutableList.Builder().addAll(this.parent.get().getTypeListenerBindings())).addAll(this.typeListenerBindings)).build();
        }
        return ImmutableList.copyOf(this.typeListenerBindings);
    }

    public ImmutableList<TypeListenerBinding> getTypeListenerBindingsThisLevel() {
        return ImmutableList.copyOf(this.typeListenerBindings);
    }

    public void addProvisionListener(ProvisionListenerBinding listenerBinding) {
        this.provisionListenerBindings.add(listenerBinding);
    }

    public ImmutableList<ProvisionListenerBinding> getProvisionListenerBindings() {
        if (this.parent.isPresent()) {
            return ((ImmutableList.Builder)((ImmutableList.Builder)new ImmutableList.Builder().addAll(this.parent.get().getProvisionListenerBindings())).addAll(this.provisionListenerBindings)).build();
        }
        return ImmutableList.copyOf(this.provisionListenerBindings);
    }

    public ImmutableList<ProvisionListenerBinding> getProvisionListenerBindingsThisLevel() {
        return ImmutableList.copyOf(this.provisionListenerBindings);
    }

    public void addScanner(ModuleAnnotatedMethodScannerBinding scanner) {
        this.scannerBindings.add(scanner);
    }

    public ImmutableList<ModuleAnnotatedMethodScannerBinding> getScannerBindings() {
        if (this.parent.isPresent()) {
            return ((ImmutableList.Builder)((ImmutableList.Builder)new ImmutableList.Builder().addAll(this.parent.get().getScannerBindings())).addAll(this.scannerBindings)).build();
        }
        return ImmutableList.copyOf(this.scannerBindings);
    }

    public ImmutableList<ModuleAnnotatedMethodScannerBinding> getScannerBindingsThisLevel() {
        return ImmutableList.copyOf(this.scannerBindings);
    }

    public Map<Class<? extends Annotation>, Scope> getScopes() {
        ImmutableMap.Builder<Class<? extends Annotation>, Scope> builder = ImmutableMap.builder();
        for (Map.Entry<Class<? extends Annotation>, ScopeBinding> entry : this.scopes.entrySet()) {
            builder.put(entry.getKey(), entry.getValue().getScope());
        }
        return builder.buildOrThrow();
    }

    void indexBindingsByType() {
        for (Binding<?> binding : this.getExplicitBindingsThisLevel().values()) {
            this.indexedExplicitBindings.put(binding.getKey().getTypeLiteral(), binding);
        }
    }

    public ListMultimap<TypeLiteral<?>, Binding<?>> getIndexedExplicitBindings() {
        return this.indexedExplicitBindings;
    }
}

