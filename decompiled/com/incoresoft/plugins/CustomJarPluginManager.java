/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.plugins.CustomJarPluginRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import org.pf4j.CompoundPluginRepository;
import org.pf4j.DevelopmentPluginRepository;
import org.pf4j.JarPluginManager;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginRepository;
import org.pf4j.PluginRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomJarPluginManager
extends JarPluginManager {
    private static final Logger log = LoggerFactory.getLogger(CustomJarPluginManager.class);

    public CustomJarPluginManager(String pluginsPathValue) {
        super(CustomJarPluginManager.createRoot(pluginsPathValue));
    }

    @Override
    protected PluginRepository createPluginRepository() {
        return new CompoundPluginRepository().add(new DevelopmentPluginRepository(this.getPluginsRoots()), this::isDevelopment).add(new CustomJarPluginRepository(this.getPluginsRoots()), this::isNotDevelopment);
    }

    private static Path createRoot(String pluginsPathValue) {
        Path pluginsPath = Path.of(pluginsPathValue, new String[0]);
        if (!Files.exists(pluginsPath, new LinkOption[0])) {
            try {
                Files.createDirectories(pluginsPath, new FileAttribute[0]);
            }
            catch (IOException e) {
                log.error("Couldn't create plugin directory", e);
            }
        }
        return pluginsPath;
    }

    @Override
    protected void validatePluginDescriptor(PluginDescriptor descriptor) {
        super.validatePluginDescriptor(descriptor);
        try {
            Version.valueOf(descriptor.getVersion());
        }
        catch (Exception e) {
            throw new PluginRuntimeException("Invalid plugin version", new Object[]{e});
        }
    }
}

