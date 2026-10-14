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
@XmlType(name="Column", propOrder={})
public class Column
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
    @XmlElement(name="column_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String columnName;
    @XmlElement(name="data_type", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String dataType;
    @XmlElement(name="character_maximum_length")
    protected Integer characterMaximumLength;
    @XmlElement(name="numeric_precision")
    protected Integer numericPrecision;
    @XmlElement(name="numeric_scale")
    protected Integer numericScale;
    @XmlElement(name="domain_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String domainCatalog;
    @XmlElement(name="domain_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String domainSchema;
    @XmlElement(name="domain_name")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String domainName;
    @XmlElement(name="udt_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String udtCatalog;
    @XmlElement(name="udt_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String udtSchema;
    @XmlElement(name="udt_name")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String udtName;
    @XmlElement(name="ordinal_position")
    protected Integer ordinalPosition;
    @XmlElement(name="identity_generation")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String identityGeneration;
    @XmlElement(name="is_nullable")
    protected Boolean isNullable;
    @XmlElement(name="column_default")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String columnDefault;
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String comment;
    protected Boolean readonly;
    @XmlElement(name="is_generated")
    protected Boolean isGenerated;
    @XmlElement(name="generation_expression")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String generationExpression;
    @XmlElement(name="generation_option")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String generationOption;

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

    public Integer getOrdinalPosition() {
        return this.ordinalPosition;
    }

    public void setOrdinalPosition(Integer value) {
        this.ordinalPosition = value;
    }

    public String getIdentityGeneration() {
        return this.identityGeneration;
    }

    public void setIdentityGeneration(String value) {
        this.identityGeneration = value;
    }

    public Boolean isIsNullable() {
        return this.isNullable;
    }

    public void setIsNullable(Boolean value) {
        this.isNullable = value;
    }

    public String getColumnDefault() {
        return this.columnDefault;
    }

    public void setColumnDefault(String value) {
        this.columnDefault = value;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String value) {
        this.comment = value;
    }

    public Boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(Boolean value) {
        this.readonly = value;
    }

    public Boolean isIsGenerated() {
        return this.isGenerated;
    }

    public void setIsGenerated(Boolean value) {
        this.isGenerated = value;
    }

    public String getGenerationExpression() {
        return this.generationExpression;
    }

    public void setGenerationExpression(String value) {
        this.generationExpression = value;
    }

    public String getGenerationOption() {
        return this.generationOption;
    }

    public void setGenerationOption(String value) {
        this.generationOption = value;
    }

    public Column withTableCatalog(String value) {
        this.setTableCatalog(value);
        return this;
    }

    public Column withTableSchema(String value) {
        this.setTableSchema(value);
        return this;
    }

    public Column withTableName(String value) {
        this.setTableName(value);
        return this;
    }

    public Column withColumnName(String value) {
        this.setColumnName(value);
        return this;
    }

    public Column withDataType(String value) {
        this.setDataType(value);
        return this;
    }

    public Column withCharacterMaximumLength(Integer value) {
        this.setCharacterMaximumLength(value);
        return this;
    }

    public Column withNumericPrecision(Integer value) {
        this.setNumericPrecision(value);
        return this;
    }

    public Column withNumericScale(Integer value) {
        this.setNumericScale(value);
        return this;
    }

    public Column withDomainCatalog(String value) {
        this.setDomainCatalog(value);
        return this;
    }

    public Column withDomainSchema(String value) {
        this.setDomainSchema(value);
        return this;
    }

    public Column withDomainName(String value) {
        this.setDomainName(value);
        return this;
    }

    public Column withUdtCatalog(String value) {
        this.setUdtCatalog(value);
        return this;
    }

    public Column withUdtSchema(String value) {
        this.setUdtSchema(value);
        return this;
    }

    public Column withUdtName(String value) {
        this.setUdtName(value);
        return this;
    }

    public Column withOrdinalPosition(Integer value) {
        this.setOrdinalPosition(value);
        return this;
    }

    public Column withIdentityGeneration(String value) {
        this.setIdentityGeneration(value);
        return this;
    }

    public Column withIsNullable(Boolean value) {
        this.setIsNullable(value);
        return this;
    }

    public Column withColumnDefault(String value) {
        this.setColumnDefault(value);
        return this;
    }

    public Column withComment(String value) {
        this.setComment(value);
        return this;
    }

    public Column withReadonly(Boolean value) {
        this.setReadonly(value);
        return this;
    }

    public Column withIsGenerated(Boolean value) {
        this.setIsGenerated(value);
        return this;
    }

    public Column withGenerationExpression(String value) {
        this.setGenerationExpression(value);
        return this;
    }

    public Column withGenerationOption(String value) {
        this.setGenerationOption(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("table_catalog", this.tableCatalog);
        builder.append("table_schema", this.tableSchema);
        builder.append("table_name", this.tableName);
        builder.append("column_name", this.columnName);
        builder.append("data_type", this.dataType);
        builder.append("character_maximum_length", this.characterMaximumLength);
        builder.append("numeric_precision", this.numericPrecision);
        builder.append("numeric_scale", this.numericScale);
        builder.append("domain_catalog", this.domainCatalog);
        builder.append("domain_schema", this.domainSchema);
        builder.append("domain_name", this.domainName);
        builder.append("udt_catalog", this.udtCatalog);
        builder.append("udt_schema", this.udtSchema);
        builder.append("udt_name", this.udtName);
        builder.append("ordinal_position", this.ordinalPosition);
        builder.append("identity_generation", this.identityGeneration);
        builder.append("is_nullable", this.isNullable);
        builder.append("column_default", this.columnDefault);
        builder.append("comment", this.comment);
        builder.append("readonly", this.readonly);
        builder.append("is_generated", this.isGenerated);
        builder.append("generation_expression", this.generationExpression);
        builder.append("generation_option", this.generationOption);
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
        Column other = (Column)that;
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
        if (this.domainCatalog == null ? other.domainCatalog != null : !this.domainCatalog.equals(other.domainCatalog)) {
            return false;
        }
        if (this.domainSchema == null ? other.domainSchema != null : !this.domainSchema.equals(other.domainSchema)) {
            return false;
        }
        if (this.domainName == null ? other.domainName != null : !this.domainName.equals(other.domainName)) {
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
        if (this.ordinalPosition == null ? other.ordinalPosition != null : !this.ordinalPosition.equals(other.ordinalPosition)) {
            return false;
        }
        if (this.identityGeneration == null ? other.identityGeneration != null : !this.identityGeneration.equals(other.identityGeneration)) {
            return false;
        }
        if (this.isNullable == null ? other.isNullable != null : !this.isNullable.equals(other.isNullable)) {
            return false;
        }
        if (this.columnDefault == null ? other.columnDefault != null : !this.columnDefault.equals(other.columnDefault)) {
            return false;
        }
        if (this.comment == null ? other.comment != null : !this.comment.equals(other.comment)) {
            return false;
        }
        if (this.readonly == null ? other.readonly != null : !this.readonly.equals(other.readonly)) {
            return false;
        }
        if (this.isGenerated == null ? other.isGenerated != null : !this.isGenerated.equals(other.isGenerated)) {
            return false;
        }
        if (this.generationExpression == null ? other.generationExpression != null : !this.generationExpression.equals(other.generationExpression)) {
            return false;
        }
        return !(this.generationOption == null ? other.generationOption != null : !this.generationOption.equals(other.generationOption));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.tableCatalog == null ? 0 : this.tableCatalog.hashCode());
        result = 31 * result + (this.tableSchema == null ? 0 : this.tableSchema.hashCode());
        result = 31 * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        result = 31 * result + (this.columnName == null ? 0 : this.columnName.hashCode());
        result = 31 * result + (this.dataType == null ? 0 : this.dataType.hashCode());
        result = 31 * result + (this.characterMaximumLength == null ? 0 : this.characterMaximumLength.hashCode());
        result = 31 * result + (this.numericPrecision == null ? 0 : this.numericPrecision.hashCode());
        result = 31 * result + (this.numericScale == null ? 0 : this.numericScale.hashCode());
        result = 31 * result + (this.domainCatalog == null ? 0 : this.domainCatalog.hashCode());
        result = 31 * result + (this.domainSchema == null ? 0 : this.domainSchema.hashCode());
        result = 31 * result + (this.domainName == null ? 0 : this.domainName.hashCode());
        result = 31 * result + (this.udtCatalog == null ? 0 : this.udtCatalog.hashCode());
        result = 31 * result + (this.udtSchema == null ? 0 : this.udtSchema.hashCode());
        result = 31 * result + (this.udtName == null ? 0 : this.udtName.hashCode());
        result = 31 * result + (this.ordinalPosition == null ? 0 : this.ordinalPosition.hashCode());
        result = 31 * result + (this.identityGeneration == null ? 0 : this.identityGeneration.hashCode());
        result = 31 * result + (this.isNullable == null ? 0 : this.isNullable.hashCode());
        result = 31 * result + (this.columnDefault == null ? 0 : this.columnDefault.hashCode());
        result = 31 * result + (this.comment == null ? 0 : this.comment.hashCode());
        result = 31 * result + (this.readonly == null ? 0 : this.readonly.hashCode());
        result = 31 * result + (this.isGenerated == null ? 0 : this.isGenerated.hashCode());
        result = 31 * result + (this.generationExpression == null ? 0 : this.generationExpression.hashCode());
        result = 31 * result + (this.generationOption == null ? 0 : this.generationOption.hashCode());
        return result;
    }
}

