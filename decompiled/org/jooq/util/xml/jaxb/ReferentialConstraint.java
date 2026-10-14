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
@XmlType(name="ReferentialConstraint", propOrder={})
public class ReferentialConstraint
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31600L;
    @XmlElement(name="constraint_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String constraintCatalog;
    @XmlElement(name="constraint_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String constraintSchema;
    @XmlElement(name="constraint_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String constraintName;
    @XmlElement(name="unique_constraint_catalog")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String uniqueConstraintCatalog;
    @XmlElement(name="unique_constraint_schema")
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String uniqueConstraintSchema;
    @XmlElement(name="unique_constraint_name", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String uniqueConstraintName;

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

    public String getUniqueConstraintCatalog() {
        return this.uniqueConstraintCatalog;
    }

    public void setUniqueConstraintCatalog(String value) {
        this.uniqueConstraintCatalog = value;
    }

    public String getUniqueConstraintSchema() {
        return this.uniqueConstraintSchema;
    }

    public void setUniqueConstraintSchema(String value) {
        this.uniqueConstraintSchema = value;
    }

    public String getUniqueConstraintName() {
        return this.uniqueConstraintName;
    }

    public void setUniqueConstraintName(String value) {
        this.uniqueConstraintName = value;
    }

    public ReferentialConstraint withConstraintCatalog(String value) {
        this.setConstraintCatalog(value);
        return this;
    }

    public ReferentialConstraint withConstraintSchema(String value) {
        this.setConstraintSchema(value);
        return this;
    }

    public ReferentialConstraint withConstraintName(String value) {
        this.setConstraintName(value);
        return this;
    }

    public ReferentialConstraint withUniqueConstraintCatalog(String value) {
        this.setUniqueConstraintCatalog(value);
        return this;
    }

    public ReferentialConstraint withUniqueConstraintSchema(String value) {
        this.setUniqueConstraintSchema(value);
        return this;
    }

    public ReferentialConstraint withUniqueConstraintName(String value) {
        this.setUniqueConstraintName(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("constraint_catalog", this.constraintCatalog);
        builder.append("constraint_schema", this.constraintSchema);
        builder.append("constraint_name", this.constraintName);
        builder.append("unique_constraint_catalog", this.uniqueConstraintCatalog);
        builder.append("unique_constraint_schema", this.uniqueConstraintSchema);
        builder.append("unique_constraint_name", this.uniqueConstraintName);
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
        ReferentialConstraint other = (ReferentialConstraint)that;
        if (this.constraintCatalog == null ? other.constraintCatalog != null : !this.constraintCatalog.equals(other.constraintCatalog)) {
            return false;
        }
        if (this.constraintSchema == null ? other.constraintSchema != null : !this.constraintSchema.equals(other.constraintSchema)) {
            return false;
        }
        if (this.constraintName == null ? other.constraintName != null : !this.constraintName.equals(other.constraintName)) {
            return false;
        }
        if (this.uniqueConstraintCatalog == null ? other.uniqueConstraintCatalog != null : !this.uniqueConstraintCatalog.equals(other.uniqueConstraintCatalog)) {
            return false;
        }
        if (this.uniqueConstraintSchema == null ? other.uniqueConstraintSchema != null : !this.uniqueConstraintSchema.equals(other.uniqueConstraintSchema)) {
            return false;
        }
        return !(this.uniqueConstraintName == null ? other.uniqueConstraintName != null : !this.uniqueConstraintName.equals(other.uniqueConstraintName));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.constraintCatalog == null ? 0 : this.constraintCatalog.hashCode());
        result = 31 * result + (this.constraintSchema == null ? 0 : this.constraintSchema.hashCode());
        result = 31 * result + (this.constraintName == null ? 0 : this.constraintName.hashCode());
        result = 31 * result + (this.uniqueConstraintCatalog == null ? 0 : this.uniqueConstraintCatalog.hashCode());
        result = 31 * result + (this.uniqueConstraintSchema == null ? 0 : this.uniqueConstraintSchema.hashCode());
        result = 31 * result + (this.uniqueConstraintName == null ? 0 : this.uniqueConstraintName.hashCode());
        return result;
    }
}

