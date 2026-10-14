/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.spi;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.PrivateBinder;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.AnnotatedElementBuilder;
import com.google.inject.internal.AbstractBindingBuilder;
import com.google.inject.internal.BindingBuilder;
import com.google.inject.internal.ConstantBindingBuilderImpl;
import com.google.inject.internal.Errors;
import com.google.inject.internal.ExposureBuilder;
import com.google.inject.internal.GuiceInternal;
import com.google.inject.internal.InternalClassesToSkipSources;
import com.google.inject.internal.InternalFlags;
import com.google.inject.internal.MoreTypes;
import com.google.inject.internal.PrivateElementsImpl;
import com.google.inject.internal.ProviderMethod;
import com.google.inject.internal.ProviderMethodsModule;
import com.google.inject.internal.util.SourceProvider;
import com.google.inject.internal.util.StackTraceElements;
import com.google.inject.matcher.Matcher;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.OptionalBinder;
import com.google.inject.spi.BindingSourceRestriction;
import com.google.inject.spi.BindingTargetVisitor;
import com.google.inject.spi.DefaultBindingTargetVisitor;
import com.google.inject.spi.Dependency;
import com.google.inject.spi.DisableCircularProxiesOption;
import com.google.inject.spi.Element;
import com.google.inject.spi.ElementSource;
import com.google.inject.spi.InjectionRequest;
import com.google.inject.spi.InstanceBinding;
import com.google.inject.spi.InterceptorBinding;
import com.google.inject.spi.MembersInjectorLookup;
import com.google.inject.spi.Message;
import com.google.inject.spi.ModuleAnnotatedMethodScanner;
import com.google.inject.spi.ModuleAnnotatedMethodScannerBinding;
import com.google.inject.spi.ModuleSource;
import com.google.inject.spi.ProviderLookup;
import com.google.inject.spi.ProvisionListener;
import com.google.inject.spi.ProvisionListenerBinding;
import com.google.inject.spi.RequireAtInjectOnConstructorsOption;
import com.google.inject.spi.RequireExactBindingAnnotationsOption;
import com.google.inject.spi.RequireExplicitBindingsOption;
import com.google.inject.spi.ScopeBinding;
import com.google.inject.spi.StaticInjectionRequest;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeConverterBinding;
import com.google.inject.spi.TypeListener;
import com.google.inject.spi.TypeListenerBinding;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.aopalliance.intercept.MethodInterceptor;

public final class Elements {
    private static final BindingTargetVisitor<Object, Object> GET_INSTANCE_VISITOR = new DefaultBindingTargetVisitor<Object, Object>(){

        @Override
        public Object visit(InstanceBinding<?> binding) {
            return binding.getInstance();
        }

        @Override
        protected Object visitOther(Binding<?> binding) {
            throw new IllegalArgumentException();
        }
    };

    public static List<Element> getElements(Module ... modules) {
        return Elements.getElements(Stage.DEVELOPMENT, Arrays.asList(modules));
    }

    public static List<Element> getElements(Stage stage, Module ... modules) {
        return Elements.getElements(stage, Arrays.asList(modules));
    }

    public static List<Element> getElements(Iterable<? extends Module> modules) {
        return Elements.getElements(Stage.DEVELOPMENT, modules);
    }

    public static List<Element> getElements(Stage stage, Iterable<? extends Module> modules) {
        RecordingBinder binder = new RecordingBinder(stage);
        for (Module module : modules) {
            binder.install(module);
        }
        binder.scanForAnnotatedMethods();
        for (RecordingBinder recordingBinder : binder.privateBindersForScanning) {
            recordingBinder.scanForAnnotatedMethods();
        }
        binder.permitMapConstruction.finish();
        StackTraceElements.clearCache();
        return Collections.unmodifiableList(binder.elements);
    }

    public static Binder withTrustedSource(GuiceInternal guiceInternal, Binder binder, Object source2) {
        Preconditions.checkNotNull(guiceInternal);
        if (binder instanceof RecordingBinder) {
            return ((RecordingBinder)binder).withTrustedSource(source2);
        }
        return binder.withSource(source2);
    }

    public static Module getModule(Iterable<? extends Element> elements) {
        return new ElementsAsModule(elements);
    }

