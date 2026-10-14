/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core;

import com.github.zafarkhaja.semver.Version;
import com.google.inject.Inject;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.core.CoreServiceMarketplaceManager;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.core.installations.CoreHttpInstaller;
import com.incoresoft.core.installations.CoreInputStreamInstaller;
import com.incoresoft.core.installations.CoreServiceUpdatesRegistry;
import com.incoresoft.core.installations.listeners.CoreUpdateListener;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.marketplace.exceptions.MarketplaceServerUnavailableException;
import com.incoresoft.marketplace.models.ProductVersion;
import com.incoresoft.plugin.commons.utils.OsUtils;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.services.ServiceUpdateState;
import com.incoresoft.services.installations.CoreServiceInstallationsSSEBroadcaster;
import com.incoresoft.version.models.MinorVersion;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CoreServiceManager {
    private final CoreServiceUpdatesRegistry updatesRegistry;
    private final CoreServiceVersionSupplier coreServiceVersionSupplier;
    private final CoreServiceMarketplaceManager marketplaceManager;
    private final ApplicationReloadManager reloadManager;
    private final CoreServiceInstallationsSSEBroadcaster broadcaster;
    private final CoreService coreService;
    private final JarFileManifestReader manifestReader;
    private final Path tempPath;
    private final Path jvmStartPath;

    @Inject
    public CoreServiceManager(CoreServiceUpdatesRegistry updatesRegistry, CoreServiceVersionSupplier coreServiceVersionSupplier, CoreServiceMarketplaceManager marketplaceManager, ApplicationReloadManager reloadManager, CoreServiceInstallationsSSEBroadcaster broadcaster, CoreService coreService, JarFileManifestReader manifestReader) {
        this(updatesRegistry, coreServiceVersionSupplier, marketplaceManager, reloadManager, broadcaster, coreService, manifestReader, Path.of("temp", new String[0]), Path.of("", new String[0]).toAbsolutePath());
    }

    public CompletableFuture<Void> updateCore(Version version) {
        ProductVersion productVersion = this.marketplaceManager.getCoreVersion(version);
        this.updatesRegistry.registerOrThrowIfRegistered();
        String fileName = this.coreService.getFilename();
        Path tempInstallPath = this.tempPath.resolve(fileName);
        Path installPath = this.getInstallPath(tempInstallPath, fileName);
        CoreHttpInstaller installer = new CoreHttpInstaller(this.reloadManager, productVersion, tempInstallPath, installPath);
        installer.subscribe(new CoreUpdateListener(this.coreService, this.updatesRegistry, this.broadcaster));
        return installer.invoke();
    }

    public CompletableFuture<Version> updateCore(InputStream inputStream2, long size) {
        this.updatesRegistry.registerOrThrowIfRegistered();
        String fileName = this.coreService.getFilename();
        Path tempInstallPath = this.tempPath.resolve(fileName);
        Path installPath = this.getInstallPath(tempInstallPath, fileName);
        CoreInputStreamInstaller installer = new CoreInputStreamInstaller(inputStream2, size, tempInstallPath, installPath, this.manifestReader, this.coreServiceVersionSupplier, this.reloadManager, this.coreService);
        installer.subscribe(new CoreUpdateListener(this.coreService, this.updatesRegistry, this.broadcaster));
        return installer.invokeAndGetVersion();
    }

    private Path getInstallPath(Path tempPath, String fileName) {
        return OsUtils.getOperatingSystemType() == OsUtils.OSType.Windows ? tempPath : this.jvmStartPath.resolve(fileName);
    }

    public ServiceUpdateState getServiceUpdateState() {
        Version currentVersion = this.coreServiceVersionSupplier.getCoreVersion();
        boolean isBeingUpdated = this.updatesRegistry.isBeingUpdated();
        return new ServiceUpdateState(this.coreService.getId(), this.coreService.getName(), this.getCoreVersions(), currentVersion, isBeingUpdated);
    }

    private List<Version> getCoreVersions() {
        try {
            MinorVersion version = this.coreServiceVersionSupplier.getMinorCoreVersion();
            return this.marketplaceManager.getCoreVersions(version).stream().map(ProductVersion::getVersion).toList();
        }
        catch (MarketplaceServerUnavailableException e) {
            return List.of();
        }
    }

    CoreServiceManager(CoreServiceUpdatesRegistry updatesRegistry, CoreServiceVersionSupplier coreServiceVersionSupplier, CoreServiceMarketplaceManager marketplaceManager, ApplicationReloadManager reloadManager, CoreServiceInstallationsSSEBroadcaster broadcaster, CoreService coreService, JarFileManifestReader manifestReader, Path tempPath, Path jvmStartPath) {
        this.updatesRegistry = updatesRegistry;
        this.coreServiceVersionSupplier = coreServiceVersionSupplier;
        this.marketplaceManager = marketplaceManager;
        this.reloadManager = reloadManager;
        this.broadcaster = broadcaster;
        this.coreService = coreService;
        this.manifestReader = manifestReader;
        this.tempPath = tempPath;
        this.jvmStartPath = jvmStartPath;
    }
}

