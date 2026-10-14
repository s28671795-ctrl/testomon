/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Keyword;
import org.jooq.Pro;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.TableList;
import org.jooq.impl.Tools;

final class ForLock
extends AbstractQueryPart
implements QOM.UNotYetImplemented {
    private static final Set<SQLDialect> NO_SUPPORT_FOR_UPDATE_QUALIFIED = SQLDialect.supportedBy(SQLDialect.COCKROACHDB, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB);
    private static final Set<SQLDialect> NO_SUPPORT_STANDARD_FOR_SHARE = SQLDialect.supportedUntil(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_5_7);
    @Pro
    static final Set<SQLDialect> EMULATE_FOR_UPDATE = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    @Pro
    static final Set<SQLDialect> EMULATE_FOR_UPDATE_OF_TABLE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.EXASOL, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> EMULATE_FOR_UPDATE_WAIT_MY = SQLDialect.supportedUntil(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL);
    private static final Set<SQLDialect> EMULATE_FOR_UPDATE_WAIT_PG = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    QueryPartList<Field<?>> forLockOf;
    TableList forLockOfTables;
    ForLockMode forLockMode;
    ForLockWaitMode forLockWaitMode;
    int forLockWait;

    ForLock() {
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (EMULATE_FOR_UPDATE.contains((Object)ctx.dialect())) {
            return;
        }
        switch (this.forLockMode) {
            case SHARE: {
                if (NO_SUPPORT_STANDARD_FOR_SHARE.contains((Object)ctx.dialect())) {
                    ctx.formatSeparator().visit(Keywords.K_LOCK_IN_SHARE_MODE);
                    break;
                }
                ctx.formatSeparator().visit(Keywords.K_FOR).sql(' ').visit(this.forLockMode.toKeyword());
                break;
            }
            default: {
                ctx.formatSeparator().visit(Keywords.K_FOR).sql(' ').visit(this.forLockMode.toKeyword());
            }
        }
        if (Tools.isNotEmpty(this.forLockOf)) {
            ctx.qualify(!NO_SUPPORT_FOR_UPDATE_QUALIFIED.contains((Object)ctx.dialect()) && ctx.qualify(), c -> c.sql(' ').visit(Keywords.K_OF).sql(' ').visit(this.forLockOf));
        } else if (Tools.isNotEmpty(this.forLockOfTables)) {
            ctx.sql(' ').visit(Keywords.K_OF).sql(' ');
            switch (ctx.family()) {
                case DB2: 
                case HANA: 
                case INFORMIX: 
                case INGRES: 
                case ORACLE: 
                case DERBY: {
                    this.forLockOfTables.toSQLFields(ctx);
                    break;
                }
                default: {
                    ctx.visit(QueryPartCollectionView.wrap(this.forLockOfTables).qualify(false));
                }
            }
        }
        if (ctx.family() == SQLDialect.FIREBIRD) {
            ctx.sql(' ').visit(Keywords.K_WITH_LOCK);
        }
        if (this.forLockWaitMode != null) {
            if (this.forLockWaitMode == ForLockWaitMode.WAIT && EMULATE_FOR_UPDATE_WAIT_PG.contains((Object)ctx.dialect())) {
                Tools.prependSQL(ctx.skipUpdateCount(), new Query[]{ctx.dsl().setLocal(Names.N_LOCK_TIMEOUT, DSL.inline(this.forLockWait * 1000))});
            } else if (this.forLockWaitMode == ForLockWaitMode.WAIT && EMULATE_FOR_UPDATE_WAIT_MY.contains((Object)ctx.dialect())) {
                if (ctx.data(Tools.BooleanDataKey.DATA_LOCK_WAIT_TIMEOUT_SET) == null) {
                    ctx.skipUpdateCounts(2).data(Tools.BooleanDataKey.DATA_LOCK_WAIT_TIMEOUT_SET, true);
                    Tools.prependSQL(ctx, ctx.dsl().query("{set} @t = @@innodb_lock_wait_timeout"), ctx.dsl().query("{set} @@innodb_lock_wait_timeout = {0}", DSL.inline(this.forLockWait)));
                    Tools.appendSQL(ctx, ctx.dsl().query("{set} @@innodb_lock_wait_timeout = @t"));
                }
            } else {
                ctx.sql(' ').visit(this.forLockWaitMode.toKeyword());
                if (this.forLockWaitMode == ForLockWaitMode.WAIT) {
                    ctx.sql(' ').sql(this.forLockWait);
                }
            }
        }
    }

    @Pro
    static final void emulateSQLServerLocking(Context<?> ctx, Table<?> table) {
        ForLock lock;
        if (EMULATE_FOR_UPDATE.contains((Object)ctx.dialect()) && (lock = (ForLock)ctx.data(Tools.ExtendedDataKey.DATA_FOR_LOCK)) != null && (lock.forLockOfTables == null || lock.forLockOfTables.contains(table))) {
            QueryPartList<Keyword> hints = new QueryPartList<Keyword>();
            hints.add(Keywords.K_ROWLOCK);
            hints.add(Keywords.K_UPDLOCK);
            if (lock.forLockWaitMode == ForLockWaitMode.NOWAIT) {
                hints.add(Keywords.K_NOWAIT);
            } else if (lock.forLockWaitMode == ForLockWaitMode.SKIP_LOCKED) {
                hints.add(Keywords.K_READPAST);
            }
            ctx.sql(' ').visit(Keywords.K_WITH).sql(" (").visit(DSL.sql("{0}", hints)).sql(')');
        }
    }

    static enum ForLockMode {
        UPDATE("update"),
        NO_KEY_UPDATE("no key update"),
        SHARE("share"),
        KEY_SHARE("key share");

        private final Keyword keyword;

        private ForLockMode(String sql) {
            this.keyword = DSL.keyword(sql);
        }

        public final Keyword toKeyword() {
            return this.keyword;
        }
    }

    static enum ForLockWaitMode {
        WAIT("wait"),
        NOWAIT("nowait"),
        SKIP_LOCKED("skip locked");

        private final Keyword keyword;

        private ForLockWaitMode(String sql) {
            this.keyword = DSL.keyword(sql);
        }

        public final Keyword toKeyword() {
            return this.keyword;
        }
    }
}

