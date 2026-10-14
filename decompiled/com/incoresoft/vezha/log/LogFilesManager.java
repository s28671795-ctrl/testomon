/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.log;

import com.google.inject.Inject;
import com.incoresoft.files.ZipFileManager;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LogFilesManager {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
    private final ZipFileManager zipFileManager;
    private final Path logFilesDirectory;
    private final Path tempFilesDirectory;

    @Inject
    public LogFilesManager(ZipFileManager zipFileManager) {
        this(zipFileManager, Path.of("log", new String[0]), Path.of("temp", new String[0]));
    }

    public File getArchivedLogFile(Instant instant) {
        String dateValue = FORMATTER.format(instant.atZone(ZoneId.systemDefault()));
        File logFile = this.logFilesDirectory.resolve(String.format("core-%s.0.log", dateValue)).toFile();
        File targetFile = this.tempFilesDirectory.resolve(String.format("core-%s.0.zip", dateValue)).toFile();
        if (!logFile.exists()) {
            return this.getArchivedEmptyFile(targetFile);
        }
        this.zipFileManager.zipRegularFile(logFile, targetFile);
        return targetFile;
    }

    private File getArchivedEmptyFile(File targetFile) {
        File emptyFile = Path.of("temp", new String[0]).resolve("empty-log-file.log").toFile();
        try {
            Files.createFile(emptyFile.toPath(), new FileAttribute[0]);
            this.zipFileManager.zipRegularFile(emptyFile, targetFile);
            File file = targetFile;
            return file;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create empty log file", e);
        }
        finally {
            emptyFile.delete();
        }
    }

    public LogFilesManager(ZipFileManager zipFileManager, Path logFilesDirectory, Path tempFilesDirectory) {
        this.zipFileManager = zipFileManager;
        this.logFilesDirectory = logFilesDirectory;
        this.tempFilesDirectory = tempFilesDirectory;
    }
}

