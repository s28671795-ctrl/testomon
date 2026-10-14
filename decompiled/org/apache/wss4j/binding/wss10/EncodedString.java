/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.apache.wss4j.binding.wss10.AttributedString;
import org.apache.wss4j.binding.wss10.BinarySecurityTokenType;
import org.apache.wss4j.binding.wss10.KeyIdentifierType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="EncodedString")
@XmlSeeAlso(value={BinarySecurityTokenType.class, KeyIdentifierType.class})
public class EncodedString
extends AttributedString {
    @XmlAttribute(name="EncodingType")
    @XmlSchemaType(name="anyURI")
    private String encodingType;

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String value) {
        this.encodingType = value;
    }
}

