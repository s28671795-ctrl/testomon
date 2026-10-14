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
@XmlType(name="", propOrder={"passphrase", "passphraseAlias"})
@XmlRootElement(name="UploadPassphrase")
public class UploadPassphrase {
    @XmlElement(name="Passphrase", required=true)
    protected String passphrase;
    @XmlElement(name="PassphraseAlias")
    protected String passphraseAlias;

    public String getPassphrase() {
        return this.passphrase;
    }

    public void setPassphrase(String value) {
        this.passphrase = value;
    }

    public String getPassphraseAlias() {
        return this.passphraseAlias;
    }

    public void setPassphraseAlias(String value) {
        this.passphraseAlias = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

