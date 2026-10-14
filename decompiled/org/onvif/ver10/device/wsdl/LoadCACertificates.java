/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.Certificate;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"caCertificate"})
@XmlRootElement(name="LoadCACertificates")
public class LoadCACertificates {
    @XmlElement(name="CACertificate", required=true)
    protected List<Certificate> caCertificate;

    public List<Certificate> getCACertificate() {
        if (this.caCertificate == null) {
            this.caCertificate = new ArrayList<Certificate>();
        }
        return this.caCertificate;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

