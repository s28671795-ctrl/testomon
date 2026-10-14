/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"fromDHCP"})
@XmlRootElement(name="SetHostnameFromDHCP")
public class SetHostnameFromDHCP {
    @XmlElement(name="FromDHCP")
    protected boolean fromDHCP;

    public boolean getFromDHCP() {
        return this.fromDHCP;
    }

    public void setFromDHCP(boolean value) {
        this.fromDHCP = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

