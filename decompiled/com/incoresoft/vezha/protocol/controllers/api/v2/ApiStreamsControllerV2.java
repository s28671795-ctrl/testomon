/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.protocol.controllers.api.v2;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.Utils.ApiParams;
import com.incoresoft.Utils.Pair;
import com.incoresoft.middleware.analytics.dto.streams.ResolutionDTO;
import com.incoresoft.middleware.analytics.dto.streams.StreamDTO;
import com.incoresoft.middleware.analytics.streams.params.EmptyParams;
import com.incoresoft.middleware.audit.Auditor;
import com.incoresoft.middleware.http.AppRole;
import com.incoresoft.middleware.http.controllers.Controller;
import com.incoresoft.middleware.http.exceptions.ErrorException;
import com.incoresoft.middleware.http.exceptions.ValidationException;
import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.middleware.storage.Storage;
import com.incoresoft.onvif.models.OnvifMediaProfile;
import com.incoresoft.plugin.commons.http.answers.ErrorAnswer;
import com.incoresoft.plugin.commons.http.error.BaseError;
import com.incoresoft.plugin.commons.params.PaginationParams;
import com.incoresoft.plugin.commons.sorting.SortFields;
import com.incoresoft.plugin.commons.sorting.SortOrders;
import com.incoresoft.plugin.commons.sorting.SortParams;
import com.incoresoft.plugin.commons.utils.QueryParamsParser;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import com.incoresoft.vezha.audit.messages.streams.StreamAddedAuditMessage;
import com.incoresoft.vezha.audit.messages.streams.StreamDeletedAuditMessage;
import com.incoresoft.vezha.audit.messages.streams.StreamFrameUpdatedAuditMessage;
import com.incoresoft.vezha.audit.messages.streams.StreamGroupAddedAuditMessage;
import com.incoresoft.vezha.audit.messages.streams.StreamGroupDeletedAuditMessage;
import com.incoresoft.vezha.audit.messages.streams.StreamGroupUpdatedAuditMessage;
import com.incoresoft.vezha.audit.messages.streams.StreamUpdatedAuditMessage;
import com.incoresoft.vezha.dto.onvif.OnvifConnectionDTO;
import com.incoresoft.vezha.dto.onvif.OnvifDTO;
import com.incoresoft.vezha.dto.onvif.OnvifMediaProfileDTO;
import com.incoresoft.vezha.dto.streams.ChangeStreamsLocationDTO;
import com.incoresoft.vezha.dto.streams.FullStreamDTO;
import com.incoresoft.vezha.dto.streams.ListStreamRenameDTO;
import com.incoresoft.vezha.dto.streams.OnvifImportResultElementDTO;
import com.incoresoft.vezha.dto.streams.ShortStreamsPaginationAnswerDTO;
import com.incoresoft.vezha.dto.streams.StreamGroupDTO;
import com.incoresoft.vezha.dto.streams.StreamRenameDTO;
import com.incoresoft.vezha.dto.streams.StreamSearchParams;
import com.incoresoft.vezha.dto.streams.StreamValidationGeneralStepDTO;
import com.incoresoft.vezha.dto.streams.StreamsPaginationAnswerDTO;
import com.incoresoft.vezha.errors.VezhaErrors;
import com.incoresoft.vezha.models.streams.StreamImpl;
import com.incoresoft.vezha.models.streams.StreamManagerImpl;
import com.incoresoft.vezha.models.streams.StreamRenameItem;
import com.incoresoft.vezha.models.streams.groups.StreamGroup;
import com.incoresoft.vezha.models.streams.groups.StreamGroupManager;
import com.incoresoft.vezha.models.streams.model.GroupsAndStreamsResponse;
import com.incoresoft.vezha.models.streams.model.GroupsResponse;
import com.incoresoft.vezha.models.streams.model.StreamRestrictions;
import com.incoresoft.vezha.models.streams.onvif.ImportResult;
import com.incoresoft.vezha.models.streams.onvif.OnvifStream;
import com.incoresoft.vezha.models.streams.onvif.SuccessfullyAddedStream;
import com.incoresoft.vezha.models.users.UserImpl;
import io.javalin.Javalin;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UploadedFile;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(info=@Info(title="Middleware-Core", description="Middleware-Core HTTP API", version="v2"))
public class ApiStreamsControllerV2
implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ApiStreamsControllerV2.class);
    private static final String API_PATH = "/api/v2/streams";
    private static final String STORAGE_FOLDER_PATH = "video-files/";
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(Thread.ofVirtual().factory());
    private final UsersManager usersManager;
    private final StreamManagerImpl streamsManager;
    private final StreamGroupManager streamGroupManager;
    private final StreamRestrictions.Converter streamRestrictionsConverter;
    private final Storage storage;
    private final Auditor auditor;
    private final BeanValidator validator;
    private final QueryParamsParser queryParamsParser;

    @Override
    @Inject
    public void init(Javalin app) {
        app.routes(() -> ApiBuilder.path(API_PATH, () -> {
            ApiBuilder.get(this::getStreams, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewStreams);
            ApiBuilder.get("with_groups", this::getGroupsAndStreams, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ViewStreams);
            ApiBuilder.path("validate", () -> ApiBuilder.post("general", this::validateGeneralStreamSettings, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams));
            ApiBuilder.post(this::addStream, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
            ApiBuilder.delete(this::deleteStreams, AppRole.API_CALL, CorePermissions.ManageStreams);
            ApiBuilder.path("onvif", () -> {
                ApiBuilder.post(this::getOnvifStreams, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
                ApiBuilder.post("import", this::importOnvifStreams, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
            });
            ApiBuilder.path("groups", () -> {
                ApiBuilder.get(this::getGroups, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.post(this::addGroup, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
                ApiBuilder.put(this::updateGroup, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
                ApiBuilder.delete("{id}", this::deleteGroup, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
            });
            ApiBuilder.post("rename", this::renameStreams, AppRole.API_CALL, CorePermissions.ManageStreams);
            ApiBuilder.post("change_location", this::changeLocation, AppRole.API_CALL, CorePermissions.ManageStreams);
            ApiBuilder.path("{id}", () -> {
                ApiBuilder.patch("thumbnail/update", this::updateThumbnail, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
                ApiBuilder.get(this::getStream, AppRole.LOGGED_IN, AppRole.API_CALL);
                ApiBuilder.put(this::editStream, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
                ApiBuilder.delete(this::deleteStream, AppRole.LOGGED_IN, AppRole.API_CALL, CorePermissions.ManageStreams);
            });
        }));
    }

    @GET
    @Path(value="/api/v2/streams")
    @Operation(tags={"Streams"}, summary="Retrieves list of all existing streams", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="short", example="true", schema=@Schema(implementation=Boolean.class), description="Short stream format"), @Parameter(in=ParameterIn.QUERY, name="name", example="Stream 1", schema=@Schema(implementation=String.class), description="Name(or its part) of streams. Resulting response will contain only streams which names contain this parameter(comparison is case-insensitive)"), @Parameter(in=ParameterIn.QUERY, name="type", example="rtsp", schema=@Schema(implementation=StreamType.class), description="Type of a stream. Resulting response will contain only streams of specified type"), @Parameter(in=ParameterIn.QUERY, name="offset", example="0", schema=@Schema(implementation=String.class), description="Number of streams to skip. Streams sorted by its id in descending order"), @Parameter(in=ParameterIn.QUERY, name="limit", example="20", schema=@Schema(implementation=String.class), description="Number of streams to return. Streams sorted by its id in descending order"), @Parameter(in=ParameterIn.QUERY, name="show_deleted", schema=@Schema(implementation=Boolean.class), example="true", description="Should archived streams be returned in the result of request. \"true\" - archived streams will be returned, \"false\" - archived streams will not be returned."), @Parameter(in=ParameterIn.QUERY, name="uuids", array=@ArraySchema(schema=@Schema(implementation=UUID.class)), description="Array of stream uuids", example="[\"8f5be21d-3690-47de-a392-9963a5f39941\",\"21df0114-1f9f-4542-88a0-22e4d315e3df\"]"), @Parameter(in=ParameterIn.QUERY, name="sort_order", example="asc", description="Sort order", schema=@Schema(implementation=SortOrders.class)), @Parameter(in=ParameterIn.QUERY, name="sort_field", example="name", description="Sort Field", schema=@Schema(implementation=SortFields.class))}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of streams"), schema=@Schema(implementation=StreamsPaginationAnswerDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getStreams(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        StreamSearchParams streamSearchParams = this.queryParamsParser.from(ctx, StreamSearchParams.class);
        PaginationParams paginationParams = this.queryParamsParser.from(ctx, PaginationParams.class);
        SortParams sortParams = this.queryParamsParser.from(ctx, SortParams.class);
        boolean shortDto = ctx.queryParamAsClass("short", Boolean.class).getOrDefault(false);
        Pair<Long, List<StreamImpl>> data = this.streamsManager.getAll(user, streamSearchParams, paginationParams, sortParams);
        if (shortDto) {
            List<StreamDTO> result = ((List)data.second).stream().map(StreamDTO::new).toList();
            ctx.json(new ShortStreamsPaginationAnswerDTO(result, (long)((Long)data.first), (int)paginationParams.getLimit().orElse(Integer.MAX_VALUE)));
        } else {
            List<FullStreamDTO> result = ((List)data.second).stream().map(FullStreamDTO::new).toList();
            ctx.json(new StreamsPaginationAnswerDTO(result, (long)((Long)data.first), (int)paginationParams.getLimit().orElse(Integer.MAX_VALUE)));
        }
    }

    @GET
    @Path(value="/api/v2/streams/{id}")
    @Operation(tags={"Streams"}, summary="Retrieves stream with specified id.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Stream ID", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=FullStreamDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getStream(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int streamId = ctx.pathParamAsClass("id", Integer.class).get();
        FullStreamDTO dto = Optional.ofNullable(this.streamsManager.get(streamId)).filter(user::hasAccessTo).map(FullStreamDTO::new).orElseThrow(NotFoundResponse::new);
        ctx.json(dto);
    }

    @POST
    @Path(value="/api/v2/streams/validate/general")
    @Operation(tags={"Streams"}, summary="Validate general stream settings", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=StreamValidationGeneralStepDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void validateGeneralStreamSettings(@Parameter(hidden=true) Context ctx) {
        StreamValidationGeneralStepDTO streamDTO = new ApiParams(ctx).getBodyAsClass(StreamValidationGeneralStepDTO.class);
        this.validator.validateObjectOrThrow(streamDTO, new Class[0]);
        ctx.status(204);
    }

    @POST
    @Path(value="/api/v2/streams")
    @Operation(tags={"Streams"}, summary="Add new stream", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="multipart/form-data", encoding={@Encoding(name="resolution", contentType="application/json"), @Encoding(name="location", contentType="application/json"), @Encoding(name="access_restrictions", contentType="application/json"), @Encoding(name="params", contentType="application/json"), @Encoding(name="file", contentType="application/octet-stream")}, schema=@Schema(implementation=FullStreamDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=FullStreamDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void addStream(@Parameter(hidden=true) Context ctx) {
        FullStreamDTO createSteamDTO;
        User user = this.usersManager.getUser(ctx);
        Stream stream = this.addOrUpdateStream(user, ctx, createSteamDTO = new ApiParams(ctx).getFormParamsAsClass(FullStreamDTO.class), null);
        boolean tryToGrabFrame = stream.getResolution() == null || stream.getResolution().getHeight() == 0 || stream.getResolution().getWidth() == 0;
        Stream newStream = this.streamsManager.addStream(stream, tryToGrabFrame);
        ctx.json(new FullStreamDTO(newStream));
        this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_ADDED, "middleware-core", new StreamAddedAuditMessage(newStream));
        this.scheduler.schedule(() -> {
            this.streamsManager.updateStream(newStream);
            log.info("Grabbed stream {}", (Object)newStream.getId());
        }, 10L, TimeUnit.SECONDS);
    }

    @PUT
    @Path(value="/api/v2/streams/{id}")
    @Operation(tags={"Streams"}, summary="Update existing stream", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Stream ID", required=true)}, requestBody=@RequestBody(content={@Content(mediaType="multipart/form-data", encoding={@Encoding(name="resolution", contentType="application/json"), @Encoding(name="location", contentType="application/json"), @Encoding(name="access_restrictions", contentType="application/json"), @Encoding(name="params", contentType="application/json"), @Encoding(name="file", contentType="application/octet-stream")}, schema=@Schema(implementation=FullStreamDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=FullStreamDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void editStream(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int streamId = ctx.pathParamAsClass("id", Integer.class).getOrThrow(ignored -> new BadRequestResponse("Stream id is required"));
        Stream old = Optional.ofNullable(this.streamsManager.get(streamId)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        if (!old.getRestrictions().isAllowed(user, CorePermissions.ManageStreams)) {
            throw new ForbiddenResponse();
        }
        FullStreamDTO updateSteamDTO = new ApiParams(ctx).getFormParamsAsClass(FullStreamDTO.class);
        updateSteamDTO.setId(streamId);
        Stream updated = this.streamsManager.updateStream(this.addOrUpdateStream(user, ctx, updateSteamDTO, old));
        ctx.json(new FullStreamDTO(updated));
        this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_UPDATED, "middleware-core", new StreamUpdatedAuditMessage(old, updated));
    }

    @DELETE
    @Path(value="/api/v2/streams/{id}")
    @Operation(tags={"Streams"}, summary="Delete stream", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Stream ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteStream(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int streamId = ctx.pathParamAsClass("id", Integer.class).getOrDefault(0);
        Stream stream = Optional.ofNullable(this.streamsManager.get(streamId)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        if (!stream.getRestrictions().isAllowed(user, CorePermissions.ManageStreams)) {
            throw new ForbiddenResponse();
        }
        this.streamsManager.delete(stream);
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_DELETED, "middleware-core", new StreamDeletedAuditMessage(stream));
    }

    @DELETE
    @Path(value="/api/v2/streams")
    @Operation(tags={"Streams"}, summary="Delete streams", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="uuids", description="Array of stream UUIDs", schema=@Schema(type="object", example="[\"bc3a5796-cebc-426a-a4d7-2013a4d9b060\",\"823aa895-39bb-4bd5-89c1-2e44bb9abd1a\"]"))}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteStreams(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        HashSet<UUID> uuids = new HashSet<UUID>(JSON.parseArray(ctx.queryParamAsClass("uuids", String.class).getOrDefault("[]"), UUID.class));
        List<Stream> streams = uuids.stream().map(this.streamsManager::get).filter(it -> Objects.nonNull(it) && user.hasAccessTo((ClientId)it)).toList();
        boolean allAllowed = streams.stream().allMatch(it -> it.getRestrictions().isAllowed(user, CorePermissions.ManageStreams));
        if (!allAllowed) {
            throw new ForbiddenResponse();
        }
        this.streamsManager.deleteAllByUuids(uuids);
        for (Stream stream : streams) {
            this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_DELETED, "middleware-core", new StreamDeletedAuditMessage(stream));
        }
        ctx.status(204);
    }

    @PATCH
    @Path(value="/api/v2/streams/{id}/thumbnail/update")
    @Operation(tags={"Streams"}, summary="Update stream frame, codec and params.", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="123", description="Stream ID", required=true)}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", schema=@Schema(implementation=FullStreamDTO.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateThumbnail(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        int streamId = ctx.pathParamAsClass("id", Integer.class).getOrThrow(e -> new ErrorException(BaseError.RequiredParams, "id"));
        Stream stream = Optional.ofNullable(this.streamsManager.get(streamId)).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        if (!stream.getRestrictions().isAllowed(user, CorePermissions.ManageStreams)) {
            throw new ForbiddenResponse();
        }
        try {
            this.streamsManager.updateStreamFrame(stream);
        }
        catch (Exception e2) {
            log.error("Couldn't grab frame from stream", e2);
            throw new ErrorException(VezhaErrors.STREAM_FRAME_GRAB_ERROR, e2.getMessage());
        }
        ctx.json(new FullStreamDTO(stream));
        this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_FRAME_UPDATED, "middleware-core", new StreamFrameUpdatedAuditMessage(stream));
    }

    @POST
    @Path(value="/api/v2/streams/onvif")
    @Operation(tags={"Onvif"}, summary="Get ONVIF streams list", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=OnvifConnectionDTO.class))}), responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of ONVIF media profiles"), schema=@Schema(implementation=OnvifMediaProfileDTO.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getOnvifStreams(@Parameter(hidden=true) Context ctx) {
        OnvifConnectionDTO dto = new ApiParams(ctx).getBodyAsClass(OnvifConnectionDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        List<OnvifMediaProfile> mediaProfiles = this.streamsManager.getOnvifStreams(dto.host(), dto.onvifPort(), dto.rtspPort(), dto.user(), dto.password());
        ctx.json(mediaProfiles.stream().map(OnvifMediaProfileDTO::new).toList());
    }

    @POST
    @Path(value="/api/v2/streams/onvif/import")
    @Operation(tags={"Onvif"}, summary="Import ONVIF streams list", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=OnvifDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void importOnvifStreams(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        OnvifDTO onvifDTO = new ApiParams(ctx).getBodyAsClass(OnvifDTO.class);
        this.validator.validateObjectOrThrow(onvifDTO, new Class[0]);
        boolean someTokensAreUnknown = onvifDTO.streams().stream().map(it -> this.streamsManager.getOnvifStreams().get(it.token())).anyMatch(Objects::isNull);
        if (someTokensAreUnknown) {
            throw new ErrorException(VezhaErrors.ONVIF_TOKEN_WAS_NULL, new Object[0]);
        }
        ImportResult result = this.streamsManager.importOnvifStreams(user, onvifDTO);
        ArrayList<OnvifImportResultElementDTO> results = new ArrayList<OnvifImportResultElementDTO>();
        for (SuccessfullyAddedStream added : result.added()) {
            results.add(new OnvifImportResultElementDTO(added));
        }
        for (OnvifStream failed : result.failed()) {
            results.add(new OnvifImportResultElementDTO(failed));
        }
        ctx.json(results);
        for (SuccessfullyAddedStream added : result.added()) {
            this.auditor.audit(ctx, user, VezhaAuditEventAction.ONVIF_VIDEOSTREAM_IMPORTED, "middleware-core", new StreamAddedAuditMessage(added.stream()));
        }
    }

    @GET
    @Path(value="/api/v2/streams/with_groups")
    @Operation(tags={"Streams"}, summary="Retrieves list of all existing streams with groups", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.QUERY, name="name", example="Stream 1", schema=@Schema(implementation=String.class), description="Name(or its part) of streams. Resulting response will contain only streams which names contain this parameter(comparison is case-insensitive)"), @Parameter(in=ParameterIn.QUERY, name="type", example="rtsp", schema=@Schema(implementation=StreamType.class), description="Type of a stream. Resulting response will contain only streams of specified type"), @Parameter(in=ParameterIn.QUERY, name="offset", example="0", schema=@Schema(implementation=String.class), description="Number of streams to skip. Streams sorted by its id in descending order"), @Parameter(in=ParameterIn.QUERY, name="limit", example="20", schema=@Schema(implementation=String.class), description="Number of streams to return. Streams sorted by its id in descending order"), @Parameter(in=ParameterIn.QUERY, name="show_deleted", schema=@Schema(implementation=Boolean.class), example="true", description="Should archived streams be returned in the result of request. \"true\" - archived streams will be returned, \"false\" - archived streams will not be returned."), @Parameter(in=ParameterIn.QUERY, name="uuids", example="20", array=@ArraySchema(schema=@Schema(implementation=UUID.class)), description="Array of stream uuids"), @Parameter(in=ParameterIn.QUERY, name="sort_order", example="asc", description="Sort order", schema=@Schema(implementation=SortOrders.class)), @Parameter(in=ParameterIn.QUERY, name="sort_field", example="name", description="Sort Field", schema=@Schema(implementation=SortFields.class))}, responses={@ApiResponse(responseCode="200", description="Successful operation", content={@Content(mediaType="application/json", schema=@Schema(implementation=GroupsAndStreamsResponse.class))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getGroupsAndStreams(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        StreamSearchParams streamSearchParams = this.queryParamsParser.from(ctx, StreamSearchParams.class);
        PaginationParams paginationParams = this.queryParamsParser.from(ctx, PaginationParams.class);
        SortParams sortParams = this.queryParamsParser.from(ctx, SortParams.class);
        ctx.json(this.streamGroupManager.getStreamGroupResponse(user, streamSearchParams, paginationParams, sortParams));
    }

    @GET
    @Path(value="/api/v2/streams/groups")
    @Operation(tags={"Streams groups"}, summary="Get list of all existing stream groups", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, responses={@ApiResponse(responseCode="200", description="Successful operation ", content={@Content(mediaType="application/json", array=@ArraySchema(arraySchema=@Schema(description="List of users"), schema=@Schema(implementation=GroupsResponse.class)))}), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void getGroups(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        ctx.json(this.streamGroupManager.getGroupsResponse(user));
    }

    @POST
    @Path(value="/api/v2/streams/groups")
    @Operation(tags={"Streams groups"}, summary="Add new stream group", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=StreamGroupDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void addGroup(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        StreamGroupDTO dto = new ApiParams(ctx).getBodyAsClass(StreamGroupDTO.class);
        dto.setId(-2);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        StreamGroup added = this.streamGroupManager.add(dto.toGroup(user.getClientId()));
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_GROUP_ADDED, "middleware-core", new StreamGroupAddedAuditMessage(added));
    }

    @PUT
    @Path(value="/api/v2/streams/groups")
    @Operation(tags={"Streams groups"}, summary="Update stream group", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=StreamGroupDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void updateGroup(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        StreamGroupDTO dto = new ApiParams(ctx).getBodyAsClass(StreamGroupDTO.class);
        this.validator.validateObjectOrThrow(dto, new Class[0]);
        StreamGroup old = Optional.of(dto.getId()).map(this.streamGroupManager::get).filter(user::hasAccessTo).orElseThrow(NotFoundResponse::new);
        if (!user.hasAccessTo(old)) {
            throw new NotFoundResponse();
        }
        StreamGroup updated = this.streamGroupManager.update(dto.toGroup(old.getClientId()));
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_GROUP_UPDATED, "middleware-core", new StreamGroupUpdatedAuditMessage(old, updated));
    }

    @DELETE
    @Path(value="/api/v2/streams/groups/{id}")
    @Operation(tags={"Streams groups"}, summary="Delete stream group", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, parameters={@Parameter(in=ParameterIn.PATH, name="id", example="1", description="Group ID", required=true)}, responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void deleteGroup(@Parameter(hidden=true) Context ctx) {
        UserImpl user = (UserImpl)this.usersManager.getUser(ctx);
        int id = ctx.pathParamAsClass("id", Integer.class).getOrThrow(it -> new ErrorException(BaseError.RequiredParams, "id"));
        StreamGroup group = this.streamGroupManager.get(id);
        if (group == null || !user.ownsItem(group.getClientId())) {
            throw new NotFoundResponse();
        }
        this.streamGroupManager.delete(id);
        ctx.status(204);
        this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_GROUP_DELETED, "middleware-core", new StreamGroupDeletedAuditMessage(group));
    }

    @POST
    @Path(value="/api/v2/streams/rename")
    @Operation(tags={"Streams"}, summary="Rename list of streams", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=ListStreamRenameDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void renameStreams(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        ListStreamRenameDTO streamsToRenameDTO = new ApiParams(ctx).getBodyAsClass(ListStreamRenameDTO.class);
        this.validator.validateObjectOrThrow(streamsToRenameDTO, new Class[0]);
        Map<UUID, String> streamsRenameMap = streamsToRenameDTO.streams().stream().collect(Collectors.toMap(StreamRenameDTO::uuid, StreamRenameDTO::newName));
        Map streamMap = this.streamsManager.getAll().stream().filter(it -> streamsRenameMap.containsKey(it.getUUID()) && user.hasAccessTo((ClientId)it)).collect(Collectors.toMap(Stream::getId, Function.identity()));
        if (!streamMap.values().stream().allMatch(it -> it.getRestrictions().isAllowed(user, CorePermissions.ManageStreams))) {
            throw new ForbiddenResponse();
        }
        List<StreamRenameItem> streamsToRename = streamMap.values().stream().map(it -> new StreamRenameItem((Stream)it, (String)streamsRenameMap.get(it.getUUID()))).toList();
        List<Stream> renamed = this.streamsManager.renameStreams(streamsToRename);
        ctx.status(204);
        for (Stream renamedStream : renamed) {
            StreamUpdatedAuditMessage message = new StreamUpdatedAuditMessage((Stream)streamMap.get(renamedStream.getId()), renamedStream);
            this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_UPDATED, "middleware-core", message);
        }
    }

    @POST
    @Path(value="/api/v2/streams/change_location")
    @Operation(tags={"Streams"}, summary="Change streams location", security={@SecurityRequirement(name="bearerAuth"), @SecurityRequirement(name="cookieAuth")}, requestBody=@RequestBody(content={@Content(mediaType="application/json", schema=@Schema(implementation=ChangeStreamsLocationDTO.class))}), responses={@ApiResponse(responseCode="204", description="Successful operation"), @ApiResponse(responseCode="400", description="Invalid value", content={@Content(mediaType="application/json", schema=@Schema(implementation=ErrorAnswer.class))}), @ApiResponse(responseCode="401", description="Unauthorized"), @ApiResponse(responseCode="403", description="Forbidden"), @ApiResponse(responseCode="404", description="Not Found")})
    public void changeLocation(@Parameter(hidden=true) Context ctx) {
        User user = this.usersManager.getUser(ctx);
        ChangeStreamsLocationDTO changeStreamsLocationDTO = new ApiParams(ctx).getBodyAsClass(ChangeStreamsLocationDTO.class);
        this.validator.validateObjectOrThrow(changeStreamsLocationDTO, new Class[0]);
        Map streamMap = this.streamsManager.getAll().stream().filter(it -> changeStreamsLocationDTO.uuids().contains(it.getUUID()) && user.hasAccessTo((ClientId)it)).collect(Collectors.toMap(Stream::getId, Function.identity()));
        if (!streamMap.values().stream().allMatch(it -> it.getRestrictions().isAllowed(user, CorePermissions.ManageStreams))) {
            throw new ForbiddenResponse();
        }
        Set<UUID> uuids = streamMap.values().stream().map(Stream::getUUID).collect(Collectors.toSet());
        List<Stream> updatedStreams = this.streamsManager.changeLocation(uuids, changeStreamsLocationDTO.location());
        ctx.status(204);
        for (Stream updated : updatedStreams) {
            StreamUpdatedAuditMessage message = new StreamUpdatedAuditMessage((Stream)streamMap.get(updated.getId()), updated);
            this.auditor.audit(ctx, user, VezhaAuditEventAction.VIDEOSTREAM_UPDATED, "middleware-core", message);
        }
    }

    private Stream addOrUpdateStream(User user, Context ctx, FullStreamDTO streamDTO, Stream existingStream) {
        if (streamDTO.getId() == null) {
            streamDTO.setClientId(user.getClientId());
        } else if (existingStream != null) {
            streamDTO.setClientId(existingStream.getClientId());
        } else {
            streamDTO.setClientId(user.getClientId());
        }
        if (existingStream != null && existingStream.getType() == StreamType.embedded) {
            streamDTO = streamDTO.withParams(new EmptyParams(StreamType.embedded));
            streamDTO = streamDTO.withResolution(new ResolutionDTO(existingStream.getResolution()));
        }
        this.validator.validateObjectOrThrow(streamDTO, new Class[0]);
        UploadedFile uploadedVideo = ctx.uploadedFile("file");
        if (existingStream == null && streamDTO.getParams().getType() == StreamType.file && uploadedVideo == null) {
            throw new ValidationException(BaseError.FileUploadError, "file", new Object[0]);
        }
        StreamRestrictions restrictions = this.streamRestrictionsConverter.convert(user, streamDTO.getRestrictions());
        StreamImpl stream = new StreamImpl(streamDTO, restrictions);
        if (uploadedVideo != null && streamDTO.getParams().getType() == StreamType.file) {
            String filename = uploadedVideo.filename().split("\\.")[0];
            String filePath = STORAGE_FOLDER_PATH + uploadedVideo.filename().replace(filename, UUID.randomUUID().toString());
            this.storage.save(filePath, uploadedVideo.content());
            stream.setUri(URI.create(filePath));
        }
        return stream;
    }

    @Inject
    public ApiStreamsControllerV2(UsersManager usersManager, StreamManagerImpl streamsManager, StreamGroupManager streamGroupManager, StreamRestrictions.Converter streamRestrictionsConverter, Storage storage, Auditor auditor, BeanValidator validator, QueryParamsParser queryParamsParser) {
        this.usersManager = usersManager;
        this.streamsManager = streamsManager;
        this.streamGroupManager = streamGroupManager;
        this.streamRestrictionsConverter = streamRestrictionsConverter;
        this.storage = storage;
        this.auditor = auditor;
        this.validator = validator;
        this.queryParamsParser = queryParamsParser;
    }
}

