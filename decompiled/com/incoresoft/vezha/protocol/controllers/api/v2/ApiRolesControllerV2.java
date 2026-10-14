/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.roles.RoleCreatedAuditMessage;
import com.incoresoft.dto.audit.messages.roles.RoleDeletedAuditMessage;
import com.incoresoft.errors.CoreErrors;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.permission.PluginPermissions;
import com.incoresoft.middleware.model.role.Role;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.audit.messages.roles.RoleUpdatedAuditMessage;
import com.incoresoft.vezha.dto.roles.PermissionGroupsDTO;
import com.incoresoft.vezha.dto.roles.RoleDTO;
import com.incoresoft.vezha.models.permission.PermissionsGroups;
import com.incoresoft.vezha.models.role.RoleImpl;
import com.incoresoft.vezha.models.role.RoleManagerImpl;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import com.incoresoft.vezha.plugins.VezhaExtensionsManager;
import com.incoresoft.vezha.protocol.controllers.plugin.MenuGroup;
import com.incoresoft.vezha.protocol.controllers.plugin.PluginMenuGenerator;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.NotFoundResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.apache.commons.lang3.tuple.Pair;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class ApiRolesControllerV2
implements Controller {
    private static final String API_PATH = "/api/v2/roles";
    private final UsersManagerImpl usersManager;
    private final RolesManager rolesManager;
    private final VezhaExtensionsManager extensionsManager;
    private final Auditor auditor;
    private final BeanValidator validator;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get("permissions", this::getPermissions, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageRoles);
            ApiBuilder.get(this::getRoles, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewRoles);
            ApiBuilder.post(this::addRole, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageRoles);
            ApiBuilder.put(this::updateRole, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageRoles);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get(this::getRole, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageRoles);
                ApiBuilder.delete(this::deleteRole, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageRoles);
            });
        }));
    }

    @GET
    @Path(value="/api/v2/roles/permissions")
    @Operation(tags={"Roles"}, summary="Get permissions", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=PermissionGroupsDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getPermissions(@Parameter(hidden=true) Context ctx) {
        LinkedHashMap<String, Collection<? extends Permission>> permissionsMap = new LinkedHashMap<String, Collection<? extends Permission>>();
        for (PermissionsGroups value : PermissionsGroups.values()) {
            permissionsMap.put(value.getLocaleKey(), value.getPermissions());
        }
        permissionsMap.putAll(this.extensionsManager.getPluginPermissionsMap().entrySet().stream().map(it -> Pair.of(PluginMenuGenerator.getMenuGroup((VezhaExtension)it.getKey()), (PluginPermissions)it.getValue())).filter(it -> it.getLeft() != null).collect(Collectors.toMap(it -> ((MenuGroup)it.getKey()).getPluginSidebar(), it -> ((PluginPermissions)it.getValue()).getPermissions())));
        ctx.json(new PermissionGroupsDTO(permissionsMap));
    }

    @GET
    @Path(value="/api/v2/roles")
    @Operation(tags={"Roles"}, summary="Get roles", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=RoleDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getRoles(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        ctx.json(this.rolesManager.getAll().stream().filter(user::hasAccessTo).map(RoleDTO::new).toList());
    }

    @GET
    @Path(value="/api/v2/roles/{id}")
    @Operation(tags={"Roles"}, summary="Get role by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=RoleDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getRole(@Parameter(hidden=true) Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        Optional.of(id).map(this.rolesManager::get).map(RoleDTO::new).ifPresentOrElse(ctx::json, NotFoundResponse::new);
    }

    @POST
    @Path(value="/api/v2/roles")
    @Operation(tags={"Roles"}, summary="Add new role", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=RoleDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=RoleDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void addRole(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        RoleDTO roleDTO = new ApiParams(ctx).getBodyAsClass(RoleDTO.class);
        this.validator.validateObjectOrThrow(roleDTO, new Class[0]);
        Set<Permission> permissions = this.extensionsManager.getPermissions().stream().filter(it -> roleDTO.getPermissions().contains(it.getId())).collect(Collectors.toUnmodifiableSet());
        RoleImpl role = new RoleImpl(roleDTO.getName(), roleDTO.getDescription(), permissions, user.getClientId());
        Role added = Optional.of(role).map(this.rolesManager::add).orElseThrow(InternalServerErrorResponse::new);
        ctx.json(new RoleDTO(added));
        this.auditor.audit(ctx, user, CoreAuditEventAction.ROLE_ADDED, "middleware-core", new RoleCreatedAuditMessage(added));
    }

    @PUT
    @Path(value="/api/v2/roles")
    @Operation(tags={"Roles"}, summary="Update role", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=RoleDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=RoleDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateRole(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        RoleDTO roleDTO = new ApiParams(ctx).getBodyAsClass(RoleDTO.class);
        this.validator.validateObjectOrThrow(roleDTO, new Class[0]);
        if (roleDTO.getId() == RoleManagerImpl.ROOT.getId()) {
            throw new ErrorException(CoreErrors.STATIC_PERMISSIONS, new Object[0]);
        }
        Role old = Optional.ofNullable((Role)this.rolesManager.get(roleDTO.getId())).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        Set<Permission> permissions = this.extensionsManager.getPermissions().stream().filter(it -> roleDTO.getPermissions().contains(it.getId())).collect(Collectors.toUnmodifiableSet());
        RoleImpl updatedRole = new RoleImpl(roleDTO.getId(), roleDTO.getName(), roleDTO.getDescription(), permissions, user.getClientId());
        Role updated = Optional.of(updatedRole).map(this.rolesManager::update).orElseThrow(InternalServerErrorResponse::new);
        this.auditor.audit(ctx, user, CoreAuditEventAction.ROLE_UPDATED, "middleware-core", new RoleUpdatedAuditMessage(old, updated));
    }

    @DELETE
    @Path(value="/api/v2/roles/{id}")
    @Operation(tags={"Roles"}, summary="Delete role by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteRole(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        Integer id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        if (id.intValue() == RoleManagerImpl.ROOT.getId()) {
            throw new ErrorException(CoreErrors.STATIC_PERMISSIONS, new Object[0]);
        }
        Role role = Optional.ofNullable((Role)this.rolesManager.get(id)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        Optional<UserImpl> check2 = this.usersManager.getData().values().stream().filter(it -> it.getStatus() != -1).filter(it -> it.getRoleIds().contains(id)).findAny();
        if (check2.isPresent()) {
            throw new ErrorException(CoreErrors.USER_STILL_EXISTS, new Object[0]);
        }
        this.rolesManager.delete(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.ROLE_DELETED, "middleware-core", new RoleDeletedAuditMessage(role));
    }

    @Inject
    public ApiRolesControllerV2(UsersManagerImpl usersManager, RolesManager rolesManager, VezhaExtensionsManager extensionsManager, Auditor auditor, BeanValidator validator) {
        this.usersManager = usersManager;
        this.rolesManager = rolesManager;
        this.extensionsManager = extensionsManager;
        this.auditor = auditor;
        this.validator = validator;
    }
}

