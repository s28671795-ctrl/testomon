/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.PassphraseAttribute;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"passphraseAttribute"})
@XmlRootElement(name="GetAllPassphrasesResponse")
public class GetAllPassphrasesResponse {
    @XmlElement(name="PassphraseAttribute")
    protected List<PassphraseAttribute> passphraseAttribute;

    public List<PassphraseAttribute> getPassphraseAttribute() {
        if (this.passphraseAttribute == null) {
            this.passphraseAttribute = new ArrayList<PassphraseAttribute>();
        }
        return this.passphraseAttribute;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

