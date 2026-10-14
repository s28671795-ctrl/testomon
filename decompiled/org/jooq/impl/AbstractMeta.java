/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.DDLExportConfiguration;
import org.jooq.Domain;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Meta;
import org.jooq.MigrationConfiguration;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.DDL;
import org.jooq.impl.DSL;
import org.jooq.impl.Diff;
import org.jooq.impl.FilteredMeta;
import org.jooq.impl.InformationSchemaExport;
import org.jooq.impl.Internal;
import org.jooq.impl.Snapshot;
import org.jooq.impl.Tools;
import org.jooq.util.xml.jaxb.InformationSchema;

abstract class AbstractMeta
extends AbstractScope
implements Meta,
Serializable {
    private Map<Name, Catalog> cachedCatalogs;
    private Map<Name, Schema> cachedQualifiedSchemas;
    private Map<Name, Table<?>> cachedQualifiedTables;
    private Map<Name, Domain<?>> cachedQualifiedDomains;
    private Map<Name, Sequence<?>> cachedQualifiedSequences;
    private Map<Name, UniqueKey<?>> cachedQualifiedPrimaryKeys;
    private Map<Name, UniqueKey<?>> cachedQualifiedUniqueKeys;
    private Map<Name, ForeignKey<?, ?>> cachedQualifiedForeignKeys;
    private Map<Name, Index> cachedQualifiedIndexes;
    private Map<Name, List<Schema>> cachedUnqualifiedSchemas;
    private Map<Name, List<Table<?>>> cachedUnqualifiedTables;
    private Map<Name, List<Domain<?>>> cachedUnqualifiedDomains;
    private Map<Name, List<Sequence<?>>> cachedUnqualifiedSequences;
    private Map<Name, List<UniqueKey<?>>> cachedUnqualifiedPrimaryKeys;
    private Map<Name, List<UniqueKey<?>>> cachedUnqualifiedUniqueKeys;
    private Map<Name, List<ForeignKey<?, ?>>> cachedUnqualifiedForeignKeys;
    private Map<Name, List<Index>> cachedUnqualifiedIndexes;

    AbstractMeta(Configuration configuration) {
        super(configuration);
    }

    @Override
    public final Catalog getCatalog(String name) {
        return this.getCatalog(DSL.name(name));
    }

    @Override
    public final Catalog getCatalog(Name name) {
        this.initCatalogs();
        return this.cachedCatalogs.get(name);
    }

    @Override
    public final List<Catalog> getCatalogs() {
        this.initCatalogs();
        return Collections.unmodifiableList(new ArrayList<Catalog>(this.cachedCatalogs.values()));
    }

    private final void initCatalogs() {
        if (this.cachedCatalogs == null) {
            this.cachedCatalogs = new LinkedHashMap<Name, Catalog>();
            for (Catalog catalog : this.getCatalogs0()) {
                this.cachedCatalogs.put(catalog.getQualifiedName(), catalog);
            }
        }
    }

    abstract List<Catalog> getCatalogs0();

    @Override
    public final List<Schema> getSchemas(String name) {
        return this.getSchemas(DSL.name(name));
    }

    @Override
    public final List<Schema> getSchemas(Name name) {
        this.initSchemas();
        return this.get(name, () -> this.getSchemas0().iterator(), this.cachedQualifiedSchemas, this.cachedUnqualifiedSchemas);
    }

    @Override
    public final List<Schema> getSchemas() {
        this.initSchemas();
        return Collections.unmodifiableList(new ArrayList<Schema>(this.cachedQualifiedSchemas.values()));
    }

    private final void initSchemas() {
        if (this.cachedQualifiedSchemas == null) {
            this.cachedQualifiedSchemas = new LinkedHashMap<Name, Schema>();
            this.cachedUnqualifiedSchemas = new LinkedHashMap<Name, List<Schema>>();
            this.get(DSL.name(""), () -> this.getSchemas0().iterator(), this.cachedQualifiedSchemas, this.cachedUnqualifiedSchemas);
        }
    }

    List<Schema> getSchemas0() {
        return Tools.flatMap(this.getCatalogs(), c -> c.getSchemas());
    }

    @Override
    public final List<Table<?>> getTables(String name) {
        return this.getTables(DSL.name(name));
    }

    @Override
    public final List<Table<?>> getTables(Name name) {
        this.initTables();
        return this.get(name, () -> this.getTables().iterator(), this.cachedQualifiedTables, this.cachedUnqualifiedTables);
    }

    @Override
    public final List<Table<?>> getTables() {
        this.initTables();
        return Collections.unmodifiableList(new ArrayList(this.cachedQualifiedTables.values()));
    }

    private final void initTables() {
        if (this.cachedQualifiedTables == null) {
            this.cachedQualifiedTables = new LinkedHashMap();
            this.cachedUnqualifiedTables = new LinkedHashMap();
            this.get(DSL.name(""), () -> this.getTables0().iterator(), this.cachedQualifiedTables, this.cachedUnqualifiedTables);
        }
    }

    List<Table<?>> getTables0() {
        return Tools.flatMap(this.getSchemas(), s2 -> s2.getTables());
    }

    @Override
    public final List<Domain<?>> getDomains(String name) {
        return this.getDomains(DSL.name(name));
    }

    @Override
    public final List<Domain<?>> getDomains(Name name) {
        this.initDomains();
        return this.get(name, () -> this.getDomains().iterator(), this.cachedQualifiedDomains, this.cachedUnqualifiedDomains);
    }

    @Override
    public final List<Domain<?>> getDomains() {
        this.initDomains();
        return Collections.unmodifiableList(new ArrayList(this.cachedQualifiedDomains.values()));
    }

    private final void initDomains() {
        if (this.cachedQualifiedDomains == null) {
            this.cachedQualifiedDomains = new LinkedHashMap();
            this.cachedUnqualifiedDomains = new LinkedHashMap();
            this.get(DSL.name(""), () -> this.getDomains0().iterator(), this.cachedQualifiedDomains, this.cachedUnqualifiedDomains);
        }
    }

    List<Domain<?>> getDomains0() {
        return Tools.flatMap(this.getSchemas(), s2 -> s2.getDomains());
    }

    @Override
    public final List<Sequence<?>> getSequences(String name) {
        return this.getSequences(DSL.name(name));
    }

    @Override
    public final List<Sequence<?>> getSequences(Name name) {
        this.initSequences();
        return this.get(name, () -> this.getSequences().iterator(), this.cachedQualifiedSequences, this.cachedUnqualifiedSequences);
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        this.initSequences();
        return Collections.unmodifiableList(new ArrayList(this.cachedQualifiedSequences.values()));
    }

    private final void initSequences() {
        if (this.cachedQualifiedSequences == null) {
            this.cachedQualifiedSequences = new LinkedHashMap();
            this.cachedUnqualifiedSequences = new LinkedHashMap();
            this.get(DSL.name(""), () -> this.getSequences0().iterator(), this.cachedQualifiedSequences, this.cachedUnqualifiedSequences);
        }
    }

    final List<Sequence<?>> getSequences0() {
        return Tools.flatMap(this.getSchemas(), s2 -> s2.getSequences());
    }

    @Override
    public final List<UniqueKey<?>> getPrimaryKeys(String name) {
        return this.getPrimaryKeys(DSL.name(name));
    }

    @Override
    public final List<UniqueKey<?>> getPrimaryKeys(Name name) {
        this.initPrimaryKeys();
        return this.get(name, () -> this.getPrimaryKeys().iterator(), this.cachedQualifiedPrimaryKeys, this.cachedUnqualifiedPrimaryKeys);
    }

    @Override
    public final List<UniqueKey<?>> getPrimaryKeys() {
        this.initPrimaryKeys();
        return Collections.unmodifiableList(new ArrayList(this.cachedQualifiedPrimaryKeys.values()));
    }

    private final void initPrimaryKeys() {
        if (this.cachedQualifiedPrimaryKeys == null) {
            this.cachedQualifiedPrimaryKeys = new LinkedHashMap();
            this.cachedUnqualifiedPrimaryKeys = new LinkedHashMap();
            this.get(DSL.name(""), () -> this.getPrimaryKeys0().iterator(), this.cachedQualifiedPrimaryKeys, this.cachedUnqualifiedPrimaryKeys);
        }
    }

    List<UniqueKey<?>> getPrimaryKeys0() {
        ArrayList result = new ArrayList();
        for (Table<?> table : this.getTables()) {
            if (table.getPrimaryKey() == null) continue;
            result.add(table.getPrimaryKey());
        }
        return result;
    }

    @Override
    public final List<UniqueKey<?>> getUniqueKeys(String name) {
        return this.getUniqueKeys(DSL.name(name));
    }

    @Override
    public final List<UniqueKey<?>> getUniqueKeys(Name name) {
        this.initUniqueKeys();
        return this.get(name, () -> this.getUniqueKeys().iterator(), this.cachedQualifiedUniqueKeys, this.cachedUnqualifiedUniqueKeys);
    }

    @Override
    public final List<UniqueKey<?>> getUniqueKeys() {
        this.initUniqueKeys();
        return Collections.unmodifiableList(new ArrayList(this.cachedQualifiedUniqueKeys.values()));
    }

    private final void initUniqueKeys() {
        if (this.cachedQualifiedUniqueKeys == null) {
            this.cachedQualifiedUniqueKeys = new LinkedHashMap();
            this.cachedUnqualifiedUniqueKeys = new LinkedHashMap();
            this.get(DSL.name(""), () -> this.getUniqueKeys0().iterator(), this.cachedQualifiedUniqueKeys, this.cachedUnqualifiedUniqueKeys);
        }
    }

    List<UniqueKey<?>> getUniqueKeys0() {
        return Tools.flatMap(this.getTables(), t2 -> t2.getUniqueKeys());
    }

    @Override
    public final List<ForeignKey<?, ?>> getForeignKeys(String name) {
        return this.getForeignKeys(DSL.name(name));
    }

    @Override
    public final List<ForeignKey<?, ?>> getForeignKeys(Name name) {
        this.initForeignKeys();
        return this.get(name, () -> this.getForeignKeys().iterator(), this.cachedQualifiedForeignKeys, this.cachedUnqualifiedForeignKeys);
    }

    @Override
    public final List<ForeignKey<?, ?>> getForeignKeys() {
        this.initForeignKeys();
        return Collections.unmodifiableList(new ArrayList(this.cachedQualifiedForeignKeys.values()));
    }

    private final void initForeignKeys() {
        if (this.cachedQualifiedForeignKeys == null) {
            this.cachedQualifiedForeignKeys = new LinkedHashMap();
            this.cachedUnqualifiedForeignKeys = new LinkedHashMap();
            this.get(DSL.name(""), () -> this.getForeignKeys0().iterator(), this.cachedQualifiedForeignKeys, this.cachedUnqualifiedForeignKeys);
        }
    }

    List<ForeignKey<?, ?>> getForeignKeys0() {
        return Tools.flatMap(this.getTables(), t2 -> t2.getReferences());
    }

    @Override
    public final List<Index> getIndexes(String name) {
        return this.getIndexes(DSL.name(name));
    }

    @Override
    public final List<Index> getIndexes(Name name) {
        this.initIndexes();
        return this.get(name, () -> this.getIndexes().iterator(), this.cachedQualifiedIndexes, this.cachedUnqualifiedIndexes);
    }

    @Override
    public final List<Index> getIndexes() {
        this.initIndexes();
        return Collections.unmodifiableList(new ArrayList<Index>(this.cachedQualifiedIndexes.values()));
    }

    private final void initIndexes() {
        if (this.cachedQualifiedIndexes == null) {
            this.cachedQualifiedIndexes = new LinkedHashMap<Name, Index>();
            this.cachedUnqualifiedIndexes = new LinkedHashMap<Name, List<Index>>();
            this.get(DSL.name(""), () -> this.getIndexes0().iterator(), this.cachedQualifiedIndexes, this.cachedUnqualifiedIndexes);
        }
    }

    List<Index> getIndexes0() {
        return Tools.flatMap(this.getTables(), t2 -> t2.getIndexes());
    }

    private final <T extends Named> List<T> get(Name name, Iterable<T> i, Map<Name, T> qualified, Map<Name, List<T>> unqualified) {
        Named object;
        if (qualified.isEmpty()) {
            for (Named object2 : i) {
                Name q = object2.getQualifiedName();
                Name u = object2.getUnqualifiedName();
                qualified.put(q, object2);
                unqualified.computeIfAbsent(u, n -> new ArrayList()).add(object2);
            }
        }
        if ((object = (Named)qualified.get(name)) != null) {
            return Collections.singletonList(object);
        }
        List<T> list = unqualified.get(name);
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public Meta filterCatalogs(Predicate<? super Catalog> filter2) {
        return new FilteredMeta(this, filter2, null, null, null, null, null, null, null, null);
    }

    @Override
    public Meta filterSchemas(Predicate<? super Schema> filter2) {
        return new FilteredMeta(this, null, filter2, null, null, null, null, null, null, null);
    }

    @Override
    public Meta filterTables(Predicate<? super Table<?>> filter2) {
        return new FilteredMeta(this, null, null, filter2, null, null, null, null, null, null);
    }

    @Override
    public Meta filterDomains(Predicate<? super Domain<?>> filter2) {
        return new FilteredMeta(this, null, null, null, filter2, null, null, null, null, null);
    }

    @Override
    public Meta filterSequences(Predicate<? super Sequence<?>> filter2) {
        return new FilteredMeta(this, null, null, null, null, filter2, null, null, null, null);
    }

    @Override
    public Meta filterPrimaryKeys(Predicate<? super UniqueKey<?>> filter2) {
        return new FilteredMeta(this, null, null, null, null, null, filter2, null, null, null);
    }

    @Override
    public Meta filterUniqueKeys(Predicate<? super UniqueKey<?>> filter2) {
        return new FilteredMeta(this, null, null, null, null, null, null, filter2, null, null);
    }

    @Override
    public Meta filterForeignKeys(Predicate<? super ForeignKey<?, ?>> filter2) {
        return new FilteredMeta(this, null, null, null, null, null, null, null, filter2, null);
    }

    @Override
    public Meta filterIndexes(Predicate<? super Index> filter2) {
        return new FilteredMeta(this, null, null, null, null, null, null, null, null, filter2);
    }

    @Override
    public final Meta snapshot() {
        return new Snapshot(this);
    }

    @Override
    public final Queries ddl() {
        return this.ddl(new DDLExportConfiguration());
    }

    @Override
    public Queries ddl(DDLExportConfiguration exportConfiguration) {
        return new DDL(this.dsl(), exportConfiguration).queries(this);
    }

    @Override
    public final Meta apply(String migration2) {
        return this.apply(this.dsl().parser().parse(migration2));
    }

    @Override
    public final Meta apply(Query ... migration2) {
        return this.apply(this.dsl().queries(migration2));
    }

    @Override
    public final Meta apply(Collection<? extends Query> migration2) {
        return this.apply(this.dsl().queries(migration2));
    }

    @Override
    public final Meta apply(Queries migration2) {
        return this.dsl().meta(this.ddl().concat(migration2).queries());
    }

    @Override
    public final Queries migrateTo(Meta other) {
        return this.migrateTo(other, new MigrationConfiguration());
    }

    @Override
    public final Queries migrateTo(Meta other, MigrationConfiguration c) {
        return new Diff(this.configuration(), c, this, other).queries();
    }

    @Override
    public InformationSchema informationSchema() {
        return InformationSchemaExport.exportCatalogs(this.configuration(), this.getCatalogs());
    }

    final Table<?> lookupTable(Table<?> table) {
        Catalog c = table.getCatalog();
        Schema s2 = table.getSchema();
        Catalog catalog = this.getCatalog(c == null ? "" : c.getName());
        if (catalog == null) {
            return null;
        }
        Schema schema = catalog.getSchema(s2 == null ? "" : s2.getName());
        if (schema == null) {
            return null;
        }
        return schema.getTable(table.getName());
    }

    final <R extends Record> UniqueKey<R> lookupKey(Table<R> in, UniqueKey<?> uk) {
        HashSet ukFields = new HashSet(uk.getFields());
        return Tools.findAny(in.getKeys(), k -> ukFields.equals(new HashSet(k.getFields())));
    }

    final UniqueKey<?> lookupUniqueKey(ForeignKey<?, ?> fk) {
        Table<?> table = this.lookupTable(fk.getKey().getTable());
        if (table == null) {
            return null;
        }
        return this.lookupKey(table, fk.getKey());
    }

    static final <R extends Record> ForeignKey<R, ?> copyFK(Table<R> fkTable, UniqueKey<?> uk, ForeignKey<R, ?> oldFk) {
        Table ukTable = uk.getTable();
        return Internal.createForeignKey(fkTable, oldFk.getQualifiedName(), Tools.map(oldFk.getFieldsArray(), f -> (TableField)fkTable.field(f), TableField[]::new), uk, Tools.map(oldFk.getKeyFieldsArray(), f -> (TableField)ukTable.field(f), TableField[]::new), oldFk.enforced());
    }

    public int hashCode() {
        return this.ddl().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Meta) {
            Meta m4 = (Meta)obj;
            return this.ddl().equals(m4.ddl());
        }
        return false;
    }

    public String toString() {
        return this.ddl().toString();
    }
}

