/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.internal.BindingProcessor;
import com.google.inject.internal.ConstantFactory;
import com.google.inject.internal.Errors;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.internal.Initializables;
import com.google.inject.internal.Initializer;
import com.google.inject.internal.InjectorBindingData;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InjectorJitBindingData;
import com.google.inject.internal.InjectorOptionsProcessor;
import com.google.inject.internal.InstanceBindingImpl;
import com.google.inject.internal.InterceptorBindingProcessor;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalFactory;
import com.google.inject.internal.ListenerBindingProcessor;
import com.google.inject.internal.MembersInjectorStore;
import com.google.inject.internal.MessageProcessor;
import com.google.inject.internal.ModuleAnnotatedMethodScannerProcessor;
import com.google.inject.internal.PrivateElementProcessor;
import com.google.inject.internal.PrivateElementsImpl;
import com.google.inject.internal.ProcessedBindingData;
import com.google.inject.internal.ProviderInstanceBindingImpl;
import com.google.inject.internal.ProvisionListenerCallbackStore;
import com.google.inject.internal.ScopeBindingProcessor;
import com.google.inject.internal.Scoping;
import com.google.inject.internal.TypeConverterBindingProcessor;
import com.google.inject.internal.UntargettedBindingProcessor;
import com.google.inject.internal.util.ContinuousStopwatch;
import com.google.inject.internal.util.SourceProvider;
import com.google.inject.spi.BindingSourceRestriction;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Element;
import com.google.inject.spi.Elements;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.ModuleAnnotatedMethodScannerBinding;
import com.google.inject.spi.PrivateElements;
import com.google.inject.spi.ProvisionListenerBinding;
import com.google.inject.spi.TypeListenerBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

final class InjectorShell {
    private final List<Element> elements;
    private final InjectorImpl injector;

    private InjectorShell(List<Element> elements, InjectorImpl injector) {
        this.elements = elements;
        this.injector = injector;
    }

    InjectorImpl getInjector() {
        return this.injector;
    }

    List<Element> getElements() {
        return this.elements;
    }

    private static void bindInjector(InjectorImpl injector) {
        Key<Injector> key = Key.get(Injector.class);
        InjectorFactory injectorFactory = new InjectorFactory(injector);
        injector.getBindingData().putBinding(key, new ProviderInstanceBindingImpl<Injector>(injector, key, SourceProvider.UNKNOWN_SOURCE, injectorFactory, Scoping.UNSCOPED, injectorFactory, ImmutableSet.of()));
    }

    private static void bindLogger(InjectorImpl injector) {
        Key<Logger> key = Key.get(Logger.class);
        LoggerFactory loggerFactory = new LoggerFactory();
        injector.getBindingData().putBinding(key, new ProviderInstanceBindingImpl<Logger>(injector, key, SourceProvider.UNKNOWN_SOURCE, loggerFactory, Scoping.UNSCOPED, loggerFactory, ImmutableSet.of()));
    }

    private static void bindStage(InjectorImpl injector, Stage stage) {
        Key<Stage> key = Key.get(Stage.class);
        InstanceBindingImpl<Stage> stageBinding = new InstanceBindingImpl<Stage>(injector, key, SourceProvider.UNKNOWN_SOURCE, new ConstantFactory<Stage>(Initializables.of(stage)), ImmutableSet.of(), stage);
        injector.getBindingData().putBinding(key, stageBinding);
    }

    private static class InheritedScannersModule
    implements Module {
        private final InjectorBindingData bindingData;

        InheritedScannersModule(InjectorBindingData bindingData) {
            this.bindingData = bindingData;
        }

        @Override
        public void configure(Binder binder) {
            for (ModuleAnnotatedMethodScannerBinding binding : this.bindingData.getScannerBindings()) {
                binding.applyTo(binder);
            }
        }
    }

    private static class RootModule
    implements Module {
        private RootModule() {
        }

        @Override
        public void configure(Binder binder) {
            binder = binder.withSource(SourceProvider.UNKNOWN_SOURCE);
            binder.bindScope(Singleton.class, Scopes.SINGLETON);
            binder.bindScope(jakarta.inject.Singleton.class, Scopes.SINGLETON);
        }
    }

    private static class LoggerFactory
    implements InternalFactory<Logger>,
    Provider<Logger> {
        private LoggerFactory() {
        }

        @Override
        public Logger get(InternalContext context, Dependency<?> dependency, boolean linked) {
            InjectionPoint injectionPoint = dependency.getInjectionPoint();
            return injectionPoint == null ? Logger.getAnonymousLogger() : Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
        }

        @Override
        public Logger get() {
            return Logger.getAnonymousLogger();
        }

        public String toString() {
            return "Provider<Logger>";
        }
    }

    private static class InjectorFactory
    implements InternalFactory<Injector>,
    Provider<Injector> {
        private final Injector injector;

        private InjectorFactory(Injector injector) {
            this.injector = injector;
        }

        @Override
        public Injector get(InternalContext context, Dependency<?> dependency, boolean linked) {
            return this.injector;
        }

        @Override
        public Injector get() {
            return this.injector;
        }

        public String toString() {
            return "Provider<Injector>";
        }
    }

