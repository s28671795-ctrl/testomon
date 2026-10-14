/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.internal.Element;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.Indexer;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProviderInstanceBindingImpl;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.RealElement;
import com.google.inject.internal.RealMultibinder;
import com.google.inject.internal.SingleParameterInjector;
import com.google.inject.multibindings.MapBinderBinding;
import com.google.inject.multibindings.MultibindingsTargetVisitor;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Element;
import com.google.inject.spi.ProviderInstanceBinding;
import com.google.inject.spi.ProviderWithExtensionVisitor;
import com.google.inject.util.Types;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RealMapBinder<K, V>
implements Module {
    private static final ImmutableSet<Dependency<?>> MODULE_DEPENDENCIES = ImmutableSet.of(Dependency.get(Key.get(Injector.class)));
    private final BindingSelection<K, V> bindingSelection;
    private final Binder binder;
    private final RealMultibinder<Map.Entry<K, Provider<V>>> entrySetBinder;

    public static <K, V> RealMapBinder<K, V> newMapRealBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return RealMapBinder.newRealMapBinder(binder, keyType, valueType, Key.get(RealMapBinder.mapOf(keyType, valueType)), RealMultibinder.newRealSetBinder(binder, Key.get(RealMapBinder.entryOfProviderOf(keyType, valueType))));
    }

    public static <K, V> RealMapBinder<K, V> newRealMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType, Annotation annotation) {
        return RealMapBinder.newRealMapBinder(binder, keyType, valueType, Key.get(RealMapBinder.mapOf(keyType, valueType), annotation), RealMultibinder.newRealSetBinder(binder, Key.get(RealMapBinder.entryOfProviderOf(keyType, valueType), annotation)));
    }

    public static <K, V> RealMapBinder<K, V> newRealMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType, Class<? extends Annotation> annotationType) {
        return RealMapBinder.newRealMapBinder(binder, keyType, valueType, Key.get(RealMapBinder.mapOf(keyType, valueType), annotationType), RealMultibinder.newRealSetBinder(binder, Key.get(RealMapBinder.entryOfProviderOf(keyType, valueType), annotationType)));
    }

    static <K, V> TypeLiteral<Map<K, V>> mapOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.mapOf(keyType.getType(), valueType.getType()));
    }

    static <K, V> TypeLiteral<Map<K, Provider<V>>> mapOfProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.mapOf(keyType.getType(), Types.providerOf(valueType.getType())));
    }

    static <K, V> TypeLiteral<Map<K, jakarta.inject.Provider<V>>> mapOfJakartaProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.mapOf(keyType.getType(), Types.newParameterizedType(jakarta.inject.Provider.class, new Type[]{valueType.getType()})));
    }

    static <K, V> TypeLiteral<Map<K, Set<Provider<V>>>> mapOfSetOfProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.mapOf(keyType.getType(), Types.setOf(Types.providerOf(valueType.getType()))));
    }

    static <K, V> TypeLiteral<Map<K, Set<jakarta.inject.Provider<V>>>> mapOfSetOfJakartaProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.mapOf(keyType.getType(), Types.setOf(Types.jakartaProviderOf(valueType.getType()))));
    }

    static <K, V> TypeLiteral<Map<K, Collection<Provider<V>>>> mapOfCollectionOfProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.mapOf(keyType.getType(), Types.collectionOf(Types.providerOf(valueType.getType()))));
    }

    static <K, V> TypeLiteral<Map<K, Collection<jakarta.inject.Provider<V>>>> mapOfCollectionOfJakartaProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.mapOf(keyType.getType(), Types.collectionOf(Types.jakartaProviderOf(valueType.getType()))));
    }

    static <K, V> TypeLiteral<Map.Entry<K, Provider<V>>> entryOfProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.newParameterizedTypeWithOwner(Map.class, Map.Entry.class, new Type[]{keyType.getType(), Types.providerOf(valueType.getType())}));
    }

    static <K, V> TypeLiteral<Map.Entry<K, Provider<V>>> entryOfJakartaProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.newParameterizedTypeWithOwner(Map.class, Map.Entry.class, new Type[]{keyType.getType(), Types.jakartaProviderOf(valueType.getType())}));
    }

    static <K, V> TypeLiteral<Set<Map.Entry<K, jakarta.inject.Provider<V>>>> setOfEntryOfJakartaProviderOf(TypeLiteral<K> keyType, TypeLiteral<V> valueType) {
        return TypeLiteral.get(Types.setOf(RealMapBinder.entryOfJakartaProviderOf(keyType, valueType).getType()));
    }

    private static <T> Key<Provider<T>> getKeyOfProvider(Key<T> valueKey) {
        return valueKey.ofType(Types.providerOf(valueKey.getTypeLiteral().getType()));
    }

    static <K, V> RealMapBinder<K, V> newRealMapBinder(Binder binder, TypeLiteral<K> keyType, Key<V> valueTypeAndAnnotation) {
        TypeLiteral<V> valueType = valueTypeAndAnnotation.getTypeLiteral();
        return RealMapBinder.newRealMapBinder(binder, keyType, valueType, valueTypeAndAnnotation.ofType(RealMapBinder.mapOf(keyType, valueType)), RealMultibinder.newRealSetBinder(binder, valueTypeAndAnnotation.ofType(RealMapBinder.entryOfProviderOf(keyType, valueType))));
    }

    private static <K, V> RealMapBinder<K, V> newRealMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType, Key<Map<K, V>> mapKey, RealMultibinder<Map.Entry<K, Provider<V>>> entrySetBinder) {
        RealMapBinder<K, V> mapBinder = new RealMapBinder<K, V>(binder, keyType, valueType, mapKey, entrySetBinder);
        binder.install(mapBinder);
        return mapBinder;
    }

    private RealMapBinder(Binder binder, TypeLiteral<K> keyType, TypeLiteral<V> valueType, Key<Map<K, V>> mapKey, RealMultibinder<Map.Entry<K, Provider<V>>> entrySetBinder) {
        this.bindingSelection = new BindingSelection(keyType, valueType, mapKey, entrySetBinder);
        this.binder = binder;
        this.entrySetBinder = entrySetBinder;
    }

    public void permitDuplicates() {
        Errors.checkConfiguration(!((BindingSelection)this.bindingSelection).isInitialized(), "MapBinder was already initialized", new Object[0]);
        this.entrySetBinder.permitDuplicates();
        this.binder.install(new MultimapBinder(this.bindingSelection));
    }

    Key<V> getKeyForNewValue(K key) {
        Errors.checkNotNull(key, "key");
        Errors.checkConfiguration(!((BindingSelection)this.bindingSelection).isInitialized(), "MapBinder was already initialized", new Object[0]);
        RealMultibinder entrySetBinder = ((BindingSelection)this.bindingSelection).getEntrySetBinder();
        Key valueKey = Key.get(((BindingSelection)this.bindingSelection).getValueType(), (Annotation)new RealElement(entrySetBinder.getSetName(), Element.Type.MAPBINDER, ((BindingSelection)this.bindingSelection).getKeyType().toString()));
        entrySetBinder.addBinding().toProvider(new ProviderMapEntry(key, valueKey));
        return valueKey;
    }

    public LinkedBindingBuilder<V> addBinding(K key) {
        return this.binder.bind(this.getKeyForNewValue(key));
    }

    @Override
    public void configure(Binder binder) {
        Errors.checkConfiguration(!((BindingSelection)this.bindingSelection).isInitialized(), "MapBinder was already initialized", new Object[0]);
        binder.bind(((BindingSelection)this.bindingSelection).getProviderMapKey()).toProvider(new RealProviderMapProvider(this.bindingSelection));
        binder.bind(((BindingSelection)this.bindingSelection).getJakartaProviderMapKey()).to(((BindingSelection)this.bindingSelection).getProviderMapKey());
        binder.bind(((BindingSelection)this.bindingSelection).getMapKey()).toProvider(new ExtensionRealMapProvider<K, V>(this.bindingSelection));
        binder.bind(((BindingSelection)this.bindingSelection).getMapOfKeyExtendsValueKey()).to(((BindingSelection)this.bindingSelection).getMapKey());
        binder.bind(((BindingSelection)this.bindingSelection).getEntrySetJakartaProviderKey()).to(((BindingSelection)this.bindingSelection).getEntrySetBinder().getSetKey());
    }

    public boolean equals(Object o) {
        return o instanceof RealMapBinder && ((RealMapBinder)o).bindingSelection.equals(this.bindingSelection);
    }

    public int hashCode() {
        return this.bindingSelection.hashCode();
    }

    private static <K, V> InternalProvisionException createNullValueException(K key, Binding<V> binding) {
        return InternalProvisionException.create(ErrorId.NULL_VALUE_IN_MAP, "Map injection failed due to null value for key \"%s\", bound at: %s", key, binding.getSource());
    }

    static /* synthetic */ ImmutableSet access$1200() {
        return MODULE_DEPENDENCIES;
    }

    private static abstract class RealMultimapBinderProviderWithDependencies<K, V, P>
    extends InternalProviderInstanceBindingImpl.Factory<P> {
        final Key<Map<K, V>> mapKey;
        BindingSelection<K, V> bindingSelection;

        private RealMultimapBinderProviderWithDependencies(Key<Map<K, V>> mapKey) {
            super(InternalProviderInstanceBindingImpl.InitializationTiming.DELAYED);
            this.mapKey = mapKey;
        }

        @Override
        final void initialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
            Binding mapBinding = injector.getExistingBinding((Key)this.mapKey);
            ProviderInstanceBinding providerInstanceBinding = (ProviderInstanceBinding)mapBinding;
            ExtensionRealMapProvider mapProvider = (ExtensionRealMapProvider)providerInstanceBinding.getUserSuppliedProvider();
            this.bindingSelection = mapProvider.getBindingSelection();
            if (((BindingSelection)this.bindingSelection).tryInitialize(injector, errors2)) {
                this.doInitialize(injector, errors2);
            }
        }

        abstract void doInitialize(InjectorImpl var1, Errors var2) throws ErrorsException;

        public boolean equals(Object obj) {
            return obj != null && this.getClass() == obj.getClass() && this.mapKey.equals(((RealMultimapBinderProviderWithDependencies)obj).mapKey);
        }

        public int hashCode() {
            return this.mapKey.hashCode();
        }
    }

    private static abstract class RealMapBinderProviderWithDependencies<K, V, P>
    extends InternalProviderInstanceBindingImpl.Factory<P> {
        final BindingSelection<K, V> bindingSelection;

        private RealMapBinderProviderWithDependencies(BindingSelection<K, V> bindingSelection) {
            super(InternalProviderInstanceBindingImpl.InitializationTiming.DELAYED);
            this.bindingSelection = bindingSelection;
        }

        @Override
        final void initialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
            if (((BindingSelection)this.bindingSelection).tryInitialize(injector, errors2)) {
                this.doInitialize(injector, errors2);
            }
        }

        protected abstract void doInitialize(InjectorImpl var1, Errors var2) throws ErrorsException;

        public boolean equals(Object obj) {
            return obj != null && this.getClass() == obj.getClass() && this.bindingSelection.equals(((RealMapBinderProviderWithDependencies)obj).bindingSelection);
        }

        public int hashCode() {
            return this.bindingSelection.hashCode();
        }
    }

    static final class ProviderMapEntry<K, V>
    extends InternalProviderInstanceBindingImpl.Factory<Map.Entry<K, Provider<V>>> {
        private final K key;
        private final Key<V> valueKey;
        private Map.Entry<K, Provider<V>> entry;

        ProviderMapEntry(K key, Key<V> valueKey) {
            super(InternalProviderInstanceBindingImpl.InitializationTiming.EAGER);
            this.key = key;
            this.valueKey = valueKey;
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return ImmutableSet.of(Dependency.get(RealMapBinder.getKeyOfProvider(this.valueKey)));
        }

        @Override
        void initialize(InjectorImpl injector, Errors errors2) {
            Binding valueBinding = injector.getExistingBinding((Key)this.valueKey);
            this.entry = Maps.immutableEntry(this.key, valueBinding.getProvider());
        }

        @Override
        protected Map.Entry<K, Provider<V>> doProvision(InternalContext context, Dependency<?> dependency) {
            return this.entry;
        }

        K getKey() {
            return this.key;
        }

        Key<V> getValueKey() {
            return this.valueKey;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ProviderMapEntry) {
                ProviderMapEntry o = (ProviderMapEntry)obj;
                return this.key.equals(o.key) && this.valueKey.equals(o.valueKey);
            }
            return false;
        }

        public int hashCode() {
            return Objects.hashCode(this.key, this.valueKey);
        }

        public String toString() {
            return "ProviderMapEntry(" + this.key + ", " + this.valueKey + ")";
        }
    }

    static final class MultimapBinder<K, V>
    implements Module {
        private final BindingSelection<K, V> bindingSelection;

        private MultimapBinder(BindingSelection<K, V> bindingSelection) {
            this.bindingSelection = bindingSelection;
        }

        @Override
        public void configure(Binder binder) {
            binder.bind(((BindingSelection)this.bindingSelection).getProviderSetMultimapKey()).toProvider(new RealProviderMultimapProvider(((BindingSelection)this.bindingSelection).getMapKey()));
            binder.bind(((BindingSelection)this.bindingSelection).getJakartaProviderSetMultimapKey()).to(((BindingSelection)this.bindingSelection).getProviderSetMultimapKey());
            binder.bind(((BindingSelection)this.bindingSelection).getProviderCollectionMultimapKey()).to(((BindingSelection)this.bindingSelection).getProviderSetMultimapKey());
            binder.bind(((BindingSelection)this.bindingSelection).getJakartaProviderCollectionMultimapKey()).to(((BindingSelection)this.bindingSelection).getProviderSetMultimapKey());
            binder.bind(((BindingSelection)this.bindingSelection).getMultimapKey()).toProvider(new RealMultimapProvider(((BindingSelection)this.bindingSelection).getMapKey()));
        }

        public int hashCode() {
            return this.bindingSelection.hashCode();
        }

        public boolean equals(Object o) {
            return o instanceof MultimapBinder && ((MultimapBinder)o).bindingSelection.equals(this.bindingSelection);
        }

        private static final class RealMultimapProvider<K, V>
        extends RealMultimapBinderProviderWithDependencies<K, V, Map<K, Set<V>>> {
            private Set<Dependency<?>> dependencies = RealMapBinder.access$1200();
            private PerKeyData<K, V>[] perKeyDatas;
            private boolean initialized = false;

            private RealMultimapProvider(Key<Map<K, V>> mapKey) {
                super(mapKey);
            }

            @Override
            public Set<Dependency<?>> getDependencies() {
                return this.dependencies;
            }

            @Override
            protected void doInitialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
                if (this.initialized) {
                    return;
                }
                this.initialized = true;
                PerKeyData[] typedPerKeyData = new PerKeyData[this.bindingSelection.getMapBindings().size()];
                this.perKeyDatas = typedPerKeyData;
                ImmutableSet.Builder dependenciesBuilder = ImmutableSet.builder();
                ArrayList<Dependency<?>> dependenciesForKey = Lists.newArrayList();
                int i = 0;
                for (Map.Entry entry : this.bindingSelection.getMultimapBindings().entrySet()) {
                    Binding[] typedBindings;
                    dependenciesForKey.clear();
                    Set bindings = (Set)entry.getValue();
                    Binding[] bindingsArray = typedBindings = new Binding[bindings.size()];
                    int j = 0;
                    for (Binding binding : bindings) {
                        Dependency dependency = Dependency.get(binding.getKey());
                        dependenciesBuilder.add(dependency);
                        dependenciesForKey.add(dependency);
                        bindingsArray[j] = binding;
                        ++j;
                    }
                    SingleParameterInjector[] injectors = injector.getParametersInjectors(dependenciesForKey, errors2);
                    this.perKeyDatas[i] = new PerKeyData(entry.getKey(), bindingsArray, injectors);
                    ++i;
                }
                this.dependencies = dependenciesBuilder.build();
            }

            @Override
            protected Map<K, Set<V>> doProvision(InternalContext context, Dependency<?> dependency) throws InternalProvisionException {
                ImmutableMap.Builder<Object, ImmutableCollection> resultBuilder = ImmutableMap.builder();
                for (PerKeyData<K, V> perKeyData : this.perKeyDatas) {
                    ImmutableSet.Builder bindingsBuilder = ImmutableSet.builder();
                    SingleParameterInjector[] injectors = ((PerKeyData)perKeyData).injectors;
                    for (int i = 0; i < injectors.length; ++i) {
                        SingleParameterInjector injector = injectors[i];
                        Object value = injector.inject(context);
                        if (value == null) {
                            throw RealMapBinder.createNullValueException(((PerKeyData)perKeyData).key, ((PerKeyData)perKeyData).bindings[i]);
                        }
                        bindingsBuilder.add(value);
                    }
                    resultBuilder.put(((PerKeyData)perKeyData).key, bindingsBuilder.build());
                }
                return resultBuilder.buildOrThrow();
            }

            private static final class PerKeyData<K, V> {
                private final K key;
                private final Binding<V>[] bindings;
                private final SingleParameterInjector<V>[] injectors;

                private PerKeyData(K key, Binding<V>[] bindings, SingleParameterInjector<V>[] injectors) {
                    Preconditions.checkArgument(bindings.length == injectors.length);
                    this.key = key;
                    this.bindings = bindings;
                    this.injectors = injectors;
                }
            }
        }

        private static final class RealProviderMultimapProvider<K, V>
        extends RealMultimapBinderProviderWithDependencies<K, V, Map<K, Set<Provider<V>>>> {
            private Map<K, Set<Provider<V>>> multimapOfProviders;
            private Set<Dependency<?>> dependencies = RealMapBinder.access$1200();
            private boolean initialized;

            private RealProviderMultimapProvider(Key<Map<K, V>> mapKey) {
                super(mapKey);
            }

            @Override
            public Set<Dependency<?>> getDependencies() {
                return this.dependencies;
            }

            @Override
            protected void doInitialize(InjectorImpl injector, Errors errors2) {
                if (this.initialized) {
                    return;
                }
                this.initialized = true;
                ImmutableMap.Builder multimapOfProvidersBuilder = ImmutableMap.builder();
                ImmutableSet.Builder dependenciesBuilder = ImmutableSet.builder();
                for (Map.Entry entry : this.bindingSelection.getMultimapBindings().entrySet()) {
                    ImmutableSet.Builder providersBuilder = ImmutableSet.builder();
                    for (Binding binding : (Set)entry.getValue()) {
                        providersBuilder.add(binding.getProvider());
                        dependenciesBuilder.add(Dependency.get(RealMapBinder.getKeyOfProvider(binding.getKey())));
                    }
                    multimapOfProvidersBuilder.put(entry.getKey(), providersBuilder.build());
                }
                this.multimapOfProviders = multimapOfProvidersBuilder.buildOrThrow();
                this.dependencies = dependenciesBuilder.build();
            }

            @Override
            protected Map<K, Set<Provider<V>>> doProvision(InternalContext context, Dependency<?> dependency) {
                return this.multimapOfProviders;
            }
        }
    }

    private static final class ExtensionRealMapProvider<K, V>
    extends RealMapBinderProviderWithDependencies<K, V, Map<K, V>>
    implements ProviderWithExtensionVisitor<Map<K, V>>,
    MapBinderBinding<Map<K, V>> {
        Set<Dependency<?>> dependencies = RealMapBinder.access$1200();
        SingleParameterInjector<V>[] injectors;
        K[] keys;
        private boolean initialized = false;

        ExtensionRealMapProvider(BindingSelection<K, V> bindingSelection) {
            super(bindingSelection);
        }

        @Override
        protected void doInitialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
            if (this.initialized) {
                return;
            }
            this.initialized = true;
            Object[] keysArray = new Object[this.bindingSelection.getMapBindings().size()];
            this.keys = keysArray;
            ImmutableSet.Builder dependenciesBuilder = ImmutableSet.builder();
            int i = 0;
            for (Map.Entry entry : this.bindingSelection.getMapBindings().entrySet()) {
                dependenciesBuilder.add(Dependency.get(((Binding)entry.getValue()).getKey()));
                this.keys[i] = entry.getKey();
                ++i;
            }
            ImmutableCollection localDependencies = dependenciesBuilder.build();
            this.dependencies = localDependencies;
            ImmutableList<Dependency<?>> dependenciesList = localDependencies.asList();
            SingleParameterInjector<?>[] typedInjectors = injector.getParametersInjectors(dependenciesList, errors2);
            this.injectors = typedInjectors;
        }

        @Override
        protected Map<K, V> doProvision(InternalContext context, Dependency<?> dependency) throws InternalProvisionException {
            SingleParameterInjector<V>[] localInjectors = this.injectors;
            if (localInjectors == null) {
                return ImmutableMap.of();
            }
            ImmutableMap.Builder<K, V> resultBuilder = ImmutableMap.builder();
            K[] localKeys = this.keys;
            for (int i = 0; i < localInjectors.length; ++i) {
                SingleParameterInjector<V> injector = localInjectors[i];
                K key = localKeys[i];
                V value = injector.inject(context);
                if (value == null) {
                    throw RealMapBinder.createNullValueException(key, (Binding)this.bindingSelection.getMapBindings().get(key));
                }
                resultBuilder.put(key, value);
            }
            return resultBuilder.buildOrThrow();
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.dependencies;
        }

        BindingSelection<K, V> getBindingSelection() {
            return this.bindingSelection;
        }

        @Override
        public <B, W> W acceptExtensionVisitor(BindingTargetVisitor<B, W> visitor, ProviderInstanceBinding<? extends B> binding) {
            if (visitor instanceof MultibindingsTargetVisitor) {
                return (W)((MultibindingsTargetVisitor)visitor).visit(this);
            }
            return visitor.visit(binding);
        }

        @Override
        public Key<Map<K, V>> getMapKey() {
            return this.bindingSelection.getMapKey();
        }

        @Override
        public Set<Key<?>> getAlternateMapKeys() {
            return ImmutableSet.of(this.bindingSelection.getJakartaProviderMapKey(), this.bindingSelection.getProviderMapKey(), this.bindingSelection.getProviderSetMultimapKey(), this.bindingSelection.getJakartaProviderSetMultimapKey(), this.bindingSelection.getProviderCollectionMultimapKey(), this.bindingSelection.getJakartaProviderCollectionMultimapKey(), new Key[]{this.bindingSelection.getMultimapKey(), this.bindingSelection.getMapOfKeyExtendsValueKey()});
        }

        @Override
        public TypeLiteral<K> getKeyTypeLiteral() {
            return this.bindingSelection.getKeyType();
        }

        @Override
        public TypeLiteral<V> getValueTypeLiteral() {
            return this.bindingSelection.getValueType();
        }

        @Override
        public List<Map.Entry<?, Binding<?>>> getEntries() {
            if (this.bindingSelection.isInitialized()) {
                return this.bindingSelection.getEntries();
            }
            throw new UnsupportedOperationException("getEntries() not supported for module bindings");
        }

        @Override
        public List<Map.Entry<?, Binding<?>>> getEntries(Iterable<? extends Element> elements) {
            Set keysFromBindings;
            ImmutableMultimap.Builder keyToValueKeyBuilder = ImmutableMultimap.builder();
            ImmutableMap.Builder valueKeyToBindingBuilder = ImmutableMap.builder();
            ImmutableMap.Builder valueKeyToKeyBuilder = ImmutableMap.builder();
            ImmutableMap.Builder valueKeyToEntryBindingBuilder = ImmutableMap.builder();
            for (Element element : elements) {
                ProviderMapEntry typedUserSuppliedProvider;
                ProviderInstanceBinding entryBinding;
                Provider typedProvider;
                Provider userSuppliedProvider;
                Binding binding;
                if (element instanceof Binding && this.bindingSelection.matchesValueKey((binding = (Binding)element).getKey()) && binding.getKey().getTypeLiteral().equals(this.bindingSelection.valueType)) {
                    Binding typedBinding = binding;
                    Key typedKey = typedBinding.getKey();
                    valueKeyToBindingBuilder.put(typedKey, typedBinding);
                }
                if (!(element instanceof ProviderInstanceBinding) || !this.bindingSelection.getEntrySetBinder().containsElement(element) || !((userSuppliedProvider = (typedProvider = (Provider)(entryBinding = (ProviderInstanceBinding)element).getUserSuppliedProvider())) instanceof ProviderMapEntry)) continue;
                ProviderMapEntry entry = typedUserSuppliedProvider = (ProviderMapEntry)userSuppliedProvider;
                keyToValueKeyBuilder.put(entry.getKey(), entry.getValueKey());
                valueKeyToEntryBindingBuilder.put(entry.getValueKey(), entryBinding);
                valueKeyToKeyBuilder.put(entry.getValueKey(), entry.getKey());
            }
            ImmutableMultimap keyToValueKey = keyToValueKeyBuilder.build();
            ImmutableMap immutableMap = valueKeyToKeyBuilder.buildOrThrow();
            ImmutableMap valueKeyToBinding = valueKeyToBindingBuilder.buildOrThrow();
            ImmutableMap valueKeyToEntryBinding = valueKeyToEntryBindingBuilder.buildOrThrow();
            HashSet keysFromProviderMapEntrys = Sets.newHashSet(keyToValueKey.values());
            if (!keysFromProviderMapEntrys.equals(keysFromBindings = valueKeyToBinding.keySet())) {
                Sets.SetView<Key> keysOnlyFromProviderMapEntrys = Sets.difference(keysFromProviderMapEntrys, keysFromBindings);
                Sets.SetView<Key> keysOnlyFromBindings = Sets.difference(keysFromBindings, keysFromProviderMapEntrys);
                StringBuilder sb = new StringBuilder("Expected a 1:1 mapping from map keys to values.");
                if (!keysOnlyFromBindings.isEmpty()) {
                    sb.append(Errors.format("\nFound these Bindings that were missing an associated entry:\n", new Object[0]));
                    for (Key key : keysOnlyFromBindings) {
                        sb.append(Errors.format("  %s bound at: %s\n", key, ((Binding)valueKeyToBinding.get(key)).getSource()));
                    }
                }
                if (!keysOnlyFromProviderMapEntrys.isEmpty()) {
                    sb.append(Errors.format("\nFound these map keys without a corresponding value:\n", new Object[0]));
                    for (Key key : keysOnlyFromProviderMapEntrys) {
                        sb.append(Errors.format("  '%s' bound at: %s\n", immutableMap.get(key), ((Binding)valueKeyToEntryBinding.get(key)).getSource()));
                    }
                }
                throw new IllegalArgumentException(sb.toString());
            }
            ImmutableList.Builder resultBuilder = ImmutableList.builder();
            for (Map.Entry entry : keyToValueKey.entries()) {
                Binding binding = (Binding)valueKeyToBinding.get(entry.getValue());
                Map.Entry newEntry = Maps.immutableEntry(entry.getKey(), binding);
                resultBuilder.add(newEntry);
            }
            return resultBuilder.build();
        }

        @Override
        public boolean permitsDuplicates() {
            if (this.bindingSelection.isInitialized()) {
                return this.bindingSelection.permitsDuplicates();
            }
            throw new UnsupportedOperationException("permitsDuplicates() not supported for module bindings");
        }

        @Override
        public boolean containsElement(Element element) {
            return this.bindingSelection.containsElement(element);
        }
    }

    private static final class RealProviderMapProvider<K, V>
    extends RealMapBinderProviderWithDependencies<K, V, Map<K, Provider<V>>> {
        private Map<K, Provider<V>> mapOfProviders;
        private Set<Dependency<?>> dependencies = RealMapBinder.access$1200();
        private boolean initialized;

        private RealProviderMapProvider(BindingSelection<K, V> bindingSelection) {
            super(bindingSelection);
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.dependencies;
        }

        @Override
        protected void doInitialize(InjectorImpl injector, Errors errors2) {
            if (this.initialized) {
                return;
            }
            this.initialized = true;
            ImmutableMap.Builder mapOfProvidersBuilder = ImmutableMap.builder();
            ImmutableSet.Builder dependenciesBuilder = ImmutableSet.builder();
            for (Map.Entry entry : this.bindingSelection.getMapBindings().entrySet()) {
                mapOfProvidersBuilder.put(entry.getKey(), ((Binding)entry.getValue()).getProvider());
                dependenciesBuilder.add(Dependency.get(RealMapBinder.getKeyOfProvider(((Binding)entry.getValue()).getKey())));
            }
            this.mapOfProviders = mapOfProvidersBuilder.buildOrThrow();
            this.dependencies = dependenciesBuilder.build();
        }

        @Override
        protected Map<K, Provider<V>> doProvision(InternalContext context, Dependency<?> dependency) {
            return this.mapOfProviders;
        }
    }

    private static final class BindingSelection<K, V> {
        private final TypeLiteral<K> keyType;
        private final TypeLiteral<V> valueType;
        private final Key<Map<K, V>> mapKey;
        private Key<Map<K, Provider<V>>> providerMapKey;
        private Key<Map<K, Set<V>>> multimapKey;
        private Key<Map<K, Set<Provider<V>>>> providerSetMultimapKey;
        private Key<Map<K, Collection<Provider<V>>>> providerCollectionMultimapKey;
        private Key<Map<K, ? extends V>> mapOfKeyExtendsValueKey;
        private Key<Map<K, jakarta.inject.Provider<V>>> jakartaProviderMapKey;
        private Key<Map<K, Set<jakarta.inject.Provider<V>>>> jakartaProviderSetMultimapKey;
        private Key<Map<K, Collection<jakarta.inject.Provider<V>>>> jakartaProviderCollectionMultimapKey;
        private Key<Set<Map.Entry<K, jakarta.inject.Provider<V>>>> entrySetJakartaProviderKey;
        private final RealMultibinder<Map.Entry<K, Provider<V>>> entrySetBinder;
        private InitializationState initializationState;
        private ImmutableMap<K, Binding<V>> mapBindings;
        private ImmutableMap<K, Set<Binding<V>>> multimapBindings;
        private ImmutableList<Map.Entry<K, Binding<V>>> entries;
        private boolean permitsDuplicates;

        private BindingSelection(TypeLiteral<K> keyType, TypeLiteral<V> valueType, Key<Map<K, V>> mapKey, RealMultibinder<Map.Entry<K, Provider<V>>> entrySetBinder) {
            this.keyType = keyType;
            this.valueType = valueType;
            this.mapKey = mapKey;
            this.entrySetBinder = entrySetBinder;
            this.initializationState = InitializationState.UNINITIALIZED;
        }

        private boolean tryInitialize(InjectorImpl injector, Errors errors2) {
            if (this.initializationState != InitializationState.UNINITIALIZED) {
                return this.initializationState != InitializationState.HAS_ERRORS;
            }
            this.permitsDuplicates = this.entrySetBinder.permitsDuplicates(injector);
            LinkedHashMap<Object, ImmutableSet.Builder> bindingMultimapMutable = new LinkedHashMap<Object, ImmutableSet.Builder>();
            LinkedHashMap bindingMapMutable = new LinkedHashMap();
            HashMultimap index = HashMultimap.create();
            Indexer indexer = new Indexer(injector);
            LinkedHashMultimap duplicates = null;
            ImmutableList.Builder entriesBuilder = ImmutableList.builder();
            for (Binding<Map.Entry<K, Provider<V>>> binding : injector.findBindingsByType(this.entrySetBinder.getElementTypeLiteral())) {
                Key valueKey;
                Binding valueBinding;
                ProviderInstanceBinding entryBinding;
                ProviderMapEntry entry;
                Object key;
                if (!this.entrySetBinder.containsElement(binding) || !index.put(key = (entry = (ProviderMapEntry)(entryBinding = (ProviderInstanceBinding)binding).getUserSuppliedProvider()).getKey(), (valueBinding = injector.getExistingBinding(valueKey = entry.getValueKey())).acceptTargetVisitor(indexer))) continue;
                entriesBuilder.add(Maps.immutableEntry(key, valueBinding));
                Binding previous = bindingMapMutable.put(key, valueBinding);
                if (previous != null && !this.permitsDuplicates) {
                    if (duplicates == null) {
                        duplicates = LinkedHashMultimap.create();
                    }
                    duplicates.put(key, previous);
                    duplicates.put(key, valueBinding);
                }
                if (!this.permitsDuplicates) continue;
                bindingMultimapMutable.computeIfAbsent(key, k -> ImmutableSet.builder()).add(valueBinding);
            }
            if (duplicates != null) {
                this.initializationState = InitializationState.HAS_ERRORS;
                BindingSelection.reportDuplicateKeysError(this.mapKey, duplicates, errors2);
                return false;
            }
            ImmutableMap.Builder bindingsMultimapBuilder = ImmutableMap.builder();
            for (Map.Entry entry : bindingMultimapMutable.entrySet()) {
                bindingsMultimapBuilder.put(entry.getKey(), ((ImmutableSet.Builder)entry.getValue()).build());
            }
            this.mapBindings = ImmutableMap.copyOf(bindingMapMutable);
            this.multimapBindings = bindingsMultimapBuilder.buildOrThrow();
            this.entries = entriesBuilder.build();
            this.initializationState = InitializationState.INITIALIZED;
            return true;
        }

        private static <K, V> void reportDuplicateKeysError(Key<Map<K, V>> mapKey, Multimap<K, Binding<V>> duplicates, Errors errors2) {
            errors2.duplicateMapKey(mapKey, duplicates);
        }

        private boolean containsElement(Element element) {
            if (this.entrySetBinder.containsElement(element)) {
                return true;
            }
            if (!(element instanceof Binding)) {
                return false;
            }
            Key key = ((Binding)element).getKey();
            return key.equals(this.getMapKey()) || key.equals(this.getProviderMapKey()) || key.equals(this.getJakartaProviderMapKey()) || key.equals(this.getMultimapKey()) || key.equals(this.getProviderSetMultimapKey()) || key.equals(this.getJakartaProviderSetMultimapKey()) || key.equals(this.getProviderCollectionMultimapKey()) || key.equals(this.getJakartaProviderCollectionMultimapKey()) || key.equals(this.entrySetBinder.getSetKey()) || key.equals(this.getEntrySetJakartaProviderKey()) || key.equals(this.getMapOfKeyExtendsValueKey()) || this.matchesValueKey(key);
        }

        private boolean matchesValueKey(Key<?> key) {
            return key.getAnnotation() instanceof RealElement && ((RealElement)key.getAnnotation()).setName().equals(this.entrySetBinder.getSetName()) && ((RealElement)key.getAnnotation()).type() == Element.Type.MAPBINDER && ((RealElement)key.getAnnotation()).keyType().equals(this.keyType.toString()) && key.getTypeLiteral().equals(this.valueType);
        }

        private Key<Map<K, Provider<V>>> getProviderMapKey() {
            Key<Map<K, Provider<V>>> local = this.providerMapKey;
            if (local == null) {
                local = this.providerMapKey = this.mapKey.ofType(RealMapBinder.mapOfProviderOf(this.keyType, this.valueType));
            }
            return local;
        }

        private Key<Map<K, jakarta.inject.Provider<V>>> getJakartaProviderMapKey() {
            Key<Map<K, jakarta.inject.Provider<V>>> local = this.jakartaProviderMapKey;
            if (local == null) {
                local = this.jakartaProviderMapKey = this.mapKey.ofType(RealMapBinder.mapOfJakartaProviderOf(this.keyType, this.valueType));
            }
            return local;
        }

        private Key<Map<K, Set<V>>> getMultimapKey() {
            Key<Map<K, Set<V>>> local = this.multimapKey;
            if (local == null) {
                local = this.multimapKey = this.mapKey.ofType(RealMapBinder.mapOf(this.keyType, RealMultibinder.setOf(this.valueType)));
            }
            return local;
        }

        private Key<Map<K, Set<Provider<V>>>> getProviderSetMultimapKey() {
            Key<Map<K, Set<Provider<V>>>> local = this.providerSetMultimapKey;
            if (local == null) {
                local = this.providerSetMultimapKey = this.mapKey.ofType(RealMapBinder.mapOfSetOfProviderOf(this.keyType, this.valueType));
            }
            return local;
        }

        private Key<Map<K, Set<jakarta.inject.Provider<V>>>> getJakartaProviderSetMultimapKey() {
            Key<Map<K, Set<jakarta.inject.Provider<V>>>> local = this.jakartaProviderSetMultimapKey;
            if (local == null) {
                local = this.jakartaProviderSetMultimapKey = this.mapKey.ofType(RealMapBinder.mapOfSetOfJakartaProviderOf(this.keyType, this.valueType));
            }
            return local;
        }

        private Key<Map<K, Collection<Provider<V>>>> getProviderCollectionMultimapKey() {
            Key<Map<K, Collection<Provider<V>>>> local = this.providerCollectionMultimapKey;
            if (local == null) {
                local = this.providerCollectionMultimapKey = this.mapKey.ofType(RealMapBinder.mapOfCollectionOfProviderOf(this.keyType, this.valueType));
            }
            return local;
        }

        private Key<Map<K, Collection<jakarta.inject.Provider<V>>>> getJakartaProviderCollectionMultimapKey() {
            Key<Map<K, Collection<jakarta.inject.Provider<V>>>> local = this.jakartaProviderCollectionMultimapKey;
            if (local == null) {
                local = this.jakartaProviderCollectionMultimapKey = this.mapKey.ofType(RealMapBinder.mapOfCollectionOfJakartaProviderOf(this.keyType, this.valueType));
            }
            return local;
        }

        private Key<Set<Map.Entry<K, jakarta.inject.Provider<V>>>> getEntrySetJakartaProviderKey() {
            Key<Set<Map.Entry<K, jakarta.inject.Provider<V>>>> local = this.entrySetJakartaProviderKey;
            if (local == null) {
                local = this.entrySetJakartaProviderKey = this.mapKey.ofType(RealMapBinder.setOfEntryOfJakartaProviderOf(this.keyType, this.valueType));
            }
            return local;
        }

        private Key<Map<K, ? extends V>> getMapOfKeyExtendsValueKey() {
            Key<Map<K, Object>> local = this.mapOfKeyExtendsValueKey;
            if (local == null) {
                WildcardType extendsValue = Types.subtypeOf(this.valueType.getType());
                ParameterizedType mapOfKeyAndExtendsValue = Types.mapOf(this.keyType.getType(), extendsValue);
                local = this.mapOfKeyExtendsValueKey = this.mapKey.ofType(mapOfKeyAndExtendsValue);
            }
            return local;
        }

        private ImmutableMap<K, Binding<V>> getMapBindings() {
            Errors.checkConfiguration(this.isInitialized(), "MapBinder has not yet been initialized", new Object[0]);
            return this.mapBindings;
        }

        private ImmutableMap<K, Set<Binding<V>>> getMultimapBindings() {
            Errors.checkConfiguration(this.isInitialized(), "MapBinder has not yet been initialized", new Object[0]);
            return this.multimapBindings;
        }

        private ImmutableList<Map.Entry<K, Binding<V>>> getEntries() {
            Errors.checkConfiguration(this.isInitialized(), "MapBinder has not yet been initialized", new Object[0]);
            return this.entries;
        }

        private boolean isInitialized() {
            return this.initializationState == InitializationState.INITIALIZED;
        }

        private TypeLiteral<K> getKeyType() {
            return this.keyType;
        }

        private TypeLiteral<V> getValueType() {
            return this.valueType;
        }

        private Key<Map<K, V>> getMapKey() {
            return this.mapKey;
        }

        private RealMultibinder<Map.Entry<K, Provider<V>>> getEntrySetBinder() {
            return this.entrySetBinder;
        }

        private boolean permitsDuplicates() {
            if (this.isInitialized()) {
                return this.permitsDuplicates;
            }
            throw new UnsupportedOperationException("permitsDuplicates() not supported for module bindings");
        }

        public boolean equals(Object o) {
            return o instanceof BindingSelection && ((BindingSelection)o).mapKey.equals(this.mapKey);
        }

        public int hashCode() {
            return this.mapKey.hashCode();
        }

        private static enum InitializationState {
            UNINITIALIZED,
            INITIALIZED,
            HAS_ERRORS;

        }
    }
}

