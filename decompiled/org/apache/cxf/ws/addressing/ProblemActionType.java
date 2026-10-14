/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.addressing.AttributedURIType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="ProblemActionType", namespace="http://www.w3.org/2005/08/addressing", propOrder={"action", "soapAction"})
public class ProblemActionType {
    @XmlElement(name="Action", namespace="http://www.w3.org/2005/08/addressing")
    protected AttributedURIType action;
    @XmlElement(name="SoapAction", namespace="http://www.w3.org/2005/08/addressing")
    @XmlSchemaType(name="anyURI")
    protected String soapAction;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public AttributedURIType getAction() {
        return this.action;
    }

    public void setAction(AttributedURIType value) {
        this.action = value;
    }

    public String getSoapAction() {
        return this.soapAction;
    }

    public void setSoapAction(String value) {
        this.soapAction = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

