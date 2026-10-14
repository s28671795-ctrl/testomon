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
import org.jooq.conf.MappedCatalog;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.SettingsBase;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="RenderMapping", propOrder={})
public class RenderMapping
extends SettingsBase
implements Serializable,
Cloneable,
XMLAppendable {
    private static final long serialVersionUID = 31800L;
    protected String defaultCatalog;
    protected String defaultSchema;
    @XmlElementWrapper(name="catalogs")
    @XmlElement(name="catalog")
    protected List<MappedCatalog> catalogs;
    @XmlElementWrapper(name="schemata")
    @XmlElement(name="schema")
    protected List<MappedSchema> schemata;

    public String getDefaultCatalog() {
        return this.defaultCatalog;
    }

    public void setDefaultCatalog(String value) {
        this.defaultCatalog = value;
    }

    public String getDefaultSchema() {
        return this.defaultSchema;
    }

    public void setDefaultSchema(String value) {
        this.defaultSchema = value;
    }

    public List<MappedCatalog> getCatalogs() {
        if (this.catalogs == null) {
            this.catalogs = new ArrayList<MappedCatalog>();
        }
        return this.catalogs;
    }

    public void setCatalogs(List<MappedCatalog> catalogs) {
        this.catalogs = catalogs;
    }

    public List<MappedSchema> getSchemata() {
        if (this.schemata == null) {
            this.schemata = new ArrayList<MappedSchema>();
        }
        return this.schemata;
    }

    public void setSchemata(List<MappedSchema> schemata) {
        this.schemata = schemata;
    }

    public RenderMapping withDefaultCatalog(String value) {
        this.setDefaultCatalog(value);
        return this;
    }

    public RenderMapping withDefaultSchema(String value) {
        this.setDefaultSchema(value);
        return this;
    }

    public RenderMapping withCatalogs(MappedCatalog ... values2) {
        if (values2 != null) {
            for (MappedCatalog value : values2) {
                this.getCatalogs().add(value);
            }
        }
        return this;
    }

    public RenderMapping withCatalogs(Collection<MappedCatalog> values2) {
        if (values2 != null) {
            this.getCatalogs().addAll(values2);
        }
        return this;
    }

    public RenderMapping withCatalogs(List<MappedCatalog> catalogs) {
        this.setCatalogs(catalogs);
        return this;
    }

    public RenderMapping withSchemata(MappedSchema ... values2) {
        if (values2 != null) {
            for (MappedSchema value : values2) {
                this.getSchemata().add(value);
            }
        }
        return this;
    }

    public RenderMapping withSchemata(Collection<MappedSchema> values2) {
        if (values2 != null) {
            this.getSchemata().addAll(values2);
        }
        return this;
    }

    public RenderMapping withSchemata(List<MappedSchema> schemata) {
        this.setSchemata(schemata);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("defaultCatalog", this.defaultCatalog);
        builder.append("defaultSchema", this.defaultSchema);
        builder.append("catalogs", "catalog", this.catalogs);
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
        RenderMapping other = (RenderMapping)that;
        if (this.defaultCatalog == null ? other.defaultCatalog != null : !this.defaultCatalog.equals(other.defaultCatalog)) {
            return false;
        }
        if (this.defaultSchema == null ? other.defaultSchema != null : !this.defaultSchema.equals(other.defaultSchema)) {
            return false;
        }
        if (this.catalogs == null ? other.catalogs != null : !this.catalogs.equals(other.catalogs)) {
            return false;
        }
        return !(this.schemata == null ? other.schemata != null : !this.schemata.equals(other.schemata));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.defaultCatalog == null ? 0 : this.defaultCatalog.hashCode());
        result = 31 * result + (this.defaultSchema == null ? 0 : this.defaultSchema.hashCode());
        result = 31 * result + (this.catalogs == null ? 0 : this.catalogs.hashCode());
        result = 31 * result + (this.schemata == null ? 0 : this.schemata.hashCode());
        return result;
    }
}

