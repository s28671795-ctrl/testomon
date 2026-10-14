/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.inject.Inject;
import com.incoresoft.Utils.ApiUtils;
import com.incoresoft.files.FileUtilsWrapper;
import com.incoresoft.files.ZipFileManager;
import com.incoresoft.plugin.commons.utils.JavalinFile;
import com.incoresoft.vezha.backup.file.BackupFileConfiguration;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Set;
import org.apache.commons.io.FileUtils;

public class BackupFilesManager {
    private static final Path CURRENT_DIRECTORY = Path.of(".", new String[0]);
    private static final Path BACKUPS_DIRECTORY = CURRENT_DIRECTORY.resolve("backups");
    private static final Path TEMPORARY_BACKUP_DIRECTORY = CURRENT_DIRECTORY.resolve("temp_backup");
    private static final Path TEMPORARY_BACKUP_ZIP_DIRECTORY = CURRENT_DIRECTORY.resolve("temp_backup_zip");
    private static final String BACKUP_FILE_CONFIGURATION_NAME = "backup_file_configuration.txt";
    private static final String BACKUP_FILE_HASH_NAME = "backup_file_hash.txt";
    private static final String ZIP_EXTENSION = ".zip";
    private static final Set<String> REQUIRED_FILE_NAMES = Set.of("images", "sounds");
    private final ZipFileManager zipFileManager;

    public final Path getTemporaryBackupDirectory() {
        return TEMPORARY_BACKUP_DIRECTORY;
    }

    public static String withZipExtension(String fileName) {
        return fileName + ZIP_EXTENSION;
    }

    public static String withoutZipExtension(String fileName) {
        if (fileName.endsWith(ZIP_EXTENSION)) {
            return fileName.substring(0, fileName.length() - ZIP_EXTENSION.length());
        }
        return fileName;
    }

    public void deleteBackupFile(String fileName) {
        File backupZipFile = this.getBackupZipFile(fileName);
        backupZipFile.delete();
    }

    public File getBackupZipFile(String fileName) {
        return BACKUPS_DIRECTORY.resolve(fileName).toFile();
    }

    public File saveBackupZipFile(JavalinFile javalinFile) {
        return javalinFile.save(TEMPORARY_BACKUP_ZIP_DIRECTORY);
    }

    public final void deleteTemporaryFiles() {
        FileUtilsWrapper.deleteDirectory(TEMPORARY_BACKUP_DIRECTORY.toFile());
        FileUtilsWrapper.deleteDirectory(TEMPORARY_BACKUP_ZIP_DIRECTORY.toFile());
    }

    public void unzipBackupFile(File backupZipFile) {
        FileUtilsWrapper.deleteDirectory(TEMPORARY_BACKUP_DIRECTORY.toFile());
        this.zipFileManager.unzip(backupZipFile, TEMPORARY_BACKUP_DIRECTORY.toFile());
    }

    public File zipBackupFile(String backupName) {
        String fileName = BackupFilesManager.withZipExtension(backupName);
        File backupZipFile = this.getBackupZipFile(fileName);
        this.zipFileManager.zipAllFilesOfDirectory(TEMPORARY_BACKUP_DIRECTORY.toFile(), backupZipFile);
        return backupZipFile;
    }

    public void importFilesFromBackup() {
        REQUIRED_FILE_NAMES.stream().map(filePath -> CURRENT_DIRECTORY.resolve((String)filePath).toFile()).forEach(file -> {
            if (file.isDirectory()) {
                FileUtilsWrapper.deleteDirectory(file);
            } else {
                file.delete();
            }
        });
        this.copyRequiredFiles(TEMPORARY_BACKUP_DIRECTORY, CURRENT_DIRECTORY);
    }

    public void copyFilesToBackup() throws IOException {
        FileUtilsWrapper.deleteDirectory(TEMPORARY_BACKUP_DIRECTORY.toFile());
        Files.createDirectories(TEMPORARY_BACKUP_DIRECTORY, new FileAttribute[0]);
        this.copyRequiredFiles(CURRENT_DIRECTORY, TEMPORARY_BACKUP_DIRECTORY);
    }

    public void deleteTemporaryBackupDirectory() {
        FileUtilsWrapper.deleteDirectory(TEMPORARY_BACKUP_DIRECTORY.toFile());
    }

    public void writeBackupFileConfiguration(BackupFileConfiguration configuration) throws IOException {
        Path target = TEMPORARY_BACKUP_DIRECTORY.resolve(BACKUP_FILE_CONFIGURATION_NAME);
        Files.write(target, JSON.toJSONBytes((Object)configuration, new SerializerFeature[0]), new OpenOption[0]);
    }

    public void writeBackupFileHash(String hash) throws IOException {
        Path target = TEMPORARY_BACKUP_DIRECTORY.resolve(BACKUP_FILE_HASH_NAME);
        Files.write(target, hash.getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
    }

    public BackupFileConfiguration readBackupFileConfiguration() throws IOException {
        Path target = TEMPORARY_BACKUP_DIRECTORY.resolve(BACKUP_FILE_CONFIGURATION_NAME);
        String content = FileUtils.readFileToString(target.toFile(), "UTF-8");
        return ApiUtils.getJSONAsClass(JSON.parseObject(content), BackupFileConfiguration.class);
    }

    public String readBackupFileHash() throws IOException {
        Path target = TEMPORARY_BACKUP_DIRECTORY.resolve(BACKUP_FILE_HASH_NAME);
        return Files.readString(target);
    }

    private void copyRequiredFiles(Path source2, Path target) {
        REQUIRED_FILE_NAMES.stream().map(source2::resolve).forEach(path -> FileUtilsWrapper.copyToDirectory(path.toFile(), target.toFile()));
    }

    @Inject
    public BackupFilesManager(ZipFileManager zipFileManager) {
        this.zipFileManager = zipFileManager;
    }
}

