/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jooq.Catalog;
import org.jooq.Check;
import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.Domain;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Key;
import org.jooq.Param;
import org.jooq.Qualified;
import org.jooq.Schema;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.Convert;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
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

final class InformationSchemaExport {
    static final InformationSchema exportTables(Configuration configuration, List<Table<?>> tables) {
        InformationSchema result = new InformationSchema();
        LinkedHashSet<Catalog> includedCatalogs = new LinkedHashSet<Catalog>();
        LinkedHashSet<Schema> includedSchemas = new LinkedHashSet<Schema>();
        LinkedHashSet includedTables = new LinkedHashSet(tables);
        for (Table<Object> t2 : tables) {
            if (t2.getSchema() == null) continue;
            includedSchemas.add(t2.getSchema());
        }
        for (Schema s2 : includedSchemas) {
            if (s2.getCatalog() == null) continue;
            includedCatalogs.add(s2.getCatalog());
        }
        for (Catalog c : includedCatalogs) {
            InformationSchemaExport.exportCatalog0(result, c);
        }
        for (Schema s2 : includedSchemas) {
            InformationSchemaExport.exportSchema0(result, s2);
        }
        for (Table<Object> t2 : tables) {
            InformationSchemaExport.exportTable0(configuration, result, t2, includedTables);
        }
        return result;
    }

    static final InformationSchema exportSchemas(Configuration configuration, List<Schema> schemas) {
        InformationSchema result = new InformationSchema();
        LinkedHashSet<Catalog> includedCatalogs = new LinkedHashSet<Catalog>();
        LinkedHashSet includedTables = new LinkedHashSet();
        for (Schema s2 : schemas) {
            if (s2.getCatalog() != null) {
                includedCatalogs.add(s2.getCatalog());
            }
            includedTables.addAll(s2.getTables());
        }
        for (Catalog c : includedCatalogs) {
            InformationSchemaExport.exportCatalog0(result, c);
        }
        for (Schema s2 : schemas) {
            InformationSchemaExport.exportSchema0(result, s2);
            for (Domain<?> domain : s2.getDomains()) {
                InformationSchemaExport.exportDomain0(configuration, result, domain);
            }
            for (Table table : s2.getTables()) {
                InformationSchemaExport.exportTable0(configuration, result, table, includedTables);
            }
            for (Sequence sequence2 : s2.getSequences()) {
                InformationSchemaExport.exportSequence0(configuration, result, sequence2);
            }
        }
        return result;
    }

    static final InformationSchema exportCatalogs(Configuration configuration, List<Catalog> catalogs) {
        InformationSchema result = new InformationSchema();
        LinkedHashSet includedTables = new LinkedHashSet();
        for (Catalog c : catalogs) {
            for (Schema s2 : c.getSchemas()) {
                includedTables.addAll(s2.getTables());
            }
        }
        for (Catalog c : catalogs) {
            InformationSchemaExport.exportCatalog0(result, c);
            for (Schema s2 : c.getSchemas()) {
                InformationSchemaExport.exportSchema0(result, s2);
                for (Domain<?> domain : s2.getDomains()) {
                    InformationSchemaExport.exportDomain0(configuration, result, domain);
                }
                for (Table table : s2.getTables()) {
                    InformationSchemaExport.exportTable0(configuration, result, table, includedTables);
                }
                for (Sequence sequence2 : s2.getSequences()) {
                    InformationSchemaExport.exportSequence0(configuration, result, sequence2);
                }
            }
        }
        return result;
    }

    private static final void exportDomain0(Configuration configuration, InformationSchema result, Domain<?> d) {
        org.jooq.util.xml.jaxb.Domain id = new org.jooq.util.xml.jaxb.Domain();
        String catalogName = InformationSchemaExport.catalogName(d);
        String schemaName = InformationSchemaExport.schemaName(d);
        String domainName = d.getName();
        if (!StringUtils.isBlank(catalogName)) {
            id.setDomainCatalog(catalogName);
        }
        if (!StringUtils.isBlank(schemaName)) {
            id.setDomainSchema(schemaName);
        }
        id.setDomainName(domainName);
        id.setDataType(d.getDataType().getTypeName(configuration));
        if (d.getDataType().lengthDefined()) {
            id.setCharacterMaximumLength(d.getDataType().length());
        }
        if (d.getDataType().precisionDefined()) {
            id.setNumericPrecision(d.getDataType().precision());
        }
        if (d.getDataType().scaleDefined()) {
            id.setNumericScale(d.getDataType().scale());
        }
        result.getDomains().add(id);
        for (Check<?> c : d.getChecks()) {
            DomainConstraint idc = new DomainConstraint();
            CheckConstraint icc = new CheckConstraint();
            if (!StringUtils.isBlank(catalogName)) {
                idc.setDomainCatalog(catalogName);
                idc.setConstraintCatalog(catalogName);
                icc.setConstraintCatalog(catalogName);
            }
            if (!StringUtils.isBlank(schemaName)) {
                idc.setDomainSchema(schemaName);
                idc.setConstraintSchema(schemaName);
                icc.setConstraintSchema(schemaName);
            }
            idc.setDomainName(domainName);
            idc.setConstraintName(c.getName());
            icc.setConstraintName(c.getName());
            icc.setCheckClause(configuration.dsl().render(c.condition()));
            result.getDomainConstraints().add(idc);
            result.getCheckConstraints().add(icc);
        }
    }

