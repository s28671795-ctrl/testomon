/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class RatioToReport
extends AbstractAggregateFunction<BigDecimal>
implements QOM.RatioToReport {
    private final Field<? extends Number> field;

    RatioToReport(Field<? extends Number> field2) {
        super(Names.N_RATIO_TO_REPORT, SQLDataType.DECIMAL, field2);
        this.field = field2;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql("ratioToReport(").visit(this.field).sql(')');
                this.acceptOverClause(ctx);
                break;
            }
            case TERADATA: {
                ctx.visit(Tools.castIfNeeded(this.field, SQLDataType.DECIMAL(38, 19))).sql(" / ").visit(DSL.sum(this.field));
                this.acceptOverClause(ctx);
                break;
            }
            case SQLITE: 
            case FIREBIRD: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case CUBRID: 
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case MEMSQL: 
            case REDSHIFT: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case VERTICA: 
            case MARIADB: 
            case MYSQL: 
            case POSTGRES: 
            case YUGABYTEDB: {
                ctx.visit(Tools.castIfNeeded(this.field, switch (ctx.family()) {
                    case SQLDialect.SQLITE -> SQLDataType.DOUBLE;
                    case SQLDialect.FIREBIRD -> SQLDataType.DECIMAL(38, 19);
                    default -> SQLDataType.DECIMAL;
                })).sql(" / ").visit(DSL.sum(this.field));
                this.acceptOverClause(ctx);
                break;
            }
            default: {
                ctx.visit(Names.N_RATIO_TO_REPORT).sql('(').visit(this.field).sql(')');
                this.acceptOverClause(ctx);
            }
        }
    }

    @Override
    public final Field<? extends Number> $field() {
        return this.field;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.field);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.field, RatioToReport::new, replacer);
    }
}

