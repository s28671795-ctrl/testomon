/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.CertificateInformationExtension;
import org.onvif.ver10.schema.CertificateUsage;
import org.onvif.ver10.schema.DateTimeRange;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CertificateInformation", propOrder={"certificateID", "issuerDN", "subjectDN", "keyUsage", "extendedKeyUsage", "keyLength", "version", "serialNum", "signatureAlgorithm", "validity", "extension"})
public class CertificateInformation {
    @XmlElement(name="CertificateID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String certificateID;
    @XmlElement(name="IssuerDN")
    protected String issuerDN;
    @XmlElement(name="SubjectDN")
    protected String subjectDN;
    @XmlElement(name="KeyUsage")
    protected CertificateUsage keyUsage;
    @XmlElement(name="ExtendedKeyUsage")
    protected CertificateUsage extendedKeyUsage;
    @XmlElement(name="KeyLength")
    protected Integer keyLength;
    @XmlElement(name="Version")
    protected String version;
    @XmlElement(name="SerialNum")
    protected String serialNum;
    @XmlElement(name="SignatureAlgorithm")
    protected String signatureAlgorithm;
    @XmlElement(name="Validity")
    protected DateTimeRange validity;
    @XmlElement(name="Extension")
    protected CertificateInformationExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getCertificateID() {
        return this.certificateID;
    }

    public void setCertificateID(String value) {
        this.certificateID = value;
    }

    public String getIssuerDN() {
        return this.issuerDN;
    }

    public void setIssuerDN(String value) {
        this.issuerDN = value;
    }

    public String getSubjectDN() {
        return this.subjectDN;
    }

    public void setSubjectDN(String value) {
        this.subjectDN = value;
    }

    public CertificateUsage getKeyUsage() {
        return this.keyUsage;
    }

    public void setKeyUsage(CertificateUsage value) {
        this.keyUsage = value;
    }

    public CertificateUsage getExtendedKeyUsage() {
        return this.extendedKeyUsage;
    }

    public void setExtendedKeyUsage(CertificateUsage value) {
        this.extendedKeyUsage = value;
    }

    public Integer getKeyLength() {
        return this.keyLength;
    }

    public void setKeyLength(Integer value) {
        this.keyLength = value;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String value) {
        this.version = value;
    }

    public String getSerialNum() {
        return this.serialNum;
    }

    public void setSerialNum(String value) {
        this.serialNum = value;
    }

    public String getSignatureAlgorithm() {
        return this.signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String value) {
        this.signatureAlgorithm = value;
    }

    public DateTimeRange getValidity() {
        return this.validity;
    }

    public void setValidity(DateTimeRange value) {
        this.validity = value;
    }

    public CertificateInformationExtension getExtension() {
        return this.extension;
    }

    public void setExtension(CertificateInformationExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

