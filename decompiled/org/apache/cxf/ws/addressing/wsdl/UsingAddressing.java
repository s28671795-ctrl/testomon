/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.wsdl;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="UsingAddressing", namespace="http://www.w3.org/2006/05/addressing/wsdl")
public class UsingAddressing {
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

