/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.dto.docs.DocsLinkDTO;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.http.docs.AsyncApiDocumentationProvider;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.openapi.OpenApiHandler;
import com.incoresoft.vezha.protocol.controllers.plugin.MenuGroup;
import com.incoresoft.vezha.protocol.controllers.plugin.PluginMenuGenerator;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.openapi.plugin.redoc.ReDocHandler;
import io.javalin.openapi.plugin.swagger.SwaggerHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.LinkedList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"), tags={@Tag(name="Audit"), @Tag(name="Clients"), @Tag(name="Countries"), @Tag(name="Event Manager"), @Tag(name="General settings"), @Tag(name="System settings"), @Tag(name="LDAP"), @Tag(name="Licenses"), @Tag(name="Notifications"), @Tag(name="Plugins"), @Tag(name="Roles"), @Tag(name="Servers"), @Tag(name="Sounds"), @Tag(name="Streams"), @Tag(name="Streams groups"), @Tag(name="Onvif"), @Tag(name="Timezones"), @Tag(name="Users"), @Tag(name="SMB protocol")})
@SecuritySchemes(value={@SecurityScheme(name="bearerAuth", type=SecuritySchemeType.HTTP, scheme="bearer", description="API bearer access token. If session id is provided, api token can be omitted."), @SecurityScheme(name="cookieAuth", type=SecuritySchemeType.APIKEY, in=SecuritySchemeIn.COOKIE, paramName="sid", description="Current session id. If api token is provided, session id can be omitted.")})
public class ApiDocsControllerV2
implements Controller {
    private final ExtensionsManager extensionsManager;
    private final AsyncApiDocumentationProvider asyncApiDocumentationProvider;
    public static final String API_ASYNC_API_WS_PATH = "asyncapi/core/ws";
    public static final String API_ASYNC_API_SSE_PATH = "asyncapi/core/sse";

    @Override
    @Inject
    public void init(Javalin app) {
        app.get("openapi/core", new OpenApiHandler(ApiDocsControllerV2.class.getPackageName(), ApiDocsControllerV2.class.getClassLoader()), AppRole.ANYONE);
        app.get("swagger/core", new SwaggerHandler("middleware-core", "/openapi/core", "5.9.0", null, ""), AppRole.ANYONE);
        app.get("redoc/core", new ReDocHandler("middleware-core", "/openapi/core", "2.0.0", "", ""), AppRole.ANYONE);
        app.get("api/v2/docs/list", this::getDocsList, AppRole.LOGGED_IN);
        app.get(API_ASYNC_API_WS_PATH, this.asyncApiDocumentationProvider::provideAsyncApiWS, AppRole.ANYONE);
        app.get(API_ASYNC_API_SSE_PATH, this.asyncApiDocumentationProvider::provideAsyncApiSSE, AppRole.ANYONE);
    }

    @GET
    @Path(value="api/v2/docs/list")
    @Operation(tags={"Docs"}, summary="Get list of documentation links for core all installed plugins", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=DocsLinkDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getDocsList(@Parameter(hidden=true) Context ctx) {
        LinkedList<DocsLinkDTO> documentationLinks = new LinkedList<DocsLinkDTO>();
        documentationLinks.add(new DocsLinkDTO("MIDDLEWARE_CORE", "swagger/core"));
        for (VezhaExtension extension : this.extensionsManager.getAll(VezhaExtension.class)) {
            MenuGroup menuGroup = PluginMenuGenerator.getMenuGroup(extension);
            if (menuGroup == null) continue;
            documentationLinks.add(new DocsLinkDTO(menuGroup.getPluginSidebar(), "swagger/" + extension.getPluginName()));
        }
        ctx.json(documentationLinks);
    }

    @Inject
    public ApiDocsControllerV2(ExtensionsManager extensionsManager, AsyncApiDocumentationProvider asyncApiDocumentationProvider) {
        this.extensionsManager = extensionsManager;
        this.asyncApiDocumentationProvider = asyncApiDocumentationProvider;
    }
}

