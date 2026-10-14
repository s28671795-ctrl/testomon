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
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.advancedsecurity.wsdl.CRL;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"crl"})
@XmlRootElement(name="GetAllCRLsResponse")
public class GetAllCRLsResponse {
    @XmlElement(name="Crl")
    protected List<CRL> crl;

    public List<CRL> getCrl() {
        if (this.crl == null) {
            this.crl = new ArrayList<CRL>();
        }
        return this.crl;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

