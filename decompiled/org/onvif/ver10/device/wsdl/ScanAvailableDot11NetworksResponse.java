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
import org.onvif.ver10.schema.Dot11AvailableNetworks;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"networks"})
@XmlRootElement(name="ScanAvailableDot11NetworksResponse")
public class ScanAvailableDot11NetworksResponse {
    @XmlElement(name="Networks")
    protected List<Dot11AvailableNetworks> networks;

    public List<Dot11AvailableNetworks> getNetworks() {
        if (this.networks == null) {
            this.networks = new ArrayList<Dot11AvailableNetworks>();
        }
        return this.networks;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, JAXBToStringStyle.DEFAULT_STYLE);
    }
}

