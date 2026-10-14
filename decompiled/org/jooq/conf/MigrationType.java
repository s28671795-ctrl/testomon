/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlElementWrapper
 *  jakarta.xml.bind.annotation.XmlType
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jooq.conf.MigrationSchema;
import org.jooq.conf.SettingsBase;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="MigrationType", propOrder={})
public class MigrationType
extends SettingsBase
implements Serializable,
Cloneable,
XMLAppendable {
    private static final long serialVersionUID = 31500L;
    @XmlElementWrapper(name="schemata")
    @XmlElement(name="schema")
    protected List<MigrationSchema> schemata;

    public List<MigrationSchema> getSchemata() {
        if (this.schemata == null) {
            this.schemata = new ArrayList<MigrationSchema>();
        }
        return this.schemata;
    }

    public void setSchemata(List<MigrationSchema> schemata) {
        this.schemata = schemata;
    }

    public MigrationType withSchemata(MigrationSchema ... values2) {
        if (values2 != null) {
            for (MigrationSchema value : values2) {
                this.getSchemata().add(value);
            }
        }
        return this;
    }

    public MigrationType withSchemata(Collection<MigrationSchema> values2) {
        if (values2 != null) {
            this.getSchemata().addAll(values2);
        }
        return this;
    }

    public MigrationType withSchemata(List<MigrationSchema> schemata) {
        this.setSchemata(schemata);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("schemata", "schema", this.schemata);
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
        MigrationType other = (MigrationType)that;
        return !(this.schemata == null ? other.schemata != null : !this.schemata.equals(other.schemata));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.schemata == null ? 0 : this.schemata.hashCode());
        return result;
    }
}

