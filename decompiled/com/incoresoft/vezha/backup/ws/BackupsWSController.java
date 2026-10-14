/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.backup.ws;

import com.google.inject.Inject;
import com.incoresoft.http.server.JavalinAvailabilityInterceptor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.vezha.backup.ws.BackupsCreationWSHandler;
import com.incoresoft.vezha.backup.ws.BackupsRestoreWSHandler;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info=@Info(title="VMS", description="VMS HTTP API", version="v1"))
public class BackupsWSController {
    private static final String API_PATH = "/api/v1/ws/backup";
    private final BackupsCreationWSHandler backupsCreationHandler;
    private final BackupsRestoreWSHandler backupsRestoreHandler;
    private final JavalinAvailabilityInterceptor javalinAvailabilityInterceptor;

    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.ws("restore", this.backupsRestoreHandler::init, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.ws("creation", this.backupsCreationHandler::init, AppRole.LOGGED_IN, AppRole.API_CALL);
        }));
        this.javalinAvailabilityInterceptor.addAlwaysAvailableEndpoint(API_PATH);
    }

    @Inject
    public BackupsWSController(BackupsCreationWSHandler backupsCreationHandler, BackupsRestoreWSHandler backupsRestoreHandler, JavalinAvailabilityInterceptor javalinAvailabilityInterceptor) {
        this.backupsCreationHandler = backupsCreationHandler;
        this.backupsRestoreHandler = backupsRestoreHandler;
        this.javalinAvailabilityInterceptor = javalinAvailabilityInterceptor;
    }
}

