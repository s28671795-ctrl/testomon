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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.BinaryData;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificateID", "subject", "attributes"})
@XmlRootElement(name="GetPkcs10Request")
public class GetPkcs10Request {
    @XmlElement(name="CertificateID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String certificateID;
    @XmlElement(name="Subject")
    protected String subject;
    @XmlElement(name="Attributes")
    protected BinaryData attributes;

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

    public BinaryData getAttributes() {
        return this.attributes;
    }

    public void setAttributes(BinaryData value) {
        this.attributes = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

