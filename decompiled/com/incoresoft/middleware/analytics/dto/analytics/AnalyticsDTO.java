/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.analytics;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.AnalyticsType;
import com.incoresoft.middleware.analytics.dto.analytics.AnalyticsErrorDTO;
import com.incoresoft.middleware.analytics.dto.events.EventsHolderDTO;
import com.incoresoft.middleware.analytics.dto.hardware.AnalyticsHardwareInfoDTO;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.analytics.dto.streams.StreamDTO;
import com.incoresoft.middleware.analytics.models.VideoFileProgress;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.permission.Permission;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.user.User;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jetbrains.annotations.NotNull;

@Schema(name="AnalyticsDTO", description="Analytics class structure")
public class AnalyticsDTO {
    @JSONField(name="id")
    @JsonProperty(value="id")
    @Schema(name="id", description="Analytics Id", example="1")
    private int id;
    @JSONField(name="plugin_name")
    @JsonProperty(value="plugin_name")
    @Schema(name="plugin_name", description="Analytics plugin name", example="alpr")
    private String pluginName;
    @JSONField(name="name")
    @JsonProperty(value="name")
    @Schema(name="name", description="Analytics name", example="Test", implementation=String.class)
    public String name;
    @JSONField(name="type")
    @JsonProperty(value="type")
    @Schema(name="type", description="Analytics plugin type", implementation=String.class)
    public String type;
    @JSONField(name="stream")
    @JsonProperty(value="stream")
    @Schema(name="stream", description="Stream info", implementation=StreamDTO.class)
    public StreamDTO stream;
    @JSONField(name="created_at")
    @JsonProperty(value="created_at")
    @Schema(name="created_at", description="Analytics creating timestamp", implementation=Instant.class)
    public Instant createdAt;
    @JSONField(name="started_at")
    @JsonProperty(value="started_at")
    @Schema(name="started_at", description="Analytics starting timestamp", implementation=Instant.class)
    public Instant startedAt;
    @JSONField(name="uptime")
    @JsonProperty(value="uptime")
    @Schema(description="Total time in active analytics status", example="02:15:10")
    public String uptime;
    @JSONField(name="status")
    @JsonProperty(value="status")
    @Schema(name="status", description="Current status of analytics", implementation=AnalyticsStatus.class, example="started")
    public AnalyticsStatus status;
    @JSONField(name="client_id", serialize=false)
    @JsonProperty(value="client_id", access=JsonProperty.Access.WRITE_ONLY)
    @Schema(name="client_id", description="Client ID", implementation=Long.class, example="0", hidden=true)
    public long clientId;
    @JSONField(name="h264_url")
    @JsonProperty(value="h264_url")
    @Schema(name="h264_url", description="H264 preview URL", nullable=true, implementation=String.class, example="http://195.34.205.115:3020/h264/1")
    public String h264Url;
    @JSONField(name="mjpeg_url")
    @JsonProperty(value="mjpeg_url")
    @Schema(name="mjpeg_url", description="MJPEG preview URL", nullable=true, implementation=String.class, example="http://127.0.0.1:3020/1")
    public String mjpegUrl;
    @JSONField(name="module")
    @JsonProperty(value="module")
    @Schema(name="module", description="Module", implementation=String.class)
    public AnalyticsModule module;
    @JSONField(name="hardware_info")
    @JsonProperty(value="hardware_info")
    @Schema(name="hardware_info", description="Hardware info", implementation=AnalyticsHardwareInfoDTO.class, nullable=true)
    public AnalyticsHardwareInfoDTO hardwareInfo;
    @JSONField(name="access_restrictions")
    @JsonProperty(value="access_restrictions")
    @Schema(name="access_restrictions", description="Restrictions info", implementation=ItemRestrictionsDTO.class, nullable=true)
    public ItemRestrictionsDTO accessRestrictions;
    @JSONField(name="current_user_restrictions")
    @JsonProperty(value="current_user_restrictions")
    @Schema(name="current_user_restrictions", description="Restrictions info for current user", nullable=true)
    public Map<String, Boolean> currentUserRestrictions;
    @JSONField(name="allowed_server_ids")
    @JsonProperty(value="allowed_server_ids")
    @ArraySchema(schema=@Schema(name="allowed_server_ids", description="Allowed server IDs on which analytics can be run", nullable=true))
    public Set<String> allowedServerIds;
    @JSONField(name="topic")
    @JsonProperty(value="topic")
    public String topic;
    @JSONField(name="progress_percentage")
    @JsonProperty(value="progress_percentage")
    @Schema(name="progress_percentage", description="Analytics processing percentage (video files only)", example="0")
    public double progressPercentage;
    @JSONField(name="progress_time")
    @JsonProperty(value="progress_time")
    @Schema(name="progress_time", description="Video file processing time by analytics (video files only)", example="00:00:00")
    public String progressTime;
    @JSONField(name="events_holder")
    @JsonProperty(value="events_holder")
    @Schema(name="events_holder", description="Events holder", implementation=EventsHolderDTO.class)
    public EventsHolderDTO eventsHolder;
    @JSONField(name="h264_stream")
    @JsonProperty(value="h264_stream")
    @Schema(name="h264_stream", description="H264 streaming allowed", implementation=Boolean.class, example="true")
    public boolean h264Stream;
    @JSONField(name="errors")
    @JsonProperty(value="errors")
    @Schema(name="errors", description="Analytics errors")
    public List<AnalyticsErrorDTO> errors;
    @JSONField(name="parent_id")
    @JsonProperty(value="parent_id")
    @Schema(name="parent_id", description="Group Id", example="1")
    public int parentId;