    static <T> BindingTargetVisitor<T, T> getInstanceVisitor() {
        return GET_INSTANCE_VISITOR;
    }

    private static class RecordingBinder
    implements Binder,
    PrivateBinder {
        private static final ImmutableSet<Class<?>> CLASSES_TO_SKIP = ((ImmutableSet.Builder)((ImmutableSet.Builder)ImmutableSet.builder().add(new Class[]{Elements.class, RecordingBinder.class, AbstractModule.class, ConstantBindingBuilderImpl.class, AbstractBindingBuilder.class, BindingBuilder.class, MapBinder.class, Multibinder.class, OptionalBinder.class})).addAll(InternalClassesToSkipSources.classesToSkipSources())).build();
        private static final SourceProvider DEFAULT_SOURCE_PROVIDER = SourceProvider.DEFAULT_INSTANCE.plusSkippedClasses(CLASSES_TO_SKIP.toArray(new Class[0]));
        private final Stage stage;
        private final Map<Module, ModuleInfo> modules;
        private final List<Element> elements;
        private final Object source;
        private final SourceProvider sourceProvider;
        private final Set<ModuleAnnotatedMethodScanner> scanners;
        private final RecordingBinder parent;
        private final PrivateElementsImpl privateElements;
        private final List<RecordingBinder> privateBindersForScanning;
        private final BindingSourceRestriction.PermitMapConstruction permitMapConstruction;
        private ModuleSource moduleSource = null;
        private ModuleAnnotatedMethodScanner scannerSource = null;
        private ModuleAnnotatedMethodScanner currentScanner = null;
        private boolean trustedSource = false;

        private RecordingBinder(Stage stage) {
            this.stage = stage;
            this.modules = Maps.newLinkedHashMap();
            this.scanners = Sets.newLinkedHashSet();
            this.elements = Lists.newArrayList();
            this.source = null;
            this.sourceProvider = DEFAULT_SOURCE_PROVIDER;
            this.parent = null;
            this.privateElements = null;
            this.privateBindersForScanning = Lists.newArrayList();
            this.permitMapConstruction = new BindingSourceRestriction.PermitMapConstruction();
        }

        private RecordingBinder(RecordingBinder prototype, Object source2, SourceProvider sourceProvider, boolean trustedSource) {
            Preconditions.checkArgument(source2 == null ^ sourceProvider == null);
            this.stage = prototype.stage;
            this.modules = prototype.modules;
            this.elements = prototype.elements;
            this.scanners = prototype.scanners;
            this.currentScanner = prototype.currentScanner;
            this.source = source2;
            this.trustedSource = trustedSource;
            this.moduleSource = prototype.moduleSource;
            this.sourceProvider = sourceProvider;
            this.parent = prototype.parent;
            this.privateElements = prototype.privateElements;
            this.privateBindersForScanning = prototype.privateBindersForScanning;
            this.permitMapConstruction = prototype.permitMapConstruction;
            this.scannerSource = prototype.scannerSource;
        }

        private RecordingBinder(RecordingBinder parent, PrivateElementsImpl privateElements) {
            this.stage = parent.stage;
            this.modules = Maps.newLinkedHashMap();
            this.scanners = Sets.newLinkedHashSet();
            this.currentScanner = parent.currentScanner;
            this.elements = privateElements.getElementsMutable();
            this.source = parent.source;
            this.moduleSource = parent.moduleSource;
            this.sourceProvider = parent.sourceProvider;
            this.parent = parent;
            this.privateElements = privateElements;
            this.privateBindersForScanning = parent.privateBindersForScanning;
            this.permitMapConstruction = parent.permitMapConstruction;
            this.scannerSource = parent.scannerSource;
        }

        @Override
        public void bindInterceptor(Matcher<? super Class<?>> classMatcher, Matcher<? super Method> methodMatcher, MethodInterceptor ... interceptors) {
            this.elements.add(new InterceptorBinding(this.getElementSource(), classMatcher, methodMatcher, interceptors));
        }

        @Override
        public void bindScope(Class<? extends Annotation> annotationType, Scope scope) {
            this.elements.add(new ScopeBinding(this.getElementSource(), annotationType, scope));
        }

        @Override
        public void requestInjection(Object instance) {
            Preconditions.checkNotNull(instance, "instance");
            this.requestInjection(TypeLiteral.get(instance.getClass()), instance);
        }

        @Override
        public <T> void requestInjection(TypeLiteral<T> type, T instance) {
            Preconditions.checkNotNull(instance, "instance");
            this.elements.add(new InjectionRequest<T>(this.getElementSource(), MoreTypes.canonicalizeForKey(type), instance));
        }

        @Override
        public <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> typeLiteral) {
            MembersInjectorLookup<T> element = new MembersInjectorLookup<T>(this.getElementSource(), MoreTypes.canonicalizeForKey(typeLiteral));
            this.elements.add(element);
            return element.getMembersInjector();
        }