    static class Builder {
        private final List<Element> elements = Lists.newArrayList();
        private final List<Module> modules = Lists.newArrayList();
        private InjectorBindingData bindingData;
        private InjectorJitBindingData jitBindingData;
        private InjectorImpl parent;
        private InjectorImpl.InjectorOptions options;
        private Stage stage;
        private PrivateElementsImpl privateElements;

        Builder() {
        }

        Builder stage(Stage stage) {
            this.stage = stage;
            return this;
        }

        Builder parent(InjectorImpl parent) {
            this.parent = parent;
            this.jitBindingData = new InjectorJitBindingData(Optional.of(parent.getJitBindingData()));
            this.bindingData = new InjectorBindingData(Optional.of(parent.getBindingData()));
            this.options = parent.options;
            this.stage = this.options.stage;
            return this;
        }

        Builder privateElements(PrivateElements privateElements) {
            this.privateElements = (PrivateElementsImpl)privateElements;
            this.elements.addAll(privateElements.getElements());
            return this;
        }

        void addModules(Iterable<? extends Module> modules) {
            for (Module module : modules) {
                this.modules.add(module);
            }
        }

        Stage getStage() {
            return this.options.stage;
        }

        Object lock() {
            if (this.bindingData == null) {
                this.jitBindingData = new InjectorJitBindingData(Optional.empty());
                this.bindingData = new InjectorBindingData(Optional.empty());
            }
            return this.jitBindingData.lock();
        }

        List<InjectorShell> build(Initializer initializer, ProcessedBindingData processedBindingData, ContinuousStopwatch stopwatch, Errors errors2) {
            Preconditions.checkState(this.stage != null, "Stage not initialized");
            Preconditions.checkState(this.privateElements == null || this.parent != null, "PrivateElements with no parent");
            Preconditions.checkState(this.bindingData != null, "no binding data. Did you remember to lock() ?");
            Preconditions.checkState(this.privateElements == null && this.elements.isEmpty() || this.modules.isEmpty(), "The shell is either built from modules (root) or from PrivateElements (children).");
            if (this.parent == null) {
                this.modules.add(0, new RootModule());
            } else {
                this.modules.add(0, new InheritedScannersModule(this.parent.getBindingData()));
            }
            this.elements.addAll(Elements.getElements(this.stage, this.modules));
            if (this.privateElements == null) {
                this.elements.addAll(BindingSourceRestriction.check(GuiceInternal.GUICE_INTERNAL, this.elements));
            }
            InjectorOptionsProcessor optionsProcessor = new InjectorOptionsProcessor(errors2);
            optionsProcessor.process(null, this.elements);
            this.options = optionsProcessor.getOptions(this.stage, this.options);
            InjectorImpl injector = new InjectorImpl(this.parent, this.bindingData, this.jitBindingData, this.options);
            if (this.privateElements != null) {
                this.privateElements.initInjector(injector);
            }
            if (this.parent == null) {
                TypeConverterBindingProcessor.prepareBuiltInConverters(injector);
            }
            stopwatch.resetAndLog("Module execution");
            new MessageProcessor(errors2).process(injector, this.elements);
            new InterceptorBindingProcessor(errors2).process(injector, this.elements);
            stopwatch.resetAndLog("Interceptors creation");
            new ListenerBindingProcessor(errors2).process(injector, this.elements);
            ImmutableList<TypeListenerBinding> typeListenerBindings = injector.getBindingData().getTypeListenerBindings();
            injector.membersInjectorStore = new MembersInjectorStore(injector, typeListenerBindings);
            ImmutableList<ProvisionListenerBinding> provisionListenerBindings = injector.getBindingData().getProvisionListenerBindings();
            injector.provisionListenerStore = new ProvisionListenerCallbackStore(provisionListenerBindings);
            stopwatch.resetAndLog("TypeListeners & ProvisionListener creation");
            new ScopeBindingProcessor(errors2).process(injector, this.elements);
            stopwatch.resetAndLog("Scopes creation");
            new TypeConverterBindingProcessor(errors2).process(injector, this.elements);
            stopwatch.resetAndLog("Converters creation");
            InjectorShell.bindStage(injector, this.stage);
            InjectorShell.bindInjector(injector);
            InjectorShell.bindLogger(injector);
            new BindingProcessor(errors2, initializer, processedBindingData).process(injector, this.elements);
            new UntargettedBindingProcessor(errors2, processedBindingData).process(injector, this.elements);
            stopwatch.resetAndLog("Binding creation");
            new ModuleAnnotatedMethodScannerProcessor(errors2).process(injector, this.elements);
            stopwatch.resetAndLog("Module annotated method scanners creation");
            ArrayList<InjectorShell> injectorShells = Lists.newArrayList();
            injectorShells.add(new InjectorShell(this.elements, injector));
            PrivateElementProcessor processor = new PrivateElementProcessor(errors2);
            processor.process(injector, this.elements);
            for (Builder builder : processor.getInjectorShellBuilders()) {
                injectorShells.addAll(builder.build(initializer, processedBindingData, stopwatch, errors2));
            }
            stopwatch.resetAndLog("Private environment creation");
            return injectorShells;
        }
    }
}

