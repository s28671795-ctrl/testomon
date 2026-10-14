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
import org.onvif.ver10.schema.NetworkHost;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"dpAddress"})
@XmlRootElement(name="GetDPAddressesResponse")
public class GetDPAddressesResponse {
    @XmlElement(name="DPAddress")
    protected List<NetworkHost> dpAddress;

    public List<NetworkHost> getDPAddress() {
        if (this.dpAddress == null) {
            this.dpAddress = new ArrayList<NetworkHost>();
        }
        return this.dpAddress;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

