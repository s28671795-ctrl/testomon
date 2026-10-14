/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.users.LDAPUsersFetchedAuditMessage;
import com.incoresoft.dto.ldap.LDAPConnectionParams;
import com.incoresoft.dto.ldap.LDAPImportParams;
import com.incoresoft.dto.ldap.LDAPUserData;
import com.incoresoft.errors.CoreErrors;
import com.incoresoft.ldap.LdapAuthorizationResponse;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.models.users.ldap.VezhaLdapClient;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiLdapControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiLdapControllerV2.class);
    private static final String API_PATH = "/api/v2/ldap";
    private final UsersManager usersManager;
    private final VezhaLdapClient ldapClient;
    private final BeanValidator validator;
    private final Auditor auditor;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.post(this::testConnection, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageUsers);
            ApiBuilder.post("fetch", this::fetchUsers, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageUsers);
            ApiBuilder.post("import", this::importUsers, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageUsers);
        }));
    }

    @POST
    @Path(value="/api/v2/ldap")
    @Operation(tags={"LDAP"}, summary="Test connection", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=LDAPConnectionParams.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void testConnection(@Parameter(hidden=true) Context ctx) {
        LDAPConnectionParams connectionParams = new ApiParams(ctx).getBodyAsClass(LDAPConnectionParams.class);
        this.validator.validateObjectOrThrow(connectionParams, new Class[0]);
        LdapAuthorizationResponse response = this.ldapClient.testConnection(connectionParams);
        if (response.resultCode() == LdapAuthorizationResponse.AuthenticationError.CONNECTION_ERROR) {
            throw new ValidationException(CoreErrors.LDAP_SERVER_CONNECTION_FAILED, "url", new Object[0]);
        }
        if (response.resultCode() == LdapAuthorizationResponse.AuthenticationError.INVALID_CREDENTIALS) {
            throw new ValidationException(CoreErrors.WRONG_LDAP_LOGIN_OR_PASSWORD, "login", new Object[0]);
        }
        if (response.resultCode() == LdapAuthorizationResponse.AuthenticationError.INVALID_DN) {
            throw new ValidationException(CoreErrors.INVALID_LDAP_BASE, "base", new Object[0]);
        }
        ctx.status(204);
    }

    @POST
    @Path(value="/api/v2/ldap/fetch")
    @Operation(tags={"LDAP"}, summary="Fetch users", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=LDAPConnectionParams.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=LDAPUserData.class)))}), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void fetchUsers(@Parameter(hidden=true) Context ctx) {
        LDAPConnectionParams connectionParams = new ApiParams(ctx).getBodyAsClass(LDAPConnectionParams.class);
        this.validator.validateObjectOrThrow(connectionParams, new Class[0]);
        List<LDAPUserData> users = this.ldapClient.fetchAllLdapUsers(connectionParams);
        ctx.json(users);
    }

    @POST
    @Path(value="/api/v2/ldap/import")
    @Operation(tags={"LDAP"}, summary="Import users", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=LDAPImportParams.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void importUsers(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        LDAPImportParams importParams = new ApiParams(ctx).getBodyAsClass(LDAPImportParams.class);
        this.validator.validateObjectOrThrow(importParams, new Class[0]);
        List<User> users = this.ldapClient.importUsers(user, importParams);
        ctx.status(204);
        for (User ldapUser : users) {
            this.auditor.audit(ctx, user, CoreAuditEventAction.USERS_FETCHED_FROM_LDAP, "middleware-core", new LDAPUsersFetchedAuditMessage(ldapUser));
        }
    }

    @Inject
    public ApiLdapControllerV2(UsersManager usersManager, VezhaLdapClient ldapClient, BeanValidator validator, Auditor auditor) {
        this.usersManager = usersManager;
        this.ldapClient = ldapClient;
        this.validator = validator;
        this.auditor = auditor;
    }
}

