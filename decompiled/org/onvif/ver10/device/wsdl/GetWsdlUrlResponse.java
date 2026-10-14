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
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"wsdlUrl"})
@XmlRootElement(name="GetWsdlUrlResponse")
public class GetWsdlUrlResponse {
    @XmlElement(name="WsdlUrl", required=true)
    @XmlSchemaType(name="anyURI")
    protected String wsdlUrl;

    public String getWsdlUrl() {
        return this.wsdlUrl;
    }

    public void setWsdlUrl(String value) {
        this.wsdlUrl = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

