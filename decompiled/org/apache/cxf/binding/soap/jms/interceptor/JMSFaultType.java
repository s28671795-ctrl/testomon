/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.jms.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.namespace.QName;

public class JMSFaultType {
    @XmlElement(name="FaultCode", required=true)
    protected QName faultCode;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public QName getFaultCode() {
        return this.faultCode;
    }

    public void setFaultCode(QName value) {
        this.faultCode = value;
    }

    public boolean isSetFaultCode() {
        return this.faultCode != null;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public boolean isSetAny() {
        return this.any != null && !this.any.isEmpty();
    }

    public void unsetAny() {
        this.any = null;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

