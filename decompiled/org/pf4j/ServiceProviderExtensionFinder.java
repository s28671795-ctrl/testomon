/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.pf4j.AbstractExtensionFinder;
import org.pf4j.PluginClassLoader;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.pf4j.processor.ExtensionStorage;
import org.pf4j.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceProviderExtensionFinder
extends AbstractExtensionFinder {
    private static final Logger log = LoggerFactory.getLogger(ServiceProviderExtensionFinder.class);
    public static final String EXTENSIONS_RESOURCE = "META-INF/services";

    public ServiceProviderExtensionFinder(PluginManager pluginManager) {
        super(pluginManager);
    }

    @Override
    public Map<String, Set<String>> readClasspathStorages() {
        log.debug("Reading extensions storages from classpath");
        LinkedHashMap<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
        HashSet<String> bucket = new HashSet<String>();
        try {
            Enumeration<URL> urls2 = this.getClass().getClassLoader().getResources(EXTENSIONS_RESOURCE);
            if (urls2.hasMoreElements()) {
                this.collectExtensions(urls2, bucket);
            } else {
                log.debug("Cannot find '{}'", (Object)EXTENSIONS_RESOURCE);
            }
            this.debugExtensions(bucket);
            result.put(null, bucket);
        }
        catch (IOException | URISyntaxException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Map<String, Set<String>> readPluginsStorages() {
        log.debug("Reading extensions storages from plugins");
        LinkedHashMap<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
        List<PluginWrapper> plugins = this.pluginManager.getPlugins();
        for (PluginWrapper plugin : plugins) {
            String pluginId = plugin.getDescriptor().getPluginId();
            log.debug("Reading extensions storages for plugin '{}'", (Object)pluginId);
            HashSet<String> bucket = new HashSet<String>();
            try {
                Enumeration<URL> urls2 = ((PluginClassLoader)plugin.getPluginClassLoader()).findResources(EXTENSIONS_RESOURCE);
                if (urls2.hasMoreElements()) {
                    this.collectExtensions(urls2, bucket);
                } else {
                    log.debug("Cannot find '{}'", (Object)EXTENSIONS_RESOURCE);
                }
                this.debugExtensions(bucket);
                result.put(pluginId, bucket);
            }
            catch (IOException | URISyntaxException e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    private void collectExtensions(Enumeration<URL> urls2, Set<String> bucket) throws URISyntaxException, IOException {
        while (urls2.hasMoreElements()) {
            URL url2 = urls2.nextElement();
            log.debug("Read '{}'", (Object)url2.getFile());
            this.collectExtensions(url2, bucket);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void collectExtensions(URL url2, Set<String> bucket) throws URISyntaxException, IOException {
        Path extensionPath = url2.toURI().getScheme().equals("jar") ? FileUtils.getPath(url2.toURI(), EXTENSIONS_RESOURCE, new String[0]) : Paths.get(url2.toURI());
        try {
            bucket.addAll(this.readExtensions(extensionPath));
        }
        finally {
            FileUtils.closePath(extensionPath);
        }
    }

    private Set<String> readExtensions(Path extensionPath) throws IOException {
        final HashSet<String> result = new HashSet<String>();
        Files.walkFileTree(extensionPath, Collections.emptySet(), 1, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                log.debug("Read '{}'", (Object)file);
                try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);){
                    ExtensionStorage.read(reader, result);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return result;
    }
}

