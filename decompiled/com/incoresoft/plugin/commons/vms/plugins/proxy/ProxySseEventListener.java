/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins.proxy;

import io.javalin.http.sse.SseClient;
import java.net.URI;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxySseEventListener
extends EventSourceListener {
    private static final Logger log = LoggerFactory.getLogger(ProxySseEventListener.class);
    private final SseClient sseClient;
    private final URI remoteAddress;

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        log.info("Connected to remote SSE server: {}", (Object)this.remoteAddress);
    }

    @Override
    public void onEvent(@NotNull EventSource eventSource, String id, String type, @NotNull String data) {
        this.sseClient.sendEvent(type != null ? type : "data", data);
    }

    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        log.info("Remote SSE connection closed: {}", (Object)this.remoteAddress);
        this.sseClient.close();
    }

    @Override
    public void onFailure(@NotNull EventSource eventSource, Throwable t2, Response response) {
        log.error("Remote connection error {}: {}", (Object)this.remoteAddress, (Object)(t2 != null ? t2.getMessage() : ""));
        this.sseClient.close();
    }

    public ProxySseEventListener(SseClient sseClient, URI remoteAddress) {
        this.sseClient = sseClient;
        this.remoteAddress = remoteAddress;
    }
}

