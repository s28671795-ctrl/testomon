/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.analytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.analytics.dto.streams.ResolutionDTO;
import com.incoresoft.middleware.analytics.streams.params.FileStreamParams;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import java.net.URI;
import java.time.ZoneId;
import java.util.Optional;

public final class AnalyticsStreamDTO {
    @JSONField(name="id")
    private final long id;
    @JSONField(name="url")
    private final String url;
    @JSONField(name="type")
    private final StreamType type;
    @JSONField(name="timestamp_from_stream")
    private final boolean timestampFromStream;
    @JSONField(name="resolution")
    private final ResolutionDTO resolution;
    @JSONField(name="file_fast_speed")
    private final boolean fileFastSpeed;
    @JSONField(name="file_cycle")
    private final boolean fileCycle;
    @JSONField(name="timezone")
    private final String timezone;

    public AnalyticsStreamDTO(Stream stream, boolean isVezhaInIntegrationMode) {
        this.id = stream.getId();
        this.url = Optional.ofNullable(stream.getUri()).map(URI::toString).orElse(null);
        this.type = stream.getType();
        this.timestampFromStream = stream.getType() == StreamType.luxriot || isVezhaInIntegrationMode;
        this.resolution = new ResolutionDTO(stream.getResolution());
        this.timezone = Optional.ofNullable(stream.getTimezone()).orElse(ZoneId.systemDefault()).getId();
        boolean localFileFastSpeed = false;
        boolean localFileCycle = false;
        Optional<FileStreamParams> maybeFileStreamParams = Optional.ofNullable(stream.getParams()).filter(params -> params.getType() == StreamType.file || params.getType() == StreamType.smb_file).map(FileStreamParams.class::cast);
        if (maybeFileStreamParams.isPresent()) {
            FileStreamParams fileStreamParams = maybeFileStreamParams.get();
            localFileFastSpeed = fileStreamParams.getFileFastSpeed() == 1;
            localFileCycle = fileStreamParams.getFileCycle() == 1;
        }
        this.fileFastSpeed = localFileFastSpeed;
        this.fileCycle = localFileCycle;
    }

    public long getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public StreamType getType() {
        return this.type;
    }

    public boolean isTimestampFromStream() {
        return this.timestampFromStream;
    }

    public ResolutionDTO getResolution() {
        return this.resolution;
    }

    public boolean isFileFastSpeed() {
        return this.fileFastSpeed;
    }

    public boolean isFileCycle() {
        return this.fileCycle;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsStreamDTO)) {
            return false;
        }
        AnalyticsStreamDTO other = (AnalyticsStreamDTO)o;
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.isTimestampFromStream() != other.isTimestampFromStream()) {
            return false;
        }
        if (this.isFileFastSpeed() != other.isFileFastSpeed()) {
            return false;
        }
        if (this.isFileCycle() != other.isFileCycle()) {
            return false;
        }
        String this$url = this.getUrl();
        String other$url = other.getUrl();
        if (this$url == null ? other$url != null : !this$url.equals(other$url)) {
            return false;
        }
        StreamType this$type = this.getType();
        StreamType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        ResolutionDTO this$resolution = this.getResolution();
        ResolutionDTO other$resolution = other.getResolution();
        if (this$resolution == null ? other$resolution != null : !((Object)this$resolution).equals(other$resolution)) {
            return false;
        }
        String this$timezone = this.getTimezone();
        String other$timezone = other.getTimezone();
        return !(this$timezone == null ? other$timezone != null : !this$timezone.equals(other$timezone));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $id = this.getId();
        result = result * 59 + (int)($id >>> 32 ^ $id);
        result = result * 59 + (this.isTimestampFromStream() ? 79 : 97);
        result = result * 59 + (this.isFileFastSpeed() ? 79 : 97);
        result = result * 59 + (this.isFileCycle() ? 79 : 97);
        String $url = this.getUrl();
        result = result * 59 + ($url == null ? 43 : $url.hashCode());
        StreamType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        ResolutionDTO $resolution = this.getResolution();
        result = result * 59 + ($resolution == null ? 43 : ((Object)$resolution).hashCode());
        String $timezone = this.getTimezone();
        result = result * 59 + ($timezone == null ? 43 : $timezone.hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsStreamDTO(id=" + this.getId() + ", url=" + this.getUrl() + ", type=" + String.valueOf((Object)this.getType()) + ", timestampFromStream=" + this.isTimestampFromStream() + ", resolution=" + String.valueOf(this.getResolution()) + ", fileFastSpeed=" + this.isFileFastSpeed() + ", fileCycle=" + this.isFileCycle() + ", timezone=" + this.getTimezone() + ")";
    }
}

