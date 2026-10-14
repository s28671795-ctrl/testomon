/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.CertificateStatus;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificateStatus"})
@XmlRootElement(name="SetCertificatesStatus")
public class SetCertificatesStatus {
    @XmlElement(name="CertificateStatus")
    protected List<CertificateStatus> certificateStatus;

    public List<CertificateStatus> getCertificateStatus() {
        if (this.certificateStatus == null) {
            this.certificateStatus = new ArrayList<CertificateStatus>();
        }
        return this.certificateStatus;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

