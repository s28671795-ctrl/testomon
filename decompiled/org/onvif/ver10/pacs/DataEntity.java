/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.pacs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.doorcontrol.wsdl.DoorInfoBase;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="DataEntity")
@XmlSeeAlso(value={DoorInfoBase.class})
public class DataEntity {
    @XmlAttribute(name="token", required=true)
    protected String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