    private static final void exportSequence0(Configuration configuration, InformationSchema result, Sequence<?> q) {
        Param p;
        Field<?> field2;
        org.jooq.util.xml.jaxb.Sequence iq = new org.jooq.util.xml.jaxb.Sequence();
        String catalogName = InformationSchemaExport.catalogName(q);
        String schemaName = InformationSchemaExport.schemaName(q);
        if (!StringUtils.isBlank(catalogName)) {
            iq.setSequenceCatalog(catalogName);
        }
        if (!StringUtils.isBlank(schemaName)) {
            iq.setSequenceSchema(schemaName);
        }
        iq.setSequenceName(q.getName());
        iq.setDataType(q.getDataType().getTypeName(configuration));
        if (q.getDataType().lengthDefined()) {
            iq.setCharacterMaximumLength(q.getDataType().length());
        }
        if (q.getDataType().precisionDefined()) {
            iq.setNumericPrecision(q.getDataType().precision());
        }
        if (q.getDataType().scaleDefined()) {
            iq.setNumericScale(q.getDataType().scale());
        }
        if (q.getStartWith() != null) {
            String string;
            field2 = q.getStartWith();
            if (field2 instanceof Param) {
                p = (Param)field2;
                string = p.getValue();
            } else {
                string = q.getStartWith().toString();
            }
            iq.setStartValue(Convert.convert((Object)string, BigInteger.class));
        }
        if (q.getIncrementBy() != null) {
            String string;
            field2 = q.getIncrementBy();
            if (field2 instanceof Param) {
                p = (Param)field2;
                string = p.getValue();
            } else {
                string = q.getIncrementBy().toString();
            }
            iq.setIncrement(Convert.convert((Object)string, BigInteger.class));
        }
        if (q.getMinvalue() != null) {
            String string;
            field2 = q.getMinvalue();
            if (field2 instanceof Param) {
                p = (Param)field2;
                string = p.getValue();
            } else {
                string = q.getMinvalue().toString();
            }
            iq.setMinimumValue(Convert.convert((Object)string, BigInteger.class));
        }
        if (q.getMaxvalue() != null) {
            String string;
            field2 = q.getMaxvalue();
            if (field2 instanceof Param) {
                p = (Param)field2;
                string = p.getValue();
            } else {
                string = q.getMaxvalue().toString();
            }
            iq.setMaximumValue(Convert.convert((Object)string, BigInteger.class));
        }
        iq.setCycleOption(q.getCycle());
        if (q.getCache() != null) {
            String string;
            field2 = q.getCache();
            if (field2 instanceof Param) {
                p = (Param)field2;
                string = p.getValue();
            } else {
                string = q.getCache().toString();
            }
            iq.setCache(Convert.convert((Object)string, BigInteger.class));
        }
        result.getSequences().add(iq);
    }

    private static final void exportCatalog0(InformationSchema result, Catalog c) {
        org.jooq.util.xml.jaxb.Catalog ic = new org.jooq.util.xml.jaxb.Catalog();
        if (!StringUtils.isBlank(c.getName())) {
            ic.setCatalogName(c.getName());
            ic.setComment(c.getComment());
            result.getCatalogs().add(ic);
        }
    }

    private static final void exportSchema0(InformationSchema result, Schema s2) {
        org.jooq.util.xml.jaxb.Schema is = new org.jooq.util.xml.jaxb.Schema();
        String catalogName = InformationSchemaExport.catalogName(s2);
        if (!StringUtils.isBlank(catalogName)) {
            is.setCatalogName(catalogName);
        }
        if (!StringUtils.isBlank(s2.getName())) {
            is.setSchemaName(s2.getName());
            is.setComment(s2.getComment());
            result.getSchemata().add(is);
        }
    }

