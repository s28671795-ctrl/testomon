/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.pf4j.DefaultPluginDescriptor;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginRuntimeException;
import org.pf4j.util.FileUtils;
import org.pf4j.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesPluginDescriptorFinder
implements PluginDescriptorFinder {
    private static final Logger log = LoggerFactory.getLogger(PropertiesPluginDescriptorFinder.class);
    public static final String DEFAULT_PROPERTIES_FILE_NAME = "plugin.properties";
    public static final String PLUGIN_ID = "plugin.id";
    public static final String PLUGIN_DESCRIPTION = "plugin.description";
    public static final String PLUGIN_CLASS = "plugin.class";
    public static final String PLUGIN_VERSION = "plugin.version";
    public static final String PLUGIN_PROVIDER = "plugin.provider";
    public static final String PLUGIN_DEPENDENCIES = "plugin.dependencies";
    public static final String PLUGIN_REQUIRES = "plugin.requires";
    public static final String PLUGIN_LICENSE = "plugin.license";
    protected String propertiesFileName;

    public PropertiesPluginDescriptorFinder() {
        this(DEFAULT_PROPERTIES_FILE_NAME);
    }

    public PropertiesPluginDescriptorFinder(String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
    }

    @Override
    public boolean isApplicable(Path pluginPath) {
        return Files.exists(pluginPath, new LinkOption[0]) && (Files.isDirectory(pluginPath, new LinkOption[0]) || FileUtils.isZipOrJarFile(pluginPath));
    }

    @Override
    public PluginDescriptor find(Path pluginPath) {
        Properties properties = this.readProperties(pluginPath);
        return this.createPluginDescriptor(properties);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Properties readProperties(Path pluginPath) {
        Path propertiesPath = this.getPropertiesPath(pluginPath, this.propertiesFileName);
        if (propertiesPath == null) {
            throw new PluginRuntimeException("Cannot find the properties path");
        }
        Properties properties = new Properties();
        try {
            log.debug("Lookup plugin descriptor in '{}'", (Object)propertiesPath);
            if (Files.notExists(propertiesPath, new LinkOption[0])) {
                throw new PluginRuntimeException("Cannot find '{}' path", propertiesPath);
            }
            try (InputStream input = Files.newInputStream(propertiesPath, new OpenOption[0]);){
                properties.load(input);
            }
            catch (IOException e) {
                throw new PluginRuntimeException(e);
            }
        }
        finally {
            FileUtils.closePath(propertiesPath);
        }
        return properties;
    }

    protected Path getPropertiesPath(Path pluginPath, String propertiesFileName) {
        if (Files.isDirectory(pluginPath, new LinkOption[0])) {
            return pluginPath.resolve(Paths.get(propertiesFileName, new String[0]));
        }
        try {
            return FileUtils.getPath(pluginPath, propertiesFileName, new String[0]);
        }
        catch (IOException e) {
            throw new PluginRuntimeException(e);
        }
    }

    protected PluginDescriptor createPluginDescriptor(Properties properties) {
        String version;
        DefaultPluginDescriptor pluginDescriptor = this.createPluginDescriptorInstance();
        String id = properties.getProperty(PLUGIN_ID);
        pluginDescriptor.setPluginId(id);
        String description = properties.getProperty(PLUGIN_DESCRIPTION);
        if (StringUtils.isNullOrEmpty(description)) {
            pluginDescriptor.setPluginDescription("");
        } else {
            pluginDescriptor.setPluginDescription(description);
        }
        String clazz = properties.getProperty(PLUGIN_CLASS);
        if (StringUtils.isNotNullOrEmpty(clazz)) {
            pluginDescriptor.setPluginClass(clazz);
        }
        if (StringUtils.isNotNullOrEmpty(version = properties.getProperty(PLUGIN_VERSION))) {
            pluginDescriptor.setPluginVersion(version);
        }
        String provider = properties.getProperty(PLUGIN_PROVIDER);
        pluginDescriptor.setProvider(provider);
        String dependencies = properties.getProperty(PLUGIN_DEPENDENCIES);
        pluginDescriptor.setDependencies(dependencies);
        String requires = properties.getProperty(PLUGIN_REQUIRES);
        if (StringUtils.isNotNullOrEmpty(requires)) {
            pluginDescriptor.setRequires(requires);
        }
        pluginDescriptor.setLicense(properties.getProperty(PLUGIN_LICENSE));
        return pluginDescriptor;
    }

    protected DefaultPluginDescriptor createPluginDescriptorInstance() {
        return new DefaultPluginDescriptor();
    }
}

