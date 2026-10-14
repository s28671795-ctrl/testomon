/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class AnyValue<T>
extends AbstractAggregateFunction<T>
implements QOM.AnyValue<T> {
    AnyValue(Field<T> field2) {
        super(false, Names.N_ANY_VALUE, Tools.nullSafeDataType(field2), Tools.nullSafeNotNull(field2, SQLDataType.OTHER));
    }

    @Override
    void acceptFunctionName(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case ASE: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case DB2: 
            case EXASOL: 
            case INFORMIX: 
            case INGRES: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case DERBY: 
            case FIREBIRD: 
            case H2: 
            case HANA: 
            case HSQLDB: 
            case IGNITE: 
            case MARIADB: 
            case POSTGRES: 
            case SQLITE: 
            case YUGABYTEDB: {
                ctx.visit(Names.N_MIN);
                break;
            }
            case MYSQL: {
                if (ctx.dialect().supports(SQLDialect.MYSQL_5_7)) {
                    super.acceptFunctionName(ctx);
                    break;
                }
                ctx.visit(Names.N_MIN);
                break;
            }
            case ORACLE: {
                if (ctx.dialect().supports(SQLDialect.ORACLE21C)) {
                    super.acceptFunctionName(ctx);
                    break;
                }
                ctx.visit(Names.N_MIN);
                break;
            }
            default: {
                super.acceptFunctionName(ctx);
            }
        }
    }

    @Override
    public final Field<T> $field() {
        return (Field)this.getArguments().get(0);
    }

    @Override
    public final QOM.AnyValue<T> $field(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    public final Function1<? super Field<T>, ? extends QOM.AnyValue<T>> $constructor() {
        return a1 -> new AnyValue(a1);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$field(), a1 -> this.$constructor().apply((Field<T>)a1), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        QOM.traverse(traverser, this, this.$field());
        return super.$traverse(traverser);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.AnyValue) {
            QOM.AnyValue o = (QOM.AnyValue)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

