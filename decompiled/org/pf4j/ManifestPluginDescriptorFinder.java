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
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.pf4j.DefaultPluginDescriptor;
import org.pf4j.PluginDescriptor;
import org.pf4j.PluginDescriptorFinder;
import org.pf4j.PluginRuntimeException;
import org.pf4j.util.FileUtils;
import org.pf4j.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManifestPluginDescriptorFinder
implements PluginDescriptorFinder {
    private static final Logger log = LoggerFactory.getLogger(ManifestPluginDescriptorFinder.class);
    public static final String PLUGIN_ID = "Plugin-Id";
    public static final String PLUGIN_DESCRIPTION = "Plugin-Description";
    public static final String PLUGIN_CLASS = "Plugin-Class";
    public static final String PLUGIN_VERSION = "Plugin-Version";
    public static final String PLUGIN_PROVIDER = "Plugin-Provider";
    public static final String PLUGIN_DEPENDENCIES = "Plugin-Dependencies";
    public static final String PLUGIN_REQUIRES = "Plugin-Requires";
    public static final String PLUGIN_LICENSE = "Plugin-License";

    @Override
    public boolean isApplicable(Path pluginPath) {
        return Files.exists(pluginPath, new LinkOption[0]) && (Files.isDirectory(pluginPath, new LinkOption[0]) || FileUtils.isZipOrJarFile(pluginPath));
    }

    @Override
    public PluginDescriptor find(Path pluginPath) {
        Manifest manifest = this.readManifest(pluginPath);
        return this.createPluginDescriptor(manifest);
    }

    protected Manifest readManifest(Path pluginPath) {
        if (FileUtils.isJarFile(pluginPath)) {
            return this.readManifestFromJar(pluginPath);
        }
        if (FileUtils.isZipFile(pluginPath)) {
            return this.readManifestFromZip(pluginPath);
        }
        return this.readManifestFromDirectory(pluginPath);
    }

    protected PluginDescriptor createPluginDescriptor(Manifest manifest) {
        String version;
        DefaultPluginDescriptor pluginDescriptor = this.createPluginDescriptorInstance();
        Attributes attributes = manifest.getMainAttributes();
        String id = attributes.getValue(PLUGIN_ID);
        pluginDescriptor.setPluginId(id);
        String description = attributes.getValue(PLUGIN_DESCRIPTION);
        if (StringUtils.isNullOrEmpty(description)) {
            pluginDescriptor.setPluginDescription("");
        } else {
            pluginDescriptor.setPluginDescription(description);
        }
        String clazz = attributes.getValue(PLUGIN_CLASS);
        if (StringUtils.isNotNullOrEmpty(clazz)) {
            pluginDescriptor.setPluginClass(clazz);
        }
        if (StringUtils.isNotNullOrEmpty(version = attributes.getValue(PLUGIN_VERSION))) {
            pluginDescriptor.setPluginVersion(version);
        }
        String provider = attributes.getValue(PLUGIN_PROVIDER);
        pluginDescriptor.setProvider(provider);
        String dependencies = attributes.getValue(PLUGIN_DEPENDENCIES);
        pluginDescriptor.setDependencies(dependencies);
        String requires = attributes.getValue(PLUGIN_REQUIRES);
        if (StringUtils.isNotNullOrEmpty(requires)) {
            pluginDescriptor.setRequires(requires);
        }
        pluginDescriptor.setLicense(attributes.getValue(PLUGIN_LICENSE));
        return pluginDescriptor;
    }

    protected DefaultPluginDescriptor createPluginDescriptorInstance() {
        return new DefaultPluginDescriptor();
    }

    protected Manifest readManifestFromJar(Path jarPath) {
        Manifest manifest;
        JarFile jar = new JarFile(jarPath.toFile());
        try {
            manifest = jar.getManifest();
        }
        catch (Throwable throwable) {
            try {
                try {
                    jar.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException e) {
                throw new PluginRuntimeException(e, "Cannot read manifest from {}", jarPath);
            }
        }
        jar.close();
        return manifest;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    protected Manifest readManifestFromZip(Path zipPath) {
        try (ZipFile zip2 = new ZipFile(zipPath.toFile());){
            Manifest manifest;
            block13: {
                ZipEntry manifestEntry = zip2.getEntry("classes/META-INF/MANIFEST.MF");
                InputStream manifestInput = zip2.getInputStream(manifestEntry);
                try {
                    manifest = new Manifest(manifestInput);
                    if (manifestInput == null) break block13;
                }
                catch (Throwable throwable) {
                    if (manifestInput != null) {
                        try {
                            manifestInput.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                manifestInput.close();
            }
            return manifest;
        }
        catch (IOException e) {
            throw new PluginRuntimeException(e, "Cannot read manifest from {}", zipPath);
        }
    }

    protected Manifest readManifestFromDirectory(Path pluginPath) {
        Manifest manifest;
        block10: {
            Path manifestPath = FileUtils.findFile(pluginPath, "MANIFEST.MF");
            if (manifestPath == null) {
                throw new PluginRuntimeException("Cannot find the manifest path");
            }
            log.debug("Lookup plugin descriptor in '{}'", (Object)manifestPath);
            if (Files.notExists(manifestPath, new LinkOption[0])) {
                throw new PluginRuntimeException("Cannot find '{}' path", manifestPath);
            }
            InputStream input = Files.newInputStream(manifestPath, new OpenOption[0]);
            try {
                manifest = new Manifest(input);
                if (input == null) break block10;
            }
            catch (Throwable throwable) {
                try {
                    if (input != null) {
                        try {
                            input.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (IOException e) {
                    throw new PluginRuntimeException(e, "Cannot read manifest from {}", pluginPath);
                }
            }
            input.close();
        }
        return manifest;
    }
}

