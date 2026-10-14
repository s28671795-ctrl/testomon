/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.CertPathValidationParameters;
import org.onvif.ver10.advancedsecurity.wsdl.TrustAnchor;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"alias", "parameters", "trustAnchor", "anyParameters"})
@XmlRootElement(name="CreateCertPathValidationPolicy")
public class CreateCertPathValidationPolicy {
    @XmlElement(name="Alias")
    protected String alias;
    @XmlElement(name="Parameters", required=true)
    protected CertPathValidationParameters parameters;
    @XmlElement(name="TrustAnchor", required=true)
    protected List<TrustAnchor> trustAnchor;
    protected AnyParameters anyParameters;

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public CertPathValidationParameters getParameters() {
        return this.parameters;
    }

    public void setParameters(CertPathValidationParameters value) {
        this.parameters = value;
    }

    public List<TrustAnchor> getTrustAnchor() {
        if (this.trustAnchor == null) {
            this.trustAnchor = new ArrayList<TrustAnchor>();
        }
        return this.trustAnchor;
    }

    public AnyParameters getAnyParameters() {
        return this.anyParameters;
    }

    public void setAnyParameters(AnyParameters value) {
        this.anyParameters = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }

    @XmlAccessorType(value=XmlAccessType.FIELD)
    @XmlType(name="", propOrder={"any"})
    public static class AnyParameters {
        @XmlAnyElement(lax=true)
        protected List<Object> any;

        public List<Object> getAny() {
            if (this.any == null) {
                this.any = new ArrayList<Object>();
            }
            return this.any;
        }

        public String toString() {
            return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
        }
    }
}

