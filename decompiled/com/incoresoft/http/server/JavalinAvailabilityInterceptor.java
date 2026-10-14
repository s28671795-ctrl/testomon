/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http.server;

import com.google.inject.Inject;
import com.incoresoft.dependencyinjection.exceptions.UnavailabilityReason;
import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import io.javalin.websocket.WsConfig;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.ObjectUtils;

public class JavalinAvailabilityInterceptor {
    private volatile UnavailabilityReason reason;
    private final Set<String> alwaysAvailableEndpoints = ConcurrentHashMap.newKeySet();

    @Inject
    void init(Javalin app) {
        app.before(ctx -> {
            if (this.reason != null && ctx.method() != HandlerType.GET && !this.isPathAvailable(ctx.path())) {
                throw this.reason.getExceptionSupplier().get();
            }
        });
        app.wsBefore(this::interceptWs);
    }

    private void interceptWs(WsConfig config) {
        config.onConnect(ctx -> {
            if (!this.isPathAvailable(ctx.matchedPath())) {
                ctx.closeSession();
            }
        });
        config.onError(ctx -> {
            if (!this.isPathAvailable(ctx.matchedPath())) {
                ctx.closeSession();
            }
        });
        config.onMessage(ctx -> {
            if (!this.isPathAvailable(ctx.matchedPath())) {
                ctx.closeSession();
            }
        });
    }

    private boolean isPathAvailable(String path) {
        if (this.reason == null) {
            return true;
        }
        return this.alwaysAvailableEndpoints.stream().anyMatch(path::startsWith);
    }

    public void addAlwaysAvailableEndpoint(String endpoint) {
        if (ObjectUtils.isEmpty(endpoint)) {
            throw new IllegalArgumentException("Endpoint cannot be empty");
        }
        if (!((String)endpoint).startsWith("/")) {
            endpoint = "/" + (String)endpoint;
        }
        this.alwaysAvailableEndpoints.add((String)endpoint);
    }

    public void setAvailable() {
        this.reason = null;
    }

    public void setUnavailable(UnavailabilityReason unavailabilityReason) {
        this.reason = unavailabilityReason;
    }
}

