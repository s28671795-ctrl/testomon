/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss10;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.wss4j.binding.wss10.AttributedString;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PasswordString")
public class PasswordString
extends AttributedString {
    @XmlAttribute(name="Type")
    @XmlSchemaType(name="anyURI")
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }
}

