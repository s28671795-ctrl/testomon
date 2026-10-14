/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.accesscontrol.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.accesscontrol.wsdl.AccessPointState;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"accessPointState"})
@XmlRootElement(name="GetAccessPointStateResponse")
public class GetAccessPointStateResponse {
    @XmlElement(name="AccessPointState", required=true)
    protected AccessPointState accessPointState;

    public AccessPointState getAccessPointState() {
        return this.accessPointState;
    }

    public void setAccessPointState(AccessPointState value) {
        this.accessPointState = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

