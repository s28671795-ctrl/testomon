/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.plugins.PluginInstalledAuditMessage;
import com.incoresoft.dto.plugins.MarketplacePluginInfoDTO;
import com.incoresoft.dto.plugins.MarketplacePluginInfoPaginationAnswerDTO;
import com.incoresoft.dto.plugins.PluginVersionDTO;
import com.incoresoft.http.InstallationListener;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.plugins.BasePluginsFacade;
import com.incoresoft.plugins.models.PluginType;
import com.incoresoft.vezha.plugins.analytics.AnalyticsPluginsManagerFacade;
import com.incoresoft.vezha.plugins.event_manager.EventManagerPluginsManagerFacade;
import com.incoresoft.vezha.utils.PluginTypeParser;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class MarketplaceController
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(MarketplaceController.class);
    private static final String API_PATH = "/api/v1/marketplace";
    private final UsersManager usersManager;
    private final AnalyticsPluginsManagerFacade analyticsPluginsManagerFacade;
    private final EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade;
    private final Auditor auditor;
    private final BeanValidator beanValidator;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getPlugins, AppRole.LOGGED_IN, CorePermissions.ManageSystemUpdatesAndPlugins);
            ApiBuilder.path("{plugin_type}/{id}", () -> {
                ApiBuilder.get(this::getPluginInfoById, AppRole.LOGGED_IN, CorePermissions.ManageSystemUpdatesAndPlugins);
                ApiBuilder.post(this::installPlugin, AppRole.LOGGED_IN, CorePermissions.ManageSystemUpdatesAndPlugins);
            });
        }));
    }

    @GET
    @Path(value="/api/v1/marketplace")
    @Operation(tags={"Marketplace"}, summary="Get marketplace plugins list", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=MarketplacePluginInfoPaginationAnswerDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getPlugins(@Parameter(hidden=true) Context ctx) {
        ArrayList<MarketplacePluginInfoDTO> dtos = new ArrayList<MarketplacePluginInfoDTO>();
        dtos.addAll(this.analyticsPluginsManagerFacade.getMarketplacePluginsInfos().stream().map(MarketplacePluginInfoDTO::new).sorted(Comparator.comparing(MarketplacePluginInfoDTO::getName)).toList());
        dtos.addAll(this.eventManagerPluginsManagerFacade.getMarketplacePluginsInfos().stream().map(MarketplacePluginInfoDTO::new).sorted(Comparator.comparing(MarketplacePluginInfoDTO::getName)).toList());
        ctx.json(new MarketplacePluginInfoPaginationAnswerDTO((List<MarketplacePluginInfoDTO>)dtos, (long)dtos.size(), dtos.size()));
    }

    @GET
    @Path(value="/api/v1/marketplace/{plugin_type}/{id}")
    @Operation(tags={"Marketplace"}, summary="Get marketplace plugin info by plugin id", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"})), @Parameter(in=ParameterIn.PATH, name="id", description="Plugin id", example="middleware-alpr-plugin", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=MarketplacePluginInfoPaginationAnswerDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getPluginInfoById(@Parameter(hidden=true) Context ctx) {
        BasePluginsFacade facade = this.getFacade(ctx);
        List<MarketplacePluginInfoDTO> dtos = facade.getMarketplacePluginInfos(ctx.pathParam("id")).stream().map(MarketplacePluginInfoDTO::new).toList();
        ctx.json(new MarketplacePluginInfoPaginationAnswerDTO(dtos, (long)dtos.size(), dtos.size()));
    }

    @POST
    @Path(value="/api/v1/marketplace/{plugin_type}/{id}")
    @Operation(tags={"Marketplace"}, summary="Install plugin", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="plugin_type", example="analytics", description="Plugin type", required=true, schema=@Schema(allowableValues={"analytics", "event-manager"})), @Parameter(in=ParameterIn.PATH, name="id", example="middleware-alpr-plugin", description="Plugin ID", required=true)}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=PluginVersionDTO.class))}), responses={@ApiResponse(responseCode="202", description="Accepted"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found Sounds Item")})
    public void installPlugin(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        BasePluginsFacade facade = this.getFacade(ctx);
        String pluginId = ctx.pathParamAsClass("id", String.class).getOrThrow(i -> new ErrorException(BaseError.RequiredParams, "id"));
        PluginVersionDTO pluginVersionDTO = ctx.bodyAsClass(PluginVersionDTO.class);
        this.beanValidator.validateObjectOrThrow(pluginVersionDTO, new Class[0]);
        facade.installPlugin(pluginId, pluginVersionDTO.version(), new InstallationListener[0]);
        ctx.status(202);
        this.auditor.audit(ctx, user, CoreAuditEventAction.PLUGIN_INSTALLED, "middleware-core", new PluginInstalledAuditMessage(pluginId));
    }

    BasePluginsFacade getFacade(Context ctx) {
        String pluginTypeValue = ctx.pathParam("plugin_type");
        PluginType pluginType = PluginTypeParser.getByValue(pluginTypeValue).orElseThrow(NotFoundResponse::new);
        return switch (pluginType) {
            case PluginType.ANALYTICS_PLUGIN -> this.analyticsPluginsManagerFacade;
            case PluginType.EVENT_MANAGER_PLUGIN -> this.eventManagerPluginsManagerFacade;
            default -> throw new NotFoundResponse();
        };
    }

    @Inject
    public MarketplaceController(UsersManager usersManager, AnalyticsPluginsManagerFacade analyticsPluginsManagerFacade, EventManagerPluginsManagerFacade eventManagerPluginsManagerFacade, Auditor auditor, BeanValidator beanValidator) {
        this.usersManager = usersManager;
        this.analyticsPluginsManagerFacade = analyticsPluginsManagerFacade;
        this.eventManagerPluginsManagerFacade = eventManagerPluginsManagerFacade;
        this.auditor = auditor;
        this.beanValidator = beanValidator;
    }
}