    private static final void exportTable0(Configuration configuration, InformationSchema result, Table<?> t2, Set<Table<?>> includedTables) {
        org.jooq.util.xml.jaxb.Table it = new org.jooq.util.xml.jaxb.Table();
        String catalogName = InformationSchemaExport.catalogName(t2);
        String schemaName = InformationSchemaExport.schemaName(t2);
        if (!StringUtils.isBlank(catalogName)) {
            it.setTableCatalog(catalogName);
        }
        if (!StringUtils.isBlank(schemaName)) {
            it.setTableSchema(schemaName);
        }
        switch (t2.getOptions().type()) {
            case MATERIALIZED_VIEW: 
            case VIEW: {
                it.setTableType(TableType.VIEW);
                break;
            }
            case TEMPORARY: {
                it.setTableType(TableType.GLOBAL_TEMPORARY);
                break;
            }
            default: {
                it.setTableType(TableType.BASE_TABLE);
            }
        }
        it.setTableName(t2.getName());
        it.setComment(t2.getComment());
        result.getTables().add(it);
        if (t2.getOptions().type() == TableOptions.TableType.VIEW) {
            View iv = new View();
            if (!StringUtils.isBlank(catalogName)) {
                iv.setTableCatalog(catalogName);
            }
            if (!StringUtils.isBlank(schemaName)) {
                iv.setTableSchema(schemaName);
            }
            iv.setTableName(t2.getName());
            iv.setViewDefinition(t2.getOptions().source());
            result.getViews().add(iv);
        }
        Field<?>[] fields = t2.fields();
        for (int i = 0; i < fields.length; ++i) {
            Field<?> field2 = fields[i];
            DataType type = field2.getDataType();
            Column ic = new Column();
            if (!StringUtils.isBlank(catalogName)) {
                ic.setTableCatalog(catalogName);
            }
            if (!StringUtils.isBlank(schemaName)) {
                ic.setTableSchema(schemaName);
            }
            ic.setTableName(t2.getName());
            ic.setColumnName(field2.getName());
            ic.setComment(field2.getComment());
            ic.setDataType(type.getTypeName(configuration));
            if (type.lengthDefined()) {
                ic.setCharacterMaximumLength(type.length());
            }
            if (type.precisionDefined()) {
                ic.setNumericPrecision(type.precision());
            }
            if (type.scaleDefined()) {
                ic.setNumericScale(type.scale());
            }
            ic.setColumnDefault(DSL.using(configuration).render(type.defaultValue()));
            ic.setIsNullable(type.nullable());
            ic.setOrdinalPosition(i + 1);
            ic.setReadonly(type.readonly());
            if (type.computed()) {
                ic.setIsGenerated(type.computed());
                ic.setGenerationExpression(DSL.using(configuration).render(type.generatedAlwaysAs()));
                ic.setGenerationOption(type.generationOption() == QOM.GenerationOption.VIRTUAL ? "VIRTUAL" : (type.generationOption() == QOM.GenerationOption.STORED ? "STORED" : null));
            }
            result.getColumns().add(ic);
        }
        for (UniqueKey<?> uniqueKey : t2.getKeys()) {
            InformationSchemaExport.exportKey0(result, t2, uniqueKey, uniqueKey.isPrimary() ? TableConstraintType.PRIMARY_KEY : TableConstraintType.UNIQUE);
        }
        for (ForeignKey foreignKey : t2.getReferences()) {
            if (!includedTables.contains(foreignKey.getKey().getTable())) continue;
            InformationSchemaExport.exportKey0(result, t2, foreignKey, TableConstraintType.FOREIGN_KEY);
        }
        for (Check check2 : t2.getChecks()) {
            if (!includedTables.contains(check2.getTable())) continue;
            InformationSchemaExport.exportCheck0(configuration, result, t2, check2);
        }
        for (Index index : t2.getIndexes()) {
            InformationSchemaExport.exportIndex0(result, t2, index);
        }
    }

    private static final void exportCheck0(Configuration configuration, InformationSchema result, Table<?> t2, Check<?> chk) {
        InformationSchemaExport.exportTableConstraint(result, t2, chk.getName(), TableConstraintType.CHECK);
        CheckConstraint c = new CheckConstraint();
        String catalogName = InformationSchemaExport.catalogName(t2);
        String schemaName = InformationSchemaExport.schemaName(t2);
        if (!StringUtils.isBlank(catalogName)) {
            c.setConstraintCatalog(catalogName);
        }
        if (!StringUtils.isBlank(schemaName)) {
            c.setConstraintSchema(schemaName);
        }
        c.setConstraintName(chk.getName());
        c.setCheckClause(configuration.dsl().render(chk.condition()));
        result.getCheckConstraints().add(c);
    }

