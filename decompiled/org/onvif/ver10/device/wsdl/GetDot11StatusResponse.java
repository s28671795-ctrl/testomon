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
import org.onvif.ver10.schema.Dot11Status;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"status"})
@XmlRootElement(name="GetDot11StatusResponse")
public class GetDot11StatusResponse {
    @XmlElement(name="Status", required=true)
    protected Dot11Status status;

    public Dot11Status getStatus() {
        return this.status;
    }

    public void setStatus(Dot11Status value) {
        this.status = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

