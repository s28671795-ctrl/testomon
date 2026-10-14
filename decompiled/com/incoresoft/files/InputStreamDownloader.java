/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.files;

import com.incoresoft.http.Downloader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class InputStreamDownloader
extends Downloader {
    protected final InputStream inputStream;
    protected final long size;
    protected final int chunksNumber;

    protected InputStreamDownloader(InputStream inputStream2, long size, Path downloadPath, int chunksNumber) {
        super(downloadPath);
        this.inputStream = inputStream2;
        this.size = size;
        this.chunksNumber = chunksNumber;
    }

    @Override
    public CompletableFuture<Void> invoke() {
        return CompletableFuture.runAsync(() -> {
            try {
                this.onStart();
                try (ReadableByteChannel readableByteChannel = Channels.newChannel(this.inputStream);
                     FileOutputStream fileOutputStream = new FileOutputStream(this.downloadPath.toString());
                     FileChannel outputChannel = fileOutputStream.getChannel();){
                    long chunkSize = this.size / (long)this.chunksNumber;
                    long remainder = this.size % (long)this.chunksNumber;
                    for (int i = 0; i < this.chunksNumber; ++i) {
                        long position = chunkSize * (long)i;
                        outputChannel.transferFrom(readableByteChannel, position, chunkSize);
                        double downloadPercent = (double)(position + chunkSize) / (double)this.size * 100.0;
                        double roundedPercent = (double)Math.round(downloadPercent * 100.0) / 100.0;
                        this.onPartDownloaded(roundedPercent);
                    }
                    if (remainder > 0L) {
                        long position = (long)this.chunksNumber * chunkSize;
                        outputChannel.transferFrom(readableByteChannel, position, remainder);
                    }
                }
                this.onDownloaded();
            }
            catch (Exception e) {
                this.onError(e);
                throw new CompletionException(e);
            }
        });
    }
}

