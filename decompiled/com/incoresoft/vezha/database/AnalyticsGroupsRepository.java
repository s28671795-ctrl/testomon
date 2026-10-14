/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.extensions.ExtensionsManager;
import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.plugin.SystemExtension;
import com.incoresoft.middleware.plugin.VezhaExtension;
import com.incoresoft.vezha.database.converters.AnalyticsGroupsConverter;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.records.AnalyticsGroupsRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jooq.Condition;
import org.jooq.DSLContext;

public class AnalyticsGroupsRepository {
    private final DSLContext context;
    private final AnalyticsGroupsConverter converter;
    private final ExtensionsManager extensionsManager;

    public List<AnalyticsGroup> getAll() {
        Set plugins = this.extensionsManager.getAll(VezhaExtension.class).stream().map(SystemExtension::getPluginName).collect(Collectors.toSet());
        return this.context.selectFrom(Tables.ANALYTICS_GROUPS).where(Tables.ANALYTICS_GROUPS.PLUGIN_NAME.in(plugins)).fetch(this.converter::from);
    }

    public List<AnalyticsGroup> getAll(String pluginId, User user) {
        ArrayList<Condition> conditions = new ArrayList<Condition>();
        conditions.add(Tables.ANALYTICS_GROUPS.PLUGIN_NAME.eq(pluginId));
        if (!user.isRoot()) {
            conditions.add(Tables.ANALYTICS_GROUPS.CLIENT_ID.eq(user.getClientId()));
        }
        return this.context.selectFrom(Tables.ANALYTICS_GROUPS).where(conditions).fetch(this.converter::from);
    }

    public AnalyticsGroup add(AnalyticsGroup analyticsGroup) {
        AnalyticsGroupsRecord record = this.context.newRecord(Tables.ANALYTICS_GROUPS);
        record.from((Object)this.converter.to(analyticsGroup));
        record.reset(Tables.ANALYTICS_GROUPS.ID);
        record.insert();
        return this.converter.from(record);
    }

    public AnalyticsGroup update(AnalyticsGroup analyticsGroup) {
        AnalyticsGroupsRecord record = this.context.newRecord(Tables.ANALYTICS_GROUPS, this.converter.to(analyticsGroup));
        record.changed(Tables.ANALYTICS_GROUPS.ID, false);
        record.update();
        return this.converter.from(record);
    }

    public void delete(int id) {
        this.context.deleteFrom(Tables.ANALYTICS_GROUPS).where(Tables.ANALYTICS_GROUPS.ID.eq(id)).execute();
    }

    public Optional<AnalyticsGroup> get(int id) {
        return this.context.selectFrom(Tables.ANALYTICS_GROUPS).where(Tables.ANALYTICS_GROUPS.ID.eq(id)).fetchOptional(this.converter::from);
    }

    @Inject
    public AnalyticsGroupsRepository(DSLContext context, AnalyticsGroupsConverter converter, ExtensionsManager extensionsManager) {
        this.context = context;
        this.converter = converter;
        this.extensionsManager = extensionsManager;
    }
}

