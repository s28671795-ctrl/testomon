/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="X509v3Extension", propOrder={"extnOID", "critical", "extnValue", "any"})
public class X509V3Extension {
    @XmlElement(required=true)
    protected String extnOID;
    @XmlElement(defaultValue="false")
    protected boolean critical;
    @XmlElement(required=true)
    protected byte[] extnValue;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getExtnOID() {
        return this.extnOID;
    }

    public void setExtnOID(String value) {
        this.extnOID = value;
    }

    public boolean getCritical() {
        return this.critical;
    }

    public void setCritical(boolean value) {
        this.critical = value;
    }

    public byte[] getExtnValue() {
        return this.extnValue;
    }

    public void setExtnValue(byte[] value) {
        this.extnValue = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

