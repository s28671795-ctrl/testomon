/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import io.r2dbc.spi.ConnectionFactory;
import java.sql.SQLException;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Query;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.impl.AbstractBatch;
import org.jooq.impl.AbstractQuery;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.ExecuteListeners;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.R2DBC;
import org.jooq.impl.SettingsEnabledPreparedStatement;
import org.jooq.impl.Tools;
import org.reactivestreams.Subscriber;

final class BatchMultiple
extends AbstractBatch {
    final Query[] queries;

    public BatchMultiple(Configuration configuration, Query ... queries) {
        super(configuration);
        this.queries = queries;
    }

    @Override
    public final int size() {
        return this.queries.length;
    }

    @Override
    public final void subscribe(Subscriber<? super Integer> subscriber) {
        ConnectionFactory cf = this.configuration.connectionFactory();
        if (cf instanceof NoConnectionFactory) {
            throw new UnsupportedOperationException("The blocking, JDBC backed implementation of reactive batching has not yet been implemented. Use the R2DBC backed implementation, instead, or avoid batching.");
        }
        subscriber.onSubscribe(new R2DBC.BatchSubscription<BatchMultiple>(this, subscriber, s2 -> new R2DBC.BatchMultipleSubscriber(this, (R2DBC.BatchSubscription<BatchMultiple>)s2)));
    }

    @Override
    public final int[] execute() {
        return BatchMultiple.execute(this.configuration, this.queries);
    }

    static int[] execute(Configuration configuration, Query[] queries) {
        DefaultExecuteContext ctx = new DefaultExecuteContext(configuration, ExecuteContext.BatchMode.MULTIPLE, queries);
        ExecuteListener listener = ExecuteListeners.get(ctx);
        try {
            int i;
            listener.start(ctx);
            ctx.transformQueries(listener);
            if (ctx.statement() == null) {
                ctx.statement(new SettingsEnabledPreparedStatement(AbstractQuery.connection(ctx)));
            }
            String[] batchSQL = ctx.batchSQL();
            for (i = 0; i < ctx.batchQueries().length; ++i) {
                ctx.sql(null);
                listener.renderStart(ctx);
                batchSQL[i] = DSL.using(configuration).renderInlined(ctx.batchQueries()[i]);
                ctx.sql(batchSQL[i]);
                listener.renderEnd(ctx);
            }
            for (i = 0; i < ctx.batchQueries().length; ++i) {
                ctx.sql(batchSQL[i]);
                listener.prepareStart(ctx);
                ctx.statement().addBatch(batchSQL[i]);
                listener.prepareEnd(ctx);
            }
            int t2 = SettingsTools.getQueryTimeout(0, ctx.settings());
            if (t2 != 0) {
                ctx.statement().setQueryTimeout(t2);
            }
            listener.executeStart(ctx);
            int[] result = ctx.statement().executeBatch();
            int[] batchRows = ctx.batchRows();
            for (int i2 = 0; i2 < batchRows.length && i2 < result.length; ++i2) {
                batchRows[i2] = result[i2];
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

    public String toString() {
        return this.dsl.queries(this.queries).toString();
    }
}

