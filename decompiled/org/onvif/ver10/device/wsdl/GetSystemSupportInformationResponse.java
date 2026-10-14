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
import org.onvif.ver10.schema.SupportInformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"supportInformation"})
@XmlRootElement(name="GetSystemSupportInformationResponse")
public class GetSystemSupportInformationResponse {
    @XmlElement(name="SupportInformation", required=true)
    protected SupportInformation supportInformation;

    public SupportInformation getSupportInformation() {
        return this.supportInformation;
    }

    public void setSupportInformation(SupportInformation value) {
        this.supportInformation = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

