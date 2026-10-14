/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BinaryExchangeType", propOrder={"value"})
public class BinaryExchangeType {
    @XmlValue
    protected String value;
    @XmlAttribute(name="ValueType", required=true)
    @XmlSchemaType(name="anyURI")
    protected String valueType;
    @XmlAttribute(name="EncodingType", required=true)
    @XmlSchemaType(name="anyURI")
    protected String encodingType;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValueType() {
        return this.valueType;
    }

    public void setValueType(String value) {
        this.valueType = value;
    }

    public String getEncodingType() {
        return this.encodingType;
    }

    public void setEncodingType(String value) {
        this.encodingType = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

