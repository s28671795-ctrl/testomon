/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.ConnectionFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.RowCountQuery;
import org.jooq.conf.ParamType;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.impl.AbstractBatch;
import org.jooq.impl.AbstractQuery;
import org.jooq.impl.BatchMultiple;
import org.jooq.impl.DefaultBindContext;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.ExecuteListeners;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.ParamCollector;
import org.jooq.impl.R2DBC;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.reactivestreams.Subscriber;

final class BatchSingle
extends AbstractBatch
implements BatchBindStep {
    private static final JooqLogger log = JooqLogger.getLogger(BatchSingle.class);
    final Query query;
    final Map<String, List<Integer>> nameToIndexMapping;
    final List<Object[]> allBindValues;
    final int expectedBindValues;

    public BatchSingle(Configuration configuration, Query query) {
        super(configuration);
        int i = 0;
        ParamCollector collector = new ParamCollector(configuration, false);
        collector.visit(query);
        this.query = query;
        this.allBindValues = new ArrayList<Object[]>();
        this.nameToIndexMapping = new LinkedHashMap<String, List<Integer>>();
        this.expectedBindValues = collector.resultList.size();
        for (Map.Entry<String, Param<?>> entry : collector.resultList) {
            this.nameToIndexMapping.computeIfAbsent(entry.getKey(), e -> new ArrayList()).add(i++);
        }
    }

    @Override
    public final BatchSingle bind(Object ... bindValues) {
        this.allBindValues.add(bindValues);
        return this;
    }

    @Override
    public final BatchSingle bind(Object[] ... bindValues) {
        for (Object[] v : bindValues) {
            this.bind(v);
        }
        return this;
    }

    @Override
    public final BatchSingle bind(Map<String, Object> namedBindValues) {
        return this.bind(new Map[]{namedBindValues});
    }

    @Override
    @SafeVarargs
    public final BatchSingle bind(Map<String, Object> ... namedBindValues) {
        List<Object> defaultValues = this.dsl.extractBindValues(this.query);
        Object[][] bindValues = new Object[namedBindValues.length][];
        for (int i = 0; i < bindValues.length; ++i) {
            bindValues[i] = defaultValues.toArray();
            Object[] row = bindValues[i];
            namedBindValues[i].forEach((k, v) -> {
                List<Integer> indexes = this.nameToIndexMapping.get(k);
                if (indexes != null) {
                    for (int index : indexes) {
                        row[index] = v;
                    }
                }
            });
        }
        this.bind(bindValues);
        return this;
    }

    @Override
    public final int size() {
        return this.allBindValues.size();
    }

    @Override
    public final void subscribe(Subscriber<? super Integer> subscriber) {
        ConnectionFactory cf = this.configuration.connectionFactory();
        if (cf instanceof NoConnectionFactory) {
            throw new UnsupportedOperationException("The blocking, JDBC backed implementation of reactive batching has not yet been implemented. Use the R2DBC backed implementation, instead, or avoid batching.");
        }
        subscriber.onSubscribe(new R2DBC.BatchSubscription<BatchSingle>(this, subscriber, s2 -> new R2DBC.BatchSingleSubscriber(this, (R2DBC.BatchSubscription<BatchSingle>)s2)));
    }

    @Override
    public final int[] execute() {
        if (this.allBindValues.isEmpty()) {
            log.info((Object)"Single batch", "No bind variables have been provided with a single statement batch execution. This may be due to accidental API misuse");
            return BatchMultiple.execute(this.configuration, new Query[]{this.query});
        }
        this.checkBindValues();
        if (SettingsTools.executeStaticStatements(this.configuration.settings())) {
            return this.executeStatic();
        }
        return this.executePrepared();
    }

    final void checkBindValues() {
        if (this.expectedBindValues > 0) {
            for (int i = 0; i < this.allBindValues.size(); ++i) {
                if (this.allBindValues.get(i).length == this.expectedBindValues) continue;
                log.info((Object)"Bind value count", "Batch bind value set " + i + " has " + this.allBindValues.get(i).length + " values when " + this.expectedBindValues + " values were expected");
            }
        }
    }

    private final int[] executePrepared() {
        DefaultExecuteContext ctx = new DefaultExecuteContext(this.configuration, ExecuteContext.BatchMode.SINGLE, new Query[]{this.query});
        ExecuteListener listener = ExecuteListeners.get(ctx);
        Param<?>[] params = this.extractParams();
        try {
            listener.start(ctx);
            ctx.transformQueries(listener);
            listener.renderStart(ctx);
            ctx.sql(this.dsl.render(this.query));
            listener.renderEnd(ctx);
            listener.prepareStart(ctx);
            if (ctx.statement() == null) {
                ctx.statement(AbstractQuery.connection(ctx).prepareStatement(ctx.sql()));
            }
            listener.prepareEnd(ctx);
            int t2 = SettingsTools.getQueryTimeout(0, ctx.settings());
            if (t2 != 0) {
                ctx.statement().setQueryTimeout(t2);
            }
            for (Object[] bindValues : this.allBindValues) {
                listener.bindStart(ctx);
                Tools.visitAll(new DefaultBindContext(this.configuration, ctx, ctx.statement()), params.length > 0 ? Tools.fields(bindValues, params) : Tools.fields(bindValues));
                listener.bindEnd(ctx);
                ctx.statement().addBatch();
            }
            listener.executeStart(ctx);
            int[] result = ctx.statement().executeBatch();
            int[] batchRows = ctx.batchRows();
            for (int i = 0; i < batchRows.length && i < result.length; ++i) {
                batchRows[i] = result[i];
            }
            listener.executeEnd(ctx);
            int[] nArray = result;
            return nArray;
        }
        catch (ControlFlowSignal e) {
            throw e;
        }
        catch (RuntimeException e) {
            ctx.exception(e);
            listener.exception(ctx);
            throw ctx.exception();
        }
        catch (SQLException e) {
            ctx.sqlException(e);
            listener.exception(ctx);
            throw ctx.exception();
        }
        finally {
            Tools.safeClose(listener, ctx);
        }
    }

    final Param<?>[] extractParams() {
        ParamCollector collector = new ParamCollector(this.configuration, false);
        collector.visit(this.query);
        return Tools.map(collector.resultList, e -> (Param)e.getValue(), Param[]::new);
    }

    private final int[] executeStatic() {
        return this.batchMultiple().execute();
    }

    private final Batch batchMultiple() {
        ArrayList<RowCountQuery> queries = new ArrayList<RowCountQuery>(this.allBindValues.size());
        for (Object[] bindValues : this.allBindValues) {
            for (int i = 0; i < bindValues.length; ++i) {
                this.query.bind(i + 1, bindValues[i]);
            }
            queries.add(this.dsl.query(this.query.getSQL(ParamType.INLINED)));
        }
        return this.dsl.batch(queries);
    }

    public String toString() {
        return this.batchMultiple().toString();
    }
}

