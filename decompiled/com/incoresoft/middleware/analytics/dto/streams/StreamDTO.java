/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.streams;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.dto.streams.CoordinatesDTO;
import com.incoresoft.middleware.analytics.dto.streams.ResolutionDTO;
import com.incoresoft.middleware.analytics.models.DeletedStream;
import com.incoresoft.middleware.analytics.serialization.StreamParamsSerializer;
import com.incoresoft.middleware.model.Coordinates;
import com.incoresoft.middleware.model.stream.Codec;
import com.incoresoft.middleware.model.stream.Resolution;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZoneId;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.apache.commons.lang3.time.DurationFormatUtils;

@Schema(name="StreamDTO", description="Video stream class structure")
public class StreamDTO {
    @JsonProperty(value="id")
    @JSONField(name="id")
    @Schema(name="id", description="Video stream Id", example="1")
    private int id;
    @JsonProperty(value="uuid")
    @JSONField(name="uuid")
    @Schema(name="uuid", description="Video stream Id", example="1")
    private String uuid;
    @JsonProperty(value="name")
    @JSONField(name="name")
    @Schema(name="name", description="Video stream name", example="Main stream", implementation=String.class)
    @NotBlank
    private String name;
    @JsonProperty(value="file_name")
    @JSONField(name="file_name")
    @Schema(name="file_name", description="Image file name (preview)", example="vUBi6rUge1.jpg")
    private String image;
    @JsonProperty(value="codec")
    @JSONField(name="codec")
    @Schema(name="codec", description="Codec type", example="H.264", implementation=Codec.class)
    private String codec;
    @JsonProperty(value="timezone")
    @JSONField(name="timezone")
    @Schema(name="timezone", description="Timezone", implementation=String.class, example="Europe/Kiev")
    private String timezone;
    @JsonProperty(value="location")
    @JSONField(name="location")
    @Schema(name="location", description="Camera location (coordinates)", implementation=CoordinatesDTO.class)
    private CoordinatesDTO location;
    @JsonProperty(value="address")
    @JSONField(name="address")
    @Schema(name="address", description="Camera location (address)", implementation=String.class, example="Kyiv, Khreshchatyk St, 1")
    private String address;
    @JsonProperty(value="resolution")
    @JSONField(name="resolution")
    @Schema(name="resolution", description="resolution", implementation=ResolutionDTO.class)
    private ResolutionDTO resolution;
    @JsonProperty(value="duration")
    @JSONField(name="duration")
    @Schema(name="duration", description="Duration of file based stream in HH:mm:ss. format.", example="01:32:43")
    private String duration;
    @JsonProperty(value="params")
    @JSONField(name="params", deserializeUsing=StreamParamsSerializer.class)
    @Schema(name="params", description="Custom stream parameters")
    @Valid
    private StreamParams params;
    @JsonProperty(value="status")
    @JSONField(name="status")
    @Schema(name="status", description="Stream status (is stream online or offline)")
    private StreamStatus status;

    public StreamDTO(Stream stream) {
        if (stream == null) {
            stream = new DeletedStream(0);
        }
        this.id = stream.getId();
        this.uuid = stream.getUUID().toString();
        this.image = stream.getImage();
        this.codec = stream.getCodec() != null ? stream.getCodec().getDisplayedName() : "";
        this.name = stream.getName();
        this.location = Objects.isNull(stream.getLocation()) ? new CoordinatesDTO(new Coordinates(0.0, 0.0)) : new CoordinatesDTO(stream.getLocation());
        this.address = stream.getAddress();
        this.resolution = Objects.isNull(stream.getResolution()) ? new ResolutionDTO(new Resolution(0, 0)) : new ResolutionDTO(stream.getResolution());
        this.timezone = stream.getTimezone() != null ? stream.getTimezone().getId() : ZoneId.systemDefault().getId();
        this.duration = stream.getDuration() != null ? DurationFormatUtils.formatDuration(stream.getDuration().toMillis(), "HH:mm:ss") : null;
        this.params = stream.getParams();
        this.status = stream.getStatus();
    }

