/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.BindContext;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.QualifiedRecord;
import org.jooq.RecordQualifier;
import org.jooq.RenderContext;
import org.jooq.Scope;
import org.jooq.conf.ParamType;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.AbstractParam;
import org.jooq.impl.Cast;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultBinding;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class QualifiedRecordConstant<R extends QualifiedRecord<R>>
extends AbstractParam<R>
implements QOM.UNotYetImplemented {
    final RecordQualifier<R> qualifier;

    QualifiedRecordConstant(R value, RecordQualifier<R> qualifier) {
        super(value, qualifier.getDataType());
        this.qualifier = qualifier;
    }

    @Override
    public void accept(Context<?> ctx) {
        if (ctx instanceof RenderContext) {
            this.toSQL0((RenderContext)ctx);
        } else {
            this.bind0((BindContext)ctx);
        }
    }

    final void toSQL0(RenderContext ctx) {
        ParamType paramType = ctx.paramType();
        if (this.isInline()) {
            ctx.paramType(ParamType.INLINED);
        }
        switch (ctx.family()) {
            case ORACLE: {
                if (ctx.paramType() == ParamType.INLINED) {
                    this.toSQLInline(ctx);
                    break;
                }
                ctx.sql('?');
                break;
            }
            case DB2: {
                ctx.sql(this.getInlineConstructor(ctx));
                ctx.sql("()");
                String separator = "..";
                for (Field<?> field2 : this.qualifier.fields()) {
                    ctx.sql(separator);
                    ctx.sql(field2.getName());
                    ctx.sql('(');
                    ctx.visit(DSL.val(((QualifiedRecord)this.value).get(field2)));
                    ctx.sql(')');
                }
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                this.toSQLInline(ctx);
                break;
            }
            default: {
                this.toSQLInline(ctx);
            }
        }
        if (this.isInline()) {
            ctx.paramType(paramType);
        }
    }

    private final void toSQLInline(RenderContext ctx) {
        Cast.renderCastIf(ctx, c -> {
            if (this.value == null) {
                c.visit(Keywords.K_NULL);
            } else {
                switch (c.family()) {
                    case AURORA_POSTGRES: 
                    case COCKROACHDB: 
                    case POSTGRES: 
                    case YUGABYTEDB: {
                        c.visit(Keywords.K_ROW);
                        break;
                    }
                    default: {
                        c.visit(this.qualifier);
                    }
                }
                c.sql('(');
                String separator = "";
                for (Field<?> field2 : ((QualifiedRecord)this.value).fields()) {
                    c.sql(separator);
                    c.visit(DSL.val(((QualifiedRecord)this.value).get(field2), field2));
                    separator = ", ";
                }
                c.sql(')');
            }
        }, c -> c.visit(this.qualifier), () -> DefaultBinding.DefaultRecordBinding.REQUIRE_RECORD_CAST.contains((Object)ctx.dialect()));
    }

    @Deprecated
    private final String getInlineConstructor(RenderContext ctx) {
        switch (ctx.family()) {
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                return "ROW";
            }
        }
        return Tools.getMappedUDTName((Scope)ctx, (QualifiedRecord)this.value);
    }

    final void bind0(BindContext ctx) {
        switch (ctx.family()) {
            case ORACLE: {
                ctx.bindValue(this.value, this);
                break;
            }
            case DB2: 
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                if (this.value == null) break;
                for (Field<?> field2 : ((QualifiedRecord)this.value).fields()) {
                    ctx.visit(DSL.val(((QualifiedRecord)this.value).get(field2), field2.getDataType()));
                }
                break;
            }
            default: {
                throw new SQLDialectNotSupportedException("UDTs not supported in dialect " + String.valueOf((Object)ctx.dialect()));
            }
        }
    }
}

