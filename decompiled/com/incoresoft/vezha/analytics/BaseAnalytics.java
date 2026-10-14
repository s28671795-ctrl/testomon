/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.analytics;

import com.alibaba.fastjson.JSONObject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsError;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.AnalyticsTypes;
import com.incoresoft.middleware.analytics.dto.events.EventsHolderDTO;
import com.incoresoft.middleware.analytics.models.polygon.Point;
import com.incoresoft.middleware.analytics.models.settings.HardwareSettingsImpl;
import com.incoresoft.middleware.analytics.models.settings.framesettings.FpsMode;
import com.incoresoft.middleware.analytics.models.zone.TimePeriod;
import com.incoresoft.middleware.analytics.models.zone.TimePeriodsZone;
import com.incoresoft.middleware.analytics.module.alpr.AlprAnalyticsModule;
import com.incoresoft.middleware.analytics.module.alpr.LprAdvancedSettings;
import com.incoresoft.middleware.analytics.module.alpr.LprRegion;
import com.incoresoft.middleware.analytics.module.alpr.SaveFrameOptions;
import com.incoresoft.middleware.analytics.module.face.FaceAdvancedSettings;
import com.incoresoft.middleware.analytics.module.face.FaceAnalyticsModule;
import com.incoresoft.middleware.eventmanager.EventsHolder;
import com.incoresoft.middleware.model.analytics.AnalyticsHardwareInfo;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.analytics.module.hardware.Hardware;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareAcceleration;
import com.incoresoft.middleware.model.analytics.module.hardware.HardwareDecode;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.permission.CorePermissions;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.plugin.commons.analytics.events.EventsHolderImpl;
import com.incoresoft.plugin.commons.analytics.model.AnalyticsRestrictions;
import com.incoresoft.vezha.analytics.errors.AnalyticsSideError;
import com.incoresoft.vezha.analytics.errors.BaseAnalyticsError;
import com.incoresoft.vezha.analytics.errors.CoreAnalyticsErrors;
import com.incoresoft.vezha.analytics.models.AnalyticsHardwareInfoImpl;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.status.AnalyticsHeartBeat;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import com.incoresoft.vezha.dto.analytics.AnalyticsStreamDTO;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.NotNull;

