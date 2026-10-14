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
import org.jooq.util.xml.jaxb.ParameterMode;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Parameter", propOrder={})
public class Parameter
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="specific_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String specificCatalog;
    @XmlElement(name="specific_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String specificSchema;
    @XmlElement(name="specific_package")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String specificPackage;
    @XmlElement(name="specific_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String specificName;
    @XmlElement(name="ordinal_position")
    protected int ordinalPosition;
    @XmlElement(name="parameter_mode", required=true)
    @XmlSchemaType(name="string")
    protected ParameterMode parameterMode;
    @XmlElement(name="parameter_name")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String parameterName;
    @XmlElement(name="data_type")
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
    @XmlElement(name="parameter_default")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String parameterDefault;
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String comment;

    public String getSpecificCatalog() {
        return this.specificCatalog;
    }

    public void setSpecificCatalog(String value) {
        this.specificCatalog = value;
    }

    public String getSpecificSchema() {
        return this.specificSchema;
    }

    public void setSpecificSchema(String value) {
        this.specificSchema = value;
    }

    public String getSpecificPackage() {
        return this.specificPackage;
    }

    public void setSpecificPackage(String value) {
        this.specificPackage = value;
    }

    public String getSpecificName() {
        return this.specificName;
    }

    public void setSpecificName(String value) {
        this.specificName = value;
    }

    public int getOrdinalPosition() {
        return this.ordinalPosition;
    }

    public void setOrdinalPosition(int value) {
        this.ordinalPosition = value;
    }

    public ParameterMode getParameterMode() {
        return this.parameterMode;
    }

    public void setParameterMode(ParameterMode value) {
        this.parameterMode = value;
    }

    public String getParameterName() {
        return this.parameterName;
    }

    public void setParameterName(String value) {
        this.parameterName = value;
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

    public String getParameterDefault() {
        return this.parameterDefault;
    }

    public void setParameterDefault(String value) {
        this.parameterDefault = value;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    public Parameter withSpecificCatalog(String value) {
        this.setSpecificCatalog(value);
        return this;
    }

    public Parameter withSpecificSchema(String value) {
        this.setSpecificSchema(value);
        return this;
    }

    public Parameter withSpecificPackage(String value) {
        this.setSpecificPackage(value);
        return this;
    }

    public Parameter withSpecificName(String value) {
        this.setSpecificName(value);
        return this;
    }

    public Parameter withOrdinalPosition(int value) {
        this.setOrdinalPosition(value);
        return this;
    }

    public Parameter withParameterMode(ParameterMode value) {
        this.setParameterMode(value);
        return this;
    }

    public Parameter withParameterName(String value) {
        this.setParameterName(value);
        return this;
    }

    public Parameter withDataType(String value) {
        this.setDataType(value);
        return this;
    }

    public Parameter withCharacterMaximumLength(Integer value) {
        this.setCharacterMaximumLength(value);
        return this;
    }

    public Parameter withNumericPrecision(Integer value) {
        this.setNumericPrecision(value);
        return this;
    }

    public Parameter withNumericScale(Integer value) {
        this.setNumericScale(value);
        return this;
    }

    public Parameter withUdtCatalog(String value) {
        this.setUdtCatalog(value);
        return this;
    }

    public Parameter withUdtSchema(String value) {
        this.setUdtSchema(value);
        return this;
    }

    public Parameter withUdtName(String value) {
        this.setUdtName(value);
        return this;
    }

    public Parameter withParameterDefault(String value) {
        this.setParameterDefault(value);
        return this;
    }

    public Parameter withComment(String value) {
        this.setComment(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("specific_catalog", this.specificCatalog);
        builder.append("specific_schema", this.specificSchema);
        builder.append("specific_package", this.specificPackage);
        builder.append("specific_name", this.specificName);
        builder.append("ordinal_position", this.ordinalPosition);
        builder.append("parameter_mode", (Object)this.parameterMode);
        builder.append("parameter_name", this.parameterName);
        builder.append("data_type", this.dataType);
        builder.append("character_maximum_length", this.characterMaximumLength);
        builder.append("numeric_precision", this.numericPrecision);
        builder.append("numeric_scale", this.numericScale);
        builder.append("udt_catalog", this.udtCatalog);
        builder.append("udt_schema", this.udtSchema);
        builder.append("udt_name", this.udtName);
        builder.append("parameter_default", this.parameterDefault);
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
        Parameter other = (Parameter)that;
        if (this.specificCatalog == null ? other.specificCatalog != null : !this.specificCatalog.equals(other.specificCatalog)) {
            return false;
        }
        if (this.specificSchema == null ? other.specificSchema != null : !this.specificSchema.equals(other.specificSchema)) {
            return false;
        }
        if (this.specificPackage == null ? other.specificPackage != null : !this.specificPackage.equals(other.specificPackage)) {
            return false;
        }
        if (this.specificName == null ? other.specificName != null : !this.specificName.equals(other.specificName)) {
            return false;
        }
        if (this.ordinalPosition != other.ordinalPosition) {
            return false;
        }
        if (this.parameterMode == null ? other.parameterMode != null : !this.parameterMode.equals((Object)other.parameterMode)) {
            return false;
        }
        if (this.parameterName == null ? other.parameterName != null : !this.parameterName.equals(other.parameterName)) {
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
        if (this.udtName == null ? other.udtName != null : !this.udtName.equals(other.udtName)) {
            return false;
        }
        if (this.parameterDefault == null ? other.parameterDefault != null : !this.parameterDefault.equals(other.parameterDefault)) {
            return false;
        }
        return !(this.comment == null ? other.comment != null : !this.comment.equals(other.comment));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.specificCatalog == null ? 0 : this.specificCatalog.hashCode());
        result = 31 * result + (this.specificSchema == null ? 0 : this.specificSchema.hashCode());
        result = 31 * result + (this.specificPackage == null ? 0 : this.specificPackage.hashCode());
        result = 31 * result + (this.specificName == null ? 0 : this.specificName.hashCode());
        result = 31 * result + this.ordinalPosition;
        result = 31 * result + (this.parameterMode == null ? 0 : this.parameterMode.hashCode());
        result = 31 * result + (this.parameterName == null ? 0 : this.parameterName.hashCode());
        result = 31 * result + (this.dataType == null ? 0 : this.dataType.hashCode());
        result = 31 * result + (this.characterMaximumLength == null ? 0 : this.characterMaximumLength.hashCode());
        result = 31 * result + (this.numericPrecision == null ? 0 : this.numericPrecision.hashCode());
        result = 31 * result + (this.numericScale == null ? 0 : this.numericScale.hashCode());
        result = 31 * result + (this.udtCatalog == null ? 0 : this.udtCatalog.hashCode());
        result = 31 * result + (this.udtSchema == null ? 0 : this.udtSchema.hashCode());
        result = 31 * result + (this.udtName == null ? 0 : this.udtName.hashCode());
        result = 31 * result + (this.parameterDefault == null ? 0 : this.parameterDefault.hashCode());
        result = 31 * result + (this.comment == null ? 0 : this.comment.hashCode());
        return result;
    }
}

