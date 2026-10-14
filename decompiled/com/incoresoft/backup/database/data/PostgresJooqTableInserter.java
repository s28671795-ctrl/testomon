/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.data;

import com.incoresoft.backup.database.data.JooqTableInserter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Record1;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresJooqTableInserter
extends JooqTableInserter {
    private static final Logger log = LoggerFactory.getLogger(PostgresJooqTableInserter.class);
    private final Schema schema;

    public PostgresJooqTableInserter(DSLContext context, Table<?> table, List<? extends Field<?>> fields, int batchSize, Schema schema) {
        super(context, table, fields, batchSize);
        this.schema = schema;
    }

    @Override
    public void flush() {
        super.flush();
        Identity identity = this.table.getIdentity();
        if (identity != null) {
            Optional<String> sequenceNameOptional = this.findSequenceName(this.schema.getName(), this.table.getName(), identity.getField().getName());
            if (sequenceNameOptional.isEmpty()) {
                log.info("Sequence for table {} not found despite identity field existing, skipping setting last value", (Object)this.table.getName());
                return;
            }
            Record1 maxResult = Objects.requireNonNull((Record1)this.context.select(DSL.max(identity.getField())).from((TableLike<?>)identity.getTable()).fetchOne());
            if (maxResult.component1() == null) {
                return;
            }
            long lastValue = maxResult.getValue(0, Long.class);
            if (lastValue > 0L) {
                this.context.fetchOne(String.format("SELECT setval('%s', %d)", sequenceNameOptional.get(), lastValue));
            }
        }
    }

    private Optional<String> findSequenceName(String schemaName, String tableName, String columnName) {
        return this.context.fetchOptional("SELECT pg_get_serial_sequence(quote_ident(n.nspname) || '.' || quote_ident(t.relname), a.attname) AS sequence_name\nFROM pg_class t\nJOIN pg_namespace n ON n.oid = t.relnamespace\nJOIN pg_attribute a ON a.attrelid = t.oid\nWHERE t.relname = ? AND a.attname = ? AND n.nspname = ?\n", tableName, columnName, schemaName).map(record -> record.get("sequence_name", String.class));
    }
}

