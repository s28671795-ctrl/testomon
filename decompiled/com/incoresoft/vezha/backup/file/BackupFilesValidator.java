/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.file;

import com.github.zafarkhaja.semver.Version;
import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceVersionSupplier;
import com.incoresoft.core.models.CoreService;
import com.incoresoft.version.VersionUtils;
import com.incoresoft.vezha.backup.exceptions.BackupFileCoreInvalidException;
import com.incoresoft.vezha.backup.exceptions.BackupFileCorruptedException;
import com.incoresoft.vezha.backup.exceptions.BackupFileIntegrationInvalidException;
import com.incoresoft.vezha.backup.exceptions.BackupFileNotCompatibleException;
import com.incoresoft.vezha.backup.file.BackupFileConfiguration;
import com.incoresoft.vezha.backup.file.BackupFilesHashManager;
import com.incoresoft.vezha.config.VezhaConfigManager;
import java.io.File;

public class BackupFilesValidator {
    private final CoreServiceVersionSupplier coreServiceVersionSupplier;
    private final BackupFilesHashManager hashManager;
    private final VezhaConfigManager vezhaConfigManager;
    private final CoreService coreService;

    public void validate(BackupFileConfiguration configuration, String hash, File dbFile) {
        Version backupVersion;
        Version currentVersion = this.coreServiceVersionSupplier.getCoreVersion();
        boolean areCompatible = VersionUtils.areCompatible(currentVersion, backupVersion = configuration.getCoreVersion());
        if (!areCompatible) {
            throw new BackupFileNotCompatibleException(currentVersion.toString(), backupVersion.toString());
        }
        if (this.vezhaConfigManager.isVmsIntegrationEnabled() != configuration.isVmsIntegrationEnabled()) {
            throw new BackupFileIntegrationInvalidException();
        }
        if (!this.coreService.getId().equals(configuration.getCoreServiceId())) {
            throw new BackupFileCoreInvalidException();
        }
        String generatedHash = this.hashManager.generateHash(configuration, dbFile);
        if (!hash.equals(generatedHash)) {
            throw new BackupFileCorruptedException();
        }
    }

    @Inject
    public BackupFilesValidator(CoreServiceVersionSupplier coreServiceVersionSupplier, BackupFilesHashManager hashManager, VezhaConfigManager vezhaConfigManager, CoreService coreService) {
        this.coreServiceVersionSupplier = coreServiceVersionSupplier;
        this.hashManager = hashManager;
        this.vezhaConfigManager = vezhaConfigManager;
        this.coreService = coreService;
    }
}

