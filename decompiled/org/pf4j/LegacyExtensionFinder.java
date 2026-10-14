/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.pf4j.AbstractExtensionFinder;
import org.pf4j.PluginManager;
import org.pf4j.PluginWrapper;
import org.pf4j.processor.ExtensionStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegacyExtensionFinder
extends AbstractExtensionFinder {
    private static final Logger log = LoggerFactory.getLogger(LegacyExtensionFinder.class);
    public static final String EXTENSIONS_RESOURCE = "META-INF/extensions.idx";

    public LegacyExtensionFinder(PluginManager pluginManager) {
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
        catch (IOException e) {
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
            log.debug("Reading extensions storage from plugin '{}'", (Object)pluginId);
            HashSet<String> bucket = new HashSet<String>();
            try {
                log.debug("Read '{}'", (Object)EXTENSIONS_RESOURCE);
                ClassLoader pluginClassLoader = plugin.getPluginClassLoader();
                try (InputStream resourceStream = pluginClassLoader.getResourceAsStream(EXTENSIONS_RESOURCE);){
                    if (resourceStream == null) {
                        log.debug("Cannot find '{}'", (Object)EXTENSIONS_RESOURCE);
                    } else {
                        this.collectExtensions(resourceStream, bucket);
                    }
                }
                this.debugExtensions(bucket);
                result.put(pluginId, bucket);
            }
            catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

    private void collectExtensions(Enumeration<URL> urls2, Set<String> bucket) throws IOException {
        while (urls2.hasMoreElements()) {
            URL url2 = urls2.nextElement();
            log.debug("Read '{}'", (Object)url2.getFile());
            this.collectExtensions(url2.openStream(), bucket);
        }
    }

    private void collectExtensions(InputStream inputStream2, Set<String> bucket) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(inputStream2, StandardCharsets.UTF_8);){
            ExtensionStorage.read(reader, bucket);
        }
    }
}

