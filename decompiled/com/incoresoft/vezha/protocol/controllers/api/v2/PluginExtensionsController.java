/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.plugin.EventManagerExtension;
import com.incoresoft.middleware.plugin.InitializableExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.dto.plugins.extensions.VezhaExtensionDTO;
import com.incoresoft.vezha.dto.plugins.extensions.VezhaExtensionDevDTO;
import com.incoresoft.vezha.dto.plugins.extensions.VezhaExtensionInterface;
import com.incoresoft.vezha.dto.plugins.menu.NavBarDTO;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import com.incoresoft.vezha.protocol.controllers.plugin.PluginMenuGenerator;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.InternalServerErrorResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class PluginExtensionsController
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(PluginExtensionsController.class);
    private static final String API_PATH = "/api/v2/plugins";
    private final UsersManagerImpl usersManager;
    private final PluginMenuGenerator pluginMenuGenerator;
    private final ExtensionsManager extensionsManager;
    private final VezhaConfigManager configManager;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get("menu", this::getMenu, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.get("extensions", this::getExtensions, AppRole.ANYONE);
        }));
    }

    @GET
    @Path(value="/api/v2/plugins/menu")
    @Operation(tags={"Plugins"}, summary="Get current user menu", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=NavBarDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized")})
    public void getMenu(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        Optional.of(user).map(this.pluginMenuGenerator::getMenuGroups).map(NavBarDTO::new).ifPresentOrElse(ctx::json, InternalServerErrorResponse::new);
    }

    @GET
    @Path(value="/api/v2/plugins/extensions")
    @Operation(tags={"Plugins"}, summary="Get event manager extensions", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=NavBarDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getExtensions(@Parameter(hidden=true) Context ctx) {
        LinkedList<VezhaExtensionInterface> components = new LinkedList<VezhaExtensionInterface>();
        components.add(this.getStyleGuidComponents());
        components.add(this.getCoreComponents());
        components.addAll(this.extensionsManager.getAll(VezhaExtension.class).stream().filter(it -> !it.getFrontComponents().isEmpty()).map(it -> {
            if (this.configManager.isDebugMode() && !it.getDevParams().isEmpty()) {
                return new VezhaExtensionDevDTO((VezhaExtension)it, this.extensionsManager.getPluginIdByExtension((InitializableExtension<?>)it));
            }
            return new VezhaExtensionDTO((VezhaExtension)it, this.extensionsManager.getPluginIdByExtension((InitializableExtension<?>)it));
        }).toList());
        components.addAll(this.extensionsManager.getAll(EventManagerExtension.class).stream().filter(it -> !it.getFrontComponents().isEmpty()).map(it -> {
            if (this.configManager.isDebugMode() && !it.getDevParams().isEmpty()) {
                return new VezhaExtensionDevDTO((EventManagerExtension)it, this.extensionsManager.getPluginIdByExtension((InitializableExtension<?>)it));
            }
            return new VezhaExtensionDTO((EventManagerExtension)it, this.extensionsManager.getPluginIdByExtension((InitializableExtension<?>)it));
        }).toList());
        ctx.json(components);
    }

    private VezhaExtensionInterface getCoreComponents() {
        return this.readComponentsFromFolder("front/core/component.json", "core", "http://localhost:8001/resources/core/app.js");
    }

    private VezhaExtensionInterface getStyleGuidComponents() {
        return this.readComponentsFromFolder("front/styleguide/component.json", "styleguide", "http://localhost:8002/resources/styleguide/app.js");
    }

    @NotNull
    private VezhaExtensionInterface readComponentsFromFolder(String componentsDescFilePath, String moduleName, String devPath) {
        List components = Optional.ofNullable(this.getClass().getClassLoader().getResourceAsStream(componentsDescFilePath)).map(is -> {
            try {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                throw new InternalServerErrorResponse("Cannot read components from " + componentsDescFilePath);
            }
        }).map(JSON::parseArray).map(it -> it.toJavaList(String.class)).orElseGet(Collections::emptyList);
        return this.configManager.isDebugMode() ? new VezhaExtensionDevDTO(moduleName, devPath, components) : new VezhaExtensionDTO(moduleName, components);
    }

    @Inject
    public PluginExtensionsController(UsersManagerImpl usersManager, PluginMenuGenerator pluginMenuGenerator, ExtensionsManager extensionsManager, VezhaConfigManager configManager) {
        this.usersManager = usersManager;
        this.pluginMenuGenerator = pluginMenuGenerator;
        this.extensionsManager = extensionsManager;
        this.configManager = configManager;
    }
}

