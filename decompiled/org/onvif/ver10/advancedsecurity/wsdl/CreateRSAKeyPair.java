/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"keyLength", "alias"})
@XmlRootElement(name="CreateRSAKeyPair")
public class CreateRSAKeyPair {
    @XmlElement(name="KeyLength", required=true)
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger keyLength;
    @XmlElement(name="Alias")
    protected String alias;

    public BigInteger getKeyLength() {
        return this.keyLength;
    }

    public void setKeyLength(BigInteger value) {
        this.keyLength = value;
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

