/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.systemlogs;

import com.google.inject.Inject;
import com.incoresoft.core.CoreServiceManager;
import com.incoresoft.files.ZipFileManager;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugins.models.InstalledPluginInfo;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsManagerFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsManagerFacade;
import com.incoresoft.vezha.systemlogs.VersionsListEntry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

public class TextLogsExporter {
    private static final Path LOGS_DIR = Path.of("log", new String[0]);
    private static final Path TEMP_DIR = Path.of("temp", "logs_export");
    private final CoreServiceManager coreServiceManager;
    private final EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade;
    private final AnalyticsPluginsManagerFacade analyticsPluginsFacade;
    private final ZipFileManager zipFileManager;

    @Inject
    public TextLogsExporter(@NotNull CoreServiceManager coreServiceManager, @NotNull EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade, @NotNull AnalyticsPluginsManagerFacade analyticsPluginsFacade, @NotNull ZipFileManager zipFileManager) {
        this.coreServiceManager = Objects.requireNonNull(coreServiceManager);
        this.eventManagerPluginsManagerFacade = Objects.requireNonNull(eventManagerPluginsManagerFacade);
        this.analyticsPluginsFacade = Objects.requireNonNull(analyticsPluginsFacade);
        this.zipFileManager = Objects.requireNonNull(zipFileManager);
    }

    public byte[] exportLogs() {
        Path currentExportDir = TEMP_DIR.resolve(UUID.randomUUID().toString());
        try {
            Files.createDirectories(currentExportDir, new FileAttribute[0]);
            this.writeVersionsFile(currentExportDir);
            FileUtils.copyDirectory(LOGS_DIR.toFile(), currentExportDir.toFile());
            Path logsZip = currentExportDir.resolve("logs.zip");
            Files.deleteIfExists(logsZip);
            this.zipFileManager.zipAllFilesOfDirectory(currentExportDir.toFile(), logsZip.toFile());
            byte[] byArray = FileUtils.readFileToByteArray(logsZip.toFile());
            return byArray;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                FileUtils.deleteDirectory(TEMP_DIR.toFile());
            }
            catch (IOException iOException) {}
        }
    }

    private void writeVersionsFile(Path currentExportDir) throws IOException {
        ArrayList<VersionsListEntry> modules = new ArrayList<VersionsListEntry>();
        modules.add(new VersionsListEntry(this.coreServiceManager.getServiceUpdateState()));
        for (InstalledPluginInfo plugin : this.eventManagerPluginsManagerFacade.getInstalledPluginsInfos()) {
            modules.add(new VersionsListEntry(plugin));
        }
        for (InstalledPluginInfo plugin : this.analyticsPluginsFacade.getInstalledPluginsInfos()) {
            modules.add(new VersionsListEntry(plugin));
        }
        Files.writeString(currentExportDir.resolve("versions.txt"), (CharSequence)Serialization.writeValueAsPrettyString(modules), new OpenOption[0]);
    }
}

