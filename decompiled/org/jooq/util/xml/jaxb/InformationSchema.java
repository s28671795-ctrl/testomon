/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlElementWrapper
 *  jakarta.xml.bind.annotation.XmlRootElement
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.util.xml.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;
import org.jooq.util.xml.jaxb.Catalog;
import org.jooq.util.xml.jaxb.CheckConstraint;
import org.jooq.util.xml.jaxb.Column;
import org.jooq.util.xml.jaxb.Domain;
import org.jooq.util.xml.jaxb.DomainConstraint;
import org.jooq.util.xml.jaxb.ElementType;
import org.jooq.util.xml.jaxb.Index;
import org.jooq.util.xml.jaxb.IndexColumnUsage;
import org.jooq.util.xml.jaxb.KeyColumnUsage;
import org.jooq.util.xml.jaxb.Parameter;
import org.jooq.util.xml.jaxb.ReferentialConstraint;
import org.jooq.util.xml.jaxb.Routine;
import org.jooq.util.xml.jaxb.Schema;
import org.jooq.util.xml.jaxb.Sequence;
import org.jooq.util.xml.jaxb.Table;
import org.jooq.util.xml.jaxb.TableConstraint;
import org.jooq.util.xml.jaxb.View;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={})
@XmlRootElement(name="information_schema")
public class InformationSchema
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElementWrapper(name="catalogs")
    @XmlElement(name="catalog")
    protected List<Catalog> catalogs;
    @XmlElementWrapper(name="schemata")
    @XmlElement(name="schema")
    protected List<Schema> schemata;
    @XmlElementWrapper(name="sequences")
    @XmlElement(name="sequence")
    protected List<Sequence> sequences;
    @XmlElementWrapper(name="tables")
    @XmlElement(name="table")
    protected List<Table> tables;
    @XmlElementWrapper(name="views")
    @XmlElement(name="view")
    protected List<View> views;
    @XmlElementWrapper(name="columns")
    @XmlElement(name="column")
    protected List<Column> columns;
    @XmlElementWrapper(name="table_constraints")
    @XmlElement(name="table_constraint")
    protected List<TableConstraint> tableConstraints;
    @XmlElementWrapper(name="key_column_usages")
    @XmlElement(name="key_column_usage")
    protected List<KeyColumnUsage> keyColumnUsages;
    @XmlElementWrapper(name="referential_constraints")
    @XmlElement(name="referential_constraint")
    protected List<ReferentialConstraint> referentialConstraints;
    @XmlElementWrapper(name="check_constraints")
    @XmlElement(name="check_constraint")
    protected List<CheckConstraint> checkConstraints;
    @XmlElementWrapper(name="domains")
    @XmlElement(name="domain")
    protected List<Domain> domains;
    @XmlElementWrapper(name="domain_constraints")
    @XmlElement(name="domainConstraint")
    protected List<DomainConstraint> domainConstraints;
    @XmlElementWrapper(name="indexes")
    @XmlElement(name="index")
    protected List<Index> indexes;
    @XmlElementWrapper(name="index_column_usages")
    @XmlElement(name="index_column_usage")
    protected List<IndexColumnUsage> indexColumnUsages;
    @XmlElementWrapper(name="routines")
    @XmlElement(name="routine")
    protected List<Routine> routines;
    @XmlElementWrapper(name="parameters")
    @XmlElement(name="parameter")
    protected List<Parameter> parameters;
    @XmlElementWrapper(name="element_types")
    @XmlElement(name="element_type")
    protected List<ElementType> elementTypes;

    public List<Catalog> getCatalogs() {
        if (this.catalogs == null) {
            this.catalogs = new ArrayList<Catalog>();
        }
        return this.catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    public List<Schema> getSchemata() {
        if (this.schemata == null) {
            this.schemata = new ArrayList<Schema>();
        }
        return this.schemata;
    }

    public void setSchemata(List<Schema> schemata) {
        this.schemata = schemata;
    }

    public List<Sequence> getSequences() {
        if (this.sequences == null) {
            this.sequences = new ArrayList<Sequence>();
        }
        return this.sequences;
    }

    public void setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
    }

    public List<Table> getTables() {
        if (this.tables == null) {
            this.tables = new ArrayList<Table>();
        }
        return this.tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<View> getViews() {
        if (this.views == null) {
            this.views = new ArrayList<View>();
        }
        return this.views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    public List<Column> getColumns() {
        if (this.columns == null) {
            this.columns = new ArrayList<Column>();
        }
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<TableConstraint> getTableConstraints() {
        if (this.tableConstraints == null) {
            this.tableConstraints = new ArrayList<TableConstraint>();
        }
        return this.tableConstraints;
    }

    public void setTableConstraints(List<TableConstraint> tableConstraints) {
        this.tableConstraints = tableConstraints;
    }

    public List<KeyColumnUsage> getKeyColumnUsages() {
        if (this.keyColumnUsages == null) {
            this.keyColumnUsages = new ArrayList<KeyColumnUsage>();
        }
        return this.keyColumnUsages;
    }

    public void setKeyColumnUsages(List<KeyColumnUsage> keyColumnUsages) {
        this.keyColumnUsages = keyColumnUsages;
    }

    public List<ReferentialConstraint> getReferentialConstraints() {
        if (this.referentialConstraints == null) {
            this.referentialConstraints = new ArrayList<ReferentialConstraint>();
        }
        return this.referentialConstraints;
    }

    public void setReferentialConstraints(List<ReferentialConstraint> referentialConstraints) {
        this.referentialConstraints = referentialConstraints;
    }

    public List<CheckConstraint> getCheckConstraints() {
        if (this.checkConstraints == null) {
            this.checkConstraints = new ArrayList<CheckConstraint>();
        }
        return this.checkConstraints;
    }

    public void setCheckConstraints(List<CheckConstraint> checkConstraints) {
        this.checkConstraints = checkConstraints;
    }

    public List<Domain> getDomains() {
        if (this.domains == null) {
            this.domains = new ArrayList<Domain>();
        }
        return this.domains;
    }

    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }

    public List<DomainConstraint> getDomainConstraints() {
        if (this.domainConstraints == null) {
            this.domainConstraints = new ArrayList<DomainConstraint>();
        }
        return this.domainConstraints;
    }

    public void setDomainConstraints(List<DomainConstraint> domainConstraints) {
        this.domainConstraints = domainConstraints;
    }

    public List<Index> getIndexes() {
        if (this.indexes == null) {
            this.indexes = new ArrayList<Index>();
        }
        return this.indexes;
    }

    public void setIndexes(List<Index> indexes) {
        this.indexes = indexes;
    }

    public List<IndexColumnUsage> getIndexColumnUsages() {
        if (this.indexColumnUsages == null) {
            this.indexColumnUsages = new ArrayList<IndexColumnUsage>();
        }
        return this.indexColumnUsages;
    }

    public void setIndexColumnUsages(List<IndexColumnUsage> indexColumnUsages) {
        this.indexColumnUsages = indexColumnUsages;
    }

    public List<Routine> getRoutines() {
        if (this.routines == null) {
            this.routines = new ArrayList<Routine>();
        }
        return this.routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }

    public List<Parameter> getParameters() {
        if (this.parameters == null) {
            this.parameters = new ArrayList<Parameter>();
        }
        return this.parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public List<ElementType> getElementTypes() {
        if (this.elementTypes == null) {
            this.elementTypes = new ArrayList<ElementType>();
        }
        return this.elementTypes;
    }

    public void setElementTypes(List<ElementType> elementTypes) {
        this.elementTypes = elementTypes;
    }

    public InformationSchema withCatalogs(Catalog ... values2) {
        if (values2 != null) {
            for (Catalog value : values2) {
                this.getCatalogs().add(value);
            }
        }
        return this;
    }

    public InformationSchema withCatalogs(Collection<Catalog> values2) {
        if (values2 != null) {
            this.getCatalogs().addAll(values2);
        }
        return this;
    }

    public InformationSchema withCatalogs(List<Catalog> catalogs) {
        this.setCatalogs(catalogs);
        return this;
    }

    public InformationSchema withSchemata(Schema ... values2) {
        if (values2 != null) {
            for (Schema value : values2) {
                this.getSchemata().add(value);
            }
        }
        return this;
    }

    public InformationSchema withSchemata(Collection<Schema> values2) {
        if (values2 != null) {
            this.getSchemata().addAll(values2);
        }
        return this;
    }

    public InformationSchema withSchemata(List<Schema> schemata) {
        this.setSchemata(schemata);
        return this;
    }

    public InformationSchema withSequences(Sequence ... values2) {
        if (values2 != null) {
            for (Sequence value : values2) {
                this.getSequences().add(value);
            }
        }
        return this;
    }

    public InformationSchema withSequences(Collection<Sequence> values2) {
        if (values2 != null) {
            this.getSequences().addAll(values2);
        }
        return this;
    }

    public InformationSchema withSequences(List<Sequence> sequences) {
        this.setSequences(sequences);
        return this;
    }

    public InformationSchema withTables(Table ... values2) {
        if (values2 != null) {
            for (Table value : values2) {
                this.getTables().add(value);
            }
        }
        return this;
    }

    public InformationSchema withTables(Collection<Table> values2) {
        if (values2 != null) {
            this.getTables().addAll(values2);
        }
        return this;
    }

    public InformationSchema withTables(List<Table> tables) {
        this.setTables(tables);
        return this;
    }

    public InformationSchema withViews(View ... values2) {
        if (values2 != null) {
            for (View value : values2) {
                this.getViews().add(value);
            }
        }
        return this;
    }

    public InformationSchema withViews(Collection<View> values2) {
        if (values2 != null) {
            this.getViews().addAll(values2);
        }
        return this;
    }

    public InformationSchema withViews(List<View> views) {
        this.setViews(views);
        return this;
    }

    public InformationSchema withColumns(Column ... values2) {
        if (values2 != null) {
            for (Column value : values2) {
                this.getColumns().add(value);
            }
        }
        return this;
    }

    public InformationSchema withColumns(Collection<Column> values2) {
        if (values2 != null) {
            this.getColumns().addAll(values2);
        }
        return this;
    }

    public InformationSchema withColumns(List<Column> columns) {
        this.setColumns(columns);
        return this;
    }

    public InformationSchema withTableConstraints(TableConstraint ... values2) {
        if (values2 != null) {
            for (TableConstraint value : values2) {
                this.getTableConstraints().add(value);
            }
        }
        return this;
    }

    public InformationSchema withTableConstraints(Collection<TableConstraint> values2) {
        if (values2 != null) {
            this.getTableConstraints().addAll(values2);
        }
        return this;
    }

    public InformationSchema withTableConstraints(List<TableConstraint> tableConstraints) {
        this.setTableConstraints(tableConstraints);
        return this;
    }

    public InformationSchema withKeyColumnUsages(KeyColumnUsage ... values2) {
        if (values2 != null) {
            for (KeyColumnUsage value : values2) {
                this.getKeyColumnUsages().add(value);
            }
        }
        return this;
    }

    public InformationSchema withKeyColumnUsages(Collection<KeyColumnUsage> values2) {
        if (values2 != null) {
            this.getKeyColumnUsages().addAll(values2);
        }
        return this;
    }

    public InformationSchema withKeyColumnUsages(List<KeyColumnUsage> keyColumnUsages) {
        this.setKeyColumnUsages(keyColumnUsages);
        return this;
    }

    public InformationSchema withReferentialConstraints(ReferentialConstraint ... values2) {
        if (values2 != null) {
            for (ReferentialConstraint value : values2) {
                this.getReferentialConstraints().add(value);
            }
        }
        return this;
    }

    public InformationSchema withReferentialConstraints(Collection<ReferentialConstraint> values2) {
        if (values2 != null) {
            this.getReferentialConstraints().addAll(values2);
        }
        return this;
    }

    public InformationSchema withReferentialConstraints(List<ReferentialConstraint> referentialConstraints) {
        this.setReferentialConstraints(referentialConstraints);
        return this;
    }

    public InformationSchema withCheckConstraints(CheckConstraint ... values2) {
        if (values2 != null) {
            for (CheckConstraint value : values2) {
                this.getCheckConstraints().add(value);
            }
        }
        return this;
    }

    public InformationSchema withCheckConstraints(Collection<CheckConstraint> values2) {
        if (values2 != null) {
            this.getCheckConstraints().addAll(values2);
        }
        return this;
    }

    public InformationSchema withCheckConstraints(List<CheckConstraint> checkConstraints) {
        this.setCheckConstraints(checkConstraints);
        return this;
    }

    public InformationSchema withDomains(Domain ... values2) {
        if (values2 != null) {
            for (Domain value : values2) {
                this.getDomains().add(value);
            }
        }
        return this;
    }

    public InformationSchema withDomains(Collection<Domain> values2) {
        if (values2 != null) {
            this.getDomains().addAll(values2);
        }
        return this;
    }

    public InformationSchema withDomains(List<Domain> domains) {
        this.setDomains(domains);
        return this;
    }

    public InformationSchema withDomainConstraints(DomainConstraint ... values2) {
        if (values2 != null) {
            for (DomainConstraint value : values2) {
                this.getDomainConstraints().add(value);
            }
        }
        return this;
    }

    public InformationSchema withDomainConstraints(Collection<DomainConstraint> values2) {
        if (values2 != null) {
            this.getDomainConstraints().addAll(values2);
        }
        return this;
    }

    public InformationSchema withDomainConstraints(List<DomainConstraint> domainConstraints) {
        this.setDomainConstraints(domainConstraints);
        return this;
    }

    public InformationSchema withIndexes(Index ... values2) {
        if (values2 != null) {
            for (Index value : values2) {
                this.getIndexes().add(value);
            }
        }
        return this;
    }

    public InformationSchema withIndexes(Collection<Index> values2) {
        if (values2 != null) {
            this.getIndexes().addAll(values2);
        }
        return this;
    }

    public InformationSchema withIndexes(List<Index> indexes) {
        this.setIndexes(indexes);
        return this;
    }

    public InformationSchema withIndexColumnUsages(IndexColumnUsage ... values2) {
        if (values2 != null) {
            for (IndexColumnUsage value : values2) {
                this.getIndexColumnUsages().add(value);
            }
        }
        return this;
    }

    public InformationSchema withIndexColumnUsages(Collection<IndexColumnUsage> values2) {
        if (values2 != null) {
            this.getIndexColumnUsages().addAll(values2);
        }
        return this;
    }

    public InformationSchema withIndexColumnUsages(List<IndexColumnUsage> indexColumnUsages) {
        this.setIndexColumnUsages(indexColumnUsages);
        return this;
    }

    public InformationSchema withRoutines(Routine ... values2) {
        if (values2 != null) {
            for (Routine value : values2) {
                this.getRoutines().add(value);
            }
        }
        return this;
    }

    public InformationSchema withRoutines(Collection<Routine> values2) {
        if (values2 != null) {
            this.getRoutines().addAll(values2);
        }
        return this;
    }

    public InformationSchema withRoutines(List<Routine> routines) {
        this.setRoutines(routines);
        return this;
    }

    public InformationSchema withParameters(Parameter ... values2) {
        if (values2 != null) {
            for (Parameter value : values2) {
                this.getParameters().add(value);
            }
        }
        return this;
    }

    public InformationSchema withParameters(Collection<Parameter> values2) {
        if (values2 != null) {
            this.getParameters().addAll(values2);
        }
        return this;
    }

    public InformationSchema withParameters(List<Parameter> parameters) {
        this.setParameters(parameters);
        return this;
    }

    public InformationSchema withElementTypes(ElementType ... values2) {
        if (values2 != null) {
            for (ElementType value : values2) {
                this.getElementTypes().add(value);
            }
        }
        return this;
    }

    public InformationSchema withElementTypes(Collection<ElementType> values2) {
        if (values2 != null) {
            this.getElementTypes().addAll(values2);
        }
        return this;
    }

    public InformationSchema withElementTypes(List<ElementType> elementTypes) {
        this.setElementTypes(elementTypes);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("catalogs", "catalog", this.catalogs);
        builder.append("schemata", "schema", this.schemata);
        builder.append("sequences", "sequence", this.sequences);
        builder.append("tables", "table", this.tables);
        builder.append("views", "view", this.views);
        builder.append("columns", "column", this.columns);
        builder.append("table_constraints", "table_constraint", this.tableConstraints);
        builder.append("key_column_usages", "key_column_usage", this.keyColumnUsages);
        builder.append("referential_constraints", "referential_constraint", this.referentialConstraints);
        builder.append("check_constraints", "check_constraint", this.checkConstraints);
        builder.append("domains", "domain", this.domains);
        builder.append("domain_constraints", "domainConstraint", this.domainConstraints);
        builder.append("indexes", "index", this.indexes);
        builder.append("index_column_usages", "index_column_usage", this.indexColumnUsages);
        builder.append("routines", "routine", this.routines);
        builder.append("parameters", "parameter", this.parameters);
        builder.append("element_types", "element_type", this.elementTypes);
    }

    public String toString() {
        XMLBuilder builder = XMLBuilder.nonFormatting();
        this.appendTo(builder);
        return builder.toString();
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        InformationSchema other = (InformationSchema)that;
        if (this.catalogs == null ? other.catalogs != null : !this.catalogs.equals(other.catalogs)) {
            return false;
        }
        if (this.schemata == null ? other.schemata != null : !this.schemata.equals(other.schemata)) {
            return false;
        }
        if (this.sequences == null ? other.sequences != null : !this.sequences.equals(other.sequences)) {
            return false;
        }
        if (this.tables == null ? other.tables != null : !this.tables.equals(other.tables)) {
            return false;
        }
        if (this.views == null ? other.views != null : !this.views.equals(other.views)) {
            return false;
        }
        if (this.columns == null ? other.columns != null : !this.columns.equals(other.columns)) {
            return false;
        }
        if (this.tableConstraints == null ? other.tableConstraints != null : !this.tableConstraints.equals(other.tableConstraints)) {
            return false;
        }
        if (this.keyColumnUsages == null ? other.keyColumnUsages != null : !this.keyColumnUsages.equals(other.keyColumnUsages)) {
            return false;
        }
        if (this.referentialConstraints == null ? other.referentialConstraints != null : !this.referentialConstraints.equals(other.referentialConstraints)) {
            return false;
        }
        if (this.checkConstraints == null ? other.checkConstraints != null : !this.checkConstraints.equals(other.checkConstraints)) {
            return false;
        }
        if (this.domains == null ? other.domains != null : !this.domains.equals(other.domains)) {
            return false;
        }
        if (this.domainConstraints == null ? other.domainConstraints != null : !this.domainConstraints.equals(other.domainConstraints)) {
            return false;
        }
        if (this.indexes == null ? other.indexes != null : !this.indexes.equals(other.indexes)) {
            return false;
        }
        if (this.indexColumnUsages == null ? other.indexColumnUsages != null : !this.indexColumnUsages.equals(other.indexColumnUsages)) {
            return false;
        }
        if (this.routines == null ? other.routines != null : !this.routines.equals(other.routines)) {
            return false;
        }
        if (this.parameters == null ? other.parameters != null : !this.parameters.equals(other.parameters)) {
            return false;
        }
        return !(this.elementTypes == null ? other.elementTypes != null : !this.elementTypes.equals(other.elementTypes));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.catalogs == null ? 0 : this.catalogs.hashCode());
        result = 31 * result + (this.schemata == null ? 0 : this.schemata.hashCode());
        result = 31 * result + (this.sequences == null ? 0 : this.sequences.hashCode());
        result = 31 * result + (this.tables == null ? 0 : this.tables.hashCode());
        result = 31 * result + (this.views == null ? 0 : this.views.hashCode());
        result = 31 * result + (this.columns == null ? 0 : this.columns.hashCode());
        result = 31 * result + (this.tableConstraints == null ? 0 : this.tableConstraints.hashCode());
        result = 31 * result + (this.keyColumnUsages == null ? 0 : this.keyColumnUsages.hashCode());
        result = 31 * result + (this.referentialConstraints == null ? 0 : this.referentialConstraints.hashCode());
        result = 31 * result + (this.checkConstraints == null ? 0 : this.checkConstraints.hashCode());
        result = 31 * result + (this.domains == null ? 0 : this.domains.hashCode());
        result = 31 * result + (this.domainConstraints == null ? 0 : this.domainConstraints.hashCode());
        result = 31 * result + (this.indexes == null ? 0 : this.indexes.hashCode());
        result = 31 * result + (this.indexColumnUsages == null ? 0 : this.indexColumnUsages.hashCode());
        result = 31 * result + (this.routines == null ? 0 : this.routines.hashCode());
        result = 31 * result + (this.parameters == null ? 0 : this.parameters.hashCode());
        result = 31 * result + (this.elementTypes == null ? 0 : this.elementTypes.hashCode());
        return result;
    }
}

