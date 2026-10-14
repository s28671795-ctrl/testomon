/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.incoresoft.middleware.analytics.AnalyticsGroup;
import com.incoresoft.plugin.commons.database.CRUDConverter;
import com.incoresoft.vezha.generated.tables.records.AnalyticsGroupsRecord;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalyticsGroupsConverter
implements CRUDConverter<AnalyticsGroupsRecord, AnalyticsGroup> {
    private static final Logger log = LoggerFactory.getLogger(AnalyticsGroupsConverter.class);

    @Override
    public AnalyticsGroup from(AnalyticsGroupsRecord record) {
        return new AnalyticsGroup(record.getId(), record.getName(), record.getPluginName(), record.getParentId(), record.getClientId());
    }

    @Override
    public AnalyticsGroupsRecord to(AnalyticsGroup group) {
        return new AnalyticsGroupsRecord(group.getId(), group.getName(), group.getParentId(), group.getPluginName(), group.getClientId());
    }

    @Override
    @NotNull
    public Class<AnalyticsGroupsRecord> fromType() {
        return AnalyticsGroupsRecord.class;
    }

    @Override
    @NotNull
    public Class<AnalyticsGroup> toType() {
        return AnalyticsGroup.class;
    }
}

