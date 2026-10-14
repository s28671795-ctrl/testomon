/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

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
import org.onvif.ver10.schema.SecurityCapabilitiesExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="SecurityCapabilities", propOrder={"tls11", "tls12", "onboardKeyGeneration", "accessPolicyConfig", "x509Token", "samlToken", "kerberosToken", "relToken", "any", "extension"})
public class SecurityCapabilities {
    @XmlElement(name="TLS1.1")
    protected boolean tls11;
    @XmlElement(name="TLS1.2")
    protected boolean tls12;
    @XmlElement(name="OnboardKeyGeneration")
    protected boolean onboardKeyGeneration;
    @XmlElement(name="AccessPolicyConfig")
    protected boolean accessPolicyConfig;
    @XmlElement(name="X.509Token")
    protected boolean x509Token;
    @XmlElement(name="SAMLToken")
    protected boolean samlToken;
    @XmlElement(name="KerberosToken")
    protected boolean kerberosToken;
    @XmlElement(name="RELToken")
    protected boolean relToken;
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlElement(name="Extension")
    protected SecurityCapabilitiesExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public boolean getTLS11() {
        return this.tls11;
    }

    public void setTLS11(boolean value) {
        this.tls11 = value;
    }

    public boolean getTLS12() {
        return this.tls12;
    }

    public void setTLS12(boolean value) {
        this.tls12 = value;
    }

    public boolean getOnboardKeyGeneration() {
        return this.onboardKeyGeneration;
    }

    public void setOnboardKeyGeneration(boolean value) {
        this.onboardKeyGeneration = value;
    }

    public boolean getAccessPolicyConfig() {
        return this.accessPolicyConfig;
    }

    public void setAccessPolicyConfig(boolean value) {
        this.accessPolicyConfig = value;
    }

    public boolean getX509Token() {
        return this.x509Token;
    }

    public void setX509Token(boolean value) {
        this.x509Token = value;
    }

    public boolean getSAMLToken() {
        return this.samlToken;
    }

    public void setSAMLToken(boolean value) {
        this.samlToken = value;
    }

    public boolean getKerberosToken() {
        return this.kerberosToken;
    }

    public void setKerberosToken(boolean value) {
        this.kerberosToken = value;
    }

    public boolean getRELToken() {
        return this.relToken;
    }

    public void setRELToken(boolean value) {
        this.relToken = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public SecurityCapabilitiesExtension getExtension() {
        return this.extension;
    }

    public void setExtension(SecurityCapabilitiesExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

