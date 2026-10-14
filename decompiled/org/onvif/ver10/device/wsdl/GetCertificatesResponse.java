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
@XmlType(name="", propOrder={"nvtCertificate"})
@XmlRootElement(name="GetCertificatesResponse")
public class GetCertificatesResponse {
    @XmlElement(name="NvtCertificate")
    protected List<Certificate> nvtCertificate;

    public List<Certificate> getNvtCertificate() {
        if (this.nvtCertificate == null) {
            this.nvtCertificate = new ArrayList<Certificate>();
        }
        return this.nvtCertificate;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

