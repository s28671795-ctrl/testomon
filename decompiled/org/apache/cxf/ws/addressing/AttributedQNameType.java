/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="AttributedQNameType", namespace="http://www.w3.org/2005/08/addressing", propOrder={"value"})
public class AttributedQNameType {
    @XmlValue
    protected QName value;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public QName getValue() {
        return this.value;
    }

    public void setValue(QName value) {
        this.value = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

