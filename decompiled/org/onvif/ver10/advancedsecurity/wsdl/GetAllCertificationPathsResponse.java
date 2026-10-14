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

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"certificationPathID"})
@XmlRootElement(name="GetAllCertificationPathsResponse")
public class GetAllCertificationPathsResponse {
    @XmlElement(name="CertificationPathID")
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlSchemaType(name="NCName")
    protected List<String> certificationPathID;

    public List<String> getCertificationPathID() {
        if (this.certificationPathID == null) {
            this.certificationPathID = new ArrayList<String>();
        }
        return this.certificationPathID;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

