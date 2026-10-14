/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.PivotForStep;
import org.jooq.PivotInStep;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.TableOptions;
import org.jooq.conf.ParamType;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.TableAlias;
import org.jooq.impl.Tools;

@Pro
final class Pivot<T>
extends AbstractTable<Record>
implements PivotForStep,
PivotInStep<T>,
QOM.UNotYetImplemented {
    private static final Set<SQLDialect> NO_SUPPORT_NATIVE = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE10G, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    private final Table<?> table;
    private final SelectFieldList<Field<?>> aggregateFunctions;
    private Field<T> for_;
    private SelectFieldList<Field<?>> in;

    Pivot(Table<?> table, Field<?> ... aggregateFunctions) {
        super(TableOptions.expression(), Names.N_PIVOT);
        this.table = table;
        this.aggregateFunctions = new SelectFieldList((SelectFieldOrAsterisk[])aggregateFunctions);
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (NO_SUPPORT_NATIVE.contains((Object)ctx.dialect())) {
            ctx.visit(new DefaultPivotTable());
        } else {
            ctx.visit(new OraclePivotTable());
        }
    }

    private <Z> Condition condition(Table<?> pivot, Field<Z> field2) {
        return field2.equal(pivot.field(field2));
    }

    @Override
    public final boolean declaresTables() {
        return true;
    }

    @Override
    public final Table<Record> as(Name alias) {
        return new TableAlias<Record>(this, alias);
    }

    @Override
    public final Table<Record> as(Name alias, Name ... fieldAliases) {
        return new TableAlias<Record>(this, alias, fieldAliases);
    }

    @Override
    final FieldsImpl<Record> fields0() {
        return new FieldsImpl<Record>(new SelectField[0]);
    }

    @Deprecated
    public final <Z> Pivot<Z> on(Field<Z> field2) {
        return this.for_((Field)field2);
    }

    public final <Z> Pivot<Z> for_(Field<Z> field2) {
        this.for_ = field2;
        return this;
    }

    @Override
    public final Table<Record> in(Object ... values2) {
        return this.in(Tools.fields(values2, this.for_).toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final Table<Record> in(Field<?> ... f) {
        this.in = new SelectFieldList((SelectFieldOrAsterisk[])f);
        return this;
    }

    @Override
    public final Table<Record> in(Collection<? extends Field<T>> f) {
        return this.in(f.toArray(Tools.EMPTY_FIELD));
    }

    private class DefaultPivotTable
    extends DialectPivotTable {
        private DefaultPivotTable() {
        }

        @Override
        public final void accept(Context<?> ctx) {
            ctx.declareTables(true, c -> c.visit(this.select(ctx.configuration())));
        }

        /*
         * WARNING - void declaration
         */
        private Table<Record> select(Configuration configuration) {
            void var8_13;
            ArrayList groupingFields = new ArrayList();
            ArrayList aliasedGroupingFields = new ArrayList();
            ArrayList<Field> aggregatedFields = new ArrayList<Field>();
            SelectField pivot = Pivot.this.table.as("pivot_outer");
            for (Field field2 : Pivot.this.aggregateFunctions) {
                if (!(field2 instanceof AbstractAggregateFunction)) continue;
                AbstractAggregateFunction abstractAggregateFunction = (AbstractAggregateFunction)field2;
                for (QueryPart queryPart : abstractAggregateFunction.getArguments()) {
                    if (!(queryPart instanceof Field)) continue;
                    Field f = (Field)queryPart;
                    aggregatedFields.add(f);
                }
            }
            Field<?>[] fieldArray = Pivot.this.table.fields();
            int n = fieldArray.length;
            boolean bl = false;
            while (var8_13 < n) {
                Field<?> field3 = fieldArray[var8_13];
                if (!aggregatedFields.contains(field3) && !Pivot.this.for_.equals(field3)) {
                    aliasedGroupingFields.add(pivot.field(field3));
                    groupingFields.add(field3);
                }
                ++var8_13;
            }
            ArrayList aggregationSelects = new ArrayList();
            for (Field field3 : Pivot.this.in) {
                for (Field field4 : Pivot.this.aggregateFunctions) {
                    void var11_22;
                    Condition condition = DSL.noCondition();
                    for (Field field5 : groupingFields) {
                        Condition condition2 = var11_22.and(Pivot.this.condition((Table<?>)pivot, field5));
                    }
                    SelectConditionStep aggregateSelect = DSL.using(configuration).select(field4).from((TableLike<?>)Pivot.this.table).where(Pivot.this.for_.equal(field3)).and((Condition)var11_22);
                    aggregationSelects.add(aggregateSelect.asField(field3.getName() + "_" + field4.getName()));
                }
            }
            Table<Record> table = DSL.using(configuration).select(aliasedGroupingFields).select(aggregationSelects).from((TableLike<?>)((Object)pivot)).where(pivot.field(Pivot.this.for_).in(Pivot.this.in.toArray((E[])Tools.EMPTY_FIELD))).groupBy(aliasedGroupingFields).asTable();
            return table;
        }
    }

    private class OraclePivotTable
    extends DialectPivotTable {
        private OraclePivotTable() {
        }

        @Override
        public final void accept(Context<?> ctx) {
            ParamType paramType = ctx.paramType();
            boolean declareFields = ctx.declareFields();
            ctx.declareTables(true, c -> c.visit(Pivot.this.table)).formatSeparator().visit(Keywords.K_PIVOT).sql(" (").paramType(ParamType.INLINED).declareFields(true).formatIndentStart().visit(Pivot.this.aggregateFunctions).formatSeparator().visit(Keywords.K_FOR).sql(' ').visit(Pivot.this.for_.getUnqualifiedName()).formatSeparator().visit(Keywords.K_IN).sql(" (");
            if (ctx.family() == SQLDialect.SQLSERVER) {
                ctx.visit(QueryPartCollectionView.wrap(Tools.map(Pivot.this.in, Named::getUnqualifiedName)));
            } else {
                ctx.visit(Pivot.this.in);
            }
            ctx.declareFields(declareFields).paramType(paramType).sql(')').formatIndentEnd().formatNewLine().sql(')');
        }
    }

    private abstract class DialectPivotTable
    extends AbstractTable<Record>
    implements QOM.UTransient {
        DialectPivotTable() {
            super(TableOptions.expression(), Names.N_PIVOT);
        }

        @Override
        public final Class<? extends Record> getRecordType() {
            return RecordImplN.class;
        }

        @Override
        public final Table<Record> as(Name as) {
            return new TableAlias<Record>(this, as);
        }

        @Override
        public final Table<Record> as(Name as, Name ... fieldAliases) {
            return new TableAlias<Record>(this, as, fieldAliases);
        }

        @Override
        final FieldsImpl<Record> fields0() {
            return Pivot.this.fields0();
        }
    }
}

