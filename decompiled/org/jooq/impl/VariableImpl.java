/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record1;
import org.jooq.Replacer;
import org.jooq.Select;
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.Variable;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractName;
import org.jooq.impl.Assignment;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

@Pro
final class VariableImpl<T>
extends AbstractField<T>
implements Variable<T> {
    static final Variable<Object> NO_VARIABLE = new VariableImpl<Object>((Name)AbstractName.NO_NAME, SQLDataType.OTHER);

    VariableImpl(Name name, DataType<T> type) {
        super(name, type);
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case H2: {
                List list = (List)ctx.data("org.jooq.impl.BlockImpl.statement-variables");
                if (list == null) {
                    ctx.sql(this.getName());
                    break;
                }
                ctx.sql("?");
                list.add(this);
                break;
            }
            case FIREBIRD: {
                if (ctx.declareParameters()) {
                    ctx.visit(this.getQualifiedName());
                    break;
                }
                ctx.sql(':').visit(this.getQualifiedName());
                break;
            }
            case HANA: {
                ctx.visit(this.getQualifiedName().unquotedName());
                break;
            }
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.sql('@').visit(this.getQualifiedName().unquotedName());
                break;
            }
            default: {
                ctx.visit(this.getQualifiedName());
            }
        }
    }

    @Override
    public final boolean declaresParameters() {
        return true;
    }

    @Override
    public final Statement set(T value) {
        return this.set(Tools.field(value, this.getDataType()));
    }

    @Override
    public final Statement set(Field<T> value) {
        return new Assignment<T>(this, value);
    }

    @Override
    public final Statement set(Select<? extends Record1<T>> value) {
        return this.set(DSL.field(value));
    }

    @Override
    public final Statement setNull() {
        return this.set((T)null);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$name(), this.$dataType());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$name(), this.$dataType(), VariableImpl::new, replacer);
    }
}

