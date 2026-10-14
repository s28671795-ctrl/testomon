/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.math.BigInteger;
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
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.AlgorithmIdentifier;
import org.onvif.ver10.advancedsecurity.wsdl.DistinguishedName;
import org.onvif.ver10.advancedsecurity.wsdl.X509V3Extension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"x509Version", "subject", "keyID", "alias", "notValidBefore", "notValidAfter", "signatureAlgorithm", "extension"})
@XmlRootElement(name="CreateSelfSignedCertificate")
public class CreateSelfSignedCertificate {
    @XmlElement(name="X509Version")
    @XmlSchemaType(name="positiveInteger")
    protected BigInteger x509Version;
    @XmlElement(name="Subject", required=true)
    protected DistinguishedName subject;
    @XmlElement(name="KeyID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String keyID;
    @XmlElement(name="Alias")
    protected String alias;
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar notValidBefore;
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar notValidAfter;
    @XmlElement(name="SignatureAlgorithm", required=true)
    protected AlgorithmIdentifier signatureAlgorithm;
    @XmlElement(name="Extension")
    protected List<X509V3Extension> extension;

    public BigInteger getX509Version() {
        return this.x509Version;
    }

    public void setX509Version(BigInteger value) {
        this.x509Version = value;
    }

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

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String value) {
        this.alias = value;
    }

    public XMLGregorianCalendar getNotValidBefore() {
        return this.notValidBefore;
    }

    public void setNotValidBefore(XMLGregorianCalendar value) {
        this.notValidBefore = value;
    }

    public XMLGregorianCalendar getNotValidAfter() {
        return this.notValidAfter;
    }

    public void setNotValidAfter(XMLGregorianCalendar value) {
        this.notValidAfter = value;
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public void setSignatureAlgorithm(AlgorithmIdentifier value) {
        this.signatureAlgorithm = value;
    }

    public List<X509V3Extension> getExtension() {
        if (this.extension == null) {
            this.extension = new ArrayList<X509V3Extension>();
        }
        return this.extension;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

