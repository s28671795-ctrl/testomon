/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http;

import com.google.common.hash.Hashing;
import com.incoresoft.http.HttpDownloader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHashValidatingDownloader
extends HttpDownloader {
    private static final Logger log = LoggerFactory.getLogger(HttpHashValidatingDownloader.class);
    protected final Path installPath;
    protected final String expectedHash;

    public HttpHashValidatingDownloader(String downloadUrl, Path tempInstallPath, Path installPath, String expectedHash, int chunksNumber) {
        super(downloadUrl, tempInstallPath, chunksNumber);
        this.installPath = Objects.requireNonNull(installPath);
        this.expectedHash = Objects.requireNonNull(expectedHash);
    }

    @Override
    protected void onDownloaded() {
        super.onDownloaded();
        try {
            this.validateHash();
            this.moveFile();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateHash() throws IOException {
        String actualHash = com.google.common.io.Files.asByteSource(this.downloadPath.toFile()).hash(Hashing.sha1()).toString();
        log.info(String.format("Expected checksum: %s. Actual checksum: %s. Checksums matches: %s.", this.expectedHash.toLowerCase(), actualHash.toLowerCase(), this.expectedHash.equalsIgnoreCase(actualHash)));
        if (!this.expectedHash.equalsIgnoreCase(actualHash)) {
            Files.delete(this.downloadPath);
            throw new RuntimeException("Hash of downloaded file was different to expected hash");
        }
    }

    private void moveFile() throws IOException {
        if (Files.exists(this.installPath, new LinkOption[0])) {
            Files.copy(this.installPath, Path.of(String.valueOf(this.installPath) + ".bak", new String[0]), StandardCopyOption.REPLACE_EXISTING);
        }
        Files.move(this.downloadPath, this.installPath, StandardCopyOption.REPLACE_EXISTING);
    }
}

