/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.CloseableResultQuery;
import org.jooq.Configuration;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.Results;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.AbstractQuery;
import org.jooq.impl.CursorImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Intern;
import org.jooq.impl.ResultQueryTrait;
import org.jooq.impl.ResultsImpl;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.MockResultSet;

abstract class AbstractResultQuery<R extends Record>
extends AbstractQuery<R>
implements ResultQueryTrait<R> {
    private static final JooqLogger log = JooqLogger.getLogger(AbstractResultQuery.class);
    private static final Set<SQLDialect> REPORT_FETCH_SIZE_WITH_AUTOCOMMIT = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private int maxRows;
    private int fetchSize;
    private int resultSetConcurrency;
    private int resultSetType;
    private int resultSetHoldability;
    private Table<?> coerceTable;
    private Collection<? extends Field<?>> coerceFields;
    private transient boolean lazy;
    private transient boolean many;
    private transient Cursor<R> cursor;
    private transient boolean autoclosing = true;
    private Result<R> result;
    private ResultsImpl results;
    private final Intern intern = new Intern();

    AbstractResultQuery(Configuration configuration) {
        super(configuration);
    }

    @Override
    public final CloseableResultQuery<R> bind(String param2, Object value) {
        return (CloseableResultQuery)super.bind(param2, value);
    }

    @Override
    public final CloseableResultQuery<R> bind(int index, Object value) {
        return (CloseableResultQuery)super.bind(index, value);
    }

    @Override
    public final CloseableResultQuery<R> poolable(boolean poolable) {
        return (CloseableResultQuery)super.poolable(poolable);
    }

    @Override
    public final CloseableResultQuery<R> queryTimeout(int timeout2) {
        return (CloseableResultQuery)super.queryTimeout(timeout2);
    }

    @Override
    public final CloseableResultQuery<R> keepStatement(boolean k) {
        return (CloseableResultQuery)super.keepStatement(k);
    }

    @Override
    public final CloseableResultQuery<R> maxRows(int rows) {
        this.maxRows = rows;
        return this;
    }

    @Override
    public final CloseableResultQuery<R> fetchSize(int rows) {
        this.fetchSize = rows;
        return this;
    }

    final int fetchSize() {
        return this.fetchSize;
    }

    @Override
    public final CloseableResultQuery<R> resultSetConcurrency(int concurrency) {
        this.resultSetConcurrency = concurrency;
        return this;
    }

    @Override
    public final CloseableResultQuery<R> resultSetType(int type) {
        this.resultSetType = type;
        return this;
    }

    @Override
    public final CloseableResultQuery<R> resultSetHoldability(int holdability) {
        this.resultSetHoldability = holdability;
        return this;
    }

    @Override
    public final CloseableResultQuery<R> intern(Field<?> ... fields) {
        this.intern.internFields = fields;
        return this;
    }

    @Override
    public final CloseableResultQuery<R> intern(int ... fieldIndexes) {
        this.intern.internIndexes = fieldIndexes;
        return this;
    }

    @Override
    public final CloseableResultQuery<R> intern(String ... fieldNameStrings) {
        this.intern.internNameStrings = fieldNameStrings;
        return this;
    }

    @Override
    public final CloseableResultQuery<R> intern(Name ... fieldNames) {
        this.intern.internNames = fieldNames;
        return this;
    }

    @Override
    protected final void prepare(ExecuteContext ctx) throws SQLException {
        if (ctx.statement() == null) {
            if (this.resultSetConcurrency != 0 || this.resultSetType != 0 || this.resultSetHoldability != 0) {
                int concurrency;
                int type = this.resultSetType != 0 ? this.resultSetType : 1003;
                int n = concurrency = this.resultSetConcurrency != 0 ? this.resultSetConcurrency : 1007;
                if (this.resultSetHoldability == 0) {
                    ctx.statement(ctx.connection().prepareStatement(ctx.sql(), type, concurrency));
                } else {
                    ctx.statement(ctx.connection().prepareStatement(ctx.sql(), type, concurrency, this.resultSetHoldability));
                }
            } else {
                ctx.statement(ctx.connection().prepareStatement(ctx.sql()));
            }
        }
        Tools.setFetchSize(ctx, this.fetchSize);
        int m4 = SettingsTools.getMaxRows(this.maxRows, ctx.settings());
        if (m4 != 0) {
            ctx.statement().setMaxRows(m4);
        }
    }

    @Override
    protected final int execute(ExecuteContext ctx, ExecuteListener listener) throws SQLException {
        listener.executeStart(ctx);
        int f = SettingsTools.getFetchSize(this.fetchSize, ctx.settings());
        if (REPORT_FETCH_SIZE_WITH_AUTOCOMMIT.contains((Object)ctx.dialect()) && f != 0 && ctx.connection().getAutoCommit()) {
            log.info((Object)"Fetch Size", "A fetch size of " + f + " was set on a auto-commit PostgreSQL connection, which is not recommended. See https://jdbc.postgresql.org/documentation/query/#getting-results-based-on-a-cursor");
        }
        SQLException e = Tools.executeStatementAndGetFirstResultSet(ctx, this.rendered.skipUpdateCounts);
        listener.executeEnd(ctx);
        if (!this.many) {
            if (e == null) {
                if (ctx.resultSet() == null) {
                    DSLContext dsl = DSL.using(ctx.configuration());
                    Field<Integer> c = DSL.field(DSL.name("UPDATE_COUNT"), Integer.TYPE);
                    Result<Record1<Integer>> r = dsl.newResult(c);
                    r.add(dsl.newRecord(c).values(ctx.rows()));
                    ctx.resultSet(new MockResultSet(r));
                }
                Field<?>[] fields = this.getFields(() -> ctx.resultSet().getMetaData());
                this.cursor = new CursorImpl<R>(ctx, listener, fields, this.intern.internIndexes(fields), this.keepStatement(), this.keepResultSet(), this.getRecordType(), SettingsTools.getMaxRows(this.maxRows, ctx.settings()), this.autoclosing);
                if (!this.lazy) {
                    this.result = this.cursor.fetch();
                    this.cursor = null;
                }
            }
        } else {
            this.results = new ResultsImpl(ctx.configuration());
            Tools.consumeResultSets(ctx, listener, this.results, this.intern, e);
        }
        return this.result != null ? this.result.size() : 0;
    }

    @Override
    protected final boolean keepResultSet() {
        return this.lazy;
    }

    final Collection<? extends Field<?>> coerce() {
        return this.coerceFields;
    }

    @Override
    public final Result<R> fetch() {
        this.execute();
        return this.result;
    }

    @Override
    public final Cursor<R> fetchLazy() {
        this.lazy = true;
        try {
            this.execute();
        }
        finally {
            this.lazy = false;
        }
        return this.cursor;
    }

    @Override
    public final Cursor<R> fetchLazyNonAutoClosing() {
        boolean previousAutoClosing = this.autoclosing;
        this.autoclosing = false;
        try {
            Cursor<R> cursor = this.fetchLazy();
            return cursor;
        }
        finally {
            this.autoclosing = previousAutoClosing;
        }
    }

    @Override
    public final Results fetchMany() {
        this.many = true;
        try {
            this.execute();
        }
        finally {
            this.many = false;
        }
        return this.results;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        if (this.coerceTable != null) {
            return this.coerceTable.getRecordType();
        }
        return this.getRecordType0();
    }

    abstract Class<? extends R> getRecordType0();

    @Override
    public final Result<R> getResult() {
        return this.result;
    }

    @Override
    public final <X extends Record> CloseableResultQuery<X> coerce(Table<X> table) {
        this.coerceTable = table;
        return this.coerce(Arrays.asList(table.fields()));
    }

    @Override
    public final CloseableResultQuery<Record> coerce(Collection<? extends Field<?>> fields) {
        this.coerceFields = fields;
        return this;
    }
}

