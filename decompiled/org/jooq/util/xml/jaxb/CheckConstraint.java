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
@XmlType(name="CheckConstraint", propOrder={})
public class CheckConstraint
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
    @XmlElement(name="check_clause", required=true)
    @XmlJavaTypeAdapter(value=StringAdapter.class)
    protected String checkClause;

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

    public String getCheckClause() {
        return this.checkClause;
    }

    public void setCheckClause(String value) {
        this.checkClause = value;
    }

    public CheckConstraint withConstraintCatalog(String value) {
        this.setConstraintCatalog(value);
        return this;
    }

    public CheckConstraint withConstraintSchema(String value) {
        this.setConstraintSchema(value);
        return this;
    }

    public CheckConstraint withConstraintName(String value) {
        this.setConstraintName(value);
        return this;
    }

    public CheckConstraint withCheckClause(String value) {
        this.setCheckClause(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("constraint_catalog", this.constraintCatalog);
        builder.append("constraint_schema", this.constraintSchema);
        builder.append("constraint_name", this.constraintName);
        builder.append("check_clause", this.checkClause);
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
        CheckConstraint other = (CheckConstraint)that;
        if (this.constraintCatalog == null ? other.constraintCatalog != null : !this.constraintCatalog.equals(other.constraintCatalog)) {
            return false;
        }
        if (this.constraintSchema == null ? other.constraintSchema != null : !this.constraintSchema.equals(other.constraintSchema)) {
            return false;
        }
        if (this.constraintName == null ? other.constraintName != null : !this.constraintName.equals(other.constraintName)) {
            return false;
        }
        return !(this.checkClause == null ? other.checkClause != null : !this.checkClause.equals(other.checkClause));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.constraintCatalog == null ? 0 : this.constraintCatalog.hashCode());
        result = 31 * result + (this.constraintSchema == null ? 0 : this.constraintSchema.hashCode());
        result = 31 * result + (this.constraintName == null ? 0 : this.constraintName.hashCode());
        result = 31 * result + (this.checkClause == null ? 0 : this.checkClause.hashCode());
        return result;
    }
}

