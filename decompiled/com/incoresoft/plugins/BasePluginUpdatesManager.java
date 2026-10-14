/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugins;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.http.HttpInstaller;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.plugins.BasePluginJarsManager;
import com.incoresoft.plugins.BasePluginVersionsManager;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.plugins.PluginHttpInstaller;
import com.incoresoft.plugins.PluginUpdatesTracker;
import com.incoresoft.plugins.broadcast.PluginInstallationsRegistry;
import com.incoresoft.plugins.broadcast.PluginInstallationsSSEBroadcaster;
import com.incoresoft.plugins.broadcast.listeners.ActiveInstallationUnregisteringListener;
import com.incoresoft.plugins.broadcast.listeners.SSEPluginFromUpdateAllListener;
import com.incoresoft.plugins.broadcast.listeners.SSEPluginInstallationListener;
import com.incoresoft.plugins.exceptions.PluginAlreadyInstalledException;
import com.incoresoft.plugins.exceptions.PluginAlreadyUpdatedException;
import com.incoresoft.plugins.exceptions.PluginInvalidException;
import com.incoresoft.plugins.exceptions.PluginIsBeingInstalledException;
import com.incoresoft.plugins.exceptions.PluginLoadingException;
import com.incoresoft.plugins.exceptions.PluginNotCompatibleException;
import com.incoresoft.plugins.exceptions.PluginNotFoundException;
import com.incoresoft.plugins.exceptions.PluginNotFoundInMarketplaceException;
import com.incoresoft.plugins.exceptions.PluginNotFoundInSystemException;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.plugins.models.MarketplacePluginInfo;
import com.incoresoft.plugins.models.PluginFileInfo;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.system.processes.InterferingProcessesRegistry;
import com.incoresoft.version.VersionUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.pf4j.PluginState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePluginUpdatesManager {
    private static final Logger log = LoggerFactory.getLogger(BasePluginUpdatesManager.class);
    private final CoreServiceVersionSupplier coreServiceVersionSupplier;
    private final BasePluginJarsManager jarsManager;
    private final BasePluginVersionsManager versionsManager;
    private final PluginInstallationsSSEBroadcaster broadcaster;
    private final JarFileManifestReader manifestReader;
    private final PluginType pluginType;
    private final PluginInstallationsRegistry installationsRegistry;
    public static final String TEMP_FOLDER_NAME = "temp";

    protected BasePluginUpdatesManager(CoreServiceVersionSupplier coreServiceVersionSupplier, BasePluginJarsManager jarsManager, BasePluginVersionsManager versionsManager, PluginInstallationsSSEBroadcaster broadcaster, JarFileManifestReader manifestReader, InterferingProcessesRegistry processesRegistry, PluginType pluginType) {
        this.coreServiceVersionSupplier = coreServiceVersionSupplier;
        this.jarsManager = jarsManager;
        this.versionsManager = versionsManager;
        this.broadcaster = broadcaster;
        this.manifestReader = manifestReader;
        this.pluginType = pluginType;
        this.installationsRegistry = new PluginInstallationsRegistry(processesRegistry, pluginType);
    }

    public void installPlugin(String pluginId, Version version, InstallationListener ... listeners) {
        MarketplacePluginInfo plugin = this.versionsManager.getMarketplacePluginInfo(pluginId, version);
        this.installPlugin(plugin, Arrays.stream(listeners).toList());
    }

    public void installLatestVersion(String pluginId, InstallationListener ... listeners) {
        List<MarketplacePluginInfo> pluginsWithMinorVersion = this.versionsManager.getMarketplacePluginInfos(pluginId);
        if (pluginsWithMinorVersion.isEmpty()) {
            throw new PluginNotFoundInMarketplaceException(pluginId);
        }
        this.installPlugin(pluginsWithMinorVersion.getFirst(), Arrays.stream(listeners).toList());
    }

    public void updatePlugin(String pluginId, Version version, InstallationListener ... listeners) {
        MarketplacePluginInfo marketplaceInfo = this.versionsManager.getMarketplacePluginInfo(pluginId, version);
        this.preparePluginForUpdate(marketplaceInfo, Arrays.stream(listeners).toList()).invoke();
    }

    public List<CompletableFuture<Void>> updateAll(InstallationListener ... listeners) {
        List<String> pluginIds = this.jarsManager.getPlugins().stream().map(PluginFileInfo::getId).toList();
        PluginUpdatesTracker tracker = new PluginUpdatesTracker(pluginIds.size());
        ArrayList<CompletableFuture<Void>> futures = new ArrayList<CompletableFuture<Void>>(pluginIds.size());
        for (String id : pluginIds) {
            try {
                MarketplacePluginInfo plugin = this.versionsManager.getLatestMarketplacePluginInfo(id);
                HttpInstaller installer = this.preparePluginForUpdate(plugin, Arrays.stream(listeners).toList());
                installer.subscribe(new SSEPluginFromUpdateAllListener(this.broadcaster, tracker, id, this.pluginType));
                CompletableFuture<Void> future = installer.invoke();
                futures.add(future);
            }
            catch (PluginAlreadyUpdatedException e) {
                tracker.trackUpdateFinished(id);
                log.info("Plugin {} is already up to date", (Object)id);
            }
            catch (PluginNotFoundException e) {
                tracker.trackUpdateFinished(id);
                log.info("Plugin {} cannot be updated as it is was not found in marketplace", (Object)id);
            }
            catch (PluginIsBeingInstalledException e) {
                tracker.trackUpdateFinished(id);
                log.info("Plugin {} is already being installed", (Object)id);
            }
            catch (Throwable e) {
                tracker.trackUpdateFinished(id);
                log.error("Unexpected error while updating plugin {}", (Object)id, (Object)e);
            }
        }
        return Collections.unmodifiableList(futures);
    }

    public void installPlugin(Path tempPath) {
        String pluginId = this.readCompatiblePluginId(tempPath);
        Path newPluginPath = Path.of(this.jarsManager.getPluginsPathValue(), tempPath.getFileName().toString());
        this.installationsRegistry.registerOrThrowIfRegistered(pluginId);
        try {
            Optional<PluginFileInfo> existingPlugin = this.findExistingPlugin(pluginId, newPluginPath);
            if (existingPlugin.isPresent()) {
                throw new PluginAlreadyInstalledException(existingPlugin.get().getId());
            }
            Files.copy(tempPath, newPluginPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            this.installationsRegistry.unregister(pluginId);
        }
        this.jarsManager.loadAndStart(newPluginPath);
    }

    public void updatePlugin(String pluginId, Path tempPath) {
        if (!this.readCompatiblePluginId(tempPath).equals(pluginId)) {
            throw new PluginInvalidException();
        }
        this.installationsRegistry.registerOrThrowIfRegistered(pluginId);
        try {
            PluginFileInfo installedPlugin = this.jarsManager.getPlugin(pluginId).orElseThrow(() -> new PluginNotFoundInSystemException(pluginId));
            Path oldPath = installedPlugin.getPath();
            Path newPath = oldPath.getParent().resolve(tempPath.getFileName().toString());
            Path backupPath = Path.of(String.valueOf(oldPath) + ".bak", new String[0]);
            Optional<PluginFileInfo> pluginByPath = this.findPluginByPath(newPath);
            if (pluginByPath.isPresent() && !pluginByPath.get().getId().equals(pluginId)) {
                throw new PluginAlreadyInstalledException(pluginByPath.get().getId());
            }
            boolean start2 = installedPlugin.getState() == PluginState.STARTED;
            this.jarsManager.stopPlugin(pluginId, false);
            this.jarsManager.unloadPlugin(pluginId);
            if (Files.exists(oldPath, new LinkOption[0])) {
                Files.move(oldPath, backupPath, StandardCopyOption.REPLACE_EXISTING);
            }
            Files.copy(tempPath, newPath, StandardCopyOption.REPLACE_EXISTING);
            try {
                if (start2) {
                    this.jarsManager.loadAndStart(newPath);
                } else {
                    this.jarsManager.loadPlugin(newPath);
                }
            }
            catch (RuntimeException e) {
                this.restorePreviousFile(pluginId, newPath, backupPath, oldPath, start2);
                throw e instanceof PluginLoadingException ? e : new PluginLoadingException(e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            this.installationsRegistry.unregister(pluginId);
        }
    }

    private void restorePreviousFile(String pluginId, Path newPath, Path backupPath, Path oldPath, boolean start2) {
        try {
            if (this.jarsManager.getPlugin(pluginId).isPresent()) {
                this.jarsManager.unloadPlugin(pluginId);
            }
            Files.deleteIfExists(newPath);
            if (!Files.exists(backupPath, new LinkOption[0])) {
                return;
            }
            Files.move(backupPath, oldPath, StandardCopyOption.REPLACE_EXISTING);
            this.jarsManager.loadPlugin(oldPath);
            if (start2) {
                this.jarsManager.startPlugin(pluginId);
            }
        }
        catch (Exception e) {
            log.error("Couldn't restore previous file of plugin {}", (Object)pluginId, (Object)e);
        }
    }

    private String readCompatiblePluginId(Path pluginPath) {
        Version pluginVersion;
        Map<String, String> attributes = this.manifestReader.read(pluginPath.toFile());
        String pluginId = attributes.get("Plugin-Id");
        if (pluginId == null) {
            throw new PluginInvalidException();
        }
        try {
            pluginVersion = Version.valueOf(attributes.get("Plugin-Version"));
        }
        catch (Exception e) {
            throw new PluginInvalidException();
        }
        Version coreVersion = this.coreServiceVersionSupplier.getCoreVersion();
        if (!VersionUtils.areCompatible(coreVersion, pluginVersion)) {
            throw new PluginNotCompatibleException(pluginVersion);
        }
        return pluginId;
    }

    private HttpInstaller preparePluginForUpdate(MarketplacePluginInfo plugin, List<InstallationListener> listeners) {
        this.installationsRegistry.registerOrThrowIfRegistered(plugin.getId());
        try {
            InstalledPluginInfo installedPlugin = this.jarsManager.getPlugin(plugin.getId()).map(it -> new InstalledPluginInfo((PluginFileInfo)it, plugin.getType())).orElseThrow(() -> new PluginNotFoundInSystemException(plugin.getId()));
            if (installedPlugin.getVersion().equals(plugin.getVersion())) {
                throw new PluginAlreadyUpdatedException(plugin.getId());
            }
            Path installPath = installedPlugin.getInstallPath().getParent().resolve(BasePluginUpdatesManager.getFileName(plugin));
            Optional<PluginFileInfo> pluginByPath = this.findPluginByPath(installPath);
            if (pluginByPath.isPresent() && !pluginByPath.get().getId().equals(plugin.getId())) {
                throw new PluginAlreadyInstalledException(pluginByPath.get().getId());
            }
            this.jarsManager.stopPlugin(plugin.getId(), false);
            this.jarsManager.unloadPlugin(plugin.getId());
            if (Files.exists(installedPlugin.getInstallPath(), new LinkOption[0])) {
                Files.move(installedPlugin.getInstallPath(), Path.of(String.valueOf(installedPlugin.getInstallPath()) + ".bak", new String[0]), StandardCopyOption.REPLACE_EXISTING);
            }
            boolean startAfterInstallation = installedPlugin.isEnabled();
            HttpInstaller installer = this.initInstaller(plugin, installPath, startAfterInstallation);
            listeners.forEach(installer::subscribe);
            return installer;
        }
        catch (RuntimeException e) {
            this.installationsRegistry.unregister(plugin.getId());
            throw e;
        }
        catch (IOException e) {
            this.installationsRegistry.unregister(plugin.getId());
            throw new RuntimeException(e);
        }
    }

    private static String getFileName(MarketplacePluginInfo marketplacePluginInfo) {
        return marketplacePluginInfo.getDownloadUrl().substring(marketplacePluginInfo.getDownloadUrl().lastIndexOf("/") + 1);
    }

    private void installPlugin(MarketplacePluginInfo plugin, List<InstallationListener> listeners) {
        this.installationsRegistry.registerOrThrowIfRegistered(plugin.getId());
        try {
            Path installPath = Path.of(this.jarsManager.getPluginsPathValue(), BasePluginUpdatesManager.getFileName(plugin));
            Optional<PluginFileInfo> existingPlugin = this.findExistingPlugin(plugin.getId(), installPath);
            if (existingPlugin.isPresent()) {
                throw new PluginAlreadyInstalledException(existingPlugin.get().getId());
            }
            HttpInstaller installer = this.initInstaller(plugin, installPath, true);
            listeners.forEach(installer::subscribe);
            installer.invoke();
        }
        catch (RuntimeException e) {
            this.installationsRegistry.unregister(plugin.getId());
            throw e;
        }
    }

    private HttpInstaller initInstaller(MarketplacePluginInfo plugin, Path installPath, boolean startAfterInstallation) {
        PluginHttpInstaller installer = new PluginHttpInstaller(this.jarsManager, plugin, installPath, startAfterInstallation);
        installer.subscribe(new ActiveInstallationUnregisteringListener(this.installationsRegistry, plugin.getId()));
        installer.subscribe(new SSEPluginInstallationListener(this.broadcaster, plugin.getId(), this.pluginType));
        return installer;
    }

    private Optional<PluginFileInfo> findExistingPlugin(String pluginId, Path pluginPath) {
        Optional<PluginFileInfo> pluginById = this.jarsManager.getPlugin(pluginId);
        if (pluginById.isPresent()) {
            return pluginById;
        }
        return this.findPluginByPath(pluginPath);
    }

    private Optional<PluginFileInfo> findPluginByPath(Path pluginPath) {
        Map<String, String> attributes;
        String pluginByPathId;
        if (pluginPath.toFile().exists() && (pluginByPathId = (attributes = this.manifestReader.read(pluginPath.toFile())).get("Plugin-Id")) != null) {
            return this.jarsManager.getPlugin(pluginByPathId);
        }
        return Optional.empty();
    }
}

