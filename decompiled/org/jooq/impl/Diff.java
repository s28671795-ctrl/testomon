/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jooq.AlterSequenceFlagsStep;
import org.jooq.AlterSequenceStep;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Configuration;
import org.jooq.DDLExportConfiguration;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Key;
import org.jooq.Meta;
import org.jooq.MigrationConfiguration;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Nullability;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.Comparators;
import org.jooq.impl.ConstraintType;
import org.jooq.impl.DDL;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Diff {
    private static final Set<SQLDialect> NO_SUPPORT_PK_NAMES = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.IGNITE, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private final MigrationConfiguration migrateConf;
    private final DDLExportConfiguration exportConf;
    private final DSLContext ctx;
    private final Meta meta1;
    private final Meta meta2;
    private final DDL ddl;
    private final Merge<Table<?>> MERGE_TABLE = new Merge<Table<?>>(){

        @Override
        public void merge(DiffResult r, Table<?> t1, Table<?> t2) {
            String c2;
            String c1;
            boolean v1 = t1.getTableType().isView();
            boolean v2 = t2.getTableType().isView();
            if (v1 && v2) {
                if (!Arrays.equals(t1.fields(), t2.fields()) || t2.getOptions().select() != null && !t2.getOptions().select().equals(t1.getOptions().select()) || t2.getOptions().source() != null && !t2.getOptions().source().equals(t1.getOptions().source())) {
                    this.replaceView(r, t1, t2);
                    return;
                }
            } else {
                if (v1 != v2) {
                    this.replaceView(r, t1, t2);
                    return;
                }
                Diff.this.appendColumns(r, t1, Arrays.asList(t1.fields()), Arrays.asList(t2.fields()));
                Diff.this.appendPrimaryKey(r, t1, Arrays.asList(t1.getPrimaryKey()), Arrays.asList(t2.getPrimaryKey()));
                Diff.this.appendUniqueKeys(r, t1, Diff.this.removePrimary(t1.getKeys()), Diff.this.removePrimary(t2.getKeys()));
                Diff.this.appendForeignKeys(r, t1, t1.getReferences(), t2.getReferences());
                Diff.this.appendChecks(r, t1, t1.getChecks(), t2.getChecks());
                Diff.this.appendIndexes(r, t1, t1.getIndexes(), t2.getIndexes());
            }
            if (!(c1 = StringUtils.defaultString(t1.getComment())).equals(c2 = StringUtils.defaultString(t2.getComment()))) {
                if (v2) {
                    r.queries.add(Diff.this.ctx.commentOnView(t2).is(c2));
                } else {
                    r.queries.add(Diff.this.ctx.commentOnTable(t2).is(c2));
                }
            }
        }

        private void replaceView(DiffResult r, Table<?> v1, Table<?> v2) {
            if (!Diff.this.migrateConf.createOrReplaceView()) {
                Diff.this.dropTable().drop(r, v1);
            }
            Diff.this.createTable().create(r, v2);
        }
    };

    Diff(Configuration configuration, MigrationConfiguration migrateConf, Meta meta1, Meta meta2) {
        this.migrateConf = migrateConf;
        this.exportConf = new DDLExportConfiguration().createOrReplaceView(migrateConf.createOrReplaceView());
        this.ctx = configuration.dsl();
        this.meta1 = meta1;
        this.meta2 = meta2;
        this.ddl = new DDL(this.ctx, this.exportConf);
    }

    final Queries queries() {
        return this.ctx.queries(this.appendCatalogs((DiffResult)new DiffResult(), this.meta1.getCatalogs(), this.meta2.getCatalogs()).queries);
    }

    private final DiffResult appendCatalogs(DiffResult result, List<Catalog> l1, List<Catalog> l2) {
        return this.append(result, l1, l2, null, null, null, (r, c1, c2) -> this.appendSchemas(r, c1.getSchemas(), c2.getSchemas()));
    }

    private final DiffResult appendSchemas(DiffResult result, List<Schema> l1, List<Schema> l2) {
        return this.append(result, l1, l2, null, (r, s2) -> r.queries.addAll(Arrays.asList(this.ctx.ddl((Schema)s2).queries())), (r, s2) -> {
            if (s2.getTables().isEmpty() && s2.getSequences().isEmpty()) {
                if (!StringUtils.isEmpty(s2.getName())) {
                    r.queries.add(this.ctx.dropSchema((Schema)s2));
                }
            } else if (this.migrateConf.dropSchemaCascade()) {
                for (Table<?> table : s2.getTables()) {
                    for (UniqueKey<?> uk : table.getKeys()) {
                        r.droppedFks.addAll(uk.getReferences());
                    }
                }
                if (!StringUtils.isEmpty(s2.getName())) {
                    r.queries.add(this.ctx.dropSchema((Schema)s2).cascade());
                }
            } else {
                for (Table<?> table : s2.getTables()) {
                    this.dropTable().drop(r, table);
                }
                for (Sequence sequence2 : s2.getSequences()) {
                    this.dropSequence().drop(r, sequence2);
                }
                if (!StringUtils.isEmpty(s2.getName())) {
                    r.queries.add(this.ctx.dropSchema((Schema)s2));
                }
            }
        }, (r, s1, s2) -> {
            this.appendDomains(r, s1.getDomains(), s2.getDomains());
            this.appendTables(r, s1.getTables(), s2.getTables());
            this.appendSequences(r, s1.getSequences(), s2.getSequences());
        });
    }

    private final Drop<Sequence<?>> dropSequence() {
        return (r, s2) -> r.queries.add(this.ctx.dropSequence((Sequence<?>)s2));
    }

    private final DiffResult appendSequences(DiffResult result, List<? extends Sequence<?>> l1, List<? extends Sequence<?>> l2) {
        return this.append(result, l1, l2, null, (r, s2) -> r.queries.add(this.ddl.createSequence((Sequence<?>)s2)), this.dropSequence(), (r, s1, s2) -> {
            AlterSequenceFlagsStep<Object> stmt = null;
            AlterSequenceStep stmt0 = this.ctx.alterSequence(s1);
            if (s2.getStartWith() != null && !s2.getStartWith().equals(s1.getStartWith())) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).startWith(s2.getStartWith());
            } else if (s2.getStartWith() == null && s1.getStartWith() != null) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).startWith(1);
            }
            if (s2.getIncrementBy() != null && !s2.getIncrementBy().equals(s1.getIncrementBy())) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).incrementBy(s2.getIncrementBy());
            } else if (s2.getIncrementBy() == null && s1.getIncrementBy() != null) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).incrementBy(1);
            }
            if (s2.getMinvalue() != null && !s2.getMinvalue().equals(s1.getMinvalue())) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).minvalue(s2.getMinvalue());
            } else if (s2.getMinvalue() == null && s1.getMinvalue() != null) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).noMinvalue();
            }
            if (s2.getMaxvalue() != null && !s2.getMaxvalue().equals(s1.getMaxvalue())) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).maxvalue(s2.getMaxvalue());
            } else if (s2.getMaxvalue() == null && s1.getMaxvalue() != null) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).noMaxvalue();
            }
            if (s2.getCache() != null && !s2.getCache().equals(s1.getCache())) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).cache(s2.getCache());
            } else if (s2.getCache() == null && s1.getCache() != null) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).noCache();
            }
            if (s2.getCycle() && !s1.getCycle()) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).cycle();
            } else if (!s2.getCycle() && s1.getCycle()) {
                stmt = ((AlterSequenceFlagsStep)StringUtils.defaultIfNull(stmt, stmt0)).noCycle();
            }
            if (stmt != null) {
                r.queries.add(stmt);
            }
        });
    }

    private final DiffResult appendDomains(DiffResult result, List<? extends Domain<?>> l1, List<? extends Domain<?>> l2) {
        return this.append(result, l1, l2, null, (r, d) -> r.queries.add(this.ddl.createDomain((Domain<?>)d)), (r, d) -> r.queries.add(this.ctx.dropDomain((Domain<?>)d)), (r, d1, d2) -> {
            if (!d1.getDataType().getSQLDataType().equals(d2.getDataType().getSQLDataType())) {
                r.queries.addAll(Arrays.asList(this.ctx.dropDomain((Domain<?>)d1), this.ddl.createDomain((Domain<?>)d2)));
            } else {
                if (d1.getDataType().defaulted() && !d2.getDataType().defaulted()) {
                    r.queries.add(this.ctx.alterDomain(d1).dropDefault());
                } else if (d2.getDataType().defaulted() && !d2.getDataType().defaultValue().equals(d1.getDataType().defaultValue())) {
                    r.queries.add(this.ctx.alterDomain(d1).setDefault(d2.getDataType().defaultValue()));
                }
                this.appendChecks(r, (Domain<?>)d1, (List<? extends Check<?>>)d1.getChecks(), (List<? extends Check<?>>)d2.getChecks());
            }
        });
    }

    private final Create<Table<?>> createTable() {
        return (r, t2) -> r.queries.addAll(Arrays.asList(this.ddl.queries((Table<?>)t2).queries()));
    }

    private final Drop<Table<?>> dropTable() {
        return (r, t2) -> {
            for (UniqueKey uk : t2.getKeys()) {
                for (ForeignKey fk : uk.getReferences()) {
                    if (!r.droppedFks.add(fk) || this.migrateConf.dropTableCascade()) continue;
                    r.queries.add(this.ctx.alterTable(fk.getTable()).dropForeignKey(fk.constraint()));
                }
            }
            if (t2.getTableType().isView()) {
                r.queries.add(this.ctx.dropView((Table<?>)t2));
            } else if (t2.getTableType() == TableOptions.TableType.TEMPORARY) {
                r.queries.add(this.ctx.dropTemporaryTable((Table<?>)t2));
            } else {
                r.queries.add(this.migrateConf.dropTableCascade() ? this.ctx.dropTable((Table<?>)t2).cascade() : this.ctx.dropTable((Table<?>)t2));
            }
        };
    }

    private final DiffResult appendTables(DiffResult result, List<? extends Table<?>> l1, List<? extends Table<?>> l2) {
        return this.append(result, l1, l2, null, this.createTable(), this.dropTable(), this.MERGE_TABLE);
    }

    private final List<UniqueKey<?>> removePrimary(List<? extends UniqueKey<?>> list) {
        ArrayList result = new ArrayList();
        for (UniqueKey<?> uk : list) {
            if (uk.isPrimary()) continue;
            result.add(uk);
        }
        return result;
    }

    private final boolean isSynthetic(Field<?> f) {
        switch (this.ctx.family()) {
            case COCKROACHDB: {
                return "rowid".equals(f.getName()) && f.getType() == Long.class && !f.getDataType().nullable() && f.getDataType().defaultValue() != null && f.getDataType().defaultValue().getName().equals("unique_rowid");
            }
        }
        return false;
    }

    private final boolean isSynthetic(UniqueKey<?> pk) {
        switch (this.ctx.family()) {
            case COCKROACHDB: {
                if (!"primary".equals(pk.getName())) {
                    return false;
                }
                return Tools.allMatch(pk.getFields(), f -> this.isSynthetic((Field<?>)f));
            }
        }
        return false;
    }

    private final DiffResult appendColumns(DiffResult result, final Table<?> t1, List<? extends Field<?>> l1, List<? extends Field<?>> l2) {
        ArrayList add = new ArrayList();
        ArrayList drop = new ArrayList();
        result = this.append(result, l1, l2, null, (r, f) -> {
            if (!this.isSynthetic((Field<?>)f)) {
                if (this.migrateConf.alterTableAddMultiple()) {
                    add.add(f);
                } else {
                    r.queries.add(this.ctx.alterTable(t1).add((Field<?>)f));
                }
            }
        }, (r, f) -> {
            if (!this.isSynthetic((Field<?>)f)) {
                if (this.migrateConf.alterTableDropMultiple()) {
                    drop.add(f);
                } else {
                    r.queries.add(this.ctx.alterTable(t1).drop((Field<?>)f));
                }
            }
        }, new Merge<Field<?>>(){

            @Override
            public void merge(DiffResult r, Field<?> f1, Field<?> f2) {
                DataType type2;
                DataType type1 = f1.getDataType();
                if (this.typeNameDifference(type1, type2 = f2.getDataType())) {
                    r.queries.add(Diff.this.ctx.alterTable(t1).alter(f1).set(type2.nullability(Nullability.DEFAULT)));
                }
                if (type1.nullable() && !type2.nullable()) {
                    r.queries.add(Diff.this.ctx.alterTable(t1).alter(f1).setNotNull());
                } else if (!type1.nullable() && type2.nullable()) {
                    r.queries.add(Diff.this.ctx.alterTable(t1).alter(f1).dropNotNull());
                }
                Field d1 = type1.defaultValue();
                Field d2 = type2.defaultValue();
                if (type1.defaulted() && !type2.defaulted()) {
                    r.queries.add(Diff.this.ctx.alterTable(t1).alter(f1).dropDefault());
                } else if (!(!type2.defaulted() || type1.defaulted() && d2.equals(d1))) {
                    r.queries.add(Diff.this.ctx.alterTable(t1).alter(f1).setDefault(d2));
                }
                if (type1.hasLength() && type2.hasLength() && (type1.lengthDefined() != type2.lengthDefined() || type1.length() != type2.length()) || type1.hasPrecision() && type2.hasPrecision() && this.precisionDifference(type1, type2) || type1.hasScale() && type2.hasScale() && (type1.scaleDefined() != type2.scaleDefined() || type1.scale() != type2.scale())) {
                    r.queries.add(Diff.this.ctx.alterTable(t1).alter(f1).set(type2));
                }
            }

            private final boolean typeNameDifference(DataType<?> type1, DataType<?> type2) {
                if (type1.getTypeName().equals(type2.getTypeName())) {
                    return false;
                }
                return type1.getType() != BigDecimal.class || type2.getType() != BigDecimal.class;
            }

            private final boolean precisionDifference(DataType<?> type1, DataType<?> type2) {
                boolean d1 = this.defaultPrecision(type1);
                boolean d2 = this.defaultPrecision(type2);
                if (d1 || d2) {
                    return d1 != d2;
                }
                return type1.precision() != type2.precision();
            }

            private final boolean defaultPrecision(DataType<?> type) {
                if (!type.isDateTime()) {
                    return false;
                }
                if (!type.precisionDefined()) {
                    return true;
                }
                if (Tools.NO_SUPPORT_TIMESTAMP_PRECISION.contains((Object)Diff.this.ctx.dialect())) {
                    return true;
                }
                if (Boolean.FALSE.equals(Diff.this.ctx.settings().isMigrationIgnoreDefaultTimestampPrecisionDiffs())) {
                    return false;
                }
                switch (Diff.this.ctx.family()) {
                    case MARIADB: {
                        return type.precision() == 0;
                    }
                }
                return type.precision() == 6;
            }
        });
        if (!drop.isEmpty()) {
            result.queries.add(0, this.ctx.alterTable(t1).drop(drop));
        }
        if (!add.isEmpty()) {
            result.queries.add(this.ctx.alterTable(t1).add(add));
        }
        return result;
    }

    private final DiffResult appendPrimaryKey(DiffResult result, Table<?> t1, List<? extends UniqueKey<?>> pk1, List<? extends UniqueKey<?>> pk2) {
        Create<UniqueKey> create = (r, pk) -> {
            if (!this.isSynthetic((UniqueKey<?>)pk)) {
                r.queries.add(this.ctx.alterTable(t1).add(pk.constraint()));
            }
        };
        Drop<UniqueKey> drop = (r, pk) -> {
            if (!this.isSynthetic((UniqueKey<?>)pk)) {
                if (StringUtils.isEmpty(pk.getName())) {
                    r.queries.add(this.ctx.alterTable(t1).dropPrimaryKey());
                } else {
                    r.queries.add(this.ctx.alterTable(t1).dropPrimaryKey(pk.constraint()));
                }
            }
        };
        return this.append(result, pk1, pk2, Comparators.KEY_COMP, create, drop, this.keyMerge(t1, create, drop, ConstraintType.PRIMARY_KEY), true);
    }

    private final DiffResult appendUniqueKeys(DiffResult result, Table<?> t1, List<? extends UniqueKey<?>> uk1, List<? extends UniqueKey<?>> uk2) {
        Create<UniqueKey> create = (r, u) -> r.queries.add(this.ctx.alterTable(t1).add(u.constraint()));
        Drop<UniqueKey> drop = (r, u) -> r.queries.add(this.ctx.alterTable(t1).dropUnique(u.constraint()));
        return this.append(result, uk1, uk2, Comparators.KEY_COMP, create, drop, this.keyMerge(t1, create, drop, ConstraintType.UNIQUE), true);
    }

    private final <K extends Named> Merge<K> keyMerge(Table<?> t1, Create<K> create, Drop<K> drop, ConstraintType type) {
        return (r, k1, k2) -> {
            boolean e2;
            boolean e1;
            Name n1 = k1.getUnqualifiedName();
            Name n2 = k2.getUnqualifiedName();
            if (n1.empty() ^ n2.empty()) {
                drop.drop(r, k1);
                create.create(r, k2);
                return;
            }
            if (!(Comparators.NAMED_COMP.compare(k1, k2) == 0 || type == ConstraintType.PRIMARY_KEY && NO_SUPPORT_PK_NAMES.contains((Object)this.ctx.dialect()))) {
                r.queries.add(this.ctx.alterTable(t1).renameConstraint(n1).to(n2));
            }
            if (k1 instanceof Check) {
                Check c1 = (Check)k1;
                v0 = c1.enforced();
            } else {
                v0 = e1 = ((Key)k1).enforced();
            }
            if (k2 instanceof Check) {
                Check c2 = (Check)k2;
                v1 = c2.enforced();
            } else {
                v1 = e2 = ((Key)k2).enforced();
            }
            if (e1 && !e2) {
                r.queries.add(this.ctx.alterTable(t1).alterConstraint(n1).notEnforced());
            } else if (!e1 && e2) {
                r.queries.add(this.ctx.alterTable(t1).alterConstraint(n1).enforced());
            }
        };
    }

    private final <K extends Named> Merge<K> keyMerge(Domain<?> d1, Create<K> create, Drop<K> drop) {
        return (r, k1, k2) -> {
            Name n1 = k1.getUnqualifiedName();
            Name n2 = k2.getUnqualifiedName();
            if (n1.empty() ^ n2.empty()) {
                drop.drop(r, k1);
                create.create(r, k2);
                return;
            }
            if (Comparators.NAMED_COMP.compare(k1, k2) != 0) {
                r.queries.add(this.ctx.alterDomain(d1).renameConstraint(n1).to(n2));
            }
        };
    }

    private final DiffResult appendForeignKeys(DiffResult result, Table<?> t1, List<? extends ForeignKey<?, ?>> fk1, List<? extends ForeignKey<?, ?>> fk2) {
        Create<ForeignKey> create = (r, fk) -> r.queries.add(this.ctx.alterTable(t1).add(fk.constraint()));
        Drop<ForeignKey> drop = (r, fk) -> {
            if (r.droppedFks.add((ForeignKey<?, ?>)fk)) {
                r.queries.add(this.ctx.alterTable(t1).dropForeignKey(fk.constraint()));
            }
        };
        return this.append(result, fk1, fk2, Comparators.FOREIGN_KEY_COMP, create, drop, this.keyMerge(t1, create, drop, ConstraintType.FOREIGN_KEY), true);
    }

    private final DiffResult appendChecks(DiffResult result, Table<?> t1, List<? extends Check<?>> c1, List<? extends Check<?>> c2) {
        Create<Check> create = (r, c) -> r.queries.add(this.ctx.alterTable(t1).add(c.constraint()));
        Drop<Check> drop = (r, c) -> r.queries.add(this.ctx.alterTable(t1).drop(c.constraint()));
        return this.append(result, c1, c2, Comparators.CHECK_COMP, create, drop, this.keyMerge(t1, create, drop, ConstraintType.CHECK), true);
    }

    private final DiffResult appendChecks(DiffResult result, Domain<?> d1, List<? extends Check<?>> c1, List<? extends Check<?>> c2) {
        Create<Check> create = (r, c) -> r.queries.add(this.ctx.alterDomain(d1).add(c.constraint()));
        Drop<Check> drop = (r, c) -> r.queries.add(this.ctx.alterDomain(d1).dropConstraint(c.constraint()));
        return this.append(result, c1, c2, Comparators.CHECK_COMP, create, drop, this.keyMerge(d1, create, drop), true);
    }

    private final DiffResult appendIndexes(DiffResult result, Table<?> t1, List<? extends Index> l1, List<? extends Index> l2) {
        Create<Index> create = (r, i) -> r.queries.add((i.getUnique() ? this.ctx.createUniqueIndex((Index)i) : this.ctx.createIndex((Index)i)).on(t1, i.getFields()));
        Drop<Index> drop = (r, i) -> r.queries.add(this.ctx.dropIndex((Index)i).on(t1));
        return this.append(result, l1, l2, Comparators.INDEX_COMP, create, drop, (r, ix1, ix2) -> {
            if (Comparators.INDEX_COMP.compare((Index)ix1, (Index)ix2) != 0) {
                drop.drop(r, (Index)ix1);
                create.create(r, (Index)ix2);
            } else if (Comparators.NAMED_COMP.compare(ix1, ix2) != 0) {
                r.queries.add(this.ctx.alterTable(t1).renameIndex((Index)ix1).to((Index)ix2));
            }
        }, true);
    }

    private final <N extends Named> DiffResult append(DiffResult result, List<? extends N> l1, List<? extends N> l2, Comparator<? super N> comp, Create<N> create, Drop<N> drop, Merge<N> merge) {
        return this.append(result, l1, l2, comp, create, drop, merge, false);
    }

    private final <N extends Named> DiffResult append(DiffResult result, List<? extends N> l1, List<? extends N> l2, Comparator<? super N> comp, Create<N> create, Drop<N> drop, Merge<N> merge, boolean dropMergeCreate) {
        DiffResult created;
        if (comp == null) {
            comp = Comparators.NAMED_COMP;
        }
        Named s1 = null;
        Named s2 = null;
        Iterator<N> i1 = Diff.sorted(l1, comp);
        Iterator<N> i2 = Diff.sorted(l2, comp);
        DiffResult dropped = dropMergeCreate ? new DiffResult(new ArrayList<Query>(), result.droppedFks) : result;
        DiffResult merged = dropMergeCreate ? new DiffResult(new ArrayList<Query>(), result.droppedFks) : result;
        DiffResult diffResult = created = dropMergeCreate ? new DiffResult(new ArrayList<Query>(), result.droppedFks) : result;
        while (true) {
            int c;
            if (s1 == null && i1.hasNext()) {
                s1 = (Named)i1.next();
            }
            if (s2 == null && i2.hasNext()) {
                s2 = (Named)i2.next();
            }
            if (s1 == null && s2 == null) break;
            int n = s1 == null ? 1 : (c = s2 == null ? -1 : comp.compare(s1, s2));
            if (c < 0) {
                if (drop != null) {
                    drop.drop(dropped, s1);
                }
                s1 = null;
                continue;
            }
            if (c > 0) {
                if (create != null) {
                    create.create(created, s2);
                }
                s2 = null;
                continue;
            }
            if (merge != null) {
                merge.merge(merged, s1, s2);
            }
            s2 = null;
            s1 = null;
        }
        if (dropMergeCreate) {
            result.addAll(dropped);
            result.addAll(merged);
            result.addAll(created);
        }
        return result;
    }

    private static final <N extends Named> Iterator<N> sorted(List<N> list, Comparator<? super N> comp) {
        ArrayList<N> result = new ArrayList<N>(list);
        result.sort(comp);
        return result.iterator();
    }

    private static interface Merge<N extends Named> {
        public void merge(DiffResult var1, N var2, N var3);
    }

    private record DiffResult(List<Query> queries, Set<ForeignKey<?, ?>> droppedFks) {
        DiffResult() {
            this(new ArrayList<Query>(), new HashSet());
        }

        void addAll(DiffResult other) {
            this.queries.addAll(other.queries);
            this.droppedFks.addAll(other.droppedFks);
        }

        @Override
        public String toString() {
            return this.queries.toString();
        }
    }

    private static interface Create<N extends Named> {
        public void create(DiffResult var1, N var2);
    }

    private static interface Drop<N extends Named> {
        public void drop(DiffResult var1, N var2);
    }
}

