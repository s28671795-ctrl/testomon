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
import java.math.BigInteger;
import org.jooq.util.jaxb.tools.StringAdapter;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Sequence", propOrder={})
public class Sequence
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="sequence_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String sequenceCatalog;
    @XmlElement(name="sequence_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String sequenceSchema;
    @XmlElement(name="sequence_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String sequenceName;
    @XmlElement(name="data_type", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String dataType;
    @XmlElement(name="character_maximum_length")
    protected Integer characterMaximumLength;
    @XmlElement(name="numeric_precision")
    protected Integer numericPrecision;
    @XmlElement(name="numeric_scale")
    protected Integer numericScale;
    @XmlElement(name="start_value")
    protected BigInteger startValue;
    protected BigInteger increment;
    @XmlElement(name="minimum_value")
    protected BigInteger minimumValue;
    @XmlElement(name="maximum_value")
    protected BigInteger maximumValue;
    @XmlElement(name="cycle_option")
    protected Boolean cycleOption;
    protected BigInteger cache;
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String comment;

    public String getSequenceCatalog() {
        return this.sequenceCatalog;
    }

    public void setSequenceCatalog(String value) {
        this.sequenceCatalog = value;
    }

    public String getSequenceSchema() {
        return this.sequenceSchema;
    }

    public void setSequenceSchema(String value) {
        this.sequenceSchema = value;
    }

    public String getSequenceName() {
        return this.sequenceName;
    }

    public void setSequenceName(String value) {
        this.sequenceName = value;
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

    public BigInteger getStartValue() {
        return this.startValue;
    }

    public void setStartValue(BigInteger value) {
        this.startValue = value;
    }

    public BigInteger getIncrement() {
        return this.increment;
    }

    public void setIncrement(BigInteger value) {
        this.increment = value;
    }

    public BigInteger getMinimumValue() {
        return this.minimumValue;
    }

    public void setMinimumValue(BigInteger value) {
        this.minimumValue = value;
    }

    public BigInteger getMaximumValue() {
        return this.maximumValue;
    }

    public void setMaximumValue(BigInteger value) {
        this.maximumValue = value;
    }

    public Boolean isCycleOption() {
        return this.cycleOption;
    }

    public void setCycleOption(Boolean value) {
        this.cycleOption = value;
    }

    public BigInteger getCache() {
        return this.cache;
    }

    public void setCache(BigInteger value) {
        this.cache = value;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    public Sequence withSequenceCatalog(String value) {
        this.setSequenceCatalog(value);
        return this;
    }

    public Sequence withSequenceSchema(String value) {
        this.setSequenceSchema(value);
        return this;
    }

    public Sequence withSequenceName(String value) {
        this.setSequenceName(value);
        return this;
    }

    public Sequence withDataType(String value) {
        this.setDataType(value);
        return this;
    }

    public Sequence withCharacterMaximumLength(Integer value) {
        this.setCharacterMaximumLength(value);
        return this;
    }

    public Sequence withNumericPrecision(Integer value) {
        this.setNumericPrecision(value);
        return this;
    }

    public Sequence withNumericScale(Integer value) {
        this.setNumericScale(value);
        return this;
    }

    public Sequence withStartValue(BigInteger value) {
        this.setStartValue(value);
        return this;
    }

    public Sequence withIncrement(BigInteger value) {
        this.setIncrement(value);
        return this;
    }

    public Sequence withMinimumValue(BigInteger value) {
        this.setMinimumValue(value);
        return this;
    }

    public Sequence withMaximumValue(BigInteger value) {
        this.setMaximumValue(value);
        return this;
    }

    public Sequence withCycleOption(Boolean value) {
        this.setCycleOption(value);
        return this;
    }

    public Sequence withCache(BigInteger value) {
        this.setCache(value);
        return this;
    }

    public Sequence withComment(String value) {
        this.setComment(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("sequence_catalog", this.sequenceCatalog);
        builder.append("sequence_schema", this.sequenceSchema);
        builder.append("sequence_name", this.sequenceName);
        builder.append("data_type", this.dataType);
        builder.append("character_maximum_length", this.characterMaximumLength);
        builder.append("numeric_precision", this.numericPrecision);
        builder.append("numeric_scale", this.numericScale);
        builder.append("start_value", this.startValue);
        builder.append("increment", this.increment);
        builder.append("minimum_value", this.minimumValue);
        builder.append("maximum_value", this.maximumValue);
        builder.append("cycle_option", this.cycleOption);
        builder.append("cache", this.cache);
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
        Sequence other = (Sequence)that;
        if (this.sequenceCatalog == null ? other.sequenceCatalog != null : !this.sequenceCatalog.equals(other.sequenceCatalog)) {
            return false;
        }
        if (this.sequenceSchema == null ? other.sequenceSchema != null : !this.sequenceSchema.equals(other.sequenceSchema)) {
            return false;
        }
        if (this.sequenceName == null ? other.sequenceName != null : !this.sequenceName.equals(other.sequenceName)) {
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
        if (this.startValue == null ? other.startValue != null : !this.startValue.equals(other.startValue)) {
            return false;
        }
        if (this.increment == null ? other.increment != null : !this.increment.equals(other.increment)) {
            return false;
        }
        if (this.minimumValue == null ? other.minimumValue != null : !this.minimumValue.equals(other.minimumValue)) {
            return false;
        }
        if (this.maximumValue == null ? other.maximumValue != null : !this.maximumValue.equals(other.maximumValue)) {
            return false;
        }
        if (this.cycleOption == null ? other.cycleOption != null : !this.cycleOption.equals(other.cycleOption)) {
            return false;
        }
        if (this.cache == null ? other.cache != null : !this.cache.equals(other.cache)) {
            return false;
        }
        return !(this.comment == null ? other.comment != null : !this.comment.equals(other.comment));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.sequenceCatalog == null ? 0 : this.sequenceCatalog.hashCode());
        result = 31 * result + (this.sequenceSchema == null ? 0 : this.sequenceSchema.hashCode());
        result = 31 * result + (this.sequenceName == null ? 0 : this.sequenceName.hashCode());
        result = 31 * result + (this.dataType == null ? 0 : this.dataType.hashCode());
        result = 31 * result + (this.characterMaximumLength == null ? 0 : this.characterMaximumLength.hashCode());
        result = 31 * result + (this.numericPrecision == null ? 0 : this.numericPrecision.hashCode());
        result = 31 * result + (this.numericScale == null ? 0 : this.numericScale.hashCode());
        result = 31 * result + (this.startValue == null ? 0 : this.startValue.hashCode());
        result = 31 * result + (this.increment == null ? 0 : this.increment.hashCode());
        result = 31 * result + (this.minimumValue == null ? 0 : this.minimumValue.hashCode());
        result = 31 * result + (this.maximumValue == null ? 0 : this.maximumValue.hashCode());
        result = 31 * result + (this.cycleOption == null ? 0 : this.cycleOption.hashCode());
        result = 31 * result + (this.cache == null ? 0 : this.cache.hashCode());
        result = 31 * result + (this.comment == null ? 0 : this.comment.hashCode());
        return result;
    }
}

