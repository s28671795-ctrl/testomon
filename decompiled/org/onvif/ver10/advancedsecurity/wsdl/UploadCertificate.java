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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificate", "alias", "keyAlias", "privateKeyRequired"})
@XmlRootElement(name="UploadCertificate")
public class UploadCertificate {
    @XmlElement(name="Certificate", required=true)
    protected byte[] certificate;
    @XmlElement(name="Alias")
    protected String alias;
    @XmlElement(name="KeyAlias")
    protected String keyAlias;
    @XmlElement(name="PrivateKeyRequired", defaultValue="false")
    protected Boolean privateKeyRequired;

    public byte[] getCertificate() {
        return this.certificate;
    }

    public void setCertificate(byte[] value) {
        this.certificate = value;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public String getKeyAlias() {
        return this.keyAlias;
    }

    public void setKeyAlias(String value) {
        this.keyAlias = value;
    }

    public Boolean getPrivateKeyRequired() {
        return this.privateKeyRequired;
    }

    public void setPrivateKeyRequired(Boolean value) {
        this.privateKeyRequired = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

