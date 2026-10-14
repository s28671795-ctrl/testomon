/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.google.inject.Inject;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.clients.Client;
import com.incoresoft.clients.ClientsManager;
import com.incoresoft.dto.audit.messages.clients.ClientCreatedAuditMessage;
import com.incoresoft.dto.audit.messages.clients.ClientDeletedAuditMessage;
import com.incoresoft.dto.audit.messages.clients.ClientUpdatedAuditMessage;
import com.incoresoft.dto.clients.ClientDTO;
import com.incoresoft.dto.clients.CreateUpdateClientDTO;
import com.incoresoft.errors.CoreErrors;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.utils.UserUtils;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.models.users.UsersManagerImpl;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.Context;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiClientsControllerV2
implements Controller {
    private static final String API_PATH = "/api/v2/clients";
    private final UsersManagerImpl usersManager;
    private final ClientsManager clientsManager;
    private final BeanValidator validator;
    private final Auditor auditor;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getClients, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this::addClient, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.get(this::getClient, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.put(this::updateClient, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.delete(this::deleteClient, AppRole.LOGGED_IN, AppRole.API_CALL);
            });
        }));
    }

    @GET
    @Path(value="/api/v2/clients")
    @Operation(tags={"Clients"}, summary="Retrieves list of all existing clients", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of clients"), schema=@Schema(implementation=ClientDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getClients(@Parameter(hidden=true) Context ctx) {
        this.usersManager.isRootOrThrow(ctx);
        ctx.json(this.clientsManager.getClients().stream().map(ClientDTO::new).toList());
    }

    @GET
    @Path(value="/api/v2/clients/{id}")
    @Operation(tags={"Clients"}, summary="Get client by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Client ID", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=ClientDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getClient(@Parameter(hidden=true) Context ctx) {
        this.usersManager.isRootOrThrow(ctx);
        int id = ctx.pathParamAsClass("id", Integer.class).get();
        this.clientsManager.get(id).map(ClientDTO::new).ifPresentOrElse(ctx::json, NotFoundResponse::new);
    }

    @POST
    @Path(value="/api/v2/clients")
    @Operation(tags={"Clients"}, summary="Add new client", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=ClientDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=ClientDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void addClient(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        Client client = this.parseClient(ctx, null);
        Client added = this.clientsManager.add(client);
        ctx.json(new ClientDTO(added));
        this.auditor.audit(ctx, user, CoreAuditEventAction.CLIENT_ADDED, "middleware-core", new ClientCreatedAuditMessage(added));
    }

    @PUT
    @Path(value="/api/v2/clients/{id}")
    @Operation(tags={"Clients"}, summary="Update client", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Client ID", required=true)}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=ClientDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=ClientDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateClient(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(ignored -> new ErrorException(BaseError.RequiredParams, "id"));
        Client old = this.clientsManager.get(id).orElseThrow(NotFoundResponse::new);
        Client client = this.parseClient(ctx, old);
        client.setId(id);
        ClientDTO updated = new ClientDTO(this.clientsManager.update(client));
        ctx.json(updated);
        this.auditor.audit(ctx, user, CoreAuditEventAction.CLIENT_DELETED, "middleware-core", new ClientUpdatedAuditMessage(new ClientDTO(old), updated));
    }

    @DELETE
    @Path(value="/api/v2/clients/{id}")
    @Operation(tags={"Clients"}, summary="Delete client by ID", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Client ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteClient(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        UserUtils.requireRoot(user);
        int clientId = ctx.pathParamAsClass("id", Integer.class).get();
        if (Client.DEFAULT.getId().equals(clientId)) {
            throw new ErrorException(BaseError.ImmutableData, new Object[0]);
        }
        Client client = this.clientsManager.get(clientId).orElseThrow(NotFoundResponse::new);
        if (this.clientsManager.clientHasActiveUsers(clientId)) {
            throw new ErrorException(CoreErrors.USERS_WITH_CLIENT_EXIST, new Object[0]);
        }
        this.clientsManager.delete(clientId);
        ctx.status(204);
        this.auditor.audit(ctx, user, CoreAuditEventAction.CLIENT_DELETED, "middleware-core", new ClientDeletedAuditMessage(client));
    }

    private Client parseClient(Context ctx, Client existing) {
        CreateUpdateClientDTO clientDTO = Serialization.readValue(ctx.body(), CreateUpdateClientDTO.class);
        if (existing != null) {
            clientDTO.setId(existing.getId());
        }
        this.validator.validateObjectOrThrow(clientDTO, new Class[0]);
        if (Client.DEFAULT.getId().equals(clientDTO.getId())) {
            throw new ErrorException(BaseError.ImmutableData, new Object[0]);
        }
        return new Client(clientDTO);
    }

    @Inject
    public ApiClientsControllerV2(UsersManagerImpl usersManager, ClientsManager clientsManager, BeanValidator validator, Auditor auditor) {
        this.usersManager = usersManager;
        this.clientsManager = clientsManager;
        this.validator = validator;
        this.auditor = auditor;
    }
}