    public AnalyticsDTO(@NotNull User user, @NotNull Analytics inner, @NotNull Stream stream, @NotNull VideoFileProgress progress) {
        this.id = inner.getId();
        this.pluginName = inner.getPluginName();
        this.name = inner.getName();
        this.type = inner.getModule().getAnalyticsTypes().stream().map(AnalyticsType::getName).collect(Collectors.joining(","));
        this.stream = new StreamDTO(stream);
        this.createdAt = inner.getCreatedAt();
        this.startedAt = inner.getStartInstant();
        this.uptime = AnalyticsDTO.calculateUptime(inner);
        this.status = inner.getStatus();
        this.clientId = inner.getClientId();
        this.h264Url = inner.getH264Preview();
        this.mjpegUrl = inner.getMJPEGPreview();
        this.module = inner.getModule();
        this.hardwareInfo = inner.getHardwareInfo() != null ? new AnalyticsHardwareInfoDTO(inner.getHardwareInfo()) : null;
        this.accessRestrictions = inner.getRestrictions() != null ? new ItemRestrictionsDTO(inner.getRestrictions()) : null;
        this.currentUserRestrictions = inner.getRestrictions().eligiblePermissions().stream().collect(Collectors.toUnmodifiableMap(Permission::getId, it -> inner.getRestrictions().isAllowed(user, (Permission)it)));
        this.allowedServerIds = inner.getAllowedServerIds();
        this.topic = inner.getUUID().toString();
        this.progressPercentage = progress.getProgressPercent();
        this.progressTime = DurationFormatUtils.formatDuration(progress.getCurrentProgress().toMillis(), "HH:mm:ss");
        this.eventsHolder = new EventsHolderDTO(inner.getEventsHolder());
        this.h264Stream = inner.isH264Stream();
        this.errors = inner.getErrors().stream().map(AnalyticsErrorDTO::new).toList();
        this.parentId = inner.getParentId();
    }

    private static String calculateUptime(Analytics analytics) {
        if (analytics.getStartInstant() == null) {
            return null;
        }
        Duration uptime = Duration.between(analytics.getStartInstant(), Instant.now(Clock.systemDefaultZone()));
        return String.format("%02d:%02d:%02d", uptime.toHours(), uptime.toMinutesPart(), uptime.toSecondsPart());
    }

    public int getId() {
        return this.id;
    }