public final class BaseAnalytics
implements Analytics {
    public static int ITEM_NOT_SAVED = -2;
    private static int ROOT_GROUP = 0;
    private final int id;
    private final UUID uuid;
    private final String name;
    private final String pluginName;
    private final Instant createdAt;
    private final Set<String> allowedServerIds;
    private final Boolean disableBalancing;
    private final RoleUserRestrictions restrictions;
    private final int streamId;
    private final UUID streamUUID;
    private final AnalyticsModule module;
    private final EventsHolder eventsHolder;
    private final AnalyticsStatusImpl analyticsStatus;
    private final AtomicReference<AnalyticsHeartBeat> heartBeat;
    private final int parentId;
    private final int clientId;

    public BaseAnalytics(Stream stream, AnalyticsTypes type, String serverId) {
        this.id = ITEM_NOT_SAVED;
        this.name = stream.getName();
        this.pluginName = type.getName();
        this.allowedServerIds = Set.of(serverId);
        this.restrictions = new AnalyticsRestrictions(Set.of(CorePermissions.StartAnalytics, CorePermissions.StopAnalytics, CorePermissions.EditAnalytics, CorePermissions.ViewAnalyticsLive, CorePermissions.ViewAnalyticsEvents));
        this.streamId = stream.getId();
        this.streamUUID = stream.getUUID();
        this.module = switch (Objects.requireNonNull(type)) {
            case AnalyticsTypes.alpr -> BaseAnalytics.defaultAlprModule();
            case AnalyticsTypes.face -> BaseAnalytics.defaultFaceModule();
            default -> throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        };
        this.eventsHolder = new EventsHolderImpl(new EventsHolderDTO(false, Collections.emptyList()));
        this.uuid = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.disableBalancing = true;
        this.analyticsStatus = null;
        this.heartBeat = new AtomicReference<Object>(null);
        this.parentId = ROOT_GROUP;
        this.clientId = stream.getClientId();
    }

    public static AnalyticsModule defaultAlprModule() {
        AlprAnalyticsModule module = new AlprAnalyticsModule();
        module.setPolygons(BaseAnalytics.defaultPolygons());
        module.setRegion(LprRegion.EUROPE);
        module.setVehicle_wo_number(false);
        module.setBlurInPreview(false);
        module.setAdvancedSettings(new LprAdvancedSettings(40, 15, 60, "0", 4, 15, false, Collections.emptyList(), Float.valueOf(0.5f), SaveFrameOptions.plate, 0));
        module.setHardwareSettings(BaseAnalytics.defaultHardwareSettings());
        return module;
    }

    public static AnalyticsModule defaultFaceModule() {
        FaceAnalyticsModule module = new FaceAnalyticsModule();
        module.setPolygons(BaseAnalytics.defaultPolygons());
        module.setMinFaceHeight(40);
        module.setMaxFaceHeight(0);
        module.setCameraBuffer(0);
        module.setFramesToDetect(25);
        module.setBlurInPreview(false);
        module.setGenderAge(true);
        module.setDetectorSensitivity(1);
        module.setMask(false);
        module.setRace(false);
        module.setSpoofed(false);
        module.setSaveBestFace(true);
        module.setAdvancedSettings(new FaceAdvancedSettings(10));
        module.setHardwareSettings(BaseAnalytics.defaultHardwareSettings());
        return module;
    }

    private static TimePeriodsZone @NotNull [] defaultPolygons() {
        return new TimePeriodsZone[]{new TimePeriodsZone(UUID.randomUUID().toString(), "Zone 1", new Point[]{new Point(0.0, 0.0), new Point(1.0, 0.0), new Point(1.0, 1.0), new Point(0.0, 1.0)}, "#8E50ED", new TimePeriod[]{new TimePeriod("00:00:00", "23:59:59", new int[]{0, 1, 2, 3, 4, 5, 6})})};
    }

    @NotNull
    private static HardwareSettingsImpl defaultHardwareSettings() {
        return new HardwareSettingsImpl(Hardware.cpu, HardwareAcceleration.none, HardwareDecode.nvidia, new FpsMode(25), true, false);
    }

    public BaseAnalytics(Analytics analytics, BaseAnalytics baseAnalytics) {
        this.id = analytics.getId();
        this.name = analytics.getName();
        this.pluginName = analytics.getPluginName();
        this.allowedServerIds = analytics.getAllowedServerIds();
        this.restrictions = analytics.getRestrictions();
        this.streamId = analytics.getStreamId();
        this.streamUUID = analytics.getStreamUUID();
        this.module = analytics.getModule();
        this.eventsHolder = analytics.getEventsHolder();
        this.parentId = analytics.getParentId();
        this.uuid = baseAnalytics.getUUID();
        this.createdAt = baseAnalytics.getCreatedAt();
        this.disableBalancing = baseAnalytics.getDisableBalancing();
        this.analyticsStatus = baseAnalytics.getAnalyticsStatus();
        this.heartBeat = baseAnalytics.getHeartBeat();
        this.clientId = baseAnalytics.getClientId();
    }

    public void setStoppedStatus() {
        this.analyticsStatus.setStatusValue(AnalyticsStatus.stopped);
        this.analyticsStatus.clearErrors();
    }

    public void stopAnalytics() {
        this.heartBeat.set(null);
        this.changeStatus(AnalyticsStatus.stopped, null, null, null, true);
    }

    public void setPausedStatus() {
        this.analyticsStatus.setStatusValue(AnalyticsStatus.paused);
    }

    public void pauseAnalytics() {
        this.heartBeat.set(null);
        this.changeStatus(AnalyticsStatus.paused, null, null, null, true);
    }

    public void setStartedStatus(Server server) {
        Optional.ofNullable(this.getHeartBeatValue()).orElseThrow(() -> new IllegalStateException("Heartbeat is null"));
        this.changeStatus(AnalyticsStatus.started, server, this.getHeartBeatValue().getGpuId(), Instant.now(), false);
    }

    public AnalyticsHeartBeat getHeartBeatValue() {
        if (this.heartBeat.get() == null) {
            return null;
        }
        return this.heartBeat.get();
    }

    public void setHeartBeatValue(AnalyticsHeartBeat analyticsHeartBeat) {
        this.heartBeat.set(analyticsHeartBeat);
    }

    public void setProcessingStatus() {
        this.analyticsStatus.setStatusValue(AnalyticsStatus.processing);
    }

    public void changeStatus(AnalyticsStatus status, Server server, Integer gpuId, Instant startAt, boolean resetEncryption) {
        this.analyticsStatus.changeStatus(status, server, gpuId, startAt, resetEncryption);
    }

    public String initEncryptionKey() {
        return this.analyticsStatus.initEncryption();
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public String getH264Preview() {
        return this.analyticsStatus.getH264Preview(this.id);
    }

    @Override
    public String getMJPEGPreview() {
        return this.analyticsStatus.getMJPEGPreview(this.id);
    }

    @Override
    public AnalyticsModule getModule() {
        return this.module;
    }

    @Override
    public Instant getStartInstant() {
        return Optional.ofNullable(this.analyticsStatus).map(AnalyticsStatusImpl::getStartInstant).orElse(null);
    }

    @Override
    public AnalyticsStatus getStatus() {
        return Optional.ofNullable(this.analyticsStatus).map(AnalyticsStatusImpl::getStatus).map(AtomicReference::get).orElse(AnalyticsStatus.stopped);
    }

    public JSONObject runParams(Stream stream, boolean isVezhaInIntegrationMode) {
        return new JSONObject().fluentPut("module", this.module).fluentPut("stream", new AnalyticsStreamDTO(stream, isVezhaInIntegrationMode));
    }

    @Override
    public AnalyticsHardwareInfo getHardwareInfo() {
        if (this.analyticsStatus.getStatusValue() != AnalyticsStatus.started) {
            return null;
        }
        return new AnalyticsHardwareInfoImpl(this);
    }

    @Override
    public boolean isH264Stream() {
        if (this.module.getHardwareSettings().getDecoding() != HardwareDecode.nvidia) {
            return false;
        }
        return this.analyticsStatus.getH264Stream();
    }

    public boolean isServerAllowed(Server server) {
        return this.allowedServerIds == null || this.allowedServerIds.contains(server.getId());
    }

    @Override
    public Collection<? extends AnalyticsError> getErrors() {
        return this.analyticsStatus.getErrors();
    }

    @Override
    public int getParentId() {
        return this.parentId;
    }

    public void setAnalyticsSideErrors(Set<AnalyticsSideError> errors2) {
        this.analyticsStatus.setAnalyticsSideErrors(errors2);
    }

    public void setErrors(@NotNull Set<BaseAnalyticsError> errors2) {
        this.analyticsStatus.setErrors(errors2);
    }

    public void addErrorIfAbsent(@NotNull BaseAnalyticsError error) {
        this.analyticsStatus.addError(error);
    }

    public void removeErrorIfPresent(@NotNull BaseAnalyticsError error) {
        this.analyticsStatus.removeError(error);
    }

    public boolean isServerWrong() {
        String serverHeartbeat;
        if (this.analyticsStatus.getServerValue() == null || this.heartBeat.get() == null) {
            return false;
        }
        String server = this.analyticsStatus.getServerValue().getId();
        return !Objects.equals(server, serverHeartbeat = (String)Optional.ofNullable(this.getHeartBeatValue()).map(AnalyticsHeartBeat::getServerId).orElse(null));
    }

    public boolean isHeartbeatTimeout() {
        return Optional.ofNullable(this.getHeartBeatValue()).map(AnalyticsHeartBeat::getTimestamp).filter(timestamp -> timestamp.plusSeconds(60L).isAfter(Instant.now())).isEmpty();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        BaseAnalytics analytics = (BaseAnalytics)o;
        return this.id == analytics.id;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.createdAt);
    }

    public boolean hasError(CoreAnalyticsErrors coreAnalyticsErrors) {
        return this.analyticsStatus.getErrors().contains(coreAnalyticsErrors);
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
    public String getPluginName() {
        return this.pluginName;
    }

    @Override
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public Set<String> getAllowedServerIds() {
        return this.allowedServerIds;
    }

    public Boolean getDisableBalancing() {
        return this.disableBalancing;
    }

    @Override
    public RoleUserRestrictions getRestrictions() {
        return this.restrictions;
    }

    @Override
    public int getStreamId() {
        return this.streamId;
    }

    @Override
    public UUID getStreamUUID() {
        return this.streamUUID;
    }

    @Override
    public EventsHolder getEventsHolder() {
        return this.eventsHolder;
    }

    public AnalyticsStatusImpl getAnalyticsStatus() {
        return this.analyticsStatus;
    }

    public AtomicReference<AnalyticsHeartBeat> getHeartBeat() {
        return this.heartBeat;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public String toString() {
        return "BaseAnalytics(id=" + this.getId() + ", uuid=" + String.valueOf(this.getUUID()) + ", name=" + this.getName() + ", pluginName=" + this.getPluginName() + ", createdAt=" + String.valueOf(this.getCreatedAt()) + ", allowedServerIds=" + String.valueOf(this.getAllowedServerIds()) + ", disableBalancing=" + this.getDisableBalancing() + ", restrictions=" + String.valueOf(this.getRestrictions()) + ", streamId=" + this.getStreamId() + ", streamUUID=" + String.valueOf(this.getStreamUUID()) + ", module=" + String.valueOf(this.getModule()) + ", eventsHolder=" + String.valueOf(this.getEventsHolder()) + ", analyticsStatus=" + String.valueOf(this.getAnalyticsStatus()) + ", heartBeat=" + String.valueOf(this.getHeartBeat()) + ", parentId=" + this.getParentId() + ", clientId=" + this.getClientId() + ")";
    }

    public BaseAnalytics(int id, UUID uuid, String name, String pluginName, Instant createdAt, Set<String> allowedServerIds, Boolean disableBalancing, RoleUserRestrictions restrictions, int streamId, UUID streamUUID, AnalyticsModule module, EventsHolder eventsHolder, AnalyticsStatusImpl analyticsStatus, AtomicReference<AnalyticsHeartBeat> heartBeat, int parentId, int clientId) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.pluginName = pluginName;
        this.createdAt = createdAt;
        this.allowedServerIds = allowedServerIds;
        this.disableBalancing = disableBalancing;
        this.restrictions = restrictions;
        this.streamId = streamId;
        this.streamUUID = streamUUID;
        this.module = module;
        this.eventsHolder = eventsHolder;
        this.analyticsStatus = analyticsStatus;
        this.heartBeat = heartBeat;
        this.parentId = parentId;
        this.clientId = clientId;
    }

    public BaseAnalytics withName(String name) {
        return this.name == name ? this : new BaseAnalytics(this.id, this.uuid, name, this.pluginName, this.createdAt, this.allowedServerIds, this.disableBalancing, this.restrictions, this.streamId, this.streamUUID, this.module, this.eventsHolder, this.analyticsStatus, this.heartBeat, this.parentId, this.clientId);
    }
}

