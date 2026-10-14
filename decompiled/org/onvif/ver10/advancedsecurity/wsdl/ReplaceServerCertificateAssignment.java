/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"oldCertificationPathID", "newCertificationPathID"})
@XmlRootElement(name="ReplaceServerCertificateAssignment")
public class ReplaceServerCertificateAssignment {
    @XmlElement(name="OldCertificationPathID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String oldCertificationPathID;
    @XmlElement(name="NewCertificationPathID", required=true)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected String newCertificationPathID;

    public String getOldCertificationPathID() {
        return this.oldCertificationPathID;
    }

    public void setOldCertificationPathID(String value) {
        this.oldCertificationPathID = value;
    }

    public String getNewCertificationPathID() {
        return this.newCertificationPathID;
    }

    public void setNewCertificationPathID(String value) {
        this.newCertificationPathID = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

