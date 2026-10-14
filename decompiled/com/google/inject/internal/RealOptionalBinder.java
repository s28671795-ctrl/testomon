/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.internal.Annotations;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ErrorsException;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.InternalProviderInstanceBindingImpl;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.LinkedBindingImpl;
import com.google.inject.multibindings.MultibindingsTargetVisitor;
import com.google.inject.multibindings.OptionalBinderBinding;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Element;
import com.google.inject.spi.ProviderInstanceBinding;
import com.google.inject.spi.ProviderWithExtensionVisitor;
import com.google.inject.util.Types;
import jakarta.inject.Qualifier;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Set;

public final class RealOptionalBinder<T>
implements Module {
    private final BindingSelection<T> bindingSelection;
    private final Binder binder;

    public static <T> RealOptionalBinder<T> newRealOptionalBinder(Binder binder, Key<T> type) {
        binder = binder.skipSources(RealOptionalBinder.class);
        RealOptionalBinder<T> optionalBinder = new RealOptionalBinder<T>(binder, type);
        binder.install(optionalBinder);
        return optionalBinder;
    }

    static <T> TypeLiteral<com.google.common.base.Optional<T>> optionalOf(TypeLiteral<T> type) {
        return TypeLiteral.get(Types.newParameterizedType(com.google.common.base.Optional.class, new Type[]{type.getType()}));
    }

    static <T> TypeLiteral<Optional<T>> javaOptionalOf(TypeLiteral<T> type) {
        return TypeLiteral.get(Types.newParameterizedType(Optional.class, new Type[]{type.getType()}));
    }

    static <T> TypeLiteral<com.google.common.base.Optional<jakarta.inject.Provider<T>>> optionalOfJakartaProvider(TypeLiteral<T> type) {
        return TypeLiteral.get(Types.newParameterizedType(com.google.common.base.Optional.class, new Type[]{Types.newParameterizedType(jakarta.inject.Provider.class, new Type[]{type.getType()})}));
    }

    static <T> TypeLiteral<Optional<jakarta.inject.Provider<T>>> javaOptionalOfJakartaProvider(TypeLiteral<T> type) {
        return TypeLiteral.get(Types.newParameterizedType(Optional.class, new Type[]{Types.newParameterizedType(jakarta.inject.Provider.class, new Type[]{type.getType()})}));
    }

    static <T> TypeLiteral<com.google.common.base.Optional<Provider<T>>> optionalOfProvider(TypeLiteral<T> type) {
        return TypeLiteral.get(Types.newParameterizedType(com.google.common.base.Optional.class, new Type[]{Types.newParameterizedType(Provider.class, new Type[]{type.getType()})}));
    }

    static <T> TypeLiteral<Optional<Provider<T>>> javaOptionalOfProvider(TypeLiteral<T> type) {
        return TypeLiteral.get(Types.newParameterizedType(Optional.class, new Type[]{Types.newParameterizedType(Provider.class, new Type[]{type.getType()})}));
    }

    static <T> Key<Provider<T>> providerOf(Key<T> key) {
        ParameterizedType providerT = Types.providerOf(key.getTypeLiteral().getType());
        return key.ofType(providerT);
    }

    private RealOptionalBinder(Binder binder, Key<T> typeKey) {
        this.bindingSelection = new BindingSelection<T>(typeKey);
        this.binder = binder;
    }

    private void addDirectTypeBinding(Binder binder) {
        binder.bind(this.bindingSelection.getDirectKey()).toProvider(new RealDirectTypeProvider<T>(this.bindingSelection));
    }

    Key<T> getKeyForDefaultBinding() {
        this.bindingSelection.checkNotInitialized();
        this.addDirectTypeBinding(this.binder);
        return this.bindingSelection.getKeyForDefaultBinding();
    }

    public LinkedBindingBuilder<T> setDefault() {
        return this.binder.bind(this.getKeyForDefaultBinding());
    }

    Key<T> getKeyForActualBinding() {
        this.bindingSelection.checkNotInitialized();
        this.addDirectTypeBinding(this.binder);
        return this.bindingSelection.getKeyForActualBinding();
    }

    public LinkedBindingBuilder<T> setBinding() {
        return this.binder.bind(this.getKeyForActualBinding());
    }

    @Override
    public void configure(Binder binder) {
        this.bindingSelection.checkNotInitialized();
        Key<T> key = this.bindingSelection.getDirectKey();
        TypeLiteral<T> typeLiteral = key.getTypeLiteral();
        Key<com.google.common.base.Optional<Provider<T>>> guavaOptProviderKey = key.ofType(RealOptionalBinder.optionalOfProvider(typeLiteral));
        binder.bind(guavaOptProviderKey).toProvider(new RealOptionalProviderProvider<T>(this.bindingSelection));
        Key<Optional<Provider<T>>> javaOptProviderKey = key.ofType(RealOptionalBinder.javaOptionalOfProvider(typeLiteral));
        binder.bind(javaOptProviderKey).toProvider(new JavaOptionalProviderProvider<T>(this.bindingSelection));
        binder.bind(key.ofType(RealOptionalBinder.optionalOfJakartaProvider(typeLiteral))).to(guavaOptProviderKey);
        binder.bind(key.ofType(RealOptionalBinder.javaOptionalOfJakartaProvider(typeLiteral))).to(javaOptProviderKey);
        Key<com.google.common.base.Optional<T>> guavaOptKey = key.ofType(RealOptionalBinder.optionalOf(typeLiteral));
        binder.bind(guavaOptKey).toProvider(new RealOptionalKeyProvider<T>(this.bindingSelection, guavaOptKey));
        Key<Optional<T>> javaOptKey = key.ofType(RealOptionalBinder.javaOptionalOf(typeLiteral));
        binder.bind(javaOptKey).toProvider(new JavaOptionalProvider<T>(this.bindingSelection, javaOptKey));
    }

    public boolean equals(Object o) {
        return o instanceof RealOptionalBinder && ((RealOptionalBinder)o).bindingSelection.equals(this.bindingSelection);
    }

    public int hashCode() {
        return this.bindingSelection.hashCode();
    }

    static abstract class BaseAnnotation
    implements Serializable,
    Annotation {
        private final String value;
        private final Class<? extends Annotation> clazz;
        private static final long serialVersionUID = 0L;

        BaseAnnotation(Class<? extends Annotation> clazz, String value) {
            this.clazz = Preconditions.checkNotNull(clazz, "clazz");
            this.value = Preconditions.checkNotNull(value, "value");
        }

        public String value() {
            return this.value;
        }

        @Override
        public int hashCode() {
            return 127 * "value".hashCode() ^ this.value.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Actual && this.clazz == Actual.class) {
                Actual other = (Actual)o;
                return this.value.equals(other.value());
            }
            if (o instanceof Default && this.clazz == Default.class) {
                Default other = (Default)o;
                return this.value.equals(other.value());
            }
            return false;
        }

        @Override
        public String toString() {
            return "@" + this.clazz.getName() + (this.value.isEmpty() ? "" : "(value=" + this.value + ")");
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return this.clazz;
        }
    }

    static class ActualImpl
    extends BaseAnnotation
    implements Actual {
        public ActualImpl(String value) {
            super(Actual.class, value);
        }
    }

    static class DefaultImpl
    extends BaseAnnotation
    implements Default {
        public DefaultImpl(String value) {
            super(Default.class, value);
        }
    }

    private static abstract class RealOptionalBinderProviderWithDependencies<T, P>
    extends InternalProviderInstanceBindingImpl.Factory<P> {
        protected final BindingSelection<T> bindingSelection;
        private boolean initialized = false;

        RealOptionalBinderProviderWithDependencies(BindingSelection<T> bindingSelection) {
            super(InternalProviderInstanceBindingImpl.InitializationTiming.DELAYED);
            this.bindingSelection = bindingSelection;
        }

        @Override
        final void initialize(InjectorImpl injector, Errors errors2) throws ErrorsException {
            if (!this.initialized) {
                this.initialized = true;
                this.bindingSelection.initialize(injector, errors2);
                this.doInitialize();
            }
        }

        abstract void doInitialize();

        public boolean equals(Object obj) {
            return obj != null && this.getClass() == obj.getClass() && this.bindingSelection.equals(((RealOptionalBinderProviderWithDependencies)obj).bindingSelection);
        }

        public int hashCode() {
            return this.bindingSelection.hashCode();
        }
    }

    private static final class BindingSelection<T> {
        private static final ImmutableSet<Dependency<?>> MODULE_DEPENDENCIES = ImmutableSet.of(Dependency.get(Key.get(Injector.class)));
        BindingImpl<T> actualBinding;
        BindingImpl<T> defaultBinding;
        BindingImpl<T> binding;
        private boolean initialized;
        private final Key<T> key;
        private ImmutableSet<Dependency<?>> dependencies = MODULE_DEPENDENCIES;
        private ImmutableSet<Dependency<?>> providerDependencies = MODULE_DEPENDENCIES;
        private String bindingName;
        private Key<T> defaultBindingKey;
        private Key<T> actualBindingKey;

        BindingSelection(Key<T> key) {
            this.key = key;
        }

        void checkNotInitialized() {
            Errors.checkConfiguration(!this.initialized, "already initialized", new Object[0]);
        }

        void initialize(InjectorImpl injector, Errors errors2) {
            if (this.initialized) {
                return;
            }
            this.actualBinding = injector.getExistingBinding((Key)this.getKeyForActualBinding());
            this.defaultBinding = injector.getExistingBinding((Key)this.getKeyForDefaultBinding());
            Binding userBinding = injector.getExistingBinding((Key)this.key);
            if (this.actualBinding != null) {
                this.binding = this.actualBinding;
            } else if (this.defaultBinding != null) {
                this.binding = this.defaultBinding;
            } else if (userBinding != null) {
                this.binding = userBinding;
                this.actualBinding = userBinding;
            }
            if (this.binding != null) {
                this.dependencies = ImmutableSet.of(Dependency.get(this.binding.getKey()));
                this.providerDependencies = ImmutableSet.of(Dependency.get(RealOptionalBinder.providerOf(this.binding.getKey())));
            } else {
                this.dependencies = ImmutableSet.of();
                this.providerDependencies = ImmutableSet.of();
            }
            this.checkBindingIsNotRecursive(errors2);
            this.initialized = true;
        }

        private void checkBindingIsNotRecursive(Errors errors2) {
            LinkedBindingImpl linkedBindingImpl;
            if (this.binding instanceof LinkedBindingImpl && (linkedBindingImpl = (LinkedBindingImpl)this.binding).getLinkedKey().equals(this.key)) {
                errors2.recursiveBinding(this.key, linkedBindingImpl.getLinkedKey());
            }
        }

        Key<T> getKeyForDefaultBinding() {
            if (this.defaultBindingKey == null) {
                this.defaultBindingKey = this.key.withAnnotation(new DefaultImpl(this.getBindingName()));
            }
            return this.defaultBindingKey;
        }

        Key<T> getKeyForActualBinding() {
            if (this.actualBindingKey == null) {
                this.actualBindingKey = this.key.withAnnotation(new ActualImpl(this.getBindingName()));
            }
            return this.actualBindingKey;
        }

        Key<T> getDirectKey() {
            return this.key;
        }

        private String getBindingName() {
            if (this.bindingName == null) {
                this.bindingName = Annotations.nameOf(this.key);
            }
            return this.bindingName;
        }

        BindingImpl<T> getBinding() {
            return this.binding;
        }

        BindingImpl<T> getDefaultBinding() {
            return this.defaultBinding;
        }

        BindingImpl<T> getActualBinding() {
            return this.actualBinding;
        }

        ImmutableSet<Dependency<?>> providerDependencies() {
            return this.providerDependencies;
        }

        ImmutableSet<Dependency<?>> dependencies() {
            return this.dependencies;
        }

        Dependency<?> getDependency() {
            return Iterables.getOnlyElement(this.dependencies);
        }

        boolean containsElement(Element element) {
            jakarta.inject.Provider providerInstance;
            if (element instanceof ProviderInstanceBinding && (providerInstance = ((ProviderInstanceBinding)element).getUserSuppliedProvider()) instanceof RealOptionalBinderProviderWithDependencies) {
                return ((RealOptionalBinderProviderWithDependencies)providerInstance).bindingSelection.equals(this);
            }
            if (element instanceof Binding) {
                TypeLiteral<T> typeLiteral = this.key.getTypeLiteral();
                Key elementKey = ((Binding)element).getKey();
                return elementKey.equals(this.getKeyForActualBinding()) || elementKey.equals(this.getKeyForDefaultBinding()) || elementKey.equals(this.key.ofType(RealOptionalBinder.javaOptionalOfJakartaProvider(typeLiteral))) || elementKey.equals(this.key.ofType(RealOptionalBinder.optionalOfJakartaProvider(typeLiteral)));
            }
            return false;
        }

        public boolean equals(Object o) {
            return o instanceof BindingSelection && ((BindingSelection)o).key.equals(this.key);
        }

        public int hashCode() {
            return this.key.hashCode();
        }
    }

    private static final class RealOptionalKeyProvider<T>
    extends RealOptionalBinderProviderWithDependencies<T, com.google.common.base.Optional<T>>
    implements ProviderWithExtensionVisitor<com.google.common.base.Optional<T>>,
    OptionalBinderBinding<com.google.common.base.Optional<T>> {
        private final Key<com.google.common.base.Optional<T>> optionalKey;
        private Dependency<?> targetDependency;
        private InternalFactory<? extends T> delegate;

        RealOptionalKeyProvider(BindingSelection<T> bindingSelection, Key<com.google.common.base.Optional<T>> optionalKey) {
            super(bindingSelection);
            this.optionalKey = optionalKey;
        }

        @Override
        void doInitialize() {
            if (this.bindingSelection.getBinding() != null) {
                this.delegate = this.bindingSelection.getBinding().getInternalFactory();
                this.targetDependency = this.bindingSelection.getDependency();
            }
        }

        @Override
        protected com.google.common.base.Optional<T> doProvision(InternalContext context, Dependency<?> currentDependency) throws InternalProvisionException {
            T result;
            InternalFactory<T> local = this.delegate;
            if (local == null) {
                return com.google.common.base.Optional.absent();
            }
            Dependency<?> localDependency = this.targetDependency;
            try {
                result = local.get(context, localDependency, false);
            }
            catch (InternalProvisionException ipe) {
                throw ipe.addSource(localDependency);
            }
            return com.google.common.base.Optional.fromNullable(result);
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.bindingSelection.dependencies();
        }

        @Override
        public <B, R> R acceptExtensionVisitor(BindingTargetVisitor<B, R> visitor, ProviderInstanceBinding<? extends B> binding) {
            if (visitor instanceof MultibindingsTargetVisitor) {
                return (R)((MultibindingsTargetVisitor)visitor).visit(this);
            }
            return visitor.visit(binding);
        }

        @Override
        public Key<com.google.common.base.Optional<T>> getKey() {
            return this.optionalKey;
        }

        @Override
        public Set<Key<?>> getAlternateKeys() {
            Key key = this.bindingSelection.getDirectKey();
            TypeLiteral typeLiteral = key.getTypeLiteral();
            return ImmutableSet.of(key.ofType(RealOptionalBinder.optionalOfProvider(typeLiteral)), key.ofType(RealOptionalBinder.optionalOfJakartaProvider(typeLiteral)));
        }

        @Override
        public Binding<?> getActualBinding() {
            return this.bindingSelection.getActualBinding();
        }

        @Override
        public Binding<?> getDefaultBinding() {
            return this.bindingSelection.getDefaultBinding();
        }

        @Override
        public boolean containsElement(Element element) {
            return this.bindingSelection.containsElement(element);
        }
    }

    private static final class RealOptionalProviderProvider<T>
    extends RealOptionalBinderProviderWithDependencies<T, com.google.common.base.Optional<Provider<T>>> {
        private com.google.common.base.Optional<Provider<T>> value;

        RealOptionalProviderProvider(BindingSelection<T> bindingSelection) {
            super(bindingSelection);
        }

        @Override
        void doInitialize() {
            this.value = this.bindingSelection.getBinding() == null ? com.google.common.base.Optional.absent() : com.google.common.base.Optional.of(this.bindingSelection.getBinding().getProvider());
        }

        @Override
        protected com.google.common.base.Optional<Provider<T>> doProvision(InternalContext context, Dependency<?> dependency) {
            return this.value;
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.bindingSelection.providerDependencies();
        }
    }

    private static final class RealDirectTypeProvider<T>
    extends RealOptionalBinderProviderWithDependencies<T, T> {
        private Key<? extends T> targetKey;
        private InternalFactory<? extends T> targetFactory;

        RealDirectTypeProvider(BindingSelection<T> bindingSelection) {
            super(bindingSelection);
        }

        @Override
        void doInitialize() {
            BindingImpl targetBinding = this.bindingSelection.getBinding();
            this.targetKey = targetBinding.getKey();
            this.targetFactory = targetBinding.getInternalFactory();
        }

        @Override
        protected T doProvision(InternalContext context, Dependency<?> dependency) throws InternalProvisionException {
            try {
                return this.targetFactory.get(context, dependency, true);
            }
            catch (InternalProvisionException ipe) {
                throw ipe.addSource(this.targetKey);
            }
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.bindingSelection.dependencies;
        }
    }

    private static final class JavaOptionalProviderProvider<T>
    extends RealOptionalBinderProviderWithDependencies<T, Optional<Provider<T>>> {
        private Optional<Provider<T>> value;

        JavaOptionalProviderProvider(BindingSelection<T> bindingSelection) {
            super(bindingSelection);
        }

        @Override
        void doInitialize() {
            this.value = this.bindingSelection.getBinding() == null ? Optional.empty() : Optional.of(this.bindingSelection.getBinding().getProvider());
        }

        @Override
        protected Optional<Provider<T>> doProvision(InternalContext context, Dependency<?> dependency) {
            return this.value;
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.bindingSelection.providerDependencies();
        }
    }

    private static final class JavaOptionalProvider<T>
    extends RealOptionalBinderProviderWithDependencies<T, Optional<T>>
    implements ProviderWithExtensionVisitor<Optional<T>>,
    OptionalBinderBinding<Optional<T>> {
        private final Key<Optional<T>> optionalKey;
        private Dependency<?> targetDependency;
        private InternalFactory<? extends T> target;

        JavaOptionalProvider(BindingSelection<T> bindingSelection, Key<Optional<T>> optionalKey) {
            super(bindingSelection);
            this.optionalKey = optionalKey;
        }

        @Override
        void doInitialize() {
            if (this.bindingSelection.getBinding() != null) {
                this.target = this.bindingSelection.getBinding().getInternalFactory();
                this.targetDependency = this.bindingSelection.getDependency();
            }
        }

        @Override
        protected Optional<T> doProvision(InternalContext context, Dependency<?> currentDependency) throws InternalProvisionException {
            T result;
            InternalFactory<T> local = this.target;
            if (local == null) {
                return Optional.empty();
            }
            Dependency<?> localDependency = this.targetDependency;
            try {
                result = local.get(context, localDependency, false);
            }
            catch (InternalProvisionException ipe) {
                throw ipe.addSource(localDependency);
            }
            return Optional.ofNullable(result);
        }

        @Override
        public Set<Dependency<?>> getDependencies() {
            return this.bindingSelection.dependencies;
        }

        @Override
        public <B, R> R acceptExtensionVisitor(BindingTargetVisitor<B, R> visitor, ProviderInstanceBinding<? extends B> binding) {
            if (visitor instanceof MultibindingsTargetVisitor) {
                return (R)((MultibindingsTargetVisitor)visitor).visit(this);
            }
            return visitor.visit(binding);
        }

        @Override
        public boolean containsElement(Element element) {
            return this.bindingSelection.containsElement(element);
        }

        @Override
        public Binding<?> getActualBinding() {
            return this.bindingSelection.getActualBinding();
        }

        @Override
        public Binding<?> getDefaultBinding() {
            return this.bindingSelection.getDefaultBinding();
        }

        @Override
        public Key<Optional<T>> getKey() {
            return this.optionalKey;
        }

        @Override
        public Set<Key<?>> getAlternateKeys() {
            Key key = this.bindingSelection.getDirectKey();
            TypeLiteral typeLiteral = key.getTypeLiteral();
            return ImmutableSet.of(key.ofType(RealOptionalBinder.javaOptionalOfProvider(typeLiteral)), key.ofType(RealOptionalBinder.javaOptionalOfJakartaProvider(typeLiteral)));
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Qualifier
    static @interface Actual {
        public String value();
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Qualifier
    static @interface Default {
        public String value();
    }

    static enum Source {
        DEFAULT,
        ACTUAL;

    }
}

