/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.incoresoft.backup.database.data.JooqTableInserter;
import java.util.ArrayList;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertValuesStepN;
import org.jooq.Record;
import org.jooq.RowCountQuery;
import org.jooq.Table;

public class SQLServerJooqTableInserter
extends JooqTableInserter {
    public SQLServerJooqTableInserter(DSLContext context, Table<?> table, List<? extends Field<?>> fields, int batchSize) {
        super(context, table, fields, batchSize);
    }

    @Override
    public void insert(List<? extends Record> records) {
        String enableIdentityInsert = String.format("SET IDENTITY_INSERT %s ON", this.table.getName());
        String disableIdentityInsert = String.format("SET IDENTITY_INSERT %s OFF", this.table.getName());
        ArrayList<RowCountQuery> queries = new ArrayList<RowCountQuery>();
        InsertValuesStepN insertStep = this.context.insertInto(this.table).columns(this.fields);
        for (Record record : records) {
            insertStep = insertStep.values(record.intoArray());
        }
        queries.add(insertStep);
        if (this.table.getIdentity() != null) {
            queries.addFirst(this.context.query(enableIdentityInsert));
            queries.addLast(this.context.query(disableIdentityInsert));
        }
        this.context.batch(queries).execute();
    }
}

