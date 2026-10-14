/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated;

import com.incoresoft.vezha.generated.DefaultCatalog;
import com.incoresoft.vezha.generated.tables.AggregatedMetricsAnalyticsServers;
import com.incoresoft.vezha.generated.tables.AggregatedMetricsAnalyticsServersGpu;
import com.incoresoft.vezha.generated.tables.AggregatedMetricsCore;
import com.incoresoft.vezha.generated.tables.Analytics;
import com.incoresoft.vezha.generated.tables.AnalyticsGroups;
import com.incoresoft.vezha.generated.tables.AxisStationProSettings;
import com.incoresoft.vezha.generated.tables.Backups;
import com.incoresoft.vezha.generated.tables.EventManager;
import com.incoresoft.vezha.generated.tables.Roles;
import com.incoresoft.vezha.generated.tables.Servers;
import com.incoresoft.vezha.generated.tables.StreamGroups;
import com.incoresoft.vezha.generated.tables.Streams;
import com.incoresoft.vezha.generated.tables.Users;
import com.incoresoft.vezha.generated.tables.WindowsShareFolderConfigs;
import java.util.Arrays;
import java.util.List;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

public class DefaultSchema
extends SchemaImpl {
    private static final long serialVersionUID = 1L;
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();
    public final AggregatedMetricsAnalyticsServers AGGREGATED_METRICS_ANALYTICS_SERVERS = AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS;
    public final AggregatedMetricsAnalyticsServersGpu AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU = AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU;
    public final AggregatedMetricsCore AGGREGATED_METRICS_CORE = AggregatedMetricsCore.AGGREGATED_METRICS_CORE;
    public final Analytics ANALYTICS = Analytics.ANALYTICS;
    public final AnalyticsGroups ANALYTICS_GROUPS = AnalyticsGroups.ANALYTICS_GROUPS;
    public final AxisStationProSettings AXIS_STATION_PRO_SETTINGS = AxisStationProSettings.AXIS_STATION_PRO_SETTINGS;
    public final Backups BACKUPS = Backups.BACKUPS;
    public final EventManager EVENT_MANAGER = EventManager.EVENT_MANAGER;
    public final Roles ROLES = Roles.ROLES;
    public final Servers SERVERS = Servers.SERVERS;
    public final StreamGroups STREAM_GROUPS = StreamGroups.STREAM_GROUPS;
    public final Streams STREAMS = Streams.STREAMS;
    public final Users USERS = Users.USERS;
    public final WindowsShareFolderConfigs WINDOWS_SHARE_FOLDER_CONFIGS = WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS;

    private DefaultSchema() {
        super("", null);
    }

    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(AggregatedMetricsAnalyticsServers.AGGREGATED_METRICS_ANALYTICS_SERVERS, AggregatedMetricsAnalyticsServersGpu.AGGREGATED_METRICS_ANALYTICS_SERVERS_GPU, AggregatedMetricsCore.AGGREGATED_METRICS_CORE, Analytics.ANALYTICS, AnalyticsGroups.ANALYTICS_GROUPS, AxisStationProSettings.AXIS_STATION_PRO_SETTINGS, Backups.BACKUPS, EventManager.EVENT_MANAGER, Roles.ROLES, Servers.SERVERS, StreamGroups.STREAM_GROUPS, Streams.STREAMS, Users.USERS, WindowsShareFolderConfigs.WINDOWS_SHARE_FOLDER_CONFIGS);
    }
}

