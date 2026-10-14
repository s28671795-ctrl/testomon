/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certWithPrivateKey", "certificationPathAlias", "keyAlias", "ignoreAdditionalCertificates", "integrityPassphraseID", "encryptionPassphraseID", "passphrase"})
@XmlRootElement(name="UploadCertificateWithPrivateKeyInPKCS12")
public class UploadCertificateWithPrivateKeyInPKCS12 {
    @XmlElement(name="CertWithPrivateKey", required=true)
    protected byte[] certWithPrivateKey;
    @XmlElement(name="CertificationPathAlias")
    protected String certificationPathAlias;
    @XmlElement(name="KeyAlias")
    protected String keyAlias;
    @XmlElement(name="IgnoreAdditionalCertificates", defaultValue="false")
    protected Boolean ignoreAdditionalCertificates;
    @XmlElement(name="IntegrityPassphraseID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String integrityPassphraseID;
    @XmlElement(name="EncryptionPassphraseID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String encryptionPassphraseID;
    @XmlElement(name="Passphrase")
    protected String passphrase;

    public byte[] getCertWithPrivateKey() {
        return this.certWithPrivateKey;
    }

    public void setCertWithPrivateKey(byte[] value) {
        this.certWithPrivateKey = value;
    }

    public String getCertificationPathAlias() {
        return this.certificationPathAlias;
    }

    public void setCertificationPathAlias(String value) {
        this.certificationPathAlias = value;
    }

    public String getKeyAlias() {
        return this.keyAlias;
    }

    public void setKeyAlias(String value) {
        this.keyAlias = value;
    }

    public Boolean getIgnoreAdditionalCertificates() {
        return this.ignoreAdditionalCertificates;
    }

    public void setIgnoreAdditionalCertificates(Boolean value) {
        this.ignoreAdditionalCertificates = value;
    }

    public String getIntegrityPassphraseID() {
        return this.integrityPassphraseID;
    }

    public void setIntegrityPassphraseID(String value) {
        this.integrityPassphraseID = value;
    }

    public String getEncryptionPassphraseID() {
        return this.encryptionPassphraseID;
    }

    public void setEncryptionPassphraseID(String value) {
        this.encryptionPassphraseID = value;
    }

    public String getPassphrase() {
        return this.passphrase;
    }

    public void setPassphrase(String value) {
        this.passphrase = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

