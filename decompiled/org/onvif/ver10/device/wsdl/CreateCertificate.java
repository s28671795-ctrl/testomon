/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificateID", "subject", "validNotBefore", "validNotAfter"})
@XmlRootElement(name="CreateCertificate")
public class CreateCertificate {
    @XmlElement(name="CertificateID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String certificateID;
    @XmlElement(name="Subject")
    protected String subject;
    @XmlElement(name="ValidNotBefore")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar validNotBefore;
    @XmlElement(name="ValidNotAfter")
    @XmlSchemaType(name="dateTime")
    protected XMLGregorianCalendar validNotAfter;

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

    public XMLGregorianCalendar getValidNotBefore() {
        return this.validNotBefore;
    }

    public void setValidNotBefore(XMLGregorianCalendar value) {
        this.validNotBefore = value;
    }

    public XMLGregorianCalendar getValidNotAfter() {
        return this.validNotAfter;
    }

    public void setValidNotAfter(XMLGregorianCalendar value) {
        this.validNotAfter = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

