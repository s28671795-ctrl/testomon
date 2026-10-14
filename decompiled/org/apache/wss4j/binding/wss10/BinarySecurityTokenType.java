/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wss10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="BinarySecurityTokenType", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", propOrder={"content"})
public class BinarySecurityTokenType {
    @XmlElementRef(name="Include", namespace="http://www.w3.org/2004/08/xop/include", type=JAXBElement.class)
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name="ValueType")
    @XmlSchemaType(name="anyURI")
    protected String valueType;
    @XmlAttribute(name="EncodingType")
    @XmlSchemaType(name="anyURI")
    protected String encodingType;
    @XmlAttribute(name="Id", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    protected String id;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Serializable> getContent() {
        if (this.content == null) {
            this.content = new ArrayList<Serializable>();
        }
        return this.content;
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

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

