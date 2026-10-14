/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accessrules.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accessrules.wsdl.AccessProfile;
import org.onvif.ver10.pacs.DataEntity;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AccessProfileInfo", propOrder={"name", "description"})
@XmlSeeAlso(value={AccessProfile.class})
public class AccessProfileInfo
extends DataEntity {
    @XmlElement(name="Name", required=true)
    protected String name;
    @XmlElement(name="Description")
    protected String description;

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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

