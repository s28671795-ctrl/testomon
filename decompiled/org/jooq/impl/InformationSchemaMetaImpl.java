/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.lang.invoke.CallSite;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.impl.AbstractMeta;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.CheckImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.DomainImpl;
import org.jooq.impl.IndexImpl;
import org.jooq.impl.Internal;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.SequenceImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.UniqueKeyImpl;
import org.jooq.tools.StringUtils;
import org.jooq.util.xml.jaxb.CheckConstraint;
import org.jooq.util.xml.jaxb.Column;
import org.jooq.util.xml.jaxb.DomainConstraint;
import org.jooq.util.xml.jaxb.IndexColumnUsage;
import org.jooq.util.xml.jaxb.InformationSchema;
import org.jooq.util.xml.jaxb.KeyColumnUsage;
import org.jooq.util.xml.jaxb.ReferentialConstraint;
import org.jooq.util.xml.jaxb.TableConstraint;
import org.jooq.util.xml.jaxb.TableConstraintType;
import org.jooq.util.xml.jaxb.TableType;
import org.jooq.util.xml.jaxb.View;

final class InformationSchemaMetaImpl
extends AbstractMeta {
    private final List<Catalog> catalogs = new ArrayList<Catalog>();
    private final Map<Name, Catalog> catalogsByName = new HashMap<Name, Catalog>();
    private final List<Schema> schemas = new ArrayList<Schema>();
    private final Map<Name, Schema> schemasByName = new HashMap<Name, Schema>();
    private final Map<Catalog, List<Schema>> schemasPerCatalog = new HashMap<Catalog, List<Schema>>();
    private final List<InformationSchemaTable> tables = new ArrayList<InformationSchemaTable>();
    private final Map<Name, InformationSchemaTable> tablesByName = new HashMap<Name, InformationSchemaTable>();
    private final Map<Schema, List<InformationSchemaTable>> tablesPerSchema = new HashMap<Schema, List<InformationSchemaTable>>();
    private final List<InformationSchemaDomain<?>> domains = new ArrayList();
    private final Map<Name, InformationSchemaDomain<?>> domainsByName = new HashMap();
    private final Map<Schema, List<InformationSchemaDomain<?>>> domainsPerSchema = new HashMap();
    private final List<Sequence<?>> sequences = new ArrayList();
    private final Map<Schema, List<Sequence<?>>> sequencesPerSchema = new HashMap();
    private final List<UniqueKeyImpl<Record>> primaryKeys = new ArrayList<UniqueKeyImpl<Record>>();
    private final Map<Name, UniqueKeyImpl<Record>> keysByName = new HashMap<Name, UniqueKeyImpl<Record>>();
    private final Map<Name, Name> referentialKeys = new HashMap<Name, Name>();
    private final Map<Name, IndexImpl> indexesByName = new HashMap<Name, IndexImpl>();

    InformationSchemaMetaImpl(Configuration configuration, InformationSchema source2) {
        super(configuration);
        this.init(source2);
    }

    private final void init(InformationSchema meta) {
        Object c;
        InformationSchemaTable table;
        Schema schema;
        Name schemaName;
        InformationSchemaCatalog ic;
        ArrayList<CallSite> errors2 = new ArrayList<CallSite>();
        boolean hasCatalogs = false;
        for (org.jooq.util.xml.jaxb.Catalog catalog : meta.getCatalogs()) {
            ic = new InformationSchemaCatalog(catalog.getCatalogName(), catalog.getComment());
            this.catalogs.add(ic);
            this.catalogsByName.put(DSL.name(catalog.getCatalogName()), ic);
            hasCatalogs = true;
        }
        for (org.jooq.util.xml.jaxb.Schema schema2 : meta.getSchemata()) {
            Name catalogName;
            Catalog catalog;
            if (!hasCatalogs && !this.catalogs.contains(ic = new InformationSchemaCatalog(schema2.getCatalogName(), null))) {
                this.catalogs.add(ic);
                this.catalogsByName.put(DSL.name(schema2.getCatalogName()), ic);
            }
            if ((catalog = this.catalogsByName.get(catalogName = DSL.name(schema2.getCatalogName()))) == null) {
                errors2.add((CallSite)((Object)("Catalog " + String.valueOf(catalogName) + " not defined for schema " + schema2.getSchemaName())));
                continue;
            }
            InformationSchemaSchema is = new InformationSchemaSchema(schema2.getSchemaName(), catalog, schema2.getComment());
            this.schemas.add(is);
            this.schemasByName.put(DSL.name(schema2.getCatalogName(), schema2.getSchemaName()), is);
        }
        for (org.jooq.util.xml.jaxb.Domain domain : meta.getDomains()) {
            schemaName = DSL.name(domain.getDomainCatalog(), domain.getDomainSchema());
            schema = this.schemasByName.get(schemaName);
            if (schema == null) {
                errors2.add((CallSite)((Object)("Schema " + String.valueOf(schemaName) + " not defined for domain " + domain.getDomainName())));
                continue;
            }
            Name domainName = DSL.name(domain.getDomainCatalog(), domain.getDomainSchema(), domain.getDomainName());
            int length = domain.getCharacterMaximumLength() == null ? 0 : domain.getCharacterMaximumLength();
            int n = domain.getNumericPrecision() == null ? 0 : domain.getNumericPrecision();
            int scale = domain.getNumericScale() == null ? 0 : domain.getNumericScale();
            boolean nullable = true;
            ArrayList checks = new ArrayList();
            for (DomainConstraint dc : meta.getDomainConstraints()) {
                if (!domainName.equals(DSL.name(dc.getDomainCatalog(), dc.getDomainSchema(), dc.getDomainName()))) continue;
                Name constraintName = DSL.name(dc.getConstraintCatalog(), dc.getConstraintSchema(), dc.getConstraintName());
                for (CheckConstraint cc : meta.getCheckConstraints()) {
                    if (!constraintName.equals(DSL.name(cc.getConstraintCatalog(), cc.getConstraintSchema(), cc.getConstraintName()))) continue;
                    checks.add(new CheckImpl(null, constraintName, DSL.condition(cc.getCheckClause()), true));
                }
            }
            InformationSchemaDomain id = new InformationSchemaDomain(schema, DSL.name(domain.getDomainName()), this.type(domain.getDataType(), length, n, scale, nullable, false, null, null), checks.toArray(Tools.EMPTY_CHECK));
            this.domains.add(id);
            this.domainsByName.put(domainName, id);
        }
        for (org.jooq.util.xml.jaxb.Table table2 : meta.getTables()) {
            schemaName = DSL.name(table2.getTableCatalog(), table2.getTableSchema());
            schema = this.schemasByName.get(schemaName);
            if (schema == null) {
                errors2.add((CallSite)((Object)("Schema " + String.valueOf(schemaName) + " not defined for table " + table2.getTableName())));
                continue;
            }
            TableOptions.TableType tableType = switch (table2.getTableType()) {
                case TableType.GLOBAL_TEMPORARY -> TableOptions.TableType.TEMPORARY;
                case TableType.VIEW -> TableOptions.TableType.VIEW;
                default -> TableOptions.TableType.TABLE;
            };
            String sql = null;
            if (tableType == TableOptions.TableType.VIEW) {
                for (View vt : meta.getViews()) {
                    if (!StringUtils.equals(StringUtils.defaultIfNull(table2.getTableCatalog(), ""), StringUtils.defaultIfNull(vt.getTableCatalog(), "")) || !StringUtils.equals(StringUtils.defaultIfNull(table2.getTableSchema(), ""), StringUtils.defaultIfNull(vt.getTableSchema(), "")) || !StringUtils.equals(StringUtils.defaultIfNull(table2.getTableName(), ""), StringUtils.defaultIfNull(vt.getTableName(), ""))) continue;
                    sql = vt.getViewDefinition();
                    break;
                }
            }
            InformationSchemaTable informationSchemaTable = new InformationSchemaTable(table2.getTableName(), schema, table2.getComment(), tableType, sql);
            this.tables.add(informationSchemaTable);
            this.tablesByName.put(DSL.name(table2.getTableCatalog(), table2.getTableSchema(), table2.getTableName()), informationSchemaTable);
        }
        ArrayList<Column> columns = new ArrayList<Column>(meta.getColumns());
        columns.sort((o1, o2) -> {
            Integer p2;
            Integer p1 = o1.getOrdinalPosition();
            if (Objects.equals(p1, p2 = o2.getOrdinalPosition())) {
                return 0;
            }
            if (p1 == null) {
                return -1;
            }
            if (p2 == null) {
                return 1;
            }
            return p1.compareTo(p2);
        });
        for (Column xc : columns) {
            Field<Object> generatedAlwaysAs;
            Iterator<org.jooq.util.xml.jaxb.Index> typeName = xc.getDataType();
            int length = xc.getCharacterMaximumLength() == null ? 0 : xc.getCharacterMaximumLength();
            int precision = xc.getNumericPrecision() == null ? 0 : xc.getNumericPrecision();
            int n = xc.getNumericScale() == null ? 0 : xc.getNumericScale();
            boolean nullable = !Boolean.FALSE.equals(xc.isIsNullable());
            boolean readonly = Boolean.TRUE.equals(xc.isReadonly());
            Field<Object> field2 = generatedAlwaysAs = Boolean.TRUE.equals(xc.isIsGenerated()) ? DSL.field(xc.getGenerationExpression()) : null;
            QOM.GenerationOption generationOption = Boolean.TRUE.equals(xc.isIsGenerated()) ? ("STORED".equalsIgnoreCase(xc.getGenerationOption()) ? QOM.GenerationOption.STORED : ("VIRTUAL".equalsIgnoreCase(xc.getGenerationOption()) ? QOM.GenerationOption.VIRTUAL : null)) : null;
            Name tableName = DSL.name(xc.getTableCatalog(), xc.getTableSchema(), xc.getTableName());
            InformationSchemaTable table2 = this.tablesByName.get(tableName);
            if (table2 == null) {
                errors2.add((CallSite)((Object)("Table " + String.valueOf(tableName) + " not defined for column " + xc.getColumnName())));
                continue;
            }
            AbstractTable.createField(DSL.name(xc.getColumnName()), this.type((String)((Object)typeName), length, precision, n, nullable, readonly, generatedAlwaysAs, generationOption), table2, xc.getComment());
        }
        HashMap<Name, List> hashMap = new HashMap<Name, List>();
        ArrayList<IndexColumnUsage> indexColumnUsages = new ArrayList<IndexColumnUsage>(meta.getIndexColumnUsages());
        indexColumnUsages.sort(Comparator.comparingInt(IndexColumnUsage::getOrdinalPosition));
        for (IndexColumnUsage ic2 : indexColumnUsages) {
            Name indexName = DSL.name(ic2.getIndexCatalog(), ic2.getIndexSchema(), ic2.getTableName(), ic2.getIndexName());
            List list = hashMap.computeIfAbsent(indexName, k -> new ArrayList());
            Name tableName = DSL.name(ic2.getTableCatalog(), ic2.getTableSchema(), ic2.getTableName());
            InformationSchemaTable table3 = this.tablesByName.get(tableName);
            if (table3 == null) {
                errors2.add((CallSite)((Object)("Table " + String.valueOf(tableName) + " not defined for index " + String.valueOf(indexName))));
                continue;
            }
            TableField field3 = (TableField)table3.field(ic2.getColumnName());
            if (field3 == null) {
                errors2.add((CallSite)((Object)("Column " + ic2.getColumnName() + " not defined for table " + String.valueOf(tableName))));
                continue;
            }
            list.add(Boolean.TRUE.equals(ic2.isIsDescending()) ? field3.desc() : field3.asc());
        }
        for (org.jooq.util.xml.jaxb.Index i : meta.getIndexes()) {
            Name tableName = DSL.name(i.getTableCatalog(), i.getTableSchema(), i.getTableName());
            Name name = DSL.name(i.getIndexCatalog(), i.getIndexSchema(), i.getTableName(), i.getIndexName());
            InformationSchemaTable table4 = this.tablesByName.get(tableName);
            if (table4 == null) {
                errors2.add((CallSite)((Object)("Table " + String.valueOf(tableName) + " not defined for index " + String.valueOf(name))));
                continue;
            }
            List c2 = (List)hashMap.get(name);
            if (c2 == null || c2.isEmpty()) {
                errors2.add((CallSite)((Object)("No columns defined for index " + String.valueOf(name))));
                continue;
            }
            IndexImpl index = (IndexImpl)Internal.createIndex(i.getIndexName(), table4, (OrderField[])c2.toArray(Tools.EMPTY_SORTFIELD), Boolean.TRUE.equals(i.isIsUnique()));
            table4.indexes.add(index);
            this.indexesByName.put(name, index);
        }
        HashMap<Name, List> columnsByConstraint = new HashMap<Name, List>();
        ArrayList<KeyColumnUsage> keyColumnUsages = new ArrayList<KeyColumnUsage>(meta.getKeyColumnUsages());
        keyColumnUsages.sort(Comparator.comparing(KeyColumnUsage::getOrdinalPosition));
        for (KeyColumnUsage keyColumnUsage : keyColumnUsages) {
            Name constraintName = DSL.name(keyColumnUsage.getConstraintCatalog(), keyColumnUsage.getConstraintSchema(), keyColumnUsage.getConstraintName());
            List fields = columnsByConstraint.computeIfAbsent(constraintName, k -> new ArrayList());
            Name tableName = DSL.name(keyColumnUsage.getTableCatalog(), keyColumnUsage.getTableSchema(), keyColumnUsage.getTableName());
            InformationSchemaTable table5 = this.tablesByName.get(tableName);
            if (table5 == null) {
                errors2.add((CallSite)((Object)("Table " + String.valueOf(tableName) + " not defined for constraint " + String.valueOf(constraintName))));
                continue;
            }
            TableField field4 = (TableField)table5.field(keyColumnUsage.getColumnName());
            if (field4 == null) {
                errors2.add((CallSite)((Object)("Column " + keyColumnUsage.getColumnName() + " not defined for table " + String.valueOf(tableName))));
                continue;
            }
            fields.add(field4);
        }
        for (TableConstraint tableConstraint : meta.getTableConstraints()) {
            switch (tableConstraint.getConstraintType()) {
                case PRIMARY_KEY: 
                case UNIQUE: {
                    Name tableName = DSL.name(tableConstraint.getTableCatalog(), tableConstraint.getTableSchema(), tableConstraint.getTableName());
                    Name constraintName = DSL.name(tableConstraint.getConstraintCatalog(), tableConstraint.getConstraintSchema(), tableConstraint.getConstraintName());
                    table = this.tablesByName.get(tableName);
                    if (table == null) {
                        errors2.add((CallSite)((Object)("Table " + String.valueOf(tableName) + " not defined for constraint " + String.valueOf(constraintName))));
                        break;
                    }
                    c = (List)columnsByConstraint.get(constraintName);
                    if (c == null || c.isEmpty()) {
                        errors2.add((CallSite)((Object)("No columns defined for constraint " + String.valueOf(constraintName))));
                        break;
                    }
                    UniqueKeyImpl key = (UniqueKeyImpl)Internal.createUniqueKey(table, tableConstraint.getConstraintName(), c.toArray(new TableField[0]));
                    if (tableConstraint.getConstraintType() == TableConstraintType.PRIMARY_KEY) {
                        table.primaryKey = key;
                        this.primaryKeys.add(key);
                    } else {
                        table.uniqueKeys.add(key);
                    }
                    this.keysByName.put(constraintName, key);
                    break;
                }
            }
        }
        for (ReferentialConstraint referentialConstraint : meta.getReferentialConstraints()) {
            this.referentialKeys.put(DSL.name(referentialConstraint.getConstraintCatalog(), referentialConstraint.getConstraintSchema(), referentialConstraint.getConstraintName()), DSL.name(referentialConstraint.getUniqueConstraintCatalog(), referentialConstraint.getUniqueConstraintSchema(), referentialConstraint.getUniqueConstraintName()));
        }
        for (TableConstraint tableConstraint : meta.getTableConstraints()) {
            switch (tableConstraint.getConstraintType()) {
                case FOREIGN_KEY: {
                    Name tableName = DSL.name(tableConstraint.getTableCatalog(), tableConstraint.getTableSchema(), tableConstraint.getTableName());
                    Name constraintName = DSL.name(tableConstraint.getConstraintCatalog(), tableConstraint.getConstraintSchema(), tableConstraint.getConstraintName());
                    table = this.tablesByName.get(tableName);
                    if (table == null) {
                        errors2.add((CallSite)((Object)("Table " + String.valueOf(tableName) + " not defined for constraint " + String.valueOf(constraintName))));
                        break;
                    }
                    c = (List)columnsByConstraint.get(constraintName);
                    if (c == null || c.isEmpty()) {
                        errors2.add((CallSite)((Object)("No columns defined for constraint " + String.valueOf(constraintName))));
                        break;
                    }
                    UniqueKeyImpl<Record> uniqueKey = this.keysByName.get(this.referentialKeys.get(constraintName));
                    if (uniqueKey == null) {
                        errors2.add((CallSite)((Object)("No unique key defined for foreign key " + String.valueOf(constraintName))));
                        break;
                    }
                    ForeignKey<Record, Record> key = Internal.createForeignKey(uniqueKey, table, tableConstraint.getConstraintName(), c.toArray(new TableField[0]));
                    table.foreignKeys.add(key);
                    break;
                }
            }
        }
        block27: for (TableConstraint tableConstraint : meta.getTableConstraints()) {
            switch (tableConstraint.getConstraintType()) {
                case CHECK: {
                    Name tableName = DSL.name(tableConstraint.getTableCatalog(), tableConstraint.getTableSchema(), tableConstraint.getTableName());
                    Name constraintName = DSL.name(tableConstraint.getConstraintCatalog(), tableConstraint.getConstraintSchema(), tableConstraint.getConstraintName());
                    table = this.tablesByName.get(tableName);
                    if (table == null) {
                        errors2.add((CallSite)((Object)("Table " + String.valueOf(tableName) + " not defined for constraint " + String.valueOf(constraintName))));
                        continue block27;
                    }
                    for (CheckConstraint cc : meta.getCheckConstraints()) {
                        if (!constraintName.equals(DSL.name(cc.getConstraintCatalog(), cc.getConstraintSchema(), cc.getConstraintName()))) continue;
                        table.checks.add(new CheckImpl<Record>(table, constraintName, DSL.condition(cc.getCheckClause()), true));
                        continue block27;
                    }
                    errors2.add((CallSite)((Object)("No check clause found for check constraint " + String.valueOf(constraintName))));
                    continue block27;
                }
            }
        }
        for (org.jooq.util.xml.jaxb.Sequence sequence2 : meta.getSequences()) {
            Name schemaName2 = DSL.name(sequence2.getSequenceCatalog(), sequence2.getSequenceSchema());
            Schema schema2 = this.schemasByName.get(schemaName2);
            if (schema2 == null) {
                errors2.add((CallSite)((Object)("Schema " + String.valueOf(schemaName2) + " not defined for sequence " + sequence2.getSequenceName())));
                continue;
            }
            String typeName = sequence2.getDataType();
            int length = sequence2.getCharacterMaximumLength() == null ? 0 : sequence2.getCharacterMaximumLength();
            int precision = sequence2.getNumericPrecision() == null ? 0 : sequence2.getNumericPrecision();
            int scale = sequence2.getNumericScale() == null ? 0 : sequence2.getNumericScale();
            boolean nullable = true;
            BigInteger startWith = sequence2.getStartValue();
            BigInteger incrementBy = sequence2.getIncrement();
            BigInteger minvalue = sequence2.getMinimumValue();
            BigInteger maxvalue = sequence2.getMaximumValue();
            Boolean cycle = sequence2.isCycleOption();
            BigInteger cache = sequence2.getCache();
            InformationSchemaSequence is = new InformationSchemaSequence(sequence2.getSequenceName(), schema2, this.type(typeName, length, precision, scale, nullable, false, null, null), startWith, incrementBy, minvalue, maxvalue, cycle, cache);
            this.sequences.add(is);
        }
        for (Schema schema3 : this.schemas) {
            this.initLookup(this.schemasPerCatalog, schema3.getCatalog(), schema3);
        }
        for (InformationSchemaDomain informationSchemaDomain : this.domains) {
            this.initLookup(this.domainsPerSchema, informationSchemaDomain.getSchema(), informationSchemaDomain);
        }
        for (InformationSchemaTable informationSchemaTable : this.tables) {
            this.initLookup(this.tablesPerSchema, informationSchemaTable.getSchema(), informationSchemaTable);
        }
        for (Sequence sequence3 : this.sequences) {
            this.initLookup(this.sequencesPerSchema, sequence3.getSchema(), sequence3);
        }
        if (!errors2.isEmpty()) {
            throw new IllegalArgumentException(((Object)errors2).toString());
        }
    }

    private final <K, V> void initLookup(Map<K, List<V>> lookup, K key, V value) {
        lookup.computeIfAbsent(key, k -> new ArrayList()).add(value);
    }

    private final DataType<?> type(String typeName, int length, int precision, int scale, boolean nullable, boolean readonly, Field<?> generatedAlwaysAs, QOM.GenerationOption generationOption) {
        DataType<Object> type = null;
        try {
            type = DefaultDataType.getDataType(this.configuration.family(), typeName);
            type = type.nullable(nullable);
            type = type.readonly(readonly);
            if (length != 0) {
                type = type.length(length);
            } else if (precision != 0 || scale != 0) {
                type = type.precision(precision, scale);
            }
            if (generatedAlwaysAs != null) {
                type = type.generatedAlwaysAs(generatedAlwaysAs);
            }
            if (generationOption != null) {
                type = type.generationOption(generationOption);
            }
        }
        catch (SQLDialectNotSupportedException e) {
            type = SQLDataType.OTHER;
        }
        return type;
    }

    @Override
    final List<Catalog> getCatalogs0() {
        return this.catalogs;
    }

    @Override
    final List<Schema> getSchemas0() {
        return this.schemas;
    }

    @Override
    final List<Table<?>> getTables0() {
        return this.tables;
    }

    @Override
    final List<Domain<?>> getDomains0() {
        return this.domains;
    }

    @Override
    final List<UniqueKey<?>> getPrimaryKeys0() {
        return this.primaryKeys;
    }

    private static final <T> List<T> unmodifiableList(List<? extends T> list) {
        return list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
    }

    private final class InformationSchemaCatalog
    extends CatalogImpl {
        InformationSchemaCatalog(String name, String comment) {
            super(name, comment);
        }

        @Override
        public final List<Schema> getSchemas() {
            return InformationSchemaMetaImpl.unmodifiableList(InformationSchemaMetaImpl.this.schemasPerCatalog.get(this));
        }
    }

    private final class InformationSchemaSchema
    extends SchemaImpl {
        InformationSchemaSchema(String name, Catalog catalog, String comment) {
            super(name, catalog, comment);
        }

        @Override
        public final List<Domain<?>> getDomains() {
            return InformationSchemaMetaImpl.unmodifiableList(InformationSchemaMetaImpl.this.domainsPerSchema.get(this));
        }

        @Override
        public final List<Table<?>> getTables() {
            return InformationSchemaMetaImpl.unmodifiableList(InformationSchemaMetaImpl.this.tablesPerSchema.get(this));
        }

        @Override
        public final List<Sequence<?>> getSequences() {
            return InformationSchemaMetaImpl.unmodifiableList(InformationSchemaMetaImpl.this.sequencesPerSchema.get(this));
        }
    }

    private static final class InformationSchemaDomain<T>
    extends DomainImpl<T> {
        InformationSchemaDomain(Schema schema, Name name, DataType<T> type, Check<?>[] checks) {
            super(schema, name, type, checks);
        }
    }

    private static final class InformationSchemaTable
    extends TableImpl<Record> {
        UniqueKey<Record> primaryKey;
        final List<UniqueKey<Record>> uniqueKeys = new ArrayList<UniqueKey<Record>>();
        final List<ForeignKey<Record, Record>> foreignKeys = new ArrayList<ForeignKey<Record, Record>>();
        final List<Check<Record>> checks = new ArrayList<Check<Record>>();
        final List<Index> indexes = new ArrayList<Index>();

        InformationSchemaTable(String name, Schema schema, String comment, TableOptions.TableType tableType, String source2) {
            super(DSL.name(name), schema, null, null, DSL.comment(comment), tableType == TableOptions.TableType.VIEW ? TableOptions.view(source2) : TableOptions.of(tableType));
        }

        @Override
        public List<Index> getIndexes() {
            return InformationSchemaMetaImpl.unmodifiableList(this.indexes);
        }

        @Override
        public UniqueKey<Record> getPrimaryKey() {
            return this.primaryKey;
        }

        @Override
        public List<UniqueKey<Record>> getUniqueKeys() {
            return InformationSchemaMetaImpl.unmodifiableList(this.uniqueKeys);
        }

        @Override
        public List<ForeignKey<Record, ?>> getReferences() {
            return InformationSchemaMetaImpl.unmodifiableList(this.foreignKeys);
        }

        @Override
        public List<Check<Record>> getChecks() {
            return InformationSchemaMetaImpl.unmodifiableList(this.checks);
        }
    }

    private static final class InformationSchemaSequence<N extends Number>
    extends SequenceImpl<N> {
        InformationSchemaSequence(String name, Schema schema, DataType<N> type, Number startWith, Number incrementBy, Number minvalue, Number maxvalue, Boolean cycle, Number cache) {
            super(DSL.name(name), schema, type, false, startWith != null ? Tools.field((Object)startWith, type) : null, incrementBy != null ? Tools.field((Object)incrementBy, type) : null, minvalue != null ? Tools.field((Object)minvalue, type) : null, maxvalue != null ? Tools.field((Object)maxvalue, type) : null, Boolean.TRUE.equals(cycle), cache != null ? Tools.field((Object)cache, type) : null);
        }
    }
}

