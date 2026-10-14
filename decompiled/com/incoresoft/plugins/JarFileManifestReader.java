/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;
import org.pf4j.PluginWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JarFileManifestReader {
    private static final Logger log = LoggerFactory.getLogger(JarFileManifestReader.class);
    private static final String MANIFEST_FILE = "META-INF/MANIFEST.MF";
    public static final String PLUGIN_ID_PROPERTY = "Plugin-Id";
    public static final String PLUGIN_NAME_PROPERTY = "Plugin-Name";
    public static final String PLUGIN_VERSION_PROPERTY = "Plugin-Version";
    public static final String PLUGIN_ICON_RESOURCE_PATH_PROPERTY = "Plugin-Icon-Resource-Path";

    public Map<String, String> read(File jarFile) {
        Map<String, String> map;
        JarFile jarFiles = new JarFile(jarFile);
        try {
            Manifest manifest = jarFiles.getManifest();
            map = this.formatAttributes(manifest.getMainAttributes());
        }
        catch (Throwable throwable) {
            try {
                try {
                    jarFiles.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (Exception e) {
                log.error("Error occurred during reading manifest file in jar {}", (Object)jarFile.toURI(), (Object)e);
                return this.formatAttributes(new Attributes());
            }
        }
        jarFiles.close();
        return map;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Map<String, String> read(PluginWrapper wrapper) {
        String pluginId = wrapper.getPluginId();
        try (InputStream input = wrapper.getPluginClassLoader().getResourceAsStream(MANIFEST_FILE);){
            if (input == null) {
                log.warn("Manifest file not found in plugin {}", (Object)pluginId);
                Map<String, String> map2 = this.formatAttributes(new Attributes());
                return map2;
            }
            Manifest manifest = new Manifest(input);
            Map<String, String> map = this.formatAttributes(manifest.getMainAttributes());
            return map;
        }
        catch (Exception e) {
            log.error("Error occurred during reading manifest file in plugin {}", (Object)pluginId, (Object)e);
            return this.formatAttributes(new Attributes());
        }
    }

    private Map<String, String> formatAttributes(Attributes attributes) {
        return attributes.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), entry -> entry.getValue().toString()));
    }
}

