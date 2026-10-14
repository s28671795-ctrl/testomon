/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import org.pf4j.ClassLoadingStrategy;
import org.pf4j.PluginDependency;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginClassLoader
extends URLClassLoader {
    private static final Logger log = LoggerFactory.getLogger(PluginClassLoader.class);
    private static final String JAVA_PACKAGE_PREFIX = "java.";
    private static final String PLUGIN_PACKAGE_PREFIX = "org.pf4j.";
    private PluginManager pluginManager;
    private PluginDescriptor pluginDescriptor;
    private ClassLoadingStrategy classLoadingStrategy;

    public PluginClassLoader(PluginManager pluginManager, PluginDescriptor pluginDescriptor, ClassLoader parent) {
        this(pluginManager, pluginDescriptor, parent, ClassLoadingStrategy.PDA);
    }

    @Deprecated
    public PluginClassLoader(PluginManager pluginManager, PluginDescriptor pluginDescriptor, ClassLoader parent, boolean parentFirst) {
        this(pluginManager, pluginDescriptor, parent, parentFirst ? ClassLoadingStrategy.APD : ClassLoadingStrategy.PDA);
    }

    public PluginClassLoader(PluginManager pluginManager, PluginDescriptor pluginDescriptor, ClassLoader parent, ClassLoadingStrategy classLoadingStrategy) {
        super(new URL[0], parent);
        this.pluginManager = pluginManager;
        this.pluginDescriptor = pluginDescriptor;
        this.classLoadingStrategy = classLoadingStrategy;
    }

    @Override
    public void addURL(URL url2) {
        log.debug("Add '{}'", (Object)url2);
        super.addURL(url2);
    }

    public void addFile(File file) {
        try {
            this.addURL(file.getCanonicalFile().toURI().toURL());
        }
        catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        Object object = this.getClassLoadingLock(className);
        synchronized (object) {
            if (className.startsWith(JAVA_PACKAGE_PREFIX)) {
                return this.findSystemClass(className);
            }
            if (className.startsWith(PLUGIN_PACKAGE_PREFIX) && !className.startsWith("org.pf4j.demo") && !className.startsWith("org.pf4j.test")) {
                return this.getParent().loadClass(className);
            }
            log.trace("Received request to load class '{}'", (Object)className);
            Class<?> loadedClass = this.findLoadedClass(className);
            if (loadedClass != null) {
                log.trace("Found loaded class '{}'", (Object)className);
                return loadedClass;
            }
            for (ClassLoadingStrategy.Source classLoadingSource : this.classLoadingStrategy.getSources()) {
                Class<?> c = null;
                try {
                    switch (classLoadingSource) {
                        case APPLICATION: {
                            c = super.loadClass(className);
                            break;
                        }
                        case PLUGIN: {
                            c = this.findClass(className);
                            break;
                        }
                        case DEPENDENCIES: {
                            c = this.loadClassFromDependencies(className);
                        }
                    }
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
                if (c != null) {
                    log.trace("Found class '{}' in {} classpath", (Object)className, (Object)classLoadingSource);
                    return c;
                }
                log.trace("Couldn't find class '{}' in {} classpath", (Object)className, (Object)classLoadingSource);
            }
            throw new ClassNotFoundException(className);
        }
    }

    @Override
    public URL getResource(String name) {
        log.trace("Received request to load resource '{}'", (Object)name);
        for (ClassLoadingStrategy.Source classLoadingSource : this.classLoadingStrategy.getSources()) {
            URL url2 = null;
            switch (classLoadingSource) {
                case APPLICATION: {
                    url2 = super.getResource(name);
                    break;
                }
                case PLUGIN: {
                    url2 = this.findResource(name);
                    break;
                }
                case DEPENDENCIES: {
                    url2 = this.findResourceFromDependencies(name);
                }
            }
            if (url2 != null) {
                log.trace("Found resource '{}' in {} classpath", (Object)name, (Object)classLoadingSource);
                return url2;
            }
            log.trace("Couldn't find resource '{}' in {}", (Object)name, (Object)classLoadingSource);
        }
        return null;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        ArrayList<URL> resources = new ArrayList<URL>();
        log.trace("Received request to load resources '{}'", (Object)name);
        for (ClassLoadingStrategy.Source classLoadingSource : this.classLoadingStrategy.getSources()) {
            switch (classLoadingSource) {
                case APPLICATION: {
                    if (this.getParent() == null) break;
                    resources.addAll(Collections.list(this.getParent().getResources(name)));
                    break;
                }
                case PLUGIN: {
                    resources.addAll(Collections.list(this.findResources(name)));
                    break;
                }
                case DEPENDENCIES: {
                    resources.addAll(this.findResourcesFromDependencies(name));
                }
            }
        }
        return Collections.enumeration(resources);
    }

    protected Class<?> loadClassFromDependencies(String className) {
        log.trace("Search in dependencies for class '{}'", (Object)className);
        List<PluginDependency> dependencies = this.pluginDescriptor.getDependencies();
        for (PluginDependency dependency : dependencies) {
            ClassLoader classLoader = this.pluginManager.getPluginClassLoader(dependency.getPluginId());
            if (classLoader == null && dependency.isOptional()) continue;
            try {
                return classLoader.loadClass(className);
            }
            catch (ClassNotFoundException classNotFoundException) {
            }
        }
        return null;
    }

    protected URL findResourceFromDependencies(String name) {
        log.trace("Search in dependencies for resource '{}'", (Object)name);
        List<PluginDependency> dependencies = this.pluginDescriptor.getDependencies();
        for (PluginDependency dependency : dependencies) {
            URL url2;
            PluginClassLoader classLoader = (PluginClassLoader)this.pluginManager.getPluginClassLoader(dependency.getPluginId());
            if (classLoader == null && dependency.isOptional() || !Objects.nonNull(url2 = classLoader.findResource(name))) continue;
            return url2;
        }
        return null;
    }

    protected Collection<URL> findResourcesFromDependencies(String name) throws IOException {
        log.trace("Search in dependencies for resources '{}'", (Object)name);
        ArrayList<URL> results = new ArrayList<URL>();
        List<PluginDependency> dependencies = this.pluginDescriptor.getDependencies();
        for (PluginDependency dependency : dependencies) {
            PluginClassLoader classLoader = (PluginClassLoader)this.pluginManager.getPluginClassLoader(dependency.getPluginId());
            if (classLoader == null && dependency.isOptional()) continue;
            results.addAll(Collections.list(classLoader.findResources(name)));
        }
        return results;
    }
}

