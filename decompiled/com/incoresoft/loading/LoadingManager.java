/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.loading;

import com.incoresoft.dto.loading.LoadingStageDTO;
import com.incoresoft.loading.AppLoadingListener;
import com.incoresoft.loading.LoadingStage;
import com.incoresoft.loading.LoadingStatus;
import com.incoresoft.middleware.connector.Connector;
import com.incoresoft.middleware.connector.ConnectorStatus;
import com.incoresoft.middleware.connector.OnStatusChangeListener;
import com.incoresoft.middleware.database.DatabaseConnector;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.mq.MessageQueueConnector;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.plugin.commons.http.answers.Ws.WsAnswer;
import com.incoresoft.plugin.commons.http.answers.Ws.WsObjectAnswer;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.websocket.WsContext;
import java.lang.runtime.SwitchBootstraps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class LoadingManager
implements AppLoadingListener,
OnStatusChangeListener {
    private final Set<WsContext> wsSessions = Collections.newSetFromMap(new ConcurrentHashMap());
    protected final Map<LoadingStage, LoadingStatus> stages = new ConcurrentHashMap<LoadingStage, LoadingStatus>();
    private final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private final AtomicReference<LoadingStage> currentLoadingStage = new AtomicReference();

    public void init(Javalin javalin) {
        javalin.routes(() -> {
            ApiBuilder.before("/*", ctx -> {
                if (!this.isInitialized.get() && !ctx.path().startsWith("/loading")) {
                    ctx.redirect("/loading/index.html");
                }
                if (this.isInitialized.get() && ctx.path().startsWith("/loading")) {
                    ctx.redirect("/");
                }
            });
            ApiBuilder.path("loading", () -> ApiBuilder.get("stages", this::getStages));
        });
        javalin.ws("loading", wsHandler -> {
            wsHandler.onConnect(ctx -> {
                if (!this.isInitialized.get()) {
                    this.wsSessions.add(ctx);
                }
            });
            wsHandler.onClose(ctx -> this.wsSessions.removeIf(it -> it.getSessionId().equals(ctx.getSessionId())));
        }, AppRole.ANYONE);
    }

    @Override
    public LoadingStage getCurrentLoadingStage() {
        return this.currentLoadingStage.get();
    }

    @Override
    public void onStageChanged(@NotNull LoadingStage stage) {
        this.currentLoadingStage.set(stage);
        this.stages.put(stage, LoadingStatus.OK);
        this.wsSessions.forEach(session -> session.send(new WsObjectAnswer<LoadingStageDTO>("current_loading_stage", new LoadingStageDTO(stage, LoadingStatus.OK))));
    }

    @Override
    public void onCurrentStageStateChange(@NotNull LoadingStage stage, @NotNull LoadingStatus status, @Nullable String message) {
        this.currentLoadingStage.set(stage);
        this.stages.put(stage, status);
        this.wsSessions.forEach(session -> session.send(new WsObjectAnswer<LoadingStageDTO>("state_changed", new LoadingStageDTO(stage, status, message))));
    }

    @Override
    public void onAppLoaded(Runnable logAppLoaded) {
        this.isInitialized.set(true);
        this.wsSessions.forEach(session -> session.send(new WsAnswer("app_loaded")));
        this.wsSessions.clear();
        logAppLoaded.run();
    }

    public void getStages(Context ctx) {
        List<LoadingStageDTO> loadingStageDTOS = this.getStages().stream().map(it -> new LoadingStageDTO((LoadingStage)((Object)it), this.stages.getOrDefault(it, LoadingStatus.WAITING))).toList();
        ctx.json(loadingStageDTOS);
    }

    public abstract List<LoadingStage> getStages();

    @Override
    public void onStatusChanged(Connector connector, ConnectorStatus connectorStatus) {
        LoadingStage loadingStage;
        Connector connector2 = connector;
        Objects.requireNonNull(connector2);
        Connector connector3 = connector2;
        int n = 0;
        switch (SwitchBootstraps.typeSwitch("typeSwitch", new Object[]{Storage.class, DatabaseConnector.class, MessageQueueConnector.class}, (Object)connector3, n)) {
            case 0: {
                Storage s2 = (Storage)connector3;
                LoadingStage loadingStage2 = LoadingStage.STORAGE;
                break;
            }
            case 1: {
                DatabaseConnector d = (DatabaseConnector)connector3;
                LoadingStage loadingStage2 = LoadingStage.DATABASE;
                break;
            }
            case 2: {
                MessageQueueConnector m4 = (MessageQueueConnector)connector3;
                LoadingStage loadingStage2 = LoadingStage.MESSAGE_QUEUE;
                break;
            }
            default: {
                LoadingStage loadingStage2 = loadingStage = null;
            }
        }
        if (loadingStage == null) {
            return;
        }
        this.onCurrentStageStateChange(loadingStage, LoadingStatus.from(connectorStatus), null);
    }
}

