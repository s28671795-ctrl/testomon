/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsStatus;
import com.incoresoft.middleware.model.stream.Stream;
import com.incoresoft.middleware.model.stream.StreamType;
import com.incoresoft.middleware.model.stream.StreamsManager;
import com.incoresoft.vezha.analytics.BaseAnalytics;
import com.incoresoft.vezha.database.converters.AnalyticsRecordConverter;
import com.incoresoft.vezha.generated.Tables;
import com.incoresoft.vezha.generated.tables.records.AnalyticsRecord;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.DSLContext;

public class AnalyticsRepository {
    private final DSLContext create;
    private final AnalyticsRecordConverter converter;
    private final StreamsManager streamManager;

    public List<BaseAnalytics> getItems(String pluginName) {
        return this.getItems(List.of(Tables.ANALYTICS.PLUGIN_NAME.eq(pluginName)));
    }

    public List<BaseAnalytics> getItems() {
        return this.getItems(Collections.emptyList());
    }

    @NotNull
    private List<BaseAnalytics> getItems(List<Condition> conditions) {
        return this.create.selectFrom(Tables.ANALYTICS).where(conditions).fetch().map(this.converter::from).stream().filter(Objects::nonNull).toList();
    }

    public Optional<BaseAnalytics> get(Integer id) {
        return this.create.selectFrom(Tables.ANALYTICS).where(Tables.ANALYTICS.ID.eq(id)).fetchOptional(this.converter::from);
    }

    public BaseAnalytics add(Analytics analytics) {
        AnalyticsRecord record = this.create.newRecord(Tables.ANALYTICS);
        record.from((Object)this.converter.to(analytics));
        record.reset(Tables.ANALYTICS.ID);
        record.insert();
        return this.converter.from(record);
    }

    public void update(Analytics analytics) {
        AnalyticsRecord record = this.create.newRecord(Tables.ANALYTICS);
        record.from((Object)this.converter.to(analytics));
        record.changed(Tables.ANALYTICS.ID, false);
        Stream stream = this.streamManager.get(analytics.getStreamUUID());
        if (stream != null && stream.getType() == StreamType.embedded) {
            record.reset(Tables.ANALYTICS.ALLOWED_SERVER_IDS);
        }
        record.update();
    }

    public Analytics archive(Integer id) {
        AnalyticsRecord record = this.create.newRecord(Tables.ANALYTICS);
        record.setId(id);
        record.setStatus(AnalyticsStatus.deleted.name());
        record.update(Tables.ANALYTICS.STATUS);
        return this.get(id).orElseThrow();
    }

    public void delete(Integer id) {
        AnalyticsRecord record = this.create.newRecord(Tables.ANALYTICS);
        record.setId(id);
        record.delete();
    }

    public void renameAll(Map<Integer, String> analyticsRenameMap, DSLContext transaction) {
        List<AnalyticsRecord> records = analyticsRenameMap.entrySet().stream().map(it -> this.getRenamedRecord((Integer)it.getKey(), (String)it.getValue())).toList();
        transaction.batchUpdate(records).execute();
    }

    private AnalyticsRecord getRenamedRecord(Integer id, String newName) {
        AnalyticsRecord record = new AnalyticsRecord();
        record.setId(id);
        record.changed(Tables.ANALYTICS.ID, false);
        record.setName(newName);
        return record;
    }

    public List<Analytics> getByGroupId(int groupId) {
        return this.create.selectFrom(Tables.ANALYTICS).where(Tables.ANALYTICS.GROUP_ID.eq(groupId).and(Tables.ANALYTICS.STATUS.notEqual(AnalyticsStatus.deleted.name()))).fetch(this.converter::from);
    }

    public Set<Integer> removeArchivedAnalytics(Set<Integer> restrictedAnalytics) {
        List<Condition> conditions = List.of(Tables.ANALYTICS.STATUS.eq(AnalyticsStatus.deleted.name()), Tables.ANALYTICS.ID.in((Collection<?>)restrictedAnalytics));
        return this.create.transactionResult(tx -> {
            DSLContext dsl = tx.dsl();
            Set deletedAnalytics = dsl.selectFrom(Tables.ANALYTICS).where(conditions).fetchSet(Tables.ANALYTICS.ID);
            dsl.deleteFrom(Tables.ANALYTICS).where(conditions).execute();
            return deletedAnalytics;
        });
    }

    @Inject
    public AnalyticsRepository(DSLContext create, AnalyticsRecordConverter converter, StreamsManager streamManager) {
        this.create = create;
        this.converter = converter;
        this.streamManager = streamManager;
    }
}

