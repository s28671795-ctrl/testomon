/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import org.jooq.CloseableQuery;
import org.jooq.Configuration;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteType;
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.conf.QueryPoolable;
import org.jooq.conf.SettingsTools;
import org.jooq.conf.ThrowExceptions;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DetachedException;
import org.jooq.impl.AbstractAttachableQueryPart;
import org.jooq.impl.AbstractParamX;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteContext;
import org.jooq.impl.DefaultRenderContext;
import org.jooq.impl.ExecuteListeners;
import org.jooq.impl.ExecutorProviderCompletionStage;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.ParamCollector;
import org.jooq.impl.StartTransaction;
import org.jooq.impl.Tools;
import org.jooq.tools.Ints;
import org.jooq.tools.JooqLogger;

abstract class AbstractQuery<R extends Record>
extends AbstractAttachableQueryPart
implements CloseableQuery {
    private static final JooqLogger log = JooqLogger.getLogger(AbstractQuery.class);
    private static final Set<SQLDialect> SET_AUTOCOMMIT_ON_START_TRANSACTION = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA);
    private int timeout;
    private QueryPoolable poolable = QueryPoolable.DEFAULT;
    private boolean keepStatement;
    transient PreparedStatement statement;
    transient int statementExecutionCount;
    transient DefaultRenderContext.Rendered rendered;

    AbstractQuery(Configuration configuration) {
        super(configuration);
    }

    final void toSQLSemiColon(RenderContext ctx) {
        if (ctx.configuration().family() == SQLDialect.ACCESS) {
            ctx.sql(';');
        }
    }

    @Override
    public CloseableQuery bind(String param2, Object value) {
        Integer index = Ints.tryParse(param2);
        if (index != null) {
            return this.bind(index, value);
        }
        ParamCollector collector = new ParamCollector(this.configuration(), true);
        collector.visit(this);
        List<Param<?>> params = collector.result.get(param2);
        if (params == null || params.size() == 0) {
            throw new IllegalArgumentException("No such parameter : " + param2);
        }
        for (Param<?> p : params) {
            ((AbstractParamX)p).setConverted0(value);
            this.closeIfNecessary(p);
        }
        return this;
    }

    @Override
    public CloseableQuery bind(int index, Object value) {
        Param<?>[] params = this.getParams().values().toArray(Tools.EMPTY_PARAM);
        if (index < 1 || index > params.length) {
            throw new IllegalArgumentException("Index out of range for Query parameters : " + index);
        }
        AbstractParamX param2 = (AbstractParamX)params[index - 1];
        param2.setConverted0(value);
        this.closeIfNecessary(param2);
        return this;
    }

    private final void closeIfNecessary(Param<?> param2) {
        if (this.keepStatement() && this.statement != null) {
            if (param2.isInline()) {
                this.close();
            } else if (SettingsTools.getParamType(this.configuration().settings()) == ParamType.INLINED) {
                this.close();
            }
        }
    }

    @Override
    public CloseableQuery poolable(boolean p) {
        this.poolable = p ? QueryPoolable.TRUE : QueryPoolable.FALSE;
        return this;
    }

    @Override
    public CloseableQuery queryTimeout(int t2) {
        this.timeout = t2;
        return this;
    }

    @Override
    public CloseableQuery keepStatement(boolean k) {
        this.keepStatement = k;
        return this;
    }

    protected final boolean keepStatement() {
        return this.keepStatement;
    }

    @Override
    public final void close() {
        if (this.statement != null) {
            try {
                this.statement.close();
                this.statement = null;
            }
            catch (SQLException e) {
                throw Tools.translate(this.rendered.sql, e);
            }
        }
    }

    @Override
    public final void cancel() {
        if (this.statement != null) {
            try {
                this.statement.cancel();
            }
            catch (SQLException e) {
                throw Tools.translate(this.rendered.sql, e);
            }
        }
    }

    @Override
    public final int execute() {
        if (this.isExecutable()) {
            Configuration c = this.configurationOrDefault();
            DefaultExecuteContext ctx = new DefaultExecuteContext(c, this);
            ExecuteListener listener = ExecuteListeners.get(ctx);
            int result = 0;
            try {
                QueryPoolable p;
                listener.start(ctx);
                if (this.keepStatement() && this.statement != null) {
                    ctx.sql(this.rendered.sql);
                    ctx.statement(this.statement);
                    ctx.connection(c.connectionProvider(), this.statement.getConnection());
                    ctx.withStatementExecutionCount(++this.statementExecutionCount);
                } else {
                    ctx.transformQueries(listener);
                    listener.renderStart(ctx);
                    this.rendered = AbstractQuery.getSQL0(ctx);
                    ctx.sql(this.rendered.sql);
                    listener.renderEnd(ctx);
                    this.rendered.sql = ctx.sql();
                    AbstractQuery.connection(ctx);
                    if (this instanceof StartTransaction && SET_AUTOCOMMIT_ON_START_TRANSACTION.contains((Object)ctx.dialect())) {
                        ctx.connection().setAutoCommit(false);
                    }
                    listener.prepareStart(ctx);
                    this.prepare(ctx);
                    listener.prepareEnd(ctx);
                    this.statement = ctx.statement();
                }
                int t2 = SettingsTools.getQueryTimeout(this.timeout, ctx.settings());
                if (t2 != 0) {
                    ctx.statement().setQueryTimeout(t2);
                }
                if ((p = SettingsTools.getQueryPoolable(this.poolable, ctx.settings())) == QueryPoolable.TRUE) {
                    ctx.statement().setPoolable(true);
                } else if (p == QueryPoolable.FALSE) {
                    ctx.statement().setPoolable(false);
                }
                if (SettingsTools.executePreparedStatements(c.settings()) && !Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_FORCE_STATIC_STATEMENT))) {
                    listener.bindStart(ctx);
                    if (this.rendered.bindValues != null) {
                        DSL.using(c).bindContext(ctx.statement()).visit(this.rendered.bindValues);
                    }
                    listener.bindEnd(ctx);
                }
                int n = result = this.execute(ctx, listener);
                return n;
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
                if (!this.keepResultSet() || ctx.exception() != null) {
                    Tools.safeClose(listener, ctx, this.keepStatement());
                }
                if (!this.keepStatement()) {
                    this.statement = null;
                    this.rendered = null;
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug((Object)"Query is not executable", this);
        }
        return 0;
    }

    static final Connection connection(DefaultExecuteContext ctx) {
        Connection result = ctx.connection();
        if (result == null) {
            if (ctx.configuration().connectionFactory() instanceof NoConnectionFactory) {
                throw new DetachedException("Cannot execute query. No JDBC Connection configured");
            }
            throw new DetachedException("Attempt to execute a blocking method (e.g. Query.execute() or ResultQuery.fetch()) when only an R2BDC ConnectionFactory was configured. jOOQ's RowCountQuery and ResultQuery extend Publisher, which allows for reactive streams implementations to subscribe to the results of a jOOQ query. Simply embed your query in the stream, e.g. using Flux.from(query). See also: https://www.jooq.org/doc/latest/manual/sql-execution/fetching/reactive-fetching/");
        }
        return result;
    }

    @Override
    public final CompletionStage<Integer> executeAsync() {
        return this.executeAsync(Tools.configuration(this).executorProvider().provide());
    }

    @Override
    public final CompletionStage<Integer> executeAsync(Executor executor2) {
        return ExecutorProviderCompletionStage.of(CompletableFuture.supplyAsync(Tools.blocking(this::execute), executor2), () -> executor2);
    }

    protected boolean keepResultSet() {
        return false;
    }

    protected void prepare(ExecuteContext ctx) throws SQLException {
        if (ctx.statement() == null) {
            ctx.statement(ctx.connection().prepareStatement(ctx.sql()));
        }
    }

    protected int execute(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
        int result = 0;
        PreparedStatement stmt = ctx.statement();
        try {
            listener.executeStart(ctx);
            if (!stmt.execute()) {
                result = stmt.getUpdateCount();
                ctx.rows(result);
            }
            if (ctx.family() == SQLDialect.SQLSERVER || ctx.family() == SQLDialect.SQLDATAWAREHOUSE) {
                while (stmt.getMoreResults() || stmt.getUpdateCount() != -1) {
                }
            }
            listener.executeEnd(ctx);
            return result;
        }
        catch (SQLException e) {
            Tools.consumeExceptions(ctx.configuration(), stmt, e);
            if (ctx.settings().getThrowExceptions() != ThrowExceptions.THROW_NONE) {
                throw e;
            }
            return stmt.getUpdateCount();
        }
    }

    @Override
    public boolean isExecutable() {
        return true;
    }

    private static final DefaultRenderContext.Rendered getSQL0(DefaultExecuteContext ctx) {
        DefaultRenderContext.Rendered result;
        Configuration c = ctx.originalConfiguration();
        if (ctx.type() == ExecuteType.DDL) {
            ctx.data(Tools.BooleanDataKey.DATA_FORCE_STATIC_STATEMENT, true);
            DefaultRenderContext render = new DefaultRenderContext(c, ctx);
            result = new DefaultRenderContext.Rendered(((RenderContext)((RenderContext)render.paramType(ParamType.INLINED)).visit(ctx.query())).render(), null, render.skipUpdateCounts());
        } else if (SettingsTools.executePreparedStatements(c.settings())) {
            try {
                DefaultRenderContext render = new DefaultRenderContext(c, ctx);
                render.data(Tools.BooleanDataKey.DATA_COUNT_BIND_VALUES, true);
                result = new DefaultRenderContext.Rendered(((RenderContext)render.visit(ctx.query())).render(), render.bindValues(), render.skipUpdateCounts());
            }
            catch (DefaultRenderContext.ForceInlineSignal e) {
                ctx.data(Tools.BooleanDataKey.DATA_FORCE_STATIC_STATEMENT, true);
                DefaultRenderContext render = new DefaultRenderContext(c, ctx);
                result = new DefaultRenderContext.Rendered(((RenderContext)((RenderContext)render.paramType(ParamType.INLINED)).visit(ctx.query())).render(), null, render.skipUpdateCounts());
            }
        } else {
            DefaultRenderContext render = new DefaultRenderContext(c, ctx);
            result = new DefaultRenderContext.Rendered(((RenderContext)((RenderContext)render.paramType(ParamType.INLINED)).visit(ctx.query())).render(), null, render.skipUpdateCounts());
        }
        return result;
    }
}