    private static final void exportIndex0(InformationSchema result, Table<?> t2, Index index) {
        org.jooq.util.xml.jaxb.Index i = new org.jooq.util.xml.jaxb.Index();
        String catalogName = InformationSchemaExport.catalogName(t2);
        String schemaName = InformationSchemaExport.schemaName(t2);
        if (!StringUtils.isBlank(catalogName)) {
            i.withIndexCatalog(catalogName).withTableCatalog(catalogName);
        }
        if (!StringUtils.isBlank(schemaName)) {
            i.withIndexSchema(schemaName).withTableSchema(schemaName);
        }
        i.setIndexName(index.getName());
        i.setTableName(t2.getName());
        i.setIsUnique(index.getUnique());
        result.getIndexes().add(i);
        int position = 1;
        for (SortField<?> sortField : index.getFields()) {
            IndexColumnUsage ic = new IndexColumnUsage();
            if (!StringUtils.isBlank(catalogName)) {
                ic.withIndexCatalog(catalogName).withTableCatalog(catalogName);
            }
            if (!StringUtils.isBlank(schemaName)) {
                ic.withIndexSchema(schemaName).withTableSchema(schemaName);
            }
            ic.setIndexName(index.getName());
            ic.setTableName(t2.getName());
            ic.setOrdinalPosition(position++);
            ic.setColumnName(sortField.getName());
            ic.setIsDescending(sortField.getOrder() == SortOrder.DESC);
            result.getIndexColumnUsages().add(ic);
        }
    }

    private static final void exportKey0(InformationSchema result, Table<?> t2, Key<?> key, TableConstraintType constraintType) {
        InformationSchemaExport.exportTableConstraint(result, t2, key.getName(), constraintType);
        String catalogName = InformationSchemaExport.catalogName(t2);
        String schemaName = InformationSchemaExport.schemaName(t2);
        int i = 0;
        for (Field field2 : key.getFields()) {
            KeyColumnUsage kc = new KeyColumnUsage();
            if (!StringUtils.isBlank(catalogName)) {
                kc.setConstraintCatalog(catalogName);
                kc.setTableCatalog(catalogName);
            }
            if (!StringUtils.isBlank(schemaName)) {
                kc.setConstraintSchema(schemaName);
                kc.setTableSchema(schemaName);
            }
            kc.setColumnName(field2.getName());
            kc.setTableName(t2.getName());
            kc.setOrdinalPosition(++i);
            kc.setConstraintName(key.getName());
            result.getKeyColumnUsages().add(kc);
        }
        if (constraintType == TableConstraintType.FOREIGN_KEY) {
            ReferentialConstraint rc = new ReferentialConstraint();
            UniqueKey uniqueKey = ((ForeignKey)key).getKey();
            String ukCatalogName = InformationSchemaExport.catalogName(uniqueKey.getTable());
            String ukSchemaName = InformationSchemaExport.schemaName(uniqueKey.getTable());
            if (!StringUtils.isBlank(catalogName)) {
                rc.setConstraintCatalog(catalogName);
            }
            if (!StringUtils.isBlank(ukCatalogName)) {
                rc.setUniqueConstraintCatalog(ukCatalogName);
            }
            if (!StringUtils.isBlank(schemaName)) {
                rc.setConstraintSchema(schemaName);
            }
            if (!StringUtils.isBlank(ukSchemaName)) {
                rc.setUniqueConstraintSchema(ukSchemaName);
            }
            rc.setConstraintName(key.getName());
            rc.setUniqueConstraintName(uniqueKey.getName());
            result.getReferentialConstraints().add(rc);
        }
    }

    private static final void exportTableConstraint(InformationSchema result, Table<?> t2, String constraintName, TableConstraintType constraintType) {
        TableConstraint tc = new TableConstraint();
        String catalogName = InformationSchemaExport.catalogName(t2);
        String schemaName = InformationSchemaExport.schemaName(t2);
        tc.setConstraintName(constraintName);
        tc.setConstraintType(constraintType);
        if (!StringUtils.isBlank(catalogName)) {
            tc.withConstraintCatalog(catalogName).withTableCatalog(catalogName);
        }
        if (!StringUtils.isBlank(schemaName)) {
            tc.withConstraintSchema(schemaName).withTableSchema(schemaName);
        }
        tc.setTableName(t2.getName());
        result.getTableConstraints().add(tc);
    }

    private static final String catalogName(Schema s2) {
        return s2.getCatalog() == null ? null : s2.getCatalog().getName();
    }

    private static final String catalogName(Qualified q) {
        return q.getCatalog() == null ? null : q.getCatalog().getName();
    }

    private static final String schemaName(Qualified q) {
        return q.getSchema() == null ? null : q.getSchema().getName();
    }

    private InformationSchemaExport() {
    }
}

