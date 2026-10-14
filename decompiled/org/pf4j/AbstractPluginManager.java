/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.pf4j.DependencyResolver;
import org.pf4j.ExtensionFactory;
import org.pf4j.ExtensionFinder;
import org.pf4j.ExtensionWrapper;
import org.pf4j.Plugin;
import org.pf4j.PluginAlreadyLoadedException;
import org.pf4j.PluginDependency;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginFactory;
import org.pf4j.PluginLoader;
import org.pf4j.PluginManager;
import org.pf4j.PluginRepository;
import org.pf4j.PluginRuntimeException;
import org.pf4j.PluginState;
import org.pf4j.PluginStateEvent;
import org.pf4j.PluginStateListener;
import org.pf4j.PluginStatusProvider;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;
import org.pf4j.VersionManager;
import org.pf4j.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractPluginManager
implements PluginManager {
    private static final Logger log = LoggerFactory.getLogger(AbstractPluginManager.class);
    public static final String PLUGINS_DIR_PROPERTY_NAME = "pf4j.pluginsDir";
    public static final String MODE_PROPERTY_NAME = "pf4j.mode";
    public static final String DEFAULT_PLUGINS_DIR = "plugins";
    public static final String DEVELOPMENT_PLUGINS_DIR = "../plugins";
    protected final List<Path> pluginsRoots = new ArrayList<Path>();
    protected ExtensionFinder extensionFinder;
    protected PluginDescriptorFinder pluginDescriptorFinder;
    protected Map<String, PluginWrapper> plugins;
    protected Map<String, ClassLoader> pluginClassLoaders;
    protected List<PluginWrapper> unresolvedPlugins;
    protected List<PluginWrapper> resolvedPlugins;
    protected List<PluginWrapper> startedPlugins;
    protected List<PluginStateListener> pluginStateListeners;
    protected RuntimeMode runtimeMode;
    protected String systemVersion = "0.0.0";
    protected PluginRepository pluginRepository;
    protected PluginFactory pluginFactory;
    protected ExtensionFactory extensionFactory;
    protected PluginStatusProvider pluginStatusProvider;
    protected DependencyResolver dependencyResolver;
    protected PluginLoader pluginLoader;
    protected boolean exactVersionAllowed = false;
    protected VersionManager versionManager;

    public AbstractPluginManager() {
        this.initialize();
    }

    public AbstractPluginManager(Path ... pluginsRoots) {
        this(Arrays.asList(pluginsRoots));
    }

    public AbstractPluginManager(List<Path> pluginsRoots) {
        this.pluginsRoots.addAll(pluginsRoots);
        this.initialize();
    }

    @Override
    public void setSystemVersion(String version) {
        this.systemVersion = version;
    }

    @Override
    public String getSystemVersion() {
        return this.systemVersion;
    }

    @Override
    public List<PluginWrapper> getPlugins() {
        return new ArrayList<PluginWrapper>(this.plugins.values());
    }

    @Override
    public List<PluginWrapper> getPlugins(PluginState pluginState) {
        ArrayList<PluginWrapper> plugins = new ArrayList<PluginWrapper>();
        for (PluginWrapper plugin : this.getPlugins()) {
            if (!pluginState.equals((Object)plugin.getPluginState())) continue;
            plugins.add(plugin);
        }
        return plugins;
    }

    @Override
    public List<PluginWrapper> getResolvedPlugins() {
        return this.resolvedPlugins;
    }

    @Override
    public List<PluginWrapper> getUnresolvedPlugins() {
        return this.unresolvedPlugins;
    }

    @Override
    public List<PluginWrapper> getStartedPlugins() {
        return this.startedPlugins;
    }

    @Override
    public PluginWrapper getPlugin(String pluginId) {
        return this.plugins.get(pluginId);
    }

    @Override
    public String loadPlugin(Path pluginPath) {
        if (pluginPath == null || Files.notExists(pluginPath, new LinkOption[0])) {
            throw new IllegalArgumentException(String.format("Specified plugin %s does not exist!", pluginPath));
        }
        log.debug("Loading plugin from '{}'", (Object)pluginPath);
        PluginWrapper pluginWrapper = this.loadPluginFromPath(pluginPath);
        this.resolvePlugins();
        return pluginWrapper.getDescriptor().getPluginId();
    }

    @Override
    public void loadPlugins() {
        log.debug("Lookup plugins in '{}'", (Object)this.pluginsRoots);
        if (this.pluginsRoots.isEmpty()) {
            log.warn("No plugins roots configured");
            return;
        }
        this.pluginsRoots.forEach(path -> {
            if (Files.notExists(path, new LinkOption[0]) || !Files.isDirectory(path, new LinkOption[0])) {
                log.warn("No '{}' root", path);
            }
        });
        List<Path> pluginPaths = this.pluginRepository.getPluginPaths();
        if (pluginPaths.isEmpty()) {
            log.info("No plugins");
            return;
        }
        log.debug("Found {} possible plugins: {}", (Object)pluginPaths.size(), (Object)pluginPaths);
        for (Path pluginPath : pluginPaths) {
            try {
                this.loadPluginFromPath(pluginPath);
            }
            catch (PluginRuntimeException e) {
                log.error(e.getMessage(), e);
            }
        }
        try {
            this.resolvePlugins();
        }
        catch (PluginRuntimeException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void unloadPlugins() {
        for (PluginWrapper pluginWrapper : new ArrayList<PluginWrapper>(this.resolvedPlugins)) {
            this.unloadPlugin(pluginWrapper.getPluginId());
        }
    }

    @Override
    public boolean unloadPlugin(String pluginId) {
        return this.unloadPlugin(pluginId, true);
    }

    protected boolean unloadPlugin(String pluginId, boolean unloadDependents) {
        try {
            ClassLoader classLoader;
            PluginState pluginState;
            if (unloadDependents) {
                List<String> dependents = this.dependencyResolver.getDependents(pluginId);
                while (!dependents.isEmpty()) {
                    String dependent = dependents.remove(0);
                    this.unloadPlugin(dependent, false);
                    dependents.addAll(0, this.dependencyResolver.getDependents(dependent));
                }
            }
            if (PluginState.STARTED == (pluginState = this.stopPlugin(pluginId, false))) {
                return false;
            }
            PluginWrapper pluginWrapper = this.getPlugin(pluginId);
            log.info("Unload plugin '{}'", (Object)this.getPluginLabel(pluginWrapper.getDescriptor()));
            this.plugins.remove(pluginId);
            this.getResolvedPlugins().remove(pluginWrapper);
            this.firePluginStateEvent(new PluginStateEvent(this, pluginWrapper, pluginState));
            Map<String, ClassLoader> pluginClassLoaders = this.getPluginClassLoaders();
            if (pluginClassLoaders.containsKey(pluginId) && (classLoader = pluginClassLoaders.remove(pluginId)) instanceof Closeable) {
                try {
                    ((Closeable)((Object)classLoader)).close();
                }
                catch (IOException e) {
                    throw new PluginRuntimeException(e, "Cannot close classloader", new Object[0]);
                }
            }
            return true;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    @Override
    public boolean deletePlugin(String pluginId) {
        this.checkPluginId(pluginId);
        PluginWrapper pluginWrapper = this.getPlugin(pluginId);
        PluginState pluginState = this.stopPlugin(pluginId);
        if (PluginState.STARTED == pluginState) {
            log.error("Failed to stop plugin '{}' on delete", (Object)pluginId);
            return false;
        }
        Plugin plugin = pluginWrapper.getPlugin();
        if (!this.unloadPlugin(pluginId)) {
            log.error("Failed to unload plugin '{}' on delete", (Object)pluginId);
            return false;
        }
        plugin.delete();
        Path pluginPath = pluginWrapper.getPluginPath();
        return this.pluginRepository.deletePluginPath(pluginPath);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void startPlugins() {
        for (PluginWrapper pluginWrapper : this.resolvedPlugins) {
            PluginState pluginState = pluginWrapper.getPluginState();
            if (PluginState.DISABLED == pluginState || PluginState.STARTED == pluginState) continue;
            try {
                log.info("Start plugin '{}'", (Object)this.getPluginLabel(pluginWrapper.getDescriptor()));
                pluginWrapper.getPlugin().start();
                pluginWrapper.setPluginState(PluginState.STARTED);
                pluginWrapper.setFailedException(null);
                this.startedPlugins.add(pluginWrapper);
            }
            catch (Exception | LinkageError e) {
                pluginWrapper.setPluginState(PluginState.FAILED);
                pluginWrapper.setFailedException(e);
                log.error("Unable to start plugin '{}'", (Object)this.getPluginLabel(pluginWrapper.getDescriptor()), (Object)e);
            }
            finally {
                this.firePluginStateEvent(new PluginStateEvent(this, pluginWrapper, pluginState));
            }
        }
    }

    @Override
    public PluginState startPlugin(String pluginId) {
        this.checkPluginId(pluginId);
        PluginWrapper pluginWrapper = this.getPlugin(pluginId);
        PluginDescriptor pluginDescriptor = pluginWrapper.getDescriptor();
        PluginState pluginState = pluginWrapper.getPluginState();
        if (PluginState.STARTED == pluginState) {
            log.debug("Already started plugin '{}'", (Object)this.getPluginLabel(pluginDescriptor));
            return PluginState.STARTED;
        }
        if (!this.resolvedPlugins.contains(pluginWrapper)) {
            log.warn("Cannot start an unresolved plugin '{}'", (Object)this.getPluginLabel(pluginDescriptor));
            return pluginState;
        }
        if (PluginState.DISABLED == pluginState && !this.enablePlugin(pluginId)) {
            return pluginState;
        }
        for (PluginDependency dependency : pluginDescriptor.getDependencies()) {
            if (dependency.isOptional() && !this.plugins.containsKey(dependency.getPluginId())) continue;
            this.startPlugin(dependency.getPluginId());
        }
        log.info("Start plugin '{}'", (Object)this.getPluginLabel(pluginDescriptor));
        pluginWrapper.getPlugin().start();
        pluginWrapper.setPluginState(PluginState.STARTED);
        this.startedPlugins.add(pluginWrapper);
        this.firePluginStateEvent(new PluginStateEvent(this, pluginWrapper, pluginState));
        return pluginWrapper.getPluginState();
    }

    @Override
    public void stopPlugins() {
        Collections.reverse(this.startedPlugins);
        Iterator<PluginWrapper> itr = this.startedPlugins.iterator();
        while (itr.hasNext()) {
            PluginWrapper pluginWrapper = itr.next();
            PluginState pluginState = pluginWrapper.getPluginState();
            if (PluginState.STARTED != pluginState) continue;
            try {
                log.info("Stop plugin '{}'", (Object)this.getPluginLabel(pluginWrapper.getDescriptor()));
                pluginWrapper.getPlugin().stop();
                pluginWrapper.setPluginState(PluginState.STOPPED);
                itr.remove();
                this.firePluginStateEvent(new PluginStateEvent(this, pluginWrapper, pluginState));
            }
            catch (PluginRuntimeException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public PluginState stopPlugin(String pluginId) {
        return this.stopPlugin(pluginId, true);
    }

    protected PluginState stopPlugin(String pluginId, boolean stopDependents) {
        this.checkPluginId(pluginId);
        PluginWrapper pluginWrapper = this.getPlugin(pluginId);
        PluginDescriptor pluginDescriptor = pluginWrapper.getDescriptor();
        PluginState pluginState = pluginWrapper.getPluginState();
        if (PluginState.STOPPED == pluginState) {
            log.debug("Already stopped plugin '{}'", (Object)this.getPluginLabel(pluginDescriptor));
            return PluginState.STOPPED;
        }
        if (PluginState.DISABLED == pluginState) {
            return pluginState;
        }
        if (stopDependents) {
            List<String> dependents = this.dependencyResolver.getDependents(pluginId);
            while (!dependents.isEmpty()) {
                String dependent = dependents.remove(0);
                this.stopPlugin(dependent, false);
                dependents.addAll(0, this.dependencyResolver.getDependents(dependent));
            }
        }
        log.info("Stop plugin '{}'", (Object)this.getPluginLabel(pluginDescriptor));
        pluginWrapper.getPlugin().stop();
        pluginWrapper.setPluginState(PluginState.STOPPED);
        this.startedPlugins.remove(pluginWrapper);
        this.firePluginStateEvent(new PluginStateEvent(this, pluginWrapper, pluginState));
        return pluginWrapper.getPluginState();
    }

    protected void checkPluginId(String pluginId) {
        if (!this.plugins.containsKey(pluginId)) {
            throw new IllegalArgumentException(String.format("Unknown pluginId %s", pluginId));
        }
    }

    @Override
    public boolean disablePlugin(String pluginId) {
        this.checkPluginId(pluginId);
        PluginWrapper pluginWrapper = this.getPlugin(pluginId);
        PluginDescriptor pluginDescriptor = pluginWrapper.getDescriptor();
        PluginState pluginState = pluginWrapper.getPluginState();
        if (PluginState.DISABLED == pluginState) {
            log.debug("Already disabled plugin '{}'", (Object)this.getPluginLabel(pluginDescriptor));
            return true;
        }
        if (PluginState.STOPPED == this.stopPlugin(pluginId)) {
            pluginWrapper.setPluginState(PluginState.DISABLED);
            this.firePluginStateEvent(new PluginStateEvent(this, pluginWrapper, PluginState.STOPPED));
            this.pluginStatusProvider.disablePlugin(pluginId);
            log.info("Disabled plugin '{}'", (Object)this.getPluginLabel(pluginDescriptor));
            return true;
        }
        return false;
    }

    @Override
    public boolean enablePlugin(String pluginId) {
        this.checkPluginId(pluginId);
        PluginWrapper pluginWrapper = this.getPlugin(pluginId);
        if (!this.isPluginValid(pluginWrapper)) {
            log.warn("Plugin '{}' can not be enabled", (Object)this.getPluginLabel(pluginWrapper.getDescriptor()));
            return false;
        }
        PluginDescriptor pluginDescriptor = pluginWrapper.getDescriptor();
        PluginState pluginState = pluginWrapper.getPluginState();
        if (PluginState.DISABLED != pluginState) {
            log.debug("Plugin '{}' is not disabled", (Object)this.getPluginLabel(pluginDescriptor));
            return true;
        }
        this.pluginStatusProvider.enablePlugin(pluginId);
        pluginWrapper.setPluginState(PluginState.CREATED);
        this.firePluginStateEvent(new PluginStateEvent(this, pluginWrapper, pluginState));
        log.info("Enabled plugin '{}'", (Object)this.getPluginLabel(pluginDescriptor));
        return true;
    }

    @Override
    public ClassLoader getPluginClassLoader(String pluginId) {
        return this.pluginClassLoaders.get(pluginId);
    }

    @Override
    public List<Class<?>> getExtensionClasses(String pluginId) {
        List<ExtensionWrapper> extensionsWrapper = this.extensionFinder.find(pluginId);
        ArrayList extensionClasses = new ArrayList(extensionsWrapper.size());
        for (ExtensionWrapper extensionWrapper : extensionsWrapper) {
            Class<?> c = extensionWrapper.getDescriptor().extensionClass;
            extensionClasses.add(c);
        }
        return extensionClasses;
    }

    @Override
    public <T> List<Class<? extends T>> getExtensionClasses(Class<T> type) {
        return this.getExtensionClasses(this.extensionFinder.find(type));
    }

    @Override
    public <T> List<Class<? extends T>> getExtensionClasses(Class<T> type, String pluginId) {
        return this.getExtensionClasses(this.extensionFinder.find(type, pluginId));
    }

    @Override
    public <T> List<T> getExtensions(Class<T> type) {
        return this.getExtensions(this.extensionFinder.find(type));
    }

    @Override
    public <T> List<T> getExtensions(Class<T> type, String pluginId) {
        return this.getExtensions(this.extensionFinder.find(type, pluginId));
    }

    @Override
    public List getExtensions(String pluginId) {
        List<ExtensionWrapper> extensionsWrapper = this.extensionFinder.find(pluginId);
        ArrayList extensions = new ArrayList(extensionsWrapper.size());
        for (ExtensionWrapper extensionWrapper : extensionsWrapper) {
            try {
                extensions.add(extensionWrapper.getExtension());
            }
            catch (PluginRuntimeException e) {
                log.error("Cannot retrieve extension", e);
            }
        }
        return extensions;
    }

    @Override
    public Set<String> getExtensionClassNames(String pluginId) {
        return this.extensionFinder.findClassNames(pluginId);
    }

    @Override
    public ExtensionFactory getExtensionFactory() {
        return this.extensionFactory;
    }

    public PluginLoader getPluginLoader() {
        return this.pluginLoader;
    }

    @Override
    public Path getPluginsRoot() {
        return (Path)this.pluginsRoots.stream().findFirst().orElseThrow(() -> new IllegalStateException("pluginsRoots have not been initialized, yet."));
    }

    @Override
    public List<Path> getPluginsRoots() {
        return Collections.unmodifiableList(this.pluginsRoots);
    }

    @Override
    public RuntimeMode getRuntimeMode() {
        if (this.runtimeMode == null) {
            String modeAsString = System.getProperty(MODE_PROPERTY_NAME, RuntimeMode.DEPLOYMENT.toString());
            this.runtimeMode = RuntimeMode.byName(modeAsString);
        }
        return this.runtimeMode;
    }

    @Override
    public PluginWrapper whichPlugin(Class<?> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        for (PluginWrapper plugin : this.resolvedPlugins) {
            if (plugin.getPluginClassLoader() != classLoader) continue;
            return plugin;
        }
        return null;
    }

    @Override
    public synchronized void addPluginStateListener(PluginStateListener listener) {
        this.pluginStateListeners.add(listener);
    }

    @Override
    public synchronized void removePluginStateListener(PluginStateListener listener) {
        this.pluginStateListeners.remove(listener);
    }

    public String getVersion() {
        return "3.8.0";
    }

    protected abstract PluginRepository createPluginRepository();

    protected abstract PluginFactory createPluginFactory();

    protected abstract ExtensionFactory createExtensionFactory();

    protected abstract PluginDescriptorFinder createPluginDescriptorFinder();

    protected abstract ExtensionFinder createExtensionFinder();

    protected abstract PluginStatusProvider createPluginStatusProvider();

    protected abstract PluginLoader createPluginLoader();

    protected abstract VersionManager createVersionManager();

    protected PluginDescriptorFinder getPluginDescriptorFinder() {
        return this.pluginDescriptorFinder;
    }

    protected PluginFactory getPluginFactory() {
        return this.pluginFactory;
    }

    protected Map<String, ClassLoader> getPluginClassLoaders() {
        return this.pluginClassLoaders;
    }

    protected void initialize() {
        this.plugins = new HashMap<String, PluginWrapper>();
        this.pluginClassLoaders = new HashMap<String, ClassLoader>();
        this.unresolvedPlugins = new ArrayList<PluginWrapper>();
        this.resolvedPlugins = new ArrayList<PluginWrapper>();
        this.startedPlugins = new ArrayList<PluginWrapper>();
        this.pluginStateListeners = new ArrayList<PluginStateListener>();
        if (this.pluginsRoots.isEmpty()) {
            this.pluginsRoots.addAll(this.createPluginsRoot());
        }
        this.pluginRepository = this.createPluginRepository();
        this.pluginFactory = this.createPluginFactory();
        this.extensionFactory = this.createExtensionFactory();
        this.pluginDescriptorFinder = this.createPluginDescriptorFinder();
        this.extensionFinder = this.createExtensionFinder();
        this.pluginStatusProvider = this.createPluginStatusProvider();
        this.pluginLoader = this.createPluginLoader();
        this.versionManager = this.createVersionManager();
        this.dependencyResolver = new DependencyResolver(this.versionManager);
    }

    protected List<Path> createPluginsRoot() {
        String pluginsDir = System.getProperty(PLUGINS_DIR_PROPERTY_NAME);
        if (pluginsDir != null && !pluginsDir.isEmpty()) {
            return Arrays.stream(pluginsDir.split(",")).map(String::trim).map(x$0 -> Paths.get(x$0, new String[0])).collect(Collectors.toList());
        }
        pluginsDir = this.isDevelopment() ? DEVELOPMENT_PLUGINS_DIR : DEFAULT_PLUGINS_DIR;
        return Collections.singletonList(Paths.get(pluginsDir, new String[0]));
    }

    protected boolean isPluginValid(PluginWrapper pluginWrapper) {
        String requires = pluginWrapper.getDescriptor().getRequires().trim();
        if (!this.isExactVersionAllowed() && requires.matches("^\\d+\\.\\d+\\.\\d+$")) {
            requires = ">=" + requires;
        }
        if (this.systemVersion.equals("0.0.0") || this.versionManager.checkVersionConstraint(this.systemVersion, requires)) {
            return true;
        }
        PluginDescriptor pluginDescriptor = pluginWrapper.getDescriptor();
        log.warn("Plugin '{}' requires a minimum system version of {}, and you have {}", this.getPluginLabel(pluginDescriptor), requires, this.getSystemVersion());
        return false;
    }

    protected boolean isPluginDisabled(String pluginId) {
        return this.pluginStatusProvider.isPluginDisabled(pluginId);
    }

    protected void resolvePlugins() {
        ArrayList<PluginDescriptor> descriptors = new ArrayList<PluginDescriptor>();
        for (PluginWrapper plugin : this.plugins.values()) {
            descriptors.add(plugin.getDescriptor());
        }
        DependencyResolver.Result result = this.dependencyResolver.resolve(descriptors);
        if (result.hasCyclicDependency()) {
            throw new DependencyResolver.CyclicDependencyException();
        }
        List<String> notFoundDependencies = result.getNotFoundDependencies();
        if (!notFoundDependencies.isEmpty()) {
            throw new DependencyResolver.DependenciesNotFoundException(notFoundDependencies);
        }
        List<DependencyResolver.WrongDependencyVersion> wrongVersionDependencies = result.getWrongVersionDependencies();
        if (!wrongVersionDependencies.isEmpty()) {
            throw new DependencyResolver.DependenciesWrongVersionException(wrongVersionDependencies);
        }
        List<String> sortedPlugins = result.getSortedPlugins();
        for (String pluginId : sortedPlugins) {
            PluginWrapper pluginWrapper = this.plugins.get(pluginId);
            if (!this.unresolvedPlugins.remove(pluginWrapper)) continue;
            PluginState pluginState = pluginWrapper.getPluginState();
            if (pluginState != PluginState.DISABLED) {
                pluginWrapper.setPluginState(PluginState.RESOLVED);
            }
            this.resolvedPlugins.add(pluginWrapper);
            log.info("Plugin '{}' resolved", (Object)this.getPluginLabel(pluginWrapper.getDescriptor()));
            this.firePluginStateEvent(new PluginStateEvent(this, pluginWrapper, pluginState));
        }
    }

    protected synchronized void firePluginStateEvent(PluginStateEvent event) {
        for (PluginStateListener listener : this.pluginStateListeners) {
            log.trace("Fire '{}' to '{}'", (Object)event, (Object)listener);
            listener.pluginStateChanged(event);
        }
    }

    protected PluginWrapper loadPluginFromPath(Path pluginPath) {
        String pluginId = this.idForPath(pluginPath);
        if (pluginId != null) {
            throw new PluginAlreadyLoadedException(pluginId, pluginPath);
        }
        PluginDescriptorFinder pluginDescriptorFinder = this.getPluginDescriptorFinder();
        log.debug("Use '{}' to find plugins descriptors", (Object)pluginDescriptorFinder);
        log.debug("Finding plugin descriptor for plugin '{}'", (Object)pluginPath);
        PluginDescriptor pluginDescriptor = pluginDescriptorFinder.find(pluginPath);
        this.validatePluginDescriptor(pluginDescriptor);
        pluginId = pluginDescriptor.getPluginId();
        if (this.plugins.containsKey(pluginId)) {
            PluginWrapper loadedPlugin = this.getPlugin(pluginId);
            throw new PluginRuntimeException("There is an already loaded plugin ({}) with the same id ({}) as the plugin at path '{}'. Simultaneous loading of plugins with the same PluginId is not currently supported.\nAs a workaround you may include PluginVersion and PluginProvider in PluginId.", loadedPlugin, pluginId, pluginPath);
        }
        log.debug("Found descriptor {}", (Object)pluginDescriptor);
        String pluginClassName = pluginDescriptor.getPluginClass();
        log.debug("Class '{}' for plugin '{}'", (Object)pluginClassName, (Object)pluginPath);
        log.debug("Loading plugin '{}'", (Object)pluginPath);
        ClassLoader pluginClassLoader = this.getPluginLoader().loadPlugin(pluginPath, pluginDescriptor);
        log.debug("Loaded plugin '{}' with class loader '{}'", (Object)pluginPath, (Object)pluginClassLoader);
        PluginWrapper pluginWrapper = this.createPluginWrapper(pluginDescriptor, pluginPath, pluginClassLoader);
        if (this.isPluginDisabled(pluginDescriptor.getPluginId())) {
            log.info("Plugin '{}' is disabled", (Object)pluginPath);
            pluginWrapper.setPluginState(PluginState.DISABLED);
        }
        if (!this.isPluginValid(pluginWrapper)) {
            log.warn("Plugin '{}' is invalid and it will be disabled", (Object)pluginPath);
            pluginWrapper.setPluginState(PluginState.DISABLED);
        }
        log.debug("Created wrapper '{}' for plugin '{}'", (Object)pluginWrapper, (Object)pluginPath);
        pluginId = pluginDescriptor.getPluginId();
        this.plugins.put(pluginId, pluginWrapper);
        this.getUnresolvedPlugins().add(pluginWrapper);
        this.getPluginClassLoaders().put(pluginId, pluginClassLoader);
        return pluginWrapper;
    }

    protected PluginWrapper createPluginWrapper(PluginDescriptor pluginDescriptor, Path pluginPath, ClassLoader pluginClassLoader) {
        log.debug("Creating wrapper for plugin '{}'", (Object)pluginPath);
        PluginWrapper pluginWrapper = new PluginWrapper(this, pluginDescriptor, pluginPath, pluginClassLoader);
        pluginWrapper.setPluginFactory(this.getPluginFactory());
        return pluginWrapper;
    }

    protected String idForPath(Path pluginPath) {
        for (PluginWrapper plugin : this.plugins.values()) {
            if (!plugin.getPluginPath().equals(pluginPath)) continue;
            return plugin.getPluginId();
        }
        return null;
    }

    protected void validatePluginDescriptor(PluginDescriptor descriptor) {
        if (StringUtils.isNullOrEmpty(descriptor.getPluginId())) {
            throw new PluginRuntimeException("Field 'id' cannot be empty");
        }
        if (descriptor.getVersion() == null) {
            throw new PluginRuntimeException("Field 'version' cannot be empty");
        }
    }

    public boolean isExactVersionAllowed() {
        return this.exactVersionAllowed;
    }

    public void setExactVersionAllowed(boolean exactVersionAllowed) {
        this.exactVersionAllowed = exactVersionAllowed;
    }

    @Override
    public VersionManager getVersionManager() {
        return this.versionManager;
    }

    protected String getPluginLabel(PluginDescriptor pluginDescriptor) {
        return pluginDescriptor.getPluginId() + "@" + pluginDescriptor.getVersion();
    }

    protected <T> List<Class<? extends T>> getExtensionClasses(List<ExtensionWrapper<T>> extensionsWrapper) {
        ArrayList<Class<T>> extensionClasses = new ArrayList<Class<T>>(extensionsWrapper.size());
        for (ExtensionWrapper<T> extensionWrapper : extensionsWrapper) {
            Class<?> c = extensionWrapper.getDescriptor().extensionClass;
            extensionClasses.add(c);
        }
        return extensionClasses;
    }

    protected <T> List<T> getExtensions(List<ExtensionWrapper<T>> extensionsWrapper) {
        ArrayList<T> extensions = new ArrayList<T>(extensionsWrapper.size());
        for (ExtensionWrapper<T> extensionWrapper : extensionsWrapper) {
            try {
                extensions.add(extensionWrapper.getExtension());
            }
            catch (PluginRuntimeException e) {
                log.error("Cannot retrieve extension", e);
            }
        }
        return extensions;
    }
}

