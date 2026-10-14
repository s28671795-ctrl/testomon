/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.FieldOrRowOrSelect;
import org.jooq.GeneratorStatementType;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Row;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.conf.WriteIfReadonly;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractQueryPartMap;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldMapsForInsert;
import org.jooq.impl.Keywords;
import org.jooq.impl.NoField;
import org.jooq.impl.ProjectSingleScalarSubquery;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.Tools;

final class FieldMapForUpdate
extends AbstractQueryPartMap<FieldOrRow, FieldOrRowOrSelect> {
    static final Set<SQLDialect> CASTS_NEEDED = SQLDialect.supportedBy(SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> NO_SUPPORT_QUALIFY = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.POSTGRES, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.TERADATA, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> EMULATE_RVE_SET_QUERY = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.REDSHIFT, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.VERTICA);
    @Pro
    static final Set<SQLDialect> EMULATE_RVE_SET_QUERY_UPDATE_FROM = SQLDialect.supportedUntil(SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.POSTGRES_9_4, SQLDialect.SNOWFLAKE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    @Pro
    static final Set<SQLDialect> NO_SUPPORT_RVE_SET_RHS = SQLDialect.supportedBy(SQLDialect.INGRES, SQLDialect.ORACLE);
    @Pro
    static final Set<SQLDialect> SET_ORDER_IS_RELEVANT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    static final Set<SQLDialect> NO_SUPPORT_RVE_SET = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    static final Set<SQLDialect> NO_SUPPORT_RVE_SET_IN_MERGE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.IGNITE, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    static final Set<SQLDialect> REQUIRE_RVE_ROW_CLAUSE = SQLDialect.supportedBy(SQLDialect.POSTGRES_10, SQLDialect.YUGABYTEDB);
    final Table<?> table;
    final SetClause setClause;
    final Clause assignmentClause;

    FieldMapForUpdate(FieldMapForUpdate um, SetClause setClause) {
        this(um.table, setClause, um.assignmentClause);
        this.putAll(um);
    }

    FieldMapForUpdate(Table<?> table, SetClause setClause, Clause assignmentClause) {
        this.table = table;
        this.setClause = setClause;
        this.assignmentClause = assignmentClause;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.size() > 0) {
            String separator = "";
            boolean supportsQualify = !NO_SUPPORT_QUALIFY.contains((Object)ctx.dialect()) && ctx.qualify();
            RenderContext.CastMode previous = ctx.castMode();
            if (!CASTS_NEEDED.contains((Object)ctx.dialect())) {
                ctx.castMode(RenderContext.CastMode.NEVER);
            }
            for (Map.Entry<FieldOrRow, FieldOrRowOrSelect> entry : Tools.flattenEntrySet(this.entrySet(), true)) {
                Field f;
                FieldOrRow key = entry.getKey();
                FieldOrRowOrSelect value = entry.getValue();
                if (ctx.settings().getReadonlyUpdate() == WriteIfReadonly.IGNORE && key instanceof Field && (f = (Field)key).getDataType().readonlyInternal(ctx.configuration())) continue;
                separator = this.acceptAssignmentClause(ctx, supportsQualify, key, value, separator);
            }
            if (!CASTS_NEEDED.contains((Object)ctx.dialect())) {
                ctx.castMode(previous);
            }
        } else {
            ctx.sql("[ no fields are updated ]");
        }
    }

    private final String acceptAssignmentClause(Context<?> ctx, boolean supportsQualify, FieldOrRow key, FieldOrRowOrSelect value, String separator) {
        if (!"".equals(separator)) {
            ctx.sql(separator).formatSeparator();
        }
        if (this.assignmentClause != null) {
            ctx.start(this.assignmentClause);
        }
        if (key instanceof Row) {
            Select<?> multiSelect;
            Row multiRow = (Row)key;
            Row multiValue = value instanceof Row ? (Row)value : null;
            Select<?> select = multiSelect = value instanceof Select ? (Select<?>)value : null;
            if (multiValue != null && (NO_SUPPORT_RVE_SET.contains((Object)ctx.dialect()) || NO_SUPPORT_RVE_SET_IN_MERGE.contains((Object)ctx.dialect()) && this.setClause == SetClause.MERGE)) {
                FieldMapForUpdate map = new FieldMapForUpdate(DSL.table(new Record[0]), this.setClause, null);
                for (int i = 0; i < multiRow.size(); ++i) {
                    Field<?> k = multiRow.field(i);
                    Field<?> v = multiValue.field(i);
                    map.put(k, Tools.field(v, k));
                }
                ctx.visit(map);
            } else if (multiSelect != null && EMULATE_RVE_SET_QUERY_UPDATE_FROM.contains((Object)ctx.dialect()) && this.setClause == SetClause.UPDATE) {
                FieldMapForUpdate map = new FieldMapForUpdate(DSL.table(new Record[0]), this.setClause, null);
                List<Field<?>> fields = multiSelect.getSelect();
                for (int i = 0; i < fields.size(); ++i) {
                    Field<?> k = multiRow.field(i);
                    Field v = DSL.field(Tools.fieldName(i), fields.get(i).getDataType());
                    map.put(k, Tools.field(v, k));
                }
                ctx.visit(map);
            } else if (multiSelect != null && (EMULATE_RVE_SET_QUERY.contains((Object)ctx.dialect()) || NO_SUPPORT_RVE_SET_IN_MERGE.contains((Object)ctx.dialect()) && this.setClause == SetClause.MERGE)) {
                Row row = FieldMapForUpdate.removeReadonly(ctx, multiRow);
                int size = row.size();
                Select<?> select2 = size < multiRow.size() ? FieldMapForUpdate.removeReadonly(multiRow, row, multiSelect) : multiSelect;
                if (size == 1) {
                    FieldMapForUpdate.acceptStoreAssignment(ctx, false, row.field(0));
                    FieldMapForUpdate.visitSubquery(ctx, select2);
                } else {
                    for (int i = 0; i < size; ++i) {
                        FieldMapForUpdate mu = new FieldMapForUpdate(this.table, this.setClause, null);
                        separator = mu.acceptAssignmentClause(ctx, supportsQualify, row.field(i), new ProjectSingleScalarSubquery(select2, i), separator);
                    }
                }
            } else {
                Row row = FieldMapForUpdate.removeReadonly(ctx, multiRow);
                FieldMapForUpdate.acceptStoreAssignment(ctx, false, row);
                if (multiValue != null) {
                    if (NO_SUPPORT_RVE_SET_RHS.contains((Object)ctx.dialect())) {
                        FieldMapForUpdate.visitSubquery(ctx, DSL.select(FieldMapForUpdate.removeReadonly(ctx, multiRow, multiValue).fields()));
                    } else {
                        if (REQUIRE_RVE_ROW_CLAUSE.contains((Object)ctx.dialect())) {
                            ctx.visit(Keywords.K_ROW).sql(" ");
                        }
                        ctx.visit(FieldMapForUpdate.removeReadonly(ctx, multiRow, multiValue));
                    }
                } else if (multiSelect != null) {
                    if (row.size() < multiRow.size()) {
                        FieldMapForUpdate.visitSubquery(ctx, FieldMapForUpdate.removeReadonly(multiRow, row, multiSelect));
                    } else {
                        FieldMapForUpdate.visitSubquery(ctx, multiSelect);
                    }
                }
            }
        } else {
            FieldMapForUpdate.acceptStoreAssignment(ctx, supportsQualify, key);
            Condition condition = (Condition)ctx.data(Tools.SimpleDataKey.DATA_ON_DUPLICATE_KEY_WHERE);
            if (condition != null) {
                ctx.visit(DSL.when(condition, (Field)value).else_(key));
            } else if (ctx.family() == SQLDialect.ORACLE) {
                ctx.data(Tools.BooleanDataKey.DATA_FORCE_LIMIT_WITH_ORDER_BY, true, c -> c.visit(value));
            } else {
                ctx.visit(value);
            }
        }
        if (this.assignmentClause != null) {
            ctx.end(this.assignmentClause);
        }
        return ",";
    }

    private static final void acceptStoreAssignment(Context<?> ctx, boolean qualify, QueryPart target) {
        ctx.qualify(qualify, c1 -> c1.data(Tools.BooleanDataKey.DATA_STORE_ASSIGNMENT, true, c2 -> c2.visit(target))).sql(" = ");
    }

    private static final void visitSubquery(Context<?> ctx, Select<?> select) {
        if (ctx.family() == SQLDialect.ORACLE) {
            ctx.data(Tools.BooleanDataKey.DATA_FORCE_LIMIT_WITH_ORDER_BY, true, c -> Tools.visitSubquery(c, select));
        } else {
            Tools.visitSubquery(ctx, select);
        }
    }

    static final Row removeReadonly(Context<?> ctx, Row row) {
        return FieldMapForUpdate.removeReadonly(ctx, row, row);
    }

    static final Row removeReadonly(Context<?> ctx, Row checkRow, Row removeRow) {
        if (ctx.settings().getReadonlyUpdate() == WriteIfReadonly.IGNORE && Tools.anyMatch(checkRow.fields(), f -> f.getDataType().readonlyInternal(ctx.configuration()))) {
            removeRow = Tools.row0(Tools.collect(Tools.filter(removeRow.fields(), (f, i) -> !checkRow.field(i).getDataType().readonlyInternal(ctx.configuration()))));
        }
        return removeRow;
    }

    final void set(Map<?, ?> map) {
        map.forEach((k, v) -> {
            if (k instanceof Row) {
                Row r = (Row)k;
                this.put(r, (FieldOrRowOrSelect)v);
            } else {
                Field<?> field2 = Tools.tableField(this.table, k);
                this.put(field2, Tools.field(v, field2));
            }
        });
    }

    @Override
    public FieldOrRowOrSelect put(FieldOrRow key, FieldOrRowOrSelect value) {
        if (key instanceof NoField || value instanceof NoField) {
            return null;
        }
        return super.put(key, value);
    }

    @Pro
    private static final Select<?> removeReadonly(Row multiRow, Row row, Select<?> multiSelect) {
        return DSL.select(Tools.map(row.fields(), t2 -> Tools.unqualified(t2))).from((TableLike<?>)multiSelect.asTable(DSL.table(DSL.name("t")), multiRow.fields()));
    }

    @Pro
    final void computeOnClientStoredReorder(Context<?> ctx) {
        Set<Field<?>> keys2 = FieldMapsForInsert.keysFlattenedAndComputedOnClientStored(ctx, Tools.flattenFieldOrRows(this.keySet(), new LinkedHashSet()), this.table, GeneratorStatementType.UPDATE);
        LinkedHashMap<FieldOrRow, FieldOrRowOrSelect> copy = new LinkedHashMap<FieldOrRow, FieldOrRowOrSelect>(this);
        this.clear();
        ThrowingFunction<FieldOrRow, Field<FieldOrRow>, RuntimeException> mapper2 = FieldMapsForInsert.computeOnClientStoredFieldMapper(ctx, true, GeneratorStatementType.UPDATE);
        for (FieldOrRow fieldOrRow : keys2) {
            if (((Field)fieldOrRow).getDataType().computedOnClientStored(ctx.configuration())) {
                Tools.apply(mapper2.apply(fieldOrRow), v -> this.put(key, (FieldOrRowOrSelect)v));
                continue;
            }
            FieldOrRow t2 = (FieldOrRow)copy.get(fieldOrRow);
            if (t2 == null) continue;
            Tools.apply(mapper2.apply(t2), v -> this.put(key, (FieldOrRowOrSelect)v));
        }
    }

    @Pro
    final void computeOnClientStoredReplace(Context<?> ctx) {
        if (Tools.anyMatch(this.keySet(), k -> k instanceof Row)) {
            this.computeOnClientStoredSelect(ctx);
        } else if (SET_ORDER_IS_RELEVANT.contains((Object)ctx.dialect())) {
            this.computeOnClientStoredReorder(ctx);
        } else {
            this.computeOnClientStoredReplace0(ctx);
        }
    }

    @Pro
    final void computeOnClientStoredReplace0(Context<?> ctx) {
        Set<Field<?>> keys2 = FieldMapsForInsert.keysFlattenedAndComputedOnClientStored(ctx, Tools.flattenFieldOrRows(this.keySet(), new LinkedHashSet()), this.table, GeneratorStatementType.UPDATE);
        LinkedHashSet keysNotComputed = new LinkedHashSet(keys2);
        keysNotComputed.removeIf(f -> f.getDataType().computedOnClientStoredOn(GeneratorStatementType.UPDATE, ctx.configuration()));
        LinkedHashMap<FieldOrRow, FieldOrRowOrSelect> copy = new LinkedHashMap<FieldOrRow, FieldOrRowOrSelect>(this);
        this.clear();
        ThrowingFunction<FieldOrRow, Field<FieldOrRow>, RuntimeException> mapper2 = FieldMapsForInsert.computeOnClientStoredFieldMapper(ctx, true, GeneratorStatementType.UPDATE);
        for (FieldOrRow fieldOrRow : keys2) {
            if (((Field)fieldOrRow).getDataType().computedOnClientStored(ctx.configuration())) {
                Tools.apply(mapper2.apply(fieldOrRow), v -> this.put(key, (FieldOrRowOrSelect)v));
                continue;
            }
            FieldOrRow t2 = (FieldOrRow)copy.get(fieldOrRow);
            if (t2 == null) continue;
            Tools.apply(mapper2.apply(t2), v -> this.put(key, (FieldOrRowOrSelect)v));
        }
        for (Map.Entry entry : this.entrySet()) {
            if (!((Field)entry.getKey()).getDataType().computedOnClientStored(ctx.configuration())) continue;
            entry.setValue((FieldOrRowOrSelect)((FieldOrRowOrSelect)entry.getValue()).$replace((? super QueryPart p) -> keysNotComputed.contains(p) ? this.get(p) : p));
        }
    }

    @Pro
    final void computeOnClientStoredSelect(Context<?> ctx) {
        RowN row = DSL.row(FieldMapsForInsert.keysFlattenedAndComputedOnClientStored(ctx, Tools.flattenFieldOrRows(this.keySet(), new LinkedHashSet()), this.table, GeneratorStatementType.UPDATE));
        SelectSelectStep<Record> s2 = (SelectSelectStep<Record>)Tools.findAny(this.values(), frs -> frs instanceof Select);
        if (s2 == null) {
            s2 = DSL.select(Tools.flattenFieldOrRows(this.values(), new ArrayList()));
        }
        Select<?> select = FieldMapsForInsert.computeOnClientStoredSelect(ctx, this.keySet(), this.table, s2, GeneratorStatementType.UPDATE);
        this.clear();
        this.put(row, select);
    }

    @Pro
    final void checkReadonly(Context<?> ctx) {
        if (WriteIfReadonly.THROW.equals((Object)ctx.settings().getReadonlyUpdate())) {
            for (FieldOrRow fr : this.keySet()) {
                for (Field<?> f : Tools.flattenFieldOrRow(fr)) {
                    if (!f.getDataType().readonly()) continue;
                    throw new DataTypeException("Cannot update readonly column: " + String.valueOf(f));
                }
            }
        }
    }

    @Override
    final Function<? super Map<FieldOrRow, FieldOrRowOrSelect>, ? extends AbstractQueryPartMap<FieldOrRow, FieldOrRowOrSelect>> $construct() {
        return m4 -> {
            FieldMapForUpdate r = new FieldMapForUpdate(this.table, this.setClause, this.assignmentClause);
            r.putAll(m4);
            return r;
        };
    }

    static enum SetClause {
        UPDATE,
        INSERT,
        MERGE;

    }
}

