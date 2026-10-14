/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlType
 *  jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
package org.jooq.util.xml.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import org.jooq.util.jaxb.tools.StringAdapter;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="IndexColumnUsage", propOrder={})
public class IndexColumnUsage
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="index_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String indexCatalog;
    @XmlElement(name="index_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String indexSchema;
    @XmlElement(name="index_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String indexName;
    @XmlElement(name="table_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableCatalog;
    @XmlElement(name="table_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableSchema;
    @XmlElement(name="table_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableName;
    @XmlElement(name="column_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String columnName;
    @XmlElement(name="ordinal_position")
    protected int ordinalPosition;
    @XmlElement(name="is_descending")
    protected Boolean isDescending;

    public String getIndexCatalog() {
        return this.indexCatalog;
    }

    public void setIndexCatalog(String value) {
        this.indexCatalog = value;
    }

    public String getIndexSchema() {
        return this.indexSchema;
    }

    public void setIndexSchema(String value) {
        this.indexSchema = value;
    }

    public String getIndexName() {
        return this.indexName;
    }

    public void setIndexName(String value) {
        this.indexName = value;
    }

    public String getTableCatalog() {
        return this.tableCatalog;
    }

    public void setTableCatalog(String value) {
        this.tableCatalog = value;
    }

    public String getTableSchema() {
        return this.tableSchema;
    }

    public void setTableSchema(String value) {
        this.tableSchema = value;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String value) {
        this.tableName = value;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String value) {
        this.columnName = value;
    }

    public int getOrdinalPosition() {
        return this.ordinalPosition;
    }

    public void setOrdinalPosition(int value) {
        this.ordinalPosition = value;
    }

    public Boolean isIsDescending() {
        return this.isDescending;
    }

    public void setIsDescending(Boolean value) {
        this.isDescending = value;
    }

    public IndexColumnUsage withIndexCatalog(String value) {
        this.setIndexCatalog(value);
        return this;
    }

    public IndexColumnUsage withIndexSchema(String value) {
        this.setIndexSchema(value);
        return this;
    }

    public IndexColumnUsage withIndexName(String value) {
        this.setIndexName(value);
        return this;
    }

    public IndexColumnUsage withTableCatalog(String value) {
        this.setTableCatalog(value);
        return this;
    }

    public IndexColumnUsage withTableSchema(String value) {
        this.setTableSchema(value);
        return this;
    }

    public IndexColumnUsage withTableName(String value) {
        this.setTableName(value);
        return this;
    }

    public IndexColumnUsage withColumnName(String value) {
        this.setColumnName(value);
        return this;
    }

    public IndexColumnUsage withOrdinalPosition(int value) {
        this.setOrdinalPosition(value);
        return this;
    }

    public IndexColumnUsage withIsDescending(Boolean value) {
        this.setIsDescending(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("index_catalog", this.indexCatalog);
        builder.append("index_schema", this.indexSchema);
        builder.append("index_name", this.indexName);
        builder.append("table_catalog", this.tableCatalog);
        builder.append("table_schema", this.tableSchema);
        builder.append("table_name", this.tableName);
        builder.append("column_name", this.columnName);
        builder.append("ordinal_position", this.ordinalPosition);
        builder.append("is_descending", this.isDescending);
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
        IndexColumnUsage other = (IndexColumnUsage)that;
        if (this.indexCatalog == null ? other.indexCatalog != null : !this.indexCatalog.equals(other.indexCatalog)) {
            return false;
        }
        if (this.indexSchema == null ? other.indexSchema != null : !this.indexSchema.equals(other.indexSchema)) {
            return false;
        }
        if (this.indexName == null ? other.indexName != null : !this.indexName.equals(other.indexName)) {
            return false;
        }
        if (this.tableCatalog == null ? other.tableCatalog != null : !this.tableCatalog.equals(other.tableCatalog)) {
            return false;
        }
        if (this.tableSchema == null ? other.tableSchema != null : !this.tableSchema.equals(other.tableSchema)) {
            return false;
        }
        if (this.tableName == null ? other.tableName != null : !this.tableName.equals(other.tableName)) {
            return false;
        }
        if (this.columnName == null ? other.columnName != null : !this.columnName.equals(other.columnName)) {
            return false;
        }
        if (this.ordinalPosition != other.ordinalPosition) {
            return false;
        }
        return !(this.isDescending == null ? other.isDescending != null : !this.isDescending.equals(other.isDescending));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.indexCatalog == null ? 0 : this.indexCatalog.hashCode());
        result = 31 * result + (this.indexSchema == null ? 0 : this.indexSchema.hashCode());
        result = 31 * result + (this.indexName == null ? 0 : this.indexName.hashCode());
        result = 31 * result + (this.tableCatalog == null ? 0 : this.tableCatalog.hashCode());
        result = 31 * result + (this.tableSchema == null ? 0 : this.tableSchema.hashCode());
        result = 31 * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        result = 31 * result + (this.columnName == null ? 0 : this.columnName.hashCode());
        result = 31 * result + this.ordinalPosition;
        result = 31 * result + (this.isDescending == null ? 0 : this.isDescending.hashCode());
        return result;
    }
}

