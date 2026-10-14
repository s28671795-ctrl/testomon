/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.internal.Annotations;
import com.google.inject.internal.DuplicateElementError;
import com.google.inject.internal.Element;
import com.google.inject.internal.ErrorId;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.internal.Indexer;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProviderInstanceBindingImpl;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.RealElement;
import com.google.inject.internal.SingleParameterInjector;
import com.google.inject.multibindings.MultibinderBinding;
import com.google.inject.multibindings.MultibindingsTargetVisitor;
import com.google.inject.name.Names;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Message;
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
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public final class RealMultibinder<T>
implements Module {
    private final BindingSelection<T> bindingSelection;
    private final Binder binder;

    public static <T> RealMultibinder<T> newRealSetBinder(Binder binder, Key<T> key) {
        RealMultibinder<T> result = new RealMultibinder<T>(binder, key);
        binder.install(result);
        return result;
    }

    static <T> TypeLiteral<Set<T>> setOf(TypeLiteral<T> elementType) {
        ParameterizedType type = Types.setOf(elementType.getType());
        return TypeLiteral.get(type);
    }

    static <T> TypeLiteral<Collection<Provider<T>>> collectionOfProvidersOf(TypeLiteral<T> elementType) {
        ParameterizedType providerType = Types.providerOf(elementType.getType());
        ParameterizedType type = Types.collectionOf(providerType);
        return TypeLiteral.get(type);
    }

    static <T> TypeLiteral<Collection<jakarta.inject.Provider<T>>> collectionOfJakartaProvidersOf(TypeLiteral<T> elementType) {
        ParameterizedType providerType = Types.newParameterizedType(jakarta.inject.Provider.class, new Type[]{elementType.getType()});
        ParameterizedType type = Types.collectionOf(providerType);
        return TypeLiteral.get(type);
    }

    static <T> TypeLiteral<Set<? extends T>> setOfExtendsOf(TypeLiteral<T> elementType) {
        WildcardType extendsType = Types.subtypeOf(elementType.getType());
        ParameterizedType setOfExtendsType = Types.setOf(extendsType);
        return TypeLiteral.get(setOfExtendsType);
    }

    RealMultibinder(Binder binder, Key<T> key) {
        this.binder = Errors.checkNotNull(binder, "binder");
        this.bindingSelection = new BindingSelection<T>(key);
    }

    @Override
    public void configure(Binder binder) {
        Errors.checkConfiguration(!this.bindingSelection.isInitialized(), "Multibinder was already initialized", new Object[0]);
        binder.bind(this.bindingSelection.getSetKey()).toProvider(new RealMultibinderProvider<T>(this.bindingSelection));
        binder.bind(this.bindingSelection.getSetOfExtendsKey()).to(this.bindingSelection.getSetKey());
        binder.bind(this.bindingSelection.getCollectionOfProvidersKey()).toProvider(new RealMultibinderCollectionOfProvidersProvider<T>(this.bindingSelection));
        binder.bind(this.bindingSelection.getCollectionOfJakartaProvidersKey()).to(this.bindingSelection.getCollectionOfProvidersKey());
    }

    public void permitDuplicates() {
        this.binder.install(new PermitDuplicatesModule(this.bindingSelection.getPermitDuplicatesKey()));
    }

    Key<T> getKeyForNewItem() {
        Errors.checkConfiguration(!this.bindingSelection.isInitialized(), "Multibinder was already initialized", new Object[0]);
        return Key.get(this.bindingSelection.getElementTypeLiteral(), (Annotation)new RealElement(this.bindingSelection.getSetName(), Element.Type.MULTIBINDER, ""));
    }

    public LinkedBindingBuilder<T> addBinding() {
        return this.binder.bind(this.getKeyForNewItem());
    }

    Key<Set<T>> getSetKey() {
        return this.bindingSelection.getSetKey();
    }

    TypeLiteral<T> getElementTypeLiteral() {
        return this.bindingSelection.getElementTypeLiteral();
    }

    String getSetName() {
        return this.bindingSelection.getSetName();
    }

    boolean permitsDuplicates(Injector injector) {
        return this.bindingSelection.permitsDuplicates(injector);
    }

    boolean containsElement(com.google.inject.spi.Element element) {
        return this.bindingSelection.containsElement(element);
    }

    public boolean equals(Object o) {
        return o instanceof RealMultibinder && ((RealMultibinder)o).bindingSelection.equals(this.bindingSelection);
    }

    public int hashCode() {
        return this.bindingSelection.hashCode();
    }

    private static class PermitDuplicatesModule
    extends AbstractModule {
        private final Key<Boolean> key;

        PermitDuplicatesModule(Key<Boolean> key) {
            this.key = key;
        }

        @Override
        protected void configure() {
            this.bind(this.key).toInstance(true);
        }

        public boolean equals(Object o) {
            return o instanceof PermitDuplicatesModule && ((PermitDuplicatesModule)o).key.equals(this.key);
        }

        public int hashCode() {
            return this.getClass().hashCode() ^ this.key.hashCode();
        }
    }

    private static final class BindingSelection<T> {
        private static final ImmutableSet<Dependency<?>> MODULE_DEPENDENCIES = ImmutableSet.of(Dependency.get(Key.get(Injector.class)));
        private final TypeLiteral<T> elementType;
        private final Key<Set<T>> setKey;
        private String setName;
        private Key<Collection<Provider<T>>> collectionOfProvidersKey;
        private Key<Collection<jakarta.inject.Provider<T>>> collectionOfJakartaProvidersKey;
        private Key<Set<? extends T>> setOfExtendsKey;
        private Key<Boolean> permitDuplicatesKey;
        private boolean isInitialized;
        private ImmutableList<Binding<T>> bindings;
        private ImmutableSet<Dependency<?>> dependencies = MODULE_DEPENDENCIES;
        private ImmutableSet<Dependency<?>> providerDependencies = MODULE_DEPENDENCIES;
        private boolean permitDuplicates;
        private SingleParameterInjector<T>[] parameterinjectors;

        BindingSelection(Key<T> key) {
            this.setKey = key.ofType(RealMultibinder.setOf(key.getTypeLiteral()));
            this.elementType = key.getTypeLiteral();
        }

        void initialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
            if (this.isInitialized) {
                return;
            }
            ArrayList<Binding<T>> bindings = Lists.newArrayList();
            HashSet<Indexer.IndexedBinding> index = Sets.newHashSet();
            Indexer indexer = new Indexer(injector);
            ArrayList<Dependency<?>> dependencies = Lists.newArrayList();
            ArrayList<Dependency<?>> providerDependencies = Lists.newArrayList();
            for (Binding<Object> binding : injector.findBindingsByType(this.elementType)) {
                Binding<Object> binding2;
                if (!this.keyMatches(binding.getKey()) || !index.add((binding2 = binding).acceptTargetVisitor(indexer))) continue;
                bindings.add(binding2);
                Key<T> key = binding2.getKey();
                dependencies.add(Dependency.get(key));
                providerDependencies.add(Dependency.get(key.ofType(Types.providerOf(key.getTypeLiteral().getType()))));
            }
            this.bindings = ImmutableList.copyOf(bindings);
            this.dependencies = ImmutableSet.copyOf(dependencies);
            this.providerDependencies = ImmutableSet.copyOf(providerDependencies);
            this.permitDuplicates = this.permitsDuplicates(injector);
            SingleParameterInjector<?>[] typed = injector.getParametersInjectors(dependencies, errors2);
            this.parameterinjectors = typed;
            this.isInitialized = true;
        }

        boolean permitsDuplicates(Injector injector) {
            return injector.getBindings().containsKey(this.getPermitDuplicatesKey());
        }

        ImmutableList<Binding<T>> getBindings() {
            Errors.checkConfiguration(this.isInitialized, "not initialized", new Object[0]);
            return this.bindings;
        }

        SingleParameterInjector<T>[] getParameterInjectors() {
            Errors.checkConfiguration(this.isInitialized, "not initialized", new Object[0]);
            return this.parameterinjectors;
        }

        ImmutableSet<Dependency<?>> getDependencies() {
            return this.dependencies;
        }

        ImmutableSet<Dependency<?>> getProviderDependencies() {
            return this.providerDependencies;
        }

        String getSetName() {
            if (this.setName == null) {
                this.setName = Annotations.nameOf(this.setKey);
            }
            return this.setName;
        }

        Key<Boolean> getPermitDuplicatesKey() {
            Key<Boolean> local = this.permitDuplicatesKey;
            if (local == null) {
                local = this.permitDuplicatesKey = Key.get(Boolean.class, (Annotation)Names.named(this.toString() + " permits duplicates"));
            }
            return local;
        }

        Key<Collection<Provider<T>>> getCollectionOfProvidersKey() {
            Key<Collection<Provider<Object>>> local = this.collectionOfProvidersKey;
            if (local == null) {
                local = this.collectionOfProvidersKey = this.setKey.ofType(RealMultibinder.collectionOfProvidersOf(this.elementType));
            }
            return local;
        }

        Key<Collection<jakarta.inject.Provider<T>>> getCollectionOfJakartaProvidersKey() {
            Key<Collection<jakarta.inject.Provider<Object>>> local = this.collectionOfJakartaProvidersKey;
            if (local == null) {
                local = this.collectionOfJakartaProvidersKey = this.setKey.ofType(RealMultibinder.collectionOfJakartaProvidersOf(this.elementType));
            }
            return local;
        }

        Key<Set<? extends T>> getSetOfExtendsKey() {
            Key<Set<Object>> local = this.setOfExtendsKey;
            if (local == null) {
                local = this.setOfExtendsKey = this.setKey.ofType(RealMultibinder.setOfExtendsOf(this.elementType));
            }
            return local;
        }

        boolean isInitialized() {
            return this.isInitialized;
        }

        TypeLiteral<T> getElementTypeLiteral() {
            return this.elementType;
        }

        Key<Set<T>> getSetKey() {
            return this.setKey;
        }

        List<Binding<?>> getElements() {
            if (this.isInitialized()) {
                return this.bindings;
            }
            throw new UnsupportedOperationException("getElements() not supported for module bindings");
        }

        boolean permitsDuplicates() {
            if (this.isInitialized()) {
                return this.permitDuplicates;
            }
            throw new UnsupportedOperationException("permitsDuplicates() not supported for module bindings");
        }

        boolean containsElement(com.google.inject.spi.Element element) {
            if (element instanceof Binding) {
                Binding binding = (Binding)element;
                return this.keyMatches(binding.getKey()) || binding.getKey().equals(this.getPermitDuplicatesKey()) || binding.getKey().equals(this.setKey) || binding.getKey().equals(this.collectionOfProvidersKey) || binding.getKey().equals(this.collectionOfJakartaProvidersKey) || binding.getKey().equals(this.setOfExtendsKey);
            }
            return false;
        }

        private boolean keyMatches(Key<?> key) {
            return key.getTypeLiteral().equals(this.elementType) && key.getAnnotation() instanceof Element && ((Element)key.getAnnotation()).setName().equals(this.getSetName()) && ((Element)key.getAnnotation()).type() == Element.Type.MULTIBINDER;
        }

        public boolean equals(Object obj) {
            if (obj instanceof BindingSelection) {
                return this.setKey.equals(((BindingSelection)obj).setKey);
            }
            return false;
        }

        public int hashCode() {
            return this.setKey.hashCode();
        }

        public String toString() {
            return (this.getSetName().isEmpty() ? "" : this.getSetName() + " ") + "Multibinder<" + this.elementType + ">";
        }
    }

    private static final class RealMultibinderCollectionOfProvidersProvider<T>
    extends BaseFactory<T, Collection<Provider<T>>> {
        ImmutableList<Provider<T>> providers;

        RealMultibinderCollectionOfProvidersProvider(BindingSelection<T> bindingSelection) {
            super(bindingSelection, bs -> bs.getProviderDependencies());
        }

        @Override
        protected void doInitialize() {
            ImmutableList.Builder providers = ImmutableList.builder();
            for (Binding binding : this.bindingSelection.getBindings()) {
                providers.add(binding.getProvider());
            }
            this.providers = providers.build();
        }

        @Override
        protected ImmutableList<Provider<T>> doProvision(InternalContext context, Dependency<?> dependency) {
            return this.providers;
        }
    }

    private static final class RealMultibinderProvider<T>
    extends BaseFactory<T, Set<T>>
    implements ProviderWithExtensionVisitor<Set<T>>,
    MultibinderBinding<Set<T>> {
        List<Binding<T>> bindings;
        SingleParameterInjector<T>[] injectors;
        boolean permitDuplicates;

        RealMultibinderProvider(BindingSelection<T> bindingSelection) {
            super(bindingSelection, bs -> bs.getDependencies());
        }

        @Override
        protected void doInitialize() {
            this.bindings = this.bindingSelection.getBindings();
            this.injectors = this.bindingSelection.getParameterInjectors();
            this.permitDuplicates = this.bindingSelection.permitsDuplicates();
        }

        @Override
        protected ImmutableSet<T> doProvision(InternalContext context, Dependency<?> dependency) throws InternalProvisionException {
            SingleParameterInjector<T>[] localInjectors = this.injectors;
            if (localInjectors == null) {
                return ImmutableSet.of();
            }
            Object[] values2 = new Object[localInjectors.length];
            for (int i = 0; i < localInjectors.length; ++i) {
                SingleParameterInjector<T> parameterInjector = localInjectors[i];
                T newValue = parameterInjector.inject(context);
                if (newValue == null) {
                    throw this.newNullEntryException(i);
                }
                values2[i] = newValue;
            }
            ImmutableSet<Object> set = ImmutableSet.copyOf(values2);
            if (!this.permitDuplicates && set.size() < values2.length) {
                throw this.newDuplicateValuesException(values2);
            }
            return set;
        }

        private InternalProvisionException newNullEntryException(int i) {
            return InternalProvisionException.create(ErrorId.NULL_ELEMENT_IN_SET, "Set injection failed due to null element bound at: %s", this.bindings.get(i).getSource());
        }

        private InternalProvisionException newDuplicateValuesException(T[] values2) {
            Message message = new Message(GuiceInternal.GUICE_INTERNAL, ErrorId.DUPLICATE_ELEMENT, new DuplicateElementError(this.bindingSelection.getSetKey(), this.bindings, values2, ImmutableList.of(this.getSource())));
            return new InternalProvisionException(message);
        }

        @Override
        public <B, V> V acceptExtensionVisitor(BindingTargetVisitor<B, V> visitor, ProviderInstanceBinding<? extends B> binding) {
            if (visitor instanceof MultibindingsTargetVisitor) {
                return ((MultibindingsTargetVisitor)visitor).visit(this);
            }
            return visitor.visit(binding);
        }

        @Override
        public Key<Set<T>> getSetKey() {
            return this.bindingSelection.getSetKey();
        }

        @Override
        public ImmutableSet<Key<?>> getAlternateSetKeys() {
            return ImmutableSet.of(this.bindingSelection.getCollectionOfProvidersKey(), this.bindingSelection.getCollectionOfJakartaProvidersKey(), this.bindingSelection.getSetOfExtendsKey());
        }

        @Override
        public TypeLiteral<?> getElementTypeLiteral() {
            return this.bindingSelection.getElementTypeLiteral();
        }

        @Override
        public List<Binding<?>> getElements() {
            return this.bindingSelection.getElements();
        }

        @Override
        public boolean permitsDuplicates() {
            return this.bindingSelection.permitsDuplicates();
        }

        @Override
        public boolean containsElement(com.google.inject.spi.Element element) {
            return this.bindingSelection.containsElement(element);
        }
    }

    private static abstract class BaseFactory<ValueT, ProvidedT>
    extends InternalProviderInstanceBindingImpl.Factory<ProvidedT> {
        final Function<BindingSelection<ValueT>, ImmutableSet<Dependency<?>>> dependenciesFn;
        final BindingSelection<ValueT> bindingSelection;

        BaseFactory(BindingSelection<ValueT> bindingSelection, Function<BindingSelection<ValueT>, ImmutableSet<Dependency<?>>> dependenciesFn) {
            super(InternalProviderInstanceBindingImpl.InitializationTiming.DELAYED);
            this.bindingSelection = bindingSelection;
            this.dependenciesFn = dependenciesFn;
        }

        @Override
        void initialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
            this.bindingSelection.initialize(injector, errors2);
            this.doInitialize();
        }

        abstract void doInitialize();

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.dependenciesFn.apply(this.bindingSelection);
        }

        public boolean equals(Object obj) {
            return this.getClass().isInstance(obj) && this.bindingSelection.equals(((BaseFactory)obj).bindingSelection);
        }

        public int hashCode() {
            return this.bindingSelection.hashCode();
        }
    }
}

