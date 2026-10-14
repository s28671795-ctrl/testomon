/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.CertificateIDs;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificateIDs", "alias"})
@XmlRootElement(name="CreateCertificationPath")
public class CreateCertificationPath {
    @XmlElement(name="CertificateIDs", required=true)
    protected CertificateIDs certificateIDs;
    @XmlElement(name="Alias")
    protected String alias;

    public CertificateIDs getCertificateIDs() {
        return this.certificateIDs;
    }

    public void setCertificateIDs(CertificateIDs value) {
        this.certificateIDs = value;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

