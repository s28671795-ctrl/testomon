/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model.secext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.apache.cxf.ws.security.sts.provider.model.secext.EncodedString;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="KeyIdentifierType")
public class KeyIdentifierType
extends EncodedString {
    @XmlAttribute(name="ValueType")
    @XmlSchemaType(name="anyURI")
    protected String valueType;

    public String getValueType() {
        return this.valueType;
    }

    public void setValueType(String value) {
        this.valueType = value;
    }
}

