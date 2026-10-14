/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointInfo;
import org.onvif.ver10.pacs.DataEntity;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccessPointInfoBase", propOrder={"name", "description", "areaFrom", "areaTo", "entityType", "entity"})
@XmlSeeAlso(value={AccessPointInfo.class})
public class AccessPointInfoBase
extends DataEntity {
    @XmlElement(name="Name", required=true)
    protected String name;
    @XmlElement(name="Description")
    protected String description;
    @XmlElement(name="AreaFrom")
    protected String areaFrom;
    @XmlElement(name="AreaTo")
    protected String areaTo;
    @XmlElement(name="EntityType")
    protected QName entityType;
    @XmlElement(name="Entity", required=true)
    protected String entity;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getAreaFrom() {
        return this.areaFrom;
    }

    public void setAreaFrom(String value) {
        this.areaFrom = value;
    }

    public String getAreaTo() {
        return this.areaTo;
    }

    public void setAreaTo(String value) {
        this.areaTo = value;
    }

    public QName getEntityType() {
        return this.entityType;
    }

    public void setEntityType(QName value) {
        this.entityType = value;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String value) {
        this.entity = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

