/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.eventmanager.Event;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.plugin.commons.analytics.events.EventImpl;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import com.incoresoft.vezha.audit.messages.event_manager.EventManagerEventAddedAuditMessage;
import com.incoresoft.vezha.audit.messages.event_manager.EventManagerEventDeletedAuditMessage;
import com.incoresoft.vezha.audit.messages.event_manager.EventManagerEventUpdatedAuditMessage;
import com.incoresoft.vezha.dto.eventmanager.EventCopyDTO;
import com.incoresoft.vezha.dto.eventmanager.EventListElementDTO;
import com.incoresoft.vezha.dto.eventmanager.EventSchemeDTO;
import com.incoresoft.vezha.eventmanager.EventManager;
import com.incoresoft.vezha.eventmanager.EventScheme;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiEventManagerControllerV2
implements Controller {
    private static final String API_PATH = "/api/v2/event_manager";
    private final UsersManager usersManager;
    private final EventManager eventManager;
    private final Auditor auditor;
    private final BeanValidator validator;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getList, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.post(this::create, AppRole.LOGGED_IN, AppRole.API_CALL);
            ApiBuilder.path("{uuid}", () -> {
                ApiBuilder.get(this::getData, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.put(this::update, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.delete(this::deleteEvent, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.post("start", this::start, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.post("cancel", this::cancel, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.post("copy", this::copy, AppRole.LOGGED_IN, AppRole.API_CALL);
            });
        }));
    }

    @GET
    @Path(value="/api/v2/event_manager")
    @Operation(tags={"Event Manager"}, summary="Get list of events", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(schema=@Schema(implementation=EventListElementDTO.class)))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getList(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        Comparator<EventListElementDTO> comparator = Comparator.comparing(EventListElementDTO::getTitle, String.CASE_INSENSITIVE_ORDER).thenComparing(EventListElementDTO::getDescription, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER));
        ctx.json(this.eventManager.getEventsList().stream().filter(user::hasAccessTo).map(EventListElementDTO::new).sorted(comparator).toList());
    }

    @POST
    @Path(value="/api/v2/event_manager")
    @Operation(tags={"Event Manager"}, summary="Create new event", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=EventSchemeDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=EventSchemeDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void create(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        EventSchemeDTO dto = new ApiParams(ctx).getBodyAsClass(EventSchemeDTO.class);
        this.eventManager.validateEventSchemeOrThrow(dto);
        EventScheme eventScheme = new EventScheme(dto, user.getClientId());
        eventScheme = this.eventManager.create(eventScheme);
        ctx.json(new EventSchemeDTO(eventScheme));
        this.auditor.audit(ctx, user, VezhaAuditEventAction.EVENT_MANAGER_EVENT_ADDED, "middleware-core", new EventManagerEventAddedAuditMessage(eventScheme));
    }

    @GET
    @Path(value="/api/v2/event_manager/{uuid}")
    @Operation(tags={"Event Manager"}, summary="Get event data by uuid", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="uuid", example="1cc33440-af50-468b-9914-162658226e59", description="Event UUID", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=EventSchemeDTO.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void getData(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String uuid = ctx.pathParamAsClass("uuid", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "uuid"));
        Optional.ofNullable(this.eventManager.get(uuid)).filter(user::hasAccessTo).map(EventSchemeDTO::new).ifPresentOrElse(ctx::json, NotFoundResponse::new);
    }

    @PUT
    @Path(value="/api/v2/event_manager/{uuid}")
    @Operation(tags={"Event Manager"}, summary="Update event data by uuid", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="uuid", example="1cc33440-af50-468b-9914-162658226e59", description="Event UUID", required=true)}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(implementation=EventSchemeDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void update(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String uuid = ctx.pathParamAsClass("uuid", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "uuid"));
        EventSchemeDTO dto = new ApiParams(ctx).getBodyAsClass(EventSchemeDTO.class);
        dto.setId(uuid);
        this.eventManager.validateEventSchemeOrThrow(dto);
        EventScheme eventToUpdate = Optional.ofNullable(this.eventManager.get(uuid)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        EventScheme eventScheme = new EventScheme(dto, eventToUpdate);
        this.eventManager.update(eventScheme);
        ctx.status(204);
        EventManagerEventUpdatedAuditMessage message = new EventManagerEventUpdatedAuditMessage(eventToUpdate, eventScheme);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.EVENT_MANAGER_EVENT_UPDATED, "middleware-core", message);
    }

    @DELETE
    @Path(value="/api/v2/event_manager/{uuid}")
    @Operation(tags={"Event Manager"}, summary="Delete event by uuid", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="uuid", example="1cc33440-af50-468b-9914-162658226e59", description="Event uuid", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteEvent(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String uuid = ctx.pathParamAsClass("uuid", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "uuid"));
        EventScheme eventScheme = Optional.ofNullable(this.eventManager.get(uuid)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        this.eventManager.delete(eventScheme);
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.EVENT_MANAGER_EVENT_DELETED, "middleware-core", new EventManagerEventDeletedAuditMessage(eventScheme));
    }

    @POST
    @Path(value="/api/v2/event_manager/{uuid}/start")
    @Operation(tags={"Event Manager"}, summary="Start event", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="uuid", example="1cc33440-af50-468b-9914-162658226e59", description="Event UUID", required=true)}, requestBody=@RequestBody(required=true, content={@Content(mediaType="application/json", schema=@Schema(type="object", description="Event start params"))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=EventSchemeDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void start(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String uuid = ctx.pathParamAsClass("uuid", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "uuid"));
        EventScheme eventScheme = Optional.ofNullable(this.eventManager.get(uuid)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        EventImpl event = new EventImpl(eventScheme.getUuid(), Collections.emptyMap());
        JSONObject message = JSON.parseObject(ctx.body());
        this.eventManager.send((Event)event, message);
        ctx.status(204);
    }

    @POST
    @Path(value="/api/v2/event_manager/{uuid}/cancel")
    @Operation(tags={"Event Manager"}, summary="Cancel event", parameters={@Parameter(in=ParameterIn.PATH, name="uuid", example="1cc33440-af50-468b-9914-162658226e59", description="Event UUID", required=true)}, security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="204", description="Successful operation "), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden")})
    public void cancel(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        String uuid = ctx.pathParamAsClass("uuid", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "uuid"));
        EventScheme eventScheme = Optional.ofNullable(this.eventManager.get(uuid)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        this.eventManager.cancel(eventScheme.getUuid());
        ctx.status(204);
    }

    @POST
    @Path(value="/api/v2/event_manager/{uuid}/copy")
    @Operation(tags={"Event Manager"}, summary="Copy event", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="uuid", example="1cc33440-af50-468b-9914-162658226e59", description="Event UUID which we wants to copy", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=EventSchemeDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void copy(@Parameter(hidden=true) Context ctx) {
        EventScheme copiedEvent;
        User user = this.usersManager.getUser(ctx);
        String uuid = ctx.pathParamAsClass("uuid", String.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "uuid"));
        EventCopyDTO dto = new ApiParams(ctx).getBodyAsClass(EventCopyDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        try {
            copiedEvent = this.eventManager.copy(uuid, dto.title(), dto.description());
        }
        catch (IllegalArgumentException e2) {
            throw new NotFoundResponse();
        }
        ctx.json(new EventSchemeDTO(copiedEvent));
        this.auditor.audit(ctx, user, VezhaAuditEventAction.EVENT_MANAGER_EVENT_ADDED, "middleware-core", new EventManagerEventAddedAuditMessage(copiedEvent));
    }

    @Inject
    public ApiEventManagerControllerV2(UsersManager usersManager, EventManager eventManager, Auditor auditor, BeanValidator validator) {
        this.usersManager = usersManager;
        this.eventManager = eventManager;
        this.auditor = auditor;
        this.validator = validator;
    }
}

