/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.file;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.incoresoft.vezha.backup.file.BackupFileConfiguration;
import com.incoresoft.vezha.backup.file.BackupFilePluginInfo;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BackupFilesHashManager {
    private static final String HASH_SEPARATOR = "-";

    public String generateHash(BackupFileConfiguration configuration, File dbFile) {
        String analyticsPluginsHashes = this.getPluginsHash(configuration.getAnalyticsPlugins());
        String eventManagerPluginsHashes = this.getPluginsHash(configuration.getEventManagerPlugins());
        String coreFileHash = this.hash(configuration.getCoreVersion().toString());
        String databaseHash = this.hash(dbFile);
        String isIntegratedCode = configuration.isVmsIntegrationEnabled() ? "t" : "f";
        return analyticsPluginsHashes + HASH_SEPARATOR + eventManagerPluginsHashes + HASH_SEPARATOR + coreFileHash + HASH_SEPARATOR + databaseHash + HASH_SEPARATOR + isIntegratedCode;
    }

    private String getPluginsHash(List<BackupFilePluginInfo> pluginInfos) {
        return pluginInfos.stream().sorted(Comparator.comparing(BackupFilePluginInfo::getId)).map(plugin -> plugin.getId() + String.valueOf(plugin.getVersion())).map(this::hash).collect(Collectors.joining());
    }

    private String hash(File file) {
        return this.hash(Files.asByteSource(file));
    }

    private String hash(String string) {
        return this.hash(ByteSource.wrap(string.getBytes(StandardCharsets.UTF_8)));
    }

    private String hash(ByteSource source2) {
        try {
            return source2.hash(Hashing.sha256()).toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