        @Override
        public <T> MembersInjector<T> getMembersInjector(Class<T> type) {
            return this.getMembersInjector(TypeLiteral.get(type));
        }

        @Override
        public void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener) {
            this.elements.add(new TypeListenerBinding(this.getElementSource(), listener, typeMatcher));
        }

        @Override
        public void bindListener(Matcher<? super Binding<?>> bindingMatcher, ProvisionListener ... listeners) {
            this.elements.add(new ProvisionListenerBinding(this.getElementSource(), bindingMatcher, listeners));
        }

        @Override
        public void requestStaticInjection(Class<?> ... types) {
            for (Class<?> type : types) {
                this.elements.add(new StaticInjectionRequest(this.getElementSource(), type));
            }
        }

        void scanForAnnotatedMethods() {
            Iterable<ModuleAnnotatedMethodScanner> scanners = this.getAllScanners();
            for (Map.Entry<Module, ModuleInfo> entry : Maps.newLinkedHashMap(this.modules).entrySet()) {
                Module module = entry.getKey();
                ModuleInfo info = entry.getValue();
                if (info.skipScanning) continue;
                Iterator<ModuleAnnotatedMethodScanner> iterator2 = scanners.iterator();
                while (iterator2.hasNext()) {
                    ModuleAnnotatedMethodScanner scanner;
                    this.currentScanner = scanner = iterator2.next();
                    this.moduleSource = entry.getValue().moduleSource;
                    this.permitMapConstruction.restoreCurrentModulePermits(this.moduleSource);
                    try {
                        this.install(ProviderMethodsModule.forModule(module, scanner));
                    }
                    catch (RuntimeException e) {
                        Collection<Message> messages = Errors.getMessagesFromThrowable(e);
                        if (!messages.isEmpty()) {
                            this.elements.addAll(messages);
                            continue;
                        }
                        this.addError(e);
                    }
                }
            }
            this.moduleSource = null;
        }

        @Override
        public void install(Module module) {
            if (this.modules.containsKey(module)) {
                return;
            }
            boolean customScanner = false;
            Class<?> newModuleClass = null;
            RecordingBinder binder = this;
            if (module instanceof ProviderMethodsModule) {
                ProviderMethodsModule providerMethodsModule = (ProviderMethodsModule)module;
                if (!providerMethodsModule.isScanningBuiltInProvidesMethods()) {
                    this.scannerSource = providerMethodsModule.getScanner();
                    customScanner = true;
                }
                Class<?> delegateClass = providerMethodsModule.getDelegateModuleClass();
                if (this.moduleSource == null || !this.moduleSource.getModuleClassName().equals(delegateClass.getName())) {
                    newModuleClass = delegateClass;
                }
            } else {
                if (this.moduleScanning()) {
                    this.forbidNestedScannerMethods(module);
                }
                newModuleClass = module.getClass();
            }
            if (newModuleClass != null) {
                this.moduleSource = this.getModuleSource(newModuleClass);
                this.permitMapConstruction.pushModule(newModuleClass, this.moduleSource);
            }
            boolean skipScanning = false;
            if (module instanceof PrivateModule) {
                binder = (RecordingBinder)binder.newPrivateBinder();
                binder.modules.put(module, new ModuleInfo(this.moduleSource, false));
                skipScanning = true;
            }
            this.modules.put(module, new ModuleInfo(this.moduleSource, skipScanning));
            try {
                module.configure(binder);
            }
            catch (RuntimeException e) {
                Collection<Message> messages = Errors.getMessagesFromThrowable(e);
                if (!messages.isEmpty()) {
                    this.elements.addAll(messages);
                }
                this.addError(e);
            }
            binder.install(ProviderMethodsModule.forModule(module));
            if (newModuleClass != null) {
                this.moduleSource = this.moduleSource.getParent();
                this.permitMapConstruction.popModule();
            }
            if (customScanner) {
                this.scannerSource = null;
            }
        }

        private void forbidNestedScannerMethods(Module module) {
            for (ModuleAnnotatedMethodScanner scanner : this.getAllScanners()) {
                ProviderMethodsModule providerMethodsModule = (ProviderMethodsModule)ProviderMethodsModule.forModule(module, scanner);
                for (ProviderMethod<?> method2 : providerMethodsModule.getProviderMethods(this)) {
                    this.addError("Scanner %s is installing a module with %s method. Installing modules with custom provides methods from a ModuleAnnotatedMethodScanner is not supported.", this.currentScanner, method2.getAnnotation().annotationType().getCanonicalName());
                }
            }
        }

        private Iterable<ModuleAnnotatedMethodScanner> getAllScanners() {
            if (this.privateElements == null) {
                return this.scanners;
            }
            return Iterables.concat(this.scanners, this.parent.getAllScanners());
        }

        @Override
        public Stage currentStage() {
            return this.stage;
        }

        @Override
        public void addError(String message, Object ... arguments) {
            this.elements.add(new Message(this.getElementSource(), Errors.format(message, arguments)));
        }

        @Override
        public void addError(Throwable t2) {
            String message = "An exception was caught and reported. Message: " + t2.getMessage();
            this.elements.add(new Message(ImmutableList.of(this.getElementSource()), message, t2));
        }

        @Override
        public void addError(Message message) {
            this.elements.add(message);
        }

        public <T> AnnotatedBindingBuilder<T> bind(Key<T> key) {
            BindingBuilder<T> builder = new BindingBuilder<T>(this, this.elements, this.getElementSource(), MoreTypes.canonicalizeKey(key));
            return builder;
        }

        @Override
        public <T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral) {
            return this.bind((Key)Key.get(typeLiteral));
        }

        @Override
        public <T> AnnotatedBindingBuilder<T> bind(Class<T> type) {
            return this.bind((Key)Key.get(type));
        }

        @Override
        public AnnotatedConstantBindingBuilder bindConstant() {
            return new ConstantBindingBuilderImpl(this, this.elements, this.getElementSource());
        }

        @Override
        public <T> Provider<T> getProvider(Key<T> key) {
            return this.getProvider(Dependency.get(key));
        }

        @Override
        public <T> Provider<T> getProvider(Dependency<T> dependency) {
            ProviderLookup<T> element = new ProviderLookup<T>((Object)this.getElementSource(), dependency);
            this.elements.add(element);
            return element.getProvider();
        }

        @Override
        public <T> Provider<T> getProvider(Class<T> type) {
            return this.getProvider(Key.get(type));
        }

        @Override
        public void convertToTypes(Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter) {
            this.elements.add(new TypeConverterBinding(this.getElementSource(), typeMatcher, converter));
        }

        @Override
        public RecordingBinder withSource(Object source2) {
            return source2 == this.source ? this : new RecordingBinder(this, source2, null, false);
        }

        public RecordingBinder withTrustedSource(Object source2) {
            return source2 == this.source ? this : new RecordingBinder(this, source2, null, true);
        }

        @Override
        public RecordingBinder skipSources(Class<?> ... classesToSkip) {
            if (this.source != null) {
                return this;
            }
            SourceProvider newSourceProvider = this.sourceProvider.plusSkippedClasses(classesToSkip);
            return new RecordingBinder(this, null, newSourceProvider, false);
        }

        @Override
        public PrivateBinder newPrivateBinder() {
            PrivateElementsImpl privateElements = new PrivateElementsImpl(this.getElementSource());
            RecordingBinder binder = new RecordingBinder(this, privateElements);
            this.elements.add(privateElements);
            if (!this.moduleScanning()) {
                this.privateBindersForScanning.add(binder);
            }
            return binder;
        }

        @Override
        public void disableCircularProxies() {
            this.elements.add(new DisableCircularProxiesOption(this.getElementSource()));
        }

        @Override
        public void requireExplicitBindings() {
            this.elements.add(new RequireExplicitBindingsOption(this.getElementSource()));
        }

        @Override
        public void requireAtInjectOnConstructors() {
            this.elements.add(new RequireAtInjectOnConstructorsOption(this.getElementSource()));
        }

        @Override
        public void requireExactBindingAnnotations() {
            this.elements.add(new RequireExactBindingAnnotationsOption(this.getElementSource()));
        }

        @Override
        public void scanModulesForAnnotatedMethods(ModuleAnnotatedMethodScanner scanner) {
            if (this.moduleScanning()) {
                this.addError("Attempting to register ModuleAnnotatedMethodScanner %s from scanner %s. Scanners are not allowed to register other scanners.", this.currentScanner, scanner);
                return;
            }
            this.scanners.add(scanner);
            this.elements.add(new ModuleAnnotatedMethodScannerBinding(this.getElementSource(), scanner));
        }

        @Override
        public void expose(Key<?> key) {
            this.exposeInternal(key);
        }

        @Override
        public AnnotatedElementBuilder expose(Class<?> type) {
            return this.exposeInternal(Key.get(type));
        }

        @Override
        public AnnotatedElementBuilder expose(TypeLiteral<?> type) {
            return this.exposeInternal(Key.get(type));
        }

        private <T> AnnotatedElementBuilder exposeInternal(Key<T> key) {
            if (this.privateElements == null) {
                this.addError("Cannot expose %s on a standard binder. Exposed bindings are only applicable to private binders.", key);
                return new AnnotatedElementBuilder(this){

                    @Override
                    public void annotatedWith(Class<? extends Annotation> annotationType) {
                    }

                    @Override
                    public void annotatedWith(Annotation annotation) {
                    }
                };
            }
            ExposureBuilder<T> builder = new ExposureBuilder<T>(this, this.getElementSource(), MoreTypes.canonicalizeKey(key));
            this.privateElements.addExposureBuilder(builder);
            return builder;
        }

        private ModuleSource getModuleSource(Class<?> module) {
            if (this.moduleSource == null) {
                return new ModuleSource(module, this.permitMapConstruction.getPermitMap());
            }
            return this.moduleSource.createChild(module);
        }

        private ElementSource getElementSource() {
            ElementSource originalSource = null;
            Object declaringSource = this.source;
            if (declaringSource instanceof ElementSource) {
                originalSource = (ElementSource)declaringSource;
                declaringSource = originalSource.getDeclaringSource();
            }
            if (declaringSource == null) {
                StackTraceElement callingSource;
                InternalFlags.IncludeStackTraceOption stackTraceOption = InternalFlags.getIncludeStackTraceOption();
                declaringSource = stackTraceOption == InternalFlags.IncludeStackTraceOption.ONLY_FOR_DECLARING_SOURCE ? ((callingSource = this.sourceProvider.getCaller()).getClassName().equals("com.google.inject.internal.InjectorShell$Builder") && callingSource.getMethodName().equals("build") ? SourceProvider.UNKNOWN_SOURCE : callingSource) : this.sourceProvider.getFromClassNames(this.moduleSource.getModuleClassNames());
            }
            return new ElementSource(originalSource, this.trustedSource, declaringSource, this.moduleSource, this.scannerSource);
        }

        private boolean moduleScanning() {
            return this.currentScanner != null;
        }

        public String toString() {
            return "Binder";
        }
    }

    private static class ModuleInfo {
        private final ModuleSource moduleSource;
        private final boolean skipScanning;

        private ModuleInfo(ModuleSource moduleSource, boolean skipScanning) {
            this.moduleSource = moduleSource;
            this.skipScanning = skipScanning;
        }
    }

    private static class ElementsAsModule
    implements Module {
        private final Iterable<? extends Element> elements;

        ElementsAsModule(Iterable<? extends Element> elements) {
            this.elements = elements;
        }

        @Override
        public void configure(Binder binder) {
            for (Element element : this.elements) {
                element.applyTo(binder);
            }
        }
    }
}

