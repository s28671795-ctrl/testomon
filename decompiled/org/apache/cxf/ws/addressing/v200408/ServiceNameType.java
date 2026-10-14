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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ServiceNameType", namespace="http://schemas.xmlsoap.org/ws/2004/08/addressing", propOrder={"value"})
public class ServiceNameType {
    @XmlValue
    protected QName value;
    @XmlAttribute(name="PortName")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String portName;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public QName getValue() {
        return this.value;
    }

    public void setValue(QName value) {
        this.value = value;
    }

    public String getPortName() {
        return this.portName;
    }

    public void setPortName(String value) {
        this.portName = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

