/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.core.installations;

import com.github.zafarkhaja.semver.Version;
import com.incoresoft.core.ApplicationReloadManager;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.files.InputStreamInstaller;
import com.incoresoft.plugins.JarFileManifestReader;
import com.incoresoft.services.exceptions.ServiceFileIsInvalidException;
import com.incoresoft.version.VersionUtils;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CoreInputStreamInstaller
extends InputStreamInstaller {
    private final JarFileManifestReader manifestReader;
    private final CoreServiceVersionSupplier coreServiceVersionSupplier;
    private final CoreService coreService;
    private final ApplicationReloadManager reloadManager;
    protected volatile Version version;

    public CoreInputStreamInstaller(InputStream inputStream2, long size, Path tempInstallPath, Path installPath, JarFileManifestReader manifestReader, CoreServiceVersionSupplier coreServiceVersionSupplier, ApplicationReloadManager reloadManager, CoreService coreService) {
        super(inputStream2, size, tempInstallPath, installPath, 100);
        this.manifestReader = manifestReader;
        this.coreServiceVersionSupplier = coreServiceVersionSupplier;
        this.reloadManager = reloadManager;
        this.coreService = coreService;
    }

    @Override
    protected void install() {
        this.reloadManager.reload();
    }

    @Override
    protected void validate() {
        this.version = this.getVersionOrThrow(this.downloadPath);
    }

    public CompletableFuture<Version> invokeAndGetVersion() {
        CompletableFuture<Void> future = super.invoke();
        return future.thenApply(v -> this.version);
    }

    private Version getVersionOrThrow(Path path) {
        try {
            Map<String, String> attributes = this.manifestReader.read(path.toFile());
            String specificationTitle = attributes.get("Specification-Title");
            if (specificationTitle != null && !specificationTitle.equals(this.coreService.getSpecificationTitle())) {
                throw new RuntimeException("Update file Specification-Title mismatch: expected " + this.coreService.getSpecificationTitle() + ", got " + specificationTitle);
            }
            String versionValue = attributes.get("Specification-Version");
            if (versionValue == null) {
                throw new RuntimeException("Specification-Version attribute is missing");
            }
            Version version = Version.valueOf(versionValue);
            if (!VersionUtils.areCompatible(version, this.coreServiceVersionSupplier.getCoreVersion())) {
                throw new RuntimeException("Incompatible core version: " + String.valueOf(version));
            }
            return version;
        }
        catch (Exception e) {
            throw new ServiceFileIsInvalidException(e, this.coreService.getId());
        }
    }
}

