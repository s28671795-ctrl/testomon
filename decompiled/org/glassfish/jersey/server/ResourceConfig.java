/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Configurable;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import org.glassfish.jersey.internal.Errors;
import org.glassfish.jersey.internal.config.ExternalPropertiesConfigurationFactory;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.spi.AutoDiscoverable;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.internal.util.Tokenizer;
import org.glassfish.jersey.model.ContractProvider;
import org.glassfish.jersey.model.internal.CommonConfig;
import org.glassfish.jersey.model.internal.ComponentBag;
import org.glassfish.jersey.model.internal.ManagedObjectsFinalizer;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceFinder;
import org.glassfish.jersey.server.ServerConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.scanning.AnnotationAcceptingListener;
import org.glassfish.jersey.server.internal.scanning.FilesScanner;
import org.glassfish.jersey.server.internal.scanning.PackageNamesScanner;
import org.glassfish.jersey.server.model.Resource;

public class ResourceConfig
extends Application
implements Configurable<ResourceConfig>,
ServerConfig {
    private static final Logger LOGGER = Logger.getLogger(ResourceConfig.class.getName());
    private transient Set<Class<?>> cachedClasses = null;
    private transient Set<Class<?>> cachedClassesView = null;
    private transient Set<Object> cachedSingletons = null;
    private transient Set<Object> cachedSingletonsView = null;
    private transient boolean resetFinders = false;
    private volatile State state;

    public static ResourceConfig forApplication(Application application) {
        return application instanceof ResourceConfig ? (ResourceConfig)application : new WrappingResourceConfig(application, null, null);
    }

    public static ResourceConfig forApplicationClass(Class<? extends Application> applicationClass) {
        return new WrappingResourceConfig(null, applicationClass, null);
    }

    public static ResourceConfig forApplicationClass(Class<? extends Application> applicationClass, Set<Class<?>> defaultClasses) {
        return new WrappingResourceConfig(null, applicationClass, defaultClasses);
    }

    public ResourceConfig() {
        this.state = new State();
    }

    public ResourceConfig(Set<Class<?>> classes) {
        this();
        this.registerClasses(classes);
    }

    public ResourceConfig(Class<?> ... classes) {
        this(Arrays.stream(classes).collect(Collectors.toSet()));
    }

    public ResourceConfig(ResourceConfig original) {
        this.state = new State(original.state);
    }

    public final ResourceConfig addProperties(Map<String, Object> properties) {
        this.state.addProperties(properties);
        return this;
    }

    public ResourceConfig setProperties(Map<String, ?> properties) {
        this.state.setProperties(properties);
        return this;
    }

    @Override
    public ResourceConfig property(String name, Object value) {
        this.state.property(name, value);
        return this;
    }

    @Override
    public ResourceConfig register(Class<?> componentClass) {
        this.invalidateCache();
        this.state.register((Class)componentClass);
        return this;
    }

    @Override
    public ResourceConfig register(Class<?> componentClass, int bindingPriority) {
        this.invalidateCache();
        this.state.register((Class)componentClass, bindingPriority);
        return this;
    }

    @Override
    public ResourceConfig register(Class<?> componentClass, Class<?> ... contracts) {
        this.invalidateCache();
        this.state.register((Class)componentClass, (Class[])contracts);
        return this;
    }

    @Override
    public ResourceConfig register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        this.invalidateCache();
        this.state.register((Class)componentClass, (Map)contracts);
        return this;
    }

    @Override
    public ResourceConfig register(Object component) {
        this.invalidateCache();
        this.state.register(component);
        return this;
    }

    @Override
    public ResourceConfig register(Object component, int bindingPriority) {
        this.invalidateCache();
        this.state.register(component, bindingPriority);
        return this;
    }

    @Override
    public ResourceConfig register(Object component, Class<?> ... contracts) {
        this.invalidateCache();
        this.state.register(component, (Class[])contracts);
        return this;
    }

    @Override
    public ResourceConfig register(Object component, Map<Class<?>, Integer> contracts) {
        this.invalidateCache();
        this.state.register(component, (Map)contracts);
        return this;
    }

    public final ResourceConfig registerClasses(Set<Class<?>> classes) {
        if (classes == null) {
            return this;
        }
        for (Class<?> cls : classes) {
            this.register((Class)cls);
        }
        return this;
    }

    public final ResourceConfig registerClasses(Class<?> ... classes) {
        if (classes == null) {
            return this;
        }
        return this.registerClasses(Arrays.stream(classes).collect(Collectors.toSet()));
    }

    public final ResourceConfig registerInstances(Set<Object> instances) {
        if (instances == null) {
            return this;
        }
        for (Object instance : instances) {
            this.register(instance);
        }
        return this;
    }

    public final ResourceConfig registerInstances(Object ... instances) {
        if (instances == null) {
            return this;
        }
        return this.registerInstances(Arrays.stream(instances).collect(Collectors.toSet()));
    }

    public final ResourceConfig registerResources(Resource ... resources) {
        if (resources == null) {
            return this;
        }
        return this.registerResources(Arrays.stream(resources).collect(Collectors.toSet()));
    }

    public final ResourceConfig registerResources(Set<Resource> resources) {
        if (resources == null) {
            return this;
        }
        this.state.registerResources(resources);
        return this;
    }

    public final ResourceConfig registerFinder(ResourceFinder resourceFinder) {
        if (resourceFinder == null) {
            return this;
        }
        this.invalidateCache();
        this.state.registerFinder(resourceFinder);
        return this;
    }

    public final ResourceConfig setApplicationName(String applicationName) {
        this.state.setApplicationName(applicationName);
        return this;
    }

    public final ResourceConfig setClassLoader(ClassLoader classLoader) {
        this.state.setClassLoader(classLoader);
        return this;
    }

    public final ResourceConfig packages(String ... packages) {
        return this.packages(true, packages);
    }

    public final ResourceConfig packages(boolean recursive, String ... packages) {
        if (packages == null || packages.length == 0) {
            return this;
        }
        return this.registerFinder(new PackageNamesScanner(packages, recursive));
    }

    public final ResourceConfig packages(boolean recursive, ClassLoader classLoader, String ... packages) {
        if (packages == null || packages.length == 0) {
            return this;
        }
        return this.registerFinder(new PackageNamesScanner(classLoader, packages, recursive));
    }

    public final ResourceConfig files(String ... files) {
        return this.files(true, files);
    }

    public final ResourceConfig files(boolean recursive, String ... files) {
        if (files == null || files.length == 0) {
            return this;
        }
        return this.registerFinder(new FilesScanner(files, recursive));
    }

    final void invalidateCache() {
        this.cachedClasses = null;
        this.cachedClassesView = null;
        this.cachedSingletons = null;
        this.cachedSingletonsView = null;
        if (this.resetFinders) {
            for (ResourceFinder finder : this.state.resourceFinders) {
                finder.reset();
            }
            this.resetFinders = false;
        }
    }

    final void lock() {
        State current = this.state;
        if (!(current instanceof ImmutableState)) {
            this.setupApplicationName();
            ExternalPropertiesConfigurationFactory.configure(this.state);
            this.state = new ImmutableState(current);
        }
    }

    @Override
    public final ServerConfig getConfiguration() {
        return this;
    }

    @Override
    public final Map<String, Object> getProperties() {
        return this.state.getProperties();
    }

    @Override
    public final Object getProperty(String name) {
        return this.state.getProperty(name);
    }

    @Override
    public Collection<String> getPropertyNames() {
        return this.state.getPropertyNames();
    }

    @Override
    public final boolean isProperty(String name) {
        return this.state.isProperty(name);
    }

    @Override
    public final Set<Class<?>> getClasses() {
        if (this.cachedClassesView == null) {
            this.cachedClasses = this._getClasses();
            this.cachedClassesView = Collections.unmodifiableSet(this.cachedClasses);
        }
        return this.cachedClassesView;
    }

    @Override
    public final Set<Object> getInstances() {
        return this.getSingletons();
    }

    @Override
    public final Set<Object> getSingletons() {
        if (this.cachedSingletonsView == null) {
            this.cachedSingletons = this._getSingletons();
            this.cachedSingletonsView = Collections.unmodifiableSet(this.cachedSingletons == null ? new HashSet() : this.cachedSingletons);
        }
        return this.cachedSingletonsView;
    }

    final ComponentBag getComponentBag() {
        return this.state.getComponentBag();
    }

    final void configureAutoDiscoverableProviders(InjectionManager injectionManager, Collection<AutoDiscoverable> autoDiscoverables) {
        this.state.configureAutoDiscoverableProviders(injectionManager, autoDiscoverables, false);
    }

    final void configureForcedAutoDiscoverableProviders(InjectionManager injectionManager) {
        this.state.configureAutoDiscoverableProviders(injectionManager, Collections.emptyList(), true);
    }

    final void configureMetaProviders(InjectionManager injectionManager, ManagedObjectsFinalizer finalizer) {
        this.state.configureMetaProviders(injectionManager, finalizer);
    }

    @Override
    public RuntimeType getRuntimeType() {
        return this.state.getRuntimeType();
    }

    @Override
    public boolean isEnabled(Feature feature) {
        return this.state.isEnabled(feature);
    }

    @Override
    public boolean isEnabled(Class<? extends Feature> featureClass) {
        return this.state.isEnabled(featureClass);
    }

    @Override
    public boolean isRegistered(Object component) {
        return this.state.isRegistered(component);
    }

    @Override
    public boolean isRegistered(Class<?> componentClass) {
        return this.state.isRegistered(componentClass);
    }

    @Override
    public Map<Class<?>, Integer> getContracts(Class<?> componentClass) {
        return this.state.getContracts(componentClass);
    }

    Set<Class<?>> _getClasses() {
        Set<Class<?>> result = this.scanClasses();
        result.addAll(this.state.getClasses());
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Set<Class<?>> scanClasses() {
        String[] classPathElements;
        String[] packageNames;
        HashSet result = new HashSet();
        State _state = this.state;
        HashSet<ResourceFinder> rfs = new HashSet<ResourceFinder>(_state.getResourceFinders());
        this.resetFinders = true;
        String[] classNames = this.parsePropertyValue("jersey.config.server.provider.classnames");
        if (classNames != null) {
            for (String className : classNames) {
                try {
                    result.add(_state.getClassLoader().loadClass(className));
                }
                catch (ClassNotFoundException e) {
                    LOGGER.log(Level.CONFIG, LocalizationMessages.UNABLE_TO_LOAD_CLASS(className));
                }
            }
        }
        if ((packageNames = this.parsePropertyValue("jersey.config.server.provider.packages")) != null) {
            Object p = this.getProperty("jersey.config.server.provider.scanning.recursive");
            boolean recursive = p == null || PropertiesHelper.isProperty(p);
            rfs.add(new PackageNamesScanner(packageNames, recursive));
        }
        if ((classPathElements = this.parsePropertyValue("jersey.config.server.provider.classpath")) != null) {
            rfs.add(new FilesScanner(classPathElements, true));
        }
        AnnotationAcceptingListener parentAfl = AnnotationAcceptingListener.newJaxrsResourceAndProviderListener(_state.getClassLoader());
        for (ResourceFinder resourceFinder : rfs) {
            AnnotationAcceptingListener afl = parentAfl;
            if (resourceFinder instanceof PackageNamesScanner) {
                ClassLoader classLoader = ((PackageNamesScanner)resourceFinder).getClassloader();
                if (!this.getClassLoader().equals(classLoader)) {
                    afl = AnnotationAcceptingListener.newJaxrsResourceAndProviderListener(classLoader);
                }
            }
            while (resourceFinder.hasNext()) {
                String next = (String)resourceFinder.next();
                if (!afl.accept(next)) continue;
                InputStream in = resourceFinder.open();
                try {
                    afl.process(next, in);
                }
                catch (IOException e) {
                    LOGGER.log(Level.WARNING, LocalizationMessages.RESOURCE_CONFIG_UNABLE_TO_PROCESS(next));
                }
                finally {
                    try {
                        in.close();
                    }
                    catch (IOException ex) {
                        LOGGER.log(Level.FINER, "Error closing resource stream.", ex);
                    }
                }
            }
            if (afl == parentAfl) continue;
            result.addAll(afl.getAnnotatedClasses());
        }
        result.addAll(parentAfl.getAnnotatedClasses());
        return result;
    }

    private String[] parsePropertyValue(String propertyName) {
        String[] classNames = null;
        Object o = this.state.getProperties().get(propertyName);
        if (o != null) {
            if (o instanceof String) {
                classNames = Tokenizer.tokenize((String)o);
            } else if (o instanceof String[]) {
                classNames = Tokenizer.tokenize((String[])o);
            }
        }
        return classNames;
    }

    Set<Class<?>> getRegisteredClasses() {
        return this.state.getComponentBag().getRegistrations();
    }

    Set<Object> _getSingletons() {
        HashSet<Object> result = new HashSet<Object>();
        result.addAll(this.state.getInstances());
        return result;
    }

    @Override
    public final Set<Resource> getResources() {
        return this.state.getResources();
    }

    public final ClassLoader getClassLoader() {
        return this.state.getClassLoader();
    }

    public final Application getApplication() {
        return this._getApplication();
    }

    Application _getApplication() {
        return this;
    }

    public String getApplicationName() {
        return this.state.getApplicationName();
    }

    Class<? extends Application> getApplicationClass() {
        return null;
    }

    final ResourceConfig setApplication(Application app) {
        return this._setApplication(app);
    }

    ResourceConfig _setApplication(Application app) {
        throw new UnsupportedOperationException();
    }

    static ResourceConfig createRuntimeConfig(Application application) {
        return application instanceof ResourceConfig ? new RuntimeConfig((ResourceConfig)application) : new RuntimeConfig(application);
    }

    private static Application unwrapCustomRootApplication(ResourceConfig resourceConfig) {
        Application app = null;
        while (resourceConfig != null) {
            app = resourceConfig.getApplication();
            if (app == resourceConfig) {
                return null;
            }
            if (!(app instanceof ResourceConfig)) break;
            resourceConfig = (ResourceConfig)app;
        }
        return app;
    }

    static Application unwrapApplication(Application application) {
        Application wrappedApplication;
        while (application instanceof ResourceConfig && (wrappedApplication = ((ResourceConfig)application).getApplication()) != application) {
            application = wrappedApplication;
        }
        return application;
    }

    private void setupApplicationName() {
        String appName = ServerProperties.getValue(this.getProperties(), "jersey.config.server.application.name", null, String.class);
        if (appName != null && this.getApplicationName() == null) {
            this.setApplicationName(appName);
        }
    }

    private static class RuntimeConfig
    extends ResourceConfig {
        private final Set<Class<?>> originalRegistrations;
        private final Application application;

        private RuntimeConfig(ResourceConfig original) {
            super(original);
            this.application = original;
            Application customRootApp = ResourceConfig.unwrapCustomRootApplication(original);
            if (customRootApp != null) {
                this.registerComponentsOf(customRootApp);
            }
            this.originalRegistrations = Collections.newSetFromMap(new IdentityHashMap());
            this.originalRegistrations.addAll(super.getRegisteredClasses());
            Set<Object> externalInstances = original.getSingletons().stream().filter(external -> !this.originalRegistrations.contains(external.getClass())).collect(Collectors.toSet());
            this.registerInstances(externalInstances);
            Set<Class<?>> externalClasses = original.getClasses().stream().filter(external -> !this.originalRegistrations.contains(external)).collect(Collectors.toSet());
            this.registerClasses(externalClasses);
        }

        private void registerComponentsOf(final Application application) {
            Errors.processWithException(new Runnable(){

                @Override
                public void run() {
                    Set<Class<?>> classes;
                    Set<Object> singletons = application.getSingletons();
                    if (singletons != null) {
                        this.registerInstances(singletons.stream().filter(input -> {
                            if (input == null) {
                                Errors.warning(application, LocalizationMessages.NON_INSTANTIABLE_COMPONENT(null));
                            }
                            return input != null;
                        }).collect(Collectors.toSet()));
                    }
                    if ((classes = application.getClasses()) != null) {
                        this.registerClasses(classes.stream().filter(input -> {
                            if (input == null) {
                                Errors.warning(application, LocalizationMessages.NON_INSTANTIABLE_COMPONENT(null));
                            }
                            return input != null;
                        }).collect(Collectors.toSet()));
                    }
                }
            });
        }

        private RuntimeConfig(Application application) {
            this.application = application;
            if (application != null) {
                this.registerComponentsOf(application);
                this.addProperties(application.getProperties());
            }
            this.originalRegistrations = super.getRegisteredClasses();
        }

        @Override
        Set<Class<?>> _getClasses() {
            return ((ResourceConfig)this).state.getClasses();
        }

        @Override
        Set<Object> _getSingletons() {
            return ((ResourceConfig)this).state.getInstances();
        }

        @Override
        Set<Class<?>> getRegisteredClasses() {
            return this.originalRegistrations;
        }

        @Override
        Application _getApplication() {
            return this.application;
        }
    }

    private static class WrappingResourceConfig
    extends ResourceConfig {
        private Application application;
        private Class<? extends Application> applicationClass;
        private final Set<Class<?>> defaultClasses = new HashSet();

        public WrappingResourceConfig(Application application, Class<? extends Application> applicationClass, Set<Class<?>> defaultClasses) {
            if (application == null && applicationClass == null) {
                throw new IllegalArgumentException(LocalizationMessages.RESOURCE_CONFIG_ERROR_NULL_APPLICATIONCLASS());
            }
            this.application = application;
            this.applicationClass = applicationClass;
            if (defaultClasses != null) {
                this.defaultClasses.addAll(defaultClasses);
            }
            this.mergeApplications(application);
        }

        @Override
        ResourceConfig _setApplication(Application application) {
            this.application = application;
            this.applicationClass = null;
            this.mergeApplications(application);
            return this;
        }

        @Override
        Application _getApplication() {
            return this.application;
        }

        @Override
        Class<? extends Application> getApplicationClass() {
            return this.applicationClass;
        }

        private void mergeApplications(Application application) {
            if (application instanceof ResourceConfig) {
                ResourceConfig rc = (ResourceConfig)application;
                super.registerResources(rc.getResources());
                rc.invalidateCache();
                rc.addProperties(super.getProperties());
                super.addProperties(rc.getProperties());
                super.setApplicationName(rc.getApplicationName());
                super.setClassLoader(rc.getClassLoader());
                rc.lock();
            } else if (application != null) {
                super.addProperties(application.getProperties());
            }
        }

        @Override
        Set<Class<?>> _getClasses() {
            HashSet result = new HashSet();
            HashSet applicationClasses = this.application.getClasses();
            result.addAll(applicationClasses == null ? new HashSet() : applicationClasses);
            if (result.isEmpty() && this.getSingletons().isEmpty()) {
                result.addAll(this.defaultClasses);
            }
            if (!(this.application instanceof ResourceConfig)) {
                result.addAll(super._getClasses());
            }
            return result;
        }

        @Override
        Set<Object> _getSingletons() {
            return this.application.getSingletons();
        }
    }

    private static final class ImmutableState
    extends State {
        private ImmutableState(State original) {
            super(original);
        }

        @Override
        public void setClassLoader(ClassLoader classLoader) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public void registerResources(Set<Resource> resources) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public void registerFinder(ResourceFinder resourceFinder) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State addProperties(Map<String, ?> properties) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State property(String name, Object value) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State register(Class<?> componentClass) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State register(Class<?> componentClass, int bindingPriority) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State register(Class<?> componentClass, Class<?> ... contracts) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State register(Object component) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State register(Object component, int bindingPriority) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State register(Object component, Class<?> ... contracts) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State register(Object component, Map<Class<?>, Integer> contracts) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public State setProperties(Map<String, ?> properties) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public void configureAutoDiscoverableProviders(InjectionManager injectionManager, Collection<AutoDiscoverable> autoDiscoverables, boolean forcedOnly) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }

        @Override
        public void configureMetaProviders(InjectionManager injectionManager, ManagedObjectsFinalizer finalizer) {
            throw new IllegalStateException(LocalizationMessages.RC_NOT_MODIFIABLE());
        }
    }

    private static class State
    extends CommonConfig
    implements ServerConfig {
        private final Set<ResourceFinder> resourceFinders;
        private final Set<Resource> resources;
        private final Set<Resource> resourcesView;
        private volatile String applicationName;
        private volatile ClassLoader classLoader = null;

        public State() {
            super(RuntimeType.SERVER, ComponentBag.INCLUDE_ALL);
            this.classLoader = AccessController.doPrivileged(ReflectionHelper.getContextClassLoaderPA());
            this.resourceFinders = new HashSet<ResourceFinder>();
            this.resources = new HashSet<Resource>();
            this.resourcesView = Collections.unmodifiableSet(this.resources);
        }

        public State(State original) {
            super(original);
            this.classLoader = original.classLoader;
            this.applicationName = original.applicationName;
            this.resources = new HashSet<Resource>(original.resources);
            this.resourcesView = Collections.unmodifiableSet(this.resources);
            this.resourceFinders = new HashSet<ResourceFinder>(original.resourceFinders);
        }

        public void setClassLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public void registerResources(Set<Resource> resources) {
            this.resources.addAll(resources);
        }

        public void registerFinder(ResourceFinder resourceFinder) {
            this.resourceFinders.add(resourceFinder);
        }

        @Override
        protected Inflector<ContractProvider.Builder, ContractProvider> getModelEnhancer(Class<?> componentClass) {
            return builder -> {
                if (builder.getScope() == null && builder.getContracts().isEmpty() && Resource.getPath(componentClass) != null) {
                    builder.scope(RequestScoped.class);
                }
                return builder.build();
            };
        }

        @Override
        public State loadFrom(Configuration config) {
            super.loadFrom(config);
            this.resourceFinders.clear();
            this.resources.clear();
            State other = null;
            if (config instanceof ResourceConfig) {
                other = ((ResourceConfig)config).state;
            }
            if (config instanceof State) {
                other = (State)config;
            }
            if (other != null) {
                this.resourceFinders.addAll(other.resourceFinders);
                this.resources.addAll(other.resources);
            }
            return this;
        }

        @Override
        public final Set<Resource> getResources() {
            return this.resourcesView;
        }

        @Override
        public ServerConfig getConfiguration() {
            return this;
        }

        public Set<ResourceFinder> getResourceFinders() {
            return this.resourceFinders;
        }

        public ClassLoader getClassLoader() {
            return this.classLoader;
        }

        private String getApplicationName() {
            return this.applicationName;
        }
    }
}

