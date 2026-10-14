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
@XmlType(name="", propOrder={"keyPair", "alias", "encryptionPassphraseID", "encryptionPassphrase"})
@XmlRootElement(name="UploadKeyPairInPKCS8")
public class UploadKeyPairInPKCS8 {
    @XmlElement(name="KeyPair", required=true)
    protected byte[] keyPair;
    @XmlElement(name="Alias")
    protected String alias;
    @XmlElement(name="EncryptionPassphraseID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String encryptionPassphraseID;
    @XmlElement(name="EncryptionPassphrase")
    protected String encryptionPassphrase;

    public byte[] getKeyPair() {
        return this.keyPair;
    }

    public void setKeyPair(byte[] value) {
        this.keyPair = value;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public String getEncryptionPassphraseID() {
        return this.encryptionPassphraseID;
    }

    public void setEncryptionPassphraseID(String value) {
        this.encryptionPassphraseID = value;
    }

    public String getEncryptionPassphrase() {
        return this.encryptionPassphrase;
    }

    public void setEncryptionPassphrase(String value) {
        this.encryptionPassphrase = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

