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
@XmlType(name="Domain", propOrder={})
public class Domain
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="domain_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String domainCatalog;
    @XmlElement(name="domain_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String domainSchema;
    @XmlElement(name="domain_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String domainName;
    @XmlElement(name="data_type", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String dataType;
    @XmlElement(name="character_maximum_length")
    protected Integer characterMaximumLength;
    @XmlElement(name="numeric_precision")
    protected Integer numericPrecision;
    @XmlElement(name="numeric_scale")
    protected Integer numericScale;
    @XmlElement(name="domain_default")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String domainDefault;

    public String getDomainCatalog() {
        return this.domainCatalog;
    }

    public void setDomainCatalog(String value) {
        this.domainCatalog = value;
    }

    public String getDomainSchema() {
        return this.domainSchema;
    }

    public void setDomainSchema(String value) {
        this.domainSchema = value;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public void setDomainName(String value) {
        this.domainName = value;
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

    public String getDomainDefault() {
        return this.domainDefault;
    }

    public void setDomainDefault(String value) {
        this.domainDefault = value;
    }

    public Domain withDomainCatalog(String value) {
        this.setDomainCatalog(value);
        return this;
    }

    public Domain withDomainSchema(String value) {
        this.setDomainSchema(value);
        return this;
    }

    public Domain withDomainName(String value) {
        this.setDomainName(value);
        return this;
    }

    public Domain withDataType(String value) {
        this.setDataType(value);
        return this;
    }

    public Domain withCharacterMaximumLength(Integer value) {
        this.setCharacterMaximumLength(value);
        return this;
    }

    public Domain withNumericPrecision(Integer value) {
        this.setNumericPrecision(value);
        return this;
    }

    public Domain withNumericScale(Integer value) {
        this.setNumericScale(value);
        return this;
    }

    public Domain withDomainDefault(String value) {
        this.setDomainDefault(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("domain_catalog", this.domainCatalog);
        builder.append("domain_schema", this.domainSchema);
        builder.append("domain_name", this.domainName);
        builder.append("data_type", this.dataType);
        builder.append("character_maximum_length", this.characterMaximumLength);
        builder.append("numeric_precision", this.numericPrecision);
        builder.append("numeric_scale", this.numericScale);
        builder.append("domain_default", this.domainDefault);
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
        Domain other = (Domain)that;
        if (this.domainCatalog == null ? other.domainCatalog != null : !this.domainCatalog.equals(other.domainCatalog)) {
            return false;
        }
        if (this.domainSchema == null ? other.domainSchema != null : !this.domainSchema.equals(other.domainSchema)) {
            return false;
        }
        if (this.domainName == null ? other.domainName != null : !this.domainName.equals(other.domainName)) {
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
        return !(this.domainDefault == null ? other.domainDefault != null : !this.domainDefault.equals(other.domainDefault));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.domainCatalog == null ? 0 : this.domainCatalog.hashCode());
        result = 31 * result + (this.domainSchema == null ? 0 : this.domainSchema.hashCode());
        result = 31 * result + (this.domainName == null ? 0 : this.domainName.hashCode());
        result = 31 * result + (this.dataType == null ? 0 : this.dataType.hashCode());
        result = 31 * result + (this.characterMaximumLength == null ? 0 : this.characterMaximumLength.hashCode());
        result = 31 * result + (this.numericPrecision == null ? 0 : this.numericPrecision.hashCode());
        result = 31 * result + (this.numericScale == null ? 0 : this.numericScale.hashCode());
        result = 31 * result + (this.domainDefault == null ? 0 : this.domainDefault.hashCode());
        return result;
    }
}

