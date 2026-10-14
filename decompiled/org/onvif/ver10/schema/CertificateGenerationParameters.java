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
import org.onvif.ver10.schema.CertificateGenerationParametersExtension;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="CertificateGenerationParameters", propOrder={"certificateID", "subject", "validNotBefore", "validNotAfter", "extension"})
public class CertificateGenerationParameters {
    @XmlElement(name="CertificateID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String certificateID;
    @XmlElement(name="Subject")
    protected String subject;
    @XmlElement(name="ValidNotBefore")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String validNotBefore;
    @XmlElement(name="ValidNotAfter")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String validNotAfter;
    @XmlElement(name="Extension")
    protected CertificateGenerationParametersExtension extension;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    public String getCertificateID() {
        return this.certificateID;
    }

    public void setCertificateID(String value) {
        this.certificateID = value;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String value) {
        this.subject = value;
    }

    public String getValidNotBefore() {
        return this.validNotBefore;
    }

    public void setValidNotBefore(String value) {
        this.validNotBefore = value;
    }

    public String getValidNotAfter() {
        return this.validNotAfter;
    }

    public void setValidNotAfter(String value) {
        this.validNotAfter = value;
    }

    public CertificateGenerationParametersExtension getExtension() {
        return this.extension;
    }

    public void setExtension(CertificateGenerationParametersExtension value) {
        this.extension = value;
    }

    public Map<QName, String> getOtherAttributes() {
        return this.otherAttributes;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

