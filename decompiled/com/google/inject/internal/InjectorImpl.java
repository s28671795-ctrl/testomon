/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.ConfigurationException;
import com.google.inject.ImplementedBy;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.ProvidedBy;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.Annotations;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.ConstantFactory;
import com.google.inject.internal.ConstructorBindingImpl;
import com.google.inject.internal.ConstructorInjectorStore;
import com.google.inject.internal.CreationListener;
import com.google.inject.internal.DeferredLookups;
import com.google.inject.internal.DelayedInitialize;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.FactoryProxy;
import com.google.inject.internal.Initializables;
import com.google.inject.internal.InjectorBindingData;
import com.google.inject.internal.InjectorJitBindingData;
import com.google.inject.internal.InstanceBindingImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalInjectorCreator;
import com.google.inject.internal.LinkedBindingImpl;
import com.google.inject.internal.LinkedProviderBindingImpl;
import com.google.inject.internal.Lookups;
import com.google.inject.internal.MembersInjectorImpl;
import com.google.inject.internal.MembersInjectorStore;
import com.google.inject.internal.MoreTypes;
import com.google.inject.internal.ProvidedByInternalFactory;
import com.google.inject.internal.ProvisionListenerCallbackStore;
import com.google.inject.internal.Scoping;
import com.google.inject.internal.SingleParameterInjector;
import com.google.inject.internal.util.SourceProvider;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.ConvertedConstantBinding;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Element;
import com.google.inject.spi.HasDependencies;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.InstanceBinding;
import com.google.inject.spi.ProviderBinding;
import com.google.inject.spi.TypeConverterBinding;
import com.google.inject.util.Providers;
import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

