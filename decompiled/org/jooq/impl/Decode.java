/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractCaseSimple;
import org.jooq.impl.CaseSearched;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class Decode<V, T>
extends AbstractCaseSimple<V, T, Decode<V, T>>
implements QOM.Decode<V, T> {
    private static final Set<SQLDialect> EMULATE_DECODE_ORACLE = SQLDialect.supportedBy(SQLDialect.MARIADB_10_3);
    private static final Set<SQLDialect> EMULATE_DISTINCT = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.HSQLDB, SQLDialect.INGRES, SQLDialect.MARIADB_10_2, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.YUGABYTEDB);
    @Pro
    private static final Set<SQLDialect> EMULATE_MAP = SQLDialect.supportedBy(SQLDialect.HANA);

    Decode(Field<V> field2, Field<V> search, Field<T> result, Field<?>[] more) {
        super(Names.N_DECODE, field2, result.getDataType());
        this.when(search, result);
        if (more.length > 1) {
            int i = 0;
            while (i + 1 < more.length) {
                this.when(more[i], more[i + 1]);
                i += 2;
            }
        }
        if (more.length % 2 != 0) {
            this.else_(more[more.length - 1]);
        }
    }

    Decode(Field<V> field2, DataType<T> type) {
        super(Names.N_DECODE, field2, type);
    }

    @Override
    final void accept0(Context<?> ctx) {
        if (EMULATE_DISTINCT.contains((Object)ctx.dialect())) {
            ctx.visit(Tools.derivedTableIf(ctx, this.when.size() > 1, this.value, f -> {
                CaseSearched c = new CaseSearched(this.getDataType());
                this.when.forEach(t2 -> c.when(f.isNotDistinctFrom((Field)t2.$1()), (Field)t2.$2()));
                if (this.else_ == null) {
                    return c;
                }
                return c.else_(this.else_);
            }));
        } else if (EMULATE_MAP.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.function(Names.N_MAP, this.getDataType(), this.args()));
        } else if (EMULATE_DECODE_ORACLE.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.function(Names.N_DECODE_ORACLE, this.getDataType(), this.args()));
        } else {
            ctx.visit(DSL.function(Names.N_DECODE, this.getDataType(), this.args()));
        }
    }

    final Field<?>[] args() {
        ArrayList<Field> result = new ArrayList<Field>();
        result.add(this.value);
        this.when.forEach(t2 -> {
            result.add((Field)t2.$1());
            result.add((Field)t2.$2());
        });
        if (this.else_ != null) {
            result.add(this.else_);
        }
        return result.toArray(Tools.EMPTY_FIELD);
    }

    @Override
    final Decode<V, T> construct(Field<V> v, DataType<T> t2) {
        return new Decode<V, T>(v, t2);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.Decode.super.$traverse(traverser);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.Decode.super.$replace(replacer);
    }
}

