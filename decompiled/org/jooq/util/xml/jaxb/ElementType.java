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
@XmlType(name="ElementType", propOrder={})
public class ElementType
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="object_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String objectCatalog;
    @XmlElement(name="object_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String objectSchema;
    @XmlElement(name="object_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String objectName;
    @XmlElement(name="object_type", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String objectType;
    @XmlElement(name="data_type", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String dataType;
    @XmlElement(name="character_maximum_length")
    protected Integer characterMaximumLength;
    @XmlElement(name="numeric_precision")
    protected Integer numericPrecision;
    @XmlElement(name="numeric_scale")
    protected Integer numericScale;
    @XmlElement(name="udt_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String udtCatalog;
    @XmlElement(name="udt_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String udtSchema;
    @XmlElement(name="udt_name")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String udtName;

    public String getObjectCatalog() {
        return this.objectCatalog;
    }

    public void setObjectCatalog(String value) {
        this.objectCatalog = value;
    }

    public String getObjectSchema() {
        return this.objectSchema;
    }

    public void setObjectSchema(String value) {
        this.objectSchema = value;
    }

    public String getObjectName() {
        return this.objectName;
    }

    public void setObjectName(String value) {
        this.objectName = value;
    }

    public String getObjectType() {
        return this.objectType;
    }

    public void setObjectType(String value) {
        this.objectType = value;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String value) {
        this.dataType = value;
    }

    public Integer getCharacterMaximumLength() {
        return this.characterMaximumLength;
    }

    public void setCharacterMaximumLength(Integer value) {
        this.characterMaximumLength = value;
    }

    public Integer getNumericPrecision() {
        return this.numericPrecision;
    }

    public void setNumericPrecision(Integer value) {
        this.numericPrecision = value;
    }

    public Integer getNumericScale() {
        return this.numericScale;
    }

    public void setNumericScale(Integer value) {
        this.numericScale = value;
    }

    public String getUdtCatalog() {
        return this.udtCatalog;
    }

    public void setUdtCatalog(String value) {
        this.udtCatalog = value;
    }

    public String getUdtSchema() {
        return this.udtSchema;
    }

    public void setUdtSchema(String value) {
        this.udtSchema = value;
    }

    public String getUdtName() {
        return this.udtName;
    }

    public void setUdtName(String value) {
        this.udtName = value;
    }

    public ElementType withObjectCatalog(String value) {
        this.setObjectCatalog(value);
        return this;
    }

    public ElementType withObjectSchema(String value) {
        this.setObjectSchema(value);
        return this;
    }

    public ElementType withObjectName(String value) {
        this.setObjectName(value);
        return this;
    }

    public ElementType withObjectType(String value) {
        this.setObjectType(value);
        return this;
    }

    public ElementType withDataType(String value) {
        this.setDataType(value);
        return this;
    }

    public ElementType withCharacterMaximumLength(Integer value) {
        this.setCharacterMaximumLength(value);
        return this;
    }

    public ElementType withNumericPrecision(Integer value) {
        this.setNumericPrecision(value);
        return this;
    }

    public ElementType withNumericScale(Integer value) {
        this.setNumericScale(value);
        return this;
    }

    public ElementType withUdtCatalog(String value) {
        this.setUdtCatalog(value);
        return this;
    }

    public ElementType withUdtSchema(String value) {
        this.setUdtSchema(value);
        return this;
    }

    public ElementType withUdtName(String value) {
        this.setUdtName(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("object_catalog", this.objectCatalog);
        builder.append("object_schema", this.objectSchema);
        builder.append("object_name", this.objectName);
        builder.append("object_type", this.objectType);
        builder.append("data_type", this.dataType);
        builder.append("character_maximum_length", this.characterMaximumLength);
        builder.append("numeric_precision", this.numericPrecision);
        builder.append("numeric_scale", this.numericScale);
        builder.append("udt_catalog", this.udtCatalog);
        builder.append("udt_schema", this.udtSchema);
        builder.append("udt_name", this.udtName);
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
        ElementType other = (ElementType)that;
        if (this.objectCatalog == null ? other.objectCatalog != null : !this.objectCatalog.equals(other.objectCatalog)) {
            return false;
        }
        if (this.objectSchema == null ? other.objectSchema != null : !this.objectSchema.equals(other.objectSchema)) {
            return false;
        }
        if (this.objectName == null ? other.objectName != null : !this.objectName.equals(other.objectName)) {
            return false;
        }
        if (this.objectType == null ? other.objectType != null : !this.objectType.equals(other.objectType)) {
            return false;
        }
        if (this.dataType == null ? other.dataType != null : !this.dataType.equals(other.dataType)) {
            return false;
        }
        if (this.characterMaximumLength == null ? other.characterMaximumLength != null : !this.characterMaximumLength.equals(other.characterMaximumLength)) {
            return false;
        }
        if (this.numericPrecision == null ? other.numericPrecision != null : !this.numericPrecision.equals(other.numericPrecision)) {
            return false;
        }
        if (this.numericScale == null ? other.numericScale != null : !this.numericScale.equals(other.numericScale)) {
            return false;
        }
        if (this.udtCatalog == null ? other.udtCatalog != null : !this.udtCatalog.equals(other.udtCatalog)) {
            return false;
        }
        if (this.udtSchema == null ? other.udtSchema != null : !this.udtSchema.equals(other.udtSchema)) {
            return false;
        }
        return !(this.udtName == null ? other.udtName != null : !this.udtName.equals(other.udtName));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.objectCatalog == null ? 0 : this.objectCatalog.hashCode());
        result = 31 * result + (this.objectSchema == null ? 0 : this.objectSchema.hashCode());
        result = 31 * result + (this.objectName == null ? 0 : this.objectName.hashCode());
        result = 31 * result + (this.objectType == null ? 0 : this.objectType.hashCode());
        result = 31 * result + (this.dataType == null ? 0 : this.dataType.hashCode());
        result = 31 * result + (this.characterMaximumLength == null ? 0 : this.characterMaximumLength.hashCode());
        result = 31 * result + (this.numericPrecision == null ? 0 : this.numericPrecision.hashCode());
        result = 31 * result + (this.numericScale == null ? 0 : this.numericScale.hashCode());
        result = 31 * result + (this.udtCatalog == null ? 0 : this.udtCatalog.hashCode());
        result = 31 * result + (this.udtSchema == null ? 0 : this.udtSchema.hashCode());
        result = 31 * result + (this.udtName == null ? 0 : this.udtName.hashCode());
        return result;
    }
}

