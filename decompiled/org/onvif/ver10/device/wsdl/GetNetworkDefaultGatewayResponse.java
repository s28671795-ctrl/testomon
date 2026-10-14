/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.device.wsdl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.cxf.xjc.runtime.JAXBToStringStyle;
import org.onvif.ver10.schema.NetworkGateway;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"networkGateway"})
@XmlRootElement(name="GetNetworkDefaultGatewayResponse")
public class GetNetworkDefaultGatewayResponse {
    @XmlElement(name="NetworkGateway", required=true)
    protected NetworkGateway networkGateway;

    public NetworkGateway getNetworkGateway() {
        return this.networkGateway;
    }

    public void setNetworkGateway(NetworkGateway value) {
        this.networkGateway = value;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

