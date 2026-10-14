/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.dto.audit.messages.users.LDAPUsersFetchedAuditMessage;
import com.incoresoft.dto.audit.messages.users.UserCreatedAuditMessage;
import com.incoresoft.dto.audit.messages.users.UserDeletedAuditMessage;
import com.incoresoft.dto.sessions.api.ApiTokenDTO;
import com.incoresoft.dto.sessions.api.CreateApiTokenDTO;
import com.incoresoft.dto.sessions.api.CreateApiTokenResponseDTO;
import com.incoresoft.dto.user.UserProfileInformationDTO;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.dto.ErrorDTO;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.sorting.SortFields;
import com.incoresoft.plugin.commons.sorting.SortOrders;
import com.incoresoft.plugin.commons.sorting.SortParams;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.sessions.SessionHandlerImpl;
import com.incoresoft.sessions.api.ApiToken;
import com.incoresoft.user.UserSettings;
import com.incoresoft.vezha.audit.messages.users.UserUpdatedAuditMessage;
import com.incoresoft.vezha.auth.exceptions.LoginIsDisabledException;
import com.incoresoft.vezha.config.VezhaConfigManager;
import com.incoresoft.vezha.dto.users.ChangePasswordDTO;
import com.incoresoft.vezha.dto.users.LoginDTO;
import com.incoresoft.vezha.dto.users.UserDTO;
import com.incoresoft.vezha.dto.users.UserInfoDTO;
import com.incoresoft.vezha.dto.users.UsersPaginationAnswerDTO;
import com.incoresoft.vezha.dto.users.VmsCreateLdapUserDTO;
import com.incoresoft.vezha.models.users.UserImpl;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import com.incoresoft.vezha.models.users.exceptions.CannotEditRootUserException;
import com.incoresoft.vezha.models.users.model.UserSearchParams;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
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
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API"))
public class ApiUsersControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiUsersControllerV2.class);
    private final String API_PATH = "/api/v2/users";
    private final UsersManagerImpl usersManager;
    private final RolesManager roleManager;
    private final SessionHandlerImpl sessionHandler;
    private final Auditor auditor;
    private final VezhaConfigManager configManager;
    private final BeanValidator validator;

    @Override
    @Inject
    public void init(Javalin app) {
        if (this.configManager.isVmsIntegrationEnabled()) {
            app.addHandler(HandlerType.POST, "/api/v2/users/ldap", this::addLdapUser);
        }
        app.routes(() -> ApiBuilder.path("/api/v2/users", () -> {
            ApiBuilder.post("login", this::login, AppRole.ANYONE);
            ApiBuilder.post("logout", this::logout, AppRole.LOGGED_IN);
            ApiBuilder.get(this::getUsers, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewUsers);
            ApiBuilder.post(this::addUser, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageUsers);
            ApiBuilder.put(this::updateUser, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageUsers);
            ApiBuilder.get("user_info", this::getMe, AppRole.LOGGED_IN);
            ApiBuilder.path("tokens", () -> {
                ApiBuilder.get(this::getTokens, AppRole.LOGGED_IN);
                ApiBuilder.get("current", this::getCurrentToken, AppRole.API_CALL);
                ApiBuilder.post("issue", this::issueToken, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.delete("{id}/revoke", this::revokeToken, AppRole.LOGGED_IN, AppRole.API_CALL);
            });
            ApiBuilder.post("toggle_sounds", this::toggleSounds, AppRole.LOGGED_IN);
            ApiBuilder.patch("change_language", this::changeLanguage, AppRole.LOGGED_IN);
            ApiBuilder.patch("change_password", this::changePassword, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("profile_info", () -> {
                ApiBuilder.get(this::getUserProfileInformation, AppRole.LOGGED_IN);
                ApiBuilder.patch(this::updateUserProfileInformation, AppRole.LOGGED_IN);
            });
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get(this::getUser, AppRole.LOGGED_IN);
                ApiBuilder.put("status", this::setUserStatus, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageUsers);
                ApiBuilder.delete(this::deleteUser, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageUsers);
            });
        }));
    }

    @POST
    @Path(value="/api/v2/users/login")
    @Operation(tags={"Users"}, summary="Login", requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=LoginDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(type="object", example="\"sid\":\"some generated sid\""))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void login(@Parameter(hidden=true) Context ctx) {
        this.validateLoginEnabled();
        LoginDTO loginDTO = new ApiParams(ctx).getBodyAsClass(LoginDTO.class);
        this.validator.validateObjectOrThrow(loginDTO, new Class[0]);
        User user = this.usersManager.login(ctx, loginDTO.getLogin(), loginDTO.getPassword());
        ctx.json(new JSONObject().fluentPut("sid", ctx.req().getSession().getId()));
        this.auditor.audit(ctx, user, CoreAuditEventAction.LOGIN, "middleware-core");
    }

    @POST
    @Path(value="/api/v2/users/logout")
    @Operation(tags={"Users"}, summary="Logout", security={@SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful logout"), @ApiResponse(responseCode="304", description="Successful redirect to /login/ page"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void logout(@Parameter(hidden=true) Context ctx) {
        Integer userId = (Integer)ctx.sessionAttribute("user_id");
        Optional.ofNullable(userId).map(this.usersManager::get).ifPresent(user -> this.auditor.audit(ctx, (User)user, CoreAuditEventAction.LOGOUT, "middleware-core"));
        ctx.req().getSession().invalidate();
        ctx.status(HttpStatus.NO_CONTENT);
    }

    @GET
    @Path(value="/api/v2/users")
    @Operation(tags={"Users"}, summary="Retrieves list of all existing users.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="offset", example="0", description="Selected offset"), @Parameter(in=ParameterIn.QUERY, name="limit", example="10", description="Maximum number of elements to return"), @Parameter(in=ParameterIn.QUERY, name="name", example="user@example.com", description="User login or full name"), @Parameter(in=ParameterIn.QUERY, name="role_ids", example="[1,2,3]", description="User role IDs (should be passed as \"role_ids=[1, 2, 3]\")"), @Parameter(in=ParameterIn.QUERY, name="sort_field", example="name", description="Sort field", schema=@Schema(implementation=SortFields.class)), @Parameter(in=ParameterIn.QUERY, name="sort_order", example="asc", description="Sort order", schema=@Schema(implementation=SortOrders.class))}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of users"), schema=@Schema(implementation=UserDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getUsers(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        UserSearchParams params = new ApiParams(ctx).getQueryParamsAsClass(UserSearchParams.class);
        PaginationParams paginationParams = new ApiParams(ctx).getQueryParamsAsClass(PaginationParams.class);
        SortParams sortParams = new ApiParams(ctx).getQueryParamsAsClass(SortParams.class);
        Collection<? extends Permission> allPermissions = this.roleManager.getPermissions();
        List<UserImpl> allUsers = this.usersManager.getUsers(user, params, sortParams);
        int limit = paginationParams.getLimit().orElse(Integer.MAX_VALUE);
        List<UserDTO> result = allUsers.stream().skip(paginationParams.getOffset()).limit(limit).map(it -> new UserDTO((UserImpl)it, allPermissions)).toList();
        ctx.json(new UsersPaginationAnswerDTO(result, (long)allUsers.size(), limit));
    }

    @GET
    @Path(value="/api/v2/users/{id}")
    @Operation(tags={"Users"}, summary="Get user by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getUser(@Parameter(hidden=true) Context ctx) {
        Integer id = ctx.pathParamAsClass("id", Integer.TYPE).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        Optional.of(id).map(this.usersManager::get).map(UserImpl.class::cast).map(it -> new UserDTO((UserImpl)it, this.roleManager.getPermissions())).ifPresentOrElse(ctx::json, NotFoundResponse::new);
    }

    @POST
    @Path(value="/api/v2/users")
    @Operation(tags={"Users"}, summary="Add new user", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void addUser(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserDTO userDTO = new ApiParams(ctx).getBodyAsClass(UserDTO.class);
        UserImpl added = Optional.of(this.getUserModel(ctx, userDTO, user, false)).map(this.usersManager::addUser).orElseThrow(InternalServerErrorResponse::new);
        ctx.json(new UserDTO(added, this.roleManager.getPermissions()));
        this.auditor.audit(ctx, user, CoreAuditEventAction.USER_ADDED, "middleware-core", new UserCreatedAuditMessage(added));
    }

    @PUT
    @Path(value="/api/v2/users")
    @Operation(tags={"Users"}, summary="Update user", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateUser(@Parameter(hidden=true) Context ctx) {
        UserImpl current = (UserImpl)this.usersManager.getUser(ctx);
        UserDTO userDTO = new ApiParams(ctx).getBodyAsClass(UserDTO.class);
        UserImpl old = Optional.of(userDTO.getId()).map(this.usersManager::get).map(UserImpl.class::cast).orElseThrow(NotFoundResponse::new);
        UserImpl userToUpdate = this.getUserModel(ctx, userDTO, old, true);
        if (UsersManagerImpl.isOriginalRoot(userToUpdate) && !UsersManagerImpl.isOriginalRoot(current)) {
            throw new CannotEditRootUserException();
        }
        UserImpl updated = (UserImpl)this.usersManager.updateUser(userToUpdate);
        Collection<? extends Permission> permissions = this.roleManager.getPermissions();
        ctx.status(204);
        this.auditor.audit(ctx, userToUpdate, CoreAuditEventAction.USER_UPDATED, "middleware-core", new UserUpdatedAuditMessage(old, updated, permissions));
    }

    @DELETE
    @Path(value="/api/v2/users/{id}")
    @Operation(tags={"Users"}, summary="Get user by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteUser(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        User deleted = Optional.of(id).map(this.usersManager::get).orElseThrow(NotFoundResponse::new);
        this.usersManager.deleteUser(ctx.req().getSession(), user, id);
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.USER_DELETED, "middleware-core", new UserDeletedAuditMessage(deleted));
    }

    @GET
    @Path(value="/api/v2/users/user_info")
    @Operation(tags={"Users"}, summary="Get current user info", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserInfoDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized")})
    public void getMe(@Parameter(hidden=true) Context ctx) {
        Optional.ofNullable(this.usersManager.getUser(ctx)).map(UserImpl.class::cast).map(user -> new UserInfoDTO((UserImpl)user, this.roleManager.getPermissions(), "/storage/")).ifPresentOrElse(ctx::json, NotFoundResponse::new);
    }

    @PUT
    @Path(value="/api/v2/users/{id}/status")
    @Operation(tags={"Users"}, summary="Set user status", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Analytics ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void setUserStatus(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        if (user.getId() == id) {
            throw new ForbiddenResponse();
        }
        int status = ctx.queryParamAsClass("status", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "status"));
        UserImpl updated = (UserImpl)this.usersManager.setUserStatus(id, status);
        Collection<? extends Permission> permissions = this.roleManager.getPermissions();
        this.auditor.audit(ctx, user, CoreAuditEventAction.USER_UPDATED, "middleware-core", new UserUpdatedAuditMessage(user, updated, permissions));
        ctx.status(204);
    }

    @POST
    @Path(value="/api/v2/users/toggle_sounds")
    @Operation(tags={"Users"}, summary="Toggle all sounds for user", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void toggleSounds(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        user.getSettings().toggleSound();
        this.usersManager.updateUser(user);
        ctx.status(204);
    }

    @GET
    @Path(value="/api/v2/users/tokens")
    @Operation(tags={"Users"}, summary="Get all API tokens for current user", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ApiTokenDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getTokens(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        List<ApiTokenDTO> apiSessions = this.usersManager.getApiSessions(user).stream().map(ApiTokenDTO::new).toList();
        ctx.json(apiSessions);
    }

    @GET
    @Path(value="/api/v2/users/tokens/current")
    @Operation(tags={"Users"}, summary="Get API token used by current request", security={@SecurityRequirement(name="bearerAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=ApiTokenDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getCurrentToken(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        ApiToken apiToken = this.getApiTokenFromRequest(ctx).filter(token -> token.userId() == user.getId()).orElseThrow(ForbiddenResponse::new);
        ctx.json(new ApiTokenDTO(apiToken));
    }

    @POST
    @Path(value="/api/v2/users/tokens/issue")
    @Operation(tags={"Users"}, summary="Issue API token for current user", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=CreateApiTokenDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=CreateApiTokenResponseDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void issueToken(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        CreateApiTokenDTO dto = new ApiParams(ctx).getBodyAsClass(CreateApiTokenDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        Pair<String, ApiToken> apiSession = this.usersManager.issueToken(user, dto.name(), Duration.between(Instant.now(), dto.expiresAt()));
        ctx.json(new CreateApiTokenResponseDTO(apiSession.getLeft(), new ApiTokenDTO(apiSession.getRight())));
    }

    @DELETE
    @Path(value="/api/v2/users/tokens/{id}/revoke")
    @Operation(tags={"Users"}, summary="Revokes specified API token for this user. To receive new one, use /api/v2/users/token.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Token ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void revokeToken(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        this.usersManager.revokeToken(user, id);
        ctx.status(204);
    }

    private Optional<ApiToken> getApiTokenFromRequest(Context ctx) {
        String authorization = ctx.header("Authorization");
        if (!StringUtils.startsWith(authorization, "Bearer ")) {
            return Optional.empty();
        }
        String token = StringUtils.substringAfter(authorization, "Bearer ").trim();
        if (TextUtils.isEmpty(token)) {
            return Optional.empty();
        }
        return this.usersManager.getApiSession(token);
    }

    @PATCH
    @Path(value="/api/v2/users/change_language")
    @Operation(tags={"Users"}, summary="Change user language", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="lang", description="Language", schema=@Schema(implementation=String.class, example="en"))}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void changeLanguage(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        String newLanguage = ctx.queryParam("lang");
        if (TextUtils.isEmpty(newLanguage)) {
            throw new ErrorException(BaseError.RequiredParams, "lang");
        }
        this.usersManager.setLanguage(user, newLanguage);
        ctx.status(204);
    }

    @PATCH
    @Path(value="/api/v2/users/change_password")
    @Operation(tags={"Users"}, summary="Change user password", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=ChangePasswordDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void changePassword(@Parameter(hidden=true) Context context) {
        UserImpl user = (UserImpl)this.usersManager.getUser(context);
        ChangePasswordDTO changePasswordDTO = context.bodyAsClass(ChangePasswordDTO.class);
        changePasswordDTO = changePasswordDTO.withId(user.getId());
        this.validator.validateObjectOrThrow(changePasswordDTO, new Class[0]);
        user.setPassword(this.usersManager.generatePassword(changePasswordDTO.getPassword()));
        this.sessionHandler.invalidateUserSessions(context.req().getSession(), user.getId());
        this.usersManager.updateUser(user);
        context.status(204);
    }

    @GET
    @Path(value="/api/v2/users/profile_info")
    @Operation(tags={"Users"}, summary="Get current user profile information", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserProfileInformationDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getUserProfileInformation(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        ctx.json(new UserProfileInformationDTO(user.getFullname(), user.getSettings()));
    }

    @PATCH
    @Path(value="/api/v2/users/profile_info")
    @Operation(tags={"Users"}, summary="Update current user profile information", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=UserProfileInformationDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Bad request", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))}), @ApiResponse(responseCode="500", description="Internal server error", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=ErrorDTO.class)))})})
    public void updateUserProfileInformation(@Parameter(hidden=true) Context context) {
        UserImpl user = (UserImpl)this.usersManager.getUser(context);
        UserProfileInformationDTO dto = context.bodyAsClass(UserProfileInformationDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        user.setSettings(new UserSettings(user.getSettings(), dto));
        UserImpl updated = (UserImpl)this.usersManager.updateUser(user);
        Collection<? extends Permission> permissions = this.roleManager.getPermissions();
        context.status(204);
        this.auditor.audit(context, user, CoreAuditEventAction.USER_UPDATED, "middleware-core", new UserUpdatedAuditMessage(user, updated, permissions));
    }

    @POST
    @Path(value="/api/v2/users/ldap")
    @Operation(tags={"VMS integration"}, summary="Add LDAP user", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=UserDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void addLdapUser(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        VmsCreateLdapUserDTO userDTO = new ApiParams(ctx).getBodyAsClass(VmsCreateLdapUserDTO.class);
        this.validator.validateObjectOrThrow(userDTO, new Class[0]);
        UserImpl ldapUser = this.usersManager.addUser(new UserImpl(userDTO, this.roleManager));
        this.auditor.audit(ctx, user, CoreAuditEventAction.USERS_FETCHED_FROM_LDAP, "middleware-core", new LDAPUsersFetchedAuditMessage(ldapUser));
    }

    private UserImpl getUserModel(Context ctx, UserDTO userDTO, User base, boolean update) {
        User user;
        if (userDTO.getClientId() == null) {
            userDTO.setClientId(base.getClientId());
        }
        if (update && userDTO.getId() == null) {
            throw new ErrorException(BaseError.RequiredParams, "id");
        }
        this.validator.validateObjectOrThrow(userDTO, new Class[0]);
        UserImpl userObject = new UserImpl(userDTO, this.roleManager);
        if (update) {
            userObject.setLastIP(((UserImpl)base).getLastIP());
            userObject.setLastLogin(((UserImpl)base).getLastLogin());
            userObject.setCreatedAt(((UserImpl)base).getCreatedAt());
            userObject.setIpParams(((UserImpl)base).getIpParams());
        }
        boolean passwordUpdate = false;
        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            userObject.setPassword(this.usersManager.generatePassword(userObject.getPassword()));
            this.sessionHandler.invalidateUserSessions(ctx.req().getSession(), userObject.getId());
            passwordUpdate = true;
        }
        if (!passwordUpdate && (user = this.usersManager.get(userObject.getId())) != null) {
            userObject.setPassword(((UserImpl)user).getPassword());
        }
        if (StringUtils.isNotEmpty(userDTO.getTimezone())) {
            userObject.setTimezone(ZoneId.of(userDTO.getTimezone()));
        } else {
            userObject.setTimezone(base.getTimezone());
        }
        return userObject;
    }

    private void validateLoginEnabled() {
        if (this.configManager.isVmsIntegrationEnabled()) {
            throw new LoginIsDisabledException();
        }
    }

    @Inject
    public ApiUsersControllerV2(UsersManagerImpl usersManager, RolesManager roleManager, SessionHandlerImpl sessionHandler, Auditor auditor, VezhaConfigManager configManager, BeanValidator validator) {
        this.usersManager = usersManager;
        this.roleManager = roleManager;
        this.sessionHandler = sessionHandler;
        this.auditor = auditor;
        this.configManager = configManager;
        this.validator = validator;
    }
}

