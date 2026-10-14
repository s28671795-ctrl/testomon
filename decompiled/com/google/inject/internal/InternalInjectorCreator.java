/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.common.base.Stopwatch;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.internal.BindingImpl;
import com.google.inject.internal.DeferredLookups;
import com.google.inject.internal.Errors;
import com.google.inject.internal.Initializer;
import com.google.inject.internal.InjectionRequestProcessor;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.InjectorShell;
import com.google.inject.internal.InternalContext;
import com.google.inject.internal.InternalProvisionException;
import com.google.inject.internal.LinkedBindingImpl;
import com.google.inject.internal.LookupProcessor;
import com.google.inject.internal.ProcessedBindingData;
import com.google.inject.internal.util.ContinuousStopwatch;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.Element;
import com.google.inject.spi.InjectionPoint;
import com.google.inject.spi.TypeConverterBinding;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class InternalInjectorCreator {
    private final ContinuousStopwatch stopwatch = new ContinuousStopwatch(Stopwatch.createUnstarted());
    private final Errors errors = new Errors();
    private final Initializer initializer = new Initializer();
    private final ProcessedBindingData processedBindingData;
    private final InjectionRequestProcessor injectionRequestProcessor;
    private final InjectorShell.Builder shellBuilder = new InjectorShell.Builder();
    private List<InjectorShell> shells;

    public InternalInjectorCreator() {
        this.injectionRequestProcessor = new InjectionRequestProcessor(this.errors, this.initializer);
        this.processedBindingData = new ProcessedBindingData();
    }

    public InternalInjectorCreator stage(Stage stage) {
        this.shellBuilder.stage(stage);
        return this;
    }

    public InternalInjectorCreator parentInjector(InjectorImpl parent) {
        this.shellBuilder.parent(parent);
        return this;
    }

    public InternalInjectorCreator addModules(Iterable<? extends Module> modules) {
        this.shellBuilder.addModules(modules);
        return this;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Injector build() {
        if (this.shellBuilder == null) {
            throw new AssertionError((Object)"Already built, builders are not reusable.");
        }
        Object object = this.shellBuilder.lock();
        synchronized (object) {
            this.shells = this.shellBuilder.build(this.initializer, this.processedBindingData, this.stopwatch, this.errors);
            this.stopwatch.resetAndLog("Injector construction");
            this.initializeStatically();
        }
        this.injectDynamically();
        if (this.shellBuilder.getStage() == Stage.TOOL) {
            return new ToolStageInjector(this.primaryInjector());
        }
        return this.primaryInjector();
    }

    private void initializeStatically() {
        this.processedBindingData.initializeBindings();
        this.stopwatch.resetAndLog("Binding initialization");
        for (InjectorShell shell : this.shells) {
            shell.getInjector().getBindingData().indexBindingsByType();
        }
        this.stopwatch.resetAndLog("Binding indexing");
        this.injectionRequestProcessor.process(this.shells);
        this.stopwatch.resetAndLog("Collecting injection requests");
        this.processedBindingData.runCreationListeners(this.errors);
        this.stopwatch.resetAndLog("Binding validation");
        this.injectionRequestProcessor.validate();
        this.stopwatch.resetAndLog("Static validation");
        this.initializer.validateOustandingInjections(this.errors);
        this.stopwatch.resetAndLog("Instance member validation");
        new LookupProcessor(this.errors).process(this.shells);
        for (InjectorShell shell : this.shells) {
            ((DeferredLookups)shell.getInjector().lookups).initialize(this.errors);
        }
        this.stopwatch.resetAndLog("Provider verification");
        this.processedBindingData.initializeDelayedBindings();
        this.stopwatch.resetAndLog("Delayed Binding initialization");
        for (InjectorShell shell : this.shells) {
            if (!shell.getElements().isEmpty()) {
                throw new AssertionError((Object)("Failed to execute " + shell.getElements()));
            }
        }
        this.errors.throwCreationExceptionIfErrorsExist();
    }

    private Injector primaryInjector() {
        return this.shells.get(0).getInjector();
    }

    private void injectDynamically() {
        this.injectionRequestProcessor.injectMembers();
        this.stopwatch.resetAndLog("Static member injection");
        this.initializer.injectAll(this.errors);
        this.stopwatch.resetAndLog("Instance injection");
        this.errors.throwCreationExceptionIfErrorsExist();
        if (this.shellBuilder.getStage() != Stage.TOOL) {
            for (InjectorShell shell : this.shells) {
                this.loadEagerSingletons(shell.getInjector(), this.shellBuilder.getStage(), this.errors);
            }
            this.stopwatch.resetAndLog("Preloading singletons");
        }
        this.errors.throwCreationExceptionIfErrorsExist();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void loadEagerSingletons(InjectorImpl injector, Stage stage, Errors errors2) {
        ArrayList candidateBindings = new ArrayList();
        Collection<Binding<?>> bindingsAtThisLevel = injector.getBindingData().getExplicitBindingsThisLevel().values();
        candidateBindings.addAll(bindingsAtThisLevel);
        Object object = injector.getJitBindingData().lock();
        synchronized (object) {
            candidateBindings.addAll(injector.getJitBindingData().getJitBindings().values());
        }
        try (InternalContext context = injector.enterContext();){
            for (BindingImpl bindingImpl : candidateBindings) {
                if (!this.isEagerSingleton(injector, bindingImpl, stage)) continue;
                Dependency dependency = Dependency.get(bindingImpl.getKey());
                try {
                    bindingImpl.getInternalFactory().get(context, dependency, false);
                }
                catch (InternalProvisionException e) {
                    errors2.withSource(dependency).merge(e);
                }
            }
        }
    }

    private boolean isEagerSingleton(InjectorImpl injector, BindingImpl<?> binding, Stage stage) {
        if (binding.getScoping().isEagerSingleton(stage)) {
            return true;
        }
        if (binding instanceof LinkedBindingImpl) {
            Key linkedBinding = ((LinkedBindingImpl)binding).getLinkedKey();
            return binding.getScoping().isNoScope() && this.isEagerSingleton(injector, (BindingImpl<?>)injector.getBinding(linkedBinding), stage);
        }
        return false;
    }

    static class ToolStageInjector
    implements Injector {
        private final Injector delegateInjector;

        ToolStageInjector(Injector delegateInjector) {
            this.delegateInjector = delegateInjector;
        }

        @Override
        public void injectMembers(Object o) {
            throw new UnsupportedOperationException("Injector.injectMembers(Object) is not supported in Stage.TOOL");
        }

        @Override
        public Map<Key<?>, Binding<?>> getBindings() {
            return this.delegateInjector.getBindings();
        }

        @Override
        public Map<Key<?>, Binding<?>> getAllBindings() {
            return this.delegateInjector.getAllBindings();
        }

        @Override
        public <T> Binding<T> getBinding(Key<T> key) {
            return this.delegateInjector.getBinding(key);
        }

        @Override
        public <T> Binding<T> getBinding(Class<T> type) {
            return this.delegateInjector.getBinding(type);
        }

        @Override
        public <T> Binding<T> getExistingBinding(Key<T> key) {
            return this.delegateInjector.getExistingBinding(key);
        }

        @Override
        public <T> List<Binding<T>> findBindingsByType(TypeLiteral<T> type) {
            return this.delegateInjector.findBindingsByType(type);
        }

        @Override
        public Injector getParent() {
            return this.delegateInjector.getParent();
        }

        @Override
        public Injector createChildInjector(Iterable<? extends Module> modules) {
            return this.delegateInjector.createChildInjector(modules);
        }

        @Override
        public Injector createChildInjector(Module ... modules) {
            return this.delegateInjector.createChildInjector(modules);
        }

        @Override
        public Map<Class<? extends Annotation>, Scope> getScopeBindings() {
            return this.delegateInjector.getScopeBindings();
        }

        @Override
        public Set<TypeConverterBinding> getTypeConverterBindings() {
            return this.delegateInjector.getTypeConverterBindings();
        }

        @Override
        public List<Element> getElements() {
            return this.delegateInjector.getElements();
        }

        @Override
        public Map<TypeLiteral<?>, List<InjectionPoint>> getAllMembersInjectorInjectionPoints() {
            return this.delegateInjector.getAllMembersInjectorInjectionPoints();
        }

        @Override
        public <T> Provider<T> getProvider(Key<T> key) {
            throw new UnsupportedOperationException("Injector.getProvider(Key<T>) is not supported in Stage.TOOL");
        }

        @Override
        public <T> Provider<T> getProvider(Class<T> type) {
            throw new UnsupportedOperationException("Injector.getProvider(Class<T>) is not supported in Stage.TOOL");
        }

        @Override
        public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral) {
            throw new UnsupportedOperationException("Injector.getMembersInjector(TypeLiteral<T>) is not supported in Stage.TOOL");
        }

        @Override
        public <T> MembersInjector<T> getMembersInjector(Class<T> type) {
            throw new UnsupportedOperationException("Injector.getMembersInjector(Class<T>) is not supported in Stage.TOOL");
        }

        @Override
        public <T> T getInstance(Key<T> key) {
            throw new UnsupportedOperationException("Injector.getInstance(Key<T>) is not supported in Stage.TOOL");
        }

        @Override
        public <T> T getInstance(Class<T> type) {
            throw new UnsupportedOperationException("Injector.getInstance(Class<T>) is not supported in Stage.TOOL");
        }
    }
}

