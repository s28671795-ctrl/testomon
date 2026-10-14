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
import org.onvif.ver10.schema.CertificateInformation;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificateInformation"})
@XmlRootElement(name="GetCertificateInformationResponse")
public class GetCertificateInformationResponse {
    @XmlElement(name="CertificateInformation", required=true)
    protected CertificateInformation certificateInformation;

    public CertificateInformation getCertificateInformation() {
        return this.certificateInformation;
    }

    public void setCertificateInformation(CertificateInformation value) {
        this.certificateInformation = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

