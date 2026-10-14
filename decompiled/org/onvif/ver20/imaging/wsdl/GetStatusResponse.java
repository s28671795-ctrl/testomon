/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver20.imaging.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.ImagingStatus20;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"status"})
@XmlRootElement(name="GetStatusResponse")
public class GetStatusResponse {
    @XmlElement(name="Status", required=true)
    protected ImagingStatus20 status;

    public ImagingStatus20 getStatus() {
        return this.status;
    }

    public void setStatus(ImagingStatus20 value) {
        this.status = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

