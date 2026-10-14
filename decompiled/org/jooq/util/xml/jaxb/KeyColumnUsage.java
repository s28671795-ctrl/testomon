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
@XmlType(name="KeyColumnUsage", propOrder={})
public class KeyColumnUsage
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="column_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String columnName;
    @XmlElement(name="constraint_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String constraintCatalog;
    @XmlElement(name="constraint_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String constraintSchema;
    @XmlElement(name="constraint_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String constraintName;
    @XmlElement(name="ordinal_position")
    protected int ordinalPosition;
    @XmlElement(name="table_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableCatalog;
    @XmlElement(name="table_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableSchema;
    @XmlElement(name="table_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableName;

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String value) {
        this.columnName = value;
    }

    public String getConstraintCatalog() {
        return this.constraintCatalog;
    }

    public void setConstraintCatalog(String value) {
        this.constraintCatalog = value;
    }

    public String getConstraintSchema() {
        return this.constraintSchema;
    }

    public void setConstraintSchema(String value) {
        this.constraintSchema = value;
    }

    public String getConstraintName() {
        return this.constraintName;
    }

    public void setConstraintName(String value) {
        this.constraintName = value;
    }

    public int getOrdinalPosition() {
        return this.ordinalPosition;
    }

    public void setOrdinalPosition(int value) {
        this.ordinalPosition = value;
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

    public KeyColumnUsage withColumnName(String value) {
        this.setColumnName(value);
        return this;
    }

    public KeyColumnUsage withConstraintCatalog(String value) {
        this.setConstraintCatalog(value);
        return this;
    }

    public KeyColumnUsage withConstraintSchema(String value) {
        this.setConstraintSchema(value);
        return this;
    }

    public KeyColumnUsage withConstraintName(String value) {
        this.setConstraintName(value);
        return this;
    }

    public KeyColumnUsage withOrdinalPosition(int value) {
        this.setOrdinalPosition(value);
        return this;
    }

    public KeyColumnUsage withTableCatalog(String value) {
        this.setTableCatalog(value);
        return this;
    }

    public KeyColumnUsage withTableSchema(String value) {
        this.setTableSchema(value);
        return this;
    }

    public KeyColumnUsage withTableName(String value) {
        this.setTableName(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("column_name", this.columnName);
        builder.append("constraint_catalog", this.constraintCatalog);
        builder.append("constraint_schema", this.constraintSchema);
        builder.append("constraint_name", this.constraintName);
        builder.append("ordinal_position", this.ordinalPosition);
        builder.append("table_catalog", this.tableCatalog);
        builder.append("table_schema", this.tableSchema);
        builder.append("table_name", this.tableName);
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
        KeyColumnUsage other = (KeyColumnUsage)that;
        if (this.columnName == null ? other.columnName != null : !this.columnName.equals(other.columnName)) {
            return false;
        }
        if (this.constraintCatalog == null ? other.constraintCatalog != null : !this.constraintCatalog.equals(other.constraintCatalog)) {
            return false;
        }
        if (this.constraintSchema == null ? other.constraintSchema != null : !this.constraintSchema.equals(other.constraintSchema)) {
            return false;
        }
        if (this.constraintName == null ? other.constraintName != null : !this.constraintName.equals(other.constraintName)) {
            return false;
        }
        if (this.ordinalPosition != other.ordinalPosition) {
            return false;
        }
        if (this.tableCatalog == null ? other.tableCatalog != null : !this.tableCatalog.equals(other.tableCatalog)) {
            return false;
        }
        if (this.tableSchema == null ? other.tableSchema != null : !this.tableSchema.equals(other.tableSchema)) {
            return false;
        }
        return !(this.tableName == null ? other.tableName != null : !this.tableName.equals(other.tableName));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.columnName == null ? 0 : this.columnName.hashCode());
        result = 31 * result + (this.constraintCatalog == null ? 0 : this.constraintCatalog.hashCode());
        result = 31 * result + (this.constraintSchema == null ? 0 : this.constraintSchema.hashCode());
        result = 31 * result + (this.constraintName == null ? 0 : this.constraintName.hashCode());
        result = 31 * result + this.ordinalPosition;
        result = 31 * result + (this.tableCatalog == null ? 0 : this.tableCatalog.hashCode());
        result = 31 * result + (this.tableSchema == null ? 0 : this.tableSchema.hashCode());
        result = 31 * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        return result;
    }
}

