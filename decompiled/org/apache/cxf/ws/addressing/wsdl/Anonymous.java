/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.wsdl;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.addressing.wsdl.AnonymousType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"value"})
@XmlRootElement(name="Anonymous", namespace="http://www.w3.org/2006/05/addressing/wsdl")
public class Anonymous {
    @XmlValue
    protected AnonymousType value;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AnonymousType getValue() {
        return this.value;
    }

    public void setValue(AnonymousType value) {
        this.value = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

