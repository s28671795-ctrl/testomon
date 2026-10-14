/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.server;

import com.google.inject.Inject;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.config.models.VezhaConfig;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServerInitActionsExecutor {
    private static final Logger log = LoggerFactory.getLogger(HttpServerInitActionsExecutor.class);
    private final HttpClient httpClient;
    private final URI healthCheckUri;
    @GuardedBy(value="this")
    private boolean isHttpServerInitialized = false;
    @GuardedBy(value="this")
    private final List<Runnable> waitingActions = new ArrayList<Runnable>();

    @Inject
    public HttpServerInitActionsExecutor(VezhaConfigManager configManager) {
        this.httpClient = HttpClient.newHttpClient();
        int httpPort = ((VezhaConfig)configManager.getConfig()).getConnectionConfig().getHttpPort();
        this.healthCheckUri = URI.create(String.format("http://127.0.0.1:%d/api/v2/plugins/extensions", httpPort));
    }

    public synchronized void onHttpServerInit(Runnable runnable2) {
        if (this.isHttpServerInitialized) {
            runnable2.run();
            return;
        }
        this.waitingActions.add(runnable2);
    }

    public void init() {
        Thread thread2 = new Thread(() -> {
            try {
                this.waitUntilHttpServerInitialized();
                log.info("HTTP server initialized!");
            }
            catch (Exception e) {
                log.error("Failed to wait until HTTP server initialized", e);
            }
            HttpServerInitActionsExecutor httpServerInitActionsExecutor = this;
            synchronized (httpServerInitActionsExecutor) {
                this.isHttpServerInitialized = true;
                this.waitingActions.forEach(Runnable::run);
            }
        });
        thread2.start();
    }

    private void waitUntilHttpServerInitialized() throws InterruptedException {
        while (!this.isHttpServerInitialized()) {
            Thread.sleep(100L);
        }
        return;
    }

    private boolean isHttpServerInitialized() {
        HttpResponse<String> response;
        HttpRequest request = HttpRequest.newBuilder().uri(this.healthCheckUri).GET().build();
        try {
            response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (ConnectException e) {
            return false;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to send HTTP request", e);
        }
        if (response.statusCode() == 200) {
            return true;
        }
        if (response.statusCode() == 302) {
            return false;
        }
        log.error("Http server initialization is failed, status code: {}, response: {}", (Object)response.statusCode(), (Object)response.body());
        throw new RuntimeException("Http server initialization is failed");
    }
}

