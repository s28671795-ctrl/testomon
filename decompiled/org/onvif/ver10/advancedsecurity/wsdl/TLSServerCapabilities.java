/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="TLSServerCapabilities", propOrder={"any"})
public class TLSServerCapabilities {
    @XmlAnyElement(lax=true)
    protected List<Object> any;
    @XmlAttribute(name="TLSServerSupported")
    protected List<String> tlsServerSupported;
    @XmlAttribute(name="MaximumNumberOfTLSCertificationPaths")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger maximumNumberOfTLSCertificationPaths;
    @XmlAttribute(name="TLSClientAuthSupported")
    protected Boolean tlsClientAuthSupported;
    @XmlAttribute(name="MaximumNumberOfTLSCertificationPathValidationPolicies")
    @XmlSchemaType(name="nonNegativeInteger")
    protected BigInteger maximumNumberOfTLSCertificationPathValidationPolicies;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList<Object>();
        }
        return this.any;
    }

    public List<String> getTLSServerSupported() {
        if (this.tlsServerSupported == null) {
            this.tlsServerSupported = new ArrayList<String>();
        }
        return this.tlsServerSupported;
    }

    public BigInteger getMaximumNumberOfTLSCertificationPaths() {
        return this.maximumNumberOfTLSCertificationPaths;
    }

    public void setMaximumNumberOfTLSCertificationPaths(BigInteger value) {
        this.maximumNumberOfTLSCertificationPaths = value;
    }

    public Boolean getTLSClientAuthSupported() {
        return this.tlsClientAuthSupported;
    }

    public void setTLSClientAuthSupported(Boolean value) {
        this.tlsClientAuthSupported = value;
    }

    public BigInteger getMaximumNumberOfTLSCertificationPathValidationPolicies() {
        return this.maximumNumberOfTLSCertificationPathValidationPolicies;
    }

    public void setMaximumNumberOfTLSCertificationPathValidationPolicies(BigInteger value) {
        this.maximumNumberOfTLSCertificationPathValidationPolicies = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

