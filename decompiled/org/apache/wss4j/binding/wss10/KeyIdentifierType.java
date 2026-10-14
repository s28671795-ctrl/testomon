/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.wss4j.binding.wss10.EncodedString;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="KeyIdentifierType")
public class KeyIdentifierType
extends EncodedString {
    @XmlAttribute(name="ValueType")
    @XmlSchemaType(name="anyURI")
    private String valueType;

    public String getValueType() {
        return this.valueType;
    }

    public void setValueType(String value) {
        this.valueType = value;
    }
}

