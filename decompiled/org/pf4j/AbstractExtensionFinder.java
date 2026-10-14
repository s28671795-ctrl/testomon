/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.pf4j.Extension;
import org.pf4j.ExtensionDescriptor;
import org.pf4j.ExtensionFinder;
import org.pf4j.ExtensionWrapper;
import org.pf4j.PluginDependency;
import org.pf4j.PluginManager;
import org.pf4j.PluginState;
import org.pf4j.PluginStateEvent;
import org.pf4j.PluginStateListener;
import org.pf4j.PluginWrapper;
import org.pf4j.RuntimeMode;
import org.pf4j.asm.ExtensionInfo;
import org.pf4j.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExtensionFinder
implements ExtensionFinder,
PluginStateListener {
    private static final Logger log = LoggerFactory.getLogger(AbstractExtensionFinder.class);
    protected PluginManager pluginManager;
    protected volatile Map<String, Set<String>> entries;
    protected volatile Map<String, ExtensionInfo> extensionInfos;
    protected Boolean checkForExtensionDependencies = null;

    public AbstractExtensionFinder(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    public abstract Map<String, Set<String>> readPluginsStorages();

    public abstract Map<String, Set<String>> readClasspathStorages();

    @Override
    public <T> List<ExtensionWrapper<T>> find(Class<T> type) {
        log.debug("Finding extensions of extension point '{}'", (Object)type.getName());
        Map<String, Set<String>> entries = this.getEntries();
        ArrayList<ExtensionWrapper<T>> result = new ArrayList<ExtensionWrapper<T>>();
        for (String pluginId : entries.keySet()) {
            List<ExtensionWrapper<T>> pluginExtensions = this.find(type, pluginId);
            result.addAll(pluginExtensions);
        }
        if (result.isEmpty()) {
            log.debug("No extensions found for extension point '{}'", (Object)type.getName());
        } else {
            log.debug("Found {} extensions for extension point '{}'", (Object)result.size(), (Object)type.getName());
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public <T> List<ExtensionWrapper<T>> find(Class<T> type, String pluginId) {
        log.debug("Finding extensions of extension point '{}' for plugin '{}'", (Object)type.getName(), (Object)pluginId);
        ArrayList<ExtensionWrapper<T>> result = new ArrayList<ExtensionWrapper<T>>();
        Set<String> classNames = this.findClassNames(pluginId);
        if (classNames.isEmpty()) {
            return result;
        }
        if (pluginId != null) {
            PluginWrapper pluginWrapper = this.pluginManager.getPlugin(pluginId);
            if (PluginState.STARTED != pluginWrapper.getPluginState()) {
                return result;
            }
            log.trace("Checking extensions from plugin '{}'", (Object)pluginId);
        } else {
            log.trace("Checking extensions from classpath");
        }
        ClassLoader classLoader = pluginId != null ? this.pluginManager.getPluginClassLoader(pluginId) : this.getClass().getClassLoader();
        for (String className : classNames) {
            try {
                if (this.isCheckForExtensionDependencies()) {
                    ExtensionInfo extensionInfo = this.getExtensionInfo(className, classLoader);
                    if (extensionInfo == null) {
                        log.error("No extension annotation was found for '{}'", (Object)className);
                        continue;
                    }
                    ArrayList<String> missingPluginIds = new ArrayList<String>();
                    for (String requiredPluginId : extensionInfo.getPlugins()) {
                        PluginWrapper requiredPlugin = this.pluginManager.getPlugin(requiredPluginId);
                        if (requiredPlugin != null && PluginState.STARTED.equals((Object)requiredPlugin.getPluginState())) continue;
                        missingPluginIds.add(requiredPluginId);
                    }
                    if (!missingPluginIds.isEmpty()) {
                        StringBuilder missing = new StringBuilder();
                        for (String missingPluginId : missingPluginIds) {
                            if (missing.length() > 0) {
                                missing.append(", ");
                            }
                            missing.append(missingPluginId);
                        }
                        log.trace("Extension '{}' is ignored due to missing plugins: {}", (Object)className, (Object)missing);
                        continue;
                    }
                }
                log.debug("Loading class '{}' using class loader '{}'", (Object)className, (Object)classLoader);
                Class<?> extensionClass = classLoader.loadClass(className);
                log.debug("Checking extension type '{}'", (Object)className);
                if (type.isAssignableFrom(extensionClass)) {
                    ExtensionWrapper extensionWrapper = this.createExtensionWrapper(extensionClass);
                    result.add(extensionWrapper);
                    log.debug("Added extension '{}' with ordinal {}", (Object)className, (Object)extensionWrapper.getOrdinal());
                    continue;
                }
                log.trace("'{}' is not an extension for extension point '{}'", (Object)className, (Object)type.getName());
                if (!RuntimeMode.DEVELOPMENT.equals((Object)this.pluginManager.getRuntimeMode())) continue;
                this.checkDifferentClassLoaders(type, extensionClass);
            }
            catch (ClassNotFoundException | NoClassDefFoundError e) {
                log.error(e.getMessage(), e);
            }
        }
        if (result.isEmpty()) {
            log.debug("No extensions found for extension point '{}'", (Object)type.getName());
        } else {
            log.debug("Found {} extensions for extension point '{}'", (Object)result.size(), (Object)type.getName());
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public List<ExtensionWrapper> find(String pluginId) {
        log.debug("Finding extensions from plugin '{}'", (Object)pluginId);
        ArrayList<ExtensionWrapper> result = new ArrayList<ExtensionWrapper>();
        Set<String> classNames = this.findClassNames(pluginId);
        if (classNames.isEmpty()) {
            return result;
        }
        if (pluginId != null) {
            PluginWrapper pluginWrapper = this.pluginManager.getPlugin(pluginId);
            if (PluginState.STARTED != pluginWrapper.getPluginState()) {
                return result;
            }
            log.trace("Checking extensions from plugin '{}'", (Object)pluginId);
        } else {
            log.trace("Checking extensions from classpath");
        }
        ClassLoader classLoader = pluginId != null ? this.pluginManager.getPluginClassLoader(pluginId) : this.getClass().getClassLoader();
        for (String className : classNames) {
            try {
                log.debug("Loading class '{}' using class loader '{}'", (Object)className, (Object)classLoader);
                Class<?> extensionClass = classLoader.loadClass(className);
                ExtensionWrapper extensionWrapper = this.createExtensionWrapper(extensionClass);
                result.add(extensionWrapper);
                log.debug("Added extension '{}' with ordinal {}", (Object)className, (Object)extensionWrapper.getOrdinal());
            }
            catch (ClassNotFoundException | NoClassDefFoundError e) {
                log.error(e.getMessage(), e);
            }
        }
        if (result.isEmpty()) {
            log.debug("No extensions found for plugin '{}'", (Object)pluginId);
        } else {
            log.debug("Found {} extensions for plugin '{}'", (Object)result.size(), (Object)pluginId);
        }
        Collections.sort(result);
        return result;
    }

    @Override
    public Set<String> findClassNames(String pluginId) {
        Set<String> classNames = this.getEntries().get(pluginId);
        if (classNames == null) {
            return Collections.emptySet();
        }
        return classNames;
    }

    @Override
    public void pluginStateChanged(PluginStateEvent event) {
        this.entries = null;
        if (this.checkForExtensionDependencies == null && PluginState.STARTED.equals((Object)event.getPluginState())) {
            for (PluginDependency dependency : event.getPlugin().getDescriptor().getDependencies()) {
                if (!dependency.isOptional()) continue;
                log.debug("Enable check for extension dependencies via ASM.");
                this.checkForExtensionDependencies = true;
                break;
            }
        }
    }

    public final boolean isCheckForExtensionDependencies() {
        return Boolean.TRUE.equals(this.checkForExtensionDependencies);
    }

    public void setCheckForExtensionDependencies(boolean checkForExtensionDependencies) {
        this.checkForExtensionDependencies = checkForExtensionDependencies;
    }

    protected void debugExtensions(Set<String> extensions) {
        if (log.isDebugEnabled()) {
            if (extensions.isEmpty()) {
                log.debug("No extensions found");
            } else {
                log.debug("Found possible {} extensions:", (Object)extensions.size());
                for (String extension : extensions) {
                    log.debug("   " + extension);
                }
            }
        }
    }

    private Map<String, Set<String>> readStorages() {
        LinkedHashMap<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
        result.putAll(this.readClasspathStorages());
        result.putAll(this.readPluginsStorages());
        return result;
    }

    private Map<String, Set<String>> getEntries() {
        if (this.entries == null) {
            this.entries = this.readStorages();
        }
        return this.entries;
    }

    private ExtensionInfo getExtensionInfo(String className, ClassLoader classLoader) {
        if (this.extensionInfos == null) {
            this.extensionInfos = new HashMap<String, ExtensionInfo>();
        }
        if (!this.extensionInfos.containsKey(className)) {
            log.trace("Load annotation for '{}' using asm", (Object)className);
            ExtensionInfo info = ExtensionInfo.load(className, classLoader);
            if (info == null) {
                log.warn("No extension annotation was found for '{}'", (Object)className);
                this.extensionInfos.put(className, null);
            } else {
                this.extensionInfos.put(className, info);
            }
        }
        return this.extensionInfos.get(className);
    }

    private ExtensionWrapper createExtensionWrapper(Class<?> extensionClass) {
        Extension extensionAnnotation = AbstractExtensionFinder.findExtensionAnnotation(extensionClass);
        int ordinal = extensionAnnotation != null ? extensionAnnotation.ordinal() : 0;
        ExtensionDescriptor descriptor = new ExtensionDescriptor(ordinal, extensionClass);
        return new ExtensionWrapper(descriptor, this.pluginManager.getExtensionFactory());
    }

    public static Extension findExtensionAnnotation(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Extension.class)) {
            return clazz.getAnnotation(Extension.class);
        }
        for (Annotation annotation : clazz.getAnnotations()) {
            Extension extensionAnnotation;
            Class<? extends Annotation> annotationClass = annotation.annotationType();
            if (annotationClass.getName().startsWith("java.lang.annotation") || (extensionAnnotation = AbstractExtensionFinder.findExtensionAnnotation(annotationClass)) == null) continue;
            return extensionAnnotation;
        }
        return null;
    }

    private void checkDifferentClassLoaders(Class<?> type, Class<?> extensionClass) {
        ClassLoader typeClassLoader = type.getClassLoader();
        ClassLoader extensionClassLoader = extensionClass.getClassLoader();
        boolean match = ClassUtils.getAllInterfacesNames(extensionClass).contains(type.getSimpleName());
        if (match && !extensionClassLoader.equals(typeClassLoader)) {
            log.error("Different class loaders: '{}' (E) and '{}' (EP)", (Object)extensionClassLoader, (Object)typeClassLoader);
        }
    }
}

