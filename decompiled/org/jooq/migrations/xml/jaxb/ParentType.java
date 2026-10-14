/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.migrations.xml.jaxb;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ParentType", propOrder={})
public class ParentType
implements Serializable,
XMLAppendable {
    private static final long serialVersionUID = 31500L;
    @XmlElement(required=true)
    protected String id;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public ParentType withId(String value) {
        this.setId(value);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("id", this.id);
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
        ParentType other = (ParentType)that;
        return !(this.id == null ? other.id != null : !this.id.equals(other.id));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        return result;
    }
}

