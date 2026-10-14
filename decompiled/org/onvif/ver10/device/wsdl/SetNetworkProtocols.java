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
import org.onvif.ver10.schema.NetworkProtocol;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"networkProtocols"})
@XmlRootElement(name="SetNetworkProtocols")
public class SetNetworkProtocols {
    @XmlElement(name="NetworkProtocols", required=true)
    protected List<NetworkProtocol> networkProtocols;

    public List<NetworkProtocol> getNetworkProtocols() {
        if (this.networkProtocols == null) {
            this.networkProtocols = new ArrayList<NetworkProtocol>();
        }
        return this.networkProtocols;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

