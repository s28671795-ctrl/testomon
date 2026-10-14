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
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CertPathValidationParameters", propOrder={"requireTLSWWWClientAuthExtendedKeyUsage", "useDeltaCRLs", "anyParameters"})
public class CertPathValidationParameters {
    @XmlElement(name="RequireTLSWWWClientAuthExtendedKeyUsage", defaultValue="false")
    protected Boolean requireTLSWWWClientAuthExtendedKeyUsage;
    @XmlElement(name="UseDeltaCRLs", defaultValue="false")
    protected Boolean useDeltaCRLs;
    protected AnyParameters anyParameters;

    public Boolean getRequireTLSWWWClientAuthExtendedKeyUsage() {
        return this.requireTLSWWWClientAuthExtendedKeyUsage;
    }

    public void setRequireTLSWWWClientAuthExtendedKeyUsage(Boolean value) {
        this.requireTLSWWWClientAuthExtendedKeyUsage = value;
    }

    public Boolean getUseDeltaCRLs() {
        return this.useDeltaCRLs;
    }

    public void setUseDeltaCRLs(Boolean value) {
        this.useDeltaCRLs = value;
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