    public int getId() {
        return this.id;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public String getCodec() {
        return this.codec;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public CoordinatesDTO getLocation() {
        return this.location;
    }

    public String getAddress() {
        return this.address;
    }

    public ResolutionDTO getResolution() {
        return this.resolution;
    }

    public String getDuration() {
        return this.duration;
    }

    public StreamParams getParams() {
        return this.params;
    }

    public StreamStatus getStatus() {
        return this.status;
    }

    @JsonProperty(value="id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty(value="uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty(value="name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value="file_name")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty(value="codec")
    public void setCodec(String codec) {
        this.codec = codec;
    }

    @JsonProperty(value="timezone")
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonProperty(value="location")
    public void setLocation(CoordinatesDTO location) {
        this.location = location;
    }

    @JsonProperty(value="address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty(value="resolution")
    public void setResolution(ResolutionDTO resolution) {
        this.resolution = resolution;
    }

    @JsonProperty(value="duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty(value="params")
    public void setParams(StreamParams params) {
        this.params = params;
    }

    @JsonProperty(value="status")
    public void setStatus(StreamStatus status) {
        this.status = status;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StreamDTO)) {
            return false;
        }
        StreamDTO other = (StreamDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        String this$uuid = this.getUuid();
        String other$uuid = other.getUuid();
        if (this$uuid == null ? other$uuid != null : !this$uuid.equals(other$uuid)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
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
        String this$address = this.getAddress();
        String other$address = other.getAddress();
        if (this$address == null ? other$address != null : !this$address.equals(other$address)) {
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
        StreamParams this$params = this.getParams();
        StreamParams other$params = other.getParams();
        if (this$params == null ? other$params != null : !this$params.equals(other$params)) {
            return false;
        }
        StreamStatus this$status = this.getStatus();
        StreamStatus other$status = other.getStatus();
        return !(this$status == null ? other$status != null : !((Object)((Object)this$status)).equals((Object)other$status));
    }

    protected boolean canEqual(Object other) {
        return other instanceof StreamDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        String $uuid = this.getUuid();
        result = result * 59 + ($uuid == null ? 43 : $uuid.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $image = this.getImage();
        result = result * 59 + ($image == null ? 43 : $image.hashCode());
        String $codec = this.getCodec();
        result = result * 59 + ($codec == null ? 43 : $codec.hashCode());
        String $timezone = this.getTimezone();
        result = result * 59 + ($timezone == null ? 43 : $timezone.hashCode());
        CoordinatesDTO $location = this.getLocation();
        result = result * 59 + ($location == null ? 43 : ((Object)$location).hashCode());
        String $address = this.getAddress();
        result = result * 59 + ($address == null ? 43 : $address.hashCode());
        ResolutionDTO $resolution = this.getResolution();
        result = result * 59 + ($resolution == null ? 43 : ((Object)$resolution).hashCode());
        String $duration = this.getDuration();
        result = result * 59 + ($duration == null ? 43 : $duration.hashCode());
        StreamParams $params = this.getParams();
        result = result * 59 + ($params == null ? 43 : $params.hashCode());
        StreamStatus $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)((Object)$status)).hashCode());
        return result;
    }

    public String toString() {
        return "StreamDTO(id=" + this.getId() + ", uuid=" + this.getUuid() + ", name=" + this.getName() + ", image=" + this.getImage() + ", codec=" + this.getCodec() + ", timezone=" + this.getTimezone() + ", location=" + String.valueOf(this.getLocation()) + ", address=" + this.getAddress() + ", resolution=" + String.valueOf(this.getResolution()) + ", duration=" + this.getDuration() + ", params=" + String.valueOf(this.getParams()) + ", status=" + String.valueOf((Object)this.getStatus()) + ")";
    }

    public StreamDTO() {
    }

    public StreamDTO(int id, String uuid, String name, String image, String codec, String timezone, CoordinatesDTO location, String address, ResolutionDTO resolution, String duration, StreamParams params, StreamStatus status) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.image = image;
        this.codec = codec;
        this.timezone = timezone;
        this.location = location;
        this.address = address;
        this.resolution = resolution;
        this.duration = duration;
        this.params = params;
        this.status = status;
    }
}

