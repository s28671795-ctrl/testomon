/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertValuesStepN;
import org.jooq.Record;
import org.jooq.Table;

public class JooqTableInserter
implements Closeable {
    protected final DSLContext context;
    protected final Table<?> table;
    protected final List<? extends Field<?>> fields;
    protected final int batchSize;
    protected List<Record> queue = new ArrayList<Record>();

    public void insert(List<? extends Record> records) {
        InsertValuesStepN<?> insertStep = this.context.insertInto(this.table).columns(this.fields);
        for (Record record : records) {
            insertStep = insertStep.values(record.intoArray());
        }
        insertStep.execute();
    }

    public void submit(List<? extends Record> newRecords) {
        this.queue.addAll(newRecords);
        if (this.queue.size() < this.batchSize) {
            return;
        }
        int i = 0;
        while (i + this.batchSize <= this.queue.size()) {
            List<Record> batch = this.queue.subList(i, i + this.batchSize);
            this.insert(batch);
            i += this.batchSize;
        }
        this.queue = this.queue.subList(i, this.queue.size());
    }

    public void flush() {
        if (this.queue.isEmpty()) {
            return;
        }
        this.insert(this.queue);
        this.queue.clear();
    }

    @Override
    public void close() {
        this.flush();
    }

    int getQueueSize() {
        return this.queue.size();
    }

    public JooqTableInserter(DSLContext context, Table<?> table, List<? extends Field<?>> fields, int batchSize) {
        this.context = context;
        this.table = table;
        this.fields = fields;
        this.batchSize = batchSize;
    }
}

