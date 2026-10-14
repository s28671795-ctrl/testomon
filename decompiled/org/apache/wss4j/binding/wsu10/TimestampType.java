/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.binding.wsu10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.wss4j.binding.wsu10.AttributedDateTime;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TimestampType", propOrder={"created", "expires", "any"})
public class TimestampType {
    @XmlElement(name="Created")
    private AttributedDateTime created;
    @XmlElement(name="Expires")
    private AttributedDateTime expires;
    @XmlAnyElement(lax=true)
    private List<Object> any;
    @XmlAttribute(name="Id", namespace="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name="ID")
    private String id;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AttributedDateTime getCreated() {
        return this.created;
    }

    public void setCreated(AttributedDateTime value) {
        this.created = value;
    }

    public AttributedDateTime getExpires() {
        return this.expires;
    }

    public void setExpires(AttributedDateTime value) {
        this.expires = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
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

