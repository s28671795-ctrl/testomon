/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.v200408;

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
@XmlType(name="Relationship", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", propOrder={"value"})
public class Relationship {
    @XmlValue
    @XmlSchemaType(name="anyURI")
    protected String value;
    @XmlAttribute(name="RelationshipType")
    protected QName relationshipType;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public QName getRelationshipType() {
        return this.relationshipType;
    }

    public void setRelationshipType(QName value) {
        this.relationshipType = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

