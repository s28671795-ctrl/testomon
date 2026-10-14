/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.analytics.dto.streams.ResolutionDTO;
import com.incoresoft.middleware.analytics.serialization.StreamAuthSerializer;
import com.incoresoft.middleware.analytics.serialization.StreamParamsSerializer;
import com.incoresoft.middleware.analytics.streams.authorization.BasicAuth;
import com.incoresoft.middleware.model.Coordinates;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamAuth;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamStatus;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.vezha.dto.streams.validations.StreamNameMustBeUnique;
import com.incoresoft.vezha.dto.streams.validations.StreamPathMustExistForMilestoneRtsp;
import com.incoresoft.vezha.dto.streams.validations.StreamUrlMustBeUnique;
import com.incoresoft.vezha.dto.streams.validations.TimezoneIsNotValid;
import com.incoresoft.vezha.dto.streams.validations.host.NotBlank;
import com.incoresoft.vezha.dto.streams.validations.port.NotNull;
import com.incoresoft.vezha.models.streams.StreamURIParser;
import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.apache.commons.lang3.time.DurationFormatUtils;

@StreamUrlMustBeUnique
@StreamNameMustBeUnique
@StreamPathMustExistForMilestoneRtsp
@NotBlank
@NotNull
@Schema(name="FullStreamDTO", description="Video stream class structure")
public final class FullStreamDTO
implements StreamUrlMustBeUnique.Validatable,
StreamNameMustBeUnique.Validatable,
NotBlank.Validatable,
NotNull.Validatable,
StreamPathMustExistForMilestoneRtsp.Validatable {
    @Schema(description="Video stream Id", example="1")
    private Integer id;
    @JSONField(name="uuid")
    @Schema(name="uuid", description="Video stream UUID", example="1")
    private final UUID uuid;
    @JSONField(name="parent_id")
    @Schema(name="parent_id", description="Parent folder Id", example="1")
    private final int parentId;
    @JSONField(name="file_name")
    @Schema(name="file_name", description="Image file name (preview)", example="vUBi6rUge1.jpg")
    private final String image;
    @JSONField(name="codec")
    @Schema(name="codec", description="Codec type", example="H.264", implementation=Codec.class)
    private final String codec;
    @JSONField(name="status")
    @Schema(name="status", description="Stream status", example="online")
    private final StreamStatus status;
    @Schema(name="direction", description="Camera direction angle on the map", example="10", implementation=Integer.class)
    private final int direction;
    @Schema(name="host", description="Stream host", example="demo_host.com", implementation=String.class)
    private final String host;
    @Schema(name="port", description="Stream port", example="554", implementation=Integer.class)
    @Min(value=0L)
    @Max(value=65535L)
    private final @Min(value=0L) @Max(value=65535L) Integer port;
    @Schema(name="path", description="Stream path", example="/lpr_path", implementation=String.class)
    private final String path;
    @JSONField(name="full_path", deserialize=false)
    @Schema(name="full_path", description="Stream full path / url", example="rtsp://demo_host.com:554/lpr_path", implementation=String.class)
    private final String fullPath;
    @Schema(name="name", description="Video stream name", example="Main stream", implementation=String.class)
    @javax.validation.constraints.NotBlank
    private final String name;
    @Schema(name="address", description="Stream address", example="Some street 45", implementation=String.class)
    private final String address;
    @JSONField(name="access_restrictions")
    @Schema(name="access_restrictions", description="Stream restrictions", implementation=ItemRestrictionsDTO.class)
    private final ItemRestrictionsDTO restrictions;
    @JSONField(name="current_user_restrictions", deserialize=false)
    @Schema(name="current_user_restrictions", description="Restrictions info for current user", nullable=true)
    private final Map<String, Boolean> currentUserRestrictions;
    @JSONField(name="auth", deserializeUsing=StreamAuthSerializer.class)
    @Schema(name="auth", description="Authorization params", example="{\"type\": \"no_auth\"}", implementation=StreamAuth.class)
    @javax.validation.constraints.NotNull
    @Valid
    private final StreamAuth auth;
    @JSONField(name="params", deserializeUsing=StreamParamsSerializer.class)
    @Schema(name="params", description="Additional params", example="{\"type\": \"rtsp\"}", implementation=StreamParams.class)
    @javax.validation.constraints.NotNull
    @Valid
    private final StreamParams params;
    @Schema(name="timezone", description="Timezone", implementation=String.class, example="Europe/Kiev")
    @javax.validation.constraints.NotBlank
    @TimezoneIsNotValid
    private final String timezone;
    @Schema(name="location", description="Camera location (coordinates)", implementation=CoordinatesDTO.class)
    private final CoordinatesDTO location;
    @Schema(name="resolution", description="resolution", implementation=ResolutionDTO.class)
    private final ResolutionDTO resolution;
    @JSONField(name="duration", deserialize=false)
    @Schema(name="duration", description="Duration of file based stream in HH:mm:ss.SSS. format.", example="01:32:43.587")
    private final String duration;
    @JSONField(name="client_id", serialize=false, deserialize=false)
    @Schema(hidden=true)
    private Integer clientId;
    @Schema(name="file", description="Uploaded file", type="string", format="binary")
    @JSONField(deserialize=false, serialize=false)
    private final String uploadedFile;

    public FullStreamDTO(Stream stream) {
        this(null, stream);
    }

    public FullStreamDTO(User user, Stream stream) {
        String[] userInfo;
        this.id = stream.getId();
        this.uuid = stream.getUUID();
        this.parentId = stream.getFolderId();
        this.image = stream.getImage();
        this.codec = stream.getCodec() != null ? stream.getCodec().getDisplayedName() : "";
        this.status = stream.getStatus();
        this.direction = stream.getDirection();
        Optional<URI> uri = Optional.ofNullable(stream.getUri());
        this.host = uri.map(URI::getHost).orElse(null);
        this.port = uri.map(URI::getPort).orElse(0);
        Object path = uri.map(URI::getPath).orElse(null);
        if (uri.isPresent() && stream.getUri().getQuery() != null && !stream.getUri().getQuery().isBlank()) {
            path = (String)path + "?" + stream.getUri().getQuery();
        }
        this.path = path;
        this.fullPath = uri.map(URI::toString).orElse(null);
        this.name = stream.getName();
        this.address = stream.getAddress();
        this.restrictions = stream.getRestrictions() != null ? new ItemRestrictionsDTO(stream.getRestrictions()) : new ItemRestrictionsDTO(1, null, null, null);
        this.currentUserRestrictions = user == null ? null : stream.getRestrictions().eligiblePermissions().stream().collect(Collectors.toUnmodifiableMap(Permission::getId, it -> stream.getRestrictions().isAllowed(user, (Permission)it)));
        StreamAuth auth = stream.getAuth();
        if (uri.isPresent() && stream.getUri().getUserInfo() != null && (userInfo = stream.getUri().getUserInfo().split(":")).length == 2) {
            auth = new BasicAuth(userInfo[0], userInfo[1]);
        }
        this.auth = auth;
        this.params = stream.getParams();
        this.clientId = stream.getClientId();
        this.location = Objects.isNull(stream.getLocation()) ? new CoordinatesDTO(new Coordinates(0.0, 0.0)) : new CoordinatesDTO(stream.getLocation());
        this.resolution = Objects.isNull(stream.getResolution()) ? new ResolutionDTO(new Resolution(0, 0)) : new ResolutionDTO(stream.getResolution());
        this.timezone = stream.getTimezone() != null ? stream.getTimezone().getId() : ZoneId.systemDefault().getId();
        this.duration = stream.getDuration() != null ? DurationFormatUtils.formatDuration(stream.getDuration().toMillis(), "HH:mm:ss.SSS") : null;
        this.uploadedFile = null;
    }

    @Override
    @JSONField(serialize=false)
    public URI getURI() {
        return StreamURIParser.parse(this.host, this.port, this.path, this.auth, this.params);
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public static FullStreamDTOBuilder builder() {
        return new FullStreamDTOBuilder();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getParentId() {
        return this.parentId;
    }

    public String getImage() {
        return this.image;
    }

    public String getCodec() {
        return this.codec;
    }

    public StreamStatus getStatus() {
        return this.status;
    }

    public int getDirection() {
        return this.direction;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public Integer getPort() {
        return this.port;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    public String getFullPath() {
        return this.fullPath;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public ItemRestrictionsDTO getRestrictions() {
        return this.restrictions;
    }

    public Map<String, Boolean> getCurrentUserRestrictions() {
        return this.currentUserRestrictions;
    }

    public StreamAuth getAuth() {
        return this.auth;
    }

    @Override
    public StreamParams getParams() {
        return this.params;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public CoordinatesDTO getLocation() {
        return this.location;
    }

    public ResolutionDTO getResolution() {
        return this.resolution;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getUploadedFile() {
        return this.uploadedFile;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FullStreamDTO)) {
            return false;
        }
        FullStreamDTO other = (FullStreamDTO)o;
        if (this.getParentId() != other.getParentId()) {
            return false;
        }
        if (this.getDirection() != other.getDirection()) {
            return false;
        }
        Integer this$id = this.getId();
        Integer other$id = other.getId();
        if (this$id == null ? other$id != null : !((Object)this$id).equals(other$id)) {
            return false;
        }
        Integer this$port = this.getPort();
        Integer other$port = other.getPort();
        if (this$port == null ? other$port != null : !((Object)this$port).equals(other$port)) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        UUID this$uuid = this.getUuid();
        UUID other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !((Object)this$uuid).equals(other$uuid)) {
            return false;
        }
        String this$image = this.getImage();
        String other$image = other.getImage();
        if (this$image == null ? other$image != null : !this$image.equals(other$image)) {
            return false;
        }
        String this$codec = this.getCodec();
        String other$codec = other.getCodec();
        if (this$codec == null ? other$codec != null : !this$codec.equals(other$codec)) {
            return false;
        }
        StreamStatus this$status = this.getStatus();
        StreamStatus other$status = other.getStatus();
        if (this$status == null ? other$status != null : !((Object)((Object)this$status)).equals((Object)other$status)) {
            return false;
        }
        String this$host = this.getHost();
        String other$host = other.getHost();
        if (this$host == null ? other$host != null : !this$host.equals(other$host)) {
            return false;
        }
        String this$path = this.getPath();
        String other$path = other.getPath();
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) {
            return false;
        }
        String this$fullPath = this.getFullPath();
        String other$fullPath = other.getFullPath();
        if (this$fullPath == null ? other$fullPath != null : !this$fullPath.equals(other$fullPath)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$address = this.getAddress();
        String other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) {
            return false;
        }
        ItemRestrictionsDTO this$restrictions = this.getRestrictions();
        ItemRestrictionsDTO other$restrictions = other.getRestrictions();
        if (this$restrictions == null ? other$restrictions != null : !((Object)this$restrictions).equals(other$restrictions)) {
            return false;
        }
        Map<String, Boolean> this$currentUserRestrictions = this.getCurrentUserRestrictions();
        Map<String, Boolean> other$currentUserRestrictions = other.getCurrentUserRestrictions();
        if (this$currentUserRestrictions == null ? other$currentUserRestrictions != null : !((Object)this$currentUserRestrictions).equals(other$currentUserRestrictions)) {
            return false;
        }
        StreamAuth this$auth = this.getAuth();
        StreamAuth other$auth = other.getAuth();
        if (this$auth == null ? other$auth != null : !this$auth.equals(other$auth)) {
            return false;
        }
        StreamParams this$params = this.getParams();
        StreamParams other$params = other.getParams();
        if (this$params == null ? other$params != null : !this$params.equals(other$params)) {
            return false;
        }
        String this$timezone = this.getTimezone();
        String other$timezone = other.getTimezone();
        if (this$timezone == null ? other$timezone != null : !this$timezone.equals(other$timezone)) {
            return false;
        }
        CoordinatesDTO this$location = this.getLocation();
        CoordinatesDTO other$location = other.getLocation();
        if (this$location == null ? other$location != null : !((Object)this$location).equals(other$location)) {
            return false;
        }
        ResolutionDTO this$resolution = this.getResolution();
        ResolutionDTO other$resolution = other.getResolution();
        if (this$resolution == null ? other$resolution != null : !((Object)this$resolution).equals(other$resolution)) {
            return false;
        }
        String this$duration = this.getDuration();
        String other$duration = other.getDuration();
        if (this$duration == null ? other$duration != null : !this$duration.equals(other$duration)) {
            return false;
        }
        String this$uploadedFile = this.getUploadedFile();
        String other$uploadedFile = other.getUploadedFile();
        return !(this$uploadedFile == null ? other$uploadedFile != null : !this$uploadedFile.equals(other$uploadedFile));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getParentId();
        result = result * 59 + this.getDirection();
        Integer $id = this.getId();
        result = result * 59 + ($id == null ? 43 : ((Object)$id).hashCode());
        Integer $port = this.getPort();
        result = result * 59 + ($port == null ? 43 : ((Object)$port).hashCode());
        result = result * 59 + this.getClientId();
        UUID $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : ((Object)$uuid).hashCode());
        String $image = this.getImage();
        result = result * 59 + ($image == null ? 43 : $image.hashCode());
        String $codec = this.getCodec();
        result = result * 59 + ($codec == null ? 43 : $codec.hashCode());
        StreamStatus $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)((Object)$status)).hashCode());
        String $host = this.getHost();
        result = result * 59 + ($host == null ? 43 : $host.hashCode());
        String $path = this.getPath();
        result = result * 59 + ($path == null ? 43 : $path.hashCode());
        String $fullPath = this.getFullPath();
        result = result * 59 + ($fullPath == null ? 43 : $fullPath.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $address = this.getAddress();
        result = result * 59 + ($address == null ? 43 : $address.hashCode());
        ItemRestrictionsDTO $restrictions = this.getRestrictions();
        result = result * 59 + ($restrictions == null ? 43 : ((Object)$restrictions).hashCode());
        Map<String, Boolean> $currentUserRestrictions = this.getCurrentUserRestrictions();
        result = result * 59 + ($currentUserRestrictions == null ? 43 : ((Object)$currentUserRestrictions).hashCode());
        StreamAuth $auth = this.getAuth();
        result = result * 59 + ($auth == null ? 43 : $auth.hashCode());
        StreamParams $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : $params.hashCode());
        String $timezone = this.getTimezone();
        result = result * 59 + ($timezone == null ? 43 : $timezone.hashCode());
        CoordinatesDTO $location = this.getLocation();
        result = result * 59 + ($location == null ? 43 : ((Object)$location).hashCode());
        ResolutionDTO $resolution = this.getResolution();
        result = result * 59 + ($resolution == null ? 43 : ((Object)$resolution).hashCode());
        String $duration = this.getDuration();
        result = result * 59 + ($duration == null ? 43 : $duration.hashCode());
        String $uploadedFile = this.getUploadedFile();
        result = result * 59 + ($uploadedFile == null ? 43 : $uploadedFile.hashCode());
        return result;
    }

    public String toString() {
        return "FullStreamDTO(id=" + this.getId() + ", uuid=" + String.valueOf(this.getUuid()) + ", parentId=" + this.getParentId() + ", image=" + this.getImage() + ", codec=" + this.getCodec() + ", status=" + String.valueOf((Object)this.getStatus()) + ", direction=" + this.getDirection() + ", host=" + this.getHost() + ", port=" + this.getPort() + ", path=" + this.getPath() + ", fullPath=" + this.getFullPath() + ", name=" + this.getName() + ", address=" + this.getAddress() + ", restrictions=" + String.valueOf(this.getRestrictions()) + ", currentUserRestrictions=" + String.valueOf(this.getCurrentUserRestrictions()) + ", auth=" + String.valueOf(this.getAuth()) + ", params=" + String.valueOf(this.getParams()) + ", timezone=" + this.getTimezone() + ", location=" + String.valueOf(this.getLocation()) + ", resolution=" + String.valueOf(this.getResolution()) + ", duration=" + this.getDuration() + ", clientId=" + this.getClientId() + ", uploadedFile=" + this.getUploadedFile() + ")";
    }

    public FullStreamDTO(Integer id, UUID uuid, int parentId, String image, String codec, StreamStatus status, int direction, String host2, Integer port, String path, String fullPath, String name, String address, ItemRestrictionsDTO restrictions, Map<String, Boolean> currentUserRestrictions, StreamAuth auth, StreamParams params, String timezone, CoordinatesDTO location, ResolutionDTO resolution, String duration, Integer clientId, String uploadedFile) {
        this.id = id;
        this.uuid = uuid;
        this.parentId = parentId;
        this.image = image;
        this.codec = codec;
        this.status = status;
        this.direction = direction;
        this.host = host2;
        this.port = port;
        this.path = path;
        this.fullPath = fullPath;
        this.name = name;
        this.address = address;
        this.restrictions = restrictions;
        this.currentUserRestrictions = currentUserRestrictions;
        this.auth = auth;
        this.params = params;
        this.timezone = timezone;
        this.location = location;
        this.resolution = resolution;
        this.duration = duration;
        this.clientId = clientId;
        this.uploadedFile = uploadedFile;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FullStreamDTO withParams(StreamParams params) {
        return this.params == params ? this : new FullStreamDTO(this.id, this.uuid, this.parentId, this.image, this.codec, this.status, this.direction, this.host, this.port, this.path, this.fullPath, this.name, this.address, this.restrictions, this.currentUserRestrictions, this.auth, params, this.timezone, this.location, this.resolution, this.duration, this.clientId, this.uploadedFile);
    }

    public FullStreamDTO withResolution(ResolutionDTO resolution) {
        return this.resolution == resolution ? this : new FullStreamDTO(this.id, this.uuid, this.parentId, this.image, this.codec, this.status, this.direction, this.host, this.port, this.path, this.fullPath, this.name, this.address, this.restrictions, this.currentUserRestrictions, this.auth, this.params, this.timezone, this.location, resolution, this.duration, this.clientId, this.uploadedFile);
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public static class FullStreamDTOBuilder {
        private Integer id;
        private UUID uuid;
        private int parentId;
        private String image;
        private String codec;
        private StreamStatus status;
        private int direction;
        private String host;
        private Integer port;
        private String path;
        private String fullPath;
        private String name;
        private String address;
        private ItemRestrictionsDTO restrictions;
        private Map<String, Boolean> currentUserRestrictions;
        private StreamAuth auth;
        private StreamParams params;
        private String timezone;
        private CoordinatesDTO location;
        private ResolutionDTO resolution;
        private String duration;
        private Integer clientId;
        private String uploadedFile;

        FullStreamDTOBuilder() {
        }

        public FullStreamDTOBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public FullStreamDTOBuilder uuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public FullStreamDTOBuilder parentId(int parentId) {
            this.parentId = parentId;
            return this;
        }

        public FullStreamDTOBuilder image(String image) {
            this.image = image;
            return this;
        }

        public FullStreamDTOBuilder codec(String codec) {
            this.codec = codec;
            return this;
        }

        public FullStreamDTOBuilder status(StreamStatus status) {
            this.status = status;
            return this;
        }

        public FullStreamDTOBuilder direction(int direction) {
            this.direction = direction;
            return this;
        }

        public FullStreamDTOBuilder host(String host2) {
            this.host = host2;
            return this;
        }

        public FullStreamDTOBuilder port(Integer port) {
            this.port = port;
            return this;
        }

        public FullStreamDTOBuilder path(String path) {
            this.path = path;
            return this;
        }

        public FullStreamDTOBuilder fullPath(String fullPath) {
            this.fullPath = fullPath;
            return this;
        }

        public FullStreamDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public FullStreamDTOBuilder address(String address) {
            this.address = address;
            return this;
        }

        public FullStreamDTOBuilder restrictions(ItemRestrictionsDTO restrictions) {
            this.restrictions = restrictions;
            return this;
        }

        public FullStreamDTOBuilder currentUserRestrictions(Map<String, Boolean> currentUserRestrictions) {
            this.currentUserRestrictions = currentUserRestrictions;
            return this;
        }

        public FullStreamDTOBuilder auth(StreamAuth auth) {
            this.auth = auth;
            return this;
        }

        public FullStreamDTOBuilder params(StreamParams params) {
            this.params = params;
            return this;
        }

        public FullStreamDTOBuilder timezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public FullStreamDTOBuilder location(CoordinatesDTO location) {
            this.location = location;
            return this;
        }

        public FullStreamDTOBuilder resolution(ResolutionDTO resolution) {
            this.resolution = resolution;
            return this;
        }

        public FullStreamDTOBuilder duration(String duration) {
            this.duration = duration;
            return this;
        }

        public FullStreamDTOBuilder clientId(Integer clientId) {
            this.clientId = clientId;
            return this;
        }

        public FullStreamDTOBuilder uploadedFile(String uploadedFile) {
            this.uploadedFile = uploadedFile;
            return this;
        }

        public FullStreamDTO build() {
            return new FullStreamDTO(this.id, this.uuid, this.parentId, this.image, this.codec, this.status, this.direction, this.host, this.port, this.path, this.fullPath, this.name, this.address, this.restrictions, this.currentUserRestrictions, this.auth, this.params, this.timezone, this.location, this.resolution, this.duration, this.clientId, this.uploadedFile);
        }

        public String toString() {
            return "FullStreamDTO.FullStreamDTOBuilder(id=" + this.id + ", uuid=" + String.valueOf(this.uuid) + ", parentId=" + this.parentId + ", image=" + this.image + ", codec=" + this.codec + ", status=" + String.valueOf((Object)this.status) + ", direction=" + this.direction + ", host=" + this.host + ", port=" + this.port + ", path=" + this.path + ", fullPath=" + this.fullPath + ", name=" + this.name + ", address=" + this.address + ", restrictions=" + String.valueOf(this.restrictions) + ", currentUserRestrictions=" + String.valueOf(this.currentUserRestrictions) + ", auth=" + String.valueOf(this.auth) + ", params=" + String.valueOf(this.params) + ", timezone=" + this.timezone + ", location=" + String.valueOf(this.location) + ", resolution=" + String.valueOf(this.resolution) + ", duration=" + this.duration + ", clientId=" + this.clientId + ", uploadedFile=" + this.uploadedFile + ")";
        }
    }

    public static final class Fields {
        public static final String id = "id";
        public static final String uuid = "uuid";
        public static final String parentId = "parentId";
        public static final String image = "image";
        public static final String codec = "codec";
        public static final String status = "status";
        public static final String direction = "direction";
        public static final String host = "host";
        public static final String port = "port";
        public static final String path = "path";
        public static final String fullPath = "fullPath";
        public static final String name = "name";
        public static final String address = "address";
        public static final String restrictions = "restrictions";
        public static final String currentUserRestrictions = "currentUserRestrictions";
        public static final String auth = "auth";
        public static final String params = "params";
        public static final String timezone = "timezone";
        public static final String location = "location";
        public static final String resolution = "resolution";
        public static final String duration = "duration";
        public static final String clientId = "clientId";
        public static final String uploadedFile = "uploadedFile";
    }
}

