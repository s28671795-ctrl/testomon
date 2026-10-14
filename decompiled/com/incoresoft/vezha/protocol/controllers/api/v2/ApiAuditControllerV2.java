/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.Utils.Pair;
import com.incoresoft.audit.AuditEventActionsManager;
import com.incoresoft.audit.BasicAuditor;
import com.incoresoft.dto.audit.AuditEventDTO;
import com.incoresoft.dto.audit.AuditPaginationAnswerDTO;
import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventCategory;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.params.StartEndDateParams;
import com.incoresoft.vezha.dto.audit.AuditEventCategoryDTO;
import com.incoresoft.vezha.dto.audit.VezhaAuditTrailParams;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiAuditControllerV2
implements Controller {
    private static final String API_PATH = "/api/v2/audit";
    private final BasicAuditor auditor;
    private final UsersManagerImpl usersManager;
    private final AuditEventActionsManager auditEventActionsManager;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getAuditEvents, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewLogs);
            ApiBuilder.get("actions", this::getActions, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewLogs);
        }));
    }

    @GET
    @Path(value="/api/v2/audit")
    @Operation(tags={"Audit"}, summary="Get list of all audit logs", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(name="categories", in=ParameterIn.QUERY, description="Categories to filter", schema=@Schema(type="object", example="[\"user_management\",\"analytics_management\"]")), @Parameter(name="actions", in=ParameterIn.QUERY, description="Actions to filter", schema=@Schema(type="object", example="[\"user_deleted\",\"analytics_updated\"]")), @Parameter(name="user_email", in=ParameterIn.QUERY, description="User email to filter", example="admin", schema=@Schema(implementation=String.class)), @Parameter(name="start_date", in=ParameterIn.QUERY, description="Search interval start", example="1619712000000", schema=@Schema(implementation=Long.class)), @Parameter(name="end_date", in=ParameterIn.QUERY, description="Search interval end", example="1619712000000", schema=@Schema(implementation=Long.class)), @Parameter(name="offset", in=ParameterIn.QUERY, example="0", description="Selected offset"), @Parameter(name="limit", in=ParameterIn.QUERY, example="10", description="Maximum number of elements to return")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=AuditPaginationAnswerDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getAuditEvents(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        ApiParams params = new ApiParams(ctx);
        StartEndDateParams dateParams = params.getQueryParamsAsClass(StartEndDateParams.class);
        VezhaAuditTrailParams auditTrailParams = params.getQueryParamsAsClass(VezhaAuditTrailParams.class);
        PaginationParams paginationParams = params.getQueryParamsAsClass(PaginationParams.class);
        Integer clientId = user.isRoot() ? null : Integer.valueOf(user.getClientId());
        Pair<Integer, List<AuditEvent>> result = this.auditor.getAuditEvents(dateParams, auditTrailParams, paginationParams, clientId);
        List<AuditEventDTO> auditEventsDtoList = ((List)result.second).stream().map(inner -> new AuditEventDTO((AuditEvent)inner, this.usersManager.get(Math.toIntExact(inner.getUserId())))).toList();
        ctx.json(new AuditPaginationAnswerDTO(auditEventsDtoList, (long)((Integer)result.first).intValue(), (int)paginationParams.getLimit().orElse(100)));
    }

    @GET
    @Path(value="/api/v2/audit/actions")
    @Operation(tags={"Audit"}, summary="Get list of available categories and actions of audit events", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=AuditEventCategoryDTO.class)))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void getActions(@Parameter(hidden=true) Context ctx) {
        ctx.json(this.auditEventActionsManager.getActionsMap().entrySet().stream().map(it -> new AuditEventCategoryDTO((AuditEventCategory)it.getKey(), (List)it.getValue())).toList());
    }

    @Inject
    public ApiAuditControllerV2(BasicAuditor auditor, UsersManagerImpl usersManager, AuditEventActionsManager auditEventActionsManager) {
        this.auditor = auditor;
        this.usersManager = usersManager;
        this.auditEventActionsManager = auditEventActionsManager;
    }
}

