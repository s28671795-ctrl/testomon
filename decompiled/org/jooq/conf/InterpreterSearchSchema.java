/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import org.jooq.conf.SettingsBase;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="InterpreterSearchSchema", propOrder={})
public class InterpreterSearchSchema
extends SettingsBase
implements Serializable,
Cloneable,
XMLAppendable {
    private static final long serialVersionUID = 31800L;
    protected String catalog;
    @XmlElement(required=true)
    protected String schema;

    public String getCatalog() {
        return this.catalog;
    }

    public void setCatalog(String value) {
        this.catalog = value;
    }

    public String getSchema() {
        return this.schema;
    }

    public void setSchema(String value) {
        this.schema = value;
    }

    public InterpreterSearchSchema withCatalog(String value) {
        this.setCatalog(value);
        return this;
    }

    public InterpreterSearchSchema withSchema(String value) {
        this.setSchema(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("catalog", this.catalog);
        builder.append("schema", this.schema);
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
        InterpreterSearchSchema other = (InterpreterSearchSchema)that;
        if (this.catalog == null ? other.catalog != null : !this.catalog.equals(other.catalog)) {
            return false;
        }
        return !(this.schema == null ? other.schema != null : !this.schema.equals(other.schema));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.catalog == null ? 0 : this.catalog.hashCode());
        result = 31 * result + (this.schema == null ? 0 : this.schema.hashCode());
        return result;
    }
}

