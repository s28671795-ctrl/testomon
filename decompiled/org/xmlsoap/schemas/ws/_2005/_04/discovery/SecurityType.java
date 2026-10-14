/*
 * Decompiled with CFR 0.152.
 */
package org.xmlsoap.schemas.ws._2005._04.discovery;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.xmlsoap.schemas.ws._2005._04.discovery.SigType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SecurityType", propOrder={"sig"})
public class SecurityType {
    @XmlElement(name="Sig")
    protected SigType sig;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public SigType getSig() {
        return this.sig;
    }

    public void setSig(SigType value) {
        this.sig = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

