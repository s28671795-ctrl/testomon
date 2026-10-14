/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.secext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.security.sts.provider.model.secext.AttributedString;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="PasswordString")
public class PasswordString
extends AttributedString {
    @XmlAttribute(name="Type")
    @XmlSchemaType(name="anyURI")
    protected String type;

    public String getType() {
        return this.type;
    }

    public void setType(String value) {
        this.type = value;
    }
}

