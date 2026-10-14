/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.function.BiFunction;
import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.DerivedColumnList;
import org.jooq.DerivedColumnList1;
import org.jooq.DerivedColumnList10;
import org.jooq.DerivedColumnList11;
import org.jooq.DerivedColumnList12;
import org.jooq.DerivedColumnList13;
import org.jooq.DerivedColumnList14;
import org.jooq.DerivedColumnList15;
import org.jooq.DerivedColumnList16;
import org.jooq.DerivedColumnList17;
import org.jooq.DerivedColumnList18;
import org.jooq.DerivedColumnList19;
import org.jooq.DerivedColumnList2;
import org.jooq.DerivedColumnList20;
import org.jooq.DerivedColumnList21;
import org.jooq.DerivedColumnList22;
import org.jooq.DerivedColumnList3;
import org.jooq.DerivedColumnList4;
import org.jooq.DerivedColumnList5;
import org.jooq.DerivedColumnList6;
import org.jooq.DerivedColumnList7;
import org.jooq.DerivedColumnList8;
import org.jooq.DerivedColumnList9;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.ResultQuery;
import org.jooq.Traverser;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.CommonTableExpressionImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.FieldsTrait;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;

final class DerivedColumnListImpl
extends AbstractQueryPart
implements DerivedColumnList1,
DerivedColumnList2,
DerivedColumnList3,
DerivedColumnList4,
DerivedColumnList5,
DerivedColumnList6,
DerivedColumnList7,
DerivedColumnList8,
DerivedColumnList9,
DerivedColumnList10,
DerivedColumnList11,
DerivedColumnList12,
DerivedColumnList13,
DerivedColumnList14,
DerivedColumnList15,
DerivedColumnList16,
DerivedColumnList17,
DerivedColumnList18,
DerivedColumnList19,
DerivedColumnList20,
DerivedColumnList21,
DerivedColumnList22,
DerivedColumnList {
    final Name name;
    final Name[] fieldNames;
    final BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction;

    DerivedColumnListImpl(Name name, Name[] fieldNames) {
        this.name = name;
        this.fieldNames = fieldNames;
        this.fieldNameFunction = null;
    }

    DerivedColumnListImpl(String name, BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction) {
        this.name = DSL.name(name);
        this.fieldNames = null;
        this.fieldNameFunction = fieldNameFunction;
    }

    final CommonTableExpression as0(ResultQuery query, QOM.Materialized materialized) {
        ResultQuery q = query;
        if (this.fieldNameFunction != null) {
            return new CommonTableExpressionImpl(new DerivedColumnListImpl(this.name, Tools.map(FieldsImpl.fieldsRow0((FieldsTrait)((Object)q)).fields(), (f, i) -> DSL.name(this.fieldNameFunction.apply((Field<?>)f, i)), Name[]::new)), q, materialized);
        }
        return new CommonTableExpressionImpl(this, q, materialized);
    }

    public final CommonTableExpression as(ResultQuery query) {
        return this.as0(query, null);
    }

    public final CommonTableExpression asMaterialized(ResultQuery query) {
        return this.as0(query, QOM.Materialized.MATERIALIZED);
    }

    public final CommonTableExpression asNotMaterialized(ResultQuery query) {
        return this.as0(query, QOM.Materialized.NOT_MATERIALIZED);
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(this.name);
        if (this.fieldNames != null && this.fieldNames.length > 0) {
            ctx.sql('(');
            for (int i = 0; i < this.fieldNames.length; ++i) {
                if (i > 0) {
                    ctx.sql(", ");
                }
                ctx.visit(this.fieldNames[i]);
            }
            ctx.sql(')');
        }
    }

    @Override
    public final Name $tableName() {
        return this.name;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Name> $columnNames() {
        return QOM.unmodifiable((QueryPart[])(this.fieldNames != null ? this.fieldNames : Tools.EMPTY_NAME));
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this.name, this.fieldNames);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.name, this.fieldNames, DerivedColumnListImpl::new, replacer);
    }
}

