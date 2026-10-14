/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.CommonTableExpression;
import org.jooq.Context;
import org.jooq.DerivedColumnList;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.Traverser;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.DSL;
import org.jooq.impl.DerivedColumnListImpl;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.FieldsTrait;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.Transformations;

final class CommonTableExpressionImpl<R extends Record>
extends AbstractTable<R>
implements CommonTableExpression<R> {
    private static final Set<SQLDialect> SUPPORT_MATERIALIZED = SQLDialect.supportedBy(SQLDialect.POSTGRES_12, SQLDialect.SQLITE_3_38);
    @Pro
    private static final Set<SQLDialect> EMULATE_MATERIALIZED = SQLDialect.supportedBy(SQLDialect.ORACLE);
    private final DerivedColumnListImpl name;
    private final ResultQuery<R> query;
    private final FieldsImpl<R> fields;
    private final QOM.Materialized materialized;

    CommonTableExpressionImpl(DerivedColumnListImpl name, ResultQuery<R> query, QOM.Materialized materialized) {
        super(TableOptions.expression(), name.name);
        this.name = name;
        this.query = query;
        this.fields = this.fields1();
        this.materialized = materialized;
    }

    @Override
    public final Class<? extends R> getRecordType() {
        return this.query.getRecordType();
    }

    @Override
    public final boolean declaresCTE() {
        return true;
    }

    @Override
    public final boolean declaresTables() {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("/* common table expressions are not yet supported. See known limitations: https://github.com/jOOQ/jOOQ/issues/11911 */");
        }
        if (ctx.declareCTE()) {
            QueryPart s2 = this.query;
            if (ctx.family() == SQLDialect.BIGQUERY && this.query instanceof Select) {
                ctx.visit(this.name.name);
                s2 = new AliasedSelect((Select)this.query, false, true, false, this.name.fieldNames);
            } else {
                ctx.visit(this.name);
            }
            ctx.sql(' ').visit(Keywords.K_AS).sql(' ');
            Object previous = null;
            if (this.materialized != null) {
                if (SUPPORT_MATERIALIZED.contains((Object)ctx.dialect())) {
                    if (this.materialized == QOM.Materialized.MATERIALIZED) {
                        ctx.visit(Keywords.K_MATERIALIZED).sql(' ');
                    } else {
                        ctx.visit(Keywords.K_NOT).sql(' ').visit(Keywords.K_MATERIALIZED).sql(' ');
                    }
                } else if (EMULATE_MATERIALIZED.contains((Object)ctx.dialect())) {
                    previous = ctx.data(Tools.SimpleDataKey.DATA_MATERIALIZED_CTE, (Object)this.materialized);
                }
            }
            Tools.visitSubquery(ctx, s2, 1);
            if (this.materialized != null && EMULATE_MATERIALIZED.contains((Object)ctx.dialect())) {
                ctx.data(Tools.SimpleDataKey.DATA_MATERIALIZED_CTE, previous);
            }
        } else if (ctx.declareTables() && this.query instanceof Select && Transformations.transformInlineCTE(ctx.configuration())) {
            if (ctx.declareAliases()) {
                ctx.visit(this.asDerivedTable());
            } else {
                ctx.visitSubquery(DSL.selectFrom(this.asDerivedTable()));
            }
        } else {
            ctx.visit(this.name.name);
        }
    }

    @Pro
    final Table<?> asDerivedTable() {
        ResultQuery<R> resultQuery = this.query;
        if (resultQuery instanceof Select) {
            Select s2 = (Select)resultQuery;
            return this.name.fieldNames.length > 0 ? s2.asTable(this.name.name, this.name.fieldNames) : s2.asTable(this.name.name);
        }
        return this;
    }

    @Override
    final FieldsImpl<R> fields0() {
        return this.fields;
    }

    final FieldsImpl<R> fields1() {
        Field<?>[] s2 = FieldsImpl.fieldsRow0((FieldsTrait)((Object)this.query)).fields();
        Field[] f = new Field[Tools.degree(this.query)];
        for (int i = 0; i < f.length; ++i) {
            f[i] = DSL.field(DSL.name(this.name.name, this.name.fieldNames.length > 0 ? this.name.fieldNames[i] : s2[i].getUnqualifiedName()), f.length == 1 ? Tools.scalarType(this.query) : s2[i].getDataType());
        }
        return new FieldsImpl(f);
    }

    @Override
    public final DerivedColumnList $derivedColumnList() {
        return this.name;
    }

    @Override
    public final ResultQuery<R> $query() {
        return this.query;
    }

    @Override
    public final QOM.Materialized $materialized() {
        return this.materialized;
    }

    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.name, this.query);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.name, this.query, this.materialized, CommonTableExpressionImpl::new, replacer);
    }
}

