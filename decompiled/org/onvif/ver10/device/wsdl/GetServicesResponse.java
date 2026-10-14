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
import org.onvif.ver10.device.wsdl.Service;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"service"})
@XmlRootElement(name="GetServicesResponse")
public class GetServicesResponse {
    @XmlElement(name="Service", required=true)
    protected List<Service> service;

    public List<Service> getService() {
        if (this.service == null) {
            this.service = new ArrayList<Service>();
        }
        return this.service;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

