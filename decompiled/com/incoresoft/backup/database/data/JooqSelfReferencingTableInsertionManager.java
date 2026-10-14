/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.incoresoft.backup.database.data.JooqTableInserter;
import com.incoresoft.utils.ConcurrencyUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JooqSelfReferencingTableInsertionManager {
    private static final Logger log = LoggerFactory.getLogger(JooqSelfReferencingTableInsertionManager.class);
    private static final int MAX_SELECT_STATEMENT_BATCH_SIZE = 1000;
    private static final int MAX_PRIMARY_KEYS_BATCH_SIZE = 1000;

    public void insert(DSLContext sourceContext, Table<?> sourceTable, List<? extends Field<?>> sourceFields, ForeignKey<?, ?> sourceForeignKey, JooqTableInserter insertionManager) throws InterruptedException {
        List<String> primaryKeyFieldNames = sourceForeignKey.getKey().getFields().stream().map(Field::getName).toList();
        List<String> foreignKeyFieldNames = sourceForeignKey.getFields().stream().map(Field::getName).toList();
        List<Map<String, Object>> foreignKeyFieldToValueMaps = new ArrayList<Map<String, Object>>();
        foreignKeyFieldToValueMaps.add(this.getForeignKeyFieldNameToNullValueMap(sourceForeignKey));
        long recordsNumber = this.getRecordsNumber(sourceContext, sourceTable);
        long insertedRecords = 0L;
        while (!foreignKeyFieldToValueMaps.isEmpty() && insertedRecords < recordsNumber) {
            Result<?> selectedRecords;
            int foreignKeysBatchSize = Math.min(1000, foreignKeyFieldToValueMaps.size());
            ArrayList<Map<String, Object>> foreignKeysBatch = new ArrayList<Map<String, Object>>(foreignKeyFieldToValueMaps.subList(0, foreignKeysBatchSize));
            int offset = 0;
            while (!(selectedRecords = this.selectRecords(sourceContext, sourceTable, sourceFields, offset, foreignKeysBatch, sourceForeignKey.getKey().getFields())).isEmpty()) {
                foreignKeyFieldToValueMaps.addAll(this.getPrimaryKeyFieldNameToValueMaps(selectedRecords, primaryKeyFieldNames, foreignKeyFieldNames));
                ConcurrencyUtils.ensureNotInterrupted();
                insertionManager.submit(selectedRecords);
                insertedRecords += (long)selectedRecords.size();
                offset += 1000;
            }
            foreignKeyFieldToValueMaps = foreignKeyFieldToValueMaps.subList(foreignKeysBatchSize, foreignKeyFieldToValueMaps.size());
        }
        insertionManager.flush();
    }

    private Map<String, Object> getForeignKeyFieldNameToNullValueMap(ForeignKey<?, ?> foreignKey) {
        HashMap<String, Object> nullableForeignKeyFieldToValueMap = new HashMap<String, Object>();
        for (TableField field2 : foreignKey.getFields()) {
            nullableForeignKeyFieldToValueMap.put(field2.getName(), null);
        }
        return nullableForeignKeyFieldToValueMap;
    }

    private List<Map<String, Object>> getPrimaryKeyFieldNameToValueMaps(List<? extends Record> records, List<String> primaryKeyFieldNames, List<String> foreignKeyFieldNames) {
        return records.stream().map(record -> {
            HashMap<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < primaryKeyFieldNames.size(); ++i) {
                Object primaryKeyValue = record.get((String)primaryKeyFieldNames.get(i));
                String fieldName = (String)foreignKeyFieldNames.get(i);
                map.put(fieldName, primaryKeyValue);
            }
            return map;
        }).toList();
    }

    private Result<?> selectRecords(DSLContext context, Table<?> table, List<? extends Field<?>> fields, long offset, List<Map<String, Object>> foreignKeyFieldNameToValueMaps, List<? extends TableField<?, ?>> primaryKeyFields) {
        SelectJoinStep query = context.select(fields).from((TableLike<?>)table);
        Condition keysCondition = DSL.falseCondition();
        for (Map<String, Object> primaryKeyFieldToValueMap : foreignKeyFieldNameToValueMaps) {
            Condition keyCondition = DSL.trueCondition();
            for (Map.Entry<String, Object> entry : primaryKeyFieldToValueMap.entrySet()) {
                if (entry.getValue() == null) {
                    keyCondition = keyCondition.and(DSL.field(entry.getKey()).isNull());
                    continue;
                }
                keyCondition = keyCondition.and(DSL.field(entry.getKey()).eq(entry.getValue()));
            }
            keysCondition = keysCondition.or(keyCondition);
        }
        return query.where(keysCondition).orderBy(primaryKeyFields).offset(offset).limit(1000L).fetch();
    }

    private long getRecordsNumber(DSLContext context, Table<?> table) {
        return context.fetchCount(table);
    }
}

