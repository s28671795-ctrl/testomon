/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.http;

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
@XmlType(name="addressType", namespace="http://schemas.xmlsoap.org/wsdl/http/")
public class AddressType {
    @XmlAttribute(name="location", required=true)
    @XmlSchemaType(name="anyURI")
    protected String location;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String value) {
        this.location = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

