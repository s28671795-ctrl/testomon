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
import org.jooq.util.xml.jaxb.RoutineType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Routine", propOrder={})
public class Routine
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
    @XmlElement(name="specific_name")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String specificName;
    @XmlElement(name="routine_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String routineCatalog;
    @XmlElement(name="routine_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String routineSchema;
    @XmlElement(name="routine_package")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String routinePackage;
    @XmlElement(name="routine_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String routineName;
    @XmlElement(name="routine_type", required=true)
    @XmlSchemaType(name="string")
    protected RoutineType routineType;
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

    public String getRoutineCatalog() {
        return this.routineCatalog;
    }

    public void setRoutineCatalog(String value) {
        this.routineCatalog = value;
    }

    public String getRoutineSchema() {
        return this.routineSchema;
    }

    public void setRoutineSchema(String value) {
        this.routineSchema = value;
    }

    public String getRoutinePackage() {
        return this.routinePackage;
    }

    public void setRoutinePackage(String value) {
        this.routinePackage = value;
    }

    public String getRoutineName() {
        return this.routineName;
    }

    public void setRoutineName(String value) {
        this.routineName = value;
    }

    public RoutineType getRoutineType() {
        return this.routineType;
    }

    public void setRoutineType(RoutineType value) {
        this.routineType = value;
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

    public String getComment() {
        return this.comment;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    public Routine withSpecificCatalog(String value) {
        this.setSpecificCatalog(value);
        return this;
    }

    public Routine withSpecificSchema(String value) {
        this.setSpecificSchema(value);
        return this;
    }

    public Routine withSpecificPackage(String value) {
        this.setSpecificPackage(value);
        return this;
    }

    public Routine withSpecificName(String value) {
        this.setSpecificName(value);
        return this;
    }

    public Routine withRoutineCatalog(String value) {
        this.setRoutineCatalog(value);
        return this;
    }

    public Routine withRoutineSchema(String value) {
        this.setRoutineSchema(value);
        return this;
    }

    public Routine withRoutinePackage(String value) {
        this.setRoutinePackage(value);
        return this;
    }

    public Routine withRoutineName(String value) {
        this.setRoutineName(value);
        return this;
    }

    public Routine withRoutineType(RoutineType value) {
        this.setRoutineType(value);
        return this;
    }

    public Routine withDataType(String value) {
        this.setDataType(value);
        return this;
    }

    public Routine withCharacterMaximumLength(Integer value) {
        this.setCharacterMaximumLength(value);
        return this;
    }

    public Routine withNumericPrecision(Integer value) {
        this.setNumericPrecision(value);
        return this;
    }

    public Routine withNumericScale(Integer value) {
        this.setNumericScale(value);
        return this;
    }

    public Routine withUdtCatalog(String value) {
        this.setUdtCatalog(value);
        return this;
    }

    public Routine withUdtSchema(String value) {
        this.setUdtSchema(value);
        return this;
    }

    public Routine withUdtName(String value) {
        this.setUdtName(value);
        return this;
    }

    public Routine withComment(String value) {
        this.setComment(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("specific_catalog", this.specificCatalog);
        builder.append("specific_schema", this.specificSchema);
        builder.append("specific_package", this.specificPackage);
        builder.append("specific_name", this.specificName);
        builder.append("routine_catalog", this.routineCatalog);
        builder.append("routine_schema", this.routineSchema);
        builder.append("routine_package", this.routinePackage);
        builder.append("routine_name", this.routineName);
        builder.append("routine_type", (Object)this.routineType);
        builder.append("data_type", this.dataType);
        builder.append("character_maximum_length", this.characterMaximumLength);
        builder.append("numeric_precision", this.numericPrecision);
        builder.append("numeric_scale", this.numericScale);
        builder.append("udt_catalog", this.udtCatalog);
        builder.append("udt_schema", this.udtSchema);
        builder.append("udt_name", this.udtName);
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
        Routine other = (Routine)that;
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
        if (this.routineCatalog == null ? other.routineCatalog != null : !this.routineCatalog.equals(other.routineCatalog)) {
            return false;
        }
        if (this.routineSchema == null ? other.routineSchema != null : !this.routineSchema.equals(other.routineSchema)) {
            return false;
        }
        if (this.routinePackage == null ? other.routinePackage != null : !this.routinePackage.equals(other.routinePackage)) {
            return false;
        }
        if (this.routineName == null ? other.routineName != null : !this.routineName.equals(other.routineName)) {
            return false;
        }
        if (this.routineType == null ? other.routineType != null : !this.routineType.equals((Object)other.routineType)) {
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
        return !(this.comment == null ? other.comment != null : !this.comment.equals(other.comment));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.specificCatalog == null ? 0 : this.specificCatalog.hashCode());
        result = 31 * result + (this.specificSchema == null ? 0 : this.specificSchema.hashCode());
        result = 31 * result + (this.specificPackage == null ? 0 : this.specificPackage.hashCode());
        result = 31 * result + (this.specificName == null ? 0 : this.specificName.hashCode());
        result = 31 * result + (this.routineCatalog == null ? 0 : this.routineCatalog.hashCode());
        result = 31 * result + (this.routineSchema == null ? 0 : this.routineSchema.hashCode());
        result = 31 * result + (this.routinePackage == null ? 0 : this.routinePackage.hashCode());
        result = 31 * result + (this.routineName == null ? 0 : this.routineName.hashCode());
        result = 31 * result + (this.routineType == null ? 0 : this.routineType.hashCode());
        result = 31 * result + (this.dataType == null ? 0 : this.dataType.hashCode());
        result = 31 * result + (this.characterMaximumLength == null ? 0 : this.characterMaximumLength.hashCode());
        result = 31 * result + (this.numericPrecision == null ? 0 : this.numericPrecision.hashCode());
        result = 31 * result + (this.numericScale == null ? 0 : this.numericScale.hashCode());
        result = 31 * result + (this.udtCatalog == null ? 0 : this.udtCatalog.hashCode());
        result = 31 * result + (this.udtSchema == null ? 0 : this.udtSchema.hashCode());
        result = 31 * result + (this.udtName == null ? 0 : this.udtName.hashCode());
        result = 31 * result + (this.comment == null ? 0 : this.comment.hashCode());
        return result;
    }
}

