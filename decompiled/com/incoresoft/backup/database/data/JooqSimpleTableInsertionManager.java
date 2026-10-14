/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.incoresoft.backup.database.data.JooqTableInserter;
import com.incoresoft.utils.ConcurrencyUtils;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.UniqueKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JooqSimpleTableInsertionManager {
    private static final Logger log = LoggerFactory.getLogger(JooqSimpleTableInsertionManager.class);

    public void insert(DSLContext sourceContext, Table<?> sourceTable, List<? extends Field<?>> sourceFields, JooqTableInserter insertionManager) throws InterruptedException {
        long recordsNumber = this.getRecordsNumber(sourceContext, sourceTable);
        long batchesNumber = Math.ceilDiv(recordsNumber, 1000);
        for (long i = 0L; i < batchesNumber; ++i) {
            long offset = i * 1000L;
            Result<?> records = this.selectRecords(sourceContext, sourceTable, sourceFields, offset);
            ConcurrencyUtils.ensureNotInterrupted();
            insertionManager.insert(records);
        }
        insertionManager.flush();
    }

    private Result<?> selectRecords(DSLContext context, Table<?> table, List<? extends Field<?>> fields, long offset) {
        UniqueKey<?> primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            log.warn("Table {} does not have a primary key. Backup of it may introduce some errors....", (Object)table.getName());
            return context.select(fields).from((TableLike<?>)table).offset(offset).limit(1000L).fetch();
        }
        return context.select(fields).from((TableLike<?>)table).orderBy(primaryKey.getFields()).offset(offset).limit(1000L).fetch();
    }

    private long getRecordsNumber(DSLContext context, Table<?> table) {
        return context.fetchCount(table);
    }
}

