/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http;

import com.incoresoft.http.Downloader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

public class HttpDownloader
extends Downloader {
    protected final String downloadUrl;
    protected final Consumer<HttpGet> authAction;
    protected final int chunksNumber;

    public HttpDownloader(String downloadUrl, Path downloadPath, int chunksNumber) {
        this(downloadUrl, downloadPath, httpGet -> {}, chunksNumber);
    }

    public HttpDownloader(String downloadUrl, Path downloadPath, Consumer<HttpGet> authAction, int chunksNumber) {
        super(downloadPath);
        this.downloadUrl = downloadUrl;
        this.authAction = authAction;
        this.chunksNumber = chunksNumber;
    }

    @Override
    public CompletableFuture<Void> invoke() {
        return CompletableFuture.runAsync(() -> {
            try {
                this.onStart();
                HttpGet request = new HttpGet(this.downloadUrl);
                this.authAction.accept(request);
                try (CloseableHttpClient client = HttpClients.createDefault();
                     CloseableHttpResponse response = client.execute(request);
                     InputStream inputStream2 = response.getEntity().getContent();
                     ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream2);
                     FileOutputStream fileOutputStream = new FileOutputStream(this.downloadPath.toString());
                     FileChannel outputChannel = fileOutputStream.getChannel();){
                    long contentLength = response.getEntity().getContentLength();
                    long chunkSize = contentLength / (long)this.chunksNumber;
                    long remainder = contentLength % (long)this.chunksNumber;
                    for (int i = 0; i < this.chunksNumber; ++i) {
                        long position = chunkSize * (long)i;
                        outputChannel.transferFrom(readableByteChannel, position, chunkSize);
                        double downloadPercent = (double)(position + chunkSize) / (double)contentLength * 100.0;
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

