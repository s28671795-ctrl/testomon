/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.notifications.Notification;
import com.incoresoft.middleware.notifications.NotificationsManager;
import com.incoresoft.middleware.notifications.NotificationsPanel;
import com.incoresoft.middleware.notifications.exceptions.CannotBeAcceptedByThisUserException;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.vezha.dto.notificationspanel.NotificationsResultDTO;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiNotificationsControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiNotificationsControllerV2.class);
    private static final String API_PATH = "/api/v2/notifications";
    private final UsersManager usersManager;
    private final NotificationsPanel notificationsPanel;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getLiveNotifications, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.patch("accept", this::accept, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.patch("accept_all", this::acceptAll, AppRole.LOGGED_IN, AppRole.API_CALL);
        }));
    }

    @GET
    @Path(value="/api/v2/notifications")
    @Operation(tags={"Notifications"}, summary="Get list of all live notifications", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=String.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getLiveNotifications(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        List<NotificationsResultDTO> result = this.notificationsPanel.getNotificationsManagers().stream().sorted(Comparator.comparing(NotificationsManager::getPluginName)).map(it -> new NotificationsResultDTO(it.getPluginName(), it.getNotifications(user))).toList();
        ctx.json(result);
    }

    @PATCH
    @Path(value="/api/v2/notifications/accept")
    @Operation(tags={"Notifications"}, summary="Accept notification", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="id", description="Notification ID", required=true), @Parameter(in=ParameterIn.QUERY, name="plugin_id", description="Plugin id", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void accept(@Parameter(hidden=true) Context ctx) {
        Integer id = ctx.queryParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        String pluginId = ctx.queryParamAsClass("plugin_id", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "plugin_id"));
        User user = this.usersManager.getUser(ctx);
        Optional<NotificationsManager> notificationsManager = this.notificationsPanel.getNotificationsManagers().stream().filter(it -> it.getPluginName().equals(pluginId)).findFirst();
        if (notificationsManager.isPresent()) {
            try {
                Notification notification = notificationsManager.get().get(id.intValue());
                if (notification == null) {
                    throw new NotFoundResponse();
                }
                if (!notificationsManager.get().userAllowedToSeeNotification(user, notification)) {
                    throw new ForbiddenResponse();
                }
                notificationsManager.get().accept(user, notification);
                ctx.status(204);
            }
            catch (CannotBeAcceptedByThisUserException e2) {
                throw new ForbiddenResponse();
            }
        }
    }

    @PATCH
    @Path(value="/api/v2/notifications/accept_all")
    @Operation(tags={"Notifications"}, summary="Accept all notifications", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="plugin_id", description="Plugin id", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void acceptAll(@Parameter(hidden=true) Context ctx) {
        String pluginId = ctx.queryParamAsClass("plugin_id", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "plugin_id"));
        User user = this.usersManager.getUser(ctx);
        Optional<NotificationsManager> notificationsManager = this.notificationsPanel.getNotificationsManagers().stream().filter(it -> it.getPluginName().equals(pluginId)).findFirst();
        if (notificationsManager.isPresent()) {
            try {
                notificationsManager.get().acceptAll(user);
                ctx.status(204);
            }
            catch (CannotBeAcceptedByThisUserException e2) {
                throw new ForbiddenResponse();
            }
        }
    }

    @Inject
    public ApiNotificationsControllerV2(UsersManager usersManager, NotificationsPanel notificationsPanel) {
        this.usersManager = usersManager;
        this.notificationsPanel = notificationsPanel;
    }
}

