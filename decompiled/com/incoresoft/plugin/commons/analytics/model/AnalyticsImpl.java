/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.model;

import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsError;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.dto.analytics.VideoAnalyticsDTO;
import com.incoresoft.middleware.eventmanager.EventsHolder;
import com.incoresoft.middleware.model.analytics.AnalyticsHardwareInfo;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.analytics.dto.PluginAnalyticsWithGroupDTO;
import com.incoresoft.plugin.commons.analytics.events.EventsHolderImpl;
import com.incoresoft.plugin.commons.analytics.model.AnalyticsRestrictionsConverter;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class AnalyticsImpl
implements Analytics {
    private final int id;
    private final UUID uuid;
    private final String pluginName;
    private final String name;
    private final int streamId;
    private final Instant createdAt;
    private final Instant startInstant;
    private final AnalyticsStatus status;
    private final int clientId;
    private final UUID streamUUID;
    private final Set<String> allowedServerIds;
    private final RoleUserRestrictions restrictions;
    private final AnalyticsHardwareInfo hardwareInfo;
    private final EventsHolder eventsHolder;
    private final AnalyticsModule module;
    private final boolean h264Stream = false;
    private final int parentId;

    public AnalyticsImpl(VideoAnalyticsDTO videoAnalyticsDTO, String pluginName, User user, AnalyticsRestrictionsConverter analyticsRestrictionsConverter) {
        this.pluginName = pluginName;
        this.name = videoAnalyticsDTO.getStream().getName();
        this.module = videoAnalyticsDTO.getModule();
        this.streamId = videoAnalyticsDTO.getStreamId();
        this.streamUUID = videoAnalyticsDTO.getStream().getUUID();
        this.clientId = videoAnalyticsDTO.getStream().getClientId();
        this.restrictions = analyticsRestrictionsConverter.convert(user, videoAnalyticsDTO.getRestrictionsDTO());
        this.allowedServerIds = videoAnalyticsDTO.getAllowedServerIds();
        this.eventsHolder = new EventsHolderImpl(videoAnalyticsDTO.getEventsHolder());
        this.id = 0;
        this.createdAt = Instant.now();
        this.status = AnalyticsStatus.stopped;
        this.startInstant = null;
        this.hardwareInfo = null;
        this.uuid = AnalyticsImpl.generateTopic();
        this.parentId = 0;
    }

    public AnalyticsImpl(PluginAnalyticsWithGroupDTO dto, String pluginName, User user, AnalyticsRestrictionsConverter analyticsRestrictionsConverter) {
        this.pluginName = pluginName;
        this.name = dto.getStream().getName();
        this.module = dto.getModule();
        this.streamId = dto.getStreamId();
        this.streamUUID = dto.getStream().getUUID();
        this.clientId = dto.getStream().getClientId();
        this.restrictions = analyticsRestrictionsConverter.convert(user, dto.getRestrictionsDTO());
        this.allowedServerIds = dto.getAllowedServerIds();
        this.eventsHolder = new EventsHolderImpl(dto.getEventsHolder());
        this.id = 0;
        this.createdAt = Instant.now();
        this.status = AnalyticsStatus.stopped;
        this.startInstant = null;
        this.hardwareInfo = null;
        this.uuid = AnalyticsImpl.generateTopic();
        this.parentId = dto.getParentId();
    }

    public AnalyticsImpl(Analytics analytics, VideoAnalyticsDTO videoAnalyticsDTO, String pluginName, User user, AnalyticsRestrictionsConverter analyticsRestrictionsConverter) {
        this.pluginName = pluginName;
        this.name = videoAnalyticsDTO.getStream().getName();
        this.module = videoAnalyticsDTO.getModule();
        this.streamId = videoAnalyticsDTO.getStreamId();
        this.streamUUID = videoAnalyticsDTO.getStream().getUUID();
        this.clientId = videoAnalyticsDTO.getStream().getClientId();
        this.restrictions = analyticsRestrictionsConverter.convert(user, videoAnalyticsDTO.getRestrictionsDTO());
        this.allowedServerIds = videoAnalyticsDTO.getAllowedServerIds();
        this.eventsHolder = new EventsHolderImpl(videoAnalyticsDTO.getEventsHolder());
        this.parentId = videoAnalyticsDTO.getParentId();
        this.id = analytics.getId();
        this.createdAt = analytics.getCreatedAt();
        this.startInstant = analytics.getStartInstant();
        this.status = analytics.getStatus();
        this.hardwareInfo = analytics.getHardwareInfo();
        this.uuid = analytics.getUUID();
    }

    private static UUID generateTopic() {
        return UUID.randomUUID();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public String getPluginName() {
        return this.pluginName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getH264Preview() {
        return "";
    }

    @Override
    public String getMJPEGPreview() {
        return "";
    }

    @Override
    public AnalyticsModule getModule() {
        return this.module;
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
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public Instant getStartInstant() {
        return this.startInstant;
    }

    @Override
    public AnalyticsStatus getStatus() {
        return this.status;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getAllowedServerIds() {
        return this.allowedServerIds;
    }

    @Override
    public RoleUserRestrictions getRestrictions() {
        return this.restrictions;
    }

    @Override
    public AnalyticsHardwareInfo getHardwareInfo() {
        return this.hardwareInfo;
    }

    @Override
    public EventsHolder getEventsHolder() {
        return this.eventsHolder;
    }

    @Override
    public boolean isH264Stream() {
        return false;
    }

    @Override
    public Collection<? extends AnalyticsError> getErrors() {
        return Collections.emptyList();
    }

    @Override
    public int getParentId() {
        return this.parentId;
    }
}

