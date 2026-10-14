/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.jooq.BatchBindStep;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Query;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.AbstractBatch;
import org.jooq.impl.AbstractRecord;
import org.jooq.tools.JooqLogger;
import org.reactivestreams.Subscriber;

final class BatchCRUD
extends AbstractBatch {
    private static final JooqLogger log = JooqLogger.getLogger(BatchCRUD.class);
    private final TableRecord<?>[] records;
    private final Action action;

    BatchCRUD(Configuration configuration, Action action, TableRecord<?>[] records) {
        super(configuration);
        this.action = action;
        this.records = records;
    }

    @Override
    public final int size() {
        return this.records.length;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> s2) {
        throw new UnsupportedOperationException("BatchCRUD operations are not yet supported in a reactive way. Use ordinary batch operations, instead, or avoid batching. See https://github.com/jOOQ/jOOQ/issues/14874");
    }

    @Override
    public final int[] execute() throws DataAccessException {
        if (SettingsTools.executeStaticStatements(this.configuration.settings())) {
            return this.executeStatic();
        }
        return this.executePrepared();
    }

    private final Configuration deriveConfiguration(QueryCollector collector) {
        Configuration local = this.configuration.deriveAppending(collector);
        local.settings().withExecuteLogging(false).withReturnAllOnUpdatableRecord(false).withReturnDefaultOnUpdatableRecord(false).withReturnComputedOnUpdatableRecord(false).withReturnIdentityOnUpdatableRecord(false);
        return local;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final int[] executePrepared() {
        LinkedHashMap<String, List> queries = new LinkedHashMap<String, List>();
        QueryCollector collector = new QueryCollector();
        Configuration local = this.deriveConfiguration(collector);
        for (int i = 0; i < this.records.length; ++i) {
            Configuration previous = this.records[i].configuration();
            try {
                this.records[i].attach(local);
                this.executeAction(i);
                continue;
            }
            catch (QueryCollectorSignal e) {
                Query query = e.getQuery();
                String sql = e.getSQL();
                if (!query.isExecutable()) continue;
                queries.computeIfAbsent(sql, s2 -> new ArrayList()).add(query);
                continue;
            }
            finally {
                this.records[i].attach(previous);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Batch " + String.valueOf((Object)this.action) + " of " + this.records.length + " records using " + queries.size() + " distinct queries (lower is better) with an average number of bind variable sets of " + queries.values().stream().mapToInt(List::size).average().orElse(0.0) + " (higher is better)");
        }
        ArrayList result = new ArrayList();
        queries.forEach((k, v) -> {
            int[] array;
            BatchBindStep batch = this.dsl.batch((Query)v.get(0));
            for (Query query : v) {
                batch.bind(query.getBindValues().toArray());
            }
            for (int i : array = batch.execute()) {
                result.add(i);
            }
        });
        int[] array = new int[result.size()];
        for (int i = 0; i < result.size(); ++i) {
            array[i] = (Integer)result.get(i);
        }
        this.updateChangedFlag();
        return array;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final int[] executeStatic() {
        ArrayList<Query> queries = new ArrayList<Query>();
        QueryCollector collector = new QueryCollector();
        Configuration local = this.deriveConfiguration(collector);
        for (int i = 0; i < this.records.length; ++i) {
            Configuration previous = this.records[i].configuration();
            try {
                this.records[i].attach(local);
                this.executeAction(i);
                continue;
            }
            catch (QueryCollectorSignal e) {
                Query query = e.getQuery();
                if (!query.isExecutable()) continue;
                queries.add(query);
                continue;
            }
            finally {
                this.records[i].attach(previous);
            }
        }
        int[] result = this.dsl.batch(queries).execute();
        this.updateChangedFlag();
        return result;
    }

    private final void executeAction(int i) {
        switch (this.action) {
            case STORE: {
                ((UpdatableRecord)this.records[i]).store();
                break;
            }
            case INSERT: {
                this.records[i].insert();
                break;
            }
            case UPDATE: {
                ((UpdatableRecord)this.records[i]).update();
                break;
            }
            case MERGE: {
                ((UpdatableRecord)this.records[i]).merge();
                break;
            }
            case DELETE: {
                ((UpdatableRecord)this.records[i]).delete();
            }
        }
    }

    private final void updateChangedFlag() {
        for (TableRecord<?> record : this.records) {
            record.changed(this.action == Action.DELETE);
            if (!(record instanceof AbstractRecord)) continue;
            AbstractRecord r = (AbstractRecord)((Object)record);
            r.fetched = this.action != Action.DELETE;
        }
    }

    static enum Action {
        STORE,
        INSERT,
        UPDATE,
        MERGE,
        DELETE;

    }

    private static class QueryCollector
    implements ExecuteListener {
        private QueryCollector() {
        }

        @Override
        public void renderEnd(ExecuteContext ctx) {
            throw new QueryCollectorSignal(ctx.sql(), ctx.query());
        }
    }

    private static class QueryCollectorSignal
    extends ControlFlowSignal {
        private final String sql;
        private final Query query;

        QueryCollectorSignal(String sql, Query query) {
            this.sql = sql;
            this.query = query;
        }

        String getSQL() {
            return this.sql;
        }

        Query getQuery() {
            return this.query;
        }
    }
}

