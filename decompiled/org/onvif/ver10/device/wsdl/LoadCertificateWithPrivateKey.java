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
import org.onvif.ver10.schema.CertificateWithPrivateKey;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificateWithPrivateKey"})
@XmlRootElement(name="LoadCertificateWithPrivateKey")
public class LoadCertificateWithPrivateKey {
    @XmlElement(name="CertificateWithPrivateKey", required=true)
    protected List<CertificateWithPrivateKey> certificateWithPrivateKey;

    public List<CertificateWithPrivateKey> getCertificateWithPrivateKey() {
        if (this.certificateWithPrivateKey == null) {
            this.certificateWithPrivateKey = new ArrayList<CertificateWithPrivateKey>();
        }
        return this.certificateWithPrivateKey;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

