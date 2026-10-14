/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.AlgorithmIdentifier;
import org.onvif.ver10.advancedsecurity.wsdl.CSRAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.DistinguishedName;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"subject", "keyID", "csrAttribute", "signatureAlgorithm"})
@XmlRootElement(name="CreatePKCS10CSR")
public class CreatePKCS10CSR {
    @XmlElement(name="Subject", required=true)
    protected DistinguishedName subject;
    @XmlElement(name="KeyID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String keyID;
    @XmlElement(name="CSRAttribute")
    protected List<CSRAttribute> csrAttribute;
    @XmlElement(name="SignatureAlgorithm", required=true)
    protected AlgorithmIdentifier signatureAlgorithm;

    public DistinguishedName getSubject() {
        return this.subject;
    }

    public void setSubject(DistinguishedName value) {
        this.subject = value;
    }

    public String getKeyID() {
        return this.keyID;
    }

    public void setKeyID(String value) {
        this.keyID = value;
    }

    public List<CSRAttribute> getCSRAttribute() {
        if (this.csrAttribute == null) {
            this.csrAttribute = new ArrayList<CSRAttribute>();
        }
        return this.csrAttribute;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public void setSignatureAlgorithm(AlgorithmIdentifier value) {
        this.signatureAlgorithm = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

