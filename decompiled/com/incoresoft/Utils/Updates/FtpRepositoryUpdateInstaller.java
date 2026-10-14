/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils.Updates;

import com.google.common.hash.Hashing;
import com.incoresoft.Utils.Updates.UpdateInstaller;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;

public class FtpRepositoryUpdateInstaller
extends UpdateInstaller {
    private final String host;
    private final String user;
    private final String password;
    private final String updateFilePath;
    private final String checksumFilePath;
    private final Path tempInstallPath;
    private final Path installPath;

    public FtpRepositoryUpdateInstaller(String host2, String user, String password, String updateFilePath, String checksumFilePath, Path tempPath, Path installPath) {
        this.host = Objects.requireNonNull(host2, "ftp host");
        this.user = Objects.requireNonNull(user, "user");
        this.password = Objects.requireNonNull(password, "password");
        this.updateFilePath = Objects.requireNonNull(updateFilePath, "server file path");
        this.checksumFilePath = Objects.requireNonNull(checksumFilePath, "hash sum server file path");
        this.installPath = Objects.requireNonNull(installPath, "installPath");
        this.tempInstallPath = Objects.requireNonNull(tempPath, "tempPath");
    }

    @Override
    public CompletableFuture<Void> invoke() {
        return CompletableFuture.runAsync(() -> {
            try {
                String expectedFileHash;
                this.onStart();
                FTPClient client = new FTPClient();
                client.connect(this.host);
                client.enterLocalPassiveMode();
                client.login(this.user, this.password);
                int parts = 10;
                long contentLength = client.mlistFile(this.updateFilePath).getSize();
                if (contentLength == -1L) {
                    throw new RuntimeException(String.format("File \"%s\" not found on \"%s\"", this.updateFilePath, this.host));
                }
                long chunkSize = contentLength / 10L;
                long remainder = contentLength % 10L;
                try (InputStream hashInputStream = client.retrieveFileStream(this.checksumFilePath);){
                    expectedFileHash = IOUtils.toString(hashInputStream);
                }
                client.completePendingCommand();
                client.setFileType(2);
                try (InputStream inputStream2 = client.retrieveFileStream(this.updateFilePath);
                     ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream2);
                     FileOutputStream fileOutputStream = new FileOutputStream(this.tempInstallPath.toString());
                     FileChannel outputChannel = fileOutputStream.getChannel();){
                    for (int i = 0; i < 10; ++i) {
                        long position = chunkSize * (long)i;
                        outputChannel.transferFrom(readableByteChannel, position, chunkSize);
                        double downloadPercent = (double)(position + chunkSize) / (double)contentLength * 100.0;
                        this.onPartDownloaded(downloadPercent);
                    }
                    if (remainder > 0L) {
                        long position = 10L * chunkSize;
                        outputChannel.transferFrom(readableByteChannel, position, remainder);
                    }
                }
                client.completePendingCommand();
                client.disconnect();
                this.onDownloaded();
                String downloadedFileHash = com.google.common.io.Files.asByteSource(this.tempInstallPath.toFile()).hash(Hashing.md5()).toString();
                this.onChecksum(expectedFileHash, downloadedFileHash);
                if (!expectedFileHash.equalsIgnoreCase(downloadedFileHash)) {
                    try {
                        Files.delete(this.tempInstallPath);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    throw new RuntimeException("Hash of downloaded file was different to expected hash");
                }
                Files.move(this.tempInstallPath, this.installPath, StandardCopyOption.REPLACE_EXISTING);
                this.onInstalled();
            }
            catch (Exception e) {
                this.onError(e);
                throw new CompletionException(e);
            }
        });
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String host;
        private String user;
        private String password;
        private String updateFilePath;
        private String checksumFilePath;
        private Path tempInstallPath;
        private Path installPath;

        Builder() {
        }

        public Builder host(String host2) {
            this.host = host2;
            return this;
        }

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder updateFilePath(String updateFilePath) {
            this.updateFilePath = updateFilePath;
            return this;
        }

        public Builder checksumFilePath(String checksumFilePath) {
            this.checksumFilePath = checksumFilePath;
            return this;
        }

        public Builder tempInstallPath(Path tempInstallPath) {
            this.tempInstallPath = tempInstallPath;
            return this;
        }

        public Builder installPath(Path installPath) {
            this.installPath = installPath;
            return this;
        }

        public FtpRepositoryUpdateInstaller build() {
            return new FtpRepositoryUpdateInstaller(this.host, this.user, this.password, this.updateFilePath, this.checksumFilePath, this.tempInstallPath, this.installPath);
        }

        public String toString() {
            return "FtpRepositoryUpdateInstaller.Builder(host=" + this.host + ", user=" + this.user + ", password=" + this.password + ", updateFilePath=" + this.updateFilePath + ", checksumFilePath=" + this.checksumFilePath + ", tempInstallPath=" + this.tempInstallPath + ", installPath=" + this.installPath + ")";
        }
    }
}

