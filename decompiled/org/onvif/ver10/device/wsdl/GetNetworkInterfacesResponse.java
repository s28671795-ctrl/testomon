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
import org.onvif.ver10.schema.NetworkInterface;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"networkInterfaces"})
@XmlRootElement(name="GetNetworkInterfacesResponse")
public class GetNetworkInterfacesResponse {
    @XmlElement(name="NetworkInterfaces", required=true)
    protected List<NetworkInterface> networkInterfaces;

    public List<NetworkInterface> getNetworkInterfaces() {
        if (this.networkInterfaces == null) {
            this.networkInterfaces = new ArrayList<NetworkInterface>();
        }
        return this.networkInterfaces;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

