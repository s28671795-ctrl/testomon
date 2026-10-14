/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Dot11PSKSetExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Dot11PSKSet", propOrder={"key", "passphrase", "extension"})
public class Dot11PSKSet {
    @XmlElement(name="Key", type=String.class)
    @XmlJavaTypeAdapter(value=HexBinaryAdapter.class)
    @XmlSchemaType(name="hexBinary")
    protected byte[] key;
    @XmlElement(name="Passphrase")
    protected String passphrase;
    @XmlElement(name="Extension")
    protected Dot11PSKSetExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public byte[] getKey() {
        return this.key;
    }

    public void setKey(byte[] value) {
        this.key = value;
    }

    public String getPassphrase() {
        return this.passphrase;
    }

    public void setPassphrase(String value) {
        this.passphrase = value;
    }

    public Dot11PSKSetExtension getExtension() {
        return this.extension;
    }

    public void setExtension(Dot11PSKSetExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

