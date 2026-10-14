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
@XmlType(name="Schema", propOrder={})
public class Schema
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="catalog_name")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String catalogName;
    @XmlElement(name="schema_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String schemaName;
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String comment;

    public String getCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String value) {
        this.catalogName = value;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String value) {
        this.schemaName = value;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    public Schema withCatalogName(String value) {
        this.setCatalogName(value);
        return this;
    }

    public Schema withSchemaName(String value) {
        this.setSchemaName(value);
        return this;
    }

    public Schema withComment(String value) {
        this.setComment(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("catalog_name", this.catalogName);
        builder.append("schema_name", this.schemaName);
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
        Schema other = (Schema)that;
        if (this.catalogName == null ? other.catalogName != null : !this.catalogName.equals(other.catalogName)) {
            return false;
        }
        if (this.schemaName == null ? other.schemaName != null : !this.schemaName.equals(other.schemaName)) {
            return false;
        }
        return !(this.comment == null ? other.comment != null : !this.comment.equals(other.comment));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.catalogName == null ? 0 : this.catalogName.hashCode());
        result = 31 * result + (this.schemaName == null ? 0 : this.schemaName.hashCode());
        result = 31 * result + (this.comment == null ? 0 : this.comment.hashCode());
        return result;
    }
}

