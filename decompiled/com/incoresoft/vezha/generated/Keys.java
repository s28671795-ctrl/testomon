/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated;

import com.incoresoft.vezha.generated.tables.AggregatedMetricsAnalyticsServers;
import com.incoresoft.vezha.generated.tables.AggregatedMetricsAnalyticsServersGpu;
import com.incoresoft.vezha.generated.tables.AggregatedMetricsCore;
import com.incoresoft.vezha.generated.tables.Analytics;
import com.incoresoft.vezha.generated.tables.AnalyticsGroups;
import com.incoresoft.vezha.generated.tables.Backups;
import com.incoresoft.vezha.generated.tables.EventManager;
import com.incoresoft.vezha.generated.tables.Roles;
import com.incoresoft.vezha.generated.tables.Servers;
import com.incoresoft.vezha.generated.tables.StreamGroups;
import com.incoresoft.vezha.generated.tables.Streams;
import com.incoresoft.vezha.generated.tables.Users;
import com.incoresoft.vezha.generated.tables.WindowsShareFolderConfigs;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsAnalyticsServersGpuRecord;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsAnalyticsServersRecord;
import com.incoresoft.vezha.generated.tables.records.AggregatedMetricsCoreRecord;
import com.incoresoft.vezha.generated.tables.records.AnalyticsGroupsRecord;
import com.incoresoft.vezha.generated.tables.records.AnalyticsRecord;
import com.incoresoft.vezha.generated.tables.records.BackupsRecord;
import com.incoresoft.vezha.generated.tables.records.EventManagerRecord;
import com.incoresoft.vezha.generated.tables.records.RolesRecord;
import com.incoresoft.vezha.generated.tables.records.ServersRecord;
import com.incoresoft.vezha.generated.tables.records.StreamGroupsRecord;
import com.incoresoft.vezha.generated.tables.records.StreamsRecord;
import com.incoresoft.vezha.generated.tables.records.UsersRecord;
import com.incoresoft.vezha.generated.tables.records.WindowsShareFolderConfigsRecord;
import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

public class Keys {
    public static final UniqueKey<AggregatedMetricsAnalyticsServersRecord> PK_AGGREGATED_METRICS_ANALYTICS_SERVERS = Internal.createUniqueKey(AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS, DSL.name("PK_AGGREGATED_METRICS_ANALYTICS_SERVERS"), new TableField[]{AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.ID}, true);
    public static final UniqueKey<AggregatedMetricsAnalyticsServersGpuRecord> PK_AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU = Internal.createUniqueKey(AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU, DSL.name("PK_AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU"), new TableField[]{AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.ID}, true);
    public static final UniqueKey<AggregatedMetricsCoreRecord> CONSTRAINT_3 = Internal.createUniqueKey(AggregatedMetricsCore.AGGREGATED_METRICS_CORE, DSL.name("CONSTRAINT_3"), new TableField[]{AggregatedMetricsCore.AGGREGATED_METRICS_CORE.START_TIME}, true);
    public static final UniqueKey<AggregatedMetricsCoreRecord> PK_AGGREGATED_METRICS_CORE = Internal.createUniqueKey(AggregatedMetricsCore.AGGREGATED_METRICS_CORE, DSL.name("PK_AGGREGATED_METRICS_CORE"), new TableField[]{AggregatedMetricsCore.AGGREGATED_METRICS_CORE.ID}, true);
    public static final UniqueKey<AnalyticsRecord> PK_ANALYTICS = Internal.createUniqueKey(Analytics.ANALYTICS, DSL.name("PK_ANALYTICS"), new TableField[]{Analytics.ANALYTICS.ID}, true);
    public static final UniqueKey<AnalyticsGroupsRecord> PK_ANALYTICS_GROUPS = Internal.createUniqueKey(AnalyticsGroups.ANALYTICS_GROUPS, DSL.name("PK_ANALYTICS_GROUPS"), new TableField[]{AnalyticsGroups.ANALYTICS_GROUPS.ID}, true);
    public static final UniqueKey<BackupsRecord> PK_BACKUPS = Internal.createUniqueKey(Backups.BACKUPS, DSL.name("PK_BACKUPS"), new TableField[]{Backups.BACKUPS.ID}, true);
    public static final UniqueKey<EventManagerRecord> CONSTRAINT_7 = Internal.createUniqueKey(EventManager.EVENT_MANAGER, DSL.name("CONSTRAINT_7"), new TableField[]{EventManager.EVENT_MANAGER.UUID}, true);
    public static final UniqueKey<EventManagerRecord> PK_EVENT_MANAGER_TEMP = Internal.createUniqueKey(EventManager.EVENT_MANAGER, DSL.name("PK_EVENT_MANAGER_TEMP"), new TableField[]{EventManager.EVENT_MANAGER.ID}, true);
    public static final UniqueKey<RolesRecord> PK_ROLES = Internal.createUniqueKey(Roles.ROLES, DSL.name("PK_ROLES"), new TableField[]{Roles.ROLES.ID}, true);
    public static final UniqueKey<ServersRecord> PK_SERVERS = Internal.createUniqueKey(Servers.SERVERS, DSL.name("PK_SERVERS"), new TableField[]{Servers.SERVERS.ID}, true);
    public static final UniqueKey<StreamGroupsRecord> PK_STREAM_GROUPS = Internal.createUniqueKey(StreamGroups.STREAM_GROUPS, DSL.name("PK_STREAM_GROUPS"), new TableField[]{StreamGroups.STREAM_GROUPS.ID}, true);
    public static final UniqueKey<StreamsRecord> PK_STREAMS = Internal.createUniqueKey(Streams.STREAMS, DSL.name("PK_STREAMS"), new TableField[]{Streams.STREAMS.ID}, true);
    public static final UniqueKey<UsersRecord> PK_USERS = Internal.createUniqueKey(Users.USERS, DSL.name("PK_USERS"), new TableField[]{Users.USERS.ID}, true);
    public static final UniqueKey<WindowsShareFolderConfigsRecord> PK_WINDOWS_SHARE_FOLDER_CONFIGS = Internal.createUniqueKey(WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS, DSL.name("PK_WINDOWS_SHARE_FOLDER_CONFIGS"), new TableField[]{WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS.ID}, true);
    public static final ForeignKey<AggregatedMetricsAnalyticsServersRecord, AggregatedMetricsCoreRecord> FK__METRICS_ANALYTICS_SERVERS__METRICS_CORE = Internal.createForeignKey(AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS, DSL.name("FK__metrics_analytics_servers__metrics_core"), new TableField[]{AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.START_TIME}, CONSTRAINT_3, new TableField[]{AggregatedMetricsCore.AGGREGATED_METRICS_CORE.START_TIME}, true);
    public static final ForeignKey<AggregatedMetricsAnalyticsServersGpuRecord, AggregatedMetricsAnalyticsServersRecord> FK__METRICS_ANALYTICS_SERVERS_GPU__METRICS_ANALYTICS_SERVERS = Internal.createForeignKey(AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU, DSL.name("FK__metrics_analytics_servers_gpu__metrics_analytics_servers"), new TableField[]{AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU.SERVER_METRICS_ID}, PK_AGGREGATED_METRICS_ANALYTICS_SERVERS, new TableField[]{AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS.ID}, true);
}

