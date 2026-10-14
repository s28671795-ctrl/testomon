/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlSchemaType
 *  jakarta.xml.bind.annotation.XmlType
 *  jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
package org.jooq.util.xml.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import org.jooq.util.jaxb.tools.StringAdapter;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;
import org.jooq.util.xml.jaxb.TableType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Table", propOrder={})
public class Table
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="table_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableCatalog;
    @XmlElement(name="table_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableSchema;
    @XmlElement(name="table_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String tableName;
    @XmlElement(name="table_type", defaultValue="BASE TABLE")
    @XmlSchemaType(name="string")
    protected TableType tableType = TableType.BASE_TABLE;
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String comment;

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

    public TableType getTableType() {
        return this.tableType;
    }

    public void setTableType(TableType value) {
        this.tableType = value;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    public Table withTableCatalog(String value) {
        this.setTableCatalog(value);
        return this;
    }

    public Table withTableSchema(String value) {
        this.setTableSchema(value);
        return this;
    }

    public Table withTableName(String value) {
        this.setTableName(value);
        return this;
    }

    public Table withTableType(TableType value) {
        this.setTableType(value);
        return this;
    }

    public Table withComment(String value) {
        this.setComment(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("table_catalog", this.tableCatalog);
        builder.append("table_schema", this.tableSchema);
        builder.append("table_name", this.tableName);
        builder.append("table_type", (Object)this.tableType);
        builder.append("comment", this.comment);
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
        Table other = (Table)that;
        if (this.tableCatalog == null ? other.tableCatalog != null : !this.tableCatalog.equals(other.tableCatalog)) {
            return false;
        }
        if (this.tableSchema == null ? other.tableSchema != null : !this.tableSchema.equals(other.tableSchema)) {
            return false;
        }
        if (this.tableName == null ? other.tableName != null : !this.tableName.equals(other.tableName)) {
            return false;
        }
        if (this.tableType == null ? other.tableType != null : !this.tableType.equals((Object)other.tableType)) {
            return false;
        }
        return !(this.comment == null ? other.comment != null : !this.comment.equals(other.comment));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.tableCatalog == null ? 0 : this.tableCatalog.hashCode());
        result = 31 * result + (this.tableSchema == null ? 0 : this.tableSchema.hashCode());
        result = 31 * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        result = 31 * result + (this.tableType == null ? 0 : this.tableType.hashCode());
        result = 31 * result + (this.comment == null ? 0 : this.comment.hashCode());
        return result;
    }
}

