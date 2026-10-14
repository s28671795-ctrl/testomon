/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.v200607;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.policy.v200607.OperatorContentType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="")
@XmlRootElement(name="Policy")
public class Policy
extends OperatorContentType {
    @XmlAttribute(name="Name")
    @XmlSchemaType(name="anyURI")
    protected String name;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }
}

