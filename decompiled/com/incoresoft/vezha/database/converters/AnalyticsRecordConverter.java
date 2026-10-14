/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.alibaba.fastjson.JSON;
import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.analytics.dto.restrictions.ItemRestrictionsDTO;
import com.incoresoft.middleware.model.analytics.module.AnalyticsModule;
import com.incoresoft.middleware.model.itemaccess.RoleUserRestrictions;
import com.incoresoft.middleware.model.role.RolesManager;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.middleware.model.user.UsersManager;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.analytics.servers.Server;
import com.incoresoft.vezha.analytics.servers.ServersMonitor;
import com.incoresoft.vezha.analytics.status.AnalyticsStatusImpl;
import com.incoresoft.vezha.database.converters.CoreAnalyticsRestrictionsConverter;
import com.incoresoft.vezha.database.converters.RestrictionsConverter;
import com.incoresoft.vezha.generated.tables.records.AnalyticsRecord;
import com.incoresoft.vezha.plugins.VezhaExtensionsManager;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.NotNull;
import org.jooq.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsRecordConverter
implements Converter<AnalyticsRecord, Analytics> {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsRecordConverter.class);
    private final ServersMonitor serversMonitor;
    private final RestrictionsConverter restrictionsConverter;
    private final VezhaExtensionsManager extensionsManager;
    private final RolesManager rolesManager;
    private final UsersManager usersManager;
    private final StreamsManager streamsManager;

    @Override
    public BaseAnalytics from(AnalyticsRecord record) {
        Class<? extends AnalyticsModule> moduleClass = this.extensionsManager.getExtensionsModuleClass(record.getType());
        if (Objects.isNull(moduleClass)) {
            return null;
        }
        AnalyticsModule analyticsModule = JSON.parseObject(record.getModule(), moduleClass);
        RoleUserRestrictions restrictions = this.createRoleUserRestrictions(record);
        AnalyticsStatusImpl analyticsStatus = this.convertToAnalyticsStatus(record);
        return this.constructBaseAnalytics(record, restrictions, analyticsModule, analyticsStatus);
    }

    private RoleUserRestrictions createRoleUserRestrictions(AnalyticsRecord record) {
        RoleUserRestrictions restrictions = null;
        if (record.getRestrictions() != null) {
            ItemRestrictionsDTO itemRestrictionsDTO = JSON.parseObject(record.getRestrictions(), ItemRestrictionsDTO.class);
            restrictions = this.rolesConvertor(itemRestrictionsDTO).convert();
        }
        return restrictions;
    }

    private AnalyticsStatusImpl convertToAnalyticsStatus(AnalyticsRecord record) {
        return new AnalyticsStatusImpl(AnalyticsStatus.valueOf(record.getStatus()), Optional.ofNullable(record.getLastGpuId()).orElse(-1), Optional.ofNullable(record.getDesiredServerId()).map(this.serversMonitor::getServerById).orElse(null), record.getStartSignature(), Optional.ofNullable(record.getStartAt()).map(Instant::toEpochMilli).orElse(0L));
    }

    private BaseAnalytics constructBaseAnalytics(AnalyticsRecord record, RoleUserRestrictions restrictions, AnalyticsModule analyticsModule, AnalyticsStatusImpl analyticsStatus) {
        HashSet allowedServerIds = Optional.ofNullable(record.getAllowedServerIds()).map(JSON::parseArray).map(array -> new HashSet<String>(array.toJavaList(String.class))).orElse(null);
        int streamId = Optional.ofNullable(record.getStreamUuid()).map(this.streamsManager::get).map(Stream::getId).orElse(0);
        return new BaseAnalytics(record.getId(), record.getUuid(), record.getName(), record.getPluginName(), record.getCreatedAt(), allowedServerIds, record.getDisableBalancing(), restrictions, streamId, record.getStreamUuid(), analyticsModule, record.getEventsHolder(), analyticsStatus, new AtomicReference<Object>(null), record.getGroupId(), record.getClientId());
    }

    private CoreAnalyticsRestrictionsConverter rolesConvertor(ItemRestrictionsDTO itemRestrictionsDTO) {
        return new CoreAnalyticsRestrictionsConverter(this.rolesManager, this.usersManager, itemRestrictionsDTO);
    }

    @Override
    public AnalyticsRecord to(Analytics analytics) {
        AnalyticsRecord record = new AnalyticsRecord();
        this.fillRecordFromAnalytics(record, analytics);
        return record;
    }

    private void fillRecordFromAnalytics(AnalyticsRecord record, Analytics analytics) {
        record.setId(analytics.getId());
        record.setUuid(analytics.getUUID());
        String className = analytics.getModule().getClass().getName().replaceFirst(analytics.getModule().getClass().getPackageName(), "").replace(".", "");
        record.setType(className);
        record.setName(analytics.getName());
        record.setPluginName(analytics.getPluginName());
        record.setCreatedAt(analytics.getCreatedAt());
        record.setStartAt(analytics.getStartInstant());
        record.setStatus(Optional.ofNullable(analytics.getStatus()).map(Enum::name).orElse(AnalyticsStatus.stopped.name()));
        record.setClientId(analytics.getClientId());
        record.setStreamUuid(analytics.getStreamUUID());
        record.setModule(JSON.toJSONString(analytics.getModule()));
        record.setAllowedServerIds(Optional.of(analytics).map(Analytics::getAllowedServerIds).map(JSON::toJSONString).orElse(null));
        record.setRestrictions(this.restrictionsConverter.to(analytics.getRestrictions()));
        record.setEventsHolder(analytics.getEventsHolder());
        record.setGroupId(analytics.getParentId());
        if (analytics instanceof BaseAnalytics) {
            BaseAnalytics item = (BaseAnalytics)analytics;
            this.fillRecordFromBaseAnalytics(record, item);
        }
    }

    private void fillRecordFromBaseAnalytics(AnalyticsRecord record, BaseAnalytics item) {
        Optional.of(item).map(BaseAnalytics::getAnalyticsStatus).map(AnalyticsStatusImpl::getStartSignatureValue).ifPresent(record::setStartSignature);
        Optional.of(item).map(BaseAnalytics::getAnalyticsStatus).map(AnalyticsStatusImpl::getLastGpuIdValue).ifPresent(record::setLastGpuId);
        Optional.of(item).map(BaseAnalytics::getDisableBalancing).ifPresent(record::setDisableBalancing);
        Optional.of(item).map(BaseAnalytics::getAnalyticsStatus).map(AnalyticsStatusImpl::getServerValue).map(Server::getId).ifPresent(record::setDesiredServerId);
    }

    @Override
    @NotNull
    public Class<AnalyticsRecord> fromType() {
        return AnalyticsRecord.class;
    }

    @Override
    @NotNull
    public Class<Analytics> toType() {
        return Analytics.class;
    }

    @Inject
    public AnalyticsRecordConverter(ServersMonitor serversMonitor, RestrictionsConverter restrictionsConverter, VezhaExtensionsManager extensionsManager, RolesManager rolesManager, UsersManager usersManager, StreamsManager streamsManager) {
        this.serversMonitor = serversMonitor;
        this.restrictionsConverter = restrictionsConverter;
        this.extensionsManager = extensionsManager;
        this.rolesManager = rolesManager;
        this.usersManager = usersManager;
        this.streamsManager = streamsManager;
    }
}

