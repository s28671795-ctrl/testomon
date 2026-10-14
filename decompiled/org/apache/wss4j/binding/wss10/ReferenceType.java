/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss10;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ReferenceType")
public class ReferenceType {
    @XmlAttribute(name="URI")
    @XmlSchemaType(name="anyURI")
    private String uri;
    @XmlAttribute(name="ValueType")
    @XmlSchemaType(name="anyURI")
    private String valueType;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getURI() {
        return this.uri;
    }

    public void setURI(String value) {
        this.uri = value;
    }

    public String getValueType() {
        return this.valueType;
    }

    public void setValueType(String value) {
        this.valueType = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

