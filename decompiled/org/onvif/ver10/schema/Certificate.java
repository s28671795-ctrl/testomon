/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.BinaryData;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Certificate", propOrder={"certificateID", "certificate"})
public class Certificate {
    @XmlElement(name="CertificateID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="token")
    protected String certificateID;
    @XmlElement(name="Certificate", required=true)
    protected BinaryData certificate;

    public String getCertificateID() {
        return this.certificateID;
    }

    public void setCertificateID(String value) {
        this.certificateID = value;
    }

    public BinaryData getCertificate() {
        return this.certificate;
    }

    public void setCertificate(BinaryData value) {
        this.certificate = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