final class InjectorImpl
implements Injector,
Lookups {
    public static final TypeLiteral<String> STRING_TYPE = TypeLiteral.get(String.class);
    private final InjectorBindingData bindingData;
    private final InjectorJitBindingData jitBindingData;
    final InjectorImpl parent;
    final InjectorOptions options;
    Lookups lookups = new DeferredLookups(this);
    final Set<TypeLiteral<?>> userRequestedMembersInjectorTypes = Sets.newConcurrentHashSet();
    final ConstructorInjectorStore constructors = new ConstructorInjectorStore(this);
    MembersInjectorStore membersInjectorStore;
    ProvisionListenerCallbackStore provisionListenerStore;
    private final ThreadLocal<Object[]> localContext;

    InjectorImpl(InjectorImpl parent, InjectorBindingData bindingData, InjectorJitBindingData jitBindingData, InjectorOptions injectorOptions) {
        this.parent = parent;
        this.bindingData = bindingData;
        this.jitBindingData = jitBindingData;
        this.options = injectorOptions;
        this.localContext = parent != null ? parent.localContext : new ThreadLocal();
    }

    @Override
    public <T> List<Binding<T>> findBindingsByType(TypeLiteral<T> type) {
        Collection list = this.bindingData.getIndexedExplicitBindings().get((Object)Preconditions.checkNotNull(type, "type"));
        return Collections.unmodifiableList(list);
    }

    public <T> BindingImpl<T> getBinding(Key<T> key) {
        Errors errors2 = new Errors(Preconditions.checkNotNull(key, "key"));
        try {
            BindingImpl<T> result = this.getBindingOrThrow(key, errors2, JitLimitation.EXISTING_JIT);
            errors2.throwConfigurationExceptionIfErrorsExist();
            return result;
        }
        catch (ErrorsException e) {
            ConfigurationException exception = new ConfigurationException(errors2.merge(e.getErrors()).getMessages());
            throw exception;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <T> BindingImpl<T> getExistingBinding(Key<T> key) {
        BindingImpl<T> explicitBinding = this.bindingData.getExplicitBinding(Preconditions.checkNotNull(key, "key"));
        if (explicitBinding != null) {
            return explicitBinding;
        }
        Object object = this.jitBindingData.lock();
        synchronized (object) {
            InjectorImpl injector = this;
            while (injector != null) {
                BindingImpl<?> jitBinding = injector.jitBindingData.getJitBinding(key);
                if (jitBinding != null) {
                    return jitBinding;
                }
                injector = injector.parent;
            }
        }
        if (InjectorImpl.isProvider(key)) {
            try {
                Key<T> providedKey = InjectorImpl.getProvidedKey(key, new Errors());
                if (this.getExistingBinding((Key)providedKey) != null) {
                    return this.getBinding((Key)key);
                }
            }
            catch (ErrorsException e) {
                ConfigurationException exception = new ConfigurationException(e.getErrors().getMessages());
                throw exception;
            }
        }
        return null;
    }

    <T> BindingImpl<T> getBindingOrThrow(Key<T> key, Errors errors2, JitLimitation jitType) throws ErrorsException {
        BindingImpl<T> binding = this.bindingData.getExplicitBinding(key);
        if (binding != null) {
            return binding;
        }
        return this.getJustInTimeBinding(key, errors2, jitType);
    }

    @Override
    public <T> Binding<T> getBinding(Class<T> type) {
        return this.getBinding((Key)Key.get(Preconditions.checkNotNull(type, "type")));
    }

    @Override
    public Injector getParent() {
        return this.parent;
    }

    @Override
    public Injector createChildInjector(Iterable<? extends Module> modules) {
        return new InternalInjectorCreator().parentInjector(this).addModules(modules).build();
    }

    @Override
    public Injector createChildInjector(Module ... modules) {
        return this.createChildInjector(ImmutableList.copyOf(modules));
    }

    InjectorBindingData getBindingData() {
        return this.bindingData;
    }

    InjectorJitBindingData getJitBindingData() {
        return this.jitBindingData;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <T> BindingImpl<T> getJustInTimeBinding(Key<T> key, Errors errors2, JitLimitation jitType) throws ErrorsException {
        boolean jitOverride = InjectorImpl.isProvider(key) || InjectorImpl.isTypeLiteral(key) || InjectorImpl.isMembersInjector(key);
        Object object = this.jitBindingData.lock();
        synchronized (object) {
            InjectorImpl injector = this;
            while (injector != null) {
                BindingImpl<?> binding = injector.jitBindingData.getJitBinding(key);
                if (binding != null) {
                    if (this.options.jitDisabled && jitType == JitLimitation.NO_JIT && !jitOverride && !(binding instanceof ConvertedConstantBindingImpl)) {
                        throw errors2.jitDisabled(key).toException();
                    }
                    return binding;
                }
                injector = injector.parent;
            }
            if (this.jitBindingData.isFailedJitBinding(key) && errors2.hasErrors()) {
                throw errors2.toException();
            }
            return this.createJustInTimeBindingRecursive(key, errors2, this.options.jitDisabled, jitType);
        }
    }

    private static boolean isProvider(Key<?> key) {
        return key.getTypeLiteral().getRawType().equals(Provider.class);
    }

    private static boolean isTypeLiteral(Key<?> key) {
        return key.getTypeLiteral().getRawType().equals(TypeLiteral.class);
    }

    private static <T> Key<T> getProvidedKey(Key<Provider<T>> key, Errors errors2) throws ErrorsException {
        Type providerType = key.getTypeLiteral().getType();
        if (!(providerType instanceof ParameterizedType)) {
            throw errors2.cannotInjectRawProvider().toException();
        }
        Type entryType = ((ParameterizedType)providerType).getActualTypeArguments()[0];
        Key<?> providedKey = key.ofType(entryType);
        return providedKey;
    }

    private static boolean isMembersInjector(Key<?> key) {
        return key.getTypeLiteral().getRawType().equals(MembersInjector.class) && key.getAnnotationType() == null;
    }

    private <T> BindingImpl<MembersInjector<T>> createMembersInjectorBinding(Key<MembersInjector<T>> key, Errors errors2) throws ErrorsException {
        Type membersInjectorType = key.getTypeLiteral().getType();
        if (!(membersInjectorType instanceof ParameterizedType)) {
            throw errors2.cannotInjectRawMembersInjector().toException();
        }
        TypeLiteral<?> instanceType = TypeLiteral.get(((ParameterizedType)membersInjectorType).getActualTypeArguments()[0]);
        MembersInjectorImpl<?> membersInjector = this.membersInjectorStore.get(instanceType, errors2);
        ConstantFactory factory2 = new ConstantFactory(Initializables.of(membersInjector));
        return new InstanceBindingImpl<MembersInjector<T>>(this, key, SourceProvider.UNKNOWN_SOURCE, factory2, ImmutableSet.of(), membersInjector);
    }

    private <T> BindingImpl<Provider<T>> createSyntheticProviderBinding(Key<Provider<T>> key, Errors errors2) throws ErrorsException {
        Key<T> providedKey = InjectorImpl.getProvidedKey(key, errors2);
        BindingImpl<T> delegate = this.getBindingOrThrow(providedKey, errors2, JitLimitation.NO_JIT);
        return new SyntheticProviderBindingImpl<T>(this, key, delegate);
    }

    private <T> BindingImpl<T> convertConstantStringBinding(Key<T> key, Errors errors2) throws ErrorsException {
        Key<String> stringKey = key.ofType(STRING_TYPE);
        BindingImpl<String> stringBinding = this.bindingData.getExplicitBinding(stringKey);
        if (stringBinding == null || !stringBinding.isConstant()) {
            return null;
        }
        String stringValue = (String)((InstanceBinding)((Object)stringBinding)).getInstance();
        Object source2 = stringBinding.getSource();
        TypeLiteral<T> type = key.getTypeLiteral();
        TypeConverterBinding typeConverterBinding = this.bindingData.getConverter(stringValue, type, errors2, source2);
        if (typeConverterBinding == null) {
            return null;
        }
        try {
            Object converted = typeConverterBinding.getTypeConverter().convert(stringValue, type);
            if (converted == null) {
                throw errors2.converterReturnedNull(stringValue, source2, type, typeConverterBinding).toException();
            }
            if (!type.getRawType().isInstance(converted)) {
                throw errors2.conversionTypeError(stringValue, source2, type, typeConverterBinding, converted).toException();
            }
            return new ConvertedConstantBindingImpl<Object>(this, key, converted, stringBinding, typeConverterBinding);
        }
        catch (ErrorsException e) {
            throw e;
        }
        catch (RuntimeException e) {
            throw errors2.conversionError(stringValue, source2, type, typeConverterBinding, e).toException();
        }
    }

    <T> void initializeBinding(BindingImpl<T> binding, Errors errors2) throws ErrorsException {
        if (binding instanceof DelayedInitialize) {
            ((DelayedInitialize)((Object)binding)).initialize(this, errors2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    <T> void initializeJitBinding(BindingImpl<T> binding, Errors errors2) throws ErrorsException {
        if (binding instanceof DelayedInitialize) {
            Key<T> key = binding.getKey();
            this.jitBindingData.putJitBinding(key, binding);
            boolean successful = false;
            DelayedInitialize delayed = (DelayedInitialize)((Object)binding);
            try {
                delayed.initialize(this, errors2);
                successful = true;
            }
            finally {
                if (!successful) {
                    this.removeFailedJitBinding(binding, null);
                    this.cleanup(binding, new HashSet());
                }
            }
        }
    }

    private boolean cleanup(BindingImpl<?> binding, Set<Key<?>> encountered) {
        boolean bindingFailed = false;
        Set<Dependency<?>> deps = this.getInternalDependencies(binding);
        for (Dependency<?> dep : deps) {
            Key<?> depKey = dep.getKey();
            InjectionPoint ip2 = dep.getInjectionPoint();
            if (!encountered.add(depKey)) continue;
            BindingImpl<?> depBinding = this.jitBindingData.getJitBinding(depKey);
            if (depBinding != null) {
                boolean failed = this.cleanup(depBinding, encountered);
                if (depBinding instanceof ConstructorBindingImpl) {
                    ConstructorBindingImpl ctorBinding = (ConstructorBindingImpl)depBinding;
                    ip2 = ctorBinding.getInternalConstructor();
                    if (!ctorBinding.isInitialized()) {
                        failed = true;
                    }
                }
                if (!failed) continue;
                this.removeFailedJitBinding(depBinding, ip2);
                bindingFailed = true;
                continue;
            }
            if (this.bindingData.getExplicitBinding(depKey) != null) continue;
            bindingFailed = true;
        }
        return bindingFailed;
    }

    private void removeFailedJitBinding(Binding<?> binding, InjectionPoint ip2) {
        this.jitBindingData.addFailedJitBinding(binding.getKey());
        if (ip2 == null || !this.constructors.isLoading(ip2)) {
            this.jitBindingData.removeJitBinding(binding.getKey());
        }
        if (ip2 != null && !this.constructors.isLoading(ip2)) {
            this.constructors.remove(ip2);
        }
        this.membersInjectorStore.remove(binding.getKey().getTypeLiteral());
        this.provisionListenerStore.remove(binding);
    }

    private Set<Dependency<?>> getInternalDependencies(BindingImpl<?> binding) {
        if (binding instanceof ConstructorBindingImpl) {
            return ((ConstructorBindingImpl)binding).getInternalDependencies();
        }
        if (binding instanceof HasDependencies) {
            return ((HasDependencies)((Object)binding)).getDependencies();
        }
        return ImmutableSet.of();
    }

    <T> BindingImpl<T> createUninitializedBinding(Key<T> key, Scoping scoping, Object source2, Errors errors2, boolean jitBinding, Consumer<CreationListener> creationListenerCallback) throws ErrorsException {
        Class<T> rawType = key.getTypeLiteral().getRawType();
        ImplementedBy implementedBy = rawType.getAnnotation(ImplementedBy.class);
        if (rawType.isArray() || rawType.isEnum() && implementedBy != null) {
            throw errors2.missingImplementationWithHint(key, this).toException();
        }
        if (rawType == TypeLiteral.class) {
            BindingImpl<TypeLiteral<T>> binding = this.createTypeLiteralBinding(key, errors2);
            return binding;
        }
        if (implementedBy != null) {
            Annotations.checkForMisplacedScopeAnnotations(rawType, source2, errors2);
            return this.createImplementedByBinding(key, scoping, implementedBy, errors2, creationListenerCallback);
        }
        ProvidedBy providedBy = rawType.getAnnotation(ProvidedBy.class);
        if (providedBy != null) {
            Annotations.checkForMisplacedScopeAnnotations(rawType, source2, errors2);
            return this.createProvidedByBinding(key, scoping, providedBy, errors2);
        }
        return ConstructorBindingImpl.create(this, key, null, source2, scoping, errors2, jitBinding && this.options.jitDisabled, this.options.atInjectRequired);
    }

    private <T> BindingImpl<TypeLiteral<T>> createTypeLiteralBinding(Key<TypeLiteral<T>> key, Errors errors2) throws ErrorsException {
        Type typeLiteralType = key.getTypeLiteral().getType();
        if (!(typeLiteralType instanceof ParameterizedType)) {
            throw errors2.cannotInjectRawTypeLiteral().toException();
        }
        ParameterizedType parameterizedType = (ParameterizedType)typeLiteralType;
        Type innerType = parameterizedType.getActualTypeArguments()[0];
        if (!(innerType instanceof Class || innerType instanceof GenericArrayType || innerType instanceof ParameterizedType)) {
            throw errors2.cannotInjectTypeLiteralOf(innerType).toException();
        }
        TypeLiteral<?> value = TypeLiteral.get(innerType);
        ConstantFactory factory2 = new ConstantFactory(Initializables.of(value));
        return new InstanceBindingImpl<TypeLiteral<T>>(this, key, SourceProvider.UNKNOWN_SOURCE, factory2, ImmutableSet.of(), value);
    }

    <T> BindingImpl<T> createProvidedByBinding(Key<T> key, Scoping scoping, ProvidedBy providedBy, Errors errors2) throws ErrorsException {
        Class<T> rawType = key.getTypeLiteral().getRawType();
        Class<? extends jakarta.inject.Provider<?>> providerType = providedBy.value();
        if (providerType == rawType) {
            throw errors2.recursiveProviderType().toException();
        }
        if (!scoping.isExplicitlyScoped()) {
            int numErrorsBefore = errors2.size();
            Class<? extends Annotation> scopeAnnotation = Annotations.findScopeAnnotation(errors2, rawType);
            if (scopeAnnotation != null) {
                scoping = Scoping.makeInjectable(Scoping.forAnnotation(scopeAnnotation), this, errors2.withSource(rawType));
            }
            errors2.throwIfNewErrors(numErrorsBefore);
        }
        Key<? extends jakarta.inject.Provider<?>> providerKey = Key.get(providerType);
        ProvidedByInternalFactory<T> internalFactory = new ProvidedByInternalFactory<T>(rawType, providerType, providerKey);
        Class<T> source2 = rawType;
        LinkedProviderBindingImpl<T> binding = LinkedProviderBindingImpl.createWithInitializer(this, key, source2, Scoping.scope(key, this, internalFactory, source2, scoping), scoping, providerKey, internalFactory);
        internalFactory.setProvisionListenerCallback(this.provisionListenerStore.get(binding));
        return binding;
    }

    private <T> BindingImpl<T> createImplementedByBinding(Key<T> key, Scoping scoping, ImplementedBy implementedBy, Errors errors2, Consumer<CreationListener> creationListenerCallback) throws ErrorsException {
        Class<T> rawType = key.getTypeLiteral().getRawType();
        Class<?> implementationType = implementedBy.value();
        if (implementationType == rawType) {
            throw errors2.recursiveImplementationType().toException();
        }
        if (!rawType.isAssignableFrom(implementationType)) {
            throw errors2.notASubtype(implementationType, rawType).toException();
        }
        Class<?> subclass = implementationType;
        Key<?> targetKey = Key.get(subclass);
        Class<T> source2 = rawType;
        FactoryProxy<T> factory2 = new FactoryProxy<T>(this, key, targetKey, source2);
        creationListenerCallback.accept(factory2);
        return new LinkedBindingImpl<T>(this, key, source2, Scoping.scope(key, this, factory2, source2, scoping), scoping, targetKey);
    }

    private <T> BindingImpl<T> createJustInTimeBindingRecursive(Key<T> key, Errors errors2, boolean jitDisabled, JitLimitation jitType) throws ErrorsException {
        if (this.parent != null) {
            if (jitType == JitLimitation.NEW_OR_EXISTING_JIT && jitDisabled && !this.parent.options.jitDisabled) {
                throw errors2.jitDisabledInParent(key).toException();
            }
            try {
                return this.parent.createJustInTimeBindingRecursive(key, new Errors(), jitDisabled, this.parent.options.jitDisabled ? JitLimitation.NO_JIT : jitType);
            }
            catch (ErrorsException errorsException) {
                // empty catch block
            }
        }
        Set<Object> sources = this.jitBindingData.getSourcesForBannedKey(key);
        if (this.jitBindingData.isBannedKey(key)) {
            throw errors2.childBindingAlreadySet(key, sources).toException();
        }
        key = MoreTypes.canonicalizeKey(key);
        BindingImpl<T> binding = this.createJustInTimeBinding(key, errors2, jitDisabled, jitType);
        this.jitBindingData.banKeyInParent(key, this.bindingData, binding.getSource());
        this.jitBindingData.putJitBinding(key, binding);
        return binding;
    }

    private <T> BindingImpl<T> createJustInTimeBinding(Key<T> key, Errors errors2, boolean jitDisabled, JitLimitation jitType) throws ErrorsException {
        int numErrorsBefore = errors2.size();
        Set<Object> sources = this.jitBindingData.getSourcesForBannedKey(key);
        if (this.jitBindingData.isBannedKey(key)) {
            throw errors2.childBindingAlreadySet(key, sources).toException();
        }
        if (InjectorImpl.isProvider(key)) {
            BindingImpl<Provider<T>> binding = this.createSyntheticProviderBinding(key, errors2);
            return binding;
        }
        if (InjectorImpl.isMembersInjector(key)) {
            BindingImpl<MembersInjector<T>> binding = this.createMembersInjectorBinding(key, errors2);
            return binding;
        }
        BindingImpl<Provider<T>> convertedBinding = this.convertConstantStringBinding(key, errors2);
        if (convertedBinding != null) {
            return convertedBinding;
        }
        if (!InjectorImpl.isTypeLiteral(key) && jitDisabled && jitType != JitLimitation.NEW_OR_EXISTING_JIT) {
            throw errors2.jitDisabled(key).toException();
        }
        if (key.getAnnotationType() != null) {
            if (key.hasAttributes() && !this.options.exactBindingAnnotationsRequired) {
                try {
                    Errors ignored = new Errors();
                    return this.getBindingOrThrow(key.withoutAttributes(), ignored, JitLimitation.NO_JIT);
                }
                catch (ErrorsException ignored) {
                    // empty catch block
                }
            }
            throw errors2.missingImplementationWithHint(key, this).toException();
        }
        Class<Provider<T>> source2 = key.getTypeLiteral().getRawType();
        BindingImpl<Provider<T>> binding = this.createUninitializedBinding(key, Scoping.UNSCOPED, source2, errors2, true, creationListener -> creationListener.notify(errors2));
        errors2.throwIfNewErrors(numErrorsBefore);
        this.initializeJitBinding(binding, errors2);
        return binding;
    }

    <T> InternalFactory<? extends T> getInternalFactory(Key<T> key, Errors errors2, JitLimitation jitType) throws ErrorsException {
        return this.getBindingOrThrow(key, errors2, jitType).getInternalFactory();
    }

    @Override
    public Map<Key<?>, Binding<?>> getBindings() {
        return this.bindingData.getExplicitBindingsThisLevel();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<Key<?>, Binding<?>> getAllBindings() {
        Object object = this.jitBindingData.lock();
        synchronized (object) {
            return new ImmutableMap.Builder().putAll(this.bindingData.getExplicitBindingsThisLevel()).putAll(this.jitBindingData.getJitBindings()).buildOrThrow();
        }
    }

    @Override
    public Map<Class<? extends Annotation>, Scope> getScopeBindings() {
        return ImmutableMap.copyOf(this.bindingData.getScopes());
    }

    @Override
    public Set<TypeConverterBinding> getTypeConverterBindings() {
        return ImmutableSet.copyOf(this.bindingData.getConvertersThisLevel());
    }

    @Override
    public List<Element> getElements() {
        ImmutableList.Builder elements = ImmutableList.builder();
        elements.addAll(this.getAllBindings().values());
        elements.addAll(this.bindingData.getProviderLookupsThisLevel());
        elements.addAll(this.bindingData.getConvertersThisLevel());
        elements.addAll(this.bindingData.getScopeBindingsThisLevel());
        elements.addAll(this.bindingData.getTypeListenerBindingsThisLevel());
        elements.addAll(this.bindingData.getProvisionListenerBindingsThisLevel());
        elements.addAll(this.bindingData.getScannerBindingsThisLevel());
        elements.addAll(this.bindingData.getStaticInjectionRequestsThisLevel());
        elements.addAll(this.bindingData.getMembersInjectorLookupsThisLevel());
        elements.addAll(this.bindingData.getInjectionRequestsThisLevel());
        elements.addAll(this.bindingData.getInterceptorBindingsThisLevel());
        return elements.build();
    }

    @Override
    public Map<TypeLiteral<?>, List<InjectionPoint>> getAllMembersInjectorInjectionPoints() {
        Map res = ImmutableListMultimap.copyOf(Multimaps.filterKeys(this.membersInjectorStore.getAllInjectionPoints(), this.userRequestedMembersInjectorTypes::contains)).asMap();
        return res;
    }

    SingleParameterInjector<?>[] getParametersInjectors(List<Dependency<?>> parameters, Errors errors2) throws ErrorsException {
        if (parameters.isEmpty()) {
            return null;
        }
        int numErrorsBefore = errors2.size();
        SingleParameterInjector[] result = new SingleParameterInjector[parameters.size()];
        int i = 0;
        for (Dependency<?> parameter : parameters) {
            try {
                result[i++] = this.createParameterInjector(parameter, errors2.withSource(parameter));
            }
            catch (ErrorsException errorsException) {}
        }
        errors2.throwIfNewErrors(numErrorsBefore);
        return result;
    }

    <T> SingleParameterInjector<T> createParameterInjector(Dependency<T> dependency, Errors errors2) throws ErrorsException {
        BindingImpl<T> binding = this.getBindingOrThrow(dependency.getKey(), errors2, JitLimitation.NO_JIT);
        return new SingleParameterInjector<T>(dependency, binding);
    }

    @Override
    public void injectMembers(Object instance) {
        MembersInjector<?> membersInjector = this.getMembersInjector(instance.getClass());
        membersInjector.injectMembers(instance);
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral) {
        Preconditions.checkNotNull(typeLiteral, "typeLiteral");
        this.userRequestedMembersInjectorTypes.add(typeLiteral);
        Errors errors2 = new Errors(typeLiteral);
        try {
            return this.membersInjectorStore.get(typeLiteral, errors2);
        }
        catch (ErrorsException e) {
            ConfigurationException exception = new ConfigurationException(errors2.merge(e.getErrors()).getMessages());
            throw exception;
        }
    }

    @Override
    public <T> MembersInjector<T> getMembersInjector(Class<T> type) {
        return this.getMembersInjector(TypeLiteral.get(type));
    }

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        return this.getProvider(Key.get(Preconditions.checkNotNull(type, "type")));
    }

    <T> Provider<T> getProviderOrThrow(final Dependency<T> dependency, Errors errors2) throws ErrorsException {
        Key<T> key = dependency.getKey();
        BindingImpl<T> binding = this.getBindingOrThrow(key, errors2, JitLimitation.NO_JIT);
        final InternalFactory<T> internalFactory = binding.getInternalFactory();
        return new Provider<T>(){

            @Override
            public T get() {
                try (InternalContext currentContext = InjectorImpl.this.enterContext();){
                    Object t2;
                    Object t3 = t2 = internalFactory.get(currentContext, dependency, false);
                    return t3;
                }
            }

            public String toString() {
                return internalFactory.toString();
            }
        };
    }

    @Override
    public <T> Provider<T> getProvider(Key<T> key) {
        Preconditions.checkNotNull(key, "key");
        Errors errors2 = new Errors(key);
        try {
            Provider<T> result = this.getProviderOrThrow(Dependency.get(key), errors2);
            errors2.throwIfNewErrors(0);
            return result;
        }
        catch (ErrorsException e) {
            ConfigurationException exception = new ConfigurationException(errors2.merge(e.getErrors()).getMessages());
            throw exception;
        }
    }

    @Override
    public <T> T getInstance(Key<T> key) {
        return this.getProvider(key).get();
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return this.getProvider(type).get();
    }

    InternalContext getLocalContext() {
        return (InternalContext)this.localContext.get()[0];
    }

    InternalContext enterContext() {
        InternalContext ctx;
        Object[] reference = this.localContext.get();
        if (reference == null) {
            reference = new Object[1];
            this.localContext.set(reference);
        }
        if ((ctx = (InternalContext)reference[0]) == null) {
            ctx = new InternalContext(this.options, reference);
            reference[0] = ctx;
        } else {
            ctx.enter();
        }
        return ctx;
    }

    public String toString() {
        return MoreObjects.toStringHelper(Injector.class).add("bindings", this.bindingData.getExplicitBindingsThisLevel().values()).toString();
    }

    static interface MethodInvoker {
        public Object invoke(Object var1, Object ... var2) throws IllegalAccessException, InvocationTargetException;
    }

    private static class ConvertedConstantBindingImpl<T>
    extends BindingImpl<T>
    implements ConvertedConstantBinding<T> {
        final T value;
        final Provider<T> provider;
        final Binding<String> originalBinding;
        final TypeConverterBinding typeConverterBinding;

        ConvertedConstantBindingImpl(InjectorImpl injector, Key<T> key, T value, Binding<String> originalBinding, TypeConverterBinding typeConverterBinding) {
            super(injector, key, originalBinding.getSource(), new ConstantFactory<T>(Initializables.of(value)), Scoping.UNSCOPED);
            this.value = value;
            this.provider = Providers.of(value);
            this.originalBinding = originalBinding;
            this.typeConverterBinding = typeConverterBinding;
        }

        @Override
        public Provider<T> getProvider() {
            return this.provider;
        }

        @Override
        public <V> V acceptTargetVisitor(BindingTargetVisitor<? super T, V> visitor) {
            return visitor.visit(this);
        }

        @Override
        public T getValue() {
            return this.value;
        }

        @Override
        public TypeConverterBinding getTypeConverterBinding() {
            return this.typeConverterBinding;
        }

        @Override
        public Key<String> getSourceKey() {
            return this.originalBinding.getKey();
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return ImmutableSet.of(Dependency.get(this.getSourceKey()));
        }

        @Override
        public void applyTo(Binder binder) {
            throw new UnsupportedOperationException("This element represents a synthetic binding.");
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(ConvertedConstantBinding.class).add("key", this.getKey()).add("sourceKey", this.getSourceKey()).add("value", this.value).toString();
        }

        public boolean equals(Object obj) {
            if (obj instanceof ConvertedConstantBindingImpl) {
                ConvertedConstantBindingImpl o = (ConvertedConstantBindingImpl)obj;
                return this.getKey().equals(o.getKey()) && this.getScoping().equals(o.getScoping()) && Objects.equal(this.value, o.value);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(this.getKey(), this.getScoping(), this.value);
        }
    }

    private static class SyntheticProviderBindingImpl<T>
    extends BindingImpl<Provider<T>>
    implements ProviderBinding<Provider<T>>,
    HasDependencies {
        final BindingImpl<T> providedBinding;

        SyntheticProviderBindingImpl(InjectorImpl injector, Key<Provider<T>> key, Binding<T> providedBinding) {
            super(injector, key, providedBinding.getSource(), SyntheticProviderBindingImpl.createInternalFactory(providedBinding), Scoping.UNSCOPED);
            this.providedBinding = (BindingImpl)providedBinding;
        }

        static <T> InternalFactory<Provider<T>> createInternalFactory(Binding<T> providedBinding) {
            final Provider<T> provider = providedBinding.getProvider();
            return new InternalFactory<Provider<T>>(){

                @Override
                public Provider<T> get(InternalContext context, Dependency<?> dependency, boolean linked) {
                    return provider;
                }
            };
        }

        @Override
        public Key<? extends T> getProvidedKey() {
            return this.providedBinding.getKey();
        }

        @Override
        public <V> V acceptTargetVisitor(BindingTargetVisitor<? super Provider<T>, V> visitor) {
            return visitor.visit(this);
        }

        @Override
        public void applyTo(Binder binder) {
            throw new UnsupportedOperationException("This element represents a synthetic binding.");
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(ProviderBinding.class).add("key", this.getKey()).add("providedKey", this.getProvidedKey()).toString();
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return ImmutableSet.of(Dependency.get(this.getProvidedKey()));
        }

        public boolean equals(Object obj) {
            if (obj instanceof SyntheticProviderBindingImpl) {
                SyntheticProviderBindingImpl o = (SyntheticProviderBindingImpl)obj;
                return this.getKey().equals(o.getKey()) && this.getScoping().equals(o.getScoping()) && Objects.equal(this.providedBinding, o.providedBinding);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(this.getKey(), this.getScoping(), this.providedBinding);
        }
    }

    static enum JitLimitation {
        NO_JIT,
        EXISTING_JIT,
        NEW_OR_EXISTING_JIT;

    }

    static class InjectorOptions {
        final Stage stage;
        final boolean jitDisabled;
        final boolean disableCircularProxies;
        final boolean atInjectRequired;
        final boolean exactBindingAnnotationsRequired;

        InjectorOptions(Stage stage, boolean jitDisabled, boolean disableCircularProxies, boolean atInjectRequired, boolean exactBindingAnnotationsRequired) {
            this.stage = stage;
            this.jitDisabled = jitDisabled;
            this.disableCircularProxies = disableCircularProxies;
            this.atInjectRequired = atInjectRequired;
            this.exactBindingAnnotationsRequired = exactBindingAnnotationsRequired;
        }

        public String toString() {
            return MoreObjects.toStringHelper(this.getClass()).add("stage", (Object)this.stage).add("jitDisabled", this.jitDisabled).add("disableCircularProxies", this.disableCircularProxies).add("atInjectRequired", this.atInjectRequired).add("exactBindingAnnotationsRequired", this.exactBindingAnnotationsRequired).toString();
        }
    }
}

