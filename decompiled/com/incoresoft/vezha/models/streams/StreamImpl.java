/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams;

import com.incoresoft.middleware.analytics.streams.authorization.NoAuth;
import com.incoresoft.middleware.analytics.streams.params.EmptyParams;
import com.incoresoft.middleware.model.Coordinates;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.FrameGrabResult;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamAuth;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamStatus;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.vezha.dto.servers.cameraanalytics.InitializationDTO;
import com.incoresoft.vezha.dto.streams.FullStreamDTO;
import com.incoresoft.vezha.models.streams.model.StreamRestrictions;
import java.net.URI;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamImpl
implements Stream {
    private static final Logger log = LoggerFactory.getLogger(StreamImpl.class);
    public static final int STREAM_NOT_SAVED = -2;
    private final UUID uuid;
    private final int id;
    private final String name;
    private final Instant createdAt;
    private final Coordinates location;
    private final StreamType type;
    private final String address;
    private final int direction;
    private final int clientId;
    private final ZoneId timezone;
    private final StreamAuth auth;
    private final StreamParams params;
    private final RoleUserRestrictions restrictions;
    private final int folderId;
    private URI uri;
    private StreamStatus status;
    private String image;
    private Resolution resolution;
    private Codec codec;
    private Duration duration;

    public StreamImpl(UsersManager usersManager, InitializationDTO initializationDTO, Path image) {
        this.id = -2;
        this.uuid = UUID.fromString(initializationDTO.getCameraUUID().toLowerCase());
        this.folderId = 0;
        this.status = StreamStatus.online;
        this.type = StreamType.embedded;
        this.uri = null;
        this.codec = null;
        this.resolution = new Resolution(initializationDTO.getResolution().getWidth(), initializationDTO.getResolution().getHeight());
        this.image = image.getFileName().toString();
        this.name = initializationDTO.getCameraName();
        this.createdAt = Instant.now();
        this.location = new Coordinates(0.0, 0.0);
        this.address = "";
        this.direction = 0;
        this.restrictions = new StreamRestrictions((User)usersManager.get(1));
        this.params = new EmptyParams(StreamType.embedded);
        this.auth = new NoAuth();
        this.timezone = ZoneId.systemDefault();
        this.clientId = 0;
        this.duration = null;
    }

    public StreamImpl(FullStreamDTO dto, RoleUserRestrictions restrictions) {
        this.id = Optional.ofNullable(dto.getId()).orElse(0);
        this.uuid = dto.getUuid() == null ? UUID.randomUUID() : dto.getUuid();
        this.folderId = dto.getParentId();
        this.status = Optional.ofNullable(dto.getStatus()).orElse(StreamStatus.online);
        this.type = dto.getParams().getType();
        this.uri = dto.getURI();
        this.resolution = Optional.ofNullable(dto.getResolution()).map(it -> new Resolution(it.getWidth(), it.getHeight())).orElse(new Resolution(0, 0));
        this.image = dto.getImage();
        this.name = dto.getName();
        this.location = Optional.ofNullable(dto.getLocation()).map(it -> new Coordinates(it.getLatitude(), it.getLongitude())).orElse(new Coordinates(0.0, 0.0));
        this.address = Optional.ofNullable(dto.getAddress()).orElse("");
        this.direction = dto.getDirection();
        this.restrictions = restrictions;
        this.params = dto.getParams();
        this.auth = dto.getAuth();
        this.timezone = Optional.ofNullable(dto.getTimezone()).map(ZoneId::of).orElse(ZoneId.systemDefault());
        this.clientId = dto.getClientId();
        this.createdAt = Instant.now();
        this.duration = null;
        this.codec = Optional.ofNullable(dto.getCodec()).map(Codec::getCodec).orElse(null);
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public Coordinates getLocation() {
        return this.location;
    }

    @Override
    public Resolution getResolution() {
        return this.resolution;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    @Override
    public ZoneId getTimezone() {
        return Optional.ofNullable(this.timezone).orElse(ZoneId.systemDefault());
    }

    @Override
    public void update(FrameGrabResult grabFrame) {
        this.status = StreamStatus.online;
        this.resolution = grabFrame.getResolution();
        this.codec = grabFrame.getCodec();
        this.image = grabFrame.getImage();
        this.duration = grabFrame.getDuration();
    }

    public static boolean isFile(Stream stream) {
        return stream.getType() == StreamType.file || stream.getType() == StreamType.smb_file;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public StreamType getType() {
        return this.type;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public int getDirection() {
        return this.direction;
    }

    @Override
    public StreamAuth getAuth() {
        return this.auth;
    }

    @Override
    public StreamParams getParams() {
        return this.params;
    }

    @Override
    public RoleUserRestrictions getRestrictions() {
        return this.restrictions;
    }

    @Override
    public int getFolderId() {
        return this.folderId;
    }

    @Override
    public URI getUri() {
        return this.uri;
    }

    @Override
    public StreamStatus getStatus() {
        return this.status;
    }

    @Override
    public String getImage() {
        return this.image;
    }

    @Override
    public Codec getCodec() {
        return this.codec;
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setStatus(StreamStatus status) {
        this.status = status;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamImpl)) {
            return false;
        }
        StreamImpl other = (StreamImpl)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getDirection() != other.getDirection()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        if (this.getFolderId() != other.getFolderId()) {
            return false;
        }
        UUID this$uuid = this.getUUID();
        UUID other$uuid = other.getUUID();
        if (this$uuid == null ? other$uuid != null : !((Object)this$uuid).equals(other$uuid)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt)) {
            return false;
        }
        Coordinates this$location = this.getLocation();
        Coordinates other$location = other.getLocation();
        if (this$location == null ? other$location != null : !((Object)this$location).equals(other$location)) {
            return false;
        }
        StreamType this$type = this.getType();
        StreamType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        String this$address = this.getAddress();
        String other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) {
            return false;
        }
        ZoneId this$timezone = this.getTimezone();
        ZoneId other$timezone = other.getTimezone();
        if (this$timezone == null ? other$timezone != null : !((Object)this$timezone).equals(other$timezone)) {
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
        RoleUserRestrictions this$restrictions = this.getRestrictions();
        RoleUserRestrictions other$restrictions = other.getRestrictions();
        if (this$restrictions == null ? other$restrictions != null : !this$restrictions.equals(other$restrictions)) {
            return false;
        }
        URI this$uri = this.getUri();
        URI other$uri = other.getUri();
        if (this$uri == null ? other$uri != null : !((Object)this$uri).equals(other$uri)) {
            return false;
        }
        StreamStatus this$status = this.getStatus();
        StreamStatus other$status = other.getStatus();
        if (this$status == null ? other$status != null : !((Object)((Object)this$status)).equals((Object)other$status)) {
            return false;
        }
        String this$image = this.getImage();
        String other$image = other.getImage();
        if (this$image == null ? other$image != null : !this$image.equals(other$image)) {
            return false;
        }
        Resolution this$resolution = this.getResolution();
        Resolution other$resolution = other.getResolution();
        if (this$resolution == null ? other$resolution != null : !((Object)this$resolution).equals(other$resolution)) {
            return false;
        }
        Codec this$codec = this.getCodec();
        Codec other$codec = other.getCodec();
        if (this$codec == null ? other$codec != null : !((Object)((Object)this$codec)).equals((Object)other$codec)) {
            return false;
        }
        Duration this$duration = this.getDuration();
        Duration other$duration = other.getDuration();
        return !(this$duration == null ? other$duration != null : !((Object)this$duration).equals(other$duration));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StreamImpl;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        result = result * 59 + this.getDirection();
        result = result * 59 + this.getClientId();
        result = result * 59 + this.getFolderId();
        UUID $uuid = this.getUUID();
        result = result * 59 + ($uuid == null ? 43 : ((Object)$uuid).hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        Coordinates $location = this.getLocation();
        result = result * 59 + ($location == null ? 43 : ((Object)$location).hashCode());
        StreamType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        String $address = this.getAddress();
        result = result * 59 + ($address == null ? 43 : $address.hashCode());
        ZoneId $timezone = this.getTimezone();
        result = result * 59 + ($timezone == null ? 43 : ((Object)$timezone).hashCode());
        StreamAuth $auth = this.getAuth();
        result = result * 59 + ($auth == null ? 43 : $auth.hashCode());
        StreamParams $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : $params.hashCode());
        RoleUserRestrictions $restrictions = this.getRestrictions();
        result = result * 59 + ($restrictions == null ? 43 : $restrictions.hashCode());
        URI $uri = this.getUri();
        result = result * 59 + ($uri == null ? 43 : ((Object)$uri).hashCode());
        StreamStatus $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)((Object)$status)).hashCode());
        String $image = this.getImage();
        result = result * 59 + ($image == null ? 43 : $image.hashCode());
        Resolution $resolution = this.getResolution();
        result = result * 59 + ($resolution == null ? 43 : ((Object)$resolution).hashCode());
        Codec $codec = this.getCodec();
        result = result * 59 + ($codec == null ? 43 : ((Object)((Object)$codec)).hashCode());
        Duration $duration = this.getDuration();
        result = result * 59 + ($duration == null ? 43 : ((Object)$duration).hashCode());
        return result;
    }

    public String toString() {
        return "StreamImpl(uuid=" + String.valueOf(this.getUUID()) + ", id=" + this.getId() + ", name=" + this.getName() + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", location=" + String.valueOf(this.getLocation()) + ", type=" + String.valueOf((Object)this.getType()) + ", address=" + this.getAddress() + ", direction=" + this.getDirection() + ", clientId=" + this.getClientId() + ", timezone=" + String.valueOf(this.getTimezone()) + ", auth=" + String.valueOf(this.getAuth()) + ", params=" + String.valueOf(this.getParams()) + ", restrictions=" + String.valueOf(this.getRestrictions()) + ", folderId=" + this.getFolderId() + ", uri=" + String.valueOf(this.getUri()) + ", status=" + String.valueOf((Object)this.getStatus()) + ", image=" + this.getImage() + ", resolution=" + String.valueOf(this.getResolution()) + ", codec=" + String.valueOf((Object)this.getCodec()) + ", duration=" + String.valueOf(this.getDuration()) + ")";
    }

    public StreamImpl(UUID uuid, int id, String name, Instant createdAt, Coordinates location, StreamType type, String address, int direction, int clientId, ZoneId timezone, StreamAuth auth, StreamParams params, RoleUserRestrictions restrictions, int folderId) {
        this.uuid = uuid;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.location = location;
        this.type = type;
        this.address = address;
        this.direction = direction;
        this.clientId = clientId;
        this.timezone = timezone;
        this.auth = auth;
        this.params = params;
        this.restrictions = restrictions;
        this.folderId = folderId;
    }
}