    public String getPluginName() {
        return this.pluginName;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public StreamDTO getStream() {
        return this.stream;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getStartedAt() {
        return this.startedAt;
    }

    public String getUptime() {
        return this.uptime;
    }

    public AnalyticsStatus getStatus() {
        return this.status;
    }

    public long getClientId() {
        return this.clientId;
    }

    public String getH264Url() {
        return this.h264Url;
    }

    public String getMjpegUrl() {
        return this.mjpegUrl;
    }

    public AnalyticsModule getModule() {
        return this.module;
    }

    public AnalyticsHardwareInfoDTO getHardwareInfo() {
        return this.hardwareInfo;
    }

    public ItemRestrictionsDTO getAccessRestrictions() {
        return this.accessRestrictions;
    }

    public Map<String, Boolean> getCurrentUserRestrictions() {
        return this.currentUserRestrictions;
    }

    public Set<String> getAllowedServerIds() {
        return this.allowedServerIds;
    }

    public String getTopic() {
        return this.topic;
    }

    public double getProgressPercentage() {
        return this.progressPercentage;
    }

    public String getProgressTime() {
        return this.progressTime;
    }

    public EventsHolderDTO getEventsHolder() {
        return this.eventsHolder;
    }

    public boolean isH264Stream() {
        return this.h264Stream;
    }

    public List<AnalyticsErrorDTO> getErrors() {
        return this.errors;
    }

    public int getParentId() {
        return this.parentId;
    }

    @JsonProperty(value="id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty(value="plugin_name")
    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    @JsonProperty(value="name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value="type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty(value="stream")
    public void setStream(StreamDTO stream) {
        this.stream = stream;
    }

    @JsonProperty(value="created_at")
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty(value="started_at")
    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    @JsonProperty(value="uptime")
    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    @JsonProperty(value="status")
    public void setStatus(AnalyticsStatus status) {
        this.status = status;
    }

    @JsonProperty(value="client_id", access=JsonProperty.Access.WRITE_ONLY)
    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @JsonProperty(value="h264_url")
    public void setH264Url(String h264Url) {
        this.h264Url = h264Url;
    }

    @JsonProperty(value="mjpeg_url")
    public void setMjpegUrl(String mjpegUrl) {
        this.mjpegUrl = mjpegUrl;
    }

    @JsonProperty(value="module")
    public void setModule(AnalyticsModule module) {
        this.module = module;
    }

    @JsonProperty(value="hardware_info")
    public void setHardwareInfo(AnalyticsHardwareInfoDTO hardwareInfo) {
        this.hardwareInfo = hardwareInfo;
    }

    @JsonProperty(value="access_restrictions")
    public void setAccessRestrictions(ItemRestrictionsDTO accessRestrictions) {
        this.accessRestrictions = accessRestrictions;
    }

    @JsonProperty(value="current_user_restrictions")
    public void setCurrentUserRestrictions(Map<String, Boolean> currentUserRestrictions) {
        this.currentUserRestrictions = currentUserRestrictions;
    }

    @JsonProperty(value="allowed_server_ids")
    public void setAllowedServerIds(Set<String> allowedServerIds) {
        this.allowedServerIds = allowedServerIds;
    }

    @JsonProperty(value="topic")
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @JsonProperty(value="progress_percentage")
    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    @JsonProperty(value="progress_time")
    public void setProgressTime(String progressTime) {
        this.progressTime = progressTime;
    }

    @JsonProperty(value="events_holder")
    public void setEventsHolder(EventsHolderDTO eventsHolder) {
        this.eventsHolder = eventsHolder;
    }

    @JsonProperty(value="h264_stream")
    public void setH264Stream(boolean h264Stream) {
        this.h264Stream = h264Stream;
    }

    @JsonProperty(value="errors")
    public void setErrors(List<AnalyticsErrorDTO> errors2) {
        this.errors = errors2;
    }

    @JsonProperty(value="parent_id")
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnalyticsDTO)) {
            return false;
        }
        AnalyticsDTO other = (AnalyticsDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getId() != other.getId()) {
            return false;
        }
        if (this.getClientId() != other.getClientId()) {
            return false;
        }
        if (Double.compare(this.getProgressPercentage(), other.getProgressPercentage()) != 0) {
            return false;
        }
        if (this.isH264Stream() != other.isH264Stream()) {
            return false;
        }
        if (this.getParentId() != other.getParentId()) {
            return false;
        }
        String this$pluginName = this.getPluginName();
        String other$pluginName = other.getPluginName();
        if (this$pluginName == null ? other$pluginName != null : !this$pluginName.equals(other$pluginName)) {
            return false;
        }
        String this$name = this.getName();
        String other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        String this$type = this.getType();
        String other$type = other.getType();
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) {
            return false;
        }
        StreamDTO this$stream = this.getStream();
        StreamDTO other$stream = other.getStream();
        if (this$stream == null ? other$stream != null : !((Object)this$stream).equals(other$stream)) {
            return false;
        }
        Instant this$createdAt = this.getCreatedAt();
        Instant other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !((Object)this$createdAt).equals(other$createdAt)) {
            return false;
        }
        Instant this$startedAt = this.getStartedAt();
        Instant other$startedAt = other.getStartedAt();
        if (this$startedAt == null ? other$startedAt != null : !((Object)this$startedAt).equals(other$startedAt)) {
            return false;
        }
        String this$uptime = this.getUptime();
        String other$uptime = other.getUptime();
        if (this$uptime == null ? other$uptime != null : !this$uptime.equals(other$uptime)) {
            return false;
        }
        AnalyticsStatus this$status = this.getStatus();
        AnalyticsStatus other$status = other.getStatus();
        if (this$status == null ? other$status != null : !((Object)((Object)this$status)).equals((Object)other$status)) {
            return false;
        }
        String this$h264Url = this.getH264Url();
        String other$h264Url = other.getH264Url();
        if (this$h264Url == null ? other$h264Url != null : !this$h264Url.equals(other$h264Url)) {
            return false;
        }
        String this$mjpegUrl = this.getMjpegUrl();
        String other$mjpegUrl = other.getMjpegUrl();
        if (this$mjpegUrl == null ? other$mjpegUrl != null : !this$mjpegUrl.equals(other$mjpegUrl)) {
            return false;
        }
        AnalyticsModule this$module = this.getModule();
        AnalyticsModule other$module = other.getModule();
        if (this$module == null ? other$module != null : !this$module.equals(other$module)) {
            return false;
        }
        AnalyticsHardwareInfoDTO this$hardwareInfo = this.getHardwareInfo();
        AnalyticsHardwareInfoDTO other$hardwareInfo = other.getHardwareInfo();
        if (this$hardwareInfo == null ? other$hardwareInfo != null : !((Object)this$hardwareInfo).equals(other$hardwareInfo)) {
            return false;
        }
        ItemRestrictionsDTO this$accessRestrictions = this.getAccessRestrictions();
        ItemRestrictionsDTO other$accessRestrictions = other.getAccessRestrictions();
        if (this$accessRestrictions == null ? other$accessRestrictions != null : !((Object)this$accessRestrictions).equals(other$accessRestrictions)) {
            return false;
        }
        Map<String, Boolean> this$currentUserRestrictions = this.getCurrentUserRestrictions();
        Map<String, Boolean> other$currentUserRestrictions = other.getCurrentUserRestrictions();
        if (this$currentUserRestrictions == null ? other$currentUserRestrictions != null : !((Object)this$currentUserRestrictions).equals(other$currentUserRestrictions)) {
            return false;
        }
        Set<String> this$allowedServerIds = this.getAllowedServerIds();
        Set<String> other$allowedServerIds = other.getAllowedServerIds();
        if (this$allowedServerIds == null ? other$allowedServerIds != null : !((Object)this$allowedServerIds).equals(other$allowedServerIds)) {
            return false;
        }
        String this$topic = this.getTopic();
        String other$topic = other.getTopic();
        if (this$topic == null ? other$topic != null : !this$topic.equals(other$topic)) {
            return false;
        }
        String this$progressTime = this.getProgressTime();
        String other$progressTime = other.getProgressTime();
        if (this$progressTime == null ? other$progressTime != null : !this$progressTime.equals(other$progressTime)) {
            return false;
        }
        EventsHolderDTO this$eventsHolder = this.getEventsHolder();
        EventsHolderDTO other$eventsHolder = other.getEventsHolder();
        if (this$eventsHolder == null ? other$eventsHolder != null : !((Object)this$eventsHolder).equals(other$eventsHolder)) {
            return false;
        }
        List<AnalyticsErrorDTO> this$errors = this.getErrors();
        List<AnalyticsErrorDTO> other$errors = other.getErrors();
        return !(this$errors == null ? other$errors != null : !((Object)this$errors).equals(other$errors));
    }

    protected boolean canEqual(Object other) {
        return other instanceof AnalyticsDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getId();
        long $clientId = this.getClientId();
        result = result * 59 + (int)($clientId >>> 32 ^ $clientId);
        long $progressPercentage = Double.doubleToLongBits(this.getProgressPercentage());
        result = result * 59 + (int)($progressPercentage >>> 32 ^ $progressPercentage);
        result = result * 59 + (this.isH264Stream() ? 79 : 97);
        result = result * 59 + this.getParentId();
        String $pluginName = this.getPluginName();
        result = result * 59 + ($pluginName == null ? 43 : $pluginName.hashCode());
        String $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        String $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        StreamDTO $stream = this.getStream();
        result = result * 59 + ($stream == null ? 43 : ((Object)$stream).hashCode());
        Instant $createdAt = this.getCreatedAt();
        result = result * 59 + ($createdAt == null ? 43 : ((Object)$createdAt).hashCode());
        Instant $startedAt = this.getStartedAt();
        result = result * 59 + ($startedAt == null ? 43 : ((Object)$startedAt).hashCode());
        String $uptime = this.getUptime();
        result = result * 59 + ($uptime == null ? 43 : $uptime.hashCode());
        AnalyticsStatus $status = this.getStatus();
        result = result * 59 + ($status == null ? 43 : ((Object)((Object)$status)).hashCode());
        String $h264Url = this.getH264Url();
        result = result * 59 + ($h264Url == null ? 43 : $h264Url.hashCode());
        String $mjpegUrl = this.getMjpegUrl();
        result = result * 59 + ($mjpegUrl == null ? 43 : $mjpegUrl.hashCode());
        AnalyticsModule $module = this.getModule();
        result = result * 59 + ($module == null ? 43 : $module.hashCode());
        AnalyticsHardwareInfoDTO $hardwareInfo = this.getHardwareInfo();
        result = result * 59 + ($hardwareInfo == null ? 43 : ((Object)$hardwareInfo).hashCode());
        ItemRestrictionsDTO $accessRestrictions = this.getAccessRestrictions();
        result = result * 59 + ($accessRestrictions == null ? 43 : ((Object)$accessRestrictions).hashCode());
        Map<String, Boolean> $currentUserRestrictions = this.getCurrentUserRestrictions();
        result = result * 59 + ($currentUserRestrictions == null ? 43 : ((Object)$currentUserRestrictions).hashCode());
        Set<String> $allowedServerIds = this.getAllowedServerIds();
        result = result * 59 + ($allowedServerIds == null ? 43 : ((Object)$allowedServerIds).hashCode());
        String $topic = this.getTopic();
        result = result * 59 + ($topic == null ? 43 : $topic.hashCode());
        String $progressTime = this.getProgressTime();
        result = result * 59 + ($progressTime == null ? 43 : $progressTime.hashCode());
        EventsHolderDTO $eventsHolder = this.getEventsHolder();
        result = result * 59 + ($eventsHolder == null ? 43 : ((Object)$eventsHolder).hashCode());
        List<AnalyticsErrorDTO> $errors = this.getErrors();
        result = result * 59 + ($errors == null ? 43 : ((Object)$errors).hashCode());
        return result;
    }

    public String toString() {
        return "AnalyticsDTO(id=" + this.getId() + ", pluginName=" + this.getPluginName() + ", name=" + this.getName() + ", type=" + this.getType() + ", stream=" + String.valueOf(this.getStream()) + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", startedAt=" + String.valueOf(this.getStartedAt()) + ", uptime=" + this.getUptime() + ", status=" + String.valueOf((Object)this.getStatus()) + ", clientId=" + this.getClientId() + ", h264Url=" + this.getH264Url() + ", mjpegUrl=" + this.getMjpegUrl() + ", module=" + String.valueOf(this.getModule()) + ", hardwareInfo=" + String.valueOf(this.getHardwareInfo()) + ", accessRestrictions=" + String.valueOf(this.getAccessRestrictions()) + ", currentUserRestrictions=" + String.valueOf(this.getCurrentUserRestrictions()) + ", allowedServerIds=" + String.valueOf(this.getAllowedServerIds()) + ", topic=" + this.getTopic() + ", progressPercentage=" + this.getProgressPercentage() + ", progressTime=" + this.getProgressTime() + ", eventsHolder=" + String.valueOf(this.getEventsHolder()) + ", h264Stream=" + this.isH264Stream() + ", errors=" + String.valueOf(this.getErrors()) + ", parentId=" + this.getParentId() + ")";
    }

    public AnalyticsDTO() {
    }

    public AnalyticsDTO(int id, String pluginName, String name, String type, StreamDTO stream, Instant createdAt, Instant startedAt, String uptime, AnalyticsStatus status, long clientId, String h264Url, String mjpegUrl, AnalyticsModule module, AnalyticsHardwareInfoDTO hardwareInfo, ItemRestrictionsDTO accessRestrictions, Map<String, Boolean> currentUserRestrictions, Set<String> allowedServerIds, String topic, double progressPercentage, String progressTime, EventsHolderDTO eventsHolder, boolean h264Stream, List<AnalyticsErrorDTO> errors2, int parentId) {
        this.id = id;
        this.pluginName = pluginName;
        this.name = name;
        this.type = type;
        this.stream = stream;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.uptime = uptime;
        this.status = status;
        this.clientId = clientId;
        this.h264Url = h264Url;
        this.mjpegUrl = mjpegUrl;
        this.module = module;
        this.hardwareInfo = hardwareInfo;
        this.accessRestrictions = accessRestrictions;
        this.currentUserRestrictions = currentUserRestrictions;
        this.allowedServerIds = allowedServerIds;
        this.topic = topic;
        this.progressPercentage = progressPercentage;
        this.progressTime = progressTime;
        this.eventsHolder = eventsHolder;
        this.h264Stream = h264Stream;
        this.errors = errors2;
        this.parentId = parentId;
    }
}

