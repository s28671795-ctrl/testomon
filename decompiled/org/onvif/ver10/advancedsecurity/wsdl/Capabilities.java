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
import org.onvif.ver10.advancedsecurity.wsdl.KeystoreCapabilities;
import org.onvif.ver10.advancedsecurity.wsdl.TLSServerCapabilities;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Capabilities", propOrder={"keystoreCapabilities", "tlsServerCapabilities", "any"})
public class Capabilities {
    @XmlElement(name="KeystoreCapabilities", required=true)
    protected KeystoreCapabilities keystoreCapabilities;
    @XmlElement(name="TLSServerCapabilities", required=true)
    protected TLSServerCapabilities tlsServerCapabilities;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public KeystoreCapabilities getKeystoreCapabilities() {
        return this.keystoreCapabilities;
    }

    public void setKeystoreCapabilities(KeystoreCapabilities value) {
        this.keystoreCapabilities = value;
    }

    public TLSServerCapabilities getTLSServerCapabilities() {
        return this.tlsServerCapabilities;
    }

    public void setTLSServerCapabilities(TLSServerCapabilities value) {
        this.tlsServerCapabilities = value;
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

