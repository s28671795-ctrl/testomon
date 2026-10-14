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
import org.onvif.ver10.schema.Certificate;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"nvtCertificate"})
@XmlRootElement(name="CreateCertificateResponse")
public class CreateCertificateResponse {
    @XmlElement(name="NvtCertificate", required=true)
    protected Certificate nvtCertificate;

    public Certificate getNvtCertificate() {
        return this.nvtCertificate;
    }

    public void setNvtCertificate(Certificate value) {
        this.nvtCertificate = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

